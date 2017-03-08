package hib.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import hib.restEntity.CreateCustomer;
import org.hibernate.validator.constraints.Email;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "customer")
public class Customer implements Serializable {
    private static final long serialVersionUID = -3965610784506338772L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotNull
    private String name;
    private Integer phone;
    @Email
    private String email;
    @OneToMany(mappedBy = "customerId")
    @JsonIgnore
    private Set<UserToRole> userToRoles = new HashSet<>();

    protected Customer() {
    }

    public Customer(CreateCustomer createCustomer) {
        this.name = createCustomer.getName();
        this.phone = createCustomer.getPhone();
        this.email = createCustomer.getEmail();
    }

    public Customer(String name, Integer phone, String email) {
        this.name = name;
        this.phone = phone;
        this.email = email;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPhone() {
        return phone;
    }

    public void setPhone(Integer phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<UserToRole> getUserToRoles() {
        return userToRoles;
    }

    public void setUserToRoles(Set<UserToRole> userToRoles) {
        this.userToRoles = userToRoles;
    }
}
