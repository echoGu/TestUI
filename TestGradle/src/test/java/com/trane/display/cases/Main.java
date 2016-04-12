package com.trane.display.cases;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.ConfigurationSource;
import org.apache.logging.log4j.core.config.Configurator;

public class Main {
	static String configfile = System.getProperty("user.dir")
			+ "\\config"
			+"\\log4j2.xml";
	
	public static void config()
	{
        ConfigurationSource source = null;
		try {
			source = new ConfigurationSource(new FileInputStream(configfile));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
        Configurator.initialize(null, source);
        
        Logger logger = LogManager.getLogger(Main.class.getName());
        logger.trace("trace...");
        logger.debug("debug...");
        logger.info("info...");
        logger.warn("warn...");
        logger.error("error...");
        logger.fatal("fatal...");
	}

	public static void main(String[] args) throws Exception {
		config();
	}


}
