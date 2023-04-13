package model.reservation;

import model.customer.Customer;
import model.room.IRoom;

import java.util.Date;

public class Reservation {
    private Customer customer;
    private IRoom iRoom;
    private Date checkIn;
    private Date checkOut;

    public Reservation(Customer customer, IRoom iRoom, Date checkIn, Date checkOut) {
        this.customer = customer;
        this.iRoom = iRoom;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public IRoom getiRoom() {
        return iRoom;
    }

    public void setiRoom(IRoom iRoom) {
        this.iRoom = iRoom;
    }

    public Date getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(Date checkIn) {
        this.checkIn = checkIn;
    }

    public Date getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(Date checkOut) {
        this.checkOut = checkOut;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "customer=" + customer +
                ", iRoom=" + iRoom +
                ", checkIn=" + checkIn +
                ", checkOut=" + checkOut +
                '}';
    }
}
