package com.javaxp.tps;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.javaxp.tps.service.InstructionHandlerService;
import com.javaxp.tps.utils.Constant;

/**
 * The Class InstructionHandlerServiceTest.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class InstructionHandlerServiceTest {

	/** The instruction handler service. */
	@Autowired
	private InstructionHandlerService instructionHandlerService;

	/** The instruction file location. */
	@Value("${tps.instructionFile.location}")
	public String instructionFileLocation;

	/**
	 * Test handle message.
	 */
	@Test
	public void testHandleMessage() {

		File dummyInstructionFile = getValidDummyInstructionFile();

		Message<File> dummyMesg = new Message<File>() {

			@Override
			public MessageHeaders getHeaders() {
				Map<String, Object> map = new HashMap<>();
				return new MessageHeaders(map);
			}

			@Override
			public File getPayload() {

				return dummyInstructionFile;
			}
		};

		assertEquals(true, dummyMesg.getPayload().exists());

		deleteIfAlreadyPresent(dummyMesg.getPayload().getName());

		assertEquals(false,
				new File(instructionFileLocation + "/processed/" + dummyMesg.getPayload().getName()).exists());

		instructionHandlerService.handleMessage(dummyMesg);

		assertEquals(false, dummyMesg.getPayload().exists());

		assertEquals(true,
				new File(instructionFileLocation + "/processed/" + dummyMesg.getPayload().getName()).exists());

	}

	
	/**
	 * Delete if already present.
	 *
	 * @param fileName the file name
	 */
	private void deleteIfAlreadyPresent(String fileName) {

		File destFile = new File(instructionFileLocation + "/processed/" + fileName);
		if (destFile.exists()) {
			destFile.delete();
		}
	}

	/**
	 * Gets the valid dummy instruction file.
	 *
	 * @return the valid dummy instruction file
	 */
	private File getValidDummyInstructionFile() {

		System.out.println("creating.......");

		final File file = new File("dummyInstructions.txt");
		
		LocalDate today = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Constant.INPUT_DATE_FORMAT);

		List<String> lines = new ArrayList<>();
		lines.add("foo|B|0.50|SGP|01 Jan 2016|"+formatter.format(today)+"|200|100.25");
		lines.add("bar|S|0.22|AED|05 Jan 2016|"+formatter.format(today)+"|450|150.5");

		try {
			FileUtils.writeLines(file, lines);
		} catch (IOException e) {

			e.printStackTrace();
		}

		return file;
	}

}
