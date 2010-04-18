package br.eti.rslemos.brill.events;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public final class ObserverUtils {
	private ObserverUtils () {}
	
	public static void fireNotification(List<?> listeners, Method method, Object prototype) {
		for (Object listener : listeners) {
			try {
				method.invoke(listener, prototype);
			} catch (InvocationTargetException e) {
				// swallow
			} catch (Exception e) {
				throw (Error)(new LinkageError().initCause(e));
			}
		}
	}
}
