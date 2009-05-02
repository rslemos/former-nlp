package br.eti.rslemos.tiger.stax;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertSame;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import br.eti.rslemos.tiger.Domain;

@Test(groups = {"br.eti.rslemos.tiger"})
public class StAXFeatureBehavior {

	public void shouldHaveWorkingGetters() {
		StAXFeature feature = new StAXFeature("name", Domain.T);
		assertEquals(feature.getName(), "name");
		assertSame(feature.getDomain(), Domain.T);
	}


	@Test(groups = "basics.java.lang.Object")
	public void shouldReturnSaneString() {
		StAXFeature feature = new StAXFeature("name", Domain.T);
		assertEquals(feature.toString(), "name(T)");
	}

	@Test(groups = "basics.java.lang.Object")
	public void shouldBeEqual() {
		StAXFeature featureA = new StAXFeature("name", Domain.T);
		StAXFeature featureB = new StAXFeature("name", Domain.T);

		assertTrue(featureA.equals(featureB));
		assertTrue(featureB.equals(featureA));

		// identity
		assertTrue(featureA.equals(featureA));
	}

	@Test(groups = "basics.java.lang.Object")
	public void shouldBeDeeplyEqual() {
		StAXFeature featureA = new StAXFeature("name", Domain.T);
		featureA.add(new StAXFeatureValue("name", "comment"));

		StAXFeature featureB = new StAXFeature("name", Domain.T);
		featureB.add(new StAXFeatureValue("name", "comment"));

		assertTrue(featureA.equals(featureB));
		assertTrue(featureB.equals(featureA));

		// identity
		assertTrue(featureA.equals(featureA));
	}

	@Test(groups = "basics.java.lang.Object")
	public void shouldNotBeEqual() {
		StAXFeature featureA = new StAXFeature("name", Domain.T);
		StAXFeature featureB = new StAXFeature("other-name", Domain.T);
		StAXFeature featureC = new StAXFeature("name", Domain.NT);

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
		StAXFeature featureA = new StAXFeature("name", Domain.T);
		featureA.add(new StAXFeatureValue("name", "comment"));

		StAXFeature featureB = new StAXFeature("name", Domain.T);
		featureB.add(new StAXFeatureValue("name", "other-comment"));

		assertFalse(featureA.equals(featureB));
		assertFalse(featureB.equals(featureA));
	}

	@Test(groups = "basics.java.lang.Object")
	public void shouldHashToSameCode() {
		StAXFeature featureA = new StAXFeature("name", Domain.T);
		featureA.add(new StAXFeatureValue("name", "comment"));

		StAXFeature featureB = new StAXFeature("name", Domain.T);
		featureB.add(new StAXFeatureValue("name", "comment"));

		assertTrue(featureA.hashCode() == featureB.hashCode());
	}

	@Test(groups = "basics.java.lang.Object")
	public void shouldAcceptNulls() {
		StAXFeature featureA = new StAXFeature(null, null);
		StAXFeature featureB = new StAXFeature(null, null);

		featureA.toString();
		featureA.equals(featureB);
		featureA.equals(null);
		featureA.hashCode();
	}
}
