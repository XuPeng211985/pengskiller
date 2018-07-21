package com.seckillpeng.dao.cache;

import com.seckillpeng.dao.SeckillGoodsDao;
import com.seckillpeng.entity.SeckillGoods;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
@RunWith(SpringJUnit4ClassRunner.class)
//指明加载的Spring容器的具体路径
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class RedisDaoTest {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private long id = 1002;
    @Autowired
    private SeckillGoodsDao seckillGoodsDao;
    @Autowired
    private RedisDao redisDao;
    @Test
    public void RedisTest(){
        SeckillGoods seckillGoods = redisDao.getSeckill(id);
        if(seckillGoods == null){
            seckillGoods = seckillGoodsDao.queryById(id);
            if(seckillGoods != null){
               String result = redisDao.putSeckill(seckillGoods);
               logger.info("result={}",result);
               seckillGoods = redisDao.getSeckill(id);
                System.out.println(seckillGoods);
            }
        }
    }
}