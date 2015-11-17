#################
Elements of Choco
#################

.. _51_icstr_main:

**********************************
Constraints over integer variables
**********************************



.. _51_icstr_abs:

absolute
========

The ``absolute`` constraint involves two variables `VAR1` and `VAR2`.
It ensures that `VAR1` = \| `VAR2` \| .

**API**:  ::

    Constraint absolute(IntVar VAR1, IntVar VAR2)

.. admonition:: Example

    .. literalinclude:: /../../choco-samples/src/test/java/org/chocosolver/docs/IntConstraintExamples.java
          :language: java
          :lines: 101-104,106
          :emphasize-lines: 104
          :linenos:

    The solutions of the problem are :

     - `X = 0, Y = 0`
     - `X = 1, Y = -1`
     - `X = 1, Y = 1`
     - `X = 2, Y = -2`

.. _51_icstr_alld:

alldifferent
============

The `alldifferent` constraints involves two or more integer variables `VARS` and holds that all variables from `VARS` take a different value.
A signature offers the possibility to specify the filtering algorithm to use:

- ``"BC"``: filters on bounds only, based on :cite:`Lopez-OrtizQTB03`.
- ``"AC"``: filters on the entire domain of the variables, based on :cite:`Regin94`. It runs in `O(m.n)`
worst case time for the initial propagation. The average runtime of further propagations is `O(n+m)`.
- ``"DEFAULT"``: uses ``"BC"`` plus a probabilistic ``"AC"`` propagator to get a compromise between ``"BC"`` and ``"AC"``.

**See also**: `alldifferent <http://sofdem.github.io/gccat/gccat/Calldifferent.html>`_ in the Global Constraint Catalog.

**Implementation based on**: :cite:`Regin94`, :cite:`Lopez-OrtizQTB03`.

**API**:  ::

    Constraint alldifferent(IntVar[] VARS)
    Constraint alldifferent(IntVar[] VARS, String CONSISTENCY)

.. admonition:: Example

    .. literalinclude:: /../../choco-samples/src/test/java/org/chocosolver/docs/IntConstraintExamples.java
          :language: java
          :lines: 232-237,239
          :emphasize-lines: 237
          :linenos:

    Some solutions of the problem are :

     - `X = -1, Y = 2, Z = 5, W = 1`
     - `X = 1, Y = 2, Z = 7, W = 0`
     - `X = 2, Y = 3, Z = 5, W = 0`
     - `X = 2, Y = 4, Z = 7, W = 1`

.. _51_icstr_alldc:

alldifferent_conditionnal
=========================

The `alldifferent_conditionnal` constraint is a variation of the `alldifferent` constraint.
It holds the `alldifferent` constraint on the subset of variables `VARS` which satisfies the given condition `CONDITION`.

A simple example is the `alldifferent_except_0` variation of the `alldifferent` constraint.

**API**:  ::

    Constraint alldifferent_conditionnal(IntVar[] VARS, Condition CONDITION)
    Constraint alldifferent_conditionnal(IntVar[] VARS, Condition CONDITION, boolean AC)

One can force the `AC` algorithm to be used by calling the second signature.


.. admonition:: Example

    .. literalinclude:: /../../choco-samples/src/test/java/org/chocosolver/docs/IntConstraintExamples.java
          :language: java
          :lines: 244-249,258
          :emphasize-lines: 256
          :linenos:

    The condition in the example states that the values `1` and `3` can appear more than once, unlike other values.

    Some solutions of the problem are :

     - `XS[0] = 0, XS[1] = 1, XS[2] = 1, XS[3] = 1, XS[4] = 1`
     - `XS[0] = 0, XS[1] = 1, XS[2] = 2, XS[3] = 1, XS[4] = 1`
     - `XS[0] = 1, XS[1] = 2, XS[2] = 1, XS[3] = 1, XS[4] = 1`
     - `XS[0] = 0, XS[1] = 1, XS[2] = 2, XS[3] = 3, XS[4] = 3`

.. _51_icstr_alld_e0:

alldifferent_except_0
=====================

The `alldifferent_except_0` involves an array of variables `VARS`.
It ensures that all variables from `VAR` take a distinct value or 0, that is, all values but 0 can't appear more than once.

**See also**: `alldifferent_except_0 <http://sofdem.github.io/gccat/gccat/Calldifferent_except_0.html>`_ in the Global Constraint Catalog.

**API**:  ::

    Constraint alldifferent_except_0(IntVar[] VARS)


.. admonition:: Example

    .. literalinclude:: /../../choco-samples/src/test/java/org/chocosolver/docs/IntConstraintExamples.java
          :language: java
          :lines: 254-256,258
          :emphasize-lines: 256
          :linenos:

    Some solutions of the problem are :

     - `XS[0] = 0, XS[1] = 0, XS[2] = 0, XS[3] = 0`
     - `XS[0] = 0, XS[1] = 1, XS[2] = 2, XS[3] = 0`
     - `XS[0] = 0, XS[1] = 2, XS[2] = 0, XS[3] = 0`
     - `XS[0] = 2, XS[1] = 1, XS[2] = 0, XS[3] = 0`

.. _51_icstr_amo:

among
=====

The `among` constraint involves:

 - an integer variable `NVAR`,
 - an array of integer variables `VARIABLES` and
 - an array of integers.

It holds that `NVAR` is the number of variables of the collection `VARIABLES` that take their value in `VALUES`.

**See also**: `among <http://sofdem.github.io/gccat/gccat/Camong.html>`_ in the Global Constraint Catalog.

**Implementation based on**: :cite:`BessiereHHKW05`.

**API**: ::

    Constraint among(IntVar NVAR, IntVar[] VARS, int[] VALUES)

.. admonition:: Example

    .. literalinclude:: /../../choco-samples/src/test/java/org/chocosolver/docs/IntConstraintExamples.java
          :language: java
          :lines: 263-266,268
          :emphasize-lines: 266
          :linenos:

    Some solutions of the problem are :

     - `N = 2, XS[0] = 0, XS[1] = 0, XS[2] = 1, XS[3] = 1`
     - `N = 2, XS[0] = 0, XS[1] = 1, XS[2] = 3, XS[3] = 6`
     - `N = 3, XS[0] = 1, XS[1] = 1, XS[2] = 2, XS[3] = 4`
     - `N = 3, XS[0] = 3, XS[1] = 2, XS[2] = 1, XS[3] = 0`

.. _51_icstr_ari:

arithm
======

The constraint `arithm` involves either:

- a integer variable `VAR`, an operator `OP` and a constant `CST`. It holds `VAR` `OP` `CSTE`, where `CSTE` must be chosen in ``{"=", "!=", ">","<",">=","<="}``.
- or two variables `VAR1` and `VAR2` and an operator `OP`. It ensures that `VAR1` `OP` `VAR2`, where `OP` must be chosen in ``{"=", "!=", ">","<",">=","<="}`` .
- or two variables `VAR1` and `VAR2`, two operators `OP1` and `OP2` and an constant `CSTE`. The operators must be different, taken from ``{"=", "!=", ">","<",">=","<="}``  or ``{"+", "-"}``, the constarint ensures that `VAR1` `OP1` `VAR2` `OP2` `CSTE`.


**API**:  ::

    Constraint arithm(IntVar VAR, String OP, int CSTE)
    Constraint arithm(IntVar VAR1, String OP, IntVar VAR2)
    Constraint arithm(IntVar VAR1, String OP1, IntVar VAR2, String OP2, int CSTE)

.. admonition:: Example 1

    .. literalinclude:: /../../choco-samples/src/test/java/org/chocosolver/docs/IntConstraintExamples.java
          :language: java
          :lines: 55-57,59
          :emphasize-lines: 57
          :linenos:

    The solutions of the problem are :

        - `X = 3`
        - `X = 4`

.. admonition:: Example 2

    .. literalinclude:: /../../choco-samples/src/test/java/org/chocosolver/docs/IntConstraintExamples.java
          :language: java
          :lines: 111-114,116
          :emphasize-lines: 114
          :linenos:

 The solutions of the problem are :

     - `X = 0, Y = -1`
     - `X = 0, Y = 0`
     - `X = 0, Y = 1`
     - `X = 1, Y = 0`
     - `X = 1, Y = 1`
     - `X = 2, Y = 1`


.. _51_icstr_atl:

atleast_nvalues
===============

The `atleast_nvalues` constraint involves:

- an array of integer variables `VARS`,
- an integer variable `NVALUES` and
- a boolean `AC`.

Let `N` be the number of distinct values assigned to the variables of the `VARS` collection.
The constraint enforces the condition `N` :math:`\geq` `NVALUES` to hold.
The boolean `AC` set to true enforces arc-consistency.

**See also**: `atleast_nvalues <http://sofdem.github.io/gccat/gccat/Catleast_nvalue.html>`_ in the Global Constraint Catalog.

**Implementation based on**: :cite:`Regin95`.

**API**:  ::

    Constraint atleast_nvalues(IntVar[] VARS, IntVar NVALUES, boolean AC)

.. admonition:: Example

    .. literalinclude:: /../../choco-samples/src/test/java/org/chocosolver/docs/IntConstraintExamples.java
          :language: java
          :lines: 274-277,279
          :emphasize-lines: 277
          :linenos:

 Some solutions of the problem are :

     - `XS[0] = 0 XS[1] = 0 XS[2] = 0 XS[3] = 1 N = 2`
     - `XS[0] = 0 XS[1] = 1 XS[2] = 0 XS[3] = 1 N = 2`
     - `XS[0] = 0 XS[1] = 1 XS[2] = 2 XS[3] = 1 N = 2`
     - `XS[0] = 2 XS[1] = 0 XS[2] = 2 XS[3] = 1 N = 3`
     - `XS[0] = 2 XS[1] = 2 XS[2] = 1 XS[3] = 0 N = 3`

.. _51_icstr_atm:

atmost_nvalues
==============

The `atmost_nvalues` constraint involves:

- an array of integer variables `VARS`,
- an integer variable `NVALUES` and
- a boolean `STRONG`.

Let `N` be the number of distinct values assigned to the variables of the `VARS` collection.
The constraint enforces the condition `N` :math:`\leq` `NVALUES` to hold.

If the boolean `STRONG` is set to true, then the filtering algorithm of :cite:`FagesAIJ14` is added.
It automatically detects disequalities and `alldifferent` constraints.
This propagator is more powerful but more time consuming as well. this is presumably worthwhile when `NVALUES` must be minimized

**See also**: `atmost_nvalues <http://sofdem.github.io/gccat/gccat/Catmost_nvalue.html>`_ in the Global Constraint Catalog.

**Implementation based on**: :cite:`FagesAIJ14`.

**API**:  ::

    Constraint atmost_nvalues(IntVar[] VARS, IntVar NVALUES, boolean GREEDY)

.. admonition:: Example

    .. literalinclude:: /../../choco-samples/src/test/java/org/chocosolver/docs/IntConstraintExamples.java
          :language: java
          :lines: 284-287,289
          :emphasize-lines: 287
          :linenos:

 Some solutions of the problem are :

     - `XS[0] = 0, XS[1] = 0, XS[2] = 0, XS[3] = 0, N = 1`
     - `XS[0] = 0, XS[1] = 0, XS[2] = 0, XS[3] = 0, N = 2`
     - `XS[0] = 0, XS[1] = 0, XS[2] = 0, XS[3] = 0, N = 3`
     - `XS[0] = 0, XS[1] = 0, XS[2] = 0, XS[3] = 1, N = 2`
     - `XS[0] = 0, XS[1] = 1, XS[2] = 1, XS[3] = 0, N = 2`
     - `XS[0] = 2, XS[1] = 2, XS[2] = 1, XS[3] = 0, N = 3`

.. _51_icstr_bin:

bin_packing
===========

The `bin_packing` constraint involves:

 - an array of integer variables `ITEM_BIN`,
 - an array of integers `ITEM_SIZE`,
 - an array of integer variables `BIN_LOAD` and
 - an integer `OFFSET`.

It holds the Bin Packing Problem rules: a set of items with various SIZES to pack into bins with respect to the capacity of each bin.

- `ITEM_BIN` represents the bin of each item, that is, `ITEM_BIN[i] = j` states that the i :math:`^{th}` ITEM is put in the j :math:`^{th}` bin.
- `ITEM_SIZE` represents the size of each item.
- `BIN_LOAD` represents the load of each bin, that is, the sum of size of the items in it.

This constraint is not a built-in constraint and is based on various propagators.

**See also**: `bin_packing <http://sofdem.github.io/gccat/gccat/Cbin_packing.html>`_ in the Global Constraint Catalog.

**API**:  ::

    Constraint[] bin_packing(IntVar[] ITEM_BIN, int[] ITEM_SIZE, IntVar[] BIN_LOAD, int OFFSET)

.. admonition:: Example

    .. literalinclude:: /../../choco-samples/src/test/java/org/chocosolver/docs/IntConstraintExamples.java
          :language: java
          :lines: 294-298,300
          :emphasize-lines: 298
          :linenos:

 Some solutions of the problem are :

     - `IBIN[0] = 1, IBIN[1] = 1, IBIN[2] = 2, IBIN[3] = 2, IBIN[4] = 3, BLOADS[0] = 5, BLOADS[1] = 5, BLOADS[2] = 2`
     - `IBIN[0] = 1, IBIN[1] = 3, IBIN[2] = 1, IBIN[3] = 2, IBIN[4] = 1, BLOADS[0] = 5, BLOADS[1] = 4, BLOADS[2] = 3`
     - `IBIN[0] = 2, IBIN[1] = 3, IBIN[2] = 1, IBIN[3] = 1, IBIN[4] = 3, BLOADS[0] = 5, BLOADS[1] = 2, BLOADS[2] = 5`

.. _51_icstr_bitc:

bit_channeling
==============

The `bit_channeling` constraint involves:

 - an array of boolean variables `BVARS` and
 - an integer variable `VAR`.

It ensures that: `VAR` = :math:`2^0 \times` `BITS[0]` :math:`2^1 \times` `BITS[1]` + ... +:math:`2^{n} \times` `BITS[n]`.
`BIT[0] is related to the first bit of `VAR` (:math:`2^0`),
`BIT[1] is related to the second bit of `VAR` (:math:`2^1`), etc.
The upper bound of `VAR` is given by :math:`2^{|BITS|+1}`.

**API**:  ::

    Constraint bit_channeling(BoolVar[] BITS, IntVar VAR)

.. admonition:: Example

    .. literalinclude:: /../../choco-samples/src/test/java/org/chocosolver/docs/IntConstraintExamples.java
          :language: java
          :lines: 613-616,618
          :emphasize-lines: 616
          :linenos:

 The solutions of the problem are :

     - `VAR = 0, BVARS[0] = 0, BVARS[1] = 0, BVARS[2] = 0, BVARS[3] = 0`
     - `VAR = 1, BVARS[0] = 1, BVARS[1] = 0, BVARS[2] = 0, BVARS[3] = 0`
     - `VAR = 2, BVARS[0] = 0, BVARS[1] = 1, BVARS[2] = 0, BVARS[3] = 0`
     - `VAR = 11, BVARS[0] = 1, BVARS[1] = 1, BVARS[2] = 0, BVARS[3] = 1`
     - `VAR = 15, BVARS[0] = 1, BVARS[1] = 1, BVARS[2] = 1, BVARS[3] = 1`

.. _51_icstr_booc:

boolean_channeling
==================

The `boolean_channeling` constraint involves:

 - an array of boolean variables `BVARS`,
 - an integer variable `VAR` and
 - an integer `OFFSET`.

It ensures that: `VAR` = `i` :math:`\Leftrightarrow` `BVARS` [ `i-OFFSET` ] = `1`.
The `OFFSET` is typically set to 0.

**API**:  ::

    Constraint boolean_channeling(BoolVar[] BVARS, IntVar VAR, int OFFSET)

.. admonition:: Example

    .. literalinclude:: /../../choco-samples/src/test/java/org/chocosolver/docs/IntConstraintExamples.java
          :language: java
          :lines: 305-308,310
          :emphasize-lines: 308
          :linenos:

 The solutions of the problem are :

     - `VAR = 1, BVARS[0] = 1, BVARS[1] = 0, BVARS[2] = 0, BVARS[3] = 0, BVARS[4] = 0`
     - `VAR = 2, BVARS[0] = 0, BVARS[1] = 1, BVARS[2] = 0, BVARS[3] = 0, BVARS[4] = 0`
     - `VAR = 3, BVARS[0] = 0, BVARS[1] = 0, BVARS[2] = 1, BVARS[3] = 0, BVARS[4] = 0`
     - `VAR = 4, BVARS[0] = 0, BVARS[1] = 0, BVARS[2] = 0, BVARS[3] = 1, BVARS[4] = 0`
     - `VAR = 5, BVARS[0] = 0, BVARS[1] = 0, BVARS[2] = 0, BVARS[3] = 0, BVARS[4] = 1`


.. _51_icstr_clauc:

clause_channeling
=================

The `clause_channeling` constraint involves:

 - an integer variable `VAR` and
 - two arrays of boolean variables `EVARS` and `LVARS`.

It ensures that: `VAR` = `i` :math:`\Leftrightarrow` `EVARS` [ `i` - OFFSET ] = `1` and
 `VAR` :math:`\leq` `i` :math:`\Leftrightarrow` `LVARS` [ `i` - OFFSET ] = `1`
 where  OFFSET is the initial lower bound of VAR.

**API**:  ::

    Constraint clause_channeling(IntVar VAR, BoolVar[] EVARS, BoolVar[] LVARS)

