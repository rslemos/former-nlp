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
package br.eti.rslemos.tiger.stax;

import static javax.xml.stream.XMLStreamConstants.END_ELEMENT;
import static javax.xml.stream.XMLStreamConstants.START_ELEMENT;

import java.io.InputStream;
import java.net.URI;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import br.eti.rslemos.tiger.Annotation;
import br.eti.rslemos.tiger.Body;
import br.eti.rslemos.tiger.Corpus;
import br.eti.rslemos.tiger.Domain;
import br.eti.rslemos.tiger.EdgeLabel;
import br.eti.rslemos.tiger.Feature;
import br.eti.rslemos.tiger.Head;
import br.eti.rslemos.tiger.Meta;
import br.eti.rslemos.tiger.TigerException;

public class StAXCorpus implements Corpus {
	private static final String CORPUS_ELEMENT = "corpus";
	private static final String CORPUS_HEAD_ELEMENT = "head";
	private static final String CORPUS_HEAD_META_ELEMENT = "meta";

	private static final String CORPUS_HEAD_META_NAME_ELEMENT = "name";
	private static final String CORPUS_HEAD_META_AUTHOR_ELEMENT = "author";
	private static final String CORPUS_HEAD_META_DATE_ELEMENT = "date";
	private static final String CORPUS_HEAD_META_DESCRIPTION_ELEMENT = "description";
	private static final String CORPUS_HEAD_META_FORMAT_ELEMENT = "format";
	private static final String CORPUS_HEAD_META_HISTORY_ELEMENT = "history";

	private static final String CORPUS_HEAD_ANNOTATION_ELEMENT = "annotation";
	private static final String CORPUS_HEAD_ANNOTATION_FEATURE_ELEMENT = "feature";
	private static final String CORPUS_HEAD_ANNOTATION_FEATURE_VALUE_ELEMENT = "value";

	private final XMLStreamReader xmlstream;

	private final String id;
	private final StAXMetadata metadata;
	private final LinkedHashMap<String, StAXFeature> features = new LinkedHashMap<String, StAXFeature>();

	public StAXCorpus(InputStream input) throws TigerException {
		XMLInputFactory factory = javax.xml.stream.XMLInputFactory.newInstance();
		try {
			xmlstream = factory.createXMLStreamReader(input);
		} catch (XMLStreamException e) {
			throw new TigerException("Unparseable XML", e);
		}

		// this first part could well be done with DOM (would actually be easier)
		// stax is only really needed on the second part, which can be huge

		try {
			// get id

			xmlstream.nextTag();
			xmlstream.require(START_ELEMENT, null, CORPUS_ELEMENT);
			id = xmlstream.getAttributeValue(null, "id");

			xmlstream.nextTag();
			xmlstream.require(START_ELEMENT, null, CORPUS_HEAD_ELEMENT);

			// get metadata
			metadata = new StAXMetadata();

			xmlstream.nextTag();
			xmlstream.require(START_ELEMENT, null, CORPUS_HEAD_META_ELEMENT);

			metadata.setName(readTextElement(CORPUS_HEAD_META_NAME_ELEMENT));
			metadata.setAuthor(readTextElement(CORPUS_HEAD_META_AUTHOR_ELEMENT));
			metadata.setDate(readTextElement(CORPUS_HEAD_META_DATE_ELEMENT));
			metadata.setDescription(readTextElement(CORPUS_HEAD_META_DESCRIPTION_ELEMENT));
			metadata.setFormat(readTextElement(CORPUS_HEAD_META_FORMAT_ELEMENT));
			metadata.setHistory(readTextElement(CORPUS_HEAD_META_HISTORY_ELEMENT));

			xmlstream.nextTag();
			xmlstream.require(END_ELEMENT, null, CORPUS_HEAD_META_ELEMENT);

			xmlstream.nextTag();
			xmlstream.require(START_ELEMENT, null, CORPUS_HEAD_ANNOTATION_ELEMENT);

			while(xmlstream.nextTag() != END_ELEMENT) {
				try {
					xmlstream.require(START_ELEMENT, null, CORPUS_HEAD_ANNOTATION_FEATURE_ELEMENT);
				} catch (XMLStreamException e) {
					break;
				}

				String featureName = xmlstream.getAttributeValue(null, "name");
				String featureDomain = xmlstream.getAttributeValue(null, "domain");

				StAXFeature feature = new StAXFeature(featureName, Domain.valueOf(featureDomain));

				while(xmlstream.nextTag() != END_ELEMENT) {
					xmlstream.require(START_ELEMENT, null, CORPUS_HEAD_ANNOTATION_FEATURE_VALUE_ELEMENT);
					String name = xmlstream.getAttributeValue(null, "name");
					String comment = xmlstream.getElementText();

					feature.add(new StAXFeatureValue(name, comment));
				}

				addFeature(feature);
			}

		} catch (XMLStreamException e) {
			throw new TigerException("Unparseable XML", e);
		}
	}

	private String readTextElement(String elementName) throws XMLStreamException {
		xmlstream.nextTag();
		xmlstream.require(START_ELEMENT, null, elementName);
		return xmlstream.getElementText();
	}

	private void addFeature(StAXFeature feature) {
		features.put(feature.getName(), feature);
	}

	public String getId() {
		return id;
	}

	public Body getBody() {
		return null;
	}

	public Head getHead() {
		return new Head() {

			public Annotation getAnnotation() {
				return new Annotation() {

					public EdgeLabel getEdgeLabel() {
						return null;
					}

					public List<? extends Feature> getFeatures() {
						return new LinkedList<Feature>(features.values());
					}

					public EdgeLabel getSecEdgeLabel() {
						return null;
					}

				};
			}

			public URI getExternal() {
				return null;
			}

			public Meta getMeta() {
				return metadata;
			}

		};
	}

	public String getVersion() {
		return null;
	}


}
