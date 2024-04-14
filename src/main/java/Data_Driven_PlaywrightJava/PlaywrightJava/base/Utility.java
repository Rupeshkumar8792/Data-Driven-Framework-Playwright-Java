package Data_Driven_PlaywrightJava.PlaywrightJava.base;

import com.microsoft.playwright.Page;

public class Utility
{
	protected Page page;
	protected static final String USER_DIRECTORY = "D:\\GTPLBank\\";

	public Utility(Page page)
	{
		this.page = page;
	}

	public void takeScreenshotForFailedTestcases(String name)
	{

	}

}
