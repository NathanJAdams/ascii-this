package com.repocleaner.clean.website.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "Transformation")
public class TransformationEntity {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(updatable = false, nullable = false)
    private UUID id;

    @ManyToOne
    private BranchEntity branch;
    @Enumerated(EnumType.STRING)
    private TransformationOrigin origin;
    @Column(nullable = false, insertable = false, updatable = false)
    private LocalDateTime requested;
    @Column
    private LocalDateTime started;
    @Column
    private LocalDateTime completed;
    @Column
    private String gitDiff;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public BranchEntity getBranch() {
        return branch;
    }

    public void setBranch(BranchEntity branch) {
        this.branch = branch;
    }

    public TransformationOrigin getOrigin() {
        return origin;
    }

    public void setOrigin(TransformationOrigin origin) {
        this.origin = origin;
    }

    public LocalDateTime getRequested() {
        return requested;
    }

    public void setRequested(LocalDateTime requested) {
        this.requested = requested;
    }

    public LocalDateTime getStarted() {
        return started;
    }

    public void setStarted(LocalDateTime started) {
        this.started = started;
    }

    public LocalDateTime getCompleted() {
        return completed;
    }

    public void setCompleted(LocalDateTime completed) {
        this.completed = completed;
    }

    public String getGitDiff() {
        return gitDiff;
    }

    public void setGitDiff(String gitDiff) {
        this.gitDiff = gitDiff;
    }
}
