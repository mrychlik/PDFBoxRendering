package com.marekrychlik.Demo;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.Option;

public class CLITester {
   public static void main(String[] args) throws ParseException {
      //***Definition Stage***
      // create Options object
      Options options = new Options();
      
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
      CommandLineParser parser = new DefaultParser();
      CommandLine cmd = parser.parse( options, args);
      
      // has the logFile argument been passed?
      if(cmd.hasOption("logFile")) {
         //get the logFile argument passed
         System.out.println( cmd.getOptionValue( "logFile" ) );
      }

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
}
