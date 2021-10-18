package com.vten.gedeon.api.utils;

import org.apache.commons.lang3.StringUtils;

import lombok.Getter;

public enum Setability {
	READWRITE("read/write",1),
	ONCREATE("oncreate",2),
	ONCHECKIN("oncheckin",3);
	
	protected String name;
	@Getter
	protected int value;
	
	Setability(String n,int v){
		name=n;
		value=v;
	}
	
	public static Setability fromString(String str) {
		for(Setability set : Setability.values())
			if(StringUtils.equalsIgnoreCase(str, set.name))
				return set;
		return READWRITE;
	}
	
	public static Setability fromInt(int val) {
		for(Setability set : Setability.values())
			if(val==set.value)
				return set;
		return READWRITE;
	}
}
