package com.dogdailylog.config;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Encrypt{
	private static final Logger logger = LoggerFactory.getLogger(Encrypt.class);

	public static String md5(String password, String salt) {
		String hashingPassword = getEncrypt(password, salt);
		return hashingPassword;
	}
	
	
	/**
	 * salt값 얻기
	 * @return String salt
	 */
	public static String getSalt() {
		// random , byte 객체 생성
		SecureRandom secureRandom = new SecureRandom();
		byte[] salt = new byte[20];
		
		// 난수 생성
		secureRandom.nextBytes(salt);
		
		//byte to String (10진수의 문자열로 변경)
		StringBuffer sb = new StringBuffer();
		for (byte b : salt) {
			// %02x : 2자리 16진수(헥사)를 대문자로, 그리고 1자리 헥사는 앞에 0을 붙임
			sb.append(String.format("%02x", b));
			logger.debug("### before salt ### : {}",b);
			logger.debug("### after salt ### : {}",String.format("%02x", b));
		}
		
		return sb.toString();
		
	}
	
	/**
	 * 암호화된 비밀번호 얻기
	 * @param pwd
	 * @param salt
	 * @return String
	 */
	public static String getEncrypt(String pwd, String salt) {
		String encData = "";
		
		try {
			// SHA-256 알고리즘 객체 생성 후 할당
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			
//			logger.debug("[before hashing] pwd : {}, salt : {}", pwd, salt);
			
			// pwd와 salt 합친 문자열에 SHA-256 알고리즘 적용
			md.update((pwd+salt).getBytes());
			byte[] pwdSalt = md.digest(); // hash값 byte array로 리턴해줌
			
			// byte to string 256비트로 생성 => 32Byte => 1Byte(8bit) =>  16진수 2자리로 변환 => 16진수 한 자리는 4bit
			StringBuffer sb = new StringBuffer();
			for (byte b : pwdSalt) {
				sb.append(String.format("%02x", b));
			}
			
			encData = sb.toString();
			logger.debug("[after hashing] encData : {}", encData);
			
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		return encData;
	}
	
}
