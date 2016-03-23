/*
   Copyright (c) 2014 NeTH 
   
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
   
package org.sdmlib.test.examples.mancala.model.util;

import org.sdmlib.serialization.EntityFactory;
import org.sdmlib.test.examples.mancala.model.Mancala;
import org.sdmlib.test.examples.mancala.model.Pit;
import org.sdmlib.test.examples.mancala.model.Player;

import de.uniks.networkparser.IdMap;

public class MancalaCreator extends EntityFactory
{
   private final String[] properties = new String[]
   {
      Mancala.PROPERTY_ACTIVEPLAYER,
      Mancala.PROPERTY_PLAYERS,
      Mancala.PROPERTY_PITS,
   };
   
   @Override
   public String[] getProperties()
   {
      return properties;
   }
   
   @Override
   public Object getSendableInstance(boolean reference)
   {
      return new Mancala();
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

      if (Mancala.PROPERTY_ACTIVEPLAYER.equalsIgnoreCase(attribute))
      {
         return ((Mancala) target).getActivePlayer();
      }

      if (Mancala.PROPERTY_PLAYERS.equalsIgnoreCase(attribute))
      {
         return ((Mancala) target).getPlayers();
      }

      if (Mancala.PROPERTY_PITS.equalsIgnoreCase(attribute))
      {
         return ((Mancala) target).getPits();
      }
      
      return null;
   }
   
   @Override
   public boolean setValue(Object target, String attrName, Object value, String type)
   {
      if (IdMap.REMOVE.equals(type) && value != null)
      {
         attrName = attrName + type;
      }

      if (Mancala.PROPERTY_ACTIVEPLAYER.equalsIgnoreCase(attrName))
      {
         ((Mancala) target).setActivePlayer((Player) value);
         return true;
      }

      if (Mancala.PROPERTY_PLAYERS.equalsIgnoreCase(attrName))
      {
         ((Mancala) target).withPlayers((Player) value);
         return true;
      }
      
      if ((Mancala.PROPERTY_PLAYERS + IdMap.REMOVE).equalsIgnoreCase(attrName))
      {
         ((Mancala) target).withoutPlayers((Player) value);
         return true;
      }

      if (Mancala.PROPERTY_PITS.equalsIgnoreCase(attrName))
      {
         ((Mancala) target).withPits((Pit) value);
         return true;
      }
      
      if ((Mancala.PROPERTY_PITS + IdMap.REMOVE).equalsIgnoreCase(attrName))
      {
         ((Mancala) target).withoutPits((Pit) value);
         return true;
      }
      
      return false;
   }
   public static IdMap createIdMap(String sessionID)
   {
      return org.sdmlib.test.examples.mancala.model.util.CreatorCreator.createIdMap(sessionID);
   }
   
   //==========================================================================
   
   @Override
   public void removeObject(Object entity)
   {
      ((Mancala) entity).removeYou();
   }
}
