# School's out

### Part 0: Overview and explanation about UML

### UML description

* One to One  -||  ----------  ||- 
* One to Many -||  ----------  <<-
* Many to One ->>  ----------  ||-
* Many to Many ->>  ----------  <<-

### Exercise overview

The UML below is an overview of the entire exercise and serves as a reference. It is not the intention that this (may but must not) be
fully worked out from the first day.

```
User -||  --------  ||- Person 
```

```
Person ->> ---------- <<- Course
Person ->> ---------- ||- Course
```

```
Course -|| --------- <<- Module
```

```
Module -|| --------- <<- Exam
```

```
Exam   -|| --------- <<- Exam
Exam   -|| --------- <<- Grade
```

```
Grade ->> --------- ||- Person
```


As you might deduce from the UML diagram, we are going to write a school administration application in our project. In the table below
you will find a little more explanation about the different tables / entities.

The project will consist of several parts. You are supposed to commit and share each part with your instructors via github.


**Table**

Person : The Person entity will keep our personal data

User : The User entity keeps track of all information about the user logins. 1 login per person

Course : The Course entity describes the course that a Person follows. In the first instance we will only keep track of what the current course is. Later we will also add a 'history'

Module : The Module entity describes the different modules that make up a course.

Exam: The Exam entity keeps track of the various exams or tests that are taken. Initially we will keep 'simple' exams in our
program. Later we will add exams that can consist of several parts.

Grade : The Grade entity keeps track of the individual scores of the tests and the people who take them.

## Part 1: Descend into madness

Set up a new JPA / Hibernate project. Use your personal database (the same as for the SQL / JDBC exercises) to set up the connection.

Create the entities below and provide the necessary classes to write and read instances of these classes to the database. For now, we
only provide CRUD operations.

```
User
login: string
passwordhash: string
active: boolean
person: Person
```

```
Person
id: Integer
firstname: string
familyname: string
gender: Gender
course: Course
```

```
Course
id: Long
name: string
description: string
code: string
imageURL: string
active: boolean
modules: List <Module>
```

```
Module
id: Long
name: string
description: string
course: Course
exams: List <Exam>
```

```
Exam
id: Long
name: string
description: string
date: LocalDate
weight: int
total: int
module: Module
```

Extra information:

The database should have 5 tables if you let Hibernate create these tables. All numeric IDs are autonomous
numbered. The PK for the User class is login. Descriptions for each must contain a minimum of 2000 characters.

