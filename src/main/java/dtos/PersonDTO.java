/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

import entities.Hobby;
import entities.Person;
import entities.Phone;

import java.util.*;

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
    private Set<String> phone;
//    private Map<String, String> hobbies;
    private Set<HobbyDTO> hobbies;
    private String streetName;
    private String streetAdditionalInfo;
    
    public PersonDTO(String email, String firstName, String lastName, String password, Set<String> phone, Set<HobbyDTO> hobbies, String streetName, String streetAdditionalInfo) {
        // Map<String, String> hobbies,
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.phone = phone;
        this.hobbies = hobbies;
        this.streetName = streetName;
        this.streetAdditionalInfo = streetAdditionalInfo;
    }
    
    public static List<PersonDTO> getDtos(List<Person> persons, Set<HobbyDTO> hobbies){
        List<PersonDTO> personDTOs = new ArrayList();
        for(int i = 0; i < persons.size(); )
        persons.forEach(person-> personDTOs.add(new PersonDTO(person)));
        return personDTOs;
    }


    public PersonDTO(Person person, Set<HobbyDTO> hobbies) {
        if(person.getId() != null)
            this.id = person.getId();
        this.email = person.getEmail();
        this.firstName = person.getFirstName();
        this.lastName = person.getLastName();
        this.password = person.getPassword();
        phone = new HashSet<>();
        for (Phone p : person.getPhone()) {
            phone.add(p.getNumber());
        }
//        hobbies = new HashMap<>();
//        for (Hobby hobby : person.getHobby()) {
//            hobbies.put(hobby.getName(), hobby.getDescription());
//        }
        this.hobbies = hobbies;
        this.streetName = person.getAddress().getStreet();
        this.streetAdditionalInfo = person.getAddress().getAdditionalInfo();
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
    
    public Set<String> getPhone() {
        return phone;
    }
    
    public void setPhone(Set<String> phone) {
        this.phone = phone;
    }
    
//    public Map<String, String> getHobbies() {
//        return hobbies;
//    }
//
//    public void setHobbies(Map<String, String> hobbies) {
//        this.hobbies = hobbies;
//    }


    public Set<HobbyDTO> getHobbies() {
        return hobbies;
    }

    public void setHobbies(Set<HobbyDTO> hobbies) {
        this.hobbies = hobbies;
    }

    public String getStreetName() {
        return streetName;
    }
    
    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }
    
    public String getStreetAdditionalInfo() {
        return streetAdditionalInfo;
    }
    
    public void setStreetAdditionalInfo(String streetAdditionalInfo) {
        this.streetAdditionalInfo = streetAdditionalInfo;
    }
    
    @Override
    public String toString() {
        return "PersonDTO{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", password='" + password + '\'' +
                ", phone=" + phone +
                ", hobbies=" + hobbies +
                ", streetName='" + streetName + '\'' +
                ", streetAdditionalInfo='" + streetAdditionalInfo + '\'' +
                '}';
    }
}
