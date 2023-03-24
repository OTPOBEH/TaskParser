# Task Parser: Job Processing Service.
The service processes tasks and orders them based on their dependencies(requirements). Each task has a name and a shell command. Tasks may depend on other tasks, requiring those to be executed beforehand.

***Note:*** To create a proper execution order, the service employs [Kahn's algorithm](https://en.wikipedia.org/wiki/Topological_sorting#Algorithms) for topological sorting of an acyclic graph - meaning if cyclic dependency is detected, the service will report an error.
## Tech:
- Java 17
- Maven 4.0.0
- Spring Boot v.3.0.3
- OpenApi v.2.0.2
- Caffeine Cache
- Lombok
## Features:
- Ordering tasks based on their requirements
- Return a sorted tasks bash script representation directly
## Requirements:
- [Java 17 (or newer)](https://www.oracle.com/java/technologies/downloads/#java17)
- [Maven 4.0.0 (or newer)](https://maven.apache.org/)
## API Endpoints v1:
| Verbs | Endpoints              | Action                                                             |
|-------|------------------------|--------------------------------------------------------------------|
| GET   | /swagger-ui/index.html | To get the OpenApi Documentation                                   |
| POST  | /api/jobs/v1/tasks     | To sort job tasks                                                  |
| POST  | /api/jobs/v1/commands  | To sort job tasks and get extracted task commands as a bash script |

## API Endpoints v2 (not shown in OpenAPI doc):
| Verbs | Endpoints             | Action                                                             |
|-------|-----------------------|--------------------------------------------------------------------|
| POST  | /api/jobs/v2/commands | To sort job tasks and get extracted task commands as a bash script |

### Starting the app with v2 endpoints enabled:
```sh
mvn spring-boot:run -Dspring-boot.run.arguments="--job.controller.v2.visible=true"
```
## Usage:
#### Running the service:
```sh
mvn spring-boot:run
```
#### Executing result script via curl command example:
**Note:** Example myTasks.json file can be found in folder `/resources/static`
##### Getting bash script - v1:
```sh
curl -d @myTasks.json http://localhost:8080/api/jobs/v1/commands | bash
```
##### Getting bash script - v2:
- To enable v2 endpoints see [corresponding section](#starting-the-app-with-v2-endpoints-enabled):
- Providing `Content-Type: application/json` is required.
```sh
curl -H "Content-Type: application/json" -d @myTasks.json http://localhost:8080/api/jobs/v2/commands | bash
```
## Caching (LFU):
- Jobs with task count over 1000 are cached.
- Default cache size is 100.
##### Adjusting cache size:
```sh
mvn spring-boot:run -Dspring-boot.run.arguments="--cache.size=200"
```
## Example Payloads:
#### Request body:
```json
{
	"tasks": [
		{
			"name": "task-1",
			"command": "touch /tmp/file1"
		},
		{
			"name": "task-2",
			"command": "cat /tmp/file1",
			"requires": [
				"task-3"
			]
		},
		{
			"name": "task-3",
			"command": "echo 'Hello World!' > /tmp/file1",
			"requires": [
				"task-1"
			]
		},
		{
			"name": "task-4",
			"command": "rm /tmp/file1",
			"requires": [
				"task-2",
				"task-3"
			]
		}
	]
}
```
#### Response body:
```json
[
	{
		"name": "task-1",
		"command": "touch /tmp/file1"
	},
	{
		"name": "task-3",
		"command": "echo 'Hello World!' > /tmp/file1"
	},
	{
		"name": "task-2",
		"command": "cat /tmp/file1"
	},
	{
		"name": "task-4",
		"command": "rm /tmp/file1"
	}
]
```
#### Bash commands script response text example:
```sh
touch /tmp/file1
echo "Hello World!" > /tmp/file1
cat /tmp/file1
rm /tmp/file1
```
## License:
[Apache 2.0](https://www.apache.org/licenses/LICENSE-2.0)