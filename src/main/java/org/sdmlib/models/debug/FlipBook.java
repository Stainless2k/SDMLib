package org.sdmlib.models.debug;

import java.beans.PropertyChangeSupport;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.sdmlib.serialization.PropertyChangeInterface;

import de.uniks.networkparser.IdMap;
import de.uniks.networkparser.SimpleEvent;
import de.uniks.networkparser.interfaces.ObjectCondition;
import de.uniks.networkparser.interfaces.SendableEntityCreator;
import de.uniks.networkparser.json.JsonObject;

public class FlipBook implements ObjectCondition, PropertyChangeInterface

{
   public void step()
   {
      long stopHere = 0;
      if (changes.size() >= stopStep) 
      {
         stopHere = stopStep;
      }
      
   }
   
   public FlipBook back()
   {
      if (currentStep <= 0 )
      {
         // already at start
         return this;
      }
      
      StepInfo step = changes.get((int) (currentStep - 1));
      
      // undo step by swapping rem and upd
      JsonObject jo = step.change;

      JsonObject undo = new JsonObject();
      
      undo.put(IdMap.ID, jo.getString(IdMap.ID));
      
      Object update = jo.get(SendableEntityCreator.UPDATE);
      if (update != null)
      {
         // if the value is a JsonObject, just use the id
         JsonObject jsonUpdate = (JsonObject) update;
         String key = jsonUpdate.keyIterator().next();
         Object value = jsonUpdate.get(key);
         
         if (value instanceof JsonObject)
         {
            JsonObject jsonValue = (JsonObject) value;
            
            JsonObject newValue = new JsonObject();
            newValue.put(IdMap.ID, jsonValue.getString(IdMap.ID));
            JsonObject newUpdate = new JsonObject();
            newUpdate.put(key, newValue);
            undo.put(SendableEntityCreator.REMOVE, newUpdate);
         }
         else
         {
            undo.put(SendableEntityCreator.REMOVE, update);
         }
      }
      
      Object remove = jo.get(SendableEntityCreator.REMOVE);
      if (remove != null)
      {
         undo.put(SendableEntityCreator.UPDATE, remove);
      }
      
      setReading(true);
      map.decode(undo);
      setReading(false);
      currentStep--;
      
      return this;
   }
   
   
   public FlipBook back(Object target, String property)
   {
      while (true)
      {
         back();
         
         if (currentStep <= 0)
         {
            return this;
         }
         
         // does current step operate on target?
         StepInfo stepInfo = changes.get((int) (currentStep - 1));
         
         JsonObject jo = stepInfo.change;
         
         String id = jo.getString(IdMap.ID);
         
         Object obj = map.getObject(id);
         
         if (obj == target)
         {
            Object update = jo.get(SendableEntityCreator.UPDATE);
            
            if (update != null)
            {
               JsonObject jsonUpdate = (JsonObject) update;
               
               String key = jsonUpdate.keyIterator().next();
               
               if (key.equals(property))
               {
                  // print stacktrace
                  // stepInfo.e.printStackTrace();
                  return this;
               }
            }
         }
      }
   }
   
   
   public FlipBook storeCurrentStepAsStopStep()
   {
      this.withStopStep(currentStep);
      return this;
   }
   
   
   public FlipBook back(Object target)
   {
      while (true)
      {
         back();
         
         if (currentStep <= 0)
         {
            return this;
         }
         
         // does current step operate on target?
         StepInfo stepInfo = changes.get((int) (currentStep - 1));
         
         JsonObject jo = stepInfo.change;
         
         String id = jo.getString(IdMap.ID);
         
         Object obj = map.getObject(id);
         
         if (obj == target)
         {
            // print stacktrace
            stepInfo.e.printStackTrace();
            return this;
         }
         
      }
   }
   
   public FlipBook printCurrentStackTrace()
   {
      if (currentStep <= 0)
      {
         return this;
      }

      StepInfo stepInfo = changes.get((int) (currentStep -1));
      
      stepInfo.e.printStackTrace();
      return this;
      
   }
   
   public FlipBook back(long steps)
   {
      for (long l = 0; l < steps; l++)
      {
         back();
      }
      return this;
   }
   
   public FlipBook forward(long steps)
   {
      for (long l = 0; l < steps; l++)
      {
         forward();
      }
      return this;
   }
   
   public FlipBook forward()
   {
      if (currentStep >= changes.size() )
      {
         // already at start
         return this;
      }
      
      StepInfo step = changes.get((int) (currentStep));
      
      // redo step
      JsonObject jo = step.change;
      
      setReading(true);
      map.decode(jo);
      setReading(false);
      currentStep++;
      
      return this;
   }
   
   private static IdMap map = null;

   public long stopStep = Long.MAX_VALUE;
   
   public FlipBook withStopStep(long value)
   {
      if (value != stopStep)
      {
         stopStep = value;
         
         // store for reload
         JsonObject jsonObject = new JsonObject();
         jsonObject.put("stopStep", stopStep);
         
         File file = new File("doc");
         file.mkdirs();
         
         file = new File("doc/flibBookStopStep.json");
         
         try
         {
            FileWriter fileWriter = new FileWriter(file);
            
            fileWriter.write(jsonObject.toString() + "/n");
            fileWriter.close();
         }
         catch (IOException e)
         {
            // TODO Auto-generated catch block
            e.printStackTrace();
         }
      }
      return this;
   }
   
   public FlipBook init(IdMap theMap)
   {
      map = theMap;
      
      // read stopStep from file
      File file = new File("doc/flibBookStopStep.json");
      
      try
      {
         FileReader fileReader = new FileReader(file);
         BufferedReader in = new BufferedReader(fileReader);
         String line = in.readLine();
         in.close();
         
         JsonObject jsonObject = new JsonObject().withValue(line);
         
         long value = jsonObject.getInt("stopStep");
         
         stopStep = value;
         
      }
      catch (IOException e)
      {
         // no problem, work with default
      }
      return this;
   }
   
   public long currentStep = -1;
   
   private ArrayList<StepInfo> changes = new ArrayList<StepInfo>();
   
   public boolean update(Object event) {
      if (isReading)
      {
         // do nothing
         return true;
      }
      // store message in list
      SimpleEvent simpleEvent = (SimpleEvent) event;
      if(simpleEvent.isIdEvent()) {
    	  return true;
      }
//      if(simpleEvent.isNewEvent()) {
//    	  return true;
//      }
      if (simpleEvent.getEntity() == null) 
      {
         // looks like a bug in IDMap. It fires an empty property change within 
         // Filter.isPropertyRegard 
         return false;
      }
      JsonObject json = (JsonObject) simpleEvent.getEntity();

      StepInfo stepInfo = new StepInfo(json, new RuntimeException());
      changes.add(stepInfo);
      
      currentStep = changes.size();
      step();
      
      return true;
   }

   boolean isReading = false;
   
   public boolean isReading()
   {
      return isReading;
   }
   
   public void setReading(boolean isReading)
   {
      this.isReading = isReading;
   }
   

   class StepInfo
   {
      public StepInfo(JsonObject jsonObject, RuntimeException runtimeException)
      {
         this.change = jsonObject;
         this.e = runtimeException;
      }
      
      public JsonObject change;
      public Exception e;
   }
   
 //==========================================================================

   protected PropertyChangeSupport listeners = new PropertyChangeSupport(this);

   @Override
   public PropertyChangeSupport getPropertyChangeSupport()
   {
      return listeners;
   }
}
