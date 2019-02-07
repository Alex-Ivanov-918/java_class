package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactCreationTests extends TestBase {


  @Test
  public void testContactCreation() throws Exception {
    app.goTo().homePage();
    Contacts before = app.contact().all();
    ContactData contact = new ContactData()
            .withLastName("Parker").withFirstName("Peter").withNickname("Spider-man").withTitle("Friendly Neighbor")
            .withCompany("Avengers").withAddress("20 Ingram St.").withMobile("0987654321");;
    app.contact().create(contact);
    Contacts after = app.contact().all();
    assertThat(after.size(),equalTo (before.size() + 1));

    assertThat(after, equalTo(
            before.withAdded(contact.withId(after.stream().mapToInt((c) -> c.getId()).max().getAsInt()))));

  }

}
