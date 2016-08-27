package ehealth.controller;

import ehealth.model.bl.PersonBL;
import ehealth.model.to.People;
import ehealth.model.to.Person;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

@Stateless // will work only inside a Java EE application
@LocalBean // will work only inside a Java EE application
@Path("/person")
public class PersonCollectionResource {

    // Allows to insert contextual objects into the class,
    // e.g. ServletContext, Request, Response, UriInfo
    @Context
    UriInfo uriInfo;
    @Context
    Request request;

    //REQUEST #1 and #12
    // Return the list of people to the user in the browser
    @GET
    @Produces({MediaType.TEXT_XML,  MediaType.APPLICATION_JSON ,  MediaType.APPLICATION_XML })
    public Response getPersonsBrowser(@QueryParam("measureType") String measureType,@DefaultValue("1000000") @QueryParam("max") double max,@DefaultValue("-1000000") @QueryParam("min") double min) {
        System.out.println("Getting list of people...*");

        PersonBL user = new PersonBL();
        People people = new People();
        try
        {
            if(measureType != null)
            {
                people.setPerson(user.getPersonsByMinMax(measureType,min,max));
            }
            else
            {
                people.setPerson(user.getPersons());
                //If there are less than three people return an error
                if(people.getPerson().size()<3)
                    return Response.status(Response.Status.NOT_FOUND).build();
            }
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok().entity(people).build();
    }

    //Request #12
    //@GET
    //@Produces({MediaType.TEXT_XML,  MediaType.APPLICATION_JSON ,  MediaType.APPLICATION_XML })
    //public Response getPersonsByMinMaxMeasure(@QueryParam("measureType") String measureType,@DefaultValue("1000000") @QueryParam("max") double max,@DefaultValue("-1000000") @QueryParam("min") double min)
    //{
    //    System.out.println("Getting list of people by min max ...");

    //    PersonBL user = new PersonBL();
    //    People people = new People();
    //    try
    //    {
    //        people.setPerson(user.getPersonsByMinMax(measureType,min,max));

    //    }catch (Exception e)
    //    {
    //        System.out.println(e.getMessage());
    //        return Response.status(Response.Status.NOT_FOUND).build();
    //    }
    //    return Response.ok().entity(people).build();
    //}

    //REQUEST #4
    @POST
    @Produces({MediaType.APPLICATION_JSON ,  MediaType.APPLICATION_XML})
    @Consumes({MediaType.APPLICATION_JSON ,  MediaType.APPLICATION_XML})
    public Response newPerson(Person person) {
        Response response;

        PersonBL user = new PersonBL();
        try
        {
            //We don't have ID in our XML template so we need to put ID in firstname as a trick
            person.setFirstname(String.valueOf(user.setPerson(person).getIdPerson()+"::"+person.getFirstname()));
            response = Response.ok().entity(person).build();
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
            response = Response.status(Response.Status.NOT_FOUND).build();
        }
        return response;
    }

    // retuns the number of people
    // to get the total number of records
    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public Response getCount() {
        System.out.println("Getting count...");

        Response response;
        PersonBL user = new PersonBL();
        long count = 0;
        try
        {
            count = user.getPersonCount();
            response = Response.ok().entity(String.valueOf(count)).build();
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
            response = Response.status(Response.Status.NOT_FOUND).build();
        }
        return response;
    }

    // Return the id of the first and last person
    @GET
    @Path("firstlast")
    @Produces({MediaType.TEXT_PLAIN})
    public Response getFirstLast() {
        System.out.println("Getting First and Last");

        PersonBL user = new PersonBL();
        String firstLastPersonId = "";
        try
        {
            firstLastPersonId = user.getFirstId()+":"+user.getLastId();
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok().entity(firstLastPersonId).build();
    }

    // Defines that the next path parameter after the base url is
    // treated as a parameter and passed to the PersonResources
    // Allows to type http://localhost:599/base_url/1
    // 1 will be treaded as parameter todo and passed to PersonResource
    @Path("{person_id}")
    public PersonResource getPerson(@PathParam("person_id") long idPerson) {
        PersonBL user = new PersonBL();
        Person person = new Person();
        try
        {
            person = new Person(user.getPerson(idPerson));
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        //This is checked in PersonResource
        //if(person.getIdPerson() == 0)
        //    return null;
        return new PersonResource(uriInfo, request, new Person(person));
    }
}