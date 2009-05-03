package br.eti.rslemos.tiger.jaxb;

import java.io.InputStream;
import java.util.Iterator;
import java.util.NoSuchElementException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.namespace.QName;
import javax.xml.stream.EventFilter;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

import br.eti.rslemos.tiger.Body;
import br.eti.rslemos.tiger.Corpus;
import br.eti.rslemos.tiger.Head;
import br.eti.rslemos.tiger.Sentence;
import br.eti.rslemos.tiger.TigerException;

public class FastCorpus implements Corpus {
	private static final Unmarshaller headUnmarshaller;
	private static final Unmarshaller sentenceUnmarshaller;

	static {
		try {
			JAXBContext headContext = JAXBContext.newInstance(JAXBHead.class);
			headUnmarshaller = headContext.createUnmarshaller();
			JAXBContext sentenceContext = JAXBContext.newInstance(JAXBSentence.class);
			sentenceUnmarshaller = sentenceContext.createUnmarshaller();
		} catch (JAXBException e) {
			throw (LinkageError)new LinkageError("cinit").initCause(e);
		}
	}

	private final String id;
	private final String version;

	private final Head head;
	private final Body body;

	public FastCorpus(InputStream input) throws TigerException {
		try {
			XMLInputFactory factory = XMLInputFactory.newInstance();

			final XMLEventReader xmlstream = factory.createXMLEventReader(input);

			//StartDocument start = (StartDocument) xmlstream.nextEvent();

			final XMLEventReader xmlelementstream = factory.createFilteredReader(xmlstream, new EventFilter() {
				@Override
				public boolean accept(XMLEvent event) {
					return event.isStartElement();
				}
			});

			StartElement event;

			// root
			event = (StartElement) xmlelementstream.nextEvent();
			id = event.getAttributeByName(new QName("id")).getValue();

			Attribute versionAttribute = event.getAttributeByName(new QName("version"));
			version = versionAttribute != null ? versionAttribute.getValue() : null;

			event = (StartElement) xmlelementstream.peek();
			if ("head".equals(event.getName().getLocalPart())) {
				JAXBElement<JAXBHead> jaxbhead = headUnmarshaller.unmarshal(xmlstream, JAXBHead.class);
				head = jaxbhead.getValue();
			} else
				head = null;

			// skip body element
			event = (StartElement) xmlelementstream.nextEvent();

			if (event != null && "body".equals(event.getName().getLocalPart()))
				body = new Body() {

				private final Iterator<JAXBSentence> it = new Iterator<JAXBSentence>() {

					@Override
					public boolean hasNext() {
						try {
							return xmlelementstream.peek() != null;
						} catch (XMLStreamException e) {
							throw new RuntimeException(e);
						}
					}

					@Override
					public JAXBSentence next() {
						if (hasNext()) {
							try {
								JAXBElement<JAXBSentence> jaxbsentence = sentenceUnmarshaller.unmarshal(xmlstream, JAXBSentence.class);
								return jaxbsentence.getValue();
							} catch (JAXBException e) {
								throw new RuntimeException(e);
							}
						} else
							throw new NoSuchElementException();
					}

					@Override
					public void remove() {
						throw new UnsupportedOperationException();
					}
				};

				@Override
				public Iterator<? extends Sentence> sentences() {
					return it;
				}

			};
			else
				body = null;
		} catch (XMLStreamException e) {
			throw new TigerException("Error", e);
		} catch (JAXBException e) {
			throw new TigerException("Error", e);
		}
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public String getVersion() {
		return version;
	}

	@Override
	public Head getHead() {
		return head;
	}

	@Override
	public Body getBody() {
		return body;
	}

}
