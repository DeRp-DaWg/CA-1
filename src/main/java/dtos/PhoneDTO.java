package dtos;

import entities.Person;
import entities.Phone;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class PhoneDTO {
    private Long id;
    private String number;

    private PersonDTO person;


    public PhoneDTO(String number) {
        this.number = number;
    }

    public PhoneDTO(Phone phone) {
        if(phone != null) {
            if(phone.getId() != null) {
                this.id = phone.getId();
            }
            this.number = phone.getNumber();
        }
    }

    public static List<PhoneDTO> getDtos(List<Phone> phones){
        List<PhoneDTO> phoneDTOs = new ArrayList();
        for(Phone p : phones){
            phoneDTOs.add(new PhoneDTO(p));
        }
//      persons.forEach(person-> personDTOs.add(new PersonDTO(person)));
        return phoneDTOs;
    }

//    public Phone getEntity(){
//        Phone phone = new Phone(this.number);
//        if(this.id != null){
//            phone.setId(this.id);
//        }
//        if(this.person != null){
//            phone.setPerson(this.person);
//        }
//    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public PersonDTO getPerson() {
        return person;
    }

    public void setPerson(PersonDTO person) {
        this.person = person;
    }
}
