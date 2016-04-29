package com.trane.display.utils;

/**
 * This class is the father of all testNG test classes.
 * It covers all the necessary basic actions.
 * 
 * @author irblir
 * @since 2016-04-22
 */

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BaseActions 
{
	
	public WebDriver driver = new FirefoxDriver();
	public HashMap<String, Locator> locatorMap;
	public Log log = new Log(BaseActions.class);
	public String localDirAndFileName;
	public HashMap<String, String> dataMap;
	
	public String mainResourePath = "\\src\\main\\resources";
	public String testResourePath = "\\src\\test\\resources";
	public String uiMapPackagePath = "\\com\\trane\\display\\UIMaps\\";
	public String testDataPackagePath = "\\com\\trane\\display\\cases\\data\\";
	
	/**
	 * read xml to inital UI locator map
	 * @throws Exception
	 */
	public void initLocatorMap() throws Exception 
	{
		localDirAndFileName = System.getProperty("user.dir") 
				+ mainResourePath
				+ uiMapPackagePath
				+ "UIMap.xml";
		log.info(localDirAndFileName);
		locatorMap = XMLutils.readXMLDocument(localDirAndFileName);
		
	}
	
	public void navigateToPage(Integer pageIndex) throws Exception
	{
		String currentPageNum = getElement("PageNum").getText();
		log.info("currentPageNum: " + currentPageNum);
		
		int PageNum =Integer.parseInt(currentPageNum);
		int min = pageIndex - PageNum;
		int flag;
		
		if(min == 0)
		{
			flag = 0;
		} 
		else if(min>0)
		{
			flag = 1;
		}
		else
		{
			flag = -1;
		}
		
		switch(flag)
		{
		case 0:
			log.info("on the expected page. Do noting.");
			break;
			
		case 1:
			log.info("need click movedown button "+ min + " times to get the expected page.");
			for(int i=0; i<min; i++)
			{
				click("btn_Down");
			}
			break;
		
		case -1:
			log.info("need click moveup button "+ min + " times to get the expected page.");
			for(int i=0; i<min; i++)
			{
				click("btn_Up");
			}
			break;
		
		}
	}
	
	public HashMap<String,String> initialDataMap(String filename, String pageIndex) throws Exception
	{
		localDirAndFileName = System.getProperty("user.dir") 
				+ testResourePath
				+ testDataPackagePath 
				+ filename
				+ ".csv";
		log.info(localDirAndFileName);
		dataMap = CSVutils.readCSV(localDirAndFileName, pageIndex);
		
		return dataMap;
	}
	
	public void verifySpecificPageData(String filename, Integer pageIndex) throws Exception
	{
		navigateToPage(pageIndex);
		compareData(filename, pageIndex+"");
	}
	
	public void navigateToCustomReportGroup(String groupname) throws Exception
	{
		String currentGroup = getElement("txt_group_name").getText();
		
		while(!currentGroup.equals(groupname))
		{
			click("btn_report_group_Down");
			currentGroup = getElement("txt_group_name").getText();
		}
		
		log.debug("currentGroup is " + currentGroup + "; expected group is " + groupname);
		
	}
	
	public void verifyAllCustomReportData(String filename) throws Exception
	{
		verifySpecificCustomReportData(filename, "Chiller");
		verifySpecificCustomReportData(filename, "Evaporator");
		verifySpecificCustomReportData(filename, "Condenser");
		verifySpecificCustomReportData(filename, "Compressor");
		verifySpecificCustomReportData(filename, "Motor");
		
	}
	
	public void verifySpecificCustomReportData(String filename, String groupname) throws Exception
	{
		navigateToCustomReportGroup(groupname);
		
		try {
			dataMap = initialDataMap(filename, groupname);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		  if(!dataMap.isEmpty()) 
		  {
			  String nextItemId = null;
			  for(Iterator<String> iter = dataMap.keySet().iterator(); iter.hasNext();)
				  {
				      String key = (String)iter.next();
				      String value = (String)dataMap.get(key);
				      String actualValue =null;
				      
		    		  try 
		    		  {
		    			  WebElement e = driver.findElement(By.id(key));
		    			  actualValue = e.getText();

		    			} 
		    		    catch (NoSuchElementException e) 
		    		    {
		    				actualValue = "Blank";
		    			}
		    		  
		    		  if(!actualValue.equals(value))
		    		  {
		    			  log.error("filename: " + filename);
		    			  log.error("page: " + groupname);
			    		  log.error("id: " + key);
			    		  log.error("Actual_value: " + actualValue);
		    			  log.error("Expect_value: " + value + "\n");
		    		  }
	    			  Assertion.verifyEquals(actualValue, value, groupname);
	    			  
	    			 int index = Integer.parseInt(key.substring(key.lastIndexOf("_")+1));
					 if(index >0 && index % 2 == 0)  
					 {
						 String btnDownlocatorname = "btn_report_entries_available_down";
						 if(isElementDisplayed(byLocatorName(btnDownlocatorname)))
						 {
							 log.debug("Click down button to get the next two items.");
							 click(btnDownlocatorname);
						 }
					 }
					 nextItemId = "item_available_" + (index+1);
				   }
			  
    		  try 
    		  {
    			  By by = By.id(nextItemId);
    			  
    			  if(isElementDisplayed(by))
    			  {
    				  Assertion.verifyEquals(nextItemId + " exists on the UI","no related data on the CSV file.");
    			  }

    			} 
    		    catch (NoSuchElementException e) {
    			  log.debug("nextItemId: " + nextItemId);
    			  Assertion.verifyEquals("null","null");
    			}
			} 
		  else 
			{
			  Assertion.verifyEquals("no data for page " + groupname + " !!! Please check CSV file.", "some data");
		    }
	}

	public void compareData(String filename, String pageIndex)
	{
		try {
			dataMap = initialDataMap(filename, pageIndex);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		  if(!dataMap.isEmpty()) 
		  {
				for(Iterator<String> iter = dataMap.keySet().iterator(); iter.hasNext();)
				  {
				      String key = (String)iter.next();
				      String value = (String)dataMap.get(key);
				      String actualValue =null;
				      
		    		  try {
						      WebElement e = driver.findElement(By.id(key));
				    		  actualValue = e.getText();
		    			} catch (NoSuchElementException e) {
		    				actualValue = "Blank";
		    			}
		    		  
		    		  if(!actualValue.equals(value))
		    		  {
		    			  log.error("filename: " + filename);
		    			  log.error("page: " + pageIndex);
			    		  log.error("id: " + key);
			    		  log.error("Actual_value: " + actualValue);
		    			  log.error("Expect_value: " + value + "\n");
		    		  }
		    	
	    			  Assertion.verifyEquals(actualValue, value, pageIndex);
				      
				   }
			} 
		  else 
			{
			  Assertion.verifyEquals("no data for page " + pageIndex + " !!! Please check CSV file.", "some data");
		    }
	}
	
	public void verifyAllData(String filename) throws Exception
	{
		String totalPageNum = getElement("TotalPageNum").getText();
		log.info("ready to verify " + totalPageNum + " pages data. Please make sure your CSV file has the necessary data.");
		
		int tpn = Integer.parseInt(totalPageNum);
		
		for(int i=1; i<=tpn; i++)
		{
			log.info("ready to verify page " + i +".");
			verifySpecificPageData(filename, i);
		}
	}
	
	public void replaceFTPfile(String localfilename, String ftpDirAndFileName) throws Exception 
	{
		localDirAndFileName = System.getProperty("user.dir") 
				+ testResourePath
				+ testDataPackagePath
				+ "UC Configs\\" 
				+ localfilename
				+ ".xml";

		FTPutils.deleteFile(ftpDirAndFileName);
		FTPutils.uploadFile(localDirAndFileName, ftpDirAndFileName);
	
	}
	
	public void configFTPfiles(String localRequiredDevices, String localConfigurationRecord, String localNameplateRecord, String localQuestionRecord) throws Exception 
	{
		FTPutils.connectFTP();
		
		log.info("configure RequiredDevices......");
		replaceFTPfile(localRequiredDevices, "/FW_IPC3DeviceBinding/RequiredDevices.xml");
		
		log.info("configure ConfigurationRecord......");
		replaceFTPfile(localConfigurationRecord, "/Configuration/ConfigurationRecord.xml");
		
		log.info("configure NameplateRecord......");
		replaceFTPfile(localNameplateRecord, "/Configuration/NameplateRecord.xml");
		
		log.info("configure QuestionRecord......");
		replaceFTPfile(localQuestionRecord, "/Configuration/QuestionRecord.xml");

		FTPutils.closeFTP();
		rebootMP();
		
	}
	
	public void rebootMP() 
	{
		String path = System.getProperty("user.dir") 
				+ testResourePath
				+ testDataPackagePath 
				+ "UC800_reset.py";
		 try {  
	             log.info("start to reboot MP.........");
	             Process pr = Runtime.getRuntime().exec("python " + path);  
	             pr.waitFor();
	             log.info("wait 30 seconds to wait for reboot successfully......");
	             Thread.sleep(30000);
	             log.info("reboot MP successfully");
             } catch (Exception e) 
		 	  {  
                 e.printStackTrace();  
              }  
	}
	
	public WebDriver getDriver() 
	{
		return driver;
	}

    public void openHomePage() throws Exception 
    {
    	initLocatorMap();
    	driver.get("http://192.168.1.3/FS/root/UI_Medium/index.html");  
    	WebDriverWait wait = new WebDriverWait(driver,20); 
    	wait.until(ExpectedConditions.
    			textToBePresentInElementLocated(byLocatorName("Title"), "Home"));
        log.info("Home Page is loaded successfully");
    	Thread.sleep(10000);
    }
    
    public void takeScreenShot() 
    {
		
		SimpleDateFormat sf = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
		Calendar cal = Calendar.getInstance();
		Date date = cal.getTime();
		String dateStr = sf.format(date);
		
		String localDirAndFileName = System.getProperty("user.dir") 
				+ "\\screenshots\\" 
				+ dateStr
				+ ".png";
		
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		
		try 
		{
			log.info("saved screenshot is:" + localDirAndFileName);
			FileUtils.copyFile(scrFile, new File(localDirAndFileName));
		} catch (Exception e) 
		  {
			log.error("Can't save screenshot");
			e.printStackTrace();
		  } finally 
		    {
			 log.info("screen shot finished");
		    }
	}
    
    
	public boolean isElementDisplayed(By by) 
	{
		try 
		{
			if(driver.findElement(by).isDisplayed())
			{
				return true;
			}
		} catch (NoSuchElementException ex) 
		{
			return false;
		}
		return false;
	}
    
	public Locator getLocator(String locatorName) throws IOException 
	{
		Locator locator = locatorMap.get(locatorName);
		return locator;

	}
	
	public By byLocatorName(String locatorName) throws Exception
	{
		return getByLocator(getLocator(locatorName));
	}
	
	public WebElement getElement(String locatorName) throws Exception
	{
		Locator locator = getLocator(locatorName);
		By by = getByLocator(locator);
		WebElement element = driver.findElement(by);
		log.info("getElement method - get the WebElement by locatorName: " + locatorName);
		return element;
	}
	
	public void click(String locatorName) throws Exception 
	{
		WebElement e = getElement(locatorName);
		log.info("click "+locatorName);
		e.click();
		Thread.sleep(1000);
	}
	
	public By getByLocator(Locator locator) throws IOException 
	{
		By by = null;
		String selector = locator.getSelector();
		switch (locator.getBy()) 
		{
			case xpath:
				log.info("find element By xpath:"+selector);
				by = By.xpath(selector);
				break;
			case id:
				log.info("find element By id:"+selector);
				by = By.id(selector);
				break;
			case name:
				log.info("find element By name:"+selector);
				by = By.name(selector);
				break;
			case cssSelector:
				log.info("find element By cssSelector:"+selector);
				by = By.cssSelector(selector);
				break;
			case className:
				log.info("find element By className:"+selector);
				by = By.className(selector);
				break;
			case tagName:
				log.info("find element By tagName:"+selector);
				by = By.tagName(selector);
				break;
			case linkText:
				log.info("find element By linkText:"+selector);
				by = By.linkText(selector);
				break;
			case partialLinkText:
				log.info("find element By partialLinkText:"+selector);
				by = By.partialLinkText(selector);
				break;
		}
		return by;
	}
	
	public void verifyText(String locatorName, Integer num) throws Exception 
	{
		Thread.sleep(1000);
		Assertion.verifyEquals(getElement(locatorName).getText(), num+"");
		
	}

	public void verifyText(String locatorName, String text) throws Exception 
	{
		Thread.sleep(1000);
		Assertion.verifyEquals(getElement(locatorName).getText(), text);
		
	}

	public void verifyAttribute(String locatorName, String attribute, String content) throws Exception 
	{
		Thread.sleep(1000);
		Assertion.verifyEquals(getElement(locatorName).getAttribute(attribute), content);
	}
	
	public void clickVisibleDiv(String text) 
	{
		driver.findElement(By.xpath("//*[text()='"+text+"']")).click();
		try 
		{
			Thread.sleep(1000);
		} catch (InterruptedException e) 
		{
			e.printStackTrace();
		}
	}
	
	public void closeFirefox()
	{
		driver.close();
	}
	


}
