package demo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.filter.Filter;

public class JavaStreams {


public void regular() 
{
		List<String> names = new  ArrayList<String>();
		names.add("ALpha");
		names.add("Abhijeet");
		names.add("Alex");
		names.add("Adam");
		names.add("Omega");
		
		
		int count = 0;
		for ( int i =0 ;i <names.size();i++)
		{
			if ( names.get(i).startsWith("A"))
					{
				count ++;
					}
		}
		
		System.out.println(count);

	}
@Test
public void streamFilter() 

	{
		List<String> names = new  ArrayList<String>();
		names.add("ALpha");
		names.add("Abhijeet");
		names.add("Alex");
		names.add("Adam");
		names.add("Omega");
		
		Long c = names.stream().filter(s->s.startsWith("A")).count();
		 System.out.println(c);
		 
		 Long d = Stream.of("Alpha", "Omega", "Abhijeet","Adam ","Rama", "Alex").filter(s->
		 {
			 s.startsWith("A");
			 return false ; // IF we run this , we will get zero as O/P as terminal operations should return true 
		 }).count();
		 //Print all names of the array 
		names.stream().filter(s->s.startsWith("A")).forEach(s->System.out.println(s));
		//Print the names of list in UPPERCASE
		names.stream().filter(s->s.endsWith("a")).map(s->s.toUpperCase()).forEach(s->System.out.println(s));
		//print names with first letter as A in upper case and sorted
		String[] arg = {"Alpha", "Omega", "Abhijeet","Adam ","Rama", "Alex"};
		List<String> nms = Arrays.asList(arg);
		nms.stream().filter(s->s.startsWith("A")).sorted().forEach(s->s.toUpperCase());
		
		List<String> names1 = new  ArrayList<String>();
		names1.add("Sigma");
		names1.add("Alekhya");
		names1.add("Amna");
		names1.add("Aren");
		names1.add("TISSOT");
		
		Stream<String> newStream = Stream.concat(names.stream()	, names1.stream());
		//newStream.sorted().forEach(s->System.out.println(s));
		
		//to check if anything is present in the stream.
		boolean flag = newStream.anyMatch(s->s.equalsIgnoreCase("Amna"));
		Assert.assertTrue(flag);

	}

public void streamCollect()
{
	List<String> names1 = new  ArrayList<String>();
	names1.add("Sigma");
	names1.add("Alekhya");
	names1.add("Amna");
	names1.add("Aren");
	names1.add("TISSOT");
	
	List<String> ls = names1.stream().filter(s->s.startsWith("A")).map(s->s.toUpperCase()).collect(Collectors.toList());
	System.out.println(ls.get(0));
	
	//Print a unique sorted array 
	List<Integer> values = Arrays.asList(3,2,56,73,23,2,3,23);
	values.stream().distinct().sorted().forEach(s->System.out.println(s));
	
	// print the 4th element of the unique-sorted list
List<Integer> sortedValues =	values.stream().distinct().sorted().collect(Collectors.toList());
System.out.println(sortedValues.get(3));
	
	
	
}
}	
