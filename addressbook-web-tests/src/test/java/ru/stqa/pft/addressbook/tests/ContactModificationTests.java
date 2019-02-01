package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.List;

public class ContactModificationTests extends TestBase {

  @Test
  public void testContactModification() {

    app.getContactHelper().gotoHomePage();
    if (! app.getContactHelper().isThereAContact()) {
      app.getContactHelper().createContact(new ContactData("Peter", "Porker", "Spider-Man", "Friendly Neighbor", "Avengers", "20 Ingram St.", "0987654321", null));
    }
    List<ContactData> before = app.getContactHelper().getContactList();
    app.getContactHelper().selectEditContact(before.size() - 1);
    app.getContactHelper().fillContactInfo(new ContactData("Miles", "Morales", "Spider-Man", "Friendly Neighbor", "Avengers", "20 Ingram St.", "0987654321", null), false);
    app.getContactHelper().submitContactModification();
    app.getContactHelper().actionConfirmation();
    app.getNavigationHelper().returnToHomePage();
    List<ContactData> after = app.getContactHelper().getContactList();
    Assert.assertEquals(after.size(),before.size());
    app.getSessionHelper().logout();
  }

}
