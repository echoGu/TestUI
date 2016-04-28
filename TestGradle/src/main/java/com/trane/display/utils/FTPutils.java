package com.trane.display.utils;

/**
 * This class is designed to update configuration files.
 * 
 * @author irblir
 * @since 2016-04-22
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.SocketException;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FTPutils 
{
	
	 private static FTPClient ftpClient = new FTPClient();
	 private static Log log = new Log(FTPutils.class);
	 
	 public static boolean connectFTP() 
	 {
		 boolean result = false;
		 String ip = "192.168.1.3";
		 String username = "root";
		 String password = "p21onLUC";

         try 
         {
        	 
			int reply;
			ftpClient.connect(ip);
			ftpClient.login(username, password);

			reply = ftpClient.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) 
			{
				log.error("Connect failed");
				ftpClient.disconnect();
				return result;
			}
			
	         result = true;
	         log.info("Connect to the Server successfully");
		         
		} catch (SocketException e) 
         {
			e.printStackTrace();
		} catch (IOException e) 
         {
			e.printStackTrace();
		}
		 
		 return result;
		 
	 }

	 public static boolean uploadFile(String localDirAndFileName, String ftpDirAndFileName) throws Exception 
	 {
	        boolean result = false;
	        
         	int separator = ftpDirAndFileName.lastIndexOf("/");
         	String ftpDir = ftpDirAndFileName.substring(0, separator+1);
         	String ftpfilename = ftpDirAndFileName.substring(separator+1);
         	log.info("uploadFile method: ftpDirectory: " + ftpDir);
         	log.info("uploadFile method: ftpfilename: " + ftpfilename);
	        
	        if(!ftpClient.isConnected()) 
	        {
	        	log.info("uploadFile method: the connection is lost!");
	        	return false;
	        }
	
        		 boolean changeDir = ftpClient.changeWorkingDirectory(ftpDir);
        		 log.info("changeDir: "+changeDir);
        		 FileInputStream fis = new FileInputStream(new File(localDirAndFileName));

 	            if (changeDir) 
 	            {

 	            	ftpClient.setControlEncoding("utf-8");
 	            	ftpClient.setBufferSize(1024);
 	            	ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
 	            	ftpClient.enterLocalPassiveMode();
 	            	
 	                result = ftpClient.storeFile(new String(ftpfilename.getBytes()), fis);
 	            	
 	                if (result) 
 	                {
 	                    log.info("upload " + localDirAndFileName +" Successfully to "+ ftpDir + ftpfilename);
 	                } else if(!result) 
 	                      {
 	                		log.error("fail to upload " + localDirAndFileName +" to "+ ftpDir + ftpfilename);
 	                	  }
 	            } else if(!changeDir) 
 	                  {
 	            		log.error("fail to change Directory.");
 	            	  }
 	            fis.close();
	           
	        
	        return result;
	    }
	 
	 public static boolean deleteFile(String ftpDirAndFileName) 
	 {
	        boolean result = false;
         	log.info("deleteFile method: ftpDirAndFileName: " + ftpDirAndFileName);
	        
	        if(!ftpClient.isConnected()) 
	        {
	        	log.info("deleteFile method: the connection is lost!");
	        	return false;
	        }
	
	        try 
	        {
	        		result = ftpClient.deleteFile(ftpDirAndFileName);
	        		if (result) 
	        		{
	        			log.info("delete " + ftpDirAndFileName + " Successfully");
	        		} else if(!result) 
	        		      {
	        				log.info("deleteFile method: do nothing here. Maybe " + ftpDirAndFileName + " doesn't exist.");
	        			  }
	        } catch (IOException e) 
	        {
	            e.printStackTrace();
	        }
	        return result;
		 
	 }

	 
	 public static void closeFTP() 
	 {
		 if (ftpClient != null && ftpClient.isConnected()) 
		 {
			 try 
			 {
				ftpClient.disconnect();
			} catch (IOException e) 
			 {
				e.printStackTrace();
			}
			 log.info("Close Server Successfully!");

         }
	 }
}