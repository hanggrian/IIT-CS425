# [Relational Algebra](https://www.geeksforgeeks.org/introduction-of-relational-algebra-in-dbms/)

- [Selection ($\sigma$)](#selection)
- [Projection ($\pi$)](#projection)
- [Union ($\cup$)](#union)
- [Intersection ($\cap$)](#intersection)
- [Set-difference ($-$)](#set-difference)
- [Rename ($\rho$)](#rename)
- [Cross-product ($\times$)](#cartesian-product)
- [Join ($\bowtie$)](#join)

## Operations

### Selection ($\sigma$)

$$\sigma_c (R)$$

Where $c$ is selection condition which is a boolean expression (condition), $R$
is a relational algebra expression, whose result is a relation.

> #### Example
>
> | Roll | Name | Department | Fees | Team |
> | --- | --- | --- | --- | --- |
> | 1 | Bikash | CSE | 22000 | A |
> | 2 | Josh | CSE | 34000 | A |
> | 3 | Kevin | ECE | 36000 | C |
> | 4 | Ben | ECE | 56000 | D |
>
> Select all the student of Team A.
>
> $$\sigma_\text{Team = 'A'} (\text{Student})$$
>
> Select all the students of department ECE whose fees is greater than equal to
  10000 and belongs to Team other than A.
> $$\sigma_\text{Fees >= 10000} (\sigma_\text{Class != 'A'} (\text{Student}))$$

### Projection ($\pi$)

$$\pi_A(R)$$

Where $A$ is the attribute list, it is the desired set of attributes from the
attributes of relation $R$, symbol $\pi$ is used to denote the Project operator,
$R$ is generally a relational algebra expression, which results in a relation.

> #### Example
>
> | Class | Dept | Position |
> | --- | --- | --- |
> | 5 | CSE | Assistant Professor |
> | 5 | CSE | Assistant Professor |
> | 6 | EE | Assistant Professor |
> | 6 | EE | Assistant Professor |
>
> Project class and Dept from Faculty.
>
> $$\pi_\text{Class, Dept} (\text{Faculty})$$
>
> Project position from Faculty.
>
> $$\pi_\text{Position} (\text{Faculty})$$

### Union ($\cup$)

$$A \cup S$$

Where $A$ and $S$ are the relations, symbol $\cup$ is used to denote the Union
operator.

> #### Example
>
> <table>
> <tr><th>Student</th><th>Faculty</th></tr>
> <tr><td>

| First | Last |
| --- | --- |
| Aisha | Arora |
| Bikash | Dutta |
| Makku | Singh |
| Raju | Chopra |

> </td><td>

| FirstN | LastN |
| --- | --- |
| Raj | Kumar |
| Honey | Chand |
| Makku | Singh |
| Karan | Rao |

> </td></tr>
> </table>
>
> Student UNION Faculty.
>
> $$\text{Student} \cup \text{Faculty}$$

### Intersection ($\cap$)

$$A \cap S$$

Where $A$ and $S$ are the relations, symbol $\cap$ is used to denote the
Intersection operator.

$$
A \cap B = B \cap A\\
A \cap (B \cap C) = (A \cap B) \cap C
$$

### Set-difference ($-$)

$$A - S$$

Where $A$ and $S$ are the relations, symbol $-$ is used to denote the Minus
operator.

$$A - B \ne B - A \\$$

### Rename ($\rho$)

$$\rho_x (R)$$

Where the symbol $\rho$ is used to denote the Rename operator and $R$ is the
result of the sequence of operation or expression which is saved with the
name $X$.

> #### Example
>
> | Sno | Name |
> | --- | --- |
> | 2600 | Ronny |
> | 2655 | Raja |
>
> Query to rename the relation Student as Male Student and the attributes of
> Student – RollNo, SName as (Sno, Name).
>
> $$\rho_\text{MaleStudent(Sno, Name)} \ \pi_\text{RollNo, SName} (\sigma_\text{Condition} (\text{Student}))$$

### Cross-product ($\times$)

$$A \times S$$

Where $A$ and $S$ are the relations, the symbol $\times$ is used to denote the
Cross Product operator.

> #### Example
>
> <table>
> <tr><th>Student</th><th>Faculty</th></tr>
> <tr><td>

| SNO | FNAME | LNAME |
| --- | --- | --- |
| 1 | Albert | Singh |
| 2 | Nora | Fatehi |

> </td><td>

| ROLLNO | AGE |
| --- | --- |
| 5 | 18 |
| 9 | 21 |

> </td></tr>
> </table>
>
> On applying Cross-product on Student and Detail.
>
> $$\text{Student} \times \text{Details}$$

### Join ($\bowtie$)

> #### Example
>
> <table>
> <tr><th>Employee</th><th>Dept</th></tr>
> <tr><td>

| Name | EmpId | DeptName |
| --- | --- | --- |
| Harry | 3415 | Finance |
| Sally | 2241 | Sales |
| George | 3401 | Finance |
| Harriet | 2202 | Sales |

> </td><td>

| DeptName | Manager |
| --- | --- |
| Finance | George |
| Sales | Harriet |
| Production | Charles |

> </td></tr>
> </table>
>
> On applying Cross-product on Student and Detail.
>
> $$\text{Employee} \bowtie \text{Dept}$$
