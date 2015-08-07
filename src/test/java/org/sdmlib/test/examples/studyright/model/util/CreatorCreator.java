package org.sdmlib.test.examples.studyright.model.util;

import org.sdmlib.serialization.SDMLibJsonIdMap;

import de.uniks.networkparser.json.JsonIdMap;

class CreatorCreator{

   public static JsonIdMap createIdMap(String sessionID)
   {
      JsonIdMap jsonIdMap = (JsonIdMap) new SDMLibJsonIdMap().withSessionId(sessionID);
      
      jsonIdMap.withCreator(new org.sdmlib.test.examples.studyright.model.util.LectureCreator());
      jsonIdMap.withCreator(new org.sdmlib.test.examples.studyright.model.util.LecturePOCreator());
      jsonIdMap.withCreator(new org.sdmlib.test.examples.studyright.model.util.RoomCreator());
      jsonIdMap.withCreator(new org.sdmlib.test.examples.studyright.model.util.RoomPOCreator());
      jsonIdMap.withCreator(new org.sdmlib.test.examples.studyright.model.util.UniversityCreator());
      jsonIdMap.withCreator(new org.sdmlib.test.examples.studyright.model.util.UniversityPOCreator());
      jsonIdMap.withCreator(new org.sdmlib.test.examples.studyright.model.util.FemaleCreator());
      jsonIdMap.withCreator(new org.sdmlib.test.examples.studyright.model.util.FemalePOCreator());
      jsonIdMap.withCreator(new org.sdmlib.test.examples.studyright.model.util.ProfessorCreator());
      jsonIdMap.withCreator(new org.sdmlib.test.examples.studyright.model.util.ProfessorPOCreator());
      jsonIdMap.withCreator(new org.sdmlib.test.examples.studyright.model.util.StudentCreator());
      jsonIdMap.withCreator(new org.sdmlib.test.examples.studyright.model.util.StudentPOCreator());

      jsonIdMap.withCreator(new org.sdmlib.test.examples.studyright.model.util.TopicCreator());
      jsonIdMap.withCreator(new org.sdmlib.test.examples.studyright.model.util.TopicPOCreator());
      jsonIdMap.withCreator(new org.sdmlib.test.examples.studyright.model.util.AssignmentCreator());
      jsonIdMap.withCreator(new org.sdmlib.test.examples.studyright.model.util.AssignmentPOCreator());
      jsonIdMap.withCreator(new LectureCreator());
      jsonIdMap.withCreator(new LecturePOCreator());
      jsonIdMap.withCreator(new RoomCreator());
      jsonIdMap.withCreator(new RoomPOCreator());
      jsonIdMap.withCreator(new UniversityCreator());
      jsonIdMap.withCreator(new UniversityPOCreator());
      jsonIdMap.withCreator(new FemaleCreator());
      jsonIdMap.withCreator(new FemalePOCreator());
      jsonIdMap.withCreator(new ProfessorCreator());
      jsonIdMap.withCreator(new ProfessorPOCreator());
      jsonIdMap.withCreator(new StudentCreator());
      jsonIdMap.withCreator(new StudentPOCreator());
      jsonIdMap.withCreator(new ProfessorCreator());
      jsonIdMap.withCreator(new ProfessorPOCreator());
      jsonIdMap.withCreator(new TopicCreator());
      jsonIdMap.withCreator(new TopicPOCreator());
      jsonIdMap.withCreator(new UniversityCreator());
      jsonIdMap.withCreator(new UniversityPOCreator());
      jsonIdMap.withCreator(new StudentCreator());
      jsonIdMap.withCreator(new StudentPOCreator());
      jsonIdMap.withCreator(new RoomCreator());
      jsonIdMap.withCreator(new RoomPOCreator());
      jsonIdMap.withCreator(new AssignmentCreator());
      jsonIdMap.withCreator(new AssignmentPOCreator());
      return jsonIdMap;
   }
}
