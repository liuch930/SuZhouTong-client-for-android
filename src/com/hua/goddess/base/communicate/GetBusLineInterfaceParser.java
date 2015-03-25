package com.hua.goddess.base.communicate;

import com.google.gson.Gson;
import com.hua.goddess.vo.BusLineListVo;


public class GetBusLineInterfaceParser {
	public static BusLineListVo parserData(String str){
		if(str!=null&&!"".equals(str)){
			Gson gson = new Gson();
			BusLineListVo vo = gson.fromJson(str, BusLineListVo.class);
			return vo;
		}
		return null;
	}
}
