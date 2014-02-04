package pentru.qa;

import infoiasi.Infoiasi;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import npchunkerwrapper.NpChunkerWrapper;
import org.jdom.JDOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import qa.transf.QaTRANSF;

public class PentruQA {

    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException, TransformerConfigurationException, TransformerException, JDOMException, FileNotFoundException, Exception {

        //rezolvaAnafora("C:\\Users\\Veronica\\Desktop\\Mama.txt", "C:\\Users\\Veronica\\Desktop\\Tata.txt");
//        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
//        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
//        Document doc = dBuilder.parse(new FileInputStream("QA4MRE-2013-RO_test.xml"));
//        Element root = doc.getDocumentElement();
//        root.normalize();
//
//        NodeList nl_doc = root.getElementsByTagName("doc");
//        for (int i = 0; i < nl_doc.getLength(); i++) {
//            Element current_doc = (Element) nl_doc.item(i);
//            String id = current_doc.getAttribute("d_id");
/*
        File dir = new File(args[0]);
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (String name : children) {
                System.out.println(name);
                String input = args[0].toLowerCase() + "/" + name;
                String output = args[0].toLowerCase() + "/" + "_output" + name;
                String text = readFileAsString(input);
                String chunked = chunkText(text);
                NpChunkerWrapper wrapper = new NpChunkerWrapper();
                String for_rare = wrapper.wrapp(chunked);
                for_rare = for_rare.replaceAll("ID=\"([0-9]+)\\.([0-9]+)\"", "ID=\"$1$2\"");
                Infoiasi rare = new Infoiasi();
                String out = rare.parse("ro", for_rare);

//            System.out.println(id);
//            FileWriter fw = new FileWriter(new File(id + ".xml"));
//            fw.append(output);
//            fw.flush();
//            fw.close();

                qa.transf.QaTRANSF transf = new QaTRANSF();
                String docc = transf.transf(out);
                //current_doc.setTextContent(docc);
                FileWriter fw = new FileWriter(output);
                fw.append(docc);
                fw.flush();
                fw.close();
            }
        }

//        String outputFile = "QA4MRE-2013-RO_test RARE.xml";

//        doc.normalize();
//
//        TransformerFactory factory = TransformerFactory.newInstance();
//        Transformer transformer = factory.newTransformer();
//        Result result = new StreamResult(new File(outputFile));
//        Source source = new DOMSource(doc);
//        transformer.transform(source, result);*/
    }
    
    public static void rezolvaAnafora(String inputFilePath, String outputFilePath) throws IOException, ParserConfigurationException, SAXException, TransformerConfigurationException, TransformerException, FileNotFoundException, Exception{
                String text = readFileAsString(inputFilePath);
                String chunked = chunkText(text);
                NpChunkerWrapper wrapper = new NpChunkerWrapper();
                String for_rare = wrapper.wrapp(chunked);
                for_rare = for_rare.replaceAll("ID=\"([0-9]+)\\.([0-9]+)\"", "ID=\"$1$2\"");
                Infoiasi rare = new Infoiasi();
                String out = rare.parse("ro", for_rare);
                
                qa.transf.QaTRANSF transf = new QaTRANSF();
                String docc = transf.transf(out);
                //current_doc.setTextContent(docc);
                File f = new File(outputFilePath);
                    if(!f.exists())
                        f.createNewFile();
                FileWriter fw = new FileWriter(new File(outputFilePath));
                fw.append(docc);
                fw.flush();
                fw.close();
    }

    private static String chunkText(java.lang.String inputText) {
        uaic.webnpchunkerro.NpChunkerRoWS_Service service = new uaic.webnpchunkerro.NpChunkerRoWS_Service();
        uaic.webnpchunkerro.NpChunkerRoWS port = service.getNpChunkerRoWSPort();
        return port.chunkText(inputText);
    }

    private static String readFileAsString(String filePath) throws java.io.IOException {
        StringBuilder fileData = new StringBuilder();
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(filePath).getAbsolutePath()), "UTF8"));
        char[] buf = new char[1024];
        int numRead = 0;
        while ((numRead = reader.read(buf)) != -1) {
            String readData = String.valueOf(buf, 0, numRead);
            fileData.append(readData);
            buf = new char[1024];
        }
        reader.close();
        return fileData.toString();
    }
}
