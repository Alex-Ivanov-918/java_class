package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.List;

public class ContactCreationTests extends TestBase {


  @Test
  public void testContactCreation() throws Exception {
    List<ContactData> before = app.getContactHelper().getContactList();
    app.getContactHelper().gotoHomePage();
    app.getContactHelper().gotoContactPage();
    app.getContactHelper().createContact(new ContactData("Peter", "Parker", "Spider-Man", "Friendly Neighbor", "Avengers", "20 Ingram St.", "1234567890", null));
    List<ContactData> after = app.getContactHelper().getContactList();
    Assert.assertEquals(after.size(),before.size() + 1);
    app.getSessionHelper().logout();
  }


}
