package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.CityInfoDTO;
import dtos.HobbyDTO;
import dtos.PersonDTO;
import facades.PersonFacade;
import utils.EMF_Creator;

import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

//Todo Remove or change relevant parts before ACTUAL use
@Path("hobby")
public class HobbyResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
       
    private static final PersonFacade FACADE =  PersonFacade.getFacadeExample(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    
    @GET
    @Path("/{hobby}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getByHobby(@PathParam("hobby") String hobby) throws EntityNotFoundException {
        List<PersonDTO> p = new ArrayList<>(FACADE.getByHobby(hobby));
        return Response.ok().entity(GSON.toJson(p)).build();
    }

    @GET
    @Path("/amount/{hobby}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAmountHobby(@PathParam("hobby") String hobby) throws EntityNotFoundException {
        int amount = new ArrayList<>(FACADE.getByHobby(hobby)).size();
        String json = "{\n  \"  amount\": " + amount + "\n}";
        return Response.ok().entity(json).build();
    }
    
    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response createHobby(String content) {
        HobbyDTO h = GSON.fromJson(content, HobbyDTO.class);
        HobbyDTO newH = FACADE.createHobby(h);
        return Response.ok().entity(GSON.toJson(newH)).build();
    }
}
