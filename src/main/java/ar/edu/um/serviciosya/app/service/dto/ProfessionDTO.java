package ar.edu.um.serviciosya.app.service.dto;
import io.swagger.annotations.ApiModel;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link ar.edu.um.serviciosya.app.domain.Profession} entity.
 */
@ApiModel(description = "The Profession entity.")
public class ProfessionDTO implements Serializable {

    private Long id;

    private String name;

    private String professionDetail;


    private Long offererId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfessionDetail() {
        return professionDetail;
    }

    public void setProfessionDetail(String professionDetail) {
        this.professionDetail = professionDetail;
    }

    public Long getOffererId() {
        return offererId;
    }

    public void setOffererId(Long offererId) {
        this.offererId = offererId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ProfessionDTO professionDTO = (ProfessionDTO) o;
        if (professionDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), professionDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProfessionDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", professionDetail='" + getProfessionDetail() + "'" +
            ", offerer=" + getOffererId() +
            "}";
    }
}
