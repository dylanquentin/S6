0 rows deleted.


0 rows deleted.


0 rows deleted.


0 rows deleted.


0 rows deleted.


0 rows deleted.


0 rows deleted.


1 row inserted.


1 row inserted.


1 row inserted.


1 row inserted.


1 row inserted.


1 row inserted.


1 row inserted.

1 row inserted.

7 rows deleted.


1 row deleted.


0 rows deleted.


0 rows deleted.


0 rows deleted.


0 rows deleted.


0 rows deleted.


1 row inserted.


1 row inserted.


1 row inserted.


1 row inserted.


1 row inserted.


1 row inserted.


1 row inserted.


1 row inserted.


1 row inserted.


1 row inserted.


1 row inserted.


Error starting at line : 22 in command -
Insert into Livre values (1, 'Design Patterns, Tête la Première', 'Edition O’Reilly')
Error report -
SQL Error: ORA-12899: value too large for column "QUENTIN"."LIVRE"."TITRE" (actual: 33, maximum: 20)
12899. 00000 -  "value too large for column %s (actual: %s, maximum: %s)"
*Cause:    An attempt was made to insert or update a column with a value
           which is too wide for the width of the destination column.
           The name of the column is given, along with the actual width
           of the value, and the maximum allowed width of the column.
           Note that widths are reported in characters if character length
           semantics are in effect for the column, otherwise widths are
           reported in bytes.
*Action:   Examine the SQL statement for correctness.  Check source
           and destination column data types.
           Either make the destination column wider, or use a subset
           of the source column (i.e. use substring).

1 row inserted.


1 row inserted.


Error starting at line : 25 in command -
Insert into Livre values (4, 'Les Bases de données pour les nuls', 'Editions Générales First')
Error report -
SQL Error: ORA-12899: value too large for column "QUENTIN"."LIVRE"."TITRE" (actual: 34, maximum: 20)
12899. 00000 -  "value too large for column %s (actual: %s, maximum: %s)"
*Cause:    An attempt was made to insert or update a column with a value
           which is too wide for the width of the destination column.
           The name of the column is given, along with the actual width
           of the value, and the maximum allowed width of the column.
           Note that widths are reported in characters if character length
           semantics are in effect for the column, otherwise widths are
           reported in bytes.
*Action:   Examine the SQL statement for correctness.  Check source
           and destination column data types.
           Either make the destination column wider, or use a subset
           of the source column (i.e. use substring).
