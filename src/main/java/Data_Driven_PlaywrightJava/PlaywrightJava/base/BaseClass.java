package Data_Driven_PlaywrightJava.PlaywrightJava.base;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.nio.file.Paths;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.Tracing;
import com.microsoft.playwright.options.WaitUntilState;

import Data_Driven_PlaywrightJava.PlaywrightJava.propertyReader.PropertyReader;

public class BaseClass 
{
	protected static Playwright playwright;
	protected static BrowserType browserType;
	protected static Browser browser;
	protected static BrowserContext context;
	protected static Page page;
    protected static String browserName;
	protected static String url;
	protected static String headless;
	
	PropertyReader prop;
	
	@BeforeMethod(alwaysRun = true)
	public void setUp()
	{
		//calling static method
		launchPlaywright();
		launchURL();
	}
	
	public static void launchPlaywright()
	{
		//finding the size of the window using dimension class
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		
		//finding the width of screen 
		int width = (int) screenSize.getWidth();
		
		//finding the height of screen
		int height = (int) screenSize.getHeight();
		
		//Launches new Playwright driver process and connects to it
		playwright = Playwright.create();
		
		if (browserName.equalsIgnoreCase("chrome")) 
		{
			//BrowserType provides methods to launch a chrome browser
			browserType = playwright.chromium();
		}
		else if(browserName.equalsIgnoreCase("firefox"))
		{
			////BrowserType provides methods to launch a firefox browser
			browserType = playwright.firefox();
		}
		else if(browserName.equalsIgnoreCase("webkit"))
		{
			////BrowserType provides methods to launch a webkit or safari browser
			browserType = playwright.webkit();
		}
		
		/*A Browser is created via BrowserType.launch() and setheadless method used to open browser*/		
		browser = browserType.launch(new BrowserType.LaunchOptions().setChannel(browserName).setHeadless(false));
		
		/*setIgnoreHTTPSErrors used to run website when url is not built on https and setViewportSize to set the window dimension*/
		Browser.NewContextOptions browserContextOptions = new Browser.NewContextOptions().setIgnoreHTTPSErrors(true).setViewportSize(width, height);	
		context = browser.newContext(browserContextOptions);
		
		//capturing all flow screenshot
		context.tracing().start(new Tracing.StartOptions().setScreenshots(true));
		
		//Page provides methods to interact with a single tab in a Browser,
		page = context.newPage();
			
	}
	
	public static void launchURL()
	{
		//to launch the url
		page.navigate(url, new Page.NavigateOptions().setWaitUntil(WaitUntilState.NETWORKIDLE));
	}
	
	@AfterMethod()
	public void browserClose(ITestResult result)
	{
		if (ITestResult.FAILURE == result.getStatus()) 
		{
			Utility lUtility = new Utility(page);
			lUtility.takeScreenshotForFailedTestcases(result.getName());
		}
		closePlaywright();
	}
	
	public void closePlaywright()
	{
		page.close();
		
	    //Stopping screenshot capturing for flows
		context.tracing().stop(new Tracing.StopOptions().setPath(Paths.get("trace.zip")));
		browser.close();
		playwright.close();
		
	}
}
