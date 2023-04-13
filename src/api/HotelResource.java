package api;

import model.customer.Customer;
import model.reservation.Reservation;
import model.room.IRoom;
import service.CustomerService;
import service.ReservationService;

import java.text.ParseException;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public class HotelResource {
    private static final HotelResource INSTANCE = new HotelResource();

    private CustomerService customerService = CustomerService.getInstance();
    private ReservationService reservationService = ReservationService.getInstance();

    private HotelResource(){
    }

    public static HotelResource getInstance(){
        return INSTANCE;
    }

    public Customer getCustomer(String email){
        return customerService.getCustomer(email);
    }

    public void createACustomer(String email, String firstName, String lastName){
        customerService.addCustomer(email, firstName, lastName);
    }

    public IRoom getRoom(String roomNumber){
        return reservationService.getRoom(roomNumber);
    }

    public Reservation bookARoom(String customerEmail, IRoom room, Date checkInDate, Date checkOutDate) throws Exception {
        Customer customer = customerService.getCustomer(customerEmail);
        return reservationService.reserveARoom(customer, room, checkInDate, checkOutDate);
    }

    public List<Reservation> getCustomersReservations(String customerEmail){
        Customer customer = customerService.getCustomer(customerEmail);
        return reservationService.getCustomersReservation(customer);
    }

    public Collection<IRoom> findARoom(Date checkIn, Date checkOut, String customerEmail) throws Exception {
        if (!isValidDateRange(checkIn, checkOut)){
            throw new Exception("DATE RANGE MUST BE VALID!");
        }
        return reservationService.findRooms(checkIn, checkOut, customerEmail);
    }




    public void addRoom(IRoom room){
        reservationService.addRoom(room);
    }

    public List<Date> suggestNewDates(Date checkIn, Date checkOut) throws ParseException {
        return reservationService.suggestNewDates(checkIn, checkOut);
    }

    public Collection<IRoom> getAllRooms(){
        return reservationService.getAllRooms();
    }

    public boolean isValidDateRange(Date checkIn, Date checkOut){
        return reservationService.isValidDateRange(checkIn, checkOut);
    }

    public void isAnyRoomAvailable() throws Exception {
        if (reservationService.isAnyRoomAvailable()){
            throw new Exception("NO ROOMS AVAILABLE! PLEASE ADD ROOMS FIRST!");
        }
    }

}
