package com.luo.test;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.List;

public class XmlTest {
    public static void main(String[] args) throws ParserConfigurationException, Exception, SAXException {
        /*DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        documentBuilderFactory.setValidating(true);
        DocumentBuilder builder = documentBuilderFactory.newDocumentBuilder();
        Document document = builder.parse(new File("E:\\JavaFile\\a.xml"));
        Element element= document.getElementById("root");
        element.getNodeValue();*/

        SAXReader saxReader = new SAXReader();
        Document document = saxReader.read("E:\\JavaFile\\a.xml");
        Element element = document.getRootElement();
        String name = element.element("name").getTextTrim();
        System.out.println(name);

        List<Element> element1 = element.elements("a");

    }

}
