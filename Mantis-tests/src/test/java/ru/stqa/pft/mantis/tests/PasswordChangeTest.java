package ru.stqa.pft.mantis.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.testng.AssertJUnit;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.lanwen.verbalregex.VerbalExpression;
import ru.stqa.pft.mantis.appmanager.HttpSession;
import ru.stqa.pft.mantis.model.MailMessage;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static org.testng.Assert.assertTrue;


public class PasswordChangeTest   extends TestBase {

    @BeforeMethod
    public void startMailServer() {
        app.getDriver();
        app.wd.manage().window().setSize(new Dimension(1200, 786));
        app.mail().start();
    }

    @Test
    public void testRegistration() throws IOException, MessagingException {
        HttpSession session = app.newSession();

        app.wd.findElement(By.name("username")).sendKeys("administrator");
        app.wd.findElement(By.xpath("//*[@id=\"login-form\"]/fieldset/input[2]")).click();
        app.wd.manage().timeouts().pageLoadTimeout(10,  TimeUnit.SECONDS);

        app.wd.findElement(By.name("password")).sendKeys("root");
        app.wd.findElement(By.xpath("//*[@id=\"login-form\"]/fieldset/input[3]")).click();
        app.wd.manage().timeouts().pageLoadTimeout(10,  TimeUnit.SECONDS);

        app.wd.findElement(By.xpath("//*[@id=\"sidebar\"]/ul/li[6]/a")).click();
        app.wd.findElement(By.xpath("//*[@id=\"main-container\"]/div[2]/div[2]/div/ul/li[2]/a")).click();

        List<WebElement> usernameCells = app.wd.findElements(By.xpath("//*[@id=\"main-container\"]/div[2]/div[2]/div/div/div[4]/div[2]/div[2]/div/table/tbody/tr/td[1]/a"));
        List<WebElement> emailCells = app.wd.findElements(By.xpath("//*[@id=\"main-container\"]/div[2]/div[2]/div/div/div[4]/div[2]/div[2]/div/table/tbody/tr/td[3]"));
        List<WebElement> realnameCells = app.wd.findElements(By.xpath("//*[@id=\"main-container\"]/div[2]/div[2]/div/div/div[4]/div[2]/div[2]/div/table/tbody/tr/td[2]"));

        List<WebElement> noneEmptyRealnameCells = realnameCells.stream().filter((i) -> !i.getText().isEmpty()).collect(Collectors.toList());


        int randomRealnameIndex = ThreadLocalRandom.current().nextInt(0, noneEmptyRealnameCells.size() + 1);
        WebElement randomRealnameCell = noneEmptyRealnameCells.get(randomRealnameIndex);
        int index = realnameCells.indexOf(randomRealnameCell);
        WebElement selectCell = usernameCells.get(index);


        String selectedUserName = selectCell.getText();
        String selectedUserEmail = emailCells.get(index).getText();
        String selectedUserRealname = randomRealnameCell.getText();


        selectCell.click();
        app.wd.findElement(By.xpath("//*[@id=\"manage-user-reset-form\"]/fieldset/span/input")).click();

        List<MailMessage> mailMessages = app.mail().waitForMail(1, 10000);
        String confirmationLink = findconfirmationLink(mailMessages, selectedUserEmail);
        app.wd.get(confirmationLink);
//        app.wd.findElement(By.name("realname")).sendKeys(selectedUserRealname);
        app.wd.findElement(By.name("password")).sendKeys("12345");
        app.wd.findElement(By.name("password_confirm")).sendKeys("12345");
        app.wd.findElement(By.xpath("//*[@id=\"account-update-form\"]/fieldset/span/button/span")).click();

        AssertJUnit.assertTrue(session.login(selectedUserName, "12345", selectedUserRealname));
        assertTrue(session.isLoggedInAs(selectedUserName,selectedUserRealname));
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