package hib.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "user2role")
@IdClass(UserToRoleId.class)
public class UserToRole implements Serializable {

    private final static long serialVersionUID = 1L;

    @Id
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customerId;
    @Id
    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role roleId;

    public UserToRole() {
    }

    public UserToRole(Customer customerId, Role roleId) {
        this.customerId = customerId;
        this.roleId = roleId;
    }

    public Customer getCustomerId() {
        return customerId;
    }

    public Role getRoleId() {
        return roleId;
    }

    public void setCustomerId(Customer customerId) {
        this.customerId = customerId;
    }

    public void setRoleId(Role roleId) {
        this.roleId = roleId;
    }
}
