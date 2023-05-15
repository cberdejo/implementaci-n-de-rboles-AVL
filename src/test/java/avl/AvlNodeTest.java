package avl;

import org.junit.jupiter.api.*;
import static org.assertj.core.api.AssertionsForClassTypes.*;


/**
 * Created with IntelliJ IDEA. User: Antonio J. Nebro Date: 09/07/13 Time: 15:29
 */

  /*
1. shouldSetHeightReturnExpected -> Verify that the setHeight() method works correctly
2. shouldRootNodeNotHaveParentAndHeightZeroAndLeaf -> Verify that the root node has no parent, height zero and is a leaf
3. shouldSetRightChildAndHeight -> Verify that the setRight() method works correctly
4. shouldSetLeftChildAndHeight -> Verify that the setLeft() method works correctly
5. shouldSetAndHasParentWork -> Verify that the setParent() and hasParent() methods work correctly
6. shouldGetItemReturnExpected -> Verify that the getItem() method works correctly
7. shouldSetAndGetClosestNodeWork -> Verify that the setClosestNode() and getClosestNode() methods work correctly
*/


  /*
  @author: Alvaro Tapia (alvartap@uma.es)
  @author: Christian Berdejo (cberdejo@uma.es)
   */
@Nested
@DisplayName("Test para la clase AvlNode")
public class AvlNodeTest {

  private AvlNode<Integer> node;

  @BeforeEach
  public void setUp() throws Exception {
    node = new AvlNode<Integer>(5);
  }

  @AfterEach
  public void tearDown() throws Exception {
    node = null;
  }

  @Test
  @DisplayName("Given a node, when setHeight, then node should have the expected height")
  public void shouldSetHeightReturnExpected()
  {
    int expectedHeight = 1000213;
    node.setHeight(expectedHeight);
    assertThat(node.getHeight()).isEqualTo(expectedHeight);
  }

  @Test
  @DisplayName("Given a node with no parent, when updateHeight, then node should have no parent, height zero and be a leaf")
  public void shouldRootNodeNotHaveParentAndHeightZeroAndLeaf()
  {
    assertThat(node.hasParent()).isFalse();
    assertThat(node.getHeight()).isEqualTo(0);
    assertThat(node.isLeaf()).isTrue();
    assertThat(node.getParent()).isNull();
  }

  @Test
  @DisplayName("Given a node, when setRight, then node should have the expected right child and height")
  public void shouldSetRightChildAndHeight()
  {
    AvlNode<Integer> rightChild = new AvlNode<>(6);

    node.setRight(rightChild);
    node.updateHeight();

    assertThat(node.hasRight()).isTrue();
    assertThat(node.getRight()).isEqualTo(rightChild);
    assertThat(node.getHeight()).isEqualTo(1);
    assertThat(node.hasOnlyALeftChild()).isFalse();
    assertThat(node.hasOnlyARightChild()).isTrue();
    assertThat(node.hasLeft()).isFalse();
  }

  @Test
  @DisplayName("Given a node, when setLeft, then node should have the expected left child and height")
  public void shouldSetLeftChildAndHeight()
  {
    AvlNode<Integer> leftChild = new AvlNode<>(4);

    node.setLeft(leftChild);
    node.updateHeight();

    assertThat(node.hasLeft()).isTrue();
    assertThat(node.getLeft()).isEqualTo(leftChild);
    assertThat(node.getHeight()).isEqualTo(1);
    assertThat(node.hasOnlyALeftChild()).isTrue();
    assertThat(node.hasOnlyARightChild()).isFalse();
    assertThat(node.hasRight()).isFalse();
  }

  @Test
  @DisplayName("Given a node, when setParent, then node should have the expected parent")
  public void shouldSetAndHasParentWork()
  {
    AvlNode<Integer> parent = new AvlNode<>(1);

    node.setParent(parent);

    assertThat(node.hasParent()).isTrue();
    assertThat(node.getParent()).isEqualTo(parent);
  }

  @Test
  @DisplayName("Given a node, when setItem, then node should have the expected item")
  public void shouldGetItemReturnExpected()
  {
    int expectedItem = 1000213;
    node.setItem(expectedItem);
    assertThat(node.getItem()).isEqualTo(expectedItem);
  }

  @Test
  @DisplayName("Given a node, when setClosestNode, then node should have the expected closest node")
  public void shouldSetAndGetClosestNodeWork()
  {
    AvlNode<Integer> closestNode = new AvlNode<>(1);

    node.setClosestNode(closestNode);

    assertThat(node.getClosestNode()).isEqualTo(closestNode);
  }
}


