package com.trane.display.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.SocketException;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FTPutils {
	
	 private static FTPClient ftpClient = new FTPClient();
	 private static Logger logger = LogManager.getLogger(FTPutils.class);
	 private static String encoding = System.getProperty("file.encoding");
	 private static String ftpDir = "Configuration";
	 
	 public static boolean connectFTP() {
		 boolean result = false;
		 String ip = "192.168.1.3";
		 String username = "user";
		 String password = "p21onLUC";

         try {
        	 
			int reply;
			ftpClient.connect(ip);
			ftpClient.login(username, password);

			reply = ftpClient.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) {
				logger.error("Connect failed");
				ftpClient.disconnect();
				return result;
			}
			
	         result = true;
		         
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		 
		 return result;
		 
	 }

	 public static boolean uploadFile(String localDirAndFileName, String ftpfilename) {
	        boolean result = false;
         	File srcFile = new File(localDirAndFileName);
         	FileInputStream fis = null;
	        
	        if(!ftpClient.isConnected()) {
	        	return false;
	        }
	
	        try {
        		 boolean changeDir = ftpClient.changeWorkingDirectory(ftpDir);

 	            if (changeDir) {
 	            	fis = new FileInputStream(srcFile);

// 	            	ftpClient.setBufferSize(1024);
 	            	ftpClient.setControlEncoding(encoding);
 	            	ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
 	                result = ftpClient.storeFile(new String(ftpfilename.getBytes(),"iso-8859-1"), fis);
 	                if (result) {
 	                    logger.info("upload " + ftpfilename +" Successfully!");
 	                }
 	            }
 	            fis.close();
 	            ftpClient.logout();

	           
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        return result;
	    }
	 
	 public static boolean deleteFile(String ftpfilename) {
	        boolean result = false;
	        
	        if(!ftpClient.isConnected()) {
	        	return false;
	        }
	
	        try {
	        	boolean changeDir = ftpClient.changeWorkingDirectory(ftpDir);

	            if (changeDir) {
	            	result = ftpClient.deleteFile(ftpfilename);
	                if (result) {
	                    logger.info("upload File Successfully");
	                }
	            }
	            ftpClient.logout();

	           
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        return result;
		 
	 }

	 
	 public static void closeFTP() {
		 if (ftpClient != null && ftpClient.isConnected()) {
			 try {
				ftpClient.disconnect();
			} catch (IOException e) {
				e.printStackTrace();
			}
			 logger.info("Close Server Successfully!");

         }
	 }
}