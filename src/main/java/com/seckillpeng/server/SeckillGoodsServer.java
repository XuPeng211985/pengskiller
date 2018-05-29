package com.seckillpeng.server;

import com.seckillpeng.dto.Exposer;
import com.seckillpeng.dto.SeckillExecution;
import com.seckillpeng.entity.SeckillGoods;
import com.seckillpeng.exception.RepeatKillException;
import com.seckillpeng.exception.SeckillCloseException;
import com.seckillpeng.exception.SeckillException;

import java.util.List;

/**
 * 应该站在用户的角度去设计接口，包括简介易于理解的接口名称
 *            简练的参数结构，用最少的参数组成完成业务需求
 *    返回类型通常写成包装类，该类包含前端和后台的综合属性
 */
public interface SeckillGoodsServer {
    /**
     * 查询当前商品库中的所有商品清单
     * @return
     */
    List<SeckillGoods> findAllSeckillGoods();

    /**
     * 查询指定id号的商品库存情况
     * @param seckillId
     * @return
     */
    SeckillGoods findSeckillGoodsById(long seckillId);

    /**
     * 请求执行秒杀，如果当前商品处于可以秒杀的状态 那么返回秒杀接口的地址
     * 否则输出系统时间和当前商品执行秒杀的时间范围
     * @param seckillId
     */
    Exposer exportSeckillUrl(long seckillId) throws SeckillException,SeckillCloseException,RepeatKillException;
    /**
     * 启动秒杀，并返回秒杀结果
     * @param seckillId 秒杀商品的id
     * @param userPhone 执行秒杀操作的手机号
     * @param md5 加密后的秒杀入口
     * @return 秒杀结果，若是成功返回秒杀明细 若是失败返回失败状态的明文标识给用户
     */
    SeckillExecution executeSeckillProcedure(long seckillId, long userPhone, String md5);
 }
