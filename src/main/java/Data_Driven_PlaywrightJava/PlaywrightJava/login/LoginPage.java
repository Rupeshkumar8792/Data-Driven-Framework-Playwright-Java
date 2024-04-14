package Data_Driven_PlaywrightJava.PlaywrightJava.login;

import java.util.Properties;

import com.microsoft.playwright.Page;

import Data_Driven_PlaywrightJava.PlaywrightJava.propertyReader.PropertyReader;

public class LoginPage 
{
	Page page;
	Properties prop = PropertyReader.intializeProperties();
	
	// stage 1 : declaring the webElement
		String gUsernameLocator = prop.getProperty("usernameLocator");
		String gPasswordLocator = prop.getProperty("passwordLocator");
		String gLoginBtnLocator = prop.getProperty("loginBTNLocator");
		
	public LoginPage(Page page)
	{
		// this keyword is used to call the present class constructor
		// this.page = page - passing session page instance to the login page
		this.page = page;
	}
	
	public void enterUsername() 
	{
		page.locator(gLoginBtnLocator).fill("");
	}
	
	public void enterPassword()
	{
		page.locator(gPasswordLocator).fill("");
	}
	
	public void clickOnLoginButton()
	{
		page.locator(gLoginBtnLocator).click();
	}
	
}
