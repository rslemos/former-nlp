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
			throw new NullPointerException("Can't set NULL token tag to '" + tag + "'");
		}
	};
	
	public String getWord();
	public String getTag();
	public void setTag(String tag);
}
