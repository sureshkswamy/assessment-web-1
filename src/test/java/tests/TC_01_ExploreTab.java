package tests;


import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import base.TestBase;
import pages.ExplorePage;
import pages.HomePage;
import pages.LoginPage;
import utils.CommonUtils;

public class TC_01_ExploreTab extends TestBase {
	
	LoginPage loginPage;
	HomePage homePage;
	ExplorePage explorePage;
	
	static String tProperties = "config.properties";
	
	public TC_01_ExploreTab() {
		super("config.properties");
		
	}
	
	@BeforeMethod
	public void setUp() {
		
		initialization();
		loginPage = new LoginPage(tProperties);
	}
	
	@Test
	public void Test_01_ExploreTab() {
		
		WebDriverWait webDriverWait = new WebDriverWait(driver, 30);
		
		// login to application and assertion
		homePage = loginPage.login(prop.getProperty("username"), prop.getProperty("password"));
		
			// login assertion - check charts display
			boolean pStatus = homePage.validatePieCharts();
			Assert.assertTrue(pStatus);
			
			// login assertion - check metric chart display
			boolean mStatus = homePage.validateMetricsChart();
			Assert.assertTrue(mStatus);
		
		// navigate to explore tab
		explorePage = homePage.clickExploreTab();
		
			// assert explore page
			boolean eStatus = explorePage.validateSearchInput();
			Assert.assertTrue(eStatus);
		
			
		// Explore tab - run query
//		explorePage.runQuery("# of CPU Cores", "4.0");
//			
//			// query run assertions
//			int rowCount = explorePage.queryRunValidation();
//			boolean rcStatus = false;
//			if (rowCount>0) {
//				rcStatus = true;
//			}
//			Assert.assertEquals(rcStatus,true);
//		
//			// record count from query run
//			String recordCount = explorePage.queryResultCount();
//			System.out.println(recordCount);
		
		// explore functionality
			
			driver.findElement(By.xpath("//a[@keys='2']")).click();
			driver.findElement(By.xpath("(//span[@class='ant-select-selection-item'])[2]")).click();
			driver.findElement(By.xpath("//input[@id='one[0]']")).sendKeys("# of CPU Cores");
			driver.findElement(By.xpath("//input[@id='one[0]']")).sendKeys(Keys.ENTER);
			driver.findElement(By.xpath("//input[@id='one[0]']")).sendKeys(Keys.TAB);
			driver.findElement(By.xpath("//input[@id='three[0]']")).sendKeys("4.0");
			driver.findElement(By.xpath("//input[@id='three[0]']")).sendKeys(Keys.ENTER);
			
			
			driver.findElement(By.xpath("//span[text()='Run']")).click();
			
			webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//tr[@class='ant-table-row ant-table-row-level-0']")));
			String result = driver.findElement(By.xpath("//div[@class='ant-row']//span[@style='margin-right: 10px;']")).getText();
			System.out.println("Query result count is :: " + result);
			
			// capture record count from query run for future use
			String subresult = driver.findElement(By.xpath("//div[@class='ant-row']//span[@style='margin-right: 10px;']")).getText();
			String queryResultCount = subresult.substring(15);
			System.out.println("sub string count :: " + queryResultCount);
			System.out.println(queryResultCount);
			
			
			System.out.println("START - Save Query #########################");
				
				//random query name to save
				String queryName = "Test_"+CommonUtils.randomAlphaString(9);
			
				// save query functionality
				driver.findElement(By.xpath("//button[@class='ant-btn ant-btn-primary ant-btn-icon-only ant-dropdown-trigger']")).click();
				driver.findElement(By.xpath("//li[contains(@data-menu-id, 'saveQuery')]")).click();
			
				webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(text(),'Save Query')]")));
			
				// query details
				driver.findElement(By.id("name")).sendKeys(queryName);
				driver.findElement(By.id("remark")).sendKeys(queryName);
				driver.findElement(By.xpath("//span[contains(text(),'Confirm')]")).click();
			
				WebElement querySaved = driver.findElement(By.xpath("//span[contains(text(),'Query saved')]"));
				
				Assert.assertEquals(true, querySaved.isDisplayed());
				System.out.println("Query :: " + queryName + " :: Successfully saved");
				
			System.out.println("END - Save Query #########################");
			
				CommonUtils.sleep(3000);
			
			System.out.println("START - Query Management - Query Library #########################");
				
				// navigate to query management
				driver.findElement(By.xpath("//button[@class='ant-btn ant-btn-primary ant-btn-icon-only ant-dropdown-trigger']")).click();
				driver.findElement(By.xpath("//li[contains(@data-menu-id, 'queryManagement')]")).click();
				webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//li[contains(@data-menu-id, 'queryManagement')]")));
			
