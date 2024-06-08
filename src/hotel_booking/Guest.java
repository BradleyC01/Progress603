/*
     * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
     * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hotel_booking;

import java.time.LocalDate;

/**
 *
 * @author shaikasif
 */
public class Guest {

    private String firstName;
    private String lastName;
    private final String email;
    private final String telephone;

    public Guest(String firstName, String lastName, LocalDate dateOfBirth, String email, String country, String telephone) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.telephone = telephone;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getTelephone() {
        return telephone;
    }

    public String getEmail() {
        return email;
    }

}
//No change needed