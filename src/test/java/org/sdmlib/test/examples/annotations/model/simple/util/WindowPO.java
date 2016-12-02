package org.sdmlib.test.examples.annotations.model.simple.util;

import org.sdmlib.models.pattern.PatternObject;
import org.sdmlib.test.examples.annotations.model.simple.House;
import org.sdmlib.test.examples.annotations.model.simple.Window;

public class WindowPO extends PatternObject<WindowPO, Window>
{

    public WindowSet allMatches()
   {
      this.setDoAllMatches(true);
      
      WindowSet matches = new WindowSet();

      while (this.getPattern().getHasMatch())
      {
         matches.add((Window) this.getCurrentMatch());
         
         this.getPattern().findMatch();
      }
      
      return matches;
   }


   public WindowPO(){
      newInstance(null);
   }

   public WindowPO(Window... hostGraphObject) {
      if(hostGraphObject==null || hostGraphObject.length<1){
         return ;
      }
      newInstance(null, hostGraphObject);
   }
   public HousePO filterHouse()
   {
      HousePO result = new HousePO(new House[]{});
      
      result.setModifier(this.getPattern().getModifier());
      super.hasLink(Window.PROPERTY_HOUSE, result);
      
      return result;
   }

   public HousePO createHouse()
   {
      return this.startCreate().filterHouse().endCreate();
   }

   public WindowPO filterHouse(HousePO tgt)
   {
      return hasLinkConstraint(tgt, Window.PROPERTY_HOUSE);
   }

   public WindowPO createHouse(HousePO tgt)
   {
      return this.startCreate().filterHouse(tgt).endCreate();
   }

   public House getHouse()
   {
      if (this.getPattern().getHasMatch())
      {
         return ((Window) this.getCurrentMatch()).getHouse();
      }
      return null;
   }

}
