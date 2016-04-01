package com.trane.display.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.StringTokenizer;

public class CSVutils {
	public static void readCSV(String filename) {
		String path = System.getProperty("user.dir") 
				+ "\\src\\main\\java\\com\\trane\\display\\cases\\data\\" 
				+ filename;
		try {
			File csv = new File(path);
			BufferedReader br = new BufferedReader(new FileReader(csv));
			String line = "";
			while ((line = br.readLine()) != null) {
				StringTokenizer st = new StringTokenizer(line, ",");
				while (st.hasMoreTokens()) {
					System.out.print(st.nextToken() + "/t");
				}
				System.out.println();
			}
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void writeCSV(String filename) {
		String path = System.getProperty("user.dir") 
				+ "\\src\\main\\java\\com\\trane\\display\\cases\\data\\" 
				+ filename;
		try {
			File csv = new File(path);
			BufferedWriter bw = new BufferedWriter(new FileWriter(csv, true));
//			bw.newLine();
			bw.write("12" + "," + "2009" + "," + "1212");
			bw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