.. admonition:: Example

    .. literalinclude:: /../../choco-samples/src/test/java/org/chocosolver/docs/IntConstraintExamples2.java
          :language: java
          :lines: 65-69,71
          :emphasize-lines: 69
          :linenos:


 The solutions of the problem are :

     - `VAR = 1, EVARS[0] = 1, EVARS[1] = 0, EVARS[2] = 0, LVARS[0] = 1, LVARS[1] = 1, LVARS[2] = 1`
     - `VAR = 2, EVARS[0] = 0, EVARS[1] = 1, EVARS[2] = 0, LVARS[0] = 0, LVARS[1] = 1, LVARS[2] = 1`
     - `VAR = 3, EVARS[0] = 0, EVARS[1] = 0, EVARS[2] = 1, LVARS[0] = 0, LVARS[1] = 0, LVARS[2] = 1`


.. _51_icstr_cir:

circuit
=======

The `circuit` constraint involves:

 - an array of integer variables `VARS`,
 - an integer `OFFSET` and
 - a configuration `CONF`.

It ensures that the elements of `VARS` define a covering circuit where `VARS` [i] = `OFFSET` + `j` means that `j` is the successor of `i`.

The filtering algorithms are the subtour elimination of :cite:`CaseauL97` (constant-time per propagation) and the `alldifferent` GAC filtering of :cite:`Regin94`.
In addition, depending on `CONF`, the dominator filtering of the tree (GAC) constraint :cite:`FagesL11`
and the strongly connected components filtering of the path constraint :cite:`Cambazard04,FagesCoRR12` may be added through a dynamical circuit/path transformation.

The `CONF` is a defined by an ``enum``:

- ``CircuitConf.LIGHT``:  no circuit/path transformation
- ``CircuitConf.FIRST``: circuit/path transformation by duplicating the first node
- ``CircuitConf.RD``: circuit/path transformation by duplicating a random node
- ``CircuitConf.ALL``: circuit/path transformation by duplicating every node

This implementation is detailed in :cite:`FagesPhD`

**See also**: `circuit <http://sofdem.github.io/gccat/gccat/Ccircuit.html>`_ in the Global Constraint Catalog.

**Implementation based on**: :cite:`Regin94,CaseauL97,Cambazard04,FagesCoRR12,FagesL11,FagesPhD`.

**API**:  ::

    Constraint circuit(IntVar[] VARS, int OFFSET, CircuitConf CONF)
    Constraint circuit(IntVar[] VARS, int OFFSET) // with CircuitConf.RD

.. admonition:: Example

    .. literalinclude:: /../../choco-samples/src/test/java/org/chocosolver/docs/IntConstraintExamples.java
          :language: java
          :lines: 315-317,319
          :emphasize-lines: 317
          :linenos:

 Some solutions of the problem are :

     - `NODES[0] = 1, NODES[1] = 2, NODES[2] = 3, NODES[3] = 4, NODES[4] = 0`
     - `NODES[0] = 3, NODES[1] = 4, NODES[2] = 0, NODES[3] = 1, NODES[4] = 2`
     - `NODES[0] = 4, NODES[1] = 2, NODES[2] = 3, NODES[3] = 0, NODES[4] = 1`
     - `NODES[0] = 4, NODES[1] = 3, NODES[2] = 1, NODES[3] = 0, NODES[4] = 2`

.. _51_icstr_creg:

cost_regular
============

The `cost_regular` constraint involves:

 - an array of integer variables `VARS`,
 - an integer variable `COST` and
 - a cost automaton `CAUTOMATON`.

It ensures that the assignment of a sequence of variables `VARS` is recognized by `CAUTOMATON`, a deterministic finite automaton,
and that the sum of the costs associated to each assignment is bounded by the cost variable.
This version allows to specify different costs according to the automaton state at which the assignment occurs (i.e. the transition starts).

The `CAUOTMATON` can be defined using the `org.chocosolver.solver.constraints.nary.automata.FA.CostAutomaton` either:

- by creating a ``CostAutomaton``: once created, states should be added, then initial and final states are defined and finally, transitions are declared.
- or by first creating a ``FiniteAutomaton`` and then creating a matrix of costs and finally calling one of the following API from ``CostAutomaton``:

    + ``ICostAutomaton makeSingleResource(IAutomaton pi, int[][][] costs, int inf, int sup)``
    + ``ICostAutomaton makeSingleResource(IAutomaton pi, int[][] costs, int inf, int sup)``

 The other API of ``CostAutomaton`` (``makeMultiResources(...)``) are dedicated to the `multicost_regular` constraint.

**Implementation based on**: :cite:`DemasseyPR06`.

**API**:  ::

    Constraint cost_regular(IntVar[] VARS, IntVar COST, ICostAutomaton CAUTOMATON)

.. admonition:: Example

     .. literalinclude:: /../../choco-samples/src/test/java/org/chocosolver/docs/IntConstraintExamples.java
           :language: java
           :lines: 324-346,348
           :emphasize-lines: 348
           :linenos:

  Some solutions of the problem are :

      - `VARS[0] = 0, VARS[1] = 0, VARS[2] = 0, VARS[3] = 0, VARS[4] = 1, COST = 10`
      - `VARS[0] = 0, VARS[1] = 0, VARS[2] = 0, VARS[3] = 1, VARS[4] = 1, COST = 9`
      - `VARS[0] = 0, VARS[1] = 0, VARS[2] = 1, VARS[3] = 2, VARS[4] = 1, COST = 6`
      - `VARS[0] = 1, VARS[1] = 2, VARS[2] = 1, VARS[3] = 0, VARS[4] = 1, COST = 8`


.. _51_icstr_cou:


count
=====

The `count` constraint involves:

 - an integer `VALUE`,
 - an array of integer variables `VARS` and
 - an integer variable `LIMIT`.

The constraint holds that `LIMIT` is equal to the number of variables from  `VARS` assigned to the value `VALUE`.
An alternate signature enables `VALUE` to be an integer variable.

**See also**: `count <http://sofdem.github.io/gccat/gccat/Ccount.html>`_ in the Global Constraint Catalog.

**API**:  ::

    Constraint count(int VALUE, IntVar[] VARS, IntVar LIMIT)
    Constraint count(IntVar VALUE, IntVar[] VARS, IntVar LIMIT)

.. admonition:: Example

     .. literalinclude:: /../../choco-samples/src/test/java/org/chocosolver/docs/IntConstraintExamples.java
           :language: java
           :lines: 353-357,359
           :emphasize-lines: 357
           :linenos:

  Some solutions of the problem are :

      - `VS[0] = 0, VS[1] = 0, VS[2] = 0, VS[3] = 0, VA = 1, CO = 0`
      - `VS[0] = 0, VS[1] = 1, VS[2] = 1, VS[3] = 0, VA = 1, CO = 2`
      - `VS[0] = 0, VS[1] = 2, VS[2] = 2, VS[3] = 1, VA = 3, CO = 0`
      - `VS[0] = 3, VS[1] = 3, VS[2] = 3, VS[3] = 3, VA = 3, CO = 4`

.. _51_icstr_cum:

cumulative
==========

The `cumulative` constraints involves:

 - an array of task object `TASKS`,
 - an array of integer variable `HEIGHTS`,
 - an integer variable `CAPACITY` and
 - a boolean `INCREMENTAL` (graph-based self-decomposition of :cite:`FagesECAI14`).

It ensures that at each point of the time the cumulative height of the set of tasks that overlap that point does not exceed the given capacity.

**See also**: `cumulative <http://sofdem.github.io/gccat/gccat/Ccumulative.html>`_ in the Global Constraint Catalog.

**Implementation based on**: :cite:`FagesECAI14`.

**API**:  ::

    Constraint cumulative(Task[] TASKS, IntVar[] HEIGHTS, IntVar CAPACITY)
    Constraint cumulative(Task[] TASKS, IntVar[] HEIGHTS, IntVar CAPACITY, boolean INCREMENTAL)

The first API relies on the second, and set `INCREMENTAL` to ``TASKS.length > 500``.

.. admonition:: Example 1

    .. literalinclude:: /../../choco-samples/src/test/java/org/chocosolver/docs/IntConstraintExamples.java
          :language: java
          :lines: 364-377,379
          :emphasize-lines: 377
          :linenos:

 Some solutions of the problem are :

     - `S_0 = 0, HE_0 = 0, S_1 = 0, HE_1 = 0, S_2 = 0, HE_2 = 1, S_3 = 0, HE_3 = 2 S_4 = 4, HE_4 = 3, CA = 3`
     - `S_0 = 4, HE_0 = 0, S_1 = 4, HE_1 = 0, S_2 = 1, HE_2 = 1, S_3 = 0, HE_3 = 2 S_4 = 4, HE_4 = 3, CA = 3`
     - `S_0 = 0, HE_0 = 1, S_1 = 0, HE_1 = 0, S_2 = 1, HE_2 = 1, S_3 = 0, HE_3 = 2 S_4 = 4, HE_4 = 3, CA = 3`

.. _51_icstr_diffn:

diffn
=====

The `diffn` constraint involves:

 - four arrays of integer variables `X`, `Y`, `WIDTH` and `HEIGHT` and
 - a boolean `USE_CUMUL`.

It ensures that each rectangle `i` defined by its coordinates (`X[i]`, `Y[i]`) and its dimensions (`WIDTH[i]`, `HEIGHT[i]`) does not overlap each other.
The option `USE_CUMUL`, recommended, indicates whether or not redundant `cumulative` constraints should be added on each dimension.

**See also**: `diffn <http://sofdem.github.io/gccat/gccat/Cdiffn.html>`_ in the Global Constraint Catalog.

**Implementation based on**: :cite:`FagesECAI14`.

**API**:  ::

    Constraint[] diffn(IntVar[] X, IntVar[] Y, IntVar[] WIDTH, IntVar[] HEIGHT, boolean USE_CUMUL)


.. admonition:: Example 1

    .. literalinclude:: /../../choco-samples/src/test/java/org/chocosolver/docs/IntConstraintExamples.java
          :language: java
          :lines: 384-393,395
          :emphasize-lines: 393
          :linenos:

 Some solutions of the problem are :

     - `X[0] = 0 X[1] = 1, X[2] = 0, X[3] = 1, Y[0] = 0, Y[1] = 0, Y[2] = 1, Y[3]`
     - `X[0] = 1 X[1] = 0, X[2] = 1, X[3] = 0, Y[0] = 0, Y[1] = 0, Y[2] = 2, Y[3]`
     - `X[0] = 0 X[1] = 1, X[2] = 0, X[3] = 1, Y[0] = 1, Y[1] = 0, Y[2] = 2, Y[3]`


.. _51_icstr_dist:

distance
========

The ``distance`` constraint involves either:

- two variables `VAR1` and `VAR2`, an operator `OP` and a constant  `CSTE`. It ensures that \| `VAR1` - `VAR2` \| `OP` `CSTE`, where `OP` must be chosen in ``{"=", "!=", ">","<"}`` .
- or three variables `VAR1`, `VAR2` and `VAR3` and an operator `OP`. It ensures that \| `VAR1` - `VAR2` \| `OP` `VAR3`, where `OP` must be chosen in ``{"=",">","<"}`` .


**See also**: `distance <http://sofdem.github.io/gccat/gccat/Cdistance.html>`_ in the Global Constraint Catalog.

**API**:  ::

    Constraint distance(IntVar VAR1, IntVar VAR2, String OP, int CSTE)
    Constraint distance(IntVar VAR1, IntVar VAR2, String OP, IntVar VAR3)

.. admonition:: Example 1

    .. literalinclude:: /../../choco-samples/src/test/java/org/chocosolver/docs/IntConstraintExamples.java
          :language: java
          :lines: 121-124,126
          :emphasize-lines: 124
          :linenos:

 The solutions of the problem are :

     - `X = 0, Y = -1`
     - `X = 0, Y = 1`
     - `X = 1, Y = 0`
     - `X = 2, Y = 1`

.. admonition:: Example 2

    .. literalinclude:: /../../choco-samples/src/test/java/org/chocosolver/docs/IntConstraintExamples.java
          :language: java
          :lines: 166-170,172
          :emphasize-lines: 170
          :linenos:

 The solutions of the problem are :

     - `X = 1, Y = 0, Z = 2`
     - `X = 1, Y = 1, Z = 2`
     - `X = 2, Y = 1, Z = 2`
     - `X = 1, Y = -1, Z = 3`
     - `X = 1, Y = 0, Z = 3`
     - `X = 1, Y = 1, Z = 3`
     - `X = 2, Y = 0, Z = 3`
     - `X = 2, Y = 1, Z = 3`
     - `X = 3, Y = 1, Z = 3`


.. _51_icstr_elm:

element
=======

The `element` constraint involves either:

- two variables `VALUE` and `INDEX`, an array of values `TABLE`, an offset `OFFSET` and an ordering property `SORT`. `SORT` must be chosen among:

    + ``"none"``: if values in `TABLE` are not sorted,
    + ``"asc"``: if values in `TABLE` are sorted in increasing order,
    + ``"desc"``: if values in `TABLE` are sorted in decreasing order,
    + ``"detect"``: let the constraint detects the ordering of values in `TABLE`, if any (default value).

- or an integer variable `VALUE`, an array of integer variables `TABLE`, an integer variable `INDEX` and an integer `OFFSET`.

The `element` constraint ensures that `VALUE` = `TABLE` [`INDEX` - `OFFSET`]. `OFFSET` matches `INDEX.LB` and `TABLE[0]` (0 by default).


**See also**: `element <http://sofdem.github.io/gccat/gccat/Celement.html>`_ in the Global Constraint Catalog.

**API**:  ::

    Constraint element(IntVar VALUE, int[] TABLE, IntVar INDEX)
    Constraint element(IntVar VALUE, int[] TABLE, IntVar INDEX, int OFFSET, String SORT)
    Constraint element(IntVar VALUE, IntVar[] TABLE, IntVar INDEX, int OFFSET)



.. admonition:: Example

    .. literalinclude:: /../../choco-samples/src/test/java/org/chocosolver/docs/IntConstraintExamples.java
          :language: java
          :lines: 131-134,136
          :emphasize-lines: 134
          :linenos:

 The solutions of the problem are :

     - `V = -2, I = 1`
     - `V = -1, I = 3`
     - `V = 0, I = 4`
     - `V = 1, I = 2`
     - `V = 2, I = 0`

.. _51_icstr_div:

eucl_div
========

The `eucl_div` constraints involves three variables `DIVIDEND`, `DIVISOR` and `RESULT`.
It ensures that `DIVIDEND` / `DIVISOR` = `RESULT`, rounding towards 0.

The API is : ::

    Constraint eucl_div(IntVar DIVIDEND, IntVar DIVISOR, IntVar RESULT)

.. admonition:: Example

    .. literalinclude:: /../../choco-samples/src/test/java/org/chocosolver/docs/IntConstraintExamples.java
          :language: java
          :lines: 177-181,183
          :emphasize-lines: 181
          :linenos:

 The solutions of the problem are :

     - `X = 2, Y = 1, Z = 2`
     - `X = 3, Y = 1, Z = 3`


.. _51_icstr_fal:

FALSE
=====

The `FALSE` constraint is always unsatisfied. It should only be used with ``LogicalFactory``.

.. _51_icstr_gcc:

global_cardinality
==================

The `global_cardinality` constraint involves:

 - an array of integer variables `VARS`,
 - an array of integer `VALUES`,
 - an array of integer variables `OCCURRENCES` and
 - a boolean `CLOSED`.

It ensures that each value `VALUES[i]` is taken by exactly `OCCURRENCES[i]` variables in `VARS`.
The boolean `CLOSED` set to `true` restricts the domain of `VARS` to the values defined in `VALUES`.

*The underlying propagator does not ensure any well-defined level of consistency*.

**See also**: `global_cardinality <http://sofdem.github.io/gccat/gccat/Cglobal_cardinality.html>`_ in the Global Constraint Catalog.

**API**:  ::

    Constraint global_cardinality(IntVar[] VARS, int[] VALUES, IntVar[] OCCURRENCES, boolean CLOSED)


.. admonition:: Example

    .. literalinclude:: /../../choco-samples/src/test/java/org/chocosolver/docs/IntConstraintExamples.java
          :language: java
          :lines: 400-404,406
          :emphasize-lines: 404
          :linenos:

 The solutions of the problem are :

     - `VS[0] = 1, VS[1] = 1, VS[2] = 2, VS[3] = 2, OCC[0] = 0, OCC[1] = 2, OCC[2] = 2`
     - `VS[0] = 1, VS[1] = 2, VS[2] = 1, VS[3] = 2, OCC[0] = 0, OCC[1] = 2, OCC[2] = 2`
     - `VS[0] = 1, VS[1] = 2, VS[2] = 2, VS[3] = 1, OCC[0] = 0, OCC[1] = 2, OCC[2] = 2`
     - `VS[0] = 2, VS[1] = 1, VS[2] = 1, VS[3] = 2, OCC[0] = 0, OCC[1] = 2, OCC[2] = 2`
     - `VS[0] = 2, VS[1] = 1, VS[2] = 2, VS[3] = 1, OCC[0] = 0, OCC[1] = 2, OCC[2] = 2`
     - `VS[0] = 2, VS[1] = 2, VS[2] = 1, VS[3] = 1, OCC[0] = 0, OCC[1] = 2, OCC[2] = 2`

.. _51_icstr_ich:

inverse_channeling
==================

The `inverse_channeling` constraint involves:

 - two arrays of integer variables `VARS1` and `VARS2` and
 - two integers `OFFSET1` and `OFFSET2`.

It ensures that `VARS1[i - OFFSET2] = j` :math:`\Leftrightarrow` `VARS2[j - OFFSET1] = i`.
It performs AC if the domains are enumerated. Otherwise, BC is not guaranteed.
It also automatically imposes one `alldifferent` constraints on each array of variables.

