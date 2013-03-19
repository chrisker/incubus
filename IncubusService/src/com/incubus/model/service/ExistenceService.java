package com.incubus.model.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.incubus.model.Element;
import com.incubus.model.Existence;
import com.incubus.model.function.UniquenessFunction;

public class ExistenceService {

	public ExistenceService() {
		
	}
	
	public Existence getExistence(UniquenessFunction unicityFunction){
		Existence existence = new InnerExistence(unicityFunction);
		return existence;
	}
	
	public Existence getExistence(){
		Existence existence = new InnerExistence();
		return existence;
	}
	
	
	
	private class InnerExistence implements Existence{

		private List<Element> elements = new ArrayList<Element>();
		
		private UniquenessFunction uniquenessFunction;
		
		private boolean isFunctionalKey = false;

		public InnerExistence(UniquenessFunction unicityFunction){
			this.uniquenessFunction = unicityFunction;
			isFunctionalKey = true;
		}
		
		public InnerExistence(){
		}
		
		
		@Override
		public long[] getId() {

			long[] id;
			if (uniquenessFunction != null){
				int length = elements.size();
				id = new long[length + 1];
				id[length] = uniquenessFunction.getValue();
			}else{
				id = new long[elements.size()];
			}
			
			int i = 0;
			for (Iterator<Element> iterator = elements.iterator(); iterator.hasNext();i++) {
				Element element = iterator.next();
				id[i] = element.getId();
			}
			return id;
		}
		
		@Override
		public void addElement(Element element) {
			elements.add(element);
		}

		@Override
		public boolean isFunctionKey() {
			return isFunctionalKey;
		}

	}
}
