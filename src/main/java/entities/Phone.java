package entities;

import java.io.Serializable;
import javax.persistence.*;


@Entity
@NamedQuery(name = "Phone.deleteAllRows", query = "DELETE from Phone")
public class Phone implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String number;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="PERSON_ID")
    private Person person;
    
    public Phone() {
    }
    
    public Phone(String number) {
        this.number = number;
    }
    
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
    
    public Person getPerson() {
        return person;
    }
    
    public void setPerson(Person person) {
        this.person = person;
    }

    public void assignPersons(Person person){
        if(person != null){
            this.person = person;
            person.getPhone().add(this); // bi-directional: sætter student ind i Hashset hos semester
        }
    }
}
