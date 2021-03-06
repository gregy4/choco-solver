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
package org.chocosolver.solver.constraints.nary;

import org.chocosolver.solver.ResolutionPolicy;
import org.chocosolver.solver.Solver;
import org.chocosolver.solver.constraints.IntConstraintFactory;
import org.chocosolver.solver.search.strategy.IntStrategyFactory;
import org.chocosolver.solver.variables.IntVar;
import org.chocosolver.solver.variables.VariableFactory;
import org.chocosolver.util.tools.ArrayUtils;
import org.testng.annotations.Test;

/**
 * <br/>
 *
 * @author Charles Prud'homme
 * @since 24/01/11
 */
public class BottleneckTest {


    @Test(groups = "10s")
    public void testStynes1() {
        for (int n = 100; n < 201; n += 50) {
            Solver solver = new Solver();

            IntVar[] nexts = new IntVar[n];
            IntVar[] exps = new IntVar[n];
            IntVar[] bws = new IntVar[n];
            for (int i = 0; i < n; i++) {
                nexts[i] = VariableFactory.enumerated("n_" + i, 0, 200, solver);
                exps[i] = VariableFactory.enumerated("e_" + i, 0, 200, solver);
                bws[i] = VariableFactory.enumerated("b_" + i, 0, 2000, solver);
                solver.post(IntConstraintFactory.scalar(new IntVar[]{bws[i], exps[i]}, new int[]{1, 1}, nexts[i]));
            }

            IntVar sum = VariableFactory.bounded("sum", 0, 2000 * n, solver);
			solver.post(IntConstraintFactory.sum(bws, sum));

            IntVar[] allvars = ArrayUtils.append(nexts, exps, bws, new IntVar[]{sum});


            solver.set(IntStrategyFactory.minDom_LB(allvars));
            solver.findOptimalSolution(ResolutionPolicy.MAXIMIZE, sum);
        }
    }

    @Test(groups = "10s")
    public void testStynes2() {
        int n = 10000;
        {
            Solver solver = new Solver();

            IntVar[] nexts = new IntVar[n];
            IntVar[] exps = new IntVar[n];
            IntVar[] bws = new IntVar[n];
            for (int i = 0; i < n; i++) {
                nexts[i] = VariableFactory.enumerated("n_" + i, 0, 200, solver);
                exps[i] = VariableFactory.enumerated("e_" + i, 0, 200, solver);
                bws[i] = VariableFactory.enumerated("b_" + i, 0, 2000, solver);
				solver.post(IntConstraintFactory.scalar(new IntVar[]{bws[i], exps[i]}, new int[]{1, 1}, nexts[i]));
            }

            IntVar sum = VariableFactory.bounded("sum", 0, 2000 * n, solver);
			solver.post(IntConstraintFactory.sum(bws, sum));

            IntVar[] allvars = ArrayUtils.append(nexts, exps, bws, new IntVar[]{sum});

            // Heuristic val
            solver.set(IntStrategyFactory.minDom_LB(allvars));

            solver.findSolution();
        }


    }


}
