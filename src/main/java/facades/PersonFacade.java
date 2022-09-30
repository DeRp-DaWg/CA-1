package facades;

import dtos.CityInfoDTO;
import dtos.HobbyDTO;
import dtos.PersonDTO;
import entities.*;

import java.util.*;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

//import errorhandling.RenameMeNotFoundException;
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

    public PersonDTO create(PersonDTO personDTO){
        Set<Phone> phones = new LinkedHashSet<>();
        for(String s : personDTO.getPhone()) {
            phones.add(new Phone(s));
        }
        Address address = new Address(personDTO.getStreetName(), personDTO.getStreetAdditionalInfo());
        Set<Hobby> hobbies = new LinkedHashSet<>();
        for(Map.Entry<String, String> entry : personDTO.getHobbies().entrySet()) {
            hobbies.add(new Hobby(entry.getKey(), entry.getValue()));
        }
        Person person = new Person(personDTO.getEmail(), personDTO.getFirstName(), personDTO.getLastName(), personDTO.getPassword());
        person.setPhone(phones);
        person.setAddress(address);
        person.setHobby(hobbies);
        // Phone phone = new Phone(number, );
        // Address address = new Address(street, info, CityInfo);
        // Hobby hobby = new Hobby(name, desc);
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(person);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return new PersonDTO(person);
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

            public HobbyDTO insertHobby(HobbyDTO hobbyDTO){
            Hobby hobby = new Hobby(hobbyDTO.getHobby_name(), hobbyDTO.getHobby_wikiLink(), hobbyDTO.getHobby_category(), hobbyDTO.getHobby_type());
            EntityManager em = getEntityManager();
            try {
                em.getTransaction().begin();
                em.persist(hobby);
                em.getTransaction().commit();
            } finally {
                em.close();
            }
            return new HobbyDTO(hobby);
        }



        public List<HobbyDTO> getHobbies(long hobby_id) { //throws RenameMeNotFoundException {
            EntityManager em = emf.createEntityManager();
            TypedQuery<Hobby> query = em.createQuery("SELECT h FROM Hobby h WHERE h.id = :idh", Hobby.class)
                    .setParameter("idh", hobby_id);
            List<Hobby> hobbyList = query.getResultList();
            List<HobbyDTO> hobbyDTOs = new ArrayList<>();
            for (Hobby h : hobbyList) {
                hobbyDTOs.add(new HobbyDTO(h));
            }
            //Person person = em.find(Person.class, phoneNumber);
//        if (rm == null)
//            throw new RenameMeNotFoundException("The Person entity with ID: "+id+" Was not found");
            return hobbyDTOs;
        }

            public CityInfoDTO insertCityInfo(CityInfoDTO cityInfoDTO){
            CityInfo cityInfo = new CityInfo(cityInfoDTO.getNr(), cityInfoDTO.getnavn());
            EntityManager em = getEntityManager();
            try {
                em.getTransaction().begin();
                em.persist(cityInfo);
                em.getTransaction().commit();
            } finally {
                em.close();
            }
            return new CityInfoDTO(cityInfo);
        }



        public List<CityInfoDTO> getCityInfo(long cityInfo_id) { //throws RenameMeNotFoundException {
            EntityManager em = emf.createEntityManager();
            TypedQuery<CityInfo> query = em.createQuery("SELECT c FROM CityInfo c WHERE c.id = :idh", CityInfo.class)
                    .setParameter("idh", cityInfo_id);
            List<CityInfo> cityInfoList = query.getResultList();
            List<CityInfoDTO> cityInfoDTOs = new ArrayList<>();
            for (CityInfo c : cityInfoList) {
                cityInfoDTOs.add(new CityInfoDTO(c));
            }
            //Person person = em.find(Person.class, phoneNumber);
//        if (rm == null)
//            throw new RenameMeNotFoundException("The Person entity with ID: "+id+" Was not found");
            return cityInfoDTOs;
        }
}
