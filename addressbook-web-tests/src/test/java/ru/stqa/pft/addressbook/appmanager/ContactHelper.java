package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import java.util.List;

public class ContactHelper extends HelperBase {

  public ContactHelper(WebDriver wd) {
    super(wd);
  }

  public void gotoHomePage() {
    click(By.linkText("home"));
  }

  public void initContactCreation() {
    click(By.linkText("add new"));
  }

  public void submitNewContact() {
    click(By.xpath("(//input[@name='submit'])[2]"));
  }

  public int count() {
    return wd.findElements(By.name("selected[]")).size();
  }

  public void fillContactInfo(ContactData contactData, boolean creation) {
    type(By.name("firstname"), contactData.getFirstName());
    type(By.name("lastname"), contactData.getLastName());
    type(By.name("nickname"), contactData.getNickname());
    type(By.name("title"), contactData.getTitle());
    type(By.name("company"), contactData.getCompany());
    type(By.name("address"), contactData.getAddress());
    type(By.name("mobile"), contactData.getMobile());

    if (creation) {
      if (null != contactData.getGroup()) {
        new Select (wd.findElement(By.name("new_group"))).selectByVisibleText(contactData.getGroup());
      }
    } else {
      Assert.assertFalse(isElementPresent(By.name("new_group")));
    }
  }

  public void selectContactById(int id) {

      wd.findElement(By.cssSelector("input[value='" + id + "']")).click();
    }

  public void deleteSelectedContacts() {
    click(By.xpath("//input[@value='Delete']"));
    wd.switchTo().alert().accept();
  }

  public void returnToHomePage() {
    click(By.linkText("home"));
  }

  public void submitContactModification() {
    click(By.name("update"));
  }

  public void actionConfirmation() {
    notification(By.className("msgbox"));
  }

  public void selectEditContactById(int id) {
    wd.findElement(By.xpath("//*[@href='edit.php?id="+id+"']")).click();
  }

  public void create(ContactData contact) {
    initContactCreation();
    fillContactInfo(contact, true);
    submitNewContact();
    contactCache = null;
    gotoHomePage();
  }

  public void modify(ContactData contact) {
    selectEditContactById(contact.getId());
    fillContactInfo(contact,false);
    submitContactModification();
    actionConfirmation();
    contactCache = null;
    returnToHomePage();
  }

  public void delete(ContactData contact) {
    selectContactById(contact.getId());
    deleteSelectedContacts();
    actionConfirmation();
    contactCache = null;
    returnToHomePage();
  }

  public Contacts contactCache = null;


  public Contacts all() {
      if (contactCache != null) {
          return new Contacts(contactCache);
      }
      contactCache = new Contacts();
    List<WebElement> rows = wd.findElements(By.name("entry"));
    for (WebElement row : rows) {
      List<WebElement> cell = row.findElements(By.tagName("td"));
      int id = Integer.parseInt(cell.get(0).findElement(By.tagName("input")).getAttribute("value"));
      String lastName = cell.get(1).getText();
      String firstName = cell.get(2).getText();
      String address = cell.get(3).getText();
      String allEmails = cell.get(4).getText();
      String allPhones = cell.get(5).getText();
        contactCache.add(new ContactData()
                .withId(id).withFirstName(firstName).withLastName(lastName).withAllPhones(allPhones));
    }
    return new Contacts(contactCache);
  }

  public ContactData infoFromEditForm(ContactData contact) {
    selectEditContactById(contact.getId());
    String firstname = wd.findElement(By.name("firstname")).getAttribute("value");
    String lastname = wd.findElement(By.name("lastname")).getAttribute("value");
    String home = wd.findElement(By.name("home")).getAttribute("value");
    String mobile = wd.findElement(By.name("mobile")).getAttribute("value");
    String work = wd.findElement(By.name("work")).getAttribute("value");
    wd.navigate().back();
    return new ContactData().withId(contact.getId()).withFirstName(firstname).withLastName(lastname).withHome(home)
            .withMobile(mobile).withWork(work);
  }
}
