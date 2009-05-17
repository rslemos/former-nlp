package br.eti.rslemos.brill;

public interface Token {
	Token NULL = new Token() {

		public String getTag() {
			return null;
		}

		public String getWord() {
			return null;
		}

		public void setTag(String tag) {
			throw new IllegalStateException("Can't set NULL token tag to '" + tag + "'");
		}
	};
	
	String getWord();
	String getTag();
	void setTag(String tag);
}
