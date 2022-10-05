package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import dtos.CityInfoDTO;
import dtos.HobbyDTO;
import dtos.PersonDTO;
import entities.Address;
import entities.Hobby;
import entities.Person;
import entities.Phone;
import facades.PersonFacade;
import org.junit.jupiter.api.*;
import utils.EMF_Creator;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

import io.restassured.parsing.Parser;

import java.lang.reflect.Type;
import java.net.URI;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.core.UriBuilder;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.util.HttpStatus;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import utils.HttpUtils;

//Uncomment the line below, to temporarily disable this test
//@Disabled

public class PersonResourceTest {

    private static final int SERVER_PORT = 7777;
    private static final String SERVER_URL = "http://localhost/api";
    private static Person p1, p2;
    
    private static Hobby hobby1, hobby2, hobby3, hobby4, hobby5;

    static final URI BASE_URI = UriBuilder.fromUri(SERVER_URL).port(SERVER_PORT).build();
    private static HttpServer httpServer;
    private static EntityManagerFactory emf;

    static HttpServer startServer() {
        ResourceConfig rc = ResourceConfig.forApplication(new ApplicationConfig());
        System.out.println("Base-URI: "+BASE_URI);
        return GrizzlyHttpServerFactory.createHttpServer(BASE_URI, rc);
    }

    @BeforeAll
    public static void setUpClass() {
        //This method must be called before you request the EntityManagerFactory
        EMF_Creator.startREST_TestWithDB();
        emf = EMF_Creator.createEntityManagerFactoryForTest();

        httpServer = startServer();
        //Setup RestAssured
        System.out.println("SERVER_URL: "+SERVER_URL);
        RestAssured.baseURI = SERVER_URL;
        RestAssured.port = SERVER_PORT;
        RestAssured.defaultParser = Parser.JSON;


        PersonFacade fe = PersonFacade.getFacadeExample(emf);

        String json = HttpUtils.fetchAPIData("https://api.dataforsyningen.dk/postnumre");
        Type collectionType = new TypeToken<Collection<CityInfoDTO>>(){}.getType();
        Collection<CityInfoDTO> ZipDTO = gson.fromJson(json, collectionType);

        for (CityInfoDTO dto : ZipDTO) {
            //System.out.println("Name: " + dto.getnavn() + " Zip: " + dto.getNr());
            fe.insertCityInfo(new CityInfoDTO(dto.getnavn(), dto.getNr()));
        }
    }

    @AfterAll
    public static void closeTestServer() {
        //System.in.read();

        //Don't forget this, if you called its counterpart in @BeforeAll
        EMF_Creator.endREST_TestWithDB();
        httpServer.shutdownNow();
    }
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    // Setup the DataBase (used by the test-server and this test) in a known state BEFORE EACH TEST
    //TODO -- Make sure to change the EntityClass used below to use YOUR OWN (renamed) Entity class
    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        hobby1 = new Hobby("3D-udskrivning","https://en.wikipedia.org/wiki/3D_printing","Generel","Indendørs");
        hobby2 = new Hobby("Akrobatik","https://en.wikipedia.org/wiki/Acrobatics","Generel","Indendørs");
        hobby3 = new Hobby("Skuespil","https://en.wikipedia.org/wiki/Acting","Generel","Indendørs");
        hobby4 = new Hobby("Amatørradio","https://en.wikipedia.org/wiki/Amateur_radio","Generel","Indendørs");
        hobby5 = new Hobby("Animation","https://en.wikipedia.org/wiki/Animation","Generel","Indendørs");

        Set<Phone> hansPhones = new HashSet<>();
        hansPhones.add(new Phone("12345678"));
        hansPhones.add(new Phone("23456789"));
        Set<Hobby> hansHobbies = new HashSet<>();
        hansHobbies.add(hobby3);
        hansHobbies.add(hobby4);
        p1 = new Person("HansHansen@fake.mail", "Hans", "Hansen", "1234");
        p1.setPhone(hansPhones);
        p1.setHobby(hansHobbies);
        p1.setAddress(new Address("Hansstreet 38", "Nothing"));
    
