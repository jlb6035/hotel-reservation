import Utils.ValidationUtil;
import api.AdminResource;
import api.HotelResource;
import model.customer.Customer;
import model.reservation.Reservation;
import model.room.IRoom;
import model.room.Room;
import model.room.RoomType;
import service.ReservationService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static Utils.ValidationUtil.isValidDate;
import static Utils.ValidationUtil.isValidMenuOption;

public class MainMenu {
    AdminResource adminResource = AdminResource.getInstance();
    HotelResource hotelResource = HotelResource.getInstance();

    Scanner scanner = new Scanner(System.in);

    public MainMenu() throws Exception {
    }

    public void mainMenu() throws Exception {
        int option = 0;
        do {
            System.out.println("                       ");
            System.out.println("Please enter a valid number 1 to 5");
            System.out.println("==================");
            System.out.println("1. Find and reserve a room");
            System.out.println("2. See my reservations");
            System.out.println("3. Create an account");
            System.out.println("4. Admin");
            System.out.println("5. Exit");

            try{
                option = scanner.nextInt();
            } catch (InputMismatchException e){
                System.out.println("Please enter a valid number 1 - 5!");
            }
            scanner.nextLine();
        } while (!isValidMenuOption(option));

        switch (option){
            case 1:
                makeReservation();
                break;
            case 2:
                findMyReservations();
                break;
            case 3:
                createAnAccount();
                break;
            case 4:
                adminMenu();
                break;
            case 5:
                System.out.println("Good Bye!");
                System.exit(0);
                break;
        }

    }

    private void adminMenu() throws Exception {
        AdminMenu adminMenu = new AdminMenu();
        adminMenu.adminMenu();
    }

    private void createAnAccount() throws Exception {
        System.out.println("let's create an account for you!");
        System.out.println("Please enter your first name: ");
        String firstName = scanner.nextLine();
        System.out.println("Please enter your last name: ");
        String lastName = scanner.nextLine();
        System.out.println("Please enter a email address: ");
        String customerEmail = scanner.nextLine();
        try{
            hotelResource.createACustomer(customerEmail, firstName, lastName);
            System.out.println("Your account has been created!");
        } catch (Exception e){
            System.out.println(e.toString());
            mainMenu();
        }
        mainMenu();
    }

    private void findMyReservations() throws Exception {
        System.out.println("Please enter your email address: ");
        String customerEmail = scanner.nextLine();
        Customer customer = hotelResource.getCustomer(customerEmail);
        if (customer == null){
            System.out.println("Looks like you do not have any reservations!");
            mainMenu();
        }
        List<Reservation> reservations = this.hotelResource.getCustomersReservations(customerEmail);
        System.out.println("Here are your reservations: ");
        System.out.println("=============================");
        for (int i = 0; i < reservations.size(); i++){
            System.out.println(reservations.get(i).toString());
        }
        mainMenu();
    }


    private void makeReservation() throws Exception {
        try{
            hotelResource.isAnyRoomAvailable();
        } catch (Exception e){
            System.out.println(e);
            mainMenu();
        }
        System.out.println("Please enter your email address: ");
        String customerEmail = scanner.nextLine();
        Customer customer = hotelResource.getCustomer(customerEmail);
        if (customer == null){
            System.out.println("Looks like you don't have an account, let's create one for you!");
            System.out.println("Please enter your first name: ");
            String firstName = scanner.nextLine();
            System.out.println("Please enter your last name: ");
            String lastName = scanner.nextLine();
            try{
                hotelResource.createACustomer(customerEmail, firstName, lastName);
            } catch (Exception e){
                System.out.println(e.toString());
                mainMenu();
            }
            System.out.println("Your account has been created! ");
        }
        String checkInDate = "";
        String checkOutDate = "";
        Date checkIn = null;
        Date checkOut = null;
        Boolean result = null;
        Collection<IRoom> rooms = null;
        do {
            System.out.println("Please enter a valid check in date using MM/DD/YYYY format: ");
            checkInDate = scanner.nextLine();
            System.out.println("Please enter a valid check out date using MM/DD/YYYY format: ");
            checkOutDate = scanner.nextLine();
            if (isValidDate(checkInDate) && isValidDate(checkOutDate)){
                checkIn = new SimpleDateFormat("MM/dd/yyyy").parse(checkInDate);
                checkOut = new SimpleDateFormat("MM/dd/yyyy").parse(checkOutDate);
                try {
                    rooms = hotelResource.findARoom(checkIn, checkOut, customerEmail);
                } catch (Exception e){
                    System.out.println(e);
                    mainMenu();
                }
                if (rooms.size() == 0){
                    List<Date> dates =  hotelResource.suggestNewDates(checkIn, checkOut);
                    try{
                        checkIn = dates.get(0);
                        checkOut = dates.get(1);
                        rooms = hotelResource.findARoom(checkIn, checkOut, customerEmail);
                        System.out.println("No rooms available, please try our suggested dates!");
                        System.out.println(dates.get(0).toString() + " to " + dates.get(1).toString());
                    } catch (Exception e){
                        System.out.println(e);
                        mainMenu();
                    }


                    result = false;
                } else {
                    result = false;
                }
            } else {
                result = true;
            }
        } while (result);

        Boolean results = false;
        do {
            System.out.println("Here are the available rooms: ");
            for (IRoom room : rooms){
                System.out.println("Room number " + room.getRoomNumber() + " " + room.getRoomType());
            }
            System.out.println("What room would you like to stay in? Please enter the room number");

            String roomId = scanner.nextLine();
            for (IRoom room : rooms){
                if (room.getRoomNumber().equalsIgnoreCase(roomId)){
                    IRoom myRoom = hotelResource.getRoom(roomId);
                    hotelResource.bookARoom(customerEmail, myRoom, checkIn, checkOut);
                    System.out.println("Your reservation is booked!");
                    mainMenu();
                } else {
                    results = true;
                }
            }
        } while (results);



    }

}
