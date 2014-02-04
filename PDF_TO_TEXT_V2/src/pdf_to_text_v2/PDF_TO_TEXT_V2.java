package pdf_to_text_v2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;

/**
 *
 * @author Octavian
 */
public class PDF_TO_TEXT_V2 
{
    private static COSDocument cosDoc;
    private static PDFTextStripper pdfStripper;
    private static PDDocument pdDoc;
    private static String parsedText;
    
     void writeTexttoFile(String pdfText, String fileName) 
     {
    	
    	System.out.println("\nWriting PDF text to output text file " + fileName + "....");
    	try 
        {
    		PrintWriter pw = new PrintWriter(fileName);
    		pw.print(pdfText);
    		pw.close();    	
    	} 
        catch (Exception e) 
        {
    		System.out.println("An exception occured in writing the pdf text to file.");
    		e.printStackTrace();
    	}
    	System.out.println("Done.");
    }
    
    public static void main(String[] args) throws IOException
    {
       /* 
        PDFParser parser;
 
        System.out.println("Give the name of the .pdf you want to convert. No \".pdf\" needed.");
        String nameOfPDF,nameOfTXT;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        nameOfPDF = br.readLine() + ".pdf";
        System.out.println("Give the name of the resulting .txt file. No \".txt.\" needed.");
        nameOfTXT = br.readLine() + ".txt";
        System.out.println("Parsing text from PDF file " + nameOfPDF + "....");
        File f = new File(nameOfPDF);
        
        if (!f.isFile()) 
        {
            System.out.println("File " + nameOfPDF + " does not exist.");
            return;
        }
        
        try 
        {
          parser = new PDFParser(new FileInputStream(f));
        } catch (Exception e) 
        {
            System.out.println("Unable to open PDF Parser.");
            return;
        }
        
        try 
        {
            parser.parse();
            cosDoc = parser.getDocument();
            pdfStripper = new PDFTextStripper();
            pdDoc = new PDDocument(cosDoc);
            parsedText = pdfStripper.getText(pdDoc); 
        } 
        catch (Exception e) 
        {
            System.out.println("An exception occured in parsing the PDF Document.");
            e.printStackTrace();
            try 
            {
                   if (cosDoc != null) cosDoc.close();
                   if (pdDoc != null) pdDoc.close();
            } 
            catch (Exception e1) 
            {
               e.printStackTrace();
            }
            return;
        }    
        try 
        {
    		PrintWriter pw = new PrintWriter(nameOfTXT);
    		pw.print(parsedText);
    		pw.close();    	
    	} 
        catch (Exception e)
             {
    		System.out.println("An exception occured in writing the pdf text to file.");
    		e.printStackTrace();
             }
        */
        transformaPdfInTxt(args[0]);
       // transformaPdfInTxt("C:\\Users\\Veronica\\Desktop\\POS.pdf");
    }
    
    public static void transformaPdfInTxt (String inputFileName){
        File f = new File(inputFileName);
        String outputFileName= inputFileName.replace(".pdf", ".txt");
        PDFParser parser;
        if (!f.isFile()) 
        {
            System.out.println("File " + inputFileName + " does not exist.");
            return;
        }
        
        try 
        {
          parser = new PDFParser(new FileInputStream(f));
        } catch (Exception e) 
        {
            System.out.println("Unable to open PDF Parser.");
            return;
        }
        
        try 
        {
            parser.parse();
            cosDoc = parser.getDocument();
            pdfStripper = new PDFTextStripper();
            pdDoc = new PDDocument(cosDoc);
            parsedText = pdfStripper.getText(pdDoc); 
        } 
        catch (Exception e) 
        {
            System.out.println("An exception occured in parsing the PDF Document.");
            e.printStackTrace();
            try 
            {
                   if (cosDoc != null) cosDoc.close();
                   if (pdDoc != null) pdDoc.close();
            } 
            catch (Exception e1) 
            {
               e.printStackTrace();
            }
            return;
        }    
        try 
        {
    		PrintWriter pw = new PrintWriter(outputFileName,"UTF-8");
    		pw.print(parsedText);
    		pw.close();    	
    	} 
        catch (Exception e)
             {
    		System.out.println("An exception occured in writing the pdf text to file.");
    		e.printStackTrace();
             }
    }
    
}
