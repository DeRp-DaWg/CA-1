package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.CityInfoDTO;
import dtos.HobbyDTO;
import dtos.PersonDTO;
import entities.Hobby;
import entities.Person;
import utils.EMF_Creator;
import facades.PersonFacade;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

//Todo Remove or change relevant parts before ACTUAL use
@Path("person")
public class PersonResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
       
    private static final PersonFacade FACADE =  PersonFacade.getFacadeExample(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
            
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAll() throws EntityNotFoundException {
        List<PersonDTO> p = new ArrayList<>(FACADE.getAll());
//        System.out.println(FACADE.getByPhone(1));
        return Response.ok().entity(GSON.toJson(p)).build();
    }
//    @Path("count")
//    @GET
//    @Produces({MediaType.APPLICATION_JSON})
//    public String getRenameMeCount() {
//
//        long count = FACADE.getRenameMeCount();
//        //System.out.println("--------------->"+count);
//        return "{\"count\":"+count+"}";  //Done manually so no need for a DTO
//    }

    @GET
    @Path("/phone/{number}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getById(@PathParam("number") String number) throws EntityNotFoundException {
        List<PersonDTO> p = new ArrayList<>(FACADE.getByPhone(number));
        return Response.ok().entity(GSON.toJson(p)).build();
    }
    
    @GET
    @Path("/zips")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAllZips() throws EntityNotFoundException {
        List<CityInfoDTO> c = new ArrayList<>(FACADE.getAllZips());
        return Response.ok().entity(GSON.toJson(c)).build();
    }

    @GET
    @Path("/hobby/{hobby}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getByHobby(@PathParam("hobby") String hobby) throws EntityNotFoundException {
        List<PersonDTO> p = new ArrayList<>(FACADE.getByHobby(hobby));
        return Response.ok().entity(GSON.toJson(p)).build();
    }

    @GET
    @Path("/hobby/amount/{hobby}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAmountHobby(@PathParam("hobby") String hobby) throws EntityNotFoundException {
        int amount = new ArrayList<>(FACADE.getByHobby(hobby)).size();
        String json = "The amount of people that has the hobby: " + hobby + " is: " + amount;
        return Response.ok().entity(GSON.toJson(json)).build();
    }

    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response create(String content) {
        PersonDTO p = GSON.fromJson(content, PersonDTO.class);
        PersonDTO newP = FACADE.create(p);
        return Response.ok().entity(GSON.toJson(newP)).build();
    }

    @PUT
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response update(@PathParam("id") Long id, String content) throws EntityNotFoundException {
        PersonDTO p = GSON.fromJson(content, PersonDTO.class);
        p.setId(id); //Should be implemented
        PersonDTO updated = FACADE.update(p);
        return Response.ok().entity(GSON.toJson(updated)).build();
    }

    @DELETE
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response delete(@PathParam("id") int id) throws EntityNotFoundException {
        PersonDTO deleted = FACADE.delete(id);
        return Response.ok().entity(GSON.toJson(deleted)).build();
    }
    
    @POST
    @Path("/hobby")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response createHobby(String content) {
        HobbyDTO h = GSON.fromJson(content, HobbyDTO.class);
        HobbyDTO newH = FACADE.createHobby(h);
        return Response.ok().entity(GSON.toJson(newH)).build();
    }

//    @GET
//    @Path("/phone")
//    @Produces({MediaType.APPLICATION_JSON})
//    public Response getById() throws EntityNotFoundException {
//        List<PersonDTO> p = new ArrayList<>(FACADE.getByPhone(67821902));
//        return Response.ok().entity(GSON.toJson(p)).build();
//    }
//    @POST
//    @Produces({MediaType.APPLICATION_JSON})
//    @Consumes({MediaType.APPLICATION_JSON})
//    public Response postExample(String input){
//        PersonDTO rmdto = GSON.fromJson(input, PersonDTO.class);
//        System.out.println(rmdto);
//        return Response.ok().entity(rmdto).build();
//    }
}
