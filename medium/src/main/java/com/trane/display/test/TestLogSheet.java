package com.trane.display.test;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.Test;

import com.trane.display.pageActions.ReportsPage;


public class TestLogSheet {
	WebDriver driver = new FirefoxDriver();
	ReportsPage repo = new ReportsPage(driver);

	@Test
	public void clickReports() throws Exception {
		repo.verifyFooterBtnOff();
		repo.clickBtnReports();
	}
	
	@Test
	public void verifyBtnReportsClicked() throws Exception {
//		repo.verifyFooterBtnOn();
		repo.verifyReportsTitle();
	}

	@Test
	public void clickBLogSheet() throws Exception {
		repo.clickBtnLogSheet();
		repo.verifyStandardReportTitle("Log Sheet");
		repo.verifyStandardReportSubTitle("Evaporator");
		repo.verifyPageNum(1);
		repo.verifyTotalPageNum(10);

	}
	
	@Test
	public void verifyBtnLogSheetClicked() throws Exception {
		repo.verifyStandardReportTitle("Log Sheet");
		repo.verifyStandardReportSubTitle("Evaporator");
		repo.verifyPageNum(1);
		repo.verifyTotalPageNum(10);
	}
	
//	@Test
//	public void tearDown() {
//		driver.close();
//	}
	

}
