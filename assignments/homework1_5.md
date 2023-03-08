[View questions](https://github.com/hendraanggrian/IIT-CS425/raw/assets/assignments/homework1_5.pdf)
/ [homepage](https://github.com/hendraanggrian/IIT-CS425/)

# CS425: Homework 1.5

## Schema

| row_num | first_name | last_name | salary |
| ---: | --- | ---| ---: |
| 1 | Karen | Colmenares | 2500.00 |
| 2 | Guy | Himuro | 2600.00 |
| 3 | Irene | Mikkilineni | 2700.00 |
| 4 | Sigal | Tobias | 2800.00 |
| 5 | Shelli | Baida | 2900.00 |
| 6 | Alexander | Khoo | 3100.00 |
| 7 | Britney | Everett | 3900.00 |
| 8 | Sarah | Bell | 4000.00 |
| 9 | Diana | Lorentz | 4200.00 |
| 10 | Jennifer | Whalen | 4400.00 |
| 11 | David | Austin | 4800.00 |
| 12 | Valli | Pataballa | 4800.00 |
| 13 | Bruce | Ernst | 6000.00 |
| 14 | Pat | Fay | 6000.00 |
| 15 | Charles | Johnson | 6200.00 |

```sql
CREATE SCHEMA IF NOT EXISTS WorkplaceDB;
USE WorkplaceDB;

CREATE TABLE Salaries(
  `row_num` INT AUTO_INCREMENT PRIMARY KEY,
  `first_name` VARCHAR(20) NOT NULL,
  `last_name` VARCHAR(20) NOT NULL,
  `salary` DECIMAL NOT NULL
);
```

[View full code](https://github.com/hendraanggrian/IIT-CS425/blob/main/workplace_db/initialize.sql)

## 1. FIRST_VALUE() = [give row num as answer]

```sql
```

![Screenschot for answer 1.](https://github.com/hendraanggrian/IIT-CS425/raw/assets/workplace_db/1.png)

## 2. LAST_VALUE() = [give row num as answer]

```sql
```

![Screenschot for answer 2.](https://github.com/hendraanggrian/IIT-CS425/raw/assets/workplace_db/2.png)

## 3. LEAD(2) for Guy = [give row num as answer]

```sql
```

![Screenschot for answer 3.](https://github.com/hendraanggrian/IIT-CS425/raw/assets/workplace_db/3.png)

## 4. LAG(4) for Pat = [give row num as answer]

```sql
```

![Screenschot for answer 4.](https://github.com/hendraanggrian/IIT-CS425/raw/assets/workplace_db/4.png)

## 5. RANK() for Vali = [give value]

```sql
```

![Screenschot for answer 5.](https://github.com/hendraanggrian/IIT-CS425/raw/assets/workplace_db/5.png)

## 6. RANK() for BRUCE = [give value]

```sql
```

![Screenschot for answer 6.](https://github.com/hendraanggrian/IIT-CS425/raw/assets/workplace_db/6.png)

## 7. DENSE_RANK() for Vali = [give value]

```sql
```

![Screenschot for answer 7.](https://github.com/hendraanggrian/IIT-CS425/raw/assets/workplace_db/7.png)

## 8. DENSE_RANK() for BRUCE = [give value]

```sql
```

![Screenschot for answer 8.](https://github.com/hendraanggrian/IIT-CS425/raw/assets/workplace_db/8.png)

## 9. ROW_NUMBER() for Vali = [give value]

```sql
```

![Screenschot for answer 9.](https://github.com/hendraanggrian/IIT-CS425/raw/assets/workplace_db/9.png)

## 10. ROW_NUMBER() for Bruce = [give value]

```sql
```

![Screenschot for answer 10.](https://github.com/hendraanggrian/IIT-CS425/raw/assets/workplace_db/10.png)

## 11. PERCENT_RANK() for Vali = [give value]

```sql
```

![Screenschot for answer 11.](https://github.com/hendraanggrian/IIT-CS425/raw/assets/workplace_db/11.png)

## 12. NTILE(4) = [give ranges of row numbers]

```sql
```

![Screenschot for answer 12.](https://github.com/hendraanggrian/IIT-CS425/raw/assets/workplace_db/12.png)

## 13. CUME_DIST() for row 3 = [give value]

```sql
```

![Screenschot for answer 13.](https://github.com/hendraanggrian/IIT-CS425/raw/assets/workplace_db/13.png)

## 14. CUME_DIST() for row 12 = [give value]

```sql
```

![Screenschot for answer 14.](https://github.com/hendraanggrian/IIT-CS425/raw/assets/workplace_db/14.png)
