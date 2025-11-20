package repositories.abstractions;

import java.util.List;

import entities.Ticket;


public interface TicketRepository {


	boolean create(Ticket ticket);

	List<Ticket> findAvailableByRoute(int routeId);


	boolean update(Ticket ticket);


	boolean delete(Ticket ticket);
}