**API**:  ::

    Constraint inverse_channeling(IntVar[] VARS1, IntVar[] VARS2, int OFFSET1, int OFFSET2)

.. admonition:: Example

    .. literalinclude:: /../../choco-samples/src/test/java/org/chocosolver/docs/IntConstraintExamples.java
          :language: java
          :lines: 411-414,416
          :emphasize-lines: 414
          :linenos:

   The solutions of the problems are:

   - `X[0] = 0, X[1] = 1, X[2] = 2, Y[0] = 1, Y[1] = 2, Y[2] = 3`
   - `X[0] = 0, X[1] = 2, X[2] = 1, Y[0] = 1, Y[1] = 3, Y[2] = 2`
   - `X[0] = 1, X[1] = 0, X[2] = 2, Y[0] = 2, Y[1] = 1, Y[2] = 3`
   - `X[0] = 1, X[1] = 2, X[2] = 0, Y[0] = 3, Y[1] = 1, Y[2] = 2`
   - `X[0] = 2, X[1] = 0, X[2] = 1, Y[0] = 2, Y[1] = 3, Y[2] = 1`
   - `X[0] = 2, X[1] = 1, X[2] = 0, Y[0] = 3, Y[1] = 2, Y[2] = 1`

.. _51_icstr_nvpc

int_value_precede_chain
=======================

The `int_value_precede_chain` constraint involves an array of integer variables `X` and

- either two integers `S` and `T`
- or an array of distinct integers.

It ensures that if there exists j such that X[j] = T, then, there must exist i < j such that X[i] = S.
Or it ensures that, for each pair of V[k] and V[l] of values in V, such that k < l, if there exists j such that
X[j] = V[l], then, there must exist i < j such that X[i] = V[k].

**See also**: `int_value_precede <http://sofdem.github.io/gccat/gccat/Cint_value_precede.htmll>`_ in the Global Constraint Catalog.

**Implementation based on**: :cite:`YatChiuLawJimmyLee04`.

** API**: ::

    Constraint int_value_precede_chain(IntVar[] X, int S, int T)

.. admonition:: Example

    .. literalinclude:: /../../choco-samples/src/test/java/org/chocosolver/docs/IntConstraintExamples2.java
          :language: java
          :lines: 86-88,90
          :emphasize-lines: 88
          :linenos:

   The solutions of the problems are:

   - `X[0] = 2 X[1] = 2 X[2] = 2`
   - `X[0] = 2 X[1] = 2 X[2] = 3`
   - `X[0] = 2 X[1] = 3 X[2] = 1`
   - `X[0] = 2 X[1] = 3 X[2] = 2`
   - `X[0] = 2 X[1] = 3 X[2] = 3`


.. _51_icstr_ksor:

keysorting
==========

The `keysorting` constraint involves three matrices of integer variables `VARS` and `SORTEDVARS`,
an array of integer variables `PERMVARS` and an integer `K`.
It ensures that the variables of `SORTEDVARS` correspond to the variables of `VARS` according to a permutation.
Moreover, the variable of `SORTEDVARS` are sorted in increasing order wrt to K-tuple and PERMVARS store the permutations.

.. **See also**: `sort <http://sofdem.github.io/gccat/gccat/Csort.html>`_ in the Global Constraint Catalog.

.. **Implementation based on**: :cite:`MehlhornT00`.

**API**: ::

    Constraint keysorting(IntVar[][] VARS, IntVar[] PERMVARS, IntVar[][] SORTEDVARS, int K)

.. admonition:: Example

    .. literalinclude:: /../../choco-samples/src/test/java/org/chocosolver/docs/IntConstraintExamples2.java
          :language: java
          :lines: 77-81,83
          :emphasize-lines: 81
          :linenos:

    Some solutions of the problem are :

     - `X[0][0] = 2 X[0][1] = 1 X[0][2] = 1 X[1][0] = 1 X[1][1] = 1 X[1][2] = 1`
     `Y[0][0] = 1 Y[0][1] = 1 Y[0][2] = 1 Y[1][0] = 2 Y[1][1] = 1 Y[1][2] = 1`
     `P[0] = 2 P[1] = 1 P[2] = 0`

     - `X[0][0] = 2 X[0][1] = 1 X[0][2] = 1 X[1][0] = 1 X[1][1] = 3 X[1][2] = 2`
     `Y[0][0] = 1 Y[0][1] = 3 Y[0][2] = 2 Y[1][0] = 2 Y[1][1] = 1 Y[1][2] = 1`
     `P[0] = 2 P[1] = 1 P[2] = 2`

     - `X[0][0] = 2 X[0][1] = 1 X[0][2] = 3 X[1][0] = 1 X[1][1] = 1 X[1][2] = 2`
     `Y[0][0] = 1 Y[0][1] = 1 Y[0][2] = 2 Y[1][0] = 2 Y[1][1] = 1 Y[1][2] = 3`
     `P[0] = 2 P[1] = 1 P[2] = 1`

.. _51_icstr_kna:

knapsack
========

The `knapsack` constraint involves:
- an array of integer variables `OCCURRENCES`,
- an integer variable `TOTAL_WEIGHT`,
- an integer variable `TOTAL_ENERGY`,
- an array of integers `WEIGHT` and
- an an array of integers `ENERGY`.

It formulates the Knapsack Problem: to determine the count of each item to include in a collection so that the total weight is less than or equal to a given limit and the total value is as large as possible.

- :math:`\sum` `OCCURRENCES[i]` :math:`\times` `WEIGHT[i]` :math:`\leq` `TOTAL_WEIGHT` and
- :math:`\sum` `OCCURRENCES[i]` :math:`\times` `ENERGY[i]` = `TOTAL_ENERGY`.

**API**:  ::

    Constraint knapsack(IntVar[] OCCURRENCES, IntVar TOTAL_WEIGHT, IntVar TOTAL_ENERGY,
                                          int[] WEIGHT, int[] ENERGY)


.. admonition:: Example

    .. literalinclude:: /../../choco-samples/src/test/java/org/chocosolver/docs/IntConstraintExamples.java
          :language: java
          :lines: 421-430,432
          :emphasize-lines: 430
          :linenos:

   Some solutions of the problems are:

   - `IT_0 = 0, IT_1 = 0, IT_2 = 0, WE = 0, EN = 0`
   - `IT_0 = 3, IT_1 = 0, IT_2 = 0, WE = 3, EN = 3`
   - `IT_0 = 1, IT_1 = 1, IT_2 = 0, WE = 4, EN = 5`
   - `IT_0 = 2, IT_1 = 1, IT_2 = 0, WE = 5, EN = 6`

.. _51_icstr_lexcl:

lex_chain_less
==============

The `lex_chain_less` constraint involves a matrix of integer variables `VARS`.
It ensures that, for each pair of consecutive arrays `VARS[i]` and `VARS[i+1]`,
`VARS[i]` is lexicographically strictly less than `VARS[i+1]`.

**See also**: `lex_chain_less <http://sofdem.github.io/gccat/gccat/Clex_chain_less.html>`_ in the Global Constraint Catalog.

**Implementation based on**: :cite:`CarlssonB02`.

**API**:  ::

    Constraint lex_chain_less(IntVar[]... VARS)

.. admonition:: Example

    .. literalinclude:: /../../choco-samples/src/test/java/org/chocosolver/docs/IntConstraintExamples.java
          :language: java
          :lines: 437-441,443
          :emphasize-lines: 441
          :linenos:

   Some solutions of the problems are:

   - `X[0] = -1, X[1] = -1, X[2] = -1, Y[0] = 1, Y[1] = 1, Y[2] = 1, Z[0] = 1, Z[1] = 1, Z[2] = 2`
   - `X[0] = 0, X[1] = 1, X[2] = -1, Y[0] = 1, Y[1] = 1, Y[2] = 1, Z[0] = 1, Z[1] = 2, Z[2] = 0`
   - `X[0] = 1, X[1] = 0, X[2] = 1, Y[0] = 1, Y[1] = 1, Y[2] = 1, Z[0] = 1, Z[1] = 2, Z[2] = 0`
   - `X[0] = -1, X[1] = 1, X[2] = 1, Y[0] = 1, Y[1] = 1, Y[2] = 1, Z[0] = 2, Z[1] = 2, Z[2] = 1`

.. _51_icstr_lexce:

lex_chain_less_eq
=================

The `lex_chain_less_eq` constraint involves a matrix of integer variables `VARS`.
It ensures that, for each pair of consecutive arrays `VARS[i]` and `VARS[i+1]`,
`VARS[i]` is lexicographically strictly less or equal than `VARS[i+1]`.

**See also**: `lex_chain_less_eq <http://sofdem.github.io/gccat/gccat/Clex_chain_lesseq.html>`_ in the Global Constraint Catalog.

**Implementation based on**: :cite:`CarlssonB02`.

**API**:  ::

    Constraint lex_chain_less_eq(IntVar[]... VARS)

.. admonition:: Example

    .. literalinclude:: /../../choco-samples/src/test/java/org/chocosolver/docs/IntConstraintExamples.java
          :language: java
          :lines: 448-452,454
          :emphasize-lines: 452
          :linenos:

   Some solutions of the problems are:

   - `X[0] = -1, X[1] = -1, X[2] = -1, Y[0] = 1, Y[1] = 1, Y[2] = 1, Z[0] = 1, Z[1] = 1, Z[2] = 1`
   - `X[0] = -1, X[1] = 1, X[2] = 1, Y[0] = 1, Y[1] = 1, Y[2] = 1, Z[0] = 1, Z[1] = 1, Z[2] = 1`
   - `X[0] = 0, X[1] = 1, X[2] = -1, Y[0] = 1, Y[1] = 1, Y[2] = 1, Z[0] = 2, Z[1] = 1, Z[2] = 2`
   - `X[0] = -1, X[1] = -1, X[2] = 0, Y[0] = 1, Y[1] = 1, Y[2] = 2, Z[0] = 2, Z[1] = 2, Z[2] = 2`

.. _51_icstr_lexl:

lex_less
========

The `lex_less` constraint involves two arrays of integer variables `VARS1` and `VARS2`.
It ensures that `VARS1` is lexicographically strictly less than `VARS2`.

**See also**: `lex_less <http://sofdem.github.io/gccat/gccat/Clex_less.html>`_ in the Global Constraint Catalog.

**Implementation based on**: :cite:`FrischHKMW02`.

**API**:  ::

    Constraint lex_less(IntVar[] VARS1, IntVar[] VARS2)

.. admonition:: Example

    .. literalinclude:: /../../choco-samples/src/test/java/org/chocosolver/docs/IntConstraintExamples.java
          :language: java
          :lines: 459-462,464
          :emphasize-lines: 462
          :linenos:

   Some solutions of the problems are:

   - `X[0] = -1, X[1] = -1, X[2] = -1, Y[0] = 1, Y[1] = 1, Y[2] = 1`
   - `X[0] = -1, X[1] = 0, X[2] = 0, Y[0] = 1, Y[1] = 2, Y[2] = 1`
   - `X[0] = -1, X[1] = 0, X[2] = -1, Y[0] = 2, Y[1] = 1, Y[2] = 1`
   - `X[0] = -1, X[1] = -1, X[2] = 0, Y[0] = 2, Y[1] = 2, Y[2] = 2`

.. _51_icstr_lexe:

lex_less_eq
===========

The `lex_less_eq` constraint involves two arrays of integer variables `VARS1` and `VARS2`.
It ensures that `VARS1` is lexicographically strictly less or equal than `VARS2`.

**See also**: `lex_less_eq <http://sofdem.github.io/gccat/gccat/Clex_lesseq.html>`_ in the Global Constraint Catalog.

**Implementation based on**: :cite:`FrischHKMW02`.

**API**:  ::

    Constraint lex_less_eq(IntVar[] VARS1, IntVar[] VARS2)

.. admonition:: Example

    .. literalinclude:: /../../choco-samples/src/test/java/org/chocosolver/docs/IntConstraintExamples.java
          :language: java
          :lines: 469-472,474
          :emphasize-lines: 472
          :linenos:

   Some solutions of the problems are:

   - `X[0] = -1, X[1] = -1, X[2] = -1, Y[0] = 1, Y[1] = 1, Y[2] = 1`
   - `X[0] = 1, X[1] = -1, X[2] = -1, Y[0] = 1, Y[1] = 1, Y[2] = 1`
   - `X[0] = 0, X[1] = 0, X[2] = 0, Y[0] = 2, Y[1] = 1, Y[2] = 2`
   - `X[0] = 1, X[1] = 1, X[2] = 1, Y[0] = 2, Y[1] = 2, Y[2] = 2`

.. _51_icstr_max:

maximum
=======

The `maximum` constraints involves a set of integer variables and a third party integer variable, either:

- two integer variables `VAR1` and `VAR2` and an integer variable `MAX`, it ensures that `MAX`= maximum(`VAR1`, `VAR2`).
- or an array of integer variables `VARS` and an integer variable `MAX`, it ensures that `MAX` is the maximum value of the collection of domain variables `VARS`.
- or an array of boolean variables `BVARS` and a booean variable `MAX`, it ensures that `MAX` is the maximum value of the collection of boolean variables `BVARS`.

**See also**: `maximum <http://sofdem.github.io/gccat/gccat/Cmaximum.html>`_ in the Global Constraint Catalog.

**API**:  ::

    Constraint maximum(IntVar MAX, IntVar VAR1, IntVar VAR2)
    Constraint maximum(IntVar MAX, IntVar[] VARS)
    Constraint maximum(BoolVar MAX, BoolVar[] VARS)

.. admonition:: Example

    .. literalinclude:: /../../choco-samples/src/test/java/org/chocosolver/docs/IntConstraintExamples.java
          :language: java
          :lines: 188-192,194
          :emphasize-lines: 192
          :linenos:

    The solutions of the problem are :

        - `MAX = 2, Y = -1, Z = 2`
        - `MAX = 2, Y = 0, Z = 2`
        - `MAX = 2, Y = 1, Z = 2`
        - `MAX = 3, Y = -1, Z = 3`
        - `MAX = 3, Y = 0, Z = 3`
        - `MAX = 3, Y = 1, Z = 3`

.. _51_icstr_mdd:

mddc
====

A constraint which restricts the values a variable can be assigned to the solutions encoded with a multi-valued decision diagram.

**Implementation based on**: :cite:`ChengY08`.

**API**:  ::

    Constraint mddc(IntVar[] VARS, MultivaluedDecisionDiagram MDD)

.. admonition:: Example

    .. literalinclude:: /../../choco-samples/src/test/java/org/chocosolver/docs/IntConstraintExamples2.java
          :language: java
          :lines: 52-58,60
          :emphasize-lines: 59
          :linenos:

    The solutions of the problem are :

        - `X[0] = 0, X[1] = -1`
        - `X[0] = 0, X[1] = 1`,
        - `X[0] = 1, X[1] = -1`


.. _51_icstr_mem:

member
======

A constraint which restricts the values a variable can be assigned to with respect to either:

- a given list of values, it involves a integer variable `VAR` and an array of distinct values `TABLE`. It ensures that `VAR` takes its values in `TABLE`.
- or two bounds (included), it involves a integer variable `VAR` and two integer `LB` and  `UB`. It ensures that `VAR` takes its values in [`LB`, `UB`].

**API**:  ::

    Constraint member(IntVar VAR, int[] TABLE)
    Constraint member(IntVar VAR, int LB, int UB)

.. admonition:: Example 1

    .. literalinclude:: /../../choco-samples/src/test/java/org/chocosolver/docs/IntConstraintExamples.java
          :language: java
          :lines: 64-66,68
          :emphasize-lines: 66
          :linenos:

    The solutions of the problem are :

        - `X = 1`
        - `X = 2`

.. admonition:: Example 2

    .. literalinclude:: /../../choco-samples/src/test/java/org/chocosolver/docs/IntConstraintExamples.java
          :language: java
          :lines: 73-75,77
          :emphasize-lines: 75
          :linenos:

 The solutions of the problem are :

     - `X = 2`
     - `X = 3`
     - `X = 4`

.. _51_icstr_min:

minimum
=======

The `minimum` constraints involves a set of integer variables and a third party integer variable, either:

- two integer variables `VAR1` and `VAR2` and an integer variable `MIN`, it ensures that `MIN`= minimum(`VAR1`, `VAR2`).
- or an array of integer variables `VARS` and an integer variable `MIN`, it ensures that `MIN` is the minimum value of the collection of domain variables `VARS`.
- or an array of boolean variables `BVARS` and a booean variable `MIN`, it ensures that `MIN` is the minimum value of the collection of boolean variables `BVARS`.

**See also**: `minimum <http://sofdem.github.io/gccat/gccat/Cminimum.html>`_ in the Global Constraint Catalog.

**API**:  ::
    Constraint minimum(IntVar MIN, IntVar VAR1, IntVar VAR2)
    Constraint minimum(IntVar MIN, IntVar[] VARS)
    Constraint minimum(BoolVar MIN, BoolVar[] VARS)

.. admonition:: Example

    .. literalinclude:: /../../choco-samples/src/test/java/org/chocosolver/docs/IntConstraintExamples.java
          :language: java
          :lines: 199-203,205
          :emphasize-lines: 203
          :linenos:

    The solutions of the problem are :

        - `MIN = 2, Y = -1, Z = 2`
        - `MIN = 2, Y = 0, Z = 2`
        - `MIN = 2, Y = 1, Z = 2`
        - `MIN = 3, Y = -1, Z = 3`
        - `MIN = 3, Y = 0, Z = 3`
        - `MIN = 3, Y = 1, Z = 3`

.. _51_icstr_mod:

mod
===

The `mod` constraints involves three variables `X`, `Y` and `Z`.
It ensures that `X` :math:`\mod` `Y` = `Z`.
There is no native constraint for `mod`, so this is reformulated with the help of additional variables.

