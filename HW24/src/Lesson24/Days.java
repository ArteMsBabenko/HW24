package Lesson24;

import java.io.Serializable;
import java.util.Scanner;

public enum Days implements Serializable {
	MONDAY(1), TUESDAY(2), WEDNESDAY(3), THURSDAY(4), FRIDAY(5), SATURDAY(6), SUNDAY(7);
	
	int serialNumber;
	
	Days(int serialNumber) {
		this.serialNumber = serialNumber;
	}
	
	public int getSerialNumber() {
		return serialNumber;
	}
	public String toLiteral(boolean fullType) {
		String dayToLiteralFull;
		String dayToLiteralShort;

		switch (Days.this) {
		case MONDAY:
			dayToLiteralFull = "Monday";
			dayToLiteralShort = "Mn";
			break;
		case TUESDAY:
			dayToLiteralFull = "TUESDAY";
			dayToLiteralShort = "Tu.";
			break;
		case WEDNESDAY:
			dayToLiteralFull = "WEDNESDAY";
			dayToLiteralShort = "Wd.";
			break;
		case THURSDAY:
			dayToLiteralFull = "THURSDAY";
			dayToLiteralShort = "Th.";
			break;
		case FRIDAY:
			dayToLiteralFull = "FRIDAY";
			dayToLiteralShort = "Fr.";
			break;
		case SATURDAY:
			dayToLiteralFull = " SATURDAY";
			dayToLiteralShort = "Sa.";
			break;
		case SUNDAY:
			dayToLiteralFull = "SUNDAY";
			dayToLiteralShort = "Su.";
			break;
		default:
			dayToLiteralFull = dayToLiteralShort = "";
			break;
		}
		
		if (fullType)
			return dayToLiteralFull;
		else
			return dayToLiteralShort;
	}
	
	public static Days inputDay() {
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		Integer returnedNumber = 0;
		Days foundDay = null;
		
		System.out.print("Enter the number of the day of the week: ");
		if(sc.hasNextInt()) {
			int nextInt = sc.nextInt();
			
			if(nextInt > 0 && nextInt <=Days.values().length) {
				returnedNumber = nextInt;
			}
			else 
				System.err.println("The number of the day of the week must be in the range from 1 to 7");
			}
			else 
				System.err.println("The number of the day of the week is entered incorrectly");
		for(Days day : Days.values()) {
			if(day.getSerialNumber() == returnedNumber) {
				foundDay = day;
			}
		}
		return foundDay;
	}
}
	

