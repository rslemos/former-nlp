package br.eti.rslemos.tiger.stax;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertSame;
import static org.testng.Assert.assertTrue;

import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.testng.annotations.Test;

import br.eti.rslemos.tiger.Domain;
import br.eti.rslemos.tiger.TigerException;
import br.eti.rslemos.tiger.TigerInputMother;

@Test(groups = {"br.eti.rslemos.tiger"})
public class StAXCorpusBehavior {

	@Test
	public void shouldReadId() throws Throwable {
		StAXCorpus corpus = getStraightCorpus();

		assertEquals(corpus.getId(), TigerInputMother.getInputStraightTigerId());
	}

	@Test
	public void shouldReadMetadata() throws Throwable {
		StAXMetadata expectedMetadata = new StAXMetadata(
				"Test corpus",
				"Wolfgang Lezius",
				"April 2003",
				"illustrates the TIGER-XML format",
				"NeGra format, version 3",
				"first version"
		);

		StAXCorpus corpus = getStraightCorpus();
		StAXMetadata metadata = corpus.getMetadata();

		assertNotNull(metadata);
		assertEquals(metadata, expectedMetadata);
	}

	@Test
	public void shouldHaveFeatures() throws Throwable {
		StAXCorpus corpus = getStraightCorpus();
		Map<String, StAXFeature> features = corpus.getFeatures();

		assertNotNull(features);
		assertEquals(features.size(), 4);

		// assume que está em ordem
		Iterator<Entry<String, StAXFeature>> it = features.entrySet().iterator();
		Entry<String, StAXFeature> entry;

		entry = it.next();
		assertTrue(entry.getValue().getName().equals(entry.getKey()));
		assertEquals(entry.getValue().getName(), "word");
		assertSame(entry.getValue().getDomain(), Domain.T);

		entry = it.next();
		assertTrue(entry.getValue().getName().equals(entry.getKey()));
		assertEquals(entry.getValue().getName(), "pos");
		assertSame(entry.getValue().getDomain(), Domain.T);

		entry = it.next();
		assertTrue(entry.getValue().getName().equals(entry.getKey()));
		assertEquals(entry.getValue().getName(), "morph");
		assertSame(entry.getValue().getDomain(), Domain.T);

		entry = it.next();
		assertTrue(entry.getValue().getName().equals(entry.getKey()));
		assertEquals(entry.getValue().getName(), "cat");
		assertSame(entry.getValue().getDomain(), Domain.NT);

		assertFalse(it.hasNext());
	}

	@Test
	public void shouldHaveValuedFeature() throws Throwable {

		StAXCorpus corpus = getStraightCorpus();
		Map<String, StAXFeature> features = corpus.getFeatures();

		StAXFeature feature = features.get("pos");
		Collection<StAXFeatureValue> values = feature.getValues();

		assertNotNull(values);
		assertEquals(values.size(), 6);

		// assume que está em ordem
		Iterator<StAXFeatureValue> it = values.iterator();

		assertEquals(it.next(), new StAXFeatureValue("ART", "determiner"));
		assertEquals(it.next(), new StAXFeatureValue("ADV", "adverb"));
		assertEquals(it.next(), new StAXFeatureValue("KOKOM", "conjunction"));
		assertEquals(it.next(), new StAXFeatureValue("NN", "noun"));
		assertEquals(it.next(), new StAXFeatureValue("PIAT", "indefinite attributive pronoun"));
		assertEquals(it.next(), new StAXFeatureValue("VVFIN", "finite verb"));

		assertFalse(it.hasNext());

	}

	@Test
	public void shouldFullyReadFeatures() throws Throwable {
		Map<String, StAXFeature> expectedFeatures = new LinkedHashMap<String, StAXFeature>();
		{
			// bloco para não poluir a visão de variáveis locais
			// na perspectiva de debugging
			StAXFeature wordFeature = new StAXFeature("word", Domain.T);

			StAXFeature posFeature = new StAXFeature("pos", Domain.T);
			posFeature.add(new StAXFeatureValue("ART", "determiner"));
			posFeature.add(new StAXFeatureValue("ADV", "adverb"));
			posFeature.add(new StAXFeatureValue("KOKOM", "conjunction"));
			posFeature.add(new StAXFeatureValue("NN", "noun"));
			posFeature.add(new StAXFeatureValue("PIAT", "indefinite attributive pronoun"));
			posFeature.add(new StAXFeatureValue("VVFIN", "finite verb"));

			StAXFeature morphFeature = new StAXFeature("morph", Domain.T);
			morphFeature.add(new StAXFeatureValue("Def.Fem.Nom.Sg"));
			morphFeature.add(new StAXFeatureValue("Fem.Nom.Sg.*"));
			morphFeature.add(new StAXFeatureValue("Masc.Akk.Pl.*"));
			morphFeature.add(new StAXFeatureValue("3.Sg.Pres.Ind"));
			morphFeature.add(new StAXFeatureValue("--", "not bound"));

			StAXFeature catFeature = new StAXFeature("cat", Domain.NT);
			catFeature.add(new StAXFeatureValue("AP", "adjektive phrase"));
			catFeature.add(new StAXFeatureValue("AVP", "adverbial phrase"));
			catFeature.add(new StAXFeatureValue("NP", "noun phrase"));
			catFeature.add(new StAXFeatureValue("S", "sentence"));

			expectedFeatures.put(wordFeature.getName(), wordFeature);
			expectedFeatures.put(posFeature.getName(), posFeature);
			expectedFeatures.put(morphFeature.getName(), morphFeature);
			expectedFeatures.put(catFeature.getName(), catFeature);
		}

		StAXCorpus corpus = getStraightCorpus();
		Map<String, StAXFeature> features = corpus.getFeatures();

		assertNotNull(features);
		assertEquals(features, expectedFeatures);
	}

	public static StAXCorpus getStraightCorpus()
	throws UnsupportedEncodingException, TigerException {
		return new StAXCorpus(TigerInputMother.getInputStraightTiger());
	}
}
