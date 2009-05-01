package br.eti.rslemos.tiger;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

@Test(groups = {"br.eti.rslemos.tiger"})
public class FeatureBehavior {

	public void shouldHaveWorkingGetters() {
		Feature feature = new Feature("name", "domain");
		assertEquals(feature.getName(), "name");
		assertEquals(feature.getDomain(), "domain");
	}


	@Test(groups = "basics.java.lang.Object")
	public void shouldReturnSaneString() {
		Feature feature = new Feature("name", "domain");
		assertEquals(feature.toString(), "name(domain)");
	}

	@Test(groups = "basics.java.lang.Object")
	public void shouldBeEqual() {
		Feature featureA = new Feature("name", "domain");
		Feature featureB = new Feature("name", "domain");

		assertTrue(featureA.equals(featureB));
		assertTrue(featureB.equals(featureA));

		// identity
		assertTrue(featureA.equals(featureA));
	}

	@Test(groups = "basics.java.lang.Object")
	public void shouldBeDeeplyEqual() {
		Feature featureA = new Feature("name", "domain");
		featureA.add(new FeatureValue("name", "comment"));

		Feature featureB = new Feature("name", "domain");
		featureB.add(new FeatureValue("name", "comment"));

		assertTrue(featureA.equals(featureB));
		assertTrue(featureB.equals(featureA));

		// identity
		assertTrue(featureA.equals(featureA));
	}

	@Test(groups = "basics.java.lang.Object")
	public void shouldNotBeEqual() {
		Feature featureA = new Feature("name", "domain");
		Feature featureB = new Feature("other-name", "domain");
		Feature featureC = new Feature("name", "other-domain");

		assertFalse(featureA.equals(featureB));
		assertFalse(featureA.equals(featureC));

		assertFalse(featureB.equals(featureA));
		assertFalse(featureB.equals(featureC));

		assertFalse(featureC.equals(featureA));
		assertFalse(featureC.equals(featureB));

		// other class
		assertFalse(featureA.equals(new Object()));
	}

	@Test(groups = "basics.java.lang.Object")
	public void shouldNotBeDeeplyEqual() {
		Feature featureA = new Feature("name", "domain");
		featureA.add(new FeatureValue("name", "comment"));

		Feature featureB = new Feature("name", "domain");
		featureB.add(new FeatureValue("name", "other-comment"));

		assertFalse(featureA.equals(featureB));
		assertFalse(featureB.equals(featureA));
	}

	@Test(groups = "basics.java.lang.Object")
	public void shouldHashToSameCode() {
		Feature featureA = new Feature("name", "domain");
		featureA.add(new FeatureValue("name", "comment"));

		Feature featureB = new Feature("name", "domain");
		featureB.add(new FeatureValue("name", "comment"));

		assertTrue(featureA.hashCode() == featureB.hashCode());
	}

	@Test(groups = "basics.java.lang.Object")
	public void shouldAcceptNulls() {
		Feature featureA = new Feature(null, null);
		Feature featureB = new Feature(null, null);

		featureA.toString();
		featureA.equals(featureB);
		featureA.equals(null);
		featureA.hashCode();
	}
}
