package com.trane.display.cases;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.trane.display.utils.BaseActions;
import com.trane.display.utils.TestNGListener;

@Listeners({TestNGListener.class })
public class TestLogSheet {

	private WebDriver driver = new FirefoxDriver();
	BaseActions instance = new BaseActions(driver);
	
	private String btn_Reports = "btn_Reports";
	private String title_Reports = "title_Reports";
	private String StandardReport_Title = "StandardReport_Title";
	private String StandardReport_SubTitle = "StandardReport_SubTitle";
	private String page_num = "page_num";
	private String total_page_num = "total_page_num";
	
	private String localRequiredDevices = "UC800 - RTAF - Comp4 - BASE - RequiredDevices";
	private String localConfigurationRecord = "UC800 - RTAF - Comp4 - BASE - ConfigurationRecord";
	private String localNameplateRecord = "UC800 - RTAF - Comp4 - BASE - NameplateRecord";
	private String localQuestionRecord = "UC800 - RTAF - Comp4 - BASE - QuestionRecord";
	
	@BeforeClass
	public void configure() throws Exception {
		instance.configFTPfiles(localRequiredDevices, localConfigurationRecord, localNameplateRecord, localQuestionRecord);
		instance.InitLocatorMap();
		instance.openHomePage();
	}
	
	@Test(description = "Click Reports button on the navigate footer")
	public void clickReports() throws Exception {
		instance.verifyAttribute(btn_Reports, "class", "navfooter_btn_off");
		instance.clickVisibleDiv("Reports");
	}
	
	@Test(description = "Verify Reports button pressed in and navigated to Reports page")
	public void verifyBtnReportsClicked() throws Exception {
		instance.verifyAttribute(btn_Reports, "class", "navfooter_btn_on");
		instance.verifyText(title_Reports, "Reports");
	}

	@Test(description = "Click Log Sheet button on the Reports page")
	public void clickLogSheet() throws Exception {
		instance.clickVisibleDiv("Log Sheet");
	}
	
	@Test(description = "Verify on Log Sheet page 1")
	public void verifyBtnLogSheetClicked() throws Exception {
		instance.verifyText(StandardReport_Title, "Log Sheet");
		instance.verifyText(StandardReport_SubTitle, "Evaporator");
		instance.verifyText(page_num, 1);
		instance.verifyText(total_page_num, 13);
	}
	
	@Test(description = "Verify Log Sheet Data")
	public void verifyLogSheetData() throws Exception {
		instance.VerifyData("BaseConfigLogSheet", 1);
		instance.click("btn_Down");
		instance.VerifyData("BaseConfigLogSheet", 2);
	}
	
	@Test(description = "Close FireFox")
	public void tearDown() {
		driver.close();
	}
	
	

}
