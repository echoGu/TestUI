package com.trane.display.pages;

import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LogSheetPage {
	private WebDriver driver;
	
	@FindBy(id = "idLbl_pgStandardReport_Title")
	private WebElement logSheetTitle;

	public LogSheetPage(WebDriver driver) {
		this.driver = driver;
		if(logSheetTitle.getText() != "Log Sheet")
			throw new NoSuchWindowException("This is not Log Sheet Page");
		
	}

}
