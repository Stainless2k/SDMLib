package org.sdmlib.models.taskflows.util;

import java.util.Timer;

import org.sdmlib.models.pattern.util.PatternObjectCreator;

import de.uniks.networkparser.IdMap;

public class TimerPOCreator extends PatternObjectCreator
{
   @Override
   public Object getSendableInstance(boolean reference)
   {
      if(reference) {
          return new TimerPO(new Timer[]{});
      } else {
          return new TimerPO();
      }
   }
   
   public static IdMap createIdMap(String sessionID) {
      return CreatorCreator.createIdMap(sessionID);
   }
}
