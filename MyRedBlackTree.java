/**
 * Implemention file for CS 3345.HON.24F Programming Project #2.
 * <p>
 * Student name: Oscar Ruenes
 * Student NetID: oxr220005
 *
 * @param <E> The element's type.
 */
public class MyRedBlackTree<E extends Comparable<? super E>> {
  /**
   * Instantiate an empty red-black tree.
   */
  MyRedBlackTree() {
    this.size = 0;
    this.root = null;
  }

  /**
   * Print all elements of tree in sorted order with the color of each element's node.
   * Elements are printed one line at a time, each followed by a space and then
   * its color in paranethes.
   * Also, each element is indented a number of '=' equal to twice the node's depth.
   * <p>
   * For example, a tree containing 5, 10, 15, 20, 25, and 30 might be printed as
   * ```
   * ==5 (black)
   * 10 (black)
   * ====15 (black)
   * ==20 (red)
   * ====25 (black)
   * ======30 (red)
   * ```
   * <p>
   * Implementation should run in O(n) time for a tree of n elements.
   */
  public void printAll() {
    printAll(root, 0);
  }

  /**
   * Returns whether or not the tree contains the given element.
   * <p>
   * Implementation should run in O(log n) time for a tree of n elements.
   *
   * @param element The element to find.
   * @returns true if the tree contains the element or false otherwise.
   */
  public boolean contains(E element) {
    Node<E> node = root;

    while (node != null) {
      int compareResult = element.compareTo(node.element);

      if (compareResult < 0) { // element < node.element
        node = node.left;
      } else if (compareResult > 0) { // element > node.element
        node = node.right;
      } else { // element == node.element
        return true;
      }
    }

    return false;
    
  }

  /**
   * Returns the minimum element of the tree.
   * <p>
   * Implementation should run in O(log n) time for a tree of n elements.
   *
   * @returns The minimum element of the tree or null if tree is empty.
   */
  public E findMin() {
    if(size == 0)
      return null;
    Node<E> node = root;
    while (node.left != null) {
      node = node.left;
    }
    return node.element;
  }

  /**
   * Returns the maximum element of the tree.
   * <p>
   * Implementation should run in O(log n) time for a tree of n elements.
   *
   * @returns The maximum element of the tree or null if tree is empty.
   */
  public E findMax() {
    if(size == 0)
      return null;
    Node<E> node = root;
    while (node.right != null) {
      node = node.right;
    }
    return node.element;
  }
  // rotates this node with its left child v
  private Node<E> rotateWithLeftChild(Node<E> p){
    boolean newRoot = false;
    if(p == root)       // update tree's root node if p is being rotated out
      newRoot = true;
    Node<E> v = p.left;
    v.parent = p.parent;// v is being brought up, new parent is p's parent
    if(p != root && p == p.parent.left) // p's parent must connect to v
      p.parent.left = v;
    else if (p != root)
      p.parent.right = v;
    p.left = v.right; // p gets v's right subtree
    if(v.right != null)
      v.right.parent = p; // if the subtree is nonempty, its new parent is now p
    v.right = p;    // p is now v's right child
    p.parent = v;   // p's parent is v
    if(newRoot)
      root = v;
    return v;
  }
  // rotates this node with its right child v
  private Node<E> rotateWithRightChild(Node<E> p){
    boolean newRoot = false;
    if(p == root)       // update tree's root node if p is being rotated out
      newRoot = true;
    Node<E> u = p.right;
    u.parent = p.parent;// u is being brought up, new parent is p's parent
    if(p != root && p == p.parent.left) // p's parent must connect to u
      p.parent.left = u;
    else if (p != root)
      p.parent.right = u;
    p.right = u.left; // p gets u's left subtree
    if(u.left != null)
      u.left.parent = p;  // if the subtree is nonempty, its new parent is now p
    u.left = p;     // p is now u's left child
    p.parent = u;   // p's parent is u
    if(newRoot)
      root = u;
    return u;
  }

  /**
   * Insert Helper function used for recursion.
   * @param Node The node to be recolored.
   */

