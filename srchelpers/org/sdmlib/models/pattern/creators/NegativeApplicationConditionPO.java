package org.sdmlib.models.pattern.creators;

import org.sdmlib.models.pattern.PatternObject;
import org.sdmlib.models.pattern.NegativeApplicationCondition;
import org.sdmlib.models.pattern.AttributeConstraint;
import org.sdmlib.models.pattern.creators.NegativeApplicationConditionSet;

public class NegativeApplicationConditionPO extends PatternObject
{
   public NegativeApplicationConditionPO hasCurrentNAC(NegativeApplicationCondition value)
   {
      AttributeConstraint constr = (AttributeConstraint) new AttributeConstraint()
      .withAttrName(NegativeApplicationCondition.PROPERTY_CURRENTNAC)
      .withTgtValue(value)
      .withSrc(this)
      .withPattern(this.getPattern());
      
      this.getPattern().findMatch();
      
      return this;
   }
   
   public NegativeApplicationConditionPO withCurrentNAC(NegativeApplicationCondition value)
   {
      if (this.getPattern().getHasMatch())
      {
         ((NegativeApplicationCondition) getCurrentMatch()).withCurrentNAC(value);
      }
      return this;
   }
   
   public NegativeApplicationConditionPO hasModifier(String value)
   {
      AttributeConstraint constr = (AttributeConstraint) new AttributeConstraint()
      .withAttrName(NegativeApplicationCondition.PROPERTY_MODIFIER)
      .withTgtValue(value)
      .withSrc(this)
      .withPattern(this.getPattern());
      
      this.getPattern().findMatch();
      
      return this;
   }
   
   public NegativeApplicationConditionPO withModifier(String value)
   {
      if (this.getPattern().getHasMatch())
      {
         ((NegativeApplicationCondition) getCurrentMatch()).withModifier(value);
      }
      return this;
   }
   
   public NegativeApplicationConditionPO hasHasMatch(boolean value)
   {
      AttributeConstraint constr = (AttributeConstraint) new AttributeConstraint()
      .withAttrName(NegativeApplicationCondition.PROPERTY_HASMATCH)
      .withTgtValue(value)
      .withSrc(this)
      .withPattern(this.getPattern());
      
      this.getPattern().findMatch();
      
      return this;
   }
   
   public NegativeApplicationConditionPO withHasMatch(boolean value)
   {
      if (this.getPattern().getHasMatch())
      {
         ((NegativeApplicationCondition) getCurrentMatch()).withHasMatch(value);
      }
      return this;
   }
   
   public NegativeApplicationCondition getCurrentNAC()
   {
      if (this.getPattern().getHasMatch())
      {
         return ((NegativeApplicationCondition) getCurrentMatch()).getCurrentNAC();
      }
      return null;
   }
   
   public String getModifier()
   {
      if (this.getPattern().getHasMatch())
      {
         return ((NegativeApplicationCondition) getCurrentMatch()).getModifier();
      }
      return null;
   }
   
   public boolean getHasMatch()
   {
      if (this.getPattern().getHasMatch())
      {
         return ((NegativeApplicationCondition) getCurrentMatch()).getHasMatch();
      }
      return false;
   }
   
   public NegativeApplicationConditionPO hasName(String value)
   {
      AttributeConstraint constr = (AttributeConstraint) new AttributeConstraint()
      .withAttrName(NegativeApplicationCondition.PROPERTY_NAME)
      .withTgtValue(value)
      .withSrc(this)
      .withModifier(this.getPattern().getModifier())
      .withPattern(this.getPattern());
      
      this.getPattern().findMatch();
      
      return this;
   }
   
   public String getName()
   {
      if (this.getPattern().getHasMatch())
      {
         return ((NegativeApplicationCondition) getCurrentMatch()).getName();
      }
      return null;
   }
   
}



