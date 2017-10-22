# javaee8-jsf-sample(WIP)


## Purpose 

This sample application demonstrates how to use the latest specificaitons brought in Java EE 8 to build a traditional MVC application.

The following specifications are included in this sample:

* JSF 2.3
* JPA 2.2(and EJB, JTA) for database operations
* CDI 2
* Bean Validation 2
* Java EE Security 1.0


## What's it?

This sample is a simple taskboard appliation, including the following features:

* Display tasks in Swimlan view(split by status)
* Create and update task(requires authentication)
* Delete task if it is done

![preview](https://github.com/hantsy/javaee8-jsf-sample/blob/master/preview.png)

There are some variants I'v created to demonstrate varied technology stack.

* [Spring MVC and Apache Tiles](https://github.com/hantsy/spring4-sandbox/tree/master/mvc-tiles3)
* [Spring MVC and Freemaker](https://github.com/hantsy/spring4-sandbox/tree/master/mvc-freemarker)
* [Spring MVC and Thymeleaf](https://github.com/hantsy/spring4-sandbox/tree/master/mvc-thymeleaf)
* [Spring and JSF](https://github.com/hantsy/spring4-sandbox/tree/master/mvc-jsf2)
* [Spring MVC for REST API](https://github.com/hantsy/spring4-sandbox/tree/master/mvc-rest)
* [MVC 1.0](https://github.com/hantsy/ee8-sandbox/tree/master/mvc)(cancelled in Java EE 8)
* [MVC 1.0 and Facelets Template Engine](https://github.com/hantsy/ee8-sandbox/tree/master/mvc-facelets)

## Run 

### Prerequisites

To experience this appliacation in your local system, make sure you have already installed the following software.

* [Oracle Java 8](https://java.oracle.com) 
* [Apache Maven](https://maven.apache.org)
* [NetBeans IDE](http://www.netbeans.org), NetBeans 9 nightly is recommended.
* [Glassfish v5](https://javaee.github.io/glassfish/)


### Get the source codes

Clone the source codes from github. 

```
git clone https://github.com/hantsy/javaee8-jsf-sample
```

Or check out the codes NetBeans IDE which have great Git support.

Now you can run it from mvn command line or NetBeans IDE.

#### Command line

```
mvn verify cargo:run
```

#### NetBeans IDE

1. Adds Glassfish in **Server** node in the **Service** view.
2. Open the project(if you used NetBeans to check out the codes, it could be open by default)
3. Right click the project node, and select **Run** to run this project on Glassfish.

## Contribution 

Welcome to contribute this project, you can fork it and send a pull request, or share your idea on Github issues.

