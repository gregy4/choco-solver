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
package org.chocosolver.solver.constraints.binary;

import org.chocosolver.solver.constraints.Propagator;
import org.chocosolver.solver.constraints.PropagatorPriority;
import org.chocosolver.solver.exception.ContradictionException;
import org.chocosolver.solver.explanations.RuleStore;
import org.chocosolver.solver.variables.IntVar;
import org.chocosolver.solver.variables.events.IEventType;
import org.chocosolver.solver.variables.events.IntEventType;
import org.chocosolver.util.ESat;

/**
 * X + Y >= C
 * <br/>
 *
 * @author Charles Prud'homme
 * @since 13/06/12
 */
public final class PropGreaterOrEqualXY_C extends Propagator<IntVar> {

    final IntVar x;
    final IntVar y;
    final int cste;

    @SuppressWarnings({"unchecked"})
    public PropGreaterOrEqualXY_C(IntVar[] vars, int c) {
        super(vars, PropagatorPriority.BINARY, true);
        this.x = vars[0];
        this.y = vars[1];
        this.cste = c;
    }

    @Override
    public int getPropagationConditions(int vIdx) {
        return IntEventType.INSTANTIATE.getMask() + IntEventType.DECUPP.getMask();
    }


    @Override
    public void propagate(int evtmask) throws ContradictionException {
        x.updateLowerBound(this.cste - y.getUB(), this);
        y.updateLowerBound(this.cste - x.getUB(), this);
        if (x.getLB() + y.getLB() >= this.cste) {
            this.setPassive();
        }
    }

    @Override
    public void propagate(int idxVarInProp, int mask) throws ContradictionException {
        if (idxVarInProp == 0) {
            y.updateLowerBound(this.cste - x.getUB(), this);
        } else {
            x.updateLowerBound(this.cste - y.getUB(), this);
        }
        if (x.getLB() + y.getLB() >= this.cste) {
            this.setPassive();
        }
    }


    @Override
    public ESat isEntailed() {
        if (x.getUB() + y.getUB() < cste)
            return ESat.FALSE;
        else if (x.getLB() + y.getLB() >= this.cste)
            return ESat.TRUE;
        else
            return ESat.UNDEFINED;
    }

    @Override
    public String toString() {
        return x.getName() + " + " + y.getName() + " >= " + cste;
    }

    @Override
    public boolean why(RuleStore ruleStore, IntVar var, IEventType evt, int value) {
        boolean newrules =ruleStore.addPropagatorActivationRule(this);
        if (var.equals(x)) {
            newrules |=ruleStore.addUpperBoundRule(y);
        } else if (var.equals(y)) {
            newrules |=ruleStore.addUpperBoundRule(x);
        } else {
            newrules |=super.why(ruleStore, var, evt, value);
        }
        return newrules;
    }

}
