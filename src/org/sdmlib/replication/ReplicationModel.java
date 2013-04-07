package org.sdmlib.replication;

import java.net.Socket;

import org.sdmlib.models.classes.ClassModel;
import org.sdmlib.models.classes.Clazz;
import org.sdmlib.models.classes.Role.R;
import org.sdmlib.models.modelsets.StringList;
import org.sdmlib.scenarios.Scenario;

public class ReplicationModel
{
   private static final String CHANGE_HISTORY = "ChangeHistory";
   private static final String REPLICATION_NODE = "ReplicationNode";

   public static void main(String[] args)
   {
      Scenario scenario = new Scenario("src", "ReplicationModel");
      
      ClassModel model = new ClassModel("org.sdmlib.replication");
      
      Clazz thread = model.createClazz(Thread.class.getName()).withExternal(true);
      
      Clazz socket = model.createClazz(Socket.class.getName()).withExternal(true);
      
      Clazz replicationNode = model.createClazz(REPLICATION_NODE);
      
      Clazz sharedSpace = replicationNode.createClassAndAssoc("SharedSpace", "sharedSpaces", R.MANY, "node", R.ONE)
            .withAttributes(
               "spaceId", R.STRING,
               "history", CHANGE_HISTORY, 
               "lastChangeId", R.LONG, 
               "nodeId", R.STRING
                  )
            .withSuperClass(thread);
      
      Clazz replicationChannel = sharedSpace.createClassAndAssoc("ReplicationChannel", "channels", R.MANY, "sharedSpace", R.ONE)
            .withSuperClass(thread)
            .withAttributes("socket", Socket.class.getSimpleName());
      
      Clazz replicationServer = model.createClazz("ReplicationServer").withSuperClass(replicationNode);
      
      Clazz serverSocketAcceptThread = model.createClazz("ServerSocketAcceptThread")
            .withSuperClass(thread)
            .withAttributes(
               "port", R.INT, 
               "replicationNode", REPLICATION_NODE);
      
      Clazz task = model.createClazz("Task");
      
      task.createClassAndAssoc("LogEntry", "logEntries", R.MANY, "task", R.ONE)
         .withAttributes(
            "stepName", R.STRING,
            "executedBy", R.STRING,
            "timeStamp", R.LONG 
               );
      
      Clazz changeHistory = model.createClazz(CHANGE_HISTORY);
      
      Clazz change = changeHistory.createClassAndAssoc("ReplicationChange", "changes", R.MANY, "history", R.ONE)
            .withSuperClass(task)
            .withAttributes(
            "historyIdPrefix", R.STRING,
            "historyIdNumber", R.LONG, 
            "targetObjectId", R.STRING, 
            "targetProperty", R.STRING,
            "isToManyProperty", R.BOOLEAN,
            "changeMsg", R.STRING
               );
      
      model.generate("src");
      
      scenario.addImage(model.dumpClassDiag("src", "ReplicationModel01"));
      
      scenario.dumpHTML();
   }
}