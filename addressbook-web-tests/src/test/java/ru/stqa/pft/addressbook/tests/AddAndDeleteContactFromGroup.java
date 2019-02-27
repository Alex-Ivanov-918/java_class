package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.io.File;


public class AddAndDeleteContactFromGroup extends TestBase{


    @BeforeMethod
    public void ensurePreconditions () {
        app.goTo().groupPage();
        if (app.db().groups().size() == 0) {
            app.group().create(new GroupData().withName("test1"));
        }
    }

    @Test
    public void testContactDeletionFormGroup () {
        Groups groups = app.db().groups();
        GroupData modifiedGroup = groups.iterator().next();
        app.wd.get("http://study.loc/index.php?group=" + modifiedGroup.getId());
        if (! app.contact().isThereAContact()) {
            app.goTo().addNewContactPage();
            File photo = new  File("src/test/resources/smile.png");
            app.contact().create(new ContactData().withFirstName("Nick 0").withLastName("Simpson 0").withHome("11110")
                    .withMobile("22220").withWork("33330").withEmail("0@mail.com").withEmail2("0@mail.com")
                    .withEmail3("0@mail.com").withAddress("New York 0").withPhoto(photo).inGroup(modifiedGroup));
            app.wd.get("http://study.loc/index.php?group=" + modifiedGroup.getId());
        }

        ContactData deletedContact = app.contact().all().iterator().next();
        app.contact().deleteFromGroup(deletedContact);
        Assert.assertFalse(app.contact().isThereTheContact(deletedContact.getId()));
    }
}
