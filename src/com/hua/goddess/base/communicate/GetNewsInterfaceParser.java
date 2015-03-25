package com.hua.goddess.base.communicate;

import com.google.gson.Gson;
import com.hua.goddess.vo.NewsListVo;


public class GetNewsInterfaceParser {
	public static NewsListVo parserData(String str){
		if(str!=null&&!"".equals(str)){
			Gson gson = new Gson();
			NewsListVo vo = gson.fromJson(str, NewsListVo.class);
			return vo;
		}
		return null;
	}
}
