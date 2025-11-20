package entities;

public class Carrier {

	private int carrierId;
	private long cardNumber;

	public Carrier(int carrierId, long cardNumber) {
		this.carrierId = carrierId;
		this.cardNumber = cardNumber;
	}

	public long getCardNumber() {
		return cardNumber;
	}

	public int getCarrierId() {
		return carrierId;
	}
}
