package br.eti.rslemos.uima;

import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;

public class MyAnnotator extends JCasAnnotator_ImplBase {

	public void process(JCas cas) throws AnalysisEngineProcessException {
		Word word = new Word(cas);
		word.setBegin(5);
		word.setEnd(20);
		word.setLemma("this should be the lemma");
	}

}
