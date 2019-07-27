package com.javaxp.tps.model;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * The Class Report.
 */
public class Report {

	/** The incoming entities map. */
	private Map<String, BigDecimal> incomingEntitiesMap;

	/** The outgoing entities map. */
	private Map<String, BigDecimal> outgoingEntitiesMap;

	/**
	 * Instantiates a new report.
	 */
	public Report() {
		incomingEntitiesMap = new HashMap<>();
		outgoingEntitiesMap = new HashMap<>();
	}

	/**
	 * Adds the incoming entity.
	 *
	 * @param entity
	 *            the entity
	 * @param amount
	 *            the amount
	 */
	public void addIncomingEntity(String entity, BigDecimal amount) {
		incomingEntitiesMap.merge(entity, amount, BigDecimal::add);
	}

	/**
	 * Adds the outgoing entity.
	 *
	 * @param entity
	 *            the entity
	 * @param amount
	 *            the amount
	 */
	public void addOutgoingEntity(String entity, BigDecimal amount) {
		outgoingEntitiesMap.merge(entity, amount, BigDecimal::add);
	}

	/**
	 * Gets the incoming entities map.
	 *
	 * @return the incoming entities map
	 */
	public Map<String, BigDecimal> getIncomingEntitiesMap() {
		return incomingEntitiesMap;
	}

	/**
	 * Gets the outgoing entities map.
	 *
	 * @return the outgoing entities map
	 */
	public Map<String, BigDecimal> getOutgoingEntitiesMap() {
		return outgoingEntitiesMap;
	}

	
	/**
	 * The Class ReportItem.
	 */
	public static class ReportItem {

		/** The entity. */
		private String entity;

		/** The usd amount. */
		private BigDecimal usdAmount;

		/**
		 * Instantiates a new report item.
		 *
		 * @param entity
		 *            the entity
		 * @param usdAmount
		 *            the usd amount
		 */
		public ReportItem(String entity, BigDecimal usdAmount) {
			super();
			this.entity = entity;
			this.usdAmount = usdAmount;
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
		 * Gets the usd amount.
		 *
		 * @return the usd amount
		 */
		public BigDecimal getUsdAmount() {
			return usdAmount;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			return " [entity=" + entity + ", usdAmount=" + usdAmount + "]";
		}
	}
}
