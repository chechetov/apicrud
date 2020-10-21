# Technical Assesment

This is an application which allows setting and getting feature switches for users


## How to run it

1. Clone repository and change to project folder
2. Run application with `mvnw spring-boot:run`


## Examples

1. Create feature-user combination at
`http://localhost:8080/feature` using POST request with body
`{
"featureName": "test",
"email": "test",
"enable": true
}`
	Responses:
	1. Empty 200 OK if combination was created in the database
	2. Empty 304 Not modified if such combination is already present in database

2. Get feature status GET request at 
`http://localhost:8080/feature?email=test&featureName=test`

	Response:
	`{
	"canAccess": true }`