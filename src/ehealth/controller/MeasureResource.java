package ehealth.controller;


import ehealth.model.bl.HealthMeasureHistoryBL;
import ehealth.model.to.HealthMeasureHistory;
import ehealth.model.to.MeasureDefinition;
import ehealth.model.to.MeasureHistory;
import ehealth.model.to.Person;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Stateless // only used if the the application is deployed in a Java EE container
@LocalBean // only used if the the application is deployed in a Java EE container
public class MeasureResource {
    @Context
    UriInfo uriInfo;
    @Context
    Request request;
    Person person;
    MeasureDefinition measureDefinition;

    public MeasureResource(UriInfo uriInfo, Request request, Person p, MeasureDefinition md) {
        this.uriInfo = uriInfo;
        this.request = request;
        this.person = p;
        this.measureDefinition = md;
    }

    //REQUEST #6 AND REQUEST #11
    @GET
    @Produces({  MediaType.TEXT_XML, MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public Response getHealthMeasureHistories(@QueryParam("before") String before, @QueryParam("after") String after) {
        System.out.println("Returning HealthMeasureHistories... " + this.measureDefinition.getMeasureType());

        HealthMeasureHistoryBL user = new HealthMeasureHistoryBL();
        MeasureHistory measureHistory = new MeasureHistory();
        Date beforeDate = new Date();
        Date afterDate = new Date();

        if(before != null && after != null)
        {
            DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
            try
            {
                beforeDate  = df.parse(before);
                afterDate  = df.parse(after);
            } catch (ParseException e) {
                //HOW TO TAKE CARE of ERROR?
                System.out.println(e.getMessage());
                return Response.status(Response.Status.NOT_ACCEPTABLE).entity("Dates are not in dd-MM-yyyy format.").build();
            }
        }

        try
        {
            if(before != null && after != null)
                measureHistory.setHealthMeasureHistories(user.getMeasureHistories(person.getIdPerson(), measureDefinition.getIdMeasureDef(), beforeDate, afterDate));
            else
                measureHistory.setHealthMeasureHistories(user.getMeasureHistories(person.getIdPerson(), measureDefinition.getIdMeasureDef()));
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok().entity(measureHistory).build();
    }

    //REQUEST #8
    @POST
    @Produces({MediaType.APPLICATION_JSON ,  MediaType.APPLICATION_XML})
    @Consumes({MediaType.APPLICATION_JSON ,  MediaType.APPLICATION_XML})
    public Response newPerson(HealthMeasureHistory healthMeasureHistory) {
        HealthMeasureHistoryBL user = new HealthMeasureHistoryBL();

        Response response;
        try
        {
            healthMeasureHistory.setPerson(this.person);
            healthMeasureHistory.setMeasureDefinition(this.measureDefinition);
            healthMeasureHistory.setMid(user.setMeasureHistory(healthMeasureHistory).getMid());
            response = Response.ok().entity(healthMeasureHistory).build();
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
            response = Response.status(Response.Status.NOT_FOUND).build();
        }

        return response;
    }

    //REQUEST #7
    // Application integration
    @GET
    @Path("{mid}")
    @Produces({  MediaType.TEXT_XML, MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public Response getHealthMeasureHistory(@PathParam("mid") int mId) {
        System.out.println("Returning HealthMeasureHistory... " + this.measureDefinition.getMeasureType());

        Response response;
        HealthMeasureHistoryBL user = new HealthMeasureHistoryBL();
        HealthMeasureHistory healthMeasureHistory = new HealthMeasureHistory();
        try
        {
            healthMeasureHistory = new HealthMeasureHistory(user.getMeasureHistory(person.getIdPerson(), measureDefinition.getIdMeasureDef(), mId));
            response = Response.ok().entity(healthMeasureHistory).build();
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
            response = Response.status(Response.Status.NOT_FOUND).build();
        }
        if (healthMeasureHistory.getMid() == 0)
            response = Response.status(Response.Status.NOT_FOUND).build();
        System.out.println("MID: "+healthMeasureHistory.getMid());
        return response;
    }

    //REQUEST #10
    @PUT
    @Path("{mid}")
    @Consumes({ MediaType.APPLICATION_XML, MediaType.TEXT_XML, MediaType.APPLICATION_JSON })
    public Response updateMeasure(@PathParam("mid") int mId, HealthMeasureHistory healthMeasureHistory) {
        System.out.println("--> Updating Measure by MID... " +this.measureDefinition.getMeasureType());

        HealthMeasureHistoryBL user = new HealthMeasureHistoryBL();
        Response response;

        healthMeasureHistory.setPerson(this.person);
        healthMeasureHistory.setMeasureDefinition(this.measureDefinition);
        healthMeasureHistory.setMid(mId);

        try
        {
            user.updatePersonMeasure(healthMeasureHistory);
            response = Response.created(uriInfo.getAbsolutePath()).build();
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
            response = Response.status(Response.Status.NOT_FOUND).build();
        }
        return response;
    }
}