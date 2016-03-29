package com.trane.display.pageActions;

import org.openqa.selenium.WebDriver;

import com.trane.display.utils.BaseActions;
import com.trane.display.utils.Log;

public class ReportsPage extends BaseActions {

	protected WebDriver driver;
	protected Log log = new Log(this.getClass());
	protected String btn_Reports = "btn_Reports";
	protected String title_Reports = "title_Reports";
	protected String StandardReport_Title = "StandardReport_Title";
	protected String StandardReport_SubTitle = "StandardReport_SubTitle";
	protected String btn_LogSheet = "btn_LogSheet";
	protected String page_num = "page_num";
	protected String total_page_num = "total_page_num";
	
	public ReportsPage(WebDriver driver) {
		super(driver);
		this.driver=driver;
	}

	public WebDriver getDriver() {
		return driver;
	}
	public void setDriver(WebDriver driver) {
		this.driver = driver;
	}
	/**
	 * Click "Reports"	
	 * @throws Exception
	 */
	public void clickBtnReports() throws Exception {
//		click(btn_Reports);
		clickVisibleDiv("Reports");

	}
	/**
	 * Click "Log Sheet"
	 * @throws Exception
	 */
	public void clickBtnLogSheet() throws Exception {
//		click(btn_LogSheet);
		clickVisibleDiv("Log Sheet");
	}
	/**
	 * FooterBtn is pressed in
	 * @throws Exception
	 */
	public void verifyFooterBtnOn() throws Exception {
		verifyAttribute(btn_Reports, "class", "navfooter_btn_on");
	}
	/**
	 * FooterBtn is Not pressed in
	 * @throws Exception
	 */
	public void verifyFooterBtnOff() throws Exception {
		verifyAttribute(btn_Reports, "class", "navfooter_btn_off");
	}
    /**
     * verify ReportsTitle is Reports
     * @throws Exception
     */
	public void verifyReportsTitle() throws Exception {
		verifyText(title_Reports, "Reports");
	}
	/**
	 * 
	 * @param Title
	 * @throws Exception
	 */
	public void verifyStandardReportTitle(String Title) throws Exception {
		verifyText(StandardReport_Title, Title);
	}
	/**
	 * 
	 * @param subTitle
	 * @throws Exception
	 */
	public void verifyStandardReportSubTitle(String subTitle) throws Exception {
		verifyText(StandardReport_SubTitle, subTitle);
	}
	/**
	 * 
	 * @param num
	 * @throws Exception
	 */
	public void verifyPageNum(Integer num) throws Exception {
		verifyText(page_num, num);
		
	}
	/**
	 * 
	 * @param num
	 * @throws Exception
	 */
	public void verifyTotalPageNum(Integer num) throws Exception {
		verifyText(total_page_num, num);
		
	}

}