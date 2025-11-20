package clientapp;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import entities.Ticket;
import exceptions.AlreadyExistingCustomerException;
import exceptions.AuthorizationException;
import exceptions.PurchaseException;
import services.StoreServiceFactoryImpl;

public class ConsoleApplication extends ConsoleApplicationBase {

	private final StoreServiceFactory storeServicesFactory = new StoreServiceFactoryImpl();
	private StoreService customerStoreServices;
	private int ticketRouteId;
	private LocalDate ticketDate;

	private static final String RED = "\u001B[31m";
	private static final String GREEN = "\u001B[32m";
	private static final String YELLOW = "\u001B[33m";
	private static final String BLUE = "\u001B[34m";
	private static final String RESET = "\u001B[0m";

	public void runLifecycle() {
		boolean run = true;
		while (run) {
			printColoredMessageLine(BLUE, "Приложение для покупки транспортных билетов");
			printMenuOptions();
			int choice = getUserChoice(0, 2);
			run = handleLoginRegisterChoice(choice);
		}
	}

	private void printMenuOptions() {
		System.out.println(YELLOW + "1. Войти как зарегистрированный клиент" + RESET);
		System.out.println(YELLOW + "2. Зарегистрировать нового клиента" + RESET);
		System.out.println(RED + "0. Завершить приложение" + RESET);
		System.out.print("?> ");
	}

	private int getUserChoice(int min, int max) {
		try {
			return inputInt(min, max);
		} catch (RuntimeException ex) {
			printColoredMessageLine(RED, ex.getMessage());
			return getUserChoice(min, max);
		} finally {
			System.out.println(DIV_STR);
		}
	}

	private boolean handleLoginRegisterChoice(int choice) {
		customerStoreServices = null;
		switch (choice) {
			case 1:
				if (login()) {
					runBuyingMenu();
				}
				break;
			case 2:
				if (register()) {
					runBuyingMenu();
				}
				break;
			default:
				return false;
		}
		return true;
	}

	private boolean login() {
		printColoredMessageLine(BLUE, "Вход для существующего клиента");
		System.out.print("Имя для входа: ");
		String loginName = inputString();
		System.out.print("Пароль: ");
		String password = inputString();
		System.out.println(DIV_STR);

		try {
			customerStoreServices = storeServicesFactory.forExistingCustomer(loginName, password);
			if (customerStoreServices != null) {
				printColoredMessageLine(GREEN, "Вы успешно вошли в систему...");
				return true;
			} else {
				printColoredMessageLine(RED, "Пользователь не найден.");
				return false;
			}
		} catch (AuthorizationException e) {
			printColoredMessageLine(RED, "Ошибка авторизации: " + e.getMessage());
			return false;
		} catch (Exception e) {
			printColoredMessageLine(RED, "Неизвестная ошибка входа: " + e.getMessage());
			e.printStackTrace();
			return false;
		}
	}

	private boolean register() {
		printColoredMessageLine(BLUE, "Регистрация нового клиента");
		System.out.print("Имя для входа: ");
		String loginName = inputString();
		System.out.print("Пароль: ");
		String password = inputString();
		System.out.print("Повторите пароль: ");
		String password2 = inputString();

		if (!password.equals(password2)) {
			printColoredMessageLine(RED, "Пароли не совпадают.");
			return false;
		}

		System.out.print("Номер банковской карты: ");
		long cardNumber = inputLong(1L, 9999_9999_9999_9999L);
		System.out.println(DIV_STR);

		try {
			customerStoreServices = storeServicesFactory.forNewCustomer(loginName, password, cardNumber);
			printColoredMessageLine(GREEN, "Регистрация успешна.");
			return true;
		} catch (AlreadyExistingCustomerException e) {
			printColoredMessageLine(RED, "Ошибка регистрации: " + e.getMessage());
			return false;
		} catch (Exception e) {
			printColoredMessageLine(RED, "Неизвестная ошибка регистрации: " + e.getMessage());
			e.printStackTrace();
			return false;
		}
	}

