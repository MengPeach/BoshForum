/**
 * 
 */
package com.bosh.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bosh.dto.QuarkResult;
import com.bosh.exception.ServiceProcessException;

/**
 * @author wangmt
 * @date 2018年9月20日
 */
public class BaseController {
	
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    protected QuarkResult restProcessor(ResultProcessor processor){
        QuarkResult result = null;
        try{
        	result = processor.process();
        }
        catch (ServiceProcessException e1){
            logger.error("ServiceProcess Error Log :"+e1.getLocalizedMessage(),e1);
            result = QuarkResult.error(e1.getMessage());
        }
        catch (Exception e){
            logger.error("Error Log :"+e.getLocalizedMessage(),e);
            result = QuarkResult.error("服务器出现异常");
        }

        return result;
    }

}
