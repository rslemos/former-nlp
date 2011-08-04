/*******************************************************************************
 * BEGIN COPYRIGHT NOTICE
 * 
 * This file is part of program "Natural Language Processing"
 * Copyright 2011  Rodrigo Lemos
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * END COPYRIGHT NOTICE
 ******************************************************************************/
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

import br.eti.rslemos.tiger.NonTerminal;

@XmlType(name = "ntType", propOrder = {"edges", "secEdges"})
public class JAXBNonTerminal implements NonTerminal {

	private String id;
	private Map<QName, String> features = new HashMap<QName, String>();
	private List<JAXBEdge> edges;
	private List<JAXBSecEdge> secEdges;


	@XmlAttribute(required = true)
	@XmlID
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@XmlAnyAttribute
	public Map<QName, String> getFeatures() {
		return features;
	}

	public void setFeatures(Map<QName, String> features) {
		this.features = features;
	}

	@XmlElement(required = false, name = "edge")
	public List<JAXBEdge> getEdges() {
		return edges;
	}

	public void setEdges(List<JAXBEdge> edges) {
		this.edges = edges;
	}

	@XmlElement(required = false, name = "secedge")
	public List<JAXBSecEdge> getSecEdges() {
		return secEdges;
	}

	public void setSecEdges(List<JAXBSecEdge> secEdges) {
		this.secEdges = secEdges;
	}


}
