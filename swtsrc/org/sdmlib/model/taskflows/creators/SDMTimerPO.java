package org.sdmlib.model.taskflows.creators;

import org.sdmlib.models.pattern.PatternObject;
import org.sdmlib.model.taskflows.SDMTimer;
import org.sdmlib.model.taskflows.creators.SDMTimerSet;

public class SDMTimerPO extends PatternObject<SDMTimerPO, SDMTimer>
{
   public SDMTimerSet allMatches()
   {
      this.setDoAllMatches(true);
      
      SDMTimerSet matches = new SDMTimerSet();

      while (this.getPattern().getHasMatch())
      {
         matches.add((SDMTimer) this.getCurrentMatch());
         
         this.getPattern().findMatch();
      }
      
      return matches;
   }
   
   
   //==========================================================================
   
   public void schedule(TimerTask p0)
   {
      if (this.getPattern().getHasMatch())
      {
          ((SDMTimer) getCurrentMatch()).schedule( p0);
      }
   }

}
