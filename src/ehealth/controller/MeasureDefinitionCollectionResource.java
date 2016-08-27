package ehealth.controller;

import ehealth.model.bl.MeasureDefinitionBL;
import ehealth.model.to.MeasureTypes;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.*;

@Stateless // will work only inside a Java EE application
@LocalBean // will work only inside a Java EE application
@Path("/measureTypes")
public class MeasureDefinitionCollectionResource {

    // Allows to insert contextual objects into the class,
    // e.g. ServletContext, Request, Response, UriInfo
    @Context
    UriInfo uriInfo;
    @Context
    Request request;

    //REQUEST #9
    // Return the list of people to the user in the browser
    @GET
    @Produces({MediaType.TEXT_XML,  MediaType.APPLICATION_JSON ,  MediaType.APPLICATION_XML })
    public Response getPersonsBrowser() {
        System.out.println("Getting list of MeasureDefinition...");

        Response response;
        MeasureDefinitionBL user = new MeasureDefinitionBL();
        MeasureTypes measureTypes = new MeasureTypes();
        try
        {
            measureTypes.setMeasureType(user.getMeasureTypes());
            response = Response.ok().entity(measureTypes).build();
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
            response = Response.status(Response.Status.NOT_FOUND).build();
        }
        return response;
    }
}