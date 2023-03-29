package com.marekrychlik.Demo;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import java.io.File;
// import java.math.BigInteger;
// import java.nio.file.Files;
// import java.security.MessageDigest;
import java.util.concurrent.Callable;

@Command(name = "pdfcli", mixinStandardHelpOptions = true, version = "checksum 0.0.1", description = "Performs actions on PDF files.")

class PDFCli implements Callable<Integer> {

    @Parameters(index = "0", description = "The input PDF file.")
    private File infile;

    @Parameters(index = "1", description = "The output PDF file.")
    private File outfile;

    @Option(names = { "-r", "--remove-all-text" }, description = "Removes all text from PDF")
    private Integer action;


    @Override
    public Integer call() throws Exception { // your business logic goes here...
        System.out.println("Hello");
        return 0;
    }

    // this example implements Callable, so parsing, error handling and handling user
    // requests for usage help or version help can be done with one line of code.
    public static void main(String... args) {
        int exitCode = new CommandLine(new PDFCli()).execute(args);
        System.exit(exitCode);
    }
}
