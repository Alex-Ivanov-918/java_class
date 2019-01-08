package ru.stqa.pft.addressbook;

import org.testng.annotations.Test;

public class ContactCreationTests extends TestBase{


  @Test
  public void testContactCreation() throws Exception {

    goToContactPage();
    fillNewContact(new ContactData("Piter", "Parker", "Spider-Man", "Friendly Neighbor", "Avengers", "20 Ingram St.", "1234567890"));
    submitNewContact();
    returnToHomePage();
    logout();
  }


}
