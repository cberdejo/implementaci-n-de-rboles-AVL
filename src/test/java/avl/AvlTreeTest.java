package avl;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


import java.util.Comparator;


import org.junit.jupiter.api.*;
import org.junit.jupiter.api.DisplayName;

  /*
  1. Test for isEmpty()
    1.1 shouldReturnTrueWhenAvlIsEmpty -> Verify that isEmpty() returns true when the AVL is empty
    1.2 shouldTreeBeEmptyWhenAllNodesAreRemoved -> Verify that the tree is empty when all nodes are removed

  2. Test for insert() and balance()
    2.1 shouldUpdateTopNodeAndTreeRepresentationOnInsertTop -> Verify that the top node is updated and the tree representation is correct when inserting the top node
    2.2 shouldUpdateTreeCorrectlyOnInsertingRightAndLeftElementsJustAfterTop -> Verify that the tree is updated correctly when inserting right and left elements just after the top
    2.3 shouldUpdateTreeCorrectlyOnInsertingLeftElement -> Verify that the tree is updated correctly when inserting a left element
    2.4 shouldUpdateTreeCorrectlyOnInsertingRightElement -> Verify that the tree is updated correctly when inserting a right element
    2.5 shouldModifyTreeCorrectlyOnInsertingLeftLeftNodeAndRebalance -> Verify that the tree is modified correctly when inserting a left-left node and rebalancing
    2.6 shouldModifyTreeCorrectlyOnInsertingRightRightNodeAndRebalance -> Verify that the tree is modified correctly when inserting a right-right node and rebalancing
    2.7 shouldVerifyCorrectStructureOnInserting7_4_3_2_1 -> Verify that the tree is modified correctly when inserting 7, 4, 3, 2, 1
    2.8 shouldVerifyCorrectStructureOnInserting7_8_9_10_11 -> Verify that the tree is modified correctly when inserting 7, 8, 9, 10, 11
    2.9 shouldCorrectlyModifyTreeOnInsertingLeftRightNodeAndRebalance -> Verify that the tree is modified correctly when inserting a left-right node and rebalancing
    2.10 shouldCorrectlyModifyTreeOnInsertingRightLeftNodeAndRebalance -> Verify that the tree is modified correctly when inserting a right-left node and rebalancing
    2.11 shouldInsertOnlyOneInstanceOnInsertingDuplicateNodes -> Verify that only one instance is inserted when inserting duplicate nodes

  3. Test for comparator
    3.1 shouldCompareNodesCorrectly -> Verify that the comparator compares nodes correctly

  4. Test for searching
    4.1 shouldSearchClosestNodeCorrectly -> Verify that the closest node is searched correctly
    4.2 shouldSearchNodeCorrectly -> Verify that the node is searched correctly
    4.3 shouldFindSuccessor -> Verify that the successor is found correctly

  5. Test for height and balance
    5.1 shouldCalculateHeightAndBalanceOfASimpleBalancedTree -> Verify that the height and balance of a simple balanced tree is calculated correctly

  6. Test for deleting
    6.1 shouldDeleteLeafNodesProperly -> Verify that leaf nodes are deleted properly
    6.2 shouldDeleteNodesWithOneLeafProperly -> Verify that nodes with one leaf are deleted properly
    6.3 shouldDeleteNodesWithTwoLeavesProperly -> Verify that nodes with two leaves are deleted properly
    6.4 shouldDeleteAndRebalanceCorrectly -> Verify that nodes are deleted and the tree is rebalanced correctly
    6.5 shouldRebalanceWhenDeletingTopNode -> Verify that the tree is rebalanced correctly when deleting the top node
    6.6 shouldNotChangeTreeWhenDeletingNonExistingNode -> Verify that the tree remains unchanged when deleting a non-existing node
   */

  /*
  @author: Alvaro Tapia (alvartap@uma.es)
  @author: Christian Berdejo (cberdejo@uma.es)
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
    public void shouldReturnTrueWhenAvlIsEmpty() throws Exception {
      assertTrue( avlTree.avlIsEmpty());

      avlTree.insertTop(new AvlNode(5));
      assertFalse( avlTree.avlIsEmpty());
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
    public void shouldUpdateTopNodeAndTreeRepresentationOnInsertTop() throws Exception {
      AvlNode<Integer> node = new AvlNode(4);
      avlTree.insertTop(node);
      assertEquals(node, avlTree.getTop());
      String tree = " | 4";
      assertEquals( tree, avlTree.toString());
    }

    @Test
    @DisplayName("Given an AVL tree with a top node, when inserting right and left elements just after the top, then the tree should be updated correctly")
    public void shouldUpdateTreeCorrectlyOnInsertingRightAndLeftElementsJustAfterTop() throws Exception {
      AvlNode<Integer> node = new AvlNode<Integer>(6);
      avlTree.insertAvlNode(node);
      AvlNode<Integer> nodeLeft = new AvlNode<Integer>(4);
      AvlNode<Integer> nodeRight = new AvlNode<Integer>(9);

      assertEquals( -1, avlTree.searchClosestNode(nodeLeft));
      assertEquals( node, nodeLeft.getClosestNode());
      assertEquals( +1, avlTree.searchClosestNode(nodeRight));
      assertEquals( node, nodeRight.getClosestNode());
      assertEquals( 0, avlTree.searchClosestNode(node));

      node.setLeft(nodeLeft);
      node.setRight(nodeRight);
      AvlNode<Integer> nodeRightLeft = new AvlNode<Integer>(7);
      avlTree.searchClosestNode(nodeRightLeft);
      assertEquals( -1,
              avlTree.searchClosestNode(nodeRightLeft));
      assertEquals( nodeRight, nodeRightLeft.getClosestNode());

      AvlNode<Integer> nodeLeftRight = new AvlNode<Integer>(5);
      assertEquals(1, avlTree.searchClosestNode(nodeLeftRight));
      assertEquals( nodeLeft, nodeLeftRight.getClosestNode());

      String tree = " | 6 | 4 | 9";
      assertEquals( tree, avlTree.toString());
    }

    @Test
    @DisplayName("Given an AVL tree with a top node, when inserting a left element, then the tree should be updated correctly")
    public void shouldUpdateTreeCorrectlyOnInsertingLeftElement() throws Exception {
      AvlNode<Integer> node = new AvlNode<Integer>(6);
      avlTree.insertAvlNode(node);
      AvlNode<Integer> nodeLeft = new AvlNode<Integer>(4);
      avlTree.insertAvlNode(nodeLeft);

      assertEquals( node, nodeLeft.getParent());
      assertEquals( nodeLeft, node.getLeft());

      String tree = " | 6 | 4";
      assertEquals(tree, avlTree.toString());
    }

    @Test
    @DisplayName("Given an AVL tree with a top node, when inserting a right element, then the tree should be updated correctly")
    public void shouldUpdateTreeCorrectlyOnInsertingRightElement() throws Exception {
      AvlNode<Integer> node = new AvlNode<Integer>(6);
      avlTree.insertAvlNode(node);
      AvlNode<Integer> nodeRight = new AvlNode<Integer>(9);
      avlTree.insertAvlNode(nodeRight);

      assertEquals( node, nodeRight.getParent());
      assertEquals( nodeRight, node.getRight());

      String tree = " | 6 | 9";
      assertEquals( tree, avlTree.toString());
    }
    /**
     * Testing adding 7 - 4 - 3
     *
     * @throws Exception
     */
    @Test
    @DisplayName("Given an AVL tree, when inserting a left-left node and rebalancing, then the tree should be correctly modified")
    public void shouldModifyTreeCorrectlyOnInsertingLeftLeftNodeAndRebalance() throws Exception {
      AvlNode<Integer> node1, node2, node3, node4, node5;

      node1 = new AvlNode<Integer>(7);
      avlTree.insertAvlNode(node1);
      assertEquals( 0, node1.getHeight());
      assertEquals( 0, avlTree.getBalance(node1));

      node2 = new AvlNode<Integer>(4);
      avlTree.insertAvlNode(node2);
      assertEquals( 0, node2.getHeight());
      assertEquals( 1, node1.getHeight());
      assertEquals( -1, avlTree.getBalance(node1));
      assertEquals(0, avlTree.getBalance(node2));

      node3 = new AvlNode<Integer>(3);
      avlTree.insertAvlNode(node3);
      assertEquals( node2, avlTree.getTop());
      assertEquals( node3, node2.getLeft());
      assertEquals( node1, node2.getRight());

      assertEquals( 1, avlTree.getTop().getHeight());
      assertEquals( 0,
              avlTree.getTop().getLeft().getHeight());
      assertEquals( 0,
              avlTree.getTop().getRight().getHeight());
      assertEquals( -1, avlTree.height(node1.getLeft()));
      assertEquals( -1, avlTree.height(node1.getRight()));
      assertEquals( -1, avlTree.height(node3.getLeft()));
      assertEquals(-1, avlTree.height(node3.getRight()));

      String tree = " | 4 | 3 | 7";
      assertEquals( tree, avlTree.toString());
    }

    /**
     * Testing adding 7 - 10 - 14
     *
     * @throws Exception
     */
    @Test
    @DisplayName("Given an AVL tree, when inserting a right-right node and rebalancing, then the tree should be correctly modified")
    public void shouldModifyTreeCorrectlyOnInsertingRightRightNodeAndRebalance() throws Exception {
      AvlNode<Integer> node1, node2, node3, node4, node5;

      node1 = new AvlNode<Integer>(7);
      avlTree.insertAvlNode(node1);
      assertEquals( 0, node1.getHeight());
      assertEquals( 0, avlTree.getBalance(node1));

      node2 = new AvlNode<Integer>(10);
      avlTree.insertAvlNode(node2);
      assertEquals(0, node2.getHeight());
      assertEquals( 1, node1.getHeight());
      assertEquals(1, avlTree.getBalance(node1));
      assertEquals( 0, avlTree.getBalance(node2));

      node3 = new AvlNode<Integer>(14);
      avlTree.insertAvlNode(node3);
      assertEquals( node2, avlTree.getTop());
      assertEquals( node1, node2.getLeft());
      assertEquals( node3, node2.getRight());

      assertEquals( 1, avlTree.getTop().getHeight());
      assertEquals( 0,
              avlTree.getTop().getLeft().getHeight());
      assertEquals( 0,
              avlTree.getTop().getRight().getHeight());
      assertEquals(-1, avlTree.height(node1.getLeft()));
      assertEquals( -1, avlTree.height(node1.getRight()));
      assertEquals( -1, avlTree.height(node3.getLeft()));
      assertEquals( -1, avlTree.height(node3.getRight()));

      String tree = " | 10 | 7 | 14";
      assertEquals( tree, avlTree.toString());
    }

    /**
     * Testing adding 7 - 4 - 3 - 2 - 1
     *
     * @throws Exception
     */
    @Test
    @DisplayName("Given an AVL tree, when inserting 7, 4, 3, 2, and 1, then verify the correct structure")
    public void shouldVerifyCorrectStructureOnInserting7_4_3_2_1() throws Exception {
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

      assertEquals( node2, avlTree.getTop());
      assertEquals( node4, node2.getLeft());
      assertEquals( node1, node2.getRight());
      assertEquals( node5, node4.getLeft());
      assertEquals( node3, node4.getRight());
      assertEquals( 0, node1.getHeight());
      assertEquals( 2, node2.getHeight());
      assertEquals( 1, node4.getHeight());

      String tree = " | 4 | 2 | 1 | 3 | 7";
      assertEquals( tree, avlTree.toString());
    }

    /**
     * Testing adding 7 - 4 - 3 - 2 - 1
     *
     * @throws Exception
     */
    @Test
    @DisplayName("Given an AVL tree, when inserting 7, 8, 9 , 10, and 11, then verify the correct structure")
    public void shouldVerifyCorrectStructureOnInserting7_8_9_10_11() throws Exception {
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

      assertEquals( node2, avlTree.getTop());
      assertEquals( node4, node2.getRight());
      assertEquals( node1, node2.getLeft());
      assertEquals( node5, node4.getRight());
      assertEquals( node3, node4.getLeft());
      assertEquals( 2, avlTree.getTop().getHeight());
      assertEquals( 1, node4.getHeight());
      assertEquals( 0, node1.getHeight());

      String tree = " | 8 | 7 | 10 | 9 | 11";
      assertEquals( tree, avlTree.toString());
    }

    /**
     * Testing adding 7 - 2 - 3
     *
     * @throws Exception
     */
    @Test
    @DisplayName("Given an AVL tree, when inserting a left-right node and rebalancing, then the tree should be correctly modified")
    public void shouldCorrectlyModifyTreeOnInsertingLeftRightNodeAndRebalance() throws Exception {
      AvlNode<Integer> node1, node2, node3;

      node1 = new AvlNode<Integer>(7);
      avlTree.insertAvlNode(node1);

      node2 = new AvlNode<Integer>(2);
      avlTree.insertAvlNode(node2);

      node3 = new AvlNode<Integer>(3);
      avlTree.insertAvlNode(node3);

      assertEquals( node3, avlTree.getTop());
      assertEquals( node2, node3.getLeft());
      assertEquals( node1, node3.getRight());

      assertEquals( 1, avlTree.getTop().getHeight());
      assertEquals( 0,
              avlTree.getTop().getLeft().getHeight());
      assertEquals( 0,
              avlTree.getTop().getRight().getHeight());
      assertEquals(-1, avlTree.height(node2.getLeft()));
      assertEquals( -1, avlTree.height(node2.getRight()));
      assertEquals( -1, avlTree.height(node1.getLeft()));
      assertEquals(-1, avlTree.height(node1.getRight()));

      String tree = " | 3 | 2 | 7";
      assertEquals( tree, avlTree.toString());
    }

    /**
     * Testing adding 7 - 9 - 8
     *
     * @throws Exception
     */
    @Test
    @DisplayName("Given an AVL tree, when inserting a right-left node and rebalancing, then the tree should be correctly modified")
    public void shouldCorrectlyModifyTreeOnInsertingRightLeftNodeAndRebalance() throws Exception {
      AvlNode<Integer> node1, node2, node3;

      node1 = new AvlNode<Integer>(7);
      avlTree.insertAvlNode(node1);

      node2 = new AvlNode<Integer>(9);
      avlTree.insertAvlNode(node2);

      node3 = new AvlNode<Integer>(8);
      avlTree.insertAvlNode(node3);

      assertEquals( node3, avlTree.getTop());
      assertEquals( node1, node3.getLeft());
      assertEquals( node2, node3.getRight());

      assertEquals( 1, avlTree.getTop().getHeight());
      assertEquals( 0,
              avlTree.getTop().getLeft().getHeight());
      assertEquals( 0,
              avlTree.getTop().getRight().getHeight());
      assertEquals( -1, avlTree.height(node2.getLeft()));
      assertEquals(-1, avlTree.height(node2.getRight()));
      assertEquals( -1, avlTree.height(node1.getLeft()));
      assertEquals( -1, avlTree.height(node1.getRight()));

      String tree = " | 8 | 7 | 9";
      assertEquals( tree, avlTree.toString());
    }



    @Test
    @DisplayName("Given an AVL tree, when inserting duplicate nodes, then verify only one instance of the duplicate node is inserted")
    public void shouldInsertOnlyOneInstanceOnInsertingDuplicateNodes(){
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
  @DisplayName("Test for comparator")
  public class testsForComparator{
    @Test
    @DisplayName("Given two AVL nodes, when comparing their values, then the correct comparison result should be returned")
    public void shouldCompareNodesCorrectly() throws Exception {
      AvlNode<Integer> node1 = new AvlNode<Integer>(4);
      AvlNode<Integer> node2 = new AvlNode<Integer>(5);
      AvlNode<Integer> node3 = new AvlNode<Integer>(5);

      assertEquals( -1, avlTree.compareNodes(node1, node2));
      assertEquals( 1, avlTree.compareNodes(node3, node1));
      assertEquals( 0, avlTree.compareNodes(node2, node3));
    }
  }

  @Nested
  @DisplayName("Test searching nodes related methods")
  public class testForSearchingNodes{

    @Test
    @DisplayName("Given an AVL tree, when searching for the closest node, then the correct result should be returned")
    public void shouldSearchClosestNodeCorrectly() throws Exception {
      int result;
      AvlNode<Integer> node = new AvlNode<Integer>(7);
      result = avlTree.searchClosestNode(node);
      assertEquals( 0, result);
      avlTree.insertAvlNode(node);

      node = new AvlNode<Integer>(4);
      result = avlTree.searchClosestNode(node);
      assertEquals( -1, result);
      avlTree.insertAvlNode(node);

      node = new AvlNode<Integer>(9);
      result = avlTree.searchClosestNode(node);
      assertEquals( 1, result);
      avlTree.insertAvlNode(node);

      node = new AvlNode<Integer>(6);
      result = avlTree.searchClosestNode(node);
      assertEquals( 1, result);
      avlTree.insertAvlNode(node);

      node = new AvlNode<Integer>(8);
      result = avlTree.searchClosestNode(node);
      assertEquals( -1, result);
      avlTree.insertAvlNode(node);

      String tree = " | 7 | 4 | 6 | 9 | 8";
      assertEquals( tree, avlTree.toString());
    }

    @Test
    @DisplayName("Given an AVL tree with nodes 7, 9, 8, 2, and 3, when searching for specific nodes, then verify correct search results")
    public void shouldSearchNodeCorrectly() throws Exception {
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

      assertEquals( node1, avlTree.search(7));
      assertEquals( node2, avlTree.search(9));
      assertEquals( node3, avlTree.search(8));
      assertEquals( (Integer) 2,
              avlTree.searchNode(new AvlNode<Integer>(2)).getItem());
      assertEquals( node4, avlTree.search(2));
      assertEquals( node5, avlTree.search(3));
      assertNull( avlTree.search(14));
      assertNull( avlTree.search(0));

      String tree = " | 8 | 3 | 2 | 7 | 9";
      assertEquals( tree, avlTree.toString());
    }

    @Test
    @DisplayName("Given an AVL tree with nodes 20, 8, 22, 4, 12, 24, 10, and 14, when finding successors for specific nodes, then verify correct successor nodes are returned")
    public void shouldFindSuccessor() throws Exception {
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
      assertEquals( avlTree.search(10), avlTree.findSuccessor(node));
      node = avlTree.search(10);
      assertEquals( avlTree.search(12), avlTree.findSuccessor(node));
      node = avlTree.search(14);
      assertEquals( avlTree.search(20), avlTree.findSuccessor(node));

      String tree = " | 20 | 8 | 4 | 12 | 10 | 14 | 22 | 24";
      assertEquals( tree, avlTree.toString());
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
    public void shouldCalculateHeightAndBalanceOfASimpleBalancedTree() throws Exception {
      AvlNode<Integer> node1, node2, node3, node4, node5;

      node1 = new AvlNode<Integer>(7);
      avlTree.insertAvlNode(node1);
      assertEquals( 0, node1.getHeight());
      assertEquals( 0, avlTree.getBalance(node1));

      node2 = new AvlNode<Integer>(4);
      avlTree.insertAvlNode(node2);
      assertEquals( 0, node2.getHeight());
      assertEquals( 1, node1.getHeight());
      assertEquals( -1, avlTree.getBalance(node1));
      assertEquals( 0, avlTree.getBalance(node2));

      node3 = new AvlNode<Integer>(9);
      avlTree.insertAvlNode(node3);
      assertEquals( 0, node3.getHeight());
      assertEquals( 1, node1.getHeight());
      assertEquals( 0, avlTree.getBalance(node1));
      assertEquals( 0, avlTree.getBalance(node3));

      node4 = new AvlNode<Integer>(3);
      avlTree.insertAvlNode(node4);
      assertEquals( 0, node4.getHeight());
      assertEquals( 1, node2.getHeight());
      assertEquals( 2, node1.getHeight());
      assertEquals( -1, avlTree.getBalance(node2));
      assertEquals( -1, avlTree.getBalance(node1));
      assertEquals( 0, avlTree.getBalance(node4));

      node5 = new AvlNode<Integer>(5);
      avlTree.insertAvlNode(node5);
      assertEquals( 0, node5.getHeight());
      assertEquals( 1, node2.getHeight());
      assertEquals( 2, node1.getHeight());
      assertEquals( 0, avlTree.getBalance(node2));
      assertEquals( -1, avlTree.getBalance(node1));
      assertEquals(0, avlTree.getBalance(node5));

      String tree = " | 7 | 4 | 3 | 5 | 9";
      assertEquals( tree, avlTree.toString());
    }

  }

  @Nested
  @DisplayName("Test for deleting Nodes")
  public class testsForDeletingNodes{
    @Test
    @DisplayName("Given an AVL tree with nodes 7, 9, 2, 8, and 3, when deleting leaf nodes, then verify nodes are properly deleted and tree is rebalanced")
    public void shouldDeleteLeafNodesProperly() throws Exception {
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
      assertEquals( tree, avlTree.toString());

      avlTree.delete(3); // right leaf node
      assertEquals( null, node3.getRight());
      assertEquals( 0, node3.getHeight());
      assertEquals( 2, avlTree.getTop().getHeight());
      assertEquals( " | 7 | 2 | 9 | 8", avlTree.toString());

      avlTree.delete(8); // left leaf node
      assertEquals( null, node2.getLeft());
      assertEquals( 0, node2.getHeight());
      assertEquals( 1, avlTree.getTop().getHeight());
      assertEquals( " | 7 | 2 | 9", avlTree.toString());

      avlTree.delete(2); // left leaf node
      assertEquals( null, node1.getLeft());
      assertEquals( 1, node1.getHeight());
      assertEquals(" | 7 | 9", avlTree.toString());

      avlTree.delete(9); // right leaf node
      assertEquals( null, node1.getRight());
      assertEquals( 0, node1.getHeight());
      assertEquals( " | 7", avlTree.toString());

      avlTree.delete(7); // left leaf node
      assertEquals( null, avlTree.getTop());
      assertEquals("", avlTree.toString());
    }

    @Test
    @DisplayName("Given an AVL tree with nodes 7, 9, 2, and 8, when deleting nodes with one leaf, then verify nodes are properly deleted and tree is rebalanced")
    public void shouldDeleteNodesWithOneLeafProperly() throws Exception {
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
      assertEquals( tree, avlTree.toString());

      avlTree.delete(2);
      assertEquals( node3.getItem(), node1.getLeft().getItem());
      assertEquals( null, node3.getRight());
      assertEquals( 0, node3.getHeight());
      assertEquals( 2, avlTree.getTop().getHeight());
      assertEquals(" | 7 | 3 | 9 | 8", avlTree.toString());

      avlTree.delete(9);
      assertEquals( node2.getItem(), node1.getRight().getItem());
      assertEquals( null, node2.getLeft());
      assertEquals( 0, node2.getHeight());
      assertEquals( 1, avlTree.getTop().getHeight());
      assertEquals( " | 7 | 3 | 8", avlTree.toString());
    }

    @Test
    @DisplayName("Given an AVL tree with nodes 20, 8, 22, 4, 12, 24, 10, and 14, when deleting nodes with two leaves, then verify nodes are properly deleted and tree is rebalanced")
    public void shouldDeleteNodesWithTwoLeavesProperly() throws Exception {
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
      assertEquals( expected, avlTree.toString());

      avlTree.delete(12);
      node = avlTree.search(8);
      assertEquals( 14, (int) node.getRight().getItem());
      assertEquals( " | 20 | 8 | 4 | 14 | 10 | 22 | 24",
              avlTree.toString());

      avlTree.delete(8);
      assertEquals( 10, (int) avlTree.getTop().getLeft().getItem());
      assertEquals( " | 20 | 10 | 4 | 14 | 22 | 24",
              avlTree.toString());
    }

    @Test
    @DisplayName("Given an AVL tree with nodes 20, 8, 22, 4, 12, 24, 10, and 14, when deleting nodes and rebalancing, then verify tree is properly rebalanced")
    public void shouldDeleteAndRebalanceCorrectly() throws Exception {
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

      assertEquals( 3, avlTree.getTop().getHeight());

      avlTree.delete(22);
      assertEquals( 12, (int) avlTree.getTop().getItem());
      assertEquals( avlTree.search(8), avlTree.getTop().getLeft());
      assertEquals( avlTree.search(20), avlTree.getTop().getRight());
    }

    @Test
    @DisplayName("Given an AVL tree with nodes 20, 8, 22, 4, 12, 24, 10, and 14, when deleting the top node, then verify tree is properly rebalanced")
    public void shouldRebalanceWhenDeletingTopNode() throws Exception {
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

      assertEquals( 3, avlTree.getTop().getHeight());

      avlTree.delete(20);
      assertEquals( " | 12 | 8 | 4 | 10 | 22 | 14 | 24", avlTree.toString());
    }
    @Test
    @DisplayName("Given an AVL tree, when deleting a non-existing node, then verify the tree remains unchanged")
    public void shouldNotChangeTreeWhenDeletingNonExistingNode() {
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
