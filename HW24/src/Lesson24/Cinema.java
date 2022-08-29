package Lesson24;

import java.io.Serializable;
import java.util.Optional;
import java.util.Scanner;
import java.util.TreeSet;
import java.util.Map.Entry;

public class Cinema implements Serializable {
	
	private static final long serialVersionUID = -132012401495325908L;
    private String name;
    private TimeTable cinemaTimeTable;
    private TreeSet<Hall> cinemaHalls;
    
    public Cinema(String name) {
    	this.name = name;
    	this.cinemaTimeTable = new TimeTable();
    	this.cinemaHalls = new TreeSet<Hall>();
    }
    
    public String getName() {
    	return name;
    }
    
    public static Cinema inputCinema() {
    	@SuppressWarnings("resource")
    	Scanner sc = new Scanner(System.in);
    	
    	System.out.println("Enter the name of the cinema:");
    	String name = sc.nextLine();
    	if(name.equals("")) {
    		System.err.println("The name of the cinema was entered incorrectly");
    	name = " Some random cinema"; 
    	}
    	System.out.println("Cinema   "+ name.toString() +  " successfully created");
    	return new Cinema(name);
    }
    
    public void addHallToCinema() {
    	Hall cinemaHall = Hall.inputHall();
    	cinemaHalls.add(cinemaHall);
    	
    	System.out.println("The cinema hall " + cinemaHall.getName() + " successfully added to the cinema " + name);
    }
    
    public Optional<Hall> getHallFromSet(Hall cinemaHall) {
    	Optional<Hall> hallFound = cinemaHalls.stream().filter(entry -> entry.getName().equals(cinemaHall.getName())).findFirst();
    	return hallFound;
    }
    
    public boolean removeHallFromCinema() {
    	Hall cinemaHall = Hall.inputHall();
    	
    	Optional<Hall> hallFound = getHallFromSet(cinemaHall);
    	
    	if(hallFound.isPresent()) {
    		cinemaHalls.remove(hallFound.get());
    		System.out.println("Cinema hall" + hallFound.get().getName() + "successfully removed from the cinema" + name);
    		return true;
    	}
    	else {
    		System.err.println("Cinema hall" +cinemaHall.getName() +"absent in the cinema" + name );
    		return false;
    	}
    }
    
    
    public boolean addTimeTableToCinema() throws IllegalTimeFormatException {
    	boolean isDone = cinemaTimeTable.addTimeTableEntry();
    	if (isDone) {
    		System.out.println("The schedule of the cinema " + name + " has been successfully changed");
			return true;
		} else {
			System.err.println("It was not possible to make changes to the schedule of the cinema \" + name + \"ь");
			return false;
		}
    }
    	
    	
    
    
    public boolean removeTimeTableFromCinema() {
    	boolean isDone = cinemaTimeTable.removeTimeTableEntry();
    	if (isDone) {
    		System.out.println("The schedule of the cinema" + name + "successfully changed");
    		return true;
    	}
    	else {
    		System.err.println("It was not possible to make changes to the work schedule of the cinema " + name);
    		return false;
    	}
    }
    
    public boolean addTimeTableToHallInCinema() throws IllegalTimeFormatException {
    	Hall cinemaHall = Hall.inputHall();
    	
    	Optional<Hall> hallFound = getHallFromSet(cinemaHall);
    	
    	if(hallFound.isPresent()) {
    		boolean isDone = hallFound.get().addTimeTableToHall();
    		
    		if(isDone) {
    			System.out.println("Work schedule for " + hallFound.get() + " successfully added to cinema "+ name);
				return true;
    		}
    		else {
    			System.err.println("It was not possible to make changes to the schedule of the cinema " + name);
    			return false;
    		}
    	}
    		else {
    			System.err.println("Cinema " + cinemaHall.getName() + " is missing from cinema " + name);
    			return false;
    		}
    	}

	
    public boolean removeTimeTableFromHallInCinema() {
    	Hall cinemaHall = Hall.inputHall();
    	
    	Optional<Hall> hallFound = getHallFromSet(cinemaHall);
    	
    	if(hallFound.isPresent()) {
    		boolean isDone = hallFound.get().removeTimeTableFromHall();
    		
    		if(isDone) {
    			System.out.println("Work schedule for " + hallFound.get() + " successfully removed from cinema" + name );
    					return true;
    		}
    		else {
    			System.err.println("It was not possible to make changes to the schedule of the cinema " + name );
    			return false;
    		}
    	}
    		else {
    			System.err.println("Cinema " + cinemaHall.getName() + " is missing from cinema " + name);
    			return false;
    			}
    }
    
