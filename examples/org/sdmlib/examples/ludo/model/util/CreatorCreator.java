package org.sdmlib.examples.ludo.model.util;

import de.uniks.networkparser.json.JsonIdMap;
import org.sdmlib.serialization.SDMLibJsonIdMap;

class CreatorCreator{

   public static JsonIdMap createIdMap(String sessionID)
   {
      JsonIdMap jsonIdMap = (JsonIdMap) new SDMLibJsonIdMap().withSessionId(sessionID);
      
      jsonIdMap.withCreator(new org.sdmlib.examples.ludo.model.util.LudoCreator());
      jsonIdMap.withCreator(new org.sdmlib.examples.ludo.model.util.LudoPOCreator());
      jsonIdMap.withCreator(new org.sdmlib.examples.ludo.model.util.PointCreator());
      jsonIdMap.withCreator(new org.sdmlib.examples.ludo.model.util.PointPOCreator());
      jsonIdMap.withCreator(new org.sdmlib.examples.ludo.model.util.PlayerCreator());
      jsonIdMap.withCreator(new org.sdmlib.examples.ludo.model.util.PlayerPOCreator());
      jsonIdMap.withCreator(new org.sdmlib.examples.ludo.model.util.DiceCreator());
      jsonIdMap.withCreator(new org.sdmlib.examples.ludo.model.util.DicePOCreator());
      jsonIdMap.withCreator(new org.sdmlib.examples.ludo.model.util.FieldCreator());
      jsonIdMap.withCreator(new org.sdmlib.examples.ludo.model.util.FieldPOCreator());
      jsonIdMap.withCreator(new org.sdmlib.examples.ludo.model.util.PawnCreator());
      jsonIdMap.withCreator(new org.sdmlib.examples.ludo.model.util.PawnPOCreator());
      jsonIdMap.withCreator(new org.sdmlib.examples.ludo.model.util.LudoColorCreator());
      jsonIdMap.withCreator(new org.sdmlib.examples.ludo.model.util.LudoColorPOCreator());

      return jsonIdMap;
   }
}
