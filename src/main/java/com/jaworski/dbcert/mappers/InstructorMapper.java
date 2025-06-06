package com.jaworski.dbcert.mappers;

import com.jaworski.dbcert.dto.InstructorDto;
import com.jaworski.dbcert.entity.Instructor;
import org.springframework.stereotype.Component;

import java.sql.Blob;
import java.sql.SQLException;
import java.util.Base64;

@Component
public class InstructorMapper {

  public InstructorDto mapToInstructorDto(Instructor instructor) {
    InstructorDto instructorDto = new InstructorDto();
    instructorDto.setNo(instructor.getNo());
    instructorDto.setName(instructor.getName());
    instructorDto.setSurname(instructor.getSurname());
    instructorDto.setEmail(instructor.getEmail());
    instructorDto.setPhone(instructor.getPhone());
    instructorDto.setMobile(instructor.getMobile());
    instructorDto.setCity(instructor.getCity());
    instructorDto.setAddress(instructor.getAddress());
    instructorDto.setPostcode(instructor.getPostcode());
    instructorDto.setPhoto1(blobToString(instructor.getPhoto1()));
    instructorDto.setPhoto2(blobToString(instructor.getPhoto2()));
    instructorDto.setPhoto3(blobToString(instructor.getPhoto3()));
    instructorDto.setPhoto4(blobToString(instructor.getPhoto4()));
    instructorDto.setNotes(instructor.getNotes());
    instructorDto.setOtherNotes(instructor.getOtherNotes());
    instructorDto.setCertNo(instructor.getCertNo());
    instructorDto.setSpecialization(instructor.getSpecialization());
    instructorDto.setDiploma(instructor.getDiploma());
    instructorDto.setBirthDate(instructor.getBirthDate());
    instructorDto.setBirthPlace(instructor.getBirthPlace());
    instructorDto.setMrMs(instructor.getMrMs());
    instructorDto.setNick(instructor.getNick());
    instructorDto.setNoCertificate(instructor.getNoCertificate());
    instructorDto.setExpirationDate(instructor.getExpirationDate());
    return instructorDto;
  }

  private String blobToString(Blob photo) {
    try {
      return Base64.getEncoder().encodeToString(photo == null ?
              new byte[0] :
              photo.getBytes(1, (int) photo.length()));
    } catch (SQLException e) {
      return null;
    }
  }
}
