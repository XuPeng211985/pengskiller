package com.seckillpeng.server.impl;
import com.seckillpeng.dao.SeckillGoodsDao;
import com.seckillpeng.dao.SuccessKilledDao;
import com.seckillpeng.dto.Exposer;
import com.seckillpeng.dto.SeckillExecution;
import com.seckillpeng.entity.SeckillGoods;
import com.seckillpeng.entity.SuccessKilled;
import com.seckillpeng.enums.SeckillStatuEnums;
import com.seckillpeng.exception.RepeatKillException;
import com.seckillpeng.exception.SeckillCloseException;
import com.seckillpeng.exception.SeckillException;
import com.seckillpeng.server.SeckillGoodsServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import java.util.Date;
import java.util.List;
@Service
public class SeckillGoodsServerImpl implements SeckillGoodsServer {
    //定义商品Dao属性
    @Autowired
    private SeckillGoodsDao seckillGoodsDao;
    //定义秒杀Dao属性
    @Autowired
    private SuccessKilledDao successKilledDao;
    //指定日志
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 查找商品列表，直接调用Dao中的实现方法
     *
     * @return
     */
    @Override
    public List<SeckillGoods> findAllSeckillGoods() {
        return seckillGoodsDao.queryAll(0, 5);
    }

    /**
     * 查找指定id号的商品，直接调用Dao中的方法
     *
     * @param seckillId
     * @return
     */
    @Override
    public SeckillGoods findSeckillGoodsById(long seckillId) {
        return seckillGoodsDao.queryById(seckillId);
    }

    /**
     * 尝试提交秒杀请求，获得秒杀接口以便于执行秒杀
     *
     * @param seckillId
     * @return 根据不同的情况返回包装类的不同字段
     * @throws SeckillException
     * @throws SeckillCloseException
     * @throws RepeatKillException
     */
    @Override
    public Exposer exportSeckillUrl(long seckillId) throws SeckillException, SeckillCloseException, RepeatKillException {
        //在数据库中查询出来请求的商品对象
        SeckillGoods seckillGoods = seckillGoodsDao.queryById(seckillId);
        //如果为空，查询失败 请求秒杀失败
        if (null == seckillGoods) {
            //回滚所查询的id号 将请求秒杀状态设置为false
            return new Exposer(false, seckillId);
        }
        //走到这一步 相当于查询成功，则获取该对象的基本信息
        Date startTime = seckillGoods.getStartTime();
        Date endTime = seckillGoods.getEndTime();
        Date nowTime = new Date();
        //判断当前时间是否可以秒杀该商品
        if (nowTime.getTime() < startTime.getTime()
                || nowTime.getTime() > endTime.getTime()) {
            //不在秒杀时间范围内则请求失败
            return new Exposer(false, startTime.getTime(), nowTime.getTime(), endTime.getTime(), seckillId);
        }
        //获取id号加密后的码字
        String md5 = getMd5(seckillId);
        //获取秒杀接口，将码字传递给用户
        return new Exposer(true, md5, seckillId);
    }
    private String getMd5(long seckillId) {
        String base = seckillId + "/" + "gndsjgnjv#$%^&dkrgh*&";
        String md5 = DigestUtils.md5DigestAsHex(base.getBytes());
        return md5;
    }
    @Override
    @Transactional
    public SeckillExecution executeSeckillProcedure(long seckillId, long userPhone, String md5) {
        Date nowTime = new Date();
        //如果用户传过来的加密id号和服务器生成的加密id号不匹配
        //说明用户拿到的秒杀接口是错误的 或者是用户篡改了码字
        if (md5 == null || !md5.equals(getMd5(seckillId))) {
            throw new SeckillCloseException("seckill is rewrite");
        } else {
            //如果加密id和服务器生成的id完全相同 那么插入秒杀明细 检查当前商品
            //是否已经被秒杀，如果插入失败说明 该商品已经杯盖用户秒杀过 不能重复秒杀 抛出异常
            int insertCount = successKilledDao.insertSuccessKilled(seckillId, userPhone);
            try {
                if (insertCount <= 0) {
                    throw new SeckillCloseException("seckill is closed!");
                } else {
                    //检查过没有重复秒杀，那么执行减库存操作
                    int updateCount = seckillGoodsDao.reduceNumber(seckillId, nowTime);
                    // 减库存,热点商品竞争
                    if (updateCount <= 0) {
                        // 没有更新库存记录，说明秒杀结束 rollback
                        throw new SeckillCloseException("seckill is closed");
                    } else {
                        // 秒杀成功,得到成功插入的明细记录,并返回成功秒杀的信息 commit
                        SuccessKilled successKilled = successKilledDao.queryByIdWithSeckillGoods(seckillId, userPhone);
                        return new SeckillExecution(seckillId, SeckillStatuEnums.SUCCESS, successKilled);
                    }
                }
            } catch (SeckillCloseException e1) {
                throw e1;
            } catch (RepeatKillException e2) {
                throw e2;
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                // 将编译期异常转化为运行期异常
                throw new SeckillException("seckill inner error :" + e.getMessage());
            }
        }
    }
}
