package hib.restEntity;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class CreateAddress implements Serializable {
    private static final long serialVersionUID = 1L;

    private String city;
    private String street;
    private Integer buildingNumber;
    private Integer postalCode;

    public CreateAddress() {
    }

    public CreateAddress(@JsonProperty(value = "city") String city,
                         @JsonProperty(value = "street") String street,
                         @JsonProperty(value = "building_number") Integer buildingNumber,
                         @JsonProperty(value = "postal_code") Integer postalCode) {
        this.city = city;
        this.street = street;
        this.buildingNumber = buildingNumber;
        this.postalCode = postalCode;
    }

    public String getCity() {
        return city;
    }

    public String getStreet() {
        return street;
    }

    public Integer getBuildingNumber() {
        return buildingNumber;
    }

    public Integer getPostalCode() {
        return postalCode;
    }
}
