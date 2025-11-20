package repositories.abstractions;

import entities.Carrier;

public interface CarrierRepository {

	Carrier read(int carrierId);
}
