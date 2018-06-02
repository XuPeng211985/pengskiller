package com.seckillpeng.server.impl;

import com.seckillpeng.dto.Exposer;
import com.seckillpeng.dto.SeckillExecution;
import com.seckillpeng.entity.SeckillGoods;
import com.seckillpeng.exception.RepeatKillException;
import com.seckillpeng.exception.SeckillCloseException;
import com.seckillpeng.server.SeckillGoodsServer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml","classpath:spring/spring-server.xml"})
public class SeckillGoodsServerImplTest {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
     private SeckillGoodsServer seckillGoodsServer;
    @Test
    public void findAllSeckillGoods() {
       List<SeckillGoods> list =  seckillGoodsServer.findAllSeckillGoods();
        logger.info("seckillList:{}" + list);
    }

    @Test
    public void findSeckillGoodsById() {
        long id = 1000L;
        SeckillGoods seckillGoods = seckillGoodsServer.findSeckillGoodsById(id);
        logger.info("seckillGoods:{}" + seckillGoods);
    }

    @Test
    public void exportSeckillUrl() {
        long id = 1001L;
        Exposer exposer = seckillGoodsServer.exportSeckillUrl(id);
        if(exposer.isExposed()){
            logger.info("exposer : " + exposer);
            String md5 = exposer.getMd5();
            long userPhone = 18828480333L;
            try {
                SeckillExecution seckillExecution = seckillGoodsServer.executeSeckillProcedure(id, userPhone, md5);
               logger.info("execution: " + seckillExecution);
            }catch(RepeatKillException e){
              logger.error(e.getMessage());
            }catch(SeckillCloseException e){
                logger.error(e.getMessage());
            }
        }else{
            logger.warn("exposer: " + exposer);
        }
    }

    @Test
    public void executeSeckillProcedure() {
    }
}