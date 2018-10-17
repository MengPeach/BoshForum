/**
 * 
 */
package com.bosh.base;

import com.bosh.dto.QuarkResult;

/**
 * @author wangmt
 * @date 2018年9月20日
 */
@FunctionalInterface
public interface ResultProcessor {
	 QuarkResult process();

}
