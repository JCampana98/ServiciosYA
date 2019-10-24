package ar.edu.um.serviciosya.app.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * The Location entity.
 */
@Entity
@Table(name = "location")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Location implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "street_address", nullable = false)
    private String streetAddress;

    @NotNull
    @Column(name = "street_number", nullable = false)
    private Integer streetNumber;

    @Column(name = "flat_number")
    private Integer flatNumber;

    @NotNull
    @Column(name = "safe_zone", nullable = false)
    private Boolean safeZone;

    @NotNull
    @Column(name = "country", nullable = false)
    private String country;

    @NotNull
    @Column(name = "zip_code", nullable = false)
    private String zipCode;

    @NotNull
    @Column(name = "city", nullable = false)
    private String city;

    @NotNull
    @Column(name = "province", nullable = false)
    private String province;

    @Column(name = "department")
    private String department;

    @OneToOne
    @JoinColumn(unique = true)
    private Coordinate coordinate;

    @OneToMany(mappedBy = "location")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Person> persons = new HashSet<>();

    @OneToMany(mappedBy = "location")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Offerer> offerers = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public Location streetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
        return this;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public Integer getStreetNumber() {
        return streetNumber;
    }

    public Location streetNumber(Integer streetNumber) {
        this.streetNumber = streetNumber;
        return this;
    }

    public void setStreetNumber(Integer streetNumber) {
        this.streetNumber = streetNumber;
    }

    public Integer getFlatNumber() {
        return flatNumber;
    }

    public Location flatNumber(Integer flatNumber) {
        this.flatNumber = flatNumber;
        return this;
    }

    public void setFlatNumber(Integer flatNumber) {
        this.flatNumber = flatNumber;
    }

    public Boolean isSafeZone() {
        return safeZone;
    }

    public Location safeZone(Boolean safeZone) {
        this.safeZone = safeZone;
        return this;
    }

    public void setSafeZone(Boolean safeZone) {
        this.safeZone = safeZone;
    }

    public String getCountry() {
        return country;
    }

    public Location country(String country) {
        this.country = country;
        return this;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getZipCode() {
        return zipCode;
    }

    public Location zipCode(String zipCode) {
        this.zipCode = zipCode;
        return this;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCity() {
        return city;
    }

    public Location city(String city) {
        this.city = city;
        return this;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public Location province(String province) {
        this.province = province;
        return this;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getDepartment() {
        return department;
    }

    public Location department(String department) {
        this.department = department;
        return this;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public Location coordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
        return this;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public Set<Person> getPersons() {
        return persons;
    }

    public Location persons(Set<Person> people) {
        this.persons = people;
        return this;
    }

    public Location addPersons(Person person) {
        this.persons.add(person);
        person.setLocation(this);
        return this;
    }

    public Location removePersons(Person person) {
        this.persons.remove(person);
        person.setLocation(null);
        return this;
    }

    public void setPersons(Set<Person> people) {
        this.persons = people;
    }

    public Set<Offerer> getOfferers() {
        return offerers;
    }

    public Location offerers(Set<Offerer> offerers) {
        this.offerers = offerers;
        return this;
    }

    public Location addOfferers(Offerer offerer) {
        this.offerers.add(offerer);
        offerer.setLocation(this);
        return this;
    }

    public Location removeOfferers(Offerer offerer) {
        this.offerers.remove(offerer);
        offerer.setLocation(null);
        return this;
    }

    public void setOfferers(Set<Offerer> offerers) {
        this.offerers = offerers;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Location)) {
            return false;
        }
        return id != null && id.equals(((Location) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Location{" +
            "id=" + getId() +
            ", streetAddress='" + getStreetAddress() + "'" +
            ", streetNumber=" + getStreetNumber() +
            ", flatNumber=" + getFlatNumber() +
            ", safeZone='" + isSafeZone() + "'" +
            ", country='" + getCountry() + "'" +
            ", zipCode='" + getZipCode() + "'" +
            ", city='" + getCity() + "'" +
            ", province='" + getProvince() + "'" +
            ", department='" + getDepartment() + "'" +
            "}";
    }
}
