package entities;

import javax.persistence.*;
import java.io.Serializable;

    @Entity
    @NamedQuery(name = "Hobby.deleteAllRows" , query = "DELETE from Hobby")
    public class Hobby implements Serializable{

        private  static  final long serialVersionUTD = 1L;

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        public boolean getHobby_name;
        
            private String name;
            private String description;

        public Hobby() {
        }

        public Hobby(String name, String description) {
            this.name = name;
            this.description = description;
        }

            public Long getId() {return id;}

            public void setId(Long id) {this.id = id;}

            public String getName () {
            return name;
        }

            public void setName (String name){
            this.name = name;
        }

            public String getDescription () {
            return description;
        }

            public void setDescription (String description){
            this.description = description;
        }

}
