package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.List;

public class ContactHelper extends HelperBase {

  NavigationHelper nvh = new NavigationHelper(wd);
  GroupHelper grph = new GroupHelper(wd);

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
    type(By.name("home"), contactData.getHome());
    type(By.name("mobile"), contactData.getMobile());
    type(By.name("work"), contactData.getWork());
    type(By.name("email"), contactData.getEmail());
    type(By.name("email2"), contactData.getEmail2());
    type(By.name("email3"), contactData.getEmail3());
    attach(By.name("photo"), contactData.getPhoto());


    if (creation) {
      if (contactData.getGroups().size() > 0) {
        Assert.assertTrue(contactData.getGroups().size() == 1);
        new Select(wd.findElement(By.name("new_group")))
                .selectByVisibleText(contactData.getGroups().iterator().next().getName());
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

  public boolean isThereTheContact (int id) {
    return isElementPresent(By.xpath("//input[@id='" + id + "']"));
  }
  public boolean isThereAContact () {
    return isElementPresent(By.name("selected[]"));
  }

  private void contactToGroupAddition() {
    click(By.xpath("//input[@value='Add to']"));
  }


  public void goToGroupPage() {
    click(By.linkText("groups"));
  }


  public void deleteFromGroup(ContactData contact) {
    selectContactById(contact.getId());
    deleteSelectedContactFromGroup();
    goToGroupPage();
  }



  private void deleteSelectedContactFromGroup() {
    click(By.name("remove"));
  }

  public void addToGroup(GroupData group, ContactData cnt) {
    if(!isThereAContact()){
      nvh.homePageWithoutFilters();
      selectContactById(cnt.getId());
      grph.toGroup(group.getId());
      click(By.name("add"));
    }
  }

  public void addContactToGroup(ContactData contact) {
    selectContactById(contact.getId());
    contactToGroupAddition();
    goToGroupPage();
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
                .withId(id).withFirstName(firstName).withLastName(lastName).withAllPhones(allPhones)
                .withAddress(address).withAllEmails(allEmails));
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
    String address = wd.findElement(By.name("address")).getAttribute("value");
    String email = wd.findElement(By.name("email")).getAttribute("value");
    String email2 = wd.findElement(By.name("email2")).getAttribute("value");
    String email3 = wd.findElement(By.name("email3")).getAttribute("value");
    wd.navigate().back();
    return new ContactData().withId(contact.getId()).withFirstName(firstname).withLastName(lastname).withHome(home)
            .withMobile(mobile).withWork(work).withAddress(address).withEmail(email).withEmail2(email2)
            .withEmail3(email3);
  }


  public void checkAvailabilityOfContactsAndCreate(ContactData cnt) {
    if (new DbHelper().contacts().size() == 0){
      nvh.homePageWithoutFilters();
      new ContactHelper(wd).create(cnt);
      nvh.homePageWithoutFilters();
    }
  }

  public void deleteContactInGroups(ContactData cnt){
    WebDriverWait wait = new WebDriverWait(wd,5);
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[value='"+cnt.getId()+"']"))).click();
    click(By.name("remove"));
  }
}
