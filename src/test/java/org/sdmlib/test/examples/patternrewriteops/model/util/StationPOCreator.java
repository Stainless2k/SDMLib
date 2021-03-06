package org.sdmlib.test.examples.patternrewriteops.model.util;

import org.sdmlib.models.pattern.util.PatternObjectCreator;
import de.uniks.networkparser.IdMap;
import org.sdmlib.test.examples.patternrewriteops.model.Station;

public class StationPOCreator extends PatternObjectCreator
{
   @Override
   public Object getSendableInstance(boolean reference)
   {
      if(reference) {
          return new StationPO(new Station[]{});
      } else {
          return new StationPO();
      }
   }
   
   public static IdMap createIdMap(String sessionID) {
      return org.sdmlib.test.examples.patternrewriteops.model.util.CreatorCreator.createIdMap(sessionID);
   }
}
