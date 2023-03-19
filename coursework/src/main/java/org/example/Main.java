package org.example;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.openqa.selenium.edge.EdgeDriver;

public class Main {
    WebDriver driver;

    @BeforeMethod
    public void setup(){
        WebDriverManager.edgedriver().setup();
        driver = new EdgeDriver();
    }

    @Test
    public void test() throws InterruptedException {
        // Open URL
        driver.get("https://magento.softwaretestingboard.com/customer/account/login/");

        // Enter login details
        WebElement email = driver.findElement(By.id("email"));
        email.sendKeys("test.account@gmail.com");
        WebElement password = driver.findElement(By.id("pass"));
        password.sendKeys("Password1234");
        // Click on login button
        WebElement loginButton = driver.findElement(By.id("send2"));
        loginButton.click();

        // Selecting the gear
        WebElement mens = driver.findElement(By.className("nav-4"));
        Actions actions = new Actions(driver);
        actions.moveToElement(mens).perform();
        // Selecting the category
        WebElement bags = driver.findElement(By.id("ui-id-25"));
        actions.moveToElement(bags).perform();
        bags.click();
        // Selecting the item
        WebElement itemBag1 = driver.findElement(By.xpath("//img[@alt='Fusion Backpack']"));
        itemBag1.click();

        //Verifying the item name
        WebElement itemName = driver.findElement(By.xpath("//h1[@class='page-title']"));
        String name = itemName.getText();
        String expectedName = "Fusion Backpack";
        Assert.assertEquals(name, expectedName);
        //Verifying the item price
        WebElement itemPrice1 = driver.findElement(By.xpath("//span[@class='price']"));
        String price1str = itemPrice1.getText().replace("$","");
        Double expectedPrice1 = 59.00;
        Assert.assertEquals(Double.parseDouble(price1str), expectedPrice1);
        System.out.println("Item name " + name + " and price " + price1str + " are correct");
        // Adding the item to the cart
        WebElement addToCart1 = driver.findElement(By.xpath("//button[@title='Add to Cart']"));
        addToCart1.click();

        //Go back to the products page
        driver.navigate().back();

        // Adding the item2
        WebElement itemBag2 = driver.findElement(By.xpath("//img[@alt='Push It Messenger Bag']"));
        itemBag2.click();
        //Getting the item price to use later
        WebElement itemPrice2 = driver.findElement(By.xpath("//span[@class='price']"));
        String price2str = itemPrice2.getText().replace("$","");
        // Adding the item to the cart
        WebElement addToCart2 = driver.findElement(By.xpath("//button[@title='Add to Cart']"));
        addToCart2.click();

        //Go to the cart
        Thread.sleep(5000); // Wait for the cart to load/update
        WebElement cart = driver.findElement(By.xpath("//a[@class='action showcart']"));
        cart.click();
        WebElement cartAll = driver.findElement(By.className("viewcart"));
        cartAll.click();

        //Verifying the total price
        //WebElement totalPrice = driver.findElement(By.xpath("//span[@class='price']"));
    }
}