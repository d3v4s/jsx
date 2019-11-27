package jsx;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

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
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import exception.JSXException;
import exception.JSXLockException;
import exception.XMLException;

/**
 * Class for parser, create and change a XLM file 
 * @author Andrea Serra
 *
 */
public class JSX {
	private final ReentrantLock REENTRANT_LOCK = new ReentrantLock();
	private final WriterXML XML_WRITER = new WriterXML();
	private final ReaderXML XML_READER = new ReaderXML();
	private boolean featLoadDTDGramm = false;
	private boolean namespaceAware = false;
	private boolean featValidation = false;
	private boolean featNamespaces = false;
	private boolean featLoadExtDTD = false;
	private boolean validating = false;
	private boolean autoFlush = false;
	private boolean lock = false;
	private int indentAmount = 4;
	private Document document;
	private String filePath;

	/* ################################################################################# */
	/* START CONSTRUCTORS */
	/* ################################################################################# */

	/**
	 * simple construct
	 */
	public JSX() {
	}

	/**
	 * constructor that set a file path
	 * @param filePath of XML
	 * @throws XMLException
	 * @throws JSXLockException
	 */
	public JSX(String filePath) throws JSXLockException {
		this.filePath = filePath;
		loadDocument();
	}

	/**
	 * constructor that set a Document
	 * @param document XML
	 */
	public JSX(Document document) {
		this.document = document;
		XML_WRITER.setDocument(document);
		XML_READER.setDocument(document);
	}

	/* ################################################################################# */
	/* END CONSTRUCTORS */
	/* ################################################################################# */

	/* ################################################################################# */
	/* START GET AND SET */
	/* ################################################################################# */

