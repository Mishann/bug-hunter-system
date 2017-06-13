package com.app.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TaskHelper {

	public static List<String> parseAndShuffle(String str, String delimeter){	
		String[] array = (str.split(delimeter));
		List<String> list= new ArrayList<String>(Arrays.asList(array));
 		
	
		Collections.shuffle(list);
 		
 		return list;
		
	}
}
