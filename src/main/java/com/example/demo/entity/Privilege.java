package com.example.demo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Privileges")
public class Privilege {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "privilege_id")
    private Integer privilegeId;

    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @Column(name = "description", columnDefinition = "LONGTEXT", nullable = false)
    private String description;

    @Column(name = "required_reputation", nullable = false)
    private Integer requiredReputation;

    public Privilege() {
    }

    public Integer getPrivilegeId() {
        return privilegeId;
    }

    public void setPrivilegeId(Integer privilegeId) {
        this.privilegeId = privilegeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getRequiredReputation() {
        return requiredReputation;
    }

    public void setRequiredReputation(Integer requiredReputation) {
        this.requiredReputation = requiredReputation;
    }
}
