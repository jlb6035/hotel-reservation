import api.AdminResource;
import api.HotelResource;
import model.customer.Customer;
import model.room.IRoom;
import model.room.Room;
import model.room.RoomType;

import java.text.ParseException;
import java.util.Collection;
import java.util.InputMismatchException;
import java.util.Scanner;

import static Utils.ValidationUtil.isValidMenuOption;

public class AdminMenu {

    AdminResource adminResource = AdminResource.getInstance();
    HotelResource hotelResource = HotelResource.getInstance();

    Scanner scanner = new Scanner(System.in);

    public void adminMenu() throws Exception {
        int option = 0;

        do{
            System.out.println("1. See All Customers");
            System.out.println("2. See All Rooms");
            System.out.println("3. See All Reservations");
            System.out.println("4. Add A Room");
            System.out.println("5. Back To Main Menu");
            System.out.println("            ");

            try{
                option = scanner.nextInt();
            } catch (InputMismatchException e){
                System.out.println("Please enter a valid number 1 - 5!");
            }
            scanner.nextLine();
        } while (!isValidMenuOption(option));


        switch (option){
            case 1:
                seeAllCustomers();
                break;
            case 2:
                seeAllRooms();
                break;
            case 3:
                seeAllReservations();
                break;
            case 4:
                addARoom();
                break;
            case 5:
                MainMenu mainMenu = new MainMenu();
                mainMenu.mainMenu();
                break;
        }
    }

    private void seeAllCustomers() throws Exception {
        Collection<Customer> customers = adminResource.getAllCustomers();
        for (Customer customer : customers){
            System.out.println(customer.toString());
        }
        adminMenu();
    }

    private void seeAllReservations() throws Exception {
        adminResource.displayAllReservations();
        adminMenu();
    }

    private void seeAllRooms() throws Exception {
        Collection<IRoom> rooms = hotelResource.getAllRooms();

        for (IRoom room : rooms){
            System.out.println(room.toString());
        }
        adminMenu();
    }

    private void addARoom() throws Exception {
        Boolean result = true;
        String roomNumber = "";
        String roomType = "";
        Double priceConverted = 0.0;
        RoomType roomType1 = null;
        do {
            try {
                System.out.println("Please enter a room number: ");
                roomNumber = scanner.nextLine();
                try{
                    int test = Integer.parseInt(roomNumber);
                } catch (Exception e){
                    System.out.println("Please enter a valid number!");
                    addARoom();
                    scanner.nextLine();
                }
                System.out.println("Please enter a room type, SINGLE or DOUBLE: ");
                roomType = scanner.nextLine();
                if (roomType.equalsIgnoreCase("SINGLE") || roomType.equalsIgnoreCase("DOUBLE")){
                    if (RoomType.SINGLE.toString().equalsIgnoreCase(roomType)){
                        roomType1 = RoomType.SINGLE;
                    }
                    if (RoomType.DOUBLE.toString().equalsIgnoreCase(roomType)){
                        roomType1 = RoomType.DOUBLE;
                    }
                } else {
                    System.out.println("Please enter SINGLE OR DOUBLE!");
                    throw new Exception("Please enter SINGLE OR DOUBLE!");
                }
                System.out.println("Please enter a price: ");
                String price = scanner.nextLine();
                try {
                    priceConverted = Double.parseDouble(price);
                } catch (Exception e){
                    System.out.println("Please enter a valid price!");
                    throw new Exception("Please enter a valid price!");
                }
                result = false;
            } catch (Exception e){
                System.out.println("Let's try again...");
            }
        } while (result);


        Room room = new Room(roomNumber, priceConverted, roomType1);
        hotelResource.addRoom(room);
        System.out.println("Room Added!");
        adminMenu();
    }
}
