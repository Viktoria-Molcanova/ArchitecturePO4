package repositories;

import java.util.ArrayList;
import java.util.List;

import entities.Customer;
import exceptions.AlreadyExistingCustomerException;
import repositories.abstractions.CustomerRepository;

public class CustomerRep implements CustomerRepository {

	private static final ArrayList<Customer> CUSTOMERS;

	static {
		CUSTOMERS = new ArrayList<Customer>();
	}

	@Override
	public Customer create(String loginName, String password, long cardNumber) throws AlreadyExistingCustomerException {

		int id = CUSTOMERS.size() + 1;
		Customer newCustomer = new Customer(id, loginName, password, cardNumber);
		if (CUSTOMERS.stream().anyMatch(c -> loginName.equalsIgnoreCase(c.getLoginName()))) {
			throw new AlreadyExistingCustomerException();
		}

		CUSTOMERS.add(newCustomer);
		return newCustomer;
	}

	@Override
	public Customer read(int customerId) {

		for (var customer : CUSTOMERS) {
			if (customer.getCustomerId() == customerId) {
				return customer;
			}
		}
		throw new RuntimeException("A customer with this ID not found");
	}

	@Override
	public Customer findByLoginName(String loginName) {

		return CUSTOMERS.stream().filter(c -> loginName.equalsIgnoreCase(c.getLoginName())).findAny().orElse(null);
	}

	@Override
	public List<Customer> readAll() {

		if (CUSTOMERS == null) {
			throw new RuntimeException("Customers database isn't ready");
		}

		return CUSTOMERS;
	}

	@Override
	public boolean update(Customer customer) {

		for (Customer aCustomer : CUSTOMERS) {
			if (aCustomer.getCustomerId() == customer.getCustomerId()) {
				CUSTOMERS.remove(aCustomer);
				CUSTOMERS.add(customer);
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean delete(Customer customer) {
		for (Customer aCustomer : CUSTOMERS) {
			if (aCustomer.equals(customer)) {
				CUSTOMERS.remove(aCustomer);
				return true;
			}
		}
		return false;
	}

}
