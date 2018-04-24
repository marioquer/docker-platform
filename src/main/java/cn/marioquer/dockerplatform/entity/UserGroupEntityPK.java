package cn.marioquer.dockerplatform.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

public class UserGroupEntityPK implements Serializable {
    private int groupId;
    private int userId;

    @Column(name = "group_id", nullable = false)
    @Id
    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    @Column(name = "user_id", nullable = false)
    @Id
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserGroupEntityPK that = (UserGroupEntityPK) o;
        return groupId == that.groupId &&
                userId == that.userId;
    }

    @Override
    public int hashCode() {

        return Objects.hash(groupId, userId);
    }
}
