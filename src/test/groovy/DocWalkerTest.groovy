//package io.jnorthr.tools.properties;

/*
* see: http://code.google.com/p/spock/wiki/SpockBasics
* A spock test wrapper around the base class
*/
import spock.lang.*
import java.text.SimpleDateFormat;

class DocWalkerTest extends Specification 
{
  // fields
  DocWalker pt;
  	    
  @Shared
  String homePath

  
  // Fixture Methods
  // run before every feature method
  def setup() 
  { 
  }          

  // run after every feature method
  def cleanup() {}
  
  // Note: The setupSpec() and cleanupSpec() methods may not reference instance fields.
  // Both run before the first feature method
  def setupSpec() 
  {
  }

  
  // run after the last feature method}
  def cleanupSpec() 
  {
  }   


  // Feature Methods
  // First Test
  def "1st Test: DocWalkerTest"() {
    given: "1st Test: Constructor non-default"
		pt = new DocWalker(".config.properties","./fred");

    when:
        println "... DocWalkerTest1 current input folder"
 
    then:
        pt.getFileName()== null;
  } // end of test


} // end of class  // Spock Set of Tests