The API is : ::

    Constraint mod(IntVar X, IntVar Y, IntVar Z)

.. admonition:: Example

    .. literalinclude:: /../../choco-samples/src/test/java/org/chocosolver/docs/IntConstraintExamples.java
          :language: java
          :lines: 210-214,216
          :emphasize-lines: 214
          :linenos:

 The solutions of the problem are :

     - `X = 2, Y = 3, Z = 2`
     - `X = 2, Y = 4, Z = 2`
     - `X = 3, Y = 2, Z = 1`
     - `X = 3, Y = 4, Z = 3`
     - `X = 4, Y = 3, Z = 1`

.. _51_icstr_mcreg:

multicost_regular
=================

The `multicost_regular` constraint involves:

 - an array of integer variables `VARS`,
 - an array of integer variables `CVARS` and
 - a cost automaton `CAUTOMATON`.

It ensures that the assignment of a sequence of variables `VARS` is recognized by `CAUTOMATON`, a deterministic finite automaton,
and that the sum of the cost array associated to each assignment is bounded by the `CVARS`.
This version allows to specify different costs according to the automaton state at which the assignment occurs (i.e. the transition starts).

The `CAUOTMATON` can be defined using the `org.chocosolver.solver.constraints.nary.automata.FA.CostAutomaton` either:

- by creating a ``CostAutomaton``: once created, states should be added, then initial and final states are defined and finally, transitions are declared.
- or by first creating a ``FiniteAutomaton`` and then creating a matrix of costs and finally calling one of the following API from ``CostAutomaton``:

    + ``ICostAutomaton makeMultiResources(IAutomaton pi, int[][][] layer_value_resource, int[] infs, int[] sups)``
    + ``ICostAutomaton makeMultiResources(IAutomaton pi, int[][][][] layer_value_resource_state, int[] infs, int[] sups)``
    + ``ICostAutomaton makeMultiResources(IAutomaton auto, int[][][][] c, IntVar[] z)``
    + ``ICostAutomaton makeMultiResources(IAutomaton auto, int[][][] c, IntVar[] z)``

 The other API of ``CostAutomaton`` (``makeSingleResource(...)``) are dedicated to the `cost_regular` constraint.

**Implementation based on**: :cite:`MenanaD09`.

**API**:  ::

    Constraint multicost_regular(IntVar[] VARS, IntVar[] CVARS, ICostAutomaton CAUTOMATON)

.. admonition:: Example

     *TBD*

.. _51_icstr_nmem:

not_member
==========

A constraint which prevents a variable to be assigned to some values defined by either:

- a list of values, it involves a integer variable `VAR` and an array of distinct values `TABLE`. It ensures that `VAR` does not take its values in `TABLE`.
- two bounds (included), it involves a integer variable `VAR` and two integer `LB` and  `UB`. It ensures that `VAR` does not take its values in [`LB`, `UB`].

The constraint

**API**:  ::

    Constraint not_member(IntVar VAR, int[] TABLE)
    Constraint not_member(IntVar VAR, int LB, int UB)

.. admonition:: Example 1

    .. literalinclude:: /../../choco-samples/src/test/java/org/chocosolver/docs/IntConstraintExamples.java
          :language: java
          :lines: 82-84,86
          :emphasize-lines: 84
          :linenos:

    The solutions of the problem are :

        - `X = 3`
        - `X = 4`

.. admonition:: Example

    .. literalinclude:: /../../choco-samples/src/test/java/org/chocosolver/docs/IntConstraintExamples.java
          :language: java
          :lines: 92-94,96
          :emphasize-lines: 94
          :linenos:

    The solution of the problem is :

     - `X = 1`

.. _51_icstr_nva:

nvalues
=======

The `nvalues` constraint involves:

- an array of integer variables `VARS` and
- an integer variable `NVALUES`.

The constraint ensures that `NVALUES` is the number of distinct values assigned to the variables of the `VARS` array.
This constraint is a combination of the `atleast_nvalues` and `atmost_nvalues` constraints.

This constraint is not a built-in constraint and is based on various propagators.

**See also**: `nvalues <http://sofdem.github.io/gccat/gccat/Cnvalues.html>`_ in the Global Constraint Catalog.

**Implementation based on**: `atleast_nvalues` and `atmost_nvalues`.

**API**:  ::

    Constraint[] nvalues(IntVar[] VARS, IntVar NVALUES)

.. admonition:: Example

    .. literalinclude:: /../../choco-samples/src/test/java/org/chocosolver/docs/IntConstraintExamples.java
          :language: java
          :lines: 508-511,513
          :emphasize-lines: 511
          :linenos:

    Some solutions of the problem are :

     - `VS[0] = 0 VS[1] = 0 VS[2] = 0 VS[3] = 0 N = 1`
     - `VS[0] = 0 VS[1] = 0 VS[2] = 0 VS[3] = 1 N = 2`
     - `VS[0] = 0 VS[1] = 1 VS[2] = 2 VS[3] = 2 N = 3`

.. _51_icstr_pat:

path
====

The `path` constraint involves:

- an array of integer variables `VARS`,
- an integer variable `START`,
- an integer variable `END` and
- an integer `OFFSET`.

It ensures that the elements of `VARS` define a covering path from `START` to `END`,
where `VARS[i] = OFFSET + j` means that `j` is the successor of `i`.
Moreover, `VARS[END-OFFSET]` = \|`VARS` \|+ `OFFSET`.

The constraint relies on the `circuit` propagators.

**See also**: `path <http://sofdem.github.io/gccat/gccat/Cpath.html>`_ in the Global Constraint Catalog.

**Implementation based on**: `circuit`.

**API**:  ::

    Constraint[] path(IntVar[] VARS, IntVar START, IntVar END, int OFFSET)

.. admonition:: Example

    .. literalinclude:: /../../choco-samples/src/test/java/org/chocosolver/docs/IntConstraintExamples.java
          :language: java
          :lines: 518-522,524
          :emphasize-lines: 522
          :linenos:

    Some solutions of the problem are :

     - `VS[0] = 1, VS[1] = 2, VS[2] = 3, VS[3] = 4, S = 0, E = 3`
     - `VS[0] = 1, VS[1] = 3, VS[2] = 0, VS[3] = 4, S = 2, E = 3`
     - `VS[0] = 3, VS[1] = 4, VS[2] = 0, VS[3] = 1, S = 2, E = 1`
     - `VS[0] = 4, VS[1] = 3, VS[2] = 1, VS[3] = 0, S = 2, E = 0`

.. _51_icstr_reg:

regular
=======

The `regular` constraint involves:

- an array of integer variables `VARS` and
- a deterministic finite automaton `AUTOMATON`.

It enforces the sequences of `VARS` to be a word recognized by `AUTOMATON`.

There are various ways to declare the automaton:

- create a ``FiniteAutomaton`` and add states, initial and final ones and transitions (see ``FiniteAutomaton`` API for more details),
- create a ``FiniteAutomaton`` with a regexp as argument.

**Beware**: ``FiniteAutomaton`` only handles values between 0 and 65535, because it relies on ``java.Character``.

**Implementation based on**: :cite:`Pesant04`.

**API**:  ::

    Constraint regular(IntVar[] VARS, IAutomaton AUTOMATON)

.. admonition:: Example

    .. literalinclude:: /../../choco-samples/src/test/java/org/chocosolver/docs/IntConstraintExamples.java
          :language: java
          :lines: 529-532,534
          :emphasize-lines: 531-532
          :linenos:

    The solutions of the problem are :

     - `CS[0] = 1, CS[1] = 3, CS[2] = 3, CS[3] = 4`
     - `CS[0] = 1, CS[1] = 3, CS[2] = 3, CS[3] = 5`
     - `CS[0] = 2, CS[1] = 3, CS[2] = 3, CS[3] = 4`
     - `CS[0] = 2, CS[1] = 3, CS[2] = 3, CS[3] = 5`


.. _51_icstr_sca:

scalar
======

The `scalar` constraint involves:

- an array of integer variables `VARS`,
- an array of integer `COEFFS`,
- an optional operator `OPERATOR` and
- an integer variable `SCALAR`.

It ensures that `sum(VARS[i]*COEFFS[i]) OPERATOR SCALAR`; where `OPERATOR` must be chosen from ``{"=", "!=", ">","<",">=","<="}``.
The `scalar` constraint filters on bounds only.
The constraint suppress variables with coefficients set to 0, recognizes `sum` (when all coefficients are equal to `-1`, or all equal to `-1`),
and enables, under certain conditions, to reformulate the constraint with a `table` constraint providint AC filtering algorithm.

**See also**: `scalar_product <http://sofdem.github.io/gccat/gccat/Cscalar_product.html>`_ in the Global Constraint Catalog.

**Implementation based on**: :cite:`HarveyS02`.

**API**: ::

    Constraint scalar(IntVar[] VARS, int[] COEFFS, IntVar SCALAR)
    Constraint scalar(IntVar[] VARS, int[] COEFFS, String OPERATOR, IntVar SCALAR)


.. admonition:: Example

    .. literalinclude:: /../../choco-samples/src/test/java/org/chocosolver/docs/IntConstraintExamples.java
          :language: java
          :lines: 539-543,545
          :emphasize-lines: 543
          :linenos:

    Some solutions of the problem are :

     - `CS[0] = 1, CS[1] = 1, CS[2] = 1, CS[3] = 1, R = 10`
     - `CS[0] = 1, CS[1] = 2, CS[2] = 3, CS[3] = 1, R = 18`
     - `CS[0] = 1, CS[1] = 4, CS[2] = 2, CS[3] = 1, R = 19`
     - `CS[0] = 1, CS[1] = 2, CS[2] = 1, CS[3] = 3, R = 20`

.. _51_icstr_sor:

sort
====

The `sort` constraint involves two arrays of integer variables `VARS` and `SORTEDVARS`.
It ensures that the variables of `SORTEDVARS` correspond to the variables of `VARS` according to a permutation.
Moreover, the variable of `SORTEDVARS` are sorted in increasing order.

**See also**: `sort <http://sofdem.github.io/gccat/gccat/Csort.html>`_ in the Global Constraint Catalog.

**Implementation based on**: :cite:`MehlhornT00`.

**API**: ::

    Constraint sort(IntVar[] VARS, IntVar[] SORTEDVARS)

.. admonition:: Example

    .. literalinclude:: /../../choco-samples/src/test/java/org/chocosolver/docs/IntConstraintExamples.java
          :language: java
          :lines: 550-553,555
          :emphasize-lines: 553
          :linenos:

    Some solutions of the problem are :

     - `X[0] = 0, X[1] = 0, X[2] = 0, Y[0] = 0, Y[1] = 0, Y[2] = 0`
     - `X[0] = 1, X[1] = 0, X[2] = 2, Y[0] = 0, Y[1] = 1, Y[2] = 2`
     - `X[0] = 2, X[1] = 1, X[2] = 0, Y[0] = 0, Y[1] = 1, Y[2] = 2`
     - `X[0] = 2, X[1] = 1, X[2] = 2, Y[0] = 1, Y[1] = 2, Y[2] = 2`

.. _51_icstr_squa:

square
======

The ``square`` constraint involves two variables `VAR1` and `VAR2`.
It ensures that `VAR1` = `VAR2`:math:`^2`.

**API**:  ::

    Constraint square(IntVar VAR1, IntVar VAR2)

.. admonition:: Example

    .. literalinclude:: /../../choco-samples/src/test/java/org/chocosolver/docs/IntConstraintExamples.java
          :language: java
          :lines: 141-144,146
          :emphasize-lines: 144
          :linenos:

 The solutions of the problem are :

     - `X = 1, Y = -1`
     - `X = 0, Y = 0`
     - `X = 1, Y = 1`
     - `X = 4, Y = 2`

.. _51_icstr_scir:

subcircuit
==========

The `subcircuit` constraint involves:

- an array of integer variables `VARS`,
- an integer `OFFSET` and
- an integer variable `SUBCIRCUIT_SIZE`.

It ensures that the elements of `VARS` define a single circuit of `SUBCIRCUIT_SIZE` nodes where:

- `VARS[i] = OFFSET+j` means that `j` is the successor of `i`,
- `VARS[i] = OFFSET+i` means that `i` is not part of the circuit.

It also ensures that \| `{VARS[i]` :math:`\neq` `OFFSET+i}` \| = `SUBCIRCUIT_SIZE`.

**Implementation based on**: `circuit`.

**API**:  ::

    Constraint subcircuit(IntVar[] VARS, int OFFSET, IntVar SUBCIRCUIT_SIZE)

.. admonition:: Example

    .. literalinclude:: /../../choco-samples/src/test/java/org/chocosolver/docs/IntConstraintExamples.java
          :language: java
          :lines: 560-563,565
          :emphasize-lines: 563
          :linenos:

 Some solutions of the problem are :

     - `NS[0] = 0, NS[1] = 1, NS[2] = 2, NS[3] = 4, NS[4] = 3, SI = 2`
     - `NS[0] = 4, NS[1] = 1, NS[2] = 2, NS[3] = 3, NS[4] = 0, SI = 2`
     - `NS[0] = 1, NS[1] = 2, NS[2] = 0, NS[3] = 3, NS[4] = 4, SI = 3`
     - `NS[0] = 3, NS[1] = 1, NS[2] = 2, NS[3] = 4, NS[4] = 0, SI = 3`

.. _51_icstr_spat:

subpath
=======

The `subpath` constraint involves:

- an array of integer variables `VARS`,
- an integer variable `START`,
- an integer variable `END`,
- an integer `OFFSET` and
- an integer variable `SIZE`.

It ensures that the elements of `VARS` define a path of `SIZE` vertices, leading from `START` to `END` where:

+ `VARS[i] = OFFSET+j` means that `j` is the successor of `i`,
+ `VARS[i] = OFFSET+i` means that vertex `i` is excluded from the path.

Moreover, `VARS[END-OFFSET]` = \| `VARS` \| +`OFFSET`.

**See also**: `subpath <http://sofdem.github.io/gccat/gccat/Cpath_from_to.html>`_ in the Global Constraint Catalog.

**Implementation based on**: `path, circuit`.

**API**:  ::

    Constraint[] subpath(IntVar[] VARS, IntVar START, IntVar END, int OFFSET, IntVar SIZE)

.. admonition:: Example

    .. literalinclude:: /../../choco-samples/src/test/java/org/chocosolver/docs/IntConstraintExamples.java
          :language: java
          :lines: 570-575,577
          :emphasize-lines: 575
          :linenos:

 Some solutions of the problem are :

     - `VS[0] = 1, VS[1] = 4, VS[2] = 2, VS[3] = 3, S = 0, E = 1, SI = 2`
     - `VS[0] = 4, VS[1] = 1, VS[2] = 2, VS[3] = 0, S = 3, E = 0, SI = 2`
     - `VS[0] = 3, VS[1] = 1, VS[2] = 4, VS[3] = 2, S = 0, E = 2, SI = 3`
     - `VS[0] = 0, VS[1] = 2, VS[2] = 4, VS[3] = 1, S = 3, E = 2, SI = 3`


.. _51_icstr_sum:

sum
===

The `sum` constraint involves:

- an array of integer (or boolean) variables `VARS`,
- an optional operator `OPERATOR` and
- an integer variable `SUM`.

It ensures that `sum(VARS[i]) OPERATOR SUM`; where operator must be chosen among ``{"=", "!=", ">","<",">=","<="}``.
If no operator is defined, ``"="`` is set by default.
Note that when the operator differs from ``"="``, an intermediate variable is declared and an `arithm` constraint is returned.
For performance reasons, a specialization for boolean variables is provided.

**See also**: `scalar_product <http://sofdem.github.io/gccat/gccat/Cscalar_product.html>`_ in the Global Constraint Catalog.

**Implementation based on**:  :cite:`HarveyS02`.

**API**:  ::

    Constraint sum(IntVar[] VARS, IntVar SUM)
    Constraint sum(IntVar[] VARS, String OPERATOR, IntVar SUM)
    Constraint sum(BoolVar[] VARS, IntVar SUM)
    Constraint sum(BoolVar[] VARS, String OPERATOR, IntVar SUM)

.. admonition:: Example

    .. literalinclude:: /../../choco-samples/src/test/java/org/chocosolver/docs/IntConstraintExamples.java
          :language: java
          :lines: 582-585,587
          :emphasize-lines: 585
          :linenos:

 Some solutions of the problem are :

     - `VS[0] = 0 VS[1] = 0 VS[2] = 0 VS[3] = 0 SU = 2`
     - `VS[0] = 0 VS[1] = 0 VS[2] = 0 VS[3] = 2 SU = 2`
     - `VS[0] = 0 VS[1] = 0 VS[2] = 0 VS[3] = 3 SU = 3`
     - `VS[0] = 1 VS[1] = 1 VS[2] = 0 VS[3] = 0 SU = 3`

.. _51_icstr_tab:

table
=====

The ``table`` constraint involves either:

- two variables `VAR1` and `VAR2`, a list of pair of values, named `TUPLES` and an algorithm `ALGORITHM`.
- or an array of variables `VARS`, a list of tuples of values, named `TUPLES` and an algorithm `ALGORITHM`.

It is an extensional constraint enforcing, most of the time, arc-consistency.

When only two variables are involved, the available algorithms are:

- ``"AC2001"``: applies the AC2001 algorithm,
- ``"AC3"``: applies the AC3 algorithm,
- ``"AC3rm"``: applies the AC3rm algorithm,
- ``"AC3bit+rm"``: (default) applies the AC3bit+rm algorithm,
- ``"FC"``: applies the forward checking algorithm.


When more than two variables are involved, the available algorithms are:

