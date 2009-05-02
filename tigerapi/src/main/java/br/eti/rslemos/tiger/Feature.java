package br.eti.rslemos.tiger;

import java.util.List;

public interface Feature {
	String getName();
	Domain getDomain();
	List<? extends FeatureValue> getValues();
}