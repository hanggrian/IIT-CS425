# [MySQL Basics](https://www.w3schools.com/MySQL/mysql_sql.asp)

There are several non-common SQL operators in this handbook.

| Operator | Denotes |
| --- | --- |
| [ ] | Optional |
| \| | Choices |

## SELECT

```sql
SELECT * FROM table1;
```

## WHERE

```sql
SELECT * FROM table1 WHERE condition1;
```

## AND, OR & NOT

```sql
SELECT * FROM table1 WHERE [NOT] condition1 AND|OR condition2 AND|OR ...;
```

## ORDER BY

```sql
SELECT * FROM table1 ORDER BY `column1`, `column2`, ... [ASC|DESC];
```

## INSERT INTO

```sql
INSERT INTO table1[(`column1`, `column2`, ...)]
  VALUES('value1', 'value2', ...), ('value1', 'value2', ...), ...;
```

## NULL

```sql
SELECT * FROM table1 WHERE `column1` IS [NOT] NULL;
```

## UPDATE

```sql
UPDATE table1 SET `column1` = 'value1', `column2` = 'value2', ...
  WHERE condition1;
```

## DELETE

```sql
DELETE FROM table1 WHERE condition1;
```

## LIMIT

```sql
SELECT * FROM table1 WHERE condition1 LIMIT number1;
```

## MIN, MAX, COUNT, AVG & SUM

```sql
SELECT MIN|MAX|COUNT|AVG|SUM(`column1`) FROM table1 WHERE condition1;
```

## LIKE

```sql
SELECT * FROM table1 WHERE `column1` LIKE pattern1;
```

### Wildcards

| Symbol | Description |
| :--- | --- |
| % | 	Represents zero or more characters. |
| _ | Represents a single character. |

## IN

```sql
SELECT * FROM table1 WHERE `column1` IN('value1', 'value2', ...);
SELECT * FROM table1 WHERE `column1` IN(SELECT * FROM table2);
```

## BETWEEN

```sql
SELECT * FROM table1 WHERE `column1` BETWEEN 'value1' AND|OR 'value2';
```

## AS

```sql
SELECT `column1` AS `alias1` FROM table1;
SELECT * FROM table1 AS t;
```

## JOIN

```sql
SELECT * FROM table1 INNER|LEFT|RIGHT|CROSS JOIN table2 ON condition1;
```

### Join types

| Inner | Left | Right | Cross |
| --- | --- | --- | --- |
| ![INNER JOIN image.](https://www.w3schools.com/MySQL/img_innerjoin.gif) | ![LEFT JOIN image.](https://www.w3schools.com/MySQL/img_leftjoin.gif) | ![RIGHT JOIN image.](https://www.w3schools.com/MySQL/img_rightjoin.gif) | ![CROSS JOIN image.](https://www.w3schools.com/MySQL/img_crossjoin.png) |

### Self join

```sql
SELECT * FROM table1 T1, table1 T2 WHERE condition1;
```

## UNION

```sql
SELECT * FROM table1 UNION [ALL] SELECT * FROM table2;
```

## GROUP BY

```sql
SELECT * FROM table1 WHERE condition1
  GROUP BY `column1`, `column2`, ...
  ORDER BY `column1`, `column2`, ...;
```

## HAVING

```sql
SELECT * FROM table1 GROUP BY `column1`, `column2`, ... HAVING condition1;
```

## EXISTS

```sql
SELECT * FROM table1
  WHERE EXISTS(SELECT `column1` FROM table2 WHERE condition1);
```

## ANY & ALL

```sql
SELECT * FROM table1
  WHERE `column1` = ANY|ALL(SELECT `column1` FROM table2 WHERE condition1);
```

## INSERT INTO SELECT

```sql
INSERT INTO table2[(`column1`, `column2`, ...)]
  SELECT column1, column2, ... FROM table1 WHERE condition1;
```

## CASE

```sql
CASE
  WHEN condition1 THEN 'value1'
  WHEN condition2 THEN 'value2'
  ...
  ELSE 'defaultValue'
END;
```

## IFNULL & COALESCE

```sql
SELECT `UnitPrice` * (`UnitsInStock` + IFNULL|COALESCE(`UnitsOnOrder`, 0))
  FROM Products;
```

## Comments

```sql
-- Hello World
```

## Operators

<table>
<tr><th>Arithmetic</th><th>Bitwise</th></tr>
<tr><td>

| Operator | Description |
| --- | --- |
| + | Add |
| - | Subtract |
| * | Multiply |
| / | Divide |
| % | Modulo |

</td><td>

| Operator | Description |
| --- | --- |
| & | AND |
| \| | OR |
| ^ | Exclusive OR |

</td></tr>
</table>

<table>
<tr><th>Comparison</th><th>Compound</th></tr>
<tr><td>

| Operator | Description |
| --- | --- |
| = | Equal to |
| > | Greater than |
| < | Less than |
| >= | Greater than or equal to |
| <= | Less than or equal to |
| <> | Not equal to |

</td><td>

| Operator | Description |
| --- | --- |
| += | Add equals |
| -= | Subtract equals |
| *= | Multiply equals |
| /= | Divide equals |
| %= | Modulo equals |
| &= | AND equals |
| ^-= | Exclusive AND equals |
| \|*= | OR equals |

</td></tr>
</table>

| Operator | Description |
| --- | --- |
| `ALL` | TRUE if all of the subquery values meet the condition. |
| `AND` | TRUE if all the conditions separated by AND is TRUE. |
| `ANY` | TRUE if any of the subquery values meet the condition. |
| `BETWEEN` | TRUE if the operand is within the range of comparisons. |
| `EXISTS` | TRUE if the subquery returns one or more records. |
| `IN` | TRUE if the operand is equal to one of a list of expressions. |
| `LIKE` | TRUE if the operand matches a pattern. |
| `NOT` | Displays a record if the condition(s) is NOT TRUE. |
| `OR` | TRUE if any of the conditions separated by OR is TRUE. |
| `SOME` | TRUE if any of the subquery values meet the condition. |
