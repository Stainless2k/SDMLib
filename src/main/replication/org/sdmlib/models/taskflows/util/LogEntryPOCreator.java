package org.sdmlib.models.taskflows.util;

import org.sdmlib.models.pattern.util.PatternObjectCreator;
import org.sdmlib.models.taskflows.LogEntry;

import de.uniks.networkparser.json.JsonIdMap;

public class LogEntryPOCreator extends PatternObjectCreator
{
   @Override
   public Object getSendableInstance(boolean reference)
   {
      if(reference) {
          return new LogEntryPO(new LogEntry[]{});
      } else {
          return new LogEntryPO();
      }
   }
   
   public static JsonIdMap createIdMap(String sessionID) {
      return CreatorCreator.createIdMap(sessionID);
   }
}