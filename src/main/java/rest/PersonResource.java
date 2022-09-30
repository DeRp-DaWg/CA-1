package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.PersonDTO;
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
        System.out.println(FACADE.getByPhone(1));
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

    // Waiting to be implemented - Claes
//    @GET
//    @Path("/{number}")
//    @Produces({MediaType.APPLICATION_JSON})
//    public Response getByP(@PathParam("number") int pNumber) throws EntityNotFoundException {
//        System.out.println("Hello");
//        System.out.println(pNumber);
//        PersonDTO p = FACADE.getByPhone(pNumber);
//        return Response.ok().entity(GSON.toJson(p)).build();
//    }

//    @GET
//    @Path("/phone")
//    @Produces({MediaType.APPLICATION_JSON})
//    public Response getById(@PathParam("id") int id) throws EntityNotFoundException {
//        List<PersonDTO> p = new ArrayList<>(FACADE.getByPhone(67821902));
//        return Response.ok().entity(GSON.toJson(p)).build();
//    }

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
