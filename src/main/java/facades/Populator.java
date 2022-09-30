/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import dtos.HobbyDTO;
import dtos.PersonDTO;
import dtos.CityInfoDTO;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

import entities.CityInfo;
import entities.Hobby;
import entities.Person;
import utils.EMF_Creator;
import utils.HttpUtils;

import java.lang.reflect.Type;
import java.util.*;

/**
 *
 * @author tha
 */
public class Populator {
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    public static void populate(){
        EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory();
        PersonFacade fe = PersonFacade.getFacadeExample(emf);

         fe.insertHobby(new HobbyDTO(new Hobby("3D-udskrivning","https://en.wikipedia.org/wiki/3D_printing","Generel","Indendørs")));
         fe.insertHobby(new HobbyDTO(new Hobby("Akrobatik","https://en.wikipedia.org/wiki/Acrobatics","Generel","Indendørs")));
         fe.insertHobby(new HobbyDTO(new Hobby("Skuespil","https://en.wikipedia.org/wiki/Acting","Generel","Indendørs")));
         fe.insertHobby(new HobbyDTO(new Hobby("Amatørradio","https://en.wikipedia.org/wiki/Amateur_radio","Generel","Indendørs")));
         fe.insertHobby(new HobbyDTO(new Hobby("Animation","https://en.wikipedia.org/wiki/Animation","Generel","Indendørs")));

        // Insert into PersonFacade
//        public HobbyDTO insertHobby(HobbyDTO hobbyDTO){
//            Hobby hobby = new Hobby(hobbyDTO.getHobby_name(), hobbyDTO.getHobby_wikiLink(), hobbyDTO.getHobby_category(), hobbyDTO.getHobby_type());
//            EntityManager em = getEntityManager();
//            try {
//                em.getTransaction().begin();
//                em.persist(hobby);
//                em.getTransaction().commit();
//            } finally {
//                em.close();
//            }
//            return new HobbyDTO(hobby);
//        }



//        public List<HobbyDTO> getHobbies(long hobby_id) { //throws RenameMeNotFoundException {
//            EntityManager em = emf.createEntityManager();
//            TypedQuery<Hobby> query = em.createQuery("SELECT h FROM Hobby h WHERE h.id = :idh", Hobby.class)
//                    .setParameter("idh", hobby_id);
//            List<Hobby> hobbyList = query.getResultList();
//            List<HobbyDTO> hobbyDTOs = new ArrayList<>();
//            for (Hobby h : hobbyList) {
//                hobbyDTOs.add(new HobbyDTO(h));
//            }
//            //Person person = em.find(Person.class, phoneNumber);
////        if (rm == null)
////            throw new RenameMeNotFoundException("The Person entity with ID: "+id+" Was not found");
//            return hobbyDTOs;
//        }

        String json = HttpUtils.fetchAPIData("https://api.dataforsyningen.dk/postnumre");
        Type collectionType = new TypeToken<Collection<CityInfoDTO>>(){}.getType();
        Collection<CityInfoDTO> ZipDTO = gson.fromJson(json, collectionType);

        for (CityInfoDTO dto : ZipDTO) {
            //System.out.println("Name: " + dto.getnavn() + " Zip: " + dto.getNr());
            fe.insertCityInfo(new CityInfoDTO(dto.getnavn(), dto.getNr()));
        }

        // Insert into PersonFacade
//        public CityInfoDTO insertCityInfo(CityInfoDTO cityInfoDTO){
//            CityInfo cityInfo = new CityInfo(cityInfoDTO.getNr(), cityInfoDTO.getNavn());
//            EntityManager em = getEntityManager();
//            try {
//                em.getTransaction().begin();
//                em.persist(cityInfo);
//                em.getTransaction().commit();
//            } finally {
//                em.close();
//            }
//            return new CityInfoDTO(cityInfo);
//        }



//        public List<CityInfoDTO> getCityInfo(long cityInfo_id) { //throws RenameMeNotFoundException {
//            EntityManager em = emf.createEntityManager();
//            TypedQuery<CityInfo> query = em.createQuery("SELECT c FROM CityInfo c WHERE c.id = :idh", CityInfo.class)
//                    .setParameter("idh", cityInfo_id);
//            List<CityInfo> cityInfoList = query.getResultList();
//            List<CityInfo> cityInfoDTOs = new ArrayList<>();
//            for (CityInfo c : cityInfoList) {
//                cityInfoDTOs.add(new CityInfoDTO(c));
//            }
//            //Person person = em.find(Person.class, phoneNumber);
////        if (rm == null)
////            throw new RenameMeNotFoundException("The Person entity with ID: "+id+" Was not found");
//            return cityInfoDTOs;
//        }
        Set<String> phones = new LinkedHashSet<>();
        phones.add("67821902");
        //Map<String, String> hobbies = new HashMap<>();
//        hobbies.put("Fodbold", "Man sparker til en bold");
        Set<HobbyDTO> hobbies = new LinkedHashSet<>();
        for(HobbyDTO dto : fe.getHobbies(1)){
            hobbies.add(dto);
        }
        fe.create(new PersonDTO("user@mail.dk", "Lars", "Jørgensen", "1234", phones, hobbies, "Østerbrogade 3", "1. th", 1, 1));
//        System.out.println(fe.getByPhone(67821902));
    }
    
    public static void main(String[] args) {
        populate();
    }
}
