package org.sdmlib.model.classes.test.creators;

import org.sdmlib.model.classes.test.Kid;
import org.sdmlib.model.classes.test.Parent;
import org.sdmlib.serialization.interfaces.EntityFactory;
import org.sdmlib.serialization.json.JsonIdMap;

public class KidCreator extends EntityFactory
{
   private final String[] properties = new String[]
   {
      Parent.PROPERTY_NAME,
      Parent.PROPERTY_UNCLE,
   };
   
   public String[] getProperties()
   {
      return properties;
   }
   
   public Object getSendableInstance(boolean reference)
   {
      return new Kid();
   }
   
   public Object getValue(Object target, String attrName)
   {
      return ((Kid) target).get(attrName);
   }
   
   public boolean setValue(Object target, String attrName, Object value, String type)
   {
      return ((Kid) target).set(attrName, value);
   }
   
   public static JsonIdMap createIdMap(String sessionID)
   {
      return CreatorCreator.createIdMap(sessionID);
   }

   
   //==========================================================================
   
   @Override
   public void removeObject(Object entity)
   {
      ((Kid) entity).removeYou();
   }
}


