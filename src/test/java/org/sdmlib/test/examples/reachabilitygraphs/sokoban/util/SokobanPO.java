package org.sdmlib.test.examples.reachabilitygraphs.sokoban.util;

import org.sdmlib.models.pattern.PatternObject;
import org.sdmlib.test.examples.reachabilitygraphs.sokoban.Sokoban;
import org.sdmlib.test.examples.reachabilitygraphs.sokoban.util.MazePO;
import org.sdmlib.test.examples.reachabilitygraphs.sokoban.Maze;
import org.sdmlib.test.examples.reachabilitygraphs.sokoban.util.SokobanPO;
import org.sdmlib.test.examples.reachabilitygraphs.sokoban.util.BoxPO;
import org.sdmlib.test.examples.reachabilitygraphs.sokoban.Box;
import org.sdmlib.test.examples.reachabilitygraphs.sokoban.util.BoxSet;
import org.sdmlib.test.examples.reachabilitygraphs.sokoban.util.KarliPO;
import org.sdmlib.test.examples.reachabilitygraphs.sokoban.Karli;

public class SokobanPO extends PatternObject<SokobanPO, Sokoban>
{

    public SokobanSet allMatches()
   {
      this.setDoAllMatches(true);
      
      SokobanSet matches = new SokobanSet();

      while (this.getPattern().getHasMatch())
      {
         matches.add((Sokoban) this.getCurrentMatch());
         
         this.getPattern().findMatch();
      }
      
      return matches;
   }


   public SokobanPO(){
      newInstance(null);
   }

   public SokobanPO(Sokoban... hostGraphObject) {
      if(hostGraphObject==null || hostGraphObject.length<1){
         return ;
      }
      newInstance(null, hostGraphObject);
   }

   public SokobanPO(String modifier)
   {
      this.setModifier(modifier);
   }
   public MazePO createMazePO()
   {
      MazePO result = new MazePO(new Maze[]{});
      
      result.setModifier(this.getPattern().getModifier());
      super.hasLink(Sokoban.PROPERTY_MAZE, result);
      
      return result;
   }

   public MazePO createMazePO(String modifier)
   {
      MazePO result = new MazePO(new Maze[]{});
      
      result.setModifier(modifier);
      super.hasLink(Sokoban.PROPERTY_MAZE, result);
      
      return result;
   }

   public SokobanPO createMazeLink(MazePO tgt)
   {
      return hasLinkConstraint(tgt, Sokoban.PROPERTY_MAZE);
   }

   public SokobanPO createMazeLink(MazePO tgt, String modifier)
   {
      return hasLinkConstraint(tgt, Sokoban.PROPERTY_MAZE, modifier);
   }

   public Maze getMaze()
   {
      if (this.getPattern().getHasMatch())
      {
         return ((Sokoban) this.getCurrentMatch()).getMaze();
      }
      return null;
   }

   public BoxPO createBoxesPO()
   {
      BoxPO result = new BoxPO(new Box[]{});
      
      result.setModifier(this.getPattern().getModifier());
      super.hasLink(Sokoban.PROPERTY_BOXES, result);
      
      return result;
   }

   public BoxPO createBoxesPO(String modifier)
   {
      BoxPO result = new BoxPO(new Box[]{});
      
      result.setModifier(modifier);
      super.hasLink(Sokoban.PROPERTY_BOXES, result);
      
      return result;
   }

   public SokobanPO createBoxesLink(BoxPO tgt)
   {
      return hasLinkConstraint(tgt, Sokoban.PROPERTY_BOXES);
   }

   public SokobanPO createBoxesLink(BoxPO tgt, String modifier)
   {
      return hasLinkConstraint(tgt, Sokoban.PROPERTY_BOXES, modifier);
   }

   public BoxSet getBoxes()
   {
      if (this.getPattern().getHasMatch())
      {
         return ((Sokoban) this.getCurrentMatch()).getBoxes();
      }
      return null;
   }

   public KarliPO createKarliPO()
   {
      KarliPO result = new KarliPO(new Karli[]{});
      
      result.setModifier(this.getPattern().getModifier());
      super.hasLink(Sokoban.PROPERTY_KARLI, result);
      
      return result;
   }

   public KarliPO createKarliPO(String modifier)
   {
      KarliPO result = new KarliPO(new Karli[]{});
      
      result.setModifier(modifier);
      super.hasLink(Sokoban.PROPERTY_KARLI, result);
      
      return result;
   }

   public SokobanPO createKarliLink(KarliPO tgt)
   {
      return hasLinkConstraint(tgt, Sokoban.PROPERTY_KARLI);
   }

   public SokobanPO createKarliLink(KarliPO tgt, String modifier)
   {
      return hasLinkConstraint(tgt, Sokoban.PROPERTY_KARLI, modifier);
   }

   public Karli getKarli()
   {
      if (this.getPattern().getHasMatch())
      {
         return ((Sokoban) this.getCurrentMatch()).getKarli();
      }
      return null;
   }

}
