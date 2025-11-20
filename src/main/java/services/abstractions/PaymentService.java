package services.abstractions;

import exceptions.TransactionException;

/**
 * Интерфейс взаимодействия с базой сервиса платежей
 */
public interface PaymentService {

	/**
	 * Метод проведения транзакции
	 *
	 * @param amount   размер транзакции
	 * @param cardFrom карта покупателя
	 * @param cardTo   карта продавца
	 * @throws TransactionException в случае ошибки при обработке транзакции
	 */
	void transaction(int amount, long cardFrom, long cardTo) throws TransactionException;
}
