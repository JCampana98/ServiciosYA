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
 * The Person entity.
 */
@Entity
@Table(name = "person")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Person implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    private Long id;
    
    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    private User user;

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

    @OneToMany(mappedBy = "person")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Turn> turns = new HashSet<>();

    @OneToMany(mappedBy = "person")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Comment> comments = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("persons")
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

    public Person gender(Gender gender) {
        this.gender = gender;
        return this;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Long getPhoneNumber() {
        return phoneNumber;
    }

    public Person phoneNumber(Long phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public void setPhoneNumber(Long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public ZonedDateTime getBirthday() {
        return birthday;
    }

    public Person birthday(ZonedDateTime birthday) {
        this.birthday = birthday;
        return this;
    }

    public void setBirthday(ZonedDateTime birthday) {
        this.birthday = birthday;
    }

    public Set<Turn> getTurns() {
        return turns;
    }

    public Person turns(Set<Turn> turns) {
        this.turns = turns;
        return this;
    }

    public Person addTurn(Turn turn) {
        this.turns.add(turn);
        turn.setPerson(this);
        return this;
    }

    public Person removeTurn(Turn turn) {
        this.turns.remove(turn);
        turn.setPerson(null);
        return this;
    }

    public void setTurns(Set<Turn> turns) {
        this.turns = turns;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public Person comments(Set<Comment> comments) {
        this.comments = comments;
        return this;
    }

    public Person addComment(Comment comment) {
        this.comments.add(comment);
        comment.setPerson(this);
        return this;
    }

    public Person removeComment(Comment comment) {
        this.comments.remove(comment);
        comment.setPerson(null);
        return this;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    public Location getLocation() {
        return location;
    }

    public Person location(Location location) {
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
        if (!(o instanceof Person)) {
            return false;
        }
        return id != null && id.equals(((Person) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Person{" +
            "id=" + getId() +
            ", gender='" + getGender() + "'" +
            ", phoneNumber=" + getPhoneNumber() +
            ", birthday='" + getBirthday() + "'" +
            "}";
    }
}
