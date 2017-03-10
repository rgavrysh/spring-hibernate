package hib.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;

@Entity
@Table(name = "role")
public class Role implements Serializable, GrantedAuthority {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Integer id;
    @NotNull
    private String name;
    private String grants;

    @OneToMany(mappedBy = "roleId", fetch = FetchType.EAGER)
    @JsonIgnore
    private Collection<UserToRole> userToRoles = new HashSet<>();

    public Role() {
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Collection<UserToRole> getUserToRoles() {
        return userToRoles;
    }

    @Override
    public String getAuthority() {
        return name;
    }
}
