# [Homework 1.7](https://github.com/hendraanggrian/IIT-CS425/raw/assets/assignments/homework1_7.pdf): LaunchpadERD

Launch Pad business rules:

1. A launchpad hosts multiple launches over time, and each launch is hosted by
  exactly one launchpad. Therefore, Launchpads will be created in the database
  even before the first launch is hosted there.
2. A launchpad is located in a country, and a country, in turn, is located on a
  continent.
3. Each launchpad has a name and a location.
4. A launch requires a launchpad that hosts it, a launch vehicle (the rocket),
  and the payload.
5. For a launch, the date, time and name should be recorded as well as whether
  it is a crewed launch or not.
6. Each launch is performed by a launch vehicle. The serial number for the
  launch vehicle should be recorded. A launch vehicle can perform multiple
  launches over time.
7. Each launch vehicle is of a specific launch vehicle type. The name, maximum
  thrust and whether it is reusable need to be recorded for the launch vehicle
  type.
8. A specific manufacturer makes each launch vehicle type, and a manufacturer
  can make many different launch vehicle types.
9. Each launch carries one payload, and a payload is only carried by one launch.
10. A manufacturer also makes each payload, and a manufacturer can make many
  different payloads over time.
11. Multiple crew members can be carried on a crewed launch, and each crew
  member can be carried on multiple launches over time.
12. Each crew member has a nationality (the country they come from), and their
  name and surname also need to be recorded.
13. Crew members can appear in the database before they launch for the first
  time.

## Schema

```sql
CREATE SCHEMA IF NOT EXISTS LaunchpadERD;
USE LaunchpadERD;
```

[View full code](https://github.com/hendraanggrian/IIT-CS425/blob/main/launchpad_erd/initialize.sql)