				// customize xpath to fetch query name used for save query feature
				String qmcustomXpath = "//tr[@class='ant-table-row ant-table-row-level-0']//td[contains(text(),'" + queryName + "')]";
				System.out.println("qmcustomxpath is :: " + qmcustomXpath);
				
				// delete saved query
				String qmRemoveRecord = "//td[contains(text(),'" + queryName + "')]//parent::td//parent::tr[@class='ant-table-row ant-table-row-level-0']//*//input[@type='checkbox']";
				driver.findElement(By.xpath(qmRemoveRecord)).click();
				driver.findElement(By.xpath("//span[contains(text(),'Delete')]")).click();
				webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@type='button']//span[text()='OK']")));
				driver.findElement(By.xpath("//button[@type='button']//span[text()='OK']")).click();
				
				// assertion for query deletion
				WebElement qlibraryDelete = driver.findElement(By.xpath("//span[contains(text(),'Query deleted')]"));
				Assert.assertEquals(true, qlibraryDelete.isDisplayed());
				
				System.out.println("Query :: " + queryName + " :: Successfully deleted");
			
			System.out.println("END - Query management - Query Library #########################");	 
			
				CommonUtils.sleep(3000);
			
			System.out.println("START - Query Management - Query Run History #########################");	
				
				// navigatge query run history
				driver.findElement(By.xpath("//div[contains(text(),'Query Run History')]")).click();
				
				// customize xpath to fetch record count
				String qmHistoryXpath = "//tr[@class='ant-table-row ant-table-row-level-0']//td[text()='" + queryResultCount + "']";
				// assertion using record count
				Assert.assertEquals(queryResultCount, driver.findElement(By.xpath(qmHistoryXpath)).getText());
				
				// customize xpath to fetch record based on record count
				String qmHistoryDeleteXpath = "//td[text()='" + queryResultCount + "']//parent::tr[@class='ant-table-row ant-table-row-level-0']//input[@type='checkbox']";
				driver.findElement(By.xpath(qmHistoryDeleteXpath)).click();
				
				// delete record from query run history
				driver.findElement(By.xpath("//div[contains(@id,'queryHistory')]//button[@type='button']//span[text()='Delete']")).click();
				
				// assertion - query history deletion
				boolean qmHistoryStatus = driver.findElements(By.xpath(qmHistoryDeleteXpath)).isEmpty();
				Assert.assertEquals(true, qmHistoryStatus);
				
				System.out.println("Query :: " + queryName + " :: Run History Successfully deleted");
				
				// close dialog
				driver.findElement(By.xpath("//span[@class='ant-modal-close-x']")).click();
			
			System.out.println("END - Query Management - Query Run History #########################");	
			
				CommonUtils.sleep(3000);
			
			System.out.println("START - Edit Column #########################");	
				
				// naivagte to edit column dialog
				driver.findElement(By.xpath("//button[@class='ant-btn ant-btn-primary ant-btn-icon-only ant-dropdown-trigger']")).click();
				webDriverWait.until(ExpectedConditions.elementToBeClickable(By.xpath("//li[contains(@data-menu-id, 'editColumn')]"))).click();
				WebElement editColumnDialog = driver.findElement(By.xpath("//span[contains(text(),'Confirm')]"));
				// assert edit column dialog display
				Assert.assertEquals(true, editColumnDialog.isDisplayed());
		
				// column to add
				String cField = "# of CPU Cores";
				// customize xpath based on column to add
				String customXpath = "//span[contains(text(),'" + cField + "')]//parent::td//parent::tr[@class='ant-table-row ant-table-row-level-0']//*//input[@type='checkbox']";
				
				// select checkbox of column to add
				driver.findElement(By.xpath(customXpath)).click();
				// confirm column add
				driver.findElement(By.xpath("//span[contains(text(),'Confirm')]")).click();
				
				// assertion - column add
				String verifyAdd = "//span[@class='ant-table-column-title' and text()='" + cField + "']";
				WebElement customField = driver.findElement(By.xpath(verifyAdd));
				Assert.assertEquals(true, customField.isDisplayed());
				
				System.out.println("Column :: " + cField + " :: Successfully Added");
				
				// navigate to edi column dialog to uncheck/remove custom field
				driver.findElement(By.xpath("//button[@class='ant-btn ant-btn-primary ant-btn-icon-only ant-dropdown-trigger']")).click();
				webDriverWait.until(ExpectedConditions.elementToBeClickable(By.xpath("//li[contains(@data-menu-id, 'editColumn')]"))).click();
				
				String delFieldXpath = "//span[contains(text(),'" + cField + "')]//parent::td//parent::tr[contains(@class,'ant-table-row ant-table-row-level-0')]//*//input[@type='checkbox']";
				driver.findElement(By.xpath(delFieldXpath)).click();
				// click confirm to delete
				driver.findElement(By.xpath("//span[contains(text(),'Confirm')]")).click();
				
				// assertion column deletion
				String dCField = "//span[@class='ant-table-column-title' and text()='" + cField + "']";
				boolean status = driver.findElements(By.xpath(dCField)).isEmpty();
				Assert.assertEquals(true, status);
				System.out.println("Column :: " + cField + " :: Successfully Unchekced");
			
			System.out.println("END - Edit Column #########################");	
			
				CommonUtils.sleep(3000);
			
			System.out.println("START - Export Result #########################");
				
				// navigate to export fields dialog
				driver.findElement(By.xpath("//button[@class='ant-btn ant-btn-primary ant-btn-icon-only ant-dropdown-trigger']")).click();
				webDriverWait.until(ExpectedConditions.elementToBeClickable(By.xpath("//li[contains(@data-menu-id, 'exportResult')]"))).click();
				boolean exportResultDialog = driver.findElement(By.xpath("//span[contains(text(),'Confirm')]")).isDisplayed();
				System.out.println("export result dialog status :: " + exportResultDialog);
				// assert dialog display														
				Assert.assertEquals(true, exportResultDialog);
				
				// click confirm to export
				driver.findElement(By.xpath("//span[contains(text(),'Confirm')]")).click();
				
				// file download assertion
				Assert.assertTrue(CommonUtils.isFileDownloaded("/downloads/", "query_export"), "Failed to download qurey result");
			
			System.out.println("END - Export Result #########################");
			
			
			System.out.println("START - Custom Field Manager #########################");
				
				// navgiate to custom field management
				driver.findElement(By.xpath("//button[@class='ant-btn ant-btn-primary ant-btn-icon-only ant-dropdown-trigger']")).click();
				webDriverWait.until(ExpectedConditions.elementToBeClickable(By.xpath("//li[contains(@data-menu-id, 'customFieldManager')]"))).click();
				WebElement cfmDialog = driver.findElement(By.xpath("//div[@class='ant-modal-title' and text()='Custom Field Management']"));
			
				// assert dialog display														
				Assert.assertEquals(true, cfmDialog.isDisplayed());
				
				// click to add new field
				driver.findElement(By.xpath("//button[@type='button']//span[text()='Add new field']")).click();
				//div[@class='ant-modal-title' and text()='Add Custom Field']
				webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='ant-modal-title' and text()='Add Custom Field']")));
				
				// field name to add
				String cfName = CommonUtils.randomAlphaString(9);
				// input field name to add
				driver.findElement(By.xpath("//input[@class='ant-input' and @id='name']")).sendKeys(cfName);
				// confirm field name add
				driver.findElement(By.xpath("//button[@type='button']//span[text()='Confirm']")).click();
			
				// customize xpath of field name added
				String cfNameXpath = "//tr[@data-row-key='" + cfName + "']";
			
				webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(cfNameXpath)));
			
				//WebElement cfmAdd = driver.findElement(By.xpath("//tr[@data-row-key='TestCustomField']"));
				WebElement cfmAdd = driver.findElement(By.xpath(cfNameXpath));
				// assert field name added
				Assert.assertEquals(true, cfmAdd.isDisplayed());
				
				System.out.println("Custom Field :: " + cfName + " :: Successfully added");
				
				webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@type='button']//span[text()='Add new field']")));
				
				// delete custom field
				driver.findElement(By.xpath("//a[contains(text(),'delete')]")).click();	
				webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'Do you want to delete this record?')]")));
				driver.findElement(By.xpath("//button[@type='button']//span[text()='OK']")).click();
				
				// assert field name added
				webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='ant-empty-image']")));
				WebElement cfmRemove= driver.findElement(By.xpath("//div[@class='ant-empty-image']"));
				Assert.assertEquals(true, cfmRemove.isDisplayed());
				
				// close dialog
				driver.findElement(By.xpath("//button[@class='ant-modal-close']")).click();
				
				// assert explore page navigation
				boolean exStatus = driver.findElement(By.xpath("(//span[@class='ant-select-selection-item'])[2]")).isDisplayed();
				Assert.assertEquals(true, exStatus);
		}
	
		@AfterMethod
		public void tearDown() {
			
			// logout and validate
			boolean lStatus = homePage.logOff();
			Assert.assertTrue(lStatus);
			
			driver.quit();
		}
	
}
