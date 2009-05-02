package br.eti.rslemos.tiger.jaxb;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlAnyAttribute;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;

@XmlType(name = "tType")
public class Terminal {

	@XmlAttribute(required = true)
	@XmlID
	public String id;

	@XmlAnyAttribute
	public Map<QName, String> features = new HashMap<QName, String>();

	@XmlElement(required = false, name = "secedge")
	public List<SecEdge> secEdges;

}
