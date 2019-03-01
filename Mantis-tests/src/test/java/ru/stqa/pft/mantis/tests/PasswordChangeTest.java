package ru.stqa.pft.mantis.tests;

import org.openqa.selenium.Dimension;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.lanwen.verbalregex.VerbalExpression;
import ru.stqa.pft.mantis.appmanager.HttpSession;
import ru.stqa.pft.mantis.model.MailMessage;
import ru.stqa.pft.mantis.model.UserData;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static org.testng.Assert.assertTrue;


public class PasswordChangeTest   extends TestBase {

    @BeforeMethod
    public void startMailServer() {
        app.wd.manage().window().setSize(new Dimension(1200, 786));
        app.mail().start();
    }

    @Test
    public void testRegistration() throws IOException, MessagingException {
        HttpSession session = app.newSession();

        hb.typeByName("username", "administrator");
        hb.clickByXpath("//*[@id=\"login-form\"]/fieldset/input[2]");

        hb.typeByName("password", "root");
        hb.clickByXpath("//*[@id=\"login-form\"]/fieldset/input[3]");

        hb.clickByXpath("//*[@id=\"sidebar\"]/ul/li[6]/a");
        hb.clickByXpath("//*[@id=\"main-container\"]/div[2]/div[2]/div/ul/li[2]/a");

        List<UserData> allUsers = db.getAllUsersExceptAdmin();

        int index = ThreadLocalRandom.current().nextInt(0, allUsers.size() + 1);

        UserData user = allUsers.get(index);

        hb.clickByXpath("//a[text()='" + user.getUsername() + "']");
        hb.clickByXpath("//*[@id=\"manage-user-reset-form\"]/fieldset/span/input");

        List<MailMessage> mailMessages = app.mail().waitForMail(1, 10000);
        String confirmationLink = findconfirmationLink(mailMessages, user.getEmail());
        hb.goTo(confirmationLink);

        hb.typeByName("password", "12345");
        hb.typeByName("password_confirm", "12345");
        hb.clickByXpath("//*[@id=\"account-update-form\"]/fieldset/span/button/span");

        assertTrue(session.login(user.getUsername(), "12345", user.getRealname()));
        assertTrue(session.isLoggedInAs(user.getUsername(),user.getRealname()));
    }

    private String findconfirmationLink(List<MailMessage> mailMessages, String email) {
        MailMessage mailMessage = mailMessages.stream().filter((m)-> m.to.equals(email)).findAny().get();
        VerbalExpression regex = VerbalExpression.regex().find("http://").nonSpace().oneOrMore().build();
        return regex.getText(mailMessage.text);
    }

    @AfterMethod(alwaysRun = true)
    public void stopMailServer() {
        app.mail().stop();
    }
}