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

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import br.eti.rslemos.tiger.Annotation;

@XmlType(name = "annotationType", propOrder = {"features", "edgeLabel", "secEdgeLabel"})
public class JAXBAnnotation implements Annotation {

	private List<JAXBFeature> features;
	private JAXBEdgeLabel edgeLabel;
	private JAXBEdgeLabel secEdgeLabel;


	@XmlElement(required = true, name = "feature")
	public List<JAXBFeature> getFeatures() {
		return features;
	}

	public void setFeatures(List<JAXBFeature> features) {
		this.features = features;
	}

	@XmlElement(required = false, name = "edgelabel")
	public JAXBEdgeLabel getEdgeLabel() {
		return edgeLabel;
	}

	public void setEdgeLabel(JAXBEdgeLabel edgeLabel) {
		this.edgeLabel = edgeLabel;
	}

	@XmlElement(required = false, name = "secedgelabel")
	public JAXBEdgeLabel getSecEdgeLabel() {
		return secEdgeLabel;
	}

	public void setSecEdgeLabel(JAXBEdgeLabel secEdgeLabel) {
		this.secEdgeLabel = secEdgeLabel;
	}


}
