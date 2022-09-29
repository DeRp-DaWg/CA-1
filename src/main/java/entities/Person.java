package entities;

import java.io.Serializable;
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
    @ManyToOne
    private Address address;
    @ManyToMany
    private Set<Hobby> hobby;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "person")
    private Set<Phone> phone;
    
    public Person() {
    }
    
    public Person(String email, String firstName, String lastName, String password) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
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
    
    public Hobby getHobby() {
        return hobby;
    }
    
    public void setHobby(Hobby hobby) {
        this.hobby = hobby;
    }
    
    public Phone getPhone() {
        return phone;
    }
    
    public void setPhone(Phone phone) {
        this.phone = phone;
    }
}
