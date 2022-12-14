package entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "cityinfo")
@NamedQuery(name = "CityInfo.deleteAllRows" , query = "DELETE from CityInfo ")
public class CityInfo implements Serializable { // zipcode: 2100, city: Østerbro

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    //XXXXxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
    @OneToMany(mappedBy = "cityInfo", cascade = CascadeType.PERSIST)
    private Set<Address> addresses = new LinkedHashSet<>();
    
    @Column(name = "zipcode")
    private String zipCode;

    @Column(name = "city")
    private String city;

    public CityInfo(){}

    public CityInfo(String zipCode, String city) {
        this.zipCode = zipCode;
        this.city = city;
    }

    public Long getId() {
        return id;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Set<Address> getAddresses(){
        return addresses;
    }

    public void addAddress(Address address){
        this.addresses.add(address);
        address.setCityInfo(this);
    }

//    public Set<Address> getAddresses() {
//        return addresses;
//    }
//
//    public void setAddresses(Set<Address> address) {
//        this.addresses = address;
//    }
}
