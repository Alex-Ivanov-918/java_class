package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class NavigationHelper {
  private WebDriver wd;

  public NavigationHelper(WebDriver wd) {
    this.wd = wd;
  }

  public void returnToGroupPage() { wd.findElement(By.linkText("group page")).click(); }

  public void gotoGroupPage() {
    wd.findElement(By.linkText("groups")).click();
  }

  public void returnToHomePage() {
    wd.findElement(By.linkText("home")).click();
  }

  public void goToContactPage() {
    wd.findElement(By.linkText("add new")).click();
  }

  public void gotoHomePage() {
    wd.findElement(By.linkText("home")).click();
  }
}