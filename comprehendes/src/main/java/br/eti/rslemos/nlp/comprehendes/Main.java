package br.eti.rslemos.nlp.comprehendes;

import gate.Document;
import gate.Factory;
import gate.FeatureMap;
import gate.Gate;

import java.net.URL;

public class Main {

	public static void main(String[] args) throws Exception {
		Gate.init();
		
		URL u = Main.class.getResource("/CONTRATO de Compra e Venda apoio de pé.doc");
		FeatureMap params = Factory.newFeatureMap();
		params.put("sourceUrl", u);
		FeatureMap features = Factory.newFeatureMap();
		Document doc = (Document)
		  Factory.createResource("gate.corpora.DocumentImpl",
		                          params, features, "GATE Homepage");
		
		doc.getAnnotations(null).add(10L, 20L, "this.is.my.type", params);
		System.out.println(doc);
	}

}
