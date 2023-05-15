package avl;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


import java.util.Comparator;


import org.junit.jupiter.api.*;
import org.junit.jupiter.api.DisplayName;


/**
 * Created with IntelliJ IDEA. User: Antonio J. Nebro Date: 08/07/13
 */
public class AvlTreeTest {

  AvlTree<Integer> avlTree;
  Comparator<?> comparator;

  @BeforeEach
  public void setUp() throws Exception {
    comparator = Comparator.comparingInt((Integer o) -> o);
    avlTree = new AvlTree(comparator);
  }

  @AfterEach
  public void tearDown() throws Exception {
    avlTree = null;
    comparator = null;
  }



  @DisplayName("tests for isEmpty method")
  @Nested
  public class isEmptyTests{
    @Test
    @DisplayName("Given an empty AVL tree, when checking isEmpty, then it should return true")
    public void testAvlIsEmpty() throws Exception {
      assertTrue("TestAvlIsEmpty", avlTree.avlIsEmpty());

      avlTree.insertTop(new AvlNode(5));
      assertFalse("TestAvlIsEmpty", avlTree.avlIsEmpty());
    }

    @Test
    @DisplayName("Given an AVL tree, when removing all nodes, then the tree should be empty")
    public void shouldTreeBeEmptyWhenAllNodesAreRemoved() {
      avlTree.insert(1);
      assertFalse(avlTree.avlIsEmpty());

      avlTree.delete(1);
      assertTrue(avlTree.avlIsEmpty());
    }
  }

  @Nested
  @DisplayName("Tests for inserting and rebalancing")
  public class TestsForInsertAndReBalance{
    @Test
    @DisplayName("Given a AVL tree, when inserting a node at the top, then the top node and tree representation should be updated accordingly")
    public void testInsertTop() throws Exception {
      AvlNode<Integer> node = new AvlNode(4);
      avlTree.insertTop(node);
      assertEquals("TestInsertTop", node, avlTree.getTop());
      String tree = " | 4";
      assertEquals("TestInsertTop", tree, avlTree.toString());
    }

    @Test
    @DisplayName("Given an AVL tree with a top node, when inserting right and left elements just after the top, then the tree should be updated correctly")
    public void testInsertingRightAndLeftElementsJustAfterTop() throws Exception {
      AvlNode<Integer> node = new AvlNode<Integer>(6);
      avlTree.insertAvlNode(node);
      AvlNode<Integer> nodeLeft = new AvlNode<Integer>(4);
      AvlNode<Integer> nodeRight = new AvlNode<Integer>(9);

      assertEquals("testInsertingSecondSmallerElement", -1, avlTree.searchClosestNode(nodeLeft));
      assertEquals("testInsertingSecondSmallerElement", node, nodeLeft.getClosestNode());
      assertEquals("testInsertingSecondSmallerElement", +1, avlTree.searchClosestNode(nodeRight));
      assertEquals("testInsertingSecondSmallerElement", node, nodeRight.getClosestNode());
      assertEquals("testInsertingSecondSmallerElement", 0, avlTree.searchClosestNode(node));

      node.setLeft(nodeLeft);
      node.setRight(nodeRight);
      AvlNode<Integer> nodeRightLeft = new AvlNode<Integer>(7);
      avlTree.searchClosestNode(nodeRightLeft);
      assertEquals("testInsertingSecondSmallerElement", -1,
              avlTree.searchClosestNode(nodeRightLeft));
      assertEquals("testInsertingSecondSmallerElement", nodeRight, nodeRightLeft.getClosestNode());

      AvlNode<Integer> nodeLeftRight = new AvlNode<Integer>(5);
      assertEquals("testInsertingSecondSmallerElement", 1, avlTree.searchClosestNode(nodeLeftRight));
      assertEquals("testInsertingSecondSmallerElement", nodeLeft, nodeLeftRight.getClosestNode());

      String tree = " | 6 | 4 | 9";
      assertEquals("testInsertingSecondSmallerElement", tree, avlTree.toString());
    }

    @Test
    @DisplayName("Given an AVL tree with a top node, when inserting a left element, then the tree should be updated correctly")
    public void testInsertingLeftElement() throws Exception {
      AvlNode<Integer> node = new AvlNode<Integer>(6);
      avlTree.insertAvlNode(node);
      AvlNode<Integer> nodeLeft = new AvlNode<Integer>(4);
      avlTree.insertAvlNode(nodeLeft);

      assertEquals("testInsertingLeftElement", node, nodeLeft.getParent());
      assertEquals("testInsertingLeftElement", nodeLeft, node.getLeft());

      String tree = " | 6 | 4";
      assertEquals("testInsertingLeftElement", tree, avlTree.toString());
    }

