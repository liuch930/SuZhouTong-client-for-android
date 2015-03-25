package com.hua.goddess.base.communicate;

import com.hua.goddess.base.exception.ErrorCodeException;
import com.hua.goddess.base.net.ParcelMap;
import com.hua.goddess.base.net.UnEncryptionHttpConnect;
import com.hua.goddess.base.net.UnEncryptionRequestParcelable;
import com.hua.goddess.global.Globe;
import com.hua.goddess.vo.BusLineDetailListVo;

public class GetBusLineDetailInterface {

	public static BusLineDetailListVo getNetData(String guid) throws Exception {

		UnEncryptionRequestParcelable requestParam = new UnEncryptionRequestParcelable(
				Globe.BUS_LINE_DETAIL + guid);
		System.out.println("Globe.BUS_LINE_DETAIL + guid = " + Globe.BUS_LINE_DETAIL + guid);
		UnEncryptionHttpConnect dhc = new UnEncryptionHttpConnect(requestParam);
		for (int i = 0; i < 3; i++) {
			try {
				dhc.connect();
				String responseBody = dhc.getResponseBody();

				ParcelMap respHeaders = dhc.getHeaders();
				if (respHeaders != null) {
					if (Globe.RESPONSE_HEADER_RESULT_ERROR.equals(respHeaders
							.get(Globe.RESPONSE_HEADER_RESULT))) {
						String errorCode = respHeaders
								.get(Globe.RESPONSE_HEADER_ERROR_CODE);
						if ("404-1".equals(errorCode)) {
							// 缺少必要参数
							continue;
						} else {
							// 无结果
						}
					} else {
						if (responseBody != null) {
							if(responseBody.contains("{\"@attributes\":{\"Type\":\"TEXT\"}}")){
								responseBody = responseBody.replace("{\"@attributes\":{\"Type\":\"TEXT\"}}","\"NODATA\"");
							}
							System.out.println(responseBody);
							BusLineDetailListVo vo = GetBusLineDetailInterfaceParser
									.parserData(responseBody);
							return vo;
						}
					}
				}
			} catch (ErrorCodeException e) {
				throw e;
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				dhc = null;
			}
		}
		return null;
	}

}
