package ehealth.controller;

import ehealth.model.bl.MeasureDefinitionBL;
import ehealth.model.bl.PersonBL;
import ehealth.model.to.MeasureDefinition;
import ehealth.model.to.Person;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

@Stateless // only used if the the application is deployed in a Java EE container
@LocalBean // only used if the the application is deployed in a Java EE container
public class PersonResource {
    @Context
    UriInfo uriInfo;
    @Context
    Request request;
    Person person_tmp;

    public PersonResource(UriInfo uriInfo, Request request, Person p) {
        this.uriInfo = uriInfo;
        this.request = request;
        this.person_tmp = p;
    }

    //REQUEST #2
    @GET
    @Produces({MediaType.TEXT_XML, MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public Response getPerson() {
        System.out.println("Returning person... " + this.person_tmp.getIdPerson());
        Response response;
        if(person_tmp.getIdPerson()==null)
        {
            response = Response.status(Response.Status.NOT_FOUND).entity(person_tmp).build();
        } else
        {
            response = Response.ok().entity(this.person_tmp).build();
        }
        return response;
    }

    //REQUEST #3
    @PUT
    @Consumes({ MediaType.APPLICATION_XML, MediaType.TEXT_XML, MediaType.APPLICATION_JSON })
    public Response putPerson(Person person) {
        System.out.println("--> Updating Person... " +this.person_tmp.getIdPerson());

        Response response;
        if(person_tmp.getIdPerson()==null)
        {
            response = Response.status(Response.Status.NOT_FOUND).build();
        } else
        {
            person.setIdPerson(this.person_tmp.getIdPerson());
            PersonBL user = new PersonBL();
            try
            {
                user.updatePerson(person);
                response = Response.created(uriInfo.getAbsolutePath()).build();
            }catch (Exception e)
            {
                System.out.println(e.getMessage());
                response = Response.status(Response.Status.NOT_FOUND).build();
            }
        }
        return response;
    }

    //REQUEST #5
    @DELETE
    public Response deletePerson() {
        System.out.println("--> Deleting Person...* " +this.person_tmp.getIdPerson());

        Response response;
        if(person_tmp.getIdPerson()==null)
        {
            response = Response.status(Response.Status.NOT_FOUND).build();
        }else{
            PersonBL user = new PersonBL();
            try
            {
                user.deletePerson(new Person(this.person_tmp));
                response = Response.created(uriInfo.getAbsolutePath()).build();
            }catch (Exception e)
            {
                System.out.println(e.getMessage());
                response = Response.status(Response.Status.NOT_FOUND).build();
            }
        }
        return response;
    }


    @Path("{measureType}")
    public MeasureResource getMeasureType(@PathParam("measureType") String measureType) {
        MeasureDefinitionBL user = new MeasureDefinitionBL();
        MeasureDefinition measureDefinition = new MeasureDefinition();
        try
        {
            measureDefinition = new MeasureDefinition(user.getMeasureDefinition(measureType.toLowerCase()));

        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        if (measureDefinition.getIdMeasureDef() == 0)
            return null;
        return new MeasureResource(uriInfo, request, new Person(this.person_tmp), new MeasureDefinition(measureDefinition));
    }
}