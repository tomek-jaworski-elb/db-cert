package com.jaworski.dbcert.dto;

import java.util.Date;

public class StudentDTO {
    private int id;
    private String name;
    private String lastName;
    private String courseNo;
    private Date dateBegine;
    private Date dateEnd;
    private String mrMs;
    private String certType;

    public StudentDTO() {}

    public StudentDTO(int id, String name, String lastName, String courseNo, Date dateBegine, Date dateEnd, String mrMs, String certType) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.courseNo = courseNo;
        this.dateBegine = dateBegine;
        this.dateEnd = dateEnd;
        this.mrMs = mrMs;
        this.certType = certType;
    }

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
}
