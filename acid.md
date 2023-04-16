# [ACID](https://en.wikipedia.org/wiki/ACID)

In computer science, ACID (atomicity, consistency, isolation, durability) is a
set of properties of database transactions intended to guarantee data validity
despite errors, power failures, and other mishaps. In the context of databases,
a sequence of database operations that satisfies the ACID properties (which can
be perceived as a single logical operation on the data) is called a transaction.
For example, a transfer of funds from one bank account to another, even
involving multiple changes such as debiting one account and crediting another,
is a single transaction.

In 1983, Andreas Reuter and Theo Härder coined the acronym ACID, building on
earlier work by Jim Gray who named atomicity, consistency, and durability, but
not isolation, when characterizing the transaction concept. These four
properties are the major guarantees of the transaction paradigm, which has
influenced many aspects of development in database systems.

According to Gray and Reuter, the IBM Information Management System supported
ACID transactions as early as 1973 (although the acronym was created later).

## Characteristics

The characteristics of these four properties as defined by Reuter and Härder are
as follows:

### [Atomicity](https://en.wikipedia.org/wiki/Atomicity_(database_systems))

Transactions are often composed of multiple statements. Atomicity guarantees
that each transaction is treated as a single "unit", which either succeeds
completely or fails completely: if any of the statements constituting a
transaction fails to complete, the entire transaction fails and the database is
left unchanged. An atomic system must guarantee atomicity in each and every
situation, including power failures, errors, and crashes. A guarantee of
atomicity prevents updates to the database from occurring only partially, which
can cause greater problems than rejecting the whole series outright. As a
consequence, the transaction cannot be observed to be in progress by another
database client. At one moment in time, it has not yet happened, and at the
next, it has already occurred in whole (or nothing happened if the transaction
was canceled in progress).

### [Consistency](https://en.wikipedia.org/wiki/Consistency_(database_systems))

Consistency ensures that a transaction can only bring the database from one
consistent state to another, preserving database invariants: any data written to
the database must be valid according to all defined rules, including
constraints, cascades, triggers, and any combination thereof. This prevents
database corruption by an illegal transaction. Referential integrity guarantees
the primary key–foreign key relationship.

### [Isolation](https://en.wikipedia.org/wiki/Isolation_(database_systems))

Transactions are often executed concurrently (e.g., multiple transactions
reading and writing to a table at the same time). Isolation ensures that
concurrent execution of transactions leaves the database in the same state that
would have been obtained if the transactions were executed sequentially.
Isolation is the main goal of concurrency control; depending on the isolation
level used, the effects of an incomplete transaction might not be visible to
other transactions.

### [Durability](https://en.wikipedia.org/wiki/Durability_(database_systems))

Durability guarantees that once a transaction has been committed, it will
remain committed even in the case of a system failure (e.g., power outage or
crash). This usually means that completed transactions (or their effects) are
recorded in non-volatile memory.
