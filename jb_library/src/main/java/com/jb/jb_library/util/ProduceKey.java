/**
 * Copyright (c) 2005-2012 springside.org.cn
 */
package com.jb.jb_library.util;

import java.io.UnsupportedEncodingException;
import java.util.Random;


public class ProduceKey extends Cryptos{
	
	public static final String DEFAULT_IV = "5203608181111919";
	
	private static final String DEFAULT_URL_ENCODING = "UTF-8";
	/**
	 * 使用AES加密原始字符串.
	 * 
	 * @param input 原始输入字符数组
	 * @param key 符合AES要求的密钥
	 * @param iv 向量
	 */
	public static String aesEncrypt(String input, String key, String iv) {
		try {
			return Encodes.encodeHex(aesEncrypt(input.getBytes(DEFAULT_URL_ENCODING), Encodes.decodeHex(key), iv.getBytes()));
		} catch (UnsupportedEncodingException e) {
			return "";
		}
	}
	
	/**
	 * 使用AES解密字符串, 返回原始字符串.
	 * 
	 * @param input Hex编码的加密字符串
	 * @param key 符合AES要求的密钥
	 * @param iv 向量
	 */
	public static String aesDecrypt(String input, String key, String iv) {
		try {
			return new String(aesDecrypt(Encodes.decodeHex(input), Encodes.decodeHex(key), iv.getBytes()), DEFAULT_URL_ENCODING);
		} catch (UnsupportedEncodingException e) {
			return "";
		}
	}
	
	
	public static String getAESStringkey() throws Exception {
		String randomStr = getRandom16Str(); // 生成十六位字符串
//		randomStr = "5B88C7618F0984B9";
		byte[] aesByte = randomStr.getBytes() ;
		String key = "";
		if (aesByte != null) {
			key = Encodes.encodeHex(aesByte);
		}
		return key;
	}

	/**
	 * 得到16位由字母和数字组成的随机字符串
	 * 
	 * @return
	 */
	public static String getRandom16Str() {
		String source = "0123456789ABCDEF";
		return doProduce(16, source);
	}

	/**
	 * 生产结果
	 */
	private static String doProduce(int maxLength, String source) {
		StringBuffer sb = new StringBuffer(100);
		for (int i = 0; i < maxLength; i++) {
			final int number = produceNumber(source.length());
			sb.append(source.charAt(number));
		}
		return sb.toString();
	}

	/**
	 * 随机产生几位字符串
	 */
	public static final int produceNumber(int maxLength) {
		Random random = new Random();
		return random.nextInt(maxLength);
	}
	
	public static void main(String[] args) throws Exception {
		String asekey = getAESStringkey();
		String encrypted = aesEncrypt("{\"test\":\"test001\"}", asekey, DEFAULT_IV);
		
		String decrypted = aesDecrypt(encrypted, asekey, DEFAULT_IV);
		System.out.println("解密后："+ decrypted);
	}
}