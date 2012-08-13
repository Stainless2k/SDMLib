package org.sdmlib.model.test.superclasses.creators;

import org.sdmlib.serialization.interfaces.SendableEntityCreator;
import org.sdmlib.serialization.json.JsonIdMap;
import org.sdmlib.model.test.superclasses.State;

public class StateCreator implements SendableEntityCreator
{
   private final String[] properties = new String[]
   {
      State.PROPERTY_TEST,
   };
   
   public String[] getProperties()
   {
      return properties;
   }
   
   public Object getSendableInstance(boolean reference)
   {
      return new State();
   }
   
   public Object getValue(Object target, String attrName)
   {
      return ((State) target).get(attrName);
   }
   
   public boolean setValue(Object target, String attrName, Object value, String type)
   {
      return ((State) target).set(attrName, value);
   }
   
   public static JsonIdMap createIdMap(String sessionID)
   {
      return CreatorCreator.createIdMap(sessionID);
   }

   
   //==========================================================================
   
   @Override
   public void removeObject(Object entity)
   {
      ((State) entity).removeYou();
   }
}



