package com.trane.display.cases;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.trane.display.utils.BaseActions;
import com.trane.display.utils.TestNGListener;

@Listeners({TestNGListener.class })
public class TestCustomReport extends BaseActions
{
	private String btn_Reports = "btn_Reports";
	private String title_Reports = "title_Reports";
	
	private String localRequiredDevices = "UC800 - RTAF - Comp4 - BASE - RequiredDevices";
	private String localConfigurationRecord = "UC800 - RTAF - Comp4 - BASE - ConfigurationRecord";
	private String localNameplateRecord = "UC800 - RTAF - Comp4 - BASE - NameplateRecord";
	private String localQuestionRecord = "UC800 - RTAF - Comp4 - BASE - QuestionRecord";
	
	@BeforeClass
	public void configure() throws Exception 
	{
		configFTPfiles(localRequiredDevices, localConfigurationRecord, localNameplateRecord, localQuestionRecord);
		initLocatorMap();
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

	@Test(description = "Click Custom Report 1 button on the Reports page")
	public void clickCustomReport() throws Exception 
	{
		clickVisibleDiv("Custom Report 1");
	}
	
	@Test(description = "Click Edit button")
	public void ClickEditButton() throws Exception 
	{
		clickVisibleDiv("Edit");
	}
	
	@Test(description = "Verify Custom Report 1 Data")
	public void verifyCustomReportData() throws Exception 
	{
		verifySpecificCustomReportData("BaseConfigCustomReport", "Chiller");
	}
	
	@Test(description = "Close FireFox")
	public void tearDown() 
	{
		closeFirefox();
	}
	
	

}
