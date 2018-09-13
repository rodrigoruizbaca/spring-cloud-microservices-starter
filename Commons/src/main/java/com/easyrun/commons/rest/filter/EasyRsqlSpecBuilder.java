package com.easyrun.commons.rest.filter;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;

import cz.jirutka.rsql.parser.ast.ComparisonNode;
import cz.jirutka.rsql.parser.ast.LogicalNode;
import cz.jirutka.rsql.parser.ast.Node;

public class EasyRsqlSpecBuilder<T> {

	private T q;
	Map<String, Field> names;
	
	public EasyRsqlSpecBuilder(T q) {
		this.q = q;
		names = Maps.newHashMap();
		Arrays.stream(q.getClass().getDeclaredFields()).forEach(f -> {
			names.put(f.getName(), f);
		});	
	}
	
	public BooleanExpression createPredicate(Node node) {
		if (node instanceof LogicalNode) {
			return createPredicate((LogicalNode) node);
		}
		if (node instanceof ComparisonNode) {
			return createPredicate((ComparisonNode) node);
		}
		return null;
	}

	public BooleanExpression createPredicate(LogicalNode logicalNode) {
		List<BooleanExpression> expressions = Lists.newArrayList();
		BooleanExpression temp;
		for (Node node : logicalNode.getChildren()) {
			temp = createPredicate(node);
			if (temp != null) {
				expressions.add(temp);				
			}
		}
		if (!expressions.isEmpty()) {
			temp = expressions.get(0);
			for (int x = 1; x < expressions.size(); x++) {
				temp.and(expressions.get(x));
			}
			return temp;
		}
		return null;
	}
	
	public BooleanExpression getEquals(String fieldName, String val) {
		Field field = names.get(fieldName);
		if (field != null) {			
			if (field.getType().getName().equals(StringPath.class.getName())) {
				try {
					StringExpression path = (StringExpression) field.get(q);
					return path.eq(val);
				} catch (IllegalArgumentException | IllegalAccessException e) {
					return null;
				}
			}
		}
		return null;
	}


	public BooleanExpression createPredicate(ComparisonNode comparisonNode) {
		List<String> args = comparisonNode.getArguments();		
		String field = comparisonNode.getSelector();				
		switch (RsqlSearchOperation.getSimpleOperator(comparisonNode.getOperator())) {					
			case EQUAL: {				
				return getEquals(field, args.get(0));
			}
			case NOT_EQUAL: {
				break;
			}
			case GREATER_THAN: {
				
			}
			case GREATER_THAN_OR_EQUAL: {
				
			}
			case LESS_THAN: {
				
			}
			case LESS_THAN_OR_EQUAL: {
				
			}
			case IN: {
				
			}
			case NOT_IN: {
				
			}
		}
		return null;
	}
}