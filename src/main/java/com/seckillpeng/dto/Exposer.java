package com.seckillpeng.dto;

/**
 * 暴露秒杀接口DTO
 */
public class Exposer {
    /**
     * 是否满足秒杀的条件
     */
  private boolean exposed;
    /**
     * 商品秒杀接口加密
     */
  private String md5;
    /**
     * 秒杀开启时间
     */
  private long startTime;
    /**
     * 系统当前时间
     */
  private long now;
    /**
     * 秒杀结束时间
     */
  private long endTime;
    /**
     * 秒杀商品的主键id 作为该商品的秒杀接口
     * 起到一个验证的作用，判断当前请求秒杀商品是否可以被秒杀
     */
  private long seckilId;

    public Exposer(boolean exposed, long seckilId) {
        this.exposed = exposed;
        this.seckilId = seckilId;
    }

    public Exposer(boolean exposed, String md5, long seckilId) {
        this.exposed = exposed;
        this.md5 = md5;
        this.seckilId = seckilId;
    }

    public Exposer(boolean exposed, long startTime, long now, long endTime, long seckilId) {
        this.exposed = exposed;
        this.startTime = startTime;
        this.now = now;
        this.endTime = endTime;
        this.seckilId = seckilId;
    }
}