	public ReentrantLock getReentrantLock() {
		return REENTRANT_LOCK;
	}
	public String getFilePath() {
		return filePath;
	}
	public Document getDocument() {
		return document;
	}
	public void setDocument(Document document) {
		this.document = document;
		XML_WRITER.setDocument(document);
		XML_READER.setDocument(document);
	}
	public boolean isLock() {
		return lock;
	}
	public void setLock(boolean lock) {
		this.lock = lock;
	}
	public int getIndentAmount() {
		return indentAmount;
	}
	public void setIndentAmount(int indentAmount) {
		this.indentAmount = indentAmount;
	}
	public boolean isAutoFlush() {
		return autoFlush;
	}
	public void setAutoFlush(boolean autoFlush) {
		this.autoFlush = autoFlush;
	}
	public boolean isValidating() {
		return validating;
	}
	public void setValidating(boolean validating) {
		this.validating = validating;
	}
	public boolean isNamespaceAware() {
		return namespaceAware;
	}
	public void setNamespaceAware(boolean namespaceAware) {
		this.namespaceAware = namespaceAware;
	}
	public boolean isFeatValidation() {
		return featValidation;
	}
	public void setFeatValidation(boolean featValidation) {
		this.featValidation = featValidation;
	}
	public boolean isFeatNamespaces() {
		return featNamespaces;
	}
	public void setFeatNamespaces(boolean featNamespaces) {
		this.featNamespaces = featNamespaces;
	}
	public boolean isFeatLoadDTDGramm() {
		return featLoadDTDGramm;
	}
	public void setFeatLoadDTDGramm(boolean featLoadDTDGramm) {
		this.featLoadDTDGramm = featLoadDTDGramm;
	}
	public boolean isFeatLoadExtDTD() {
		return featLoadExtDTD;
	}
	public void setFeatLoadExtDTD(boolean featLoadExtDTD) {
		this.featLoadExtDTD = featLoadExtDTD;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	/* ################################################################################# */
	/* END GET AND SET */
	/* ################################################################################# */

	/* ################################################################################# */
	/* START STATIC METHODS */
	/* ################################################################################# */

	/**
	 * method that create a Document
	 * @param pathFile
	 * path del file da cui creare il Document
	 * @return org.w3c.dom.Docuement
	 * oggetto che rappresenta documento xml 
	 * @throws XMLException
	 * eccezione sollevata se la creazione dell'oggetto Document ritorna errori
	 */
	public static Document createDocument(String pathFile, boolean validating, boolean namespaceAware, boolean featValidation, boolean featNamespaces, boolean featLoadDTDGramm, boolean featLoadExtDTD) throws XMLException {
		try {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			
			docFactory.setValidating(validating);
			docFactory.setNamespaceAware(namespaceAware);
			docFactory.setFeature("http://xml.org/sax/features/validation", featValidation);
			docFactory.setFeature("http://xml.org/sax/features/namespaces", featNamespaces);
			docFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-dtd-grammar", featLoadDTDGramm);
			docFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", featLoadExtDTD);

			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			return docBuilder.parse(pathFile);
		} catch (ParserConfigurationException | SAXException | IOException e) {
			throw new XMLException("Unable to work on a XML file.\nError message: " + e.getMessage());
		}
	}

	/**
	 * method that get a great integer id
	 * @param setId
	 * set di id numerici
	 * @return int
	 * id piu' id maggiore tra quelli passati nel set
	 */
	/* metodo che torna l'id piu'grande da un set id */
	public static int getGreatId(Set<String> setId) {
		int greatId = Integer.MIN_VALUE;
		for (String id : setId) greatId = (Long.parseLong(id) > greatId) ? Integer.parseInt(id) : greatId; 
		return greatId;
	}

	/**
	 * method that remove blank lines on document
	 * metodo che elimina le righe bianche dal document
	 * @param document
	 * oggetto documento a cui si vogliono eliminare le righe vuote
	 * @throws JSXException
	 * eccezione sollevata se la rimozione delle linee vuote ritorna errori
	 */
	public static void removeBlankLines(Document document) throws JSXException {
		try {
			XPath xp = XPathFactory.newInstance().newXPath();
			NodeList nl;
			nl = (NodeList) xp.evaluate("//text()[normalize-space(.)='']", document, XPathConstants.NODESET);
			
			for (int i=0, lenght = nl.getLength(); i < lenght; ++i) {
				Node node = nl.item(i);
				node.getParentNode().removeChild(node);
			}
		} catch (Exception e) {
			throw new JSXException("Error while deleting blank lines.\nError message: " + e.getMessage());
		}
	}

	/* ################################################################################# */
	/* END STATIC METHODS */
	/* ################################################################################# */

	/* ################################################################################# */
	/* START DOCUMENT WRITER METHODS */
	/* ################################################################################# */
	
	/* metodo che aggiunge elementi con child e mappe per attributi e scrive su pathFile */
	/**
	 * method that add child element
	 * @param nameElement
	 * nome elemento da aggiungere
	 * @param mapAttributes
	 * mappa attributi da inserire sul nuovo elemento, da impostare con &lt;chiave, valore&gt;
	 * @param mapChildNode
	 * mappa elementi figli del nuovo elemento, da impostare con &lt;nome_elemento, test_interno_tag&gt;
	 * @param mapAttributesChild
	 * mappa attributi degli elementi figli, da impostare con &lt;nome_elemento, &lt;chiave, valore&gt;&gt;
	 * @throws JSXLockException 
	 */
	public void addElementWithChild(String nameElement, HashMap<String, String> mapAttributes, HashMap<String, String> mapChildNode, HashMap<String, HashMap<String, String>> mapAttributesChild) throws JSXLockException {
		if (tryLock()) {
			XML_WRITER.addElementWithChild(nameElement, mapAttributes, mapChildNode, mapAttributesChild);
			tryUnlock();
			if (autoFlush) flush(filePath, true);
		}
	}

	/**
	 * method that add child element 
	 * @param nameElement
	 * nome elemento da aggiungere
	 * @param idElement
	 * id da dare all'elemento
	 * @param mapChildNode
	 * mappa elementi figli del nuovo elemento, da impostare con &lt;nome_elemento, test_interno_tag&gt;
	 * @param mapIdChild
	 * mappa id elementi figli del nuovo elemento, da impostare con &lt;nome_elemento, id&gt;
	 * @throws JSXLockException 
	 */
	/* metodo che aggiunge elementi con child, setta attributo id e scrive su pathFile */
	public void addElementWithChild(String nameElement, String idElement, HashMap<String, String> mapChildNode, HashMap<String, String> mapIdChild) throws JSXLockException {
		if (tryLock()) {
			XML_WRITER.addElementWithChild(nameElement, idElement, mapChildNode, mapIdChild);
			tryUnlock();
			if (autoFlush) flush(filePath, true);
		}
	}

	
	/**
	 * method that add element
	 * @param nameElement
	 * nome elemento da aggiungere
	 * @param mapAttributes
	 * mappa attributi da inserire sul nuovo elemento, da impostare con &lt;chiave, valore&gt;
	 * @throws JSXLockException 
	 */
	/* metodo che aggiunge un elemento al firstchild con mappa attributi su pathFileName */
	public void addElement(String nameElement, HashMap<String, String> mapAttributes) throws JSXLockException {
		if (tryLock()) {
			XML_WRITER.addElement(nameElement, mapAttributes);
			tryUnlock();
			if (autoFlush) flush(filePath, true);
		}
	}
	
	/**
	 * method that add element
	 * @param nameElement
	 * nome elemento da aggiungere
	 * @param idElement
	 * id da dare all'elemento
	 * @throws JSXLockException 
	 */
	/* metodo che aggiunge un elemento al firstchild con attributo id su pathFileName */
	public void addElement(String nameElement, String idElement) throws JSXLockException {
		if (tryLock()) {
			XML_WRITER.addElement(nameElement, idElement);
			tryUnlock();
			if (autoFlush) flush(filePath, true);
		}
	}
	
	
	/**
	 * method that add child element
	 * @param parentNode
	 * nodo in cui inserire il nuovo elemento
	 * @param nameElement
	 * nome elemento da aggiungere
	 * @param mapAttributes
	 * mappa attributi da inserire sul nuovo elemento, da impostare con &lt;chiave, valore&gt;
	 * @param textContext
	 * testo da inserire all'interno del nuovo elemento
	 * @throws JSXLockException 
	 */
	/* metodo che aggiunge un elemento figlio al node con mappe attributi */
	public void addChildElement(Node parentNode, String nameElement, HashMap<String, String> mapAttributes, String textContext) throws JSXLockException {
		if (tryLock()) {
			XML_WRITER.addChildElement(parentNode, nameElement, mapAttributes, textContext);
			tryUnlock();
			if (autoFlush) flush(filePath, true);
		}
	}

	/**
	 * method that add child element
	 * @param parentNode
	 * nodo in cui inserire il nuovo elemento
	 * @param nameElement
	 * nome elemento da aggiungere
	 * @param idElement
	 * id da dare all'elemento
	 * @param textContext
	 * testo da inserire all'interno del nuovo elemento
	 * @throws JSXLockException 
	 */
	/* metodo che aggiunge un elemento figlio al node con id */
	public void addChildElement(Node parentNode, String nameElement, String idElement, String textContext) throws JSXLockException {
		if (tryLock()) {
			XML_WRITER.addChildElement(parentNode, nameElement, idElement, textContext);
			tryUnlock();
			if (autoFlush) flush(filePath, true);
		}
	}

	/**
	 * method that add child element with child
	 * @param nameElement
	 * nome elemento da aggiungere
	 * @param mapAttributes
	 * mappa attributi da inserire sul nuovo elemento, da impostare con &lt;chiave, valore&gt;
	 * @param mapChildNode
	 * mappa elementi figli del nuovo elemento, da impostare con &lt;nome_elemento, test_interno_tag&gt;
	 * @throws JSXLockException 
	 */
	/* metodo che aggiunge elementi con child e mappe per attributi e scrive su pathFile */
	public void addElementWithChild(String nameElement, HashMap<String, String> mapAttributes, HashMap<String, String> mapChildNode) throws JSXLockException {
		if (tryLock()) {
			XML_WRITER.addElementWithChild(nameElement, mapAttributes, mapChildNode);
			tryUnlock();
			if (autoFlush) flush(filePath, true);
		}
	}

	/**
	 * method that add child element with child
	 * @param nameElement
	 * nome elemento da aggiungere
	 * @param idElement
	 * id da dare all'elemento
	 * @param mapChildNode
	 * mappa elementi figli del nuovo elemento, da impostare con &lt;nome_elemento, test_interno_tag&gt;
	 * @throws JSXLockException 
	 */
	/* metodo che aggiunge elementi con child, setta attributo id e scrive su Document */
	public void addElementWithChild(String nameElement, String idElement, HashMap<String, String> mapChildNode) throws JSXLockException {
		if (tryLock()) {
			XML_WRITER.addElementWithChild(nameElement, idElement, mapChildNode);
			tryUnlock();
			if (autoFlush) flush(filePath, true);
		}
	}
	
	/**
	 * method that add element
	 * @param nameElement
	 * nome elemento da aggiungere
	 * @throws JSXLockException 
	 */
	/* metodo che aggiunge un elemento al firstchild con mappa attributi su pathFileName */
	public void addElement(String nameElement) throws JSXLockException {
		if (tryLock()) {
			XML_WRITER.addElement(nameElement);
			tryUnlock();
			if (autoFlush) flush(filePath, true);
		}
	}
	
	/**
	 * method that add element eith text
	 * @param nameElement
	 * nome elemento da aggiungere
	 * @param textContent
	 * testo da inserire all'interno del nuovo elemento
	 * @throws JSXLockException 
	 */
	/* metodo che aggiunge un elemento al firstchild con attributo id su pathFileName */
	public void addElementText(String nameElement, String textContent) throws JSXLockException {
		if (tryLock()) {
			XML_WRITER.addElementText(nameElement, textContent);
			tryUnlock();
			if (autoFlush) flush(filePath, true);
		}
	}
	

	/**
	 * method that add child element
	 * @param parentNode
	 * nodo in cui inserire il nuovo elemento
	 * @param nameElement
	 * nome elemento da aggiungere
	 * @param textContent
	 * testo da inserire all'interno del nuovo elemento
	 * @throws JSXLockException 
	 */
	/* metodo che aggiunge un elemento figlio al node con mappe attributi */
	public void addChildElement(Node parentNode, String nameElement, String textContext) throws JSXLockException {
		if (tryLock()) {
			XML_WRITER.addChildElement(parentNode, nameElement, textContext);
			tryUnlock();
			if (autoFlush) flush(filePath, true);
		}
	}

	/**
	 * method that append element with child on node
	 * @param node parent
	 * @param nameElement
	 * @param mapChildNode element child
	 * @throws JSXLockException
	 */
	public void appendElementWithChild(Node node, String nameElement, HashMap<String, String> mapChildNode) throws JSXLockException {
		if (tryLock()) {
			XML_WRITER.appendElementWithChild(node, nameElement, mapChildNode);
			tryUnlock();
			if (autoFlush) flush(filePath, true);
		}
	}

	/**
	 * method that delete a node
	 * @param nameElement
	 * nome elemento da eliminare
	 * @param id
	 * id elemento da eliminare
	 * @throws JSXLockException 
	 */
	public void deleteNode(String nameElement, String id) throws JSXLockException {
		if (tryLock()) {
			XML_WRITER.deleteNode(nameElement, id);
			tryUnlock();
			if (autoFlush) flush(filePath, true);
		}
	}

	/**
	 * method that delete a node
	 * @param node
	 * oggetto nodo da eliminare
	 * @throws JSXLockException 
	 */
	public void deleteNode(Node node) throws JSXLockException {
		if (tryLock()) {
			XML_WRITER.deleteNode(node);
			tryUnlock();
			if (autoFlush) flush(filePath, true);
		}
	}

	/**
	 * method that remove blank lines on document
	 * metodo che elimina le righe bianche dal document
	 * @throws JSXLockException 
	 */
	public void removeBlankLines() throws JSXLockException {
		if (tryLock()) {
			try {
				removeBlankLines(document);
				if (autoFlush) flush(filePath, true);
			} catch (JSXException e) {
				e.printStackTrace();
			} finally {
				tryUnlock();
			}
		}
	}

	/* ################################################################################# */
	/* END DOCUMENT WRITER METHODS */
	/* ################################################################################# */

	/* ################################################################################# */
	/* START DOCUMENT READER METHODS */
	/* ################################################################################# */
	
	/**
	 * method that get array of elements
	 * @param nameElements
	 * nome elementi da inserire nella lista
	 * @return ArrayList&lt;Node&gt;
	 * lista di nodi
	 * @throws JSXLockException 
	 */
	/* metodo che ritorna lista di elementi con filtro nome */
	public ArrayList<Node> getArrayElements(String nameElements) throws JSXLockException {
		ArrayList<Node> nodeList = null;
		if (tryLock()) {
			XML_READER.getArrayElements(nameElements);
			tryUnlock();
		}
		return nodeList;
	}

	/**
	 * method that add map id to element
	 * @param nameElements
	 * nome elementi da inserire nella lista
	 * @return HashMap&lt;String, Node&gt;
	 * mappa con &lt;id, nodo&gt;
	 * @throws JSXLockException 
	 * 
	 */
	public HashMap<String, Node> getMapIdElement(String nameElements) throws JSXLockException {
		HashMap<String, Node> nodeMap = null;
		if (tryLock()) {
			nodeMap = XML_READER.getMapIdElement(nameElements);
			tryUnlock();
		}
		return nodeMap;
	}

	/**
	 * method that get element by id
	 * @param nameElements
	 * nome elementi da cercare
	 * @param idElement
	 * id elemento da cercare
	 * @return Node
	 * primo nodo trovate con nome e id passati in ingresso
	 * @throws JSXLockException 
	 */
	/* metodo che ritorna lista di elementi con filtro nome e id */
	public Node getElementById(String nameElement, String idElement) throws JSXLockException {
		Node node = null;
		if (tryLock()) {
			node = XML_READER.getElementById(nameElement, idElement);
			tryUnlock();
		}
		return node;
	}

	/**
	 * method that get array of child node
	 * @param parentNode
	 * nodo genitore
	 * @return ArrayList&lt;Node&gt;
	 * lista con tutti gli elementi figli di parentNode
	 * @throws JSXLockException 
	 */
	/* metodo che ritorna lista di elementi figli */
	public ArrayList<Node> getArrayChildNode(Node parentNode) throws JSXLockException {
		ArrayList<Node> nodeList = null;
		if (tryLock()) {
			nodeList = XML_READER.getArrayChildNode(parentNode);
			tryUnlock();
		}
		return nodeList;
	}

	/**
	 * method that get array of child node
	 * @param parentNode
	 * nodo genitore
	 * @param nameElements
	 * nome elementi da inserire nella lista
	 * @return ArrayList&lt;Node&gt;
	 * lista con elementi figli che corrispondono al nome
	 * @throws JSXLockException 
	 */
	/* metodo che ritorna lista di elementi figli con filtro nome */
	public ArrayList<Node> getArrayChildNode(Node parentNode, String nameElements) throws JSXLockException {
		ArrayList<Node> nodeList = null;
		if (tryLock()) {
			nodeList = XML_READER.getArrayChildNode(parentNode, nameElements);
			tryUnlock();
		}
		return nodeList;
	}

	/**
	 * method that get child node by id
	 * @param parentNode
	 * nodo genitore
	 * @param nameElements
	 * nome elementi da inserire nella lista
	 * @param idElement
	 * id dell'elemento da ritornare
	 * @return Node
	 * primo nodo figlio trovato con nome e id passati
	 * @throws JSXLockException 
	 */
	/* metodo che ritorna un elemento figlo con filtro nome e id */
	public Node getChildNodeById(Node parentNode, String nameElements, String idElement) throws JSXLockException {
		Node node = null;
		if (tryLock()) {
			node = XML_READER.getChildNodeById(parentNode, nameElements, idElement);
			tryUnlock();
		}
		return node;
	}

	/**
	 * method that get map id to child node
	 * @param parentNode
	 * nodo genitore
	 * @param nameElements
	 * nome elementi da inserire nella lista
	 * @return HashMap&lt;String, Node&gt;
	 * mappa con elementi figli &lt;id, nodo&gt;
	 * @throws JSXLockException 
	 */
	/* metodo che ritorna mappa di id con nodi child con key id */
	public HashMap<String, Node> getMapIdChildNode(Node parentNode, String nameElement) throws JSXLockException {
		HashMap<String, Node> map = null;
		if (tryLock()) {
			map = XML_READER.getMapIdChildNode(parentNode, nameElement);
			tryUnlock();
		}
		return map;
	}

	/* ################################################################################# */
	/* END READER DOCUMENT METHODS */
	/* ################################################################################# */
	
	/* ################################################################################# */
	/* START LOCK METHODS */
	/* ################################################################################# */
	
	/**
	 * method that check if is set lock and try to lock a document
	 * @return true if lock is disabled or successfully lock a file, false otherwise  
	 * @throws JSXLockException
	 */
	public boolean tryLock() throws JSXLockException {
		if (lock) {
			try {
				if (!REENTRANT_LOCK.tryLock(30, TimeUnit.SECONDS)) throw new JSXLockException("Error Timeout Reentrant Lock");
			} catch (InterruptedException e) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * method that check if lock is set and unlock a document 
	 */
	public void tryUnlock() {
		if (lock) REENTRANT_LOCK.unlock();
	}
	
	/* ################################################################################# */
	/* END LOCK METHODS */
	/* ################################################################################# */
	
	/**
	 * method that save changes on XML file
	 * @param filePath
	 * path in cui salvare l'xml
	 * @throws JSXLockException 
	 */
	/* salva modifiche nel file xml */
	public void flush(String pathFile, boolean reloadDocument) throws JSXLockException {
		if (tryLock()) {
			try {
				TransformerFactory transformerFactory = TransformerFactory.newInstance();
				Transformer transformer = transformerFactory.newTransformer();
				if (indentAmount > 0) {
					removeBlankLines();
					transformer.setOutputProperty(OutputKeys.INDENT, "yes");
					transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", String.valueOf(indentAmount));
				}
				DOMSource source = new DOMSource(document);
				StreamResult result = new StreamResult(new File(pathFile));
				transformer.transform(source, result);
				if (reloadDocument) loadDocument();
			} catch (TransformerException e) {
				e.printStackTrace();
			} finally {
				tryUnlock();
			}
		}
	}

	/**
	 * method that load the Document
	 * @throws JSXLockException 
	 */
	public void loadDocument() throws JSXLockException {
		if (tryLock()) {
			try {
				document = createDocument(filePath, validating, namespaceAware, featValidation, featNamespaces, featLoadDTDGramm, featLoadExtDTD);
				XML_WRITER.setDocument(document);
				XML_READER.setDocument(document);
			} catch (XMLException e) {
				e.printStackTrace();
			} finally {
				tryUnlock();
			}
		}
	}
}
