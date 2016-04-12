package com.trane.display.cases;

import com.trane.display.utils.FTPutils;

public class Main {

	public static void main(String[] args) throws Exception {
		String configPath = "file:"
				+ System.getProperty("user.dir")
				+ "\\configs"
				+"\\log4j2.xml";
		System.out.println(configPath);
	}


}
