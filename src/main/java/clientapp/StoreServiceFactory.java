package clientapp;

import exceptions.AlreadyExistingCustomerException;
import exceptions.AuthorizationException;

public interface StoreServiceFactory {

	StoreService forExistingCustomer(String loginName, String password) throws AuthorizationException;

	StoreService forNewCustomer(String loginName, String password, long cardNumber)
			throws AlreadyExistingCustomerException;
}
