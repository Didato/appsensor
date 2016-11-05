package org.owasp.appsensor.analysis;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.LinkedList;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.owasp.appsensor.core.DetectionPoint;
import org.owasp.appsensor.core.Interval;

public class CheckExpressionTest {
	static AggregateEventAnalysisEngine engine;
	static Clause clause1, clause2;
	static RulesDetectionPoint point1, point2;

	@BeforeClass
	public static void setUp() {
		engine = new AggregateEventAnalysisEngine();
		point1 = new RulesDetectionPoint(new DetectionPoint(DetectionPoint.Category.INPUT_VALIDATION, "IE1"), "1");
		point2 = new RulesDetectionPoint(new DetectionPoint(DetectionPoint.Category.INPUT_VALIDATION, "IE2"), "2");
		clause1 = buildClause(point1);
		clause2 = buildClause(point2);
	}

	@Test
	public void testOneValidClause() {
		ArrayList<Clause> clauses = new ArrayList<Clause>();
		clauses.add(clause1);
		Expression expression = new Expression(new Interval(), clauses);

		LinkedList<TriggeredSensor> sensors = new LinkedList<TriggeredSensor>();
		sensors.add(new TriggeredSensor(2, Interval.MINUTES, new DateTime(10), point1));

		assertTrue(engine.checkExpression(expression, sensors));
	}

	@Test
	public void testTwoValidClause() {
		ArrayList<Clause> clauses = new ArrayList<Clause>();
		clauses.add(clause1);
		clauses.add(clause2);
		Expression expression = new Expression(new Interval(), clauses);

		LinkedList<TriggeredSensor> sensors = new LinkedList<TriggeredSensor>();
		sensors.add(new TriggeredSensor(2, Interval.MINUTES, new DateTime(10), point1));
		sensors.add(new TriggeredSensor(2, Interval.MINUTES, new DateTime(10), point2));

		assertTrue(engine.checkExpression(expression, sensors));
	}

	@Test
	public void testOneValidOneInvalidClause() {
		ArrayList<Clause> clauses = new ArrayList<Clause>();
		clauses.add(clause1);
		clauses.add(clause2);
		Expression expression = new Expression(new Interval(), clauses);

		LinkedList<TriggeredSensor> sensors = new LinkedList<TriggeredSensor>();
		sensors.add(new TriggeredSensor(2, Interval.MINUTES, new DateTime(10), point2));

		assertTrue(engine.checkExpression(expression, sensors));
	}

	@Test
	public void testOneInvalidClause() {
		ArrayList<Clause> clauses = new ArrayList<Clause>();
		clauses.add(clause1);
		Expression expression = new Expression(new Interval(), clauses);

		LinkedList<TriggeredSensor> sensors = new LinkedList<TriggeredSensor>();

		assertFalse(engine.checkExpression(expression, sensors));
	}

	private static Clause buildClause(RulesDetectionPoint point) {
		ArrayList<RulesDetectionPoint> points = new ArrayList<RulesDetectionPoint>();
		points.add(point);

		return new Clause (new Interval(5, Interval.MINUTES), points);
	}
}
