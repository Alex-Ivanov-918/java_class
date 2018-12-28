package ru.stqa.pft.addressbook;

import org.testng.annotations.Test;

public class GroupCreationTests extends TestBase{


  @Test
  public void testGroupCreation() throws Exception {
    gotoGroupPage();
    initGroupCreation();
    fillGroupForm(new GroupData("trest1", "trest2", "trest3"));
    submitGroupCreation();
    returnToGroupPage();
    logout();
  }

}
