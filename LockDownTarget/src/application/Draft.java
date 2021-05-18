package application;

import java.util.ArrayList;
import java.util.HashMap;

public class Draft {
	
	public static void main(String[] args) {
		ArrayList<HashMap<Integer,Integer>> listAll = new ArrayList<HashMap<Integer,Integer>>();
		
		HashMap<Integer,Integer> list1=new HashMap<>();
		HashMap<Integer,Integer> list2=new HashMap<>();
		HashMap<Integer,Integer> list3=new HashMap<>();
		HashMap<Integer,Integer> list4=new HashMap<>();
		list1.put(1,2);
		list1.put(1,4);
		list1.put(1,6);
		list2.put(15,16);
		list3.put(20,21);
		list3.put(20,23);
		
		listAll.add(list1);
		listAll.add(list2);
		listAll.add(list3);
		list4=listAll.get(2);
		int x=list1.get(1);
		
		System.out.println(listAll.size());
		
	}	

}
