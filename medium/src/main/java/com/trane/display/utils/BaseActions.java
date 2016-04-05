package com.trane.display.utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class BaseActions {
	
	protected WebDriver driver;
	protected HashMap<String, Locator> locatorMap;
	protected Log log = new Log(this.getClass());
	protected String path;
	private HashMap<String, String> dataMap;
	
	public BaseActions(WebDriver driver) {
        this.driver = driver;
        try {
			this.InitLocatorMap();
		} catch (Exception e) {
			e.printStackTrace();
		}
        this.openHomePage();
    
    }
	/**
	 * read xml to inital UI locator map
	 * @throws Exception
	 */
	public void InitLocatorMap() throws Exception {
		path = System.getProperty("user.dir") 
				+ "\\src\\main\\java\\com\\trane\\display\\UIMaps\\" 
				+ "UIMap.xml";
		log.info(path);
		locatorMap = XMLutils.readXMLDocument(path);
	}
	
	public void VerifyData(String filename, Integer pageIndex) throws Exception {
		path = System.getProperty("user.dir") 
				+ "\\src\\main\\java\\com\\trane\\display\\cases\\data\\" 
				+ filename
				+ ".csv";
		dataMap = CSVutils.readCSV(path, pageIndex);
		log.info(path);
		  
		  if(!dataMap.isEmpty()) {
				Set<String> keys = dataMap.keySet();
				
				Iterator<String> iter = keys.iterator();
				  
				  while(iter.hasNext()) {
				      String key = (String)iter.next();
				      String value = (String)dataMap.get(key);
				      
				      WebElement e = driver.findElement(By.id(key));
				      String actualValue = e.getText();
				      
				      Assert.assertEquals(actualValue, value);
				      
				      if(actualValue.equalsIgnoreCase(value)) {
				    	  log.info("Pass!");
				      } else {
				    	  log.error("Fail!");
				    	  log.info("Actual value: " + actualValue + " Expected value: " + value);
				      }
				  }
		  } else {
			  log.error("dataMap is empty!!!");
		  }
	}
	
	public WebDriver getDriver() {
		return driver;
	}
    /**
     * load home page
     */
    public void openHomePage() {
    	driver.get("http://192.168.1.3/FS/root/UI_Medium/index.html");  
    	WebDriverWait wait = new WebDriverWait(driver,20); 
    	wait.until(ExpectedConditions.textToBePresentInElementLocated(By.id("idLbl_pgHomePage_Title"), "Home"));
        log.info("Home Page is loaded successfully");
    }
    /**
     * 
     * @param locatorName
     * @return
     * @throws Exception
     */
    public boolean isElementPresent(String locatorName) throws Exception {
    	Locator locator = getLocator(locatorName);
    	boolean isPresent = false;
    	WebDriverWait wait = new WebDriverWait(driver, 10);
    	isPresent = wait.until(ExpectedConditions.presenceOfElementLocated(getByLocator(locator))).isDisplayed();
    	return isPresent;
    	
    }
    
	/**
	 * 
	 * @param locatorName
	 * @return
	 * @throws IOException
	 */
	public Locator getLocator(String locatorName) throws IOException {
		Locator locator = locatorMap.get(locatorName);
		return locator;

	}
	/**
	 * 
	 * @param locatorName
	 * @return
	 * @throws Exception
	 */
	public WebElement getElement(String locatorName) throws Exception {
		Locator locator = getLocator(locatorName);
		By by = getByLocator(locator);
		WebElement element = driver.findElement(by);
		return element;
	}
	/**
	 * click and find a WebElement by locatorName
	 * @param locatorName
	 * @throws Exception
	 */
	public void click(String locatorName) throws Exception {
		WebElement e = getElement(locatorName);
		log.info("click "+locatorName);
		e.click();
		Thread.sleep(3000);
	}
	/**
	 * 
	 * @param locator
	 * @return By
	 * @throws IOException
	 */
	public By getByLocator(Locator locator) throws IOException {
		By by = null;
		String selector = locator.getSelector();
		switch (locator.getBy()) {
		case xpath:
			log.debug("find element By xpath:"+selector);
			by = By.xpath(selector);
			break;
		case id:
			log.debug("find element By id:"+selector);
			by = By.id(selector);
			break;
		case name:
			log.debug("find element By name:"+selector);
			by = By.name(selector);
			break;
		case cssSelector:
			log.debug("find element By cssSelector:"+selector);
			by = By.cssSelector(selector);
			break;
		case className:
			log.debug("find element By className:"+selector);
			by = By.className(selector);
			break;
		case tagName:
			log.debug("find element By tagName:"+selector);
			by = By.tagName(selector);
			break;
		case linkText:
			log.debug("find element By linkText:"+selector);
			by = By.linkText(selector);
			break;
		case partialLinkText:
			log.debug("find element By partialLinkText:"+selector);
			by = By.partialLinkText(selector);
			break;
		}
		return by;
	}
	/**
	 * 
	 * @param locatorName
	 * @param num
	 * @throws Exception
	 */
	public void verifyText(String locatorName, Integer num) throws Exception {
		Thread.sleep(3000);
		Assert.assertEquals(getElement(locatorName).getText(), num+"");
		
	}
	/**
	 * 
	 * @param locatorName
	 * @param text
	 * @throws Exception
	 */
	public void verifyText(String locatorName, String text) throws Exception {
		Thread.sleep(3000);
		Assert.assertEquals(getElement(locatorName).getText(), text);
		
	}
	/**
	 * 
	 * @param locatorName
	 * @param attribute
	 * @param content
	 * @throws Exception
	 */
	public void verifyAttribute(String locatorName, String attribute, String content) throws Exception {
		Thread.sleep(3000);
		Assert.assertEquals(getElement(locatorName).getAttribute(attribute), content);
	}
	
	/**
	 * find and click a WebElemnt by Text 
	 * @param WebElemnt's Text
	 */
	public void clickVisibleDiv(String text) {
		driver.findElement(By.xpath("//*[text()='"+text+"']")).click();
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	


}
