package com.javaxp.tps;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.javaxp.tps.exception.InvalidInstructionFileException;
import com.javaxp.tps.model.Instruction;
import com.javaxp.tps.model.InstructionType;
import com.javaxp.tps.model.Report;
import com.javaxp.tps.service.InstructionReaderService;
import com.javaxp.tps.service.ReportService;
import com.javaxp.tps.utils.Constant;

/**
 * The Class ReportServiceTest.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ReportServiceTest {

	/** The report service. */
	@Autowired
	private ReportService reportService;

	/** The instruction reader service. */
	@Autowired
	private InstructionReaderService instructionReaderService;

	/**
	 * Test generate report.
	 *
	 * @throws InvalidInstructionFileException the invalid instruction file exception
	 */
	@Test
	public void testGenerateReport() throws InvalidInstructionFileException {

		List<Instruction> dummyInstructions = instructionReaderService.readInstruction(getDummyInstructionFile());

		Report expectedReport = generateReport(dummyInstructions);

		Report actualReport = reportService.generateReport(dummyInstructions);

		assertEquals(expectedReport.getIncomingEntitiesMap().size(), actualReport.getIncomingEntitiesMap().size());
		assertEquals(expectedReport.getOutgoingEntitiesMap().size(), actualReport.getOutgoingEntitiesMap().size());

		assertEquals(expectedReport.getIncomingEntitiesMap().values().stream().reduce(BigDecimal.ZERO, BigDecimal::add),
				actualReport.getIncomingEntitiesMap().values().stream().reduce(BigDecimal.ZERO, BigDecimal::add));
		assertEquals(expectedReport.getOutgoingEntitiesMap().values().stream().reduce(BigDecimal.ZERO, BigDecimal::add),
				actualReport.getOutgoingEntitiesMap().values().stream().reduce(BigDecimal.ZERO, BigDecimal::add));

	}

	/**
	 * Generate report.
	 *
	 * @param totalInstructionList the total instruction list
	 * @return the report
	 */
	public Report generateReport(List<Instruction> totalInstructionList) {

		LocalDate today = LocalDate.now();

		Report report = new Report();

		totalInstructionList.forEach(inst -> {

			if (today.isEqual(inst.getActualSettlementDate())) {

				if (InstructionType.BUY == inst.getInstructionType()) {
					report.addOutgoingEntity(inst.getEntity(),
							inst.getPrice().multiply(new BigDecimal(inst.getUnit())).multiply(inst.getAgreedFx()));
				} else if (InstructionType.SELL == inst.getInstructionType()) {
					report.addIncomingEntity(inst.getEntity(),
							inst.getPrice().multiply(new BigDecimal(inst.getUnit())).multiply(inst.getAgreedFx()));
				}
			}
		});

		return report;

	}

	/**
	 * Gets the dummy instruction file.
	 *
	 * @return the dummy instruction file
	 */
	private File getDummyInstructionFile() {

		System.out.println("creating.......");

		final File file = new File("dummyInstructions.txt");

		LocalDate today = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Constant.INPUT_DATE_FORMAT);

		List<String> lines = new ArrayList<>();
		lines.add("foo|B|0.50|SGP|01 Jan 2016|" + formatter.format(today) + "|200|100.25");
		lines.add("foo|B|0.50|SGP|01 Jan 2016|" + formatter.format(today) + "|100|100.25");
		lines.add("bar|S|0.20|AED|05 Jan 2016|" + formatter.format(today) + "|300|99");
		lines.add("baz|B|0.70|USD|01 Jan 2016|" + formatter.format(today) + "|250|85");
		lines.add("qux|S|0.25|AED|05 Jan 2016|" + formatter.format(today) + "|45|200");
		lines.add("foo|S|0.10|SGP|01 Jan 2016|" + formatter.format(today) + "|99|66.66");
		lines.add("bar|B|0.22|USD|05 Jan 2016|" + formatter.format(today) + "|1000|150.5");

		try {
			FileUtils.writeLines(file, lines);
		} catch (IOException e) {

			e.printStackTrace();
		}

		return file;
	}

}
