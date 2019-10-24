package ar.edu.um.serviciosya.app.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;

import ar.edu.um.serviciosya.app.domain.enumeration.TurnState;

/**
 * The Turn entity.
 */
@Entity
@Table(name = "turn")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Turn implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "order_date")
    private ZonedDateTime orderDate;

    @Column(name = "work_date")
    private ZonedDateTime workDate;

    @Column(name = "cost", precision = 21, scale = 2)
    private BigDecimal cost;

    
    @Lob
    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "available")
    private Boolean available;

    @Enumerated(EnumType.STRING)
    @Column(name = "turn_state")
    private TurnState turnState;

    @OneToOne
    @JoinColumn(unique = true)
    private Transaction transaction;

    @OneToOne
    @JoinColumn(unique = true)
    private Location location;

    @ManyToOne
    @JsonIgnoreProperties("turns")
    private Offerer offerer;

    @ManyToOne
    @JsonIgnoreProperties("turns")
    private Person person;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getOrderDate() {
        return orderDate;
    }

    public Turn orderDate(ZonedDateTime orderDate) {
        this.orderDate = orderDate;
        return this;
    }

    public void setOrderDate(ZonedDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public ZonedDateTime getWorkDate() {
        return workDate;
    }

    public Turn workDate(ZonedDateTime workDate) {
        this.workDate = workDate;
        return this;
    }

    public void setWorkDate(ZonedDateTime workDate) {
        this.workDate = workDate;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public Turn cost(BigDecimal cost) {
        this.cost = cost;
        return this;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public String getDescription() {
        return description;
    }

    public Turn description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean isAvailable() {
        return available;
    }

    public Turn available(Boolean available) {
        this.available = available;
        return this;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public TurnState getTurnState() {
        return turnState;
    }

    public Turn turnState(TurnState turnState) {
        this.turnState = turnState;
        return this;
    }

    public void setTurnState(TurnState turnState) {
        this.turnState = turnState;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public Turn transaction(Transaction transaction) {
        this.transaction = transaction;
        return this;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    public Location getLocation() {
        return location;
    }

    public Turn location(Location location) {
        this.location = location;
        return this;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Offerer getOfferer() {
        return offerer;
    }

    public Turn offerer(Offerer offerer) {
        this.offerer = offerer;
        return this;
    }

    public void setOfferer(Offerer offerer) {
        this.offerer = offerer;
    }

    public Person getPerson() {
        return person;
    }

    public Turn person(Person person) {
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
        if (!(o instanceof Turn)) {
            return false;
        }
        return id != null && id.equals(((Turn) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Turn{" +
            "id=" + getId() +
            ", orderDate='" + getOrderDate() + "'" +
            ", workDate='" + getWorkDate() + "'" +
            ", cost=" + getCost() +
            ", description='" + getDescription() + "'" +
            ", available='" + isAvailable() + "'" +
            ", turnState='" + getTurnState() + "'" +
            "}";
    }
}
