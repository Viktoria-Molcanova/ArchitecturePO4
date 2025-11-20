package repositories;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import entities.Carrier;
import repositories.abstractions.CarrierRepository;

public class CarrierRep implements CarrierRepository {

	private static final List<Carrier> CARRIERS;

	static {

		CARRIERS = new ArrayList<>(
				List.of(new Carrier(1, 1)));
	}

	@Override
	public Carrier read(int carrierId) throws RuntimeException {

		for (var carrier : CARRIERS) {
			if (Objects.equals(carrier.getCarrierId(), carrierId)) {
				return carrier;
			}
		}
		throw new RuntimeException("A carrier with this ID not found");
	}
}
