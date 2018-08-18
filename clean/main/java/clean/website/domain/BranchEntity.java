package clean.website.domain;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "Branch", uniqueConstraints = {@UniqueConstraint(columnNames = {"REPO_ID", "NAME"})})
public class BranchEntity {
    @Id
    @GeneratedValue
    private long id;

    @ManyToOne(optional = false)
    private RepoEntity repo;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private LocalDateTime nextTransformation;
    @OneToMany(mappedBy = "branch")
    private List<TransformationEntity> transformations;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public RepoEntity getRepo() {
        return repo;
    }

    public void setRepo(RepoEntity repo) {
        this.repo = repo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getNextTransformation() {
        return nextTransformation;
    }

    public void setNextTransformation(LocalDateTime nextTransformation) {
        this.nextTransformation = nextTransformation;
    }

    public List<TransformationEntity> getTransformations() {
        return transformations;
    }

    public void setTransformations(List<TransformationEntity> transformations) {
        this.transformations = transformations;
    }
}
