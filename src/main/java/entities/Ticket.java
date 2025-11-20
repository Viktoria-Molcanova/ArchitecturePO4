package entities;

import java.time.LocalDate;
import java.util.Objects;

public class Ticket {

	private int thicketId;
	private int routeId;
	private int placeId;
	private int price;
	private LocalDate date;
	private boolean available;

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

	public Integer getThicketId() {
		return thicketId;
	}

	public int getRouteId() {
		return routeId;
	}

	public int getPlaceId() {
		return placeId;
	}

	public int getPrice() {
		return price;
	}

	public LocalDate getDate() {
		return date;
	}

	public Ticket(int ticketId, int routeId, int placeId, int price, LocalDate date) {
		this.thicketId = ticketId;
		this.routeId = routeId;
		this.placeId = placeId;
		this.price = price;
		this.date = date;
		this.available = true;
	}

	@Override
	public String toString() {
		return "Ticket" +
				" Route Number " + routeId +
				", Place " + placeId +
				", Price " + price + " rub." +
				", Date " + date +
				", " + (available ? "Free" : "Busy");
	}

	public String toPrint() {
		return "Билет" +
				"\nМаршрут " + routeId +
				"\nМесто " + placeId +
				"\nЦена " + price + "руб." +
				"\nДата " + date;
	}

	@Override
	public int hashCode() {
		return Objects.hash(date, available, placeId, price, routeId, thicketId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Ticket))
			return false;
		Ticket other = (Ticket) obj;
		return Objects.equals(date, other.date)
				&& available == other.available
				&& placeId == other.placeId
				&& price == other.price
				&& routeId == other.routeId
				&& Objects.equals(thicketId, other.thicketId);
	}

}
