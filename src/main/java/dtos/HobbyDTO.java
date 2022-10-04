package dtos;

import entities.Hobby;
import java.util.ArrayList;
import java.util.List;

public class HobbyDTO {

    private Long id;
    private String hobby_name;

    private String hobby_wikiLink;

    private String hobby_category;
    private String hobby_type;

    public HobbyDTO(Long id, String hobby_name, String hobby_wikiLink, String hobby_category, String hobby_type) {
        this.id = id;
        this.hobby_name = hobby_name;
        this.hobby_wikiLink = hobby_wikiLink;
        this.hobby_category = hobby_category;
        this.hobby_type = hobby_type;
    }

    public static List<HobbyDTO> getDtos(List<Hobby> hobbies){
        List<HobbyDTO> hobbyDTOs = new ArrayList();
        hobbies.forEach(hobby->hobbyDTOs.add(new HobbyDTO(hobby)));
        return hobbyDTOs;
    }
    public HobbyDTO(Hobby hobby) {
        if(hobby != null) {
            this.id = hobby.getId();
            this.hobby_name = hobby.getName();
            this.hobby_wikiLink = hobby.getWikiLink();
            this.hobby_category = hobby.getCategory();
            this.hobby_type = hobby.getType();
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHobby_name(){return hobby_name; }
    public void setHobby_name(String hobby_name){this.hobby_name = hobby_name;}

    public String getHobby_wikiLink() {
        return hobby_wikiLink;
    }

    public void setHobby_wikiLink(String hobby_wikiLink) {
        this.hobby_wikiLink = hobby_wikiLink;
    }

    public String getHobby_category() {
        return hobby_category;
    }

    public void setHobby_category(String hobby_category) {
        this.hobby_category = hobby_category;
    }

    public String getHobby_type() {
        return hobby_type;
    }

    public void setHobby_type(String hobby_type) {
        this.hobby_type = hobby_type;
    }

    @Override
    public String toString() {
        return "HobbyDTO{" +
                "hobby_name='" + hobby_name + '\'' +
                ", hobby_wikiLink='" + hobby_wikiLink + '\'' +
                ", hobby_category='" + hobby_category + '\'' +
                ", hobby_type='" + hobby_type + '\'' +
                '}';
    }
}
