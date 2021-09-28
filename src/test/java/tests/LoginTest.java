package tests;


import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import base.TestBase;
import pages.HomePage;
import pages.LoginPage;

public class LoginTest extends TestBase {
	
	LoginPage loginPage;
	HomePage homePage;
	
	//static String tProperties = "/src/main/java/config/config.properties";
	static String tProperties = "config.properties";
	
	public LoginTest() {
		super("config.properties");
		
	}
	
	@BeforeMethod
	public void setUp() {
		
		initialization();
		loginPage = new LoginPage(tProperties);
		
	}
	
	@Test
	public void loginTest() {
		homePage = loginPage.login(prop.getProperty("username"), prop.getProperty("password"));
		
		// login assertion - check charts display
		boolean pStatus = homePage.validatePieCharts();
		Assert.assertTrue(pStatus);
		
		// login assertion - check metric chart display
		boolean mStatus = homePage.validateMetricsChart();
		Assert.assertTrue(mStatus);
		
	}
	
	
	@AfterMethod
	public void tearDown() {
		
		// logout and validate
		boolean lStatus = homePage.logOff();
		Assert.assertTrue(lStatus);
		
		driver.quit();
	}

}
