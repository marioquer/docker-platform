package cn.marioquer.dockerplatform.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "user_group", schema = "docker_platform", catalog = "")
@IdClass(UserGroupEntityPK.class)
public class UserGroupEntity {
    private int groupId;
    private int userId;
    private String role;

    @Id
    @Column(name = "group_id", nullable = false)
    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    @Id
    @Column(name = "user_id", nullable = false)
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "role", nullable = false, length = 255)
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserGroupEntity that = (UserGroupEntity) o;
        return groupId == that.groupId &&
                userId == that.userId &&
                Objects.equals(role, that.role);
    }

    @Override
    public int hashCode() {

        return Objects.hash(groupId, userId, role);
    }
}
