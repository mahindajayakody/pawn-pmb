package com.m4.core.bean;

public final class UserSession {
	public static ThreadLocal<String> transactionId = new ThreadLocal<String>();
}
