# Aster input plugin for Embulk

Connect Aster server with jdbc

## Overview

* **Plugin type**: input
* **Resume supported**: no
* **Cleanup supported**: no
* **Guess supported**: no

## Configuration

- **host**: description (string, required)
- **user**: description (string, required)
- **password**: description (string, required)
- **database**: description (string, required)

## Example

```yaml
in:
  type: aster
  driver_class: com.asterdata.ncluster.Driver
  host: 127.0.0.1
  user: beehive
  password: "beehive"
  database: beehive
  query: select * from nc_system.nc_user_tables;
```


## Build

```
$ ./gradlew gem  # -t to watch change of files and rebuild continuously
```
