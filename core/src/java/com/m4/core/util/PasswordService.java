package com.m4.core.util;

import java.security.MessageDigest;
import sun.misc.BASE64Encoder;

public final class PasswordService {
	private static PasswordService instance;

	private PasswordService() {}

	public synchronized String encrypt(String plaintext)throws Exception {
		MessageDigest md = null;
		md = MessageDigest.getInstance("SHA1");
		md.update(plaintext.getBytes("UTF-8")); 
		byte raw[] = md.digest(); 
		String hash = (new BASE64Encoder()).encode(raw); 
		return hash;
	}

	//Use singleton pattern
	public static synchronized PasswordService getInstance(){
		if (instance == null) {
			return new PasswordService();
		} else {
			return instance;
		}
	}
}
