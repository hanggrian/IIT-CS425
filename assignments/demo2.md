<!-- hotfix: KaTeX -->
<!-- https://github.com/yzane/vscode-markdown-pdf/issues/21/ -->
<script type="text/javascript" src="http://cdn.mathjax.org/mathjax/latest/MathJax.js?config=TeX-AMS-MML_HTMLorMML"></script>
<script type="text/x-mathjax-config">MathJax.Hub.Config({ tex2jax: { inlineMath: [['$', '$']] }, messageStyle: 'none' });</script>

# [Demo 2](https://github.com/hanggrian/IIT-CS425/blob/assets/assignments/demo2.pdf): Functional Dependency

## Problem 1

> Given the Relation $R(A,B,C,D,E)$ with functional
  dependencies
>
> $$
> \begin{array}{llll}
>   F = \{ \\
>   & CE & \to & D \\
>   & D & \to & B \\
>   & C & \to & A \\
>   \}
> \end{array}
> $$

### Subproblem 1A

> All candidate keys?

Finding determinants $(CE,D,C)$:

$$
\begin{array}{llll}
  (CE)^+ & = & \{CE\} \\
  & = & \{CDE\} & (CE \to D) \\
  & = & \{BCDE\} & (D \to B) \\
  & = & \{ABCDE\} & (C \to A) \\
  & = & \textsf{candidate key} \\
  \\
  (D)^+ & = & \{D\} \\
  & = & \{DB\} & (D \to B) \\
  & = & \textsf{not candidate key} \\
  \\
  (C)^+ & = & \{C\} & \\
  & = & \{AC\} & (C \to A) \\
  & = & \textsf{not candidate key}
\end{array}
$$

**Therefore, the candidate key is $\bf CE$**.

### Subproblem 1B

> Normal forms the Relation, $R$ satisfies?

