package entities;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.persistence.*;


@Entity
@NamedQuery(name = "Address.deleteAllRows", query = "DELETE from Address")
public class Address implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String street;
    private String additionalInfo;

//    private Long cityinfo_id;
    
//    @ManyToOne
//    @Where(clause = "")
//    @JoinColumn(name="cityinfo_id")
//    private CityInfo cityInfo;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "Cityinfo")


    @OneToMany(mappedBy = "address", cascade = CascadeType.PERSIST)
    private Set<Person> persons = new LinkedHashSet<>();

    @ManyToOne
    @JoinColumn(name = "city_info_id", referencedColumnName = "id")
    private CityInfo cityInfo;

//    @ManyToOne
//    @JoinColumn(name = "address_id")
//    private CityInfo address;

//    public CityInfo getAddress() {
//        return address;
//    }
//
//    public void setAddress(CityInfo address) {
//        this.address = address;
//    }

    public CityInfo getCityInfo() {
        return cityInfo;
    }

    public void setCityInfo(CityInfo cityInfo) {
        this.cityInfo = cityInfo;
    }

    //Many to one

    public Address() {}
    
    public Address(String street, String additionalInfo) {
        this.street = street;
        this.additionalInfo = additionalInfo;
//        this.cityinfo_id = cityinfo_id;
    }
//    public void assingCityinfo(CityInfo cityInfo){
//        if(cityInfo != null){
//            this.cityInfo = cityInfo;
//            cityInfo.getAddresses().add(this);
//        }
//    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getStreet() {
        return street;
    }
    
    public void setStreet(String street) {
        this.street = street;
    }
    
    public String getAdditionalInfo() {
        return additionalInfo;
    }
    
    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    public void addPerson(Person person){
        this.persons.add(person);
        person.setAddress(this);
    }
}
