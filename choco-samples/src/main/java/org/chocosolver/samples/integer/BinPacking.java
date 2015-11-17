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

import org.chocosolver.samples.AbstractProblem;
import org.chocosolver.solver.ResolutionPolicy;
import org.chocosolver.solver.Solver;
import org.chocosolver.solver.constraints.ICF;
import org.chocosolver.solver.search.loop.monitors.IMonitorSolution;
import org.chocosolver.solver.search.solution.AllSolutionsRecorder;
import org.chocosolver.solver.search.strategy.ISF;
import org.chocosolver.solver.variables.IntVar;
import org.chocosolver.solver.variables.VF;

/**
 * Bin packing example
 * put items into bins so that bin load is balanced
 *
 * Illustrates the enumeration of optimal solutions
 *
 * @author Jean-Guillaume Fages
 */
public class BinPacking extends AbstractProblem{

	//***********************************************************************************
	// VARIABLES
	//***********************************************************************************

	int nbItems;
	IntVar[] bins;
	int[] weights;
	int nbBins;
	IntVar[] loads;
	IntVar minLoad;

	//***********************************************************************************
	// METHODS
	//***********************************************************************************

	@Override
	public void createSolver() {
		solver = new Solver("bin packing sample");
	}

	@Override
	public void buildModel() {
		// input
		nbItems = d1_w.length;
		weights = d1_w;
		nbBins  = d1_nb;
		// variables
		bins = VF.enumeratedArray("bin",nbItems,0,nbBins-1,solver);
		loads= VF.boundedArray("load",nbBins,0,1000,solver);
		minLoad = VF.bounded("minLoad",0,1000,solver);
		solver.post(ICF.bin_packing(bins,weights,loads,0));
		solver.post(ICF.minimum(minLoad,loads));
	}

	@Override
	public void configureSearch() {
		solver.set(ISF.random_value(bins, 0));
		solver.plugMonitor((IMonitorSolution) new IMonitorSolution() {
			@Override
			public void onSolution() {
				String s = minLoad + " : ";
				for (IntVar l : loads) {
					s += " " + l.getValue();
				}
				System.out.println(s);
			}
		});
	}

	@SuppressWarnings("ConstantConditions")
	@Override
	public void solve() {
		int mode = 2;
		switch (mode){
			case 0:// to check
				solver.post(ICF.arithm(minLoad,"=",17));
				solver.set(new AllSolutionsRecorder(solver));
				solver.findAllSolutions();
				break;
			case 1:// one step approach (could be slow)
				solver.findAllOptimalSolutions(ResolutionPolicy.MAXIMIZE, minLoad, false);
				break;
			case 2:// two step approach (find and prove optimum, then enumerate)
				solver.findAllOptimalSolutions(ResolutionPolicy.MAXIMIZE, minLoad, true);
				break;
			default:
				throw new UnsupportedOperationException();
		}
	}

	@Override
	public void prettyOut() {
		System.out.println("There are "+solver.getSolutionRecorder().getSolutions().size()+" optimal solutions");
	}

	//***********************************************************************************
	// DATA
	//***********************************************************************************

	public static void main(String[] args){
	    new BinPacking().execute(args);
	}
	private final static int[] d1_w = new int[]{
			2,5,3,4,12,9,1,0,5,6,2,4
	};
	private final static int d1_nb = 3;
}
