package com.jaworski.dbcert.dto;

import java.util.Date;
import java.util.Objects;

public class StudentDTO {
    private int id;
    private String name;
    private String lastName;
    private String courseNo;
    private Date dateBegine;
    private Date dateEnd;
    private String mrMs;
    private String certType;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCourseNo() {
        return courseNo;
    }

    public void setCourseNo(String courseNo) {
        this.courseNo = courseNo;
    }

    public Date getDateBegine() {
        return dateBegine;
    }

    public void setDateBegine(Date dateBegine) {
        this.dateBegine = dateBegine;
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
    }

    public String getMrMs() {
        return mrMs;
    }

    public void setMrMs(String mrMs) {
        this.mrMs = mrMs;
    }

    public String getCertType() {
        return certType;
    }

    public void setCertType(String certType) {
        this.certType = certType;
    }

    @Override
    public String toString() {
        return "StudentDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", courseNo='" + courseNo + '\'' +
                ", dateBegine=" + dateBegine +
                ", dateEnd=" + dateEnd +
                ", mrMs='" + mrMs + '\'' +
                ", certType='" + certType + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        StudentDTO that = (StudentDTO) o;
        return id == that.id && Objects.equals(name, that.name) && Objects.equals(lastName, that.lastName) && Objects.equals(courseNo, that.courseNo) && Objects.equals(dateBegine, that.dateBegine) && Objects.equals(dateEnd, that.dateEnd) && Objects.equals(mrMs, that.mrMs) && Objects.equals(certType, that.certType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, lastName, courseNo, dateBegine, dateEnd, mrMs, certType);
    }
}
