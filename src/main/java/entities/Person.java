package entities;

import dtos.HobbyDTO;
import dtos.PersonDTO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.*;


@Entity
@NamedQuery(name = "Person.deleteAllRows", query = "DELETE from Person")
public class Person implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String email;
    private String firstName;
    private String lastName;
    private String password;

    private int hobby_id;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private Address address;
//    @ManyToMany(cascade = CascadeType.PERSIST)
//    @JoinTable(
//            name = "personhobby",
//            joinColumns = @JoinColumn(name = "person_id"),
//            inverseJoinColumns = @JoinColumn(name = "hobby_id")
//    )
//    private Set<Hobby> hobby;
    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "person")
    private Set<Phone> phone = new LinkedHashSet<>();
    
    public Person() {
    }
    
    public Person(String email, String firstName, String lastName, String password, int hobby_id) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.hobby_id = hobby_id;
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
    
    public Address getAddress() {
        return address;
    }
    
    public void setAddress(Address address) {
        this.address = address;
    }
    
//    public Set<Hobby> getHobby() {
//        return hobby;
//    }
//
//    public void setHobby(Set<Hobby> hobby) {
//        this.hobby = hobby;
//    }
    
    public Set<Phone> getPhone() {
        return phone;
    }
    
    public void setPhone(Set<Phone> phone) {
        this.phone = phone;
    }

    public int getHobby_id() {
        return hobby_id;
    }

    public void setHobby_id(int hobby_id) {
        this.hobby_id = hobby_id;
    }

}
