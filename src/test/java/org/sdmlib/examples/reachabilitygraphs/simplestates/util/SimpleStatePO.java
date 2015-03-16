package org.sdmlib.examples.reachabilitygraphs.simplestates.util;

import org.sdmlib.examples.reachabilitygraphs.simplestates.Node;
import org.sdmlib.examples.reachabilitygraphs.simplestates.SimpleState;
import org.sdmlib.models.pattern.PatternObject;

public class SimpleStatePO extends PatternObject<SimpleStatePO, SimpleState>
{

    public SimpleStateSet allMatches()
   {
      this.setDoAllMatches(true);
      
      SimpleStateSet matches = new SimpleStateSet();

      while (this.getPattern().getHasMatch())
      {
         matches.add((SimpleState) this.getCurrentMatch());
         
         this.getPattern().findMatch();
      }
      
      return matches;
   }


   public SimpleStatePO(){
      newInstance(org.sdmlib.examples.reachabilitygraphs.simplestates.util.CreatorCreator.createIdMap("PatternObjectType"));
   }

   public SimpleStatePO(SimpleState... hostGraphObject) {
      if(hostGraphObject==null || hostGraphObject.length<1){
         return ;
      }
      newInstance(org.sdmlib.examples.reachabilitygraphs.simplestates.util.CreatorCreator.createIdMap("PatternObjectType"), hostGraphObject);
   }
   public NodePO hasNodes()
   {
      NodePO result = new NodePO(new Node[]{});
      
      result.setModifier(this.getPattern().getModifier());
      super.hasLink(SimpleState.PROPERTY_NODES, result);
      
      return result;
   }

   public NodePO createNodes()
   {
      return this.startCreate().hasNodes().endCreate();
   }

   public SimpleStatePO hasNodes(NodePO tgt)
   {
      return hasLinkConstraint(tgt, SimpleState.PROPERTY_NODES);
   }

   public SimpleStatePO createNodes(NodePO tgt)
   {
      return this.startCreate().hasNodes(tgt).endCreate();
   }

   public NodeSet getNodes()
   {
      if (this.getPattern().getHasMatch())
      {
         return ((SimpleState) this.getCurrentMatch()).getNodes();
      }
      return null;
   }

}
