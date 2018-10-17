/**
 * 
 */
package com.bosh.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bosh.base.BaseController;
import com.bosh.dto.QuarkResult;
import com.bosh.entity.Label;
import com.bosh.service.LabelService;

/**
 * 
 * @author wangmt
 * @date 2018年9月27日
 */
@RestController
@RequestMapping("/label")
public class LabelController extends BaseController {

	@Autowired
	private LabelService labelService;

	@GetMapping
	public QuarkResult getAllLabel() {

		QuarkResult result = restProcessor(() -> {
			List<Label> labels = labelService.findAll();
			return QuarkResult.ok(labels);
		});

		return result;
	}

}
