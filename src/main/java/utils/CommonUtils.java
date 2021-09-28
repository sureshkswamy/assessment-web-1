package utils;

import java.io.File;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class CommonUtils {
	
	
	// generate random string
	public static String randomAlphaString(int n) {
		
		String aString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		StringBuilder sb = new StringBuilder(n);
		
		for (int i = 0; i < n; i++) {
            int index = (int)(aString.length() * Math.random());
            sb.append(aString.charAt(index));
        }
		
        return sb.toString();
    }
	
	// wait time
	public static void sleep(int time) {
		try {
			Thread.sleep(time);
		} catch (Exception e) {
			System.out.print(e.getMessage());
		}
	}
	
	public static void highlight(WebDriver driver, WebElement element) {
	    JavascriptExecutor jsexecute = (JavascriptExecutor) driver;
	    jsexecute.executeScript("arguments[0].setAttribute('style', 'background: yellow; border: 2px solid green;');", element);
	}
	
	public static boolean isFileDownloaded(String downloadPath, String fileName) {
		boolean flag = false;
	    File dir = new File(downloadPath);
	    File[] dir_contents = dir.listFiles();
	  	    
	    for (int i = 0; i < dir_contents.length; i++) {
	    	System.out.println("file is :: " + dir_contents[i].getName());
	    	String sFileName = dir_contents[i].getName();
	        if (sFileName.contains(fileName)) {
	        	System.out.println("file is :: " + sFileName);
	            return flag=true;
	            }
	    }
	    
	    	return flag; 
	}	

}