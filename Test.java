public class Test{
    public static void main(String[] args){
	LinkedList l = new LinkedList();
	l.add("One");
	l.add("Two");
	l.add("Three");
	l.add("Four");
	l.add("Five");
	l.add("Six");
	l.add("Seven");
	l.print();

	System.out.println("\n\nReversing the list...\n\n");
	l.reverse();
	l.print();

	l.createLoop();
	System.out.println(" This linkedlist has loop: " + l.detectLoop());
    }
}


