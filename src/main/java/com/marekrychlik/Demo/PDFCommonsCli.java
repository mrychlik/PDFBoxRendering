package com.marekrychlik.Demo;


import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.HelpFormatter;

import java.util.Properties;
import java.io.PrintWriter;


class PDFCommonsCli {
    public static void main(String[] args) throws ParseException {
	Options options = new Options();

	// add option "-h"
	options.addOption("h", false, "print help");

	if(cmd.hasOption("h")) {
	    usage(options);
	}
    }

    private static void usage(Options options) {
	HelpFormatter formatter = new HelpFormatter();

	final PrintWriter writer = new PrintWriter(System.out);
	formatter.printUsage(writer,80,"CLITester", options);
	writer.flush();
    }
}