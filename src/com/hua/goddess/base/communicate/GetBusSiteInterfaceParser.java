package com.hua.goddess.base.communicate;

import com.google.gson.Gson;
import com.hua.goddess.vo.BusSiteListVo;


public class GetBusSiteInterfaceParser {
	public static BusSiteListVo parserData(String str){
		if(str!=null&&!"".equals(str)){
			Gson gson = new Gson();
			BusSiteListVo vo = gson.fromJson(str, BusSiteListVo.class);
			return vo;
		}
		return null;
	}
}
