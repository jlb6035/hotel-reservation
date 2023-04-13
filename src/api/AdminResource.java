package api;

import model.customer.Customer;
import model.room.IRoom;
import model.room.Room;
import service.CustomerService;
import service.ReservationService;

import java.util.Collection;
import java.util.List;

public class AdminResource {
    private static final AdminResource INSTANCE = new AdminResource();
    public CustomerService customerService = CustomerService.getInstance();
    private ReservationService reservationService = ReservationService.getInstance();

    private AdminResource(){
    }

    public static AdminResource getInstance(){
        return INSTANCE;
    }
    public Customer getCustomer(String email){
        return customerService.getCustomer(email);
    }

    public void addRoom(List<IRoom> rooms){
        for (IRoom room : rooms){
            reservationService.addRoom(room);
        }
    }

    public Collection<Customer> getAllCustomers(){
        return customerService.getAllCustomers();
    }

    public void displayAllReservations(){
        reservationService.printAllReservations();
    }


}
