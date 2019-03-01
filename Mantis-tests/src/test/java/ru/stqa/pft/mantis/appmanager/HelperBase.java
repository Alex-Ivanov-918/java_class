package ru.stqa.pft.mantis.appmanager;

import org.openqa.selenium.By;

import java.io.File;

public class HelperBase {

    protected ApplicationManager app;

    public HelperBase(ApplicationManager app) {
        this.app = app;
    }

    public void click(By locator) {
        app.wd.findElement(locator).click();
    }

    public void type(By locator, String text) {
        click(locator);
        if (text != null) {
            String existingText = app.wd.findElement(locator).getAttribute("value");
            if (! text.equals(existingText)) {
                app.wd.findElement(locator).clear();
                app.wd.findElement(locator).sendKeys(text);
            }
        }

    }

    public void typeByName (String name, String text) {
        type(By.name(name), text);
    }

    public void clickByName (String name) {
        click(By.name(name));
    }

    public void clickByXpath (String xpath) {
        click(By.xpath(xpath));
    }

    public void goTo (String url) {
        app.wd.get(url);
    }
    protected void attach(By locator, File file) {
        if (file != null) {
            app.wd.findElement(locator).sendKeys(file.getAbsolutePath());
        }

    }
}
