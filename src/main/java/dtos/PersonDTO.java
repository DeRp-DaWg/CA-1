/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

import entities.Person;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author tha
 */
public class PersonDTO {
    private long id;
    private String email;
    private String firstName;
    private String lastName;
    private String password;
    private String phone;
    private String description;
    private String streetName;
    private String streetInfo;
    
    public PersonDTO(String email, String firstName, String lastName, String password, String phone, String description, String streetName, String streetInfo) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.phone = phone;
        this.description = description;
        this.streetName = streetName;
        this.streetInfo = streetInfo;
    }
    
    public static List<PersonDTO> getDtos(List<Person> persons){
        List<PersonDTO> personDTOs = new ArrayList();
        persons.forEach(person->personDTOs.add(new PersonDTO(person)));
        return personDTOs;
    }


    public PersonDTO(Person person) {
        if(person.getId() != null)
            this.id = person.getId();
        this.email = person.getEmail();
        this.firstName = person.getFirstName();
        this.lastName = person.getLastName();
        this.password = person.getPassword();
        this.phone = person.getPhone();
        this.description = person.getDescription();
        this.streetName = person.getStreetName();
        this.streetInfo = person.getStreetInfo();
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
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
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getPhone() {
        return phone;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getStreetName() {
        return streetName;
    }
    
    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }
    
    public String getStreetInfo() {
        return streetInfo;
    }
    
    public void setStreetInfo(String streetInfo) {
        this.streetInfo = streetInfo;
    }
    
    @Override
    public String toString() {
        return "PersonDTO{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", password='" + password + '\'' +
                ", phone='" + phone + '\'' +
                ", description='" + description + '\'' +
                ", streetName='" + streetName + '\'' +
                ", streetInfo='" + streetInfo + '\'' +
                '}';
    }
}
