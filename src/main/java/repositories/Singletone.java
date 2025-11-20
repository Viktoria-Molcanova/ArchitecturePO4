package repositories;

import repositories.abstractions.CarrierRepository;
import repositories.abstractions.CustomerRepository;
import repositories.abstractions.DbContext;
import repositories.abstractions.TicketRepository;

public class Singletone implements DbContext {

	public static DbContext getInstance() {
		return Holder.INSTANCE;
	}

	private final CustomerRepository customerRepository = new CustomerRep();
	private final CarrierRepository carrierRepository = new CarrierRep();
	private final TicketRepository ticketRepository = new TicketRep();

	@Override
	public CustomerRepository getCustomerRepository() {
		return customerRepository;
	}

	@Override
	public CarrierRepository getCarrierRepository() {
		return carrierRepository;
	}

	@Override
	public TicketRepository geTicketRepository() {
		return ticketRepository;
	}

	private static class Holder {
		static final DbContext INSTANCE = new Singletone();
	}
}
