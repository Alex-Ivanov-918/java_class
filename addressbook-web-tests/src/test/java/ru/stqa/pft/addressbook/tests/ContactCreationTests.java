package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactCreationTests extends TestBase {

  @DataProvider
  public Iterator<Object[]> validContacts() {
    File photo = new  File("src/test/resources/smile.png");
    List<Object[]> list = new ArrayList<Object[]>();
    list.add(new Object[] {new ContactData().withFirstName("Peter1").withLastName("Parker1").withNickname("Spider-Man1")
            .withTitle("Friendly neighbour1").withCompany("Avengers1").withAddress("775 Westminster Avenue APT D51")
            .withMobile("12121212121").withPhoto(photo)});
    list.add(new Object[] {new ContactData().withFirstName("Peter2").withLastName("Parker2").withNickname("Spider-Man2")
            .withTitle("Friendly neighbour2").withCompany("Avengers2").withAddress("775 Westminster Avenue APT D52")
            .withMobile("12121212122").withPhoto(photo)});
    list.add(new Object[] {new ContactData().withFirstName("Peter3").withLastName("Parker3").withNickname("Spider-Man3")
            .withTitle("Friendly neighbour3").withCompany("Avengers3").withAddress("775 Westminster Avenue APT D53")
            .withMobile("12121212123").withPhoto(photo)});
    return list.iterator();
  }


  @Test(dataProvider = "validContacts")
  public void testContactCreation(ContactData contact) throws Exception {
    app.goTo().homePage();
    Contacts before = app.contact().all();
    app.contact().create(contact);
    Contacts after = app.contact().all();
    assertThat(app.contact().count(), equalTo(before.size() + 1));

    assertThat(after, equalTo(
            before.withAdded(contact.withId(after.stream().mapToInt((c) -> c.getId()).max().getAsInt()))));

  }

}
