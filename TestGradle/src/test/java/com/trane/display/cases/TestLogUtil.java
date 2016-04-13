package com.trane.display.cases;

import org.testng.annotations.Test;

import com.trane.display.utils.Log;

public class TestLogUtil {
	
	Log logger = new Log(this.getClass());
	
  @Test
  public void testLog() 
  {
		logger.trace("I am trace...");
		logger.debug("I am debug...");
		logger.info("I am info...");
		logger.warn("I am warn...");
		logger.error("I am error...");
		logger.fatal("I am fatal...");
  }
}
