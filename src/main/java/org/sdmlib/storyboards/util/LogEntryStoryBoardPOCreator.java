package org.sdmlib.storyboards.util;

import org.sdmlib.models.pattern.util.PatternObjectCreator;
import org.sdmlib.storyboards.LogEntryStoryBoard;

import de.uniks.networkparser.IdMap;

public class LogEntryStoryBoardPOCreator extends PatternObjectCreator
{
   @Override
   public Object getSendableInstance(boolean reference)
   {
      if(reference) {
          return new LogEntryStoryBoardPO(new LogEntryStoryBoard[]{});
      } else {
          return new LogEntryStoryBoardPO();
      }
   }
   
   public static IdMap createIdMap(String sessionID) {
      return org.sdmlib.storyboards.util.CreatorCreator.createIdMap(sessionID);
   }
}
