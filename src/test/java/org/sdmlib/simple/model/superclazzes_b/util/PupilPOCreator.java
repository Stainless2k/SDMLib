package org.sdmlib.simple.model.superclazzes_b.util;

import org.sdmlib.models.pattern.util.PatternObjectCreator;
import de.uniks.networkparser.IdMap;
import org.sdmlib.simple.model.superclazzes_b.Pupil;

public class PupilPOCreator extends PatternObjectCreator
{
   @Override
   public Object getSendableInstance(boolean reference)
   {
      if(reference) {
          return new PupilPO(new Pupil[]{});
      } else {
          return new PupilPO();
      }
   }
   
   public static IdMap createIdMap(String sessionID) {
      return org.sdmlib.simple.model.superclazzes_b.util.CreatorCreator.createIdMap(sessionID);
   }
}