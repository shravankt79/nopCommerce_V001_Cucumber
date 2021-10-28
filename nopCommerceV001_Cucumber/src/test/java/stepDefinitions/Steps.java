package stepDefinitions;


import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import pageObjects.AddcustomerPage;
import pageObjects.LoginPage;
import pageObjects.SearchCustomerPage;

public class Steps extends BaseClass 
{
    @Before
	public void setup() throws IOException
	{
    	logger=Logger.getLogger("nopCommerce"); //Added logger
        PropertyConfigurator.configure("Log4j.properties"); // Added logger
    	// Reading Properties
    	configProp=new Properties();
    	FileInputStream configPropefile=new FileInputStream("config.properties");
    	configProp.load(configPropefile);
    	    
        String br=configProp.getProperty("browser");
        
        if(br.equals("chrome"))
        {
        System.setProperty("webdriver.chrome.driver",configProp.getProperty("chromepath"));
        //System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir")+ "//Drivers/chromedriver.exe"); // chrome driver from Drivers folder
        //System.setProperty("webdriver.chrome.driver","C:\\Users\\shrav\\selenium-3.141.59\\chromedriver.exe"); // chrome driver path from exact located path
        driver=new ChromeDriver();
	    }
        else if(br.equals("firefox"))
        {
        System.setProperty("webdriver.gecko.driver",configProp.getProperty("firefoxpath"));
        driver=new FirefoxDriver();
        }
        else if(br.equals("edge"))
        {
        System.setProperty("webdriver.edge.driver",configProp.getProperty("edgepath"));
        driver=new EdgeDriver();
        }
    	
        logger.info("*******Launching browser********"); 
	}
	
    @Given("User Launch Chrome Browser")
    public void user_Launch_Chrome_Browser() {
    
    lp=new LoginPage(driver);
   }

   @When("User opens URL {string}")
   public void user_opens_URL(String url) {
   logger.info("*******Opening URL********");
   driver.get(url);
   driver.manage().window().maximize();
	     
   }

   @When("User enters Email as {string} and Password as {string}")
   public void user_enters_Email_as_and_Password_as(String email, String password) {
   logger.info("*******Providing login details********");
   lp.setUserName(email);	   
   lp.setPassword(password);
   
   }

   @When("Click on Login")
   public void click_on_Login() throws InterruptedException {
   logger.info("*******Started login********");
   lp.clickLogin();
   Thread.sleep(3000);
   }

   @Then("Page Title should be {string}")
   public void page_Title_should_be(String title) throws InterruptedException {
    
	   if (driver.getPageSource().contains("Login was unsuccessful."))  {
	   driver.close(); // only closes the focused window.
	   logger.info("*******Login Passed********");
	   Assert.assertTrue(false);
   } else {
	   logger.info("*******Login failed********");
	   Assert.assertEquals(title, driver.getTitle());
   } 
	   Thread.sleep(3000);
   }

   @When("User click on Log out Link")
   public void user_click_on_Log_out_Link() throws InterruptedException {
	   logger.info("*******Click on logout link*******");
	   lp.clickLogout();
	   Thread.sleep(3000);
   }

   @Then("close browser")
   public void close_browser() {
   logger.info("*******Closing browser********");	   
   driver.quit();	   // exits the browser ,end the session,tabs and pop-ups etc
    
    }
   
   //Customers feature step definitions...............
   
   @Then("User can view Dashboard")
   public void user_can_view_Dashboard() {
	   addCust=new AddcustomerPage(driver);
	   Assert.assertEquals("Dashboard / nopCommerce administration", addCust.getPageTitle());      
   }

   @When("User click on Customers Menu")
   public void user_click_on_Customers_Menu() throws InterruptedException {
       Thread.sleep(3000); 
	   addCust.clickOnCustomersMenu();
   }

   @When("Click on customers Menu Item")
   public void click_on_customers_Menu_Item() throws InterruptedException {
	   Thread.sleep(2000);
        addCust.clickOnCustomersMenuItem();
   }

   @When("Click on Add new button")
   public void click_on_Add_new_button() throws InterruptedException {
	   addCust.clickOnAddnew();
	   Thread.sleep(2000);
        
   }

   @Then("User can view add new cutomer page")
   public void user_can_view_add_new_cutomer_page() {
   Assert.assertEquals("Add a new customer / nopCommerce administration",addCust.getPageTitle());        
   }

   @When("User enter customer info")
   public void user_enter_customer_information() throws InterruptedException {
	   logger.info("*******Adding new customer********");
	   logger.info("*******Providing customer details********");
	   String email=randomestring()+"@gmail.com";
	   addCust.setEmail(email);
	   addCust.setPassword("test123");
	   //Registered - default
	   //The customer cannot be in both'Guests' and 'Registered' customer roles
       //Add the customer to 'Guests' or 'Registered' customer role
	   addCust.setCustomerRoles("Guests");
	   Thread.sleep(3000);
	   
	   addCust.setManagerOfVendor("Vendor 2");
	   addCust.setGender("Male");
	   addCust.setFirstName("Shravan");
	   addCust.setLastName("Kumar");
	   addCust.setDob("08/05/1979");  //Format: D/MM/YY
	   addCust.setCompanyName("busyQA");
	   addCust.setAdminContent("This is for testing");
        	   
   }

   @When("click on Save button")
   public void click_on_Save_button() throws InterruptedException {
	   logger.info("*******Saving customer data********");
	   addCust.clickOnSave();
	   Thread.sleep(2000);
        
   }

   @Then("User can view Confirmation message {string}")
   public void user_can_view_Confirmation_message(String msg) {
	   Assert.assertTrue(driver.findElement(By.tagName("body")).getText()
			   .contains("The new customer has been added successfully"));
        
   }
     
     //steps for Searching a Customer using Email ID
   
   @When("Enter Customer Email")
   public void enter_Customer_Email() {
	    logger.info("*******Searching a customer by email id********");
        searchCust=new SearchCustomerPage(driver);
        searchCust.setEmail("victoria_victoria@nopCommerce.com");
   }

   @When("Click on Search Button")
   public void click_on_Search_Button() throws InterruptedException {
	   searchCust.clickSearch();
	   Thread.sleep(3000);
        
   }

   @Then("User should found Email in the Search table")
   public void user_should_found_Email_in_the_Search_table() {
    
	   boolean status=searchCust.searchCustomerByEmail("victoria_victoria@nopCommerce.com");
	   Assert.assertEquals(true, status);
   }

   //steps for searching a customer by using First Name and Last Name
   @When("Enter Customer FirstName")
   public void enter_Customer_FirstName() {
	    logger.info("*******Searching customer by Name********");
        searchCust=new SearchCustomerPage(driver);
        searchCust.setFirstName("Victoria");
   }

   @When("Enrer Customer LastName")
   public void enrer_Customer_LastName() {
    
	   searchCust=new SearchCustomerPage(driver);
       searchCust.setLastName("Terces");
   }

   @Then("User should found Name in the Search table")
   public void user_should_found_Name_in_the_Search_table() {
	  boolean status=searchCust.searchCustomerByName("Victoria Terces");
      Assert.assertEquals(true, status);
   }
   


}

