package edu.uprm.cse.datastructures.cardealer.util;


import java.util.Comparator;
import java.util.Iterator;

import edu.uprm.cse.datastructures.cardealer.util.SortedList;

public class CircularSortedDoublyLinkedList<E> implements SortedList<E>
{
	private DNode<E> first;
	private int size;
	private AbstractComparator<E> comp;
	
	//Uses dummy header
	public CircularSortedDoublyLinkedList()
	{
		this.comp = (AbstractComparator<E>) comparator();
		this.first = new DNode<E>(null,this.first,this.first);
		this.size = 0;
	}
	
	public Iterator<E> iterator() 
	{
		return  (Iterator<E>) new CircularIterator(this.first);
	}

	public Comparator<E> comparator()
	{
		return new AbstractComparator();
	}
	public boolean add(E obj)
	{
//		boolean contains = true;
//		if(this.contains(obj))contains = false;
		if(contains(obj))return true;	
		if(this.isEmpty())
		{
			DNode<E> temp = new DNode(obj,this.first, this.first);
			this.first.setNext(temp);
			this.first.setPrev(temp);
			this.size++;
			return false;
		}
		else{
			DNode<E>current = this.first.getPrev();
			DNode<E> nuevo = new DNode(obj, current, this.first);
			current.setNext(nuevo);
			this.first.setPrev(nuevo);
		}
		
		this.size++;
		//Triple sort because we're too dumb to find an efficient execution
		arraySort();
		arraySort();
		arraySort();
		return false;
	}

	public int size()
	{
		return this.size;
	}

	public boolean remove(E obj)
	{
		if(isEmpty())
			return false;
		if(!contains(obj))
			return false;
		boolean removed = false;
		
		DNode<E> ptr = this.first.getNext();
		for(int i = 0 ; i < this.size(); i++)
		//while(!ptr.equals(this.getFirstNode()))
		{
			if(ptr.getElement().equals(obj))
			{
				ptr.getPrev().setNext(ptr.getNext());
				ptr.getNext().setPrev(ptr.getPrev());
				
				ptr.setElement(null);
				//ptr.cleanLinks();
				this.size--;
				removed = true;
				break;
			}
			ptr = ptr.getNext();
		}
		

//		arraySort();
//		arraySort();
//		arraySort();
		return removed;
	}

	protected Node<E> getFirstNode()
	{
		return this.first.getNext();
	}
	
	public boolean remove(int index)
	{
		if(index < 0 | index >= this.size())
			return false;
		
		DNode<E> ptr = this.first.getNext();
		int i = 0;
		while(i <= index)
		{
			ptr = ptr.getNext();
			i++;
		}
		ptr.getPrev().setNext(ptr.getNext());
		ptr.getNext().setPrev(ptr.getPrev());
		
		ptr.setElement(null);
//		ptr.cleanLinks();
		
		this.size--;
		//arraySort();arraySort();arraySort();
		return true;
	}

	public int removeAll(E obj) 
	{
		if(!this.contains(obj))
			return 0;
		
		int i = 0;
		while(this.contains(obj))
		{
			remove(obj);
			i++;
		}
		return i;
	}

	public E first() {
		return this.first.getNext().getElement();
	}

	//Can be more efficient but whatever
	public E last() {
		DNode<E> ptr = this.first;
		
		while(!ptr.getNext().equals(this.first))
		{
			ptr = ptr.getNext();
		}
		return ptr.getElement();
	}

	public E get(int index) {
		if(index < 0 |index > this.size())
			return null;
		
		DNode<E> ptr = this.first;
		int i = 0;
		while(i <= index)
		{
			ptr = ptr.getNext();
			i++;
		}
		return ptr.getElement();
	}

	public void clear()
	{
		if(this.isEmpty())
			return;
		
		DNode<E> temp = this.first;
		DNode<E> after = temp.getNext();
		while(!after.getNext().equals(null))
		{
			temp.cleanLinks();
			temp.setElement(null);
			temp = after;
			after = after.getNext();
			this.size--;
		}
	}

