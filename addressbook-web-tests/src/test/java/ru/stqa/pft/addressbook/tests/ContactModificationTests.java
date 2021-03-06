package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import java.io.File;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactModificationTests extends TestBase {

  @BeforeMethod
  public void ensurePreconditions() {

    if (app.db().contacts().size() == 0) {
    app.goTo().homePage();
    app.contact().create(new ContactData().withFirstName("Peter").withLastName("Porker").withNickname("Spider-Man"));
    }
  }

  @Test
  public void testContactModification() {
    Contacts before = app.db().contacts();
    ContactData modifiedContact = before.iterator().next();
    ContactData contact = new ContactData().withId(modifiedContact.getId())
            .withFirstName("Miles").withLastName("Morales").withNickname("Spider-Man").withTitle("Friendly Neighbor")
            .withCompany("Avengers").withAddress("20 Ingram St.").withMobile("0987654321")
            .withPhoto(new File("src/test/resources/smile.png"));
    app.goTo().homePage();
    app.contact().modify(contact);
    Contacts after = app.db().contacts();
    assertThat(app.contact().count(), equalTo(before.size()));
    assertThat(after, equalTo(before.without(modifiedContact).withAdded(contact)));
    verifyContactListInUI();
  }

}
