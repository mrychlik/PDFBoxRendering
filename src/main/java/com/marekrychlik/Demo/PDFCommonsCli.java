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


	// add option "-r"
	options.addOption("r", false, "remove all text");
      

	Option removeAllTextOption = Option.builder()
	    .longOpt("R")
	    .argName("remove-all-text")
	    .hasArg()
	    .valueSeparator()
	    .numberOfArgs(2)
	    .desc("Remove all text")
	    .build();

	options.addOption(removeAllTextOption)

	options.addOption("c", false, "get character location and size");

	// add option "-w"
	options.addOption("w", false, "get word location and size");


	Option propertyOption = Option.builder()
	    .longOpt("D")
	    .argName("property=value" )
	    .hasArg()
	    .valueSeparator()
	    .numberOfArgs(2)
	    .desc("use value for given properties" )
	    .build();

	options.addOption(propertyOption);

	// Create a parser
	CommandLineParser parser = new DefaultParser();

	// parse the options passed as command line arguments
	CommandLine cmd = parser.parse( options, args);


	if(cmd.hasOption("h")) {
	    usage(options);
	}

	if(cmd.hasOption("D")) {
	    Properties properties = cmd.getOptionProperties("D");
	    // Print the properties here
	}

	if(cmd.hasOption("r")) {
	    String fileName = cmd.getOptionValue("r");
	    RemoveAllTest.mapFile(inputFile, outputFile);
	} else if(cmd.hasOption("c")) {
	    String fileName = cmd.getOptionValue("r");
	    GetCharLocationAndSize.doFile(fileName);
	} else if(cmd.hasOption("w")) {	    

	}


    }

    private static void usage(Options options) {
	HelpFormatter formatter = new HelpFormatter();

	final PrintWriter writer = new PrintWriter(System.out);
	formatter.printUsage(writer,80,"PDFCommonsCli", options);
	writer.flush();
    }
}
