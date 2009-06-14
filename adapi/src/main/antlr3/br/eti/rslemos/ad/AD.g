/* Formato Árvores Deitadas */
/* Source: http://www.linguateca.pt/Floresta/doc/BNFfloresta.html */
/* Doc: http://www.linguateca.pt/Floresta/BibliaFlorestal/ */

grammar AD;

options {
  output = AST; 
	language = Java;
}

@header {
  package br.eti.rslemos.ad;
}

@lexer::header{
  package br.eti.rslemos.ad;
}

//<bosque>:== ( <ext <id ext> <árvores>+ </ext> )+
bosque
  : ( '<ext' id_ext arvores+ '</ext>' )+
  ;

//<árvores>:== <p> <árvore corpo>+ </p> | <árvore título>
arvores
  : '<p>' arvore_corpo+ '</p>' 
  | arvore_titulo
  ;

//<árvore corpo>:== <s> <letreiro> <planta> </s>
arvore_corpo
  : '<s>' letreiro planta '</s>'
  ;

//<árvore título>:== <t> <letreiro> <planta> </t>
arvore_titulo
  : '<t>' letreiro planta '</t>'
  ;

//<letreiro>:== \n\nSOURCE <id ext> <id frase> <frase> <meta anotação>* \n
letreiro
  : '\n\n' 'SOURCE' id_ext id_frase frase meta_anotacao* '\n'
  ;

frase
  :
  ;

//<id ext>:== n= <número natural> sec= <palavra> sem= 9(1|2|3|4|5|6|7|8)(a|b) >
id_ext
  : 'n=' numero_natural 'sec=' palavra 'sem=' '9'('1'|'2'|'3'|'4'|'5'|'6'|'7'|'8')('a'|'b') '>'
  ;

//<id frase>:== C <número natural> - <número natural>
id_frase
  : 'C' numero_natural '-' numero_natural
  ;

//<meta anotação>:== #W | #E | #S
meta_anotacao
  : '#W'
  | '#E'
  | '#S'
  ;

//<planta>:== A1\n <árvore> ( &&\nA2\n <árvore> ( &&\nA3\n <árvore>)* )*
planta
  : 'A1' '\n' arvore ( '&&' '\n' 'A2' '\n' arvore ( '&&' '\n' 'A3' '\n' arvore )* )*
  ;

//<árvore>:== <raiz> <galho>+
arvore
  : raiz galho+
  ;

//<raiz>:==<tipo de frase> : <forma da frase>
raiz
  : tipo_de_frase ':' forma_da_frase
  ;

//<galho>:== =* (<ramo florido> | <ramo nu> | <nódulo>)
galho
  : '='* ( ramo_florido | ramo_nu | nodulo )
  ;

//<ramo florido> := = <tronco> \t <palavra>
ramo_florido
  : tronco '\t' palavra
  ;

//<ramo nu> := = -* <função> : <forma> <subcat>* -*
ramo_nu
  : '-'* funcao ':' forma subcat* '-'*
  ;

//<nódulo>:= =  -- | . | , | : | ; | ? | ! | ) | ( | "« | "» | ' | [ | ]  | /
nodulo
  : '--'
  | '.'
  | ','
  | ':'
  | ';'
  | '?'
  | '!'
  | ')'
  | '('
  | '"«'
  | '"»'
  | '\''
  | '['
  | ']'
  | '/'
  ;

//<tronco>:== <função> : <forma> ( ( <morfologia> ) )* | <função> : <forma> ( '  <lema> ' <morfologia>*  )
tronco
  : funcao ':' forma ( '(' morfologia ')' )*
  | funcao ':' forma '(' '\'' lema '\'' morfologia* ')'
  ;
 
//<função>:== <ramificação>*  <etiqueta de função>
funcao
  : ramificacao* etiqueta_de_funcao
  ;

//<ramificação>:== / <etiqueta de função> [ ( + | - ) <número natural> ]
ramificacao
  : '/' etiqueta_de_funcao '[' ( '+'|'-') numero_natural ']'
  ;

//<etiqueta de função>:== ? | ACC | N< | P< | CJT | PIV | H | >N | >A | ... (consulte-se a lista das etiquetas de função)
etiqueta_de_funcao
  : '?'
  | 'ACC' 
  | 'N<' 
  | 'P<' 
  | 'CJT' 
  | 'PIV' 
  | 'H' 
  | '>N' 
  | '>A'
  ;

//<subcat>:== ( ( <pcp> | <postmod> | <error> )+ )
subcat
  : '(' ( '<pcp>' | '<postmod>' | '<error>' )+ ')'
  ;

//<forma>:==  ? | np | vp | ajp | n | v-inf | art | cu | pron-pers | adv | ... (consulte-se a lista das etiquetas de forma)
forma
  : '?' 
  | 'np' 
  | 'vp' 
  | 'ajp' 
  | 'n' 
  | 'v-inf' 
  | 'art' 
  | 'cu' 
  | 'pron-pers' 
  | 'adv'
  ;

//<morfologia>:== <etiqueta secundária>* <informação flexional>  (consulte-se a lista das etiquetas secundárias)
morfologia
  : etiqueta_secundaria* informacao_flexional
  ;

etiqueta_secundaria
  : '<artd>'
  ;

//<informação flexional>:== M S | F S |  PR 3P IND | FUT 1/3S SUBJ | M/F S/P | ...  (consulte-se a lista de informação morfológica)
informacao_flexional
  : 'M S' 
  | 'F S' 
  | 'PR 3P IND' 
  | 'FUT 1/3S SUBJ' 
  | 'M/F S/P'
  ;

//<tipo de frase>:== UTT | STA | QUE | COM | EXC | ?
tipo_de_frase
  : 'UTT' 
  | 'STA' 
  | 'QUE' 
  | 'COM' 
  | 'EXC' 
  | '?'
  ;

//<forma da frase>:== fcl | icl | acl | cu | np | vp
forma_da_frase
  : 'fcl' 
  | 'icl' 
  | 'acl' 
  | 'cu' 
  | 'np' 
  | 'vp'
  ;

//estas definições foram inferidas a partir da Floresta Virgem, pois não estão na documentação do formato
palavra
  : (
	    '_' 
	  )+
  ; 

/*
      'a'..'z' | 'A'..'Z' 
    | '0'..'9'
    | '_'
    | 'á'..'ú' | 'Á'..'Ú'
    | 'â'..'û' | 'Â'..'Û' 
    | 'à'..'ù' | 'À'..'Ù'
    | 'ä'..'ü' | 'Ä'..'Ü'
    | 'ã' | 'õ' | 'Ã' | 'Õ' | 'ñ' | 'Ñ'
    | 'ç' | 'Ç'

*/

// em tese o lema é sempre em letras minúsculas
// porém as maiúsculas devem fazer parte por causa dos nomes próprios
lema
  : palavra
  ;

fragment
Digit :
  '0'..'9';

numero_natural
  : Digit+
  ;
