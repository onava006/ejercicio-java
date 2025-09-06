package com.smartjob.ejerciciojava.domain.model;

public class Phone {

    private String number;
    private String cityCode;
    private String contryCode;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getCountryCode() {
        return contryCode;
    }

    public void setCountryCode(String contryCode) {
        this.contryCode = contryCode;
    }
}
