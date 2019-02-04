package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.ArrayList;
import java.util.List;

public class ContactHelper extends HelperBase {

  public ContactHelper(WebDriver wd) {
    super(wd);
  }

  public void gotoHomePage() {
    click(By.linkText("home"));
  }

  public void gotoContactPage() {
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

  public void deleteSelectedContacts() {
    click(By.xpath("//input[@value='Delete']"));
    wd.switchTo().alert().accept();
  }

  public void submitContactModification() {
    click(By.name("update"));
  }

  public void actionConfirmation() {
    notification(By.className("msgbox"));
  }

  public boolean isThereAContact() {
    return isElementPresent(By.xpath("//td/input"));
  }

  public void createContact(ContactData contact) {
    gotoContactPage();
    fillContactInfo(contact, true);
    submitNewContact();
    gotoHomePage();
  }


    public List<ContactData> getContactList() {
    List<ContactData> contacts = new ArrayList<ContactData>();
    WebElement table = wd.findElement(By.xpath("//*[@id='maintable']/tbody"));
    List<WebElement> rows = wd.findElements(By.tagName("tr"));

    for (WebElement row : rows) {
      List<WebElement> cell = row.findElements(By.tagName("td"));
      List<WebElement> input = row.findElements(By.xpath("//td/input"));
      int count = cell.size();
      if (count>0) {
        String lastName = cell.get(1).getText();
        String firstName = cell.get(2).getText();
        System.out.println(firstName + ", " + lastName + ", ");
        int id = Integer.parseInt(input.get(0).getAttribute("value"));
        ContactData contact = new ContactData(id, firstName, lastName, null, null, null, null, null, null);
        contacts.add(contact);
      }
    }
    return contacts;
  }

  public void selectEditContact(int index) {
    wd.findElements(By.xpath("//*[@src='icons/pencil.png']")).get(index).click();
  }
}