- ``"GAC2001"``: applies the GAC2001 algorithm,
- ``"GAC2001+"``: applies the GAC2001 algorithm for allowed tuples only,
- ``"GAC3rm"``: applies the GAC3 algorithm,
- ``"GAC3rm+"``: (default) applies the GAC3rm algorithm for allowed tuples only,
- ``"GACSTR+"``: applies the GAC version STR for allowed tuples only,
- ``"STR2+"``: applies the GAC STR2 algorithm for allowed tuples only,
- ``"FC"``: applies the forward checking algorithm.


**Implementation based on**: :cite:`tbd`.

**API**:  ::

    Constraint table(IntVar VAR1, IntVar VAR2, Tuples TUPLES, String ALGORITHM)
    Constraint table(IntVar[] VARS, Tuples TUPLES, String ALGORITHM)

.. admonition:: Example

    .. literalinclude:: /../../choco-samples/src/test/java/org/chocosolver/docs/IntConstraintExamples.java
          :language: java
          :lines: 151-159,161
          :emphasize-lines: 159
          :linenos:

 The solutions of the problem are :

     - `X = 1, Y = 1`
     - `X = 4, Y = 2`

.. _51_icstr_tim:

times
=====

The `times` constraints involves either:

- three variables `X`, `Y` and `Z`. It ensures that `X` :math:`\times` `Y` = `Z`.
- or two variables `X` and `Z` and a constant `y`. It ensures that `X` :math:`\times` `y` = `Z`.

The propagator of the `times` constraint filters on bounds only.
If the option is enabled and under certain condition, the `times` constraint may be redefined with a `table` constraint, providing a better filtering algorithm.

The API are : ::

    Constraint times(IntVar X, IntVar Y, IntVar Z)
    Constraint times(IntVar X, int Y, IntVar Z)

.. admonition:: Example

    .. literalinclude:: /../../choco-samples/src/test/java/org/chocosolver/docs/IntConstraintExamples.java
          :language: java
          :lines: 221-225,227
          :emphasize-lines: 225
          :linenos:

 The solution of the problem is :

     - `X = 2 Y = 3 Z = 6`

.. _51_icstr_tree:

tree
====

The `tree` constraint involves:

- an array of integer variables `SUCCS`,
- an integer variable `NBTREES` and
- an integer `OFFSET`.

It partitions the `SUCCS` variables into `NBTREES` (anti) arborescences:

- `SUCCS[i] = OFFSET+j` means that `j` is the successor of `i`,
- `SUCCS[i] = OFFSET+i` means that `i` is a root.


**See also**: `tree <http://sofdem.github.io/gccat/gccat/Ctree.html>`_ in the Global Constraint Catalog.

**Implementation based on**: :cite:`FagesL11`.

**API**:  ::

    Constraint tree(IntVar[] SUCCS, IntVar NBTREES, int OFFSET)

.. admonition:: Example

    .. literalinclude:: /../../choco-samples/src/test/java/org/chocosolver/docs/IntConstraintExamples.java
          :language: java
          :lines: 592-595,599
          :emphasize-lines: 595
          :linenos:

 Some solutions of the problem are :

     - `VS[0] = 0, VS[1] = 1, VS[2] = 1, VS[3] = 1, NT = 2`
     - `VS[0] = 1, VS[1] = 1, VS[2] = 2, VS[3] = 1, NT = 2`
     - `VS[0] = 2, VS[1] = 0, VS[2] = 2, VS[3] = 3, NT = 2`
     - `VS[0] = 0, VS[1] = 3, VS[2] = 2, VS[3] = 3, NT = 3`
     - `VS[0] = 3, VS[1] = 1, VS[2] = 2, VS[3] = 3, NT = 3`


.. _51_icstr_tru:

TRUE
====

The `TRUE` constraint is always satisfied. It should only be used with ``LogicalFactory``.


.. _51_icstr_tsp:

tsp
===

The `tsp` constraint involves:

- an array of integer variables `SUCCS`,
- an integer variable `COST` and
- a matrix of integers `COST_MATRIX`.

It formulates the Travelling Salesman Problem: the variables `SUCCS` form a hamiltonian circuit of value `COST`.
Going from `i` to `j`, `SUCCS[i] = j`, costs `COST_MATRIX[i][j]`.

This constraint is not a built-in constraint and is based on various propagators.

