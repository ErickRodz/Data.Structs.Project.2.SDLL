package edu.uprm.ece.icom4035.list;

import java.util.Iterator;
import java.util.NoSuchElementException;



public class SortedCircularDoublyLinkedList<E extends Comparable<E>> implements SortedList<E> {

	private Node<E> header;
	private int currentSize;
	
	
	public SortedCircularDoublyLinkedList() {
		this.header = new Node<E>();
		header.setPrev(header);
		header.setNext(header);
		this.currentSize = 0;
}
	
	
		//------------------------------------------------------------------------------------------------
		//------------------------------------------------------------------------------------------------
		//------------------------------------------------------------------------------------------------
		//Iterator Calls
	@Override
	public Iterator<E> iterator() {
		return new SortedListFIterator();
	}
	@Override
	public Iterator<E> iterator(int index) {
		return new SortedListFIterator(index);
	}
	
	@Override
	public ReverseIterator<E> reverseIterator() {
		return new SortedListRIterator();
	}

	@Override
	public ReverseIterator<E> reverseIterator(int index) {
		return new SortedListRIterator(index);
	}
		//------------------------------------------------------------------------------------------------
		//------------------------------------------------------------------------------------------------
		//------------------------------------------------------------------------------------------------
	@Override
	public boolean add(E obj) {
		
		Node<E> nta = new Node(obj);
		
		if (currentSize == 0) {
			nta.setNext(header);
			nta.setPrev(header);
			
			header.setNext(nta);
			header.setPrev(nta);

			this.currentSize++;
			return true;

		}else {
			Node<E> crr = header.getNext();
			while (crr != header) {
				if(obj.compareTo(crr.getElement()) < 0) {
					
					nta.setNext(crr);
					nta.setPrev(crr.getPrev());
					
					nta.getPrev().setNext(nta);
					crr.setPrev(nta);
					
					currentSize++;
					return true;
				}
				crr = crr.getNext();
			}

			
			nta.setNext(header);
			
			nta.setPrev(header.getPrev());
			header.getPrev().setNext(nta);
			
			header.setPrev(nta);
			currentSize++;
			
			return true;
		}
	}

	@Override
	public int size() {
		return this.currentSize;
	}

	@Override
	public boolean remove(E obj) {
		
		Node<E> ntr = header.getNext();
		
		while(ntr != header) {
			if(ntr.getElement().equals(obj)) {
				Node<E> p = ntr.getPrev();
				Node<E> n = ntr.getNext();
			
				p.setNext(ntr.getNext());
				n.setPrev(ntr.getPrev());
				ntr.clear();
				currentSize--;
				return true;
			}
			ntr = ntr.getNext();
		}
		
		
		return false;
	}

	@Override
	public boolean remove(int index) {
		
		if(index < 0|| index >  currentSize) {return false;}
		
		int count = 0;
		
		Node<E>ntr = header.getNext();
		
		
		while(count < index) {
			ntr = ntr.getNext();
			count++;
		}
		
		Node<E> p = ntr.getPrev();
		Node<E> n = ntr.getNext();
		
		p.setNext(n);
		n.setPrev(p);
		ntr.clear();
		currentSize--;
		
		return true;
	}

	@Override
	public int removeAll(E obj) {
		
		int count = 0;
		Node<E>ntr = header.getNext();
		
			while(ntr != header) {
				
				if(ntr.getElement() == obj) {
					Node<E> tmp = ntr;
					ntr = ntr.getNext();
					tmp.getPrev().setNext(tmp.getNext());
					tmp.getNext().setPrev(tmp.getPrev());
					count++;
					currentSize--;
				}else {
					ntr = ntr.getNext();
				}
			}
			
		
		return count;
	}

	@Override
	public E first() {
		if(currentSize  == 0) {return null;}
		return header.getNext().getElement();
	}

	@Override
	public E last() {
		if(currentSize  == 0) {return null;}
		return header.getPrev().getElement();
	}

	@Override
	public E get(int index) {
		if(index < 0 || index > currentSize) {throw new IndexOutOfBoundsException();}
		
		Node<E> crr = header.getNext();
		int count = 0;
		
		while (count < index) {
			crr = crr.getNext();
			count++;
		}		
		return crr.getElement();
	}

