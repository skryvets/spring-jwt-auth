# Stateless JWT Authentication with Spring Security

This repository demonstrates how to implement **stateless JWT (JSON Web Token) authentication** in a Spring Boot application. The project covers generating and validating JWTs, securing endpoints, and issuing tokens with a simple user authentication setup.

Please refer to the accompanying [article](https://skryvets.com/blog/2024/12/15/spring-auth-jwt/) on my blog

---

## Features

- Stateless authentication using **JWT**
- Spring Security configuration for stateless applications
- JWT generation and validation using RSA key pairs

---

## Generate RSA Key Pair

Scripts for generating rsa key pairs located in `scripts` folder

## Set keys as env vars

```yaml
rsa-config:
  public-key: MIIEvgIBADANBgkqhki...
  private-key: MIIBIjANBgkqhkiG9w...
```


## Endpoints overview

### Public Endpoint

Accessible to everyone without authentication.

`Request`:

```shell
curl http://localhost:8080/public
```

`Response`:

```json
{"message":"Hello From Public Endpoint"}
```

### Private Endpoint

Accessible only with a valid JWT

```shell
curl http://localhost:8080/private
```

The response will result in 401 unauthorized code

### Generating a token

Authenticate with a username and password to receive a JWT token

```bash
curl -X POST http://localhost:8080/token \
-H "Content-Type: application/json" \
-d '{"email":"john@example.com","password":"password"}'
```

`Response`

```json
{"jwt_token":"eyJhbGciOiJSUzI1NiJ9.eyJzdWIiOiIxMjM0N..."}
```

### Accessing Private Endpoint with JWT

Use the generated token to access the private endpoint

`Request`

```bash
curl http://localhost:8080/private \
-H "Authorization: Bearer eyJhbGciOiJSUzI1NiJ9.eyJzdWIiOiIxMjM0N..."
```

`Response`

```json
{"message":"Hello From Private Endpoint"}
```