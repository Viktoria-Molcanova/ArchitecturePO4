package exceptions;

public class PurchaseException extends Exception {

	private static final long serialVersionUID = 1L;

	public PurchaseException() {
		super();
	}

	public PurchaseException(String message, Throwable cause) {
		super(message, cause);
	}

	public PurchaseException(String message) {
		super(message);
	}

	public PurchaseException(Throwable cause) {
		super(cause);
	}

}
