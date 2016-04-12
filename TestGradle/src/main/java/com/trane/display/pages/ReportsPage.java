package com.trane.display.pages;

import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ReportsPage {
	private WebDriver driver;
	
	@FindBy(id = "idLbl_pgReportsLanding_PageTitle")
	private WebElement reportsTitle;
	
	@FindBy(id = "idBtn_pgReportsLanding_r3c3")
	private WebElement logSheetButton;

	public ReportsPage(WebDriver driver) {
		this.driver = driver;
	}
	
	public LogSheetPage NavigateToLogSheetPage() {
		if(reportsTitle.getText() != "Reports")
			throw new NoSuchWindowException("This is not Reports Page");
		logSheetButton.click();
		PageFactory.initElements(driver, new LogSheetPage(this.driver));
		return new LogSheetPage(driver);
	}

}
