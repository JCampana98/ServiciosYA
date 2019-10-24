package ar.edu.um.serviciosya.app.service.dto;
import io.swagger.annotations.ApiModel;
import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import javax.persistence.Lob;
import ar.edu.um.serviciosya.app.domain.enumeration.TurnState;

/**
 * A DTO for the {@link ar.edu.um.serviciosya.app.domain.Turn} entity.
 */
@ApiModel(description = "The Turn entity.")
public class TurnDTO implements Serializable {

    private Long id;

    private ZonedDateTime orderDate;

    private ZonedDateTime workDate;

    private BigDecimal cost;

    
    @Lob
    private String description;

    private Boolean available;

    private TurnState turnState;


    private Long transactionId;

    private Long locationId;

    private Long offererId;

    private Long personId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(ZonedDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public ZonedDateTime getWorkDate() {
        return workDate;
    }

    public void setWorkDate(ZonedDateTime workDate) {
        this.workDate = workDate;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean isAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public TurnState getTurnState() {
        return turnState;
    }

    public void setTurnState(TurnState turnState) {
        this.turnState = turnState;
    }

    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }

    public Long getOffererId() {
        return offererId;
    }

    public void setOffererId(Long offererId) {
        this.offererId = offererId;
    }

    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TurnDTO turnDTO = (TurnDTO) o;
        if (turnDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), turnDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TurnDTO{" +
            "id=" + getId() +
            ", orderDate='" + getOrderDate() + "'" +
            ", workDate='" + getWorkDate() + "'" +
            ", cost=" + getCost() +
            ", description='" + getDescription() + "'" +
            ", available='" + isAvailable() + "'" +
            ", turnState='" + getTurnState() + "'" +
            ", transaction=" + getTransactionId() +
            ", location=" + getLocationId() +
            ", offerer=" + getOffererId() +
            ", person=" + getPersonId() +
            "}";
    }
}
