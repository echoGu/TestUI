package com.trane.display.cases;

import org.openqa.selenium.WebElement;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.trane.display.utils.BaseActions;
import com.trane.display.utils.TestNGListener;

@Listeners({TestNGListener.class })
public class UnitTest extends BaseActions {
  @Test
  public void f() throws Exception 
  {
	  initLocatorMap();
	  openHomePage();
	  
	  
	  clickVisibleDiv("Reports");
		clickVisibleDiv("Evaporator");
		clickVisibleDiv("Circuit 1");
		
		System.out.println("PageNum: " + getElement("PageNum").getText());
		System.out.println("TotalPageNum: " + getElement("TotalPageNum").getText());
		
		verifyAllData("BaseConfigEvap_Ckt1");

  }
}
