# javaee8-jsf-sample(WIP)

> Check the latest [Jakarta EE Faces Example](https://github.com/hantsy/jakartaee-faces-sample) project.

Java EE 8 and Glassfish v5 had beed released for a few days, I also spend some time to update to the last specificaitons brought by Java EE 8. This sample application will show how to build a traditional MVC application using Java EE 8 new features.

The following specifications are used in this sample appliacation:

* JSF 2.3
* JPA 2.2(and EJB, JTA) for database operations
* CDI 2
* Bean Validation 2
* Java EE Security 1.0


## What's it?

You could have embraced some Agile methodology in your projects, such as Scrum, Kanban, TODO list etc.  This sample application can be considered as a simple Kanban like applicaiton, I name it **Taskboard**.

In the initial version, it could includes the following features:

* Display tasks in Swimlan view(split by status)
* Create and update task(requires authentication)
* Delete task if it is done

![preview](https://github.com/hantsy/javaee8-jsf-sample/blob/master/preview.png)

I have also created some some variants to demonstrate varied technology stack in the past, you can browse which you are interested in.

* [Spring MVC with Apache Tiles and JSP](https://github.com/hantsy/spring4-sandbox/tree/master/mvc-tiles3)
* [Spring MVC with Freemarker](https://github.com/hantsy/spring4-sandbox/tree/master/mvc-freemarker)
* [Spring MVC with Thymeleaf](https://github.com/hantsy/spring4-sandbox/tree/master/mvc-thymeleaf)
* [Spring and JSF 2](https://github.com/hantsy/spring4-sandbox/tree/master/mvc-jsf2)
* [Spring MVC for RESTful APIs](https://github.com/hantsy/spring4-sandbox/tree/master/mvc-rest)
* [JSR 371: MVC 1.0](https://github.com/hantsy/ee8-sandbox/tree/master/mvc)(JSR 371 was finally cancelled in Java EE 8)
* [JSR 371: MVC 1.0 with Facelets Template Engine](https://github.com/hantsy/ee8-sandbox/tree/master/mvc-facelets)

## Build the project 

### Prerequisites

To try this appliacation in your local system, make sure you have already installed the following software.

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

