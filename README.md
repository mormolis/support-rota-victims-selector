# support-rota-victims-selector
a fair support team selector

run the application locally

```
./gradlew clean build
./gradlew run -q --console=plain 
```

### the algorithm
Given a team spread across different subteams and locations, the algorithm picks a number of people (configurable) to serve a support rota based on the date they served last time and as a secondary metric the number of times they have served.  
There are configurations available (currently on the application.properties file) on the maximum people that needs to serve on support, the maximum number of people from each location and assumed only 1 member of each subteam. The process will return a list of people (victims). The amount of people is <= to the configured number, depending on availability.

### requirements
1. in the config file the user should include the allowed subteams and the allowed locations for data validation.
2. a csv file needs to be provided with the following information **(example file provided on test resources folder)**

> Name (not null),SubTeam (not null),Location (not null) ,LastServed (default:today),ShiftsCounter(default:0),IsExcluded(default:false)