  private void insertHelper(Node<E> v){
    if(v == root){
      v.color = true;
      return;
    }
    if(v.parent.color){ // parent is black, coloring is valid
      return;
    }
    if(v.parent == v.parent.parent.left){ // p is red left child
      if(v.parent.parent.right.color == false){ // p has red sibling
        v.parent.color = true;
        v.parent.parent.right.color = true;
        v.parent.parent.color = false;
        insertHelper(v.parent.parent);
      }
      else{ // p has black sibling
        if(v == v.parent.left){ // v is outside grandchild 
          v.parent.color = true;
          v.parent.parent.color = false;
          rotateWithLeftChild(v.parent.parent);
          return;
        }
        else{ // v is inside grandchild
          v.color = true;
          v.parent.parent.color = false;
          rotateWithRightChild(v.parent);
          rotateWithLeftChild(v.parent.parent);
          return;
        }
      }
    }
    else{ // p is red right child
      if(v.parent.parent.left.color == false){ // p has red sibling
        v.parent.color = true;
        v.parent.parent.left.color = true;
        v.parent.parent.color = false;
        insertHelper(v.parent.parent);
      }
      else{ // p has black sibling
        if(v == v.parent.right){ // v is outside grandchild 
          v.parent.color = true;
          v.parent.parent.color = false;
          rotateWithRightChild(v.parent.parent);
          return;
        }
        else{ // v is inside grandchild
          v.color = true;
          v.parent.parent.color = false;
          rotateWithLeftChild(v.parent);
          rotateWithRightChild(v.parent.parent);
          return;
        }
      }
    }
  }

