package com.javaxp.tps;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
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
import com.javaxp.tps.service.InstructionProcessorService;
import com.javaxp.tps.service.InstructionReaderService;
import com.javaxp.tps.utils.Constant;

/**
 * The Class InstructionProcessorServiceTest.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class InstructionProcessorServiceTest {

	/** The instruction processor service. */
	@Autowired
	private InstructionProcessorService instructionProcessorService;

	/** The instruction reader service. */
	@Autowired
	private InstructionReaderService instructionReaderService;

	/**
	 * Test process instruction.
	 *
	 * @throws InvalidInstructionFileException the invalid instruction file exception
	 */
	@Test
	public void testProcessInstruction() throws InvalidInstructionFileException {
		
		
		instructionProcessorService.setTotalInstructionList(new ArrayList<>());

		assertEquals(0, instructionProcessorService.getTotalInstructionList().size());

		instructionProcessorService
				.processInstruction(instructionReaderService.readInstruction(getDummyInstructionFile()));

		assertEquals(2, instructionProcessorService.getTotalInstructionList().size());

		instructionProcessorService
				.processInstruction(instructionReaderService.readInstruction(getDummyInstructionFile()));

		assertEquals(4, instructionProcessorService.getTotalInstructionList().size());
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
		lines.add("bar|S|0.22|AED|05 Jan 2016|" + formatter.format(today) + "|450|150.5");

		try {
			FileUtils.writeLines(file, lines);
		} catch (IOException e) {

			e.printStackTrace();
		}

		return file;
	}

}
