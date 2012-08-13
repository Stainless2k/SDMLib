package org.sdmlib.models.objects.creators;

import org.sdmlib.models.objects.creators.CreatorCreator;
import org.sdmlib.serialization.interfaces.SendableEntityCreator;
import org.sdmlib.serialization.json.JsonIdMap;
import org.sdmlib.models.objects.GenericObject;

public class GenericObjectCreator implements SendableEntityCreator
{
   private final String[] properties = new String[]
   {
      GenericObject.PROPERTY_NAME,
      GenericObject.PROPERTY_TYPE,
      GenericObject.PROPERTY_GRAPH,
      GenericObject.PROPERTY_ATTRS,
      GenericObject.PROPERTY_OUTGOINGLINKS,
      GenericObject.PROPERTY_INCOMMINGLINKS,
   };
   
   public String[] getProperties()
   {
      return properties;
   }
   
   public Object getSendableInstance(boolean reference)
   {
      return new GenericObject();
   }
   
   public Object getValue(Object target, String attrName)
   {
      return ((GenericObject) target).get(attrName);
   }
   
   public boolean setValue(Object target, String attrName, Object value, String type)
   {
      return ((GenericObject) target).set(attrName, value);
   }
   
   public static JsonIdMap createIdMap(String sessionID)
   {
      return CreatorCreator.createIdMap(sessionID);
   }

   
   //==========================================================================
   
   @Override
   public void removeObject(Object entity)
   {
      ((GenericObject) entity).removeYou();
   }
}


