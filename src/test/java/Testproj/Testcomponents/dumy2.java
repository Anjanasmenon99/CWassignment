package Testproj.Testcomponents;

import com.google.gson.Gson;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class dumy2 {

    public static void main(String[] args) {
    	System.setProperty("webdriver.chrome.driver", "C:/Users/Admin/Documents/chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get("https://testpages.herokuapp.com/styled/tag/dynamic-table.html");
      
        WebElement table = driver.findElement(By.id("dynamictable")); // Table ID is 'dynamictable'

        List<WebElement> headerElements = table.findElements(By.tagName("th"));
        List<String> headers = headerElements.stream().map(WebElement::getText).collect(Collectors.toList());

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

        
        Gson gson = new Gson();
        String jsonData = gson.toJson(tableData);

        System.out.println("Table data in JSON format:");
        System.out.println(jsonData);

        // Close the browser
        driver.quit();
    }
}
