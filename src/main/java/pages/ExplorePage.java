package pages;

import java.util.List;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import base.TestBase;

public class ExplorePage extends TestBase{
	
	@FindBy(xpath="//a[@keys='2']")
	WebElement exploreTab;
	
	@FindBy(xpath="(//span[@class='ant-select-selection-item'])[2]")
	WebElement searchInput;
	
	@FindBy(xpath="//input[@id='one[0]']")
	WebElement searchValue;
	
	@FindBy(xpath="//input[@id='three[0]']")
	WebElement filterValue;
	
	@FindBy(xpath="//span[text()='Run']")
	WebElement runButton;
	
	////tr[@class='ant-table-row ant-table-row-level-0']
	@FindAll(@FindBy(xpath="//tr[@class='ant-table-row ant-table-row-level-0']"))
	List<WebElement> resultTable;
	
	@FindBy(xpath="//div[@class='ant-row']//span[@style='margin-right: 10px;']")
	WebElement resultLabel;
	
	WebDriverWait webDriverWait = new WebDriverWait(driver, 30);
	Actions actions = new Actions(driver);
	
	// initialize page objects
	public ExplorePage() {
		
		super();
		PageFactory.initElements(driver, this);
	}
	
	// method to return search term input display status
	public boolean validateSearchInput() {
			
		return searchInput.isDisplayed();
	}
	
	
	
	//run query method
	public void runQuery(String searchTerm, String value) {
		
		// input search criteria
		searchInput.click();
		
		webDriverWait.until(ExpectedConditions.visibilityOf(searchInput));
		
		
		searchInput.sendKeys(searchTerm);
		searchInput.sendKeys(Keys.ENTER);
		searchInput.sendKeys(Keys.TAB);
		filterValue.sendKeys(value);
		filterValue.sendKeys(Keys.ENTER);
		
		// click run button
		runButton.click();
		
		// result table 
		int rowCount = resultTable.size();
		System.out.println("row count is : " + rowCount);
		System.out.println(rowCount);
		
		// fetch result count
		String queryResults = resultLabel.getText();
		System.out.println(queryResults);
		String recordCount = queryResults.substring(15);
		System.out.println(recordCount);
		
		String queryResult = "{resultTable: }";
	}
	
	
	
	// return record count from query run
	public int queryRunValidation() {
		
		// result table 
		int rowCount = resultTable.size();
		System.out.println("row count is : " + rowCount);
		System.out.println(rowCount);
		
		return rowCount;
	}
	
	// return record count from query run
	public String queryResultCount() {
		
		// record count
		String queryResults = resultLabel.getText();
		System.out.println(queryResults);
		String recordCount = queryResults.substring(15);
		System.out.println(recordCount);
		
		return recordCount;
	}	

}
