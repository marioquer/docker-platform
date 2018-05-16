package cn.marioquer.dockerplatform.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "server", schema = "docker_platform", catalog = "")
public class ServerEntity {
    private int id;
    private int ownerId;
    private String name;
    private String ip;
    private String platform;
    private String dockerVersion;
    private String cpu;
    private String memory;
    private String swarmId;
    private String swarmRole;
    private String uname;
    private String password;

    public ServerEntity() {

    }

    public ServerEntity(int ownerId, String name, String ip, String platform, String dockerVersion, String cpu, String memory, String uname, String password) {
        this.ownerId = ownerId;
        this.name = name;
        this.ip = ip;
        this.platform = platform;
        this.dockerVersion = dockerVersion;
        this.cpu = cpu;
        this.memory = memory;
        this.uname = uname;
        this.password = password;
    }

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
    @Column(name = "name", nullable = false, length = 255)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "ip", nullable = false, length = 255)
    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    @Basic
    @Column(name = "platform", nullable = false, length = 255)
    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    @Basic
    @Column(name = "docker_version", nullable = false, length = 255)
    public String getDockerVersion() {
        return dockerVersion;
    }

    public void setDockerVersion(String dockerVersion) {
        this.dockerVersion = dockerVersion;
    }

    @Basic
    @Column(name = "cpu", nullable = false, length = 255)
    public String getCpu() {
        return cpu;
    }

    public void setCpu(String cpu) {
        this.cpu = cpu;
    }

    @Basic
    @Column(name = "memory", nullable = false, length = 255)
    public String getMemory() {
        return memory;
    }

    public void setMemory(String memory) {
        this.memory = memory;
    }

    @Basic
    @Column(name = "swarm_id", nullable = true, length = 255)
    public String getSwarmId() {
        return swarmId;
    }

    public void setSwarmId(String swarmId) {
        this.swarmId = swarmId;
    }

    @Basic
    @Column(name = "swarm_role", nullable = true, length = 255)
    public String getSwarmRole() {
        return swarmRole;
    }

    public void setSwarmRole(String swarmRole) {
        this.swarmRole = swarmRole;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ServerEntity that = (ServerEntity) o;
        return id == that.id &&
                ownerId == that.ownerId &&
                Objects.equals(name, that.name) &&
                Objects.equals(ip, that.ip) &&
                Objects.equals(platform, that.platform) &&
                Objects.equals(dockerVersion, that.dockerVersion) &&
                Objects.equals(cpu, that.cpu) &&
                Objects.equals(memory, that.memory) &&
                Objects.equals(swarmId, that.swarmId) &&
                Objects.equals(swarmRole, that.swarmRole);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, ownerId, name, ip, platform, dockerVersion, cpu, memory, swarmId, swarmRole);
    }

    @Basic
    @Column(name = "uname", nullable = false, length = 255)
    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    @Basic
    @Column(name = "password", nullable = false, length = 255)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
