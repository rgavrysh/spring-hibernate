package hib.model;

import java.io.Serializable;

public class UserToRoleId implements Serializable {
    private final static long serialVersionUID = 1L;
    private Integer customerId;
    private Integer roleId;

    public UserToRoleId() {
    }

    public UserToRoleId(Integer userId, Integer roleId) {
        this.customerId = userId;
        this.roleId = roleId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    @Override
    public String toString() {
        return "User2Role:{\"customer_id\": \"" + this.getCustomerId() + "\", \"role_id\": \"" + this.getRoleId() + "\"}";
    }
}
