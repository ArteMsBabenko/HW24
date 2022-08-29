package Lesson24;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Time implements Comparable<Time>, Serializable{
	
	private static final long serialVersionUID = 1487194286130673625L;
	private int min;
	private int hour;

	public Time(int hour, int min) throws IllegalTimeFormatException {
		if ((hour >= 0 && hour < 24) && (min >= 0 && min < 60)) {
			this.setHour(hour);
			this.setMin(min);
		} else
			throw new IllegalTimeFormatException();
	}

	public int getHour() {
		return hour;
	}

	public void setHour(int hour) {
		this.hour = hour;
	}

	public int getMin() {
		return min;
	}

	public void setMin(int min) {
		this.min = min;
	}
	
	public String toLiteral() {
		if (hour == 0)
			return min + " мин.";
		else if (min == 0)
			return hour + " час. ";
		else
			return hour + " час. " + min + " мин.";
	}

	public static Time inputTime() throws IllegalTimeFormatException {
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in).useDelimiter("[:,./\\s]");
		List<Integer> time = new ArrayList<Integer>(Arrays.asList(null, null));

		System.out.print("Введите время (часы/минуты): ");
		for (int i = 0; scanner.hasNextInt(); i++) {
			if (scanner.hasNextInt())
				time.add(i, scanner.nextInt());
		}

		if (time.size() - 2 < 2)
			System.err.println("Время введено некорректно!");

		int hour = Optional.ofNullable(time.get(0)).orElse(0);
		int min = Optional.ofNullable(time.get(1)).orElse(0);

		return new Time(hour, min);
	}

	public static Time sumTime(Time time1, Time time2) throws IllegalTimeFormatException {
		int hour = time1.getHour() + time2.getHour();
		int min = time1.getMin() + time2.getMin();

		if (min >= 60) {
			hour = hour + 1;
			min = min - 60;
		} else if (hour >= 24) {
			hour = hour - 24;
		}

		return new Time(hour, min);
	}

	public static Integer timeToMinutes(Time time) {
		return time.getHour() * 60 + time.getMin();
	}

	public static Time minutesToTime(Integer minutes) throws IllegalTimeFormatException {
		int hour = minutes / 60;
		int min = minutes - hour * 60;

		return new Time(hour, min);
	}

	public static final Time getMinValue() throws IllegalTimeFormatException {
		return new Time(0, 0);
	}

	public static final Time getMaxValue() throws IllegalTimeFormatException {
		return new Time(23, 59);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + hour;
		result = prime * result + min;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Time other = (Time) obj;
		if (hour != other.hour)
			return false;
		if (min != other.min)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return String.format("%2d", hour) + ":" + String.format("%02d", min);
	}

	@Override
	public int compareTo(Time anotherTime) {
		return timeToMinutes(this).compareTo(timeToMinutes(anotherTime));
	}
}

class IllegalTimeFormatException extends Exception {
	
	private static final long serialVersionUID = 7568427077693695817L;
	static final String message = "Input time must be within 0...24 for hours and 0..60 for minutes!";

	public IllegalTimeFormatException() {
		super(message);
	}
	
	

}
