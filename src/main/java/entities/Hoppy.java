package entities;

import jdk.jfr.Name;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.NamedQuery;
import java.io.Serializable;

    @Entity
    @NamedQuery(name = "" , query = "delete from Hoppy")
    public class Hoppy implements Serializable{

        private  static  final long serialVersionUTD = 1L;

        public boolean getHoppy_name;


        @Name
            @GeneratedValue(strategy = GenerationType.IDENTITY)
            private String name;
            private String description;

        public Hoppy() {
        }

        public Hoppy(String name, String description) {
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
