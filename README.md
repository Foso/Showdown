<h1 align="center">Showdown - Scrum Poker Web App</h1>

[![jCenter](https://img.shields.io/badge/Apache-2.0-green.svg
)](https://github.com/Foso/Showdown/blob/master/LICENSE)
[![PRs Welcome](https://img.shields.io/badge/PRs-welcome-brightgreen.svg?style=flat-square)](http://makeapullrequest.com)


<p align="left">
    <img src ="https://github.com/Foso/Showdown/blob/master/docs/img/ShowdownExample.png" height=500 />

</p>

## Introduction  🙋‍♂️ 🙋‍
Showdown is a selfhosted web app and server, you can use for planning poker™. You can try it at http://showdown.fly.dev/#/

[![Deploy to Heroku](https://www.herokucdn.com/deploy/button.png)](https://heroku.com/deploy)

### Show some :heart: and star the repo to support the project

[![GitHub stars](https://img.shields.io/github/stars/Foso/Showdown.svg?style=social&label=Star)](https://github.com/Foso/Showdown) [![GitHub forks](https://img.shields.io/github/forks/Foso/Showdown.svg?style=social&label=Fork)](https://github.com/Foso/Showdown/fork) [![GitHub watchers](https://img.shields.io/github/watchers/Foso/Showdown.svg?style=social&label=Watch)](https://github.com/Foso/Showdown) [![Twitter Follow](https://img.shields.io/twitter/follow/jklingenberg_.svg?style=social)](https://twitter.com/jklingenberg_)


## 🎨 Features
* Automatic estimation timer for your estimations.
* Choose your estimation sequence:

  Select <kbd>Settings>Change GameMode</kbd>, on the page below you can select between:
 **Fibonacci**, **T-Shirt**, **Modified Fibonacci**, **Power of 2** or you can choose **Custom** to add a custom sequence

* [Room passwords](https://github.com/Foso/Showdown/wiki/Add-password-for-a-room)
* Auto reveal votes when all players have voted (Click the checkbox "AutoReveal Votes" under Settings)

## 🗺️ Roadmap
- <a href="https://github.com/Foso/Showdown/issues">Your idea?</a>

# 🏠 Architecture

## 🛠️ Built With
### Kotlin
[![jCenter](https://img.shields.io/badge/Kotlin-1.7.10-green.svg
)]()



### Project Structure
* <kbd>server</kbd> - A Ktor project with the server for Showdown
* <kbd>web</kbd> - The frontend of Showdown, written with Kotlin and Compose for Web
* <kbd>shared</kbd> - Shared module for <kbd>server and web</kbd>, which contains specific classes/interfaces like Error types or responses

# Development

### Backend
* Run <kbd>ShowdownApplicationKt</kbd> to start the Ktor server


### Frontend
#### Run development webpack server
* Run <kbd>./gradlew -t web:run</kbd> inside the project folder to start the development server for the Frontend. The server will run on port 3001. Open "localhost:3001" inside your browser.

#### Build production frontend files
* Run the gradle task <kbd>deployToServerAssets</kbd>, it will build the webproject and copy the files to the server project

# Deployment

### On Heroku
You can use this button [![Deploy to Heroku](https://www.herokucdn.com/deploy/button.png)](https://heroku.com/deploy)


### On Docker

Build image with

```bash
docker build -t foso-showdown . 
```

Launch container from image with

```bash
docker run -p 23567:23567 foso-showdown
```

### On other server
You can download the jar from a release tag or when you want to build it yourself you can use the gradle task <kbd>stage</kbd>. It will build a .jar inside  <kbd>server/build/install/server-shadow/lib</kbd>. You can run it with "java -jar $nameOfTheJarFile"

## ✍️ Feedback

Feel free to send feedback on [Twitter](https://twitter.com/jklingenberg_) or [file an issue](https://github.com/foso/Showdown/issues/new). Feature requests and Pull Requests are always welcome.


## 📜 License

-------

This project is licensed under Apache License, Version 2.0

    Copyright 2020 Jens Klingenberg

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

