package ru.stqa.pft.addressbook.model;

import java.io.File;
import java.util.Objects;

public class ContactData {
  private int id = Integer.MAX_VALUE;
  private String firstName;
  private String lastName;
  private String nickname;
  private String title;
  private String company;
  private String address;
  private String allPhones;
  private String home;
  private String mobile;
  private String work;
  private String group;
  private String allEmails;
  private String email;
  private String email2;
  private String email3;
  private File photo;

  public ContactData withId(int id) {
    this.id = id;
    return this;
  }

  public ContactData withFirstName(String firstName) {
    this.firstName = firstName;
    return this;
  }

  public ContactData withLastName(String lastName) {
    this.lastName = lastName;
    return this;
  }

  public ContactData withNickname(String nickname) {
    this.nickname = nickname;
    return this;
  }

  public ContactData withTitle(String title) {
    this.title = title;
    return this;
  }

  public ContactData withCompany(String company) {
    this.company = company;
    return this;
  }

  public ContactData withAddress(String address) {
    this.address = address;
    return this;
  }

  public ContactData withAllPhones(String allPhones) {
    this.allPhones = allPhones;
    return this;
  }

  public ContactData withHome(String home) {
    this.home = home;
    return this;

  }
  public ContactData withMobile(String mobile) {
    this.mobile = mobile;
    return this;
  }

  public ContactData withWork(String work) {
    this.work = work;
    return this;
  }

  public ContactData withEmail(String email1) {
    this.email = email1;
    return this;
  }

  public ContactData withEmail2(String email2) {
    this.email2 = email2;
    return this;
  }

  public ContactData withEmail3(String email3) {
    this.email3 = email3;
    return this;
  }

  public ContactData withAllEmails(String allEmails) {
    this.allEmails = allEmails;
    return this;
  }

  public ContactData withGroup(String group) {
    this.group = group;
    return this;
  }

  public ContactData withPhoto(File photo) {
    this.photo = photo;
    return this;
  }

  public int getId() {
    return id;
  }

  public String getFirstName () {
      return firstName;
    }

  public String getLastName () {
      return lastName;
    }

  public String getNickname () {
      return nickname;
    }

  public String getTitle () {
      return title;
    }

  public String getCompany () {
      return company;
    }

  public String getAddress () {
      return address;
    }

  public String getAllPhones() { return allPhones; }

  public String getHome() {
      return home;
    }

  public String getMobile () {
      return mobile;
    }

  public String getWork () {
      return work;
    }

  public String getEmail () {
      return email;
    }

  public String getEmail2 () { return email2; }

  public String getEmail3 () { return email3; }

  public String getAllEmails() { return allEmails; }

  public String getGroup () {
      return group;
    }

  public File getPhoto() {
    return photo;
  }

  @Override
  public String toString() {
    return "ContactData{" +
            "id=" + id +
            ", firstName='" + firstName + '\'' +
            ", lastName='" + lastName + '\'' +
            '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ContactData that = (ContactData) o;
    return id == that.id &&
            Objects.equals(firstName, that.firstName) &&
            Objects.equals(lastName, that.lastName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, firstName, lastName);
  }

}

