import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NegativePhoneFieldTests {
    private WebDriver driver;

    @BeforeAll
    static void setupAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setup() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
        driver.get("http://localhost:9999");
    }

    @AfterEach
    void tearDown() {
        driver.quit();
        driver = null;
    }

    @Test
    void emptyPhoneField() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Степан Васильев");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("[type='button']")).click();
        String expected = "Поле обязательно для заполнения";
        String actual = driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub")).getText().trim();
        assertEquals(expected, actual);
    }

    @Test
    void oneDigitAsPhoneNumber() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Степан Васильев");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("7");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("[type='button']")).click();
        String expected = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        String actual = driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub")).getText().trim();
        assertEquals(expected, actual);
    }

    @Test
    void tenDigitAsPhoneNumber() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Степан Васильев");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("7903123456");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("[type='button']")).click();
        String expected = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        String actual = driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub")).getText().trim();
        assertEquals(expected, actual);
    }

    @Test
    void fullNumberWithoutPlus() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Степан Васильев");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("79031234567");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("[type='button']")).click();
        String expected = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        String actual = driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub")).getText().trim();
        assertEquals(expected, actual);
    }

    @Test
    void fullNumberWithPlusAtTheEnd() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Степан Васильев");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("79031234567+");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("[type='button']")).click();
        String expected = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        String actual = driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub")).getText().trim();
        assertEquals(expected, actual);
    }

    @Test
    void numberWithTwelveDigits() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Степан Васильев");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+790312345671");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("[type='button']")).click();
        String expected = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        String actual = driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub")).getText().trim();
        assertEquals(expected, actual);
    }

    @Test
    void numberWithAlphabetSymbols() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Степан Васильев");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+7903123456A");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("[type='button']")).click();
        String expected = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        String actual = driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub")).getText().trim();
        assertEquals(expected, actual);
    }

    @Test
    void numberWithSpecialSymbols() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Степан Васильев");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+7903123456$");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("[type='button']")).click();
        String expected = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        String actual = driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub")).getText().trim();
        assertEquals(expected, actual);
    }

    @Test
    void numberDividedByDashes() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Степан Васильев");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+7-903-123-45-67");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("[type='button']")).click();
        String expected = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        String actual = driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub")).getText().trim();
        assertEquals(expected, actual);
    }

    @Test
    void numberWithBrackets() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Степан Васильев");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+7(903)1234567");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("[type='button']")).click();
        String expected = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        String actual = driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub")).getText().trim();
        assertEquals(expected, actual);
    }
}