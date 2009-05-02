package br.eti.rslemos.tiger;

import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;


public interface Terminal {
	String getId();
	Map<QName, String> getFeatures();
	List<? extends SecEdge> getSecEdges();
}