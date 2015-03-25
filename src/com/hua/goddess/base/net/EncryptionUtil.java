package com.hua.goddess.base.net;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class EncryptionUtil {

	public static final byte[] compress(byte[] b) {
		if (b == null||b.length==0)
			return null;

		byte[] compressed;
		ByteArrayOutputStream out = null;
		ZipOutputStream zout = null;

		try {
			out = new ByteArrayOutputStream();
			zout = new ZipOutputStream(out);
			zout.putNextEntry(new ZipEntry("0"));
			zout.write(b);
			zout.closeEntry();
			compressed = out.toByteArray();
		} catch (IOException e) {
			compressed = null;
		} finally {
			if (zout != null) {
				try {
					zout.close();
				} catch (IOException e) {
				}
			}
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
				}
			}
		}
		return compressed;
	}

	/**
	 * 将压缩后的 byte[] 数据解压缩
	 * 
	 * @param compressed
	 *            压缩后的 byte[] 数据
	 * @return 解压后的字符串
	 */
	public static final String decompress(byte[] compressed) {
		if (compressed == null)
			return null;

		ByteArrayOutputStream out = null;
		ByteArrayInputStream in = null;
		ZipInputStream zin = null;
		String decompressed;
		try {
			out = new ByteArrayOutputStream();
			in = new ByteArrayInputStream(compressed,20,compressed.length);
			zin = new ZipInputStream(in);
//			ZipEntry entry = zin.getNextEntry();
			zin.getNextEntry();
			byte[] buffer = new byte[1024];
			int offset = -1;
			while ((offset = zin.read(buffer)) != -1) {
				out.write(buffer, 0, offset);
			}
			decompressed = out.toString();
		} catch (IOException e) {
			decompressed = null;
		} finally {
			if (zin != null) {
				try {
					zin.close();
				} catch (IOException e) {
				}
			}
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
				}
			}
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
				}
			}
		}

		return decompressed;
	}
	
//	public static void test(){
//		StringBuffer sb = new StringBuffer();
//		for(int i=0;i<1000;i++)
//			sb.append("第三方第三方第三方斯蒂芬斯蒂芬第三方斯蒂芬斯蒂芬斯蒂芬斯蒂芬斯蒂芬算法对所发生的地方324324324 飞萨芬第三方");
//		
//		sb.append(""+System.currentTimeMillis());
//		
////		byte[] before = sb.toString().getBytes();
//		
////		byte[] b = compress(before);
//		
//		
////		String endStr = decompress(b);
//		
////		byte[] end = endStr.getBytes();
//	}
}
