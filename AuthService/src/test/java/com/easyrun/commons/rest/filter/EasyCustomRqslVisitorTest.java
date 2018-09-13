package com.easyrun.commons.rest.filter;

import org.junit.Test;

import com.easyrun.auth.model.QRole;
import com.querydsl.core.types.Predicate;

import cz.jirutka.rsql.parser.RSQLParser;
import cz.jirutka.rsql.parser.ast.Node;

public class EasyCustomRqslVisitorTest {
	@Test
	public void testForRole() {
		Node rootNode = new RSQLParser().parse("roleCd==john;roleCd==doe");
		EasyCustomRqslVisitor<QRole> visitor = new EasyCustomRqslVisitor<QRole>(QRole.role);
		Predicate p = rootNode.accept(visitor);
		System.out.println(p);
	}
	
}