    @Test
    @DisplayName("Given an AVL tree with a top node, when inserting a right element, then the tree should be updated correctly")
    public void testInsertingRightElement() throws Exception {
      AvlNode<Integer> node = new AvlNode<Integer>(6);
      avlTree.insertAvlNode(node);
      AvlNode<Integer> nodeRight = new AvlNode<Integer>(9);
      avlTree.insertAvlNode(nodeRight);

      assertEquals("testInsertingRightElement", node, nodeRight.getParent());
      assertEquals("testInsertingRightElement", nodeRight, node.getRight());

      String tree = " | 6 | 9";
      assertEquals("testInsertingRightElement", tree, avlTree.toString());
    }
    /**
     * Testing adding 7 - 4 - 3
     *
     * @throws Exception
     */
    @Test
    @DisplayName("Given an AVL tree, when inserting a left-left node and rebalancing, then the tree should be correctly modified")
    public void testInsertingLeftLeftNodeAndRebalance() throws Exception {
      AvlNode<Integer> node1, node2, node3, node4, node5;

      node1 = new AvlNode<Integer>(7);
      avlTree.insertAvlNode(node1);
      assertEquals("testInsertingLeftLeftNodeAndRebalance", 0, node1.getHeight());
      assertEquals("testInsertingLeftLeftNodeAndRebalance", 0, avlTree.getBalance(node1));

      node2 = new AvlNode<Integer>(4);
      avlTree.insertAvlNode(node2);
      assertEquals("testInsertingLeftLeftNodeAndRebalance", 0, node2.getHeight());
      assertEquals("testInsertingLeftLeftNodeAndRebalance", 1, node1.getHeight());
      assertEquals("testInsertingLeftLeftNodeAndRebalance", -1, avlTree.getBalance(node1));
      assertEquals("testInsertingLeftLeftNodeAndRebalance", 0, avlTree.getBalance(node2));

      node3 = new AvlNode<Integer>(3);
      avlTree.insertAvlNode(node3);
      assertEquals("testInsertingLeftLeftNodeAndRebalance", node2, avlTree.getTop());
      assertEquals("testInsertingLeftLeftNodeAndRebalance", node3, node2.getLeft());
      assertEquals("testInsertingLeftLeftNodeAndRebalance", node1, node2.getRight());

      assertEquals("testInsertingLeftLeftNodeAndRebalance", 1, avlTree.getTop().getHeight());
      assertEquals("testInsertingLeftLeftNodeAndRebalance", 0,
              avlTree.getTop().getLeft().getHeight());
      assertEquals("testInsertingLeftLeftNodeAndRebalance", 0,
              avlTree.getTop().getRight().getHeight());
      assertEquals("testInsertingLeftLeftNodeAndRebalance", -1, avlTree.height(node1.getLeft()));
      assertEquals("testInsertingLeftLeftNodeAndRebalance", -1, avlTree.height(node1.getRight()));
      assertEquals("testInsertingLeftLeftNodeAndRebalance", -1, avlTree.height(node3.getLeft()));
      assertEquals("testInsertingLeftLeftNodeAndRebalance", -1, avlTree.height(node3.getRight()));

      String tree = " | 4 | 3 | 7";
      assertEquals("testInsertingLeftLeftNodeAndRebalance", tree, avlTree.toString());
    }

