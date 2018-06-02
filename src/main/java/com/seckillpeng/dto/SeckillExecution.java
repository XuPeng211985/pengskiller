package com.seckillpeng.dto;
import com.seckillpeng.entity.SuccessKilled;
import com.seckillpeng.enums.SeckillStatuEnums;

/**
 * 封装执行秒杀后的结果
 */
public class SeckillExecution {
    /**
     * 执行秒杀的商品Id
     */
   private long seckillId;
   /**
    * 秒杀的状态
    */
   private int state;
    /**
     * 秒杀状态明文标识
     */
   private String stateInfo;

    @Override
    public String toString() {
        return "SeckillExecution{" +
                "seckillId=" + seckillId +
                ", state=" + state +
                ", stateInfo='" + stateInfo + '\'' +
                ", successKilled=" + successKilled +
                '}';
    }

    /**
     * 秒杀成功，返回秒杀明细记录
     */
   private SuccessKilled successKilled;

    /**
     * 秒杀成功 返回所有属性
     * @param seckillId
     * @param seckillStatuEnums
     * @param successKilled
     */
    public SeckillExecution(long seckillId,SeckillStatuEnums seckillStatuEnums, SuccessKilled successKilled) {
        this.seckillId = seckillId;
        this.state = seckillStatuEnums.getStatus();
        this.stateInfo = seckillStatuEnums.getStatuInfo();
        this.successKilled = successKilled;
    }

    /**
     * 秒杀失败返回 失败状态及明文标识
     * @param seckillId
     * @param seckillStatuEnums
     */
    public SeckillExecution(long seckillId, SeckillStatuEnums seckillStatuEnums) {
        this.seckillId = seckillId;
        this.state = seckillStatuEnums.getStatus();
        this.stateInfo = seckillStatuEnums.getStatuInfo();
    }

    public long getSeckillId() {
        return seckillId;
    }

    public void setSeckillId(long seckillId) {
        this.seckillId = seckillId;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public void setStateInfo(String stateInfo) {
        this.stateInfo = stateInfo;
    }

    public SuccessKilled getSuccessKilled() {
        return successKilled;
    }

    public void setSuccessKilled(SuccessKilled successKilled) {
        this.successKilled = successKilled;
    }
}
