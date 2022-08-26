package default_crud_app.service;

import default_crud_app.model.Customer;
import default_crud_app.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Long add(String name, String surname) {

        return customerRepository.add(new Customer(name, surname));

    }

    public List<Customer> getList() {

        return customerRepository.getList();

    }

    public List<Customer> find(Customer customer) {

        return customerRepository.find(customer);

    }

    public Customer getById(Long id) {

        return customerRepository.getById(id);

    }

    public void edit(Long id, Customer customer) {

        customerRepository.edit(id, customer);

    }

    public void remove(Long id) {

        customerRepository.remove(id);

    }

    public void generate() {
        List<Customer> customerList = customerRepository.getList();
        List<String> names = customerList.stream()
                .map(Customer::getName)
                .collect(Collectors.toSet())
                .stream().toList();
        List<String> surnames = customerList.stream()
                .map(Customer::getSurname)
                .collect(Collectors.toSet())
                .stream().toList();

        customerList.clear();
        names.forEach(n -> surnames.forEach(s -> customerList.add(new Customer(n,s))));

        Collections.shuffle(customerList);

        customerList.stream()
                .filter(customer -> customerRepository.find(customer).isEmpty())
                .forEach(customerRepository::add);


    }

}