    /**
     * Testing adding 7 - 10 - 14
     *
     * @throws Exception
     */
    @Test
    @DisplayName("Given an AVL tree, when inserting a right-right node and rebalancing, then the tree should be correctly modified")
    public void testInsertingRightRightNodeAndRebalance() throws Exception {
      AvlNode<Integer> node1, node2, node3, node4, node5;

      node1 = new AvlNode<Integer>(7);
      avlTree.insertAvlNode(node1);
      assertEquals("testInsertingRightRightNodeAndRebalance", 0, node1.getHeight());
      assertEquals("testInsertingRightRightNodeAndRebalance", 0, avlTree.getBalance(node1));

      node2 = new AvlNode<Integer>(10);
      avlTree.insertAvlNode(node2);
      assertEquals("testInsertingRightRightNodeAndRebalance", 0, node2.getHeight());
      assertEquals("testInsertingRightRightNodeAndRebalance", 1, node1.getHeight());
      assertEquals("testInsertingRightRightNodeAndRebalance", 1, avlTree.getBalance(node1));
      assertEquals("testInsertingRightRightNodeAndRebalance", 0, avlTree.getBalance(node2));

      node3 = new AvlNode<Integer>(14);
      avlTree.insertAvlNode(node3);
      assertEquals("testInsertingRightRightNodeAndRebalance", node2, avlTree.getTop());
      assertEquals("testInsertingRightRightNodeAndRebalance", node1, node2.getLeft());
      assertEquals("testInsertingRightRightNodeAndRebalance", node3, node2.getRight());

      assertEquals("testInsertingRightRightNodeAndRebalance", 1, avlTree.getTop().getHeight());
      assertEquals("testInsertingRightRightNodeAndRebalance", 0,
              avlTree.getTop().getLeft().getHeight());
      assertEquals("testInsertingRightRightNodeAndRebalance", 0,
              avlTree.getTop().getRight().getHeight());
      assertEquals("testInsertingRightRightNodeAndRebalance", -1, avlTree.height(node1.getLeft()));
      assertEquals("testInsertingRightRightNodeAndRebalance", -1, avlTree.height(node1.getRight()));
      assertEquals("testInsertingRightRightNodeAndRebalance", -1, avlTree.height(node3.getLeft()));
      assertEquals("testInsertingRightRightNodeAndRebalance", -1, avlTree.height(node3.getRight()));

      String tree = " | 10 | 7 | 14";
      assertEquals("testInsertingRightRightNodeAndRebalance", tree, avlTree.toString());
    }

    /**
     * Testing adding 7 - 4 - 3 - 2 - 1
     *
     * @throws Exception
     */
    @Test
    @DisplayName("Given an AVL tree, when inserting 7, 4, 3, 2, and 1, then verify the correct structure")
    public void testInserting7_4_3_2_1() throws Exception {
      AvlNode<Integer> node1, node2, node3, node4, node5;

      node1 = new AvlNode<Integer>(7);
      node2 = new AvlNode<Integer>(4);
      node3 = new AvlNode<Integer>(3);
      node4 = new AvlNode<Integer>(2);
      node5 = new AvlNode<Integer>(1);

      avlTree.insertAvlNode(node1);
      avlTree.insertAvlNode(node2);
      avlTree.insertAvlNode(node3);
      avlTree.insertAvlNode(node4);
      avlTree.insertAvlNode(node5);

      assertEquals("testInserting7_4_3_2_1", node2, avlTree.getTop());
      assertEquals("testInserting7_4_3_2_1", node4, node2.getLeft());
      assertEquals("testInserting7_4_3_2_1", node1, node2.getRight());
      assertEquals("testInserting7_4_3_2_1", node5, node4.getLeft());
      assertEquals("testInserting7_4_3_2_1", node3, node4.getRight());
      assertEquals("testInserting7_4_3_2_1", 0, node1.getHeight());
      assertEquals("testInserting7_4_3_2_1", 2, node2.getHeight());
      assertEquals("testInserting7_4_3_2_1", 1, node4.getHeight());

      String tree = " | 4 | 2 | 1 | 3 | 7";
      assertEquals("testInserting7_4_3_2_1", tree, avlTree.toString());
    }

    /**
     * Testing adding 7 - 4 - 3 - 2 - 1
     *
     * @throws Exception
     */
    @Test
    @DisplayName("Given an AVL tree, when inserting 7, 8, 9 , 10, and 11, then verify the correct structure")
    public void testInserting7_8_9_10_11() throws Exception {
      AvlNode<Integer> node1, node2, node3, node4, node5;

      node1 = new AvlNode<Integer>(7);
      node2 = new AvlNode<Integer>(8);
      node3 = new AvlNode<Integer>(9);
      node4 = new AvlNode<Integer>(10);
      node5 = new AvlNode<Integer>(11);

      avlTree.insertAvlNode(node1);
      avlTree.insertAvlNode(node2);
      avlTree.insertAvlNode(node3);
      avlTree.insertAvlNode(node4);
      avlTree.insertAvlNode(node5);

      assertEquals("testInserting7_8_9_10_11", node2, avlTree.getTop());
      assertEquals("testInserting7_8_9_10_11", node4, node2.getRight());
      assertEquals("testInserting7_8_9_10_11", node1, node2.getLeft());
      assertEquals("testInserting7_8_9_10_11", node5, node4.getRight());
      assertEquals("testInserting7_8_9_10_11", node3, node4.getLeft());
      assertEquals("testInserting7_8_9_10_11", 2, avlTree.getTop().getHeight());
      assertEquals("testInserting7_8_9_10_11", 1, node4.getHeight());
      assertEquals("testInserting7_8_9_10_11", 0, node1.getHeight());

      String tree = " | 8 | 7 | 10 | 9 | 11";
      assertEquals("testInserting7_8_9_10_11", tree, avlTree.toString());
    }

