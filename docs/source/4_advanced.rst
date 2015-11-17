**************
Advanced usage
**************

.. _41_settings_label:

Settings
========

A ``Settings`` object is attached to each ``Solver``.
It declares default behavior for various purposes: from general purpose (such as the welcome message), modelling purpose (such as enabling views) or solving purpose (such as the search binder).

The API is:

``String getWelcomeMessage()``
    Return the welcome message.

``Idem getIdempotencyStrategy()``
    Define how to react when a propagator is not ensured to be idempotent.

``boolean enableViews()``
    Set to 'true' to allow the creation of views in the ``VariableFactory``. Creates new variables with channeling constraints otherwise.

``int getMaxDomSizeForEnumerated()``
    Define the maximum domain size threshold to force integer variable to be enumerated instead of bounded while calling ``VariableFactory#integer(String, int, int, Solver)``.

``boolean enableTableSubstitution()``
    Set to true to replace intension constraints by extension constraints.

``int getMaxTupleSizeForSubstitution()``
    Define the maximum domain size threshold to replace intension constraints by extension constraints. Only checked when ``enableTableSubstitution()`` is set to true.

``boolean plugExplanationIn()``
    Set to true to plug explanation engine in.

``boolean enablePropagatorInExplanation()``
    Set to true to add propagators in explanations

``double getMCRPrecision()``
    Define the rounding precision for :ref:`51_icstr_mcreg`. MUST BE < 13 as java messes up the precisions starting from 10E-12 (34.0*0.05 == 1.70000000000005).

``double getMCRDecimalPrecision()``
    Defines the smallest used double for :ref:`51_icstr_mcreg`.

``short[] getFineEventPriority()``
    Defines, for fine events, for each priority, the queue in which a propagator of such a priority should be scheduled in.

``short[] getCoarseEventPriority()``
    Defines, for coarse events, for each priority, the queue in which a propagator of such a priority should be scheduled in

``ISearchBinder getSearchBinder()``
    Return the default :ref:`31_searchbinder`.

``ICondition getEnvironmentHistorySimulationCondition()``
    Return the condition to satisfy when rebuilding history of backtrackable objects is needed.

``boolean warnUser()``
    Return true if one wants to be informed of warnings detected during modeling/solving (default value is false).

``boolean enableIncrementalityOnBoolSum(int nbvars)``
    Return true if the incrementality is enabled on boolean sum, based on the number of variables involved.
    Default condition is : nbvars > 10.

