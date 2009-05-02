package br.eti.rslemos.tiger.jaxb;

import java.io.InputStream;
import java.net.URL;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Unmarshaller;
import javax.xml.namespace.QName;
import javax.xml.stream.EventFilter;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

import br.eti.rslemos.tiger.jaxb.impl.HeadTypeImpl;
import br.eti.rslemos.tiger.jaxb.impl.SentenceTypeImpl;

public class Test {
	public static void main(String[] args) throws Throwable {
		InputStream stream = new URL("file:///home/rslemos/work/Bosque_CF_8.0.TigerXML.xml").openStream();

		XMLInputFactory factory = XMLInputFactory.newInstance();
		XMLEventReader xmlstream = factory.createXMLEventReader(stream);

		//StartDocument start = (StartDocument) xmlstream.nextEvent();

		XMLEventReader xmlelementstream = factory.createFilteredReader(xmlstream, new EventFilter() {
			@Override
			public boolean accept(XMLEvent event) {
				return event.isStartElement();
			}
		});

		StartElement event;

		// root
		event = (StartElement) xmlelementstream.nextEvent();
		System.out.printf("corpus id = %s\n", event.getAttributeByName(new QName("id")).getValue());

		event = (StartElement) xmlelementstream.peek();
		if ("head".equals(event.getName().getLocalPart())) {
			JAXBContext headContext = JAXBContext.newInstance(HeadTypeImpl.class);
			Unmarshaller headUnmarshaller = headContext.createUnmarshaller();

			JAXBElement<HeadTypeImpl> jaxbhead = headUnmarshaller.unmarshal(xmlstream, HeadTypeImpl.class);
			HeadType head = jaxbhead.getValue();

			MetaType meta = head.getMeta();
			System.out.printf("METADATA\n" +
					"name = %s\n" +
					"author = %s\n" +
					"description = %s\n" +
					"date = %s\n" +
					"history = %s\n" +
					"format = %s\n\n",
					meta.getName(), meta.getAuthor(), meta.getDescription(),
					meta.getDate(), meta.getHistory(), meta.getFormat());


			AnnotationType annotation = head.getAnnotation();
			System.out.printf("ANNOTATION:\n");
			for (FeatureType feature : annotation.getFeature()) {
				System.out.printf("FEATURE\n" +
						"name = %s\n" +
						"domain = %s\n\n",
						feature.getName(),
						feature.getDomain());
			}
		} else
			System.out.printf("No head\n");

		// skip body element
		event = (StartElement) xmlelementstream.nextEvent();

		if (event != null && "body".equals(event.getName().getLocalPart())) {
			JAXBContext sentenceContext = JAXBContext.newInstance(SentenceTypeImpl.class);
			Unmarshaller sentenceUnmarshaller = sentenceContext.createUnmarshaller();

			while(xmlelementstream.peek() != null) {
				JAXBElement<SentenceTypeImpl> jaxbsentence = sentenceUnmarshaller.unmarshal(xmlstream, SentenceTypeImpl.class);
				SentenceType sentence = jaxbsentence.getValue();
				System.out.printf("sentence id = %s\n", sentence.getId());
			}
		}

	}

}
