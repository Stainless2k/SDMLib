package org.sdmlib.test.examples.features.model.all.util;

import org.sdmlib.models.pattern.util.PatternObjectCreator;
import org.sdmlib.test.examples.features.model.all.House;

import de.uniks.networkparser.IdMap;

public class HousePOCreator extends PatternObjectCreator
{
   @Override
   public Object getSendableInstance(boolean reference)
   {
      if(reference) {
          return new HousePO(new House[]{});
      } else {
          return new HousePO();
      }
   }
   
   public static IdMap createIdMap(String sessionID) {
      return org.sdmlib.test.examples.features.model.all.util.CreatorCreator.createIdMap(sessionID);
   }
}
