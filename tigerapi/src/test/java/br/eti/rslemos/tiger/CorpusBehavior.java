package br.eti.rslemos.tiger;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.testng.annotations.Test;

@Test(groups = {"br.eti.rslemos.tiger"})
public class CorpusBehavior {

	@Test
	public void shouldReadId() throws Throwable {
		Corpus corpus = getStraightCorpus();

		assertEquals(corpus.getId(), TigerInputMother.getInputStraightTigerId());
	}

	@Test
	public void shouldReadMetadata() throws Throwable {
		Metadata expectedMetadata = new Metadata(
				"Test corpus",
				"Wolfgang Lezius",
				"April 2003",
				"illustrates the TIGER-XML format",
				"NeGra format, version 3",
				"first version"
		);

		Corpus corpus = getStraightCorpus();
		Metadata metadata = corpus.getMetadata();

		assertNotNull(metadata);
		assertEquals(metadata, expectedMetadata);
	}

	@Test
	public void shouldHaveFeatures() throws Throwable {
		Corpus corpus = getStraightCorpus();
		Map<String, Feature> features = corpus.getFeatures();

		assertNotNull(features);
		assertEquals(features.size(), 4);

		// assume que está em ordem
		Iterator<Entry<String, Feature>> it = features.entrySet().iterator();
		Entry<String, Feature> entry;

		entry = it.next();
		assertTrue(entry.getValue().getName().equals(entry.getKey()));
		assertEquals(entry.getValue().getName(), "word");
		assertEquals(entry.getValue().getDomain(), "T");

		entry = it.next();
		assertTrue(entry.getValue().getName().equals(entry.getKey()));
		assertEquals(entry.getValue().getName(), "pos");
		assertEquals(entry.getValue().getDomain(), "T");

		entry = it.next();
		assertTrue(entry.getValue().getName().equals(entry.getKey()));
		assertEquals(entry.getValue().getName(), "morph");
		assertEquals(entry.getValue().getDomain(), "T");

		entry = it.next();
		assertTrue(entry.getValue().getName().equals(entry.getKey()));
		assertEquals(entry.getValue().getName(), "cat");
		assertEquals(entry.getValue().getDomain(), "NT");

		assertFalse(it.hasNext());
	}

	@Test
	public void shouldHaveValuedFeature() throws Throwable {

		Corpus corpus = getStraightCorpus();
		Map<String, Feature> features = corpus.getFeatures();

		Feature feature = features.get("pos");
		Collection<FeatureValue> values = feature.getValues();

		assertNotNull(values);
		assertEquals(values.size(), 6);

		// assume que está em ordem
		Iterator<FeatureValue> it = values.iterator();

		assertEquals(it.next(), new FeatureValue("ART", "determiner"));
		assertEquals(it.next(), new FeatureValue("ADV", "adverb"));
		assertEquals(it.next(), new FeatureValue("KOKOM", "conjunction"));
		assertEquals(it.next(), new FeatureValue("NN", "noun"));
		assertEquals(it.next(), new FeatureValue("PIAT", "indefinite attributive pronoun"));
		assertEquals(it.next(), new FeatureValue("VVFIN", "finite verb"));

		assertFalse(it.hasNext());

	}

	@Test
	public void shouldFullyReadFeatures() throws Throwable {
		Map<String, Feature> expectedFeatures = new LinkedHashMap<String, Feature>();
		{
			// bloco para não poluir a visão de variáveis locais
			// na perspectiva de debugging
			Feature wordFeature = new Feature("word", "T");

			Feature posFeature = new Feature("pos", "T");
			posFeature.add(new FeatureValue("ART", "determiner"));
			posFeature.add(new FeatureValue("ADV", "adverb"));
			posFeature.add(new FeatureValue("KOKOM", "conjunction"));
			posFeature.add(new FeatureValue("NN", "noun"));
			posFeature.add(new FeatureValue("PIAT", "indefinite attributive pronoun"));
			posFeature.add(new FeatureValue("VVFIN", "finite verb"));

			Feature morphFeature = new Feature("morph", "T");
			morphFeature.add(new FeatureValue("Def.Fem.Nom.Sg"));
			morphFeature.add(new FeatureValue("Fem.Nom.Sg.*"));
			morphFeature.add(new FeatureValue("Masc.Akk.Pl.*"));
			morphFeature.add(new FeatureValue("3.Sg.Pres.Ind"));
			morphFeature.add(new FeatureValue("--", "not bound"));

			Feature catFeature = new Feature("cat", "NT");
			catFeature.add(new FeatureValue("AP", "adjektive phrase"));
			catFeature.add(new FeatureValue("AVP", "adverbial phrase"));
			catFeature.add(new FeatureValue("NP", "noun phrase"));
			catFeature.add(new FeatureValue("S", "sentence"));

			expectedFeatures.put(wordFeature.getName(), wordFeature);
			expectedFeatures.put(posFeature.getName(), posFeature);
			expectedFeatures.put(morphFeature.getName(), morphFeature);
			expectedFeatures.put(catFeature.getName(), catFeature);
		}

		Corpus corpus = getStraightCorpus();
		Map<String, Feature> features = corpus.getFeatures();

		assertNotNull(features);
		assertEquals(features, expectedFeatures);
	}

	public static Corpus getStraightCorpus()
	throws UnsupportedEncodingException, TigerException {
		return new Corpus(TigerInputMother.getInputStraightTiger());
	}
}
