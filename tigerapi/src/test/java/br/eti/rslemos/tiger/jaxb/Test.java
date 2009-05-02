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
			JAXBContext headContext = JAXBContext.newInstance(Head.class);
			Unmarshaller headUnmarshaller = headContext.createUnmarshaller();

			JAXBElement<Head> jaxbhead = headUnmarshaller.unmarshal(xmlstream, Head.class);
			Head head = jaxbhead.getValue();

			Meta meta = head.meta;
			System.out.printf("METADATA\n" +
					"name = %s\n" +
					"author = %s\n" +
					"description = %s\n" +
					"date = %s\n" +
					"history = %s\n" +
					"format = %s\n\n",
					meta.name, meta.author, meta.description,
					meta.date, meta.history, meta.format);


			Annotation annotation = head.annotation;
			System.out.printf("ANNOTATION:\n");
			for (Feature feature : annotation.features) {
				System.out.printf("FEATURE\n" +
						"name = %s\n" +
						"domain = %s\n\n",
						feature.name,
						feature.domain);
			}
		} else
			System.out.printf("No head\n");

		// skip body element
		event = (StartElement) xmlelementstream.nextEvent();

		if (event != null && "body".equals(event.getName().getLocalPart())) {
			JAXBContext sentenceContext = JAXBContext.newInstance(Sentence.class);
			Unmarshaller sentenceUnmarshaller = sentenceContext.createUnmarshaller();

			while(xmlelementstream.peek() != null) {
				JAXBElement<Sentence> jaxbsentence = sentenceUnmarshaller.unmarshal(xmlstream, Sentence.class);
				Sentence sentence = jaxbsentence.getValue();
				System.out.printf("sentence id = %s\n", sentence.id);
			}
		}

	}

}
