package com.javaxp.tps;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.javaxp.tps.service.DateService;

/**
 * The Class DateServiceTest.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class DateServiceTest {

	/** The date service. */
	@Autowired
	private DateService dateService;

	/**
	 * Test get next working day if weekend when normal working day then same date.
	 */
	@Test
	public void testGetNextWorkingDayIfWeekendWhenNormalWorkingDayThenSameDate() {

		LocalDate date = LocalDate.of(2018, 8, 24);

		LocalDate actualSettlementDate = dateService.getNextWorkingDayIfWeekend(date, "SGP");

		assertEquals(date, actualSettlementDate);
	}

	/**
	 * Test get next working day if weekend when normal saturday then add 2 days.
	 */
	@Test
	public void testGetNextWorkingDayIfWeekendWhenNormalSaturdayThenAdd2Days() {

		LocalDate date = LocalDate.of(2018, 8, 25);

		LocalDate actualSettlementDate = dateService.getNextWorkingDayIfWeekend(date, "SGP");

		LocalDate expectedDate = LocalDate.of(2018, 8, 27);

		assertEquals(expectedDate, actualSettlementDate);
	}

	/**
	 * Test get next working day if weekend when normal sunday then add 1 day.
	 */
	@Test
	public void testGetNextWorkingDayIfWeekendWhenNormalSundayThenAdd1Day() {

		LocalDate date = LocalDate.of(2018, 8, 26);

		LocalDate actualSettlementDate = dateService.getNextWorkingDayIfWeekend(date, "SGP");

		LocalDate expectedDate = LocalDate.of(2018, 8, 27);

		assertEquals(expectedDate, actualSettlementDate);
	}

	/**
	 * Test get next working day if weekend when exceptional working day then same
	 * date.
	 */
	@Test
	public void testGetNextWorkingDayIfWeekendWhenExceptionalWorkingDayThenSameDate() {

		LocalDate date = LocalDate.of(2018, 8, 26);

		LocalDate actualSettlementDate = dateService.getNextWorkingDayIfWeekend(date, "AED");

		assertEquals(date, actualSettlementDate);
	}

	/**
	 * Test get next working day if weekend when exceptional friday then add 2 days.
	 */
	@Test
	public void testGetNextWorkingDayIfWeekendWhenExceptionalFridayThenAdd2Days() {

		LocalDate date = LocalDate.of(2018, 8, 24);

		LocalDate actualSettlementDate = dateService.getNextWorkingDayIfWeekend(date, "AED");

		LocalDate expectedDate = LocalDate.of(2018, 8, 26);

		assertEquals(expectedDate, actualSettlementDate);
	}

	/**
	 * Test get next working day if weekend when exceptional saturday then add 1
	 * day.
	 */
	@Test
	public void testGetNextWorkingDayIfWeekendWhenExceptionalSaturdayThenAdd1Day() {

		LocalDate date = LocalDate.of(2018, 8, 25);

		LocalDate actualSettlementDate = dateService.getNextWorkingDayIfWeekend(date, "AED");

		LocalDate expectedDate = LocalDate.of(2018, 8, 26);

		assertEquals(expectedDate, actualSettlementDate);
	}

}
