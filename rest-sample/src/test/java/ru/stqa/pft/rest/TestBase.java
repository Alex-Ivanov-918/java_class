package ru.stqa.pft.rest;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import org.apache.http.client.fluent.Executor;
import org.apache.http.client.fluent.Request;
import org.apache.http.message.BasicNameValuePair;
import org.testng.SkipException;

import javax.xml.rpc.ServiceException;
import java.io.IOException;
import java.util.Set;

public class TestBase {


    protected Set<Issue> getIssue() throws IOException {
        String json = receiveExecutor().execute(Request.Get("http://bugify.stqa.ru/api/issues.json?limit=1000"))
                .returnContent().asString();
        JsonElement parsed = new JsonParser().parse(json);
        JsonElement issues = parsed.getAsJsonObject().get("issues");
        return new Gson().fromJson(issues, new TypeToken<Set<Issue>>(){}.getType());
    }

    protected Executor receiveExecutor() {
        return Executor.newInstance().auth("288f44776e7bec4bf44fdfeb1e646490", "");
    }

    protected int createIssue(Issue newIssue) throws IOException {
        String json =  receiveExecutor().execute(Request.Post("http://bugify.stqa.ru/api/issues.json")
                .bodyForm(new BasicNameValuePair("subject", newIssue.getSubject()),
                        new BasicNameValuePair("description", newIssue.getDescription())))
                .returnContent().asString();
        JsonElement parsed = new JsonParser().parse(json);
        return parsed.getAsJsonObject().get("issue_id").getAsInt();
    }

    public boolean isIssueOpen(int issueId) throws IOException, ServiceException {
        String json =  receiveExecutor().execute(Request.Get("http://bugify.stqa.ru/api/issues/" + issueId + ".json"))
                .returnContent().asString();
        JsonElement parsed = new JsonParser().parse(json);
        JsonElement issues = parsed.getAsJsonObject().get("issues").getAsJsonArray().get(0);
        Issue issue = new Gson().fromJson(issues, new TypeToken<Issue>(){}.getType());
        return !issue.isResolved();

    }

    public void skipIfNotFixed(int issueId) throws IOException, ServiceException {
        if (isIssueOpen(issueId)) {
            throw new SkipException("Ignored because of issue " + issueId);
        }
    }
}
