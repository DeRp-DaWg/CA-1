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
import java.util.function.Function;

/**
 *
 * @author tha
 */
public class PersonDTO {
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private String password;
    private Set<String> phone;
//    private Map<String, String> hobbies;
    private Set<HobbyDTO> hobbies;
    private String streetName;
    private String streetAdditionalInfo;

    // ToDo possibly find better solution
    // Just holding these variables, for when a PersonDTO is converted to a Person entity
    private int cityInfo_id;
    private int hobby_id;
    
    public PersonDTO(String email, String firstName, String lastName, String password, Set<String> phone, Set<HobbyDTO> hobbies,
                     String streetName, String streetAdditionalInfo, int cityInfo_id, int hobby_id) {
        // Map<String, String> hobbies,
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.phone = phone;
        this.hobbies = hobbies;
        this.streetName = streetName;
        this.streetAdditionalInfo = streetAdditionalInfo;
        this.cityInfo_id = cityInfo_id;
        this.hobby_id = hobby_id;

    }

    // ToDo change the List<List<>> to something smarter
    public static List<PersonDTO> getDtos(List<Person> persons){
        List<PersonDTO> personDTOs = new ArrayList();
        for(int i = 0; i < persons.size(); i++){
            Set<HobbyDTO> hobbyDTOSet = new LinkedHashSet<>();
//            for(HobbyDTO h : hobbies.get(i)){
//                hobbyDTOSet.add(h);
//            }
            personDTOs.add(new PersonDTO(persons.get(i)));
        }
//      persons.forEach(person-> personDTOs.add(new PersonDTO(person)));
        return personDTOs;
    }


    public PersonDTO(Person person) {
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
        this.hobbies = new LinkedHashSet<>();
        for(Hobby h : person.getHobby()) {
            this.hobbies.add(new HobbyDTO(h));
        }
        this.streetName = person.getAddress().getStreet();
        this.streetAdditionalInfo = person.getAddress().getAdditionalInfo();
    }

    public Person getEntity(Function f){
        Person person = new Person(this.getEmail(), this.getFirstName(), this.getLastName(), this.getPassword());
        if(this.id != 0){
            person.setId(this.id);
        }
        Set<Phone> phones = new LinkedHashSet<>();
        for(String s : this.getPhone()){
            phones.add(getPhoneObject(s));
        }
        person.setPhone(this.getPhone());
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public int getCityInfo_id() {
        return cityInfo_id;
    }

    public void setCityInfo_id(int cityInfo_id) {
        this.cityInfo_id = cityInfo_id;
    }

    public int getHobby_id() {
        return hobby_id;
    }

    public void setHobby_id(int hobby_id) {
        this.hobby_id = hobby_id;
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
