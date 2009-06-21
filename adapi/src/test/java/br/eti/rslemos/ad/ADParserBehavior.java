package br.eti.rslemos.ad;

import static org.testng.Assert.*;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Iterator;

import org.testng.annotations.Test;

public class ADParserBehavior {

	@Test
	public void shouldParseExt1000() {
		Iterator<Extract> extracts = getExtracts("ext_1000.ad");
		checkExtract1000(extracts);
	}
	
	@Test
	public void shouldParseExt1000Title() {
		Iterator<Extract> extracts = getExtracts("ext_1000.ad");
		Extract e_1000 = getNextExtract(extracts);
		checkExtract1000Title(e_1000);
	}

	@Test
	public void shouldParseExt1000TitleSentence1() {
		Iterator<Extract> extracts = getExtracts("ext_1000.ad");
		Extract e_1000 = getNextExtract(extracts);
		Title e_1000_t = getTitle(e_1000);
		Iterator<Sentence> e_1000_t_sentences = getSentences(e_1000_t);
		checkExtract1000TitleSentence1(e_1000_t_sentences);
	}

	@Test
	public void shouldParseExt1000TitleSentence1AnalysisA1() {
		Iterator<Extract> extracts = getExtracts("ext_1000.ad");
		Extract e_1000 = getNextExtract(extracts);
		Title e_1000_t = getTitle(e_1000);
		Iterator<Sentence> e_1000_t_sentences = getSentences(e_1000_t);
		Sentence e_1000_t_s1 = getNextSentence(e_1000_t_sentences);
		Iterator<Analysis> e_1000_t_s1_analyses = getAnalyses(e_1000_t_s1);
		checkExtract1000TitleSentence1AnalysisA1(e_1000_t_s1_analyses);
	}

	@Test
	public void shouldParseExt1000TitleSentence1AnalysisA1RootNode() {
		Iterator<Extract> extracts = getExtracts("ext_1000.ad");
		Extract e_1000 = getNextExtract(extracts);
		Title e_1000_t = getTitle(e_1000);
		Iterator<Sentence> e_1000_t_sentences = getSentences(e_1000_t);
		Sentence e_1000_t_s1 = getNextSentence(e_1000_t_sentences);
		Iterator<Analysis> e_1000_t_s1_analyses = getAnalyses(e_1000_t_s1);
		Analysis e_1000_t_s1_A1 = getNextAnalysis(e_1000_t_s1_analyses);		
		checkExtract1000TitleSentence1AnalysisA1RootNode(e_1000_t_s1_A1);
	}

	@Test
	public void shouldParseExt1000TitleSentence1AnalysisA1RootNodeInfo() {
		Iterator<Extract> extracts = getExtracts("ext_1000.ad");
		Extract e_1000 = getNextExtract(extracts);
		Title e_1000_t = getTitle(e_1000);
		Iterator<Sentence> e_1000_t_sentences = getSentences(e_1000_t);
		Sentence e_1000_t_s1 = getNextSentence(e_1000_t_sentences);
		Iterator<Analysis> e_1000_t_s1_analyses = getAnalyses(e_1000_t_s1);
		Analysis e_1000_t_s1_A1 = getNextAnalysis(e_1000_t_s1_analyses);
		TerminalNode e_1000_t_s1_A1_root_t = (TerminalNode) getRootNode(e_1000_t_s1_A1);
		checkExtract1000TitleSentence1AnalysisA1RootNodeInfo(e_1000_t_s1_A1_root_t);
	}

	@Test
	public void shouldParseExt1000Paragraph1() {
		Iterator<Extract> extracts = getExtracts("ext_1000.ad");
		Extract e_1000 = getNextExtract(extracts);
		Iterator<Paragraph> e_1000_p = getParagraphs(e_1000);
		checkExtract1000Paragraph1(e_1000_p);
	}
	
	@Test
	public void shouldParseExt1000Paragraph1Sentence1() {
		Iterator<Extract> extracts = getExtracts("ext_1000.ad");
		Extract e_1000 = getNextExtract(extracts);
		Iterator<Paragraph> e_1000_p = getParagraphs(e_1000);
		Paragraph e_1000_p1 = getNextParagraph(e_1000_p);
		Iterator<Sentence> e_1000_p1_sentences = getSentences(e_1000_p1);		
		checkExtract1000Paragraph1Sentence1(e_1000_p1_sentences);
	}
		