	private void runBuyingMenu() {
		assert customerStoreServices != null : "customerStoreService is null";
		boolean run = true;
		while (run) {
			printColoredHeader(BLUE, "Приложение для покупки транспортных билетов");

			System.out.println(YELLOW + "1. Выбрать маршрут и посмотреть доступные места" + RESET);
			System.out.println(RED + "0. Выйти" + RESET);

			System.out.print("?> ");
			int choice = 0;
			try {
				choice = inputInt(0, 1);
			} catch (RuntimeException ex) {
				printColoredMessageLine(RED, ex.getMessage());
				continue;
			} finally {
				System.out.println(DIV_STR);
			}
			run = runBuyingMenuChoiceLogic(choice);
		}
	}

	private boolean runBuyingMenuChoiceLogic(int choice) {
		if (choice == 1) {
			ticketRouteId = runSelectRouteMenu();
			if (ticketRouteId > 0) {
				ticketDate = runSelectDate();
				if (ticketDate != null) {
					try {
						List<Ticket> availableTickets = customerStoreServices.findAvailableTickets(ticketDate, ticketRouteId);
						List<Ticket> filteredTickets = availableTickets.stream()
								.filter(ticket -> ticket.getDate().equals(ticketDate) && ticket.isAvailable())
								.collect(Collectors.toList());
						printTickets(filteredTickets);
						buyTicketMenu(filteredTickets);

					} catch (RuntimeException ex) {
						printColoredMessageLine(RED, ex.getMessage());
					}
				}
			}
		} else {
			return false;
		}
		return true;
	}

	private int runSelectRouteMenu() {
		printColoredMessageLine(BLUE, "Выбор маршрута");
		System.out.print("Введите номер маршрута (1-2): ");
		try {
			return inputInt(1, 2);
		} catch (RuntimeException ex) {
			printColoredMessageLine(RED, ex.getMessage());
			return -1;
		} finally {
			System.out.println(DIV_STR);
		}
	}

	private LocalDate runSelectDate() {
		System.out.print("Введите дату (YYYY-MM-DD): ");
		try {
			return inputDate();
		} catch (RuntimeException ex) {
			printColoredMessageLine(RED, ex.getMessage());
			return null;
		} finally {
			System.out.println(DIV_STR);
		}
	}

	private void printTickets(List<Ticket> tickets) {

		if (tickets.isEmpty()) {
			printColoredMessageLine(YELLOW, "Нет доступных билетов.");
		} else {
			printColoredMessageLine(GREEN, "Доступные билеты:");
			for (Ticket ticket : tickets) {
				System.out.println(ticket.toString());
			}
		}
		System.out.println(DIV_STR);
	}

	private void buyTicketMenu(List<Ticket> availableTickets) {
		printColoredMessageLine(BLUE, "Подтверждение покупки");
		System.out.print("Купить билет по маршруту " + ticketRouteId + " на " + ticketDate + "? (yes/no): ");
		String answer = inputString();
		System.out.println(DIV_STR);
		buyTicketMenuConfirmLogic(availableTickets, answer);
	}

	private void buyTicketMenuConfirmLogic(List<Ticket> availableTickets, String answer) {
		if (answer.equalsIgnoreCase("yes")) {
			if (availableTickets.isEmpty()) {
				printColoredMessageLine(RED, "Нет доступных билетов для покупки.");
				return;
			}

			System.out.println("Доступные билеты для покупки:");
			for (int i = 0; i < availableTickets.size(); i++) {
				System.out.println((i + 1) + ". " + availableTickets.get(i).toPrint());
			}

			System.out.print("Введите номер билета для покупки: ");
			int ticketIndex = inputInt(1, availableTickets.size()) - 1;

			try {
				Ticket selectedTicket = availableTickets.get(ticketIndex);
				customerStoreServices.purchaseTicket(selectedTicket);
				printColoredMessageLine(GREEN, "Билет успешно приобретён:\n" + selectedTicket.toPrint());
			} catch (IndexOutOfBoundsException e) {
				printColoredMessageLine(RED, "Неверный номер билета.");
			} catch (PurchaseException ex) {
				printColoredMessageLine(RED, "Ошибка покупки: " + ex.getMessage());
			}
		} else {
			printColoredMessageLine(YELLOW, "Покупка отменена.");
		}
	}
	private void printColoredHeader(String color, String header) {
		printColoredMessageLine(color, header + "\nКлиент: " + customerStoreServices.getCustomerDetails());
	}

	private void printColoredMessageLine(String color, String message) {
		System.out.println(color + message + RESET);
		System.out.println(DIV_STR);
	}
}
