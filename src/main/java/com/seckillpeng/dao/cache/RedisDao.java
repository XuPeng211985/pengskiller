package com.seckillpeng.dao.cache;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtobufIOUtil;
import com.dyuproject.protostuff.runtime.RuntimeSchema;
import com.seckillpeng.entity.SeckillGoods;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
public class RedisDao {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final JedisPool pool;
    public RedisDao(String ip, int port){
        pool = new JedisPool(ip,port);
    }
    private RuntimeSchema<SeckillGoods> seckillGoodsSchema = RuntimeSchema.createFrom(SeckillGoods.class);
    public SeckillGoods getSeckill(long seckillId){
        try {
            Jedis jedis = pool.getResource();
          try{
              String key = "seckillGood:" + seckillId;
              //必须手动实现对象序列化 自定义开源序列化protostuff
              //当向Redis中存入POJO对象时，必须将它序列化为字节码对象
              //然后在用对象时，查出对象字节码在进行反序列化
              byte[] bytes = jedis.get(key.getBytes());
              //redis中主键的字节码
              if(bytes != null){
                  //如果当前库中存在 所要查询的主键所对应的商品
                  //那么通过schema获取到的POJO class反射后 可以new一个空的商品对象
                  SeckillGoods seckill = seckillGoodsSchema.newMessage();
                  //在根据protostuff定义的反序列化工具类，将schema查询到的
                  //的商品对象反序列化后传递给空对象  最后返回查询结果
                  ProtobufIOUtil.mergeFrom(bytes,seckill,seckillGoodsSchema);
                  return seckill;
              }
          }finally{
              jedis.close();
          }
        }catch (Exception e){
          logger.error(e.getMessage(),e);
        }
        return null;
    }

    public String putSeckill(SeckillGoods seckillGoods){
        try{
            Jedis jedis = pool.getResource();
            try{
               String key = "seckillGood:" + seckillGoods.getSeckillId();
               byte[] bytes = ProtobufIOUtil.toByteArray(seckillGoods,seckillGoodsSchema,LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));
               //设置缓存超时时间
                int timeout = 60 * 60;//one hours
                String result = jedis.setex(key.getBytes(),timeout,bytes);
                return result;
            }finally {
                jedis.close();
            }
        }catch (Exception e){
          logger.error(e.getMessage(),e);
        }
        return null;
    }
}
