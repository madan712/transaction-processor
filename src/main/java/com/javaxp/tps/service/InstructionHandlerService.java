package com.javaxp.tps.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Service;

import com.javaxp.tps.exception.InvalidInstructionFileException;
import com.javaxp.tps.model.Instruction;

/**
 * The Class InstructionHandlerService.
 */
@Service
public class InstructionHandlerService implements MessageHandler {

	/** The log. */
	private final Logger log = LoggerFactory.getLogger(InstructionHandlerService.class);

	/** The instruction reader. */
	@Autowired
	private InstructionReaderService instructionReader;
	
	/** The instruction processor. */
	@Autowired
	private InstructionProcessorService instructionProcessor;

	/** The instruction file location. */
	@Value("${tps.instructionFile.location}")
	public String instructionFileLocation;

	/* (non-Javadoc)
	 * @see org.springframework.messaging.MessageHandler#handleMessage(org.springframework.messaging.Message)
	 */
	@Override
	public void handleMessage(Message<?> mesg)  {

		log.info("Inside InstructionHandler -> handleMessage");

		File instructionFile = (File) mesg.getPayload();

		log.info("Instruction file received");
		log.info(instructionFile.getAbsolutePath());

		
		try {
			List<Instruction> instructionList = instructionReader.readInstruction(instructionFile);

			log.info("Total instruction received={}", instructionList.size());

			instructionProcessor.processInstruction(instructionList);

		} catch (InvalidInstructionFileException e) {
			log.error("Unable to process file");
			throw new MessagingException("Unable to process file", e);
		} finally {

			log.info("Moving this instruction file");
			moveFile(instructionFile);
		}

	}

	/**
	 * Move file.
	 *
	 * @param instructionFile the instruction file
	 */
	private void moveFile(File instructionFile) {
		try {
			FileUtils.moveFileToDirectory(FileUtils.getFile(instructionFile),
					FileUtils.getFile(instructionFileLocation + "/processed"), true);

			log.info("{} moved successfully", instructionFile.getPath());

		} catch (IOException e) {
			log.info("Unable to move instruction file");
			log.error(e.getMessage());
			throw new MessagingException(e.getMessage(), e);
		}
	}

}
