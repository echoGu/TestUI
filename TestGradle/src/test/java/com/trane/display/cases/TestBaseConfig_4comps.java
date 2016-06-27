package com.trane.display.cases;



import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.trane.display.utils.BaseActions;
import com.trane.display.utils.TestNGListener;

@Listeners({TestNGListener.class })
public class TestBaseConfig_4comps extends BaseActions
{
//	private String localRequiredDevices = "UC800 - RTAF - Comp4 - BASE - RequiredDevices";
//	private String localConfigurationRecord = "UC800 - RTAF - Comp4 - BASE - ConfigurationRecord";
//	private String localNameplateRecord = "UC800 - RTAF - Comp4 - BASE - NameplateRecord";
//	private String localQuestionRecord = "UC800 - RTAF - Comp4 - BASE - QuestionRecord";
	
//	@BeforeClass
//	public void configure() throws Exception 
//	{
//		configFTPfiles(localRequiredDevices, localConfigurationRecord, localNameplateRecord, localQuestionRecord);
//		openHomePage();
//	}
	
	@Test(description = "Navigate to Service Settings page")
	public void a_navigateToServiceSettingsPage() throws Exception 
	{
//		configFTPfiles("localRequiredDevices", "localConfigurationRecord", "localNameplateRecord", "localQuestionRecord");
		openHomePage();
		clickVisibleDiv("Settings");
		clickVisibleDiv("Chiller Settings");
		clickVisibleDiv("Service Settings");
		clickVisibleDiv("OK");
		verifyText("Title", "Service Settings");
	}
	
	@Test(description = "Verify Service Settings Data")
	public void b_verifyServiceSettingsData() throws Exception 
	{
		verifyAllData("RTAFComp4GlycolServiceSettings");
	}
	
//	@Test(description = "Navigate to Edit Custom Report page")
//	public void c_navigateToEditCustomReportPage() throws Exception 
//	{
//		clickVisibleDiv("Reports");
//		clickVisibleDiv("Custom Report 1");
//		clickVisibleDiv("Edit");
//		verifyText("Title", "Edit Custom Report");
//		verifyText("SubTitle", "Custom Report 1");
//	}
//	
//	@Test(description = "Verify Custom Report Data")
//	public void d_verifyCustomReportData() throws Exception 
//	{
//		verifyAllCustomReportData("BaseConfigCustomReport");
//	}
	
//	@Test(description = "Navigate to Log Sheet page")
//	public void e_navigateToLogSheetPage() throws Exception 
//	{
//		clickVisibleDiv("Reports");
//		clickVisibleDiv("Log Sheet");
//		verifyText("Title", "Log Sheet");
//		verifyText("SubTitle", "Evaporator");
//	}
//	
//	@Test(description = "Verify Log Sheet Data")
//	public void f_verifyLogSheetData() throws Exception 
//	{
//		verifyAllData("BaseConfigLogSheet");
//	}
	
//	@Test(description = "Navigate to Evaporator Circuit 1 page")
//	public void g_navigateToEvapCkt1Page() throws Exception 
//	{
//		clickVisibleDiv("Reports");
//		clickVisibleDiv("Evaporator");
//		clickVisibleDiv("Circuit 1");
//		verifyText("Title", "Evaporator");
//		verifyText("SubTitle", "Circuit 1");
//	}
//	
//	@Test(description = "Verify Data")
//	public void h_verifyEvapCkt1Data() throws Exception 
//	{
//		verifyAllData("BaseConfigEvap_Ckt1");
//	}
	
	@AfterClass(description = "Close FireFox")
	public void tearDown() 
	{
		closeFirefox();
	}
	
	

}
