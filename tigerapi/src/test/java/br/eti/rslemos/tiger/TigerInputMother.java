package br.eti.rslemos.tiger;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertSame;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.List;

public class TigerInputMother {

	public static InputStream getInputStraightTiger()
	throws UnsupportedEncodingException {
		return TigerInputMother.class.getResourceAsStream("/inputStraightTiger.xml");
	}

	public static void testStraightCorpusAttributes(Corpus corpus) {
		assertEquals(corpus.getId(), "TESTCORPUS");
	}

	public static void testStraightCorpusMeta(Meta meta) {
		assertNotNull(meta);
		assertEquals(meta.getName(), "Test corpus");
		assertEquals(meta.getAuthor(), "Wolfgang Lezius");
		assertEquals(meta.getDate(), "April 2003");
		assertEquals(meta.getDescription(), "illustrates the TIGER-XML format");
		assertEquals(meta.getFormat(), "NeGra format, version 3");
		assertEquals(meta.getHistory(), "first version");
	}

	public static void testStraightCorpusFeaturesAvailability(List<Feature> features) {
		assertNotNull(features);
		assertEquals(features.size(), 4);

		// assume que está em ordem
		Iterator<Feature> it = features.iterator();
		Feature entry;

		entry = it.next();
		assertFeatureAttributesEqual(entry, "word", Domain.T);

		entry = it.next();
		assertFeatureAttributesEqual(entry, "pos", Domain.T);

		entry = it.next();
		assertFeatureAttributesEqual(entry, "morph", Domain.T);

		entry = it.next();
		assertFeatureAttributesEqual(entry, "cat", Domain.NT);

		assertFalse(it.hasNext());
	}

	public static void testStraightCorpusValuedFeature(List<Feature> features) {
		Feature feature = getFeatureByName(features, "pos");

		assertFeatureValuesEqual(feature,
				new String[] {"ART", "determiner"},
				new String[] {"ADV", "adverb"},
				new String[] {"KOKOM", "conjunction"},
				new String[] {"NN", "noun"},
				new String[] {"PIAT", "indefinite attributive pronoun"},
				new String[] {"VVFIN", "finite verb"}
		);
	}

	private static Feature getFeatureByName(List<Feature> features, String name) {
		for (Feature f : features)
			if (name.equals(f.getName()))
				return f;

		return null;
	}

	public static void assertFeatureAttributesEqual(Feature feature, String expectedName, Domain expectedDomain) {
		assertEquals(feature.getName(), expectedName);
		assertSame(feature.getDomain(), expectedDomain);
	}

	public static void assertFeatureValuesEqual(Feature feature, String[]... expected) {
		assertEquals(feature.getValues().size(), expected.length);

		int i = 0;
		for (FeatureValue val : feature.getValues()) {
			assertEquals(val.getName(), expected[i][0]);
			assertEquals(val.getValue(), expected[i][1]);
			i++;
		}
	}

	public static void assertFeatureEquals(Feature feature, String expectedName, Domain expectedDomain, String[]... expectedValues) {
		assertFeatureAttributesEqual(feature, expectedName, expectedDomain);
		assertFeatureValuesEqual(feature, expectedValues);
	}

	public static void testStraightCorpusFeatures(List<Feature> features) {
		assertNotNull(features);

		Iterator<Feature> it = features.iterator();
		Feature feature;

		feature = it.next();
		assertFeatureEquals(feature, "word", Domain.T);

		feature = it.next();
		assertFeatureEquals(feature, "pos", Domain.T,
				new String[] {"ART", "determiner"},
				new String[] {"ADV", "adverb"},
				new String[] {"KOKOM", "conjunction"},
				new String[] {"NN", "noun"},
				new String[] {"PIAT", "indefinite attributive pronoun"},
				new String[] {"VVFIN", "finite verb"}
		);

		feature = it.next();
		assertFeatureEquals(feature, "morph", Domain.T,
				new String[] {"Def.Fem.Nom.Sg", ""},
				new String[] {"Fem.Nom.Sg.*", ""},
				new String[] {"Masc.Akk.Pl.*", ""},
				new String[] {"3.Sg.Pres.Ind", ""},
				new String[] {"--", "not bound"}
		);

		feature = it.next();
		assertFeatureEquals(feature, "cat", Domain.NT,
				new String[] {"AP", "adjektive phrase"},
				new String[] {"AVP", "adverbial phrase"},
				new String[] {"NP", "noun phrase"},
				new String[] {"S", "sentence"}
		);

		assertFalse(it.hasNext());
	}

}