package hib.restEntity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateCustomer {

    private String name;
    private Integer phone;
    private String email;

    public CreateCustomer(@JsonProperty(value = "name") String name,
                          @JsonProperty(value = "phone") Integer phone,
                          @JsonProperty(value = "email") String email) {
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public Integer getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }
}
