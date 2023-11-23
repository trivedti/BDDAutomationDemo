
package com.example.Utilities;


import java.io.File;

import java.io.FileInputStream;
import java.io.IOException;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;



//import jdk.nashorn.internal.runtime.regexp.joni.Config;

import java.util.*;

public class SendMailUser {
	public static String report_FolderName=null;
	public static String result_FolderName=null;
	
    public static FileInputStream fs;

    public static Properties extent;
	
	
	
	public void execute() throws MessagingException, IOException {
   //public static void main(String[] args) {
      // SMTP server configuration
		
		fs = new FileInputStream(
	            System.getProperty("user.dir") + "\\src\\test\\resources\\extent.properties");
		extent = new Properties();
		extent.load(fs);
		System.out.println("fs:" + fs);
	    // Date d = new Date();
		// String date=d.toString().replaceAll(" ", "_");
		// date=date.replaceAll(":", "_");
		// date=date.replaceAll("\\+", "_");
		// System.out.println(date);
//	   report_FolderName="test-output/ExtentReport";
	   report_FolderName=extent.getProperty("basefolder.name").toString();
		//result_FolderName="ExtentReport"+"_"+d-MMM-YY HH-mm-ss;
	   System.out.println("report folder name " +report_FolderName);
	   

		result_FolderName="ExtentReport"+""+extent.getProperty("basefolder.datetimepattern").toString();
		System.out.println("result folder name " + result_FolderName);
		//result_FolderName="ExtentReport"+" "+basefolder.datetimepattern=d-MMM-YY HH-mm-ss;
//		String reportsDirPath=System.getProperty("user.dir")+"\\"+report_FolderName+"\\"+result_FolderName;
		String reportsDirPath=System.getProperty("user.dir")+"\\"+report_FolderName+"\\"+result_FolderName;
		System.out.println("report path name " + reportsDirPath);

		new File(reportsDirPath).mkdirs();

	   
      String host = "smtp.office365.com";
      int port = 587;
     final String username = "automation@synoverge.com";
     final String password = "Synoverge@!@#$%";

      // Sender and recipient email addresses
      String senderEmail = "automation@synoverge.com";
      String recipientEmail = "automation@synoverge.com";

      
      // Create properties
      Properties props = new Properties();
      
      props.put("smtp.office365.com", "587");
		  
		   props.put("mail.smtp.auth", true);
		  props.put("mail.smtp.starttls.enable", true);
		  props.setProperty("mail.smtp.ssl.protocols", "TLSv1.2");
		  props.put("mail.smtp.starttls.enable", "true");
		  props.put("mail.smtp.host", host);
		  props.put("mail.smtp.port", port);
		  props.put("mail.smtp.starttls.enable", "true");
		
		  
		  // Create session
	      Session session = Session.getInstance(props, new javax.mail.Authenticator() {
	         protected PasswordAuthentication getPasswordAuthentication() {
	           // return new PasswordAuthentication(username, password);
              return new PasswordAuthentication(username, password);
	         }
	      }); 
	      
	      try {
	            // Create a new message
	            Message message = new MimeMessage(session);

	            // Set the sender's and recipient's email addresses
	            message.setFrom(new InternetAddress(username));
	            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
      
	            // Set the email subject
      message.setSubject("Execution mail with HTML report Test");

      
      // Create the message body part for text content
      BodyPart textBodyPart = new MimeBodyPart();
      textBodyPart.setText("Hello, This email has an attachment!");
      
      
      // Create the message body part for the attachment
      MimeBodyPart attachmentBodyPart = new MimeBodyPart();
      String filePath = report_FolderName+"\\"+result_FolderName+"\\Spark.html";
      attachmentBodyPart.attachFile(filePath);
      
      
   
      // Create a multipart message to combine text and attachment
      Multipart multipart = new MimeMultipart();
      multipart.addBodyPart(textBodyPart);
      multipart.addBodyPart(attachmentBodyPart);

   // Set the multipart message as the content of the email
      message.setContent(multipart);
      
      // Send the email
      Transport.send(message);

   
         System.out.println("Email sent successfully.");
      } catch (MessagingException e) {
          e.printStackTrace();
      } catch (Exception e) {
          e.printStackTrace();
      }
  }
}





