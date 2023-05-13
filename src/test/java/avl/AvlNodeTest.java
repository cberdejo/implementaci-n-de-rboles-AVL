package avl;

import org.junit.jupiter.api.*;

import static org.junit.Assert.*;
import static org.assertj.core.api.AssertionsForClassTypes.*;


/**
 * Created with IntelliJ IDEA. User: Antonio J. Nebro Date: 09/07/13 Time: 15:29
 */
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
  public void testHasLeft() {
    assertFalse("testHasLeft", node.hasLeft());
    AvlNode<Integer> node2 = new AvlNode<Integer>(6);
    node.setLeft(node2);
    assertTrue("testHasLeft", node.hasLeft());
  }

  @Test
  public void testHasRight() {
    assertFalse("testHasRight", node.hasRight());
    AvlNode<Integer> node2 = new AvlNode<Integer>(6);
    node.setRight(node2);
    assertTrue("testHasRight", node.hasRight());
  }

  @Test
  @DisplayName("Given a node, when setHeight, then node should have the expected height")
  public void shouldSetHeight() {
    int expectedHeight = 1000213;
    node.setHeight(expectedHeight);
    assertEquals("Height is different from expected.", expectedHeight, node.getHeight());
  }

  @Test
  @DisplayName("Given a node with no parent, when updateHeight, then node should have no parent, height zero and be a leaf")
  public void shouldRootNodeNotHaveParentAndHeightZeroAndLeaf()
  {

  }
}
