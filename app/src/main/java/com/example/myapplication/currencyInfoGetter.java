package com.example.myapplication;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
import java.net.URL;

public class currencyInfoGetter {
    public static float getInfoByDate(String curr1, String date) {
        try {
            String url = "http://www.cbr.ru/scripts/XML_daily.asp?date_req=" + date;
            Document doc = null;
            URL u = null;
            u = new URL(url);
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();

            doc = db.parse(new InputSource(u.openStream()));
            doc.getDocumentElement().normalize();
            float ans = (float) 1.0;
            for (int i=0; i < doc.getDocumentElement().getChildNodes().getLength(); i++){
                String str1 = doc.getDocumentElement().getChildNodes().item(i).getChildNodes().item(4).getTextContent();
                str1 = str1.replace(',', '.');
                String str2 = doc.getDocumentElement().getChildNodes().item(i).getChildNodes().item(2).getTextContent();
                str2 = str2.replace(',', '.');
                if (doc.getDocumentElement().getChildNodes().item(i).getChildNodes().item(1).getTextContent().equals("USD"))
                    ans = Float.parseFloat(str1) / Integer.parseInt(str2);
            }
            return ans;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (float) -0.01;
    }

    private static org.w3c.dom.Document convertStringToXMLDocument(String xmlString)
    {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;
        try
        {
            builder = factory.newDocumentBuilder();
            org.w3c.dom.Document doc = builder.parse(new InputSource(new StringReader(xmlString)));
            return doc;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
}
