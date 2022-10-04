package facades;

import dtos.CityInfoDTO;
import dtos.HobbyDTO;
import dtos.PersonDTO;
import entities.*;

import java.util.*;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

//import errorhandling.RenameMeNotFoundException;
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

    public Person assignCityInfo(long cityInfoId, Person person){
        EntityManager em = emf.createEntityManager();
        CityInfo cityInfo = em.find(CityInfo.class, cityInfoId);
        em.getTransaction().begin();
        person.getAddress().assingCityinfo(cityInfo);
        em.getTransaction().commit();
        em.close();
        return person;
    }

    public Person assignHobby(Set<Long> hobbyId, Person person){
        EntityManager em = emf.createEntityManager();
        Set<Hobby> hobbies = new LinkedHashSet<>();
        for(Long l : hobbyId){
            hobbies.add(em.find(Hobby.class, l));
        }
        em.getTransaction().begin();
        person.setHobby(hobbies);
        em.getTransaction().commit();
        em.close();
        return person;
    }
    public PersonDTO create(PersonDTO personDTO){
//        Set<Phone> phones = new LinkedHashSet<>();
//        for(String s : personDTO.getPhone()) {
//            phones.add(new Phone(s));
//        }
//        Address address = new Address(personDTO.getStreetName(), personDTO.getStreetAdditionalInfo(), personDTO.getCityInfo_id());
//        Set<HobbyDTO> hobbies = new LinkedHashSet<>();
//        for(HobbyDTO hobbyDTO : personDTO.getHobbies()) {
////            hobbies.add(new Hobby(hobbyDTO.getHobby_name(), hobbyDTO.getHobby_wikiLink(), hobbyDTO.getHobby_category(), hobbyDTO.getHobby_type()));
//            hobbies.add(hobbyDTO);
//        }
//        for(Map.Entry<String, String> entry : personDTO.getHobbies().entrySet()) {
//            hobbies.add(new Hobby(entry.getKey(), entry.getValue()));
//        }
        Person person = personDTO.getEntity();
//                new Person(personDTO.getEmail(), personDTO.getFirstName(), personDTO.getLastName(), personDTO.getPassword());
//        person.setPhone(personDTO.getPhone());
//        person.setAddress(address);
        for(Phone p : person.getPhone()){
            p.assignPersons(person);
        }
        person = assignCityInfo(personDTO.getCityInfo_id(), person);
        person = assignHobby(personDTO.getHobby_id(), person);
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
//        if (person.getId() == 0)
//            throw new IllegalArgumentException("No Parent can be updated when id is missing");
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
//        if (p == null)
//            throw new EntityNotFoundException("Could not remove Parent with id: "+id);
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
//        String textNumber = String.valueOf(phoneNumber);

//        TypedQuery<Person> query = em.createQuery("SELECT p FROM Person p WHERE p.phone = : phoneNumber", Person.class)
        TypedQuery<Person> query = em.createQuery("SELECT p FROM Person p JOIN p.phone t WHERE t.number = :phoneNumber", Person.class)
                .setParameter("phoneNumber", phoneNumber);
        List<Person> person = query.getResultList();
//        Set<HobbyDTO> hobbyDTOSet = new LinkedHashSet<>();
//        for(HobbyDTO h : getHobbies(person.getHobby_id())){
//            hobbyDTOSet.add(h);
//        }
//        PersonDTO personDTO = new PersonDTO(person, hobbyDTOSet);
        List<PersonDTO> personDTOS = new ArrayList<>();
        for (Person p : person) {
//            Set<HobbyDTO> hobbyDTOSet = new LinkedHashSet<>();
//            for(HobbyDTO h : getHobbies(p.getHobby_id())){
//                hobbyDTOSet.add(h);
//            }
            personDTOS.add(new PersonDTO(p));
        }
        //Person person = em.find(Person.class, phoneNumber);
//        if (rm == null)
//            throw new RenameMeNotFoundException("The Person entity with ID: "+id+" Was not found");
        return personDTOS;
    }

    public List<PersonDTO> getByHobby(String hobby) { //throws RenameMeNotFoundException {
        EntityManager em = emf.createEntityManager();

//        TypedQuery<Person> query = em.createQuery("SELECT p FROM Person p WHERE p.phone = : phoneNumber", Person.class)
        TypedQuery<Person> query = em.createQuery("SELECT p FROM Person p JOIN p.hobby h WHERE h.name = :hobbyName", Person.class)
                .setParameter("hobbyName", hobby);
        List<Person> persons = query.getResultList();
        List<PersonDTO> personDTOS = new ArrayList<>();

        for (Person p : persons) {
//            personDTOS.add(new PersonDTO(p));
        }
//        if (rm == null)
//            throw new RenameMeNotFoundException("The Person entity with ID: "+id+" Was not found");
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

        for (Person p : persons) {
//            personDTOS.add(new PersonDTO(p));
        }
//        if (rm == null)
//            throw new RenameMeNotFoundException("The Person entity with ID: "+id+" Was not found");
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
//        query = em.createQuery("SELECT p FROM Person p JOIN p.address.cityInfo c JOIN p.hobby h WHERE c.city = :name AND h.name = :hobbyName", Person.class)
//                .setParameter("name", city).setParameter("hobbyName", hobby);
        //        query = em.createQuery("SELECT p FROM Person p JOIN p.hobby h WHERE h.name = :hobbyName", Person.class)
        query = em.createQuery("SELECT h FROM Hobby h WHERE h.name = :hobbyName", Person.class)
                .setParameter("hobbyName", hobby);
        List<Person> personsFromHobby = query.getResultList();
        Set<Person> overlap = personsFromCity.stream().distinct().filter(personsFromHobby::contains).collect(Collectors.toSet()); //Make sure equals method is provided in Person entity
        List<PersonDTO> personDTOS = new ArrayList<>();

        for (Person p : overlap) {
//            personDTOS.add(new PersonDTO(p));
        }
//        if (rm == null)
//            throw new RenameMeNotFoundException("The Person entity with ID: "+id+" Was not found");
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
//        if (rm == null)
//            throw new RenameMeNotFoundException("The Person entity with ID: "+id+" Was not found");
        return cityDTOS;
    }

    // Gets all persons with a given hobby xxxxx maybe
//    public List<PersonDTO> getByHobby(String hobby){
//        EntityManager em = emf.createEntityManager();
//        TypedQuery<Person> query = em.createQuery("SELECT h FROM Hobby h WHERE h.hobby = :hobby", Person.class)
//                .setParameter("hobby", hobby);
//        List<Person> persons = query.getResultList();
//        List<PersonDTO> personDTOS = new ArrayList<>();
//        for (Person h : persons){
//            personDTOS.add(new PersonDTO(h));
//        }
//        return personDTOS;
//    }

    //Get all persons living in a given city
//    public List<PersonDTO> getByAddress(String address){
//        EntityManager em = emf.createEntityManager();
//        TypedQuery<Person> query = em.createQuery("SELECT a FROM Address a WHERE a.adress = :adress", Person.class)
//                .setParameter("adress", address);
//        List<Person> persons = query.getResultList();
//        List<PersonDTO> personDTOS = new ArrayList<>();
//        for (Person a : persons){
//            personDTOS.add(new PersonDTO(a));
//        }
//        return personDTOS;
//    }

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
//        List<List<HobbyDTO>> hobbyList = new ArrayList<>();
//        for(Person p : rms){
//            hobbyList.add(getHobbies(p.getHobby_id()));
//        }
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
