/* Formato Árvores Deitadas */
/* Doc: http://www.linguateca.pt/Floresta/BibliaFlorestal/ */

grammar AD;

options {
  output = AST; 
	language = Java;
//	charVocabulary = '\u0000'..'\uffff';
}

@parser::header {
  package br.eti.rslemos.ad;
}

@lexer::header{
  package br.eti.rslemos.ad;
}

WS
  : (' ' | '\t' | '\f')+
  ;

NL
  : ('\r' '\n' | '\r' | '\n')
  ;

REST :.;
string
  : '"' (options {greedy=false;} : . )* '"'
  ;

integer
  : ('0'|'1'|'2'|'3'|'4'|'5'|'6'|'7'|'8'|'9')*
  ;

arquivo
  : extrato+ EOF
  ;

extrato
  : '<ext' WS 
      'id=' integer WS 
      'cad=' string WS 
      'sec=' string WS 
      'sem=' string '>' NL 
      
      titulo? 
      paragrafo+
  ;

titulo
  : '<t>' NL sentenca+ '</t>' NL
  ;

paragrafo
  : '<p>' NL sentenca+ '</p>' NL
  ;

sentenca
  : '<s' WS 
      'id=' id=string WS
      'ref=' ref=string WS
      'source=' src=string '>' NL
      
      source
      cabeca
      
      analise+ '</s>' NL
  ;

source
  : 'SOURCE:' WS
      'ref=' string WS
      'source=' string NL
  ;

cabeca
  : (.)(.)(.)(.)(.)(.)(.)(.) WS (~NL)* NL
  ;

analise
  : 'A' integer NL
  ;