    /**
     * Testing adding 7 - 2 - 3
     *
     * @throws Exception
     */
    @Test
    @DisplayName("Given an AVL tree, when inserting a left-right node and rebalancing, then the tree should be correctly modified")
    public void testInsertingLeftRightNodeAndRebalance() throws Exception {
      AvlNode<Integer> node1, node2, node3;

      node1 = new AvlNode<Integer>(7);
      avlTree.insertAvlNode(node1);

      node2 = new AvlNode<Integer>(2);
      avlTree.insertAvlNode(node2);

      node3 = new AvlNode<Integer>(3);
      avlTree.insertAvlNode(node3);

      assertEquals("testInsertingLeftRightNodeAndRebalance", node3, avlTree.getTop());
      assertEquals("testInsertingLeftRightNodeAndRebalance", node2, node3.getLeft());
      assertEquals("testInsertingLeftRightNodeAndRebalance", node1, node3.getRight());

      assertEquals("testInsertingLeftRightNodeAndRebalance", 1, avlTree.getTop().getHeight());
      assertEquals("testInsertingLeftRightNodeAndRebalance", 0,
              avlTree.getTop().getLeft().getHeight());
      assertEquals("testInsertingLeftRightNodeAndRebalance", 0,
              avlTree.getTop().getRight().getHeight());
      assertEquals("testInsertingLeftRightNodeAndRebalance", -1, avlTree.height(node2.getLeft()));
      assertEquals("testInsertingLeftRightNodeAndRebalance", -1, avlTree.height(node2.getRight()));
      assertEquals("testInsertingLeftRightNodeAndRebalance", -1, avlTree.height(node1.getLeft()));
      assertEquals("testInsertingLeftRightNodeAndRebalance", -1, avlTree.height(node1.getRight()));

      String tree = " | 3 | 2 | 7";
      assertEquals("testInsertingLeftRightNodeAndRebalance", tree, avlTree.toString());
    }

    /**
     * Testing adding 7 - 9 - 8
     *
     * @throws Exception
     */
    @Test
    @DisplayName("Given an AVL tree, when inserting a right-left node and rebalancing, then the tree should be correctly modified")
    public void testInsertingRightLeftNodeAndRebalance() throws Exception {
      AvlNode<Integer> node1, node2, node3;

      node1 = new AvlNode<Integer>(7);
      avlTree.insertAvlNode(node1);

      node2 = new AvlNode<Integer>(9);
      avlTree.insertAvlNode(node2);

      node3 = new AvlNode<Integer>(8);
      avlTree.insertAvlNode(node3);

      assertEquals("testInsertingRightLeftNodeAndRebalance", node3, avlTree.getTop());
      assertEquals("testInsertingRightLeftNodeAndRebalance", node1, node3.getLeft());
      assertEquals("testInsertingRightLeftNodeAndRebalance", node2, node3.getRight());

      assertEquals("testInsertingRightLeftNodeAndRebalance", 1, avlTree.getTop().getHeight());
      assertEquals("testInsertingRightLeftNodeAndRebalance", 0,
              avlTree.getTop().getLeft().getHeight());
      assertEquals("testInsertingRightLeftNodeAndRebalance", 0,
              avlTree.getTop().getRight().getHeight());
      assertEquals("testInsertingRightLeftNodeAndRebalance", -1, avlTree.height(node2.getLeft()));
      assertEquals("testInsertingRightLeftNodeAndRebalance", -1, avlTree.height(node2.getRight()));
      assertEquals("testInsertingRightLeftNodeAndRebalance", -1, avlTree.height(node1.getLeft()));
      assertEquals("testInsertingRightLeftNodeAndRebalance", -1, avlTree.height(node1.getRight()));

      String tree = " | 8 | 7 | 9";
      assertEquals("testInsertingRightLeftNodeAndRebalance", tree, avlTree.toString());
    }



    @Test
    @DisplayName("Given an AVL tree, when inserting duplicate nodes, then verify only one instance of the duplicate node is inserted")
    public void testInsertingDuplicateNodes(){
      AvlNode<Integer> node;

      //G
      String expected = " | 5 | 2 | 8";

      avlTree.insert(5);
      avlTree.insert(2);
      avlTree.insert(8);


      //W
      avlTree.insert(2);

      //T

      String  result = avlTree.toString();
      assertEquals(expected, result);


    }
  }

