package org.sdmlib.models.pattern.creators;

import org.sdmlib.models.pattern.creators.PatternObjectCreator;

public class LinkConstraintPOCreator extends PatternObjectCreator
{
   public Object getSendableInstance(boolean reference)
   {
      return new LinkConstraintPO();
   }
   
   public Object getValue(Object target, String attrName)
   {
      return ((LinkConstraintPO) target).get(attrName);
   }
   
   public boolean setValue(Object target, String attrName, Object value)
   {
      return ((LinkConstraintPO) target).set(attrName, value);
   }
}
