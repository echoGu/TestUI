package com.trane.display.utils;

public class Locator {
	private String selector;


	/**
	 * create a enum variable for By
	 */
	public enum ByType {
		xpath, id, linkText, name, className, cssSelector, partialLinkText, tagName
	}

	private ByType byType;

	public Locator() {

	}

	/**
	 * default Locator ,use id
	 */
	public Locator(String selector) {
		this.selector = selector;
		this.byType = ByType.id;
	}

	public Locator(String selector, ByType byType) {
		this.selector = selector;
		this.byType = byType;
	}

	public String getSelector() {
		return selector;
	}

	public ByType getBy() {
		return byType;
	}

	public void setBy(ByType byType) {
		this.byType = byType;
	}

	public void setReplace(String rep, String rex)
	{
		StringTools.replaceAll(selector, rex, rep);
	}
}
