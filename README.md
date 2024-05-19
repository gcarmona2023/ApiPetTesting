## 1.Follow this link for getting information about the Swagger Pet store project. 
  First step
  
## 2. Set up the API Pet store and run it locally.
  To set up correctly and run locally, I have to change the name of file openapi-inflector.yaml on POM.xml because the file on the project called just openapi.yaml
  > Wrong call: `<scanTarget>src/main/resources/openapi-inflector.yaml</scanTarget>`
  
  > Correct call: `<scanTarget>src/main/resources/openapi.yaml</scanTarget>`
  
## 3. Propose a list of test cases for automation. 
  I tried to cover all method with a happy path focus, getting successful response of it
> PETS: `put`, `get`, `post`, `delete`
 
>  STORE: `put`, `get`, `post`, `delete`

>  USER: `put`, `get`, `post`, `delete`
 
## 4. Automate the proposed test cases. 
  For the automation I used the follow tools and frameworks 
>    - Cucumber: used to define and execute test scenarios in natural lenguage
>    - Gherkin: used to write the test scenarios
>    - Rest Assured: to make HTTP request and validate the API's response
>    - JUnit: to run the tests
>    - Gradle: to manage the dependencies and build the project
>    - Java: programming lenguage used to implements the test

## 5. Provide a brief explanation of the solution you have implemented.
  I developed a simple framework to test the various APIs of the system and obtain the corresponding responses for the different methods evaluated, 
  with a happy path view, and not looking the error on this answer.
