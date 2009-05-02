package br.eti.rslemos.tiger;

import java.util.List;

public interface Match {
	String getSubgraph();
	List<? extends Variable> getVariables();
}