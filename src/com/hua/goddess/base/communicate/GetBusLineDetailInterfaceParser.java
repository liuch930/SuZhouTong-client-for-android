package com.hua.goddess.base.communicate;

import com.google.gson.Gson;
import com.hua.goddess.vo.BusLineDetailListVo;

public class GetBusLineDetailInterfaceParser {
	public static BusLineDetailListVo parserData(String str) {
		if (str != null && !"".equals(str)) {
			Gson gson = new Gson();
			BusLineDetailListVo vo = gson.fromJson(str,
					BusLineDetailListVo.class);
			return vo;
		}
		return null;
	}
}
