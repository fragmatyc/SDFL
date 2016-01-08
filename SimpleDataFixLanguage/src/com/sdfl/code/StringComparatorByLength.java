package com.sdfl.code;

import java.util.Comparator;

public class StringComparatorByLength implements Comparator<String> {

	@Override
	public int compare(String x, String y) {
		if (x == null)
	            return y==null ? -1 : 0;
	        else if (y == null)
	            return -1;
	        else {
	            int lenx = x.length();
	            int leny = y.length();
	            if (lenx == leny)
	                return 0;
	            else
	                return lenx > leny ? -1 : +1;
	        }
		
	}

}
