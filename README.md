# MongoQL-core

## Purpose
In a nutshell, the purpose of this tool is to generate a GraphQL schema based on a MongoDB's collection.

## How to use
Note that this tool has been named **MongoQL-core** since it doesn't have much purpose alone. However, it is the foundation of the concept. In fact, a GraphQL schema can be useful to describe a contract but alone it's not executable. This tool would/should be used conjointly with another tool in order to generate the GraphQL data fetchers required.

However, one can use it using the jar arguments or by cloning this repository and using the main.

The command line jar execution uses the following syntax:
```
java -jar mongoql-core.jar *hostname* *port* *databaseName* *collectionName*
```

## Current Limitations
Since it's in the early stages, there is, sadly, a couple limitations:

### Multi types fields
Since MongoDB is schemaless, it enables fields to have multiple types. This tool should be able to handle this in a near future but currently it's not supported.

### Arrays
The tool used to introspect the MongoDB schema of a collection isn't precise enough to give information of content of arrays. For now it is out of scope of this tool even though.

## Technologies
- Kotlin
- Mockito
- JUnit
- Jackson
- Variety ([https://github.com/variety/variety](https://github.com/variety/variety))
