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