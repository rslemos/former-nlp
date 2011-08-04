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

import java.net.URI;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import br.eti.rslemos.tiger.Head;

@XmlType(name = "headType", propOrder = {"meta", "annotation"})
public class JAXBHead implements Head {

	private URI external;
	private JAXBMeta meta;
	private JAXBAnnotation annotation;


	@XmlAttribute(required = false)
	public URI getExternal() {
		return external;
	}

	public void setExternal(URI external) {
		this.external = external;
	}

	@XmlElement(required = false)
	public JAXBMeta getMeta() {
		return meta;
	}

	public void setMeta(JAXBMeta meta) {
		this.meta = meta;
	}

	@XmlElement(required = false)
	public JAXBAnnotation getAnnotation() {
		return annotation;
	}

	public void setAnnotation(JAXBAnnotation annotation) {
		this.annotation = annotation;
	}
}
