package com.repocleaner.clean.website.domain;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Repo", uniqueConstraints = {@UniqueConstraint(columnNames = {"OWNER_ID", "NAME"})})
public class RepoEntity {
    @Id
    @GeneratedValue
    private long id;

    @ManyToOne(optional = false)
    private UserEntity owner;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private LocalDateTime registeredDateTime;
    @Column
    private String apiKey;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public UserEntity getOwner() {
        return owner;
    }

    public void setOwner(UserEntity owner) {
        this.owner = owner;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getRegisteredDateTime() {
        return registeredDateTime;
    }

    public void setRegisteredDateTime(LocalDateTime registeredDateTime) {
        this.registeredDateTime = registeredDateTime;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }
}
