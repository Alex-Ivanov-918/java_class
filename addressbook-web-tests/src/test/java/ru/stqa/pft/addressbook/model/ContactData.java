package ru.stqa.pft.addressbook.model;

import com.google.gson.annotations.Expose;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.File;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "addressbook")
public class ContactData {
  @Id
  private int id = Integer.MAX_VALUE;

  @Expose
  @Column(name = "firstname")
  private String firstName;

  @Expose
  @Column(name = "lastname")
  private String lastName;

  @Expose
  @Column(name = "nickname")
  private String nickname;

  @Expose
  @Column(name = "title")
  private String title;

  @Expose
  @Column(name = "company")
  private String company;

  @Expose
  @Type(type = "text")
  @Column(name = "address")
  private String address;

  @Transient
  private String allPhones;

  @Type(type = "text")
  @Column(name = "home")
  private String home;

  @Expose
  @Type(type = "text")
  @Column(name = "mobile")
  private String mobile;

  @Type(type = "text")
  @Column(name = "work")
  private String work;

  @Transient
  private String allEmails;

  @Expose
  @Type(type = "text")
  @Column(name = "email")
  private String email
          ;
  @Type(type = "text")
  @Column(name = "email2")
  private String email2;

  @Type(type = "text")
  @Column(name = "email3")
  private String email3;

  @Type(type = "text")
  @Column(name = "photo")
  private String photo;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(name = "address_in_groups",
          joinColumns = @JoinColumn(name="id"), inverseJoinColumns = @JoinColumn(name = "group_id"))
  private Set<GroupData> groups = new HashSet<GroupData>();


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


  public ContactData withPhoto(File photo) {
    this.photo = photo.getPath();
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

  public File getPhoto() {
    return new File(photo);
  }

  public Groups getGroups() {
    return new Groups (groups);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ContactData that = (ContactData) o;
    return id == that.id &&
            Objects.equals(firstName, that.firstName) &&
            Objects.equals(lastName, that.lastName) &&
            Objects.equals(nickname, that.nickname) &&
            Objects.equals(title, that.title) &&
            Objects.equals(company, that.company) &&
            Objects.equals(address, that.address);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, firstName, lastName, nickname, title, company, address);
  }

  @Override
  public String toString() {
    return "ContactData{" +
            "id=" + id +
            ", firstName='" + firstName + '\'' +
            ", lastName='" + lastName + '\'' +
            ", nickname='" + nickname + '\'' +
            ", title='" + title + '\'' +
            ", company='" + company + '\'' +
            ", address='" + address + '\'' +
            ", allPhones='" + allPhones + '\'' +
            ", home='" + home + '\'' +
            ", mobile='" + mobile + '\'' +
            ", work='" + work + '\'' +
            ", allEmails='" + allEmails + '\'' +
            ", email='" + email + '\'' +
            ", email2='" + email2 + '\'' +
            ", email3='" + email3 + '\'' +
            '}';
  }

    public ContactData inGroup(GroupData group) {
    groups.add(group);
    return this;
    }
}

