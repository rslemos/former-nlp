package br.eti.rslemos.brill.creole;

import gate.Resource;
import gate.creole.AbstractProcessingResource;
import gate.creole.ExecutionException;
import gate.creole.ResourceInstantiationException;

public class BrillTaggerPlugin extends AbstractProcessingResource {

	private static final long serialVersionUID = 821447674299169263L;

	public void cleanup() {
	}

	public void execute() throws ExecutionException {
	}

	public Resource init() throws ResourceInstantiationException {
		return this;
	}

	public void reInit() throws ResourceInstantiationException {
	}

}
