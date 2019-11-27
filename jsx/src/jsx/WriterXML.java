package jsx;

import java.util.HashMap;
import java.util.Set;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * Class for write a XML file
 * @author Andrea Serra
 *
 */
public class WriterXML {
	private Document document;

	/* CONSTRUCTOR */
	protected WriterXML() {
	}

	/* ################################################################################# */
	/* START GET AND SET */
	/* ################################################################################# */

	protected Document getDocument() {
		return document;
	}
	protected void setDocument(Document document) {
		this.document = document;
	}

	/* ################################################################################# */
	/* END GET AND SET */
	/* ################################################################################# */

	/**
	 * 
	 * @param document
	 * @param nameElement
	 * @param mapAttributes
	 * @param mapChildNode
	 * @param mapAttributesChild
	 */
	/* metodo che aggiunge elementi con child e mappe per attributi e scrive su Document */
	protected void addElementWithChild(String nameElement, HashMap<String, String> mapAttributes, HashMap<String, String> mapChildNode, HashMap<String, HashMap<String, String>> mapAttributesChild) {
		Element el = document.createElement(nameElement);
		Set<String> setAttr = mapAttributes.keySet();
		for (String attr : setAttr) el.setAttribute(attr, mapAttributes.get(attr));

		Set<String> setChild = mapChildNode.keySet();
		Set<String> setAttrChild;
		Element childElmnt;
		for (String childName : setChild) {
			childElmnt = document.createElement(childName);
			setAttrChild = mapAttributesChild.get(childName).keySet();
			for (String attr : setAttrChild) childElmnt.setAttribute(attr, mapAttributesChild.get(childName).get(attr));
			childElmnt.setTextContent(mapChildNode.get(childName));
			el.appendChild(childElmnt);
		}

		document.getFirstChild().appendChild(el);
	}


	/**
	 * 
	 * @param document
	 * @param nameElement
	 * @param idElement
	 * @param mapChildNode
	 * @param mapIdChild
	 */
	/* metodo che aggiunge elementi con child, setta attributo id e scrive su Document */
	protected void addElementWithChild(String nameElement, String idElement, HashMap<String, String> mapChildNode, HashMap<String, String> mapIdChild) {
		HashMap<String, String> mapAttributes = new HashMap<String, String>();
		mapAttributes.put("id", idElement);
		Set<String> setKChldNd = mapChildNode.keySet();
		HashMap<String, HashMap<String, String>> mapAttributesChild = new HashMap<String, HashMap<String,String>>();
		HashMap<String, String> mapId;
		for (String kChldNd : setKChldNd) {
			mapId = new HashMap<String, String>();
			mapId.put("id", mapIdChild.get(kChldNd));
			mapAttributesChild.put(kChldNd, mapId);
		}
		addElementWithChild(nameElement, mapAttributes, mapChildNode, mapAttributesChild);
	}
	
	/**
	 * 
	 * @param document
	 * @param nameElement
	 * @param mapAttributes
	 */
	/* metodo che aggiunge un elemento al firstchild con mappa attributi su Document */
	protected void addElement(String nameElement, HashMap<String, String> mapAttributes) {
		Element el = document.createElement(nameElement);
		Set<String> setAttr = mapAttributes.keySet();
		for (String attr : setAttr) el.setAttribute(attr, mapAttributes.get(attr));
		document.getFirstChild().appendChild(el);
	}
	
	/**
	 * 
	 * @param document
	 * @param nameElement
	 * @param idElement
	 */
	/* metodo che aggiunge un elemento al firstchild con attributo id su Document */
	protected void addElement(String nameElement, String idElement) {
		HashMap<String, String> mapAttributes = new HashMap<String, String>();
		mapAttributes.put("id", idElement);
		addElement(nameElement, mapAttributes);
	}

