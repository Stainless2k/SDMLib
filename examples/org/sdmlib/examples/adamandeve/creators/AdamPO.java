package org.sdmlib.examples.adamandeve.creators;

import org.sdmlib.examples.adamandeve.Adam;
import org.sdmlib.models.pattern.PatternObject;

public class AdamPO extends PatternObject<AdamPO, Adam>
{
   public AdamSet allMatches()
   {
      this.setDoAllMatches(true);
      
      AdamSet matches = new AdamSet();

      while (this.getPattern().getHasMatch())
      {
         matches.add((Adam) this.getCurrentMatch());
         
         this.getPattern().findMatch();
      }
      
      return matches;
   }
   
}

