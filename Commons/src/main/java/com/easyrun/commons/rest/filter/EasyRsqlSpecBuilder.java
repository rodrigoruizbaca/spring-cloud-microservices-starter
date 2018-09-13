package com.easyrun.commons.rest.filter;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.DateTimeExpression;
import com.querydsl.core.types.dsl.DateTimePath;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.SimpleExpression;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;

import cz.jirutka.rsql.parser.ast.ComparisonNode;
import cz.jirutka.rsql.parser.ast.ComparisonOperator;
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
	
	public Predicate createPredicate(Node node) {
		if (node instanceof LogicalNode) {
			return createPredicate((LogicalNode) node);
		}
		if (node instanceof ComparisonNode) {
			return createPredicate((ComparisonNode) node);
		}
		return null;
	}

	public Predicate createPredicate(LogicalNode logicalNode) {
		List<Predicate> expressions = Lists.newArrayList();
		Predicate returnPredicate = null;
		for (Node node : logicalNode.getChildren()) {
			Predicate temp = createPredicate(node);
			if (temp != null) {
				expressions.add(temp);				
			}
		}
		BooleanBuilder builder = new BooleanBuilder();
		
		if (!expressions.isEmpty()) {			
			for (int x = 0; x < expressions.size(); x++) {
				returnPredicate = builder.and(expressions.get(x));
			}
			return returnPredicate;
		}
		return null;
	}
	
	private Object getFromField(Field f) {
		try {
			return f.get(q);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			return null;
		}
	}
	
	private <R extends Number & Comparable<R>> Predicate getExpressionForNumber(NumberExpression<R> exp, R val, ComparisonOperator oper) {
		switch (RsqlSearchOperation.getSimpleOperator(oper)) {
			case EQUAL: {			
				return exp.eq(val);
			}
			case NOT_EQUAL: {
				return exp.ne(val);
			}
			case GREATER_THAN: {
				return exp.gt(val);
			}
			case GREATER_THAN_OR_EQUAL: {
				return exp.gt(val).or(exp.eq(val));		
			}
			case LESS_THAN: {
				return exp.lt(val);			
			}
			case LESS_THAN_OR_EQUAL: {
				return exp.lt(val).or(exp.eq(val));						
			}			
			case IN: {
				throw new IllegalArgumentException("IN operator not yet supported");
			}
			case NOT_IN: {
				throw new IllegalArgumentException("NOT IN operator not yet supported");
			}
			default: return null;
		}
	}
	
	private <R extends Comparable<R>> Predicate getExpression(SimpleExpression<R> exp, R val, ComparisonOperator oper) {
		switch (RsqlSearchOperation.getSimpleOperator(oper)) {
			case EQUAL: {
				if (exp instanceof StringExpression) {
					return ((StringExpression) exp).containsIgnoreCase((String)val);
				}
				return exp.eq(val);
			}
			case NOT_EQUAL: {
				return exp.ne(val);
			}
			case GREATER_THAN: {
				if (exp instanceof DateTimeExpression) {
					return ((DateTimeExpression<R>) exp).gt(val);
				} else {
					throw new IllegalArgumentException("Greater than needs a date");
				}
			}
			case GREATER_THAN_OR_EQUAL: {
				if (exp instanceof DateTimeExpression) {
					return ((DateTimeExpression<R>) exp).gt(val).or(((DateTimeExpression<R>) exp).eq(val));
				} else {
					throw new IllegalArgumentException("Greater than needs a date");
				}			
			}
			case LESS_THAN: {
				if (exp instanceof DateTimeExpression) {
					return ((DateTimeExpression<R>) exp).lt(val);
				} else {
					throw new IllegalArgumentException("Greater than needs a date");
				}					
			}
			case LESS_THAN_OR_EQUAL: {
				if (exp instanceof DateTimeExpression) {
					return ((DateTimeExpression<R>) exp).lt(val).or(((DateTimeExpression<R>) exp).eq(val));
				} else {
					throw new IllegalArgumentException("Greater than needs a date");
				}					
			}			
			case IN: {
				throw new IllegalArgumentException("IN operator not yet supported");
			}
			case NOT_IN: {
				throw new IllegalArgumentException("NOT IN operator not yet supported");
			}
			default: return null;
		}
		
	}
	
	
	@SuppressWarnings("unchecked")
	private Predicate getPredicate(String fieldName, String val, ComparisonOperator oper) {
		Field field = names.get(fieldName);
		if (field != null) {			
			if (field.getType().getName().equals(StringPath.class.getName())) {				
				StringExpression path = (StringExpression) getFromField(field);				
				return getExpression(path, val, oper);					
			} else if (field.getType().getName().equals(DateTimePath.class.getName())) {				
				DateTimeExpression<Date> path = (DateTimeExpression<Date>) getFromField(field);
				LocalDateTime date = LocalDateTime.parse(val, DateTimeFormatter.ISO_LOCAL_DATE_TIME);		
				Instant instant = date.atZone(ZoneId.systemDefault()).toInstant();
				Date dateFromOld = Date.from(instant);
				return getExpression(path, dateFromOld, oper);		
			} else if (field.getType().getName().equals(NumberPath.class.getName())) {
				if (field.getGenericType().getTypeName().equals(Integer.class.getName())) {
					NumberPath<Integer> path = (NumberPath<Integer>) getFromField(field);
					Integer num = Integer.parseInt(val);
					return getExpressionForNumber(path, num, oper);
				} else if (field.getGenericType().getTypeName().equals(Double.class.getName())) {
					NumberPath<Double> path = (NumberPath<Double>) getFromField(field);
					Double num = Double.parseDouble(val);
					return getExpressionForNumber(path, num, oper);
				}  else if (field.getGenericType().getTypeName().equals(Float.class.getName())) {
					NumberPath<Float> path = (NumberPath<Float>) getFromField(field);
					Float num = Float.parseFloat(val);
					return getExpressionForNumber(path, num, oper);
				}  else if (field.getGenericType().getTypeName().equals(Long.class.getName())) {
					NumberPath<Long> path = (NumberPath<Long>) getFromField(field);
					Long num = Long.parseLong(val);
					return getExpressionForNumber(path, num, oper);
				}  else if (field.getGenericType().getTypeName().equals(BigDecimal.class.getName())) {
					NumberPath<BigDecimal> path = (NumberPath<BigDecimal>) getFromField(field);
					BigDecimal num = new BigDecimal(val);
					return getExpressionForNumber(path, num, oper);
				}  else {
					throw new IllegalArgumentException("Generic not yet supported " + field.getGenericType().getTypeName());
				}
			} else {
				throw new IllegalArgumentException("Type not yet supported " + field.getType().getName());
			}			
		} else {
			return null;
		}		
	}


	public Predicate createPredicate(ComparisonNode comparisonNode) {
		List<String> args = comparisonNode.getArguments();		
		String field = comparisonNode.getSelector();		
		return getPredicate(field, args.get(0), comparisonNode.getOperator());		
	}
}