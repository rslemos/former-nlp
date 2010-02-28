package br.eti.rslemos.brill;

import java.util.LinkedList;

import br.eti.rslemos.tagger.Token;

public class DelayedContext<T> implements Context<T> {
	private static class SetTagCommand<T1> {
		private final Token<T1> token;
		private final T1 tag;

		protected SetTagCommand(Token<T1> token, T1 tag) {
			this.token = token;
			this.tag = tag;
		}
		
		public void setTag() {
			token.setTag(tag);
		}
	}
	
	private Context<T> context;
	private LinkedList<SetTagCommand<T>> commands = new LinkedList<SetTagCommand<T>>();
	
	public DelayedContext(Context<T> context) {
		this.context = context;
	}

	public void commit() {
		for (SetTagCommand<T> command : commands) {
			command.setTag();
		}

		commands.clear();
	}

	public Token<T> getToken(int offset) {
		return delayedToken(context.getToken(offset));
	}

	public boolean hasNext() {
		return context.hasNext();
	}

	public Token<T> next() {
		return delayedToken(context.next());
	}

	public void remove() {
		context.remove();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public DelayedContext<T> clone() {
		try {
			DelayedContext<T> result = (DelayedContext<T>) super.clone();
			result.context = context.clone();
			result.commands = new LinkedList<SetTagCommand<T>>();
			
			return result;
		} catch (CloneNotSupportedException e) {
			throw new Error("Object#clone() threw CloneNotSupportedException", e);
		}
	}

	private Token<T> delayedToken(final Token<T> token) {
		return new Token<T>() {
			public T getTag() {
				return token.getTag();
			}

			public String getWord() {
				return token.getWord();
			}

			public void setTag(T tag) {
				commands.add(new SetTagCommand<T>(token, tag));
			}
			
		};
	}
}