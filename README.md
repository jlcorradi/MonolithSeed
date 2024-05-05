# Monolithic Application Seed

This project is a seed (scaffold) for building monolithic applications using spring boot.

## Requirements
 - Java 21 or later

## Features

The scaffolding features several useful abstractions that lay the foundation for a project
 
### Base Crud API
For every new resources you can use the crud api with little code. All you have to do is:
- Create the entity
- Create the mapper (Mapstruct)
- Implement a service extending ```AbstractCrudService``` and implements ```CrudService```
- Create a JpaRepository
- Implement a Rest controller extending ```AbstractCrudApi``` passing the dependencies. Don't forget to annotate it with ```@Requestmapping```.

Don't forget to rename the packages