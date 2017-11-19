package com.utils;

import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

public class XMLGenerator {

	public static void main(String[] args) {
		String nombre_archivo = "geeky";
		ArrayList key = new ArrayList();
		ArrayList value = new ArrayList();

		key.add("opcion1");
		value.add("22");

		key.add("opcion2");
		value.add("22");

		key.add("opcion3");
		value.add("22");

		key.add("opcion4");
		value.add("25");

		try {
			generate(nombre_archivo, key, value);
		} catch (Exception e) {
		}
	}

	public static void generate(String name, ArrayList<String> key, ArrayList<String> value) throws Exception {

		if (key.isEmpty() || value.isEmpty() || key.size() != value.size()) {
			System.out.println("ERROR empty ArrayList");
			return;
		} else {

			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			DOMImplementation implementation = builder.getDOMImplementation();
			Document document = implementation.createDocument(null, name, null);
			document.setXmlVersion("1.0");

			// Main Node
			Element raiz = document.getDocumentElement();
			// Por cada key creamos un item que contendrá la key y el value
			for (int i = 0; i < key.size(); i++) {
				// Item Node
				Element itemNode = document.createElement("ITEM");
				// Key Node
				Element keyNode = document.createElement("KEY");
				Text nodeKeyValue = document.createTextNode(key.get(i));
				keyNode.appendChild(nodeKeyValue);
				// Value Node
				Element valueNode = document.createElement("VALUE");
				Text nodeValueValue = document.createTextNode(value.get(i));
				valueNode.appendChild(nodeValueValue);
				// append keyNode and valueNode to itemNode
				itemNode.appendChild(keyNode);
				itemNode.appendChild(valueNode);
				// append itemNode to raiz
				raiz.appendChild(itemNode); // pegamos el elemento a la raiz
											// "Documento"
			}
			// Generate XML
			Source source = new DOMSource(document);
			// Indicamos donde lo queremos almacenar
			Result result = new StreamResult(new java.io.File("C:/Documentos/"+name + ".xml")); // nombre
																				// del
																				// archivo
			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			transformer.transform(source, result);
		}
	}

}
