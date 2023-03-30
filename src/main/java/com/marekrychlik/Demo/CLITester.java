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


public class CLITester {
    public static void main(String[] args) throws ParseException {
	//***Definition Stage***
	// create Options object
	Options options = new Options();
      
	// add option "-h"
	options.addOption("h",false,"print help");


	// add option "-a"
	options.addOption("a", false, "add numbers");
      
	// add option "-m"
	options.addOption("m", false, "multiply numbers");

	Option logfile = Option.builder()
	    .longOpt("logFile")
	    .argName("file" )
	    .hasArg()
	    .desc("use given file for log" )
	    .build();

	options.addOption(logfile);

	Option propertyOption = Option.builder()
	    .longOpt("D")
	    .argName("property=value" )
	    .hasArg()
	    .valueSeparator()
	    .numberOfArgs(2)
	    .desc("use value for given properties" )
	    .build();

	options.addOption(propertyOption);
      
	//***Parsing Stage***
	//Create a parser
	CommandLineParser parser = new DefaultParser();

	//parse the options passed as command line arguments
	CommandLine cmd = parser.parse( options, args);

	//***Interrogation Stage***
	//hasOptions checks if option is present or not
	if(cmd.hasOption("a")) {
	    System.out.println("Sum of the numbers: " + getSum(args));
	} else if(cmd.hasOption("m")) {
	    System.out.println("Multiplication of the numbers: " + getMultiplication(args));
	}

	// has the logFile argument been passed?
	if(cmd.hasOption("logFile")) {
	    //get the logFile argument passed
	    System.out.println( cmd.getOptionValue( "logFile" ) );
	}

	if(cmd.hasOption("D")) {
	    Properties properties = cmd.getOptionProperties("D");
	    System.out.println("Log: " + properties.getProperty("logFile"));
	    System.out.println("Operator: " + properties.getProperty("operator"));
	}

	if(cmd.hasOption("h")) {
	    usage(options);
	}
    }

    public static int getSum(String[] args) {
	int sum = 0;
	for(int i = 1; i < args.length ; i++) {
	    sum += Integer.parseInt(args[i]);
	}
	return sum;
    }

    public static int getMultiplication(String[] args) {
	int multiplication = 1;
	for(int i = 1; i < args.length ; i++) {
	    multiplication *= Integer.parseInt(args[i]);
	}
	return multiplication;
    }

    private static void usage(Options options) {
	HelpFormatter formatter = new HelpFormatter();

	final PrintWriter writer = new PrintWriter(System.out);
	formatter.printUsage(writer,80,"CLITester", options);
	writer.flush();
    }
}
