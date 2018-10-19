package com.masteringselenium;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.internal.Locatable;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import java.time.Duration;
import java.util.Arrays;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.function.Function;

public class TestExamples {

    Function<WebDriver, WebElement> weFindElementFoo = new Function<WebDriver, WebElement>() {
        public WebElement apply(WebDriver driver) {
            return driver.findElement(By.id("foo"));
        }
    };

    Function<WebDriver, Boolean> didWeFindElementFoo = new Function<WebDriver, Boolean>() {
        public Boolean apply(WebDriver driver) {
            return driver.findElements(By.id("foo")).size() > 0;
        }
    };

    Function<WebDriver, Boolean> didweFindElementFooLambda =
            driver -> driver.findElements(By.id("foo")).size() > 0;

    Wait<WebDriver> wait = new FluentWait<>(driver)
            .withTimeout(Duration.ofSeconds(15))
            .pollingEvery(Duration.ofMillis(500))
            .ignoreAll(Arrays.asList(NoSuchElementException.class, StaleElementReferenceException.class))
            .withMessage("The message you will see if a TimeOutException is thrown");

    public static Function<WebDriver, Boolean>
    listenerIsRegisteredOnElement(final String listenerType, final WebElement element) {
        return new Function<WebDriver, Boolean>() {
            public Boolean apply(WebDriver driver) {
                Map<String, Object> registeredListeners = (Map<String, Object>)
                ((JavascriptExecutor) driver).executeScript("return (window.jQuery != null)" +
                "&& (jQuery._data(jQuery(arguments[0]).get(0)), 'events')", element);
                for (Map.Entry<String, Object> listener : registeredListeners.entrySet()) {
                    if (listener.getKey().equals(listenerType)) {
                        return true;
                    }
                }
                return false;
            }
        };
    }

    public static Function<WebDriver, Boolean> elementHasStoppedMoving(final WebElement element) {
        return new Function<WebDriver, Boolean>() {
            public Boolean apply(WebDriver driver) {
                Point initialLocation = ((Locatable)element).getCoordinates().inViewPort();
                try {
                    Thread.sleep(50);
                } catch (InterruptedException ignored) {
                    // ignored
                }
                Point finalLocation = ((Locatable)element).getCoordinates()().inViewPort();
                return initialLocation.equals(finalLocation);
            }
        };
    }
}
