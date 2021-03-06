package org.sdmlib.test.model.refactoring.util;

import org.sdmlib.models.pattern.util.PatternObjectCreator;
import org.sdmlib.test.model.refactoring.Player;

import de.uniks.networkparser.IdMap;

public class PlayerPOCreator extends PatternObjectCreator
{
   @Override
   public Object getSendableInstance(boolean reference)
   {
      if(reference) {
          return new PlayerPO(new Player[]{});
      } else {
          return new PlayerPO();
      }
   }
   
   public static IdMap createIdMap(String sessionID) {
      return org.sdmlib.test.model.refactoring.util.CreatorCreator.createIdMap(sessionID);
   }
}
