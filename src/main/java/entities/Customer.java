package entities;

import java.util.Objects;


public class Customer {

	private int customerId;
	private String loginName;
	private String password;
	private long cardNumber;

	public Customer(int customerId, String loginName, String password, long cardNumber) {
		this.customerId = customerId;
		this.loginName = loginName;
		this.password = password;
		this.cardNumber = cardNumber;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String name) {
		this.loginName = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public long getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(long cardNumber) {
		this.cardNumber = cardNumber;
	}

	public int getCustomerId() {
		return customerId;
	}

	@Override
	public int hashCode() {
		return Objects.hash(cardNumber, customerId, loginName, password);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Customer))
			return false;
		Customer other = (Customer) obj;
		return cardNumber == other.cardNumber && customerId == other.customerId
				&& Objects.equals(loginName, other.loginName)
				&& Objects.equals(password, other.password);
	}

	@Override
	public String toString() {
		return String.format(
				"Customer [customerId=%s, loginName=%s, password=%s, cardNumber=%016d]",
				customerId, loginName, password, cardNumber);
	}

}
