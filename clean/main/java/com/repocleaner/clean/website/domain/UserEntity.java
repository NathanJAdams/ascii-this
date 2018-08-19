package com.repocleaner.clean.website.domain;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "User", uniqueConstraints = {@UniqueConstraint(columnNames = {"REPO_HOST_ID", "NAME"})})
public class UserEntity {
    @Id
    @GeneratedValue
    private int id;

    @ManyToOne(optional = false)
    private RepoHostEntity repoHost;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false, updatable = false, insertable = false)
    private LocalDateTime registered;
    @Column
    private long tokens;
    @Column
    private String repoRegex;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public RepoHostEntity getRepoHost() {
        return repoHost;
    }

    public void setRepoHost(RepoHostEntity repoHost) {
        this.repoHost = repoHost;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getRegistered() {
        return registered;
    }

    public void setRegistered(LocalDateTime registered) {
        this.registered = registered;
    }

    public long getTokens() {
        return tokens;
    }

    public void setTokens(long tokens) {
        this.tokens = tokens;
    }

    public String getRepoRegex() {
        return repoRegex;
    }

    public void setRepoRegex(String repoRegex) {
        this.repoRegex = repoRegex;
    }
}
