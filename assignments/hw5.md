<!-- hotfix: KaTeX -->
<!-- https://github.com/yzane/vscode-markdown-pdf/issues/21/ -->
<script type="text/javascript" src="http://cdn.mathjax.org/mathjax/latest/MathJax.js?config=TeX-AMS-MML_HTMLorMML"></script>
<script type="text/x-mathjax-config">MathJax.Hub.Config({ tex2jax: { inlineMath: [['$', '$']] }, messageStyle: 'none' });</script>

# [Homework 1.5](https://github.com/hanggrian/IIT-CS425/blob/assets/assignments/hw5.pdf): Workplace OLAP

> Give the answers (assume `ORDER BY` salary).
>
> row_num | first_name | last_name | salary
> ---: | --- | ---| ---:
> 1 | Karen | Colmenares | 2500
> 2 | Guy | Himuro | 2600
> 3 | Irene | Mikkilineni | 2700
> 4 | Sigal | Tobias | 2800
> 5 | Shelli | Baida | 2900
> 6 | Alexander | Khoo | 3100
> 7 | Britney | Everett | 3900
> 8 | Sarah | Bell | 4000
> 9 | Diana | Lorentz | 4200
> 10 | Jennifer | Whalen | 4400
> 11 | David | Austin | 4800
> 12 | Valli | Pataballa | 4800
> 13 | Bruce | Ernst | 6000
> 14 | Pat | Fay | 6000
> 15 | Charles | Johnson | 6200

### SQL initialization

```sql
CREATE SCHEMA IF NOT EXISTS WorkplaceOLAP;
USE WorkplaceOLAP;

DROP TABLE IF EXISTS Salaries;

CREATE TABLE Salaries(
  `row_num` INT AUTO_INCREMENT PRIMARY KEY,
  `first_name` VARCHAR(20) NOT NULL,
  `last_name` VARCHAR(20) NOT NULL,
  `salary` DECIMAL NOT NULL
);
```

