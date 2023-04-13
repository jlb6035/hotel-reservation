package service;

import model.customer.Customer;

import java.util.*;

public class CustomerService {
    private static final CustomerService INSTANCE = new CustomerService();
    public static Map<String, Customer> customers = new HashMap<>();

    private CustomerService(){
    }

    public static CustomerService getInstance(){
        return INSTANCE;
    }

    public void addCustomer(String email, String firstName, String lastName){
        Customer customer = new Customer(firstName, lastName, email);
        customers.put(email, customer);
    }

    public Customer getCustomer(String email){
        return customers.get(email);
    }

    public Collection<Customer> getAllCustomers(){
        return customers.values();
    }



}
