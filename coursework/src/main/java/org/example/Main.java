package org.example;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.util.List;

public class Main {

    WebDriver driver;

    @BeforeMethod
    public void setup() {
        WebDriverManager.edgedriver().setup();
        driver = new EdgeDriver();
    }

    @Test
    public void task02(){
        String givenUrl = "https://www.saucedemo.com/";
        String destinationUrl = "https://www.saucedemo.com/inventory.html";
        driver.get(givenUrl);
        String currentUrl = driver.getCurrentUrl();
        //we are not using if conditions here

        //Assert is used to verify the links
        Assert.assertEquals(givenUrl,currentUrl);
        //Printing the verification
        System.out.println("Verified the URL");

        //finding the username element and entering data
        WebElement username = driver.findElement(By.xpath("//input[@data-test='username']"));
        username.sendKeys("standard_user");

        //finding the password field and entering data
        WebElement password = driver.findElement(By.xpath("//input[@data-test='password']"));
        password.sendKeys("secret_sauce");

        //finding the login button and clicking it
        WebElement loginBtn = driver.findElement(By.xpath("//input[@data-test='login-button']"));
        loginBtn.click();

        //gets the inventory URL
        String inventoryUrl = driver.getCurrentUrl();
        Assert.assertEquals(destinationUrl,inventoryUrl);
        //Printing the verification
        System.out.println("Verified the destination URL");

        //Select item
        WebElement item = driver.findElement(By.id("item_1_title_link"));
        item.click();

        //Add to cart
        WebElement addToCart = driver.findElement(By.id("add-to-cart-sauce-labs-bolt-t-shirt"));
        addToCart.click();

        //Verifying product details
        String productName = "Sauce Labs Bolt T-Shirt";
        double givenPrice = 15.99;
        WebElement itemPrice = driver.findElement(By.className("inventory_details_price"));
        String priceItemStr = itemPrice.getText().replace("$","");
        WebElement productNamePicked = driver.findElement(By.className("inventory_details_name"));
        double priceConverted = Double.parseDouble(priceItemStr);
        String productNameCurrent = productNamePicked.getText();
        Assert.assertEquals(productName,productNameCurrent);
        Assert.assertEquals(priceConverted,givenPrice);
        //Printing the verification
        System.out.println("Verified the product name and price");

        //Click cart
        WebElement cartButton = driver.findElement(By.className("shopping_cart_link"));
        cartButton.click();

        //gets the cart URL
        String cartUrlGiven = "https://www.saucedemo.com/cart.html";
        String cartUrl = driver.getCurrentUrl();
        Assert.assertEquals(cartUrlGiven,cartUrl);
        //Printing the verification
        System.out.println("Verified the destination URL");

        //Verifying product details
        WebElement itemPriceCart = driver.findElement(By.className("inventory_item_price"));
        String priceItemCartStr = itemPriceCart.getText().replace("$","");
        WebElement productNameCartPicked = driver.findElement(By.className("inventory_item_name"));
        double priceCartConverted = Double.parseDouble(priceItemCartStr);
        String productNameCartCurrent = productNameCartPicked.getText();
        Assert.assertEquals(productNameCartCurrent,productNameCurrent);
        Assert.assertEquals(priceCartConverted,givenPrice);
        //Printing the verification
        System.out.println("Verified the product name and price");

        //Click remove
        WebElement removeButton = driver.findElement(By.id("remove-sauce-labs-bolt-t-shirt"));
        removeButton.click();

        //Verifying product details
        WebElement itemRemoved = driver.findElement(By.className("removed_cart_item"));
        //Printing the verification
        System.out.println("Verified the item is removed.");

        //Click continue
        WebElement continueButton = driver.findElement(By.id("continue-shopping"));
        continueButton.click();

        //Add 2 items to cart
        WebElement addToCart1 = driver.findElement(By.name("add-to-cart-sauce-labs-backpack"));
        addToCart1.click();
        WebElement addToCart2 = driver.findElement(By.name("add-to-cart-sauce-labs-bike-light"));
        addToCart2.click();

        //Go to cart
        WebElement cart = driver.findElement(By.className("shopping_cart_link"));
        cart.click();

        //Listing cart item
        List<WebElement> products = driver.findElements(By.className("cart_item"));

        String[] productNamesOg = {"Sauce Labs Backpack","Sauce Labs Bike Light"};
        Double[] productPriceOg = {29.99 , 9.99};

        //Verification of items and prices
        for (int i = 0 ; i < 2 ; i++){
            String productCartName = products.get(i).findElement(By.className("inventory_item_name")).getText();
            Double productCartPrice = Double.parseDouble(products.get(i).findElement(By.className("inventory_item_price")).getText().replace("$", ""));
            Assert.assertEquals(productCartName,productNamesOg[i]);
            Assert.assertEquals(productCartPrice, productPriceOg[i]);
        }
        //Printing the verification
        System.out.println("Verified the product names and prices in the cart.");

        //Click checkout
        WebElement checkoutButton = driver.findElement(By.id("checkout"));
        checkoutButton.click();

        //finding the first-name element and entering data
        WebElement firstname = driver.findElement(By.xpath("//input[@data-test='firstName']"));
        firstname.sendKeys("Swag");

        //finding the last-name field and entering data
        WebElement lastname = driver.findElement(By.xpath("//input[@data-test='lastName']"));
        lastname.sendKeys("Labs");

        //finding the zip-code field and entering data
        WebElement zipCode = driver.findElement(By.xpath("//input[@data-test='postalCode']"));
        zipCode.sendKeys("123");

        //finding the continue button and clicking it
        WebElement continueButtonCheckout = driver.findElement(By.xpath("//input[@data-test='continue']"));
        continueButtonCheckout.click();

        //Calculating the total
        Double sum = 0.00;
        for(int i=0; i<2; i++) {
            sum = sum + productPriceOg[i];
        }
        //Verifying total
        WebElement totalPrice = driver.findElement(By.className("summary_subtotal_label"));
        String priceTotalStr = totalPrice.getText().replace("Item total: $","");
        double totalConverted = Double.parseDouble(priceTotalStr);
        Assert.assertEquals(totalConverted,sum);
        //Printing the verification
        System.out.println("Verified the total price");

        //Clicking the finish button
        WebElement finishButton = driver.findElement(By.id("finish"));
        finishButton.click();

        //Verifying thank you text
        WebElement thankYou = driver.findElement(By.className("complete-header"));
        //Printing the verification
        System.out.println("Verified the thank you text is visible.");

        //gets the finish URL
        String finishUrlGiven = "https://www.saucedemo.com/checkout-complete.html";
        String finishUrl = driver.getCurrentUrl();
        Assert.assertEquals(finishUrlGiven,finishUrl);
        //Printing the verification
        System.out.println("Verified the the test is finished with the URL!");
    }
}
