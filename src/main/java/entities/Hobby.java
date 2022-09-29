package entities;

import jdk.jfr.Name;

import javax.persistence.*;
import java.io.Serializable;

    @Entity
    @NamedQuery(name = "Hoppy.deleteAllRows" , query = "DELETE from Hobby")
    public class Hobby implements Serializable{

        private  static  final long serialVersionUTD = 1L;

        public boolean getHoppy_name;


        @Name
            @GeneratedValue(strategy = GenerationType.IDENTITY)
            private String name;
            private String description;


        public Hobby() {
        }

        public Hobby(String name, String description) {
            this.name = name;
            this.description = description;
        }

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
