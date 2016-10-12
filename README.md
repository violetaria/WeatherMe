# *WeatherMe*

**WeatherMe** is an android app that shows the current weather for Atlanta + 5 days out. The app utilizes [Open Weathermap API](http://openweathermap.org).

Time spent: 7 hours spent in total

## User Stories

The following **required** functionality is completed:

* [x] Current weather data is pulled from Current Weather API
* [x] Five day forecast data is pulled from Daily Forecast API 
* [x] User can tap on any weather item in results to see weather details for that day
* [x] Handle various sizes of phones
* [x] No 3rd party libraries used

## Video Walkthrough

Here's a walkthrough of implemented user stories:

### Nexus 6P (real device)
![Walkthrough](http://i.imgur.com/bCQs48p.gif)

### Nexus 5 (Genymotion emulator)
![Walkthrough](http://i.imgur.com/dsBEWjT.gif)

GIFs created with Android Studio Recording and [LiceCap](http://www.cockos.com/licecap/).

## Notes

- Daily Forecast API returns back today in results which had to be filtered out.
- Current Weather API does not return back true min/max temps for the day based on API documentations

## License

    Copyright [2016] [Terri Chu]

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
