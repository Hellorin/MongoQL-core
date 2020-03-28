# MongoQL-core
[![Build Status](https://travis-ci.org/Hellorin/MongoQL-core.svg?branch=master)](https://travis-ci.org/Hellorin/MongoQL-core)
[![codecov](https://codecov.io/gh/Hellorin/MongoQL-core/branch/master/graph/badge.svg)](https://codecov.io/gh/Hellorin/MongoQL-core)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=Hellorin_MongoQL-core&metric=alert_status)](https://sonarcloud.io/dashboard?id=Hellorin_MongoQL-core)

## Purpose
In a nutshell, the purpose of this tool is to generate a GraphQL schema based on a MongoDB's collection.

## How to use
Note that this tool has been named **MongoQL-core** since it doesn't have much purpose alone. However, it is the foundation of the concept. In fact, a GraphQL schema can be useful to describe a contract but alone it's not executable. This tool would/should be used conjointly with another tool in order to generate the GraphQL data fetchers required.

### Requirements
Since Variety uses the mongo shell to connect to the database for introspection, one must install it and make it available through the command line or terminal. Please look on [here](https://docs.mongodb.com/manual/administration/install-community/) for a full description on how to install the mongo shell

### Standalone jar
One can use it using the jar arguments or by cloning this repository and using the main.

The command line jar execution uses the following syntax:
```
java -jar mongoql-core-1.0.0-SNAPSHOT.jar *databaseName* *collectionName* *graphQLRootName*
```

### Full example
Here is described exactly how to get a running example of MongoQL-core:

__Step 1__: Create a MongoDB database locally and create/populate a collection
Open a terminal or a command line and connect to Mongo locally with:
```
mongo
```
Now you can populate data into a database/collection:
```
use myDatabase
db.myCollection.insertOne({"name": "David", "age":32})
db.myCollection.insertOne({"name": "Mathieu", "age":32})
db.myCollection.insertOne({"name": "Nuno", "age":32})
db.myCollection.insertOne({"name": "Kevin"})
db.myCollection.insertOne({"name": "Michael", "child": {"name" : "L", "age":1}})
```

__Step 2__: Compile the jar

Compile it using maven:
```
mvn clean install
```

__Step 3__: Execute jar

Execute it with the following command
```
java -jar mongoql-core-1.0.0-SNAPSHOT-jar-with-dependencies.jar myDatabase myCollection Person
```
__Result__:
```
type Child {
        age : Int!
        name : String!
}

type Person {
        _id : ID!
        name : String!
        age : Int
        child : Child
}

```

## Current Limitations
Since it's in the early stages, there is, sadly, a couple limitations:

### Multi types fields
Since MongoDB is schemaless, it enables fields to have multiple types. This tool should be able to handle this in a near future but currently it's not supported.

### Arrays
The tool used to introspect the MongoDB schema of a collection isn't precise enough to give information of content of arrays. For now, it is out of scope of this tool.

### Type recognition
There might be schema with sub documents that are the same as other documents. One could determine an fields equivalence between types found to regroup into one type. In the full example above, the type **Child** is most probably a type Person and it should be only one type rather than two.

## Technologies
- Kotlin
- Mockito
- JUnit
- Jackson
- Variety ([https://github.com/variety/variety](https://github.com/variety/variety))
