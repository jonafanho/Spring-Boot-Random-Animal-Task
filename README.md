# Spring Boot Random Animal Task

This project uses websockets to send random animal names to an Angular frontend. The backend service is created using
Spring Boot.

A start and stop button in the frontend is used to control the backend service. Websockets are used rather than polling
because of the following reasons:

- Websockets allow the client to listen to a topic for any updates, in which the server will post to.
- Modern websocket libraries automatically handle timeouts and reconnects, so it is very easy to implement.
- Polling requires sending constant requests to the server. This wastes system resources and network bandwidth,
  especially if updates are not expected to be very frequent. Depending on the frequency of polling, updates might also
  be quite delayed.
- RTC technically works, but it's overkill for this project. RTC is used more in realtime video streaming (such as video
  conferencing apps).

## Running the Application

To run the application, make sure [Java 17](https://www.oracle.com/java/technologies/downloads/) (or above) is
installed.

1. Head over to [GitHub Actions](https://github.com/jonafanho/Spring-Boot-Random-Animal-Task/actions) and download the
   artifact from the latest run. (You must be signed in to GitHub.)
2. Unzip the file, and using a terminal, navigate to the directory of the extracted contents.
3. Run the following command to execute the JAR file (do not run the `*-plain.jar` file):
   ```bash
   java -jar Spring-Boot-Random-Animal-Task-<version>.jar
   ```

## Building Locally

To build the project locally, make sure [Java 17](https://www.oracle.com/java/technologies/downloads/) (or above)
and [Node.js](https://nodejs.org/en/download) are installed (preferably the latest LTS version). Run the following
commands:

```bash
npm install --prefix frontend
npm run build --prefix frontend
cd backend
./gradlew build --no-daemon
```

The built JAR will appear in `backend/build/libs/Spring-Boot-Random-Animal-Task-<version>.jar`.

## Project Structure

The HTML entry point is in `app.html`. This is simply a wrapper for the rest of the application, containing a padding
around the outer edges.

The project uses [Angular Material](https://material.angular.dev/) to avoid recreating common UI elements.

### Components

Because the app is simple enough, there is only one frontend component, and it is in the `frontend/src/app/component` folder.

### Services

For the frontend, there are two services, `frontend/src/app/service/api.service.ts` and `frontend/src/app/service/websocket.service.ts`.

* The API service is responsible for calling the GET requests and storing the current running state of the backend service. On frontend app startup, the status is fetched so that the button states already match the backend server's state.
* The websocket service connects to the Spring Boot websocket on frontend app startup. It includes a listener for messages posted to the `/topic/animals` websocket topic.

For the backend, there is just one service that manages the animal name cycling. When the service is started, the `ThreadPoolTaskScheduler` (configured to only use one thread) is set to trigger the cycling at a fixed rate of 5 seconds. Every time the trigger is run, a random word is chosen. (The code also tries to avoid picking the same word two times in a row, otherwise it will be hard to tell that something happened in the frontend.)

The `synchronized` keyword is used for methods in the backend service to avoid concurrency issues, for example if multiple clients try to click start at the same time.

## Code Quality

Simple unit tests are present for each frontend component. [Angular ESLint](https://www.npmjs.com/package/@angular-eslint/eslint-plugin) has also been installed.

A simple JUnit test is present for the backend.

## Git Structure

Development (except for the initial file setup) is done on the [`dev` branch](https://github.com/jonafanho/Spring-Boot-Random-Animal-Task/tree/dev) to avoid committing directly to master. When a feature is complete, a pull request should be made to merge code to master.

The repository is set up to enforce signed commits on all branches.

Each time something is committed, a [GitHub Actions](https://github.com/jonafanho/Spring-Boot-Random-Animal-Task/actions) pipeline will run. This will run the unit tests and the linter to ensure code quality, then build the project. The built files are exported to a ZIP artifact which can be downloaded directly from GitHub for each run.
