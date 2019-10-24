package ar.edu.um.serviciosya.app.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * The Profession entity.
 */
@Entity
@Table(name = "profession")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Profession implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "profession_detail")
    private String professionDetail;

    @ManyToOne
    @JsonIgnoreProperties("professions")
    private Offerer offerer;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Profession name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfessionDetail() {
        return professionDetail;
    }

    public Profession professionDetail(String professionDetail) {
        this.professionDetail = professionDetail;
        return this;
    }

    public void setProfessionDetail(String professionDetail) {
        this.professionDetail = professionDetail;
    }

    public Offerer getOfferer() {
        return offerer;
    }

    public Profession offerer(Offerer offerer) {
        this.offerer = offerer;
        return this;
    }

    public void setOfferer(Offerer offerer) {
        this.offerer = offerer;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Profession)) {
            return false;
        }
        return id != null && id.equals(((Profession) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Profession{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", professionDetail='" + getProfessionDetail() + "'" +
            "}";
    }
}
