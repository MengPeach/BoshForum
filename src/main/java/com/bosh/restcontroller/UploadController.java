/**
 * 
 */
package com.bosh.restcontroller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bosh.dto.UploadResult;
import com.bosh.exception.ServiceProcessException;
import com.bosh.service.UserService;
import com.bosh.utils.FileUtils;

/**
 *  文件上传接口,图片上传
 * @author wangmt
 * @date 2018年9月25日
 */
@RestController
@RequestMapping("/upload")
public class UploadController {
	
	@Autowired
    private UserService userService;

    @PostMapping("/image")
    public UploadResult upload(@RequestParam("file") MultipartFile file) {
        UploadResult result = null;
        if (!file.isEmpty()) {
            try {
                String s = FileUtils.uploadFile(file);
              result = new UploadResult(0, new UploadResult.Data(s));
                return result;
            } catch (IOException e) {
                e.printStackTrace();
                result = new UploadResult(1,"上传图片失败");
            }
            return result;
        }
        result = new UploadResult(1,"文件不存在");
        return result;
    }

    @PostMapping("/usericon/{token}")
    public UploadResult iconUpload(@PathVariable("token") String token,@RequestParam("file") MultipartFile file){
        if (!file.isEmpty()) {
            try {
                String icon = FileUtils.uploadFile(file);
                userService.updataUserIcon(token,icon);
                return new UploadResult(0, new UploadResult.Data(icon));

            } catch (IOException e) {
                e.printStackTrace();
                return new UploadResult(1,"上传图片失败");
            }catch (ServiceProcessException e1){
                e1.printStackTrace();
                return new UploadResult(1,e1.getMessage());
            }
        }
        return new UploadResult(1,"文件不存在");
    }


}
