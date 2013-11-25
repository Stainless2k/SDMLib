/*
   Copyright (c) 2013 zuendorf 
   
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
   
package org.sdmlib.model.test.superclasses.creators;

import java.util.LinkedHashSet;

import org.sdmlib.model.test.superclasses.State;
import org.sdmlib.models.modelsets.StringList;

public class StateSet extends LinkedHashSet<State>
{
   public StringList getTest()
   {
      StringList result = new StringList();
      
      for (State obj : this)
      {
         result.add(obj.getTest());
      }
      
      return result;
   }

   public StateSet withTest(String value)
   {
      for (State obj : this)
      {
         obj.withTest(value);
      }
      
      return this;
   }



   public String toString()
   {
      StringList stringList = new StringList();
      
      for (State elem : this)
      {
         stringList.add(elem.toString());
      }
      
      return "(" + stringList.concat(", ") + ")";
   }


   public String getEntryType()
   {
      return "org.sdmlib.model.test.superclasses.State";
   }


   public StateSet with(State value)
   {
      this.add(value);
      return this;
   }
   
   public StateSet without(State value)
   {
      this.remove(value);
      return this;
   }
}




