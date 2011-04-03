/**
 * Simple Implementation of Linked List
 * data structure in Java.
 */

public class LinkedList{

	private class Node{
		Object data = null;
		Node next = null;
	}

	private static int count = 0;
	private Node head = null;
	private Node tail = null;

	public void print(){
		Node n = head;
		while (n != null){
			System.out.println(n.data);
			n = n.next;
		}
	}

	public boolean add(Object element){
		try{
			if(head == null){
				head = new Node();
				head.data = element;
			}else{
				Node last = null;
				if (tail != null){
					last = tail;
				}else{
					last = head;
				}
				last.next = new Node();
				last.next.data = element;
				tail = last.next;
			}
		}catch(Exception e){
			return false;
		}
		++count;
		return true;
	}


	/** Removes the first occurance of node containing the element to be removed. **/
	public boolean remove(Object element){
		Node tempHead = head;
		Node prevNode = head;
		boolean isRemoved = false;
		try{
			if (tempHead != null){
				while (tempHead.next != null){
					if(tempHead.data.equals(element)){
						if(tempHead == head){ // case head is the node to be removed
							head.data = null; // make object eligible for GC
							head = head.next; // update head pointer
						}else{ // case some node between head and tail to be removed
							tempHead.data = null; // make object eligible for GC
							prevNode.next = tempHead.next;
						}
						isRemoved = true;
						break;
					}
					prevNode = tempHead;
					tempHead = tempHead.next;
				}
				// Case tail is the node to be removed
				if(tempHead.next == null && tempHead.data.equals(element)){
					prevNode.next = null;
					tail.data = null; // make object eligible for GC
					tail = prevNode; //update tail pointer
					isRemoved = true;
				}
				if(isRemoved){ // if successfully removed then decrease the list length
					tempHead.next = null; // free the pointer node for GC
					--count;
				}
			}
		}catch(Exception e){
			return false;
		}
		return isRemoved;
	}


	public Object getNode(int number){
		Node p = head;
		Object toReturn = null;
		if ( number >= 0 && number < this.length()){
			int c = -1;
			while(p.next != null){
				if(++c == number){
					toReturn = p.next;
					break;
				}
				p = p.next;
			}
		}
		return toReturn;
	}


	/**
	  This function returns previous node, the node required and the next node pointers
	  respectively to void duplicate iterations to get one node before and after.
	 **/
	private Node[] getNodePointerAlsoPrevAndNext(int number){
		Node[] sublist = new Node[3];

		if ( number >= 0 && number < this.length() ){
			int c = 0;
			Node tempNext = head;
			if ( number == 0  && head != null){ // if the node demanded is the head
				sublist[0] = null;
				sublist[1] = head;
				sublist[2] = head.next;
			}else{
				while(tempNext != null){
					if(++c == number){
						sublist[0] = tempNext;
						sublist[1] = tempNext.next;
						sublist[2] = (sublist[1] == null) ? null : tempNext.next.next; // is demanded node tail?
						break;
					}
					tempNext = tempNext.next;
				}
			}
		}
		return sublist;
	}


	public boolean removeAt(int number){
		try{
			if ( number >= 0 && number < this.length() ){

				Node[] sublist = this.getNodePointerAlsoPrevAndNext(number);
				if ( head != null && number == 0 ){ // if head is being removed
					head = sublist[2];
				}else if ( sublist[2] == null ){ // if tail is being removed
					sublist[0].next = null;
					tail = sublist[0];
				}else{
					sublist[0].next = sublist[2];
				}
				sublist[1].data = null; // for GC
				sublist[1].next = null; // for GC
				--count; // reduce the list length
				return true;
			}
		}catch(Exception e){}
		return false;
	}


	public boolean addAt(Object element, int number){
		try{
			if ( number >=0 && number < this.length() ){
				Node[] sublist = this.getNodePointerAlsoPrevAndNext(number);
				Node toAdd = new Node();
				if (number == 0){
					toAdd.data = element;
					toAdd.next = head;
					head = toAdd;
				}else if (number == this.length() - 1){
					toAdd.data = element;
					sublist[1].next = toAdd;
					tail = toAdd;
				} else {
					toAdd.next = sublist[1];
					toAdd.data = element;
					sublist[0].next = toAdd;
				}
				++count;
				return true;
			}
		}catch(Exception e){}
		return false;
	}


	public int length(){
		return count;
	}


	public Object getHead(){
		if(head != null){
			return head.data;
		}
		return null;
	}


	public Object getTail(){
		if(tail != null){
			return tail.data;
		}
		return null;
	}


	/**
	  Reversing the Singly LinledList.
	 **/
	public void reverse(){
		Node pNode = null;
		Node nNode = null;
		Node cNode = head;
		tail = head;
		while(cNode != null && cNode.next != null){
			nNode = cNode.next;
			cNode.next = pNode;
			pNode = cNode;
			cNode = nNode;
		}
		cNode.next = pNode;
		head = cNode;
	}


	public void createLoop(){
		System.out.println("Created Loop At: " + head.next.next.next.next.data);
		head.next.next.next.next.next = head;
		//tail.next = head;
	}


	/**
	  Floyd's cycle detection algorithm.
	  Tortoise & Hare Algorith Implementation.
	 **/
	public boolean detectLoop(){
		Node tortoise = head;
		Node hare = head.next;
		boolean hasLoop = false;
		while( hare != null && hare.next != null ){
			if (tortoise == hare){
				hasLoop = true;
				break;
			}
			tortoise = tortoise.next;
			hare = hare.next.next;
		}

		if(hasLoop)
			System.out.println("Loop is at element: " +  hare.data);

		return hasLoop;
	}


	/**
	  Brent's loop detection algorithm.
	 **/
	public boolean detectLoop2(){
		Node tortoise = head;
		Node hare = head;
		Node prevNode = hare;

		long limit = 2;
		long step = 1;

		boolean hasLoop = false;

		while (hare != null){
			prevNode = hare;
			hare = hare.next;
			step++;

			if(tortoise == hare){
				hasLoop = true;
				System.out.println("Breant's Algorithm. Loop is at: " + prevNode.data);
				break;
			}

			if (step == (limit-1)){
				step = 1;
				limit *= 2;
				tortoise = hare;
			}
		}
		return hasLoop;
	}
}