    public boolean addScheduleToHallInCinema() throws IllegalTimeFormatException {
    	Hall cinemaHall = Hall.inputHall();
    	
    	Optional<Hall> hallFound = getHallFromSet(cinemaHall);
    	
    	if (hallFound.isPresent()) {
			boolean isDone = hallFound.get().addScheduleToHall();
    	
    	if(isDone) {
    		System.out.println("Schedule of sessions for " + hallFound.get() + " successfully added to cinema "+ name);
    				return true;
    	}
    	else  {
    		System.err.println("Make changes to the schedule of sessions for " + hallFound.get() + " cinema "+ name + " failed");
    				return false;
    	}
    	
    }
    	else {
    		System.err.println("Cinema hall " + cinemaHall.getName() + " there is no cinema " + name);
    		return false;
    	}
}
    
    public boolean removeScheduleFromHallInCinema() {
    	  Hall cinemaHall = Hall.inputHall();
    	    
    	    Optional<Hall> hallFound = getHallFromSet(cinemaHall);
    	    
    	    if(hallFound.isPresent()) {
    	    	boolean isDone = hallFound.get().removeScheduleFromHall();
    	    	
    	    	if(isDone) {
    	    		System.out.println("Schedule of sessions for " + hallFound.get() + " successfully deleted from the cinema "+ name);
    	    				return true;
    	    	}
    	    	
    	    	else {
    	    		System.err.println("Make changes to the schedule of sessions for" + hallFound.get() +" cinema "+ name + " failed!");
    	    				return false;
    	    	}
    	    }
    	    else {
    	    	System.err.println("Cinema hall " + cinemaHall.getName() + " there is no cinema " + name);
    	    	return false;
    	    }  
    }
    
    public boolean addSeanceToScheduleInHallInCinema() throws IllegalTimeFormatException {
    	Hall cinemaHall = Hall.inputHall();

		Optional<Hall> hallFound = getHallFromSet(cinemaHall);
		
		if(hallFound.isPresent()) {
			Days day = Days.inputDay();
			if(day == null)
				return false;
			

			Optional<Entry<Days, Schedule>> hallScheduleEntryFound = hallFound.get().getHallSchedule().entrySet().stream().filter(entry -> entry.getKey().equals(day)).findFirst();
		
			if(hallScheduleEntryFound.isPresent()) {
				Movie movie = Movie.inputMovie();
				boolean isDone = hallScheduleEntryFound.get().getValue().addSeance(movie);
			
				if(isDone)
					return true;
				else
					return false;
			}
			else {
				System.err.println(day.toLiteral(true) + " is missing in the session schedule for " + hallFound.get()+ " cinema " + name);
				return false;
			}
		}
		else {
			System.err.println("Cinema hall " + cinemaHall.getName() + " there is no cinema " + name);
			return false;
		}
    }
    
    
    public boolean removeSeanceFromScheduleInHallInCinema() throws IllegalTimeFormatException {
    	Hall cinemaHall = Hall.inputHall();

		Optional<Hall> hallFound = getHallFromSet(cinemaHall);
		
		if(hallFound.isPresent()) {
			Days day = Days.inputDay();
			if(day == null)
				return false;
			
			Optional<Entry<Days, Schedule>> hallScheduleEntryFound = hallFound.get().getHallSchedule().entrySet().stream().filter(entry -> entry.getKey().equals(day)).findFirst();
			
			
			if(hallScheduleEntryFound.isPresent()) {
				Movie movie = Movie.inputMovie();
				Seance removingSeance = Seance.inputSeance(movie);
				boolean isDone = hallScheduleEntryFound.get().getValue().removeSeance(removingSeance);
				
				if(isDone)
					return true;
				else
					return false;
			}
			else {
				System.err.println(day.toLiteral(true) + " is missing in the session schedule for " + hallFound.get()+ " cinema " + name);
				return false;
			}
		}
		else {
			System.err.println("Cinema hall " + cinemaHall.getName() + " there is no cinema " + name);
			return false;
		}
    }
    
