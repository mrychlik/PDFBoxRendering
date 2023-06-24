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
import java.io.IOException;
import java.util.List;

class PDFCommonsCli {
    public static void main(String[] args) throws ParseException {
	Options options = new Options();

	// add option "-h"
	options.addOption("h", false, "print help");


	Option removeAllTextOption = Option.builder()
	    .longOpt("R")
	    .argName("input-file")
	    .argName("output-file")	    
	    .hasArg()
	    .valueSeparator()
	    .numberOfArgs(2)
	    .desc("Remove all text")
	    .build();

	options.addOption(removeAllTextOption);
	
	Option getCharLocationAndSizeOption = Option.builder()
	    .longOpt("C")
	    .argName("get-char-location-and-size")
	    .hasArg()
	    .valueSeparator()
	    .numberOfArgs(1)
	    .desc("get character location and size")
	    .build();

	options.addOption(getCharLocationAndSizeOption);

	Option getWordLocationAndSizeOption = Option.builder()
	    .longOpt("W")
	    .argName("get-word-location-and-size")
	    .hasArg()
	    .valueSeparator()
	    .numberOfArgs(1)
	    .desc("get word location and size")
	    .build();

	options.addOption(getWordLocationAndSizeOption);

	Option getWordsOption = Option.builder()
	    .longOpt("V")
	    .argName("get-words")
	    .hasArg()
	    .valueSeparator()
	    .numberOfArgs(1)
	    .desc("get words")
	    .build();

	options.addOption(getWordsOption);



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

	try {
	    if (cmd.hasOption("R")) {
		String[] loc_args = cmd.getOptionValues("R");
		RemoveAllText.mapFile(loc_args[0], loc_args[1]);
	    } else if(cmd.hasOption("I")) {
		String[] loc_args = cmd.getOptionValues("I");
		RemoveAllText.mapFile(loc_args[0], loc_args[1]);
	    } else if(cmd.hasOption("C")) {
		String fileName = cmd.getOptionValue("C");
		GetCharLocationAndSize.doFile(fileName);
	    } else if(cmd.hasOption("W")) {	    
		String fileName = cmd.getOptionValue("W");
		GetWordLocationAndSize.doFile(fileName);
	    } else if(cmd.hasOption("V")) {	    
		String fileName = cmd.getOptionValue("V");
		GetWords.doFile(fileName);
	    }
	} catch(IOException e) {
	    e.printStackTrace();
	}

    }

    private static void usage(Options options) {
	HelpFormatter formatter = new HelpFormatter();

	final PrintWriter writer = new PrintWriter(System.out);
	formatter.printUsage(writer,80,"PDFCommonsCli", options);
	writer.flush();
    }
}
