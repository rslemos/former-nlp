package br.eti.rslemos.tiger.stax;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import br.eti.rslemos.tiger.stax.StAXFeatureValue;

public class StAXFeatureValueBehavior {

	public void shouldHaveWorkingGetters() {
		StAXFeatureValue featureValue = new StAXFeatureValue("name", "comment");
		assertEquals(featureValue.getName(), "name");
		assertEquals(featureValue.getComment(), "comment");
	}


	@Test
	public void shouldReturnSaneString() {
		StAXFeatureValue featureValue = new StAXFeatureValue("name", "comment");
		assertEquals(featureValue.toString(), "name");
	}

	@Test
	public void shouldBeEqual() {
		StAXFeatureValue featureValueA = new StAXFeatureValue("name", "comment");
		StAXFeatureValue featureValueB = new StAXFeatureValue("name", "comment");

		assertTrue(featureValueA.equals(featureValueB));
		assertTrue(featureValueB.equals(featureValueA));

		// identity
		assertTrue(featureValueA.equals(featureValueA));
	}

	@Test
	public void shouldNotBeEqual() {
		StAXFeatureValue featureValueA = new StAXFeatureValue("name", "comment");
		StAXFeatureValue featureValueB = new StAXFeatureValue("other-name", "comment");
		StAXFeatureValue featureValueC = new StAXFeatureValue("name", "other-comment");

		assertFalse(featureValueA.equals(featureValueB));
		assertFalse(featureValueA.equals(featureValueC));

		assertFalse(featureValueB.equals(featureValueA));
		assertFalse(featureValueB.equals(featureValueC));

		assertFalse(featureValueC.equals(featureValueA));
		assertFalse(featureValueC.equals(featureValueB));

		// other class
		assertFalse(featureValueA.equals(new Object()));
	}

	@Test
	public void shouldHashToSameCode() {
		StAXFeatureValue featureValueA = new StAXFeatureValue("name", "comment");
		StAXFeatureValue featureValueB = new StAXFeatureValue("name", "comment");

		assertTrue(featureValueA.hashCode() == featureValueB.hashCode());
	}

	@Test
	public void shouldAcceptNulls() {
		StAXFeatureValue featureValueA = new StAXFeatureValue(null, null);
		StAXFeatureValue featureValueB = new StAXFeatureValue(null, null);

		featureValueA.toString();
		featureValueA.equals(featureValueB);
		featureValueA.equals(null);
		featureValueA.hashCode();
	}
}
