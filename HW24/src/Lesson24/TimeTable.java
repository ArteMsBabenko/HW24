package Lesson24;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.TreeMap;
import java.util.function.Function;
import static java.util.stream.Collectors.toList;

public class TimeTable {
	
	private static final long serialVersionUID = -1605084982551728546L;
	private Map<Days, ArrayList<Time>> timeTable;

	public TimeTable() {
		this.timeTable = new TreeMap<Days, ArrayList<Time>>();
	}

	public Optional<Entry<Days, ArrayList<Time>>> findDayInTimeTable(Days day) {
		Optional<Entry<Days, ArrayList<Time>>> timeTableEntryFound = timeTable.entrySet().stream()
				.filter(entry -> entry.getKey().equals(day)).findFirst();
		return timeTableEntryFound;
	}

	public Time getOpeningTime(Days day) {
		return timeTable.get(day).get(0);
	}

	public Time getClosingTime(Days day) {
		return timeTable.get(day).get(1);
	}

	public boolean addTimeTableEntry() throws IllegalTimeFormatException {
		Days day = Days.inputDay();
		if (day == null)
			return false;

		System.out.print("Opening time ");
		Time openingTime = Time.inputTime();

		System.out.print("Closing time ");
		Time closingTime = Time.inputTime();

		if (Time.timeToMinutes(closingTime) <= Time.timeToMinutes(openingTime)) {
			System.err.println("The entered closing time is less than or equal to the opening time");
			return false;
		}

		timeTable.put(day, new ArrayList<Time>(Arrays.asList(openingTime, closingTime)));
		System.out.println("Working hours in " + day.toLiteral(true) + "successfully added to work schedule");
		return true;
	}

	public boolean removeTimeTableEntry() {
		Days day = Days.inputDay();
		if (day == null)
			return false;

		Optional<Entry<Days, ArrayList<Time>>> timeTableEntryFound = findDayInTimeTable(day);

		if (timeTableEntryFound.isPresent()) {
			timeTable.remove(timeTableEntryFound.get().getKey());
			System.out.println("Working hours in" + day.toLiteral(true) + " successfully removed from schedule");
			return true;
		} else {
			System.err.println("Working hours in " + day.toLiteral(true) + " not in the work schedule");
			return false;
		}
	}
	
	public Function<Entry<Days, ArrayList<Time>>, String> timeTableToString () {
		return entry -> entry.getKey().toLiteral(false) + " " + entry.getValue().get(0).toString()
				+ " - " + entry.getValue().get(1).toString();
	}
		
	public void viewTimeTable() {
		System.out.println("Schedule");
		timeTable.entrySet().stream().map(timeTableToString()).collect(toList()).forEach(System.out::println);
		System.out.println();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((timeTable == null) ? 0 : timeTable.hashCode());
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
		TimeTable other = (TimeTable) obj;
		if (timeTable == null) {
			if (other.timeTable != null)
				return false;
		} else if (!timeTable.equals(other.timeTable))
			return false;
		return true;
	}

}
