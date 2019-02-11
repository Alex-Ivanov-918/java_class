package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import java.io.File;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactCreationTests extends TestBase {


  @Test
  public void testContactCreation() throws Exception {
    app.goTo().homePage();
    Contacts before = app.contact().all();
    File photo = new  File("src/test/resources/smile.png");
    ContactData contact = new ContactData().withFirstName("Peter").withLastName("Parker").withNickname("Spider-Man")
            .withTitle("Friendly neighbour").withCompany("Avengers").withAddress("775 Westminster Avenue APT D5")
            .withMobile("1212121212").withPhoto(photo);
    app.contact().create(contact);
    Contacts after = app.contact().all();
    assertThat(app.contact().count(), equalTo(before.size() + 1));

    assertThat(after, equalTo(
            before.withAdded(contact.withId(after.stream().mapToInt((c) -> c.getId()).max().getAsInt()))));

  }

}
