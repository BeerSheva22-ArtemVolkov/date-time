package telran.time;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAdjuster;
import java.util.Arrays;
import java.util.List;

public class WorkingDays implements TemporalAdjuster {

	DayOfWeek[] dayOffs;
	int daysCount;
	
	@Override
	public Temporal adjustInto(Temporal temporal) {
		Temporal res = LocalDate.now();
		List<DayOfWeek> dayOffsList = Arrays.asList(dayOffs);
		do {
			if (!dayOffsList.contains(LocalDate.from(res).getDayOfWeek())) {
				daysCount--;
			}
			res = res.plus(1, ChronoUnit.DAYS);
		} while (daysCount > 0);
		return res;
	}

	public WorkingDays(DayOfWeek[] dayOffs, int daysCount) {
		this.dayOffs = dayOffs;
		this.daysCount = daysCount;
	}
	
	
}