	@Override
	public void clear() {
		
		while(!this.isEmpty()) {
		this.remove(0);
		}
	}

	@Override
	public boolean contains(E e) {
		if(this.isEmpty()) {return false;}
		
		Node<E> ntf = header.getNext();
		
		while(ntf != header) {
			if(ntf.getElement().equals(e)) { return true; }
			ntf.getNext();
		} 
		return false;
	}

	@Override
	public boolean isEmpty() {
		return currentSize == 0;
	}

	public int firstIndex(E e) {
		if(this.isEmpty())
			return -1;
		else {
			Node<E> ntf = this.header.getNext();
			boolean f = false;
			int count  = 0;
			
			while(ntf != this.header) {
				if(ntf.getElement().equals(e)) {
					f = true;
					break;
				}
				count++; 
				ntf = ntf.getNext();
			}
			if(f == true)
				return count;
			else
				return -1;
		}
	}

	@Override
	public int lastIndex(E e) {
		if(this.isEmpty()) {return -1;}
		
		Node<E> ntf = header.getPrev();
		boolean f = false;
		int count = currentSize-1;
		
		while(ntf != header) {
			 if(ntf.getElement().equals(e)) {
				 f = true;
				 break;
			 }
			 count++;
			 ntf = ntf.getNext();
		}
		if(f == true) {return count;}
		else
		return -1;
		
	}
	
	//------------------------------------------------------------------------------------------------
	//------------------------------------------------------------------------------------------------
	//------------------------------------------------------------------------------------------------
	//Node Class
	private static class Node<E>{
		private E element; 
		private Node<E> prev, next; 

		public Node() {
			element = null;
			prev = next = null;
		}
		
		public Node(E e) { 
			element = e; 
			prev = next = null;
		}
		public Node(E e, Node<E> p, Node<E> n) { 
			prev = p; 
			next = n; 
		}
		public Node<E> getPrev() {
			return prev;
		}
		public void setPrev(Node<E> prev) {
			this.prev = prev;
		}
		public Node<E> getNext() {
			return next;
		}
		public void setNext(Node<E> next) {
			this.next = next;
		}
		public E getElement() {
			return element; 
		}

		public void setElement(E data) {
			this.element = data; 
		} 
		public void clear() { 
			element = null;
			prev = next = null; 
		}
		
		
	}
	
	//------------------------------------------------------------------------------------------------
	//------------------------------------------------------------------------------------------------
	//------------------------------------------------------------------------------------------------
	//Special Iterators
	
	public class SortedListFIterator implements Iterator<E>{
		private Node<E> crr ;
		
		
		public SortedListFIterator() {
			crr = header.getNext();
		}
		
		public SortedListFIterator(int index) {
			if(index < 0 || index > currentSize) {throw new IndexOutOfBoundsException();}
			int count = 0;
			while(count < index-1) {  crr = crr.getNext();  count++; }
		}
	
		public boolean hasNext() {
			return crr != header;
		}

		public E next() {
			if(!hasNext()) {throw new NoSuchElementException();}
			E e = crr.getElement();
			crr = crr.getNext();
			return e;
		}
		
	}
	
	
	//------------------------------------------------------------------------------------------------
	//------------------------------------------------------------------------------------------------
	//------------------------------------------------------------------------------------------------
	
	
	public class SortedListRIterator implements ReverseIterator<E>{
		
		private Node<E> crr;
	
		
		public SortedListRIterator() {
			this.crr = header.getPrev();
		}
		
		public SortedListRIterator(int index) {
			if(index < 0 || index > currentSize) {throw new IndexOutOfBoundsException();}
			int count = 0;
			while(count < index-1) { crr = crr.getPrev(); count++;}
			
		}
		public boolean hasPrevious() {
			return crr != header;
		
		}

		public E previous() {
			if(!hasPrevious()) {throw new NoSuchElementException();}
			E e = crr.getElement();
			crr = crr.getPrev();
			return e;
		}
	}
	//------------------------------------------------------------------------------------------------
	//------------------------------------------------------------------------------------------------
	//------------------------------------------------------------------------------------------------
	

}
