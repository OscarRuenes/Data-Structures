public class MyBSTDemonstration2 {
  public static void main(String[] args) {
    // Demonstration of inserts, removals, size, contains, and printAll with different cases printed
    System.out.println("Empty tree:");
    MyRedBlackTree<Integer> tree = new MyRedBlackTree<Integer>();
    tree.printAll();

    System.out.println("One item:");
    tree.insert(0);
    tree.printAll();
    
    System.out.println("Empty again:");
    tree.remove(0);
    tree.printAll();

    System.out.println("Empty search:");
    System.out.println(tree.contains(0));

    System.out.println("Add more items in order:");
    for (int i = 5; i <= 30; i += 5) {
      tree.insert(i);
    }
    tree.printAll();
    
    System.out.println("Size:");
    System.out.println(tree.size());

    System.out.println("Insert at beginning:");
    tree.insert(0);
    tree.printAll();

    System.out.println("Insert in middle:");
    tree.insert(9);
    tree.insert(7);
    tree.insert(8);
    tree.insert(6);
    tree.printAll();

    System.out.println("Insert duplicate:");
    tree.insert(6);
    tree.printAll();
    
    System.out.println("Size:");
    System.out.println(tree.size());

    System.out.println("Remove non-existing:");
    tree.remove(12);
    tree.printAll();
    
    System.out.println("Size:");
    System.out.println(tree.size());

    System.out.println("Remove node with two children:");
    tree.remove(5);
    tree.printAll();

    System.out.println("Remove root:");
    tree.remove(10);
    tree.printAll();
    
    System.out.println("Size:");
    System.out.println(tree.size());

    System.out.println("Successful search:");
    System.out.println(tree.contains(9));

    System.out.println("Failed search:");
    System.out.println(tree.contains(5));
  }
}
