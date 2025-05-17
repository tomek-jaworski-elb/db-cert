package com.jaworski.dbcert.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.sql.Blob;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private int id;
    private String name;
    private String lastName;
    private String courseNo;
    private Date dateBegine;
    private Date dateEnd;
    private String mrMs;
    private String certType;
    private transient Blob photo;
    private String instructors;

}
