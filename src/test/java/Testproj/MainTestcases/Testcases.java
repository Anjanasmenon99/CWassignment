package Testproj.MainTestcases;

import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import static org.testng.Assert.assertEquals;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.json.JSONException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

import Testproj.Testcomponents.BaseTest;

public class Testcases extends BaseTest {

	// Main testcase
	@Test
	public void runTest() throws InterruptedException, IOException, JSONException {
        //Click table dropdown
		driver.findElement(By.xpath("//summary[contains(.,'Table Data')]")).click();
		//Select whole
		//WebElement textbox = driver.findElement(By.cssSelector("textarea#jsondata"));
		//textbox.clear();
		Thread.sleep(2000);
		String data = getJSONdataToMap();
		//textbox.sendKeys(data);
		driver.findElement(By.tagName("button")).click();

		String outputdata = getJSONdatafromRefreshTable();
		System.out.println(outputdata);
		JSONAssert.assertEquals(outputdata.toString(), data.toString(), JSONCompareMode.NON_EXTENSIBLE);

	}

}
