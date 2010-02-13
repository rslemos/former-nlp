

/* First created by JCasGen Sat Nov 14 22:19:57 BRST 2009 */
package br.eti.rslemos.uima;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.tcas.Annotation;


/** 
 * Updated by JCasGen Sun Nov 15 19:01:24 BRST 2009
 * XML source: /home/rslemos/workspace/brilltagger-uima/src/main/resources/MyAnnotator.xml
 * @generated */
public class Word extends Annotation {
  /** @generated
   * @ordered 
   */
  public final static int typeIndexID = JCasRegistry.register(Word.class);
  /** @generated
   * @ordered 
   */
  public final static int type = typeIndexID;
  /** @generated  */
  public              int getTypeIndexID() {return typeIndexID;}
 
  /** Never called.  Disable default constructor
   * @generated */
  protected Word() {}
    
  /** Internal - constructor used by generator 
   * @generated */
  public Word(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated */
  public Word(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated */  
  public Word(JCas jcas, int begin, int end) {
    super(jcas);
    setBegin(begin);
    setEnd(end);
    readObject();
  }   

  /** <!-- begin-user-doc -->
    * Write your own initialization here
    * <!-- end-user-doc -->
  @generated modifiable */
  private void readObject() {}
     
 
    
  //*--------------*
  //* Feature: lemma

  /** getter for lemma - gets 
   * @generated */
  public String getLemma() {
    if (Word_Type.featOkTst && ((Word_Type)jcasType).casFeat_lemma == null)
      jcasType.jcas.throwFeatMissing("lemma", "br.eti.rslemos.uima.Word");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Word_Type)jcasType).casFeatCode_lemma);}
    
  /** setter for lemma - sets  
   * @generated */
  public void setLemma(String v) {
    if (Word_Type.featOkTst && ((Word_Type)jcasType).casFeat_lemma == null)
      jcasType.jcas.throwFeatMissing("lemma", "br.eti.rslemos.uima.Word");
    jcasType.ll_cas.ll_setStringValue(addr, ((Word_Type)jcasType).casFeatCode_lemma, v);}    
  }

    