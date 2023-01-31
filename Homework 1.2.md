# CS425: [Homework 1.2](https://github.com/hendraanggrian/IIT-CS425/raw/assets/Homework%201.2.pdf)

> Database schema from Homework 1.1 is used.

A university database usually revolves around 3 main entities: **Students**,
**Lecturers**, and **Classes**. But this would be overly simple and
insufficient. Given a larger set of example, it became clear that extra tables
are needed to maintain a correct format of data.

## *Registrations*

For example, with just *Students* and *Classes*, multiple students enrolling the
same class would require multiple entries of *Classes*.

<table>
<tr><th>Students</th><th>Classes</th></tr>
<tr><td>

| **id** | name |
| --- | --- |
| **0** | Mike |
| **1** | John |

</td><td>

| **id** | name | student_id |
| --- | --- | --- |
| **0** | CS101 | 0 |
| **1** | CS101 | 1 |

</td></tr>
</table>

The problem with *Classes* table above is that its perception can be misleading.
Two entries in the table would suggest that there are 2 classrooms, while in
fact there is only 1 classroom with 2 students.

To mitigate this behavior, *Registrations* table bridge the connection between
*Students* and *Classes*.

<table>
<tr><th>Students</th><th>Classes</th><th>Registrations</th></tr>
<tr><td>

| **id** | name |
| --- | --- |
| **0** | Mike |
| **1** | John |

</td><td>

| **id** | name |
| --- | --- |
| **0** | CS101 |

</td><td>

| student_id | class_id |
| --- | --- |
| 0 | 0 |
| 1 | 0 |

</td></tr>
</table>

## *Courses*

Every year, a class with the same name is repeated over and over again. They are
unrelated from the class last year, but may share some of the same elements.

<table>
<tr><th>Classes</th></tr>
<tr><td>

| id | name |
| --- | --- |
| 0 | CS101 |
| 1 | CS101 |

</td></tr>
</table>

In this situation, I propose an extra table *Courses* to keep tabs of how many
classes it represents. This table, unlike other tables, has string as its
primary key, because course codes are unique (e.g.: CS425, CS430, etc.).

<table>
<tr><th>Classes</th><th>Courses</th></tr>
<tr><td>

| id | course_id |
| --- | --- |
| 0 | CS101 |
| 1 | CS101 |

</td><td>

| id | name |
| --- | --- |
| CS101 | Database Organization |

</td></tr>
</table>

## *Others*

Some classes may have multiple events repeated every week. Based on this
assumption, there should be a one-to-many relationship between *Classes* and a
new table *Schedules*.

There is also a group *Person*, which is not related to SQL design. It is a
superclass of *Students* and *Lecturers* in an OOP pattern. They would share
base elements, which in this case, are `name` and `date_join`.
