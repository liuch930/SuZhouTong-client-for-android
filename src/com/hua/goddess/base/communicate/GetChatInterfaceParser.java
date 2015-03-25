package com.hua.goddess.base.communicate;

import com.google.gson.Gson;
import com.hua.goddess.vo.ChatMessageVO;


public class GetChatInterfaceParser {
	public static ChatMessageVO parserData(String str){
		if(str!=null&&!"".equals(str)){
			Gson gson = new Gson();
			ChatMessageVO vo = gson.fromJson(str, ChatMessageVO.class);
			return vo;
		}
		return null;
	}
}
