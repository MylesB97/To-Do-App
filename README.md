Test Coverage: 90.8%
# To-Do Web App

This is an To-Do Web Application which is designed to store Employee information as well as Tasks. Users can create employees and task with full CRUD functionality, assign task to employees and set them to be completed. 

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.

### Prerequisites

What things you need to install the software and how to install them
1. Have Java 14 installed on your PC.
2. Have a local or cloud MySQL server running.
3. Have SpringBoot  installed.
5. Have Maven installed on your PC.
6. Git Installed on your PC.
7. Visual Studio Code installed with the Live Server Extension.


```
java -version
JDK Version 14.0.2" YYYY-MM--DD

```

### Installing

1. Git Bash into your target folder

2. Git clone https://github.com/MylesBrathQA/To-Do-App.git

```
git clone https://github.com/MylesBrathQA/To-Do-App.git
```
3. Open Existing Maven Project in IDE of your choice.

4. Select To-Do folder

5. Open src/main/resources/application-prod.properties

6. Ensure spring.datasource.url matches your local MySQL Server address

```
spring.datasource.url = jdbc:mysql://localhost.3306/ToDo
```
7. Run as SpringBoot Application

8. Open the static file on VSC located in src/main/resources from your system explorer.

9. Go live from the index page.


### Running Application

1. Git Bash into your target folder

2. Git Clone https://github.com/MylesBrathQA/To-Do-App.git

```
git Clone https://github.com/MylesBrathQA/To-Do-App.git
```

3. Open your CLI/Terminal.

4. Navigate to ToDoApp/documentations.

5. Run following command

```
java -jar ToDoApp.jar
```

6. Go to http://localhost:9092/ on a broswer of your choice

## Test

Explain how to run the automated tests for this system. Break down into which tests and what they do

### Unit Tests 

The Unit Test are split into two sections
1. Service Test (Business Logic Test)
2. Controller Test

#### 1. Unit Test:
Ensure that when app is live the test Database is completely seperate from the live database (this is done by setting your active profile to "dev" in your test file)

```
@SpringBootTest
@ActiveProfiles("dev")
public class EmployeeControllerUnitTest

```
##### Running the Test
1. The Test are premade and pass upon cloning so run test coverage as JUnit test

##### Example Code

This is example from TaskServiceTest.java This test mocks what happens when the read all method is called so if the actual output when the method is called is the same then the test passes.

```
	@Test
	void readAllTest() throws Exception {
		when(repo.findAll()).thenReturn(List.of(TEST_1, TEST_2, TEST_3, TEST_4, TEST_5));
		assertThat(service.readAll()).isEqualTo(LISTOFEDTO);
		verify(repo, atLeastOnce()).findAll();
	}
```
#### 2. Controller Test

The Controller acts as the guide for the user allowing them to feed information into the DAO making the interaction with the database as smooth as possible between the user and the database

The test makes use of Mockito to mimic what the service would return when it is called, to ensure the code is responding as expected thus the test code should only be change if the developer makes changes to how the controller works.


1. Run Coverage as JUnit Test

### Integrated Tests 

This testing is done to ensure that the service and controller for both the Employee and Task side of the API work together and can interact with a database. This is done by using the path variables in the controller to send a request the expected response is mimicked and tested against the actual response if the expected response and the actual response match then the test passes.

### Selenium Testing

Selenium Testing is Automated Front End Testing software which was done in a Sperate repo please see

[SeleniumTestRepo](https://github.com/MylesBrathQA/ToDoSelenium)

## Built With

* [Maven](https://maven.apache.org/) - Dependency Management

## Versioning

We use [SemVer](http://semver.org/) for versioning.

## Authors

* **Myles Brathwaite** - *Version 1 of working IMS System* [MylesBrathwaite](https://github.com/MylesBrathQA) [PersonalAccount](https://github.com/MylesB97)

## License

This project is licensed under the MIT license - see the [LICENSE.md](LICENSE.md) file for details 

*For help in [Choosing a license](https://choosealicense.com/)*

## Acknowledgments

* QA Consulting Academy

