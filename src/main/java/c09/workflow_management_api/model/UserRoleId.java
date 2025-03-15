package c09.workflow_management_api.model;

import java.io.Serializable;
import java.util.Objects;

public class UserRoleId implements Serializable {
    private Long user;  // Phải trùng tên với trường @Id trong UserRole
    private Long role;

    public UserRoleId() {}

    public UserRoleId(Long user, Long role) {
        this.user = user;
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserRoleId that = (UserRoleId) o;
        return Objects.equals(user, that.user) && Objects.equals(role, that.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, role);
    }
}

