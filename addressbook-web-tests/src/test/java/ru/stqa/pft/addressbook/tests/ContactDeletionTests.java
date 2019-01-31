package ru.stqa.pft.addressbook.tests;


import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.List;


public class ContactDeletionTests extends TestBase {


  @Test
  public void testContactDeletion() throws Exception {

    app.getContactHelper().gotoHomePage();
    if (! app.getContactHelper().isThereAContact()) {
      app.getContactHelper().createContact(new ContactData("Peter", "Porker", "Spider-Man", "Friendly Neighbor", "Animal Avengers", "20 Ingram St.", "0987654321", null));
    }
    List<ContactData> before = app.getContactHelper().getContactList();
    app.getContactHelper().selectContact(before.size() - 1);
    app.getContactHelper().deleteSelectedContacts();
    app.getContactHelper().actionConfirmation();
    app.getContactHelper().gotoHomePage();
    List<ContactData> after = app.getContactHelper().getContactList();
    Assert.assertEquals(after.size(),before.size() - 1);

    before.remove(before.size() - 1);
    Assert.assertEquals(before, after);

    app.getSessionHelper().logout();
  }

}
