package cn.marioquer.dockerplatform.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "service", schema = "docker_platform", catalog = "")
public class ServiceEntity {
    private String serviceId;
    private String name;
    private String stackName;
    private String image;
    private String mode;
    private String port;
    private Timestamp updated;
    private Timestamp created;

    @Id
    @Column(name = "service_id", nullable = false, length = 255)
    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
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
    @Column(name = "stack_name", nullable = true, length = 255)
    public String getStackName() {
        return stackName;
    }

    public void setStackName(String stackName) {
        this.stackName = stackName;
    }

    @Basic
    @Column(name = "image", nullable = false, length = 255)
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Basic
    @Column(name = "mode", nullable = false, length = 255)
    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    @Basic
    @Column(name = "port", nullable = false, length = 255)
    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    @Basic
    @Column(name = "updated", nullable = false)
    public Timestamp getUpdated() {
        return updated;
    }

    public void setUpdated(Timestamp updated) {
        this.updated = updated;
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
        ServiceEntity that = (ServiceEntity) o;
        return Objects.equals(serviceId, that.serviceId) &&
                Objects.equals(name, that.name) &&
                Objects.equals(stackName, that.stackName) &&
                Objects.equals(image, that.image) &&
                Objects.equals(mode, that.mode) &&
                Objects.equals(port, that.port) &&
                Objects.equals(updated, that.updated) &&
                Objects.equals(created, that.created);
    }

    @Override
    public int hashCode() {

        return Objects.hash(serviceId, name, stackName, image, mode, port, updated, created);
    }
}
