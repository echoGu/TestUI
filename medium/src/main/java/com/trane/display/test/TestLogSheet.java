package com.trane.display.test;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
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
	
	@Test
	public void clickReports() throws Exception {
		instance.verifyAttribute(btn_Reports, "class", "navfooter_btn_off");
		instance.clickVisibleDiv("Reports");
	}
	
	@Test
	public void verifyBtnReportsClicked() throws Exception {
		instance.verifyAttribute(btn_Reports, "class", "navfooter_btn_on");
		instance.verifyText(title_Reports, "Reports");
	}

	@Test
	public void clickLogSheet() throws Exception {
		instance.clickVisibleDiv("Log Sheet");
	}
	
	@Test
	public void verifyBtnLogSheetClicked() throws Exception {
		instance.verifyText(StandardReport_Title, "Log Sheet");
		instance.verifyText(StandardReport_SubTitle, "Evaporator");
		instance.verifyText(page_num, 1);
		instance.verifyText(total_page_num, 10);
	}
	
	@Test
	public void tearDown() {
		driver.close();
	}
	

}
