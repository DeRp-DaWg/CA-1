/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

import entities.Address;
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
    private Set<PhoneDTO> phone;
//    private Map<String, String> hobbies;
    private Set<HobbyDTO> hobbies;
    private AddressDTO address;

    // ToDo possibly find better solution
    // Just holding these variables, for when a PersonDTO is converted to a Person entity
    private int cityInfo_id;
    private int hobby_id;

    public PersonDTO(Long id, String email, String firstName, String lastName, String password, Set<PhoneDTO> phone,
                     Set<HobbyDTO> hobbies, AddressDTO address, int cityInfo_id, int hobby_id) {
        this.id = id;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.phone = phone;
        this.hobbies = hobbies;
        this.address = address;
        this.cityInfo_id = cityInfo_id;
        this.hobby_id = hobby_id;
    }

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
        Set<PhoneDTO> phones = new LinkedHashSet<>();
        for(Phone p : person.getPhone()){
            phones.add(new PhoneDTO(p));
        }
        this.phone = phones;
//        hobbies = new HashMap<>();
//        for (Hobby hobby : person.getHobby()) {
//            hobbies.put(hobby.getName(), hobby.getDescription());
//        }
        this.hobbies = new LinkedHashSet<>();
        for(Hobby h : person.getHobby()) {
            this.hobbies.add(new HobbyDTO(h));
        }
        this.address = new AddressDTO(person.getAddress());
    }

    public Person getEntity(){
        Person person = new Person(this.getEmail(), this.getFirstName(), this.getLastName(), this.getPassword());
        if(this.id != 0){
            person.setId(this.id);
        }
        Set<Phone> phones = new LinkedHashSet<>();
        for(PhoneDTO p : this.getPhone()){
            Phone ph = new Phone(p.getNumber());
            if(p.getId() != null) {
                ph.setId(p.getId());
            }
            phones.add(ph);
        }
        person.setPhone(phones);
        person.setAddress(new Address(this.address.getStreet(), this.address.getAdditionalInfo(), this.cityInfo_id));
        Set<Hobby> hobbies = new LinkedHashSet<>();
        for(HobbyDTO h : this.getHobbies()){
            Hobby hd = new Hobby(h.getHobby_name(), h.getHobby_wikiLink(), h.getHobby_category(), h.getHobby_type());
            if(h.getId() != null){
                hd.setId(h.getId());
            }
            hobbies.add(hd);
        }
        person.setHobby(hobbies);

        return person;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public Set<PhoneDTO> getPhone() {
        return phone;
    }

    public void setPhone(Set<PhoneDTO> phone) {
        this.phone = phone;
    }

    public Set<HobbyDTO> getHobbies() {
        return hobbies;
    }

    public void setHobbies(Set<HobbyDTO> hobbies) {
        this.hobbies = hobbies;
    }

    public AddressDTO getAddress() {
        return address;
    }

    public void setAddress(AddressDTO address) {
        this.address = address;
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
}
