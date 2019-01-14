package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactModificationTests extends TestBase {

  @Test
  public void testContactModification() {

    app.getContactHelper().gotoHomePage();
    app.getContactHelper().initContactModification();
    app.getContactHelper().fillContactInfo(new ContactData("Miles", "Morales", "Spider-Man", "Friendly Neighbor", "Avengers", "20 Ingram St.", "0987654321", null), false);
    app.getContactHelper().submitContactModification();
    app.getNavigationHelper().returnToHomePage();
  }

}
