package com.javaxp.tps.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.javaxp.tps.model.Instruction;
import com.javaxp.tps.model.InstructionType;
import com.javaxp.tps.model.Report;

/**
 * The Class ReportService.
 */
@Service
public class ReportService {

	
	/** The log. */
	private final Logger log = LoggerFactory.getLogger(ReportService.class);

	/**
	 * Generate report.
	 *
	 * @param totalInstructionList the total instruction list
	 * @return the report
	 */
	public Report generateReport(List<Instruction> totalInstructionList) {

		LocalDate today = LocalDate.now();

		Report report = new Report();

		log.info("Total transaction in memory {}", totalInstructionList.size());

		log.info("Today's transaction {}",
				totalInstructionList.stream().filter(inst -> (today.isEqual(inst.getActualSettlementDate()))).count());

		totalInstructionList.forEach(inst -> {

			log.info("Instruction {}", inst);

			if (today.isEqual(inst.getActualSettlementDate())) {

				if (InstructionType.BUY == inst.getInstructionType()) {
					report.addOutgoingEntity(inst.getEntity(), getUSDAmount(inst));
				} else if (InstructionType.SELL == inst.getInstructionType()) {
					report.addIncomingEntity(inst.getEntity(), getUSDAmount(inst));
				}
			}
		});

		return report;

	}

	/**
	 * Gets the USD amount.
	 *
	 * @param inst the inst
	 * @return the USD amount
	 */
	private BigDecimal getUSDAmount(Instruction inst) {

		return inst.getPrice().multiply(new BigDecimal(inst.getUnit())).multiply(inst.getAgreedFx());
	}
	
	
	
	/**
	 * Formart report.
	 *
	 * @param report the report
	 * @return the string
	 */
	public String formartReport(Report report) {

		StringBuilder reportSB = new StringBuilder();

		LocalDate localDate = LocalDate.now();

		reportSB.append("\n-----------------------------------------------------");
		reportSB.append("\n");
		reportSB.append("--------- Today's(" + localDate + ") trade report ---------");
		reportSB.append("\n");
		reportSB.append("-----------------------------------------------------");
		reportSB.append("\n");

		reportSB.append("Total Incoming USD settled = "
				+ report.getIncomingEntitiesMap().values().stream().reduce(BigDecimal.ZERO, BigDecimal::add));
		reportSB.append("\n");
		reportSB.append("Total Outgoing USD settled = "
				+ report.getOutgoingEntitiesMap().values().stream().reduce(BigDecimal.ZERO, BigDecimal::add));
		reportSB.append("\n");

		reportSB.append("=== Incoming Entities sorted by rank ===");
		reportSB.append("\n");
		reportSB.append(getSortedReportByRank(report.getIncomingEntitiesMap()));

		reportSB.append("=== Outgoing Entities sorted by rank ===");
		reportSB.append("\n");
		reportSB.append(getSortedReportByRank(report.getOutgoingEntitiesMap()));

		return reportSB.toString();
	}

	
	/**
	 * Gets the sorted report by rank.
	 *
	 * @param entityMap the entity map
	 * @return the sorted report by rank
	 */
	private StringBuilder getSortedReportByRank(Map<String, BigDecimal> entityMap) {

		List<Report.ReportItem> reportItemList = new ArrayList<>();
		entityMap.forEach((k, v) -> reportItemList.add(new Report.ReportItem(k, v)));

		Collections.sort(reportItemList,
				(Report.ReportItem r1, Report.ReportItem r2) -> r2.getUsdAmount().compareTo(r1.getUsdAmount()));

		StringBuilder report = new StringBuilder();

		AtomicInteger rank = new AtomicInteger(1);
		reportItemList.forEach(item -> {
			report.append(rank.getAndIncrement() + " " + item);
			report.append("\n");
		});

		return report;
	}

}
