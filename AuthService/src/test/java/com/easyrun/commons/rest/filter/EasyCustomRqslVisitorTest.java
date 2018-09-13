package com.easyrun.commons.rest.filter;

import org.junit.Test;

import com.easyrun.auth.model.QRole;
import com.querydsl.core.BooleanBuilder;

import cz.jirutka.rsql.parser.RSQLParser;
import cz.jirutka.rsql.parser.ast.Node;
import static org.junit.Assert.*;

public class EasyCustomRqslVisitorTest {
	@Test
	public void testForRole() {
		Node rootNode = new RSQLParser().parse("roleCd==john;id!=1;createdDate>=2018-01-01T00:00:00");
		EasyCustomRqslVisitor<QRole> visitor = new EasyCustomRqslVisitor<QRole>(QRole.role);
		BooleanBuilder p = (BooleanBuilder) rootNode.accept(visitor);
		assertEquals("role.roleCd like john && role.id != 1 && (role.createdDate > Mon Jan 01 00:00:00 CST 2018 || role.createdDate = Mon Jan 01 00:00:00 CST 2018)", p.toString());
	}
	
}
