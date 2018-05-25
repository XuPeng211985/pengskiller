-- 数据库初始化脚本
CREATE DATABASE seckill;
use seckill;
CREATE TABLE seckill(
    `seckill_id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '商品id',
    `name` VARCHAR(120) NOT NULL COMMENT '秒杀商品名称',
    `number` int NOT NULL COMMENT '库存数量',
  `create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `start_time` TIMESTAMP NOT NULL COMMENT '秒杀开始时间',
  `end_time`  TIMESTAMP NOT NULL  COMMENT '秒杀结束时间',
   PRIMARY KEY (seckill_id),
   key ide_start_time(start_time),
   key ide_end_time(end_time),
   key idx_create_time(create_time)
)ENGINE=INNODB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8 COMMENT='商品库存表';
-- 初始化数据
 INSERT into seckill(name,number,start_time,end_time)
     VALUES
       ('1000元秒杀iphone6',100,'2016-01-01 00:00:00','2016-01-02 00:00:00'),
       ('10000元秒杀宝马',100,'2016-01-01 00:00:00','2016-01-02 00:00:00'),
       ('2000元秒杀iphone2',100,'2016-01-01 00:00:00','2016-01-02 00:00:00'),
       ('100元秒杀小手套',100,'2016-01-01 00:00:00','2016-01-02 00:00:00'),
       ('1元秒杀水果糖',100,'2016-01-01 00:00:00','2016-01-02 00:00:00');
-- 秒杀记录表
CREATE TABLE success_killed(
  `seckill_id` BIGINT NOT NULL COMMENT '秒杀的商品ID',
  `user_phone` BIGINT NOT NULL COMMENT '用户手机号',
  `state` TINYINT NOT NULL  DEFAULT -1 COMMENT '状态标识 -1 表示失败 0 表示成功 1 表示已付款 2 表示已发货',
  `create_time` TIMESTAMP NOT NULL  COMMENT '创建时间',
  PRIMARY KEY (seckill_id,user_phone),/*联合主键*/
  key idex_create_time(create_time)
)ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='秒杀成功明细表';

