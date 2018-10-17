/**
 * 
 */
package com.bosh.utils;

/**
 * @author wangmt
 * @date 2018年9月19日
 */
public enum StateEnum {
	
	SUCCESS(200),WARN(400),ERROR(500);

    private int state;

    StateEnum(int state) {
        this.state = state;
    }

    public int getState() {
        return state;
    }

}
