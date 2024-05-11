package Demo;

import java.io.IOException;
import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;



public class orangeHrm {

	public String baseUrl = "http://localhost/orangehrm/web/index.php/auth/login";
	public WebDriver driver ; 

	@BeforeTest
	public void setup()
	{
		System.out.println("Before Test executed");
		// TODO Auto-generated method stub
		driver=new ChromeDriver();

		//maximise windows
		driver.manage().window().maximize();

		//open url
		driver.get(baseUrl);

		//timer i kept as 60 you can keep 40
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60)); //60 seconds
	}
	
	
	@Test(priority = 2)
	public void login()
	{
		//admin
		driver.findElement(By.xpath("//input[@placeholder='Username']")).sendKeys("admin");

		// Admin@1234
		driver.findElement(By.xpath("//input[@placeholder='Password']")).sendKeys("Admin@1234");

		//login button click
		driver.findElement(By.xpath("//button[@type='submit']")).submit();


		// Verify if the login was successful by checking the page title
		String pageTitle = driver.getTitle();

			if (pageTitle.equals("OrangeHRM")) {
			System.out.println("Login successful!");
		} else {
			System.out.println("Login failed!");
		}
	
	}
	
	
	
	//**
	@Test(priority = 1,  enabled =false)
	public void Invalidlogin() throws InterruptedException 
	{

		driver.findElement(By.xpath("//input[@placeholder='Username']")).sendKeys("admin");
		driver.findElement(By.xpath("//input[@placeholder='Password']")).sendKeys("1234");//wrong password
		driver.findElement(By.xpath("//button[@type='submit']")).submit();

		String message_expected = "Invalid credentials";

		String message_actual = driver.findElement(By.xpath("//p[@class='oxd-text oxd-text--p oxd-alert-content-text']")).getText();

		Assert.assertTrue(message_actual.contains(message_expected));
		
		Thread.sleep(3000);
	}
	

	
	@Test(priority= 3, enabled =false)	
	public void imageAdd() throws IOException, InterruptedException
	{
		
	 
		driver.findElement(By.xpath("//span[normalize-space()='My Info']")).click();
		driver.findElement(By.xpath("//div[@class='orangehrm-edit-employee-image']")).click();
		driver.findElement(By.xpath("//div[@class='oxd-file-div oxd-file-div--active']")).click();
		
		
		
		Runtime.getRuntime().exec("E:/Testing/SeleniumOrangehrm/imageAdd.exe");
		
		Thread.sleep(3000);
		
		//click on upload button
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		Thread.sleep(3000);
		
	}
		
	
	@Test(priority =4, enabled =false)
	public void addEmployee() throws InterruptedException, IOException
	{
		
		//PIM Menu
		driver.findElement(By.xpath("//span[text()='PIM']")).click();

		//Add employe
		driver.findElement(By.xpath("//a[text()='Add Employee']")).click();
		Thread.sleep(500);
		// first name
		driver.findElement(By.xpath("//input[@placeholder='First Name']")).sendKeys("Imran");

		// last name
		driver.findElement(By.xpath(" //input[@placeholder='Last Name']")).sendKeys("Khan");
		Thread.sleep(1000);
		
		driver.findElement(By.xpath("//button[normalize-space()='Save']")).click();

		// Verify if the employee person de
		String confirmationMessage = driver.findElement(By.xpath("//h6[normalize-space()='Personal Details']")).getText();


		if (confirmationMessage.contains("Personal Details")) {
			System.out.println("Employee added successfully!");
		} else {
			System.out.println("Failed to add employee!");
		}
	}
	
	
	
	
	@Test(priority=5, enabled =false)
	public void searchEmployeeName() throws InterruptedException
	{

		//find PIM Menu and click on PIM Menu
		driver.findElement(By.xpath("//span[text()='PIM']")).click();

		//Select Employee List
		driver.findElement(By.xpath("//a[normalize-space()='Employee List']")).click();

		driver.findElements(By.tagName("input")).get(1).sendKeys("Tom");

		//Click the search button.
		driver.findElement(By.xpath("//button[normalize-space()='Search']")).click();

	   //span[@class='oxd-text oxd-text--span']
		Thread.sleep(5000)	;
		List<WebElement> element=	driver.findElements(By.xpath("//span[@class='oxd-text oxd-text--span']"));

		String expected_message = "Record Found";
		String message_actual = element.get(0).getText();
		System.out.println(message_actual);

	

	Assert.assertTrue(message_actual.contains(expected_message));


	}
	
	
	
	


	public void logOut() throws InterruptedException 
	{
		driver.findElement(By.xpath("//p[@class='oxd-userdropdown-name']")).click();
		//driver.findElement(By.xpath("//a[normalize-space()='Logout']")).click(); //logout but not show element
	
		List <WebElement> elementlist = driver.findElements(By.xpath("//a[@class='oxd-userdropdown-link']"));
		for (int i=0; i<elementlist.size(); i++)
		{
			Thread.sleep(2000);
			System.out.println(i + ":" + elementlist.get(i).getText());

		}
		
		elementlist.get(3).click();//click then logout
	}
	
	
	@Test(priority=6, enabled=false)
	public void deleteEmployee() throws InterruptedException
	{
		

		//find PIM Menu and click on PIM Menu
		driver.findElement(By.xpath("//span[text()='PIM']")).click();

		//Select Employee List
		driver.findElement(By.xpath("//a[text()='Employee List']")).click();

		//enter employee name
		driver.findElements(By.tagName("input")).get(1).sendKeys("Ratul");

		


		//Click the search button.
		driver.findElement(By.xpath("//button[normalize-space()='Search']")).click();


		Thread.sleep(3000);
		///////////////////Delete/////////////////////////

		//click on delete icon of the record
		driver.findElement(By.xpath("//i[@class='oxd-icon bi-trash']")).click();


		//click on yes, delete messaage button
		driver.findElement(By.xpath("//button[@class='oxd-button oxd-button--medium oxd-button--label-danger orangehrm-button-margin']")).click();

		//check for message "No Record Found"
		String msg = driver.findElement(By.xpath("(//span[@class='oxd-text oxd-text--span'])[1]")).getText();

		Assert.assertEquals(msg, "No Records Found");

		Thread.sleep(5000);
		

	}
	
	
	
	@Test(priority=7, enabled=false)
	public void ListEmployees() throws InterruptedException
	{
		
		//find PIM Menu and click on PIM Menu
		driver.findElement(By.xpath("//span[text()='PIM']")).click();

		//Select Employee List
		driver.findElement(By.xpath("//a[normalize-space()='Employee List']")).click();
		Thread.sleep(1000);

		//find total links
		List<WebElement> totalLinksElements = driver.findElements(By.xpath("//ul[@class='oxd-pagination__ul']/li"));

		int totalLinks = totalLinksElements.size();

		for (int i=0; i<totalLinks; i++ )//0,1,2,3,
		{

			try
			{
				String currentLinkText = totalLinksElements.get(i).getText();


				int page = Integer.parseInt(currentLinkText);
				System.out.println("Page: " + page);

				totalLinksElements.get(i).click();

				Thread.sleep(500);

				List <WebElement> emp_list = driver.findElements(By.xpath("//div[@class='oxd-table-card']/div /div[4]"));

				for(int j=0; j<emp_list.size();j++)
				{
					//print last name of each row 
					String lastName = emp_list.get(j).getText();
					System.out.println(lastName);
				}
			}
			catch(Exception e)
			{
				System.out.println("Not a number.");
			}


		}

		
		
	}
	
	@Test(priority=8, enabled=true)
	public void applyLeave() throws InterruptedException
	{
		//find username and enter username "Admin"
		driver.findElement(By.xpath("//input[@placeholder='Username']")).sendKeys("Admin");

		//find password and enter password admin123
		driver.findElement(By.xpath("//input[@placeholder='Password']")).sendKeys("admin123");

		//login button click
		driver.findElement(By.xpath("//button[@type='submit']")).submit();
		
		
		//click on leave menu
		driver.findElement(By.linkText("Leave")).click();
		
		//click on Apply menu
		driver.findElement(By.linkText("Apply")).click();
		
		//click on leave type drop down
		driver.findElement(By.xpath("//i[@class='oxd-icon bi-caret-down-fill oxd-select-text--arrow']")).click();
		
		driver.findElement(By.xpath("//*[contains(text(),'CAN')]")).click();
		
		//enter from date
		driver.findElement(By.xpath("//div[@class='oxd-date-input']/input")).sendKeys("2024-08-04");
		
		
		//enter comment
		driver.findElement(By.tagName("textarea")).sendKeys("This is my personal leave");
		Thread.sleep(3000);
		
		
		//click on Apply button
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		
		
		Thread.sleep(5000);
		logOut();
		
																												

	}

	
	
	
	@AfterTest
	public void Down() throws InterruptedException
	{
		Thread.sleep(2000);

		logOut();
		
		Thread.sleep(3000);
		driver.close();
		driver.quit(); 

	}


}
	
	
	