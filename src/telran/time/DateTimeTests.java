package telran.time;

import static org.junit.jupiter.api.Assertions.*;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DateTimeTests {

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void localDate() {
		LocalDate birthDateAS = LocalDate.parse("1799-06-26");
		LocalDate barMizvaAS = birthDateAS.plusYears(13);
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MMM.yyyy", Locale.forLanguageTag("ru"));
		System.out.println(barMizvaAS.format(dtf));
		ChronoUnit unit = ChronoUnit.DAYS;
		System.out.printf("Number of %s between %s and %s is %d", unit, birthDateAS, barMizvaAS, unit.between(birthDateAS, barMizvaAS));
	}

	@Test
	void barMizvaTest() {
		LocalDate current = LocalDate.now();
		assertEquals(current.plusYears(13), current.with(new BarMizvaAdjuster()));
	}
	
	@Test
	void nextFriday13Test() {
		LocalDate closestF13 = LocalDate.of(2023, 1, 13).with(new NextFriday13());
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale.forLanguageTag("en"));
		System.out.printf("Next friday 13th will be in %s\n" , closestF13.format(dtf));
		assertEquals(closestF13, LocalDate.parse("2023-10-13"));
		LocalDate nextF13 = closestF13.with(new NextFriday13());
		System.out.printf("And next friday 13th will be in %s\n", nextF13.format(dtf));
		assertEquals(nextF13, LocalDate.parse("2024-09-13"));
	}
	
	@Test
	void nextWorkingDay() {
		int daysAfter = 10;
		LocalDate workingDayAfter = LocalDate.of(2023, 2, 15).with(new WorkingDays(new DayOfWeek[] {DayOfWeek.SATURDAY, DayOfWeek.SUNDAY}, daysAfter));
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale.forLanguageTag("en"));
		System.out.printf("Next working day after %s day will be in %s\n", daysAfter, workingDayAfter.format(dtf));
		assertEquals(workingDayAfter, LocalDate.parse("2023-03-01"));
	}
	
	@Test
	void displayCurrentDateTimeCanadaTimeZones() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern(("EEEE MMMM dd yyyy HH:mm:ss ZZZZ"), Locale.CANADA);
		List<String> ids = Arrays.asList(TimeZone.getAvailableIDs()).stream().filter((x) -> x.toLowerCase().contains("canada")).collect(Collectors.toList());
		ids.stream().forEach(x -> System.out.printf("Now in %s %s\n", x, ZonedDateTime.now(ZoneId.of(x)).format(dtf)));
	}
}
