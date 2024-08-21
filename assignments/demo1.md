# [Demo 1](https://github.com/hanggrian/IIT-CS425/blob/assets/assignments/demo1.pdf): Normalization

## Problem 1

> - Which normal form (NF) is the dataset provided? And why?
> - If unnormalized, can you normalize the dataset to highest NF to ensure that
    database is void of data redundancy?
>
> OID | O_Date | CID | C_Name | C_State | PID | P_Desc | P_Price | Qty
> --- | --- | --- | --- | --- | --- | --- | --- | ---
> 1006 | 10/24/09 | 2 | Apex | NC | 7<br>5<br>4 | Table<br>Desk<br>Chair | 800<br>325<br>200 | 1<br>1<br>1
> 1007 | 10/25/09 | 6 | Acme | GA | 11<br>4 | Dresser<br>Chair | 500<br>200 | 4<br>6

### Eliminated the nulls and multivalued

OID | O_Date | CID | C_Name | C_State | PID | P_Desc | P_Price | Qty
--- | --- | --- | --- | --- | --- | --- | --- | ---
1006 | 10/24/09 | 2 | Apex | NC | 7 | Table | 800 | 1
1006 | 10/24/09 | 2 | Apex | NC | 5 | Desk | 325 | 1
1006 | 10/24/09 | 2 | Apex | NC | 4 | Chair | 200 | 5
1007 | 10/25/09 | 6 | Acme | GA | 11 | Dresser | 500 | 4
1007 | 10/25/09 | 6 | Acme | GA | 4 | Chair | 200 | 6

## Problem 2

> - Which normal form (NF) is the dataset provided?
> - If unnormalized, can you normalize the dataset to highest NF to ensure
    that database is void of data redundancy?
>
> CompID | CompName | DeptID | DeptName | ProductID | ProdName | ProdTypeID | ProdTypeName | Date | Value
> --- | --- | --- | --- | --- | --- | --- | --- | --- | ---
> 1 | ACME VR | 1 | Production | 1 | VR100 | 1 | Headset | 01/23/2022 | 45
> 1 | ACME VR | 1 | Production | 0 | No Product | | | 01/24/2022 | -90
> 1 | ACME VR | 2 | HR | 0 | No Product | | | 01/23/2022 | 78
> 2 | ACME Movies | 3 | Finance | 0 | No Product | | | 01/23/2022 | 80
> 2 | ACME Movies | 1 | Production | 2 | MC9 | 2 | Camera | 01/23/2022 | 50

### Normalization of datasets