The filtering power of this constraint remains limited. For stronger filtering, use the `choco-graph` extension
(https://github.com/chocoteam/choco-graph/releases/tag/choco-graph-3.2.1) which includes powerful cost-based filtering.


**API**:  ::

    Constraint[] tsp(IntVar[] SUCCS, IntVar COST, int[][] COST_MATRIX)


.. admonition:: Example

    .. literalinclude:: /../../choco-samples/src/test/java/org/chocosolver/docs/IntConstraintExamples.java
          :language: java
          :lines: 602-606,608
          :emphasize-lines: 606
          :linenos:

 The solutions of the problem are :

     - `VS[0] = 2, VS[1] = 0, VS[2] = 3, VS[3] = 1, CO = 8`
     - `VS[0] = 3, VS[1] = 0, VS[2] = 1, VS[3] = 2, CO = 10`
     - `VS[0] = 1, VS[1] = 2, VS[2] = 3, VS[3] = 0, CO = 10`
     - `VS[0] = 3, VS[1] = 2, VS[2] = 0, VS[3] = 1, CO = 14`
     - `VS[0] = 1, VS[1] = 3, VS[2] = 0, VS[3] = 2, CO = 8`
     - `VS[0] = 2, VS[1] = 3, VS[2] = 1, VS[3] = 0, CO = 14`


.. _51_scstr_main:

******************************
Constraints over set variables
******************************

.. _51_scstr_alldif:

all_different
=============

The `all_different` constraint involves an array of set variables `SETS`.
It ensures that sets in `SETS` are all different (not necessarily disjoint).
Note that there cannot be more than two empty sets.

**API**:  ::

    Constraint all_different(SetVar[] SETS)

.. _51_scstr_alldis:

all_disjoint
============

The `all_disjoint` constraint involves an array of set variables `SETS`.
It ensures that all sets from `SETS` are disjoint.
Note that there can be multiple empty sets.

**API**:  ::

    Constraint all_disjoint(SetVar[] SETS)


.. _51_scstr_alleq:

all_equal
=========

The `all_equal` constraint involves an array of set variables `SETS`.
It ensures that sets in `SETS` are all equal.

**API**:  ::

    Constraint all_equal(SetVar[] SETS)

.. _51_scstr_bcha:

bool_channel
============

The `bool_channel` constraint involves:

- an array of boolean variables `BOOLEANS`,
- a set variable `SET` and
- an integer `OFFSET`.

It channels `BOOLEANS` and `SET` such that : `i` :math:`\in` SET :math:`\Leftrightarrow` `BOOLEANS[i-OFFSET] = 1`.

**API**:  ::

    Constraint bool_channel(BoolVar[] BOOLEANS, SetVar SET, int OFFSET)

.. _51_scstr_card:

cardinality
===========

The `cardinality` constraint involves:

- a set variable `SET` and
- an integer variable `CARD`.

It ensures that \| `SET_VAR` \| = `CARD`.

The API is : ::

    Constraint cardinality(SetVar SET, IntVar CARD)

.. _51_scstr_dis:

disjoint
========

The `disjoint` constraint involves two set variables `SET_1` and `SET_2`.
It ensures that `SET_1` and `SET_2` are disjoint, that is, they cannot contain the same element.
Note that they can be both empty.

**API**:  ::

    Constraint disjoint(SetVar SET_1, SetVar SET_2)

.. _51_scstr_elm:

element
=======

The `element` constraint involves:

- an integer variable `INDEX`,
- and array of set variables `SETS`,
- an integer `OFFSET` and
- a set variable `SET`.

It ensures that `SETS[INDEX-OFFSET] = SET`.

**API**:  ::

    Constraint element(IntVar INDEX, SetVar[] SETS, int OFFSET, SetVar SET)

.. _51_scstr_icha:

int_channel
===========

The `int_channel` constraint involves:

- an array of set variables `SETS`,
- an array of integer variables `INTEGERS`,
- two integers `OFFSET_1` and `OFFSET_2`.

It ensures that: `x` :math:`\in` `SETS[y-OFFSET_1]` :math:`\Leftrightarrow` `INTEGERS[x-OFFSET_2] = y`.

The API is : ::

    Constraint int_channel(SetVar[] SETS, IntVar[] INTEGERS, int OFFSET_1, int OFFSET_2)

.. _51_scstr_intvu:

int_values_union
================

The `int_values_union` constraint involves:

- an array of integer variables `VARS` and
- a set variable `VALUES`

It ensures that: :math:`\text{VALUES} = \text{VARS}_1 \cup \text{VARS}_2 \cup \ldots \cup \text{VARS}_n`.

The API is : ::

    Constraint int_values_union(IntVar[] VARS, SetVar VALUES)

.. _51_scstr_int:

intersection
============

The `intersection` constraint involves:

- an array of set variables `SETS` and
- a set variable `INTERSECTION`.

It ensures that `INTERSECTION` is the intersection of the sets `SETS`.

The API is : ::

    Constraint intersection(SetVar[] SETS, SetVar INTERSECTION)

.. _51_scstr_inv:

inverse_set
===========

The `inverse_set` constraint involves:

 - an array of set variables `SETS`,
 - an array of set variable `INVERSE_SETS` and
 - two integers `OFFSET_1` and `OFFSET_2`.

It ensures that `x :math:`\in` `SETS[y-OFFSET_1]` :math:`\Leftrightarrow` y :math:`\in` `INVERSE_SETS[x-OFFSET_2]`.

**API**:  ::

    Constraint inverse_set(SetVar[] SETS, SetVar[] INVERSE_SETS, int OFFSET_1, int OFFSET_2)

.. _51_scstr_max:

max
===

The `max` constraint involves:

- either:

    + a set variable `SET`,
    + an integer variable `MAX_ELEMENT_VALUE` and
    + a boolean `NOT_EMPTY`.

    It ensures that `MIN_ELEMENT_VALUE` is equal to  the maximum element of `SET`.

- or:

    + a set variable `SET`,
    + an array of integer `WEIGHTS`,
    + an integer `OFFSET`,
    + an integer variable `MAX_ELEMENT_VALUE` and
    + a boolean `NOT_EMPTY`.

    It ensures that `max(WEIGHTS[i-OFFSET] | i in INDEXES) = MAX_ELEMENT_VALUE`.

The boolean `NOT_EMPTY` set to `true` states that `INDEXES` cannot be empty.

**API**:  ::

    Constraint max(SetVar SET, IntVar MAX_ELEMENT_VALUE, boolean NOT_EMPTY)
    Constraint max(SetVar INDEXES, int[] WEIGHTS, int OFFSET, IntVar MAX_ELEMENT_VALUE, boolean NOT_EMPTY)

.. _51_scstr_mem:

member
======

The `member` constraint involves:

- either:

    + an array of set variables `SETS` and
    + a set variable `SET`.

    It ensures that `SET` belongs to `SETS`.

- or:

    + an integer variable `INTEGER` and
    + a set variable `SET`.

    It ensures that `INTEGER` is included in `SET`.

**API**:  ::

        Constraint member(SetVar[] SETS, SetVar SET)
        Constraint member(IntVar INTEGER, SetVar SET)

.. _51_scstr_nme:

not_member
==========

The `not_member` constraint involves:

    + an integer variable `INTEGER` and
    + a set variable `SET`.

    It ensures that `INTEGER` is not included in `SET`.

**API**:  ::

        Constraint not_member(IntVar INTEGER, SetVar SET)

.. _51_scstr_min:

min
===

The `min` constraint involves:

- either:

    + a set variable `SET`,
    + an integer variable `MIN_ELEMENT_VALUE` and
    + a boolean `NOT_EMPTY`.

    It ensures that `MIN_ELEMENT_VALUE` is equal to  the minimum element of `SET`.

- or:

    + a set variable `SET`,
    + an array of integer `WEIGHTS`,
    + an integer `OFFSET`,
    + an integer variable `MAX_ELEMENT_VALUE` and
    + a boolean `NOT_EMPTY`.

    It ensures that `min(WEIGHTS[i-OFFSET] | i in INDEXES) = MIN_ELEMENT_VALUE`.

The boolean `NOT_EMPTY` set to `true` states that `INDEXES` cannot be empty.

**API**:  ::

    Constraint min(SetVar SET, IntVar MIN_ELEMENT_VALUE, boolean NOT_EMPTY)
    Constraint min(SetVar INDEXES, int[] WEIGHTS, int OFFSET, IntVar MIN_ELEMENT_VALUE, boolean NOT_EMPTY)


.. _51_scstr_nbe:

nbEmpty
=======

The `nbEmpty` constraint involves:

- an array of set variables `SETS` and
- an integer variable `NB_EMPTY_SETS`.

It restricts the number of empty sets in `SETS` to be equal `NB_EMPTY_SET`.

**API**:  ::

    Constraint nbEmpty(SetVar[] SETS, IntVar NB_EMPTY_SETS)

.. _51_scstr_note:

notEmpty
========

The `notEmpty` constraint involves a set variable `SET`.

It prevents `SET` to be empty.

**API**:  ::

    Constraint notEmpty(SetVar SET)


.. _51_scstr_off:

offSet
======

The `offset` constraint involves:

- two set variables `SET_1` and `SET_2` and
- an integer `OFFSET`.

It ensures that to any value `x` in `SET_1`, the value `x+OFFSET` is in `SET_2` (and reciprocally).

**API**:  ::

    Constraint offSet(SetVar SET_1, SetVar SET_2, int OFFSET)


.. _51_scstr_part:

partition
=========

The `partition` constraint involves:

- an array of set variables `SETS` and
- a set variable `UNIVERSE`.

It ensures that `UNVIVERSE` is partitioned in disjoint sets `SETS`.

**API**:  ::

    Constraint partition(SetVar[] SETS, SetVar UNIVERSE)

.. _51_scstr_sse:

subsetEq
========

The `subsetEq` constraint involves an array of set variables `SETS`.
It ensures that `i<j` :math:`\Leftrightarrow` `SET_VARS[i]` :math:`\subseteq` `SET_VARS[j]`.

The API is : ::

    Constraint subsetEq(SetVar[] SETS)

.. _51_scstr_sum:

sum
===

The `sum` constraint involves:

- a set variables `INDEXES`,
- an array of integer `WEIGHTS`,
- an integer `OFFSET`,
- an integer variable `SUM` and
- a boolean `NOT_EMPTY`.

The constraint ensures that `sum(WEIGHTS[i-OFFSET] | i in INDEXES) = SUM`.
The boolean `NOT_EMPTY` set to `true` states that `INDEXES` cannot be empty.

**API**:  ::

    Constraint sum(SetVar INDEXES, int[] WEIGHTS, int OFFSET, IntVar SUM, boolean NOT_EMPTY)

.. _51_scstr_sym:

symmetric
=========

The `symmetric` constraint involves:

- an array of set variables `SETS` and
- an integer `OFFSET`.

It ensures that: `x` :math:`\in`  `SETS[y-OFFSET]` :math:`\Leftrightarrow` `y` :math:`\in` `SETS[x-OFFSET]`.

**API**:  ::

    Constraint symmetric(SetVar[] SETS, int OFFSET)

.. _51_scstr_uni:

union
=====

The `union` constraint involves:

- an array of set variables `SETS` and
- a set variable `UNION`.

It ensures that `SET_UNION` is equal to the union if the sets in `SET_VARS`.

The API is : ::

    Constraint union(SetVar[] SETS, SetVar UNION)


.. _51_rcstr_main:

*******************************
Constraints over real variables
*******************************

Real constraints are managed externally with :ref:`47_ibex`.
Due to the limited number of declaration possibilities, there is no factory for real constraints.
Indeed, posting a ``RealConstraint`` is enough.

The available constructors are: ::

    RealConstraint(String name, String functions, int option, RealVar... rvars)
    RealConstraint(String name, String functions, RealVar... rvars)
    RealConstraint(String functions, RealVar... rvars)

- ``name`` enables to set a name to the constraint.
- ``functions`` is a ``String`` which defines the list of functions to hold, separated with semi-colon ";".

A function is a declared using the following format:
    + the '{i}' tag defines a variable, where 'i' is an explicit index the array of variables ``rvars``,
    + one or more operators :'+,-,*,/,=,<,>,<=,>=,exp( ),ln( ),max( ),min( ),abs( ),cos( ), sin( ),...'

A complete list is available in the documentation of IBEX.
- ``rvars`` is the list of involved real variables.
- ``option`` is enable to state the propagation option (default is ``Ibex.COMPO``).

.. admonition:: Example

    .. literalinclude:: /../../choco-samples/src/test/java/org/chocosolver/docs/RealConstraintExamples.java
          :language: java
          :lines: 50-59,61
          :linenos:



.. _51_satsolver:

**********
Sat solver
**********

.. _51_lcstr_atmostnminusone:

addAtMostNMinusOne
==================

Add a clause to the SAT constraint whic states that:
`BOOLVARS`:math:`_1` :math:`+` `BOOLVARS`:math:`_2` :math:`+` ... :math:`+` `BOOLVARS`:math:`_n` :math:`<` `\|BOOLVARS\|`.


**API**: ::

    boolean addAtMostNMinusOne(BoolVar[] BOOLVARS)

.. admonition:: Example

    .. literalinclude:: /../../choco-samples/src/test/java/org/chocosolver/docs/SatConstraintExamples.java
          :language: java
          :lines: 50-52,54
          :emphasize-lines: 52
          :linenos:

    Some solutions of the problem are :

        - `BS[0] = 1, BS[1] = 1, BS[2] = 1, BS[3] = 0`
        - `BS[0] = 1, BS[1] = 0, BS[2] = 1, BS[3] = 0`
        - `BS[0] = 0, BS[1] = 1, BS[2] = 1, BS[3] = 1`
        - `BS[0] = 0, BS[1] = 0, BS[2] = 0, BS[3] = 1`
        - `BS[0] = 0, BS[1] = 0, BS[2] = 0, BS[3] = 0`

.. _51_lcstr_atmostone:

addAtMostOne
============

Add a clause to the SAT constraint whic states that:
`BOOLVARS`:math:`_1` :math:`+` `BOOLVARS`:math:`_2` :math:`+` ... :math:`+` `BOOLVARS`:math:`_n` :math:`\leq` 1.


**API**: ::

    boolean addAtMostOne(BoolVar[] BOOLVARS)

.. admonition:: Example

    .. literalinclude:: /../../choco-samples/src/test/java/org/chocosolver/docs/SatConstraintExamples.java
          :language: java
          :lines: 59-61,63
          :emphasize-lines: 61
          :linenos:

    The solutions of the problem are :

        - `BS[0] = 1, BS[1] = 0, BS[2] = 0, BS[3] = 0`
        - `BS[0] = 0, BS[1] = 1, BS[2] = 0, BS[3] = 0`
        - `BS[0] = 0, BS[1] = 0, BS[2] = 1, BS[3] = 0`
        - `BS[0] = 0, BS[1] = 0, BS[2] = 0, BS[3] = 1`
        - `BS[0] = 0, BS[1] = 0, BS[2] = 0, BS[3] = 0`

.. _51_lcstr_andarrayequalfalse:

addBoolAndArrayEqualFalse
=========================

Add a clause to the SAT constraint whic states that:
:math:`|not`(`BOOLVARS`:math:`_1` :math:`\land` `BOOLVARS`:math:`_2` :math:`\land` ... :math:`\land` `BOOLVARS`:math:`_n`).

**API**:

    boolean addBoolAndArrayEqualFalse(BoolVar[] BOOLVARS)

.. admonition:: Example

    .. literalinclude:: /../../choco-samples/src/test/java/org/chocosolver/docs/SatConstraintExamples.java
          :language: java
          :lines: 68-70,72
          :emphasize-lines: 70
          :linenos:

    Some solutions of the problem are :

        - `BS[0] = 1, BS[1] = 1, BS[2] = 1, BS[3] = 0`
        - `BS[0] = 1, BS[1] = 0, BS[2] = 1, BS[3] = 1`
        - `BS[0] = 1, BS[1] = 0, BS[2] = 0, BS[3] = 0`
        - `BS[0] = 0, BS[1] = 1, BS[2] = 0, BS[3] = 1`
        - `BS[0] = 0, BS[1] = 0, BS[2] = 0, BS[3] = 0`

.. _51_lcstr_andarrayeqvar:

addBoolAndArrayEqVar
====================

Add a clause to the SAT constraint which states that:
(`BOOLVARS`:math:`_1` :math:`\land` `BOOLVARS`:math:`_2` :math:`\land` ... :math:`\land` `BOOLVARS`:math:`_n`) :math:`\Leftrightarrow` `TARGET`.

**API**: ::

    boolean addBoolAndArrayEqVar(BoolVar[] BOOLVARS, BoolVar TARGET)

.. admonition:: Example

    .. literalinclude:: /../../choco-samples/src/test/java/org/chocosolver/docs/SatConstraintExamples.java
          :language: java
          :lines: 181-184,186
          :emphasize-lines: 184
          :linenos:

    Some solutions of the problem are :

        - `BS[0] = 1, BS[1] = 1, BS[2] = 1, BS[3] = 1 T = 1`
        - `BS[0] = 1, BS[1] = 1, BS[2] = 0, BS[3] = 1, T = 0`
        - `BS[0] = 0, BS[1] = 1, BS[2] = 0, BS[3] = 0, T = 0`
        - `BS[0] = 0, BS[1] = 0, BS[2] = 0, BS[3] = 0, T = 0`

.. _51_lcstr_andeqvar:

addBoolAndEqVar
===============

Add a clause to the SAT constraint which states that:
(`LEFT` :math:`\land` `RIGTH`) :math:`\Leftrightarrow` `TARGET`.

**API**: ::

    boolean addBoolAndEqVar(BoolVar LEFT, BoolVar RIGHT, BoolVar TARGET)

.. admonition:: Example

    .. literalinclude:: /../../choco-samples/src/test/java/org/chocosolver/docs/SatConstraintExamples.java
          :language: java
          :lines: 86-90,92
          :emphasize-lines: 90
          :linenos:

    The solutions of the problem are :

        - `L = 1, R = 1, T = 1`
        - `L = 1, R = 0, T = 0`
        - `L = 0, R = 1, T = 0`
        - `L = 0, R = 0, T = 0`

.. _51_lcstr_booleq:

addBoolEq
=========

Add a clause to the SAT constraint which states that the two boolean variables `LEFT` and `RIGHT` are equal.

**API**: ::

    boolean addBoolEq(BoolVar LEFT, BoolVar RIGHT)

.. admonition:: Example

    .. literalinclude:: /../../choco-samples/src/test/java/org/chocosolver/docs/SatConstraintExamples.java
          :language: java
          :lines: 97-100,102
          :emphasize-lines: 100
          :linenos:

    The solutions of the problem are :

        - `L = 1, R = 1`
        - `L = 0, R = 0`

.. _51_lcstr_booliseqvar:

addBoolIsEqVar
==============

Add a clause to the SAT constraint which states that:
(`LEFT` :math:`=` `RIGTH`) :math:`\Leftrightarrow` `TARGET`.

**API**: ::

    boolean addBoolIsEqVar(BoolVar LEFT, BoolVar RIGHT, BoolVar TARGET)

.. admonition:: Example

    .. literalinclude:: /../../choco-samples/src/test/java/org/chocosolver/docs/SatConstraintExamples.java
          :language: java
          :lines: 107-111,113
          :emphasize-lines: 111
          :linenos:

    The solutions of the problem are :

        - `L = 1, R = 1, T = 1`
        - `L = 1, R = 0, T = 0`
        - `L = 0, R = 1, T = 0`
        - `L = 0, R = 0, T = 1`


.. _51_lcstr_boolislevar:

addBoolIsLeVar
==============

Add a clause to the SAT constraint which states that:
(`LEFT` :math:`\leq` `RIGTH`) :math:`\Leftrightarrow` `TARGET`.

**API**: ::

    boolean addBoolIsLeVar(BoolVar LEFT, BoolVar RIGHT, BoolVar TARGET)


.. admonition:: Example

    .. literalinclude:: /../../choco-samples/src/test/java/org/chocosolver/docs/SatConstraintExamples.java
          :language: java
          :lines: 118-122,124
          :emphasize-lines: 122
          :linenos:

    The solutions of the problem are :

        - `L = 1, R = 1, T = 1`
        - `L = 1, R = 0, T = 0`
        - `L = 0, R = 1, T = 1`
        - `L = 0, R = 0, T = 1`

.. _51_lcstr_boolisltvar:

addBoolIsLtVar
==============

Add a clause to the SAT constraint which states that:
(`LEFT` :math:`<` `RIGTH`) :math:`\Leftrightarrow` `TARGET`.

**API**: ::

    boolean addBoolIsLtVar(BoolVar LEFT, BoolVar RIGHT, BoolVar TARGET)

.. admonition:: Example

    .. literalinclude:: /../../choco-samples/src/test/java/org/chocosolver/docs/SatConstraintExamples.java
          :language: java
          :lines: 129-133,135
          :emphasize-lines: 133
          :linenos:

    The solutions of the problem are :

        - `L = 1, R = 1, T = 0`
        - `L = 1, R = 0, T = 0`
        - `L = 0, R = 1, T = 1`
        - `L = 0, R = 0, T = 0`

.. _51_lcstr_boolisneqvar:

addBoolIsNeqVar
===============

Add a clause to the SAT constraint which states that:
(`LEFT` :math:`\neq` `RIGTH`) :math:`\Leftrightarrow` `TARGET`.

**API**: ::

    boolean addBoolIsNeqVar(BoolVar LEFT, BoolVar RIGHT, BoolVar TARGET)

.. admonition:: Example

    .. literalinclude:: /../../choco-samples/src/test/java/org/chocosolver/docs/SatConstraintExamples.java
          :language: java
          :lines: 140-144,146
          :emphasize-lines: 144
          :linenos:

    The solutions of the problem are :

        - `L = 1, R = 1, T = 0`
        - `L = 1, R = 0, T = 1`
        - `L = 0, R = 1, T = 1`
        - `L = 0, R = 0, T = 0`


.. _51_lcstr_boolle:

addBoolLe
=========

Add a clause to the SAT constraint which states that the boolean variable `LEFT` is less or equal than the boolean variable `RIGHT`.

**API**: ::

    boolean addBoolLe(BoolVar LEFT, BoolVar RIGHT)

.. admonition:: Example

    .. literalinclude:: /../../choco-samples/src/test/java/org/chocosolver/docs/SatConstraintExamples.java
          :language: java
          :lines: 151-154,156
          :emphasize-lines: 154
          :linenos:

    The solutions of the problem are :

        - `L = 1, R = 1`
        - `L = 0, R = 1`
        - `L = 0, R = 0`

.. _51_lcstr_boollt:

addBoolLt
=========

Add a clause to the SAT constraint which states that the boolean variable `LEFT` is less than the boolean variable `RIGHT`.

**API**: ::

    boolean addBoolLt(BoolVar LEFT, BoolVar RIGHT)

.. admonition:: Example

    .. literalinclude:: /../../choco-samples/src/test/java/org/chocosolver/docs/SatConstraintExamples.java
          :language: java
          :lines: 161-163,166
          :emphasize-lines: 164
          :linenos:

    The solutions of the problem are :

        - `L = 0, R = 1`

.. _51_lcstr_boolnot:

addBoolNot
==========

Add a clause to the SAT constraint which states that the two boolean variables `LEFT` and `RIGHT` are not equal.

**API**: ::

    boolean addBoolNot(BoolVar LEFT, BoolVar RIGHT)

.. admonition:: Example

    .. literalinclude:: /../../choco-samples/src/test/java/org/chocosolver/docs/SatConstraintExamples.java
          :language: java
          :lines: 151-153,156
          :emphasize-lines: 154
          :linenos:

    The solutions of the problem are :

        - `L = 1, R = 0`
        - `L = 0, R = 1`

.. _51_lcstr_orarrayqualtrue:

addBoolOrArrayEqualTrue
=======================

Add a clause to the SAT constraint which states that:
`BOOLVARS`:math:`_1` :math:`\lor` `BOOLVARS`:math:`_2` :math:`\lor` ... :math:`\lor` `BOOLVARS`:math:`_n`.

**API**: ::

    boolean addBoolOrArrayEqualTrue(BoolVar[] BOOLVARS)

.. admonition:: Example

    .. literalinclude:: /../../choco-samples/src/test/java/org/chocosolver/docs/SatConstraintExamples.java
          :language: java
          :lines: 77-79,81
          :emphasize-lines: 79
          :linenos:

    Some solutions of the problem are :

        - `BS[0] = 1, BS[1] = 1, BS[2] = 1, BS[3] = 1`
        - `BS[0] = 1, BS[1] = 1, BS[2] = 0, BS[3] = 0`
        - `BS[0] = 1, BS[1] = 0, BS[2] = 0, BS[3] = 0`
        - `BS[0] = 0, BS[1] = 1, BS[2] = 0, BS[3] = 0`
        - `BS[0] = 0, BS[1] = 0, BS[2] = 0, BS[3] = 1`

.. _51_lcstr_orarrayeqvar:

addBoolOrArrayEqVar
===================

Add a clause to the SAT constraint which states that:
(`BOOLVARS`:math:`_1` :math:`\lor` `BOOLVARS`:math:`_2` :math:`\lor` ... :math:`\lor` `BOOLVARS`:math:`_n`) :math:`\Leftrightarrow` `TARGET`.

**API**: ::

    boolean addBoolOrArrayEqVar(BoolVar[] BOOLVARS, BoolVar TARGET)

.. admonition:: Example

    .. literalinclude:: /../../choco-samples/src/test/java/org/chocosolver/docs/SatConstraintExamples.java
          :language: java
          :lines: 191-194,196
          :emphasize-lines: 194
          :linenos:

    Some solutions of the problem are :

        - `BS[0] = 1, BS[1] = 1, BS[2] = 1, BS[3] = 1, T = 1`
        - `BS[0] = 1, BS[1] = 1, BS[2] = 0, BS[3] = 1, T = 1`
        - `BS[0] = 0, BS[1] = 1, BS[2] = 0, BS[3] = 0, T = 1`
        - `BS[0] = 0, BS[1] = 0, BS[2] = 0, BS[3] = 0, T = 0`

.. _51_lcstr_oreqvar:

addBoolOrEqVar
==============

Add a clause to the SAT constraint which states that:
(`LEFT` :math:`\lor` `RIGTH`) :math:`\Leftrightarrow` `TARGET`.


**API**: ::

    boolean addBoolOrEqVar(BoolVar LEFT, BoolVar RIGHT, BoolVar TARGET)

.. admonition:: Example

    .. literalinclude:: /../../choco-samples/src/test/java/org/chocosolver/docs/SatConstraintExamples.java
          :language: java
          :lines: 201-205,207
          :emphasize-lines: 205
          :linenos:

    The solutions of the problem are :

        - `L = 1, R = 1, T = 1`
        - `L = 1, R = 0, T = 1`
        - `L = 0, R = 1, T = 1`
        - `L = 0, R = 0, T = 0`

.. _51_lcstr_xoreqvar:

addBoolXorEqVar
===============

Add a clause to the SAT constraint which states that:
(`LEFT` :math:`\oplus` `RIGTH`) :math:`\Leftrightarrow` `TARGET`.

**API**: ::

    boolean addBoolXorEqVar(BoolVar LEFT, BoolVar RIGHT, BoolVar TARGET)

.. admonition:: Example

    .. literalinclude:: /../../choco-samples/src/test/java/org/chocosolver/docs/SatConstraintExamples.java
          :language: java
          :lines: 212-216,218
          :emphasize-lines: 216
          :linenos:

    The solutions of the problem are :

        - `L = 1, R = 1, T = 0`
        - `L = 1, R = 0, T = 1`
        - `L = 0, R = 1, T = 1`
        - `L = 0, R = 0, T = 0`

.. _51_lcstr_clauses:

addClauses
==========

Adding a clause involved either:

- a logical operator `TREE` and an instance of the solver,
- or, two arrays of boolean variables.

The two methods add clauses to the SAT constraint.

- The first method adds one or more clauses defined by a ``LogOp``. ``LopOp`` aims at simplifying the declaration of clauses by providing some static methods. However, it should be considered as a last resort, due to the verbosity it comes with.
- The second API add one or more clauses defined by two arrays `POSLITS` and `NEGLITS`. The first array declares positive boolean variables, those who should be satisfied; the second array declares negative boolean variables, those who should not be satisfied.


**API**: ::

    boolean addClauses(LogOp TREE, Solver SOLVER)
    boolean addClauses(BoolVar[] POSLITS, BoolVar[] NEGLITS)


.. admonition:: Example 1

            .. literalinclude:: /../../choco-samples/src/test/java/org/chocosolver/docs/SatConstraintExamples.java
                  :language: java
                  :lines: 223-230,232
                  :emphasize-lines: 228-230
                  :linenos:

            Some solutions of the problem are :

                - `C1 = 1, C2 = 0, R = 1, AR = 1`
                - `C1 = 1, C2 = 0, R = 0, AR = 1`
                - `C1 = 0, C2 = 1, R = 1, AR = 0`
                - `C1 = 0, C2 = 0, R = 0, AR = 1`

.. admonition:: Example 2

    .. literalinclude:: /../../choco-samples/src/test/java/org/chocosolver/docs/SatConstraintExamples.java
          :language: java
          :lines: 237-242,244
          :emphasize-lines: 242
          :linenos:

    Some solutions of the problem are :

        - `P1 = 1, P2 = 1, P3 = 1, N = 1`
        - `P1 = 1, P2 = 1, P3 = 1, N = 0`
        - `P1 = 1, P2 = 0, P3 = 1, N = 0`
        - `P1 = 0, P2 = 0, P3 = 1, N = 1`

.. _51_lcstr_false:

addFalse
========

Add a unit clause to the SAT constraint which states that the boolean variable `BOOLVAR` must be false (equal to 0).

**API**: ::

    boolean addFalse(BoolVar BOOLVAR)

.. admonition:: Example

    .. literalinclude:: /../../choco-samples/src/test/java/org/chocosolver/docs/SatConstraintExamples.java
          :language: java
          :lines: 249-251,253
          :emphasize-lines: 251
          :linenos:

    The solution of the problem is :

        - `B = 0`

.. _51_lcstr_maxboolarraylesseqvar:

addMaxBoolArrayLessEqVar
========================

Add a clause to the SAT constraint which states that:
maximum(`BOOLVARS`:math:`_i`) :math:`\leq` `TARGET`.


**API**: ::

    boolean addMaxBoolArrayLessEqVar(BoolVar[] BOOLVARS, BoolVar TARGET)

.. admonition:: Example

    .. literalinclude:: /../../choco-samples/src/test/java/org/chocosolver/docs/SatConstraintExamples.java
          :language: java
          :lines: 258-261,263
          :emphasize-lines: 261
          :linenos:

    Some solutions of the problem are :

        - `BS[0] = 1, BS[1] = 1, BS[2] = 1, T = 1`
        - `BS[0] = 1, BS[1] = 0, BS[2] = 1, T = 1`
        - `BS[0] = 0, BS[1] = 1, BS[2] = 1, T = 1`
        - `BS[0] = 0, BS[1] = 0, BS[2] = 0, T = 0`


.. _51_lcstr_sumboolarraygreatereqvar:

addSumBoolArrayGreaterEqVar
===========================

Add a clause to the SAT constraint which states that:
sum(`BOOLVARS`:math:`_i`) :math:`\geq` `TARGET`.


**API**: ::

    boolean addSumBoolArrayGreaterEqVar(BoolVar[] BOOLVARS, BoolVar TARGET)

.. admonition:: Example

    .. literalinclude:: /../../choco-samples/src/test/java/org/chocosolver/docs/SatConstraintExamples.java
          :language: java
          :lines: 268-271,273
          :emphasize-lines: 271
          :linenos:

    Some solutions of the problem are :

        - `BS[0] = 1, BS[1] = 1, BS[2] = 1, T = 1`
        - `BS[0] = 1, BS[1] = 0, BS[2] = 1, T = 1`
        - `BS[0] = 0, BS[1] = 1, BS[2] = 1, T = 1`
        - `BS[0] = 0, BS[1] = 0, BS[2] = 0, T = 0`

.. _51_lcstr_sumboolarraylesseqvar:

addSumBoolArrayLessEqVar
========================

Add a clause to the SAT constraint which states that:
sum(`BOOLVARS`:math:`_i`) :math:`\leq` `TARGET`.


**API**: ::

    boolean addSumBoolArrayLessEqVar(BoolVar[] BOOLVARS, BoolVar TARGET)

.. admonition:: Example

    .. literalinclude:: /../../choco-samples/src/test/java/org/chocosolver/docs/SatConstraintExamples.java
          :language: java
          :lines: 278-281,283
          :emphasize-lines: 281
          :linenos:

    Some solutions of the problem are :

        - `BS[0] = 1 BS[1] = 1 BS[2] = 1 T = 1`
        - `BS[0] = 1 BS[1] = 0 BS[2] = 1 T = 1`
        - `BS[0] = 0 BS[1] = 1 BS[2] = 1 T = 1`
        - `BS[0] = 0 BS[1] = 0 BS[2] = 0 T = 1`

.. _51_lcstr_true:

addTrue
=======

Add a unit clause to the SAT constraint which states that the boolean variable `BOOLVAR` must be true (equal to 1).

**API**: ::

    boolean addTrue(BoolVar BOOLVAR)

.. admonition:: Example

    .. literalinclude:: /../../choco-samples/src/test/java/org/chocosolver/docs/SatConstraintExamples.java
          :language: java
          :lines: 288-290,292
          :emphasize-lines: 290
          :linenos:

    The solution of the problem is :

        - `B = 1`

.. _51_lcstr_main:

*******************
Logical constraints
*******************

The ``LogicalConstraintFactory`` (or ``LCF``) provides various interesting constraints to manipulate other constraints.
These constraints are based on the concept of reification.
We say a constraint ``C`` is reified with a boolean variable ``b`` when we maintain
the equivalence betwen ``b`` being equal to true and ``C`` being satisfied.
This means the ``C`` constraint may be not satisfied, hence it should not be posted to the solver.

.. note::

    It is highly recommended to use that factory only when ``SatFactory`` does not meet requirements.

.. _51_lcstr_and:

and
===

Creates a logical AND constraint on the input constraints.
Each constraint is first reified and then the logical AND is made over the reified variables
(a sum constraint is returned).

Alternatively, the AND constraint can be directly declared with an array of boolean variables as input.

.. _51_lcstr_or:

or
==

Creates a logical OR constraint on the input constraints.
Each constraint is first reified and then the logical OR is made over the reified variables
(a sum constraint is returned).

Alternatively, the OR constraint can be directly declared with an array of boolean variables as input.

.. _51_lcstr_not:

not
===

Creates the opposite constraint of the input constraint.

While this works for any kind of constraint (including globals), it might be a bit naive and slow.

.. _51_lcstr_it:

ifThen
======

Creates and automatically post a constraint ensuring that if the IF statement is true
then the THEN statement must be true as well.

A statement is either a binary variable (0/1) or a reified constraint (satisfied/violated)

Note that the method returns void (you cannot reify that constraint which is automatically posted).
If you wish to reify it, use ``ifThen_reifiable`` (whose implementation differ)

.. _51_lcstr_ite:

ifThenElse
==========

Creates and automatically post a constraint ensuring that if the IF statement is true
then the THEN statement must be true as well. Otherwise, the ELSE statement must be true.

A statement is either a binary variable (0/1) or a reified constraint (satisfied/violated)

Note that the method returns void (you cannot reify that constraint which is automatically posted).
If you wish to reify it, use ``ifThenElse_reifiable`` (whose implementation differ)

.. _51_lcstr_rei:

reification
===========

Creates and automatically post a constraint maintaining the equivalent between
a binary variable being equal to 1 and a constraint being satisfied.

Note that the method returns void (you cannot reify that constraint which is automatically posted).
If you wish to reify it, use ``reification_reifiable`` (whose implementation differ)

.. _550_slf:

*******************
Search loop factory
*******************

The ``SearchLoopFactory`` (or ``SLF``) provides pre-defined methods to drive the search.
By default, a ``Solver`` is created with a Depth-Frist Search algorithm and no learning.
Those two components can be modified, though.

.. _550_slfdfs:

dfs
===

On a call to this method, the current search loop is equipped with a Depth-First Search algorithm with binary decisions and no learning.
*The previous settings are automatically erased by the new ones.*

**API**: ::

    void dfs(Solver aSolver, AbstractStrategy<V> aSearchStrategy)

*Even if ``aSearchStrategy`` can be set to null, it is recommended to declare a convenient search strategy.*


.. _550_slflds:

lds
===

On a call to this method, the current search loop is set with a Limited Discrepancy Search algorithm with binary decisions and no learning.
*The previous settings are automatically erased by the new ones.*
Using LDS is relevant when the search strategy carefully adapted to the problem treated and few decisions are wrong,
bottom decisions are refuted first, within the incremental limit given by the discrepancy.

**API**: ::

    void lds(Solver aSolver, AbstractStrategy<V> aSearchStrategy, int discrepancy)

The *discrepancy* parameter specifies the maximum discrepancy to allowed.
In practice, it starts from 0 to *discrepancy*, with a step of 1.

Even if ``aSearchStrategy`` can be set to null, it is recommended to declare a convenient search strategy.

.. _550_slfdds:

dds
===

On a call to this method, the current search loop is set with a Depth-bounded Discrepancy Search algorithm with binary decisions and no learning.
*The previous settings are automatically erased by the new ones.*
DDS is an alternative to LDS wherein top decisions are refuted first.

**API**: ::

    void dds(Solver aSolver, AbstractStrategy<V> aSearchStrategy, int discrepancy)

The *discrepancy* parameter specifies the maximum discrepancy to allowed.
In practice, it starts from 0 to *discrepancy*, with a step of 1.

*Even if ``aSearchStrategy`` can be set to null, it is recommended to declare a convenient search strategy.*

.. _550_slfhbfs:

hbfs
====

On a call to this method, the current search loop is set with a Hybrid Best-First Search algorithm with binary decisions and no learning.
*The previous settings are automatically erased by the new ones.*
HBFS is relevant when an optimization problem is treated and a good approximation of the lower bound (resp. upper bound)
in minimization (resp. maximization) can be computed.
Recall that it hybrids DFS and Best-First Search, the memory consumption should be carefully monitored.

**API**: ::

    void hbfs(Solver aSolver, AbstractStrategy<V> aSearchStrategy, double a, double b, long N)

*a* and *b* indicates the range which bounds the rate of redundantly propagated decisions.
*N* is the backtrack limit allocated to each DFS try, it should be large enough to limit redundancy.

*Even if ``aSearchStrategy`` can be set to null, it is recommended to declare a convenient search strategy.*


.. _550_slfseq:

seq
===

This method gives the possibility to combine many moves, considered then sequentially.
When the selected Move cannot be extended (resp. repaired), the following one (wrt to the input order) is selected.
*The previous settings are automatically erased by the new ones.*
This is a work-in-progress and it may lead to unexpected behavior when repair() is applied.

**API**: ::

    void seq(Solver aSolver, Move... moves)

*Even if ``aSearchStrategy`` can be set to null, it is recommended to declare a convenient search strategy.*


.. _550_slfrestart:

restart
=======

Equips a ``aSearchLoop`` with a restart strategy.
It encapsulates the current move within a restart move.
Every time the ``restartCriterion`` is met, a restart is done, the new restart limit is updated thanks to ``restartStrategy``.
There will be at most ``restartsLimit`` restarts.

**API**: ::

    void restart(Solver aSolver, LongCriterion restartCriterion, IRestartStrategy restartStrategy, int restartsLimit)


.. _550_slfrestartonsol:

restartOnSolutions
==================

Equips a ``aSearchLoop`` with a restart strategy triggered on solutions.
It encapsulates the current move within a restart move.
Every time a solution is found, a restart is done.

**API**: ::

    restartOnSolutions(Solver aSolver)

.. _550_slflns:

lns
===

Equips a ``aSearchLoop`` with a Large Neighborhood Search move.
It encapsulates the current move within a LNS move.
Anytime a solution is encountered, it is recorded and serves as a basis for the ``neighbor``.
The ``neighbor`` creates a *fragment*: selects variables to freeze/unfreeze wrt the last solution found.
If a fragment cannot be extended to a solution, a new one is selected by restarting the search.
If a fragment induces a search space which a too big to be entirely evaluated, restarting the search can be forced
using the ``restartCriterion``.
A fast restart strategy is often a good choice.

**API**: ::

    void lns(Solver aSolver, INeighbor neighbor)
    void lns(Solver aSolver, INeighbor neighbor, ICounter restartCounter)


.. _550_slfcbj:

learnCBJ
========

Equips a ``aSearchLoop`` with a Conflict-based Backjumping (CBJ) explanation strategy (learn).
It backtracks up to the most recent decision involved in the explanation, and forget younger decisions.
Set ``nogoodsOn`` to true to extract nogoods from failures.
Set ``userFeedbackOn``to true to record the propagation in conflict (only relevant when one wants to interpret the explanation of a failure).

**API**: ::

    void learnCBJ(Solver aSolver, boolean nogoodsOn, boolean userFeedbackOn)

.. _550_slfdbt:

learnDBT
========

Equips a ``aSearchLoop`` with a Dynamic-Backtracking (DBT) explanation strategy (learn).
It backtracks up to most recent decision involved in the explanation, keep unrelated ones.
Set ``nogoodsOn`` to true to extract nogoods from failures.
Set ``userFeedbackOn``to true to record the propagation in conflict (only relevant when one wants to interpret the explanation of a failure).

**API**: ::

    void learnDBT(Solver aSolver, boolean nogoodsOn, boolean userFeedbackOn)

.. _51_svarsel:

******************
Variable selectors
******************

.. important::

    By default, in case of equalities, the variable with the smallest index in the input array is returned.
    Otherwise, consider using a ``VariableSelectorWithTies`` (See :ref:`31_zoom`).

.. _51_svarsel_lex:

lexico_var_selector
===================

A built-in variable selector which chooses the first non-instantiated integer variable to branch on, regarding the lexicographic order.

**Scope**: ``IntVar``

**Factory**: ``org.chocosolver.solver.search.strategy.IntStrategyFactory``

**API**: ::

    VariableSelector<IntVar> lexico_var_selector()

.. _51_svarsel_rnd:

random_var_selector
===================

A built-in variable selector which randomly chooses an integer variable, among non-instantiated ones, to branch on.

**Scope**: ``IntVar``

**Factory**: ``org.chocosolver.solver.search.strategy.IntStrategyFactory``

**API**: ::

    VariableSelector<IntVar> random_var_selector(long SEED)

.. _51_svarsel_mind:

minDomainSize_var_selector
==========================

A built-in variable selector which chooses the non-instantiated integer variable with the smallest domain to branch on.

**Scope**: ``IntVar``

**Factory**: ``org.chocosolver.solver.search.strategy.IntStrategyFactory``

**API**: ::

    VariableSelector<IntVar> minDomainSize_var_selector()

.. _51_svarsel_maxd:

maxDomainSize_var_selector
==========================

A built-in variable selector which chooses the non-instantiated integer variable with the largest domain to branch on.

**Scope**: ``IntVar``

**Factory**: ``org.chocosolver.solver.search.strategy.IntStrategyFactory``

**API**: ::

    VariableSelector<IntVar> maxDomainSize_var_selector()

.. _51_svarsel_maxr:

maxRegret_var_selector
======================


A built-in variable selector which chooses the non-instantiated integer variable with the largest difference between the two smallest values in its domain to branch on .

**Scope**: ``IntVar``

**Factory**: ``org.chocosolver.solver.search.strategy.IntStrategyFactory``

**API**: ::

    VariableSelector<IntVar> maxRegret_var_selector()

.. _51_svalsel:

***************
Value selectors
***************

.. _51_svalsel_minv:

min_value_selector
==================

A built-in value selector which selects the variable lower bound.

**Scope**: ``IntVar``

**Factory**: ``org.chocosolver.solver.search.strategy.IntStrategyFactory``

**API**: ::

    IntValueSelector min_value_selector()

.. _51_svalsel_midv:

mid_value_selector
==================

A built-in value selector which selects the value in the variable domain closest to the mean of its current bounds.
It computes the middle value of the domain. Then checks if the value is contained in the domain.
If not and if ``floor`` is set to ``true`` (resp. ``false``)
the closest integer larger (resp. smaller) than the value is selected.

.. important::

    `mid_value_selector` should not be used with assignment decisions over bounded variables (because the decision negation would result in no inference).


**Scope**: ``IntVar``

**Factory**: ``org.chocosolver.solver.search.strategy.IntStrategyFactory``

**API**: ::

    IntValueSelector mid_value_selector(boolean floor)

.. _51_svalsel_maxv:

max_value_selector
==================

A built-in value selector which selects the variable upper bound.

**Scope**: ``IntVar``

**Factory**: ``org.chocosolver.solver.search.strategy.IntStrategyFactory``

**API**: ::

    IntValueSelector max_value_selector()

.. _51_svalsel_rndb:

randomBound_value_selector
==========================

A built-in value selector which randomly selects either the lower bound or the upper bound of the variable.

**Scope**: ``IntVar``

**Factory**: ``org.chocosolver.solver.search.strategy.IntStrategyFactory``

**API**: ::

    IntValueSelector randomBound_value_selector(long SEED)

.. _51_svalsel_rndv:

random_value_selector
=====================

Selects randomly a value in the variable domain.

.. important::

    `random_value_selector` should not be used with assignment decisions over bounded variables (because the decision negation could result in no inference).


**Scope**: ``IntVar``

**Factory**: ``org.chocosolver.solver.search.strategy.IntStrategyFactory``

**API**: ::

    IntValueSelector random_value_selector(long SEED)


.. _51_sdecop:

******************
Decision operators
******************

.. _51_sdecop_ass:

assign
======

A built-in decision operator which assigns the selected variable to the selected value.
Its negation is `remove`.

**Scope**: ``IntVar``

**Factory**: ``org.chocosolver.solver.search.strategy.IntStrategyFactory``

**API**: ::

    DecisionOperator<IntVar> assign()

.. _51_sdecop_rem:

remove
======

A built-in decision operator which removes the selected value from the selected variable domain.
Its negation is `assign`.


**Scope**: ``IntVar``

**Factory**: ``org.chocosolver.solver.search.strategy.IntStrategyFactory``

**API**: ::

    DecisionOperator<IntVar> remove()

.. _51_sdecop_spl:

split
=====

A built-in decision operator which splits the selected variable domain at the selected value, that is, it updates the upper bound of the variable to the selected value.
Its negation is `reverse_split` on `value + 1`.


**Scope**: ``IntVar``

**Factory**: ``org.chocosolver.solver.search.strategy.IntStrategyFactory``

**API**: ::

    DecisionOperator<IntVar> split()

.. _51_sdecop_rspl:

reverse_split
=============

A built-in decision operator which splits the selected variable domain at the selected value, that is, it updates the lower bound of the variable to the selected value.
Its negation is `split` on `value - 1`.


**Scope**: ``IntVar``

**Factory**: ``org.chocosolver.solver.search.strategy.IntStrategyFactory``

**API**: ::

    DecisionOperator<IntVar> reverse_split()



.. _51_sstrat:

*******************
Built-in strategies
*******************

.. _51_sstrat_cus:

custom
======

To build a specific strategy based on ``IntVar`` or ``SetVar``.
A strategy is based on a variable selector, a value selector, an optional decision operator and a set of variables.

**Scope**: ``IntVar``, ``SetVar``

**Factory**: ``org.chocosolver.solver.search.strategy.IntStrategyFactory``

**API**: ::

    IntStrategy custom(VariableSelector<IntVar> VAR_SELECTOR,
                                                   IntValueSelector VAL_SELECTOR,
                                                   DecisionOperator<IntVar> DEC_OPERATOR,
                                                   IntVar... VARS)

    IntStrategy custom(VariableSelector<IntVar> VAR_SELECTOR,
                                                  IntValueSelector VAL_SELECTOR,
                                                  IntVar... VARS)

    SetStrategy custom(VariableSelector<SetVar> varS, SetValueSelector valS, boolean enforceFirst,
                       SetVar... sets)


.. _51_sstrat_dic:

dichtotomic
===========

To build a specific strategy based on ``IntVar``.
A strategy is based on a variable selector, a boolean and a set of variables.
It builds a dichotomic search strategy which selects a variable thanks to VAR_SELECTOR,
selects the value closest to the middle of the domain and either removes the second half interval (if LOWERFIRST is true)
or the first half interval (otherwise).

**Scope**: ``IntVar``

**Factory**: ``org.chocosolver.solver.search.strategy.IntStrategyFactory``

**API**: ::

    IntStrategy dichotomic(VariableSelector<IntVar> VAR_SELECTOR,
                                                        boolean LOWERFIRST,
                                                        IntVar... VARS)

.. _51_sstrat_once:

once
====

To build a specific strategy based on ``IntVar``.
A strategy is based on a variable selector, a value selector, an optional decision operator and a set of variables.
Unlike ``custom``, it builds unary decisions, that is, decisions which can be applied but not be refuted.

**Scope**: ``IntVar``

**Factory**: ``org.chocosolver.solver.search.strategy.IntStrategyFactory``

**API**: ::

    IntStrategy once(VariableSelector<IntVar> VAR_SELECTOR,
                                   IntValueSelector VAL_SELECTOR,
                                   DecisionOperator<IntVar> DEC_OPERATOR,
                                   IntVar... VARS)

    IntStrategy once(VariableSelector<IntVar> VAR_SELECTOR,
                                   IntValueSelector VAL_SELECTOR,
                                   IntVar... VARS)


.. _51_sstrat_lexfi:

force_first
===========

A built-in strategy which chooses the first non-instantiated variable, regarding the lexicographic order, and forces its first smallest unfixed value to be part of the kernel.

**Scope**: ``SetVar``

**Factory**: ``org.chocosolver.solver.search.strategy.SetStrategyFactory``

**API**: ::

    SetStrategy force_first(SetVar... sets)

.. _51_sstrat_maxdfi:

force_maxDelta_first
====================

A built-in strategy which chooses the first non-instantiated variable of maximum delta (envelope's cardinality minus kernel's cardinality) and forces its smallest unfixed value to be part of the kernel.

**Scope**: ``SetVar``

**Factory**: ``org.chocosolver.solver.search.strategy.SetStrategyFactory``

**API**: ::

    SetStrategy force_maxDelta_first(SetVar... sets)

.. _51_sstrat_mindfi:

force_minDelta_first
====================

A built-in strategy which chooses the first non-instantiated variable of minimum delta (envelope's cardinality minus kernel's cardinality) and forces its smallest first unfixed value to be part of the kernel.

**Scope**: ``SetVar``

**Factory**: ``org.chocosolver.solver.search.strategy.SetStrategyFactory``

**API**: ::

    SetStrategy force_minDelta_first(SetVar... sets)

.. _51_sstrat_lexlb:

lexico_LB
=========

A built-in strategy which chooses the first non-instantiated variable, regarding the lexicographic order, and assigns it to its lower bound.

**Scope**: ``IntVar``

**Factory**: ``org.chocosolver.solver.search.strategy.IntStrategyFactory``

**API**: ::

    IntStrategy lexico_LB(IntVar... VARS)

.. _51_sstrat_lexnlb:

lexico_Neq_LB
=============

A built-in strategy which chooses the first non-instantiated variable, regarding the lexicographic order, and removes its lower bound from its domain.

**Scope**: ``IntVar``

**Factory**: ``org.chocosolver.solver.search.strategy.IntStrategyFactory``

**API**: ::

    IntStrategy lexico_Neq_LB(IntVar... VARS)

.. _51_sstrat_lexspl:

lexico_Split
============

A built-in strategy which chooses the first non-instantiated variable, regarding the lexicographic order, and removes the second half of its domain.

**Scope**: ``IntVar``

**Factory**: ``org.chocosolver.solver.search.strategy.IntStrategyFactory``

**API**: ::

    IntStrategy lexico_Split(IntVar... VARS)

.. _51_sstrat_lexub:

lexico_UB
=========

A built-in strategy which chooses the first non-instantiated variable, regarding the lexicographic order, and assigns it to its upper bound.

**Scope**: ``IntVar``

**Factory**: ``org.chocosolver.solver.search.strategy.IntStrategyFactory``

**API**: ::

    IntStrategy lexico_UB(IntVar... VARS)

.. _51_sstrat_minlb:

minDom_LB
=========

A built-in strategy which chooses the first non-instantiated variable with the smallest domain size, and assigns it to its lower bound.

**Scope**: ``IntVar``

**Factory**: ``org.chocosolver.solver.search.strategy.IntStrategyFactory``

**API**: ::

    IntStrategy minDom_LB(IntVar... VARS)

.. _51_sstrat_midlb:

minDom_MidValue
===============

A built-in strategy which chooses the first non-instantiated variable with the smallest domain size, and assigns it to the value closest to its middle of its domain.
When ``floor`` is set to true, it selects the closest value less than or equal to the middle value.
Set to false, it selects the closest value greater or equal to the middle value.

**Scope**: ``IntVar``

**Factory**: ``org.chocosolver.solver.search.strategy.IntStrategyFactory``

**API**: ::

    IntStrategy minDom_MidValue(boolean floor, IntVar... VARS)

.. _51_sstrat_maxspl:

maxDom_Split
============

A built-in strategy which chooses the first non-instantiated variable with largest domain size, and removes the second half of its domain.

**Scope**: ``IntVar``

**Factory**: ``org.chocosolver.solver.search.strategy.IntStrategyFactory``

**API**: ::

    IntStrategy maxDom_Split(IntVar... VARS)

.. _51_sstrat_minub:

minDom_UB
=========

A built-in strategy which chooses the first non-instantiated variable with the smallest domain size, and assigns it to its upper bound.

**Scope**: ``IntVar``

**Factory**: ``org.chocosolver.solver.search.strategy.IntStrategyFactory``

**API**: ::

    IntStrategy minDom_UB(IntVar... VARS)

.. _51_sstrat_maxrlb:

maxReg_LB
=========

A built-in strategy which chooses the first non-instantiated variable with the largest difference between the two smallest values of its domain, and assigns it to its lower bound.

**Scope**: ``IntVar``

**Factory**: ``org.chocosolver.solver.search.strategy.IntStrategyFactory``

**API**: ::

    IntStrategy maxReg_LB(IntVar... VARS)

.. _51_sstrat_objbu:

objective_bottom_up
===================

A branching strategy over the objective variable.
It is activated on the first solution, and iterates over the domain in decreasing order (upper bound first).

**Scope**: ``IntVar``

**Factory**: ``org.chocosolver.solver.search.strategy.IntStrategyFactory``

**API**: ::

    ObjectiveStrategy objective_bottom_up(IntVar OBJECTIVE)

.. _51_sstrat_objdi:

objective_dichotomic
====================

A branching strategy over the objective variable.
It is activated on the first solution, and iterates over the domain in increasing order (lower bound first).

**Scope**: ``IntVar``

**Factory**: ``org.chocosolver.solver.search.strategy.IntStrategyFactory``

**API**: ::

    ObjectiveStrategy objective_dichotomic(IntVar OBJECTIVE)

.. _51_sstrat_objtd:

objective_top_bottom
====================

A branching strategy over the objective variable.
It is activated on the first solution, and splits the domain into two parts, and evaluates first
the lower part in case of minimization and the upper part in case of maximization.

**Scope**: ``IntVar``

**Factory**: ``org.chocosolver.solver.search.strategy.IntStrategyFactory``

**API**: ::

    ObjectiveStrategy objective_dichotomic(IntVar OBJECTIVE)

.. _51_sstrat_rndb:

random_bound
============

A built-in strategy which randomly chooses a non-instantiated variable, and assigns it to one of its bounds, randomly selected.

**Scope**: ``IntVar``

**Factory**: ``org.chocosolver.solver.search.strategy.IntStrategyFactory``

**API**: ::

    IntStrategy random_bound(IntVar[] VARS)
    IntStrategy random_bound(IntVar[] VARS, long SEED)

.. _51_sstrat_rndv:

random_value
============

A built-in strategy which randomly chooses a non-instantiated variable, and assigns it to a randomly selected value from its domain.

**Scope**: ``IntVar``

**Factory**: ``org.chocosolver.solver.search.strategy.IntStrategyFactory``

**API**: ::

    IntStrategy random_value(IntVar[] VARS)
    IntStrategy random_value(IntVar[] VARS, long SEED)

.. _51_sstrat_remfi:

remove_first
============

A built-in strategy which chooses the first unfixed variable and removes its smallest unfixed value from the envelope.

**Scope**: ``SetVar``

**Factory**: ``org.chocosolver.solver.search.strategy.SetStrategyFactory``

**API**: ::

    SetStrategy remove_first(SetVar... sets)

.. _51_sstrat_seq:

sequencer
=========

A meta strategy which applies sequentially the strategies in its scope.

**Scope**: ``IntVar``

**Factory**: ``org.chocosolver.solver.search.strategy.IntStrategyFactory``

**API**: ::

    AbstractStrategy sequencer(AbstractStrategy... strategies)

.. _51_sstrat_dwdeg:

domOverWDeg
===========

A black-box strategy for ``IntVar`` which selects the non-instantiated variable with the smallest ratio :math:`\frac{|d(x)|}{w(x)}`,
where :math:`|d(x)|` denotes the domain size of a variable `x` and
:math:`w(x)` its weighted degree. The weighted degree of a variable sums the weight of each of the constraint it is involved in where at least 2 variables remains uninstantiated.
The weight of a constraint is initialized to `1` and increased by one each time a constraint propagation fails during the search.

**Implementation based on**: :cite:`BoussemartHLS04`.

**Scope**: ``IntVar``

**Factory**: ``org.chocosolver.solver.search.strategy.IntStrategyFactory``

**API**: ::

    AbstractStrategy<IntVar> domOverWDeg(IntVar[] VARS, long SEED, IntValueSelector VAL_SELECTOR)
    AbstractStrategy<IntVar> domOverWDeg(IntVar[] VARS, long SEED) // default: min_value_selector

.. _51_sstrat_act:

activity
========

A black-box strategy for ``IntVar`` which selects the non-instantiated variable with the largest ratio :math:`\frac{a(x)}{|d(x)|}`,
where :math:`|d(x)|` denotes the domain size of a variable `x` and
:math:`a(x)` its activity.
The activity of a variable measures how often the domain of the variable is reducing during the search.
Then, the value with the least activity is selected from the domain of the variable.

**Implementation based on**: :cite:`MichelH12`.

**Scope**: ``IntVar``

**Factory**: ``org.chocosolver.solver.search.strategy.IntStrategyFactory``

**API**: ::

    AbstractStrategy<IntVar> activity(IntVar[] VARS, double GAMMA, double DELTA, int ALPHA,
                                                    int FORCE_SAMPLING, long SEED)
    AbstractStrategy<IntVar> activity(IntVar[] VARS, long SEED) // default: 0.999d, 0.2d, 8, 1

.. _51_sstrat_imp:

impact
======

A black-box strategy for ``IntVar`` which selects the non-instantiated variable with the largest impact :math:`\sum_{a in d(x)}{1 - I(x = a)}`,
:math:`I(x = a)` denotes the impact of assigning the variable `x` to a value `a` from its domain :math:`d(x)`.
The impact of an assignment measures the search space reduction induced by a decision, by evaluating the size of the search before and after the application of a decision.
The higher the impact, the greater the search space reduction.
Then, the value with the least impact is selected from the domain of the variable.
An approximation of the impacts is preprocessed.

**Implementation based on**: :cite:`Refalo04`.

**Scope**: ``IntVar``

**Factory**: ``org.chocosolver.solver.search.strategy.IntStrategyFactory``

**API**: ::

    AbstractStrategy<IntVar> impact(IntVar[] VARS, int ALPHA, int SPLIT, int NODEIMPACT,
                                    long SEED, boolean INITONLY)
    AbstractStrategy<IntVar> impact(IntVar[] VARS, long SEED) // default: 2, 3, 10, true


.. _51_sstrat_lf:

lastConflict
============

A composite heuristic which override the defined strategy by forcing some decisions to branch on variables involved in recent conflicts.
After each conflict, the last assigned variable is selected in priority, so long as a failure occurs.

**Implementation based on**: :cite:`LecoutreSTV09`.

**Scope**: ``Variable``

**Factory**: ``org.chocosolver.solver.search.strategy.IntStrategyFactory``

**API**: ::

    AbstractStrategy lastConflict(Solver SOLVER)
    AbstractStrategy lastConflict(Solver SOLVER, AbstractStrategy STRAT)
    AbstractStrategy lastKConflicts(Solver SOLVER, int K, AbstractStrategy STRAT)



.. _55_smf:

***************
Search Monitors
***************




geometrical
===========

Plug a geometrical restart strategy to the solver.
It performs a search with restarts controlled by the resolution event ``counter`` which counts events occurring during the search.
Parameter ``base`` indicates the maximal number of events allowed in the first search tree.
Once this limit is reached, a restart occurs and the search continues until ``base``*``grow`` events are done, and so on.
After each restart, the limit number of events is increased by the geometric factor ``grow``.
``limit`` states the maximum number of restarts.

**Factory**: ``org.chocosolver.solver.search.loop.monitors.SearchMonitorFactory``

**API**: ::

    void geometrical(Solver solver, int base, double grow, ICounter counter, int limit)


luby
====

Branch a luby restart strategy to the solver.
It is an alternative to the geometric restart policy.
It performs a search with restarts controlled by the number of resolution events counted by ``counter``.
The maximum number of events allowed at a given restart iteration is given by base multiplied by the Las Vegas coefficient at this iteration.
The sequence of these coefficients is defined recursively on its prefix subsequences:
starting from the first prefix :math:`1`, the :math:`(k+1)^th` prefix is the :math:`k^th` prefix repeated ``grow`` times and
immediately followed by coefficient ``grow``:math:`^k`.

- the first coefficients for ``grow`` =2: [1,1,2,1,1,2,4,1,1,2,1,1,2,4,8,1,...]
- the first coefficients for ``grow`` =3 : [1, 1, 1, 3, 1, 1, 1, 3, 1, 1, 1, 3, 9,...]

**Factory**: ``org.chocosolver.solver.search.loop.monitors.SearchMonitorFactory``

**API**: ::

    luby(Solver solver, int base, int grow, ICounter counter, int limit)

limitNode
=========

Defines a limit over the number of nodes allowed during the resolution.
When the limit is reached, the resolution is stopped.

**Factory**: ``org.chocosolver.solver.search.loop.monitors.SearchMonitorFactory``

**API**: ::

    void limitNode(Solver solver, long limit)

limitSolution
=============

Defines a limit over the number of solutions allowed during the resolution.
When the limit is reached, the resolution is stopped.

**Factory**: ``org.chocosolver.solver.search.loop.monitors.SearchMonitorFactory``

**API**: ::

    void limitSolution(Solver solver, long limit)

limitTime
=========

Defines a limit over the run time.
When the limit is reached, the resolution is stopped.
The limit can be either defined in millisecond or using a String which states the duration like "WWd XXh YYm ZZs" for example:
- "1d2h3m4.5s": one day, two hours, three minutes, four seconds and 500 milliseconds<p/>
- "2h30m": two hours and 30 minutes<p/>
- "30.5s": 30 seconds and 500 ms<p/>
- "180s": three minutes

**Factory**: ``org.chocosolver.solver.search.loop.monitors.SearchMonitorFactory``

**API**: ::

    void limitTime(Solver solver, long limit)

    void limitTime(Solver solver, String duration)



limitThreadTime
===============

Defines a limit over the run time, defined in a separated thread.
When the limit is reached, the resolution is stopped.
The limit can be either defined in millisecond or using a String which states the duration like "WWd XXh YYm ZZs" for example:
- "1d2h3m4.5s": one day, two hours, three minutes, four seconds and 500 milliseconds<p/>
- "2h30m": two hours and 30 minutes<p/>
- "30.5s": 30 seconds and 500 ms<p/>
- "180s": three minutes

**Factory**: ``org.chocosolver.solver.search.loop.monitors.SearchMonitorFactory``

**API**: ::

    void limitThreadTime(Solver solver, long limit)

    void limitThreadTime(Solver solver, String duration)

convertInMilliseconds

limitFail
=========

Defines a limit over the number of fails allowed during the resolution.
When the limit is reached, the resolution is stopped.

**Factory**: ``org.chocosolver.solver.search.loop.monitors.SearchMonitorFactory``

**API**: ::

    void limitFail(Solver solver, long limit)

limitBacktrack
==============

Defines a limit over the number of backtracks allowed during the resolution.
When the limit is reached, the resolution is stopped.

**Factory**: ``org.chocosolver.solver.search.loop.monitors.SearchMonitorFactory``

**API**: ::

    void limitBacktrack(Solver solver, long limit)


restartAfterEachSolution
========================

Force the resolution to restart at root node after each solution.

**Factory**: ``org.chocosolver.solver.search.loop.monitors.SearchMonitorFactory``

**API**: ::

    void restartAfterEachSolution(Solver solver)


nogoodRecordingOnSolution
=========================

Record nogoods from solution, that is, anytime a solution is found, a nogood is produced to prevent from finding the same solution later during the search.
An array of variables, presumably decision ones, is given as input to reduce the size of the generated nogoods.


**Factory**: ``org.chocosolver.solver.search.loop.monitors.SearchMonitorFactory``

**API**: ::

    void nogoodRecordingOnSolution(IntVar[] vars)


nogoodRecordingFromRestarts
===========================

Record nogoods from restarts, that is, anytime the search restarts, one or more nogoods are produced, based on the decision path, to prevent from scanning the same sub-search tree.

**Factory**: ``org.chocosolver.solver.search.loop.monitors.SearchMonitorFactory``

**API**: ::

    void nogoodRecordingFromRestarts(Solver solver)

shareBestKnownBound
===================

A method which prepares the solvers in the list to be run in parallel.
It plugs tools to share between solvers the best known bound when dealing with an optimization problem.

**Factory**: ``org.chocosolver.solver.search.loop.monitors.SearchMonitorFactory``

**API**: ::

    void shareBestKnownBound(List<Solver> solvers)
