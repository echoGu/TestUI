package com.trane.display.utils;

public class Locator {
	private String selector;
	/**
	 * create a enum variable for By
	 */
	public enum ByType 
	{
		xpath, id, linkText, name, className, cssSelector, partialLinkText, tagName
	}

	private ByType byType;

	public Locator() 
	{

	}

	/**
	 * default Locator - id
	 */
	public Locator(String selector) 
	{
		this.selector = selector;
		this.byType = ByType.id;
	}

	public Locator(String selector, ByType byType) 
	{
		this.selector = selector;
		this.byType = byType;
	}

	public String getSelector() 
	{
		return selector;
	}

	public ByType getBy() 
	{
		return byType;
	}

	public void setBy(ByType byType) 
	{
		this.byType = byType;
	}

}