  @Nested
  @DisplayName("test for comparator")
  public class testsForComparator{
    @Test
    @DisplayName("Given two AVL nodes, when comparing their values, then the correct comparison result should be returned")
    public void testCompareNodes() throws Exception {
      AvlNode<Integer> node1 = new AvlNode<Integer>(4);
      AvlNode<Integer> node2 = new AvlNode<Integer>(5);
      AvlNode<Integer> node3 = new AvlNode<Integer>(5);

      assertEquals("testCompareNodes", -1, avlTree.compareNodes(node1, node2));
      assertEquals("testCompareNodes", 1, avlTree.compareNodes(node3, node1));
      assertEquals("testCompareNodes", 0, avlTree.compareNodes(node2, node3));
    }
  }

  @Nested
  @DisplayName("test for searching nodes related methods")
  public class testForSearchingNodes{
    /*
@Test
public void testInsertingTheFirstElement() throws Exception {
  AvlNode<Integer> node = new AvlNode<Integer>(6) ;
  avlTree_.insertAvlNode(node);
  assertEquals("testInsertingTheFirstElement", node, avlTree_.getTop());
}
*/
    @Test
    @DisplayName("Given an AVL tree, when searching for the closest node, then the correct result should be returned")
    public void testSearchClosestNode() throws Exception {
      int result;
      AvlNode<Integer> node = new AvlNode<Integer>(7);
      result = avlTree.searchClosestNode(node);
      assertEquals("testSearchClosestNode", 0, result);
      avlTree.insertAvlNode(node);

      node = new AvlNode<Integer>(4);
      result = avlTree.searchClosestNode(node);
      assertEquals("testSearchClosestNode", -1, result);
      avlTree.insertAvlNode(node);

      node = new AvlNode<Integer>(9);
      result = avlTree.searchClosestNode(node);
      assertEquals("testSearchClosestNode", 1, result);
      avlTree.insertAvlNode(node);

      node = new AvlNode<Integer>(6);
      result = avlTree.searchClosestNode(node);
      assertEquals("testSearchClosestNode", 1, result);
      avlTree.insertAvlNode(node);

      node = new AvlNode<Integer>(8);
      result = avlTree.searchClosestNode(node);
      assertEquals("testSearchClosestNode", -1, result);
      avlTree.insertAvlNode(node);

      String tree = " | 7 | 4 | 6 | 9 | 8";
      assertEquals("testSearchClosestNode", tree, avlTree.toString());
    }

    @Test
    @DisplayName("Given an AVL tree with nodes 7, 9, 8, 2, and 3, when searching for specific nodes, then verify correct search results")
    public void testSearchNode() throws Exception {
      AvlNode<Integer> node1, node2, node3, node4, node5;

      node1 = new AvlNode<Integer>(7);
      avlTree.insertAvlNode(node1);

      node2 = new AvlNode<Integer>(9);
      avlTree.insertAvlNode(node2);

      node3 = new AvlNode<Integer>(8);
      avlTree.insertAvlNode(node3);

      node4 = new AvlNode<Integer>(2);
      avlTree.insertAvlNode(node4);

      node5 = new AvlNode<Integer>(3);
      avlTree.insertAvlNode(node5);

      assertEquals("testSearchNode", node1, avlTree.search(7));
      assertEquals("testSearchNode", node2, avlTree.search(9));
      assertEquals("testSearchNode", node3, avlTree.search(8));
      assertEquals("testSearchNode", (Integer) 2,
              avlTree.searchNode(new AvlNode<Integer>(2)).getItem());
      assertEquals("testSearchNode", node4, avlTree.search(2));
      assertEquals("testSearchNode", node5, avlTree.search(3));
      assertNull("testInsertNode", avlTree.search(14));
      assertNull("testSearchNode", avlTree.search(0));

      String tree = " | 8 | 3 | 2 | 7 | 9";
      assertEquals("testSearchNode", tree, avlTree.toString());
    }

