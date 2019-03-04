package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;


public class DeleteContactFromGroup extends TestBase{


    @BeforeMethod
    public void ensurePreconditions() {
        app.group().checkAvailabilityOfGroupsAndCreate(new GroupData().withName("Test1").withHeader("Header1"));
        app.contact().checkAvailabilityOfContactsAndCreate(new ContactData().withFirstName("Peter").withLastName("Parker"));
    }

    @Test
    public void testDeleteContactFromGroup(){
        app.goTo().homePage();
        ContactData contact = app.db().contacts().iterator().next();
        Groups groups = app.db().groups();
        GroupData group = groups.iterator().next();
        app.group().selectGroup(groups.iterator().next().getName());
        if (!app.contact().isThereAContact()){
            app.contact().addToGroup(group,contact);
            app.contact().goToGroupPage();
        }else{
            contact = app.contact().all().iterator().next();
        }
        app.contact().deleteContactInGroups(contact);
        groups.removeAll(contact.getGroups());
        app.db().refreshSession(contact);
        assertThat(contact.getGroups(), not(hasItem(group)));
    }
}
