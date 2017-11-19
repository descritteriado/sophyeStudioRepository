package com.utils;

import java.io.File;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

// Clases para la creacion y manejo de XML
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

public class XMLMaker {

	// Objecto que representa al documento XML
	Document documento;

	/**
	 * Creamos el documento XML
	 *
	 * @throws ParserConfigurationException
	 */
	public XMLMaker() throws ParserConfigurationException {
		// Creamos los objectos de creacion de Documentos XML
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder constructor = docFactory.newDocumentBuilder();

		// Creamos el documento XML
		documento = constructor.newDocument();
	}

	/**
	 * Regresa el documento “plano” sin saltos de linea
	 *
	 * @return Documento XML plano
	 * @throws TransformerConfigurationException
	 * @throws TransformerException
	 */
	public String convertirString() throws TransformerConfigurationException, TransformerException {
		// Creamos el objecto transformador
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer transformer = tf.newTransformer();
		// Creamos el escritor a cadena de texto
		StringWriter writer = new StringWriter();
		// Fuente de datos, en este caso el documento XML
		DOMSource source = new DOMSource(documento);
		// Resultado, el cual se almacenara en el objecto writer
		StreamResult result = new StreamResult(writer);
		// Efectuamos la transformacion a lo que indica el objecto resultado,
		// writer apuntara a el resultado
		transformer.transform(source, result);
		// Convertimos el buffer de writer en cadena de texto
		String output = writer.getBuffer().toString();

		return output;
	}

	/**
     * Envia el documento XML a un archivo
     * @throws TransformerConfigurationException
     * @throws TransformerException 
     */
    public void escribirArchivo() throws TransformerConfigurationException, TransformerException {
        // Creamos el objecto transformador
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        
        // Archivo donde almacenaremos el XML
        File archivo = new File("C:/Documentos/entrada.xml");
        
        // Fuente de datos, en este caso el documento XML
        DOMSource source = new DOMSource(documento);
        // Resultado, el cual almacena en el archivo indicado
        StreamResult result = new StreamResult(archivo);
        // Transformamos de la fuente DOM a el resultado, lo que almacena todo en el archivo
        transformer.transform(source, result);
    }

	/**
     * Creamos un documento con un elemento principal y varios subElementos
     */
    public void crearDocumento() {
        // Creamos el elemento principal
        Element entrada = documento.createElement("ENTRADA");
        // Hacemos el elemento entrada descender directo del nodo XML principal
        documento.appendChild(entrada);

        // Creamos el Elemento de titulo
        Element titulo = documento.createElement("TITULO");
        // Establecemos el contenido del titulo
        titulo.setTextContent("Creacion de XML");
        // Indicamos que el elemento titulo desciende de entrada
        entrada.appendChild(titulo);

        //Creamos mas elementos
        Element autor = documento.createElement("AUTOR");
        autor.setTextContent("hashRaygoza");
        entrada.appendChild(autor);

        //Elemento fecha
        Element fecha = documento.createElement("FECHA");
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendario = Calendar.getInstance();
        Date date = new Date(calendario.getTimeInMillis());

        fecha.setTextContent(formato.format(date));
        entrada.appendChild(fecha);
    }

	/**
	 * Usamos todas las funciones
	 * 
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String[] args) {
		try {
			XMLMaker creador = new XMLMaker();

			creador.crearDocumento();
			creador.escribirArchivo();
			System.out.println(creador.convertirString());

		} catch (ParserConfigurationException ex) {
			Logger.getLogger(XMLMaker.class.getName()).log(Level.SEVERE, null, ex);
		} catch (TransformerException ex) {
			Logger.getLogger(XMLMaker.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
}