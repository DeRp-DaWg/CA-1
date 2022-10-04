package entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
    @NamedQuery(name = "Hobby.deleteAllRows" , query = "DELETE from Hobby")
    public class Hobby implements Serializable{

        private  static  final long serialVersionUTD = 1L;

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String name;

        private String wikiLink;

        private String category;

        private String type;

        @ManyToMany(mappedBy = "hobby")
        private Set<Person> person = new LinkedHashSet<>();

        public Hobby() {
        }

    public Hobby(String name, String wikiLink, String category, String type) {
        this.name = name;
        this.wikiLink = wikiLink;
        this.category = category;
        this.type = type;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWikiLink() {
        return wikiLink;
    }

    public void setWikiLink(String wikiLink) {
        this.wikiLink = wikiLink;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
