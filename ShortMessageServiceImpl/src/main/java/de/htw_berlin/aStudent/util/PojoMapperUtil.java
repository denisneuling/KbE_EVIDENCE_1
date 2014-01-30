package de.htw_berlin.aStudent.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.dozer.Mapper;

public class PojoMapperUtil {

	public static <T, U> List<U> map(final Mapper mapper, final List<T> source, final Class<U> destType) {
	    final List<U> dest = new ArrayList<U>();
	    for (T element : source) {
	        dest.add(mapper.map(element, destType));
	    }
	    return dest;
	}
	
	public static <T, U> List<List<U>> mapMatrix(final Mapper mapper, final List<List<T>> source, final Class<U> destType) {
	    List<List<U>> dest = new ArrayList<List<U>>();
	    if(source!=null){
		    for (List<T> els : source) {
		    	List<U> sub = new ArrayList<U>(); 
		    	for(T t : els){
		    		sub.add(mapper.map(t, destType));
		    	}
		    	dest.add(sub);
		    }
	    }else{
	    	List<U> sub = new ArrayList<U>();
	    	dest.add(sub);
	    }
	    return dest;
	}
	
	public static <T, U> Set<U> mapSet(final Mapper mapper, final List<T> source, final Class<U> destType) {
	    final Set<U> dest = new HashSet<U>();
	    for (T element : source) {
	        dest.add(mapper.map(element, destType));
	    }
	    return dest;
	}
}
