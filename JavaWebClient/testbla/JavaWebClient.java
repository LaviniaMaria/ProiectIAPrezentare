/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javawebclient;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import org.xml.sax.SAXException;
import pentru.qa.PentruQA;

/**
 *
 * @author Mada
 */
public class JavaWebClient {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {
        
        textAnalytics("C:\\Users\\Veronica\\Desktop\\tusnad.pdf", "C:\\Users\\Veronica\\Desktop\\eu.xml");
        /*FileInputStream fstream = null;
        fstream = new FileInputStream("geografie.txt");
        DataInputStream f = new DataInputStream(fstream);
        BufferedReader br = new BufferedReader(new InputStreamReader(f));
        String text = " ";

        String t;
        do {
            t = br.readLine();
            if (t != null) {
                text = text + t;
            }
        } while (t != null);
        String result = chunkText(text);
        System.out.println(result);
        try {

            FileOutputStream fOut = new FileOutputStream("File.xml");
            PrintStream ps = new PrintStream(fOut);

            ps.println(result);
           
            ps.close();
            fOut.close();

        } catch (IOException ex) {

            System.out.println("Could not create file.");
            System.exit(1);

        }*/

    }
    
     public static void textAnalytics(String inputFile, String outputFile)
    {
        try {
            FileInputStream fstream = null;
            PentruQA pentruQA= new PentruQA();
            
            if(inputFile.contains(".pdf")){
                pdf_to_text_v2.PDF_TO_TEXT_V2.transformaPdfInTxt(inputFile);
                inputFile=inputFile.replace(".pdf",".txt");
            }
            
            String outFile = inputFile.substring(0,inputFile.length()-4)+"_dupaAnafora.txt";
            pentruQA.rezolvaAnafora(inputFile,outFile);
            fstream = new FileInputStream(outFile);
            DataInputStream f = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(f));
            String text = " ";

            String t;
            do {
                t = br.readLine();
                if (t != null) {
                    text = text + t;
                }
            } while (t != null);
            String result = chunkText(text);
            System.out.println(result);
            try {

                FileOutputStream fOut = new FileOutputStream(outputFile);
                PrintStream ps = new PrintStream(fOut);

                ps.println(result);
               
                ps.close();
                fOut.close();

            } catch (IOException ex) {

                System.out.println("Could not create file.");
                System.exit(1);

            }

        } catch (IOException ex) {
            Logger.getLogger(JavaWebClient.class.getName()).log(Level.SEVERE, null, ex);

        } catch (Exception ex) {
            Logger.getLogger(JavaWebClient.class.getName()).log(Level.SEVERE, null, ex);
        }

    }


    private static String parseTextTXT(java.lang.String rawTextInput) {
        uaic.webposro.PosTaggerRoWS_Service service = new uaic.webposro.PosTaggerRoWS_Service();
        uaic.webposro.PosTaggerRoWS port = service.getPosTaggerRoWSPort();
        return port.parseTextTXT(rawTextInput);
    }

    private static String parseTextXML(java.lang.String rawTextInput) {
        uaic.webposro.PosTaggerRoWS_Service service = new uaic.webposro.PosTaggerRoWS_Service();
        uaic.webposro.PosTaggerRoWS port = service.getPosTaggerRoWSPort();
        return port.parseTextXML(rawTextInput);
    }

    private static String parseTextXML_1(java.lang.String rawTextInput) {
        uaic.webposro.PosTaggerRoWS_Service service = new uaic.webposro.PosTaggerRoWS_Service();
        uaic.webposro.PosTaggerRoWS port = service.getPosTaggerRoWSPort();
        return port.parseTextXML(rawTextInput);
    }

    private static String parseSentenceTXT(java.lang.String rawSentenceInput) {
        uaic.webposro.PosTaggerRoWS_Service service = new uaic.webposro.PosTaggerRoWS_Service();
        uaic.webposro.PosTaggerRoWS port = service.getPosTaggerRoWSPort();
        return port.parseSentenceTXT(rawSentenceInput);
    }

    private static String parseSentenceXML(java.lang.String rawSentenceInput) {
        uaic.webposro.PosTaggerRoWS_Service service = new uaic.webposro.PosTaggerRoWS_Service();
        uaic.webposro.PosTaggerRoWS port = service.getPosTaggerRoWSPort();
        return port.parseSentenceXML(rawSentenceInput);
    }

    private static String chunkTaggedText(java.lang.String taggedXml) {
        uaic.webnpchunkerro.NpChunkerRoWS_Service service = new uaic.webnpchunkerro.NpChunkerRoWS_Service();
        uaic.webnpchunkerro.NpChunkerRoWS port = service.getNpChunkerRoWSPort();
        return port.chunkTaggedText(taggedXml);
    }

    private static String chunkText(java.lang.String inputText) {
        uaic.webnpchunkerro.NpChunkerRoWS_Service service = new uaic.webnpchunkerro.NpChunkerRoWS_Service();
        uaic.webnpchunkerro.NpChunkerRoWS port = service.getNpChunkerRoWSPort();
        return port.chunkText(inputText);
    }

}
