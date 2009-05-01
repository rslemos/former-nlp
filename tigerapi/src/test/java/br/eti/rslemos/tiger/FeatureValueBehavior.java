package br.eti.rslemos.tiger;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

@Test(groups = {"br.eti.rslemos.tiger"})
public class FeatureValueBehavior {

	public void shouldHaveWorkingGetters() {
		FeatureValue featureValue = new FeatureValue("name", "comment");
		assertEquals(featureValue.getName(), "name");
		assertEquals(featureValue.getComment(), "comment");
	}


	@Test(groups = "basics.java.lang.Object")
	public void shouldReturnSaneString() {
		FeatureValue featureValue = new FeatureValue("name", "comment");
		assertEquals(featureValue.toString(), "name");
	}

	@Test(groups = "basics.java.lang.Object")
	public void shouldBeEqual() {
		FeatureValue featureValueA = new FeatureValue("name", "comment");
		FeatureValue featureValueB = new FeatureValue("name", "comment");

		assertTrue(featureValueA.equals(featureValueB));
		assertTrue(featureValueB.equals(featureValueA));

		// identity
		assertTrue(featureValueA.equals(featureValueA));
	}

	@Test(groups = "basics.java.lang.Object")
	public void shouldNotBeEqual() {
		FeatureValue featureValueA = new FeatureValue("name", "comment");
		FeatureValue featureValueB = new FeatureValue("other-name", "comment");
		FeatureValue featureValueC = new FeatureValue("name", "other-comment");

		assertFalse(featureValueA.equals(featureValueB));
		assertFalse(featureValueA.equals(featureValueC));

		assertFalse(featureValueB.equals(featureValueA));
		assertFalse(featureValueB.equals(featureValueC));

		assertFalse(featureValueC.equals(featureValueA));
		assertFalse(featureValueC.equals(featureValueB));

		// other class
		assertFalse(featureValueA.equals(new Object()));
	}

	@Test(groups = "basics.java.lang.Object")
	public void shouldHashToSameCode() {
		FeatureValue featureValueA = new FeatureValue("name", "comment");
		FeatureValue featureValueB = new FeatureValue("name", "comment");

		assertTrue(featureValueA.hashCode() == featureValueB.hashCode());
	}

	@Test(groups = "basics.java.lang.Object")
	public void shouldAcceptNulls() {
		FeatureValue featureValueA = new FeatureValue(null, null);
		FeatureValue featureValueB = new FeatureValue(null, null);

		featureValueA.toString();
		featureValueA.equals(featureValueB);
		featureValueA.equals(null);
		featureValueA.hashCode();
	}
}
