package clientapp;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.Scanner;


public abstract class ConsoleApplicationBase {

	static final Scanner SCANNER = new Scanner(System.in);

	static final String DIV_STR = "=".repeat(80);

	protected int inputInt(int min, int max) throws RuntimeException {
		int num = 0;
		try {
			num = SCANNER.nextInt();
		} catch (InputMismatchException ex) {
			throw new RuntimeException("This is not number.");
		} catch (Exception ex) {
			throw new RuntimeException("Something wrong.");
		}
		if (num < min || num > max) {
			throw new RuntimeException("You entered an invalid value.");
		}
		return num;
	}


	protected long inputLong(long min, long max) throws RuntimeException {
		long num = 0;
		try {
			num = SCANNER.nextLong();
		} catch (InputMismatchException ex) {
			throw new RuntimeException("This is not number.");
		} catch (Exception ex) {
			throw new RuntimeException("Something wrong.");
		}
		if (num < min || num > max) {
			throw new RuntimeException("You entered an invalid value.");
		}
		return num;
	}

	protected String inputString() throws RuntimeException {
		String str;
		try {
			str = SCANNER.next();
		} catch (Exception ex) {
			throw new RuntimeException("Something wrong.");
		}
		if (str.isEmpty()) {
			throw new RuntimeException("You must something enter");
		}
		return str;
	}

	protected LocalDate inputDate() throws RuntimeException {

		String str;
		LocalDate date;
		try {
			str = SCANNER.next();
		} catch (Exception ex) {
			throw new RuntimeException("Something wrong.");
		}
		if (str.isEmpty()) {
			throw new RuntimeException("You must something enter");
		}
		try {
			date = LocalDate.parse(str);
		} catch (DateTimeParseException ex) {
			throw new RuntimeException("You must enter date");
		}
		return date;
	}
}
