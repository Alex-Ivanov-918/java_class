package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Comparator;
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

    ContactData contact = new ContactData(before.get(before.size() - 1).getId(), "Miles", "Morales", "Spider-Man", "Friendly Neighbor", "Avengers", "20 Ingram St.", "0987654321", null);
    app.getContactHelper().fillContactInfo(contact,false);
    app.getContactHelper().submitContactModification();
    app.getContactHelper().actionConfirmation();
    app.getNavigationHelper().returnToHomePage();
    List<ContactData> after = app.getContactHelper().getContactList();
    Assert.assertEquals(after.size(),before.size());

    before.remove(before.size() - 1);
    before.add(contact);

    Comparator<? super ContactData> byId = (c1, c2) -> Integer.compare(c1.getId(), c2.getId());
    before.sort(byId);
    after.sort(byId);
    Assert.assertEquals(before, after);

    app.getSessionHelper().logout();
  }

}
