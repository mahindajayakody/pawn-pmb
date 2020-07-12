package com.m4.core.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import junit.framework.TestCase;

public class LoadApplication extends TestCase {
	public ApplicationContext ac = null;
	
	protected void setUp() throws Exception {				
		String paths[] = {"L:/eBag/repository/spring-bean-config/applicationContext.xml"};				
		ac = new FileSystemXmlApplicationContext(paths);
	}
	
	
	protected void tearDown() throws Exception { 
		System.out.println("In tear down");
	}
}

