package com.seckillpeng.dao;

import com.seckillpeng.entity.SeckillGoods;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

//在JUnit在启动时加载SpringIOC容器。调用的是整合包
@RunWith(SpringJUnit4ClassRunner.class)
//指明加载的Spring容器的具体路径
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SeckillGoodsDaoTest {
@Resource
private SeckillGoodsDao seckillGoodsDao;
    @Test
    public void reduceNumber() {
       long id = 1000L;
       Date data = new Date();
       int count = seckillGoodsDao.reduceNumber(id,data);
        System.out.println(count);
    }

    @Test
    public void queryById() {
        long id = 1001L;
       SeckillGoods sg =  seckillGoodsDao.queryById(id);
        System.out.println(sg);
    }

    @Test
    public void queryAll() {
        List<SeckillGoods> seckills = seckillGoodsDao.queryAll(0, 100);
        for (SeckillGoods seckill : seckills) {
            System.out.println(seckill);
        }
    }
}