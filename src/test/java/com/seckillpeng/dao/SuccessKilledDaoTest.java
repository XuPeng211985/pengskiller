package com.seckillpeng.dao;
import com.seckillpeng.entity.SuccessKilled;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SuccessKilledDaoTest {
    @Resource
   private SuccessKilledDao successKilledDao;
    @Test
    public void insertSuccessKilled() {
        successKilledDao.insertSuccessKilled(1002L,18829490);
    }

    @Test
    public void queryByIdWithSeckillGoods() {
        SuccessKilled successKilled = successKilledDao.queryByIdWithSeckillGoods(1002,18829490);
        System.out.println(successKilled);
    }
}