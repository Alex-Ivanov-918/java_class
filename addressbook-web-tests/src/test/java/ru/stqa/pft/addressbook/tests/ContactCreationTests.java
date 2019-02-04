package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

public class ContactCreationTests extends TestBase {


  @Test
  public void testContactCreation() throws Exception {
    List<ContactData> before = app.getContactHelper().getContactList();
    app.getContactHelper().gotoHomePage();
    app.getContactHelper().gotoContactPage();
    ContactData contact = new ContactData("Peter", "Parker", "Spider-Man", "Friendly Neighbor", "Avengers", "20 Ingram St.", "1234567890", null);
    app.getContactHelper().createContact(contact);
    List<ContactData> after = app.getContactHelper().getContactList();
    Assert.assertEquals(after.size(),before.size() + 1);
    app.getSessionHelper().logout();

    int max = 0;
    for (ContactData c : after) {
      if (c.getId() > max) {
       max = c.getId();
      }
    }
    Comparator<? super ContactData> byId = new Comparator<ContactData>() {
      @Override
      public int compare(ContactData o1, ContactData o2) {
        return  Integer.compare(o1.getId(), o2.getId());
      }
    };
    int max1 = after.stream().max(byId).get().getId();
    contact.setId(max1);
    before.add(contact);
    Assert.assertEquals(new HashSet<Object>(before), new HashSet<Object>(after));

  }


}
