package org.sdmlib.examples.studyrightextends.creators;

import org.sdmlib.examples.studyrightextends.creators.CreatorCreator;
import org.sdmlib.serialization.interfaces.SendableEntityCreator;
import org.sdmlib.serialization.json.JsonIdMap;
import org.sdmlib.examples.studyrightextends.Professor;

public class ProfessorCreator implements SendableEntityCreator
{
   private final String[] properties = new String[]
   {
      Professor.PROPERTY_PERSNR,
      Professor.PROPERTY_NAME,
      Professor.PROPERTY_LECTURE,
   };
   
   public String[] getProperties()
   {
      return properties;
   }
   
   public Object getSendableInstance(boolean reference)
   {
      return new Professor();
   }
   
   public Object getValue(Object target, String attrName)
   {
      return ((Professor) target).get(attrName);
   }
   
   public boolean setValue(Object target, String attrName, Object value, String type)
   {
      return ((Professor) target).set(attrName, value);
   }
   
   public static JsonIdMap createIdMap(String sessionID)
   {
      return CreatorCreator.createIdMap(sessionID);
   }

   
   //==========================================================================
   
   @Override
   public void removeObject(Object entity)
   {
      ((Professor) entity).removeYou();
   }
}


