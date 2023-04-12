# [NMF Diagram](https://opentextbc.ca/dbdesign01/chapter/chapter-11-functional-dependencies/)

A dependency diagram, shown in Figure 11.6, illustrates the various dependencies
that might exist in a non-normalized table. A non-normalized table is one that
has data redundancy in it.

![Figure 11.6. Dependency diagram.](https://opentextbc.ca/dbdesign01/wp-content/uploads/sites/11/2013/12/Ch-11-Dependency-Diagram-300x67.jpg)

The following dependencies are identified in this table:

- `ProjectNo` + `EmpNo` are the PK.
- Partial Dependencies:
  - `ProjectNo` &rarr; `ProjName`
  - `EmpNo` &rarr; `EmpName`, `DeptNo`
  - `ProjectNo`, `EmpNo` &rarr; `HrsWork`
- Transitive Dependency:
  - `DeptNo` &rarr; `DeptName`

See [video explanation](https://www.youtube.com/watch?v=VyzKDc2GyW4&ab_channel=CCISFaculty).

See [more example](https://www.czobel.bit.vt.edu/bit4514/handouts/pracHW1-2-soln.pdf).
