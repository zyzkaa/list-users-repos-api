# list-users-repos-api

A simple spring-boot aplication that returns a list of user's repositories.

## Features

- Fetches non-forked repositories for a given GitHub username
- Retrieves branch information for each repository
- Handles error cases for non-existent users and other edge cases

## Technologies

- **Java 21**  
- **Spring Boot 3.x**  
- **RestTemplate** for API communication  
- **JUnit 5 & WireMock** for testing  

## How it works

1. Make a `GET` request to `/{username}`  
2. The application calls GitHub’s API to get the user’s repository list  
3. Forked repositories are filtered out  
4. For each repository, branch information is fetched  
5. The API returns a JSON containing:  
   - Repository name  
   - Owner’s login  
   - List of branches, each with branch name and last commit SHA  

## Project structure

- **GithubFetchSerivce.java** – Service responsible for communicating with GitHub API  
- **Controller.java** – Defines the `/{username}` GET endpoint  
- **MyExceptionHandler.java** – Catches and handles HTTP exceptions  
- **ErrorResponse.java** – DTO for error responses  

## Usage

### Fetch Repositories for a User
`GET /{username}`

**Example**:
`GET /user123`

**Response**:
```json
[
  {
    "repositoryName": "repo-name",
    "ownerLogin": "user123",
    "branches": [
      {
        "name": "main",
        "lastCommitSha": "123"
      },
      {
        "name": "dev",
        "lastCommitSha": "456"
      }
    ]
  }
]
```

### Error Handling
For non-existent users, the API will return a *404* response.

## Configuration 

Add the following to your `application.properties`
```
github.api.url=https://api.github.com
```

### Building the Project

```
./mvnw clean install
./mvnw spring-boot:run
```

### Testing

```
./mvnw test
```
