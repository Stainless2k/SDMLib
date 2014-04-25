package org.sdmlib.models.classes.util;

import org.sdmlib.models.classes.Clazz;
import org.sdmlib.models.classes.DataType;
import org.sdmlib.models.classes.Method;
import org.sdmlib.models.pattern.AttributeConstraint;
import org.sdmlib.models.pattern.LinkConstraint;
import org.sdmlib.models.pattern.PatternObject;

public class MethodPO extends PatternObject<MethodPO, Method>
{
   public MethodPO startNAC()
   {
      return (MethodPO) super.startNAC();
   }
   
   public MethodPO endNAC()
   {
      return (MethodPO) super.endNAC();
   }
   
   public MethodSet allMatches()
   {
      MethodSet matches = new MethodSet();

      while (this.getPattern().getHasMatch())
      {
         matches.add((Method) this.getCurrentMatch());
         
         this.getPattern().findMatch();
      }
      
      return matches;
   }
   
   public MethodPO hasSignature(String value)
   {
      AttributeConstraint constr = (AttributeConstraint) new AttributeConstraint()
      .withAttrName(Method.PROPERTY_PARAMETERS)
      .withTgtValue(value)
      .withSrc(this)
      .withModifier(this.getPattern().getModifier())
      .withPattern(this.getPattern());
      
      this.getPattern().findMatch();
      
      return this;
   }
   
   public AttributeSet getSignature()
   {
      if (this.getPattern().getHasMatch())
      {
         return ((Method) getCurrentMatch()).getParameters();
      }
      return null;
   }
   
   public MethodPO hasReturnType(String value)
   {
      AttributeConstraint constr = (AttributeConstraint) new AttributeConstraint()
      .withAttrName(Method.PROPERTY_RETURNTYPE)
      .withTgtValue(value)
      .withSrc(this)
      .withModifier(this.getPattern().getModifier())
      .withPattern(this.getPattern());
      
      this.getPattern().findMatch();
      
      return this;
   }
   
   public DataType getReturnType()
   {
      if (this.getPattern().getHasMatch())
      {
         return ((Method) getCurrentMatch()).getReturnType();
      }
      return null;
   }
   
   public ClazzPO hasClazz()
   {
      ClazzPO result = new ClazzPO();
      result.setModifier(this.getPattern().getModifier());
      
      super.hasLink(Method.PROPERTY_CLAZZ, result);
      
      return result;
   }
   
   public MethodPO hasClazz(ClazzPO tgt)
   {
      LinkConstraint patternLink = (LinkConstraint) new LinkConstraint()
      .withTgt(tgt).withTgtRoleName(Method.PROPERTY_CLAZZ)
      .withSrc(this)
      .withModifier(this.getPattern().getModifier());
      
      this.getPattern().addToElements(patternLink);
      
      this.getPattern().findMatch();
      
      return this;
   }
   
   public Clazz getClazz()
   {
      if (this.getPattern().getHasMatch())
      {
         return ((Method) this.getCurrentMatch()).getClazz();
      }
      return null;
   }
   
   public MethodPO hasBody(String value)
   {
      AttributeConstraint constr = (AttributeConstraint) new AttributeConstraint()
      .withAttrName(Method.PROPERTY_BODY)
      .withTgtValue(value)
      .withSrc(this)
      .withModifier(this.getPattern().getModifier())
      .withPattern(this.getPattern());
      
      this.getPattern().findMatch();
      
      return this;
   }
   
   public String getBody()
   {
      if (this.getPattern().getHasMatch())
      {
         return ((Method) getCurrentMatch()).getBody();
      }
      return null;
   }
   
   public MethodPO withBody(String value)
   {
      if (this.getPattern().getHasMatch())
      {
         ((Method) getCurrentMatch()).setBody(value);
      }
      return this;
   }
   
   public MethodPO hasSignature(String lower, String upper)
   {
      AttributeConstraint constr = (AttributeConstraint) new AttributeConstraint()
      .withAttrName(Method.PROPERTY_PARAMETERS)
      .withTgtValue(lower)
      .withUpperTgtValue(upper)
      .withSrc(this)
      .withModifier(this.getPattern().getModifier())
      .withPattern(this.getPattern());
      
      this.getPattern().findMatch();
      
      return this;
   }
   
   public MethodPO hasReturnType(String lower, String upper)
   {
      AttributeConstraint constr = (AttributeConstraint) new AttributeConstraint()
      .withAttrName(Method.PROPERTY_RETURNTYPE)
      .withTgtValue(lower)
      .withUpperTgtValue(upper)
      .withSrc(this)
      .withModifier(this.getPattern().getModifier())
      .withPattern(this.getPattern());
      
      this.getPattern().findMatch();
      
      return this;
   }
   
   public MethodPO hasBody(String lower, String upper)
   {
      AttributeConstraint constr = (AttributeConstraint) new AttributeConstraint()
      .withAttrName(Method.PROPERTY_BODY)
      .withTgtValue(lower)
      .withUpperTgtValue(upper)
      .withSrc(this)
      .withModifier(this.getPattern().getModifier())
      .withPattern(this.getPattern());
      
      this.getPattern().findMatch();
      
      return this;
   }
   
   public MethodPO createSignature(String value)
   {
      this.startCreate().hasSignature(value).endCreate();
      return this;
   }
   
   public MethodPO createReturnType(String value)
   {
      this.startCreate().hasReturnType(value).endCreate();
      return this;
   }
   
   public MethodPO createBody(String value)
   {
      this.startCreate().hasBody(value).endCreate();
      return this;
   }
   
   public ClazzPO createClazz()
   {
      return this.startCreate().hasClazz().endCreate();
   }

   public MethodPO createClazz(ClazzPO tgt)
   {
      return this.startCreate().hasClazz(tgt).endCreate();
   }

}




