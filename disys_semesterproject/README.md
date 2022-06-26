# Distributed Order System

## JavaFX


## Spring Boot


## Worker


## Database
```shell
> docker run --rm --detach --name distorder -e POSTGRES_USER=distuser -e POSTGRES_PASSWORD=distpw -v data:/var/lib/postgresql/data -p 5432:5432 postgres
> docker exec -it <container_id> bash
$ psql -U distuser
$ CREATE DATABASE dist;
$ GRANT ALL PRIVILEGES ON DATABASE dist TO distuser;
$ \c dist
$ <paste sql.init contents>
```

## Queue
```shell
> .\Queue\apache-activemq-5.16.5\bin\win64\activemq.bat
```


## LOG

### DataCollectionDispatcher
customer data received
data is: {"requestid":"facc8367-c090-41f4-b061-fae2ab071ff3","customerid":"1","stations":[{"available":"t","stationid":1},{"available":"t","stationid":2},{"available":"t","stationid":3},{"available":"t","stationid":4}]}

### StationDataCollector
message was: {"requestid":"facc8367-c090-41f4-b061-fae2ab071ff3","customerid":"1","stations":[{"available":"t","stationid":1},{"available":"t","stationid":2},{"available":"t","stationid":3},{"available":"t","stationid":4}]}
send data from stationdatacollector to receiver: {"requestid":"facc8367-c090-41f4-b061-fae2ab071ff3","chargingData":[{"chargingdate":"2022-03-12","kwh":19.5,"stationId":1},{"chargingdate":"2022-03-13","kwh":5,"stationId":1},{"chargingdate":"2022-03-14","kwh":20,"stationId":2}]}

### DataCollectionReceiver
message1 was: {"requestid":"facc8367-c090-41f4-b061-fae2ab071ff3","customerid":"1","stations":[{"available":"t","stationid":1},{"available":"t","stationid":2},{"available":"t","stationid":3},{"available":"t","stationid":4}]}
message2 was: {"requestid":"facc8367-c090-41f4-b061-fae2ab071ff3","chargingData":[{"chargingdate":"2022-03-12","kwh":19.5,"stationId":1},{"chargingdate":"2022-03-13","kwh":5,"stationId":1},{"chargingdate":"2022-03-14","kwh":20,"stationId":2}]}
allRequestData {"requestid":"facc8367-c090-41f4-b061-fae2ab071ff3","customerid":"1","stations":[{"available":"t","stationid":1},{"available":"t","stationid":2},{"available":"t","stationid":3},{"available":"t","stationid":4}]}
to sender {"requestid":"facc8367-c090-41f4-b061-fae2ab071ff3","customerid":"1","chargingData":[{"chargingdate":"2022-03-12","kwh":19.5,"stationId":1},{"chargingdate":"2022-03-13","kwh":5,"stationId":1},{"chargingdate":"2022-03-14","kwh":20,"stationId":2}],"customerData":[{"zip":4330,"country":"Österreich","firstname":"Tom","address":"Hauptstraße 1","city":"Düsenbach","lastname":"Turbo"}]}
