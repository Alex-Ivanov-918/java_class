package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import java.util.ArrayList;
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

  public void selectContact(int index) {
      wd.findElements(By.name("selected[]")).get(index).click();
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
    gotoHomePage();
  }

  public void modify(ContactData contact) {
    selectEditContactById(contact.getId());
    fillContactInfo(contact,false);
    submitContactModification();
    actionConfirmation();
    returnToHomePage();
  }

    public void delete(ContactData contact) {
      selectContactById(contact.getId());
      deleteSelectedContacts();
      actionConfirmation();
      returnToHomePage();
    }

  public List<ContactData> list() {
    List<ContactData> contacts = new ArrayList<ContactData>();
    List<WebElement> rows = wd.findElements(By.name("entry"));

    for (WebElement row : rows) {
      List<WebElement> cell = row.findElements(By.tagName("td"));
        String lastName = cell.get(1).getText();
        String firstName = cell.get(2).getText();
        int id = Integer.parseInt(cell.get(0).findElement(By.tagName("input")).getAttribute("value"));
        ContactData contact = new ContactData().withId(id).withFirstName(firstName).withLastName(lastName);
        contacts.add(contact);
      }
    return contacts;
  }

  public Contacts all() {
    Contacts contacts = new Contacts();
    List<WebElement> rows = wd.findElements(By.name("entry"));

    for (WebElement row : rows) {
      List<WebElement> cell = row.findElements(By.tagName("td"));
      String lastName = cell.get(1).getText();
      String firstName = cell.get(2).getText();
      int id = Integer.parseInt(cell.get(0).findElement(By.tagName("input")).getAttribute("value"));
      ContactData contact = new ContactData().withId(id).withFirstName(firstName).withLastName(lastName);
      contacts.add(contact);
    }
    return contacts;
  }
}
