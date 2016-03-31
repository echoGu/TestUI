package com.trane.display.pages;

import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;;

public class HomePage {
	private WebDriver driver;
	
	@FindBy(id = "idLbl_pgHomePage_Title")
	private WebElement homeTitle;
	
	@FindBy(id = "idBtn_pgMain_navfooterBtnReports")
	private WebElement reportsButton;
	
	public HomePage(WebDriver driver) {
		this.driver = driver;
	}
	
	public ReportsPage NavigateToReportsPage() {
		if(homeTitle.getText() != "Home")
			throw new NoSuchWindowException("This is not Home Page");
		reportsButton.click();
		PageFactory.initElements(driver, new ReportsPage(this.driver));
		return new ReportsPage(driver);
	}
	

}
