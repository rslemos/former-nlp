package br.eti.rslemos.tiger.stax;


import java.io.UnsupportedEncodingException;
import java.util.List;

import org.testng.annotations.Test;

import br.eti.rslemos.tiger.Corpus;
import br.eti.rslemos.tiger.Feature;
import br.eti.rslemos.tiger.Meta;
import br.eti.rslemos.tiger.TigerException;
import br.eti.rslemos.tiger.TigerInputMother;

@Test(groups = {"br.eti.rslemos.tiger"})
public class StAXCorpusBehavior {

	@Test
	public void shouldReadId() throws Throwable {
		Corpus corpus = getStraightCorpus();

		TigerInputMother.testStraightCorpusAttributes(corpus);
	}

	@Test
	public void shouldReadMetadata() throws Throwable {
		Corpus corpus = getStraightCorpus();
		Meta meta = corpus.getHead().getMeta();

		TigerInputMother.testStraightCorpusMeta(meta);
	}

	@Test
	public void shouldHaveFeatures() throws Throwable {
		Corpus corpus = getStraightCorpus();
		List<Feature> features = (List<Feature>) corpus.getHead().getAnnotation().getFeatures();

		TigerInputMother.testStraightCorpusFeaturesAvailability(features);
	}

	@Test
	public void shouldHaveValuedFeature() throws Throwable {
		Corpus corpus = getStraightCorpus();
		List<Feature> features = (List<Feature>) corpus.getHead().getAnnotation().getFeatures();

		TigerInputMother.testStraightCorpusValuedFeature(features);
	}

	@Test
	public void shouldFullyReadFeatures() throws Throwable {
		Corpus corpus = getStraightCorpus();
		List<Feature> features = (List<Feature>) corpus.getHead().getAnnotation().getFeatures();

		TigerInputMother.testStraightCorpusFeatures(features);
	}

	public Corpus getStraightCorpus()
	throws UnsupportedEncodingException, TigerException {
		return new StAXCorpus(TigerInputMother.getInputStraightTiger());
	}
}
