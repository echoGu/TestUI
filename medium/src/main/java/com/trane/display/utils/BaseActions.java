package com.trane.display.utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

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
	
	protected BaseActions(WebDriver driver) {
        this.driver = driver;
        try {
			this.InitLocatorMap();
		} catch (Exception e) {
			e.printStackTrace();
		}
        this.openHomePage();
    
    }
	
	public void InitLocatorMap() throws Exception {
		log.debug(this.getClass().getCanonicalName());
		log.info(System.getProperty("user.dir"));
		path = System.getProperty("user.dir")
				+ "\\src\\main\\java\\com\\trane\\display\\pageActions\\"
				+ this.getClass().getSimpleName() + ".xml";
		log.info(path);
		locatorMap = xmlUtils.readXMLDocument(path, this.getClass().getCanonicalName());    
	}
	
	public WebDriver getDriver() {
		return driver;
	}
    
    public void openHomePage() {
    	driver.get("http://192.168.1.3/FS/root/UI_Medium/index.html");  
    	WebDriverWait wait = new WebDriverWait(driver,20); 
    	wait.until(ExpectedConditions.textToBePresentInElementLocated(By.id("idLbl_pgHomePage_Title"), "Home"));
        log.info("Home Page is loaded successfully");
    }
    
    public boolean isElementPresent(String locatorName) throws Exception {
    	Locator locator = getLocator(locatorName);
    	boolean isPresent = false;
    	WebDriverWait wait = new WebDriverWait(driver, 10);
    	isPresent = wait.until(ExpectedConditions.presenceOfElementLocated(getByLocator(locator))).isDisplayed();
    	return isPresent;
    	
    }
    
	
	public Locator getLocator(String locatorName) throws IOException {
		Locator locator = locatorMap.get(locatorName);
		return locator;

	}
	
	public WebElement getElement(String locatorName) throws Exception {
		Locator locator = getLocator(locatorName);
		By by = getByLocator(locator);
//		WebDriverWait wait = new WebDriverWait(driver, 10);
//    	wait.until(ExpectedConditions.presenceOfElementLocated(getByLocator(locator))).isDisplayed();
		WebElement element = driver.findElement(by);
		return element;
	}
	
	public void click(String locatorName) throws Exception {
		WebElement e = getElement(locatorName);
		log.info("click "+locatorName);
		e.click();
//		driver.manage().timeouts().setScriptTimeout(5, TimeUnit.SECONDS);
		Thread.sleep(3000);
	}
	
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
	
	public void verifyText(String locatorName, Integer num) throws Exception {
		Thread.sleep(3000);
		Assert.assertEquals(getElement(locatorName).getText(), num+"");
		
	}
	
	public void verifyText(String locatorName, String text) throws Exception {
		Thread.sleep(3000);
		Assert.assertEquals(getElement(locatorName).getText(), text);
		
	}
	
	public void verifyAttribute(String locatorName, String attribute, String content) throws Exception {
		Thread.sleep(3000);
		Assert.assertEquals(getElement(locatorName).getAttribute(attribute), content);
	}
	
	public void clickVisibleDiv(String text) {
		driver.findElement(By.xpath("//*[text()='"+text+"']")).click();
//		driver.manage().timeouts().setScriptTimeout(5, TimeUnit.SECONDS);
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	


}
