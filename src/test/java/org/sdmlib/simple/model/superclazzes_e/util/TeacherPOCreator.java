package org.sdmlib.simple.model.superclazzes_e.util;

import org.sdmlib.models.pattern.util.PatternObjectCreator;
import de.uniks.networkparser.IdMap;
import org.sdmlib.simple.model.superclazzes_e.Teacher;

public class TeacherPOCreator extends PatternObjectCreator
{
   @Override
   public Object getSendableInstance(boolean reference)
   {
      if(reference) {
          return new TeacherPO(new Teacher[]{});
      } else {
          return new TeacherPO();
      }
   }
   
   public static IdMap createIdMap(String sessionID) {
      return org.sdmlib.simple.model.superclazzes_e.util.CreatorCreator.createIdMap(sessionID);
   }
}