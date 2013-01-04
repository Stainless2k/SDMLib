/*
   Copyright (c) 2012 zuendorf 
   
   Permission is hereby granted, free of charge, to any person obtaining a copy of this software 
   and associated documentation files (the "Software"), to deal in the Software without restriction, 
   including without limitation the rights to use, copy, modify, merge, publish, distribute, 
   sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is 
   furnished to do so, subject to the following conditions: 
   
   The above copyright notice and this permission notice shall be included in all copies or 
   substantial portions of the Software. 
   
   The Software shall be used for Good, not Evil. 
   
   THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING 
   BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND 
   NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, 
   DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, 
   OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE. 
 */
   
package org.sdmlib.examples.chats.creators;

import java.util.LinkedHashSet;
import org.sdmlib.examples.chats.DrawPointFlow;
import org.sdmlib.models.modelsets.intList;
import java.util.List;
import org.sdmlib.examples.chats.PeerToPeerChat;
import org.sdmlib.models.modelsets.StringList;
import org.sdmlib.serialization.json.creators.SDMLibJsonIdMapSet;
import org.sdmlib.serialization.json.SDMLibJsonIdMap;
import org.sdmlib.model.taskflows.TaskFlow;
import org.sdmlib.model.taskflows.creators.TaskFlowSet;

public class DrawPointFlowSet extends LinkedHashSet<DrawPointFlow>
{
   public intList getX()
   {
      intList result = new intList();
      
      for (DrawPointFlow obj : this)
      {
         result.add(obj.getX());
      }
      
      return result;
   }

   public DrawPointFlowSet withX(int value)
   {
      for (DrawPointFlow obj : this)
      {
         obj.withX(value);
      }
      
      return this;
   }

   public intList getY()
   {
      intList result = new intList();
      
      for (DrawPointFlow obj : this)
      {
         result.add(obj.getY());
      }
      
      return result;
   }

   public DrawPointFlowSet withY(int value)
   {
      for (DrawPointFlow obj : this)
      {
         obj.withY(value);
      }
      
      return this;
   }

   public PeerToPeerChatSet getGui()
   {
      PeerToPeerChatSet result = new PeerToPeerChatSet();
      
      for (DrawPointFlow obj : this)
      {
         result.add(obj.getGui());
      }
      
      return result;
   }

   //==========================================================================
   
   public DrawPointFlowSet run()
   {
      for (DrawPointFlow obj : this)
      {
         obj.run();
      }
      return this;
   }

   public intList getTaskNo()
   {
      intList result = new intList();
      
      for (DrawPointFlow obj : this)
      {
         result.add(obj.getTaskNo());
      }
      
      return result;
   }

   public DrawPointFlowSet withTaskNo(int value)
   {
      for (DrawPointFlow obj : this)
      {
         obj.withTaskNo(value);
      }
      
      return this;
   }

   public DrawPointFlowSet withGui(PeerToPeerChat value)
   {
      for (DrawPointFlow obj : this)
      {
         obj.withGui(value);
      }
      
      return this;
   }

   public intList getR()
   {
      intList result = new intList();
      
      for (DrawPointFlow obj : this)
      {
         result.add(obj.getR());
      }
      
      return result;
   }

   public DrawPointFlowSet withR(int value)
   {
      for (DrawPointFlow obj : this)
      {
         obj.withR(value);
      }
      
      return this;
   }

   public intList getG()
   {
      intList result = new intList();
      
      for (DrawPointFlow obj : this)
      {
         result.add(obj.getG());
      }
      
      return result;
   }

   public DrawPointFlowSet withG(int value)
   {
      for (DrawPointFlow obj : this)
      {
         obj.withG(value);
      }
      
      return this;
   }

   public intList getB()
   {
      intList result = new intList();
      
      for (DrawPointFlow obj : this)
      {
         result.add(obj.getB());
      }
      
      return result;
   }

   public DrawPointFlowSet withB(int value)
   {
      for (DrawPointFlow obj : this)
      {
         obj.withB(value);
      }
      
      return this;
   }



   public String toString()
   {
      StringList stringList = new StringList();
      
      for (DrawPointFlow elem : this)
      {
         stringList.add(elem.toString());
      }
      
      return "(" + stringList.concat(", ") + ")";
   }


   public DrawPointFlowSet with(DrawPointFlow value)
   {
      this.add(value);
      return this;
   }
   
   public DrawPointFlowSet without(DrawPointFlow value)
   {
      this.remove(value);
      return this;
   }
   public SDMLibJsonIdMapSet getIdMap()
   {
      SDMLibJsonIdMapSet result = new SDMLibJsonIdMapSet();
      
      for (DrawPointFlow obj : this)
      {
         result.add(obj.getIdMap());
      }
      
      return result;
   }

   public DrawPointFlowSet withIdMap(SDMLibJsonIdMap value)
   {
      for (DrawPointFlow obj : this)
      {
         obj.withIdMap(value);
      }
      
      return this;
   }



   public String getEntryType()
   {
      return "org.sdmlib.examples.chats.DrawPointFlow";
   }
   public TaskFlowSet getSubFlow()
   {
      TaskFlowSet result = new TaskFlowSet();
      
      for (DrawPointFlow obj : this)
      {
         result.add(obj.getSubFlow());
      }
      
      return result;
   }

   public DrawPointFlowSet withSubFlow(TaskFlow value)
   {
      for (DrawPointFlow obj : this)
      {
         obj.withSubFlow(value);
      }
      
      return this;
   }

   public TaskFlowSet getParent()
   {
      TaskFlowSet result = new TaskFlowSet();
      
      for (DrawPointFlow obj : this)
      {
         result.add(obj.getParent());
      }
      
      return result;
   }

   public DrawPointFlowSet withParent(TaskFlow value)
   {
      for (DrawPointFlow obj : this)
      {
         obj.withParent(value);
      }
      
      return this;
   }

}










