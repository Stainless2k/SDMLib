/*
   Copyright (c) 2015 zuendorf
   
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
   
package org.sdmlib.modelspace.util;

import java.util.Collection;

import org.sdmlib.modelspace.ModelSpace;

import de.uniks.networkparser.list.SimpleSet;
import de.uniks.networkparser.interfaces.Condition;

public class ModelSpaceSet extends SimpleSet<ModelSpace>
{

   public static final ModelSpaceSet EMPTY_SET = new ModelSpaceSet().withFlag(ModelSpaceSet.READONLY);


   public ModelSpacePO hasModelSpacePO()
   {
      return new ModelSpacePO(this.toArray(new ModelSpace[this.size()]));
   }


   public String getEntryType()
   {
      return "org.sdmlib.modelspace.ModelSpace";
   }


   @SuppressWarnings("unchecked")
   public ModelSpaceSet with(Object value)
   {
      if (value instanceof java.util.Collection)
      {
         this.addAll((Collection<ModelSpace>)value);
      }
      else if (value != null)
      {
         this.add((ModelSpace) value);
      }
      
      return this;
   }
   
   public ModelSpaceSet without(ModelSpace value)
   {
      this.remove(value);
      return this;
   }



   public ModelSpacePO filterModelSpacePO()
   {
      return new ModelSpacePO(this.toArray(new ModelSpace[this.size()]));
   }

   public ModelSpaceSet()
   {
      // empty
   }

   public ModelSpaceSet(ModelSpace... objects)
   {
      for (ModelSpace obj : objects)
      {
         this.add(obj);
      }
   }

   public ModelSpaceSet(Collection<ModelSpace> objects)
   {
      this.addAll(objects);
   }


   public ModelSpacePO createModelSpacePO()
   {
      return new ModelSpacePO(this.toArray(new ModelSpace[this.size()]));
   }


   @Override
   public ModelSpaceSet getNewList(boolean keyValue)
   {
      return new ModelSpaceSet();
   }


   public ModelSpaceSet filter(Condition<ModelSpace> condition) {
      ModelSpaceSet filterList = new ModelSpaceSet();
      filterItems(filterList, condition);
      return filterList;
   }}
