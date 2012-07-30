package org.sdmlib.model.test.superclasses.creators;

import org.sdmlib.models.pattern.PatternObject;
import org.sdmlib.model.test.superclasses.State;
import org.sdmlib.models.pattern.AttributeConstraint;

public class StatePO extends PatternObject
{
   public StatePO hasTest(String value)
   {
      AttributeConstraint constr = (AttributeConstraint) new AttributeConstraint()
      .withAttrName(State.PROPERTY_TEST)
      .withTgtValue(value)
      .withSrc(this)
      .withPattern(this.getPattern());
      
      this.getPattern().findMatch();
      
      return this;
   }
   
   public StatePO withTest(String value)
   {
      if (this.getPattern().getHasMatch())
      {
         ((State) getCurrentMatch()).withTest(value);
      }
      return this;
   }
   
}
