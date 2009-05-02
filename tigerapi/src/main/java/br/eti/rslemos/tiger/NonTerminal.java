package br.eti.rslemos.tiger;

import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;

public interface NonTerminal {
	String getId();
	Map<QName, String> getFeatures();
	List<? extends Edge> getEdges();
	List<? extends SecEdge> getSecEdges();
}