  /**
   * Inserts a new element into the tree.
   * If the element already exists in the tree, this method makes no changes.
   * <p>
   * Implementation should run in O(log n) time for a tree of n elements.
   *
   * @param element The element to be inserted.
   */
  public void insert(E element) {
    Node<E> newNode = new Node<E>(element);
    if(size == 0){
      root = newNode;
      root.color = true;
      this.size++;
      return;
    }
    newNode.color = false;
    Node<E> node = root;
    while (node != newNode) {
      int compareResult = element.compareTo(node.element);

      if (compareResult < 0) { // element < node.element
        if (node.left == null) {
          if(node.color == true){ // giving black node a child
            node.left = newNode;
            node.left.parent = node;
            this.size++;
            return;
          }
          else{ // giving red node a red left child, two cases
            if(node.parent.left == null || node.parent.right == null || ((node.parent.right != null && node.parent.right.color )|| (node.parent.left != null && node.parent.left.color))){ // p has black sibling
              if(node == node.parent.left){ // p is the left child, v is an out grandchild
                node.left = newNode;  
                node.left.parent = node;
                node.parent.color = false;
                node.color = true;
                rotateWithLeftChild(node.parent);
                this.size++;
                return;
              }
              else{ // p is the right child, v is an inner grandchild
                node.left = newNode; 
                node.left.parent = node;
                node.parent.color = false;
                node.left.color = true;
                rotateWithLeftChild(node);
                rotateWithRightChild(node.parent.parent);
                this.size++;    
                return;    
              }
            }
            else{// p has red sibling
              node.left = newNode; 
              node.left.parent = node;
              this.size++;
              node.parent.left.color = true;
              node.parent.right.color = true;
              node.parent.color = false;
              insertHelper(node.parent);
              return;
            }
          }
        }
        node = node.left;
      } else if (compareResult > 0) { // element > node.element
          if (node.right == null) {
            if(node.color == true){ // giving black node a child
              node.right = newNode;
              node.right.parent = node;
              this.size++;
              return;
            }
            else{ // giving red node a red parent, two cases
              if(node.parent.left == null || node.parent.right == null || ((node.parent.right != null && node.parent.right.color )|| (node.parent.left != null && node.parent.left.color))){// p has black sibling
                if(node == node.parent.right){ // p is the right child, v is an out grandchild
                  node.right = newNode;
                  node.right.parent = node;
                  node.parent.color = false;
                  node.color = true;
                  rotateWithRightChild(node.parent);
                  this.size++;
                  return;
                }
                else{ // p is the left child, v is an inner grandchild
                  node.right = newNode;
                  node.right.parent = node;
                  node.parent.color = false;
                  node.right.color = true;
                  rotateWithRightChild(node);
                  rotateWithLeftChild(node.parent.parent);
                  this.size++;
                  return;
                }
              }
              else{// p has red sibling
                node.right = newNode; 
                node.right.parent = node;
                this.size++;
                node.parent.right.color = true;
                node.parent.left.color = true;
                node.parent.color = false;
                insertHelper(node.parent);
                return;
              }
            }
          }
        node = node.right;
      } else { // element == node.element
        newNode = node; // Duplicate; do nothing.
      }
    }
  }
   /**
   * Remove Helper function for recursion.
   * @param Node The node to be removed.
   */
  private void removeHelper(Node<E> v){
    if(v == root){
      if(root.left != null && !root.left.color){ // the root's red child is the left
        root.left.parent = null;
        root.right.parent = root.left;
        root.left.right = root.right;
        root = root.left;
        return;
      }
      else{ // the root's red child is the right child
        root.right.parent = null;
        root.left.parent = root.right;
        root.right.right = root.left;
        root = root.right;
        return;
      }
    }
    if(v.parent.left == v){ // v is the left child
      if(v.parent.right != null && !v.parent.right.color){ // v has a red sibling
        v.parent.right.color = true;
        v.parent.color = false;
        rotateWithRightChild(v.parent);
      }
      if((v.parent.right.left != null && !v.parent.right.left.color) || (v.parent.right.right != null && !v.parent.right.right.color)){ // v black sibling has red child
        if(v.parent.right.left != null && !v.parent.right.left.color){ // v's sibling's red child is close to v, case 1a)
          v.parent.right.left.color = v.parent.color; // color c same as p 
          v.parent.color = true; // make p black 
          rotateWithLeftChild(v.parent.right);
          rotateWithRightChild(v.parent);  
          if(v.left != null){ // v's child is the left
            v.left.parent = v.parent;
            v.parent.left = v.left;
            v = null;
            return;
          }
          else if (v.right != null){ // v's child is the right child
            v.right.parent = v.parent;
            v.parent.left = v.right;
            v = null;
            return;
          }
          else{
            v.parent.left= null;
            return;
          }
        }
        else{ // v's sibling's red child is far from v, case 1b)
          v.parent.right.color = v.parent.color; // color w as p's old color black 
          v.parent.color = true; // make p black
          v.parent.right.right.color = true; // make c black

          rotateWithRightChild(v.parent);
           if(v.left != null){ // v's child is the left
            v.left.parent = v.parent;
            v.parent.left = v.left;
            v = null;
            return;
          }
          else if (v.right != null){ // v's child is the right child
            v.right.parent = v.parent;
            v.parent.left = v.right;
            v = null;
            return;
          }
          else{
            v.parent.left= null;
            return;
          }
        }
      }
      else{ // v's sibling has no red children -> sibling is a "2-node"
        if(!v.parent.color){ // v's parent p is red, we can swap its color and v's sibling's color
          v.parent.color = true;
          v.parent.right.color = false;
          if(v.left != null){ // v's child is the left
            v.left.parent = v.parent;
            v.parent.left = v.left;
            v = null;
            return;
          }
          else if (v.right != null){ // v's child is the right child
            v.right.parent = v.parent;
            v.parent.left = v.right;
            v = null;
            return;
          }
          else{
            v.parent.left= null;
            return;
          }
        }
        else{ // parent is also black, must recurse
          v.parent.right.color = false;
          Node<E> dummyNode = new Node<E>(null);
          if(v.parent == v.parent.parent.left)
            v.parent.parent.left = dummyNode;
          else  
            v.parent.parent.right = dummyNode;
          dummyNode.parent = v.parent.parent;
          dummyNode.left = v.parent;
          v.parent.parent = dummyNode;
          if(v.left != null){ // v's child is the left
            v.left.parent = v.parent;
            v.parent.left = v.left;
            v = null;
          }
          else if (v.right != null){ // v's child is the right child
            v.right.parent = v.parent;
            v.parent.left = v.right;
            v = null;
          }
          else{
            v.parent.left= null;
          }
          removeHelper(dummyNode);
          return;
        }
      }
    }
    else{// v is the right child
      if(v.parent.left != null && !v.parent.left.color){ // v has a red sibling
        v.parent.left.color = true;
        v.parent.color = false;
        rotateWithLeftChild(v.parent);
      }
      if((v.parent.left.left != null && !v.parent.left.left.color) || (v.parent.left.right != null && !v.parent.left.right.color)){ // v black sibling has red child
        if(v.parent.left.right != null && !v.parent.left.right.color){ // v's sibling's red child is close to v, case 1a)
          v.parent.left.right.color = v.parent.color; // color c same as p  
          v.parent.color = true; // make p black
          rotateWithRightChild(v.parent.left);
          rotateWithLeftChild(v.parent);  
          if(v.left != null){ // v's child is the left
            v.left.parent = v.parent;
            v.parent.left = v.left;
            v = null;
            return;
          }
          else if (v.right != null){ // v's child is the right child
            v.right.parent = v.parent;
            v.parent.left = v.right;
            v = null;
            return;
          }
          else{
            v.parent.right= null;
            return;
          }
        }
        else{ // v's sibling's red child is far from v, case 1b)
          v.parent.left.color = v.parent.color; // color w as p's old color black 
          v.parent.color = true; // make p black
          v.parent.left.left.color = true; // make c black
          rotateWithLeftChild(v.parent);
          if(v.left != null && !v.left.color){ // v's red child is the left
            v.left.parent = null;
            v.right.parent = v.left;
            v.left.right = v.right;
            v = v.left;
            return;
          }
          else if (v.right != null){ // v's red child is the right child
            v.right.parent = null;
            v.left.parent = v.right;
            v.right.right = v.left;
            v = v.right;
            return;
          }
          else{
            v.parent.right= null;
            return;
          }
        }
      }
      else{ // v's sibling has no red children -> sibling is a "2-node"
        if(!v.parent.color){ // v's parent p is red, we can swap its color and v's sibling's color
          v.parent.color = true;
          v.parent.left.color = false;
          if(v.left != null){ // v's child is the left
            v.left.parent = v.parent;
            v.parent.left = v.left;
            v = null;
            return;
          }
          else if (v.right != null){ // v's child is the right child
            v.right.parent = v.parent;
            v.parent.left = v.right;
            v = null;
            return;
          }
          else{
            v.parent.right = null;
            return;
          }
        }
        else{ // parent is also black, must recurse
          v.parent.left.color = false;
          Node<E> dummyNode = new Node<E>(null);
          if(v.parent == v.parent.parent.left)
            v.parent.parent.left = dummyNode;
          else  
            v.parent.parent.right = dummyNode;
          dummyNode.parent = v.parent.parent;
          dummyNode.left = v.parent;
          v.parent.parent = dummyNode;
          removeHelper(dummyNode);
          return;
        }
      }
    }
  }

