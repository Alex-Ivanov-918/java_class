package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactCreationTests extends TestBase {


  @Test
  public void testContactCreation() throws Exception {

    app.getContactHelper().gotoContactPage();
    app.getContactHelper().fillContactInfo(new ContactData("Piter", "Parker", "Spider-Man", "Friendly Neighbor", "Avengers", "20 Ingram St.", "1234567890", "trest1"), true);
    app.getContactHelper().submitNewContact();
    app.getNavigationHelper().returnToHomePage();
    app.getSessionHelper().logout();
  }


}
