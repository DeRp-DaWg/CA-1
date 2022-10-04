package dtos;

import entities.Person;
import entities.Phone;

public class PhoneDTO {
    private Long id;
    private String number;

    private PersonDTO person;


    public PhoneDTO(String number) {
        this.number = number;
    }

    public PhoneDTO(Phone phone) {
        if(phone != null) {
            this.id = phone.getId();
            this.number = phone.getNumber();
            this.person = new PersonDTO(phone.getPerson());

        }
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
