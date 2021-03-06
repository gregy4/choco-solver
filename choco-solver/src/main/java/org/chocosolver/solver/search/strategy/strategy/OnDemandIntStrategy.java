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
package org.chocosolver.solver.search.strategy.strategy;

import org.chocosolver.solver.search.strategy.assignments.DecisionOperator;
import org.chocosolver.solver.search.strategy.decision.IntDecision;
import org.chocosolver.solver.variables.IntVar;
import org.chocosolver.util.PoolManager;

/**
 * A factory which eases unit decision creation (for debugging mainly).
 * Created by cprudhom on 27/11/2015.
 * Project: choco.
 */
public class OnDemandIntStrategy{

    /**
     * object recycling management
     */
    PoolManager<IntDecision> decisionPool;

    /**
     * Create an instance of this decision creator
     */
    public OnDemandIntStrategy() {
        this.decisionPool = new PoolManager<>();
    }

    /**
     * Creates and returns an {@link IntDecision}: "<code>var</code> <code>dop</code> <code>value</code>".
     * @param var a integer variable
     * @param dop a decision operator
     * @param value a value
     * @return an IntDecision
     */
    public IntDecision makeIntDecision(IntVar var, DecisionOperator dop, int value) {
        IntDecision d = decisionPool.getE();
        if (d == null) {
            d = new IntDecision(decisionPool);
        }
        d.set(var, value, dop);
        return d;
    }
}
