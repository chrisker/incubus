package com.incubus.model.service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.incubus.model.Node;

public class DeclarationService {

	private Map<String, Node> existences = new HashMap<String, Node>();
	
	private Set<String> declarations = new HashSet<>();
	
	
	public DeclarationService() {
	}
	
	public Node getDeclaration(String name){
	
		declarations.add(name);
		return new InnerNode(name);
	}
	
	private class InnerNode implements Node{

		private String name;
		
		private InnerNode(String name) {
			super();
			this.name = name;
		}

		@Override
		public Node getChildNode(String name) {
			existences.put(getName(), this);
			declarations.remove(getName());
			return getDeclaration(name);
		}

		@Override
		public String getName() {
			return name;
		}
		
	}
	
	

}
