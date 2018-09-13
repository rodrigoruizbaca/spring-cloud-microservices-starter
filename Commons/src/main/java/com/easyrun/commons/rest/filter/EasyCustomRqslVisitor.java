package com.easyrun.commons.rest.filter;





import com.querydsl.core.types.Predicate;

import cz.jirutka.rsql.parser.ast.AndNode;
import cz.jirutka.rsql.parser.ast.ComparisonNode;
import cz.jirutka.rsql.parser.ast.OrNode;
import cz.jirutka.rsql.parser.ast.RSQLVisitor;

public class EasyCustomRqslVisitor<T> implements RSQLVisitor<Predicate, Void>{

	private EasyRsqlSpecBuilder<T> builder;
	
	public EasyCustomRqslVisitor(T root) {		
		builder = new EasyRsqlSpecBuilder<T>(root);
	}
	
	@Override
	public Predicate visit(AndNode node, Void arg1) {
		return builder.createPredicate(node);
	}

	@Override
	public Predicate visit(OrNode node, Void arg1) {
		return builder.createPredicate(node);
	}

	@Override
	public Predicate visit(ComparisonNode node, Void arg1) {
		return builder.createPredicate(node);
	}

}
