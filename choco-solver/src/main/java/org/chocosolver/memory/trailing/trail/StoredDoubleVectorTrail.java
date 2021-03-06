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
package org.chocosolver.memory.trailing.trail;

import org.chocosolver.memory.trailing.EnvironmentTrailing;
import org.chocosolver.memory.trailing.StoredDoubleVector;


/**
 * Implements a trail with the history of all the stored search vectors.
 */
public class StoredDoubleVectorTrail implements ITrailStorage {

    /**
     * The current environment.
     */

    private final EnvironmentTrailing environment;


    /**
     * All the stored search vectors.
     */

    private StoredDoubleVector[] vectorStack;


    /**
     * Indices of the previous values in the stored vectors.
     */

    private int[] indexStack;


    /**
     * Previous values of the stored vector elements.
     */

    private double[] valueStack;


    /**
     * World stamps associated to the previous values
     */

    private int[] stampStack;

    /**
     * The last world an search vector was modified in.
     */

    private int currentLevel;


    /**
     * Starts of levels in all the history arrays.
     */

    private int[] worldStartLevels;

    /**
     * capacity of the trailing stack (in terms of number of updates that can be stored)
     */
    private int maxUpdates = 0;


    /**
     * Constructs a trail for the specified environment with the
     * specified numbers of updates and worlds.
     */

    public StoredDoubleVectorTrail(EnvironmentTrailing env, int nUpdates, int nWorlds) {
        this.environment = env;
        this.currentLevel = 0;
        maxUpdates = nUpdates;
        this.vectorStack = new StoredDoubleVector[nUpdates];
        this.indexStack = new int[nUpdates];
        this.valueStack = new double[nUpdates];
        this.stampStack = new int[nUpdates];
        this.worldStartLevels = new int[nWorlds];
    }


    /**
     * Reacts on the modification of an element in a stored search vector.
     */

    public void savePreviousState(StoredDoubleVector vect, int index, double oldValue, int oldStamp) {
        this.vectorStack[currentLevel] = vect;
        this.indexStack[currentLevel] = index;
        this.stampStack[currentLevel] = oldStamp;
        this.valueStack[currentLevel] = oldValue;
        currentLevel++;
        if (currentLevel == maxUpdates) {
            resizeUpdateCapacity();
        }
    }

    private void resizeUpdateCapacity() {
        final int newCapacity = ((maxUpdates * 3) / 2);
        // first, copy the stack of variables
        final StoredDoubleVector[] tmp1 = new StoredDoubleVector[newCapacity];
        System.arraycopy(vectorStack, 0, tmp1, 0, vectorStack.length);
        vectorStack = tmp1;
        // then, copy the stack of former values
        final double[] tmp2 = new double[newCapacity];
        System.arraycopy(valueStack, 0, tmp2, 0, valueStack.length);
        valueStack = tmp2;
        // then, copy the stack of world stamps
        final int[] tmp3 = new int[newCapacity];
        System.arraycopy(stampStack, 0, tmp3, 0, stampStack.length);
        stampStack = tmp3;
        // then, copy the stack of indices
        final int[] tmp4 = new int[newCapacity];
        System.arraycopy(indexStack, 0, tmp4, 0, indexStack.length);
        indexStack = tmp4;

        // last update the capacity
        maxUpdates = newCapacity;
    }

    public void resizeWorldCapacity(int newWorldCapacity) {
        final int[] tmp = new int[newWorldCapacity];
        System.arraycopy(worldStartLevels, 0, tmp, 0, worldStartLevels.length);
        worldStartLevels = tmp;
    }

    /**
     * Moving up to the next world.
     *
     * @param  worldIndex current world index
     */

    public void worldPush(int worldIndex) {
        this.worldStartLevels[worldIndex] = currentLevel;
    }


    /**
     * Moving down to the previous world.
     *
     * @param worldIndex current world index
     */

    public void worldPop(int worldIndex) {
        final int wsl = worldStartLevels[worldIndex];
        while (currentLevel > wsl) {
            currentLevel--;
            StoredDoubleVector v = vectorStack[currentLevel];
            v._set(indexStack[currentLevel], valueStack[currentLevel], stampStack[currentLevel]);
        }
    }


    /**
     * Comits a world: merging it with the previous one.
     */

    public void worldCommit(int worldIndex) {
        // principle:
        //   currentLevel decreases to end of previous world
        //   updates of the committed world are scanned:
        //     if their stamp is the previous one (merged with the current one) -> remove the update (garbage collecting this position for the next update)
        //     otherwise update the worldStamp
        int startLevel = worldStartLevels[environment.getWorldIndex()];
        int prevWorld = environment.getWorldIndex() - 1;
        int writeIdx = startLevel;
        for (int level = startLevel; level < currentLevel; level++) {
            StoredDoubleVector var = vectorStack[level];
            int idx = indexStack[level];
            double val = valueStack[level];
            int stamp = stampStack[level];
            var.worldStamps[idx] = prevWorld;// update the stamp of the variable (current stamp refers to a world that no longer exists)
            if (stamp != prevWorld) {
                // shift the update if needed
                if (writeIdx != level) {
                    valueStack[writeIdx] = val;
                    indexStack[writeIdx] = idx;
                    vectorStack[writeIdx] = var;
                    stampStack[writeIdx] = stamp;
                }
                writeIdx++;
            }  //else:writeIdx is not incremented and the update will be discarded (since a good one is in prevWorld)
        }
        currentLevel = writeIdx;
    }


    /**
     * Returns the current size of the stack.
     */

    public int getSize() {
        return currentLevel;
    }
}
