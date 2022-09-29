package facades;

import dtos.PersonDTO;
import entities.Person;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

//import errorhandling.RenameMeNotFoundException;
import entities.Phone;
import utils.EMF_Creator;

/**
 *
 * Rename Class to a relevant name Add add relevant facade methods
 */
public class PersonFacade {

    private static PersonFacade instance;
    private static EntityManagerFactory emf;
    
    //Private Constructor to ensure Singleton
    private PersonFacade() {}
    
    
    /**
     * 
     * @param _emf
     * @return an instance of this facade class.
     */
    public static PersonFacade getFacadeExample(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new PersonFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public PersonDTO create(PersonDTO person){
        Person personO = new Person(person.getEmail(), person.getFirstName(), person.getLastName(), person.getPassword());
        // Phone phone = new Phone(number, );
        // Address address = new Address(street, info, CityInfo);
        // Hobby hobby = new Hobby(name, desc);
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(personO);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return new PersonDTO(personO);
    }
    /* Returns and works with lists, because there is a chance that
     more people uses the same phone number (per example: a child and
     their parent) */
    public List<PersonDTO> getByPhone(long phoneNumber) { //throws RenameMeNotFoundException {
        EntityManager em = emf.createEntityManager();
        TypedQuery<Person> query = em.createQuery("SELECT p FROM Person p WHERE p.phone = :phoneNumber", Person.class)
                .setParameter("phoneNumber", phoneNumber);
        List<Person> persons = query.getResultList();
        List<PersonDTO> personDTOS = new ArrayList<>();
        for (Person p : persons) {
            personDTOS.add(new PersonDTO(p));
        }
        //Person person = em.find(Person.class, phoneNumber);
//        if (rm == null)
//            throw new RenameMeNotFoundException("The Person entity with ID: "+id+" Was not found");
        return personDTOS;
    }

    // Gets all persons with a given hobby xxxxx maybe
    public List<PersonDTO> getByHobby(String hobby){
        EntityManager em = emf.createEntityManager();
        TypedQuery<Person> query = em.createQuery("SELECT h FROM Hobby h WHERE h.hobby = :hobby", Person.class)
                .setParameter("hobby", hobby);
        List<Person> persons = query.getResultList();
        List<PersonDTO> personDTOS = new ArrayList<>();
        for (Person h : persons){
            personDTOS.add(new PersonDTO(h));
        }
        return personDTOS;
    }

    //Get all persons living in a given city
    public List<PersonDTO> getByAddress(String address){
        EntityManager em = emf.createEntityManager();
        TypedQuery<Person> query = em.createQuery("SELECT a FROM Address a WHERE a.adress = :adress", Person.class)
                .setParameter("adress", address);
        List<Person> persons = query.getResultList();
        List<PersonDTO> personDTOS = new ArrayList<>();
        for (Person a : persons){
            personDTOS.add(new PersonDTO(a));
        }
        return personDTOS;
    }
    //Get the number of people with a given hobby


    //
    //Get a list of all zip codes in Denmark


    //
    //Create new Persons

    //
    //Edit Persons

    //
    //TODO Remove/Change this before use
    public long getRenameMeCount(){
        EntityManager em = getEntityManager();
        try{
            long renameMeCount = (long)em.createQuery("SELECT COUNT(r) FROM Person r").getSingleResult();
            return renameMeCount;
        }finally{  
            em.close();
        }
    }
    
    public List<PersonDTO> getAll(){
        EntityManager em = emf.createEntityManager();
        TypedQuery<Person> query = em.createQuery("SELECT r FROM Person r", Person.class);
        List<Person> rms = query.getResultList();
        return PersonDTO.getDtos(rms);
    }
    
    public static void main(String[] args) {
        emf = EMF_Creator.createEntityManagerFactory();
        PersonFacade fe = getFacadeExample(emf);
        fe.getAll().forEach(dto->System.out.println(dto));
    }

}
