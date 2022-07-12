package StepDefinition;

import org.junit.Assert;
import com.mega.pages.MegaAppPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class AssignmentTests
{
	public AssignmentTests()
	{
		super();
	}
	
	MegaAppPage megaPage = new MegaAppPage();
	private String urlApplication = "https://mega.nz/";
	private String urlDownload = "https://mega.io/desktop";
	
	
// Test Case 1 - Create a text file a.txt with content mega testing in it.
	@Given("user is logged in and landed on home page")
	public void user_is_logged_in_and_landed_on_home_page()
	{
		megaPage.initialization(urlApplication);
		megaPage.login();
	}

	@When("user creates a text file")
	public void user_creates_a_text_file()
	{
		megaPage.createTextFile();
	}

	@Then("text file must be created")
	public void text_file_must_be_created()
	{
		Assert.assertTrue(megaPage.verifyTextFileExist());
	}

	
// Test Case 2 - Delete the file (a.txt)
	@Given("user is logged in and text file must have been created")
	public void user_is_logged_in_and_text_file_must_have_been_created()
	{
		megaPage.clickOnCloudDrive();
	}

	@When("user deletes a text file")
	public void user_deletes_a_text_file()
	{
		megaPage.deleteTextFile();
	}

	@Then("text file must be deleted")
	public void text_file_must_be_deleted()
	{
		Assert.assertFalse(megaPage.verifyTextFileExist());
	}

	
// Test Case 3 - The text file (a.txt) can be restored from Rubbish Bin
	@Given("user is loged in and text file must have been deleted")
	public void user_is_loged_in_and_text_file_must_have_been_deleted()
	{
		megaPage.clickOnRecycleBin();
	}

	@When("user restores a text file")
	public void user_restores_a_text_file()
	{
		megaPage.restoreTextFile();
	}

	@Then("text file must be restored")
	public void text_file_must_be_restored()
	{
		Assert.assertTrue(megaPage.verifyTextFileExist());
		megaPage.logout();
		megaPage.tearDown();
	}

	
// Test case 4: The links for downloading should be working properly
	@Given("user must be on home page")
	public void user_must_be_on_home_page()
	{
		megaPage.initialization(urlDownload);
	}

	@When("user click on linux file download")
	public void user_click_on_linux_file_download()
	{
		megaPage.downloadLinuxFile();
	}

	@Then("file download links must be working")
	public void file_download_links_must_be_working()
	{
		// Prints the status of links on the console.
		megaPage.verifyDownloadLinksAreWorking();
		megaPage.tearDown();
	}

}
