package ru.stqa.pft.addressbook.tests;


import org.testng.annotations.Test;


public class ContactDeletionTests extends TestBase {


  @Test
  public void testContactDeletion() throws Exception {

    app.getContactHelper().gotoHomePage();
    app.getContactHelper().selectContact();
    app.getContactHelper().deleteSelectedContacts();
    app.getContactHelper().deleteConfirmation();
    app.getContactHelper().gotoHomePage();
    app.getSessionHelper().logout();
  }

}