    @Test
    @DisplayName("Given an AVL tree with nodes 20, 8, 22, 4, 12, 24, 10, and 14, when finding successors for specific nodes, then verify correct successor nodes are returned")
    public void testFindSuccessor() throws Exception {
      AvlNode<Integer> node;

      node = new AvlNode<Integer>(20);
      avlTree.insertAvlNode(node);

      node = new AvlNode<Integer>(8);
      avlTree.insertAvlNode(node);

      node = new AvlNode<Integer>(22);
      avlTree.insertAvlNode(node);

      node = new AvlNode<Integer>(4);
      avlTree.insertAvlNode(node);

      node = new AvlNode<Integer>(12);
      avlTree.insertAvlNode(node);

      node = new AvlNode<Integer>(24);
      avlTree.insertAvlNode(node);

      node = new AvlNode<Integer>(10);
      avlTree.insertAvlNode(node);

      node = new AvlNode<Integer>(14);
      avlTree.insertAvlNode(node);

      node = avlTree.search(8);
      assertEquals("testFindSuccessor", avlTree.search(10), avlTree.findSuccessor(node));
      node = avlTree.search(10);
      assertEquals("testFindSuccessor", avlTree.search(12), avlTree.findSuccessor(node));
      node = avlTree.search(14);
      assertEquals("testFindSuccessor", avlTree.search(20), avlTree.findSuccessor(node));

      String tree = " | 20 | 8 | 4 | 12 | 10 | 14 | 22 | 24";
      assertEquals("testSearchNode", tree, avlTree.toString());
    }
  }

  @Nested
  @DisplayName("Test for balance and height")
  public class testsForHeightAndBalancce{
    /**
     * Test adding 7 - 4 - 9 - 3 - 5
     *
     * @throws Exception
     */
    @Test
    @DisplayName("Given a simple balanced AVL tree, when calculating the height and balance factors, then the correct values should be returned")
    public void testHeightAndBalanceOfASimpleBalancedTree() throws Exception {
      AvlNode<Integer> node1, node2, node3, node4, node5;

      node1 = new AvlNode<Integer>(7);
      avlTree.insertAvlNode(node1);
      assertEquals("testHeightOfASimpleBalancedTree", 0, node1.getHeight());
      assertEquals("testHeightOfASimpleBalancedTree", 0, avlTree.getBalance(node1));

      node2 = new AvlNode<Integer>(4);
      avlTree.insertAvlNode(node2);
      assertEquals("testHeightOfASimpleBalancedTree", 0, node2.getHeight());
      assertEquals("testHeightOfASimpleBalancedTree", 1, node1.getHeight());
      assertEquals("testHeightOfASimpleBalancedTree", -1, avlTree.getBalance(node1));
      assertEquals("testHeightOfASimpleBalancedTree", 0, avlTree.getBalance(node2));

      node3 = new AvlNode<Integer>(9);
      avlTree.insertAvlNode(node3);
      assertEquals("testHeightOfASimpleBalancedTree", 0, node3.getHeight());
      assertEquals("testHeightOfASimpleBalancedTree", 1, node1.getHeight());
      assertEquals("testHeightOfASimpleBalancedTree", 0, avlTree.getBalance(node1));
      assertEquals("testHeightOfASimpleBalancedTree", 0, avlTree.getBalance(node3));

      node4 = new AvlNode<Integer>(3);
      avlTree.insertAvlNode(node4);
      assertEquals("testHeightOfASimpleBalancedTree", 0, node4.getHeight());
      assertEquals("testHeightOfASimpleBalancedTree", 1, node2.getHeight());
      assertEquals("testHeightOfASimpleBalancedTree", 2, node1.getHeight());
      assertEquals("testHeightOfASimpleBalancedTree", -1, avlTree.getBalance(node2));
      assertEquals("testHeightOfASimpleBalancedTree", -1, avlTree.getBalance(node1));
      assertEquals("testHeightOfASimpleBalancedTree", 0, avlTree.getBalance(node4));

      node5 = new AvlNode<Integer>(5);
      avlTree.insertAvlNode(node5);
      assertEquals("testHeightOfASimpleBalancedTree", 0, node5.getHeight());
      assertEquals("testHeightOfASimpleBalancedTree", 1, node2.getHeight());
      assertEquals("testHeightOfASimpleBalancedTree", 2, node1.getHeight());
      assertEquals("testHeightOfASimpleBalancedTree", 0, avlTree.getBalance(node2));
      assertEquals("testHeightOfASimpleBalancedTree", -1, avlTree.getBalance(node1));
      assertEquals("testHeightOfASimpleBalancedTree", 0, avlTree.getBalance(node5));

      String tree = " | 7 | 4 | 3 | 5 | 9";
      assertEquals("testHeightOfASimpleBalancedTree", tree, avlTree.toString());
    }

  }

