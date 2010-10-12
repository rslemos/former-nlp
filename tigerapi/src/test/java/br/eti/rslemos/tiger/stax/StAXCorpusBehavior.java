package br.eti.rslemos.tiger.stax;


import java.io.UnsupportedEncodingException;

import br.eti.rslemos.tiger.Corpus;
import br.eti.rslemos.tiger.CorpusBehavior;
import br.eti.rslemos.tiger.TigerException;
import br.eti.rslemos.tiger.TigerInputMother;

public class StAXCorpusBehavior extends CorpusBehavior {

	@Override
	public Corpus getStraightCorpus()
	throws UnsupportedEncodingException, TigerException {
		return new StAXCorpus(TigerInputMother.getInputStraightTiger());
	}

	@Override
	public void shouldFullyReadFeatures() throws Throwable {
		// TODO Auto-generated method stub
		super.shouldFullyReadFeatures();
	}

	@Override
	public void shouldHaveFeatures() throws Throwable {
		// TODO Auto-generated method stub
		super.shouldHaveFeatures();
	}

	@Override
	public void shouldHaveValuedFeature() throws Throwable {
		// TODO Auto-generated method stub
		super.shouldHaveValuedFeature();
	}

	@Override
	public void shouldReadId() throws Throwable {
		// TODO Auto-generated method stub
		super.shouldReadId();
	}

	@Override
	public void shouldReadMetadata() throws Throwable {
		// TODO Auto-generated method stub
		super.shouldReadMetadata();
	}


}