	@Test
	public void shouldParseExt1000Paragraph1Sentence1AnalysisA1() {
		Iterator<Extract> extracts = getExtracts("ext_1000.ad");
		Extract e_1000 = getNextExtract(extracts);
		Iterator<Paragraph> e_1000_p = getParagraphs(e_1000);
		Paragraph e_1000_p1 = getNextParagraph(e_1000_p);
		Iterator<Sentence> e_1000_p1_sentences = getSentences(e_1000_p1);
		Sentence e_1000_p1_s2 = getNextSentence(e_1000_p1_sentences);
		Iterator<Analysis> e_1000_p1_s2_analyses = getAnalyses(e_1000_p1_s2);
		checkExtract1000Paragraph1Sentence1AnalysisA1(e_1000_p1_s2_analyses);
	}
	
	@Test
	public void shouldParseExt1000Paragraph1Sentence1AnalysisA1RootNode() {
		Iterator<Extract> extracts = getExtracts("ext_1000.ad");
		Extract e_1000 = getNextExtract(extracts);
		Iterator<Paragraph> e_1000_p = getParagraphs(e_1000);
		Paragraph e_1000_p1 = getNextParagraph(e_1000_p);
		Iterator<Sentence> e_1000_p1_sentences = getSentences(e_1000_p1);
		Sentence e_1000_p1_s2 = getNextSentence(e_1000_p1_sentences);
		Iterator<Analysis> e_1000_p1_s2_analyses = getAnalyses(e_1000_p1_s2);
		Analysis e_1000_p1_s2_A1 = getNextAnalysis(e_1000_p1_s2_analyses);
		checkExtract1000Paragraph1Sentence1AnalysisA1RootNode(e_1000_p1_s2_A1);
	}
		
	@Test
	public void shouldParseExt1000Paragraph1Sentence1AnalysisA1RootNodeInfo() {
		Iterator<Extract> extracts = getExtracts("ext_1000.ad");
		Extract e_1000 = getNextExtract(extracts);
		Iterator<Paragraph> e_1000_p = getParagraphs(e_1000);
		Paragraph e_1000_p1 = getNextParagraph(e_1000_p);
		Iterator<Sentence> e_1000_p1_sentences = getSentences(e_1000_p1);
		Sentence e_1000_p1_s2 = getNextSentence(e_1000_p1_sentences);
		Iterator<Analysis> e_1000_p1_s2_analyses = getAnalyses(e_1000_p1_s2);
		Analysis e_1000_p1_s2_A1 = getNextAnalysis(e_1000_p1_s2_analyses);
		NonTerminalNode e_1000_p1_s2_A1_root_nt = (NonTerminalNode) getRootNode(e_1000_p1_s2_A1);
		checkExtract1000Paragraph1Sentence1AnalysisA1RootNodeInfo(e_1000_p1_s2_A1_root_nt);
	}
	
	@Test
	public void shouldFullyParseExt1000() {
		Iterator<Extract> extracts = getExtracts("ext_1000.ad");
		Extract e_1000 = checkExtract1000(extracts);

		Title e_1000_t = checkExtract1000Title(e_1000);
		Iterator<Sentence> e_1000_t_sentences = getSentences(e_1000_t);
		Sentence e_1000_t_s1 = checkExtract1000TitleSentence1(e_1000_t_sentences);
		Iterator<Analysis> e_1000_t_s1_analyses = getAnalyses(e_1000_t_s1);
		Analysis e_1000_t_s1_A1 = checkExtract1000TitleSentence1AnalysisA1(e_1000_t_s1_analyses);
		TerminalNode e_1000_t_s1_A1_root_t = checkExtract1000TitleSentence1AnalysisA1RootNode(e_1000_t_s1_A1);
		checkExtract1000TitleSentence1AnalysisA1RootNodeInfo(e_1000_t_s1_A1_root_t);
		
		assertEquals(e_1000_t_s1_analyses.hasNext(), false);
		assertEquals(e_1000_t_sentences.hasNext(), false);
		
		Iterator<Paragraph> e_1000_p = getParagraphs(e_1000);
		Paragraph e_1000_p1 = checkExtract1000Paragraph1(e_1000_p);
		Iterator<Sentence> e_1000_p1_sentences = getSentences(e_1000_p1);		
		Sentence e_1000_p1_s2 = checkExtract1000Paragraph1Sentence1(e_1000_p1_sentences);
		Iterator<Analysis> e_1000_p1_s2_analyses = getAnalyses(e_1000_p1_s2);
		Analysis e_1000_p1_s2_A1 = checkExtract1000Paragraph1Sentence1AnalysisA1(e_1000_p1_s2_analyses);
		NonTerminalNode e_1000_p1_s2_A1_root_nt = checkExtract1000Paragraph1Sentence1AnalysisA1RootNode(e_1000_p1_s2_A1);
		checkExtract1000Paragraph1Sentence1AnalysisA1RootNodeInfo(e_1000_p1_s2_A1_root_nt);

		// .... continua apartir da linha 17
	}

