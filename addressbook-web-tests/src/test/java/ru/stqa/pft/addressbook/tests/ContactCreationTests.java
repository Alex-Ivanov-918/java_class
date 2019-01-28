package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactCreationTests extends TestBase {


  @Test
  public void testContactCreation() throws Exception {

    int before = app.getContactHelper().getContactCount();
    app.getContactHelper().gotoHomePage();
    app.getContactHelper().gotoContactPage();
    app.getContactHelper().createContact(new ContactData("Piter", "Parker", "Spider-Man", "Friendly Neighbor", "Avengers", "20 Ingram St.", "1234567890", null));
    int after = app.getContactHelper().getContactCount();
    Assert.assertEquals(after,before + 1);
    app.getSessionHelper().logout();
  }


}
