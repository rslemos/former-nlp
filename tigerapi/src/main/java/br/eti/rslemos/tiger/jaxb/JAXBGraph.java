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
import javax.xml.bind.annotation.XmlType;

import br.eti.rslemos.tiger.Graph;

@XmlType(name = "graphType", propOrder = {"terminals", "nonTerminals"})
public class JAXBGraph implements Graph {

	private String root;
	private Boolean discontinuous;
	private List<JAXBTerminal> terminals;
	private List<JAXBNonTerminal> nonTerminals;


	@XmlAttribute(required = true)
	public String getRoot() {
		return root;
	}

	public void setRoot(String root) {
		this.root = root;
	}

	@XmlAttribute
	public Boolean getDiscontinuous() {
		return discontinuous;
	}

	public void setDiscontinuous(Boolean discontinuous) {
		this.discontinuous = discontinuous;
	}

	@XmlElementWrapper(name = "terminals", required = true)
	@XmlElement(name = "t", required = true)
	public List<JAXBTerminal> getTerminals() {
		return terminals;
	}

	public void setTerminals(List<JAXBTerminal> terminals) {
		this.terminals = terminals;
	}

	@XmlElementWrapper(name = "nonterminals", required = true)
	@XmlElement(name = "nt", required = false)
	public List<JAXBNonTerminal> getNonTerminals() {
		return nonTerminals;
	}

	public void setNonTerminals(List<JAXBNonTerminal> nonTerminals) {
		this.nonTerminals = nonTerminals;
	}


}
