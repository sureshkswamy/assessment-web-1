package pages;


import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import base.TestBase;

public class LoginPage extends TestBase {
	
	// object repository
	@FindBy(id="username")
	WebElement userName;
	
	@FindBy(id="password")
	WebElement passWord;
	
	@FindBy(xpath="//span[text()='Login']")
	WebElement loginBtn;
	
	@FindBy(xpath="//div[@class='ant-row'][1]")
	WebElement pieCharts;
	
	@FindBy(xpath="//div[@id='metricsChart']")
	WebElement metricsChart;
	
	// initialize page objects
	public LoginPage(String tProperties) {
		
		super(tProperties);
		PageFactory.initElements(driver, this);
	}
	
	// login method
	public HomePage login(String username, String password) {
		
		
		userName.sendKeys(username);
		passWord.sendKeys(password);
		loginBtn.click();
		
		return new HomePage();
	}
	
}
