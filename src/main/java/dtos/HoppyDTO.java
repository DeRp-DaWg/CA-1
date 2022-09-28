package dtos;

import entities.Hoppy;
import java.util.ArrayList;
import java.util.List;

public class HoppyDTO {
    private String hoppy_name;
    private String hoppy_description;

    public HoppyDTO(String hoppy_name, String hoppy_description){
    this.hoppy_name = hoppy_name;
    this.hoppy_description = hoppy_description;
    }

    public static List<HoppyDTO> getDtos(List<Hoppy> hoppies){
        List<HoppyDTO> hoppyDTOs = new ArrayList();
        hoppies.forEach(hoppy->hoppyDTOs.add(new HoppyDTO(hoppy)));
        return hoppyDTOs;
    }
    public HoppyDTO(Hoppy hoppy) {
        if(hoppy != null)
            this.hoppy_name = hoppy.getName();
            this.hoppy_description = hoppy.getDescription();
    }

    public String getHoppy_name(){return hoppy_name; }
    public void setHoppy_name(String hoppy_name){this.hoppy_name = hoppy_name;}

    public String getHoppy_description(){return hoppy_description;}
    public void  setHoppy_description(String hoppy_description){this.hoppy_description = hoppy_description; }

    @Override
    public String toString() {
        return "HoppyDTO{" +
                "hoppy_name='" + hoppy_name + '\'' +
                ", hoppy_description='" + hoppy_description + '\'' +
                '}';
    }
}
