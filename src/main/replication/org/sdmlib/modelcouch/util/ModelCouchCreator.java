/*
   Copyright (c) 2016 deeptought
   
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
   
package org.sdmlib.modelcouch.util;

import org.sdmlib.modelcouch.CouchDBAdapter;
import org.sdmlib.modelcouch.ModelCouch;
import org.sdmlib.modelcouch.ModelDBListener;

import de.uniks.networkparser.IdMap;
import de.uniks.networkparser.interfaces.SendableEntityCreator;

public class ModelCouchCreator implements SendableEntityCreator
{
   private final String[] properties = new String[]
   {
      ModelCouch.PROPERTY_HOSTNAME,
      ModelCouch.PROPERTY_PORT,
      ModelCouch.PROPERTY_MODELDBLISTENER,
   };
   
   @Override
   public String[] getProperties()
   {
      return properties;
   }
   
   @Override
   public Object getSendableInstance(boolean reference)
   {
      return new ModelCouch(new CouchDBAdapter());
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

      if (ModelCouch.PROPERTY_HOSTNAME.equalsIgnoreCase(attribute))
      {
         return ((ModelCouch) target).getHostName();
      }

      if (ModelCouch.PROPERTY_PORT.equalsIgnoreCase(attribute))
      {
         return ((ModelCouch) target).getPort();
      }

      if (ModelCouch.PROPERTY_MODELDBLISTENER.equalsIgnoreCase(attribute))
      {
         return ((ModelCouch) target).getModelDBListener();
      }
      
      return null;
   }
   
   @Override
   public boolean setValue(Object target, String attrName, Object value, String type)
   {
      if (ModelCouch.PROPERTY_PORT.equalsIgnoreCase(attrName))
      {
         ((ModelCouch) target).withPort(Integer.parseInt(value.toString()));
         return true;
      }

      if (ModelCouch.PROPERTY_HOSTNAME.equalsIgnoreCase(attrName))
      {
         ((ModelCouch) target).withHostName((String) value);
         return true;
      }

      if (REMOVE.equals(type) && value != null)
      {
         attrName = attrName + type;
      }

      if (ModelCouch.PROPERTY_MODELDBLISTENER.equalsIgnoreCase(attrName))
      {
         ((ModelCouch) target).setModelDBListener((ModelDBListener) value);
         return true;
      }
      
      return false;
   }
   public static IdMap createIdMap(String sessionID)
   {
      return org.sdmlib.modelcouch.util.CreatorCreator.createIdMap(sessionID);
   }
   
   //==========================================================================
      public void removeObject(Object entity)
   {
      ((ModelCouch) entity).removeYou();
   }
}