	private Info checkExtract1000Paragraph1Sentence1AnalysisA1RootNodeInfo(NonTerminalNode e_1000_p1_s2_A1_root) {
		Info e_1000_p1_s2_A1_root_info = e_1000_p1_s2_A1_root.getInfo();
		assertNull(e_1000_p1_s2_A1_root_info);
		
		return e_1000_p1_s2_A1_root_info;
	}

	private NonTerminalNode checkExtract1000Paragraph1Sentence1AnalysisA1RootNode(Analysis e_1000_p1_s2_A1) {
		// STA:fcl
		Node e_1000_p1_s2_A1_root = getRootNode(e_1000_p1_s2_A1);
		assertNotNull(e_1000_p1_s2_A1_root);

		assertEquals(e_1000_p1_s2_A1_root.getFunction(), "STA");
		assertEquals(e_1000_p1_s2_A1_root.getForm(), "fcl");
		
		assertTrue(e_1000_p1_s2_A1_root instanceof NonTerminalNode);

		NonTerminalNode e_1000_p1_s2_A1_root_nt = (NonTerminalNode) e_1000_p1_s2_A1_root;
		
		return e_1000_p1_s2_A1_root_nt;
	}

	private Analysis checkExtract1000Paragraph1Sentence1AnalysisA1(Iterator<Analysis> e_1000_p1_s2_analyses) {
		// A1
		Analysis e_1000_p1_s2_A1 = getNextAnalysis(e_1000_p1_s2_analyses);
		assertNotNull(e_1000_p1_s2_A1);
		
		assertEquals(e_1000_p1_s2_A1.getIndex(), 1);
		
		return e_1000_p1_s2_A1;
	}

	private Sentence checkExtract1000Paragraph1Sentence1(Iterator<Sentence> e_1000_p1_sentences) {
		// <s id="2" ref="CF1000-2" source="CETENFolha id=1000 cad=Esporte sec=des sem=94a">
		// SOURCE: ref="CF1000-2" source="CETENFolha id=1000 cad=Esporte sec=des sem=94a"
		// CF1000-2 As seleções do Brasil, Cuba, Coréia do Sul, Angola, Argentina e Egito, eliminadas na primeira fase, participarão do torneio de consolação, em Hamilton, que definirá as colocações do nono ao 16o.

		Sentence e_1000_p1_s2 = getNextSentence(e_1000_p1_sentences);
		assertNotNull(e_1000_p1_s2);

		assertEquals(e_1000_p1_s2.getId(), "2");
		assertEquals(e_1000_p1_s2.getRef(), "CF1000-2");
		assertEquals(e_1000_p1_s2.getSource(), "CETENFolha id=1000 cad=Esporte sec=des sem=94a");
		assertEquals(e_1000_p1_s2.getText(), "As seleções do Brasil, Cuba, Coréia do Sul, Angola, Argentina e Egito, eliminadas na primeira fase, participarão do torneio de consolação, em Hamilton, que definirá as colocações do nono ao 16o.");
		
		return e_1000_p1_s2;
	}

	private Paragraph checkExtract1000Paragraph1(Iterator<Paragraph> e_1000_p) {
		Paragraph e_1000_p1 = getNextParagraph(e_1000_p);
		assertNotNull(e_1000_p1);
		
		return e_1000_p1;
	}

	private Info_n checkExtract1000TitleSentence1AnalysisA1RootNodeInfo(TerminalNode e_1000_t_s1_A1_root_t) {
		Info e_1000_t_s1_A1_root_info = e_1000_t_s1_A1_root_t.getInfo();
		assertNotNull(e_1000_t_s1_A1_root_info);
		
		assertTrue(e_1000_t_s1_A1_root_info instanceof Info_n);
		Info_n e_1000_t_s1_A1_root_info_n = (Info_n) e_1000_t_s1_A1_root_info;
		
		assertEquals(e_1000_t_s1_A1_root_info_n.getLemma(), "consolação");
		assertEquals(e_1000_t_s1_A1_root_info_n.getSecondary(), "<act>");
		assertEquals(e_1000_t_s1_A1_root_info_n.getGender(), "F");
		assertEquals(e_1000_t_s1_A1_root_info_n.getNumber(), "S");
		
		return e_1000_t_s1_A1_root_info_n;
	}