  /**
   * Removes the element from the tree.
   * If the element does not exist in the tree, this method makes no changes.
   * <p>
   * Implementation should run in O(log n) time for a tree of n elements.
   *
   * @param element The element to be removed.
   */
  public void remove(E element) {
    if(size == 0){
      return;
    }
    boolean swap = false;
    Node<E> maxLeftSubTree = root.left;
    if(size == 1 && 0 == element.compareTo(root.element)){
      root = null;
      this.size--;
      return;
    }
    else if (0 == element.compareTo(root.element)){
      E oldRootElement = root.element;
      if(maxLeftSubTree != null){
        while(maxLeftSubTree.right != null){
          maxLeftSubTree = maxLeftSubTree.right;
        }
      }
      else{ // root only has one child and it is the right child due to perfectly balanced condition for the tree
        root.right.parent = null;
        root = root.right;
        this.size--;
        return;
      }
      swap = true;
      root.element = maxLeftSubTree.element;
      maxLeftSubTree.element = oldRootElement;
    }
    Node<E> v = root;
    if(swap){
      v = maxLeftSubTree;
    }
      while (v != null) {
        int compareResult = element.compareTo(v.element);

        if (compareResult < 0) { // element < v.element
          v = v.left;
        } else if (compareResult > 0) { // element > v.element
            v = v.right;
        } else { // element == v.element
            if(v.left == null && v.right == null){// v is a leaf
              if(!v.color){ // red leaf, just drop it.
                if(v.parent.left == v){ // v is the left child
                  v.parent.left = null;
                  v = null;
                  this.size--;
                  return;
                }
                else{
                  v.parent.right = null;
                  v = null;
                  this.size--;
                  return;
                }
              }
              else{ // black leaf, recursion needed
                this.size--;
                removeHelper(v);
                return;
              }
            }
            if(v.color && ((v.right != null && !v.right.color) || (v.left != null && !v.left.color))){ // v is black with one red child
              E oldElement = v.element;
              maxLeftSubTree = v.left;
              if(maxLeftSubTree != null){
                while(maxLeftSubTree.right != null){
                  maxLeftSubTree = maxLeftSubTree.right;
                }
              }
              else{ // v only has one child and it is the right child due to perfectly balanced condition for the tree
                v.right.parent = v.parent;
                if(v == v.parent.left)
                  v.parent.left = v.right;
                else
                  v.parent.right = v.right;
                v.right.color = v.color;  // does not affect black path
                v = null;
                this.size--;
                return;
              }
              v.element = maxLeftSubTree.element;
              maxLeftSubTree.element = oldElement;
              v = maxLeftSubTree;
            }
            else{// v is a black node with two black children, element swap with max of left subtree and remove.
              E oldElement = v.element;
              maxLeftSubTree = v.left;
              if(maxLeftSubTree != null){
                while(maxLeftSubTree.right != null){
                  maxLeftSubTree = maxLeftSubTree.right;
                }
              }
              else if (v.right != null){ // v only has one right child
                v.right.parent = v.parent;
                if(v == v.parent.left)
                  v.parent.left = v.right;
                else
                  v.parent.right = v.right;
                v.right.color = v.color; // does not affect black path
                v = null;
                this.size--;
                return;
              }
              else { // v is a leaf

              }
              v.element = maxLeftSubTree.element;
              maxLeftSubTree.element = oldElement;
              v = maxLeftSubTree;
            }
          }
      }
    return;
  }

