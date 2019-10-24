package ar.edu.um.serviciosya.app.service.dto;
import io.swagger.annotations.ApiModel;
import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import ar.edu.um.serviciosya.app.domain.enumeration.Gender;

/**
 * A DTO for the {@link ar.edu.um.serviciosya.app.domain.Offerer} entity.
 */
@ApiModel(description = "The Offerer entity.")
public class OffererDTO implements Serializable {

    private Long id;

    @NotNull
    private Gender gender;

    @NotNull
    private Long phoneNumber;

    @NotNull
    private ZonedDateTime birthday;

    private Float reputation;


    private Long locationId;

    private String locationStreetAddress;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(Long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public ZonedDateTime getBirthday() {
        return birthday;
    }

    public void setBirthday(ZonedDateTime birthday) {
        this.birthday = birthday;
    }

    public Float getReputation() {
        return reputation;
    }

    public void setReputation(Float reputation) {
        this.reputation = reputation;
    }

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }

    public String getLocationStreetAddress() {
        return locationStreetAddress;
    }

    public void setLocationStreetAddress(String locationStreetAddress) {
        this.locationStreetAddress = locationStreetAddress;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        OffererDTO offererDTO = (OffererDTO) o;
        if (offererDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), offererDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "OffererDTO{" +
            "id=" + getId() +
            ", gender='" + getGender() + "'" +
            ", phoneNumber=" + getPhoneNumber() +
            ", birthday='" + getBirthday() + "'" +
            ", reputation=" + getReputation() +
            ", location=" + getLocationId() +
            ", location='" + getLocationStreetAddress() + "'" +
            "}";
    }
}
