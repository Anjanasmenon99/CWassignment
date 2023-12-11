package Testproj.Testcomponents;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import com.google.gson.Gson;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseTest {
	public WebDriver driver;

	@BeforeMethod
	public void urlLoading() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.manage().window().maximize();
		driver.get("https://testpages.herokuapp.com/styled/tag/dynamic-table.html");

	}

	//This method wil call JSON from the below location and return in string 
	public String getJSONdataToMap() throws IOException {
		String jsonContent = FileUtils.readFileToString(
				new File(System.getProperty("user.dir") + "\\src\\test\\java\\Testproj\\Data\\Tabledata.json"),
				StandardCharsets.UTF_8);

		return jsonContent;
	}
	
	//This method will get the data from table and save in JSON format by using loop
    public String getJSONdatafromRefreshTable() {

		WebElement table = driver.findElement(By.id("dynamictable"));

		List<String> headers = table.findElements(By.tagName("th")).stream().map(element -> element.getText())
				.collect(Collectors.toList());
		System.out.println(headers);

		List<WebElement> rows = table.findElements(By.tagName("tr"));

		List<Map<String, String>> tableData = new ArrayList<>();

		for (int i = 1; i < rows.size(); i++) {
			WebElement row = rows.get(i);
			List<WebElement> cells = row.findElements(By.tagName("td"));

			Map<String, String> rowData = new HashMap<>();

			for (int j = 0; j < cells.size(); j++) {
				String cellValue = cells.get(j).getText();
				rowData.put(headers.get(j), cellValue);
			}

			tableData.add(rowData);
		}
		System.out.println(tableData.toString());

		Gson gson = new Gson();
		String jsonData = gson.toJson(tableData);

		System.out.println(jsonData);
		return jsonData;
	}

	@AfterMethod()
	public void urlclosing() {
		driver.close();
	}

}
