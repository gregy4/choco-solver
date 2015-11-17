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
package org.chocosolver.samples;

import org.chocosolver.samples.integer.GolombRuler;
import org.chocosolver.solver.ResolutionPolicy;
import org.chocosolver.solver.Solver;
import org.chocosolver.solver.propagation.PropagationEngineFactory;
import org.chocosolver.solver.variables.IntVar;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * <br/>
 *
 * @author Charles Prud'homme
 * @since 15/03/11
 */
public class GolombRulerTest {

    public final static int[][] OPTIMAL_RULER = {
            {5, 11}, {6, 17}, {7, 25}, {8, 34}, {9, 44}, {10, 55}, {11, 72}
    };

    protected Solver modeler(int m) {
        GolombRuler pb = new GolombRuler();
        pb.readArgs("-m", Integer.toString(m));
        pb.createSolver();
        pb.buildModel();
        pb.configureSearch();
        return pb.getSolver();
    }

    @Test(groups = "verylong")
    public void testSmall() {
        Solver sol;
        for (int j = 0; j < 4; j++) {
            sol = modeler(OPTIMAL_RULER[j][0]);
            sol.findOptimalSolution(ResolutionPolicy.MINIMIZE, (IntVar) sol.getVars()[OPTIMAL_RULER[j][0] - 1]);
            long sols = sol.getMeasures().getSolutionCount();
            long nodes = sol.getMeasures().getNodeCount();
            for (int k = 1; k < PropagationEngineFactory.values().length; k++) {
                sol = modeler(OPTIMAL_RULER[j][0]);
                PropagationEngineFactory.values()[k].make(sol);
                sol.findOptimalSolution(ResolutionPolicy.MINIMIZE, (IntVar) sol.getVars()[OPTIMAL_RULER[j][0] - 1]);
                Assert.assertEquals(sol.getMeasures().getSolutionCount(), sols);
                Assert.assertEquals(sol.getMeasures().getNodeCount(), nodes);

            }
        }
    }

    @Test(groups = "verylong")
    public void testAll() {
        Solver sol;
        for (int j = 0; j < OPTIMAL_RULER.length; j++) {
            sol = modeler(OPTIMAL_RULER[j][0]);
            sol.findOptimalSolution(ResolutionPolicy.MINIMIZE, (IntVar) sol.getVars()[OPTIMAL_RULER[j][0] - 1]);
            long sols = sol.getMeasures().getSolutionCount();
            long nodes = sol.getMeasures().getNodeCount();
            for (int k = 1; k < PropagationEngineFactory.values().length; k++) {
                sol = modeler(OPTIMAL_RULER[j][0]);
                PropagationEngineFactory.values()[k].make(sol);
                sol.findOptimalSolution(ResolutionPolicy.MINIMIZE, (IntVar) sol.getVars()[OPTIMAL_RULER[j][0] - 1]);
                Assert.assertEquals(sol.getMeasures().getSolutionCount(), sols);
                Assert.assertEquals(sol.getMeasures().getNodeCount(), nodes);

            }
        }
    }

}
