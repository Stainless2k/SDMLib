package org.sdmlib.models.objects.creators;

import org.sdmlib.models.pattern.PatternObject;
import org.sdmlib.models.objects.GenericAttribute;
import org.sdmlib.models.pattern.AttributeConstraint;
import org.sdmlib.models.pattern.PatternLink;
import org.sdmlib.models.objects.creators.GenericObjectPO;
import org.sdmlib.models.pattern.LinkConstraint;
import org.sdmlib.models.objects.creators.GenericAttributePO;
import org.sdmlib.models.objects.GenericObject;

public class GenericAttributePO extends PatternObject
{
   public GenericAttributePO startNAC()
   {
      return (GenericAttributePO) super.startNAC();
   }
   
   public GenericAttributePO endNAC()
   {
      return (GenericAttributePO) super.endNAC();
   }
   
   public GenericAttributePO hasName(String value)
   {
      AttributeConstraint constr = (AttributeConstraint) new AttributeConstraint()
      .withAttrName(GenericAttribute.PROPERTY_NAME)
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
         return ((GenericAttribute) getCurrentMatch()).getName();
      }
      return null;
   }
   
   public GenericAttributePO hasValue(String value)
   {
      AttributeConstraint constr = (AttributeConstraint) new AttributeConstraint()
      .withAttrName(GenericAttribute.PROPERTY_VALUE)
      .withTgtValue(value)
      .withSrc(this)
      .withModifier(this.getPattern().getModifier())
      .withPattern(this.getPattern());
      
      this.getPattern().findMatch();
      
      return this;
   }
   
   public String getValue()
   {
      if (this.getPattern().getHasMatch())
      {
         return ((GenericAttribute) getCurrentMatch()).getValue();
      }
      return null;
   }
   
   public GenericObjectPO hasOwner()
   {
      GenericObjectPO result = new GenericObjectPO();
      result.setModifier(this.getPattern().getModifier());
      
      super.hasLink(GenericAttribute.PROPERTY_OWNER, result);
      
      return result;   }
   
   public GenericAttributePO hasOwner(GenericObjectPO tgt)
   {
      LinkConstraint patternLink = (LinkConstraint) new LinkConstraint()
      .withTgt(tgt).withTgtRoleName(GenericAttribute.PROPERTY_OWNER)
      .withSrc(this)
      .withModifier(this.getPattern().getModifier());
      
      this.getPattern().addToElements(patternLink);
      
      this.getPattern().findMatch();
      
      return this;
   }
   
   public GenericObject getOwner()
   {
      if (this.getPattern().getHasMatch())
      {
         return ((GenericAttribute) this.getCurrentMatch()).getOwner();
      }
      return null;
   }
   
}

