package dtos;

import entities.Address;
import entities.CityInfo;

public class AddressDTO {
    private String street;
    private String additionalInfo;

    private CityInfoDTO cityInfoDTO;


    public AddressDTO(String street, String additionalInfo, CityInfoDTO cityInfoDTO) {
        this.street = street;
        this.additionalInfo = additionalInfo;
        this.cityInfoDTO = cityInfoDTO;
    }

    public AddressDTO(Address address) {
        if(address != null) {
            this.street = address.getStreet();
            this.additionalInfo = address.getAdditionalInfo();
            this.cityInfoDTO = new CityInfoDTO(address.getCityInfo());
        }
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

    public CityInfoDTO getCityInfoDTO() {
        return cityInfoDTO;
    }

    public void setCityInfoDTO(CityInfoDTO cityInfoDTO) {
        this.cityInfoDTO = cityInfoDTO;
    }
}
