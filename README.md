# Campus Lost & Found — Android (Java) Project (JDBC)

This project scaffold demonstrates an Android app in Java that connects directly to a MariaDB instance using JDBC.
**Important security note:** direct DB connections from mobile apps are insecure for production. This scaffold is for development/testing only.

## What's included
- Full Java activity skeletons: Login, Register, Dashboard, Feed (tabs), Post Item, Item Details, Matches.
- JDBC DB helper that talks to MariaDB over TCP.
- Gradle config with MariaDB JDBC dependency.
- SQL schema for MariaDB.
- Example dummy admin credentials: admin / admin123
- README notes on how to configure DB host/user/password.

## How to use
1. Install MariaDB and create the database using `server/schema_mariadb.sql`.
2. Update DB connection settings in `app/src/main/java/com/example/campuslostfound/db/DBConfig.java`.
3. Open the project in Android Studio.
4. Run on an emulator or device with network access to the MariaDB host.

## DB schema
See `server/schema_mariadb.sql`.

## Dummy admin
- username/email: admin@campus.local
- password: admin123

