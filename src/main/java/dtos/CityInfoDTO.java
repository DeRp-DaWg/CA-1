package dtos;

import entities.CityInfo;
import entities.Hobby;

public class CityInfoDTO {
    private String nr;
    private String navn;

    public CityInfoDTO(String nr, String navn){
        this.nr = nr;
        this.navn = navn;
    }

    public CityInfoDTO(CityInfo cityInfo) {
        if(cityInfo != null) {
            this.navn = cityInfo.getCity();
            this.nr = cityInfo.getZipCode();
        }
    }

    public String getNr() {
        return nr;
    }

    public void setNr(String nr) {
        this.nr = nr;
    }

    public String getnavn() {
        return navn;
    }

    public void setnavn(String navn) {
        this.navn = navn;
    }
}
