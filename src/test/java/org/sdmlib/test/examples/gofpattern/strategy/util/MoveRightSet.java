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
   
package org.sdmlib.test.examples.gofpattern.strategy.util;

import java.util.Collection;

import org.sdmlib.test.examples.gofpattern.strategy.BombermanStrategy;
import org.sdmlib.test.examples.gofpattern.strategy.MoveRight;

import de.uniks.networkparser.list.ObjectSet;
import de.uniks.networkparser.list.SimpleSet;
import de.uniks.networkparser.interfaces.Condition;

public class MoveRightSet extends SimpleSet<MoveRight>
{

   public static final MoveRightSet EMPTY_SET = new MoveRightSet().withFlag(MoveRightSet.READONLY);


   public MoveRightPO hasMoveRightPO()
   {
      return new MoveRightPO(this.toArray(new MoveRight[this.size()]));
   }


   public String getEntryType()
   {
      return "org.sdmlib.test.examples.gofpattern.strategy.MoveRight";
   }


   @SuppressWarnings("unchecked")
   public MoveRightSet with(Object value)
   {
      if (value instanceof java.util.Collection)
      {
         this.addAll((Collection<MoveRight>)value);
      }
      else if (value != null)
      {
         this.add((MoveRight) value);
      }
      
      return this;
   }
   
   public MoveRightSet without(MoveRight value)
   {
      this.remove(value);
      return this;
   }

   public BombermanStrategySet getSuccessor()
   {
      BombermanStrategySet result = new BombermanStrategySet();
      
      for (MoveRight obj : this)
      {
         result.add(obj.getSuccessor());
      }
      
      return result;
   }

   public MoveRightSet hasSuccessor(Object value)
   {
      ObjectSet neighbors = new ObjectSet();

      if (value instanceof Collection)
      {
         neighbors.addAll((Collection<?>) value);
      }
      else
      {
         neighbors.add(value);
      }
      
      MoveRightSet answer = new MoveRightSet();
      
      for (MoveRight obj : this)
      {
         if (neighbors.contains(obj.getSuccessor()))
         {
            answer.add(obj);
         }
      }
      
      return answer;
   }


   public BombermanStrategySet getSuccessorTransitive()
   {
      BombermanStrategySet todo = new BombermanStrategySet().with(this);
      
      BombermanStrategySet result = new BombermanStrategySet();
      
      while ( ! todo.isEmpty())
      {
         BombermanStrategy current = todo.first();
         
         todo.remove(current);
         
         if ( ! result.contains(current))
         {
            result.add(current);
            
            if ( ! result.contains(current.getSuccessor()))
            {
               todo.with(current.getSuccessor());
            }
         }
      }
      
      return result;
   }

   public MoveRightSet withSuccessor(BombermanStrategy value)
   {
      for (MoveRight obj : this)
      {
         obj.withSuccessor(value);
      }
      
      return this;
   }



   public MoveRightPO filterMoveRightPO()
   {
      return new MoveRightPO(this.toArray(new MoveRight[this.size()]));
   }

   public MoveRightSet()
   {
      // empty
   }

   public MoveRightSet(MoveRight... objects)
   {
      for (MoveRight obj : objects)
      {
         this.add(obj);
      }
   }

   public MoveRightSet(Collection<MoveRight> objects)
   {
      this.addAll(objects);
   }


   public MoveRightPO createMoveRightPO()
   {
      return new MoveRightPO(this.toArray(new MoveRight[this.size()]));
   }


   @Override
   public MoveRightSet getNewList(boolean keyValue)
   {
      return new MoveRightSet();
   }


   public MoveRightSet filter(Condition<MoveRight> condition) {
      MoveRightSet filterList = new MoveRightSet();
      filterItems(filterList, condition);
      return filterList;
   }}
