package com.javaxp.tps.config;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.InboundChannelAdapter;
import org.springframework.integration.annotation.Poller;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.core.MessageSource;
import org.springframework.integration.file.FileReadingMessageSource;
import org.springframework.integration.file.filters.SimplePatternFileListFilter;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

import com.javaxp.tps.service.InstructionHandlerService;

/**
 * The Class ApplicationConfiguration.
 */
@Configuration
@EnableIntegration
@ComponentScan(basePackages = {"com.jpmc.tps"})
public class ApplicationConfiguration {

	/** The log. */
	private final Logger log = LoggerFactory.getLogger(ApplicationConfiguration.class);

	/** The instruction file location. */
	@Value("${tps.instructionFile.location}")
	private String instructionFileLocation;

	/** The instruction file ext. */
	@Value("${tps.instructionFile.ext}")
	private String instructionFileExt;

	/** The instruction handler. */
	@Autowired
	private InstructionHandlerService instructionHandler;

	/**
	 * Instruction file channel.
	 *
	 * @return the message channel
	 */
	@Bean
	public MessageChannel instructionFileChannel() {
		return new DirectChannel();
	}

	/**
	 * Read instruction file.
	 *
	 * @return the message source
	 */
	@Bean
	@InboundChannelAdapter(value = "instructionFileChannel", poller = @Poller(fixedDelay = "1000"))
	public MessageSource<File> readInstructionFile() {
		
		log.debug("Inside readInstructionFile");
		
		FileReadingMessageSource sourceReader = new FileReadingMessageSource();
		sourceReader.setDirectory(new File(instructionFileLocation));
		sourceReader.setFilter(new SimplePatternFileListFilter(instructionFileExt));
		return sourceReader;
	}

	/**
	 * Instruction file handler.
	 *
	 * @return the message handler
	 */
	@Bean
	@ServiceActivator(inputChannel = "instructionFileChannel")
	public MessageHandler instructionFileHandler() {

		log.debug("Inside instructionFileHandler");

		return instructionHandler;
	}

}