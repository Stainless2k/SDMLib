package org.sdmlib.storyboards.creators;

import org.sdmlib.storyboards.creators.CreatorCreator;
import org.sdmlib.serialization.interfaces.EntityFactory;
import org.sdmlib.serialization.json.JsonIdMap;
import org.sdmlib.storyboards.Storyboard;

public class StoryboardCreator extends EntityFactory
{
   private final String[] properties = new String[]
   {
      Storyboard.PROPERTY_STORYBOARDSTEPS,
      Storyboard.PROPERTY_WALL,
      Storyboard.PROPERTY_ROOTDIR,
      Storyboard.PROPERTY_STEPCOUNTER,
   };
   
   public String[] getProperties()
   {
      return properties;
   }
   
   public Object getSendableInstance(boolean reference)
   {
      return new Storyboard();
   }
   
   public Object getValue(Object target, String attrName)
   {
      return ((Storyboard) target).get(attrName);
   }
   
   public boolean setValue(Object target, String attrName, Object value, String type)
   {
      if (JsonIdMap.REMOVE.equals(type))
      {
         attrName = attrName + type;
      }
      return ((Storyboard) target).set(attrName, value);
   }
   
   public static JsonIdMap createIdMap(String sessionID)
   {
      return CreatorCreator.createIdMap(sessionID);
   }

   
   //==========================================================================
   
   @Override
   public void removeObject(Object entity)
   {
      ((Storyboard) entity).removeYou();
   }
}




