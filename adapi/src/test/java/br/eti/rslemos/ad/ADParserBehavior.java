package br.eti.rslemos.ad;

import static org.mockito.Mockito.*;
import static org.testng.Assert.*;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
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
		Extract e_1000 = getNext(extracts);
		Iterator<SentenceSet> e_1000_p = getSentenceSets(e_1000);
		checkExtract1000Title(e_1000_p);
	}

	@Test
	public void shouldParseExt1000TitleSentence1() {
		Iterator<Extract> extracts = getExtracts("ext_1000.ad");
		Title e_1000_t = getSentenceSet(extracts, 0, 0, Title.class);
		Iterator<Sentence> e_1000_t_sentences = getSentences(e_1000_t);
		checkExtract1000TitleSentence1(e_1000_t_sentences);
	}

	@Test
	public void shouldParseExt1000TitleSentence1AnalysisA1() {
		Iterator<Extract> extracts = getExtracts("ext_1000.ad");
		Analysis e_1000_t_s1_A1 = getAnalysis(extracts, 0, 0, 0, 0);
		
		checkAnalysis(e_1000_t_s1_A1, 1);
	}

	@Test
	public void shouldParseExt1000TitleSentence1AnalysisA1RootNode() {
		Iterator<Extract> extracts = getExtracts("ext_1000.ad");
		Analysis e_1000_t_s1_A1 = getAnalysis(extracts, 0, 0, 0, 0);
		TerminalNode e_1000_t_s1_A1_root_t = (TerminalNode) getRootNode(e_1000_t_s1_A1, 0);
		check_e_1000_t_s1_A1_0(e_1000_t_s1_A1_root_t);
	}

	@Test
	public void shouldParseExt1000Paragraph1() {
		Iterator<Extract> extracts = getExtracts("ext_1000.ad");
		Extract e_1000 = getNext(extracts);
		Iterator<SentenceSet> e_1000_p = getSentenceSets(e_1000);
		checkExtract1000Paragraph1(e_1000_p);
	}
	
	@Test
	public void shouldParseExt1000Paragraph1Sentence1() {
		Iterator<Extract> extracts = getExtracts("ext_1000.ad");
		SentenceSet e_1000_p1 = getSentenceSet(extracts, 0, 1);
		Iterator<Sentence> e_1000_p1_sentences = getSentences(e_1000_p1);		
		checkExtract1000Paragraph1Sentence1(e_1000_p1_sentences);
	}
		
	@Test
	public void shouldParseExt1000Paragraph1Sentence1AnalysisA1() {
		Iterator<Extract> extracts = getExtracts("ext_1000.ad");
		Analysis e_1000_p1_s2_A1 = getAnalysis(extracts, 0, 0, 0, 0);
		checkAnalysis(e_1000_p1_s2_A1, 1);
	}

	@Test
	public void shouldParseExt1000Paragraph1Sentence1AnalysisA1RootNode() {
		Iterator<Extract> extracts = getExtracts("ext_1000.ad");
		Analysis e_1000_p1_s2_A1 = getAnalysis(extracts, 0, 1, 0, 0);
		NonTerminalNode e_1000_p1_s2_A1_root_nt = (NonTerminalNode) getRootNode(e_1000_p1_s2_A1, 0);
		check_e_1000_p1_s2_A1_0(e_1000_p1_s2_A1_root_nt);
	}
		
	@Test
	public void shouldParseExt1000Paragraph1Sentence1AnalysisA1RootNodeChild1() {
		Iterator<Extract> extracts = getExtracts("ext_1000.ad");
		Analysis e_1000_p1_s2_A1 = getAnalysis(extracts, 0, 1, 0, 0);
		NonTerminalNode e_1000_p1_s2_A1_root_c1_nt = getNonTerminalChild(e_1000_p1_s2_A1, 0);
		check_e_1000_p1_s2_A1_0_0(e_1000_p1_s2_A1_root_c1_nt);
	}

	@Test
	public void shouldParseExt1000Paragraph1Sentence1AnalysisA1RootNodeChild1Child1() {
		Iterator<Extract> extracts = getExtracts("ext_1000.ad");
		Analysis e_1000_p1_s2_A1 = getAnalysis(extracts, 0, 1, 0, 0);
		TerminalNode e_1000_p1_s2_A1_root_c1_c1_t = getTerminalChild(e_1000_p1_s2_A1, 0, 0);
		check_e_1000_p1_s2_A1_0_0_0(e_1000_p1_s2_A1_root_c1_c1_t);
	}

	@Test
	public void shouldParseExt1000Paragraph1Sentence1AnalysisA1RootNodeChild1Child2() {
		Iterator<Extract> extracts = getExtracts("ext_1000.ad");
		Analysis e_1000_p1_s2_A1 = getAnalysis(extracts, 0, 1, 0, 0);
		TerminalNode e_1000_p1_s2_A1_root_c1_c2_t = getTerminalChild(e_1000_p1_s2_A1, 0, 1);
		check_e_1000_p1_s2_A1_0_0_1(e_1000_p1_s2_A1_root_c1_c2_t);
	}

	@Test
	public void shouldParseExt1000Paragraph1Sentence1AnalysisA1RootNodeChild1Child3() {
		Iterator<Extract> extracts = getExtracts("ext_1000.ad");
		Analysis e_1000_p1_s2_A1 = getAnalysis(extracts, 0, 1, 0, 0);
		NonTerminalNode e_1000_p1_s2_A1_root_c1_c3_nt = getNonTerminalChild(e_1000_p1_s2_A1, 0, 2);
		check_e_1000_p1_s2_A1_0_0_2(e_1000_p1_s2_A1_root_c1_c3_nt);
	}

	@Test
	public void shouldParseExt1000Paragraph1Sentence1AnalysisA1RootNodeChild1Child3Child1() {
		Iterator<Extract> extracts = getExtracts("ext_1000.ad");
		Analysis e_1000_p1_s2_A1 = getAnalysis(extracts, 0, 1, 0, 0);
		TerminalNode e_1000_p1_s2_A1_root_c1_c3_c1_t = getTerminalChild(e_1000_p1_s2_A1, 0, 2, 0);
		check_e_1000_p1_s2_A1_0_0_2_0(e_1000_p1_s2_A1_root_c1_c3_c1_t);
	}

	@Test
	public void shouldParseExt1000Paragraph1Sentence1AnalysisA1RootNodeChild1Child3Child2() {
		Iterator<Extract> extracts = getExtracts("ext_1000.ad");
		Analysis e_1000_p1_s2_A1 = getAnalysis(extracts, 0, 1, 0, 0);
		NonTerminalNode e_1000_p1_s2_A1_root_c1_c3_c2_nt = getNonTerminalChild(e_1000_p1_s2_A1, 0, 2, 1);
		check_e_1000_p1_s2_A1_0_0_2_1(e_1000_p1_s2_A1_root_c1_c3_c2_nt);
	}

	@Test
	public void shouldParseExt1000Paragraph1Sentence1AnalysisA1RootNodeChild1Child3Child2Child1() {
		Iterator<Extract> extracts = getExtracts("ext_1000.ad");
		Analysis e_1000_p1_s2_A1 = getAnalysis(extracts, 0, 1, 0, 0);
		NonTerminalNode e_1000_p1_s2_A1_root_c1_c3_c2_c1_nt = getNonTerminalChild(e_1000_p1_s2_A1, 0, 2, 1, 0);
		check_e_1000_p1_s2_A1_0_0_2_1_0(e_1000_p1_s2_A1_root_c1_c3_c2_c1_nt);
	}

	@Test
	public void shouldParseExt1000Paragraph1Sentence1AnalysisA1RootNodeChild1Child3Child2Child1Child1() {
		Iterator<Extract> extracts = getExtracts("ext_1000.ad");
		Analysis e_1000_p1_s2_A1 = getAnalysis(extracts, 0, 1, 0, 0);
		TerminalNode e_1000_p1_s2_A1_root_c1_c3_c2_c1_c1_t = getTerminalChild(e_1000_p1_s2_A1, 0, 2, 1, 0, 0);
		check_e_1000_p1_s2_A1_0_0_2_1_0_0(e_1000_p1_s2_A1_root_c1_c3_c2_c1_c1_t);
	}

	@Test
	public void shouldParseExt1000Paragraph1Sentence1AnalysisA1RootNodeChild1Child3Child2Child1Child2() {
		Iterator<Extract> extracts = getExtracts("ext_1000.ad");
		Analysis e_1000_p1_s2_A1 = getAnalysis(extracts, 0, 1, 0, 0);
		TerminalNode e_1000_p1_s2_A1_root_c1_c3_c2_c1_c2_t = getTerminalChild(e_1000_p1_s2_A1, 0, 2, 1, 0, 1);
		check_e_1000_p1_s2_A1_0_0_2_1_0_1(e_1000_p1_s2_A1_root_c1_c3_c2_c1_c2_t);
	}

	@Test
	public void shouldParseExt1000Paragraph1Sentence1AnalysisA1RootNodeChild1Child3Child2Child2() {
		Iterator<Extract> extracts = getExtracts("ext_1000.ad");
		Analysis e_1000_p1_s2_A1 = getAnalysis(extracts, 0, 1, 0, 0);
		TerminalNode e_1000_p1_s2_A1_root_c1_c3_c2_c2_t = getTerminalChild(e_1000_p1_s2_A1, 0, 2, 1, 1);
		check_e_1000_p1_s2_A1_0_0_2_1_1(e_1000_p1_s2_A1_root_c1_c3_c2_c2_t);
	}

	@Test
	public void shouldParseTitleIntermingledInParagraphs() {
		Iterator<Extract> extracts = getExtracts("/pt_BR/FlorestaVirgem_CF.txt");
		Title e_7_t1 = getSentenceSet(extracts, 6, 1, Title.class);
		Iterator<Sentence> e_7_t1_sentences = getSentences(e_7_t1);
		Sentence e_7_t1_s1 = getNext(e_7_t1_sentences);
		assertEquals(e_7_t1_s1.getText(), "Mais frangos");
	}
	
	@Test
	public void shouldParseAnonymousSentenceSet() {
		Iterator<Extract> extracts = getExtracts("/pt_BR/Amazonia_CF.txt");
		Sentence e_0_0_0 = getSentence(extracts, 0, 0, 0);
		assertEquals(e_0_0_0.getText(), "depois se encontram com a dissidência do grupo, os Bacamarteiros de Pinga Fogo, e a festa continua por muito tempo...");
	}
	
	@Test
	public void shouldParseSentenceWithNonNumericRef() {
		Iterator<Extract> extracts = getExtracts("/pt_BR/Amazonia_CF.txt");
		Sentence e_0_0_0 = getSentence(extracts, 0, 0, 0);
		assertEquals(e_0_0_0.getRef(), "1001.porto-poesia=removeme=-2 a poesia toma porto-alegre=removeme=-1");
	}
	
	@Test
	public void shouldIgnoreSFragTag() {
		Iterator<Extract> extracts = getExtracts("/pt_BR/FlorestaVirgem_CF.txt");
		Sentence sentence = getSentence(extracts, 8, 1, 0);
		assertEquals(sentence.getText(), "«Fatos e Relatos»");
	}
	
	@Test
	public void shouldIgnoreLixoTag() {
		Iterator<Extract> extracts = getExtracts("/pt_BR/Amazonia_CF.txt");
		Sentence sentence = getSentence(extracts, 13, 0, 13);
		assertEquals(sentence.getRef(), "1012.morangos nervososfragmentos de caio f-no-secxxi=removeme=-14");
	}
	
	@Test
	public void shouldAllowColonNode() {
		Iterator<Extract> extracts = getExtracts("/pt_BR/Amazonia_CF.txt");
		Analysis e_0_0_5_A1 = getAnalysis(extracts, 0, 0, 5, 0);
		TerminalNode e_0_0_5_A1_c2 = (TerminalNode) getRootNode(e_0_0_5_A1, 2);
		assertEquals(e_0_0_5_A1_c2.getText(), ":");
	}	

	@Test
	public void shouldIgnoreRemarksWithSharps() {
		Iterator<Extract> extracts = getExtracts("/pt_BR/Amazonia_CF.txt");
		Sentence e_1_0_4 = getSentence(extracts, 1, 0, 4);
		assertEquals(e_1_0_4.getRef(), "1002.pesquisa da usp mapeia cultura livre em são paulo-5");
	}
	
	@Test
	public void shouldNotRelyOnEmptyLineAtEndOfAnalysis() {
		Iterator<Extract> extracts = getExtracts("/pt_BR/Amazonia_CF.txt");
		Sentence e_3_0_14 = getSentence(extracts, 3, 0, 14);
		assertEquals(e_3_0_14.getRef(), "1004.heroi exilado e estrangeiro em-seu-proprio=removeme=-pais-15");
	}
	
	@Test
	public void shouldAllowEqualsSignOnSentence() {
		Iterator<Extract> extracts = getExtracts("/pt_BR/Amazonia_CF.txt");
		Sentence e_10_0_75 = getSentence(extracts, 10, 0, 75);
		assertEquals(e_10_0_75.getRef(), "1011.a nova decadência da cultura-pernambucana=removeme=-76");
		
		Iterator<Analysis> e_10_0_75_analyses = getAnalyses(e_10_0_75);
		Analysis e_10_0_75_A1 = getNext(e_10_0_75_analyses);
		
		TerminalNode e_10_0_75_A1_c2 = (TerminalNode) getRootNode(e_10_0_75_A1, 1);
		assertEquals(e_10_0_75_A1_c2.getText(), "=");
	}
	
	@Test
	public void shouldSkipRootNodes() {
		Iterator<Extract> extracts = getExtracts("ext_1000.ad");
		Analysis e_1000_p1_s2_A1 = getAnalysis(extracts, 0, 1, 0, 0);
		TerminalNode e_1000_p1_s2_A1_2ndroot_t = (TerminalNode) getRootNode(e_1000_p1_s2_A1, 1);
		check_e_1000_p1_s2_A1_1(e_1000_p1_s2_A1_2ndroot_t);
	}

	
	@Test
	public void shouldSkipAnalyses() {
		// actually we don't have more than 1 analysis per sentence
		// but hasNext should know how to tell this
		Iterator<Extract> extracts = getExtracts("ext_1000.ad");
		Sentence sentence = getSentence(extracts, 0, 0, 0);
		
		Iterator<Analysis> analyses = getAnalyses(sentence);
		getNext(analyses);
		assertEquals(analyses.hasNext(), false);
	}

	@Test
	public void shouldSkipSentences() {
		Iterator<Extract> extracts = getExtracts("ext_1000.ad");
		Analysis e_1000_p1_s3_A1 = getAnalysis(extracts, 0, 1, 1, 0);
		TerminalNode e_1000_p1_s3_A1_root_t = (TerminalNode) getRootNode(e_1000_p1_s3_A1, 0);
		check_e_1000_p1_s3_A1_0(e_1000_p1_s3_A1_root_t);
	}

	@Test
	public void shouldSkipParagraphs() {
		Iterator<Extract> extracts = getExtracts("ext_1000.ad");
		Analysis e_1000_p2_s4_A1 = getAnalysis(extracts, 0, 2, 0, 0);
		TerminalNode e_1000_p2_s4_A1_root_c5_c2_c2_c1_t = getTerminalChild(e_1000_p2_s4_A1, 4, 1, 1, 0);
		check_e_1000_p1_s2_A1_0_4_1_1_0(e_1000_p2_s4_A1_root_c5_c2_c2_c1_t);
	}

	@Test
	public void shouldSkipExtracts () {
		Iterator<Extract> extracts = getExtracts("ext_1001-1003.ad");
		Analysis e_1003_p2_s17_A1 = getAnalysis(extracts, 2, 1, 0, 0);
		TerminalNode e_1000_p2_s17_A1_root_c3_c2_c7_c2_c3_c2_c2_c3_c3_t = getTerminalChild(e_1003_p2_s17_A1, 2, 1, 6, 1, 2, 1, 1, 2, 2);
		check_e_1003_p2_s17_A1_2_1_6_1_2_1_1_2_2(e_1000_p2_s17_A1_root_c3_c2_c7_c2_c3_c2_c2_c3_c3_t);
	}

	@Test
	public void shouldPartiallyParseExt1000() {
		ADCorpus corpus = getCorpus("ext_1000.ad");
		
		ReferenceQueue<ADCorpus> refQueue = new ReferenceQueue<ADCorpus>();
		PhantomReference<ADCorpus> ref = new PhantomReference<ADCorpus>(corpus, refQueue);
		
		Iterator<Extract> extracts = corpus.extracts();
		assertNotNull(extracts);
		
		Extract e_1000 = checkExtract1000(extracts);
		Iterator<SentenceSet> e_1000_p = getSentenceSets(e_1000);

		Title e_1000_t = checkExtract1000Title(e_1000_p);
		Iterator<Sentence> e_1000_t_sentences = getSentences(e_1000_t);
		Sentence e_1000_t_s1 = checkExtract1000TitleSentence1(e_1000_t_sentences);
		Iterator<Analysis> e_1000_t_s1_analyses = getAnalyses(e_1000_t_s1);
		Analysis e_1000_t_s1_A1 = getNext(e_1000_t_s1_analyses);
		checkAnalysis(e_1000_t_s1_A1, 1);
		
		TerminalNode e_1000_t_s1_A1_root_t = (TerminalNode) getRootNode(e_1000_t_s1_A1, 0);
		check_e_1000_t_s1_A1_0(e_1000_t_s1_A1_root_t);

		assertEquals(e_1000_t_s1_analyses.hasNext(), false);
		assertEquals(e_1000_t_sentences.hasNext(), false);
		
		SentenceSet e_1000_p1 = checkExtract1000Paragraph1(e_1000_p);
		Iterator<Sentence> e_1000_p1_sentences = getSentences(e_1000_p1);		
		Sentence e_1000_p1_s2 = checkExtract1000Paragraph1Sentence1(e_1000_p1_sentences);
		Iterator<Analysis> e_1000_p1_s2_analyses = getAnalyses(e_1000_p1_s2);
		// A1
		Analysis e_1000_p1_s2_A11 = getNext(e_1000_p1_s2_analyses);
		checkAnalysis(e_1000_p1_s2_A11, 1);
		Analysis e_1000_p1_s2_A1 = e_1000_p1_s2_A11;
		NonTerminalNode e_1000_p1_s2_A1_root_nt = (NonTerminalNode) getRootNode(e_1000_p1_s2_A1, 0);
		check_e_1000_p1_s2_A1_0(e_1000_p1_s2_A1_root_nt);

		NonTerminalNode e_1000_p1_s2_A1_root_c1_nt = getNonTerminalChild(e_1000_p1_s2_A1_root_nt, 0);
		check_e_1000_p1_s2_A1_0_0(e_1000_p1_s2_A1_root_c1_nt);

		Iterator<Node> e_1000_p1_s2_A1_root_c1_children = getChildren(e_1000_p1_s2_A1_root_c1_nt);
		TerminalNode e_1000_p1_s2_A1_root_c1_c1_t = getNextChild(e_1000_p1_s2_A1_root_c1_children, TerminalNode.class);
		check_e_1000_p1_s2_A1_0_0_0(e_1000_p1_s2_A1_root_c1_c1_t);

		TerminalNode e_1000_p1_s2_A1_root_c1_c2_t = getNextChild(e_1000_p1_s2_A1_root_c1_children, TerminalNode.class);
		check_e_1000_p1_s2_A1_0_0_1(e_1000_p1_s2_A1_root_c1_c2_t);

		NonTerminalNode e_1000_p1_s2_A1_root_c1_c3_nt = getNextChild(e_1000_p1_s2_A1_root_c1_children, NonTerminalNode.class);
		check_e_1000_p1_s2_A1_0_0_2(e_1000_p1_s2_A1_root_c1_c3_nt);
		
		Iterator<Node> e_1000_p1_s2_A1_root_c1_c3_children = getChildren(e_1000_p1_s2_A1_root_c1_c3_nt);
		TerminalNode e_1000_p1_s2_A1_root_c1_c3_c1_t = getNextChild(e_1000_p1_s2_A1_root_c1_c3_children, TerminalNode.class);
		check_e_1000_p1_s2_A1_0_0_2_0(e_1000_p1_s2_A1_root_c1_c3_c1_t);

		NonTerminalNode e_1000_p1_s2_A1_root_c1_c3_c2_nt = getNextChild(e_1000_p1_s2_A1_root_c1_c3_children, NonTerminalNode.class);
		check_e_1000_p1_s2_A1_0_0_2_1(e_1000_p1_s2_A1_root_c1_c3_c2_nt);
		
		Iterator<Node> e_1000_p1_s2_A1_root_c1_c3_c2_children = getChildren(e_1000_p1_s2_A1_root_c1_c3_c2_nt);
		NonTerminalNode e_1000_p1_s2_A1_root_c1_c3_c2_c1_nt = getNextChild(e_1000_p1_s2_A1_root_c1_c3_c2_children, NonTerminalNode.class);
		check_e_1000_p1_s2_A1_0_0_2_1_0(e_1000_p1_s2_A1_root_c1_c3_c2_c1_nt);

		Iterator<Node> e_1000_p1_s2_A1_root_c1_c3_c2_c1_children = getChildren(e_1000_p1_s2_A1_root_c1_c3_c2_c1_nt);
		TerminalNode e_1000_p1_s2_A1_root_c1_c3_c2_c1_c1_t = getNextChild(e_1000_p1_s2_A1_root_c1_c3_c2_c1_children, TerminalNode.class);
		check_e_1000_p1_s2_A1_0_0_2_1_0_0(e_1000_p1_s2_A1_root_c1_c3_c2_c1_c1_t);
		
		TerminalNode e_1000_p1_s2_A1_root_c1_c3_c2_c1_c2_t = getNextChild(e_1000_p1_s2_A1_root_c1_c3_c2_c1_children, TerminalNode.class);
		check_e_1000_p1_s2_A1_0_0_2_1_0_1(e_1000_p1_s2_A1_root_c1_c3_c2_c1_c2_t);
		
		assertEquals(e_1000_p1_s2_A1_root_c1_c3_c2_c1_children.hasNext(), false);
		
		TerminalNode e_1000_p1_s2_A1_root_c1_c3_c2_c2_t = getNextChild(e_1000_p1_s2_A1_root_c1_c3_c2_children, TerminalNode.class);
		check_e_1000_p1_s2_A1_0_0_2_1_1(e_1000_p1_s2_A1_root_c1_c3_c2_c2_t);
		
		while(extracts.hasNext())
			extracts.next();
		
		corpus = null;
		extracts = null;
		
		System.gc();
		System.gc();
		System.gc();

		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
		}
		
		assertSame(refQueue.poll(), ref);
	}

	@Test
	public void shouldFullyParseExt1000() {
		ADCorpus corpus = getCorpus("ext_1000.ad");
		fullyParse(corpus);
	}

	@Test
	public void shouldFullyParseExt1001() {
		ADCorpus corpus = getCorpus("ext_1001.ad");
		fullyParse(corpus);
	}

	@Test
	public void shouldFullyParseExt1001_1003() {
		ADCorpus corpus = getCorpus("ext_1001-1003.ad");
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

	@Test(enabled = false)
	public void shouldParseFlorestaVirgem() {
		ADCorpus corpus = getCorpus("/pt_BR/FlorestaVirgem_CF.txt");
		assertEquals(fullyParse(corpus), 12408);
	}
	
	@Test(enabled = false)
	public void shouldParseAmazonia() {
		ADCorpus corpus = getCorpus("/pt_BR/Amazonia_CF.txt");
		assertEquals(fullyParse(corpus), 4838);
	}
	
	private int fullyParse(ADCorpus corpus) {
		int f_extracts = 0;
		
		for (Extract extract : corpus) {
			f_extracts++;
			
			fullyParse(extract);
		}
		
		return f_extracts;
	}

	private void fullyParse(Extract extract) {
		for (SentenceSet sentenceSet : extract) {
			fullyParse(sentenceSet);
		}
	}

	private void fullyParse(SentenceSet sentenceSet) {
		for (Sentence sentence : sentenceSet) {
			fullyParse(sentence);
		}
	}

	private void fullyParse(Sentence sentence) {
		for (Analysis analysis : sentence) {
			fullyParse(analysis);
		}
	}

	private void fullyParse(Analysis analysis) {
		for (Node node : analysis) {
			fullyParse(node);
		}
	}
	
	private void fullyParse(Node node) {
		for (Node child : node) {
			fullyParse(child);
		}
	}

	private void check_e_1000_t_s1_A1_0(TerminalNode e_1000_t_s1_A1_root_t) {
		// X:n("consolação" <act> F S)	Consolação
		checkTerminalNode(e_1000_t_s1_A1_root_t, 0, "X", "n", "Consolação", "\"consolação\"", "<act>", "F", "S");
	}

	private void check_e_1000_p1_s2_A1_0(NonTerminalNode e_1000_p1_s2_A1_root_nt) {
		// STA:fcl
		checkNonTerminalNode(e_1000_p1_s2_A1_root_nt, 0, "STA", "fcl");
	}

	private void check_e_1000_p1_s2_A1_0_0(NonTerminalNode e_1000_p1_s2_A1_root_c1_nt) {
		// =S:np
		checkNonTerminalNode(e_1000_p1_s2_A1_root_c1_nt, 1, "S", "np");
	}

	private void check_e_1000_p1_s2_A1_0_0_0(TerminalNode e_1000_p1_s2_A1_root_c1_c1_t) {
		// ==DN:pron-indef("o" <artd> DET F P)	As
		checkTerminalNode(e_1000_p1_s2_A1_root_c1_c1_t, 2, "DN", "pron-indef", "As", "\"o\"", "<artd>", "DET", "F", "P");
	}

	private void check_e_1000_p1_s2_A1_0_0_1(TerminalNode e_1000_p1_s2_A1_root_c1_c2_t) {
		// ==H:n("seleção" <HH> F P)	seleções
		checkTerminalNode(e_1000_p1_s2_A1_root_c1_c2_t, 2, "H", "n", "seleções", "\"seleção\"", "<HH>", "F", "P");
	}

	private void check_e_1000_p1_s2_A1_0_0_2(NonTerminalNode e_1000_p1_s2_A1_root_c1_c3_nt) {
		// ==DN:pp
		checkNonTerminalNode(e_1000_p1_s2_A1_root_c1_c3_nt, 2, "DN", "pp");
	}

	private void check_e_1000_p1_s2_A1_0_0_2_0(TerminalNode e_1000_p1_s2_A1_root_c1_c3_c1_t) {
		// ===H:prp("de" <sam-> <np-close>)	de
		checkTerminalNode(e_1000_p1_s2_A1_root_c1_c3_c1_t, 3, "H", "prp", "de", "\"de\"", "<sam->", "<np-close>");
	}

	private void check_e_1000_p1_s2_A1_0_0_2_1(NonTerminalNode e_1000_p1_s2_A1_root_c1_c3_c2_nt) {
		// ===DP:par
		checkNonTerminalNode(e_1000_p1_s2_A1_root_c1_c3_c2_nt, 3, "DP", "par");
	}

	private void check_e_1000_p1_s2_A1_0_0_2_1_0(NonTerminalNode e_1000_p1_s2_A1_root_c1_c3_c2_c1_nt) {
		// ====CJT:np
		checkNonTerminalNode(e_1000_p1_s2_A1_root_c1_c3_c2_c1_nt, 4, "CJT", "np");
	}

	private void check_e_1000_p1_s2_A1_0_0_2_1_0_0(TerminalNode e_1000_p1_s2_A1_root_c1_c3_c2_c1_c1_t) {
		// =====DN:pron-indef("o" <artd> <-sam> DET M S)	o
		checkTerminalNode(e_1000_p1_s2_A1_root_c1_c3_c2_c1_c1_t, 5, "DN", "pron-indef", "o", "\"o\"", "<artd>", "<-sam>", "DET", "M", "S");
	}

	private void check_e_1000_p1_s2_A1_0_0_2_1_0_1(TerminalNode e_1000_p1_s2_A1_root_c1_c3_c2_c1_c2_t) {
		// =====H:prop("Brasil" <cjt-head> <civ> M S)	Brasil
		checkTerminalNode(e_1000_p1_s2_A1_root_c1_c3_c2_c1_c2_t, 5, "H", "prop", "Brasil", "\"Brasil\"", "<cjt-head>", "<civ>", "M", "S");
	}

	private void check_e_1000_p1_s2_A1_0_0_2_1_1(TerminalNode e_1000_p1_s2_A1_root_c1_c3_c2_c2_t) {
		// ====,
		checkTerminalNodeNoInfo(e_1000_p1_s2_A1_root_c1_c3_c2_c2_t, 4, null, null, ",");
	}

	private void check_e_1000_p1_s2_A1_1(TerminalNode e_1000_p1_s2_A1_2ndroot_t) {
		// .
		checkTerminalNodeNoInfo(e_1000_p1_s2_A1_2ndroot_t, 0, null, null, ".");
	}

	private void check_e_1000_p1_s3_A1_0(TerminalNode e_1000_p1_s3_A1_root_c1_t) {
		// X:n("lugar" <L> M P)	lugares
		checkTerminalNode(e_1000_p1_s3_A1_root_c1_t, 0, "X", "n", "lugares", "\"lugar\"", "<L>", "M", "P");
	}

	private void check_e_1000_p1_s2_A1_0_4_1_1_0(TerminalNode e_1000_p2_s4_A1_root_c1_c4_c2_c1_t) {
		// ====DN:pron-indef("o" <artd> DET F P)	as
		checkTerminalNode(e_1000_p2_s4_A1_root_c1_c4_c2_c1_t, 4, "DN", "pron-indef", "as", "\"o\"", "<artd>", "DET", "F", "P");
	}

	private void check_e_1003_p2_s17_A1_2_1_6_1_2_1_1_2_2(TerminalNode e_1000_p2_s17_A1_root_c3_c2_c7_c2_c3_c2_c2_c3_c3_t) {
		// =========DN:adj("brasileiro" <nat> <np-close> M S)	brasileiro
		checkTerminalNode(e_1000_p2_s17_A1_root_c3_c2_c7_c2_c3_c2_c2_c3_c3_t, 9, "DN", "adj", "brasileiro", "\"brasileiro\"", "<nat>", "<np-close>", "M", "S");
	}

	private Sentence checkExtract1000Paragraph1Sentence1(Iterator<Sentence> e_1000_p1_sentences) {
		// <s id="2" ref="CF1000-2" source="CETENFolha id=1000 cad=Esporte sec=des sem=94a">
		// SOURCE: ref="CF1000-2" source="CETENFolha id=1000 cad=Esporte sec=des sem=94a"
		// CF1000-2 As seleções do Brasil, Cuba, Coréia do Sul, Angola, Argentina e Egito, eliminadas na primeira fase, participarão do torneio de consolação, em Hamilton, que definirá as colocações do nono ao 16o.

		Sentence e_1000_p1_s2 = getNext(e_1000_p1_sentences);
		assertNotNull(e_1000_p1_s2);

		assertEquals(e_1000_p1_s2.getId(), "2");
		assertEquals(e_1000_p1_s2.getRef(), "CF1000-2");
		assertEquals(e_1000_p1_s2.getSource(), "CETENFolha id=1000 cad=Esporte sec=des sem=94a");
		assertEquals(e_1000_p1_s2.getText(), "As seleções do Brasil, Cuba, Coréia do Sul, Angola, Argentina e Egito, eliminadas na primeira fase, participarão do torneio de consolação, em Hamilton, que definirá as colocações do nono ao 16o.");
		
		return e_1000_p1_s2;
	}

	private SentenceSet checkExtract1000Paragraph1(Iterator<SentenceSet> e_1000_p) {
		SentenceSet e_1000_p1 = getNext(e_1000_p);
		assertNotNull(e_1000_p1);
		
		return e_1000_p1;
	}

	private void checkAnalysis(Analysis A1, int index) {
		assertNotNull(A1);
		assertEquals(A1.getIndex(), index);
	}

	private Sentence checkExtract1000TitleSentence1(Iterator<Sentence> e_1000_t_sentences) {
		// <s id="1" ref="CF1000-1" source="CETENFolha id=1000 cad=Esporte sec=des sem=94a">
		// SOURCE: ref="CF1000-1" source="CETENFolha id=1000 cad=Esporte sec=des sem=94a"
		// CF1000-1 Consolação
		Sentence e_1000_t_s1 = getNext(e_1000_t_sentences);
		
		assertNotNull(e_1000_t_s1);
		
		assertEquals(e_1000_t_s1.getId(), "1");
		assertEquals(e_1000_t_s1.getRef(), "CF1000-1");
		assertEquals(e_1000_t_s1.getSource(), "CETENFolha id=1000 cad=Esporte sec=des sem=94a");
		assertEquals(e_1000_t_s1.getText(), "Consolação");
		
		return e_1000_t_s1;
	}

	private Title checkExtract1000Title(Iterator<SentenceSet> e_1000_p) {
		Title e_1000_t = getNext(e_1000_p, Title.class);
		assertNotNull(e_1000_t);
		
		return e_1000_t;
	}

	private Extract checkExtract1000(Iterator<Extract> extracts) {
		// <ext id=1000 cad="Esporte" sec="des" sem="94a">
		Extract e_1000 = getNext(extracts);
		
		assertNotNull(e_1000);
		
		assertEquals(e_1000.getAttributes().get("id"), "1000");
		assertEquals(e_1000.getAttributes().get("cad"), "Esporte");
		assertEquals(e_1000.getAttributes().get("sec"), "des");
		assertEquals(e_1000.getAttributes().get("sem"), "94a");

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

	private Iterator<Analysis> getAnalyses(Sentence sentence) {
		Iterator<Analysis> analyses = sentence.analyses();
		assertNotNull(analyses);
		
		return analyses;
	}

	private Iterator<Node> getChildren(Node node) {
		Iterator<Node> children = node.children();
		assertNotNull(children);
		return children;
	}

	private Iterator<SentenceSet> getSentenceSets(Extract extract) {
		Iterator<SentenceSet> sentenceSets = extract.sentenceSets();
		assertNotNull(sentenceSets);
		
		return sentenceSets;
	}

	private <T> T getNext(Iterator<T> iterator) {
		assertEquals(iterator.hasNext(), true);
		return iterator.next();	
	}
	
	private <T> T getNext(Iterator<? super T> iterator, Class<T> clazz) {
		return cast(clazz, getNext(iterator));
	}
	
	private Iterator<Sentence> getSentences(SentenceSet sentenceSet) {
		Iterator<Sentence> sentences = sentenceSet.sentences();
		assertNotNull(sentences);

		return sentences;
	}

	private <T extends Node> T getNextChild(Iterator<Node> children, Class<T> clazz) {
		Node child = getNext(children);
		
		assertTrue(clazz.isInstance(child));
		return clazz.cast(child);
	}

	private void checkTerminalNode(TerminalNode node, int depth, String function, String form, String text, String... info) {
		checkTerminalNode0(node, depth, function, form, text);
		
		String[] node_info = node.getInfo();
		assertNotNull(node_info);
		assertEquals(node_info, info);
		
		checkNoChildren(node);
	}

	private void checkTerminalNodeNoInfo(TerminalNode node, int depth, String function, String form, String text) {
		checkTerminalNode0(node, depth, function, form, text);
		
		String[] node_info = node.getInfo();
		assertNull(node_info);
		
		checkNoChildren(node);
	}

	private void checkTerminalNode0(TerminalNode node, int depth,
			String function, String form, String text) {
		checkNode(node, depth, function, form);
		
		assertTrue(node instanceof TerminalNode);
		TerminalNode node_t = (TerminalNode) node;
		assertEquals(node_t.getText(), text);
	}

	private NonTerminalNode checkNonTerminalNode(Node node, int depth, String function, String form) {
		checkNode(node, depth, function, form);
		assertNull(node.getInfo());

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

	private Node getRootNode(Analysis analysis, int n) {
		Iterator<Node> children = analysis.children();
		assertNotNull(children);
		
		for (int i = 0; i < n; i++)
			getNextChild(children, Node.class);

		return getNextChild(children, Node.class);
	}

	private NonTerminalNode getNonTerminalChild(Analysis analysis, int... n) {
		Node rootNode = getRootNode(analysis, 0);
		NonTerminalNode e_1000_p1_s2_A1_root_nt = (NonTerminalNode) rootNode;
		return getNonTerminalChild(e_1000_p1_s2_A1_root_nt, n);
	}
	
	private TerminalNode getTerminalChild(Analysis analysis, int... n) {
		Node rootNode = getRootNode(analysis, 0);
		NonTerminalNode e_1000_p1_s2_A1_root_nt = (NonTerminalNode) rootNode;
		return getTerminalChild(e_1000_p1_s2_A1_root_nt, n);
	}

	private Analysis getAnalysis(Iterator<Extract> extracts, int n_extract, int n_paragraph, int n_sentence, int n_analysis) {
		Sentence sentence = getSentence(extracts, n_extract, n_paragraph, n_sentence);

		Iterator<Analysis> analyses = getAnalyses(sentence);
		for (int i = 0; i < n_analysis; i++)
			getNext(analyses);
		Analysis analysis = getNext(analyses);

		return analysis;
	}

	private Sentence getSentence(Iterator<Extract> extracts, int n_extract, int n_set, int n_sentence) {
		SentenceSet sentenceSet = getSentenceSet(extracts, n_extract, n_set);

		Iterator<Sentence> sentences = getSentences(sentenceSet);
		for (int i = 0; i < n_sentence; i++)
			getNext(sentences);
		Sentence sentence = getNext(sentences);
		
		return sentence;
	}

	private SentenceSet getSentenceSet(Iterator<Extract> extracts, int n_extract, int n_set) {
		for (int i = 0; i < n_extract; i++)
			getNext(extracts);
		Extract extract = getNext(extracts);

		Iterator<SentenceSet> sentenceSets = getSentenceSets(extract);
		for (int i = 0; i < n_set; i++)
			getNext(sentenceSets);
		SentenceSet sentenceSet = getNext(sentenceSets);
		
		return sentenceSet;
	}

	private <T extends SentenceSet> T getSentenceSet(Iterator<Extract> extracts, int n_extract, int n_set, Class<T> clazz) {
		SentenceSet sentenceSet = getSentenceSet(extracts, n_extract, n_set);
		
		return cast(clazz, sentenceSet);
	}

	private <T> T cast(Class<T> clazz, Object obj) {
		assertTrue(clazz.isInstance(obj));
		
		return clazz.cast(obj);
	}	
}
