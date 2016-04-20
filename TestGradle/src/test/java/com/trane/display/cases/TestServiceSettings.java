package com.trane.display.cases;

import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.trane.display.utils.BaseActions;
import com.trane.display.utils.TestNGListener;

@Listeners({TestNGListener.class })
public class TestServiceSettings extends BaseActions
{
	private String StandardReport_Title = "StandardReport_Title";
	
	private String localRequiredDevices = "UC800 - RTAF - Comp4 - BASE - RequiredDevices";
	private String localConfigurationRecord = "UC800 - RTAF - Comp4 - BASE - ConfigurationRecord";
	private String localNameplateRecord = "UC800 - RTAF - Comp4 - BASE - NameplateRecord";
	private String localQuestionRecord = "UC800 - RTAF - Comp4 - BASE - QuestionRecord";
	
	@BeforeSuite
	public void configure() throws Exception 
	{
		configFTPfiles(localRequiredDevices, localConfigurationRecord, localNameplateRecord, localQuestionRecord);
		initLocatorMap();
		openHomePage();
	}
	
	@Test(description = "Navigate to Service Settings page")
	public void navigateToPage() throws Exception 
	{
		clickVisibleDiv("Settings");
		clickVisibleDiv("Chiller Settings");
		clickVisibleDiv("Service Settings");
		clickVisibleDiv("OK");
		verifyText(StandardReport_Title, "Service Settings");
	}
	
	@Test(description = "Verify Data")
	public void verifyData() throws Exception 
	{
//		verifySpecificPageData("BaseConfigLogSheet", 1);
//		verifySpecificPageData("BaseConfigLogSheet", 2);
		verifyAllData("BaseConfigServiceSettings");
	}
	
	@Test(description = "Close FireFox")
	public void tearDown() 
	{
		closeFirefox();
	}
	
	

}
