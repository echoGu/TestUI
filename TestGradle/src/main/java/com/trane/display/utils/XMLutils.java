package com.trane.display.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import com.trane.display.utils.Locator.ByType;


public class XMLutils {
    /**
     * read xml to initialize locatorMap
     * @param path
     * @param pageName
     * @return HashMap<String, Locator>
     * @throws Exception
     */
	public static HashMap<String, Locator> readXMLDocument(String path) throws Exception 
	{

		Log log = new Log(XMLutils.class);
		
		HashMap<String, Locator> locatorMap = new HashMap<String, Locator>();
		locatorMap.clear();
		File file = new File(path);
		if (!file.exists()) 
		{
			log.error("Can't find " + path);
			throw new IOException("Can't find " + path);
		}
		
		SAXReader reader = new SAXReader();
		Document document = reader.read(file);
		Element root = document.getRootElement();
		
		for (Iterator<?> it = root.elementIterator(); it.hasNext();) 
		{
			Element page = (Element) it.next();
				
				for (Iterator<?> l = page.elementIterator(); l.hasNext();) 
				{
					String type = null;
					String value = null;
					String locatorName = null;
					Element locator = (Element) l.next();
					
					for (Iterator<?> j = locator.attributeIterator(); j.hasNext();) 
					{
						Attribute attribute = (Attribute) j.next();
						
						if (attribute.getName().equals("type")) 
						{
							type = attribute.getValue();
							log.info("get locator type: " + type);
						} else 
						{
							value = attribute.getValue();
							log.info("get locator value: " + value);
						}

					}
					
					Locator temp = new Locator(value, getByType(type));
					locatorName = locator.getText();
					log.info("locator Name is: " + locatorName);
					locatorMap.put(locatorName, temp);
				}
				continue;

		}
		return locatorMap;

	}
	
	public static ByType getByType(String type) 
	{
		ByType byType = ByType.id;
		if (type == null || type.equalsIgnoreCase("id")) 
		{
			byType = ByType.id;
		} else if (type.equalsIgnoreCase("xpath")) 
		{
			byType = ByType.xpath;
		} else if (type.equalsIgnoreCase("linkText")) 
		{
			byType = ByType.linkText;
		} else if (type.equalsIgnoreCase("name")) 
		{
			byType = ByType.name;
		} else if (type.equalsIgnoreCase("className")) 
		{
			byType = ByType.className;
		} else if (type.equalsIgnoreCase("cssSelector")) 
		{
			byType = ByType.cssSelector;
		} else if (type.equalsIgnoreCase("partialLinkText")) 
		{
			byType = ByType.partialLinkText;
		} else if (type.equalsIgnoreCase("tagName")) 
		{
			byType = ByType.tagName;
		}
		return byType;
	}
	
	/**
	 * create a UIMap template
	 * @throws IOException
	 */

	public static void writeXMLDocument() throws IOException 
	{
		OutputFormat format = OutputFormat.createPrettyPrint();
		XMLWriter writer = new XMLWriter(new FileWriter("output.xml"), format);
		Document document = DocumentHelper.createDocument();
		Element root = document.addElement("map");
		root.addComment("locator of page map info");
		Element page = root.addElement("page").addAttribute("pageName","com.trane.display.UIMaps.UIMap");
		page.addComment("Locator lists");
		page.addElement("locator").addAttribute("type", "ById").addAttribute("value", "idBtn_pgMain_navfooterBtnReports").addText("btn_Reports");
		page.addElement("locator").addAttribute("type", "ById").addAttribute("value", "idLbl_pgReportsLanding_PageTitle").addText("title_Reports");
		writer.write(document);
		writer.close();
	}

}
