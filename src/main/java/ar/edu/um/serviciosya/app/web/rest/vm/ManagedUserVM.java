package ar.edu.um.serviciosya.app.web.rest.vm;

import ar.edu.um.serviciosya.app.domain.enumeration.Gender;
import ar.edu.um.serviciosya.app.service.dto.UserDTO;

import java.time.LocalDate;
import java.time.ZonedDateTime;

import javax.validation.constraints.Size;

/**
 * View Model extending the UserDTO, which is meant to be used in the user management UI.
 */
public class ManagedUserVM extends UserDTO {

    public static final int PASSWORD_MIN_LENGTH = 4;

    public static final int PASSWORD_MAX_LENGTH = 100;

    @Size(min = PASSWORD_MIN_LENGTH, max = PASSWORD_MAX_LENGTH)
    private String password;
    
    private Gender gender;

    private Long phoneNumber;

    private LocalDate birthday;
    
    private Boolean isOfferer;

    public ManagedUserVM() {
        // Empty constructor needed for Jackson.
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

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public Boolean getIsOfferer() {
        return isOfferer;
    }

    public void setIsOfferer(Boolean isOfferer) {
        this.isOfferer = isOfferer;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "ManagedUserVM{" + super.toString() + "} ";
    }
}