	public boolean contains(E e) 
	{
		if(isEmpty())return false;
		
		DNode<E> ptr = this.first.getNext();
		boolean contains = false;
		
		//while(!ptr.equals(this.getFirstNode()))
		for(int i = 0; i < this.size()  ; i++, ptr = ptr.getNext())
		{
			if(ptr.getElement().equals(e))
			{
				contains = true;
				break;
			}

		}
		ptr = null;
		return contains;
	}

	public boolean isEmpty() {
		return this.size() == 0;
	}

	//TODO Might be wrong, change to a for loop
	public int firstIndex(E e)
	{
		//Returns -1 if list is empty
		if(this.isEmpty())
			return -1;
		Iterator<E> iter = iterator();
		E temp;
		int ind = 0;
		while(iter.hasNext())
		{
			temp = iter.next();
			ind++;
			if(temp.equals(e));
				return ind;
			
			
		}
		
		return -1;
	}

	public int lastIndex(E e) {
		
		if(this.isEmpty() || !this.contains(e))
			return -1;
		
		Node<E> temp = this.first.getNext();
		int ind = 0;
		
		for(int i = 0; i < this.size(); i++, temp = temp.getNext())
		{
			if(temp.getElement().equals(e))
				ind = i;
		}
		
		return ind;
	}
	
	private void addFirstNode(DNode<E> nuevo)
	{
		addNodeAfter(this.first, nuevo); 
	}

	private void arraySort()
	{
		E[] arr = this.toArray();
		
		for(int i = 0; i < arr.length-1; i++)
		{
			if(comp.compare(arr[i], arr[i+1])>0)
				swap(arr,i, i+1);
//			if(comp.compare(arr[i], arr[i+1])<0)
//				swap(arr[i+1], arr[i]);
		}
		
		for(int i = 0; i < arr.length-1; i++)
		{
			if(comp.compare(arr[i], arr[i+1])>0)
				swap(arr,i, i+1);
//			if(comp.compare(arr[i], arr[i+1])<0)
//				swap(arr[i+1], arr[i]);
		}
		
		for(int i = 0; i < arr.length-1; i++)
		{
			if(comp.compare(arr[i], arr[i+1])>0)
				swap(arr,i, i+1);
//			if(comp.compare(arr[i], arr[i+1])<0)
//				swap(arr[i+1], arr[i]);
		}
		
		DNode<E> current = this.first.getNext();
		//DNode<E> current = this.first;
		for(int i = 0 ; i <arr.length; i++)
		{
			current.setElement(arr[i]);
			current =current.getNext();
		}
	}
	private void sort()
	{
		int n = 0;
		DNode<E> temp = this.first;
		DNode<E> after = this.first.getNext();
		while(!after.getNext().equals(this.first))
		//while(iter.hasNext())
		{
			if(temp.getElement()!=null | after.getElement()!=null )
				n = comp.compare(temp.getElement(),after.getElement());
			if(temp.getElement()== null |after.getElement()==null )
				n=0;
			switch(n)
			{
				case 1:
					swapElements(temp, after);
					break;
			
					
				default:
					break;
			}
			temp = after;
			after = after.getNext();
		}
		
		temp = after;
		after = after.getNext();
		 n = comp.compare(temp.getElement(),after.getElement());
		switch(n)
		{
			case 1:
				swapElements(temp ,after);
				break;
		
				
			default:
				break;
		}
	}
	
	private void swap(E[] arr,int target, int rec)
	{
		E temp = arr[rec];
		arr[rec] = arr[target];
		arr[target] = temp;
	}
	
	private void swapElements(DNode<E> target, DNode<E> rec)
	{
		E temp = target.getElement();
		target.setElement(rec.getElement());
		rec.setElement(temp);
	}
	
