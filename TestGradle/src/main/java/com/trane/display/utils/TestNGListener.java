package com.trane.display.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

public class TestNGListener extends TestListenerAdapter 
{
	Log log = new Log(this.getClass());

	@Override
	public void onTestSuccess(ITestResult tr) 
	{
		super.onTestSuccess(tr);
		log.info(tr.getName()+ "--Test method success\n");
	}

	@Override
	public void onTestFailure(ITestResult tr) 
	{
		super.onTestFailure(tr);
		log.info(tr.getName()+ "--Test method Failure\n");
	}

	@Override
	public void onTestSkipped(ITestResult tr) 
	{
		super.onTestSkipped(tr);
		log.info(tr.getName()+ "--Test method Skipped\n");
	}

	@Override
	public void onStart(ITestContext testContext) 
	{
		super.onStart(testContext);
		log.info("Test Start\n");
	}

	@Override
	public void onFinish(ITestContext testContext) 
	{
		super.onFinish(testContext);
		log.info("Test Finish\n");

		ArrayList<ITestResult> testsToBeRemoved = new ArrayList<ITestResult>();
		
		Set<Integer> passedTestIds = new HashSet<Integer>();
		for (ITestResult passedTest : testContext.getPassedTests().getAllResults()) 
		{
			log.info("PassedTests = " + passedTest.getName());
			passedTestIds.add(getId(passedTest));
		}

		Set<Integer> failedTestIds = new HashSet<Integer>();
		for (ITestResult failedTest : testContext.getFailedTests().getAllResults()) 
		{
			log.info("failedTest = " + failedTest.getName());
			int failedTestId = getId(failedTest);
			if (failedTestIds.contains(failedTestId)|| passedTestIds.contains(failedTestId)) 
			{
				testsToBeRemoved.add(failedTest);
			} else 
			{
				failedTestIds.add(failedTestId);
			}
		}

		for (Iterator<ITestResult> iterator = testContext.getFailedTests().getAllResults().iterator(); iterator.hasNext();) 
		{
			ITestResult testResult = iterator.next();
			if (testsToBeRemoved.contains(testResult)) 
			{
				log.info("Remove repeat Fail Test: " + testResult.getName());
				iterator.remove();
			}
		}

	}

	private int getId(ITestResult result) 
	{
		// id = class + method + dataProvider
		int id = result.getTestClass().getName().hashCode();
		id = id + result.getMethod().getMethodName().hashCode();
		id = id + (result.getParameters() != null ? Arrays.hashCode(result.getParameters()) : 0);
		return id;
	}

}
