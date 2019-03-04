package ru.stqa.pft.mantis.appmanager;

import ru.stqa.pft.mantis.model.UserData;

public class PasswordChangeHelper {

    private HelperBase hb;

    public PasswordChangeHelper(HelperBase hb) {
        this.hb = hb;
    }

    public void resetPassword(String password, String password_confirm) {
        hb.typeByName("password", password);
        hb.typeByName("password_confirm", password_confirm);
        hb.clickByXpath("//*[@id=\"account-update-form\"]/fieldset/span/button/span");
    }

    public void getConfirmationLink(String confirmationLink) {
        hb.goTo(confirmationLink);
    }

    public void selectUserFromTheList(UserData user) {
        hb.clickByXpath("//a[text()='" + user.getUsername() + "']");
        hb.clickByXpath("//*[@id=\"manage-user-reset-form\"]/fieldset/span/input");
    }

    public void goToUsersList() {
        hb.clickByXpath("//*[@id=\"sidebar\"]/ul/li[7]/a");
        hb.clickByXpath("//*[@id=\"main-container\"]/div[2]/div[2]/div/ul/li[2]/a");
    }

    public void enterPassword(String password) {
        hb.typeByName("password", password);
        hb.clickByXpath("//*[@id=\"login-form\"]/fieldset/input[3]");
    }

    public void enterLogin(String username) {
        hb.typeByName("username", username);
        hb.clickByXpath("//*[@id=\"login-form\"]/fieldset/input[2]");
    }

}
