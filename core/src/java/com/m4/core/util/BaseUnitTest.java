package com.m4.core.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import junit.framework.TestCase;

public class BaseUnitTest extends TestCase {
	private ApplicationContext context = null;
	@Override
	protected void setUp() throws Exception {	
		super.setUp();
		String paths[] = {"E:/Applications/JAVA/pawn/resources/spring-bean-config/applicationContext.xml"};				
		context = new FileSystemXmlApplicationContext(paths);		
	}
	public ApplicationContext getContext() {
		return context;
	}
	public void setContext(ApplicationContext context) {
		this.context = context;
	}
}
