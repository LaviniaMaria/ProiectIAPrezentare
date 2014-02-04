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
import java.io.Reader;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import pentru.qa.PentruQA;

/**
 *
 * @author Mada
 */
public class JavaWebClient {

    public static void main(String[] args) throws FileNotFoundException, IOException {
        
        String x = args[0];
        String y = args[1];
        textAnalytics(x,y);
       
    }
    
    public static boolean isLetter(char letter) {

        String letterCode = Integer.toHexString(letter | 0x10000).substring(1);

        String a = Integer.toHexString('Ă' | 0x10000).substring(1);
        String b = Integer.toHexString('Î' | 0x10000).substring(1);
        String c = Integer.toHexString('Â' | 0x10000).substring(1);
        String d = Integer.toHexString('Ș' | 0x10000).substring(1);
        String e = Integer.toHexString('Ț' | 0x10000).substring(1);
        String f = Integer.toHexString('Ş' | 0x10000).substring(1);

        if ((letter >= 'A' && letter <= 'Z') ||
            letterCode.equals(a) || 
            letterCode.equals(b) || 
            letterCode.equals(c) || 
            letterCode.equals(d) || 
            letterCode.equals(e) ||
            letterCode.equals(f))

           return true;

        return false;
   }
    
     private static String readFromFile(String inputFile) {
        
        String text = " ";
        try {
        
            Reader reader = new InputStreamReader(
                        new FileInputStream(inputFile),"UTF-8");
            BufferedReader br= new BufferedReader(reader);
            
            String t;
            do {
                t = br.readLine();
               
                if (t != null) {
                    text = text + t;
                }
            } while (t != null);
            
        }catch(FileNotFoundException e) {
            System.out.println("Fisierul nu exista");
            e.printStackTrace();;
       
        }catch(IOException e) {
            e.printStackTrace();
        }
        
        return text;
        
    }
    
    public static void writeToFile(String outputFile,String result) {
        
         try {
                //FileOutputStream fOut = new FileOutputStream(outputFile);
                if(outputFile.endsWith(".txt"))
                    outputFile=outputFile.substring(0,outputFile.length()-4)+".xml";
                else if(!outputFile.endsWith(".xml"))
                    outputFile=outputFile+".xml";
                
                PrintStream ps = new PrintStream(outputFile,"UTF-8");
                ps.println(result);
               
                ps.close();
               

            } catch (IOException ex) {

                System.out.println("Could not create file.");
                System.exit(1);

            }
    }
    
     public static void textAnalytics(String inputFile, String outputFile) {
   
      try {
          //  PentruQA pentruQA= new PentruQA();
            
            if(inputFile.contains(".pdf")){
                pdf_to_text_v2.PDF_TO_TEXT_V2.transformaPdfInTxt(inputFile);
                inputFile=inputFile.replace(".pdf",".txt");
            }
            
            String outFile = inputFile.substring(0,inputFile.length()-4)+"_dupaAnafora.txt";
          //  pentruQA.rezolvaAnafora(inputFile,outFile);
            String text=readFromFile(inputFile);
            String result = chunkText(text);
  
            writeToFile(outputFile,result);
            
            modifyXML(outputFile);
                
            } catch (ParserConfigurationException ex) {
                Logger.getLogger(JavaWebClient.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SAXException ex) {
                Logger.getLogger(JavaWebClient.class.getName()).log(Level.SEVERE, null, ex);
            } catch (TransformerConfigurationException ex) {
                Logger.getLogger(JavaWebClient.class.getName()).log(Level.SEVERE, null, ex);
            } catch (TransformerException ex) {
                Logger.getLogger(JavaWebClient.class.getName()).log(Level.SEVERE, null, ex);
            }

         }
     
      public static void concatQuotes(Element rootElement,Node currentNode, NodeList nodesList,int i) {
        
         Node nextNode,nextNodeParent;
         String nodeValue,newLemma="",newValue="";
         
         Element currentElement=(Element)currentNode;
       
         String msdValue=currentElement.getAttribute("MSD");
         String lemma=currentElement.getAttribute("LEMMA");
         
         do {  
              nextNode=nodesList.item(i);
              nodeValue=nextNode.getTextContent();
              ((Element)nextNode).setAttribute("Value", nodeValue);
              msdValue=((Element)nextNode).getAttribute("MSD");
              lemma=((Element)nextNode).getAttribute("LEMMA");
              
              newLemma+=nodeValue+"~";
            
              nextNode.getParentNode().removeChild(nextNode);
                      
          }  while(!msdValue.equals("DBLQ"));
                  
         
          newLemma=newLemma.substring(0,newLemma.length()-3);
        
          currentElement.setAttribute("LEMMA", newLemma);
          currentElement.setAttribute("MSD", "Np");
          currentElement.setAttribute("Type", "proper");
          currentElement.setAttribute("POS", "NOUN");
          currentNode.setTextContent('"'+newLemma.replace('~', ' ')+'"');
          currentElement.setAttribute("Value", currentNode.getTextContent());
    }
    
    public static void modifyXML(String filepath) throws ParserConfigurationException, SAXException, TransformerConfigurationException, TransformerException {
             
        try{
            
           DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
           DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
           Document doc = docBuilder.parse(filepath);

           Element rootElement = doc.getDocumentElement();
           NodeList nodesList=rootElement.getElementsByTagName("W");
           
           int i=0;
           
           while(i<nodesList.getLength()) {
               
               Node node=nodesList.item(i);
               String nodeValue=node.getTextContent();
               String msdValue=((Element)node).getAttribute("MSD");
               String lemma=((Element)node).getAttribute("LEMMA");
               
               ((Element)node).setAttribute("Value", nodeValue);
               
               int countWords=0;
               String newLemma=nodeValue;
               String newValue=nodeValue;
               
               Node nextNode=null;
               Element parentNode=(Element)node.getParentNode();
               
               if(msdValue.equals("DBLQ") || lemma.equals("“") ) {
                  
                  i++;
                  concatQuotes(rootElement,node,nodesList,i);
                  
               }
               
               while( i<nodesList.getLength() && msdValue.startsWith("Np")) {
                   
                   countWords++;
                   i++;
                        
                   if(countWords>1) {
                       
                        newLemma+="~"+nodeValue; 
                        nextNode.getParentNode().removeChild(nextNode);
                        i--;
                   }
                 
                   nextNode=nodesList.item(i);
                   nodeValue=nextNode.getTextContent();
                   ((Element)nextNode).setAttribute("Value", nodeValue);
                   
                   lemma=((Element)nextNode).getAttribute("LEMMA");
                   msdValue=((Element)nextNode).getAttribute("MSD");
                   
               }
               
               if(countWords>1)  {
                    
                    Element element=(Element)node;
                    element.removeAttribute("Case");
                    element.removeAttribute("Definiteness");
                    element.removeAttribute("Number");
                    element.removeAttribute("Gender");
                    element.setAttribute("LEMMA", newLemma);
                    element.setAttribute("MSD", "Np");
                    element.setAttribute("Type", "proper");
                    element.setAttribute("POS", "NOUN");
                    element.setTextContent(newLemma.replace('~', ' '));
                    element.setAttribute("Value",element.getTextContent());
                    
               }
               else if(countWords!=1)
                     i++;
           
           }
          
           TransformerFactory transformerFactory = TransformerFactory.newInstance();
           Transformer transformer = transformerFactory.newTransformer();
           DOMSource source = new DOMSource(doc);
           StreamResult finalResult = new StreamResult(filepath);
           transformer.transform(source, finalResult);
           
                
          } catch (IOException ex) {

                System.out.println("Could not create file.");
                System.exit(1);

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
