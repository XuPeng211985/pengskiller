package com.seckillpeng.dto;
import com.seckillpeng.entity.SuccessKilled;
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
    /**
     * 秒杀成功，返回秒杀明细记录
     */
   private SuccessKilled successKilled;

    /**
     * 秒杀成功 返回所有属性
     * @param seckillId
     * @param state
     * @param stateInfo
     * @param successKilled
     */
    public SeckillExecution(long seckillId, int state, String stateInfo, SuccessKilled successKilled) {
        this.seckillId = seckillId;
        this.state = state;
        this.stateInfo = stateInfo;
        this.successKilled = successKilled;
    }

    /**
     * 秒杀失败返回 失败状态及明文标识
     * @param seckillId
     * @param state
     * @param stateInfo
     */
    public SeckillExecution(long seckillId, int state, String stateInfo) {
        this.seckillId = seckillId;
        this.state = state;
        this.stateInfo = stateInfo;
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
