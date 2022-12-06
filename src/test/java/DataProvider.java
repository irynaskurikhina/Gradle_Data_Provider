import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class DataProvider {

         private WebDriver driver;

        @org.testng.annotations.DataProvider(name = "dp")
        public Object[][] parseData() {
            return new Object[][] {
                    {"https://www.google.com"},
                    {"https://duckduckgo.com"}
            };
        }

        @BeforeMethod
        public void setUp() {
             System.out.println("Set up");
             WebDriverManager.chromedriver().setup();
              driver = new ChromeDriver();
        }

        @Test(dataProvider = "dp")
        public void test(String searchUrl) throws InterruptedException {
            driver = new ChromeDriver();
            driver.get(searchUrl);

            try {
                WebElement btnContinue = driver.findElement(By.xpath("//*[@class='WrNOuc']"));
                while (btnContinue.isDisplayed()) {
                    btnContinue.click();
                    Thread.sleep(500);
                }
            } catch (Exception ex) {
                System.out.println("element not found");
            }

            if (searchUrl.contains("google")) {
                try {
                    WebElement btnAccept = driver.findElement(By.xpath("//*[@id='L2AGLb']"));
                    Thread.sleep(2_000);
                    btnAccept.click();
                } catch (Exception ex) {
                    System.out.println("element not found");
                }

            }

            WebElement etSearchField = driver.findElement(By.xpath(".//*[@name='q']"));
            etSearchField.sendKeys("Selenium");
            etSearchField.submit();
            Thread.sleep(2_000);
            System.out.println("Page title is " + driver.getTitle());
            Thread.sleep(2_000);
        }

        @AfterTest
        public void tearDown() {
            driver.quit();
        }
    }


