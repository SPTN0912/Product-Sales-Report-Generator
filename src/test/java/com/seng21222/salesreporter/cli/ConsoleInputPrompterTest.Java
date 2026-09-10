package com.seng21222.salesreporter.cli;

import com.seng21222.salesreporter.exception.SalesReporterException;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class ConsoleInputPrompterTest {

    @Test
    void promptForArguments_consoleMethod_doesNotAskForFilePath() throws SalesReporterException {
        String simulatedInput = "sample-data/sales.csv\nconsole\n";
        Scanner scanner = new Scanner(simulatedInput);
        ConsoleInputPrompter prompter = new ConsoleInputPrompter(scanner);

        CliArguments arguments = prompter.promptForArguments();

        assertEquals("sample-data/sales.csv", arguments.getCsvFilePath());
        assertEquals("console", arguments.getOutputMethod());
        assertNull(arguments.getOutputFilePath());
    }

    @Test
    void promptForArguments_fileMethod_alsoAsksForFilePath() throws SalesReporterException {
        String simulatedInput = "sample-data/sales.csv\nfile\nsample-output/summery-report.txt\n";
        Scanner scanner = new Scanner(simulatedInput);
        ConsoleInputPrompter prompter = new ConsoleInputPrompter(scanner);

        CliArguments arguments = prompter.promptForArguments();

        assertEquals("sample-data/sales.csv", arguments.getCsvFilePath());
        assertEquals("file", arguments.getOutputMethod());
        assertEquals("sample-output/summery-report.txt", arguments.getOutputFilePath());
    }

    @Test
    void promptForArguments_blankCsvPath_throwsException() {
        String simulatedInput = "\n";
        Scanner scanner = new Scanner(simulatedInput);
        ConsoleInputPrompter prompter = new ConsoleInputPrompter(scanner);

        assertThrows(SalesReporterException.class, prompter::promptForArguments);
    }
}
