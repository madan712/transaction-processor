package com.javaxp.tps.model;

import java.math.BigDecimal;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.javaxp.tps.utils.Constant;

/**
 * The Class Instruction.
 */
public class Instruction {

	/** The entity. */
	private String entity;

	/** The instruction type. */
	private InstructionType instructionType;

	/** The agreed fx. */
	private BigDecimal agreedFx;

	/** The currency. */
	private String currency;

	/** The instruction date. */
	private LocalDate instructionDate;

	/** The settlement date. */
	private LocalDate settlementDate;

	/** The actual settlement date. */
	private LocalDate actualSettlementDate;

	/** The unit. */
	private int unit;

	/** The price. */
	private BigDecimal price;

	/**
	 * Instantiates a new instruction.
	 *
	 * @param builder
	 *            the builder
	 */
	private Instruction(Builder builder) {
		this.entity = builder.entity;
		this.instructionType = builder.instructionType;
		this.agreedFx = builder.agreedFx;
		this.currency = builder.currency;
		this.instructionDate = builder.instructionDate;
		this.settlementDate = builder.settlementDate;
		this.actualSettlementDate = builder.actualSettlementDate;
		this.unit = builder.unit;
		this.price = builder.price;
	}

	/**
	 * The Class Builder.
	 */
	public static class Builder {

		/** The entity. */
		private String entity;

		/** The instruction type. */
		private InstructionType instructionType;

		/** The agreed fx. */
		private BigDecimal agreedFx;

		/** The currency. */
		private String currency;

		/** The instruction date. */
		private LocalDate instructionDate;

		/** The settlement date. */
		private LocalDate settlementDate;

		/** The actual settlement date. */
		private LocalDate actualSettlementDate;

		/** The unit. */
		private int unit;

		/** The price. */
		private BigDecimal price;

		/** The formatter. */
		private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Constant.INPUT_DATE_FORMAT);

		/**
		 * Instantiates a new builder.
		 *
		 * @param entity
		 *            the entity
		 */
		public Builder(String entity) {
			this.entity = entity;
		}

		/**
		 * Instruction type.
		 *
		 * @param value
		 *            the value
		 * @return the builder
		 */
		public Builder instructionType(String value) {

			if (Constant.BUY.equals(value)) {
				instructionType = InstructionType.BUY;
			} else if (Constant.SELL.equals(value)) {
				instructionType = InstructionType.SELL;
			} else {
				instructionType = InstructionType.NA;
			}

			return this;
		}

		/**
		 * Agreed fx.
		 *
		 * @param value
		 *            the value
		 * @return the builder
		 */
		public Builder agreedFx(String value) {
			agreedFx = new BigDecimal(value);
			return this;
		}

		/**
		 * Currency.
		 *
		 * @param value
		 *            the value
		 * @return the builder
		 */
		public Builder currency(String value) {
			currency = value;
			return this;
		}

		/**
		 * Instruction date.
		 *
		 * @param value
		 *            the value
		 * @return the builder
		 * @throws ParseException
		 *             the parse exception
		 */
		public Builder instructionDate(String value) throws ParseException {

			instructionDate = LocalDate.parse(value, formatter);

			return this;
		}

		/**
		 * Settlement date.
		 *
		 * @param value
		 *            the value
		 * @return the builder
		 * @throws ParseException
		 *             the parse exception
		 */
		public Builder settlementDate(String value) throws ParseException {

			settlementDate = LocalDate.parse(value, formatter);

			return this;
		}

		/**
		 * Actual settlement date.
		 *
		 * @param value
		 *            the value
		 * @return the builder
		 */
		public Builder actualSettlementDate(LocalDate value) {
			actualSettlementDate = value;
			return this;
		}

		/**
		 * Unit.
		 *
		 * @param value
		 *            the value
		 * @return the builder
		 */
		public Builder unit(String value) {
			unit = Integer.parseInt(value);
			return this;
		}

		/**
		 * Price.
		 *
		 * @param value
		 *            the value
		 * @return the builder
		 */
		public Builder price(String value) {
			price = new BigDecimal(value);
			return this;
		}

		/**
		 * Builds the.
		 *
		 * @return the instruction
		 */
		public Instruction build() {
			return new Instruction(this);
		}

	}

	/**
	 * Gets the entity.
	 *
	 * @return the entity
	 */
	public String getEntity() {
		return entity;
	}

	/**
	 * Gets the instruction type.
	 *
	 * @return the instruction type
	 */
	public InstructionType getInstructionType() {
		return instructionType;
	}

	/**
	 * Gets the agreed fx.
	 *
	 * @return the agreed fx
	 */
	public BigDecimal getAgreedFx() {
		return agreedFx;
	}

	/**
	 * Gets the currency.
	 *
	 * @return the currency
	 */
	public String getCurrency() {
		return currency;
	}

	/**
	 * Gets the instruction date.
	 *
	 * @return the instruction date
	 */
	public LocalDate getInstructionDate() {
		return instructionDate;
	}

	/**
	 * Gets the settlement date.
	 *
	 * @return the settlement date
	 */
	public LocalDate getSettlementDate() {
		return settlementDate;
	}

	/**
	 * Gets the actual settlement date.
	 *
	 * @return the actual settlement date
	 */
	public LocalDate getActualSettlementDate() {
		return actualSettlementDate;
	}

	/**
	 * Sets the actual settlement date.
	 *
	 * @param actualSettlementDate
	 *            the new actual settlement date
	 */
	public void setActualSettlementDate(LocalDate actualSettlementDate) {
		this.actualSettlementDate = actualSettlementDate;
	}

	/**
	 * Gets the unit.
	 *
	 * @return the unit
	 */
	public int getUnit() {
		return unit;
	}

	/**
	 * Gets the price.
	 *
	 * @return the price
	 */
	public BigDecimal getPrice() {
		return price;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Instruction [entity=" + entity + ", instructionType=" + instructionType + ", agreedFx=" + agreedFx
				+ ", currency=" + currency + ", instructionDate=" + instructionDate + ", settlementDate="
				+ settlementDate + ", actualSettlementDate=" + actualSettlementDate + ", unit=" + unit + ", price="
				+ price + "]";
	}

}
