package com.exalt.transportationbookingsystem.service.soap.client;

import com.exalt.transportationbookingsystem.dataaccess.tripdao.BusTripDao;
import com.exalt.transportationbookingsystem.dataaccess.tripdao.FlightDao;
import com.exalt.transportationbookingsystem.dataaccess.tripdao.TrainTripDao;
import com.exalt.transportationbookingsystem.dataaccess.tripdao.busTripDaoImpl;
import com.exalt.transportationbookingsystem.dataaccess.tripdao.flightDaoImpl;
import com.exalt.transportationbookingsystem.dataaccess.tripdao.trainTripDaoImpl;
import com.exalt.transportationbookingsystem.dataaccess.vehicledao.BusDao;
import com.exalt.transportationbookingsystem.dataaccess.vehicledao.PlaneDao;
import com.exalt.transportationbookingsystem.dataaccess.vehicledao.TrainDao;
import com.exalt.transportationbookingsystem.dataaccess.vehicledao.busDaoImpl;
import com.exalt.transportationbookingsystem.dataaccess.vehicledao.planeDaoImpl;
import com.exalt.transportationbookingsystem.dataaccess.vehicledao.trainDaoImpl;
import com.exalt.transportationbookingsystem.exception.NotFoundException;
import com.exalt.transportationbookingsystem.exception.RestrictDeleteException;
import com.exalt.transportationbookingsystem.models.trip.db.BusTripDB;
import com.exalt.transportationbookingsystem.models.trip.db.FlightDB;
import com.exalt.transportationbookingsystem.models.trip.db.TrainTripDB;
import jakarta.jws.WebMethod;
import jakarta.jws.WebService;

import java.util.ArrayList;
import java.util.List;


@WebService
public class vehicleSoapClient {

    private final String notFoundMsg = "Object not found to delete it";
    private final String restrictDeleteMsg = "Restrict delete for this object";

    BusDao busDao = new busDaoImpl();
    TrainDao trainDao = new trainDaoImpl();
    PlaneDao planeDao = new planeDaoImpl();
    BusTripDao busTripDao = new busTripDaoImpl();
    TrainTripDao trainTripDao = new trainTripDaoImpl();
    FlightDao flightDao = new flightDaoImpl();

    @WebMethod
    public void deleteBusById(int id) throws RestrictDeleteException, NotFoundException {
        if (busDao.readBusById(id) == null){
            throw new NotFoundException(notFoundMsg);
        }
        List<BusTripDB> AllTrips = new ArrayList<>(busTripDao.readAllBusTrips());
        for (BusTripDB i : AllTrips) {
            if (i.getBus().getId() == id){
                throw new RestrictDeleteException(restrictDeleteMsg);
            }
        }
        //else if bus not assigned to any trip delete it
        busDao.deleteBusById(id);
    }

    @WebMethod
    public void deleteTrainById(int id) throws NotFoundException, RestrictDeleteException {
        if (trainDao.readTrainById(id) == null){
            throw new NotFoundException(notFoundMsg);
        }
        List<TrainTripDB> AllTrips = new ArrayList<>(trainTripDao.readAllTrainTrips());
        for (TrainTripDB i : AllTrips) {
            if (i.getTrain().getId() == id){
                throw new RestrictDeleteException(restrictDeleteMsg);
            }
        }
        //else if train not assigned to any trip delete it
        trainDao.deleteTrainById(id);
    }

    @WebMethod
    public void deletePlaneById(int id) throws NotFoundException, RestrictDeleteException {
        if (planeDao.readPlaneById(id) == null) {
            throw new NotFoundException(notFoundMsg);
        }
        List<FlightDB> AllTrips = new ArrayList<>(flightDao.readAllFlights());
        for (FlightDB i : AllTrips) {
            if (i.getPlane().getId() == id){
                throw new RestrictDeleteException(restrictDeleteMsg);
            }
        }
        //else if plane not assigned to any flight delete it
        planeDao.deletePlaneById(id);
    }
}