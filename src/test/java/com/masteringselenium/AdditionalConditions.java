package com.masteringselenium;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;

public class AdditionalConditions {

    public static ExpectedCondition<Boolean>
    jQueryAJAXCallsHaveCompleted() {
        return new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                return (Boolean) ((JavascriptExecutor)driver).executeScript(
                        "return (window.jQuery != null) && (jQuery.active === 0);");
            }
        };
    }
    /*
    Can now call this expected condition anywhere in our code by using:
    WebDriverWait wait = new WebDriverWait(getDriver(), 15, 100);
    wait.until(AdditionalConditions.jQueryAJAXCallsHaveCompleted());
    */

    public static ExpectedCondition<Boolean>
    angularHasFinishedProcessing() {
        return new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                return Boolean.valueOf(((JavascriptExecutor)driver).executeScript(
                        "return (window.angular !== undefined && (angular.element(document).injector() " +
                                "!== undefined) && (angular.element(document).injector()" +
                                ".get('$http').pendingRequests.length === 0)").toString());
            }
        };
    }
    /*
    Can call this anywhere in our code with:
    WebDriverWait wait = new WebDriverWait(getDriver(), 15, 100);
    wait.until(AdditionalConditions.angularHasFinishedProcessing());
     */
}