  /**
   * Returns number of elements in tree.
   * <p>
   * Implementation should run in O(1) time for a tree of n elements.
   *
   * @returns Number of elements in list.
   */
  public int size() {
    return this.size;
  }
  private static class Node<E> {
    public E element; // public to simplify remove
    public Node<E> left, right, parent;
    public boolean color;

    /**
     * Instantiate a new node.
     *
     * @param element The node's element.
     */
    Node(E element) {
      this.element = element;
    }
  }
  private void printAll(Node<E> node, int depth) {
    assert (depth >= 0);

    if (node != null) {
      printAll(node.left, depth + 1);                 //handle left subtree

      StringBuilder builder = new StringBuilder();
      for (int spaces = 0; spaces < depth; spaces++) { // shows depth
        builder.append("==");
      }
      builder.append(node.element);
      if(node.color)                                  // node.color == true implies it is black
        builder.append(" (black)");
      else
        builder.append(" (red)");                 // node.color == false implies it is red
      if(node.parent != null)                         // print node's parent's element (very useful for debugging)
        builder.append(" " + "Parent: " + node.parent.element);
      if(node.left != null)                           // print node's left child if the have one
        builder.append(" Left: " + node.left.element);
      if(node.right != null)                          // print node's right child if they have one
        builder.append(" Right: " + node.right.element);
      System.out.println(builder.toString());

      printAll(node.right, depth + 1);                //handle right subtree, inorder.
    }
  }
  private Node<E> root;
  private int size;
}

