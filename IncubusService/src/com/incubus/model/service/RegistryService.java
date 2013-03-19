package com.incubus.model.service;

import com.incubus.model.Element;
import com.incubus.model.Node;
import com.incubus.sem.SemSerializationMethod;
import com.incubus.sem.serialisation.SemManagement;
import com.incubus.sem.util.ParsedSemImpl;

public class RegistryService {
	
	private SemSerializationMethod method;
	
	public RegistryService(SemSerializationMethod method) {
		this.method = method;
	}
	
	private long getIdentification(Node node) {
		SemManagement semManagement = SemManagement.getInstance();
		String[] names = method.parseName(node.getName());
		long id = semManagement.getIds(new ParsedSemImpl(names));
		return id;
	}

	
	
	
	public Element registerElement(Node node){
		return new InnerElement(node);
	}

	private class InnerElement implements Element{

		
		private Node node;
		
		
		public InnerElement(Node node){
			this.node = node;
		}
		
		@Override
		public long getId() {
			return getIdentification(node);
		}
		
	}
	
	
}
