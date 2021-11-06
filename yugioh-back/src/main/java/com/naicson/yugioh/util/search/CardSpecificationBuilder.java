package com.naicson.yugioh.util.search;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.jpa.domain.Specification;

import com.naicson.yugioh.entity.Card;

public class CardSpecificationBuilder {
	/*
	 * private final List<SearchCriteria> params;
	 * 
	 * public CardSpecificationBuilder() { params = new ArrayList<SearchCriteria>();
	 * }
	 * 
	 * public CardSpecificationBuilder with (String key, String operation, Object
	 * value) { params.add(new SearchCriteria(key, operation, value)); return this;
	 * }
	 * 
	 * public CardSpecificationBuilder with (String key, String operation,
	 * List<String> values) { params.add(new SearchCriteria(key, operation,
	 * values)); return this; }
	 * 
	 * public Specification<Card> build(){ if(params.size() == 0) { return null; }
	 * 
	 * List<Specification> specs = params.stream() .map(CardSpecification::new)
	 * .collect(Collectors.toList());
	 * 
	 * Specification<Card> result = specs.get(0);
	 * 
	 * for (int i = 1; i < params.size(); i++) { result = params.get(i)
	 * .isOrPredicate() ? Specification.where(result) .or(specs.get(i)) :
	 * Specification.where(result) .and(specs.get(i)); } return result; }
	 */
		
	}