![The NMF diagram.](https://github.com/hanggrian/IIT-CS425/raw/assets/nmf/demo2.png)

[View diagram file](https://github.com/hanggrian/IIT-CS425/blob/main/nmf/demo2.drawio)

The relation has a candidate key so it is at least 1NF. There are partial
dependencies and a transitive dependency in the relation, therefore it cannot be
2NF and 3NF.

### Subproblem 1C

> If not in 3NF, show the process to take it to 3NF?

To transition into 2NF, all partial dependencies must be excluded:

ID | Relationship | New Table
--- | --- | ---
PD1 | $CE \to D$ | $R1=\{\mathbf{CE}D\}$
PD2 | $C \to A$ | $R2=\{\mathbf{C}A\}$

To transition into 3NF, all transitive dependencies must be excluded:

ID | Relationship | New Table
--- | --- | ---
TD1 | $D \to B$ | $R3=\{\mathbf{D}B\}$

### Subproblem 1D

> Compute the canonical cover of the given function dep, $F$.

#### Step 1: Decompose dependents

Dependents are already decomposed.

$$
\begin{array}{llll}
  F = \{ \\
  & CE & \to & D \\
  & D & \to & B \\
  & C & \to & A \\
  \}
\end{array}
$$

#### Step 2: Identify extraneous against determinants

For $CE \to D$.

$$
\begin{array}{llll}
  (E)^+ & = & \{E\} \\
  & = & \textsf{not extraneous} \\
  \\
  (C)^+ & = & \{C\} \\
  & = & \{AC\} & (C \to A) \\
  & = & \textsf{not extraneous}
\end{array}
$$

#### Step 3: Identify extraneous against dependents

For $CE \to D$.

$$
\begin{array}{llll}
  (CE)^+ & = & \{CE\} \\
  & = & \{ACE\} & (C \to A) \\
  & = & \textsf{not extraneous}
\end{array}
$$

For $D \to B$.

$$
\begin{array}{llll}
  (D)^+ & = & \{D\} \\
  & = & \textsf{not extraneous}
\end{array}
$$

For $C \to A$.

$$
\begin{array}{llll}
  (C)^+ & = & \{C\} \\
  & = & \textsf{not extraneous}
\end{array}
$$

## Problem 2

> Provided $R(A,B,C,D,E,F,G)$, compute the canonical (minimal) cover of
>
> $$
> \begin{array}{llll}
>   F = \{ \\
>   & AD & \to & BF \\
>   & CD & \to & EGC \\
>   & BD & \to & F \\
>   & E & \to & D \\
>   & F & \to & C \\
>   & D & \to & F \\
>   \}
> \end{array}
> $$

#### Step 1: Decompose dependents

Decomposing dependents.

$$
\begin{array}{llll}
  F = \{ \\
  & AD & \to & B \\
  & AD & \to & F \\
  & CD & \to & C \\
  & CD & \to & E \\
  & CD & \to & G \\
  & BD & \to & F \\
  & E & \to & D \\
  & F & \to & C \\
  & D & \to & F \\
  \}
\end{array}
$$

#### Step 2: Identify extraneous against determinants

For $AD \to B$.

$$
\begin{array}{llll}
  (D)^+ & = & \{D\} \\
  & = & \{DF\} & (D \to F) \\
  & = & \{CDF\} & (F \to C) \\
  & = & \{CDEF\} & (CD \to E) \\
  & = & \{CDEFG\} & (CD \to G) \\
  & = & \textsf{not extraneous} \\
  \\
  (A)^+ & = & \{A\} \\
  & = & \textsf{not extraneous}
\end{array}
$$

For $AD \to F$.

$$
\begin{array}{llll}
  (D)^+ & = & \{CDEFG\} \\
  & = & \textsf{not extraneous} \\
  \\
  (A)^+ & = & \{A\} \\
  & = & \textsf{not extraneous}
\end{array}
$$

For $CD \to C$.

$$
\begin{array}{llll}
  (D)^+ & = & \{CDEFG\} \\
  & = & \textsf{extraneous}
\end{array}
$$

Remove it from $CD \to C$ to become $D \to C$.

For $CD \to E$.

$$
\begin{array}{llll}
  (D)^+ & = & \{CDEFG\} \\
  & = & \textsf{extraneous}
\end{array}
$$

Remove it from $CD \to E$ to become $D \to E$.

For $CD \to G$.

$$
\begin{array}{llll}
  (D)^+ & = & \{CDEFG\} \\
  & = & \textsf{extraneous}
\end{array}
$$

Remove it from $CD \to G$ to become $D \to G$.

For $BD \to F$.

$$
\begin{array}{llll}
  (D)^+ & = & \{CDEFG\} \\
  & = & \textsf{not extraneous} \\
  \\
  (B)^+ & = & \{B\} \\
  & = & \textsf{not extraneous}
\end{array}
$$

Therefore the new set is:

$$
\begin{array}{llll}
  F2 = \{ \\
  & AD & \to & B \\
  & AD & \to & F \\
  & D & \to & C \\
  & D & \to & E \\
  & D & \to & G \\
  & BD & \to & F \\
  & E & \to & D \\
  & F & \to & C \\
  & D & \to & F \\
  \}
\end{array}
$$

### Step 3: Identify extraneous against dependents

For $AD \to B$.

$$
\begin{array}{llll}
  (AD)^+ & = & \{AD\} \\
  & = & \{ACD\} & (D \to C) \\
  & = & \{ACDE\} & (D \to E) \\
  & = & \{ACDEG\} & (D \to G) \\
  & = & \{ACDEFG\} & (D \to F) \\
  & = & \textsf{not extraneous} \\
\end{array}
$$

For $AD \to F$.

$$
\begin{array}{llll}
  (AD)^+ & = & \{ACDEFG\} \\
  & = & \textsf{extraneous} \\
\end{array}
$$

For $D \to C$.

$$
\begin{array}{llll}
  (D)^+ & = & \{D\} \\
  & = & \{CD\} & (D \to C) \\
  & = & \{CDE\} & (D \to E) \\
  & = & \{CDEF\} & (D \to F) \\
  & = & \{CDEFG\} & (D \to G) \\
  & = & \textsf{extraneous} \\
\end{array}
$$

## Problem 3

> Suppose you are given a relation $R=(A,B,C,D,E)$ with the following functional
  dependencies
>
> $$
> \begin{array}{llll}
>   F = \{ \\
>   & BC & \to & ADE \\
>   & D & \to & B \\
>   \}
> \end{array}
> $$

### Subproblem 3A

> Find all candidate keys.

Finding determinants $(BC,D)$:

$$
\begin{array}{llll}
  (BC)^+ & = & \{ABCDE\} & (BC \to ADE) \\
  & = & \textsf{candidate key} \\
  \\
  (D)^+ & = & \{DB\} & (D \to B) \\
  & = & \textsf{not candidate key} \\
\end{array}
$$

### Subproblem 3B

> Identify the best normal form the $R$ satisfies.

The relation is at least 1NF because there is PK.

## Problem 4

> Given, $R=\{A,B,C,D,E,F,G,H\}$
  and
>
> $$
> \begin{array}{llll}
>   F = \{ \\
>   & AC & \to & G \\
>   & D & \to & EG \\
>   & BC & \to & D \\
>   & CG & \to & BD \\
>   & ACD & \to & B \\
>   & CE & \to & AG \\
>   \}
> \end{array}
> $$
>
> Find the canonical cover of $F$.

#### Step 1

Decomposing relation.

$$
\begin{array}{llll}
  F = \{ \\
  & AC & \to & G \\
  & D & \to & E \\
  & D & \to & G \\
  & BC & \to & D \\
  & CG & \to & B \\
  & CG & \to & D \\
  & ACD & \to & B \\
  & CE & \to & A \\
  & CE & \to & G \\
  \}
\end{array}
$$

#### Step 2

Check if $A$ or $C$ is extraneous from $AC \to \ldots$.

$$
\begin{array}{llll}
  (C)^+ & = & \{C\} & \textsf{from } AC \to \ldots \\
  & = & \textsf{not extraneous} \\
  \\
  (A)^+ & = & \{A\} & \textsf{from } AC \to \ldots \\
  & = & \textsf{not extraneous} \\
\end{array}
$$

Check if $B$ or $C$ is extraneous from $BC \to \ldots$.

$$
\begin{array}{llll}
  (B)^+ & = & \{B\} & \textsf{from } BC \to \ldots \\
  & = & \textsf{not extraneous} \\
  \\
  (A)^+ & = & \{A\} & \textsf{from } BC \to \ldots \\
  & = & \textsf{not extraneous} \\
\end{array}
$$

Check if $C$ or $G$ is extraneous from $CG \to \ldots$.

$$
\begin{array}{llll}
  (G)^+ & = & \{G\} & \textsf{from } CG \to \ldots \\
  & = & \textsf{not extraneous} \\
  \\
  (C)^+ & = & \{C\} & \textsf{from } CG \to \ldots \\
  & = & \textsf{not extraneous} \\
\end{array}
$$

Check if $A$, $C$ or $D$ is extraneous from $ACD \to \ldots$.

$$
\begin{array}{llll}
  (D)^+ & = & \{D\} & \textsf{from } ACD \to \ldots \\
  & = & \{DE\} & \textsf{from } D \to E \\
  & = & \{DEG\} & \textsf{from } D \to G \\
  & = & \textsf{not extraneous} \\
  \\
  (C)^+ & = & \{C\} & \textsf{from } ACD \to \ldots \\
  & = & \textsf{not extraneous} \\
  \\
  (A)^+ & = & \{A\} & \textsf{from } ACD \to \ldots \\
  & = & \textsf{not extraneous} \\
\end{array}
$$

Check if $C$ or $E$ is extraneous from $CE \to \ldots$.

$$
\begin{array}{llll}
  (E)^+ & = & \{E\} & \textsf{from } CE \to \ldots \\
  & = & \textsf{not extraneous} \\
  \\
  (C)^+ & = & \{C\} & \textsf{from } CE \to \ldots \\
  & = & \textsf{not extraneous} \\
\end{array}
$$

Cannot go to step 3.