	private TerminalNode checkExtract1000TitleSentence1AnalysisA1RootNode(Analysis e_1000_t_s1_A1) {
		// X:n("consolação" <act> F S)	Consolação
		Node e_1000_t_s1_A1_root = getRootNode(e_1000_t_s1_A1);
		
		assertNotNull(e_1000_t_s1_A1_root);

		assertEquals(e_1000_t_s1_A1_root.getFunction(), "X");
		assertEquals(e_1000_t_s1_A1_root.getForm(), "n");
		
		assertTrue(e_1000_t_s1_A1_root instanceof TerminalNode);
		TerminalNode e_1000_t_s1_A1_root_t = (TerminalNode) e_1000_t_s1_A1_root;
		
		assertEquals(e_1000_t_s1_A1_root_t.getText(), "Consolação");

		return e_1000_t_s1_A1_root_t;
	}

	private Analysis checkExtract1000TitleSentence1AnalysisA1(Iterator<Analysis> e_1000_t_s1_analyses) {
		// A1
		Analysis e_1000_t_s1_A1 = getNextAnalysis(e_1000_t_s1_analyses);
		
		assertNotNull(e_1000_t_s1_A1);
		
		assertEquals(e_1000_t_s1_A1.getIndex(), 1);
		
		return e_1000_t_s1_A1;
	}

	private Sentence checkExtract1000TitleSentence1(Iterator<Sentence> e_1000_t_sentences) {
		// <s id="1" ref="CF1000-1" source="CETENFolha id=1000 cad=Esporte sec=des sem=94a">
		// SOURCE: ref="CF1000-1" source="CETENFolha id=1000 cad=Esporte sec=des sem=94a"
		// CF1000-1 Consolação
		Sentence e_1000_t_s1 = getNextSentence(e_1000_t_sentences);
		
		assertNotNull(e_1000_t_s1);
		
		assertEquals(e_1000_t_s1.getId(), "1");
		assertEquals(e_1000_t_s1.getRef(), "CF1000-1");
		assertEquals(e_1000_t_s1.getSource(), "CETENFolha id=1000 cad=Esporte sec=des sem=94a");
		assertEquals(e_1000_t_s1.getText(), "Consolação");
		
		return e_1000_t_s1;
	}

	private Title checkExtract1000Title(Extract e_1000) {
		Title e_1000_t = getTitle(e_1000);
		assertNotNull(e_1000_t);
		
		return e_1000_t;
	}

	private Extract checkExtract1000(Iterator<Extract> extracts) {
		// <ext id=1000 cad="Esporte" sec="des" sem="94a">
		Extract e_1000 = getNextExtract(extracts);
		
		assertNotNull(e_1000);
		
		assertEquals(e_1000.getId(), 1000);
		assertEquals(e_1000.getCad(), "Esporte");
		assertEquals(e_1000.getSec(), "des");
		assertEquals(e_1000.getSem(), "94a");

		return e_1000;
	}

	private ADCorpus getCorpus(String name) {
		InputStream ext_1000 = ADParserBehavior.class.getResourceAsStream(name);
		InputStreamReader ext_1000r = new InputStreamReader(ext_1000, Charset.forName("UTF-8"));
		return new ADCorpus(ext_1000r);
	}

	private Iterator<Extract> getExtracts(String name) {
		ADCorpus corpus = getCorpus(name);
		
		Iterator<Extract> extracts = corpus.extracts();
		assertNotNull(extracts);
		
		return extracts;
	}

	private Extract getNextExtract(Iterator<Extract> extracts) {
		assertEquals(extracts.hasNext(), true);
		return extracts.next();
	}

	private Title getTitle(Extract extract) {
		return extract.title();
	}

	private Iterator<Sentence> getSentences(Title title) {
		Iterator<Sentence> sentences = title.sentences();
		assertNotNull(sentences);
		
		return sentences;
	}

	private Sentence getNextSentence(Iterator<Sentence> sentences) {
		assertEquals(sentences.hasNext(), true);
		return sentences.next();
	}

	private Iterator<Analysis> getAnalyses(Sentence sentence) {
		Iterator<Analysis> analyses = sentence.analyses();
		assertNotNull(analyses);
		
		return analyses;
	}

	private Analysis getNextAnalysis(Iterator<Analysis> analyses) {
		assertEquals(analyses.hasNext(), true);
		return analyses.next();
	}

	private Node getRootNode(Analysis analysis) {
		return analysis.tree();
	}

	private Iterator<Paragraph> getParagraphs(Extract extract) {
		Iterator<Paragraph> paragraphs = extract.paragraphs();
		assertNotNull(paragraphs);
		
		return paragraphs;
	}

	private Paragraph getNextParagraph(Iterator<Paragraph> paragraphs) {
		assertEquals(paragraphs.hasNext(), true);
		return paragraphs.next();
	}

	private Iterator<Sentence> getSentences(Paragraph paragraph) {
		Iterator<Sentence> sentences = paragraph.sentences();
		assertNotNull(sentences);

		return sentences;
	}

}
