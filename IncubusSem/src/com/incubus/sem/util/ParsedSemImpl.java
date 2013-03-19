package com.incubus.sem.util;

import java.util.Arrays;

import com.incubus.sem.ParsedSem;

public class ParsedSemImpl implements ParsedSem {

	private final static int MAX_LENGTH = 4;

	private int nameCursor = 0;

	private int unregisteredCursor = -1;

	private int relationCursor = 0;

	private short[] ids;

	private String[] names;

	public ParsedSemImpl(String[] names) {
		super();
		this.names = names;
		ids = new short[MAX_LENGTH];
	}

	@Override
	public String nextName() {
		if (hasMoreName()) {
			nameCursor++;
		}
		return names[nameCursor];
	}

	@Override
	public String nextUnregisteredName() {
		return names[unregisteredCursor];
	}

	@Override
	public void setId(String name, short id) {
		int index = Arrays.binarySearch(names, name);
		if (index != -1) {
			ids[index] = id;
		}
	}

	@Override
	public boolean hasMoreName() {
		return nameCursor + 1 < MAX_LENGTH;
	}

	@Override
	public boolean hasMoreUnregisteredName() {
		unregisteredCursor = Arrays.binarySearch(ids, (short) 0);
		return unregisteredCursor != -1;
	}

	@Override
	public short[] nextRelation() {
		if (hasMoreRelation()) {
			relationCursor++;
		}
		return Arrays.copyOfRange(ids, relationCursor - 1, relationCursor);
	}

	@Override
	public boolean hasMoreRelation() {
		return relationCursor < names.length -2 ;
	}

	@Override
	public short[] getIds() {
		return Arrays.copyOf(ids, MAX_LENGTH);
	}

	@Override
	public long getId() {
		return shortsToLong(ids);
	}
	
	private long shortsToLong(short[] shorts) {
		byte[] bytes = new byte[8];


		for (int i = 0; i< bytes.length; i++) {
			short sh = shorts[i];

			bytes[i*2] = (byte)(sh & 0xFF);
			i++;
			bytes[(i*2)+1] = (byte)((sh >> 8) & 0xFF);
		}

		long ret = (bytes[7] << 56);
		ret = ret | (bytes[6] << 48);
		ret = ret | (bytes[5] << 40);
		ret = ret | (bytes[4] << 32);
		ret = ret | (bytes[3] << 24);
		ret = ret | (bytes[2] << 16);
		ret = ret | (bytes[1] << 8);
		ret = ret | (0xFF & bytes[0]);

		return ret;
	}
	
	


}
