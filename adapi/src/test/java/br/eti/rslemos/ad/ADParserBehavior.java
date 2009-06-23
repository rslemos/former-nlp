package br.eti.rslemos.ad;

import static org.mockito.Mockito.*;
import static org.testng.Assert.*;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
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
		TerminalNode e_1000_t_s1_A1_root_t = (TerminalNode) getRootNode(e_1000_t_s1_A1);
		checkExtract1000TitleSentence1AnalysisA1RootNode(e_1000_t_s1_A1_root_t);
		checkExtract1000TitleSentence1AnalysisA1RootNodeInfo(e_1000_t_s1_A1_root_t);
		checkNoChildren(e_1000_t_s1_A1_root_t);
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
		NonTerminalNode e_1000_p1_s2_A1_root_nt = (NonTerminalNode) getRootNode(e_1000_p1_s2_A1);
		checkExtract1000Paragraph1Sentence1AnalysisA1RootNode(e_1000_p1_s2_A1_root_nt);
		checkExtract1000Paragraph1Sentence1AnalysisA1RootNodeInfo(e_1000_p1_s2_A1_root_nt);
	}
		
	@Test
	public void shouldParseExt1000Paragraph1Sentence1AnalysisA1RootNodeChild1() {
		Iterator<Extract> extracts = getExtracts("ext_1000.ad");
		Extract e_1000 = getNextExtract(extracts);
		Iterator<Paragraph> e_1000_p = getParagraphs(e_1000);
		Paragraph e_1000_p1 = getNextParagraph(e_1000_p);
		Iterator<Sentence> e_1000_p1_sentences = getSentences(e_1000_p1);
		Sentence e_1000_p1_s2 = getNextSentence(e_1000_p1_sentences);
		Iterator<Analysis> e_1000_p1_s2_analyses = getAnalyses(e_1000_p1_s2);
		Analysis e_1000_p1_s2_A1 = getNextAnalysis(e_1000_p1_s2_analyses);
		NonTerminalNode e_1000_p1_s2_A1_root_c1_nt = getNonTerminalChild(e_1000_p1_s2_A1, 0);
		checkExtract1000Paragraph1Sentence1AnalysisA1RootNodeChild1(e_1000_p1_s2_A1_root_c1_nt);
		checkExtract1000Paragraph1Sentence1AnalysisA1RootNodeChild1Info(e_1000_p1_s2_A1_root_c1_nt);
	}

	@Test
	public void shouldParseExt1000Paragraph1Sentence1AnalysisA1RootNodeChild1Child1() {
		Iterator<Extract> extracts = getExtracts("ext_1000.ad");
		Extract e_1000 = getNextExtract(extracts);
		Iterator<Paragraph> e_1000_p = getParagraphs(e_1000);
		Paragraph e_1000_p1 = getNextParagraph(e_1000_p);
		Iterator<Sentence> e_1000_p1_sentences = getSentences(e_1000_p1);
		Sentence e_1000_p1_s2 = getNextSentence(e_1000_p1_sentences);
		Iterator<Analysis> e_1000_p1_s2_analyses = getAnalyses(e_1000_p1_s2);
		Analysis e_1000_p1_s2_A1 = getNextAnalysis(e_1000_p1_s2_analyses);
		TerminalNode e_1000_p1_s2_A1_root_c1_c1_t = getTerminalChild(e_1000_p1_s2_A1, 0, 0);
		checkExtract1000Paragraph1Sentence1AnalysisA1RootNodeChild1Child1(e_1000_p1_s2_A1_root_c1_c1_t);
		checkExtract1000Paragraph1Sentence1AnalysisA1RootNodeChild1Child1Info(e_1000_p1_s2_A1_root_c1_c1_t);
		checkNoChildren(e_1000_p1_s2_A1_root_c1_c1_t);
	}

	@Test
	public void shouldParseExt1000Paragraph1Sentence1AnalysisA1RootNodeChild1Child2() {
		Iterator<Extract> extracts = getExtracts("ext_1000.ad");
		Extract e_1000 = getNextExtract(extracts);
		Iterator<Paragraph> e_1000_p = getParagraphs(e_1000);
		Paragraph e_1000_p1 = getNextParagraph(e_1000_p);
		Iterator<Sentence> e_1000_p1_sentences = getSentences(e_1000_p1);
		Sentence e_1000_p1_s2 = getNextSentence(e_1000_p1_sentences);
		Iterator<Analysis> e_1000_p1_s2_analyses = getAnalyses(e_1000_p1_s2);
		Analysis e_1000_p1_s2_A1 = getNextAnalysis(e_1000_p1_s2_analyses);
		TerminalNode e_1000_p1_s2_A1_root_c1_c2_t = getTerminalChild(e_1000_p1_s2_A1, 0, 1);
		checkExtract1000Paragraph1Sentence1AnalysisA1RootNodeChild1Child2(e_1000_p1_s2_A1_root_c1_c2_t);
		checkExtract1000Paragraph1Sentence1AnalysisA1RootNodeChild1Child2Info(e_1000_p1_s2_A1_root_c1_c2_t);
		checkNoChildren(e_1000_p1_s2_A1_root_c1_c2_t);
	}

	@Test
	public void shouldParseExt1000Paragraph1Sentence1AnalysisA1RootNodeChild1Child3() {
		Iterator<Extract> extracts = getExtracts("ext_1000.ad");
		Extract e_1000 = getNextExtract(extracts);
		Iterator<Paragraph> e_1000_p = getParagraphs(e_1000);
		Paragraph e_1000_p1 = getNextParagraph(e_1000_p);
		Iterator<Sentence> e_1000_p1_sentences = getSentences(e_1000_p1);
		Sentence e_1000_p1_s2 = getNextSentence(e_1000_p1_sentences);
		Iterator<Analysis> e_1000_p1_s2_analyses = getAnalyses(e_1000_p1_s2);
		Analysis e_1000_p1_s2_A1 = getNextAnalysis(e_1000_p1_s2_analyses);
		NonTerminalNode e_1000_p1_s2_A1_root_c1_c3_nt = getNonTerminalChild(e_1000_p1_s2_A1, 0, 2);
		checkExtract1000Paragraph1Sentence1AnalysisA1RootNodeChild1Child3(e_1000_p1_s2_A1_root_c1_c3_nt);
		checkExtract1000Paragraph1Sentence1AnalysisA1RootNodeChild1Child3Info(e_1000_p1_s2_A1_root_c1_c3_nt);
	}

	@Test
	public void shouldParseExt1000Paragraph1Sentence1AnalysisA1RootNodeChild1Child3Child1() {
		Iterator<Extract> extracts = getExtracts("ext_1000.ad");
		Extract e_1000 = getNextExtract(extracts);
		Iterator<Paragraph> e_1000_p = getParagraphs(e_1000);
		Paragraph e_1000_p1 = getNextParagraph(e_1000_p);
		Iterator<Sentence> e_1000_p1_sentences = getSentences(e_1000_p1);
		Sentence e_1000_p1_s2 = getNextSentence(e_1000_p1_sentences);
		Iterator<Analysis> e_1000_p1_s2_analyses = getAnalyses(e_1000_p1_s2);
		Analysis e_1000_p1_s2_A1 = getNextAnalysis(e_1000_p1_s2_analyses);
		TerminalNode e_1000_p1_s2_A1_root_c1_c3_c1_t = getTerminalChild(e_1000_p1_s2_A1, 0, 2, 0);
		checkExtract1000Paragraph1Sentence1AnalysisA1RootNodeChild1Child3Child1(e_1000_p1_s2_A1_root_c1_c3_c1_t);
		checkExtract1000Paragraph1Sentence1AnalysisA1RootNodeChild1Child3Child1Info(e_1000_p1_s2_A1_root_c1_c3_c1_t);
		checkNoChildren(e_1000_p1_s2_A1_root_c1_c3_c1_t);
	}

	@Test
	public void shouldParseExt1000Paragraph1Sentence1AnalysisA1RootNodeChild1Child3Child2() {
		Iterator<Extract> extracts = getExtracts("ext_1000.ad");
		Extract e_1000 = getNextExtract(extracts);
		Iterator<Paragraph> e_1000_p = getParagraphs(e_1000);
		Paragraph e_1000_p1 = getNextParagraph(e_1000_p);
		Iterator<Sentence> e_1000_p1_sentences = getSentences(e_1000_p1);
		Sentence e_1000_p1_s2 = getNextSentence(e_1000_p1_sentences);
		Iterator<Analysis> e_1000_p1_s2_analyses = getAnalyses(e_1000_p1_s2);
		Analysis e_1000_p1_s2_A1 = getNextAnalysis(e_1000_p1_s2_analyses);
		NonTerminalNode e_1000_p1_s2_A1_root_c1_c3_c2_nt = getNonTerminalChild(e_1000_p1_s2_A1, 0, 2, 1);
		checkExtract1000Paragraph1Sentence1AnalysisA1RootNodeChild1Child3Child2(e_1000_p1_s2_A1_root_c1_c3_c2_nt);
		checkExtract1000Paragraph1Sentence1AnalysisA1RootNodeChild1Child3Child2Info(e_1000_p1_s2_A1_root_c1_c3_c2_nt);
	}

	@Test
	public void shouldParseExt1000Paragraph1Sentence1AnalysisA1RootNodeChild1Child3Child2Child1() {
		Iterator<Extract> extracts = getExtracts("ext_1000.ad");
		Extract e_1000 = getNextExtract(extracts);
		Iterator<Paragraph> e_1000_p = getParagraphs(e_1000);
		Paragraph e_1000_p1 = getNextParagraph(e_1000_p);
		Iterator<Sentence> e_1000_p1_sentences = getSentences(e_1000_p1);
		Sentence e_1000_p1_s2 = getNextSentence(e_1000_p1_sentences);
		Iterator<Analysis> e_1000_p1_s2_analyses = getAnalyses(e_1000_p1_s2);
		Analysis e_1000_p1_s2_A1 = getNextAnalysis(e_1000_p1_s2_analyses);
		NonTerminalNode e_1000_p1_s2_A1_root_c1_c3_c2_c1_nt = getNonTerminalChild(e_1000_p1_s2_A1, 0, 2, 1, 0);
		checkExtract1000Paragraph1Sentence1AnalysisA1RootNodeChild1Child3Child2Child1(e_1000_p1_s2_A1_root_c1_c3_c2_c1_nt);
		checkExtract1000Paragraph1Sentence1AnalysisA1RootNodeChild1Child3Child2Child1Info(e_1000_p1_s2_A1_root_c1_c3_c2_c1_nt);
	}

	@Test
	public void shouldParseExt1000Paragraph1Sentence1AnalysisA1RootNodeChild1Child3Child2Child1Child1() {
		Iterator<Extract> extracts = getExtracts("ext_1000.ad");
		Extract e_1000 = getNextExtract(extracts);
		Iterator<Paragraph> e_1000_p = getParagraphs(e_1000);
		Paragraph e_1000_p1 = getNextParagraph(e_1000_p);
		Iterator<Sentence> e_1000_p1_sentences = getSentences(e_1000_p1);
		Sentence e_1000_p1_s2 = getNextSentence(e_1000_p1_sentences);
		Iterator<Analysis> e_1000_p1_s2_analyses = getAnalyses(e_1000_p1_s2);
		Analysis e_1000_p1_s2_A1 = getNextAnalysis(e_1000_p1_s2_analyses);
		TerminalNode e_1000_p1_s2_A1_root_c1_c3_c2_c1_c1_t = getTerminalChild(e_1000_p1_s2_A1, 0, 2, 1, 0, 0);
		checkExtract1000Paragraph1Sentence1AnalysisA1RootNodeChild1Child3Child2Child1Child1(e_1000_p1_s2_A1_root_c1_c3_c2_c1_c1_t);
		checkExtract1000Paragraph1Sentence1AnalysisA1RootNodeChild1Child3Child2Child1Child1Info(e_1000_p1_s2_A1_root_c1_c3_c2_c1_c1_t);
		checkNoChildren(e_1000_p1_s2_A1_root_c1_c3_c2_c1_c1_t);
	}

	@Test
	public void shouldParseExt1000Paragraph1Sentence1AnalysisA1RootNodeChild1Child3Child2Child1Child2() {
		Iterator<Extract> extracts = getExtracts("ext_1000.ad");
		Extract e_1000 = getNextExtract(extracts);
		Iterator<Paragraph> e_1000_p = getParagraphs(e_1000);
		Paragraph e_1000_p1 = getNextParagraph(e_1000_p);
		Iterator<Sentence> e_1000_p1_sentences = getSentences(e_1000_p1);
		Sentence e_1000_p1_s2 = getNextSentence(e_1000_p1_sentences);
		Iterator<Analysis> e_1000_p1_s2_analyses = getAnalyses(e_1000_p1_s2);
		Analysis e_1000_p1_s2_A1 = getNextAnalysis(e_1000_p1_s2_analyses);
		TerminalNode e_1000_p1_s2_A1_root_c1_c3_c2_c1_c2_t = getTerminalChild(e_1000_p1_s2_A1, 0, 2, 1, 0, 1);
		checkExtract1000Paragraph1Sentence1AnalysisA1RootNodeChild1Child3Child2Child1Child2(e_1000_p1_s2_A1_root_c1_c3_c2_c1_c2_t);
		checkExtract1000Paragraph1Sentence1AnalysisA1RootNodeChild1Child3Child2Child1Child2Info(e_1000_p1_s2_A1_root_c1_c3_c2_c1_c2_t);
		checkNoChildren(e_1000_p1_s2_A1_root_c1_c3_c2_c1_c2_t);
	}

	@Test
	public void shouldParseExt1000Paragraph1Sentence1AnalysisA1RootNodeChild1Child3Child2Child2() {
		Iterator<Extract> extracts = getExtracts("ext_1000.ad");
		Extract e_1000 = getNextExtract(extracts);
		Iterator<Paragraph> e_1000_p = getParagraphs(e_1000);
		Paragraph e_1000_p1 = getNextParagraph(e_1000_p);
		Iterator<Sentence> e_1000_p1_sentences = getSentences(e_1000_p1);
		Sentence e_1000_p1_s2 = getNextSentence(e_1000_p1_sentences);
		Iterator<Analysis> e_1000_p1_s2_analyses = getAnalyses(e_1000_p1_s2);
		Analysis e_1000_p1_s2_A1 = getNextAnalysis(e_1000_p1_s2_analyses);
		TerminalNode e_1000_p1_s2_A1_root_c1_c3_c2_c2_t = getTerminalChild(e_1000_p1_s2_A1, 0, 2, 1, 1);
		checkExtract1000Paragraph1Sentence1AnalysisA1RootNodeChild1Child3Child2Child2(e_1000_p1_s2_A1_root_c1_c3_c2_c2_t);
		checkExtract1000Paragraph1Sentence1AnalysisA1RootNodeChild1Child3Child2Child2Info(e_1000_p1_s2_A1_root_c1_c3_c2_c2_t);
		checkNoChildren(e_1000_p1_s2_A1_root_c1_c3_c2_c2_t);
	}

	@Test
	public void shouldPartiallyParseExt1000() {
		Iterator<Extract> extracts = getExtracts("ext_1000.ad");
		Extract e_1000 = checkExtract1000(extracts);

		Title e_1000_t = checkExtract1000Title(e_1000);
		Iterator<Sentence> e_1000_t_sentences = getSentences(e_1000_t);
		Sentence e_1000_t_s1 = checkExtract1000TitleSentence1(e_1000_t_sentences);
		Iterator<Analysis> e_1000_t_s1_analyses = getAnalyses(e_1000_t_s1);
		Analysis e_1000_t_s1_A1 = checkExtract1000TitleSentence1AnalysisA1(e_1000_t_s1_analyses);
		TerminalNode e_1000_t_s1_A1_root_t = (TerminalNode) getRootNode(e_1000_t_s1_A1);
		checkExtract1000TitleSentence1AnalysisA1RootNode(e_1000_t_s1_A1_root_t);
		checkExtract1000TitleSentence1AnalysisA1RootNodeInfo(e_1000_t_s1_A1_root_t);
		checkNoChildren(e_1000_t_s1_A1_root_t);

		assertEquals(e_1000_t_s1_analyses.hasNext(), false);
		assertEquals(e_1000_t_sentences.hasNext(), false);
		
		Iterator<Paragraph> e_1000_p = getParagraphs(e_1000);
		Paragraph e_1000_p1 = checkExtract1000Paragraph1(e_1000_p);
		Iterator<Sentence> e_1000_p1_sentences = getSentences(e_1000_p1);		
		Sentence e_1000_p1_s2 = checkExtract1000Paragraph1Sentence1(e_1000_p1_sentences);
		Iterator<Analysis> e_1000_p1_s2_analyses = getAnalyses(e_1000_p1_s2);
		Analysis e_1000_p1_s2_A1 = checkExtract1000Paragraph1Sentence1AnalysisA1(e_1000_p1_s2_analyses);
		NonTerminalNode e_1000_p1_s2_A1_root_nt = (NonTerminalNode) getRootNode(e_1000_p1_s2_A1);
		checkExtract1000Paragraph1Sentence1AnalysisA1RootNode(e_1000_p1_s2_A1_root_nt);
		checkExtract1000Paragraph1Sentence1AnalysisA1RootNodeInfo(e_1000_p1_s2_A1_root_nt);

		NonTerminalNode e_1000_p1_s2_A1_root_c1_nt = getNonTerminalChild(e_1000_p1_s2_A1_root_nt, 0);
		checkExtract1000Paragraph1Sentence1AnalysisA1RootNodeChild1(e_1000_p1_s2_A1_root_c1_nt);
		checkExtract1000Paragraph1Sentence1AnalysisA1RootNodeChild1Info(e_1000_p1_s2_A1_root_c1_nt);

		Iterator<Node> e_1000_p1_s2_A1_root_c1_children = getChildren(e_1000_p1_s2_A1_root_c1_nt);
		TerminalNode e_1000_p1_s2_A1_root_c1_c1_t = getNextChild(e_1000_p1_s2_A1_root_c1_children, TerminalNode.class);
		checkExtract1000Paragraph1Sentence1AnalysisA1RootNodeChild1Child1(e_1000_p1_s2_A1_root_c1_c1_t);
		checkExtract1000Paragraph1Sentence1AnalysisA1RootNodeChild1Child1Info(e_1000_p1_s2_A1_root_c1_c1_t);
		checkNoChildren(e_1000_p1_s2_A1_root_c1_c1_t);

		TerminalNode e_1000_p1_s2_A1_root_c1_c2_t = getNextChild(e_1000_p1_s2_A1_root_c1_children, TerminalNode.class);
		checkExtract1000Paragraph1Sentence1AnalysisA1RootNodeChild1Child2(e_1000_p1_s2_A1_root_c1_c2_t);
		checkExtract1000Paragraph1Sentence1AnalysisA1RootNodeChild1Child2Info(e_1000_p1_s2_A1_root_c1_c2_t);
		checkNoChildren(e_1000_p1_s2_A1_root_c1_c2_t);

		NonTerminalNode e_1000_p1_s2_A1_root_c1_c3_nt = getNextChild(e_1000_p1_s2_A1_root_c1_children, NonTerminalNode.class);
		checkExtract1000Paragraph1Sentence1AnalysisA1RootNodeChild1Child3(e_1000_p1_s2_A1_root_c1_c3_nt);
		checkExtract1000Paragraph1Sentence1AnalysisA1RootNodeChild1Child3Info(e_1000_p1_s2_A1_root_c1_c3_nt);
		
		Iterator<Node> e_1000_p1_s2_A1_root_c1_c3_children = getChildren(e_1000_p1_s2_A1_root_c1_c3_nt);
		TerminalNode e_1000_p1_s2_A1_root_c1_c3_c1_t = getNextChild(e_1000_p1_s2_A1_root_c1_c3_children, TerminalNode.class);
		checkExtract1000Paragraph1Sentence1AnalysisA1RootNodeChild1Child3Child1(e_1000_p1_s2_A1_root_c1_c3_c1_t);
		checkExtract1000Paragraph1Sentence1AnalysisA1RootNodeChild1Child3Child1Info(e_1000_p1_s2_A1_root_c1_c3_c1_t);
		checkNoChildren(e_1000_p1_s2_A1_root_c1_c3_c1_t);

		NonTerminalNode e_1000_p1_s2_A1_root_c1_c3_c2_nt = getNextChild(e_1000_p1_s2_A1_root_c1_c3_children, NonTerminalNode.class);
		checkExtract1000Paragraph1Sentence1AnalysisA1RootNodeChild1Child3Child2(e_1000_p1_s2_A1_root_c1_c3_c2_nt);
		checkExtract1000Paragraph1Sentence1AnalysisA1RootNodeChild1Child3Child2Info(e_1000_p1_s2_A1_root_c1_c3_c2_nt);
		
		Iterator<Node> e_1000_p1_s2_A1_root_c1_c3_c2_children = getChildren(e_1000_p1_s2_A1_root_c1_c3_c2_nt);
		NonTerminalNode e_1000_p1_s2_A1_root_c1_c3_c2_c1_nt = getNextChild(e_1000_p1_s2_A1_root_c1_c3_c2_children, NonTerminalNode.class);
		checkExtract1000Paragraph1Sentence1AnalysisA1RootNodeChild1Child3Child2Child1(e_1000_p1_s2_A1_root_c1_c3_c2_c1_nt);
		checkExtract1000Paragraph1Sentence1AnalysisA1RootNodeChild1Child3Child2Child1Info(e_1000_p1_s2_A1_root_c1_c3_c2_c1_nt);

		Iterator<Node> e_1000_p1_s2_A1_root_c1_c3_c2_c1_children = getChildren(e_1000_p1_s2_A1_root_c1_c3_c2_c1_nt);
		TerminalNode e_1000_p1_s2_A1_root_c1_c3_c2_c1_c1_t = getNextChild(e_1000_p1_s2_A1_root_c1_c3_c2_c1_children, TerminalNode.class);
		checkExtract1000Paragraph1Sentence1AnalysisA1RootNodeChild1Child3Child2Child1Child1(e_1000_p1_s2_A1_root_c1_c3_c2_c1_c1_t);
		checkExtract1000Paragraph1Sentence1AnalysisA1RootNodeChild1Child3Child2Child1Child1Info(e_1000_p1_s2_A1_root_c1_c3_c2_c1_c1_t);
		checkNoChildren(e_1000_p1_s2_A1_root_c1_c3_c2_c1_c1_t);
		
		TerminalNode e_1000_p1_s2_A1_root_c1_c3_c2_c1_c2_t = getNextChild(e_1000_p1_s2_A1_root_c1_c3_c2_c1_children, TerminalNode.class);
		checkExtract1000Paragraph1Sentence1AnalysisA1RootNodeChild1Child3Child2Child1Child2(e_1000_p1_s2_A1_root_c1_c3_c2_c1_c2_t);
		checkExtract1000Paragraph1Sentence1AnalysisA1RootNodeChild1Child3Child2Child1Child2Info(e_1000_p1_s2_A1_root_c1_c3_c2_c1_c2_t);
		checkNoChildren(e_1000_p1_s2_A1_root_c1_c3_c2_c1_c2_t);
		
		assertEquals(e_1000_p1_s2_A1_root_c1_c3_c2_c1_children.hasNext(), false);
		
		TerminalNode e_1000_p1_s2_A1_root_c1_c3_c2_c2_t = getNextChild(e_1000_p1_s2_A1_root_c1_c3_c2_children, TerminalNode.class);
		checkExtract1000Paragraph1Sentence1AnalysisA1RootNodeChild1Child3Child2Child2(e_1000_p1_s2_A1_root_c1_c3_c2_c2_t);
		checkExtract1000Paragraph1Sentence1AnalysisA1RootNodeChild1Child3Child2Child2Info(e_1000_p1_s2_A1_root_c1_c3_c2_c2_t);
		checkNoChildren(e_1000_p1_s2_A1_root_c1_c3_c2_c2_t);
	}

	@Test
	public void shouldFullyParseExt1000() {
		ADCorpus corpus = getCorpus("ext_1000.ad");
		fullyParse(corpus);
	}

	@Test
	public void shouldCloseInputReader() throws Throwable {
		InputStream ext_1000 = ADParserBehavior.class.getResourceAsStream("ext_1000.ad");
		Reader ext_1000r = new InputStreamReader(ext_1000, Charset.forName("UTF-8"));
		Reader ext_1000r_spy = spy(ext_1000r);
		ADCorpus corpus = new ADCorpus(ext_1000r_spy);
		
		fullyParse(corpus);
		
		verify(ext_1000r_spy, times(1)).close();
	}

	private void fullyParse(ADCorpus corpus) {
		for (Extract extract : corpus) {
			Title title = extract.title();
			for (Sentence sentence : title) {
				for (Analysis analysis : sentence) {
					Node node = analysis.tree();
					processNode(node);
				}
			}
		}
	}
	
	private void processNode(Node node) {
		for (Node child : node) {
			processNode(child);
		}
	}

	private void checkExtract1000Paragraph1Sentence1AnalysisA1RootNodeChild1Child3Child2Child2Info(TerminalNode e_1000_p1_s2_A1_root_c1_c3_c2_c2_t) {
		String[] e_1000_p1_s2_A1_root_c1_c3_c2_c2_info = e_1000_p1_s2_A1_root_c1_c3_c2_c2_t.getInfo();
		assertNull(e_1000_p1_s2_A1_root_c1_c3_c2_c2_info);
	}

	private TerminalNode checkExtract1000Paragraph1Sentence1AnalysisA1RootNodeChild1Child3Child2Child2(Node e_1000_p1_s2_A1_root_c1_c3_c2_c2) {
		// ====,
		return checkTerminalNode(e_1000_p1_s2_A1_root_c1_c3_c2_c2, 4, null, null, ",");
	}

	private void checkExtract1000Paragraph1Sentence1AnalysisA1RootNodeChild1Child3Child2Child1Child2Info(TerminalNode e_1000_p1_s2_A1_root_c1_c3_c2_c1_c2_t) {
		// ("Brasil" <cjt-head> <civ> M S)
		String[] e_1000_p1_s2_A1_root_c1_c3_c2_c1_c2_info = e_1000_p1_s2_A1_root_c1_c3_c2_c1_c2_t.getInfo();
		assertNotNull(e_1000_p1_s2_A1_root_c1_c3_c2_c1_c2_info);

		assertEquals(e_1000_p1_s2_A1_root_c1_c3_c2_c1_c2_info, new String[] {"\"Brasil\"", "<cjt-head>", "<civ>", "M", "S"});
	}

	private TerminalNode checkExtract1000Paragraph1Sentence1AnalysisA1RootNodeChild1Child3Child2Child1Child2(Node node) {
		// =====H:prop("Brasil" <cjt-head> <civ> M S)	Brasil
		return checkTerminalNode(node, 5, "H", "prop", "Brasil");
	}

	private void checkExtract1000Paragraph1Sentence1AnalysisA1RootNodeChild1Child3Child2Child1Child1Info(TerminalNode e_1000_p1_s2_A1_root_c1_c3_c2_c1_c1_t) {
		// ("o" <artd> <-sam> DET M S)
		String[] e_1000_p1_s2_A1_root_c1_c3_c2_c1_c1_info = e_1000_p1_s2_A1_root_c1_c3_c2_c1_c1_t.getInfo();
		assertNotNull(e_1000_p1_s2_A1_root_c1_c3_c2_c1_c1_info);

		assertEquals(e_1000_p1_s2_A1_root_c1_c3_c2_c1_c1_info, new String[] {"\"o\"", "<artd>", "<-sam>", "DET", "M", "S"});
	}

	private TerminalNode checkExtract1000Paragraph1Sentence1AnalysisA1RootNodeChild1Child3Child2Child1Child1(Node node) {
		// =====DN:pron-indef("o" <artd> <-sam> DET M S)	o
		return checkTerminalNode(node, 5, "DN", "pron-indef", "o");
	}

	private void checkExtract1000Paragraph1Sentence1AnalysisA1RootNodeChild1Child3Child2Child1Info(NonTerminalNode e_1000_p1_s2_A1_root_c1_c3_c2_c1_nt) {
		String[] e_1000_p1_s2_A1_root_c1_c3_c2_c1_info = e_1000_p1_s2_A1_root_c1_c3_c2_c1_nt.getInfo();
		assertNull(e_1000_p1_s2_A1_root_c1_c3_c2_c1_info);
	}

	private NonTerminalNode checkExtract1000Paragraph1Sentence1AnalysisA1RootNodeChild1Child3Child2Child1(Node e_1000_p1_s2_A1_root_c1_c3_c2_c1) {
		// ====CJT:np
		return checkNonTerminalNode(e_1000_p1_s2_A1_root_c1_c3_c2_c1, 4, "CJT", "np");
	}

	private void checkExtract1000Paragraph1Sentence1AnalysisA1RootNodeChild1Child3Child2Info(NonTerminalNode e_1000_p1_s2_A1_root_c1_c3_c2_nt) {
		String[] e_1000_p1_s2_A1_root_c1_c3_c2_info = e_1000_p1_s2_A1_root_c1_c3_c2_nt.getInfo();
		assertNull(e_1000_p1_s2_A1_root_c1_c3_c2_info);
	}

	private NonTerminalNode checkExtract1000Paragraph1Sentence1AnalysisA1RootNodeChild1Child3Child2(Node e_1000_p1_s2_A1_root_c1_c3_c2) {
		// ===DP:par
		return checkNonTerminalNode(e_1000_p1_s2_A1_root_c1_c3_c2, 3, "DP", "par");
	}

	private void checkExtract1000Paragraph1Sentence1AnalysisA1RootNodeChild1Child3Child1Info(TerminalNode e_1000_p1_s2_A1_root_c1_c3_c1_t) {
		// ("de" <sam-> <np-close>)
		String[] e_1000_p1_s2_A1_root_c1_c3_c1_info = e_1000_p1_s2_A1_root_c1_c3_c1_t.getInfo();
		assertNotNull(e_1000_p1_s2_A1_root_c1_c3_c1_info);

		assertEquals(e_1000_p1_s2_A1_root_c1_c3_c1_info, new String[] {"\"de\"", "<sam->", "<np-close>"});
	}

	private TerminalNode checkExtract1000Paragraph1Sentence1AnalysisA1RootNodeChild1Child3Child1(Node node) {
		// ===H:prp("de" <sam-> <np-close>)	de
		return checkTerminalNode(node, 3, "H", "prp", "de");
	}

	private void checkExtract1000Paragraph1Sentence1AnalysisA1RootNodeChild1Child3Info(NonTerminalNode e_1000_p1_s2_A1_root_c1_c3_nt) {
		String[] e_1000_p1_s2_A1_root_c1_c3_info = e_1000_p1_s2_A1_root_c1_c3_nt.getInfo();
		assertNull(e_1000_p1_s2_A1_root_c1_c3_info);
	}

	private NonTerminalNode checkExtract1000Paragraph1Sentence1AnalysisA1RootNodeChild1Child3(Node e_1000_p1_s2_A1_root_c1_c3) {
		// ==DN:pp
		return checkNonTerminalNode(e_1000_p1_s2_A1_root_c1_c3, 2, "DN", "pp");
	}

	private void checkExtract1000Paragraph1Sentence1AnalysisA1RootNodeChild1Child2Info(TerminalNode e_1000_p1_s2_A1_root_c1_c2_t) {
		// ("seleção" <HH> F P)
		String[] e_1000_p1_s2_A1_root_c1_c2_info = e_1000_p1_s2_A1_root_c1_c2_t.getInfo();
		assertNotNull(e_1000_p1_s2_A1_root_c1_c2_info);

		assertEquals(e_1000_p1_s2_A1_root_c1_c2_info, new String[] {"\"seleção\"", "<HH>", "F", "P"});
	}

	private TerminalNode checkExtract1000Paragraph1Sentence1AnalysisA1RootNodeChild1Child2(Node node) {
		// ==H:n("seleção" <HH> F P)	seleções
		return checkTerminalNode(node, 2, "H", "n", "seleções");
	}

	private void checkExtract1000Paragraph1Sentence1AnalysisA1RootNodeChild1Child1Info(TerminalNode e_1000_p1_s2_A1_root_c1_c1_t) {
		// ("o" <artd> DET F P)
		String[] e_1000_p1_s2_A1_root_c1_c1_info = e_1000_p1_s2_A1_root_c1_c1_t.getInfo();
		assertNotNull(e_1000_p1_s2_A1_root_c1_c1_info);

		assertEquals(e_1000_p1_s2_A1_root_c1_c1_info, new String[] {"\"o\"", "<artd>", "DET", "F", "P"});
	}

	private TerminalNode checkExtract1000Paragraph1Sentence1AnalysisA1RootNodeChild1Child1(Node node) {
		// ==DN:pron-indef("o" <artd> DET F P)	As
		return checkTerminalNode(node, 2, "DN", "pron-indef", "As");
	}

	private void checkExtract1000Paragraph1Sentence1AnalysisA1RootNodeChild1Info(NonTerminalNode e_1000_p1_s2_A1_root_c1_nt) {
		String[] e_1000_p1_s2_A1_root_c1_info = e_1000_p1_s2_A1_root_c1_nt.getInfo();
		assertNull(e_1000_p1_s2_A1_root_c1_info);
	}

	private NonTerminalNode checkExtract1000Paragraph1Sentence1AnalysisA1RootNodeChild1(Node e_1000_p1_s2_A1_root_c1) {
		// =S:np
		return checkNonTerminalNode(e_1000_p1_s2_A1_root_c1, 1, "S", "np");
	}

	private void checkExtract1000Paragraph1Sentence1AnalysisA1RootNodeInfo(NonTerminalNode e_1000_p1_s2_A1_root) {
		String[] e_1000_p1_s2_A1_root_info = e_1000_p1_s2_A1_root.getInfo();
		assertNull(e_1000_p1_s2_A1_root_info);
	}

	private NonTerminalNode checkExtract1000Paragraph1Sentence1AnalysisA1RootNode(Node e_1000_p1_s2_A1_root) {
		// STA:fcl
		return checkNonTerminalNode(e_1000_p1_s2_A1_root, 0, "STA", "fcl");
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

	private void checkExtract1000TitleSentence1AnalysisA1RootNodeInfo(TerminalNode e_1000_t_s1_A1_root_t) {
		// "consolação" <act> F S
		String[] e_1000_t_s1_A1_root_info = e_1000_t_s1_A1_root_t.getInfo();
		assertNotNull(e_1000_t_s1_A1_root_info);
		
		assertEquals(e_1000_t_s1_A1_root_info, new String[] {"\"consolação\"", "<act>", "F", "S"});		
	}

	private TerminalNode checkExtract1000TitleSentence1AnalysisA1RootNode(Node e_1000_t_s1_A1_root) {
		// X:n("consolação" <act> F S)	Consolação
		return checkTerminalNode(e_1000_t_s1_A1_root, 0, "X", "n", "Consolação");
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

	private Iterator<Node> getChildren(Node node) {
		Iterator<Node> children = node.children();
		assertNotNull(children);
		return children;
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

	private <T extends Node> T getNextChild(Iterator<Node> children, Class<T> clazz) {
		assertEquals(children.hasNext(), true);
		Node child = children.next();
		
		assertTrue(clazz.isInstance(child));
		return clazz.cast(child);
	}

	private TerminalNode checkTerminalNode(Node node, int depth, String function, String form, String text) {
		checkNode(node, depth, function, form);
		
		assertTrue(node instanceof TerminalNode);

		TerminalNode node_t = (TerminalNode) node;
		
		assertEquals(node_t.getText(), text);
		
		return node_t;
	}

	private NonTerminalNode checkNonTerminalNode(Node node, int depth, String function, String form) {
		checkNode(node, depth, function, form);
				
		assertTrue(node instanceof NonTerminalNode);

		return (NonTerminalNode) node;
	}

	private void checkNode(Node node, int depth, String function, String form) {
		assertNotNull(node);
		
		assertEquals(node.getDepth(), depth);
		assertEquals(node.getFunction(), function);
		assertEquals(node.getForm(), form);
	}

	private void checkNoChildren(Node node) {
		Iterator<Node> children = getChildren(node);
		assertEquals(children.hasNext(), false);
	}

	private NonTerminalNode getNonTerminalChild(Node node, int... n) {
		Node child = getChild(node, n);
		assertTrue(child instanceof NonTerminalNode);
		
		return (NonTerminalNode) child;
	}

	private TerminalNode getTerminalChild(Node node, int... n) {
		Node child = getChild(node, n);
		assertTrue(child instanceof TerminalNode);
		
		return (TerminalNode) child;
	}

	private Node getChild(Node node, int... n) {
		for (int j = 0; j < n.length; j++) {
			Iterator<Node> children = getChildren(node);
			for (int i = 0; i < n[j]; i++)
				getNextChild(children, Node.class);

			node = getNextChild(children, Node.class);
		}

		return node;
	}

	private NonTerminalNode getNonTerminalChild(Analysis analysis, int... n) {
		NonTerminalNode e_1000_p1_s2_A1_root_nt = (NonTerminalNode) getRootNode(analysis);
		return getNonTerminalChild(e_1000_p1_s2_A1_root_nt, n);
	}
	
	private TerminalNode getTerminalChild(Analysis analysis, int... n) {
		NonTerminalNode e_1000_p1_s2_A1_root_nt = (NonTerminalNode) getRootNode(analysis);
		return getTerminalChild(e_1000_p1_s2_A1_root_nt, n);
	}


}
