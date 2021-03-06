package org.sdmlib.test.examples.reachabilitygraphs.simplestates.util;

import org.sdmlib.models.pattern.PatternObject;
import org.sdmlib.test.examples.reachabilitygraphs.simplestates.SimpleState;
import org.sdmlib.test.examples.reachabilitygraphs.simplestates.util.NodePO;
import org.sdmlib.test.examples.reachabilitygraphs.simplestates.Node;
import org.sdmlib.test.examples.reachabilitygraphs.simplestates.util.SimpleStatePO;
import org.sdmlib.test.examples.reachabilitygraphs.simplestates.util.NodeSet;
   /**
    * 
    * @see <a href='../../../../../../../../../../src/test/java/org/sdmlib/test/examples/reachabilitygraphs/ReachbilityGraphSimpleExamples.java'>ReachbilityGraphSimpleExamples.java</a>
 */
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


     /**
    * 
    * @see <a href='../../../../../../../../../../src/test/java/org/sdmlib/test/examples/reachabilitygraphs/ReachbilityGraphSimpleExamples.java'>ReachbilityGraphSimpleExamples.java</a>
 */
   public SimpleStatePO(){
      newInstance(null);
   }

     /**
    * 
    * @see <a href='../../../../../../../../../../src/test/java/org/sdmlib/test/examples/reachabilitygraphs/ReachbilityGraphSimpleExamples.java'>ReachbilityGraphSimpleExamples.java</a>
 */
   public SimpleStatePO(SimpleState... hostGraphObject) {
      if(hostGraphObject==null || hostGraphObject.length<1){
         return ;
      }
      newInstance(null, hostGraphObject);
   }

     /**
    * 
    * @see <a href='../../../../../../../../../../src/test/java/org/sdmlib/test/examples/reachabilitygraphs/ReachbilityGraphSimpleExamples.java'>ReachbilityGraphSimpleExamples.java</a>
 */
   public SimpleStatePO(String modifier)
   {
      this.setModifier(modifier);
   }
     /**
    * 
    * @see <a href='../../../../../../../../../../src/test/java/org/sdmlib/test/examples/reachabilitygraphs/ReachbilityGraphSimpleExamples.java'>ReachbilityGraphSimpleExamples.java</a>
 */
   public NodePO createNodesPO()
   {
      NodePO result = new NodePO(new Node[]{});
      
      result.setModifier(this.getPattern().getModifier());
      super.hasLink(SimpleState.PROPERTY_NODES, result);
      
      return result;
   }

     /**
    * 
    * @see <a href='../../../../../../../../../../src/test/java/org/sdmlib/test/examples/reachabilitygraphs/ReachbilityGraphSimpleExamples.java'>ReachbilityGraphSimpleExamples.java</a>
 */
   public NodePO createNodesPO(String modifier)
   {
      NodePO result = new NodePO(new Node[]{});
      
      result.setModifier(modifier);
      super.hasLink(SimpleState.PROPERTY_NODES, result);
      
      return result;
   }

   public SimpleStatePO createNodesLink(NodePO tgt)
   {
      return hasLinkConstraint(tgt, SimpleState.PROPERTY_NODES);
   }

   public SimpleStatePO createNodesLink(NodePO tgt, String modifier)
   {
      return hasLinkConstraint(tgt, SimpleState.PROPERTY_NODES, modifier);
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
