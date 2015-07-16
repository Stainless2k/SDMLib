package org.sdmlib.test.examples.simpleEnumModel.model.util;

import org.sdmlib.serialization.SDMLibJsonIdMap;

import de.uniks.networkparser.json.JsonIdMap;

class CreatorCreator{

   public static JsonIdMap createIdMap(String sessionID)
   {
      JsonIdMap jsonIdMap = (JsonIdMap) new SDMLibJsonIdMap().withSessionId(sessionID);
      
      jsonIdMap.withCreator(new org.sdmlib.test.examples.simpleEnumModel.model.util.AlexCreator());
      jsonIdMap.withCreator(new org.sdmlib.test.examples.simpleEnumModel.model.util.AlexPOCreator());
      jsonIdMap.withCreator(new org.sdmlib.test.examples.simpleEnumModel.model.util.MacCreator());
      jsonIdMap.withCreator(new org.sdmlib.test.examples.simpleEnumModel.model.util.MacPOCreator());

      return jsonIdMap;
   }
}