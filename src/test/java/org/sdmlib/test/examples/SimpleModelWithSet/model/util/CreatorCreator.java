package org.sdmlib.test.examples.SimpleModelWithSet.model.util;

import de.uniks.networkparser.IdMap;

class CreatorCreator{

   public static IdMap createIdMap(String session)
   {
      IdMap jsonIdMap = new IdMap().withSession(session);
      jsonIdMap.with(new PersonCreator());
      jsonIdMap.with(new PersonPOCreator());
      jsonIdMap.with(new ChildCreator());
      jsonIdMap.with(new ChildPOCreator());
      jsonIdMap.with(new SimpleKeyValueListCreator());
      jsonIdMap.with(new SimpleKeyValueListPOCreator());
      return jsonIdMap;
   }
}
