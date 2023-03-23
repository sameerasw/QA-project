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
        WebElement itemBag1 = driver.findElement(By.xpath("//img[@alt='Impulse Duffle']"));
        itemBag1.click();

        //Verifying the item name
        Thread.sleep(2000); // Wait for the button to be available
        WebElement itemName = driver.findElement(By.xpath("//h1[@class='page-title']"));
        String name = itemName.getText();
        String expectedName = "Impulse Duffle";
        Assert.assertEquals(name, expectedName);
        //Verifying the item price
        WebElement itemPrice1 = driver.findElement(By.xpath("//span[@class='price']"));
        String price1str = itemPrice1.getText().replace("$","");
        Double expectedPrice1 = 74.00;
        Assert.assertEquals(Double.parseDouble(price1str), expectedPrice1);
        System.out.println("Item name " + name + " and price " + price1str + " are correct");
        // Adding the item to the cart
        Thread.sleep(2000); // Wait for the button to be available
        WebElement addToCart1 = driver.findElement(By.xpath("//button[@title='Add to Cart']"));
        addToCart1.click();

        //Go back to the products page
        driver.navigate().back();

        // Adding the item2
        WebElement itemBag2 = driver.findElement(By.xpath("//img[@alt='Push It Messenger Bag']"));
        itemBag2.click();
        Thread.sleep(2000); // Wait for the button to be available
        //Getting the item price to use later
        WebElement itemPrice2 = driver.findElement(By.xpath("//span[@class='price']"));
        String price2str = itemPrice2.getText().replace("$","");
        // Adding the item to the cart
        Thread.sleep(2000); // Wait for the add to cart button to be available
        WebElement addToCart2 = driver.findElement(By.xpath("//button[@title='Add to Cart']"));
        addToCart2.click();

        //Go to the cart
        Thread.sleep(5000); // Wait for the cart to update
        WebElement cart = driver.findElement(By.xpath("//a[@class='action showcart']"));
        cart.click();
        WebElement cartAll = driver.findElement(By.className("viewcart"));
        cartAll.click();

        //Verifying the total price
        Thread.sleep(5000); // Wait for the cart to load/update
        WebElement totalPrice = driver.findElement(By.xpath("//td[@data-th='Order Total']"));
        WebElement totalPriceStr = totalPrice.findElement(By.className("price"));
        String totalPriceStrMod = totalPriceStr.getText().replace("$","");
        Double expectedTotalPrice = Double.parseDouble(price1str) + Double.parseDouble(price2str);
        Assert.assertEquals(Double.parseDouble(totalPriceStrMod), expectedTotalPrice);
        System.out.println("Total price is correct");

        //Remove item1 from the cart
        WebElement removeItem1 = driver.findElement(By.className("action-delete"));
        removeItem1.click();

        //Verifying the total price after removing item1
        Thread.sleep(5000); // Wait for the cart to be updated
        WebElement totalPriceAfterRemove = driver.findElement(By.xpath("//td[@data-th='Order Total']"));
        WebElement totalPriceStrAfterRemove = totalPriceAfterRemove.findElement(By.className("price"));
        String totalPriceStrModAfterRemove = totalPriceStrAfterRemove.getText().replace("$","");
        Double expectedTotalPriceAfterRemove = Double.parseDouble(price2str);
        Assert.assertEquals(Double.parseDouble(totalPriceStrModAfterRemove), Double.parseDouble(price2str));
        System.out.println("Total price after removing item1 is correct");

        //Proceed to checkout
        WebElement proceedToCheckout = driver.findElement(By.xpath("//button[@data-role='proceed-to-checkout']"));
        proceedToCheckout.click();
/*
        //Filling the checkout page - first time only
        Thread.sleep(10000); // Wait for the page to load
        WebElement streetAddress = driver.findElement(By.name("street[0]"));
        streetAddress.sendKeys("Test Address");
        WebElement city = driver.findElement(By.name("city"));
        city.sendKeys("Test City");
        WebElement state = driver.findElement(By.name("region_id"));
        state.click();
        WebElement ohio = driver.findElement(By.xpath("//option[@value='47']"));
        ohio.click();
        WebElement zipCode = driver.findElement(By.name("postcode"));
        zipCode.sendKeys("12345");
        WebElement phone = driver.findElement(By.name("telephone"));
        phone.sendKeys("+00123456789");
        Thread.sleep(5000); // Wait for the page to update
        WebElement input = driver.findElement(By.name("ko_unique_4"));
        input.click();
 */
        //Filling the checkout page - address already filled
        Thread.sleep(10000); // Wait for the page to load
        WebElement input = driver.findElement(By.name("ko_unique_2"));
        input.click();

        //Click on next button
        WebElement nextButton = driver.findElement(By.xpath("//button[@data-role='opc-continue']"));
        nextButton.click();
        Thread.sleep(5000); // Wait for the page to load

        //Verify the checkout URL
        String currentURL = driver.getCurrentUrl();
        String expectedURL = "https://magento.softwaretestingboard.com/checkout/#payment";
        Assert.assertEquals(currentURL, expectedURL);
        System.out.println("checkout URL is correct");

        //Click on place order button
        WebElement placeOrderButton = driver.findElement(By.xpath("//button[@title='Place Order']"));
        placeOrderButton.click();
        Thread.sleep(5000); // Wait for the page to load

        //Verify the thank you text
        WebElement thankYouText = driver.findElement(By.xpath("//span[@data-ui-id='page-title-wrapper']"));
        String thankYouTextStr = thankYouText.getText();
        String thankYouOg = "Thank you for your purchase!";
        Assert.assertEquals(thankYouTextStr, thankYouOg);
        System.out.println("Thank you text is correct");

        //Test completed
        System.out.println("Test is completed");

    }
}