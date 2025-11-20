package clientapp;

import java.time.LocalDate;
import java.util.List;

import entities.Ticket;
import exceptions.PurchaseException;
import exceptions.TransactionException;

public interface StoreService {

	String getCustomerDetails();

	void purchaseTicket(Ticket ticket) throws PurchaseException;
	List<Ticket> findAvailableTickets(LocalDate date, int routeId);
}
