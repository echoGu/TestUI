package com.trane.display.utils;

/**
 * This class is the testNG listener. 
 * Used by every test class with keywords - "@Listeners({TestNGListener.class })"
 * 
 * @author irblir
 * @since 2016-04-22
 */

import java.util.ArrayList;
import java.util.List;

import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

public class TestNGListener extends TestListenerAdapter 
{
	Log log = new Log(this.getClass());
	
    @Override
    public void onTestStart(ITestResult result) 
    {
        Assertion.flag = true;       
        Assertion.errors.clear();
    }

	@Override
	public void onTestSuccess(ITestResult tr) 
	{
		super.onTestSuccess(tr);
		this.handleAssertion(tr);
		log.info(tr.getName()+ "--Test method success\n");
	}

	@Override
	public void onTestFailure(ITestResult tr) 
	{
		super.onTestFailure(tr);
		this.handleAssertion(tr);
		log.info(tr.getName()+ "--Test method Failure\n");
	}

	@Override
	public void onTestSkipped(ITestResult tr) 
	{
		super.onTestSkipped(tr);
		this.handleAssertion(tr);
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

	}

	private int index = 0;
    
    private void handleAssertion(ITestResult tr)
    {
        if(!Assertion.flag)
        {
            Throwable throwable = tr.getThrowable();    
            
            if(throwable == null)
            {
                throwable = new Throwable();
            }
            
            StackTraceElement[] traces = throwable.getStackTrace();
            StackTraceElement[] alltrace = new StackTraceElement[0];
            
            for (Error e : Assertion.errors) 
            {
                StackTraceElement[] errorTraces = e.getStackTrace();
                StackTraceElement[] et = this.getKeyStackTrace(tr, errorTraces);
                StackTraceElement[] message = new StackTraceElement[] {
                				new StackTraceElement("Message: "+ e.getMessage(), 
                						"Method: " + tr.getMethod().getMethodName(), 
                						"Class: " + tr.getTestClass().getRealClass().getSimpleName(), 
                						index)
                				};
                index = 0;
                alltrace = this.merge(alltrace, message);
                alltrace = this.merge(alltrace, et);
            }
            
            if(traces != null)
            {
                traces = this.getKeyStackTrace(tr, traces);
                alltrace = this.merge(alltrace, traces);
            }  
            
            throwable.setStackTrace(alltrace);
            tr.setThrowable(throwable);
            Assertion.flag = true;   
            Assertion.errors.clear();
            tr.setStatus(ITestResult.FAILURE);           
        }
    }
     
    private StackTraceElement[] getKeyStackTrace(ITestResult tr, StackTraceElement[] stackTraceElements)
    {
        List<StackTraceElement> ets = new ArrayList<StackTraceElement>();
        
        for (StackTraceElement stackTraceElement : stackTraceElements) 
        {           
            if(stackTraceElement.getClassName().equals(tr.getTestClass().getName()))
            {               
                ets.add(stackTraceElement);
                index = stackTraceElement.getLineNumber();
            }
        }
        
        StackTraceElement[] et = new StackTraceElement[ets.size()];
        
        for (int i = 0; i < et.length; i++) 
        {
            et[i] = ets.get(i);
        }
        
        return et;
    }
     
    private StackTraceElement[] merge(StackTraceElement[] traces1, StackTraceElement[] traces2)
    {
        StackTraceElement[] ste = new StackTraceElement[traces1.length + traces2.length];
        
        for (int i = 0; i < traces1.length; i++) 
        {
            ste[i] = traces1[i];
        }
        
        for (int i = 0; i < traces2.length; i++) 
        {
            ste[traces1.length+i] = traces2[i];
        }
        
        return ste;
    }

}
