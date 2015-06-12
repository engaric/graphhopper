Feature: Smoke Tests- Peak District: Verify a route from A to B
   As a user
   I want to get a route from location A to location B using the routing service
   And route should be the fastest route and contain the waypoints,restrictions,time and other instructions

  #Error Messages
  #Successful request
  @ErrorMessages @Smoke
  Scenario: Successful request with all parameters
    Given I have route point as
      | pointA              | pointB              |
      | 53.298525,-1.679533 | 53.203145,-1.799292 |
    And I have vehicle as "foot"
    And I have avoidances as ""
    And I have weighting as "fastest"
    And I have locale as "en_US"
    And I have debug as "true"
    And I have points_encoded as "true"
    And I have points_calc as "true"
    And I have instructions as "true"
    And I have algorithm as "astar"
    And I have type as "json"
    When I request for a route
    Then I should be able to verify the http response message as "OK"
    Then I should be able to verify the http statuscode as "200"
    Then I should be able to verify the waypoints on the route map:
      | wayPointIndex | waypointco          | waypointdesc                | azimuth | direction | time | distance | avoidance |
      | 2             | 50.729205,-3.523206 | Turn right onto WELL STREET | 210.0   | SW        | 4050 | 112.5    |           |

  # Parameter :  vehicle
  @ErrorMessages @Smoke
  Scenario Outline: Incorrect Parameter Value for "Vehicle"
    Given I have route point as
      | pointA              | pointB              |
      | 53.298525,-1.679533 | 53.203145,-1.799292 |
    And I have vehicle as "<vehicleType>"
    And I have avoidances as "<avoidances>"
    And I have weighting as "<routeType>"
    When I request for a route
    Then I should be able to verify the http response message as "<httpErrorMessage>"
    Then I should be able to verify the http statuscode as "<statusCode>"
    Then I should be able to verify the response message as "<errorMessage>"
    Then I should be able to verify the statuscode as "<statusCode>"

    Examples: 
      | vehicleType | avoidances | routeType | errorMessage                                                     | statusCode | httpErrorMessage |
      | 123         |            | fastest   | Vehicle 123 is not a valid vehicle. Valid vehicles are foot,mtb. | 400        | Bad Request      |
      | car         |            | fastest   | Vehicle 123 is not a valid vehicle. Valid vehicles are foot,mtb. | 400        | Bad Request      |
      | cycle       |            | fastest   | Vehicle 123 is not a valid vehicle. Valid vehicles are foot,mtb. | 400        | Bad Request      |
      | Bike        |            | fastest   | Vehicle 123 is not a valid vehicle. Valid vehicles are foot,mtb. | 400        | Bad Request      |

  # Parameter :  vehicle
  @ErrorMessages @Smoke @Current
  Scenario Outline: Incorrect Parameter Name "vehicles"
    Given I have route point as
      | pointA              | pointB              |
      | 53.298525,-1.679533 | 53.203145,-1.799292 |
    And I have vehicles as "<vehicleType>"
    And I have avoidances as "<avoidances>"
    And I have weighting as "<routeType>"
    And I have type as "<responseFormat>"
    When I request for a route
    Then I should be able to verify the response message as "<errorMessage>"
    Then I should be able to verify the statuscode as "<statusCode>"

    Examples: 
      | vehicleType | avoidances | routeType | responseFormat | errorMessage | statusCode |
      | foot        |            | fastest   | json           |              | 400        |

  # Parameter :  vehicle
  @ErrorMessages @Smoke
  Scenario Outline: Missing Parameter "vehicle"
    Given I have route points as
      | pointA              | pointB              |
      | 53.298525,-1.679533 | 53.203145,-1.799292 |
    And I have avoidances as "<avoidances>"
    And I have weighting as "<routeType>"
    And I have type as "<responseFormat>"
    When I request for a route
    Then I should be able to verify the response message as "<errorMessage>"
    Then I should be able to verify the statuscode as "<statusCode>"

    Examples: 
      | vehicleType | avoidances | routeType | responseFormat | errorMessage                | statusCode |
      | foot        |            | fastest   | json           | No point parameter provided | 400        |

  # Parameter :  avoidances
  @ErrorMessages @Smoke
  Scenario Outline: Invalid Parameter Value for "avoidances"
    Given I have route point as
      | pointA              | pointB              |
      | 53.298525,-1.679533 | 53.203145,-1.799292 |
    And I have vehicle as "<vehicleType>"
    And I have avoidances as "<avoidances>"
    And I have weighting as "<routeType>"
    And I have type as "<responseFormat>"
    When I request for a route
    Then I should be able to verify the response message as "<errorMessage>"
    Then I should be able to verify the statuscode as "<statusCode>"

    Examples: 
      | vehicleType | avoidances | routeType | responseFormat | errorMessage                                                                                                     | statusCode |
      | foot        | trees      | fastest   | json           | Parameter blah is not a valid parameter for resource nearest. Valid parameters for requested resource are point. | 400        |

  # Parameter :  avoidances
  @ErrorMessages @Smoke
  Scenario Outline: Invalid Parameter Name for "avoidances"
    Given I have route point as
      | pointA              | pointB              |
      | 53.298525,-1.679533 | 53.203145,-1.799292 |
    And I have vehicle as "<vehicleType>"
    And I have avoidance as "<avoidances>"
    And I have weighting as "<routeType>"
    And I have type as "<responseFormat>"
    When I request for a route
    Then I should be able to verify the response message as "<errorMessage>"
    Then I should be able to verify the statuscode as "<statusCode>"

    Examples: 
      | vehicleType | avoidances | routeType | responseFormat | errorMessage                                                                                                     | statusCode |
      | foot        | cliff      | fastest   | json           | Parameter blah is not a valid parameter for resource nearest. Valid parameters for requested resource are point. | 400        |

  # Parameter :  weighting
  @ErrorMessages @Smoke
  Scenario Outline: Invalid Parameter Value for "weighting"
    Given I have route point as
      | pointA              | pointB              |
      | 53.298525,-1.679533 | 53.203145,-1.799292 |
    And I have vehicle as "<vehicleType>"
    And I have avoidances as "<avoidances>"
    And I have weighting as "<routeType>"
    And I have type as "<responseFormat>"
    When I request for a route
    Then I should be able to verify the response message as "<errorMessage>"
    Then I should be able to verify the statuscode as "<statusCode>"

    Examples: 
      | vehicleType | avoidances | routeType | responseFormat | errorMessage                                                                                 | statusCode |
      | foot        |            | quick     | json           | Weighting faster not supported. Valid weightings are shorted, fastest, fastavoid, shortavoid | 400        |

  # Parameter :  weighting
  @ErrorMessages @Smoke
  Scenario Outline: Invalid Parameter Name for "weighting"
    Given I have route point as
      | pointA              | pointB              |
      | 53.298525,-1.679533 | 53.203145,-1.799292 |
    And I have vehicle as "<vehicleType>"
    And I have avoidance as "<avoidances>"
    And I have weightings as "<routeType>"
    And I have type as "<responseFormat>"
    When I request for a route
    Then I should be able to verify the response message as "<errorMessage>"
    Then I should be able to verify the statuscode as "<statusCode>"

    Examples: 
      | vehicleType | avoidances | routeType | responseFormat | errorMessage                                                                                                     | statusCode |
      | foot        |            | fastest   | json           | Parameter blah is not a valid parameter for resource nearest. Valid parameters for requested resource are point. | 400        |

  # Parameter :  weighting
  @ErrorMessages @Smoke
  Scenario Outline: Valid Parameter Values for "weighting"
    Given I have route point as
      | pointA              | pointB              |
      | 50.729961,-3.524853 | 50.723364,-3.523895 |
    And I have vehicle as "foot"
    And I have avoidances as "<avoidances>"
    And I have weighting as "<weighting>"
    And I have locale as "en_US"
    And I have debug as "true"
    And I have points_encoded as "true"
    And I have points_calc as "true"
    And I have instructions as "true"
    And I have algorithm as "astar"
    And I have type as "json"
    When I request for a route
    Then I should be able to verify the http response message as "OK"
    Then I should be able to verify the http statuscode as "200"

    Examples: 
      | weighting  | avoidances |
      | fastest    |            |
      | shortest   |            |
      | shortavoid | cliff      |
      | fastavoid  | cliff      |

  # Parameter :  avoidances
  @ErrorMessages @Smoke
  Scenario Outline: Valid Parameter Values for "avoidances"
    Given I have route point as
      | pointA              | pointB              |
      | 50.729961,-3.524853 | 50.723364,-3.523895 |
    And I have vehicle as "foot"
    And I have avoidances as "<avoidances>"
    And I have weighting as "fastest"
    And I have locale as "en_US"
    And I have debug as "true"
    And I have points_encoded as "true"
    And I have points_calc as "true"
    And I have instructions as "true"
    And I have algorithm as "astar"
    And I have type as "json"
    When I request for a route
    Then I should be able to verify the http response message as "OK"
    Then I should be able to verify the http statuscode as "200"

    Examples: 
      | avoidances                                                                                                 |
      | aroad, boulders, cliff, inlandwater, marsh, quarryorpit, scree, rock, mud, sand, shingle, spoil,tidalwater |
      | boulders,aroad,  cliff, inlandwater, marsh, quarryorpit, scree, rock, mud, sand, shingle, spoil,tidalwater |
      | marsh, quarryorpit, scree, rock, mud, sand, shingle, spoil,tidalwater                                      |
