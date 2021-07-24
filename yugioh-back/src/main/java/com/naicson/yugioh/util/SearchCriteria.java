package com.naicson.yugioh.util;

import java.util.List;

public class SearchCriteria {

	private String key;
	//private String operation;
	private SearchOperation operation;
	private Object value;
	private boolean orPredicate;
	private List<String> values;
	
	public SearchCriteria() {
		
	}
	public SearchCriteria(final String key, final SearchOperation operation, final Object value) {
		super();
		this.key = key;
		this.operation = operation;
		this.value = value;
		
	}
	
	public SearchCriteria(final String key, final SearchOperation operation, final List<String> values) {
		super();
		this.key = key;
		this.operation = operation;
		this.values = values;
		
	}
	
	
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
	public SearchOperation getOperation() {
		return operation;
	}
	public void setOperation(SearchOperation operation) {
		this.operation = operation;
	}
	public Object getValue() {
		return value;
	}
	public void setValue(Object value) {
		this.value = value;
	}

	public void setOrPredicate(boolean orPredicate) {
		this.orPredicate = orPredicate;
	}
	
	 public boolean isOrPredicate() {
	        return orPredicate;
	    }
	public List<String> getValues() {
		return values;
	}
	public void setValues(List<String> values) {
		this.values = values;
	}
	
	
}
