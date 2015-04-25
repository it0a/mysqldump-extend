# mysqldump-extend

Converts non-extended INSERT INTO queries into the equivalent extended-insert format.

The extended-insert format is much more performant when restoring data.

## Usage

mysqldump-extend file1.sql file2.sql file3.sql > extended.sql

## License

Copyright © 2015 it0a

MIT License
