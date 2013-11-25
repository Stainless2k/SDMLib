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
   
package org.sdmlib.models.patterns.example.creators;

import java.util.LinkedHashSet;
import org.sdmlib.models.patterns.example.Node;
import org.sdmlib.models.modelsets.StringList;
import org.sdmlib.models.modelsets.intList;
import java.util.List;
import org.sdmlib.models.patterns.example.creators.SimpleStateSet;
import org.sdmlib.models.patterns.example.SimpleState;
import org.sdmlib.models.patterns.example.creators.NodeSet;

public class NodeSet extends LinkedHashSet<Node> implements org.sdmlib.models.modelsets.ModelSet
{


   public String toString()
   {
      StringList stringList = new StringList();
      
      for (Node elem : this)
      {
         stringList.add(elem.toString());
      }
      
      return "(" + stringList.concat(", ") + ")";
   }


   public String getEntryType()
   {
      return "org.sdmlib.models.patterns.example.Node";
   }


   public NodeSet with(Node value)
   {
      this.add(value);
      return this;
   }
   
   public NodeSet without(Node value)
   {
      this.remove(value);
      return this;
   }
   public intList getNum()
   {
      intList result = new intList();
      
      for (Node obj : this)
      {
         result.add(obj.getNum());
      }
      
      return result;
   }

   public NodeSet withNum(int value)
   {
      for (Node obj : this)
      {
         obj.setNum(value);
      }
      
      return this;
   }

   public SimpleStateSet getGraph()
   {
      SimpleStateSet result = new SimpleStateSet();
      
      for (Node obj : this)
      {
         result.add(obj.getGraph());
      }
      
      return result;
   }

   public NodeSet withGraph(SimpleState value)
   {
      for (Node obj : this)
      {
         obj.withGraph(value);
      }
      
      return this;
   }

   public NodeSet getNext()
   {
      NodeSet result = new NodeSet();
      
      for (Node obj : this)
      {
         result.addAll(obj.getNext());
      }
      
      return result;
   }

   public NodeSet withNext(Node value)
   {
      for (Node obj : this)
      {
         obj.withNext(value);
      }
      
      return this;
   }

   public NodeSet withoutNext(Node value)
   {
      for (Node obj : this)
      {
         obj.withoutNext(value);
      }
      
      return this;
   }

   public NodeSet getPrev()
   {
      NodeSet result = new NodeSet();
      
      for (Node obj : this)
      {
         result.addAll(obj.getPrev());
      }
      
      return result;
   }

   public NodeSet withPrev(Node value)
   {
      for (Node obj : this)
      {
         obj.withPrev(value);
      }
      
      return this;
   }

   public NodeSet withoutPrev(Node value)
   {
      for (Node obj : this)
      {
         obj.withoutPrev(value);
      }
      
      return this;
   }


   public Node first()
   {
      for (Node n : this)
      {
         return n;
      }
      return null;
   }

}


