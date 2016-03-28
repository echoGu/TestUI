package com.trane.display.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Log {
	private final Class<?> clazz;
	private static Logger logger;

    public Log(Class<?> clazz) {
		this.clazz = clazz;
		logger = LogManager.getLogger(this.clazz);
	}
	
//	trace<debug<info<warn<error<fatal

	public void info(String message) {
		logger.info(clazz.getCanonicalName() + ": " + message);
	}

	public void debug(String message) {
		logger.debug(clazz.getCanonicalName() + ": " + message);
	}


	public void error(String message) {
		logger.error(clazz.getCanonicalName() + ": " + message);
	}


	public void trace(String message) {
		logger.trace(clazz.getCanonicalName() + ": " + message);
	}


	public void warn(String message) {
		logger.warn(clazz.getCanonicalName() + ": " + message);
	}


	public void fatal(String message) {
		logger.fatal(clazz.getCanonicalName() + ": " + message);
	}
}
