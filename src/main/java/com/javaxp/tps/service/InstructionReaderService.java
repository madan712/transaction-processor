package com.javaxp.tps.service;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javaxp.tps.exception.InvalidInstructionException;
import com.javaxp.tps.exception.InvalidInstructionFileException;
import com.javaxp.tps.model.Instruction;

/**
 * The Class InstructionReaderService.
 */
@Service
public class InstructionReaderService {

	/** The log. */
	private final Logger log = LoggerFactory.getLogger(InstructionReaderService.class);

	/** The date service. */
	@Autowired
	private DateService dateService;

	/**
	 * Read instruction.
	 *
	 * @param instructionFile the instruction file
	 * @return the list
	 * @throws InvalidInstructionFileException the invalid instruction file exception
	 */
	public List<Instruction> readInstruction(File instructionFile) throws InvalidInstructionFileException {

		try (Stream<String> lines = Files.lines(Paths.get(instructionFile.getAbsolutePath()))) {

			List<Instruction> instructionList = new ArrayList<>();

			lines.forEach(line -> {
				try {
					instructionList.add(getInstruction(line));
				} catch (Exception e) {
					throw new InvalidInstructionException(e);
				}
			});

			return instructionList;

		} catch (Exception e) {
			throw new InvalidInstructionFileException("Unable to read instruction file", e);
		}

	}

	/**
	 * Gets the instruction.
	 *
	 * @param line the line
	 * @return the instruction
	 * @throws InvalidInstructionFileException the invalid instruction file exception
	 */
	private Instruction getInstruction(String line) throws InvalidInstructionFileException {

		try {
			String[] items = line.split(Pattern.quote("|"));

			Instruction inst = new Instruction.Builder(items[0]).instructionType(items[1]).agreedFx(items[2])
					.currency(items[3]).instructionDate(items[4]).settlementDate(items[5]).unit(items[6])
					.price(items[7]).build();

			inst.setActualSettlementDate(
					dateService.getNextWorkingDayIfWeekend(inst.getSettlementDate(), inst.getCurrency()));

			return inst;

		} catch (Exception e) {
			log.error("Unable to read this instruction");
			throw new InvalidInstructionFileException("Unable to read this instruction", e);
		}

	}

}