``boolean outputWithANSIColors()``
    If your terminal support ANSI colors (Windows terminals don't), you can set this to true and decisions and solutions
    will be output with colors.

``boolean debugPropagation()``
    When this setting returns true, a complete trace of the events is output.
    This can be quite big, though, and it slows down the overall process.

``boolean cloneVariableArrayInPropagator()``
   If this setting is set to true (default value), a clone of the input variable array is made in any propagator constructors.
   This prevents, for instance, wrong behavior when permutations occurred on the input array (e.g., sorting variables).
   Setting this to false may limit the memory consumption during modelling.

.. _41_LNS_label:

Large Neighborhood Search (LNS)
===============================

Local search techniques are very effective to solve hard optimization problems.
Most of them are, by nature, incomplete.
In the context of constraint programming (CP) for optimization problems, one of the most well-known and widely used local search techniques is the Large Neighborhood Search (LNS) algorithm [#q1]_.
The basic idea is to iteratively relax a part of the problem, then to use constraint programming to evaluate and bound the new solution.


.. [#q1] Paul Shaw. Using constraint programming and local search methods to solve vehicle routing problems. In Michael Maher and Jean-Francois Puget, editors, *Principles and Practice of Constraint Programming, CP98*, volume 1520 of *Lecture Notes in Computer Science*, pages 417–431. Springer Berlin Heidelberg, 1998.

Principle
---------

LNS is a two-phase algorithm which partially relaxes a given solution and repairs it.
Given a solution as input, the relaxation phase builds a partial solution (or neighborhood) by choosing a set of variables to reset to their initial domain;
The remaining ones are assigned to their value in the solution.
This phase is directly inspired from the classical Local Search techniques.
Even though there are various ways to repair the partial solution, we focus on the technique in which Constraint Programming is used to bound the objective variable and
to assign a value to variables not yet instantiated.
These two phases are repeated until the search stops (optimality proven or limit reached).

The ``LNSFactory`` provides pre-defined configurations.
Here is the way to declare LNS to solve a problem: ::

    LNSFactory.rlns(solver, ivars, 30, 20140909L, new FailCounter(solver, 100));
    solver.findOptimalSolution(ResolutionPolicy.MINIMIZE, objective);

It declares a *random* LNS which, on a solution, computes a partial solution based on ``ivars``.
If no solution are found within 100 fails (``FailCounter(solver, 100)``), a restart is forced.
Then, every ``30`` calls to this neighborhood, the number of fixed variables is randomly picked.
``20140909L`` is the seed for the ``java.util.Random``.


The instruction ``LNSFactory.rlns(solver, vars, level, seed, frcounter)`` runs:

.. literalinclude:: /../../choco-solver/src/main/java/org/chocosolver/solver/search/loop/lns/LNSFactory.java
   :language: java
   :lines: 112-114
   :linenos:

The factory provides other LNS configurations together with built-in neighbors.

Neighbors
---------

While the implementation of LNS is straightforward, the main difficulty lies in the design of neighborhoods able to move the search further.
Indeed, the balance between diversification (i.e., evaluating unexplored sub-tree) and intensification (i.e., exploring them exhaustively) should be well-distributed.


Generic neighbors
^^^^^^^^^^^^^^^^^

One drawback of LNS is that the relaxation process is quite often problem dependent.
Some works have been dedicated to the selection of variables to relax through general concept not related to the class of the problem treated [5,24].
However, in conjunction with CP, only one generic approach, namely Propagation-Guided LNS [24], has been shown to be very competitive with dedicated ones on a variation of the Car Sequencing Problem.
Nevertheless, such generic approaches have been evaluated on a single class of problem and need to be thoroughly parametrized at the instance level, which may be a tedious task to do.
It must, in a way, automatically detect the problem structure in order to be efficient.


Combining neighborhoods
^^^^^^^^^^^^^^^^^^^^^^^

There are two ways to combine neighbors.

Sequential
""""""""""

Declare an instance of ``SequenceNeighborhood(n1, n2, ..., nm)``.
Each neighbor ni is applied in a sequence until one of them leads to a solution.
At step k, the :math:`(k \mod m)^{th}` neighbor is selected.
The sequence stops if at least one of the neighbor is complete.

Adaptive
""""""""

Declare an instance of ``AdaptiveNeighborhood(1L, n1, n2, ..., nm)``.
At the beginning a weight of 1 at assigned to each neighbor ni.
Then, if a neighbor leads to solution, its weight :math:`w_i` is increased by 1.
Any time a partial solution has to be computed, a value ``W`` between 1 and :math:`w_1+w_2+...+w_n` is randomly picked (``1L`` is the seed).
Then the weight of each neighbor is subtracted from ``W``, as soon as ``W``:math:`\leq 0`, the corresponding neighbor is selected.
For instance, let's consider three neighbors n1, n2 and n3, their respective weights w1=2, w2=4, w3=1.
``W`` = 3  is randomly picked between 1 and 7.
Then, the weight of n1 is subtracted, ``W``2-=1; the weight of n2 is subtracted, ``W``-4 = -3, ``W`` is less than 0 and n2 is selected.


Defining its own neighborhoods
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

One can define its own neighbor by extending the abstract class ``INeighbor``.
It forces to implements the following methods:

+------------------------------------------------------------------------+------------------------------------------------------------------------------------------------------------------------+
| **Method**                                                             |   **Definition**                                                                                                       |
+========================================================================+========================================================================================================================+
+------------------------------------------------------------------------+------------------------------------------------------------------------------------------------------------------------+
| ``void recordSolution()``                                              | Action to perform on a solution (typicallu, storing the current variables' value).                                     |
+------------------------------------------------------------------------+------------------------------------------------------------------------------------------------------------------------+
+------------------------------------------------------------------------+------------------------------------------------------------------------------------------------------------------------+
| ``Decision fixSomeVariables()``                                        | Fix some variables to their value in the last solution, computing a partial solution and returns it as a decision.     |
+------------------------------------------------------------------------+------------------------------------------------------------------------------------------------------------------------+
+------------------------------------------------------------------------+------------------------------------------------------------------------------------------------------------------------+
| ``void restrictLess()``                                                | Relax the number of variables fixed. Called when no solution was found during a LNS run (trapped into a local optimum).|
+------------------------------------------------------------------------+------------------------------------------------------------------------------------------------------------------------+
+------------------------------------------------------------------------+------------------------------------------------------------------------------------------------------------------------+
| ``boolean isSearchComplete()``                                         | Indicates whether the neighbor is complete, that is, can end.                                                          |
+------------------------------------------------------------------------+------------------------------------------------------------------------------------------------------------------------+

Restarts
--------

A generic and common way to reinforce diversification of LNS is to introduce restart during the search process.
This technique has proven to be very flexible and to be easily integrated within standard backtracking procedures [#q2]_.

.. [#q2] Laurent Perron. Fast restart policies and large neighborhood search. In Francesca Rossi, editor, *Principles and Practice of Constraint Programming at CP 2003*, volume 2833 of *Lecture Notes in Computer Science*. Springer Berlin Heidelberg, 2003.


Walking
-------

A complementary technique that appear to be efficient in practice is named `Walking` and consists in accepting equivalent intermediate solutions in a search iteration instead of requiring a strictly better one.
This can be achieved by defining an ``ObjectiveManager`` like this: ::

    solver.set(new ObjectiveManager(objective, ResolutionPolicy.MAXIMIZE, false));

Where the last parameter, named ``strict`` must be set to false to accept equivalent intermediate solutions.

Other optimization policies may be encoded by using either search monitors or a custom ``ObjectiveManager``.


.. _44_multithreading_label:

Multi-thread resolution
=======================

Choco |version| provides a simple way to use several thread to treat a problem.
This is achieved by using Java8 lambdas.
The main idea of that driver is to solve the *same* problem with various search strategies,
and to share few possible information.

The first step is to declare a method populates (adding variables and constraints) a solver given in parameter.
The *n* solvers should be passed to that method.

Before running the resolution, a call to the following method is required to synchronize interruption and, when dealing with optimization problems, to share data relative to the objective variable(s) between solvers: ::

    SearchMonitorFactory.prepareForParallelResolution(List<Solver> solvers)

Make sure, if dealing with an optimization problem, that the objective variable is eagerly declared with ``solver.setObjectives(cost);``.

Finally, the expected ways to solve a problem using mutlple solvers is: ::

    int n =4; // number of solvers to use
    List<Solver> solvers = new ArrayList<>();
    for(int i = 0 ; i < n; i++){
       Solver solver = new Solver();
       solvers.add(solver);
       readModel(solver); // a dedicated method that declares variables and constraints
       // the search should also be declared within that method
    }
    SMF.prepareForParallelResolution(solvers);
    solvers.parallelStream().forEach(s -> {
        s.findOptimalSolution(ResolutionPolicy.MINIMIZE);
    });

When dealing with multithreading resolution, very few data is shared between threads:
when a solver ends, it communicates an interruption instruction to the others 
and the best known bound of the objective variable(s) is shared among solver. 
This enables to explore the search space in various way, setting distinct search strategy and/or search loop 
(this should be done in the dedicated method which builds the model, though) 
This also enables to solve multiple modelling of the same problemi (or even distinct problems), 
as long as the resolution policy remains the same.


.. _43_explanations_label:

Explanations
============

Choco |version| natively support explanations [#1]_. However, no explanation engine is plugged-in by default.


.. [#1] Narendra Jussien. The versatility of using explanations within constraint programming. Technical Report 03-04-INFO, 2003.


Principle
---------

Nogoods and explanations have long been used in various paradigms for improving search.
An explanation records some sufficient information to justify an inference made by the solver (domain reduction, contradiction, etc.).
It is made of a subset of the original propagators of the problem and a subset of decisions applied during search.
Explanations represent the logical chain of inferences made by the solver during propagation in an efficient and usable manner.
In a way, they provide some kind of a trace of the behavior of the solver as any operation needs to be explained.

Explanations have been successfully used for improving constraint programming search process.
Both complete (as the mac-dbt algorithm) and incomplete (as the decision-repair algorithm) techniques have been proposed.
Those techniques follow a similar pattern: learning from failures by recording each domain modification with its associated explanation (provided by the solver) and taking advantage of the information gathered to be able to react upon failure by directly pointing to relevant decisions to be undone.
Complete techniques follow a most-recent based pattern while incomplete technique design heuristics to be used to focus on decisions more prone to allow a fast recovery upon failure.

The current explanation engine is coded to be *Asynchronous, Reverse, Low-intrusive and Lazy*:

Asynchronous:
    Explanations are not computed during the propagation.

Reverse:
    Explanations are computed in a bottom-up way, from the conflict to the first event generated, *keeping* only relevant events to compute the explanation of the conflict.

Low-intrusive:
    Basically, propagators need to implement only one method to furnish a convenient explanation schema.

Lazy:
    Explanations are computed on request.


To do so, all events are stored during the descent to a conflict/solution, and are then evaluated and kept if relevant, to get the explanation.

In practice
-----------

Consider the following example:

.. literalinclude:: /../../choco-samples/src/test/java/org/chocosolver/docs/ExplanationExamples.java
   :language: java
   :lines: 52-56,59
   :linenos:

The problem has no solution since the two constraints cannot be satisfied together.
A naive strategy such as ``ISF.lexico_LB(bvars)`` (which selects the variables in lexicographical order) will detect lately and many times the failure.
By plugging-in an explanation engine, on each failure, the reasons of the conflict will be explained.

.. literalinclude:: /../../choco-samples/src/test/java/org/chocosolver/docs/ExplanationExamples.java
   :language: java
   :lines: 57
   :linenos:

The explanation engine records *deductions* and *causes* in order to compute explanations.
In that small example, when an explanation engine is plugged-in, the two first failures will enable to conclude that the problem has no solution.
Only three nodes are created to close the search, seven are required without explanations.

.. note::

    Only unary, binary, ternary and limited number of nary propagators over integer variables have a dedicated explanation algorithm.
    Although global constraints over integer variables are compatible with explanations, they should be either accurately explained or reformulated to fully benefit from explanations.


Cause
^^^^^

A cause implements ``ICause`` and must defined the ``boolean why(RuleStore ruleStore, IntVar var, IEventType evt, int value)`` method.
Such a method add new *event filtering* rules to the ruleStore in parameter in order to *filter* relevant events among all generated during the search.
Every time a variable is modified, the cause is specified in order to compute explanations afterwards.
For instance, when a propagator updates the bound of an integer variable, the cause is the propagator itself.
So do decisions, objective manager, etc.

Computing explanations
^^^^^^^^^^^^^^^^^^^^^^

When a contradiction occurs during propagation, it can only be thrown by:

- a propagator which detects unsatisfiability, based on the current domain of its variables;
- or a variable whom domain became empty.

Consequently, in addition to causes, variables can also explain the current state of their domain.
Computing the explanation of a failure consists in going up in the stack of all events generated in the current branch of the search tree and filtering the one relative to the conflict.
The entry point is either a the unsatisfiabable propagator or the empty variable.

.. note::

    Explanations can be computed without failure. The entry point is a variable, and only removed values can be explained.


Each propagator embeds its own explanation algorithm which relies on the relation it defines over variables.


.. warning::

    Even if a naive (and weak) explanation algorithm could be provided by all constraints, we made the choice to throw an `SolverException` whenever a propagator does not defined its own explanation algorithm.
    This is restrictive, but almost all non-global constraints support explanation, which enables reformulation.
    The missing explanation schemas will be integrated all needs long.



For instance, here is the algorithm of ``PropGreaterOrEqualX_YC`` (:math:`x \geq y + c`, ``x`` and ``y`` are integer variables, ``c`` is a constant):

.. literalinclude:: /../../choco-solver/src/main/java/org/chocosolver/solver/constraints/binary/PropGreaterOrEqualX_YC.java
   :language: java
   :lines: 112-122
   :linenos:

The first lines indicates that the deduction is due to the application of the propagator (l.2), maybe through reification.
Then, depending on the variable touched by the deduction, either the lower bound of ``y`` (l.4) or the upper bound of ``x`` (l.6) explains the deduction.
Indeed, such a propagator only updates lower bound of ``y`` based on the upper bound of ``x`` and *vice versa*.

Let consider that the deduction involves ``x`` and is explained by the lower bound of ``y``.
The lower bound ``y`` needs to be explained.
A new rule is added to the ruleStore to specify that events on the lower bound of ``y`` needs to be kept during the event stack analyse (only events generated before the current are relevant).
When such events are found, the ruleStore can be updated, until the first event is analyzed.

The results is a set of branching decisions, and a set a propagators, which applied altogether leads the conflict and thus, explained it.


Explanations for the system
---------------------------

Explanations for the system, which try to reduce the search space, differ from the ones giving feedback to a user about the unsatisfiability of its model.
Both rely on the capacity of the explanation engine to motivate a failure, during the search form system explanations and once the search is complete for user ones.

.. important::

    Most of the time, explanations are raw and need to be processed to be easily interpreted by users.


Conflict-based backjumping
^^^^^^^^^^^^^^^^^^^^^^^^^^

When Conflict-based Backjumping (CBJ) is plugged-in, the search is hacked in the following way.
On a failure, explanations are retrieved.
From all left branch decisions explaining the failure, the last taken, *return decision*, is stored to jump back to it.
Decisions from the current one to the return decision (excluded) are erased.
Then, the return decision is refuted and the search goes on.
If the explanation is made of no left branch decision, the problem is proven to have no solution and search stops.


**Factory**: ``solver.explanations.ExplanationFactory``

**API**: ::

    CBJ.plugin(Solver solver, boolean nogoodsOn, boolean userFeedbackOn)


+ *solver*: the solver to explain.
+ *nogoodsOn*: set to `true` to extract nogood from each conflict,. Extracting nogoods slows down the overall resolution but can reduce the search space.
+ *userFeedbackOn*: set to `true` to store the very last explanation of the search (recommended value: `false`).

Dynamic backtracking
^^^^^^^^^^^^^^^^^^^^

This strategy, Dynamic backtracking (DBT) corrects a lack of deduction of Conflict-based backjumping.
On a failure, explanations are retrieved.
From all left branch decisions explaining the failure, the last taken, *return decision*, is stored to jump back to it.
Decisions from the current one to the return decision (excluded) are maintained, only the return decision is refuted and the search goes on.
If the explanation is made of no left branch decision, the problem is proven to have no solution and search stops.


**Factory**: ``solver.explanations.ExplanationFactory``

**API**: ::

    DBT.plugin(Solver solver, boolean nogoodsOn, boolean userFeedbackOn)

+ *solver*: the solver to explain.
+ *nogoodsOn*: set to `true` to extract nogood from each conflict,. Extracting nogoods slows down the overall resolution but can reduce the search space.
+ *userFeedbackOn*: set to `true` to store the very last explanation of the search (recommended value: `false`).

Explanations for the end-user
-----------------------------

Explaining the last failure of a complete search without solution provides information about the reasons why a problem has no solution.
For the moment, there is no simplified way to get such explanations.
CBJ and DBT enable retrieving an explanation of the last conflict. ::

    // .. problem definition ..
    // First manually plug CBJ, or DBT
    ExplanationEngine ee = new ExplanationEngine(solver, userFeedbackOn);
    ConflictBackJumping cbj = new ConflictBackJumping(ee, solver, nogoodsOn);
    solver.plugMonitor(cbj);
    if(!solver.findSolution()){
        // If the problem has no solution, the end-user explanation can be retrieved
        System.out.println(cbj.getLastExplanation());
    }

Incomplete search leads to incomplete explanations: as far as at least one decision is part of the explanation, there is no guarantee the failure does not come from that decision.
On the other hand, when there is no decision, the explanation is complete.


.. _440_loops_label:

Search loop
===========

The search loop whichs drives the search is a freely-adapted version PLM [#PLM]_.
PLM stands for: Propagate, Learn and Move.
Indeed, the search loop is composed of three parts, each of them with a specific goal.

- Propagate: it aims at propagating information throughout the constraint network when a decision is made,
- Learn: it aims at ensuring that the search mechanism will avoid (as much as possible) to get back to states that have been explored and proved to be solution-less,
- Move: it aims at, unlike the former ones, not pruning the search space but rather exploring it.

.. [#PLM] Narendra Jussien and Olivier Lhomme. Unifying search algorithms for CSP. Technical report 02-3-INFO, EMN.

Any component can be freely implemented and attached to the search loop in order to customize its behavior.
There exists some pre-defined `Move` and `Learn` implementations, avaiable in :ref:`550_slf`.

**Move**:

:ref:`550_slfdfs`,
:ref:`550_slflds`,
:ref:`550_slfdds`,
:ref:`550_slfhbfs`,
:ref:`550_slfseq`,
:ref:`550_slfrestart`,
:ref:`550_slfrestartonsol`,
:ref:`550_slflns`.

**Learn**:

:ref:`550_slfcbj`,
:ref:`550_slfdbt`,

One can also define its own `Move` or `Learn` implementation, more details are given in :ref:`48_plm`.



.. _44_monitors_label:

Search monitor
==============

Principle
---------

A search monitor is an observer of the search loop.
It gives user access before and after executing each main step of the search loop:

- `initialize`: when the search loop starts and the initial propagation is run,
- `open node`: when a decision is computed,
- `down branch`: on going down in the tree search applying or refuting a decision,
- `up branch`: on going up in the tree search to reconsider a decision,
- `solution`: when a solution is got,
- `restart search`: when the search is restarted to a previous node, commonly the root node,
- `close`: when the search loop ends,
- `contradiction`: on a failure,

With the accurate search monitor, one can easily observe with the search loop, from pretty printing of a solution to learning nogoods from restart, or many other actions.

The interfaces to implement are:

- ``IMonitorInitialize``,
- ``IMonitorOpenNode``,
- ``IMonitorDownBranch``,
- ``IMonitorUpBranch``,
- ``IMonitorSolution``,
- ``IMonitorRestart``,
- ``IMonitorContradiction``,
- ``IMonitorClose``.

Most of them gives the opportunity to do something before and after a step. The other ones are called after a step.

For instance, ``NogoodStoreFromRestarts`` monitors restarts.
Before a restart is done, the nogoods are extracted from the current decision path;
after the restart has been done, the newly created nogoods are added and the nogoods are propagated.
Thus, the framework is almost not intrusive.


 .. literalinclude:: /../../choco-solver/src/main/java/org/chocosolver/solver/search/loop/monitors/NogoodFromRestarts.java
   :language: java
   :lines: 56,77-79, 82-83
   :linenos:


Available search monitors: :ref:`55_smf`.


.. important::

	A search monitor should not interact with the search loop (forcing restart and interrupting the search, for instance).
	This is the goal of the Move component of a search loop :ref:`440_loops_label`.


.. _45_define_search_label:

Defining its own search strategy
================================

One key component of the resolution is the exploration of the search space induced by the domains and constraints.
It happens that built-in search strategies are not enough to tackle the problem.
Or one may want to define its own strategy.
This can be done in three steps: selecting the variable, selecting the value, then making a decision.

The following instructions are based on IntVar, but can be easily adapted to other types of variables.

Selecting the variable
----------------------

An implementation of the ``VariableSelector<V extends Variable>`` interface is needed.
A variable selector specifies which variable should be selected at a fix point.
It is based specifications (ex: smallest domain, most constrained, etc.).
Although it is not required, the selected variable should not be already instantiated to a singleton.
This interface forces to define only one method:

    ``V getVariable(V[] variables)``

 One variable has to be selected from ``variables`` to create a decision on.
 If no valid variable exists, the method is expected to return ``null``.

An implementation of the ``VariableEvaluator<V extends Variable>`` is strongly recommended.
It enables breaking ties. It forces to define only one method:

    ``double evaluate(V variable)``

 An evaluation of the given variable is done wrt the evaluator.
 The variable with the **smallest** value will then be selected.


Here is the code of the ``FirstFail`` variable selector which selects first the variable with the smallest domain.

 .. literalinclude:: /../../choco-solver/src/main/java/org/chocosolver/solver/search/strategy/selectors/variables/FirstFail.java
   :language: java
   :lines: 43-64
   :linenos:


There is a distinction between `VariableSelector` and `VariableEvaluator`.
On the one hand, a `VariableSelector` breaks ties lexicographically, that is, the first variable in the input array which respects the specification is returned. ::

    new IntStrategy(variables,
                    new FirstFail(),
                    new IntDomainMin(),
                    DecisionOperator.int_eq);

On the other hand, a `VariableEvaluator` selects all variables which respect the specifications and let another `VariableEvaluator` breaks ties, if any, or acts like a `VariableSelector`. ::

    new IntStrategy(variables,
                    new VariableSelectorWithTies(new FirstFail(), new Largest()),
                    new IntDomainMin(),
                    DecisionOperator.int_eq);

Let's consider the following array of variables as input `{X,Y,Z}` where `X=[0,3], Y= [0,4]` and `Z=[1,4]`.
Applying the first strategy declared will return `X`.
Applying the second one will return `Z`: `X` and `Z` are batter than `Y` but equivalent compared to `FirstFail` but `Z` is better than `X` compared to `Largest`.


Selecting the value
-------------------

The value to be selected must belong to the variable domain.

For ``IntVar`` the interface ``IntValueSelector`` is required.
It imposes one method:

    ``int selectValue(IntVar var)``

 Return the value to constrain ``var`` with.

.. important::

    A value selector must consider the type of domain of the selected variable. Indeed, a value selector does not store the previous tries (unkike an iterator) and it may happen that, for bounded variable, the refutation of a decision has no effect and a value is selected twice or more.
    For example, consider `IntDomainMiddle` and a bounded variable.


Making a decision
-----------------

A decision is made of a variable, an decision operator and a value.
The decision operator should be selected in ``DecisionOperator`` among:

    ``int_eq``

 For ``IntVar``, represents an instantiation, :math:`X = 3`.
 The refutation of the decision will be a value removal.

    ``int_neq``

 For ``IntVar``, represents a value removal, :math:`X \neq 3`.
 The refutation of the decision will be an instantiation.

    ``int_split``

 For ``IntVar``, represents an upper bound modification, :math:`X \leq 3`.
 The refutation of the decision will be a lower bound modification.

    ``int_reverse_split``

 For ``IntVar``, represents a lower bound modification, :math:`X \geq 3`.
 The refutation of the decision will be an upper bound modification.

    ``set_force``

 For ``SetVar``, represents a kernel addition, :math:`3 \in S`.
 The refutation of the decision will be an envelop removal.

    ``set_remove``

 For ``SetVar``, represents an envelop removal, :math:`3 \notin S`.
 The refutation of the decision will be a kernel addition.

.. attention::

    A particular attention should be made while using ``IntVar`` s and their type of domain.
    Indeed, bounded variables does not support making holes in their domain.
    Thus, removing a value which is not a current bound will be missed, and can lead to an infinite loop.

One can define its own operator by extending ``DecisionOperator``.

    ``void apply(V var, int value, ICause cause)``

  Operations to execute when the decision is applied (left branch).
  It can throw an ``ContradictionException`` if the application is not possible.

    ``void unapply(V var, int value, ICause cause)``

  Operations to execute when the decision is refuted (right branch).
  It can throw an ``ContradictionException`` if the application is not possible.

    ``DecisionOperator opposite()``

  Opposite of the decision operator. *Currently useless*.

    ``String toString()``

  A pretty print of the decision, for logging.


Most of the time, extending ``AbstractStrategy`` is not necessary.
Using specific strategy dedicated to a type of variable, such as ``IntStrategy`` is enough.
The one above has an alternate constructor: ::

    public IntStrategy(IntVar[] scope,
                       VariableSelector<IntVar> varSelector,
                       IntValueSelector valSelector,
                       DecisionOperator<IntVar> decOperator) {...}



And defining your own strategy is really crucial, start by copying/pasting an existing one.
Indeed, decisions are stored in pool managers to avoid creating too many decision objects, and thus garbage collecting too often.

.. _46_define_constraint_label:

Defining its own constraint
===========================


In Choco-|version|, constraints is basically a list of filtering algorithms, called *propagators*.
A propagator is a function from domains to domains which removes impossible values from variable domains.


Structure of a Propagator
-------------------------

A propagator needs to extends the ``Propagator`` abstract class.
Then, a constructor and some methods have to be implemented:

``super(...)``

    a call to ``super()`` is mandatory.
    The list of variables (which determines the index of the variable in the propagator) and the priority (for the propagation engine) are required.
    An optional boolean (``true`` is the default value) can be set to ``false`` to avoid reacting on fine events (see item ``void propagate(int vIdx, int mask)``).
    More precisely, if set to ``false``, the propagator will only be informed of a modification of, at least, one of its variables, without knowing specifically which one(s) and what modifications occurred.

.. important::

    The array of variables given in parameter of a ``Propagator`` constructor is not cloned but referenced.
    That is, if a permutation occurs in the array of variables, all propagators referencing the array will be incorrect.


``ESat isEntailed()``

    This method is mandatory for reification.
    It checks whether the propagator will be always satisfied (``ESat.TRUE``), never satisfied (``ESat.FALSE``) or undefined (``ESat.UNDEFINED``) according to the current state of its domain variables and/or its internal structure.
    By default, it should consider the case where all variables are instantiated.
    For instance, :math:`A \neq B` will always be satisfied when $A=\{0,1,2\}$ and :math:`B=\{4,5\}`.
    For instance, :math:`A = B` will never be satisfied when :math:`A=\{0,1,2\}` and :math:`B=\{4,5\}`.
    For instance, entailment of :math:`A \neq B` cannot be defined when :math:`A=\{0,1,2\}` and :math:`B=\{1,2,3\}`.

This method is also called to check solutions when assertions are enabled, i.e. when the `-ea` JVM option is used.

``void propagate(int evtmask)``

    This method applies the global filtering algorithm of the propagator, that is, from *scratch*.
    It is called once during initial propagation and then on a call to ``forcePropagate(EventType)``.
    There are two available types of event this method can receive: ``EventType.FULL\_PROPAGATION`` and ``EventType.CUSTOM\_PROPAGATION``.
    The latter is propagator-dependent and should be managed by the developer when incrementality is enabled.
    Note that the ``forcePropagate()`` method will call ``propagate(int)`` when the propagator does not have any pending events.
    In other words, it is called once and for all, after many domain modifications.

``void propagate(int vIdx, int mask)``

    This method is the main entry point to the propagator during propagation.
    When the :math:`{vIdx}^{th}` variable of the propagator is modified, data relative to the modification is stored for a future execution of the propagator.
    Then, when the propagation engine has to execute the propagator, a call to this method is done with the data relative to the variable and its modifications.
    One can delegate filtering algorithm to ``propagate(int)`` with a call to ``forcePropagate()`` (see item ``void propagate(int evtmask)``).
    However, developers have to be aware that a propagator will not be informed of a modification it has generated itself.
    That's why a propagator has to be idempotent (see Section~\nameref{properties}) or being aware not to be.

Note that, when conditions enable it, a call to ``setPassive()`` will deactivate the propagator temporary, during the exploration of the sub search space. When the conditions are not met anymore, the propagator is activated again (i.e. on backtrack).

``int getPropagationConditions(int vIdx)``

    This method returns the specific mask indicating the variable events on which the propagator reacts for the :math:`{vIdx}^{th}` variable.
    This method is related to ``propagate(int, int)``: a wrong mask prevents the propagator from being informed of an event occurring on a variable.
    Event masks are not nested and all event masks have to be defined.


Properties
----------

We distinguish two kinds of propagators:

    *Basis* propagators, that ensure constraints to be satisfied.

    *Redundant* (or *Implied*) propagators that come in addition to some basis propagators, in order to get a stronger filtering.


A basis propagator should be idempotent [#fidem]_ .
A redundant propagator does not have to be idempotent:

    Some propagators cannot be idempotent because they are not even monotonic [#fmono]_  (Lagrangian relaxation, use of randomness, etc.),

    Forcing to reach the fix point may decrease performances.


.. [#fidem] **idempotent**: calling a propagator twice has no effect, i.e. calling it with its output domains returns its output domains. In that case, it has reached a fix point.

.. [#fmono] **monotonic**: calling a propagator with two input domains :math:`A` and :math:`B` for which :math:`A \subseteq B` returns two output domains :math:`A'` and :math:`B'` for which :math:`A' \subseteq B'`.


.. important::

    A redundant propagator can directly return ``ESat.TRUE`` in the body of the ``isEntailed()`` method.
    Indeed, it comes in addition to basis propagators that will already ensure constraint satisfaction.


How to make a propagator idempotent?
------------------------------------

Trying to make a propagator idempotent directly may not be straightforward.
We provide three implementation possibilities.

The *coarse* option:

    the propagator will perform its fix point by itself.
    The propagator does not react to fine events.
    The coarse filtering algorithm should be surrounded like this: ::

        long size;
        do{
          size = 0;
          for(IntVar v:vars){
            size+=v.getDomSize();
          }
          // really update domain variables here
          for(IntVar v:vars){
            size-=v.getDomSize();
          }
        }while(size>0);



.. important::

    Domain variable modifier returns a boolean valued to ``true`` if the domain variable has been modified.

.. important::

    In the case of ``SetVar`` or ``GraphVar``, replace ``getDomSize()`` by ``getEnvSize()-getKerSize()``.


The *decomposed*  option:

    Split the original propagator into many propagators so that the fix point is performed through the propagation engine.
    For instance, a channeling propagator :math:`A \Leftrightarrow B` can be decomposed into two propagators :math:`A \Rightarrow B` and :math:`B \Rightarrow A`.
    The propagators can (but does not have to) react on fine events.

The *lazy* option:

    (To be avoided has much as possible) simply post the propagator twice.
    Thus, the fix point is performed through the propagation engine.

.. _48_plm:

Implementing a search loop component
====================================

A search loop is made of three components, each of them dealing with a specific aspect of the search.
Even if many `Move` and `Learn` implementation are already provided, it may be relevant to define its own component.

.. note::

	The `Propagate` component is less prone to be modified, it will not be described here.
	However, its interface is minimalist and can be easily implemented.
	A look to `org.chocosolver.solver.search.loop.PropagateBasic.java` is a good starting point.

The two components can be easily set in the `Solver` search loop: 

``void setMove(Move m)``
	The current `Move` component is replaced by `m`.

``Move getMove()``
	The current `Move` component is returned.
 
`void setLearn(Learn l)` and `Learn getLearn()` are also avaiable.

Having access to the current `Move` (resp. `Learn`) component can be useful to combined it with another one.
For instance, the `MoveLNS` is activated on a solution and creates a partial solution.
It needs another `Move` to find the first solution and to complete the partial solution.

Move
----

Here is the API of `Move`:


``boolean extend(SearchLoop searchLoop)``
	Perform a move when the CSP associated to the current node of the search space is not proven to be not consistent.
	It returns `true` if an extension can be done, `false` when no more extension is possible.
	It has to maintain the correctness of the reversibility of the action by pushing a backup world when needed.	
	An extension is commonly based on a decision, which may be made on one or many variables.
	If a decision is created (thanks to the search strategy), it has to be linked to the previous one.

``boolean repair(SearchLoop searchLoop)``
	Perform a move when the CSP associated to the current node of the search space is proven to be not consistent.
	It returns `true` if a reparation can be done, `false` when no more reparation is possible.
	It has to backtracking backup worlds when needed, and unlinked useless decisions.
	The depth and number of backtracks have to be updated too, and "up branch" search monitors of the search loop have to called
 	(be careful, when many `Move` are combined).
 

``Move getChildMove()``
	It returns the child `Move` or `null`.

``void setChildMove(Move aMove)``                  
	It defined the child `Move` and erases the previously defined one, if any.

``boolean init()``				     
	Called before the search starts, it should initialize the search strategy, if any, and its child `Move`.
     	It should return `false` if something goes wrong (the problem has trivially no solution), `true` otherwise.	

``AbstractStrategy<V> getStrategy()``		     
	It returns the search strategy in use, which may be `null` if none has been defined.

``void setStrategy(AbstractStrategy<V> aStrategy)``
	It defines a search strategy and erases the previously defined one, that is, a service which computes and returns decisions.
 

``org.chocosolver.solver.search.loop.MoveBinaryDFS.java`` is good starting point to see how a `Move` is implemented.
It defines a Depth-First Search with binary decisions. 

Learn
-----

The aim of the component is to make sure that the search mechanism will avoid (as much as possible) to get back to states that have been explored and proved to be solution-less. Here is the API of `Learn`

``void record(SearchLoop searchLoop)``
	It validates and records a new piece of knowledge, that is, the current position is a dead-end.
	This is alwasy called *before* calling `Move.repair(SearchLoop)`.

``void forget(SearchLoop searchLoop)``
	It forgets some pieces of knowledge.
	This is alwasy called *after* calling `Move.repair(SearchLoop)`.

``org.chocosolver.solver.search.loop.LearnCBJ`` is good, yet not trivial, example of `Learn`.

.. _47_ibex:

Ibex
====


    "IBEX is a C++ library for constraint processing over real numbers.

    It provides reliable algorithms for handling non-linear constraints.
    In particular, round off errors are also taken into account.
    It is based on interval arithmetic and affine arithmetic."
    -- http://www.ibex-lib.org/

To manage continuous constraints with Choco, an interface with Ibex has been done.
It needs Ibex to be installed on your system.
Then, simply declare the following VM options:

.. code-block:: none

    -Djava.library.path=/path/to/Ibex/lib

The path `/path/to/Ibex/lib` points to the `lib` directory of the Ibex installation directory.


Installing Ibex
---------------

See the `installation instructions <http://www.ibex-lib.org/doc/install.html>`_ of Ibex to complied Ibex on your system.
More specially, take a look at `Installation as a dynamic library <http://www.ibex-lib.org/doc/install.html#installation-as-a-dynamic-library>`_
and do not forget to add the ``--with-java-package=org.chocosolver.solver.constraints.real`` configuration option.

Once the installation is completed, the JVM needs to know where Ibex is installed to fully benefit from the Choco-Ibex bridge and declare real variables and constraints.

