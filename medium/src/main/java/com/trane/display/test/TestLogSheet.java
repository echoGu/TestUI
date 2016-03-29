package com.trane.display.test;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.trane.display.pageActions.ReportsPage;
import com.trane.display.utils.TestNGListener;

@Listeners({TestNGListener.class })
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
		repo.verifyFooterBtnOn();
		repo.verifyReportsTitle();
	}

	@Test
	public void clickLogSheet() throws Exception {
		repo.clickBtnLogSheet();
	}
	
	@Test
	public void verifyBtnLogSheetClicked() throws Exception {
		repo.verifyStandardReportTitle("Log Sheet");
		repo.verifyStandardReportSubTitle("Evaporator");
		repo.verifyPageNum(1);
		repo.verifyTotalPageNum(10);
	}
	
	@Test
	public void tearDown() {
		driver.close();
	}
	

}
