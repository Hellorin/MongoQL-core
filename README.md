# MongoQL-core
[![codecov](https://codecov.io/gh/Hellorin/MongoQL-core/branch/master/graph/badge.svg)](https://codecov.io/gh/Hellorin/MongoQL-core)

## Purpose
In a nutshell, the purpose of this tool is to generate a GraphQL schema based on a MongoDB's collection.

## How to use
Note that this tool has been named **MongoQL-core** since it doesn't have much purpose alone. However, it is the foundation of the concept. In fact, a GraphQL schema can be useful to describe a contract but alone it's not executable. This tool would/should be used conjointly with another tool in order to generate the GraphQL data fetchers required.

### Requirements
A couple tools should be installed beforehand.

#### Install nodejs
[NodeJs official website](https://nodejs.org/en/download/)

#### Install variety-cli
```
npm install variety-cli -g
```

#### Add variety-cli to path
Please verify that variety is accessible from a command line/terminal.

For Windows, open a command line call and type:
```
variety.cmd
```

For Mac OS X/Linux, open a terminal and type:
```
variety
```

If it is not, please add an **environment variable** to make it available.

### Using MongoQLCoreSystemTest *MongoDB localhost* system test

#### Loading data locally
Here is described exactly how to get a running example of MongoQL-core:

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

#### Executing the test
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

### Using MongoQLCoreSystemTest *MongoDB atlas* system test

#### Setting up your MongoDB Atlas
- Connect to MongoDB atlas (or create an account);
- Create a new project;
- Create a new cluster;
    - Pick a shared cluster with a free subscription

Please be patient, this step could take a bit of time.

#### Whitelist your IP 
Go in **Network Access** and **add a new ip address**. Then select **ALLOW ACCESS FROM ANYWHERE**. 

Please be patient, this step could take a bit of time.

#### Add a user to connect to MongoDB
Go in **Database Access** and **add a new database user**. In my example, the user is called **user** and it's password is also **user**.
Note that you will need to give him access to your database in read and write (unfortunately Variety needs write rights as it performs calculations on the server).

Please be patient, this step could take a bit of time.

#### Loading data in your MongoDB Atlas
Retrieve your connection URL from MongoDB atlas. It should look something like this:
```
mongo "mongodb+srv://****.mongodb.net/test"  --username ****
```
And then enters your password.

Now you can populate data into a database/collection:
```
use myDatabase
db.myCollection.insertOne({"name": "David", "age":32})
db.myCollection.insertOne({"name": "Mathieu", "age":32})
db.myCollection.insertOne({"name": "Nuno", "age":32})
db.myCollection.insertOne({"name": "Kevin"})
db.myCollection.insertOne({"name": "Michael", "child": {"name" : "L", "age":1}})
```

#### Executing the test
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
Since MongoDB is schema-less, it enables fields to have multiple types. This tool should be able to handle this in a near future but currently it's not supported.

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
