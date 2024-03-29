package jsx;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Class for read a XML file
 * @author Andrea Serra
 *
 */
public class ReaderXML {
	private Document document;

	/* CONSTRUCTOR */
	protected ReaderXML() {
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
	 * @param doc
	 * @param nameElements
	 * @return
	 */
	/* metodo che ritorna lista di elementi con filtro nome */
	protected List<Node> getNodeList(String nameElements) {
		ArrayList<Node> listElements = new ArrayList<Node>();
		NodeList nodeList = document.getElementsByTagName(nameElements);
		for (int i = 0, length = nodeList.getLength(); i < length; i++) listElements.add(nodeList.item(i));
		return listElements;
	}

	/**
	 * 
	 * @param document
	 * @param nameElement
	 * @param idElement
	 * @return
	 */
	/* metodo che ritorna lista di elementi con filtro nome e id */
	protected Node getNodeById(String nameElement, String idElement) {
		List<Node> listElements = getNodeList(nameElement);
		for (Node node : listElements) if (node.getNodeName().equals(nameElement) && node.getAttributes().getNamedItem("id").getTextContent().equals(idElement)) return node;
		return null;
	}

	/**
	 * 
	 * @param document
	 * @param nameElements
	 * @return
	 */
	/* metodo che ritorna mappa di nodi con key id */
	protected Map<String, Node> getIdNodeMap(String nameElements) {
		HashMap<String, Node> mapElement = new HashMap<String, Node>();
		List<Node> listElement = getNodeList(nameElements);
		for (Node node : listElement)mapElement.put(node.getAttributes().getNamedItem("id").getTextContent(), node);
		return mapElement;
	}

	/**
	 * 
	 * @param parentNode
	 * @return
	 */
	/* metodo che ritorna lista di elementi figli */
	protected List<Node> getChildNodeList(Node parentNode) {
		ArrayList<Node> listElements = new ArrayList<Node>();
		NodeList nodeList = parentNode.getChildNodes();
		for (int i = 0, length = nodeList.getLength(); i < length; i++) if (!nodeList.item(i).getNodeName().equals("#text")) listElements.add(nodeList.item(i));
		return listElements;
	}

	/**
	 * 
	 * @param parentNode
	 * @param nameNode
	 * @return
	 */
	/* metodo che ritorna lista di elementi figli con filtro nome */
	protected List<Node> getChildNodeList(Node parentNode, String nameNode) {
		ArrayList<Node> listNode = new ArrayList<Node>();
		List<Node> nodeList = getChildNodeList(parentNode);
		for (Node node : nodeList) if (node.getNodeName().equals(nameNode)) listNode.add(node);
		return listNode;
	}

	/**
	 * 
	 * @param parentNode
	 * @param nameNode
	 * @param idElement
	 * @return
	 */
	/* metodo che ritorna un elemento figlo con filtro nome e id */
	protected Node getChildNodeById(Node parentNode, String nameNode, String idElement) {
		List<Node> nodelist = getChildNodeList(parentNode);
		for (Node node : nodelist) if (node.getNodeName().equals(nameNode) && node.getAttributes().getNamedItem("id").getTextContent().equals(idElement)) return node;
		return null;
	}

	/**
	 * 
	 * @param parentNode
	 * @param nameElement
	 * @return
	 */
	/* metodo che ritorna mappa di id con nodi child con key id */
	protected Map<String, Node> getIdChildNodeMap(Node parentNode, String nameElement) {
		HashMap<String, Node> mapChildNode = new HashMap<String, Node>();
		List<Node> listNode = getChildNodeList(parentNode, nameElement);
		for (Node node : listNode) mapChildNode.put(node.getAttributes().getNamedItem("id").getTextContent(), node);
		return mapChildNode;
	}
}
