package com.trane.display.utils;

/**
 * This class is designed to override Assert.assertEquals method. 
 * when some step fails, the execution will not be stopped and the errors will be logged.
 * 
 * @author irblir
 * @since 2016-04-22
 */

import java.util.ArrayList;
import java.util.List;
import org.testng.Assert;

public class Assertion 
{
    
    public static boolean flag = true;
    public static List<Error> errors = new ArrayList<Error>();
     
    public static void verifyEquals(Object actual, Object expected)
    {
        try
        {
            Assert.assertEquals(actual, expected);
        }
        catch(Error e)
        {
            errors.add(e);
            flag = false;
        }
    }
    
    public static void verifyEquals(String actual, String expected)
    {
        try
        {
            Assert.assertEquals(actual, expected);
        }
        catch(Error e)
        {
            errors.add(e);
            flag = false;
        }
    }
     
    public static void verifyEquals(Object actual, Object expected, String message)
    {
        try
        {
            Assert.assertEquals(actual, expected, message);
        }
        catch(Error e)
        {
            errors.add(e);
            flag = false;
        }
    }
    
    public static void verifyEquals(String actual, String expected, String message)
    {
        try
        {
            Assert.assertEquals(actual, expected, message);
        }
        catch(Error e)
        {
            errors.add(e);
            flag = false;
        }
    }
 
}
