/**
 * Copyright (c) 2015, Ecole des Mines de Nantes
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 * 3. All advertising materials mentioning features or use of this software
 *    must display the following acknowledgement:
 *    This product includes software developed by the <organization>.
 * 4. Neither the name of the <organization> nor the
 *    names of its contributors may be used to endorse or promote products
 *    derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY <COPYRIGHT HOLDER> ''AS IS'' AND ANY
 * EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL <COPYRIGHT HOLDER> BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package org.chocosolver.samples.integer;

import org.chocosolver.solver.Solver;
import org.chocosolver.solver.constraints.IntConstraintFactory;
import org.chocosolver.solver.search.strategy.IntStrategyFactory;
import org.chocosolver.solver.variables.IntVar;
import org.chocosolver.solver.variables.VariableFactory;
import org.chocosolver.util.ESat;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Hadrien Cambazard
 */
public class DominatingSetOfQueens {

	//***************************************************//
	//***************** Generic Example *****************//
	//***************************************************//

	/**
	 * Domination de graphes de reines. Peux t on "dominer" un echiquier
	 * de taille n*n par val reines (toutes les cases sont attaquees).
	 *
	 * @param n size fo the chessboard
	 * @param val number of queens
	 * @return la liste des positions des reines.
	 */
	public static List<Integer> dominationQueen(int n, int val) {
		System.out.println("Domination queen (Q" + n + ":" + val + ")");
		Solver pb = new Solver("Introductive Example");
		IntVar[] X = new IntVar[n * n];
		//une variable par case avec pour domaine la reine qui l attaque. (les reines sont ainsi designees par les valeurs, et les cases par les variables)
		for (int i = 0; i < X.length; i++) {
			X[i] = VariableFactory.enumerated("Q" + i, 1, n*n, pb);
		}
		IntVar N = VariableFactory.fixed(val,pb);
		pb.post(IntConstraintFactory.nvalues(X,N));
		//i appartient a la variable j ssi la case i est sur une ligne/colonne/diagonale de j
		for (int i = 1; i <= n; i++) {
			for (int j = 1; j <= n; j++) {
				//pour chaque case
				for (int k = 1; k <= n; k++) {
					for (int l = 1; l <= n; l++) {
						if (!(k == i || l == j || Math.abs(i - k) == Math.abs(j - l))) {
							pb.post(IntConstraintFactory.arithm(X[n * (i - 1) + j - 1], "!=", (k - 1) * n + l));
						}
					}
				}
			}
		}

		pb.set(IntStrategyFactory.lexico_LB(X));

		pb.findSolution();
		System.out.println("Back  : " + pb.getMeasures().getBackTrackCount());
		System.out.println("Time  : " + pb.getMeasures().getTimeCount()+" (sec)");

		List<Integer> values = new LinkedList<>();
		if (pb.isFeasible() == ESat.TRUE) {
			for (int i = 0; i < n * n; i++) {
				if (!values.contains(X[i].getValue()))
					values.add(X[i].getValue());
			}
			System.out.print("Solution: ");
			for (Integer value : values) {
				System.out.print("" + value + " ");
			}
			System.out.println();
		} else System.out.println("No Solution");
		return values;
	}

	//***************************************************//
	//***************** Main ****************************//
	//***************************************************//

	public static void main(String[] args) {
		int[] q = new int[]{6,7,8,8,9};
		int[] v = new int[]{3,4,5,4,5};
		for (int i = 0; i < v.length; i++) {
			System.out.println("-----------------");
			dominationQueen(q[i], v[i]);
		}
	}
}
