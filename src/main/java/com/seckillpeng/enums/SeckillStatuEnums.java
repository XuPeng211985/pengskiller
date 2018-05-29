package com.seckillpeng.enums;

public enum SeckillStatuEnums {
    /**
     * 注意构造多个枚举对象时用逗号隔开，并且放在属性定义的前面
     */
    SUCCESS(1,"秒杀成功"),
    END(0,"秒杀结束"),
    REPEAT_KILL(-1,"重复秒杀"),
    INNER_ERROR(-2,"系统异常"),
    DATE_REWRITE(-3,"数据篡改");
    private int status;
    private String statuInfo;
    SeckillStatuEnums(int status, String statuInfo) {
        this.status = status;
        this.statuInfo = statuInfo;
    }
    public int getStatus() {
        return status;
    }

    public String getStatuInfo() {
        return statuInfo;
    }
    public static SeckillStatuEnums stateOf(int index) {
        for (SeckillStatuEnums state : values()) {
            if (state.getStatus()==index) {
                return state;
            }
        }
        return null;
    }
}
