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

import java.util.Iterator;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import br.eti.rslemos.tiger.Body;

@XmlType(name = "bodyType")
public class JAXBBody implements Body {

	private List<JAXBSentence> sentences;


	@XmlElement(required = true, name = "s")
	public List<JAXBSentence> getSentences() {
		return sentences;
	}

	public void setSentences(List<JAXBSentence> sentences) {
		this.sentences = sentences;
	}

	public Iterator<JAXBSentence> sentences() {
		return getSentences().iterator();
	}
}
