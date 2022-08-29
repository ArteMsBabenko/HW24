package Lesson24;

import java.io.Serializable;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.function.Predicate;

public class Schedule implements Serializable{
	
	private static final long serialVersionUID = 132192163341324910L;
	private Set<Seance> schedule;
	private Map<Time, Boolean> isTimeFree;
	private final Time technicalBreakTime = new Time(0, 25);
	private final Movie technicalBreakMovie = new Movie("Technical break", technicalBreakTime);

	public Schedule() throws IllegalTimeFormatException {
		this.schedule = new TreeSet<Seance>();
		this.isTimeFree = new TreeMap<Time, Boolean>();

		Time maxTime = Time.getMaxValue();
		for (int t = 0; t <= Time.timeToMinutes(maxTime); t++) {
			isTimeFree.put(Time.minutesToTime(t), true);
		}
	}
	
	public Map<Time, Boolean> getIsTimeFree() {
		return isTimeFree;
	}
	
	public Optional<Seance> getMovieSeanceFromSet(Movie movie) {
		Optional<Seance> seanceFound = schedule.stream().filter(entry -> entry.getMovie().equals(movie)).findFirst();

		return seanceFound;
	}
	
	public boolean isSeanceTimeFree(Seance seance) throws IllegalTimeFormatException {
		Time seanceStartTime = seance.getStartTime();
		Time seanceEndTime = seance.getEndTime();
		boolean isSeanceTimeFree = true;

		for (int t = Time.timeToMinutes(seanceStartTime);
				 t <= Time.timeToMinutes(Time.sumTime(seanceEndTime, technicalBreakTime));
				 t++) {
			if (!isTimeFree.get(Time.minutesToTime(t))) {
				isSeanceTimeFree = false;
				break;
			}
		}
		return isSeanceTimeFree;
	}

	public void registerSeanceInSchedule(Seance seance) throws IllegalTimeFormatException {
		Time seanceStartTime = seance.getStartTime();
		Time seanceEndTime = seance.getEndTime();

		schedule.add(seance);
		schedule.add(new Seance(technicalBreakMovie, Time.sumTime(seanceEndTime, new Time(0, 1))));
		for (int t = Time.timeToMinutes(seanceStartTime); t <= Time
				.timeToMinutes(Time.sumTime(seanceEndTime, technicalBreakTime)); t++) {
			isTimeFree.replace(Time.minutesToTime(t), false);
		}
	}

	public boolean addSeance(Movie movie) throws IllegalTimeFormatException {
		Seance seance = Seance.inputSeance(movie);

		if (isSeanceTimeFree(seance)) {
			registerSeanceInSchedule(seance);
			System.out.println("The session was successfully added to the schedule");
			return true;
		} else {
			System.err.println("Adding a session to the schedule is not possible because this time is busy");
			return false;
		}
	}

	public Optional<Seance> findSeance(Seance seance2) {
		Predicate<Seance> isEqual = seance ->
				 seance.getMovie().getTitle().equalsIgnoreCase(seance2.getMovie().getTitle())
				 && seance.getStartTime().equals(seance2.getStartTime())
				 && seance.getEndTime().equals(seance2.getEndTime());
		Optional<Seance> seanceFound = schedule.stream().filter(isEqual).findFirst();

		return seanceFound;
	}

	public void unregisterSeanceInSchedule(Seance seance, Optional<Seance> seanceFound)
			throws IllegalTimeFormatException {
		Time seanceStartTime = seance.getStartTime();
		Time seanceEndTime = seance.getEndTime();

		schedule.remove(seanceFound.get());
		schedule.remove(new Seance(technicalBreakMovie, Time.sumTime(seanceEndTime, new Time(0, 1))));
		for (int t = Time.timeToMinutes(seanceStartTime);
				 t <= Time.timeToMinutes(Time.sumTime(seanceEndTime, technicalBreakTime));
				 t++) {
			isTimeFree.replace(Time.minutesToTime(t), true);
		}
	}

	public boolean removeSeance(Seance removingSeance) throws IllegalTimeFormatException {
		Optional<Seance> removingSeanceFound = findSeance(removingSeance);

		if (removingSeanceFound.isPresent()) {
			unregisterSeanceInSchedule(removingSeance, removingSeanceFound);
			System.out.println("The session was successfully removed from the schedule");
			return true;
		} else {
			System.err.println("The session you entered is not scheduled");
			return false;
		}
	}
	
	public void viewSchedule() {
		System.out.println("Session schedule");
		schedule.forEach(System.out::println);
		System.out.println();
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((isTimeFree == null) ? 0 : isTimeFree.hashCode());
		result = prime * result + ((schedule == null) ? 0 : schedule.hashCode());
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
		Schedule other = (Schedule) obj;
		if (isTimeFree == null) {
			if (other.isTimeFree != null)
				return false;
		} else if (!isTimeFree.equals(other.isTimeFree))
			return false;
		if (schedule == null) {
			if (other.schedule != null)
				return false;
		} else if (!schedule.equals(other.schedule))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (Seance seance : schedule) {
			sb.append(seance.toString() + "n");
		}
		return sb.toString();
	}

}
