package com.jaworski.dbcert.repository.impl;

import com.jaworski.dbcert.db.DataSourceConfiguration;
import com.jaworski.dbcert.db.TableInstructors;
import com.jaworski.dbcert.entity.Instructor;
import com.jaworski.dbcert.repository.AccessRepository;
import com.jaworski.dbcert.resources.CustomResources;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Repository
public class InstructorRepository implements AccessRepository<Instructor> {

  private final CustomResources customResources;
  private final DataSourceConfiguration dataSourceConfiguration;

  @Override
  public List<Instructor> findAll() throws SQLException, FileNotFoundException, ClassNotFoundException {
    String dbFilePath = customResources.getDbInstructorFilePath();
    Connection sqlConnection = dataSourceConfiguration.getSqlConnection(dbFilePath);
    Statement statement = sqlConnection.createStatement();
    ResultSet resultSet = statement.executeQuery("SELECT * FROM [" + TableInstructors.TABLE_NAME + "]");
    statement.close();

    Set<Instructor> names = new HashSet<>();
/* CREATE TABLE [Spis_Instruktorzy]
 (
        [No]                    Integer,
        [Name]                  Text (25) NOT NULL,
        [Surname]                       Text (25) NOT NULL,
        [BirthDate]                     DateTime NOT NULL,
        [BirthPlace]                    Text (25),
        [MrMs]                  Text (4),
        [photo1]                        OLE (255),
        [Tel_Domowy]                    Text (30),
        [Tel_GSM]                       Text (30),
        [A_Miasto]                      Text (30),
        [A_Ulica]                       Text (50),
        [A_Kod]                 Text (6),
        [Nick]                  Text (20),
        [photo2]                        OLE (255),
        [photo3]                        OLE (255),
        [photo4]                        OLE (255),
        [Notes]                 Text (255),
        [Uwagi]                 Text (50),
        [A_eml]                 Text (50),
        [NrCertyfikatu]                 Text (50),
        [CertNo]                        Text (50),
        [Specjalnosc]                   Text (255),
        [DataWaznosci]                  DateTime,
        [Dyplom]                        Text (255)
);  */
    while (resultSet.next()) {
      Instructor instructor = new Instructor();
      String name = resultSet.getString(TableInstructors.FIRST_NAME);
      instructor.setName(name);
      int id = resultSet.getInt(TableInstructors.NO);
      instructor.setNo(id);
      String surname = resultSet.getString(TableInstructors.SURNAME);
      instructor.setSurname(surname);
      Date birthDate = resultSet.getDate(TableInstructors.BIRTH_DATE);
      instructor.setBirthDate(birthDate);
      String birthPlace = resultSet.getString(TableInstructors.BIRTH_PLACE);
      instructor.setBirthPlace(birthPlace);
      String mrMrs = resultSet.getString(TableInstructors.MR_MS);
      instructor.setMrMs(mrMrs);
      var photo1 = resultSet.getBlob(TableInstructors.PHOTO1);
      instructor.setPhoto1(photo1);
      String phone = resultSet.getString(TableInstructors.PHONE);
      instructor.setPhone(phone);
      String mobile = resultSet.getString(TableInstructors.MOBILE);
      instructor.setMobile(mobile);
      String city = resultSet.getString(TableInstructors.CITY);
      instructor.setCity(city);
      String address = resultSet.getString(TableInstructors.ADDRESS);
      instructor.setAddress(address);
      String zipCode = resultSet.getString(TableInstructors.POSTCODE);
      instructor.setPostcode(zipCode);
      String nick = resultSet.getString(TableInstructors.NICK);
      instructor.setNick(nick);
      var photo2 = resultSet.getBlob(TableInstructors.PHOTO2);
      instructor.setPhoto2(photo2);
      var photo3 = resultSet.getBlob(TableInstructors.PHOTO3);
      instructor.setPhoto3(photo3);
      var photo4 = resultSet.getBlob(TableInstructors.PHOTO4);
      instructor.setPhoto4(photo4);
      String notes = resultSet.getString(TableInstructors.NOTES);
      instructor.setNotes(notes);
      String otherNotes = resultSet.getString(TableInstructors.OTHER_NOTES);
      instructor.setOtherNotes(otherNotes);
      String email = resultSet.getString(TableInstructors.EMAIL);
      instructor.setEmail(email);
      String noCertificate = resultSet.getString(TableInstructors.NO_CERTIFICATE);
      instructor.setNoCertificate(noCertificate);
      String noCert = resultSet.getString(TableInstructors.CERTNO);
      instructor.setCertNo(noCert);
      String specialization = resultSet.getString(TableInstructors.SPECIALIZATION);
      instructor.setSpecialization(specialization);
      Date expireDate = resultSet.getDate(TableInstructors.EXPIRE_DATE);
      instructor.setExpirationDate(expireDate);
      String diploma = resultSet.getString(TableInstructors.DIPLOMA);
      instructor.setDiploma(diploma);
      names.add(instructor);
    }
    return names.stream().toList();
  }
}
