package com.smartjob.ejerciciojava.domain.model;

import java.sql.Date;
import java.util.List;

public class User {

    private String name;
    private String email;
    private String password;
    private List<Phone> phones;
    private Date creationDate;
    private Date lastModificationDate;
    boolean isActive;


}
