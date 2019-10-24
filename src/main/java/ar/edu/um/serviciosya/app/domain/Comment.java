package ar.edu.um.serviciosya.app.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * The Comment entity.
 */
@Entity
@Table(name = "comment")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Comment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Min(value = 0)
    @Max(value = 5)
    @Column(name = "qualification", nullable = false)
    private Integer qualification;

    @Column(name = "commentary")
    private String commentary;

    @ManyToOne
    @JsonIgnoreProperties("comments")
    private Offerer offerer;

    @ManyToOne
    @JsonIgnoreProperties("comments")
    private Person person;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getQualification() {
        return qualification;
    }

    public Comment qualification(Integer qualification) {
        this.qualification = qualification;
        return this;
    }

    public void setQualification(Integer qualification) {
        this.qualification = qualification;
    }

    public String getCommentary() {
        return commentary;
    }

    public Comment commentary(String commentary) {
        this.commentary = commentary;
        return this;
    }

    public void setCommentary(String commentary) {
        this.commentary = commentary;
    }

    public Offerer getOfferer() {
        return offerer;
    }

    public Comment offerer(Offerer offerer) {
        this.offerer = offerer;
        return this;
    }

    public void setOfferer(Offerer offerer) {
        this.offerer = offerer;
    }

    public Person getPerson() {
        return person;
    }

    public Comment person(Person person) {
        this.person = person;
        return this;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Comment)) {
            return false;
        }
        return id != null && id.equals(((Comment) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Comment{" +
            "id=" + getId() +
            ", qualification=" + getQualification() +
            ", commentary='" + getCommentary() + "'" +
            "}";
    }
}
