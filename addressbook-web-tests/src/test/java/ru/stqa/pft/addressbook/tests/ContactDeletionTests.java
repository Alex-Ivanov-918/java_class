package ru.stqa.pft.addressbook.tests;


import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;


public class ContactDeletionTests extends TestBase {


  @Test
  public void testContactDeletion() throws Exception {

    app.getContactHelper().gotoHomePage();
    if (! app.getContactHelper().isThereAContact()) {
      app.getContactHelper().createContact(new ContactData("Piter", "Porker", "Spider-Man", "Friendly Neighbor", "Avengers", "20 Ingram St.", "0987654321", null));
    }
    app.getContactHelper().selectContact();
    app.getContactHelper().deleteSelectedContacts();
    app.getContactHelper().deleteConfirmation();
    app.getContactHelper().gotoHomePage();
    app.getSessionHelper().logout();
  }

}
