/**
 * 
 */
package com.bosh.exception;

/**
 * @author wangmt
 * @date 2018年9月20日
 */
public class ServiceProcessException extends RuntimeException {
	
	public ServiceProcessException(String message) {
        super(message);
    }

    public ServiceProcessException(String message, Throwable cause) {
        super(message, cause);
    }

}
