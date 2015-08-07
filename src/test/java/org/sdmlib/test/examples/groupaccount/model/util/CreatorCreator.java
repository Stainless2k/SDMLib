package org.sdmlib.test.examples.groupaccount.model.util;

import org.sdmlib.serialization.SDMLibJsonIdMap;

import de.uniks.networkparser.json.JsonIdMap;

class CreatorCreator{

   public static JsonIdMap createIdMap(String sessionID)
   {
      JsonIdMap jsonIdMap = (JsonIdMap) new SDMLibJsonIdMap().withSessionId(sessionID);
      
      jsonIdMap.withCreator(new org.sdmlib.test.examples.groupaccount.model.util.GroupAccountCreator());
      jsonIdMap.withCreator(new org.sdmlib.test.examples.groupaccount.model.util.GroupAccountPOCreator());
      jsonIdMap.withCreator(new org.sdmlib.test.examples.groupaccount.model.util.PersonCreator());
      jsonIdMap.withCreator(new org.sdmlib.test.examples.groupaccount.model.util.PersonPOCreator());
      jsonIdMap.withCreator(new org.sdmlib.test.examples.groupaccount.model.util.ItemCreator());
      jsonIdMap.withCreator(new org.sdmlib.test.examples.groupaccount.model.util.ItemPOCreator());

      jsonIdMap.withCreator(new GroupAccountCreator());
      jsonIdMap.withCreator(new GroupAccountPOCreator());
      jsonIdMap.withCreator(new PersonCreator());
      jsonIdMap.withCreator(new PersonPOCreator());
      jsonIdMap.withCreator(new ItemCreator());
      jsonIdMap.withCreator(new ItemPOCreator());
      jsonIdMap.withCreator(new GroupAccountCreator());
      jsonIdMap.withCreator(new GroupAccountPOCreator());
      jsonIdMap.withCreator(new PersonCreator());
      jsonIdMap.withCreator(new PersonPOCreator());
      jsonIdMap.withCreator(new ItemCreator());
      jsonIdMap.withCreator(new ItemPOCreator());
      jsonIdMap.withCreator(new GroupAccountCreator());
      jsonIdMap.withCreator(new GroupAccountPOCreator());
      jsonIdMap.withCreator(new PersonCreator());
      jsonIdMap.withCreator(new PersonPOCreator());
      jsonIdMap.withCreator(new ItemCreator());
      jsonIdMap.withCreator(new ItemPOCreator());
      return jsonIdMap;
   }
}
