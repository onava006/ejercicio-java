package com.smartjob.ejerciciojava.infrastructure.adapters.input.rest.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * ContactPhone
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen")
public class ContactPhone {

  private String number;

  private String citycode;

  private String contrycode;

  public ContactPhone() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public ContactPhone(String number, String citycode, String contrycode) {
    this.number = number;
    this.citycode = citycode;
    this.contrycode = contrycode;
  }

  public ContactPhone number(String number) {
    this.number = number;
    return this;
  }

  /**
   * Get number
   * @return number
  */
  @NotNull 
  @Schema(name = "number", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("number")
  public String getNumber() {
    return number;
  }

  public void setNumber(String number) {
    this.number = number;
  }

  public ContactPhone citycode(String citycode) {
    this.citycode = citycode;
    return this;
  }

  /**
   * Get citycode
   * @return citycode
  */
  @NotNull 
  @Schema(name = "citycode", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("citycode")
  public String getCitycode() {
    return citycode;
  }

  public void setCitycode(String citycode) {
    this.citycode = citycode;
  }

  public ContactPhone contrycode(String contrycode) {
    this.contrycode = contrycode;
    return this;
  }

  /**
   * Get contrycode
   * @return contrycode
  */
  @NotNull 
  @Schema(name = "contrycode", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("contrycode")
  public String getContrycode() {
    return contrycode;
  }

  public void setContrycode(String contrycode) {
    this.contrycode = contrycode;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ContactPhone contactPhone = (ContactPhone) o;
    return Objects.equals(this.number, contactPhone.number) &&
        Objects.equals(this.citycode, contactPhone.citycode) &&
        Objects.equals(this.contrycode, contactPhone.contrycode);
  }

  @Override
  public int hashCode() {
    return Objects.hash(number, citycode, contrycode);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ContactPhone {\n");
    sb.append("    number: ").append(toIndentedString(number)).append("\n");
    sb.append("    citycode: ").append(toIndentedString(citycode)).append("\n");
    sb.append("    contrycode: ").append(toIndentedString(contrycode)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

