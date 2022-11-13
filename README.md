# Welcome to Trip application
Hi! This repository contains a project that was created as final project during Kodilla Java Course.

# Specification
The main purpose of this project was to create an online trip application backend with predefined REST endpoints.
<br />  
Application is using two API's:
- [TomTomApi](https://developer.tomtom.com/)
- [Open meteo API](https://open-meteo.com/en)
  <br />

REST endpoints:
- Cities
    - get all cities
    - get single city (by unique id)
    - get single city (by uniqe name)
    - create city
    - update information about city
    - deactivate city (delete)
- Passengers
    - get all passengers
    - get single passenger (by unique id)
    - create passenger
    - update information about passenger
    - deactivate passenger
- Log in passenger
    - log in passenger (as currently selected passenger)
- Weather codes
    - get all weather codes
    - create weather code
- Weather
    - get all weathers
    - get weather in single city
- Route
    - get all routes
    - create route

# How to start application?

To start application you must have in database weather codes and description for them. Run test in package com.trip.tripapplication.domain WeatherCodeTest and in application.properties you must enter credentials to account in mailtrap.io

# Backend
Url to backend repository: https://github.com/ptynek/tripApplication/tree/e841a321665188f758447df14f55d1e956f26e66

# Documentation of endpoints
Project uses Swagger2 to provide documentation of endpoints.

>To enter Swagger 2 websites navigate to: http://localhost:8080/swagger-ui.html.