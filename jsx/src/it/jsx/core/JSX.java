package it.jsx.core;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import it.jsx.exception.JSXException;
import it.jsx.exception.XMLException;

public class JSX {
	private static JSX xmlManagerAS;
	private final WriterXML writerXML = new WriterXML();
	private final ReaderXML readerXML = new ReaderXML();

	private JSX() {
	}

	/* singleton */
	public static JSX getInstance() {
		return (xmlManagerAS = (xmlManagerAS == null) ? new JSX() : xmlManagerAS);
	}
	
	/**
	 * 
	 * @param pathFileName
	 * path del file da cui creare il Document
	 * @return org.w3c.dom.Docuement
	 * oggetto che rappresenta documento xml 
	 * @throws XMLException
	 * eccezione sollevata se la creazione dell'oggetto Document ritorna errori
	 */
	/* metodo che restituisce il doc */
	public Document getDocument(String pathFileName) throws XMLException {
		try {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			
			docFactory.setValidating(false);
			docFactory.setNamespaceAware(false);
			docFactory.setFeature("http://xml.org/sax/features/namespaces", false);
			docFactory.setFeature("http://xml.org/sax/features/validation", false);
			docFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-dtd-grammar", false);
			docFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);

			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			return docBuilder.parse(pathFileName);
		} catch (ParserConfigurationException | SAXException | IOException e) {
			throw new XMLException("Impossibile lavorare sul file XML.\n\t"
										+ "Message error: " + e.getMessage());
		}
	}
	
	/**
	 * 
	 * @param pathFileName
	 * path file su cui aggiungere gli elementi
	 * @param nameElement
	 * nome elemento da aggiungere
	 * @param mapAttributes
	 * mappa attributi da inserire sul nuovo elemento, da impostare con &lt;chiave, valore&gt;
	 * @param mapChildNode
	 * mappa elementi figli del nuovo elemento, da impostare con &lt;nome_elemento, test_interno_tag&gt;
	 * @param mapAttributesChild
	 * mappa attributi degli elementi figli, da impostare con &lt;nome_elemento, &lt;chiave, valore&gt;&gt;
	 * @throws XMLException
	 * eccezione sollevata se la creazione dell'oggetto Document ritorna errori
	 */
	/* metodo che aggiunge elementi con child e mappe per attributi e scrive su pathFile */
	public void addElementWithChild(String pathFileName, String nameElement, HashMap<String, String> mapAttributes, HashMap<String, String> mapChildNode,
										HashMap<String, HashMap<String, String>> mapAttributesChild) throws XMLException {
		Document doc = getDocument(pathFileName);
		writerXML.addElementWithChild(doc, nameElement, mapAttributes, mapChildNode, mapAttributesChild);
		flush(doc, pathFileName, 4);
	}

	/**
	 * 
	 * @param pathFileName
	 * path file su cui aggiungere gli elementi
	 * @param nameElement
	 * nome elemento da aggiungere
	 * @param idElement
	 * id da dare all'elemento
	 * @param mapChildNode
	 * mappa elementi figli del nuovo elemento, da impostare con &lt;nome_elemento, test_interno_tag&gt;
	 * @param mapIdChild
	 * mappa id elementi figli del nuovo elemento, da impostare con &lt;nome_elemento, id&gt;
	 * @throws XMLException
	 * eccezione sollevata se la creazione dell'oggetto Document ritorna errori
	 */
	/* metodo che aggiunge elementi con child, setta attributo id e scrive su pathFile */
	public void addElementWithChild(String pathFileName, String nameElement, String idElement, HashMap<String, String> mapChildNode,
										HashMap<String, String> mapIdChild) throws XMLException {
		Document doc = getDocument(pathFileName);
		writerXML.addElementWithChild(doc, nameElement, idElement, mapChildNode, mapIdChild);
		flush(doc, pathFileName, 4);
	}

	/**
	 * 
	 * @param doc
	 * documento che rappresenta l'xml
	 * @param nameElement
	 * nome elemento da aggiungere
	 * @param mapAttributes
	 * mappa attributi da inserire sul nuovo elemento, da impostare con &lt;chiave, valore&gt;
	 * @param mapChildNode
	 * mappa elementi figli del nuovo elemento, da impostare con &lt;nome_elemento, test_interno_tag&gt;
	 * @param mapAttributesChild
	 * mappa attributi degli elementi figli, da impostare con &lt;nome_elemento, &lt;chiave, valore&gt;&gt;
	 */
	/* metodo che aggiunge elementi con child e mappe per attributi e scrive su Document */
	public void addElementWithChild(Document doc, String nameElement, HashMap<String, String> mapAttributes, HashMap<String, String> mapChildNode,
										HashMap<String, HashMap<String, String>> mapAttributesChild) {
		writerXML.addElementWithChild(doc, nameElement, mapAttributes, mapChildNode, mapAttributesChild);
	}

	/**
	 * 
	 * @param doc
	 * documento che rappresenta l'xml
	 * @param nameElement
	 * nome elemento da aggiungere
	 * @param idElement
	 * id da dare all'elemento
	 * @param mapChildNode
	 * mappa elementi figli del nuovo elemento, da impostare con &lt;nome_elemento, test_interno_tag&gt;
	 * @param mapIdChild
	 * mappa id elementi figli del nuovo elemento, da impostare con &lt;nome_elemento, id&gt;
	 */
	/* metodo che aggiunge elementi con child, setta attributo id e scrive su Document */
	public void addElementWithChild(Document doc, String nameElement, String idElement, HashMap<String, String> mapChildNode,
										HashMap<String, String> mapIdChild) {
		writerXML.addElementWithChild(doc, nameElement, idElement, mapChildNode, mapIdChild);
	}
	
	/**
	 * 
	 * @param pathFileName
	 * path file su cui aggiungere gli elementi
	 * @param nameElement
	 * nome elemento da aggiungere
	 * @param mapAttributes
	 * mappa attributi da inserire sul nuovo elemento, da impostare con &lt;chiave, valore&gt;
	 * @throws XMLException
	 * eccezione sollevata se la creazione dell'oggetto Document ritorna errori
	 */
	/* metodo che aggiunge un elemento al firstchild con mappa attributi su pathFileName */
	public void addElement(String pathFileName, String nameElement, HashMap<String, String> mapAttributes) throws XMLException {
		Document doc = getDocument(pathFileName);
		writerXML.addElement(doc, nameElement, mapAttributes);
		flush(doc, pathFileName, 4);
	}
	
	/**
	 * 
	 * @param pathFileName
	 * path file su cui aggiungere gli elementi
	 * @param nameElement
	 * nome elemento da aggiungere
	 * @param idElement
	 * id da dare all'elemento
	 * @throws XMLException
	 * eccezione sollevata se la creazione dell'oggetto Document ritorna errori
	 */
	/* metodo che aggiunge un elemento al firstchild con attributo id su pathFileName */
	public void addElement(String pathFileName, String nameElement, String idElement) throws XMLException {
		Document doc = getDocument(pathFileName);
		writerXML.addElement(doc, nameElement, idElement);
		flush(doc, pathFileName, 4);
	}
	
	/**
	 * 
	 * @param doc
	 * documento che rappresenta l'xml
	 * @param nameElement
	 * nome elemento da aggiungere
	 * @param mapAttributes
	 * mappa attributi da inserire sul nuovo elemento, da impostare con &lt;chiave, valore&gt;
	 */
	/* metodo che aggiunge un elemento al firstchild con mappa attributi su Document */
	public void addElement(Document doc, String nameElement, HashMap<String, String> mapAttributes) {
		writerXML.addElement(doc, nameElement, mapAttributes);
	}
	
	/**
	 * 
	 * @param doc
	 * documento che rappresenta l'xml
	 * @param nameElement
	 * nome elemento da aggiungere
	 * @param idElement
	 * id da dare all'elemento
	 */
	/* metodo che aggiunge un elemento al firstchild con attributo id su Document */
	public void addElement(Document doc, String nameElement, String idElement) {
		writerXML.addElement(doc, nameElement, idElement);
	}

	/**
	 * 
	 * @param parentNode
	 * nodo in cui inserire il nuovo elemento
	 * @param nameElement
	 * nome elemento da aggiungere
	 * @param mapAttributes
	 * mappa attributi da inserire sul nuovo elemento, da impostare con &lt;chiave, valore&gt;
	 * @param textContext
	 * testo da inserire all'interno del nuovo elemento
	 */
	/* metodo che aggiunge un elemento figlio al node con mappe attributi */
	public void addChildElement(Node parentNode, String nameElement, HashMap<String, String> mapAttributes, String textContext) {
		writerXML.addChildElement(parentNode, nameElement, mapAttributes, textContext);
	}

	/**
	 * 
	 * @param parentNode
	 * nodo in cui inserire il nuovo elemento
	 * @param nameElement
	 * nome elemento da aggiungere
	 * @param idElement
	 * id da dare all'elemento
	 * @param textContext
	 * testo da inserire all'interno del nuovo elemento
	 */
	/* metodo che aggiunge un elemento figlio al node con id */
	public void addChildElement(Node parentNode, String nameElement, String idElement, String textContext) {
		writerXML.addChildElement(parentNode, nameElement, idElement, textContext);
	}

	/**
	 * @param pathFileName
	 * path file su cui aggiungere gli elementi
	 * @param nameElement
	 * nome elemento da aggiungere
	 * @param mapAttributes
	 * mappa attributi da inserire sul nuovo elemento, da impostare con &lt;chiave, valore&gt;
	 * @param mapChildNode
	 * mappa elementi figli del nuovo elemento, da impostare con &lt;nome_elemento, test_interno_tag&gt;
	 * @throws XMLException
	 * eccezione sollevata se la creazione dell'oggetto Document ritorna errori
	 */
	/* metodo che aggiunge elementi con child e mappe per attributi e scrive su pathFile */
	public void addElementWithChild(String pathFileName, String nameElement, HashMap<String, String> mapAttributes, HashMap<String, String> mapChildNode) throws XMLException {
		Document doc = getDocument(pathFileName);
		writerXML.addElementWithChild(doc, nameElement, mapAttributes, mapChildNode);
		flush(doc, pathFileName, 4);
	}

	/**
	 * 
	 * @param pathFileName
	 * path file su cui aggiungere gli elementi
	 * @param nameElement
	 * nome elemento da aggiungere
	 * @param idElement
	 * id da dare all'elemento
	 * @param mapChildNode
	 * mappa elementi figli del nuovo elemento, da impostare con &lt;nome_elemento, test_interno_tag&gt;
	 * @throws XMLException
	 * eccezione sollevata se la creazione dell'oggetto Document ritorna error
	 */
	/* metodo che aggiunge elementi con child, setta attributo id e scrive su pathFile */
	public void addElementWithChild(String pathFileName, String nameElement, String idElement, HashMap<String, String> mapChildNode) throws XMLException {
		Document doc = getDocument(pathFileName);
		writerXML.addElementWithChild(doc, nameElement, idElement, mapChildNode);
		flush(doc, pathFileName, 4);
	}

	/**
	 * 
	 * @param doc
	 * documento che rappresenta l'xml
	 * @param nameElement
	 * nome elemento da aggiungere
	 * @param mapAttributes
	 * mappa attributi da inserire sul nuovo elemento, da impostare con &lt;chiave, valore&gt;
	 * @param mapChildNode
	 * mappa elementi figli del nuovo elemento, da impostare con &lt;nome_elemento, test_interno_tag&gt;
	 */
	/* metodo che aggiunge elementi con child e mappe per attributi e scrive su Document */
	public void addElementWithChild(Document doc, String nameElement, HashMap<String, String> mapAttributes, HashMap<String, String> mapChildNode) {
		writerXML.addElementWithChild(doc, nameElement, mapAttributes, mapChildNode);
	}


	/**
	 * 
	 * @param doc
	 * documento che rappresenta l'xml
	 * @param nameElement
	 * nome elemento da aggiungere
	 * @param idElement
	 * id da dare all'elemento
	 * @param mapChildNode
	 * mappa elementi figli del nuovo elemento, da impostare con &lt;nome_elemento, test_interno_tag&gt;
	 */
	/* metodo che aggiunge elementi con child, setta attributo id e scrive su Document */
	public void addElementWithChild(Document doc, String nameElement, String idElement, HashMap<String, String> mapChildNode) {
		writerXML.addElementWithChild(doc, nameElement, idElement, mapChildNode);
	}
	
	/**
	 * 
	 * @param pathFileName
	 * path file su cui aggiungere gli elementi
	 * @param nameElement
	 * nome elemento da aggiungere
	 * @throws XMLException
	 * eccezione sollevata se la creazione dell'oggetto Document ritorna error
	 */
	/* metodo che aggiunge un elemento al firstchild con mappa attributi su pathFileName */
	public void addElement(String pathFileName, String nameElement) throws XMLException {
		Document doc = getDocument(pathFileName);
		writerXML.addElement(doc, nameElement);
		flush(doc, pathFileName, 4);
	}
	
	/**
	 * 
	 * @param pathFileName
	 * path file su cui aggiungere gli elementi
	 * @param nameElement
	 * nome elemento da aggiungere
	 * @param textContent
	 * testo da inserire all'interno del nuovo elemento
	 * @throws XMLException
	 * eccezione sollevata se la creazione dell'oggetto Document ritorna error
	 */
	/* metodo che aggiunge un elemento al firstchild con attributo id su pathFileName */
	public void addElementText(String pathFileName, String nameElement, String textContent) throws XMLException {
		Document doc = getDocument(pathFileName);
		writerXML.addElementText(doc, nameElement, textContent);
		flush(doc, pathFileName, 4);
	}
	
	/**
	 * 
	 * @param doc
	 * documento che rappresenta l'xml
	 * @param nameElement
	 * nome elemento da aggiungere
	 */
	/* metodo che aggiunge un elemento al firstchild con mappa attributi su Document */
	public void addElement(Document doc, String nameElement) {
		writerXML.addElement(doc, nameElement);
	}
	
	/**
	 * 
	 * @param doc
	 * documento che rappresenta l'xml
	 * @param nameElement
	 * nome elemento da aggiungere
	 * @param textContent
	 * testo da inserire all'interno del nuovo elemento
	 */
	/* metodo che aggiunge un elemento al firstchild con attributo id su Document */
	public void addElementText(Document doc, String nameElement, String textContent) {
		writerXML.addElementText(doc, nameElement, textContent);
	}

	/**
	 * 
	 * @param parentNode
	 * nodo in cui inserire il nuovo elemento
	 * @param nameElement
	 * nome elemento da aggiungere
	 * @param textContent
	 * testo da inserire all'interno del nuovo elemento
	 */
	/* metodo che aggiunge un elemento figlio al node con mappe attributi */
	public void addChildElement(Node parentNode, String nameElement, String textContext) {
		writerXML.addChildElement(parentNode, nameElement, textContext);
	}

	
	/**
	 * 
	 * @param pathFileName
	 * path file xml
	 * @param nameElements
	 * nome elementi da inserire nella lista
	 * @return ArrayList&lt;Node&gt;
	 * lista di nodi
	 * @throws XMLException
	 * eccezione sollevata se la creazione dell'oggetto Document ritorna error
	 */
	/* metodo che ritorna lista di elementi con filtro nome */
	public ArrayList<Node> getArrayElements(String pathFileName, String nameElements) throws XMLException {
		Document doc = getDocument(pathFileName);
		return readerXML.getArrayElements(doc, nameElements);
	}

	/**
	 * 
	 * @param pathFileName
	 * path file xml
	 * @param nameElements
	 * nome elementi da inserire nella lista
	 * @return HashMap&lt;String, Node&gt;
	 * mappa con &lt;id, nodo&gt;
	 * @throws XMLException
	 * eccezione sollevata se la creazione dell'oggetto Document ritorna error
	 * 
	 */
	public HashMap<String, Node> getMapIdElement(String pathFileName, String nameElements) throws XMLException {
		Document doc = getDocument(pathFileName);
		return readerXML.getMapIdElement(doc, nameElements);
	}

	/**
	 * 
	 * @param doc
	 * documento che rappresenta l'xml
	 * @param nameElements
	 * nome elementi da inserire nella lista
	 * @return ArrayList&lt;Node&gt;
	 * lista di nodi
	 */
	/* metodo che ritorna lista di elementi con filtro nome */
	public ArrayList<Node> getArrayElements(Document doc, String nameElements) {
		return readerXML.getArrayElements(doc, nameElements);
	}

	/**
	 * 
	 * @param doc
	 * documento che rappresenta l'xml
	 * @param nameElements
	 * nome elementi da cercare
	 * @param idElement
	 * id elemento da cercare
	 * @return Node
	 * primo nodo trovate con nome e id passati in ingresso
	 */
	/* metodo che ritorna lista di elementi con filtro nome e id */
	public Node getElementById(Document doc, String nameElement, String idElement) {
		return readerXML.getElementById(doc, nameElement, idElement);
	}

	/**
	 * 
	 * @param doc
	 * documento che rappresenta l'xml
	 * @param nameElements
	 * nome elementi da cercare
	 * @return HashMap&lt;String, Node&gt;
	 * mappa con &lt;id, nodo&gt;
	 */
	/* metodo che ritorna mappa di nodi con key id */
	public HashMap<String, Node> getMapIdElement (Document doc, String nameElements) {
		return readerXML.getMapIdElement(doc, nameElements);
	}

	/**
	 * 
	 * @param parentNode
	 * nodo genitore
	 * @return ArrayList&lt;Node&gt;
	 * lista con tutti gli elementi figli di parentNode
	 */
	/* metodo che ritorna lista di elementi figli */
	public ArrayList<Node> getArrayChildNode(Node parentNode) {
		return readerXML.getArrayChildNode(parentNode);
	}

	/**
	 * 
	 * @param parentNode
	 * nodo genitore
	 * @param nameElements
	 * nome elementi da inserire nella lista
	 * @return ArrayList&lt;Node&gt;
	 * lista con elementi figli che corrispondono al nome
	 */
	/* metodo che ritorna lista di elementi figli con filtro nome */
	public ArrayList<Node> getArrayChildNode(Node parentNode, String nameElements) {
		return readerXML.getArrayChildNode(parentNode, nameElements);
	}

	/**
	 * 
	 * @param parentNode
	 * nodo genitore
	 * @param nameElements
	 * nome elementi da inserire nella lista
	 * @param idElement
	 * id dell'elemento da ritornare
	 * @return Node
	 * primo nodo figlio trovato con nome e id passati
	 */
	/* metodo che ritorna un elemento figlo con filtro nome e id */
	public Node getChildNodeById(Node parentNode, String nameElements, String idElement) {
		return readerXML.getChildNodeById(parentNode, nameElements, idElement);
	}

	/**
	 * 
	 * @param parentNode
	 * nodo genitore
	 * @param nameElements
	 * nome elementi da inserire nella lista
	 * @return HashMap&lt;String, Node&gt;
	 * mappa con elementi figli &lt;id, nodo&gt;
	 */
	/* metodo che ritorna mappa di id con nodi child con key id */
	public HashMap<String, Node> getMapIdChildNode(Node parentNode, String nameElement) {
		return readerXML.getMapIdChildNode(parentNode, nameElement);
	}
	
	/**
	 * 
	 * @param pathFileName
	 * path file xml
	 * @param nameElement
	 * nome elemento da eliminare
	 * @param id
	 * id elemento da eliminare
	 * @throws XMLException
	 */
	public void deleteNode(String pathFileName, String nameElement, String id) throws XMLException {
		Document doc = getDocument(pathFileName);
		writerXML.deleteNode(doc, nameElement, id);
		flush(doc, pathFileName, 4);
	}

	/**
	 * 
	 * @param doc
	 * documento che rappresenta l'xml
	 * @param nameElement
	 * nome elemento da eliminare
	 * @param id
	 * id elemento da eliminare
	 */
	public void deleteNode(Document doc, String nameElement, String id) {
		writerXML.deleteNode(doc, nameElement, id);
	}

	/**
	 * 
	 * @param node
	 * oggetto nodo da eliminare
	 */
	public void deleteNode(Node node) {
		writerXML.deleteNode(node);
	}

	/**
	 * 
	 * @param setId
	 * set di id numerici
	 * @return int
	 * id piu' grande di tutti quelli passati nel set
	 */
	/* metodo che torna l'id piu'grande da un set id */
	public int getGreatId(Set<String> setId) {
		int greatId = Integer.MIN_VALUE;
		for (String id : setId)
			greatId = (Long.parseLong(id) > greatId) ? Integer.parseInt(id) : greatId; 
			
		return greatId;
	}


	protected void removeBlankLines(Document doc) throws JSXException {
		try {
			XPath xp = XPathFactory.newInstance().newXPath();
			NodeList nl;
			nl = (NodeList) xp.evaluate("//text()[normalize-space(.)='']", doc, XPathConstants.NODESET);
			
			for (int i=0; i < nl.getLength(); ++i) {
				Node node = nl.item(i);
				node.getParentNode().removeChild(node);
			}
		} catch (XPathExpressionException e) {
			throw new JSXException("Errore derante l'eliminazione degli righe vuote.\n"
									+ "Error Message: " + e.getMessage());
		}
	}

	/**
	 * 
	 * @param doc
	 * documento che rappresenta l'xml
	 * @param filePath
	 * path in cui salvare l'xml
	 * @throws XMLException
	 * eccezione sollevata se il salvataggio del file xml ritorna erroriS
	 */
	/* salva modifiche nel file xml */
	public void flush(Document doc, String filePath, int indentAmount) throws XMLException {
		try {
			removeBlankLines(doc);
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			if (indentAmount > 0) {
				transformer.setOutputProperty(OutputKeys.INDENT, "yes");
				transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", String.valueOf(indentAmount));
			}
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(filePath));
			transformer.transform(source, result);
		} catch (TransformerException | JSXException e) {
			throw new XMLException("Impossibile lavorare sul file XML.\n\t"
										+ "Message error: " + e.getMessage());
		}

	}
}
