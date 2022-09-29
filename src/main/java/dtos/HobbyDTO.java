package dtos;

import java.util.ArrayList;
import java.util.List;

public class hobbyDTO {
    private String hobby_name;
    private String hobby_description;

    public hobbyDTO(String hobby_name, String hobby_description){
    this.hobby_name = hobby_name;
    this.hobby_description = hobby_description;
    }

    public static List<hobbyDTO> getDtos(List<hobby> hobbies){
        List<hobbyDTO> hobbyDTOs = new ArrayList();
        hobbies.forEach(hobby->hobbyDTOs.add(new hobbyDTO(hobby)));
        return hobbyDTOs;
    }
    public HobbyDTO(Hobby hobby) {
        if(hobby != null)
            this.hobby_name = hobby.getName();
            this.hobby_description = hobby.getDescription();
    }

    public String gethobby_name(){return hobby_name; }
    public void sethobby_name(String hobby_name){this.hobby_name = hobby_name;}

    public String gethobby_description(){return hobby_description;}
    public void  sethobby_description(String hobby_description){this.hobby_description = hobby_description; }

    @Override
    public String toString() {
        return "hobbyDTO{" +
                "hobby_name='" + hobby_name + '\'' +
                ", hobby_description='" + hobby_description + '\'' +
                '}';
    }
}
