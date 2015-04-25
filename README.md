# mysqldump-extend

Converts non-extended INSERT INTO queries into the equivalent extended-insert format.

The extended-insert format is much more performant when restoring data.

## Build

With leiningen:
```
git clone https://github.com/it0a/mysqldump-extend && \
cd mysqldump-extend && \
lein bin
```
Will output a binary at target/mysqldump-extend (Add this to your PATH)

## Usage

```
mysqldump-extend data-non-extended-insert.sql > data-extended-insert.sql

mysqldump-extend data1.sql data2.sql data3.sql > data-extended-insert.sql

mysqldump-extend *.sql > data-extended-insert.sql

mysqldump-extend *.sql | mysql -u user -p database_name
```

## License

Copyright © 2015 it0a

MIT License
