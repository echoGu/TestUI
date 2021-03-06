package com.trane.display.cases;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.trane.display.utils.BaseActions;
import com.trane.display.utils.TestNGListener;

@Listeners({TestNGListener.class })
public class TestLogSheet extends BaseActions
{
	private String btn_Reports = "btn_Reports";
	private String title_Reports = "title_Reports";
	private String StandardReport_Title = "StandardReport_Title";
	private String StandardReport_SubTitle = "StandardReport_SubTitle";
	private String page_num = "page_num";
	private String total_page_num = "total_page_num";
	
	private String localRequiredDevices = "UC800 - RTAF - Comp2 - BASE - RequiredDevices";
	private String localConfigurationRecord = "UC800 - RTAF - Comp2 - BASE - ConfigurationRecord";
	private String localNameplateRecord = "UC800 - RTAF - Comp2 - BASE - NameplateRecord";
	private String localQuestionRecord = "UC800 - RTAF - Comp2 - BASE - QuestionRecord";
	
	@BeforeClass
	public void configure() throws Exception 
	{
		configFTPfiles(localRequiredDevices, localConfigurationRecord, localNameplateRecord, localQuestionRecord);
		InitLocatorMap();
		openHomePage();
	}
	
	@Test(description = "Click Reports button on the navigate footer")
	public void clickReports() throws Exception 
	{
		verifyAttribute(btn_Reports, "class", "navfooter_btn_off");
		clickVisibleDiv("Reports");
	}
	
	@Test(description = "Verify Reports button pressed in and navigated to Reports page")
	public void verifyBtnReportsClicked() throws Exception 
	{
		verifyAttribute(btn_Reports, "class", "navfooter_btn_on");
		verifyText(title_Reports, "Reports");
	}

	@Test(description = "Click Log Sheet button on the Reports page")
	public void clickLogSheet() throws Exception 
	{
		clickVisibleDiv("Log Sheet");
	}
	
	@Test(description = "Verify on Log Sheet page 1")
	public void verifyBtnLogSheetClicked() throws Exception 
	{
		verifyText(StandardReport_Title, "Log Sheet");
		verifyText(StandardReport_SubTitle, "Evaporator");
		verifyText(page_num, 1);
		verifyText(total_page_num, 13);
	}
	
	@Test(description = "Verify Log Sheet Data")
	public void verifyLogSheetData() throws Exception 
	{
		VerifyData("BaseConfigLogSheet", 1);
		click("btn_Down");
		VerifyData("BaseConfigLogSheet", 2);
	}
	
	@Test(description = "Close FireFox")
	public void tearDown() 
	{
		closeFirefox();
	}
	
	

}
