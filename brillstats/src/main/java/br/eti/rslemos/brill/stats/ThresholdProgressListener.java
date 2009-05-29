package br.eti.rslemos.brill.stats;

public interface ThresholdProgressListener {

	void initialErrorCount(int errorCount);

	void improvementOf(int improvement);

}