[View full code](https://github.com/hanggrian/IIT-CS425/blob/main/workplace-olap/initialize.sql)
/ [data](https://github.com/hanggrian/IIT-CS425/blob/main/workplace-olap/data.sql)

## Problem 1

> `FIRST_VALUE()` = [give row num as answer]

> **Definition**: `FIRST_VALUE` returns the lowest salary from $n$-th row.

Because the table is already sorted (by salary), the first row will always be
lower than current row. **Therefore the result is 2500**.

```sql
SELECT `row_num`, FIRST_VALUE(`salary`) OVER(ORDER BY `salary`) FROM Salaries;
```

![Screenschot for answer 1.](https://github.com/hanggrian/IIT-CS425/raw/assets/workplace-olap/1.png)

## Problem 2

> `LAST_VALUE`() = [give row num as answer]

> **Definition**: `LAST_VALUE` returns the highest salary from $n$-th row.

Because the table is already sorted (by salary), the current row will always be
higher than any previous row. **Therefore the result is salary of current row**.

```sql
SELECT `row_num`, LAST_VALUE(`salary`) OVER(ORDER BY `salary`) FROM Salaries;
```

![Screenschot for answer 2.](https://github.com/hanggrian/IIT-CS425/raw/assets/workplace-olap/2.png)

## Problem 3

> `LEAD(2)` for Guy = [give row num as answer]

> **Definition**: `LEAD` returns the last $n$ row.

The last 2 row of *Guy (2)* is *Sigal (4)*. **The salary is $\bf 2800$**.

```sql
SELECT `row_num`, `LEAD`
  FROM (SELECT *, LEAD(`salary`, 2) OVER(ORDER BY `salary`) AS `LEAD`
    FROM Salaries) AS s
  WHERE `first_name` = 'Guy';
```

![Screenschot for answer 3.](https://github.com/hanggrian/IIT-CS425/raw/assets/workplace-olap/3.png)

## Problem 4

> `LAG(4)` for Pat = [give row num as answer]

> **Definition**: `LAG` returns the last $n$ row.

The last 4 row of *Pat (14)* is *Jennifer (10)*. **The salary is $\bf 4400$**.

```sql
SELECT `row_num`, `LAG`
  FROM (SELECT *, LAG(`salary`, 4) OVER(ORDER BY `salary`) AS `LAG`
    FROM Salaries) AS s
  WHERE `first_name` = 'Pat';
```

![Screenschot for answer 4.](https://github.com/hanggrian/IIT-CS425/raw/assets/workplace-olap/4.png)

## Problem 5

> `RANK()` for Valli = [give value]

> **Definition**: `RANK` returns the rank of $n$-th row.

*Valli (12)* and *David (11)* have the same salary, therefore the same rank.
**The rank is $\bf 11$, because $\bf 11 < 12$**.

```sql
SELECT `row_num`, `RANK`
  FROM (SELECT *, RANK() OVER(ORDER BY `salary`) AS `RANK` FROM Salaries) AS s
  WHERE `first_name` = 'Valli';
```

![Screenschot for answer 5.](https://github.com/hanggrian/IIT-CS425/raw/assets/workplace-olap/5.png)

## Problem 6

> `RANK()` for BRUCE = [give value]

> **Definition**: `RANK` returns the rank of $n$-th row.

*Bruce (13)* and *Pat (14)* have the same salary, therefore the same rank. **The
rank is $\bf 13$, because $13 < 14$**.

```sql
SELECT `row_num`, `RANK`
  FROM (SELECT *, RANK() OVER(ORDER BY `salary`) AS `RANK` FROM Salaries) AS s
  WHERE `first_name` = 'Bruce';
```

![Screenschot for answer 6.](https://github.com/hanggrian/IIT-CS425/raw/assets/workplace-olap/6.png)

## Problem 7

> `DENSE_RANK()` for Valli = [give value]

> **Definition**: `DENSE_RANK` returns the rank of $n$-th row, with a condition
  that the difference between each connecting rank is 1.

There is no duplicate salary found before *Valli (12)*. **Therefore the result
is $\bf 11$, the same as `RANK`**.

```sql
SELECT `row_num`, `DENSE_RANK`
  FROM (SELECT *, DENSE_RANK() OVER(ORDER BY `salary`) AS `DENSE_RANK`
    FROM Salaries) AS s
  WHERE `first_name` = 'Valli';
```

![Screenschot for answer 7.](https://github.com/hanggrian/IIT-CS425/raw/assets/workplace-olap/7.png)

## Problem 8

> `DENSE_RANK()` for BRUCE = [give value]

> **Definition**: `DENSE_RANK` returns the rank of $n$-th row, with a condition
  that the difference between each connecting rank is 1.

There is 1 duplicate salary found before *Bruce (13)*. **Therefore the result
is $\bf 12$**.

```sql
SELECT `row_num`, `DENSE_RANK`
  FROM (SELECT *, DENSE_RANK() OVER(ORDER BY `salary`) AS `DENSE_RANK`
    FROM Salaries) AS s
  WHERE `first_name` = 'Bruce';
```

![Screenschot for answer 8.](https://github.com/hanggrian/IIT-CS425/raw/assets/workplace-olap/8.png)

## Problem 9

> `ROW_NUMBER()` for Valli = [give value]

> **Definition**: `ROW_NUMBER` returns the number of $n$-th row.

Because there is already a column `row_num`, they represent the same value.
__The result is *Valli (12)*__.

```sql
SELECT `row_num`, `ROW_NUMBER`
  FROM (SELECT *, ROW_NUMBER() OVER(ORDER BY `salary`) AS `ROW_NUMBER`
    FROM Salaries) AS s
  WHERE `first_name` = 'Valli';
```

![Screenschot for answer 9.](https://github.com/hanggrian/IIT-CS425/raw/assets/workplace-olap/9.png)

## Problem 10

> `ROW_NUMBER()` for Bruce = [give value]

> **Definition**: `ROW_NUMBER` returns the number of $n$-th row.

Because there is already a column `row_num`, they represent the same value.
__The result is *Bruce (13)*__.

```sql
SELECT `row_num`, `ROW_NUMBER`
  FROM (SELECT *, ROW_NUMBER() OVER(ORDER BY `salary`) AS `ROW_NUMBER`
    FROM Salaries) AS s
  WHERE `first_name` = 'Bruce';
```

![Screenschot for answer 10.](https://github.com/hanggrian/IIT-CS425/raw/assets/workplace-olap/10.png)

## Problem 11

> `PERCENT_RANK()` for Valli = [give value]

> **Definition**: `PERCENT_RANK` returns a percentage of rank, in form of double
  ranging from $0$ to $1$.

*Valli (12)* and *David (11)* has the same salary, therefore the same rank
percentage. **Because $\bf 11 < 12$, the result is $\bf 11 / 15 = 0.7$**.

```sql
SELECT `row_num`, `PERCENT_RANK`
  FROM (SELECT *, PERCENT_RANK() OVER(ORDER BY `salary`) AS `PERCENT_RANK`
    FROM Salaries) AS s
  WHERE `first_name` = 'Valli';
```

![Screenschot for answer 11.](https://github.com/hanggrian/IIT-CS425/raw/assets/workplace-olap/11.png)

## Problem 12

> `NTILE(4)` = [give ranges of row numbers]

> **Definition**: `NTILE` returns division of range from current row to the
  next $n$-th row.

The table length is 15, **therefore the division are 4, 4, 4, and 3**.

```sql
SELECT `row_num`, NTILE(4) OVER(ORDER BY `salary`) FROM Salaries;
```

![Screenschot for answer 12.](https://github.com/hanggrian/IIT-CS425/raw/assets/workplace-olap/12.png)

## Problem 13

> `CUME_DIST()` for row 3 = [give value]

> **Definition**: `CUME_DIST` stands for **cumulative distribution**, which is
  number of rows with values less than or equal to that row's value divided by
  the total number of rows.

Because the table is already sorted, the current row will always have higher
salary than the last one. **The result is $\bf 3/15 = 0,2$**.

```sql
SELECT `row_num`, `CUME_DIST_3`
  FROM (SELECT *, CUME_DIST() OVER(ORDER BY `salary`) AS `CUME_DIST_3`
    FROM Salaries) AS s
  WHERE `row_num` = 3;
```

![Screenschot for answer 13.](https://github.com/hanggrian/IIT-CS425/raw/assets/workplace-olap/13.png)

## Problem 14

> `CUME_DIST()` for row 12 = [give value]

> **Definition**: `CUME_DIST` stands for **cumulative distribution**, which is
  number of rows with values less than or equal to that row's value divided by
  the total number of rows.

Because the table is already sorted, the current row will always have higher
salary than the last one. **The result is $\bf 12/15 = 0,8$**.

```sql
SELECT `row_num`, `CUME_DIST_12`
  FROM (SELECT *, CUME_DIST() OVER(ORDER BY `salary`) AS `CUME_DIST_12`
    FROM Salaries) AS s
  WHERE `row_num` = 12;
```

![Screenschot for answer 14.](https://github.com/hanggrian/IIT-CS425/raw/assets/workplace-olap/14.png)
