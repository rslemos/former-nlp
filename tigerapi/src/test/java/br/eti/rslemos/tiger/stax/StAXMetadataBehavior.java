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
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import br.eti.rslemos.tiger.stax.StAXMetadata;

public class StAXMetadataBehavior {

	private static final StAXMetadata METADATA_A = new StAXMetadata(
			"Test corpus",
			"Wolfgang Lezius",
			"April 2003",
			"illustrates the TIGER-XML format",
			"NeGra format, version 3",
			"first version"
	);

	private static final StAXMetadata METADATA_B = new StAXMetadata(
			"Test corpus",
			"Wolfgang Lezius",
			"April 2003",
			"illustrates the TIGER-XML format",
			"NeGra format, version 3",
			"first version"
	);

	private static final StAXMetadata METADATA_C = new StAXMetadata(
			"X- Test corpus",
			"X- Wolfgang Lezius",
			"X- April 2003",
			"X- illustrates the TIGER-XML format",
			"X- NeGra format, version 3",
			"X- first version"
	);

	private static final StAXMetadata METADATA_NULL_A = new StAXMetadata(
			null,
			null,
			null,
			null,
			null,
			null
	);

	private static final StAXMetadata METADATA_NULL_B = new StAXMetadata(
			null,
			null,
			null,
			null,
			null,
			null
	);

	public void shouldHaveWorkingGetters() {
		assertEquals(METADATA_A.getName(), "Test corpus");
		assertEquals(METADATA_A.getAuthor(), "Wolfgang Lezius");
		assertEquals(METADATA_A.getDate(), "April 2003");
		assertEquals(METADATA_A.getDescription(), "illustrates the TIGER-XML format");
		assertEquals(METADATA_A.getFormat(), "NeGra format, version 3");
		assertEquals(METADATA_A.getHistory(), "first version");
	}

	@Test
	public void shouldReturnSaneString() {
		assertEquals(METADATA_A.toString(),
		"{name=Test corpus, author=Wolfgang Lezius, date=April 2003, description=illustrates the TIGER-XML format, format=NeGra format, version 3, history=first version");
	}

	@Test
	public void shouldBeEqual() {
		assertTrue(METADATA_A.equals(METADATA_B));
		assertTrue(METADATA_B.equals(METADATA_A));

		// identity
		assertTrue(METADATA_A.equals(METADATA_A));
	}

	@Test
	public void shouldNotBeEqual() {
		assertFalse(METADATA_A.equals(METADATA_C));
		assertFalse(METADATA_C.equals(METADATA_A));

		// other class
		assertFalse(METADATA_A.equals(new Object()));
	}

	@Test
	public void shouldHashToSameCode() {
		assertTrue(METADATA_A.hashCode() == METADATA_B.hashCode());
	}

	@Test
	public void shouldAcceptNulls() {
		METADATA_NULL_A.toString();
		METADATA_NULL_A.equals(METADATA_NULL_B);
		METADATA_NULL_A.equals(null);
		METADATA_NULL_A.hashCode();
	}
}
