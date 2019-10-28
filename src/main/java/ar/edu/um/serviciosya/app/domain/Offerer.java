package ar.edu.um.serviciosya.app.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

import ar.edu.um.serviciosya.app.domain.enumeration.Gender;

/**
 * The Offerer entity.
 */
@Entity
@Table(name = "offerer")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Offerer implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    private User user;
    
    @Id
    private Long id;

	@NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "gender", nullable = false)
    private Gender gender;

    @NotNull
    @Column(name = "phone_number", nullable = false)
    private Long phoneNumber;

    @NotNull
    @Column(name = "birthday", nullable = false)
    private ZonedDateTime birthday;

    @Column(name = "reputation")
    private Float reputation;

    @OneToMany(mappedBy = "offerer")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Profession> professions = new HashSet<>();

    @OneToMany(mappedBy = "offerer")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Turn> turns = new HashSet<>();

    @OneToMany(mappedBy = "offerer")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Comment> comments = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("offerers")
    private Location location;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove

    public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Gender getGender() {
        return gender;
    }

    public Offerer gender(Gender gender) {
        this.gender = gender;
        return this;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Long getPhoneNumber() {
        return phoneNumber;
    }

    public Offerer phoneNumber(Long phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public void setPhoneNumber(Long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public ZonedDateTime getBirthday() {
        return birthday;
    }

    public Offerer birthday(ZonedDateTime birthday) {
        this.birthday = birthday;
        return this;
    }

    public void setBirthday(ZonedDateTime birthday) {
        this.birthday = birthday;
    }

    public Float getReputation() {
        return reputation;
    }

    public Offerer reputation(Float reputation) {
        this.reputation = reputation;
        return this;
    }

    public void setReputation(Float reputation) {
        this.reputation = reputation;
    }

    public Set<Profession> getProfessions() {
        return professions;
    }

    public Offerer professions(Set<Profession> professions) {
        this.professions = professions;
        return this;
    }

    public Offerer addProfession(Profession profession) {
        this.professions.add(profession);
        profession.setOfferer(this);
        return this;
    }

    public Offerer removeProfession(Profession profession) {
        this.professions.remove(profession);
        profession.setOfferer(null);
        return this;
    }

    public void setProfessions(Set<Profession> professions) {
        this.professions = professions;
    }

    public Set<Turn> getTurns() {
        return turns;
    }

    public Offerer turns(Set<Turn> turns) {
        this.turns = turns;
        return this;
    }

    public Offerer addTurn(Turn turn) {
        this.turns.add(turn);
        turn.setOfferer(this);
        return this;
    }

    public Offerer removeTurn(Turn turn) {
        this.turns.remove(turn);
        turn.setOfferer(null);
        return this;
    }

    public void setTurns(Set<Turn> turns) {
        this.turns = turns;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public Offerer comments(Set<Comment> comments) {
        this.comments = comments;
        return this;
    }

    public Offerer addComment(Comment comment) {
        this.comments.add(comment);
        comment.setOfferer(this);
        return this;
    }

    public Offerer removeComment(Comment comment) {
        this.comments.remove(comment);
        comment.setOfferer(null);
        return this;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    public Location getLocation() {
        return location;
    }

    public Offerer location(Location location) {
        this.location = location;
        return this;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Offerer)) {
            return false;
        }
        return id != null && id.equals(((Offerer) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Offerer{" +
            "id=" + getId() +
            ", gender='" + getGender() + "'" +
            ", phoneNumber=" + getPhoneNumber() +
            ", birthday='" + getBirthday() + "'" +
            ", reputation=" + getReputation() +
            "}";
    }
}
