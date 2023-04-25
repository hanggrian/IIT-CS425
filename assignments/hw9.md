<!-- hotfix: KaTeX -->
<!-- https://github.com/yzane/vscode-markdown-pdf/issues/21/ -->
<script type="text/javascript" src="http://cdn.mathjax.org/mathjax/latest/MathJax.js?config=TeX-AMS-MML_HTMLorMML"></script>
<script type="text/x-mathjax-config">MathJax.Hub.Config({ tex2jax: { inlineMath: [['$', '$']] }, messageStyle: 'none' });</script>

# [Homework 1.9](https://github.com/hendraanggrian/IIT-CS425/blob/assets/assignments/hw9.pdf): Functional dependencies

> You are given the below functional dependencies for the
  relation $R(A\ B\ C\ D\ E\ F)$,
>
> $$
> \begin{array}{llll}
>   F = \{ \\
>   & A & \to & BC \\
>   & C & \to & DA \\
>   & D & \to & E \\
>   & AD & \to & F \\
>   \}
> \end{array}
> $$

## Problem 1

> What are the keys for the relation?

![The NMF diagram.](https://github.com/hendraanggrian/IIT-CS425/raw/assets/functional-dependency/nmf.png)

[View diagram file](https://github.com/hendraanggrian/IIT-CS425/blob/main/functional-dependency/nmf.drawio)

### Step 1: Break down right parts

$$
\begin{array}{lll}
  A & \to & B \\
  A & \to & C \\
  C & \to & D \\
  C & \to & A \\
  D & \to & E \\
  AD & \to & F
\end{array}
$$

### Step 2: Find superkeys

$$
\begin{array}{llll}
  A^+ = \{ABCDEF\} & \textsf{A is superkey} \\
  B^+ = \{B\} & \textsf{B is not superkey} \\
  C^+ = \{ABCDEF\} & \textsf{C is superkey} \\
  D^+ = \{DE\} & \textsf{D is not superkey} \\
  E^+ = \{E\} & \textsf{E is not superkey} \\
  F^+ = \{F\} & \textsf{F is not superkey}
\end{array}
$$

Therefore, there relation keys are $\{A,C\}$.

## Problem 2

> Is the relation in 1NF/2NF/3NF/BCNF? Provide an explanation for your answer.

Backtracking from the highest order:

- Not 3NF/BCNF, because there is transitive dependency $C \to D \to E$.
- Not 2NF, because there is partial dependency $AD \to F$.
- Therefore, **the relation is 1NF**.

## Problem 3

> Compute the canonical (minimal) cover of F.

$$
\begin{array}{lll}
  A & \to & BC \\
  C & \to & DA \\
  D & \to & E \\
  AD & \to & F
\end{array}
$$

### Step 1: Eliminate redundancy

It can be concluded that $C \to F$ based on relation:

$$
\begin{array}{lll}
  C & \to & DA \\
  AD & \to & F
\end{array}
$$

Below is an updated relations, there is no more to deduce.

$$
\begin{array}{lll}
  A & \to & BC \\
  C & \to & F \\
  D & \to & E
\end{array}
$$

### Step 2: Break down right parts

Breaking down $A \to BC$ into:

$$
\begin{array}{lll}
  A & \to & B \\
  A & \to & C
\end{array}
$$

Below is the result.

$$
\begin{array}{lll}
  A & \to & B \\
  A & \to & C \\
  C & \to & F \\
  D & \to & E
\end{array}
$$

Therefore, **the canonical cover
is $\bf \{A \to B, A \to B, C \to F, D \to E\}$**.