    public void addMovieToSeanceInScheduleInHallInCinema() throws IllegalTimeFormatException {
    	Movie movie = Movie.inputMovie();

		boolean addOneMoreSeance;
		
		do {

			Hall cinemaHall = Hall.inputHall();

			Optional<Hall> hallFound = getHallFromSet(cinemaHall);
			
			if(hallFound.isPresent()) {
				Days day = Days.inputDay();
				if(day == null)
					return;
				
				Optional<Entry<Days, Schedule>> hallScheduleEntryFound = hallFound.get().getHallSchedule().entrySet().stream().filter(entry -> entry.getKey().equals(day)).findFirst();
				
				if(hallScheduleEntryFound.isPresent()) {
					hallScheduleEntryFound.get().getValue().addSeance(movie);
				}
				else {
					System.err.println(day.toLiteral(true) + " absent in the session schedule for"+ hallFound.get() + " cinema " + name);
				}
			}
			else {
				System.err.println("Cinema hall " + cinemaHall.getName() + " there is no cinema " + name);
			}
			@SuppressWarnings("resource")
			Scanner sc = new Scanner(System.in);
			
			System.out.print("Add another session? (true/false) ");
			addOneMoreSeance = sc.nextBoolean();
			
			if(!addOneMoreSeance) {
				System.out.println("You refused to add more sessions for this movie");
				
			}
		} while(addOneMoreSeance);
    }
    
    public void removeMovieFromSeanceInScheduleInAllHallsInCinema() throws IllegalTimeFormatException{
    	Movie movie = Movie.inputMovie();
		boolean isDone = false;
		
		for(Hall hall : cinemaHalls) {
			for(Days day : Days.values()) {
				Optional<Seance> seance = hall.getHallSchedule().entrySet().stream()
						.filter(entry -> entry.getKey().equals(day)).findFirst().get().getValue()
						.getMovieSeanceFromSet(movie);
			
				if(seance.isPresent()) {
					hall.getHallSchedule().entrySet().stream().filter(entry -> entry.getKey().equals(day)).findFirst()
					.get().getValue().removeSeance(seance.get());
			isDone = true;
				}
				else
					break;
			
			}
		}
		if(isDone) {
			System.out.println(movie.toString() + " successfully deleted from the schedule of sessions of all cinema halls");
		}
		else {
			System.err.println("Will delete " + movie.toString()+ " from the schedule of the screenings of all cinema halls, it was not possible, because there is no such film in the schedule of the cinema theater");
		}
    }  
    
    public void viewCinemaTimeTable() {
    	System.out.print("Cinema " + name);
    	cinemaTimeTable.viewTimeTable();
    	System.out.println();
    }
    
    public void viewCinemaHalls() {
    	System.out.println("List of movie theaters of the cinema " + name);
    	cinemaHalls.forEach(System.out::println);
    	System.out.println();
    }
    
    public boolean viewHallTimeTableInCinema() {
    	Hall cinemaHall = Hall.inputHall();

		Optional<Hall> hallFound = getHallFromSet(cinemaHall);
    
    if(hallFound.isPresent()) {
    	hallFound.get().viewHallTimeTable();
		return true;
    }
    else {
    	System.err.println("Cinema hall " + cinemaHall.getName() + " there is no cinema " + name);
    	return false;
       }
   }
    
    public boolean viewHallScheduleInCinema() {
    	Hall cinemaHall = Hall.inputHall();

		Optional<Hall> hallFound = getHallFromSet(cinemaHall);

		if (hallFound.isPresent()) {
			hallFound.get().viewHallSchedule();
			return true;
		}
		else {
			System.err.println("Cinema hall " + cinemaHall.getName() + " there is no cinema " + name);
			return false;
		}
    }
    
    @Override
	public String toString() {
    	if(name == "Some random cinema") {
    		return (String) name;
    	}
    	else 
    		return "Cinema" + name;
    }
    
}
   
    
    
      
    
    
    
  