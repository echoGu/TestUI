package com.trane.display.cases;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.trane.display.pages.HomePage;

public class TestPageObjects {

	public static void main(String[] args) throws Exception {
		WebDriver driver = new FirefoxDriver();

		driver.get("http://192.168.1.3/FS/root/UI_Medium/index.html");
//    	driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
		
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.textToBePresentInElementLocated(By.id("idLbl_pgHomePage_Title"), "Home"));
		
		HomePage homePage = new HomePage(driver);
		PageFactory.initElements(driver, HomePage.class);
		System.out.println(homePage);
		
		homePage.NavigateToReportsPage().NavigateToLogSheetPage();
		driver.quit();
		


	}

}
