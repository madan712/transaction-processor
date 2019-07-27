package com.javaxp.tps.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javaxp.tps.model.Instruction;
import com.javaxp.tps.model.Report;

/**
 * The Class InstructionProcessorService.
 */
@Service
public class InstructionProcessorService {

	/** The log. */
	private final Logger log = LoggerFactory.getLogger(InstructionProcessorService.class);

	/** The total instruction list. */
	private List<Instruction> totalInstructionList = new ArrayList<>();

	/** The report service. */
	@Autowired
	private ReportService reportService;

	/**
	 * Gets the total instruction list.
	 *
	 * @return the total instruction list
	 */
	public List<Instruction> getTotalInstructionList() {
		return totalInstructionList;
	}

	/**
	 * Sets the total instruction list.
	 *
	 * @param totalInstructionList
	 *            the new total instruction list
	 */
	public void setTotalInstructionList(List<Instruction> totalInstructionList) {
		this.totalInstructionList = totalInstructionList;
	}

	/**
	 * Process instruction.
	 *
	 * @param instructionList
	 *            the instruction list
	 */
	public void processInstruction(List<Instruction> instructionList) {

		totalInstructionList.addAll(instructionList);

		Report report = reportService.generateReport(totalInstructionList);

		if (log.isInfoEnabled()) {
			log.info("Report {}", reportService.formartReport(report));
		}
	}

}
