<img width="548" alt="Screenshot 2023-01-05 at 8 22 01 AM" src="https://user-images.githubusercontent.com/6371078/210690762-7759e992-e0d9-4530-94ae-6aebea40a014.png">


# route53-ssl-monitor
A Spring and MySQL-based service that provides REST APIs to monitor and get the AWS Route 53 SSL certificate expiry details is a web service that allows users to retrieve information about the expiration dates of SSL certificates for domains hosted on Amazon's Route 53 DNS service. 

The service is built using the Spring Framework and uses MySQL as its database. It provides a set of REST APIs that users can call to retrieve the expiration dates of SSL certificates for a given domain, as well as to set up alerts to be notified when a certificate is approaching expiration. 

This service can be useful for domain owners and system administrators who need to keep track of the expiration dates of SSL certificates in order to ensure that their websites remain secure and trusted by visitors.

# Prerequisites
In order to use this project, you will need to have the following dependencies and tools installed:

Java Development Kit (JDK) version 8 or higher
Spring Framework version 5 or higher
MySQL version 8 or higher
MySQL Connector/J version 8 or higher

# Installation
Clone or download the repository.
Import the project into your preferred Java development environment (e.g. Eclipse, IntelliJ IDEA).
Create a MySQL database and run the database.sql script to set up the necessary tables.
Update the application.properties file with your MySQL connection details.
Build and run the project.

# Dependencies
This project has the following dependencies:

Spring Boot
Spring Data JPA
MySQL Connector/J
These dependencies are managed automatically by the Spring Framework and do not need to be installed separately.

## Other dependencies
Depending on the specific requirements of the project, there may be other dependencies that need to be installed. Please review the project's build.gradle file for a complete list of dependencies.


# Command to build or Run the project

* To build the project ``./gradlew clean build``
* To run the service ```./gradlew :bootRun```
