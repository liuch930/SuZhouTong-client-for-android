package com.hua.goddess.global;


public class Globe {
	
	// 网络连接超时时长R
	public static final int TIMEOUT_CONNECTION = 30 * 1000;
	public static final int TIMEOUT_SOCKET = 30 * 1000;
	/*****************************************************
	 * 服务器通用响应头键名称 *
	 *****************************************************/
	public static final String RESPONSE_HEADER_RESULT = "result";
	public static final String RESPONSE_HEADER_CONTENT_LENGTH = "Content-Length";
	public static final String RESPONSE_HEADER_ERROR_CODE = "code";

	public static final String HEADER_ACCEPT_ENCODING = "Accept-Encoding";
	public static final String ENCODING_GZIP = "gzip";
	public static String session_id = "";
	public static String SharedPreferencesName = "userId";
	
	public static int DEFAULT_MEM_CACHE_SIZE = 1024 * 1024 * 1;
	public static String IMAGE_TEMP_DIR;
	// 响应结果
	public static final String RESPONSE_HEADER_RESULT_SUCCESS = "success";
	public static final String RESPONSE_HEADER_RESULT_ERROR = "error";
	/******************************************************************
	 * 软件升级发送的消息*
	 *****************************************************************/
	public static final int ZCOM_READERHD_UPDATEING = 123;
	public static final int ZCOM_READERHD_UPDATE_FINISH = 124;
	public static final String DOWNLOAD_CACHE = "/download_cache";
	//本地数据库
	// ------------------------数据库信息--------------------------------------//
	public static final String DATABASE_NAME = "suzhoutong.db";
	public static final int DATABASE_VERSION = 1;
	
	// --------------------字体大小，阅读模式----------------------------//
	public static final String FONTSIZE = "fontsize"; // 字体大小
	public static final String READERMODE = "readermode"; // 阅读模式（夜间，白天）
	
	/**网络接口**/
	
	//
	public static final String SUZHOU = "http://content.2500city.com";
	//获取网络新闻列表
	public static final String NEWS = "http://content.2500city.com/Json?method=GetNewsListByChannelId&appVersion=3.4&numPerPage=30&adNum=50&orderType=3";
	public static final String NEWS_DETAIL = "http://content.2500city.com/news/Clientview/";
	//新闻评论接口
	public static final String NEWS_COMMENT = "http://content.2500city.com/commentNews/UserView/1-";
	
	/*
	 * 公交接口
	 */
	public static final String BUS_SITE = "http://content.2500city.com/Json?method=SearchBusStation&standName=";
	public static final String BUS_LINE = "http://content.2500city.com/Json?method=SearchBusLine&lineName=";
	public static final String BUS_SITE_DETAIL = "http://content.2500city.com/Json?method=GetBusStationDetail&NoteGuid=";
	
	//线路查询后点击跳转，根据线路Guid查询
	public static final String BUS_LINE_DETAIL = "http://content.2500city.com/Json?method=GetBusLineDetail&Guid=";
	
	
	//天气
	public static final int GET_WEATHER_SCUESS = 1;
	public static final int GET_WEATHER_FAIL = 2;
	//百度美女图片
	public static final String BEAUTY_BASE_URL = "http://image.baidu.com/data/imgs?";
	
}
