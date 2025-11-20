package repositories.abstractions;

import java.util.List;

import entities.Customer;
import exceptions.AlreadyExistingCustomerException;

public interface CustomerRepository {

	Customer create(String loginName, String password, long cardNumber) throws AlreadyExistingCustomerException;

	Customer read(int customerId);

	Customer findByLoginName(String loginName);


	List<Customer> readAll();


	boolean update(Customer customer);


	boolean delete(Customer customer);
}
