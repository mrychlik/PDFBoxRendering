/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.marekrychlik.Demo;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.awt.image.BufferedImage;
import javax.imageio.*;


import org.apache.pdfbox.contentstream.PDContentStream;
import org.apache.pdfbox.contentstream.operator.Operator;
import org.apache.pdfbox.contentstream.operator.OperatorName;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdfparser.PDFStreamParser;
import org.apache.pdfbox.pdfwriter.ContentStreamWriter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.common.PDStream;
import org.apache.pdfbox.pdmodel.graphics.PDXObject;
import org.apache.pdfbox.pdmodel.graphics.form.PDFormXObject;
import org.apache.pdfbox.pdmodel.graphics.pattern.PDAbstractPattern;
import org.apache.pdfbox.pdmodel.graphics.pattern.PDTilingPattern;
import org.apache.pdfbox.rendering.*;

/**
 * This is an example on how to remove all text from PDF document.
 *
 * @author Ben Litchfield
 */
public final class RemoveAllText
{
    /**
     * Default constructor.
     */
    private RemoveAllText()
    {
        // example class should not be instantiated
    }

    /**
     * This will remove all text from a PDF document.
     *
     * @param args The command line arguments.
     *
     * @throws IOException If there is an error parsing the document.
     */
    public static void main(String[] args) throws IOException
    {
	if (args.length != 2) {
	    usage();
	} else {
	    mapFile(args[0], args[1]);
	}
    }


    public static void mapFile(String inputFile, String outputFile) throws IOException
    {
	try (PDDocument document = PDDocument.load(new File(inputFile)) ) {
	    if (document.isEncrypted()) {
		    System.err.println("Error: Encrypted documents are not supported for this example.");
		    System.exit(1);
	    }
	    for (PDPage page : document.getPages())
		{
		    List<Object> newTokens = createTokensWithoutText(page);
		    PDStream newContents = new PDStream(document);
		    writeTokensToStream(newContents, newTokens);
		    page.setContents(newContents);
		    processResources(page.getResources());
		}
	    document.save(outputFile);
	}
    }

    // Solution for the 2.0 version:
    //
    // PDDocument document = PDDocument.load(new File(pdfFilename));
    // PDFRenderer pdfRenderer = new PDFRenderer(document);
    // for (int page = 0; page < document.getNumberOfPages(); ++page)
    // { 
    //     BufferedImage bim = pdfRenderer.renderImageWithDPI(page, 300, ImageType.RGB);

    //     // suffix in filename will be used as the file format
    //     ImageIOUtil.writeImage(bim, pdfFilename + "-" + (page+1) + ".png", 300);
    // }
    // document.close();

    public static void mapFileToImages(String inputFile, String outputFile) throws IOException
    {
	try (PDDocument document = PDDocument.load(new File(inputFile)) ) {
	    if (document.isEncrypted()) {
		    System.err.println("Error: Encrypted documents are not supported for this example.");
		    System.exit(1);
	    }
	    List<BufferedImage> bufferedImages;
	    for (PDPage page : document.getPages())
		{
		    List<Object> newTokens = createTokensWithoutText(page);
		    PDStream newContents = new PDStream(document);
		    writeTokensToStream(newContents, newTokens);
		    page.setContents(newContents);
		    processResources(page.getResources());
		}
	    PDFRenderer pdfRenderer = new PDFRenderer(document);
	     for (int page = 0; page < document.getNumberOfPages(); ++page)
	     { 
	         BufferedImage bim = pdfRenderer.renderImageWithDPI(page, 600, ImageType.RGB);
	         // suffix in filename will be used as the file format
	         ImageIO.write(bim, "JPEG", new File(outputFile+page+".jpg"));
	     }
	     document.close();
	     // document.save(outputFile);
	}
    }
    
    private static void processResources(PDResources resources) throws IOException
    {
        for (COSName name : resources.getXObjectNames())
        {
            PDXObject xobject = resources.getXObject(name);
            if (xobject instanceof PDFormXObject)
            {
                PDFormXObject formXObject = (PDFormXObject) xobject;
                writeTokensToStream(formXObject.getContentStream(),
                        createTokensWithoutText(formXObject));
                processResources(formXObject.getResources());
            }
        }
        for (COSName name : resources.getPatternNames())
        {
            PDAbstractPattern pattern = resources.getPattern(name);
            if (pattern instanceof PDTilingPattern)
            {
                PDTilingPattern tilingPattern = (PDTilingPattern) pattern;
                writeTokensToStream(tilingPattern.getContentStream(),
                        createTokensWithoutText(tilingPattern));
                processResources(tilingPattern.getResources());
            }
        }
    }

    private static void writeTokensToStream(PDStream newContents, List<Object> newTokens) throws IOException
    {
        try (OutputStream out = newContents.createOutputStream(COSName.FLATE_DECODE))
        {
            ContentStreamWriter writer = new ContentStreamWriter(out);
            writer.writeTokens(newTokens);
        }
    }

    private static List<Object> createTokensWithoutText(PDContentStream contentStream) throws IOException
    {
        PDFStreamParser parser = new PDFStreamParser(contentStream);
        Object token = parser.parseNextToken();
        List<Object> newTokens = new ArrayList<>();
        while (token != null)
        {
            if (token instanceof Operator)
            {
                Operator op = (Operator) token;
                String opName = op.getName();
                if (OperatorName.SHOW_TEXT_ADJUSTED.equals(opName)
                        || OperatorName.SHOW_TEXT.equals(opName)
                        || OperatorName.SHOW_TEXT_LINE.equals(opName))
                {
                    // remove the argument to this operator
                    newTokens.remove(newTokens.size() - 1);

                    token = parser.parseNextToken();
                    continue;
                }
                else if (OperatorName.SHOW_TEXT_LINE_AND_SPACE.equals(opName))
                {
                    // remove the 3 arguments to this operator
                    newTokens.remove(newTokens.size() - 1);
                    newTokens.remove(newTokens.size() - 1);
                    newTokens.remove(newTokens.size() - 1);

                    token = parser.parseNextToken();
                    continue;
                }
            }
            newTokens.add(token);
            token = parser.parseNextToken();
        }
        return newTokens;
    }

    /**
     * This will print the usage for this document.
     */
    private static void usage()
    {
        System.err.println(
                "Usage: java " + RemoveAllText.class.getName() + " <input-pdf> <output-pdf>");
    }

}
