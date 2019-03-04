package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.MatcherAssert.assertThat;



public class AddContactToGroup extends TestBase {

    @BeforeMethod
    public void ensurePreconditions() {
        app.group().checkAvailabilityOfGroupsAndCreate(new GroupData().withName("Test1").withHeader("Header1"));
        app.contact().checkAvailabilityOfContactsAndCreate(new ContactData().withFirstName("Peter").withLastName("Parker"));
    }

    @Test
    public void testInsertContactToGroup(){
        app.goTo().homePage();
        ContactData contact = app.db().contacts().iterator().next();
        Groups groups = app.db().groups();
        app.group().selectGroup(groups.iterator().next().getName());
        if (app.contact().isThereAContact()){
            app.contact().deleteContactInGroups(contact);
            app.contact().goToGroupPage();
        }
        GroupData group = groups.iterator().next();
        groups.removeAll(contact.getGroups());
        app.contact().addToGroup(group,contact);
        app.db().refreshSession(contact);
        assertThat(contact.getGroups(), hasItem(group));
    }
}

