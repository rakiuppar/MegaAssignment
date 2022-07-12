package com.mega.pages;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

public class MegaAppPage
{
	private String userName = "Enter your Email";
	private String password = "Enter your password";
	private String fileName = "AssignmentFile.txt";
	private String textContent = "Mega Testing";
	private HttpURLConnection huc = null;
	private int respCode = 200;
	WebDriver driver;
	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
	Actions action = new Actions(driver);

	public MegaAppPage()
	{
		try
		{
			PageFactory.initElements(driver, this);
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@FindBy(xpath = "//*[@class='top-buttons']/child::button[2]")
	WebElement loginButton;

	@FindBy(xpath = "//*[@placeholder='Your Email' and @type='text']")
	WebElement userId;

	@FindBy(xpath = "//*[@placeholder='Password']")
	WebElement passwordField;

	@FindBy(xpath = "(//*[contains(text(),'Remember')]/following::button)[1]")
	WebElement loginSubmit;

	@FindBy(xpath = "//*[@title='Create new folder']")
	WebElement createFolderButton;

	@FindBy(xpath = "//span[contains(text(),'New text file')]")
	WebElement createTextFileOption;

	@FindBy(xpath = "//*[@placeholder='File Name']")
	WebElement textFileName;

	@FindBy(xpath = "//*[@placeholder='File Name']/following::button[2]")
	WebElement createTextFileButton;

	@FindBy(xpath = "//*[@title='Save changes']")
	WebElement saveTextfile;

	@FindBy(xpath = "//*[@class='close-btn']")
	WebElement fileCloseIcon;

	@FindBy(xpath = "//*[text()='AssignmentFile.txt']")
	WebElement fileLink;

	@FindBy(xpath = "//*[@class='dropdown-item remove-item']")
	WebElement fileDeleteOption;

	@FindBy(xpath = ".//*[text()='Yes']")
	WebElement deleteConfirmYes;

	@FindBy(xpath = "//*[@data-link='bin']")
	WebElement recycleBin;

	@FindBy(xpath = "//*[text()='AssignmentFile.txt']/following::i")
	WebElement fileOptions;

	@FindBy(xpath = "//*[contains(text(),'Restore')]")
	WebElement restoreOption;

	@FindBy(xpath = "//*[@data-link='clouddrive']")
	WebElement cloudDrive;

	@FindBy(xpath = "//*[@class='topbar-links']//div[@class='dropdown account js-dropdown-account']")
	WebElement userProfileLink;

	@FindBy(xpath = "//*[@class='js-accountbtn logout']")
	WebElement logoutLink;

// Assignment 2 elements	
	@FindBy(xpath = "//*[@data-os='linux']")
	WebElement linuxButton;

	@FindBy(xpath = "//*[@class='mega-input dropdown-input box-style inline megasync-dropdown']")
	WebElement linuxVersionDropdown;

	@FindBy(xpath = "//*[@class='mega-input dropdown-input box-style inline megasync-dropdown']//*[@class='option']")
	List<WebElement> linuxOptions;

	public void initialization(String url)
	{
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.get(url);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}

	public void login()
	{
		loginButton.click();
		userId.clear();
		userId.sendKeys(userName);
		passwordField.clear();
		passwordField.sendKeys(password);
		loginButton.click();
	}

	public void tearDown()
	{
		driver.quit();
	}

	public void logout()
	{
		wait.until(ExpectedConditions.elementToBeClickable(userProfileLink)).click();
		userProfileLink.click();
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", logoutLink);
	}

	public void createTextFile()
	{
		wait.until(ExpectedConditions.elementToBeClickable(createFolderButton));
		action.moveByOffset(400, 400).build().perform();
		action.contextClick().build().perform();
		createTextFileOption.click();
		textFileName.clear();
		textFileName.sendKeys(fileName);
		createTextFileButton.click();
		wait.until(ExpectedConditions.elementToBeClickable(fileCloseIcon));
		driver.switchTo().activeElement().sendKeys(textContent);
		wait.until(ExpectedConditions.elementToBeClickable(saveTextfile)).click();
		fileCloseIcon.click();
	}

	public void deleteTextFile()
	{
		try
		{
			wait.until(ExpectedConditions.elementToBeClickable(fileLink));
			action.contextClick(fileLink).build().perform();
			wait.until(ExpectedConditions.elementToBeClickable(fileDeleteOption)).click();
			deleteConfirmYes.click();
		} catch (Exception e)
		{
		}
	}

	public void restoreTextFile()
	{
		try
		{
			fileLink.click();
			fileOptions.click();
			restoreOption.click();
		} catch (Exception e)
		{
		}
	}

	public void clickOnRecycleBin()
	{
		wait.until(ExpectedConditions.elementToBeClickable(recycleBin)).click();
	}

	public void clickOnCloudDrive()
	{
		wait.until(ExpectedConditions.elementToBeClickable(cloudDrive)).click();
	}

	public boolean verifyTextFileExist()
	{
		return (fileLink.isDisplayed());
	}

	public void downloadLinuxFile()
	{
		linuxButton.click();
		linuxVersionDropdown.click();
	}

	public void verifyDownloadLinksAreWorking()
	{
		List<WebElement> links = linuxOptions;
		Iterator<WebElement> it = links.iterator();
		while (it.hasNext())
		{
			String link = it.next().getAttribute("data-link");
			if (link == null || link.isEmpty())
			{
				System.out.println("URL is either not configured for anchor tag or it is empty");
				continue;
			}
			if (!link.startsWith(link))
			{
				System.out.println("URL belongs to another domain, skipping it.");
				continue;
			}
			try
			{
				huc = (HttpURLConnection) (new URL(link).openConnection());
				huc.setRequestMethod("HEAD");
				huc.connect();
				respCode = huc.getResponseCode();
				if (respCode >= 400)
				{
					System.out.println(link + " is a broken link");
				} else
				{
					System.out.println(link + " is a valid link");
				}
			} catch (MalformedURLException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}
