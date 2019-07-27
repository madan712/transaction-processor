package com.javaxp.tps;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
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
import com.javaxp.tps.service.InstructionReaderService;

/**
 * The Class InstructionReaderServiceTest.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class InstructionReaderServiceTest {

	/** The instruction reader service. */
	@Autowired
	private InstructionReaderService instructionReaderService;

	/**
	 * Test read instruction when valid file then get instruction.
	 *
	 * @throws InvalidInstructionFileException
	 *             the invalid instruction file exception
	 */
	@Test
	public void testReadInstructionWhenValidFileThenGetInstruction() throws InvalidInstructionFileException {

		// when(dateService.getNextWorkingDayIfWeekend(any(LocalDate.class),
		// anyString())).thenReturn(LocalDate.now());
		List<Instruction> instList = instructionReaderService.readInstruction(getValidDummyInstructionFile());

		assertEquals(2, instList.size());
	}

	/**
	 * Test read instruction when invald file then throw exception.
	 *
	 * @throws InvalidInstructionFileException
	 *             the invalid instruction file exception
	 */
	@Test(expected = InvalidInstructionFileException.class)
	public void testReadInstructionWhenInvaldFileThenThrowException() throws InvalidInstructionFileException {

		
		instructionReaderService.readInstruction(getInvalidDummyInstructionFile());

		
	}

	/**
	 * Gets the valid dummy instruction file.
	 *
	 * @return the valid dummy instruction file
	 */
	private File getValidDummyInstructionFile() {

		final File file = new File("dummyInstructions.txt");

		List<String> lines = new ArrayList<>();
		lines.add("foo|B|0.50|SGP|01 Jan 2016|24 Aug 2018|200|100.25");
		lines.add("bar|S|0.22|AED|05 Jan 2016|25 Aug 2018|450|150.5");

		try {
			FileUtils.writeLines(file, lines);
		} catch (IOException e) {

			e.printStackTrace();
		}

		return file;
	}

	/**
	 * Gets the invalid dummy instruction file.
	 *
	 * @return the invalid dummy instruction file
	 */
	private File getInvalidDummyInstructionFile() {

		final File file = new File("dummyInstructions.txt");

		List<String> lines = new ArrayList<>();
		lines.add("foo|B|0.50|SGP|01 Jan 2016|24 Aug 2018|200|100.25");
		lines.add("bar|S|0.22|AED|05 Jan 2016");

		try {
			FileUtils.writeLines(file, lines);
		} catch (IOException e) {

			e.printStackTrace();
		}

		return file;
	}

}