  @Nested
  @DisplayName("Test for deleting Nodes")
  public class testsForDeletingNodes{
    @Test
    @DisplayName("Given an AVL tree with nodes 7, 9, 2, 8, and 3, when deleting leaf nodes, then verify nodes are properly deleted and tree is rebalanced")
    public void testDeletingLeafNodes() throws Exception {
      AvlNode<Integer> node1, node2, node3, node4, node5;

      node1 = new AvlNode<Integer>(7);
      avlTree.insertAvlNode(node1);

      node2 = new AvlNode<Integer>(9);
      avlTree.insertAvlNode(node2);

      node3 = new AvlNode<Integer>(2);
      avlTree.insertAvlNode(node3);

      node4 = new AvlNode<Integer>(8);
      avlTree.insertAvlNode(node4);

      node5 = new AvlNode<Integer>(3);
      avlTree.insertAvlNode(node5);

      String tree = " | 7 | 2 | 3 | 9 | 8";
      assertEquals("testDeletingLeafNodes", tree, avlTree.toString());

      avlTree.delete(3); // right leaf node
      assertEquals("testDeletingLeafNodes", null, node3.getRight());
      assertEquals("testDeletingLeafNodes", 0, node3.getHeight());
      assertEquals("testDeletingLeafNodes", 2, avlTree.getTop().getHeight());
      assertEquals("testDeletingLeafNodes", " | 7 | 2 | 9 | 8", avlTree.toString());

      avlTree.delete(8); // left leaf node
      assertEquals("testDeletingLeafNodes", null, node2.getLeft());
      assertEquals("testDeletingLeafNodes", 0, node2.getHeight());
      assertEquals("testDeletingLeafNodes", 1, avlTree.getTop().getHeight());
      assertEquals("testDeletingLeafNodes", " | 7 | 2 | 9", avlTree.toString());

      avlTree.delete(2); // left leaf node
      assertEquals("testDeletingLeafNodes", null, node1.getLeft());
      assertEquals("testDeletingLeafNodes", 1, node1.getHeight());
      assertEquals("testDeletingLeafNodes", " | 7 | 9", avlTree.toString());

      avlTree.delete(9); // right leaf node
      assertEquals("testDeletingLeafNodes", null, node1.getRight());
      assertEquals("testDeletingLeafNodes", 0, node1.getHeight());
      assertEquals("testDeletingLeafNodes", " | 7", avlTree.toString());

      avlTree.delete(7); // left leaf node
      assertEquals("testDeletingLeafNodes", null, avlTree.getTop());
      assertEquals("testDeletingLeafNodes", "", avlTree.toString());
    }

    @Test
    @DisplayName("Given an AVL tree with nodes 7, 9, 2, and 8, when deleting nodes with one leaf, then verify nodes are properly deleted and tree is rebalanced")
    public void testDeletingNodesWithOneLeaf() throws Exception {
      AvlNode<Integer> node1, node2, node3, node4, node5;

      node1 = new AvlNode<Integer>(7);
      avlTree.insertAvlNode(node1);

      node2 = new AvlNode<Integer>(9);
      avlTree.insertAvlNode(node2);

      node3 = new AvlNode<Integer>(2);
      avlTree.insertAvlNode(node3);

      node4 = new AvlNode<Integer>(8);
      avlTree.insertAvlNode(node4);

      node5 = new AvlNode<Integer>(3);
      avlTree.insertAvlNode(node5);

      String tree = " | 7 | 2 | 3 | 9 | 8";
      assertEquals("testDeletingNodesWithOneLeaf", tree, avlTree.toString());

      avlTree.delete(2);
      assertEquals("testDeletingNodesWithOneLeaf", node3.getItem(), node1.getLeft().getItem());
      assertEquals("testDeletingNodesWithOneLeaf", null, node3.getRight());
      assertEquals("testDeletingNodesWithOneLeaf", 0, node3.getHeight());
      assertEquals("testDeletingNodesWithOneLeaf", 2, avlTree.getTop().getHeight());
      assertEquals("testDeletingNodesWithOneLeaf", " | 7 | 3 | 9 | 8", avlTree.toString());

      avlTree.delete(9);
      assertEquals("testDeletingNodesWithOneLeaf", node2.getItem(), node1.getRight().getItem());
      assertEquals("testDeletingNodesWithOneLeaf", null, node2.getLeft());
      assertEquals("testDeletingNodesWithOneLeaf", 0, node2.getHeight());
      assertEquals("testDeletingNodesWithOneLeaf", 1, avlTree.getTop().getHeight());
      assertEquals("testDeletingNodesWithOneLeaf", " | 7 | 3 | 8", avlTree.toString());
    }

