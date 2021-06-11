# Trustly Backend Github for Back-End Engineers position

### Author
Renan Gatte Picchi

### Summary
This project is for a Back-end Enginners position in Trustly.
Basically, this project contains a API that returns a summary from a public Github repository. The response is composed for total number of lines, number of files and size of total of files in bytes grouped by file extension.
For more details, read on below.

## Approaches
This project has been built using Jsoup library. This library is HTML parser very helpful. Basically, the library read the URL from a HTML page and create a object which allows extract and manipulate data. Jsoup is an open source project.

There is also another implementation done manually, in which the html parser of the Github repository page is done manually. This solution is less efficient.

### Dependencies
- java 8
- spring-boot 2.5.0
- spring-boot-devtools
- spring-boot-starter-validation
- spring-boot-starter-test
- swagger 2.9.2
- lombok
- jsoup 1.13.1
- commons-io 2.6
- junit-jupiter-engine 5.7.2

### Github repository
To clone repository:

```
git clone https://github.com/LedRenan/trustly-backend
```

### Install and run
Execute the command below to build the application

```
mvn clean install
```

Execute the command below to run the application

```
mvn spring-boot:run
```

### AWS EC2 Environment
This application is running on AWS EC2. Access the URL below to try the API.

Using Jsoup parser

```
http://ec2-18-117-184-200.us-east-2.compute.amazonaws.com:8080/api/github-repository-summary-jsoup?user=LedRenan&repository=trustly-backend
```

Using Html parser

```
http://ec2-18-117-184-200.us-east-2.compute.amazonaws.com:8080/api/github-repository-summary-html?user=LedRenan&repository=trustly-backend
```

For more information about the API, access the URL below:

```
http://ec2-18-117-184-200.us-east-2.compute.amazonaws.com:8080/swagger-ui.html
```