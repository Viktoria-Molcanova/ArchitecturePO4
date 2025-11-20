package services;

import exceptions.TransactionException;
import services.abstractions.PaymentService;


public class DemoSingletonePaymentService implements PaymentService {

	protected DemoSingletonePaymentService() {
	}

	public static PaymentService getInstance() {
		return Holder.INSTANCE;
	}

	@Override
	public void transaction(int amount, long cardFrom, long cardTo) throws TransactionException {

		if (!checkIfCardValid(cardTo)) {
			throw new TransactionException("No recipient account.");
		}
		if (!checkIfCardValid(cardFrom)) {
			throw new TransactionException("No withdrawal account.");
		}
		if (!checkFunds(cardFrom, amount)) {
			throw new TransactionException("Insufficient funds.");
		}
		if (!canAcceptPayment(cardTo, amount)) {
			throw new TransactionException("Recipient cannot accept payment.");
		}

		try {
			processTransaction(amount, cardFrom, cardTo);

		} catch (Exception e) {
			throw new TransactionException(e);
		}

	}

	private boolean checkIfCardValid(long cardNumber) {
		// TODO ...
		return true;
	}

	private boolean checkFunds(long cardNumber, int amount) {
		// TODO ...
		return true;
	}

	private boolean canAcceptPayment(long cardNumber, int amount) {
		// TODO ...
		return true;
	}

	private void processTransaction(int amount, long cardFrom, long cardTo) {

	}

	private static class Holder {
		static final PaymentService INSTANCE = new DemoSingletonePaymentService();
	}

}
