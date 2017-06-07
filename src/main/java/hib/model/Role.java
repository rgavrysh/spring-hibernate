package hib.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;

@Entity
@Table(name = "role")
public class Role implements GrantedAuthority {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotNull
    private String name;
    private String grants;

    @ManyToMany(mappedBy = "role")
    @JsonBackReference
    private Collection<Customer> customer = new HashSet<>();

    public Role() {
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Collection<Customer> getCustomer() {
        return customer;
    }

    @Override
    public String getAuthority() {
        return name;
    }
}
