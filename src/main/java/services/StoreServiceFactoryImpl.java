package services;

import clientapp.StoreService;
import clientapp.StoreServiceFactory;
import entities.Customer;
import exceptions.AlreadyExistingCustomerException;
import exceptions.AuthorizationException;
import repositories.Singletone;
import repositories.abstractions.DbContext;

public class StoreServiceFactoryImpl implements StoreServiceFactory {

	private final DbContext context = Singletone.getInstance();

	@Override
	public StoreService forExistingCustomer(String loginName, String password) throws AuthorizationException {

		Customer customer = context.getCustomerRepository().findByLoginName(loginName);
		if (customer == null) {
			return null;
		}
		if (!customer.getPassword().equals(password)) {
			throw new AuthorizationException();
		}
		return new StoreServiceImpl(customer);
	}

	@Override
	public StoreService forNewCustomer(String loginName, String password, long cardNumber)
			throws AlreadyExistingCustomerException {

		Customer customer = context.getCustomerRepository().create(loginName, password, cardNumber);
		return new StoreServiceImpl(customer);
	}

}
