<!-- hotfix: KaTeX -->
<!-- https://github.com/yzane/vscode-markdown-pdf/issues/21/ -->
<script type="text/javascript" src="http://cdn.mathjax.org/mathjax/latest/MathJax.js?config=TeX-AMS-MML_HTMLorMML"></script>
<script type="text/x-mathjax-config">MathJax.Hub.Config({ tex2jax: { inlineMath: [['$', '$']] }, messageStyle: 'none' });</script>

# [Demo 2](https://github.com/hendraanggrian/IIT-CS425/blob/assets/assignments/demo2.pdf): Functional Dependency

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
  (CE)^+ & = & \{CDE\} & \textsf{from } CE \to D \\
  & = & \{BCDE\} & \textsf{from } D \to B \\
  & = & \{ABCDE\} & \textsf{from } C \to A \\
  & = & \textsf{super key} \\
  \\
  (D)^+ & = & \{DB\} & \textsf{from } D \to B \\
  & = & \textsf{not super key} \\
  \\
  (C)^+ & = & \{AC\} & \textsf{from } C \to A \\
  & = & \textsf{not super key}
\end{array}
$$

### Subproblem 1B

> Normal forms the Relation, $R$ satisfies?

The relation is at least 1NF because there is PK.

### Subproblem 1C

> If not in 3NF, show the process to take it to 3NF?

To determine whether or not a relation is in 3NF, it must have at least one
extraneous attribute to qualify for 2NF. The 3NF evaluation can start after
the relation is separated in 2NF.

### Subproblem 1D

> Compute the canonical cover of the given function dep, $F$.

#### Step 1

Relation is already decomposed.

$$
\begin{array}{llll}
  F = \{ \\
  & CE & \to & D \\
  & D & \to & B \\
  & C & \to & A \\
  \}
\end{array}
$$

#### Step 2

Check if $C$ or $E$ is extraneous from $CE \to \ldots$.

$$
\begin{array}{llll}
  (E)^+ & = & \{E\} & \textsf{from } CE \to \ldots \\
  & = & \textsf{not extraneous} \\
  \\
  (C)^+ & = & \{C\} & \textsf{from } CE \to \ldots \\
  & = & \{AC\} & \textsf{from } A \to C \\
  & = & \textsf{not extraneous} \\
\end{array}
$$

The relation is not 2NF because both are not extraneous.

## Problem 2

> Provided $R(A,B,C,D,E,F,G)$, compute the canonical (minimal) cover
  of
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

#### Step 1

Decomposing relation.

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

#### Step 2

Check if $A$ or $D$ is extraneous from $AD \to \ldots$.

$$
\begin{array}{llll}
  (D)^+ & = & \{D\} & \textsf{from } AD \to \ldots \\
  & = & \{DF\} & \textsf{from } D \to F \\
  & = & \{CDF\} & \textsf{from } F \to C \\
  & = & \{CDEF\} & \textsf{from } CD \to E \\
  & = & \{CDEFG\} & \textsf{from } CD \to G \\
  & = & \textsf{not extraneous} \\
  \\
  (A)^+ & = & \{A\} & \textsf{from } AD \to \ldots \\
  & = & \textsf{not extraneous} \\
\end{array}
$$

Check if $C$ or $D$ is extraneous from $CD \to \ldots$.

$$
\begin{array}{llll}
  (D)^+ & = & \{D\} & \textsf{from } CD \to \ldots \\
  & = & \{DF\} & \textsf{from } D \to F \\
  & = & \{CDF\} & \textsf{from } F \to C \\
  & = & \{CDEF\} & \textsf{from } CD \to E \\
  & = & \{CDEFG\} & \textsf{from } CD \to G \\
  & = & \textsf{extraneous} \\
\end{array}
$$

Since $C$ is included in $(D)^+$, $CD \to \ldots$ becomes $D \to \ldots$.

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

### Step 3

Check if F in F2 is extraneous. For $AD \to B$ in question.

$$
\begin{array}{llll}
  (AD)^+ & = & \{AD\} & \textsf{from } AD \to \ldots \\
  & = & \{ACD\} & \textsf{from } D \to C \\
  & = & \{ACDE\} & \textsf{from } D \to E \\
  & = & \{ACDEG\} & \textsf{from } D \to G \\
  & = & \{ACDEFG\} & \textsf{from } D \to F \\
  & = & \textsf{not extraneous} \\
\end{array}
$$

For $AD \to F$ in question.

$$
\begin{array}{llll}
  (AD)^+ & = & \{AD\} & \textsf{from } AD \to \ldots \\
  & = & \{ACD\} & \textsf{from } D \to C \\
  & = & \{ACDE\} & \textsf{from } D \to E \\
  & = & \{ACDEG\} & \textsf{from } D \to G \\
  & = & \{ACDEFG\} & \textsf{from } D \to F \\
  & = & \textsf{extraneous} \\
\end{array}
$$

*I'm not sure what is the next step here, the examination guide stops at step
3.*

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
  (BC)^+ & = & \{ABCDE\} & \textsf{from } BC \to ADE \\
  & = & \textsf{super key} \\
  \\
  (D)^+ & = & \{DB\} & \textsf{from } D \to B \\
  & = & \textsf{not super key} \\
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
