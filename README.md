<h1 align="center">Showdown - Scrum Poker Web App</h1>

[![jCenter](https://img.shields.io/badge/Apache-2.0-green.svg
)](https://github.com/Foso/Showdown/blob/master/LICENSE)
[![PRs Welcome](https://img.shields.io/badge/PRs-welcome-brightgreen.svg?style=flat-square)](http://makeapullrequest.com)


<p align="left">
    <img src ="https://github.com/Foso/Showdown/blob/master/docs/img/ShowdownExample.png" height=500 />
 
</p>

## Introduction  🙋‍♂️ 🙋‍
Showdown is a selfhosted web app, you can use for planning poker™. The application does not require any registration at all. All you need is your browser. You can try it at  http://shwdwn.herokuapp.com/#/

[![Deploy to Heroku](https://www.herokucdn.com/deploy/button.png)](https://heroku.com/deploy)

### Show some :heart: and star the repo to support the project

[![GitHub stars](https://img.shields.io/github/stars/Foso/Showdown.svg?style=social&label=Star)](https://github.com/Foso/Showdown) [![GitHub forks](https://img.shields.io/github/forks/Foso/Showdown.svg?style=social&label=Fork)](https://github.com/Foso/Showdown/fork) [![GitHub watchers](https://img.shields.io/github/watchers/Foso/Showdown.svg?style=social&label=Watch)](https://github.com/Foso/Showdown) [![Twitter Follow](https://img.shields.io/twitter/follow/jklingenberg_.svg?style=social)](https://twitter.com/jklingenberg_)


## 🎨 Features
* Automatic estimation timer for your estimations.
* Choose your estimation sequence:
  
  Select <kbd>Settings>Change GameMode</kbd>, on the page below you can select between:
 **Fibonacci**, **T-Shirt**, **Modified Fibonacci**, **Power of 2** or you can choose **Custom** to add a custom sequence



## 🗺️ Roadmap
- [ ] Adding passwords to rooms
- <a href="https://github.com/Foso/Showdown/issues">Your idea?</a>

# 🏠 Architecture

## 🛠️ Built With
### Kotlin
[![jCenter](https://img.shields.io/badge/Kotlin-1.3.72-green.svg
)]()



### Project Structure
* <kbd>server</kbd> - A Ktor project with the server for Showdown
* <kbd>web</kbd> - The frontend of Showdown, written with Kotlin and Kotlin React
* <kbd>shared</kbd> - Shared module for <kbd>server and web</kbd>, which contains specific classes/interfaces like Error types or responses

# Development

### Backend
* Run <kbd>ShowdownApplicationKt</kdb> to start the Ktor server


### Frontend
#### Run development webpack server
* Run <kbd>./gradlew -t web:run</kdb> inside the project folder to start the development server for the Frontend


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

