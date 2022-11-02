package com.exalt.transportationbookingsystem.controller.rest.personcontroller;

import com.exalt.transportationbookingsystem.exception.NotFoundException;
import com.exalt.transportationbookingsystem.exception.NullValueException;
import com.exalt.transportationbookingsystem.service.rest.personservice.DriverService;
import com.exalt.transportationbookingsystem.service.rest.personservice.driverServiceImpl;
import com.exalt.transportationbookingsystem.models.person.dto.DriverDTO;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/driver")
public class driverController {
    private final String nullWarningMsg = "Posted null value object or null id";
    private final String notFoundWarningMsg = "Object not found when searching by id";
    private final String internalServerErrorMsg = "Internal server error";
    private static final Logger LOGGER = LoggerFactory.getLogger(driverController.class);
    DriverService driverService = new driverServiceImpl();

    @POST
    @Path("/create")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addDriver(DriverDTO driver){
        try {
            return Response.ok(driverService.addDriver(driver)).build();
        }
        catch (NullValueException e)
        {
            LOGGER.warn(nullWarningMsg);
            return Response.status(400, e.getMessage()).build();
        }
        catch (Exception e)
        {
            LOGGER.info(internalServerErrorMsg);
            return Response.serverError().build();
        }
    }

    @PUT
    @Path("/update")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateDriver(DriverDTO driver){
        try{
            return Response.ok(driverService.updateDriver(driver)).build();
        }
        catch (NullValueException e)
        {
            LOGGER.warn(nullWarningMsg);
            return Response.status(400, e.getMessage()).build();
        }
        catch (NotFoundException e)
        {
            LOGGER.warn(notFoundWarningMsg);
            return Response.status(404,e.getMessage()).build();
        }
        catch (Exception e){
            LOGGER.info(internalServerErrorMsg);
            return Response.serverError().build();
        }
    }


    //Calling soap client at this method
    @DELETE
    @Path("/delete/{id}")
    public Response deleteDriver(@PathParam("id") int id){
        try {
            personSoapClient.PersonSoapClientService service = new personSoapClient.PersonSoapClientService();//class
            personSoapClient.PersonSoapClient port = service.getPersonSoapClientPort();//interface
            port.deleteDriverById(id);
            return Response.ok().build();
        }
        catch (personSoapClient.NotFoundException_Exception e){
            LOGGER.warn(notFoundWarningMsg);
            return Response.status(404, e.getMessage()).build();
        }
        catch (Exception e)
        {
            LOGGER.info(internalServerErrorMsg);
            return Response.serverError().build();
        }
    }

}