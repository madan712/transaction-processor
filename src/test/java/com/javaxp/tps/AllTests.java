package com.javaxp.tps;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * The Class AllTests.
 */
@RunWith(Suite.class)
@SuiteClasses({ DateServiceTest.class, InstructionHandlerServiceTest.class, InstructionProcessorServiceTest.class,
		InstructionReaderServiceTest.class, ReportServiceTest.class })
public class AllTests {

}
