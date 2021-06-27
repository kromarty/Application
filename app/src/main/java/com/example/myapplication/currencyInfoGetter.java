package com.example.myapplication;

import org.xml.sax.InputSource;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.HttpURLConnection;
import java.util.Arrays;

public class currencyInfoGetter {
    public static float[] getInfoByDate(String curr1, String curr2, String date) {
        HttpURLConnection con = null;
        String url = "http://www.cbr.ru/scripts/XML_daily.asp?date_req=" + date;

        try {
            URL u = new URL(url);
            con = (HttpURLConnection) u.openConnection();

            con.connect();
            System.out.println("HERE I AM");

            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));

            org.w3c.dom.Document doc = convertStringToXMLDocument(br.readLine());
            float[] ans = new float[2];
            Arrays.fill(ans, (float) 1.0);
            for (int i=0; i < doc.getDocumentElement().getChildNodes().getLength(); i++){
                String str1 = doc.getDocumentElement().getChildNodes().item(i).getChildNodes().item(4).getTextContent();
                str1 = str1.replace(',', '.');
                String str2 = doc.getDocumentElement().getChildNodes().item(i).getChildNodes().item(2).getTextContent();
                str2 = str2.replace(',', '.');
                if (doc.getDocumentElement().getChildNodes().item(i).getChildNodes().item(1).getTextContent().equals(curr1))
                    ans[0] = Float.parseFloat(str1) / Integer.parseInt(str2);
                else if (doc.getDocumentElement().getChildNodes().item(i).getChildNodes().item(1).getTextContent().equals(curr2))
                    ans[1] = Float.parseFloat(str1) / Integer.parseInt(str2);

            }

            return ans;


        } catch (MalformedURLException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }finally {
            con.disconnect();
        }
        return null;
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
