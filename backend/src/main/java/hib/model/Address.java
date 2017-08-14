package hib.model;

import hib.restEntity.CreateAddress;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "address")
public class Address implements Serializable {

    private static final long serialVersionUID = -3965610784506338772L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "city")
    @NotNull
    private String city;
    @Column(name = "street")
    @NotNull
    private String street;
    @Column(name = "building_number")
    @NotNull
    private Integer buildingNumber;
    @Column(name = "postal_code")
    @NotNull
    private Integer postalCode;

    protected Address() {
    }

    public Address(CreateAddress createAddress) {
        this.city = createAddress.getCity();
        this.street = createAddress.getStreet();
        this.buildingNumber = createAddress.getBuildingNumber();
        this.postalCode = createAddress.getPostalCode();
    }

    public Address(String city, String street, Integer buildingNumber, Integer postalCode) {
        this.city = city;
        this.street = street;
        this.buildingNumber = buildingNumber;
        this.postalCode = postalCode;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public Integer getBuildingNumber() {
        return buildingNumber;
    }

    public void setBuildingNumber(Integer buildingNumber) {
        this.buildingNumber = buildingNumber;
    }

    public Integer getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(Integer postalCode) {
        this.postalCode = postalCode;
    }

    @Override
    public String toString() {
        return "Address:{\"id\": \"" + this.getId() +
                "\", \"city\": \"" + this.getCity() +
                "\", \"street\": \"" + this.getStreet() +
                "\", \"building_number\": \"" + this.getBuildingNumber() +
                "\", \"postal_code\": \"" + this.getPostalCode() + "\"}";

    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(id)
                .append(city)
                .append(street)
                .append(buildingNumber)
                .append(postalCode)
                .toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Address) {
            Address that = (Address) obj;
            return new EqualsBuilder()
                    .append(this.id, that.id)
                    .append(this.city, that.city)
                    .append(this.street, that.street)
                    .append(this.buildingNumber, that.buildingNumber)
                    .append(this.postalCode, that.postalCode)
                    .isEquals();
        }
        return false;
    }
}
