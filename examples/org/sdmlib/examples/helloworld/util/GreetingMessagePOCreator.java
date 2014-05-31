package org.sdmlib.examples.helloworld.util;

import org.sdmlib.models.pattern.util.PatternObjectCreator;

import de.uniks.networkparser.json.JsonIdMap;

public class GreetingMessagePOCreator extends PatternObjectCreator
{
   @Override
   public Object getSendableInstance(boolean reference)
   {
      return new GreetingMessagePO();
   }
   
   public static JsonIdMap createIdMap(String sessionID)
   {
      return CreatorCreator.createIdMap(sessionID);
   }
}
