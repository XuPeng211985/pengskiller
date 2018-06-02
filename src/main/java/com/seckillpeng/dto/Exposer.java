package com.seckillpeng.dto;

/**
 * 暴露秒杀接口DTO
 */
public class Exposer {
    public boolean isExposed() {
        return exposed;
    }

    public void setExposed(boolean exposed) {
        this.exposed = exposed;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    @Override
    public String toString() {
        return "Exposer{" +
                "exposed=" + exposed +
                ", md5='" + md5 + '\'' +
                ", startTime=" + startTime +
                ", now=" + now +
                ", endTime=" + endTime +
                ", seckilId=" + seckilId +
                '}';
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getNow() {
        return now;
    }

    public void setNow(long now) {
        this.now = now;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public long getSeckilId() {
        return seckilId;
    }

    public void setSeckilId(long seckilId) {
        this.seckilId = seckilId;
    }

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

