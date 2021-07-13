package com.publicstructures.methods;

import java.util.LinkedList;

public class MyQueue
{

	private LinkedList<Object> list;
	
	public MyQueue()
	{
		list = new LinkedList<Object>();
	}
	
	public void put(Object o)
	{
		list.add(o);
	}
	
	public Object get()
	{
		return list.remove();
	}
	
	public boolean isEmpty()
	{
		return list.isEmpty();
	}
	
	public boolean contains(Object o)
	{
		return list.contains(o);
	}
	
	public static void main(String[] args)
	{
		MyQueue myQueue = new MyQueue();
		
		myQueue.put("1");
		myQueue.put("2");
		myQueue.put("3");
		
		System.out.println(myQueue.get());
		System.out.println(myQueue.get());
		
		System.out.println(myQueue.contains("3"));
		
		System.out.println(myQueue.isEmpty());
		
	}
}
