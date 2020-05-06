package com.hs.Device.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;

@Controller
@RequestMapping("/hsbs")
public class TestController {

	@RequestMapping("/t")
	@ResponseBody
	public JSONObject aa(String aa) {
		JSONObject jst = new JSONObject();
		jst.put("aa", aa);
		return jst;
		
	}
	
}
