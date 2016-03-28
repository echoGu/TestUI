package com.trane.display.test;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ExampleTest {

	public static void main(String[] args) throws Exception {
		WebDriver driver = new FirefoxDriver();

		driver.get("http://192.168.1.3/FS/root/UI_Medium/index.html");
		
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.textToBePresentInElementLocated(By.id("idLbl_pgHomePage_Title"), "Home"));
		
		WebElement e = driver.findElement(By.id("idBtn_pgMain_navfooterBtnReports"));
		if (e.isDisplayed()) {

			System.out.println("getAttribute(): " + e.getAttribute("class")); // navfooter_btn_off
			System.out.println("getText: " + e.getText()); // Reports
			System.out.println("getLocation: " + e.getLocation()); // (218, 426)
			System.out.println("getSize: " + e.getSize()); // (180, 50)

			e.click();

			System.out.println("..................................");

			System.out.println("getAttribute(): " + e.getAttribute("class")); // navfooter_btn_on
			System.out.println("getText: " + e.getText()); // Reports
			System.out.println("getLocation: " + e.getLocation()); // (218, 426)
			System.out.println("getSize: " + e.getSize()); // (180, 50)
			
			/**
			 * <div class="button_text text_wrap v_align" id="idLbl_pgReportsLanding_r3c3">Log Sheet</div>
			 * #idLbl_pgReportsLanding_r3c3
			 * //*[@id='idLbl_pgReportsLanding_r3c3']
			 */
            Thread.sleep(3000);
//			WebElement el = driver.findElement(By.xpath("//*[@id='idLbl_pgReportsLanding_r3c3']"));
            WebElement el = driver.findElement(By.id("idLbl_pgReportsLanding_r3c3"));
			el.click();

			driver.close();
		}
	}

}
