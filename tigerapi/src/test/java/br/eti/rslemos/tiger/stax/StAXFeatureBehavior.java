/*******************************************************************************
 * BEGIN COPYRIGHT NOTICE
 * 
 * This file is part of program "Natural Language Processing"
 * Copyright 2011  Rodrigo Lemos
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * END COPYRIGHT NOTICE
 ******************************************************************************/
package br.eti.rslemos.tiger.stax;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import br.eti.rslemos.tiger.Domain;

public class StAXFeatureBehavior {

	public void shouldHaveWorkingGetters() {
		StAXFeature feature = new StAXFeature("name", Domain.T);
		assertEquals(feature.getName(), "name");
		assertSame(feature.getDomain(), Domain.T);
	}


	@Test
	public void shouldReturnSaneString() {
		StAXFeature feature = new StAXFeature("name", Domain.T);
		assertEquals(feature.toString(), "name(T)");
	}

	@Test
	public void shouldBeEqual() {
		StAXFeature featureA = new StAXFeature("name", Domain.T);
		StAXFeature featureB = new StAXFeature("name", Domain.T);

		assertTrue(featureA.equals(featureB));
		assertTrue(featureB.equals(featureA));

		// identity
		assertTrue(featureA.equals(featureA));
	}

	@Test
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

	@Test
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

	@Test
	public void shouldNotBeDeeplyEqual() {
		StAXFeature featureA = new StAXFeature("name", Domain.T);
		featureA.add(new StAXFeatureValue("name", "comment"));

		StAXFeature featureB = new StAXFeature("name", Domain.T);
		featureB.add(new StAXFeatureValue("name", "other-comment"));

		assertFalse(featureA.equals(featureB));
		assertFalse(featureB.equals(featureA));
	}

	@Test
	public void shouldHashToSameCode() {
		StAXFeature featureA = new StAXFeature("name", Domain.T);
		featureA.add(new StAXFeatureValue("name", "comment"));

		StAXFeature featureB = new StAXFeature("name", Domain.T);
		featureB.add(new StAXFeatureValue("name", "comment"));

		assertTrue(featureA.hashCode() == featureB.hashCode());
	}

	@Test
	public void shouldAcceptNulls() {
		StAXFeature featureA = new StAXFeature(null, null);
		StAXFeature featureB = new StAXFeature(null, null);

		featureA.toString();
		featureA.equals(featureB);
		featureA.equals(null);
		featureA.hashCode();
	}
}
