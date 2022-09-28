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
    private int number;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="PERSON_ID")
    private Person person;
    
    public Phone() {
    }
    
    public Phone(int number) {
        this.number = number;
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public int getNumber() {
        return number;
    }
    
    public void setNumber(int number) {
        this.number = number;
    }
}
