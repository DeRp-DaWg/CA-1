package dtos;

import entities.Hobby;
import java.util.ArrayList;
import java.util.List;

public class HobbyDTO {
    private String hobby_name;
    private String hobby_description;

    public HobbyDTO(String hobby_name, String hobby_description){
    this.hobby_name = hobby_name;
    this.hobby_description = hobby_description;
    }

    public static List<HobbyDTO> getDtos(List<Hobby> hobbies){
        List<HobbyDTO> hobbyDTOs = new ArrayList();
        hobbies.forEach(hobby->hobbyDTOs.add(new HobbyDTO(hobby)));
        return hobbyDTOs;
    }
    public HobbyDTO(Hobby hobby) {
        if(hobby != null)
            this.hobby_name = hobby.getName();
            this.hobby_description = hobby.getDescription();
    }

    public String getHobby_name(){return hobby_name; }
    public void setHobby_name(String hobby_name){this.hobby_name = hobby_name;}

    public String getHobby_description(){return hobby_description;}
    public void  setHobby_description(String hobby_description){this.hobby_description = hobby_description; }

    @Override
    public String toString() {
        return "HobbyDTO{" +
                "hobby_name='" + hobby_name + '\'' +
                ", hobby_description='" + hobby_description + '\'' +
                '}';
    }
}
