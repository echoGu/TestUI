package com.trane.display.utils;

import java.nio.charset.Charset;
import java.util.HashMap;

import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;

public class CSVutils {
	public static HashMap<String, String> readCSV(String path, Integer pageIndex) throws Exception {
		Log log = new Log(CSVutils.class);
		
		HashMap<String, String> dataMap = new HashMap<String, String>();
		dataMap.clear();
		
        CsvReader reader = new CsvReader(path, ',',Charset.forName("UTF-8"));
        reader.readHeaders();
        while (reader.readRecord()) {

            String selector = reader.get("id");
            String varName = reader.get("varName");
            String index = reader.get("pageIndex");

        	if(reader.get("pageIndex").equalsIgnoreCase(pageIndex+"")) {
                log.info(index + "," + selector + "," + varName);
            	dataMap.put(selector, varName);
            } else {
            	continue;
            }
        	
        }
        reader.close();
        return dataMap;
	}
	
//	public static void writeCSV(String filename) throws Exception {
//		String path = System.getProperty("user.dir") 
//				+ "\\src\\main\\java\\com\\trane\\display\\cases\\data\\" 
//				+ filename;
//		
//		CsvWriter writer = new CsvWriter(path, ',', Charset.forName("UTF-8"));
//		String[] contents = {"Liy","wang","12"};
//		writer.writeRecord(contents);
//		writer.close();
//	}

}
