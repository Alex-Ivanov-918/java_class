package ru.stqa.pft.addressbook.model;

public class ContactData {
  private final String firstName;
  private final String lastName;
  private final String nickname;
  private final String title;
  private final String company;
  private final String address;
  private final String mobile;
  private String group;

  public ContactData(String firstName, String lastName, String nickname, String title, String company, String address, String mobile, String group) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.nickname = nickname;
    this.title = title;
    this.company = company;
    this.address = address;
    this.mobile = mobile;
    this.group = group;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public String getNickname() {
    return nickname;
  }

  public String getTitle() {
    return title;
  }

  public String getCompany() {
    return company;
  }

  public String getAddress() {
    return address;
  }

  public String getMobile() {
    return mobile;
  }

  public String getGroup() {
    return group;
  }
}
