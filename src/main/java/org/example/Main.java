package org.example;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;
import java.util.ArrayList;

public class Main {
    /**
     * 2. navigate().to - переход на страницу
     *    driver.navigate().to("https://next.privat24.ua/money-transfer/card");
     *  3. back - переход назад
     *    driver.navigate().back();
     *  4. forward - переход вперед
     *     driver.navigate().forward();
     *  5. refresh - обновление странциы
     *     driver.navigate().refresh();
     *  7. остановить driver и все окна в браузере.
     *     driver.quit();
     */

    // TEST DATA
    String BASE_URL = "https://next.privat24.ua/money-transfer/card";
    String cardFromExample = "4004159115449011";

    WebDriver driver = new ChromeDriver();

    // UI ELEMENTS
    By cardNumberFrom = By.xpath(".//input[@data-qa-node='numberdebitSource']");
    By expDate = By.xpath(".//input[@data-qa-node='expiredebitSource']");
    By cvv = By.xpath(".//input[@data-qa-node='cvvdebitSource']");
    By nameFrom = By.xpath(".//input[@data-qa-node='firstNamedebitSource']");
    By surnameFrom = By.xpath(".//input[@data-qa-node='lastNamedebitSource']");
    By cardTo = By.xpath(".//input[@data-qa-node='numberreceiver']");
    By nameTo = By.xpath(".//input[@data-qa-node='firstNamereceiver']");
    By surnameTo = By.xpath(".//input[@data-qa-node='lastNamereceiver']");
    By amount = By.xpath(".//input[@data-qa-node='amount']");
    By toggleComment = By.xpath(".//span[@data-qa-node='toggle-comment']");
    By comment = By.xpath(".//textarea[@data-qa-node='comment']");
    By btnAddToBasket = By.xpath(".//button[@type='submit']");
    By termsLink = By.xpath(".//a[@href='https://privatbank.ua/terms']");

    @Test
    void checkAddToBasketMinPaymentSum() {
        //pre-condition: ожидать отображение перечисленных элементов ниже
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));

        driver.get(BASE_URL);
        driver.findElement(cardNumberFrom).sendKeys(cardFromExample);
        driver.findElement(expDate).sendKeys("0244");
        driver.findElement(cvv).sendKeys("192");
        driver.findElement(nameFrom).sendKeys("Vasul");
        driver.findElement(surnameFrom).sendKeys("Davudenko");
        driver.findElement(cardTo).sendKeys("5309233034765085");
        driver.findElement(nameTo).sendKeys("Maksym");
        driver.findElement(surnameTo).sendKeys("Ivanenko");
        driver.findElement(amount).sendKeys("300");
        driver.findElement(toggleComment).click();
        driver.findElement(comment).sendKeys("COMMENT");
        driver.findElement(btnAddToBasket).submit(); // только если это tag form.

        Assertions.assertEquals(cardFromExample, driver.findElement(By.xpath(".//span[@data-qa-node='payer-card']")).getText());

    }

    @Test
    void checkSwitchToNewWindow() {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
        driver.manage().window().maximize(); //

        driver.get(BASE_URL);
        driver.findElement(termsLink).click();
        // костыль - как перейти с одного окна на другое, если ссылка открылась в новом окне
        driver.switchTo().window(new ArrayList<>(driver.getWindowHandles()).get(driver.getWindowHandles().size() - 1));

        Assertions.assertEquals("https://privatbank.ua/terms", driver.getCurrentUrl());
        Assertions.assertEquals("Умови та правила", driver.getTitle());


    }
}