package org.sdmlib.test.examples.mancala.model.util;

import org.sdmlib.models.pattern.util.PatternObjectCreator;
import org.sdmlib.test.examples.mancala.model.Stone;

import de.uniks.networkparser.IdMap;

public class StonePOCreator extends PatternObjectCreator
{
   @Override
   public Object getSendableInstance(boolean reference)
   {
      if(reference) {
          return new StonePO(new Stone[]{});
      } else {
          return new StonePO();
      }
   }
   
   public static IdMap createIdMap(String sessionID) {
      return org.sdmlib.test.examples.mancala.model.util.CreatorCreator.createIdMap(sessionID);
   }
}