	/**
	 * 
	 * @param nameElement
	 * @param mapAttributes
	 * @param mapChildNode
	 */
	/* metodo che aggiunge elementi con child e mappe per attributi e scrive su Document */
	protected void addElementWithChild(String nameElement, HashMap<String, String> mapAttributes, HashMap<String, String> mapChildNode) {
		Element el = document.createElement(nameElement);
		Set<String> setAttr = mapAttributes.keySet();
		for (String attr : setAttr) el.setAttribute(attr, mapAttributes.get(attr));

		Set<String> setChild = mapChildNode.keySet();
		Element childElmnt;
		for (String childName : setChild) {
			childElmnt = document.createElement(childName);
			childElmnt.setTextContent(mapChildNode.get(childName));
			el.appendChild(childElmnt);
		}

		document.getFirstChild().appendChild(el);
	}

	/**
	 * 
	 * @param document
	 * @param nameElement
	 * @param idElement
	 * @param mapChildNode
	 */
	/* metodo che aggiunge elementi con child, setta attributo id e scrive su Document */
	protected void addElementWithChild(String nameElement, String idElement, HashMap<String, String> mapChildNode) {
		HashMap<String, String> mapAttributes = new HashMap<String, String>();
		mapAttributes.put("id", idElement);
		addElementWithChild(nameElement, mapAttributes, mapChildNode);
	}
	
	/**
	 * 
	 * @param document
	 * @param nameElement
	 */
	/* metodo che aggiunge un elemento al firstchild con mappa attributi su Document */
	protected void addElement(String nameElement) {
		Element el = document.createElement(nameElement);
		document.getFirstChild().appendChild(el);
	}

	/**
	 * 
	 * @param document
	 * @param nameElement
	 * @param textContent
	 */
	/* metodo che aggiunge un elemento al firstchild con attributo id su Document */
	protected void addElementText(String nameElement, String textContent) {
		Element el = document.createElement(nameElement);
		el.setTextContent(textContent);
		document.getFirstChild().appendChild(el);
	}

	/**
	 * 
	 * @param parentNode
	 * @param nameElement
	 * @param mapAttributes
	 * @param textContext
	 */
	/* metodo che aggiunge un elemento figlio al node con mappe attributi */
	protected void addChildElement(Node parentNode, String nameElement, HashMap<String, String> mapAttributes, String textContext) {
		Element el = parentNode.getOwnerDocument().createElement(nameElement);
		Set<String> setAttr = mapAttributes.keySet();
		for (String attr : setAttr) el.setAttribute(attr, mapAttributes.get(attr));
		el.setTextContent(textContext);
		parentNode.appendChild(el);
	}

	/**
	 * 
	 * @param parentNode
	 * @param nameElement
	 * @param idElement
	 * @param textContext
	 */
	/* metodo che aggiunge un elemento figlio al node con id */
	protected void addChildElement(Node parentNode, String nameElement, String idElement, String textContext) {
		HashMap<String, String> mapAttributes = new HashMap<String, String>();
		mapAttributes.put("id", idElement);
		addChildElement(parentNode, nameElement, mapAttributes, textContext);
	}

	/**
	 * 
	 * @param parentNode
	 * @param nameElement
	 * @param textContext
	 */
	/* metodo che aggiunge un elemento figlio al node con mappe attributi */
	protected void addChildElement(Node parentNode, String nameElement, String textContext) {
		Element el = parentNode.getOwnerDocument().createElement(nameElement);
		el.setTextContent(textContext);
		parentNode.appendChild(el);
	}

	/**
	 * method that append a element with child on node
	 * @param node
	 * @param nameElement
	 * @param mapChildNode
	 */
	protected void appendElementWithChild(Node node, String nameElement, HashMap<String, String> mapChildNode) {
		Element el = document.createElement(nameElement);

		Set<String> setChild = mapChildNode.keySet();
		Element childElmnt;
		for (String childName : setChild) {
			childElmnt = document.createElement(childName);
			childElmnt.setTextContent(mapChildNode.get(childName));
			el.appendChild(childElmnt);
		}

		node.appendChild(el);
	}

	/**
	 * 
	 * @param document
	 * @param nameElement
	 * @param id
	 */
	protected void deleteNode(String nameElement, String id) {
		ReaderXML readerXML = new ReaderXML();
		readerXML.setDocument(document);
		Node node = readerXML.getMapIdElement(nameElement).get(id);
		node.getParentNode().removeChild(node);
	}

	/**
	 * 
	 * @param node
	 */
	protected void deleteNode(Node node) {
		node.getParentNode().removeChild(node);
	}
}