    @Test
    @DisplayName("Given an AVL tree with nodes 20, 8, 22, 4, 12, 24, 10, and 14, when deleting nodes with two leaves, then verify nodes are properly deleted and tree is rebalanced")
    public void testDeletingNodesWithTwoLeaves() throws Exception {
      AvlNode<Integer> node;

      node = new AvlNode<Integer>(20);
      avlTree.insertAvlNode(node);

      node = new AvlNode<Integer>(8);
      avlTree.insertAvlNode(node);

      node = new AvlNode<Integer>(22);
      avlTree.insertAvlNode(node);

      node = new AvlNode<Integer>(4);
      avlTree.insertAvlNode(node);

      node = new AvlNode<Integer>(12);
      avlTree.insertAvlNode(node);

      node = new AvlNode<Integer>(24);
      avlTree.insertAvlNode(node);

      node = new AvlNode<Integer>(10);
      avlTree.insertAvlNode(node);

      node = new AvlNode<Integer>(14);
      avlTree.insertAvlNode(node);

      String expected = " | 20 | 8 | 4 | 12 | 10 | 14 | 22 | 24";
      assertEquals("testDeletingNodesWithTwoLeaves", expected, avlTree.toString());

      avlTree.delete(12);
      node = avlTree.search(8);
      assertEquals("testDeletingNodesWithTwoLeaves", 14, (int) node.getRight().getItem());
      assertEquals("testDeletingNodesWithTwoLeaves", " | 20 | 8 | 4 | 14 | 10 | 22 | 24",
              avlTree.toString());

      avlTree.delete(8);
      assertEquals("testDeletingNodesWithTwoLeaves", 10, (int) avlTree.getTop().getLeft().getItem());
      assertEquals("testDeletingNodesWithTwoLeaves", " | 20 | 10 | 4 | 14 | 22 | 24",
              avlTree.toString());
    }

    @Test
    @DisplayName("Given an AVL tree with nodes 20, 8, 22, 4, 12, 24, 10, and 14, when deleting nodes and rebalancing, then verify tree is properly rebalanced")
    public void testDeletingAndRebalancing() throws Exception {
      AvlNode<Integer> node;

      node = new AvlNode<Integer>(20);
      avlTree.insertAvlNode(node);

      node = new AvlNode<Integer>(8);
      avlTree.insertAvlNode(node);

      node = new AvlNode<Integer>(22);
      avlTree.insertAvlNode(node);

      node = new AvlNode<Integer>(4);
      avlTree.insertAvlNode(node);

      node = new AvlNode<Integer>(12);
      avlTree.insertAvlNode(node);

      node = new AvlNode<Integer>(24);
      avlTree.insertAvlNode(node);

      node = new AvlNode<Integer>(10);
      avlTree.insertAvlNode(node);

      node = new AvlNode<Integer>(14);
      avlTree.insertAvlNode(node);

      assertEquals("testDeletingDeepLeafNode", 3, avlTree.getTop().getHeight());

      avlTree.delete(22);
      assertEquals("testDeletingDeepLeafNode", 12, (int) avlTree.getTop().getItem());
      assertEquals("testDeletingDeepLeafNode", avlTree.search(8), avlTree.getTop().getLeft());
      assertEquals("testDeletingDeepLeafNode", avlTree.search(20), avlTree.getTop().getRight());
    }

    @Test
    @DisplayName("Given an AVL tree with nodes 20, 8, 22, 4, 12, 24, 10, and 14, when deleting the top node, then verify tree is properly rebalanced")
    public void testDeletingTopNode() throws Exception {
      AvlNode<Integer> node;

      node = new AvlNode<Integer>(20);
      avlTree.insertAvlNode(node);

      node = new AvlNode<Integer>(8);
      avlTree.insertAvlNode(node);

      node = new AvlNode<Integer>(22);
      avlTree.insertAvlNode(node);

      node = new AvlNode<Integer>(4);
      avlTree.insertAvlNode(node);

      node = new AvlNode<Integer>(12);
      avlTree.insertAvlNode(node);

      node = new AvlNode<Integer>(24);
      avlTree.insertAvlNode(node);

      node = new AvlNode<Integer>(10);
      avlTree.insertAvlNode(node);

      node = new AvlNode<Integer>(14);
      avlTree.insertAvlNode(node);

      assertEquals("testDeletingTopNode", 3, avlTree.getTop().getHeight());

      avlTree.delete(20);
      assertEquals("testDeletingTopNode", " | 12 | 8 | 4 | 10 | 22 | 14 | 24", avlTree.toString());
    }
    @Test
    @DisplayName("Given an AVL tree, when deleting a non-existing node, then verify the tree remains unchanged")
    public void testDeletingNonExistingNode() {
      //G
      String expected = " | 5 | 2 | 8";
      avlTree.insert(5);
      avlTree.insert(2);
      avlTree.insert(8);

      //W
      avlTree.delete(10);
      String result = avlTree.toString();


      //T
      //No debería pasar nada
      assertEquals(expected, result);
    }
  }

}
