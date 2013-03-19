package com.incubus.sem;

public interface ParsedSem {
	
	public String nextName();

	public String nextUnregisteredName();

	public void setId(String name, short id);
	
	public boolean hasMoreName();

	public boolean hasMoreUnregisteredName();
	
	public short[] nextRelation();

	public boolean hasMoreRelation();
	
	public short[] getIds();
	
	public long getId();


}
