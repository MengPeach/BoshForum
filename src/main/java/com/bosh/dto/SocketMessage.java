/**
 * 
 */
package com.bosh.dto;

import java.io.Serializable;

/**
 * WebSocket通知消息类
 * @author wangmt
 * @date 2018年9月21日
 */
public class SocketMessage implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6150251020727429210L;

	private Integer notice;

    private String message;

    public Integer getNotice() {
        return notice;
    }

    public void setNotice(Integer notice) {
        this.notice = notice;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public SocketMessage(Integer notice, String message) {
        this.notice = notice;
        this.message = message;
    }

    public SocketMessage(Integer notice) {
        this.notice = notice;
    }

    public static SocketMessage build(Integer notice){
        return new SocketMessage(notice);
    }

    public static SocketMessage build(Integer notice,String message){
        return new SocketMessage(notice,message);
    }

}
