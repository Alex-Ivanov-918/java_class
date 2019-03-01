package ru.stqa.pft.addressbook.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;

import java.io.File;

import static org.testng.AssertJUnit.assertTrue;


public class AddContactToGroup extends TestBase {

    @BeforeMethod
    public void ensurePreconditions() {
        if (app.db().groups().size() == 0) {
            app.goTo().groupPage();
            app.group().create(new GroupData().withName("test 0"));
        }
        if (app.db().contacts().size() == 0) {
            app.goTo().homePage();
            app.contact().create(new ContactData().withFirstName("Peter").withLastName("Porker").withNickname("Spider-Man")
                    .withPhoto(new File("src/test/resources/smile.png")));
        }
    }

    @Test
    public void testInsertContactToGroup() {
      app.goTo().groupNone();

      if(app.wd.findElements(By.xpath("//*[@id=\"maintable\"]/tbody/tr[@name=\"entry\"]")).isEmpty()) {
          app.contact().create(new ContactData().withFirstName("Peter").withLastName("Porker").withNickname("Spider-Man")
                  .withPhoto(new File("src/test/resources/smile.png")));
          app.goTo().groupNone();

      }
        ContactData firstWithoutGroups = app.db().contacts().getFirstWithoutGroups();
        WebElement firstGroupOption = app.wd.findElement(By.xpath("//select[@name=\"to_group\"]/option[1]"));
        int groupToAddId = Integer.parseInt(firstGroupOption.getAttribute("value"));

        app.contact().addContactToGroup(firstWithoutGroups);
        app.goTo().groupPageById(groupToAddId);
        assertTrue(app.contact().isThereTheContact(firstWithoutGroups.getId()));

    }
}

