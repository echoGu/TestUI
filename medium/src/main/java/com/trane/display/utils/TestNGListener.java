package com.trane.display.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

public class TestNGListener extends TestListenerAdapter {
	Log log = new Log(this.getClass());

	@Override
	public void onTestSuccess(ITestResult tr) {
		super.onTestSuccess(tr);
		log.info("Test Success");
	}

	@Override
	public void onTestFailure(ITestResult tr) {
		super.onTestFailure(tr);
		log.error("Test Failure");
		takeScreenShot(tr);
	}

	private void takeScreenShot(ITestResult tr) {
		UITest b = (UITest) tr.getInstance();
		WebDriver currentDriver = b.getDriver();
		System.out.println(currentDriver.getTitle());
		b.takeScreenShot();

	}

	@Override
	public void onTestSkipped(ITestResult tr) {
		super.onTestSkipped(tr);
		log.error("Test Skipped");
	}

	@Override
	public void onTestStart(ITestResult result) {
		super.onTestStart(result);
		log.info("Test Finish");
	}

	@Override
	public void onStart(ITestContext testContext) {
		super.onStart(testContext);
		log.info("Test Start");
	}

	@Override
	public void onFinish(ITestContext testContext) {
		super.onFinish(testContext);
		log.info("Test Finish");

		ArrayList<ITestResult> testsToBeRemoved = new ArrayList<ITestResult>();
		
		Set<Integer> passedTestIds = new HashSet<Integer>();
		for (ITestResult passedTest : testContext.getPassedTests().getAllResults()) {
			log.info("PassedTests = " + passedTest.getName());
			passedTestIds.add(getId(passedTest));
		}

		Set<Integer> failedTestIds = new HashSet<Integer>();
		for (ITestResult failedTest : testContext.getFailedTests().getAllResults()) {
			log.info("failedTest = " + failedTest.getName());
			
			// if we saw this test as a failed test before we mark as to be
			// deleted
			// or delete this failed test if there is at least one passed
			// version
			int failedTestId = getId(failedTest);
			if (failedTestIds.contains(failedTestId)|| passedTestIds.contains(failedTestId)) {
				testsToBeRemoved.add(failedTest);
			} else {
				failedTestIds.add(failedTestId);
			}
		}

		// finally delete all tests that are marked
		for (Iterator<ITestResult> iterator = testContext.getFailedTests().getAllResults().iterator(); iterator.hasNext();) {
			ITestResult testResult = iterator.next();
			if (testsToBeRemoved.contains(testResult)) {
				log.info("Remove repeat Fail Test: " + testResult.getName());
				iterator.remove();
			}
		}

	}

	private int getId(ITestResult result) {
		// id = class + method + dataProvider
		int id = result.getTestClass().getName().hashCode();
		id = id + result.getMethod().getMethodName().hashCode();
		id = id + (result.getParameters() != null ? Arrays.hashCode(result.getParameters()) : 0);
		return id;
	}

}
