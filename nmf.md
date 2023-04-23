# [Normalization Forms](https://www.guru99.com/database-normalization.html)

Normalization is a database design technique that reduces data redundancy and
eliminates undesirable characteristics like Insertion, Update and Deletion
Anomalies. Normalization rules divides larger tables into smaller tables and
links them using relationships. The purpose of Normalisation in SQL is to
eliminate redundant (repetitive) data and ensure data is stored logically.

The inventor of the [relational model](https://www.guru99.com/relational-data-model-dbms.html)
Edgar Codd proposed the theory of normalization of data with the introduction of
the First Normal Form, and he continued to extend theory with Second and Third
Normal Form. Later he joined Raymond F. Boyce to develop the theory of
Boyce-Codd Normal Form.

### Database Normal Forms

Here is a list of Normal Forms in SQL:

- 1NF (First Normal Form)
- 2NF (Second Normal Form)
- 3NF (Third Normal Form)
- BCNF (Boyce-Codd Normal Form)
- 4NF (Fourth Normal Form)
- 5NF (Fifth Normal Form)
- 6NF (Sixth Normal Form)

The Theory of Data Normalization in MySQL server is still being developed
further. For example, there are discussions even on 6th Normal Form. However, in
most practical applications, normalization achieves its best in 3rd Normal Form.
The evolution of Normalization in SQL theories is illustrated below-

![Database Normal Forms](https://www.guru99.com/images/NormalizationProcess(1).png)

## Database Normalization With Examples

Database Normalization Example can be easily understood with the help of a case
study. Assume, a video library maintains a database of movies rented out.
Without any normalization in database, all information is stored in one table as
shown below. Let's understand Normalization database with normalization example
with solution:

![](https://www.guru99.com/images/NormalizationTable1.png)

Here you see Movies Rented column has multiple values. Now let's move into 1st
Normal Forms:

## 1NF Rules

- Each table cell should contain a single value.
- Each record needs to be unique.

The above table in 1NF-

![Example of 1NF in DBMS](https://www.guru99.com/images/1NF.png)

## 2NF Rules

- Be in 1NF.
- Single Column Primary Key that does not functionally dependant on any subset
  of candidate key relation.

It is clear that we can't move forward to make our simple database in 2nd
Normalization form unless we partition the table above.

![](https://www.guru99.com/images/Table2.png)

We have divided our 1NF table into two tables viz. Table 1 and Table2. Table 1
contains member information. Table 2 contains information on movies rented.

We have introduced a new column called Membership_id which is the primary key
for table 1. Records can be uniquely identified in Table 1 using membership id

## 3NF Rules

- Be in 2NF.
- Has no transitive functional dependencies.

To move our 2NF table into 3NF, we again need to again divide our table.

Below is a 3NF example in SQL database:

![](https://www.guru99.com/images/2NFTable1.png)

We have again divided our tables and created a new table which stores
Salutations.

There are no transitive functional dependencies, and hence our table is in 3NF.

In Table 3 Salutation ID is primary key, and in Table 1 Salutation ID is foreign
to primary key in Table 3.

Now our little example is at a level that cannot further be decomposed to attain
higher normal form types of normalization in DBMS. In fact, it is already in
higher normalization forms. Separate efforts for moving into next levels of
normalizing data are normally needed in complex databases. However, we will be
discussing next levels of normalisation in DBMS in brief in the following.

## BCNF

Abbreviation of **Boyce-Codd Normal Form**.

Even when a database is in 3rd Normal Form, still there would be anomalies
resulted if it has more than one Candidate Key.

Sometimes is BCNF is also referred as 3.5 Normal Form.

## 4NF Rules

If no database table instance contains two or more, independent and multivalued
data describing the relevant entity, then it is in 4th Normal Form.

## 5NF Rules

A table is in 5th Normal Form only if it is in 4NF and it cannot be decomposed
into any number of smaller tables without loss of data.

## 6NF Proposed

6th Normal Form is not standardized, yet however, it is being discussed by
database experts for some time. Hopefully, we would have a clear & standardized
definition for 6th Normal Form in the near future…

That's all to SQL Normalization!!!

## Summary

- Database designing is critical to the successful implementation of a database
  management system that meets the data requirements of an enterprise system.
- Normalization in DBMS is a process which helps produce database systems that
  are cost-effective and have better security models.
- Functional dependencies are a very important component of the normalize data
  process.
- Most database systems are normalized database up to the third normal forms in
  DBMS.
- A primary key uniquely identifies are record in a Table and cannot be null.
- A foreign key helps connect table and references a primary key.
