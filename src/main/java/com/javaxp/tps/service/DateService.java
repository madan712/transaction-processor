package com.javaxp.tps.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * The Class DateService.
 */
@Service
public class DateService {

	/** The exceptional currency. */
	@Value("${tps.exceptionalCurrency}")
	private String exceptionalCurrency;

	/**
	 * Gets the next working day if weekend.
	 *
	 * @param settlementDate
	 *            the settlement date
	 * @param currency
	 *            the currency
	 * @return the next working day if weekend
	 */
	public LocalDate getNextWorkingDayIfWeekend(LocalDate settlementDate, String currency) {

		if (isWorkingDay(settlementDate, currency)) {
			return settlementDate;
		}

		LocalDate actualSettlementDate = null;

		if (isExceptionalCurrency(currency)) {

			actualSettlementDate = addDays(DayOfWeek.FRIDAY, DayOfWeek.SATURDAY, settlementDate);

		} else {
			actualSettlementDate = addDays(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY, settlementDate);
		}

		return actualSettlementDate;
	}

	/**
	 * Gets the current day.
	 *
	 * @param settlementDate
	 *            the settlement date
	 * @return the current day
	 */
	private DayOfWeek getCurrentDay(LocalDate settlementDate) {

		return settlementDate.getDayOfWeek();
	}

	/**
	 * Adds the days.
	 *
	 * @param date
	 *            the date
	 * @param days
	 *            the days
	 * @return the date
	 */
	private LocalDate addDays(LocalDate date, int days) {

		return date.plusDays(days);

	}

	/**
	 * Adds the days.
	 *
	 * @param day1
	 *            the day 1
	 * @param day2
	 *            the day 2
	 * @param date
	 *            the date
	 * @return the date
	 */
	private LocalDate addDays(DayOfWeek day1, DayOfWeek day2, LocalDate date) {

		LocalDate actualSettlementDate = null;

		DayOfWeek currentDay = getCurrentDay(date);

		if (currentDay == day1) {
			actualSettlementDate = addDays(date, 2);
		} else if (currentDay == day2) {
			actualSettlementDate = addDays(date, 1);
		}

		return actualSettlementDate;
	}

	/**
	 * Checks if is working day.
	 *
	 * @param settlementDate
	 *            the settlement date
	 * @param currency
	 *            the currency
	 * @return true, if is working day
	 */
	private boolean isWorkingDay(LocalDate settlementDate, String currency) {

		boolean isWorkingDay = false;

		DayOfWeek currentDay = getCurrentDay(settlementDate);

		if (isExceptionalCurrency(currency)) {

			isWorkingDay = isWorkingDay(DayOfWeek.FRIDAY, DayOfWeek.SATURDAY, currentDay);

		} else {

			isWorkingDay = isWorkingDay(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY, currentDay);

		}

		return isWorkingDay;
	}

	/**
	 * Checks if is working day.
	 *
	 * @param weekend1
	 *            the start of week
	 * @param weekend2
	 *            the end of week
	 * @param currentDay
	 *            the current day
	 * @return true, if is working day
	 */
	private boolean isWorkingDay(DayOfWeek weekend1, DayOfWeek weekend2, DayOfWeek currentDay) {

		return !(currentDay.equals(weekend1) || currentDay.equals(weekend2));

	}

	/**
	 * Checks if is exceptional currency.
	 *
	 * @param currency
	 *            the currency
	 * @return true, if is exceptional currency
	 */
	private boolean isExceptionalCurrency(String currency) {

		return Arrays.asList(exceptionalCurrency.split(Pattern.quote("|"))).stream().anyMatch(c -> c.equals(currency));

	}

}
