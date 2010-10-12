package br.eti.rslemos.tiger;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.junit.Test;

@SuppressWarnings("unchecked")
public abstract class CorpusBehavior {

	public abstract Corpus getStraightCorpus()
	throws UnsupportedEncodingException, TigerException;

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

}
