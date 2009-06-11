package br.eti.rslemos.brill.tracer;

import br.eti.rslemos.tracer.Tracer;

public abstract aspect BrillTracer extends Tracer {
	protected pointcut filter(): within(br.eti.rslemos.brill..*);
}
