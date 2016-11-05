package org.owasp.appsensor.analysis;

import java.util.ArrayList;
import java.util.Collection;

import org.owasp.appsensor.core.DetectionPoint;
import org.owasp.appsensor.core.Interval;

/**
 * An Expression is a logical boolean expression where the variables are RulesDetectionPoints.
 * Each Expression in a Rule is separated by the "THEN" operator.
 *
 * An Expression contains a set of Clauses. Only one Clause needs to evaluate to true
 * for an Expression to evaluate to true.
 *
 * For example:
 * 		In the Rule: "DP1 AND DP2 THEN DP3 OR DP4"
 *
 * 		"DP1 AND DP2" would be the first Expression with a single Clause
 * 		and "DP3 OR DP4" would a second Expression with two Clauses.
 *
 * @author David Scrobonia (davidscrobonia@gmail.com)
 */
public class Expression {

	/** The window of time a Clause must be triggered within */
	private Interval window;

	/** The Clauses that build up the Expression. **/
	private Collection<Clause> clauses;

	public Expression () { }

	public Expression (Interval window, ArrayList<Clause> clauses) {
		setWindow(window);
		setClauses(clauses);
	}

	public Interval getWindow(){
		return this.window;
	}

	public Expression setWindow(Interval window){
		this.window = window;
		return this;
	}

	public Collection<Clause> getClauses(){
		return this.clauses;
	}

	public Expression setClauses(ArrayList<Clause> clauses){
		this.clauses = clauses;
		return this;
	}

	public Collection<DetectionPoint> getDetectionPoints() {
		ArrayList<DetectionPoint> detectionPoints = new ArrayList<DetectionPoint>();

		for (Clause clause : clauses) {
			for (DetectionPoint detectionPoint : clause.getDetectionPoints()) {
				detectionPoints.add(detectionPoint);
			}
		}

		return detectionPoints;
	}
}
