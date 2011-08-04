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

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlType;

import br.eti.rslemos.tiger.Sentence;

@XmlType(name = "sentenceType", propOrder = {"graph", "matches"})
public class JAXBSentence implements Sentence {

	private String id;
	private JAXBGraph graph;
	private List<JAXBMatch> matches;


	@XmlAttribute(required = true)
	@XmlID
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@XmlElement(required = false)
	public JAXBGraph getGraph() {
		return graph;
	}

	public void setGraph(JAXBGraph graph) {
		this.graph = graph;
	}

	@XmlElementWrapper(required = false)
	@XmlElement(required = true, name = "match")
	public List<JAXBMatch> getMatches() {
		return matches;
	}

	public void setMatches(List<JAXBMatch> matches) {
		this.matches = matches;
	}


}
