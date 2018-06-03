package com.seckillpeng.dto;

/**
 * 处理json数据
 * @param <T>
 */
public class SeckillResult<T>{
    /**
     * 秒杀接口获取成功与否
     */
    private boolean success;
    /**
     * 返回接口获取失败错误信息
     */
    private String error;
    /**
     * 秒杀接口数据
     */
    private T data;

    public SeckillResult(boolean success,String error) {
        this.error = error;
        this.success = success;
    }

    public SeckillResult(boolean success, T data) {
        this.success = success;
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
