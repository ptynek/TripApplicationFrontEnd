package com.tripapplicationf.tripapplicationf.domain;

import com.vaadin.flow.shared.Registration;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PassengersDto {

    private long id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String mail;
    private boolean active;

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getPassengerForCombobox() {
            String result = id + ".    " + firstName + " " + lastName;
        return result;
    }

    public PassengersDto(String firstName, String lastName, String phoneNumber, String mail) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.mail = mail;
    }
}
