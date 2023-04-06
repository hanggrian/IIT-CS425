<!-- hotfix: KaTeX -->
<!-- https://github.com/yzane/vscode-markdown-pdf/issues/21/ -->
<script type="text/javascript" src="http://cdn.mathjax.org/mathjax/latest/MathJax.js?config=TeX-AMS-MML_HTMLorMML"></script>
<script type="text/x-mathjax-config">MathJax.Hub.Config({ tex2jax: { inlineMath: [['$', '$']] }, messageStyle: 'none' });</script>

# [Functional Dependency](https://www.guru99.com/dbms-functional-dependency.html)

Functional Dependency (FD) is a constraint that determines the relation of one
attribute to another attribute in a Database Management System (DBMS).
Functional Dependency helps to maintain the quality of data in the database. It
plays a vital role to find the difference between good and bad database design.

A functional dependency is denoted by an arrow $\rightarrow$. The functional
dependency of $X$ on $Y$ is represented by $X \rightarrow Y$. Let’s understand
Functional Dependency in DBMS with example.

### Example

| Employee number | Employee name | Salary | City |
| --- | --- | --- | --- |
| 1 | Dana | 50000 | San Francisco |
| 2 | Francis | 38000 | London |
| 3 | Andrew | 25000 | Tokyo |

In this example, if we know the value of Employee number, we can obtain Employee
Name, city, salary, etc. By this, we can say that the city, Employee Name, and
salary are functionally depended on Employee number.

## Rules of functional dependencies

Below are the Three most important rules for Functional Dependency in Database:

- **Reflexive rule**: If $X$ is a set of attributes and $Y$ is_subset_of $X$,
  then $X$ holds a value of $Y$.
- **Augmentation rule**: When $x \rightarrow y$ holds, and $c$ is attribute set,
  then $ac \rightarrow bc$ also holds. That is adding attributes which do not
  change the basic dependencies.
- **Transitivity rule**: This rule is very much similar to the transitive rule
  in algebra if $x \rightarrow y$ holds and $y \rightarrow z$ holds,
  then $x \rightarrow z$ also holds. $X \rightarrow y$ is called as functionally
  that determines $y$.

## Types of functional fependencies in DBMS

There are mainly four types of Functional Dependency in DBMS. Following are the
types of Functional Dependencies in DBMS:

- Multivalued Dependency
- Trivial Functional Dependency
- Non-Trivial Functional Dependency
- Transitive Dependency

### Multivalued Dependency in DBMS

Multivalued dependency occurs in the situation where there are multiple
independent multivalued attributes in a single table. A multivalued dependency
is a complete constraint between two sets of attributes in a relation. It
requires that certain tuples be present in a relation. Consider the following
Multivalued Dependency Example to understand.

| Car_model | Maf_year | Color |
| --- | --- | --- |
| H001 | 2017 | Metallic |
| H001 | 2017 | Green |
| H005 | 2018 | Metallic |
| H005 | 2018 | Blue |
| H010 | 2015 | Metallic |
| H033 | 2012 | Gray |

In this example, maf_year and color are independent of each other but dependent
on car_model. In this example, these two columns are said to be multivalue
dependent on car_model.

This dependence can be represented like this:

$$
car\_model \rightarrow maf\_year \\
car\_model \rightarrow colour
$$

### Trivial Functional Dependency in DBMS

The Trivial dependency is a set of attributes which are called a trivial if the
set of attributes are included in that attribute.

So, $X \rightarrow Y$ is a trivial functional dependency if $Y$ is a subset
of $X$. Let’s understand with a Trivial Functional Dependency Example.

For example:

| Emp_id | Emp_name |
| --- | --- |
| AS555 | Harry |
| AS811 | George |
| AS999 | Kevin |

Consider this table of with two columns Emp_id and Emp_name.

{Emp_id, Emp_name} -> Emp_id is a trivial functional dependency as Emp_id is a
subset of {Emp_id,Emp_name}. Non Trivial Functional Dependency in DBMS
Functional dependency which also known as a nontrivial dependency occurs
when $A \rightarrow B$ holds true where $B$ is not a subset of $A$. In a
relationship, if attribute $B$ is not a subset of attribute $A$, then it is
considered as a non-trivial dependency.

| Company | CEO | Age |
| --- | --- | --- |
| Microsoft | Satya Nadella | 51 |
| Google | Sundar Pichai | 46 |
| Apple | Tim Cook | 57 |

(Company} -> {CEO} (if we know the Company, we knows the CEO name)

But CEO is not a subset of Company, and hence it’s non-trivial functional
dependency.

### Transitive Dependency in DBMS

A Transitive Dependency is a type of functional dependency which happens
when “t” is indirectly formed by two functional dependencies. Let’s understand
with the following Transitive Dependency Example.

| Company | CEO | Age |
| --- | --- | --- |
| Microsoft | Satya Nadella | 51 |
| Google | Sundar Pichai | 46 |
| Alibaba | Jack Ma | 54 |

{Company} -> {CEO} (if we know the compay, we know its CEO’s name)

{CEO } -> {Age} If we know the CEO, we know the Age

Therefore according to the rule of rule of transitive dependency:

{ Company} -> {Age} should hold, that makes sense because if we know the company
name, we can know his age.

Note: You need to remember that transitive dependency can only occur in a
relation of three or more attributes.
