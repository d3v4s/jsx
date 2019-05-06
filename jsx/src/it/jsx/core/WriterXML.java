package it.jsx.core;

import java.util.HashMap;
import java.util.Set;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class WriterXML {
//	private XMLManagerAS xmlas;

	protected WriterXML() {
//		this.xmlas = xmlas;
	}
	
	/**
	 * 
	 * @param doc
	 * @param nameElement
	 * @param mapAttributes
	 * @param mapChildNode
	 * @param mapAttributesChild
	 */
	/* metodo che aggiunge elementi con child e mappe per attributi e scrive su Document */
	protected void addElementWithChild(Document doc, String nameElement, HashMap<String, String> mapAttributes, HashMap<String, String> mapChildNode,
										HashMap<String, HashMap<String, String>> mapAttributesChild) {
		Element el = doc.createElement(nameElement);
		Set<String> setAttr = mapAttributes.keySet();
		for (String attr : setAttr) {
			el.setAttribute(attr, mapAttributes.get(attr));
		}

		Set<String> setChild = mapChildNode.keySet();
		Element childElmnt;
		for (String childName : setChild) {
			childElmnt = doc.createElement(childName);
			Set<String> setAttrChild = mapAttributesChild.get(childName).keySet();
			for (String attr : setAttrChild) {
				childElmnt.setAttribute(attr, mapAttributesChild.get(childName).get(attr));
			}
			childElmnt.setTextContent(mapChildNode.get(childName));
			el.appendChild(childElmnt);
		}

		doc.getFirstChild().appendChild(el);
	}


	/**
	 * 
	 * @param doc
	 * @param nameElement
	 * @param idElement
	 * @param mapChildNode
	 * @param mapIdChild
	 */
	/* metodo che aggiunge elementi con child, setta attributo id e scrive su Document */
	protected void addElementWithChild(Document doc, String nameElement, String idElement, HashMap<String, String> mapChildNode,
										HashMap<String, String> mapIdChild) {
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
		addElementWithChild(doc, nameElement, mapAttributes, mapChildNode, mapAttributesChild);
	}
	
	/**
	 * 
	 * @param doc
	 * @param nameElement
	 * @param mapAttributes
	 */
	/* metodo che aggiunge un elemento al firstchild con mappa attributi su Document */
	protected void addElement(Document doc, String nameElement, HashMap<String, String> mapAttributes) {
		Element el = doc.createElement(nameElement);
		Set<String> setAttr = mapAttributes.keySet();
		for (String attr : setAttr) {
			el.setAttribute(attr, mapAttributes.get(attr));
		}
		doc.getFirstChild().appendChild(el);
	}
	
	/**
	 * 
	 * @param doc
	 * @param nameElement
	 * @param idElement
	 */
	/* metodo che aggiunge un elemento al firstchild con attributo id su Document */
	protected void addElement(Document doc, String nameElement, String idElement) {
		HashMap<String, String> mapAttributes = new HashMap<String, String>();
		mapAttributes.put("id", idElement);
		addElement(doc, nameElement, mapAttributes);
	}

	/**
	 * 
	 * @param doc
	 * @param nameElement
	 * @param mapAttributes
	 * @param mapChildNode
	 */
	/* metodo che aggiunge elementi con child e mappe per attributi e scrive su Document */
	protected void addElementWithChild(Document doc, String nameElement, HashMap<String, String> mapAttributes, HashMap<String, String> mapChildNode) {
		Element el = doc.createElement(nameElement);
		Set<String> setAttr = mapAttributes.keySet();
		for (String attr : setAttr) {
			el.setAttribute(attr, mapAttributes.get(attr));
		}

		Set<String> setChild = mapChildNode.keySet();
		Element childElmnt;
		for (String childName : setChild) {
			childElmnt = doc.createElement(childName);
			childElmnt.setTextContent(mapChildNode.get(childName));
			el.appendChild(childElmnt);
		}

		doc.getFirstChild().appendChild(el);
	}


	/**
	 * 
	 * @param doc
	 * @param nameElement
	 * @param idElement
	 * @param mapChildNode
	 */
	/* metodo che aggiunge elementi con child, setta attributo id e scrive su Document */
	protected void addElementWithChild(Document doc, String nameElement, String idElement, HashMap<String, String> mapChildNode) {
		HashMap<String, String> mapAttributes = new HashMap<String, String>();
		mapAttributes.put("id", idElement);
		addElementWithChild(doc, nameElement, mapAttributes, mapChildNode);
	}
	
	/**
	 * 
	 * @param doc
	 * @param nameElement
	 */
	/* metodo che aggiunge un elemento al firstchild con mappa attributi su Document */
	protected void addElement(Document doc, String nameElement) {
		Element el = doc.createElement(nameElement);
		doc.getFirstChild().appendChild(el);
	}

	/**
	 * 
	 * @param doc
	 * @param nameElement
	 * @param textContent
	 */
	/* metodo che aggiunge un elemento al firstchild con attributo id su Document */
	protected void addElementText(Document doc, String nameElement, String textContent) {
		Element el = doc.createElement(nameElement);
		el.setTextContent(textContent);
		doc.getFirstChild().appendChild(el);
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
		for (String attr : setAttr) {
			el.setAttribute(attr, mapAttributes.get(attr));
		}
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
	 * 
	 * @param doc
	 * @param nameElement
	 * @param id
	 */
	protected void deleteNode(Document doc, String nameElement, String id) {
		Node node = new ReaderXML().getMapIdElement(doc, nameElement).get(id);
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
