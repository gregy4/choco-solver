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
package org.chocosolver.solver.search.loop.monitors;

import com.github.cpprofiler.Connector;
import gnu.trove.stack.TIntStack;
import gnu.trove.stack.array.TIntArrayStack;
import org.chocosolver.solver.Solver;
import org.chocosolver.solver.exception.ContradictionException;
import org.chocosolver.solver.search.strategy.decision.Decision;
import org.chocosolver.solver.trace.IMessage;
import org.chocosolver.solver.variables.Variable;

/**
 * A search monitor to send data to <a href="https://github.com/cp-profiler/cp-profiler">cp-profiler</a>.
 * It enables to profile and to visualize Constraint Programming.
 * An installation is needed and is described <a href="https://github.com/cp-profiler/cp-profiler">here</a>.
 * This monitor relies on its <a href="https://github.com/cp-profiler/java-integration">java integration</a>.
 * <p>
 * Created by cprudhom on 22/10/2015.
 * Project: choco.
 */
public class CPProfiler implements IMonitorInitialize, IMonitorDownBranch, IMonitorUpBranch,
        IMonitorClose, IMonitorSolution, IMonitorContradiction, IMonitorRestart {

    public static boolean DEBUG = false;

    Solver mSolver;

    // Stacks of 'Parent Id' and 'Alternative' used when backtrack
    TIntStack pid_stack = new TIntArrayStack();
    TIntStack alt_stack = new TIntArrayStack();
     // Stacks of current node, to deal with jumps
    TIntStack last_stack = new TIntArrayStack();
    // Node count: different from measures.getNodeCount() as we count failure nodes as well
    int nc = 0;
    int rid; // restart id
    int last; // last node index sent
    // Used to communicate every node
    Connector connector = new Connector();

    IMessage solutionMessage = new IMessage() {
        @Override
        public String print() {
            StringBuilder s = new StringBuilder(32);
            for (Variable v : mSolver.getVars()) {
                s.append(v).append(' ');
            }
            return s.toString();
        }
    };

    /**
     * Create a bridge to <a href="https://github.com/cp-profiler/cp-profiler">cp-profiler</a>.
     *
     * @param aSolver solver to observe resolution
     */
    public CPProfiler(Solver aSolver) {
        this.mSolver = aSolver;
    }

    /**
     * Create a bridge to <a href="https://github.com/cp-profiler/cp-profiler">cp-profiler</a>.
     *
     * @param aSolver         solver to observe resolution
     * @param solutionMessage to send to cp-profiler when a solution is found
     */
    public CPProfiler(Solver aSolver, IMessage solutionMessage) {
        this.mSolver = aSolver;
        this.solutionMessage = solutionMessage;
    }

    @Override
    public void afterInitialize() {
        if (DEBUG) System.out.printf(
                "connector.restart(%d);\n",
                mSolver.getMeasures().getRestartCount());
        connector.connect(6565); // 6565 is the port used by cpprofiler by default
        connector.restart(0); // starting a new tree (also used in case of a restart)
        alt_stack.push(-1); // -1 is alt for the root node
        pid_stack.push(-1); // -1 is pid for the root node
        last_stack.push(-1);
    }

    @Override
    public void beforeDownBranch(boolean left) {
        if (left) {
            Decision dec = mSolver.getSearchLoop().getLastDecision();
            String pdec = pretty(dec.getPrevious());
            int ari = dec.getArity();
            send(nc, pid_stack.peek(), alt_stack.pop(), ari, rid, Connector.NodeStatus.BRANCH, pdec, "");
            for (int i = 0; i < ari; i++) {
                pid_stack.push(nc); // each child will have the same pid
            }
            nc++;
            alt_stack.push(dec.triesLeft() - 2);
            last_stack.push(nc-1);
        } else {
            nc++;
            alt_stack.push(1);
            last_stack.push(last);
        }
    }

    @Override
    public void beforeUpBranch() {
        last = last_stack.pop();
        while(pid_stack.peek() != last){
            pid_stack.pop();
        }
        pid_stack.pop();
    }

    @Override
    public void onSolution() {
        String dec = pretty(mSolver.getSearchLoop().getLastDecision());
        send(nc, pid_stack.peek(), alt_stack.pop(), 0, rid, Connector.NodeStatus.SOLVED, dec, solutionMessage.print());
    }

    @Override
    public void onContradiction(ContradictionException cex) {
        String dec = pretty(mSolver.getSearchLoop().getLastDecision());
        send(nc, pid_stack.peek(), alt_stack.pop(), 0, rid, Connector.NodeStatus.FAILED, dec, cex.toString());
    }

    @Override
    public void afterRestart() {
        if (DEBUG) System.out.printf(
                "connector.restart(%d);\n",
                mSolver.getMeasures().getRestartCount());
        connector.restart(++rid);
        pid_stack.clear();
        alt_stack.clear();
        alt_stack.push(-1); // -1 is alt for the root node
        pid_stack.push(-1); // -1 is pid for the root node
        last_stack.push(-1);
        nc = 0;
    }

    private void send(int nc, int pid, int alt, int kid, int rid, Connector.NodeStatus status, String label, String info) {
        if (DEBUG) {
            System.out.printf(
                    "connector.sendNode(%d, %d, %d, 0, %s, %d, \"%s\", \"%s\");\n",
                    nc, pid, alt, status.toString(), rid, label, info);
        }
        connector.createNode(nc, pid, alt, kid, status)
                .setRestartId(rid)
                .setLabel(label)
                .setInfo(info)
                .send();
    }

    @Override
    public void afterClose() {
        connector.disconnect();
    }

    private static String pretty(Decision dec) {
        // to print decision correctly (since the previous one is sent)
        int a = dec.getArity();
        int b = dec.triesLeft();
        dec.rewind();
        while (dec.triesLeft() > b + 1) {
            a--;
            dec.buildNext();
        }
        String pretty = dec.toString();
        while (a > b) {
            b++;
            dec.buildNext();
        }
        return pretty;
    }
}
