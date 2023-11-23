package com.example;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;

import org.testng.TestNG;

/**
 * Hello world!
 *
 */
public class App 
{
	static TestNG testNg;



    public static void main( String[] args ) throws MessagingException, IOException
    {
    	System.out.println("Main Called-->" + System.getProperty("user.dir"));
        TestNG testng = new TestNG();
        // Create a list of String
        List<String> suitefiles = new ArrayList<String>();

        suitefiles.add(System.getProperty("testng.xml"));
        testng.setTestSuites(suitefiles);
        testng.run();
        System.exit(testng.hasFailure() ? 1 : 0);


    }
}
