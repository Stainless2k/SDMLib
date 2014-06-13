/*
   Copyright (c) 2014 zuendorf 
   
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
   
package org.sdmlib.examples.groupAccount.model.util;

import org.sdmlib.serialization.EntityFactory;
import de.uniks.networkparser.json.JsonIdMap;
import org.sdmlib.examples.groupAccount.model.Person;
import org.sdmlib.examples.groupAccount.model.GroupAccount;
import org.sdmlib.examples.groupAccount.model.Item;

public class PersonCreator extends EntityFactory
{
   private final String[] properties = new String[]
   {
      Person.PROPERTY_NAME,
      Person.PROPERTY_BALANCE,
      Person.PROPERTY_PARENT,
      Person.PROPERTY_ITEMS,
   };
   
   @Override
   public String[] getProperties()
   {
      return properties;
   }
   
   @Override
   public Object getSendableInstance(boolean reference)
   {
      return new Person();
   }
   
   @Override
   public Object getValue(Object target, String attrName)
   {
      int pos = attrName.indexOf('.');
      String attribute = attrName;
      
      if (pos > 0)
      {
         attribute = attrName.substring(0, pos);
      }

      if (Person.PROPERTY_NAME.equalsIgnoreCase(attribute))
      {
         return ((Person) target).getName();
      }

      if (Person.PROPERTY_BALANCE.equalsIgnoreCase(attribute))
      {
         return ((Person) target).getBalance();
      }

      if (Person.PROPERTY_PARENT.equalsIgnoreCase(attribute))
      {
         return ((Person) target).getParent();
      }

      if (Person.PROPERTY_ITEMS.equalsIgnoreCase(attribute))
      {
         return ((Person) target).getItems();
      }
      
      return null;
   }
   
   @Override
   public boolean setValue(Object target, String attrName, Object value, String type)
   {
      if (JsonIdMap.REMOVE.equals(type) && value != null)
      {
         attrName = attrName + type;
      }

      if (Person.PROPERTY_NAME.equalsIgnoreCase(attrName))
      {
         ((Person) target).setName((String) value);
         return true;
      }

      if (Person.PROPERTY_BALANCE.equalsIgnoreCase(attrName))
      {
         ((Person) target).setBalance(Double.parseDouble(value.toString()));
         return true;
      }

      if (Person.PROPERTY_PARENT.equalsIgnoreCase(attrName))
      {
         ((Person) target).setParent((GroupAccount) value);
         return true;
      }

      if (Person.PROPERTY_ITEMS.equalsIgnoreCase(attrName))
      {
         ((Person) target).addToItems((Item) value);
         return true;
      }
      
      if ((Person.PROPERTY_ITEMS + JsonIdMap.REMOVE).equalsIgnoreCase(attrName))
      {
         ((Person) target).removeFromItems((Item) value);
         return true;
      }
      
      return false;
   }
   public static JsonIdMap createIdMap(String sessionID)
   {
      return CreatorCreator.createIdMap(sessionID);
   }
   
   //==========================================================================
   
   @Override
   public void removeObject(Object entity)
   {
      ((Person) entity).removeYou();
   }
}
