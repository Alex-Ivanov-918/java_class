package ru.stqa.pft.rest;


import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.xml.rpc.ServiceException;
import java.io.IOException;
import java.util.Set;

import static org.testng.Assert.assertEquals;

public class RestTests extends TestBase{

    @BeforeMethod
    public void checkIfIssueOpened() throws IOException, ServiceException {
        skipIfNotFixed(525);
    }


    @Test
    public void testCreateIssue() throws IOException {
        Set<Issue> oldIssues = getIssue();
        Issue newIssue = new Issue().withSubject("Test issue").withDescription("New test issue");
        int issueId = createIssue(newIssue);
        Set<Issue> newIssues = getIssue();
        oldIssues.add(newIssue.withId(issueId));
        assertEquals(newIssues, oldIssues);

    }


}
