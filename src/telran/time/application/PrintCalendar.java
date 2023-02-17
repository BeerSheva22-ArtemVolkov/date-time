package telran.time.application;

import java.time.*;
import java.time.format.TextStyle;
import java.util.Locale;

public class PrintCalendar {
	
	private static final String LANGUAGE_TAG = "en";
	private static final int YEAR_OFFSET = 10;
	private static final int WIDTH_FIELD = 4;
	private static Locale locale = Locale.forLanguageTag(LANGUAGE_TAG);

	public static void main(String[] args) {
		
		try {
			int monthYear[] = getMonthYear(args);
			printCalendar(monthYear[0], monthYear[1], DayOfWeek.of(monthYear[2]));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	private static int[] getMonthYear(String[] args) throws Exception {
		return args.length == 0 ? getCurrentMonthYear() : getCurrentMonthArgs(args);
	}
	
	private static int[] getCurrentMonthYear() {
		LocalDate current = LocalDate.now();
		return new int[] {current.getMonth().getValue(), current.getYear(), DayOfWeek.MONDAY.getValue()};
	}
	
	private static int[] getCurrentMonthArgs(String[] args) throws Exception {
		return new int[] {getMonthArgs(args), getYearArgs(args), getDayArgs(args)};
	}

	private static int getMonthArgs(String[] args) throws Exception {
		try {
			int res = Integer.parseInt(args[0]);
			if (res < 1 || res > 12) {
				throw new Exception("Month should be a number in the range [1-12]");
			}
			return res;
		} catch (NumberFormatException e) {
			throw new Exception("Month should be a number");
		}		
	}
	
	private static int getYearArgs(String[] args) throws Exception {
		int res = LocalDate.now().getYear();
		if (args.length > 1) {
			try {
				res = Integer.parseInt(args[1]);
				if (res < 0) {
					throw new Exception("Year must be positive number");
				}
			} catch (NumberFormatException e) {
				throw new Exception("Year must be a number");
			}
		}
		return res;
	}	
	
	private static int getDayArgs(String[] args) throws Exception {
		int res = DayOfWeek.MONDAY.getValue(); 
		if (args.length > 2) {
			try {
				res = DayOfWeek.valueOf(args[2].toUpperCase()).getValue();
			} catch (IllegalArgumentException e) {
				throw new Exception("Incorrect day value");
			}
		}
		return res;
	}
	
	private static void printCalendar(int month, int year, DayOfWeek startDay) {
		printTitle(month, year);
		printWeekDays(startDay);
		printDays(month, year, startDay.getValue()); 
	}

	private static void printTitle(int month, int year) {
		System.out.printf("%s%d, %s\n", " ".repeat(YEAR_OFFSET), year, Month.of(month).getDisplayName(TextStyle.FULL, locale));
	}
	
	private static void printWeekDays(DayOfWeek startDay) {
		System.out.print("  ");
		for (int i = 0; i < 7; i++) {
			System.out.printf("%s ", startDay.getDisplayName(TextStyle.SHORT, locale));
			startDay = startDay.plus(1);
		}
		System.out.println();
	}
	
	private static void printDays(int month, int year, int startDay) {
		
		int weekDayNumber = getFirstDay(month, year, startDay);
		int offset = getOffset(weekDayNumber);
		int nDays = YearMonth.of(year, month).lengthOfMonth();
		
		System.out.printf("%s", " ".repeat(offset));
		
		for(int date = 1; date <= nDays; date++) {
			System.out.printf("%4d", date);
			if (++weekDayNumber > 7) {
				System.out.println();
				weekDayNumber = 1;
			}
		}
	}

	private static int getFirstDay(int month, int year, int startDay) {
		return LocalDate.of(year, month, 1).getDayOfWeek().getValue() - (startDay - 1);
	}
	
	private static int getOffset(int weekDayNumber) {
		return (weekDayNumber - 1) * WIDTH_FIELD;
	}
	
}
