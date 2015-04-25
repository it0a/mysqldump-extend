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
mysqldump-extend file1.sql file2.sql file3.sql > extended.sql
```

## License

Copyright © 2015 it0a

MIT License
