package com.seckillpeng.dao;
import com.seckillpeng.entity.SeckillGoods;
import java.util.Date;
import java.util.List;
public interface SeckillGoodsDao {
    /**
     * 减库存
     * @param seckillId
     * @param killTime
     * @return 返回执行减库存操作的商品所在的行数
     */
     int reduceNumber(long seckillId,Date killTime);

    /**
     * 根据Id查询某商品的库存情况
     * @param seckillId
     * @return 返回查询结果
     */
     SeckillGoods queryById(long seckillId);

    /**
     * 在某一范围内查询商品列表
     * @param offset
     * @param limit
     * @return 返回查询结果
     */
     List<SeckillGoods> queryAll(int offset,int limit);
}
