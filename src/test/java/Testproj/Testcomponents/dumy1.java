package Testproj.Testcomponents;

import com.google.gson.Gson;

import io.github.bonigarcia.wdm.WebDriverManager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class dumy1 {

    public static void main(String[] args) throws InterruptedException {
        // Provide the path to your ChromeDriver executable
    	WebDriverManager.chromedriver().setup();
		WebDriver driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.manage().window().maximize();
		driver.get("https://testpages.herokuapp.com/styled/tag/dynamic-table.html");

        
        WebElement table = driver.findElement(By.id("dynamictable")); 
        String jsoninputData = "[{\"name\" : \"Bob\", \"age\" : 20, \"gender\": \"male\"}, {\"name\": \"George\", \"age\" : 42, \"gender\": \"male\"}, {\"name\":\r\n"
				+ "\"Sara\", \"age\" : 42, \"gender\": \"female\"}, {\"name\": \"Conor\", \"age\" : 40, \"gender\": \"male\"}, {\"name\":\r\n"
				+ "\"Jennifer\", \"age\" : 42, \"gender\": \"female\"}]";
		driver.findElement(By.xpath("//summary[contains(.,'Table Data')]")).click();
		WebElement textbox= driver.findElement(By.cssSelector("textarea#jsondata"));
		textbox.clear();
		Thread.sleep(1000);
		textbox.sendKeys(jsoninputData);
		driver.findElement(By.tagName("button")).click();

       
        List<String> headers = table.findElements(By.tagName("th"))
                .stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());

        
        List<Map<String, String>> tableData = table.findElements(By.tagName("tr"))
                .stream()
                .skip(1) 
                .map(row -> row.findElements(By.tagName("td"))
                        .stream()
                        .map(WebElement::getText)
                        .collect(Collectors.toList()))
                .map(cells -> {
                    Map<String, String> rowData = headers.stream()
                            .collect(Collectors.toMap(header -> header, header -> cells.get(headers.indexOf(header))));
                    return rowData;
                })
                .collect(Collectors.toList());

        
        Gson gson = new Gson();
        String jsonData = gson.toJson(tableData);

        // Print the JSON data
        System.out.println("Table data in JSON format:");
        System.out.println(jsonData);

        // Close the browser
        driver.quit();
    }
}

