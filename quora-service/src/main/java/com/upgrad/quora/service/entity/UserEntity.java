package com.upgrad.quora.service.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Entity
@Table(name = "users")
@NamedQueries(
    {
        @NamedQuery(name = "userByUuid", query = "select u from UserEntity u where u.uuid = :uuid"),
        @NamedQuery(name = "userByEmail", query = "select u from UserEntity u where u.email =:email"),
        @NamedQuery(name = "userByUserName", query = "select u from UserEntity u where u.userName=:userName")
    }
)


public class UserEntity implements Serializable {

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "uuid")
  @Size(max = 200)
  private String uuid;

  @Column(name = "email")
  @NotNull
  @Size(max = 50)
  private String email;

  @Column(name = "username")
  @NotNull
  @Size(max = 30)
  private String userName;

  //@ToStringExclude
  @Column(name = "password")
  @NotNull
  @Size(max = 255)
  private String password;

  @Column(name = "firstname")
  @NotNull
  @Size(max = 30)
  private String firstName;

  @Column(name = "lastname")
  @NotNull
  @Size(max = 30)
  private String lastName;

  @Column(name = "contactnumber")
  @Size(max = 30)
  private String contactnumber;



  @Column(name = "country")
  @Size(max = 30)
  private String country;

  @Column(name = "aboutme")
  @Size(max = 30)
  private String aboutMe;

  @Column(name = "dob")
  @Size(max = 30)
  private String dob;

  @Column(name = "role")
  @Size(max = 30)
  private String role;

  @Column(name = "salt")
  @NotNull
  @Size(max = 200)
  //@ToStringExclude
  private String salt;

  @Override
  public boolean equals(Object obj) {
    return new EqualsBuilder().append(this, obj).isEquals();
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getUuid() {
    return uuid;
  }

  public void setUuid(String uuid) {
    this.uuid = uuid;
  }

  public String getRole() {
    return role;
  }

  public void setRole() {
    // As per assignment spec, the role should default to "nonadmin"
    // User with "admin" role can be created only by pgadmin or sql queries
    this.role = "nonadmin";
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getContactNumber() {
    return contactnumber;
  }

  public void setContactNumber(String contactnumber) {
    this.contactnumber = contactnumber;
  }

  public String getSalt() {
    return salt;
  }

  public void setSalt(String salt) {
    this.salt = salt;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public void setAboutMe(String aboutMe) {
    this.aboutMe = aboutMe;
  }

  public void setDob(String dob) {
    this.dob = dob;
  }

  public String getUserName() {
    return userName;
  }

  public String getCountry() {
    return country;
  }

  public String getAboutMe() {
    return aboutMe;
  }

  public String getDob() {
    return dob;
  }


  @Override
  public int hashCode() {
    return new HashCodeBuilder().append(this).hashCode();
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
  }


}
