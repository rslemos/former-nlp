package br.eti.rslemos.tiger.stax;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import br.eti.rslemos.tiger.stax.Metadata;

@Test(groups = {"br.eti.rslemos.tiger"})
public class MetadataBehavior {

	private static final Metadata METADATA_A = new Metadata(
			"Test corpus",
			"Wolfgang Lezius",
			"April 2003",
			"illustrates the TIGER-XML format",
			"NeGra format, version 3",
			"first version"
	);

	private static final Metadata METADATA_B = new Metadata(
			"Test corpus",
			"Wolfgang Lezius",
			"April 2003",
			"illustrates the TIGER-XML format",
			"NeGra format, version 3",
			"first version"
	);

	private static final Metadata METADATA_C = new Metadata(
			"X- Test corpus",
			"X- Wolfgang Lezius",
			"X- April 2003",
			"X- illustrates the TIGER-XML format",
			"X- NeGra format, version 3",
			"X- first version"
	);

	private static final Metadata METADATA_NULL_A = new Metadata(
			null,
			null,
			null,
			null,
			null,
			null
	);

	private static final Metadata METADATA_NULL_B = new Metadata(
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

	@Test(groups = "basics.java.lang.Object")
	public void shouldReturnSaneString() {
		assertEquals(METADATA_A.toString(),
		"{name=Test corpus, author=Wolfgang Lezius, date=April 2003, description=illustrates the TIGER-XML format, format=NeGra format, version 3, history=first version");
	}

	@Test(groups = "basics.java.lang.Object")
	public void shouldBeEqual() {
		assertTrue(METADATA_A.equals(METADATA_B));
		assertTrue(METADATA_B.equals(METADATA_A));

		// identity
		assertTrue(METADATA_A.equals(METADATA_A));
	}

	@Test(groups = "basics.java.lang.Object")
	public void shouldNotBeEqual() {
		assertFalse(METADATA_A.equals(METADATA_C));
		assertFalse(METADATA_C.equals(METADATA_A));

		// other class
		assertFalse(METADATA_A.equals(new Object()));
	}

	@Test(groups = "basics.java.lang.Object")
	public void shouldHashToSameCode() {
		assertTrue(METADATA_A.hashCode() == METADATA_B.hashCode());
	}

	@Test(groups = "basics.java.lang.Object")
	public void shouldAcceptNulls() {
		METADATA_NULL_A.toString();
		METADATA_NULL_A.equals(METADATA_NULL_B);
		METADATA_NULL_A.equals(null);
		METADATA_NULL_A.hashCode();
	}
}
