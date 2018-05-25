package com.seckillpeng.dao;

import com.seckillpeng.entity.SuccessKilled;

public interface SuccessKilledDao {
    /**
     * 插入购买明细，这里采用联合主键 seckillId 和 userPhone
     * @param seckillId
     * @param userPhone
     * @return
     */
   int insertSuccessKilled(long seckillId,long userPhone);
    /**
     * 查询一条购买明细记录，包含秒杀商品实体
     * @param seckillId
     * @param userPhone
     * @return
     */
    SuccessKilled  queryByIdWithSeckillGoods(long seckillId,long userPhone);
}
