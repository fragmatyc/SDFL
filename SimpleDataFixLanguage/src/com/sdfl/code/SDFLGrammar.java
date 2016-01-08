package com.sdfl.code;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;


public abstract class SDFLGrammar {

	protected static List<String> valuesOf(Class<? extends SDFLGrammar> pGrammarClass) {
		List<String> lValues = new LinkedList<>();
		for (Field lCurField : pGrammarClass.getDeclaredFields()) {
			try {
				lValues.add((String) lCurField.get(String.class));
			} catch (IllegalArgumentException | IllegalAccessException e) {
				
			}
		}
		
		Collections.sort(lValues, new StringComparatorByLength());
		return lValues;
	}
	
	protected static boolean isValidGrammar(List<String> lValues, String lCurrentToken) {
		boolean lFound = false;
		
		for (String lCurSyntax : lValues) {
			if (lCurSyntax.equals(lCurrentToken)) {
				lFound = true;
				break;
			}
		}
		
		return lFound;
	}
}