        Set<Phone> jensPhones = new HashSet<>();
        jensPhones.add(new Phone("87654321"));
        jensPhones.add(new Phone("98765432"));
        Set<Hobby> jensHobbies = new HashSet<>();
        jensHobbies.add(hobby1);
        jensHobbies.add(hobby4);
        jensHobbies.add(hobby5);
        p2 = new Person("JensJensen@fake.mail", "Jens", "Jensen", "1234");
        p2.setPhone(jensPhones);
        p2.setHobby(jensHobbies);
        p2.setAddress(new Address("Jensstreet 2", ""));
        try {
            em.getTransaction().begin();
            em.createNativeQuery("SET FOREIGN_KEY_CHECKS = 0").executeUpdate();
            em.createNamedQuery("Address.deleteAllRows").executeUpdate();
            em.createNamedQuery("Hobby.deleteAllRows").executeUpdate();
            em.createNamedQuery("Person.deleteAllRows").executeUpdate();
            em.createNamedQuery("Phone.deleteAllRows").executeUpdate();
            em.createNativeQuery("SET FOREIGN_KEY_CHECKS = 1").executeUpdate();
            em.persist(hobby1);
            em.persist(hobby2);
            em.persist(hobby3);
            em.persist(hobby4);
            em.persist(hobby5);
            em.persist(p1);
            em.persist(p2);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @Test
    public void testServerIsUp() {
        System.out.println("Testing is server UP");
        given().when().get("/").then().statusCode(200);
    }
    
    @Test
    public void testWrongURL() {
        given()
                .contentType("application/json")
                .get("/thisisnotavalidurl/").then()
                .assertThat()
                .statusCode(HttpStatus.NOT_FOUND_404.getStatusCode());
    }

    @Test
    public void testGetPersonWithPhone() throws Exception {
        given()
                .contentType("application/json")
                .get("/person/12345678").then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("email", equalTo("HansHansen@fake.mail"))
                .body("firstName", equalTo("Hans"))
                .body("lastName", equalTo("Hansen"))
                .body("phone", hasItems(12345678, 23456789))
                .body("hobby", hasItem(allOf(
                        hasProperty("name", equalTo("Skuespil")),
                        hasProperty("name", equalTo("Amatørradio")))))
                .body("address", hasEntry("street", "Hansstreet 38"));
    }
    
    @Test
    public void testGetPersonsWithHobby() throws Exception {
        given()
                .contentType("application/json")
                .get("/hobby/Amatørradio").then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body(hasItem(allOf(
                        hasProperty("email", equalTo("HansHansen@fake.mail")),
                        hasProperty("firstName", equalTo("Hans")),
                        hasProperty("lastName", equalTo("Hansen")),
                        hasProperty("phone", hasItems(12345678, 23456789)),
                        hasProperty("hobby", hasItem(allOf(
                                hasProperty("name", equalTo("Skuespil")),
                                hasProperty("name", equalTo("Amatørradio"))))),
                        hasProperty("address", hasEntry("street", "Hansstreet 38")))));
    }
    
    @Test
    public void testGetAmountOfPersonsWithHobby() throws Exception {
        given()
                .contentType("application/json")
                .get("/hobby/amount/Amatørradio").then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("amount", equalTo(2));
    }
    
    @Test
    public void testGetAllZips() throws Exception {
        given()
                .contentType("application/json")
                .get("/person/zips").then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body(hasItem(allOf(hasProperty("nr", equalTo("København K")),hasProperty("navn",equalTo("1052")))));
    }
    
    @Test
    public void testCreatePerson() throws Exception {
        PersonDTO newPerson = new PersonDTO(p1);
        newPerson.setId(null);
        given()
                .contentType("application/json")
                .body(newPerson)
                .post("/person").then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode());
        given()
                .contentType("application/json")
                .get().then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode());
    }
    
    @Test
    public void testDeletePerson() throws Exception {
        given()
                .contentType("application/json")
                .delete("/person/1").then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode());
        given()
                .contentType("application/json")
                .get("/person/1").then()
                .assertThat()
                .statusCode(HttpStatus.NOT_FOUND_404.getStatusCode());
    }
    
    @Test
    public void testUpdatePerson() throws Exception {
        PersonDTO changedPerson = new PersonDTO(p1);
        changedPerson.setFirstName("Peter");
        changedPerson.setLastName("Petersen");
        given()
                .contentType("application/json")
                .body(changedPerson)
                .put("/person/1").then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode());
        given()
                .contentType("application/json")
                .get("/person/1").then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("firstName", equalTo("Peter"))
                .body("lastName", equalTo("Petersen"));
    }
    
    @Test
    public void testCreateHobby() throws Exception {
        Hobby hobby = new Hobby("Gymnastik", "https://en.wikipedia.org/wiki/Gymnastics", "Generel", "Indendørs");
        HobbyDTO hobbyDTO = new HobbyDTO(hobby);
        given()
                .contentType("application/json")
                .body(hobbyDTO)
                .post("/hobby").then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode());
        given()
                .contentType("application/json")
                .get("/hobby/Gymnastik").then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("name", equalTo("Gymnastik"))
                .body("wikiLink", equalTo("https://en.wikipedia.org/wiki/Gymnastics"))
                .body("category", equalTo("Generel"))
                .body("type", equalTo("Indendørs"));
    }
    
    @Test
    public void testGetHobby() throws Exception {
        given()
                .contentType("application/json")
                .get("/hobby/Gymnastik").then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("name", equalTo("Gymnastik"))
                .body("wikiLink", equalTo("https://en.wikipedia.org/wiki/Gymnastics"))
                .body("category", equalTo("Generel"))
                .body("type", equalTo("Indendørs"));
    }
}
