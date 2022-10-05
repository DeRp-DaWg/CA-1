package facades;

import dtos.CityInfoDTO;
import dtos.HobbyDTO;
import dtos.PersonDTO;
import dtos.PhoneDTO;
import entities.*;

import java.util.*;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

import entities.Phone;
import org.apache.commons.lang3.math.NumberUtils;
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

    public Person assignCityInfo(long cityInfoId, long person_id){
        EntityManager em = emf.createEntityManager();
        CityInfo cityInfo = em.find(CityInfo.class, cityInfoId);
        Person person = em.find(Person.class, person_id);
        em.getTransaction().begin();
        cityInfo.addAddress(person.getAddress());
        em.getTransaction().commit();
        em.close();
        return person;
    }

    public Person assignHobby(long hobbyId, long person_id){
        EntityManager em = emf.createEntityManager();
        Hobby hobby = em.find(Hobby.class, hobbyId);
        Person person = em.find(Person.class, person_id);
        em.getTransaction().begin();
        hobby.addPerson(person);
        em.getTransaction().commit();
        em.close();
        return person;
    }
    public PersonDTO create(PersonDTO personDTO){
        Set<Phone> phones = new LinkedHashSet<>();
        for(PhoneDTO p : personDTO.getPhone()) {
            phones.add(new Phone(p.getNumber()));
        }
        Address address = new Address(personDTO.getAddress().getStreet(), personDTO.getAddress().getAdditionalInfo());
        Set<HobbyDTO> hobbies = new LinkedHashSet<>();
        for(long l : personDTO.getHobby_id()){
            hobbies.add(getHobbies(l));
        }
        personDTO.setHobbies(hobbies);
        Person person = new Person(personDTO.getEmail(), personDTO.getFirstName(), personDTO.getLastName(), personDTO.getPassword());
        address.addPerson(person);
        for(Phone p : phones){
            person.addPhone(p);
        }
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(person);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        for(HobbyDTO h : personDTO.getHobbies()) {
            assignHobby(h.getId(), person.getId());
        }
        assignCityInfo(personDTO.getCityInfo_id(), person.getId());
        PersonDTO output = new PersonDTO(person);
        output.setHobbies(personDTO.getHobbies());
        return output;
    }

    public HobbyDTO createHobby(HobbyDTO hobbyDTO){

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

    public PersonDTO update(PersonDTO person) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        Person p = em.merge(person.getEntity()); //implement getEntity
        em.getTransaction().commit();
        em.close();
        return new PersonDTO(p);
    }

    public PersonDTO delete(int id){
        EntityManager em = getEntityManager();
        Person p = em.find(Person.class, id);
        em.getTransaction().begin();
        em.remove(p);
        em.getTransaction().commit();
        em.close();
        return new PersonDTO(p);
    }
    /* Returns and works with lists, because there is a chance that
     more people uses the same phone number (per example: a child and
     their parent) */

    public Phone getPhoneObject(String phoneNumber){
        EntityManager em = emf.createEntityManager();
        TypedQuery<Phone> query = em.createQuery("SELECT p FROM Phone p WHERE p.number = :phoneNumber", Phone.class)
                .setParameter("phoneNumber", phoneNumber);
        Phone phone = query.getSingleResult();
        return phone;
    }
    public List<PersonDTO> getByPhone(String phoneNumber) { //throws RenameMeNotFoundException {
        EntityManager em = emf.createEntityManager();
        TypedQuery<Person> query = em.createQuery("SELECT p FROM Person p JOIN p.phone t WHERE t.number = :phoneNumber", Person.class)
                .setParameter("phoneNumber", phoneNumber);
        List<Person> person = query.getResultList();
        List<PersonDTO> personDTOS = new ArrayList<>();
        for (Person p : person) {
            personDTOS.add(new PersonDTO(p));
        }
        return personDTOS;
    }

    public List<PersonDTO> getByHobby(String hobby) { //throws RenameMeNotFoundException {
        EntityManager em = emf.createEntityManager();

        TypedQuery<Person> query = em.createQuery("SELECT p FROM Person p JOIN p.hobby h WHERE h.name = :hobbyName", Person.class)
                .setParameter("hobbyName", hobby);
        List<Person> persons = query.getResultList();
        List<PersonDTO> personDTOS = new ArrayList<>();

        for (Person p : persons) {
            personDTOS.add(new PersonDTO(p));
        }
        return personDTOS;
    }

    public List<PersonDTO> getAllInCity(String city) { //throws RenameMeNotFoundException {
        EntityManager em = emf.createEntityManager();
        TypedQuery<Person> query;
        // If true, a number is given
        if(NumberUtils.isNumber(city)){
            query = em.createQuery("SELECT p FROM Person p JOIN p.address.cityInfo c WHERE c.zipCode = :zip", Person.class)
                    .setParameter("zip", city);
        }
        else {
            query = em.createQuery("SELECT p FROM Person p JOIN p.address.cityInfo c WHERE c.city = :name", Person.class)
                    .setParameter("name", city);
        }
        List<Person> persons = query.getResultList();
        List<PersonDTO> personDTOS = new ArrayList<>();

        return personDTOS;
    }

    public List<PersonDTO> getAllByHobbyCity(String city, String hobby) { //throws RenameMeNotFoundException {
        EntityManager em = emf.createEntityManager();
        TypedQuery<Person> query;
        // If true, a number is given
        if(NumberUtils.isNumber(city)){
            query = em.createQuery("SELECT p FROM Person p JOIN p.address.cityInfo c WHERE c.zipCode = :zip", Person.class)
                    .setParameter("zip", city);
        }
        else {
            query = em.createQuery("SELECT p FROM Person p JOIN p.address.cityInfo c WHERE c.city = :name", Person.class)
                    .setParameter("name", city);
        }
        List<Person> personsFromCity = query.getResultList();
        query = em.createQuery("SELECT h FROM Hobby h WHERE h.name = :hobbyName", Person.class)
                .setParameter("hobbyName", hobby);
        List<Person> personsFromHobby = query.getResultList();
        Set<Person> overlap = personsFromCity.stream().distinct().filter(personsFromHobby::contains).collect(Collectors.toSet()); //Make sure equals method is provided in Person entity
        List<PersonDTO> personDTOS = new ArrayList<>();

        return personDTOS;
    }

    public List<CityInfoDTO> getAllZips() { //throws RenameMeNotFoundException {
        EntityManager em = emf.createEntityManager();
        TypedQuery<CityInfo> query = em.createQuery("SELECT c FROM CityInfo c", CityInfo.class);
        List<CityInfo> citys = query.getResultList();
        List<CityInfoDTO> cityDTOS = new ArrayList<>();

        for (CityInfo c : citys) {
            cityDTOS.add(new CityInfoDTO(c));
        }
        return cityDTOS;
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



        public HobbyDTO getHobbies(long hobby_id) { //throws RenameMeNotFoundException {
            EntityManager em = emf.createEntityManager();
            TypedQuery<Hobby> query = em.createQuery("SELECT h FROM Hobby h WHERE h.id = :idh", Hobby.class)
                    .setParameter("idh", hobby_id);
            Hobby hobby = query.getSingleResult();
            return new HobbyDTO(hobby);
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
            return cityInfoDTOs;
        }
}