	private E[] toArray()
	{
		DNode<E> ptr = this.first.getNext();
		E[] arr = (E[])new Object[this.size()];
		
		for(int i = 0 ; i < this.size(); i++)
		{
			arr[i] = ptr.getElement();
			ptr = ptr.getNext();
		}
		
		return arr;
	}
	
	
	public void printList()
	{
		DNode<E> ptr = this.first;
		//while(this.iterator().hasNext())
		while(ptr.getNext()!= this.first)
		{
			System.out.println(ptr);
			ptr = ptr.getNext();
		}
		System.out.println(ptr);
	}
	
	private void addNodeAfter(DNode<E> target, DNode<E> nuevo) {
		
		if(target.equals(this.getLastNode()))
		{
			addLastNode(nuevo);
			return;
		}
		DNode<E> dnuevo = (DNode<E>) nuevo; 
		DNode<E> nBefore = (DNode<E>) target; 
		DNode<E> nAfter = nBefore.getNext(); 
		nBefore.setNext(dnuevo); 
		nAfter.setPrev(dnuevo); 
		dnuevo.setPrev(nBefore); 
		dnuevo.setNext(nAfter); 
		
		this.size++; 
	}
	
	private void addLastNode(DNode<E> nuevo)
	{ 
		DNode<E> dnuevo = (DNode<E>) nuevo; 
		DNode<E> trailer = this.getLastNode();
		DNode<E> nBefore = trailer.getPrev();  
		
		nBefore.setNext(dnuevo); 
		trailer.setPrev(dnuevo); 
		dnuevo.setPrev(nBefore); 
		dnuevo.setNext(trailer); 
		
		this.size++; 
	}

	

	private DNode<E> getLastNode(){
//		if (isEmpty()) 
//			throw new IllegalStateException("List is empty."); 
		if(isEmpty())
			return this.first;
		DNode<E> ptr = this.first;
		while(!ptr.getNext().equals(this.first))
			ptr = ptr.getNext();
		
		return ptr;
	}


	
	private void addNodeBefore(DNode<E> target, DNode<E> nuevo)
	{
		if(target.equals(this.first))
		{
			addFirstNode(nuevo);
		}
		
		DNode<E> dnuevo = (DNode<E>) nuevo; 
		DNode<E> nBefore = ((DNode<E>) target).getPrev(); 
		
		nBefore.setNext(dnuevo); 
		dnuevo.setPrev(nBefore); 
		dnuevo.setNext((DNode<E>)target);
		((DNode<E>)target).setPrev(dnuevo);
		 
		this.size++;
	}


	private static class DNode<E>  implements Node<E>
	{
		private E element; 
		private DNode<E> prev, next; 

		// Constructors
		public DNode() {this(null);}
		
		public DNode(E e) { 
			this( e,null,null); 
			
		}
		
		public DNode(E e, DNode<E> p, DNode<E> n) { 
			prev = p; 
			next = n;
			element = e;
		}
		
		
		public String toString()
		{
			return "{"+this.getElement()+ "} ";
			
		}
		// Methods
		public DNode<E> getPrev() {
			return prev;
		}
		public void setPrev(DNode<E> prev) {
			this.prev = prev;
		}
		public DNode<E> getNext() {
			return next;
		}
		public void setNext(DNode<E> next) {
			this.next = next;
		}
		public E getElement() {
			return element; 
		}

		public void setElement(E data) {
			element = data; 
		} 
		
		/**
		 * Just set references prev and next to null. Disconnect the node
		 * from the linked list.... 
		 */
		public void cleanLinks() { 
			prev = next = null; 
		}
		
	
	}

	public class CircularIterator<T> implements Iterator<Node<T>> {

		 private final Node<T> first;
		    private Node<T> mPosition;


		    public CircularIterator(Node<T> first) {
		        this.first = first;
		        this.mPosition = this.first;
		        
		    }

		   
		    public boolean hasNext() {
		        return !this.mPosition.getNext().equals(this.first);
		    }

		    
		    public Node<T> next() {
		        if (!hasNext())
		            return this.first;

		        mPosition = mPosition.getNext();

		        return  mPosition;
		    }
	
	}


	
}

	
