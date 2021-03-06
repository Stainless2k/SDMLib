package org.sdmlib.replication.util;

import org.sdmlib.models.pattern.util.PatternObjectCreator;
import org.sdmlib.replication.RemoteTaskBoard;

import de.uniks.networkparser.IdMap;

public class RemoteTaskBoardPOCreator extends PatternObjectCreator
{
   @Override
   public Object getSendableInstance(boolean reference)
   {
      if(reference) {
          return new RemoteTaskBoardPO(new RemoteTaskBoard[]{});
      } else {
          return new RemoteTaskBoardPO();
      }
   }
   
   public static IdMap createIdMap(String sessionID) {
      return CreatorCreator.createIdMap(sessionID);
   }
}
