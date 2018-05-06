package cn.marioquer.dockerplatform.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "swarm", schema = "docker_platform", catalog = "")
public class SwarmEntity {
    private String swarmId;
    private int ownerId;
    private String apiVersion;
    private String name;
    private Timestamp created;

    @Id
    @Column(name = "swarm_id", nullable = false, length = 255)
    public String getSwarmId() {
        return swarmId;
    }

    public void setSwarmId(String swarmId) {
        this.swarmId = swarmId;
    }

    @Basic
    @Column(name = "owner_id", nullable = false)
    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    @Basic
    @Column(name = "api_version", nullable = false, length = 255)
    public String getApiVersion() {
        return apiVersion;
    }

    public void setApiVersion(String apiVersion) {
        this.apiVersion = apiVersion;
    }

    @Basic
    @Column(name = "name", nullable = false, length = 255)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "created", nullable = false)
    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SwarmEntity that = (SwarmEntity) o;
        return ownerId == that.ownerId &&
                Objects.equals(swarmId, that.swarmId) &&
                Objects.equals(apiVersion, that.apiVersion) &&
                Objects.equals(name, that.name) &&
                Objects.equals(created, that.created);
    }

    @Override
    public int hashCode() {

        return Objects.hash(swarmId, ownerId, apiVersion, name, created);
    }
}
