package service;

import model.customer.Customer;
import model.reservation.Reservation;
import model.room.IRoom;
import model.room.Room;
import model.room.RoomType;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class ReservationService {
    private static final ReservationService INSTANCE = new ReservationService();

    Set<IRoom> rooms = new HashSet<>();
    private static Map<String, List<Reservation>> reservations = new HashMap<>();

    private ReservationService(){

    }

    public static ReservationService getInstance(){
        return INSTANCE;
    }
    public void addRoom(IRoom room){
        Room roomInfo = new Room(room.getRoomNumber(), room.getRoomPrice(), room.getRoomType());
        if (rooms.contains(roomInfo)){
            System.out.println("This room already exist!");
        } else {
            rooms.add(roomInfo);
        }
    }

    public IRoom getRoom(String roomId){
        for (IRoom room : rooms){
            if (room.getRoomNumber().equalsIgnoreCase(roomId)){
                return room;
            }
        }
        return null;
    }

    public Reservation reserveARoom(Customer customer, IRoom room, Date checkInDate, Date checkOutDate) throws Exception {
        Reservation reservation = new Reservation(customer, room, checkInDate, checkOutDate);
        List<Reservation> myReservations = getCustomersReservation(customer);
        if (myReservations == null){
            myReservations = new ArrayList<>();
        }
        myReservations.add(reservation);
        reservations.put(customer.getEmail(), myReservations);
        return reservation;
    }


    public Collection<IRoom> findRooms(Date checkInDate, Date checkOutDate, String customerEmail) throws Exception {
        List<IRoom> availableRooms = new ArrayList<>();
        List<Reservation> reservationList = getAllReservations();

        if (reservationList.isEmpty()){
            availableRooms.addAll(rooms);
        }


        for(int i = 0; i < reservationList.size(); i++){
            if(isRoomAvailable(reservationList.get(i), checkInDate, checkOutDate)){
                availableRooms.add(reservationList.get(i).getiRoom());
            }

        }

        return availableRooms;
    }

    public boolean isRoomAvailable(Reservation reservation, Date checkInDate, Date checkOutDate){
        return checkInDate.after(reservation.getCheckOut()) || checkOutDate.before(reservation.getCheckIn());
    }

    public boolean isValidDateRange(Date checkIn, Date checkOut){
        if (checkIn.after(checkOut) || checkOut.before(checkIn)){
            return false;
        } else {
            return true;
        }
    }



    public List<Reservation> getCustomersReservation(Customer customer){
        return reservations.get(customer.getEmail());
    }

    public void printAllReservations(){
        List<Reservation> myReservations = new ArrayList<>();

        for(List<Reservation> reservation : reservations.values()) {
            myReservations.addAll(reservation);
        }

        for (Reservation reservation : myReservations){
            System.out.println(reservation.toString());
        }
    }

    public Collection<IRoom> getAllRooms(){
        return rooms;
    }

    public List<Date> suggestNewDates(Date checkIn, Date checkOut) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        Calendar cn = Calendar.getInstance();
        cn.setTime(checkIn);
        cn.add(Calendar.DATE, 9);

        Calendar co = Calendar.getInstance();
        co.setTime(checkOut);
        co.add(Calendar.DATE, 9);

        String in = sdf.format(cn.getTime());
        String out = sdf.format(co.getTime());

        Date checkedIn = convertStringToDate(in);
        Date checkedOut = convertStringToDate(out);

        List<Date> dates = new ArrayList<>();
        dates.add(checkedIn);
        dates.add(checkedOut);

        return dates;
    }

    private Date convertStringToDate(String strDate) throws ParseException {
        Date date = new SimpleDateFormat("MM/dd/yyyy").parse(strDate);
        return date;
    }

    public boolean isAnyRoomAvailable() {
        return rooms.isEmpty();
    }

    public List<Reservation> getAllReservations(){
        List<Reservation> allReservations = new ArrayList<>();

        for(List<Reservation> reservation : reservations.values()) {
            allReservations.addAll(reservation);
        }

        return allReservations;
    }


}
