package com.jaworski.dbcert.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Blob;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Instructor {

  private int no;
  private String name;
  private String surname;
  private String email;
  private String phone;
  private String mobile;
  private String city;
  private String address;
  private String postcode;
  private Blob photo1;
  private Blob photo2;
  private Blob photo3;
  private Blob photo4;
  private String notes;
  private String otherNotes;
  private String certNo;
  private String specialization;
  private String diploma;
  private Date birthDate;
  private String birthPlace;
  private String mrMs;
  private String nick;
  private String noCertificate;
  private Date expirationDate;

}
