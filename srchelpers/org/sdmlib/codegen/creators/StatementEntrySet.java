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
   
package org.sdmlib.codegen.creators;

import java.util.ArrayList;
import java.util.LinkedHashSet;

import org.sdmlib.codegen.StatementEntry;
import org.sdmlib.models.modelsets.StringList;
import org.sdmlib.models.modelsets.intList;

public class StatementEntrySet extends LinkedHashSet<StatementEntry>
{
   public StringList getKind()
   {
      StringList result = new StringList();
      
      for (StatementEntry obj : this)
      {
         result.add(obj.getKind());
      }
      
      return result;
   }

   public StatementEntrySet withKind(String value)
   {
      for (StatementEntry obj : this)
      {
         obj.withKind(value);
      }
      
      return this;
   }

   public ArrayList<String> getTokenList()
   {
      ArrayList<String> result = new ArrayList<String>();
      
      for (StatementEntry obj : this)
      {
         result.addAll(obj.getTokenList());
      }
      
      return result;
   }

   public StatementEntrySet withTokenList(ArrayList<String> value)
   {
      for (StatementEntry obj : this)
      {
         obj.withTokenList(value);
      }
      
      return this;
   }

   public StringList getAssignTargetVarName()
   {
      StringList result = new StringList();
      
      for (StatementEntry obj : this)
      {
         result.add(obj.getAssignTargetVarName());
      }
      
      return result;
   }

   public StatementEntrySet withAssignTargetVarName(String value)
   {
      for (StatementEntry obj : this)
      {
         obj.withAssignTargetVarName(value);
      }
      
      return this;
   }

   public StatementEntrySet getBodyStats()
   {
      StatementEntrySet result = new StatementEntrySet();
      
      for (StatementEntry obj : this)
      {
         result.addAll(obj.getBodyStats());
      }
      
      return result;
   }
   public StatementEntrySet withBodyStats(StatementEntry value)
   {
      for (StatementEntry obj : this)
      {
         obj.withBodyStats(value);
      }
      
      return this;
   }

   public StatementEntrySet withoutBodyStats(StatementEntry value)
   {
      for (StatementEntry obj : this)
      {
         obj.withoutBodyStats(value);
      }
      
      return this;
   }

   public StatementEntrySet getParent()
   {
      StatementEntrySet result = new StatementEntrySet();
      
      for (StatementEntry obj : this)
      {
         result.add(obj.getParent());
      }
      
      return result;
   }
   public StatementEntrySet withParent(StatementEntry value)
   {
      for (StatementEntry obj : this)
      {
         obj.withParent(value);
      }
      
      return this;
   }



   public String toString()
   {
      StringList stringList = new StringList();
      
      for (StatementEntry elem : this)
      {
         stringList.add(elem.toString());
      }
      
      return "(" + stringList.concat(", ") + ")";
   }


   public String getEntryType()
   {
      return "org.sdmlib.codegen.StatementEntry";
   }


   public StatementEntrySet with(StatementEntry value)
   {
      this.add(value);
      return this;
   }
   
   public StatementEntrySet without(StatementEntry value)
   {
      this.remove(value);
      return this;
   }
   public intList getStartPos()
   {
      intList result = new intList();
      
      for (StatementEntry obj : this)
      {
         result.add(obj.getStartPos());
      }
      
      return result;
   }

   public StatementEntrySet withStartPos(int value)
   {
      for (StatementEntry obj : this)
      {
         obj.withStartPos(value);
      }
      
      return this;
   }

   public intList getEndPos()
   {
      intList result = new intList();
      
      for (StatementEntry obj : this)
      {
         result.add(obj.getEndPos());
      }
      
      return result;
   }

   public StatementEntrySet withEndPos(int value)
   {
      for (StatementEntry obj : this)
      {
         obj.withEndPos(value);
      }
      
      return this;
   }

}



