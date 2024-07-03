# myblockchain

May 2018

Build 
mvn clean install

Run
java -jar target/myblockchain-0.0.1-SNAPSHOT.jar

API
http://localhost:8777
Blockchain demo. Use following uri:

/create?size=10
/show
/balances
/balance?name=Donis
/transfer?from=Donis&to=Bob&value=50
/merkleTree?transactions=a,b,c,d,e

````
This file could be delete and they will recreted 
during the execution of the program
````
ECC_ledger.json
ECC_users.json
