package pages;


import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import base.TestBase;

public class HomePage extends TestBase{
	
	@FindBy(xpath="//div[@class='ant-row'][1]")
	WebElement pieCharts;
	
	@FindBy(xpath="//div[@id='metricsChart']")
	WebElement metricCharts;
	
	@FindBy(xpath="//a[@keys='2']")
	WebElement exploreTab;
	
	@FindBy(xpath="//span[@class='antd-pro-components-global-header-index-name' and text()='test']")
	WebElement userLabel;
	
	@FindBy(xpath="//span[text()='Logout']")
	WebElement logout;
	
	@FindBy(xpath="//span[@class='antd-pro-layouts-user-layout-title' and text()='Lucidum']")
	WebElement logoutTitle;
	
	// initialize page objects
		public HomePage() {
			
			super();
			PageFactory.initElements(driver, this);
		}
	
	// method to return pie charts display status
	public boolean validatePieCharts() {
		
		return pieCharts.isDisplayed();
	}
	
	// method to return metric chart display status
	public boolean validateMetricsChart() {
			
		return metricCharts.isDisplayed();
	}
	
	// click explore tab
	public ExplorePage clickExploreTab() {
		
		exploreTab.click();
		return new ExplorePage();
	}
	
	public boolean logOff() {
		
		userLabel.click();
		logout.click();
		
		return logoutTitle.isDisplayed();
	}

}