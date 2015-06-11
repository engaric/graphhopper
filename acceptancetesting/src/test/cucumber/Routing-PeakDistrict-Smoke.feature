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
      | vehicleType | avoidances | routeType | errorMessage                                                   | statusCode | httpErrorMessage |
      | 123         |            | fastest   | Vehicle 123 is not a valid vehicle. Valid vehicles are foot.   | 400        | Bad Request      |
      | car        |            | fastest   | Vehicle car is not a valid vehicle. Valid vehicles are foot.   | 400        | Bad Request      |
      | cycle       |            | fastest   | Vehicle cycle is not a valid vehicle. Valid vehicles are foot. | 400        | Bad Request      |
      | Bike        |            | fastest   | Vehicle Bike is not a valid vehicle. Valid vehicles are foot.  | 400        | Bad Request      |

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

  # Parameter :  point
  @ErrorMessages @Smoke
  Scenario Outline: Incorrect Parameter Value "point"
    Given I have route point as
      | pointA           | pointB              |
      | 50.729961,string | 50.723364,-3.523895 |
    And I have vehicles as "<vehicleType>"
    And I have avoidances as "<avoidances>"
    And I have weighting as "<routeType>"
    And I have type as "<responseFormat>"
    When I request for a route
    Then I should be able to verify the response message as "<errorMessage>"
    Then I should be able to verify the statuscode as "<statusCode>"

    Examples: 
      | vehicleType | avoidances | routeType | responseFormat | errorMessage                                     | statusCode |
      | foot        |            | fastest   | json           | At least 2 points has to be specified, but was:1 | 400        |

  @ErrorMessages @Smoke
  Scenario Outline: Incorrect Parameter Value "point"
    Given I have route point as
      | pointA              |
      | 50.723364,-3.523895 |
    And I have vehicles as "<vehicleType>"
    And I have avoidances as "<avoidances>"
    And I have weighting as "<routeType>"
    And I have type as "<responseFormat>"
    When I request for a route
    Then I should be able to verify the response message as "<errorMessage>"
    Then I should be able to verify the statuscode as "<statusCode>"

    Examples: 
      | vehicleType | avoidances | routeType | responseFormat | errorMessage                                     | statusCode |
      | foot        |            | fastest   | json           | At least 2 points has to be specified, but was:1 | 400        |

  @ErrorMessages @Smoke
  Scenario Outline: Incorrect Parameter Value "point"
    Given I have route point as
      | pointA       | pointB              |
      | 292530,92635 | -3.523895,50.723364 |
    And I have vehicles as "<vehicleType>"
    And I have avoidances as "<avoidances>"
    And I have weighting as "<routeType>"
    And I have type as "<responseFormat>"
    When I request for a route
    Then I should be able to verify the response message as "<errorMessage>"
    Then I should be able to verify the statuscode as "<statusCode>"

    Examples: 
      | vehicleType | avoidances | routeType | responseFormat | errorMessage                          | statusCode |
      | foot        |            | fastest   | json           | Cannot find point 0: 292530.0,92635.0 | 400        |

  # Parameter :  point
  @ErrorMessages @Smoke
  Scenario Outline: Incorrect Parameter Name "points"
    Given I have route points as
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
      | vehicleType | avoidances | routeType | responseFormat | errorMessage                | statusCode |
      | foot        |            | fastest   | json           | No point parameter provided | 400        |

  # Parameter :  point
  @ErrorMessages @Smoke
  Scenario Outline: Missing Parameter "point"
    Given I have vehicle as "<vehicleType>"
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

  # Parameter :  locale
  @ErrorMessages @Smoke
  Scenario Outline: Invalid Parameter Value for "locale"
    Given I have route point as
      | pointA              | pointB              |
      | 53.298525,-1.679533 | 53.203145,-1.799292 |
    And I have vehicle as "<vehicleType>"
    And I have avoidances as "<avoidances>"
    And I have locale as "<locale>"
    And I have weighting as "<routeType>"
    And I have type as "<responseFormat>"
    When I request for a route
    Then I should be able to verify the response message as "<errorMessage>"
    Then I should be able to verify the statuscode as "<statusCode>"

    Examples: 
      | vehicleType | avoidances | routeType | locale | responseFormat | errorMessage                                                                                                                                                                                                        | statusCode |
      | foot        |            | fastest   | en     | json           | en is not a valid value for parameter locale. Valid values are bg, ca, cz, de_DE, el, en_US, es, fa, fil, fi, fr, gl, he, hu_HU, it, ja, ne, nl, pl_PL, pt_BR, pt_PT, ro, ru, si, sk, sv_SE, tr, uk, vi_VI or zh_CN | 400        |

  # Parameter :  locale
  @ErrorMessages @Smoke
  Scenario Outline: Invalid Parameter Name for "locale"
    Given I have route point as
      | pointA              | pointB              |
      | 53.298525,-1.679533 | 53.203145,-1.799292 |
    And I have vehicle as "<vehicleType>"
    And I have avoidance as "<avoidances>"
    And I have locals as "<locale>"
    And I have weightings as "<routeType>"
    And I have type as "<responseFormat>"
    When I request for a route
    Then I should be able to verify the response message as "<errorMessage>"
    Then I should be able to verify the statuscode as "<statusCode>"

    Examples: 
      | vehicleType | avoidances | routeType | locale | responseFormat | errorMessage                                                                                                     | statusCode |
      | foot        |            | fastest   | en-GB  | json           | Parameter blah is not a valid parameter for resource nearest. Valid parameters for requested resource are point. | 400        |

  # Parameter :  instructions
  @ErrorMessages @Smoke
  Scenario Outline: Invalid Parameter Value for "instructions"
    Given I have route point as
      | pointA              | pointB              |
      | 53.298525,-1.679533 | 53.203145,-1.799292 |
    And I have vehicle as "<vehicleType>"
    And I have avoidances as "<avoidances>"
    And I have instructions as "<instructions>"
    And I have weighting as "<routeType>"
    And I have type as "<responseFormat>"
    When I request for a route
    Then I should be able to verify the response message as "<errorMessage>"
    Then I should be able to verify the statuscode as "<statusCode>"

    Examples: 
      | vehicleType | avoidances | routeType | instructions | responseFormat | errorMessage                                                                               | statusCode |
      | foot        |            | fastest   | msg("box")   | json           | msg("box") is not a valid value for parameter instructions. Valid values are true or false | 400        |

  # Parameter :  instructions
  @ErrorMessages @Smoke
  Scenario Outline: Invalid Parameter Name for "instructions"
    Given I have route point as
      | pointA              | pointB              |
      | 53.298525,-1.679533 | 53.203145,-1.799292 |
    And I have vehicle as "<vehicleType>"
    And I have avoidance as "<avoidances>"
    And I have instruction as "<instructions>"
    And I have weightings as "<routeType>"
    And I have type as "<responseFormat>"
    When I request for a route
    Then I should be able to verify the response message as "<errorMessage>"
    Then I should be able to verify the statuscode as "<statusCode>"

    Examples: 
      | vehicleType | avoidances | routeType | instructions | responseFormat | errorMessage                                                                                                     | statusCode |
      | foot        |            | fastest   | true         | json           | Parameter blah is not a valid parameter for resource nearest. Valid parameters for requested resource are point. | 400        |

  # Parameter :  algorithm
  @ErrorMessages @Smoke
  Scenario Outline: Invalid Parameter Value for "algorithm"
    Given I have route point as
      | pointA              | pointB              |
      | 53.298525,-1.679533 | 53.203145,-1.799292 |
    And I have vehicle as "<vehicleType>"
    And I have avoidances as "<avoidances>"
    And I have algorithm as "<algorithm>"
    And I have weighting as "<routeType>"
    And I have type as "<responseFormat>"
    When I request for a route
    Then I should be able to verify the response message as "<errorMessage>"
    Then I should be able to verify the statuscode as "<statusCode>"

    Examples: 
      | vehicleType | avoidances | routeType | algorithm | responseFormat | errorMessage                                                                                                                 | statusCode |
      | foot        |            | fastest   | xyz       | json           | xyz is not a valid value for parameter algorithm. Valid values are astar, astarbi, dijkstra, dijkstrabi or dijkstraOneToMany | 400        |
      | foot        |            | fastest   | select*   | json           | select* blah is not a valid parameter for resource nearest. Valid parameters for requested resource are point.               | 400        |

  #
  # Parameter :  algorithm
  @ErrorMessages @Smoke
  Scenario Outline: Valid Parameter Values for "algorithm"
    Given I have route point as
      | pointA              | pointB              |
      | 53.298525,-1.679533 | 53.203145,-1.799292 |
    And I have vehicle as "<vehicleType>"
    And I have avoidances as "<avoidances>"
    And I have algorithm as "<algorithm>"
    And I have weighting as "<routeType>"
    And I have type as "<responseFormat>"
    When I request for a route
    Then I should be able to verify the http response message as "OK"
    Then I should be able to verify the http statuscode as "200"

    Examples: 
      | vehicleType | avoidances | routeType | algorithm  | responseFormat |
      | foot        |            | fastest   | dijkstra   | json           |
      | foot        |            | fastest   | astar      | json           |
      | foot        |            | fastest   | astarbi    | json           |
      | foot        |            | fastest   | dijkstrabi | json           |

  #
  # Parameter :  algorithm
  @ErrorMessages @Smoke
  Scenario Outline: Invalid Parameter Name for "algorithm"
    Given I have route point as
      | pointA              | pointB              |
      | 53.298525,-1.679533 | 53.203145,-1.799292 |
    And I have vehicle as "<vehicleType>"
    And I have avoidance as "<avoidances>"
    And I have algorithms as "<algorithm>"
    And I have weightings as "<routeType>"
    And I have type as "<responseFormat>"
    When I request for a route
    Then I should be able to verify the response message as "<errorMessage>"
    Then I should be able to verify the statuscode as "<statusCode>"

    Examples: 
      | vehicleType | avoidances | routeType | algorithm | responseFormat | errorMessage                                                                                                     | statusCode |
      | foot        |            | fastest   | dijkstra  | json           | Parameter blah is not a valid parameter for resource nearest. Valid parameters for requested resource are point. | 400        |

  # Parameter :  points_encoded
  @ErrorMessages @Smoke
  Scenario Outline: Invalid Parameter Value for "points_encoded"
    Given I have route point as
      | pointA              | pointB              |
      | 53.298525,-1.679533 | 53.203145,-1.799292 |
    And I have vehicle as "<vehicleType>"
    And I have avoidances as "<avoidances>"
    And I have points_encoded as "<points_encoded>"
    And I have weighting as "<routeType>"
    And I have type as "<responseFormat>"
    When I request for a route
    Then I should be able to verify the response message as "<errorMessage>"
    Then I should be able to verify the statuscode as "<statusCode>"

    Examples: 
      | vehicleType | avoidances | routeType | points_encoded | responseFormat | errorMessage                                                                               | statusCode |
      | foot        |            | fastest   | xyz            | json           | xyz is not a valid value for parameter pointsEncodedString. Valid values are true or false | 400        |

  # Parameter :  points_encoded
  @ErrorMessages @Smoke
  Scenario Outline: Invalid Parameter Name for "points_encoded"
    Given I have route point as
      | pointA              | pointB              |
      | 53.298525,-1.679533 | 53.203145,-1.799292 |
    And I have vehicle as "<vehicleType>"
    And I have avoidance as "<avoidances>"
    And I have points_encodedSSS as "<algorithm>"
    And I have weightings as "<routeType>"
    And I have type as "<responseFormat>"
    When I request for a route
    Then I should be able to verify the response message as "<errorMessage>"
    Then I should be able to verify the statuscode as "<statusCode>"

    Examples: 
      | vehicleType | avoidances | routeType | points_encoded | responseFormat | errorMessage                                                                                                     | statusCode |
      | foot        |            | fastest   | true           | json           | Parameter blah is not a valid parameter for resource nearest. Valid parameters for requested resource are point. | 400        |

  # Parameter :  debug
  @ErrorMessages @Smoke
  Scenario Outline: Invalid Parameter Value for "debug"
    Given I have route point as
      | pointA              | pointB              |
      | 53.298525,-1.679533 | 53.203145,-1.799292 |
    And I have vehicle as "<vehicleType>"
    And I have avoidances as "<avoidances>"
    And I have debug as "<debug>"
    And I have weighting as "<routeType>"
    And I have type as "<responseFormat>"
    When I request for a route
    Then I should be able to verify the response message as "<errorMessage>"
    Then I should be able to verify the statuscode as "<statusCode>"

    Examples: 
      | vehicleType | avoidances | routeType | debug | responseFormat | errorMessage                                                                 | statusCode |
      | foot        |            | fastest   | xyz   | json           | xyz is not a valid value for parameter debug. Valid values are true or false | 400        |

  # Parameter :  debug
  @ErrorMessages @Smoke
  Scenario Outline: Invalid Parameter Name for "debug"
    Given I have route point as
      | pointA              | pointB              |
      | 53.298525,-1.679533 | 53.203145,-1.799292 |
    And I have vehicle as "<vehicleType>"
    And I have avoidance as "<avoidances>"
    And I have debug as "<debug>"
    And I have weightings as "<routeType>"
    And I have type as "<responseFormat>"
    When I request for a route
    Then I should be able to verify the response message as "<errorMessage>"
    Then I should be able to verify the statuscode as "<statusCode>"

    Examples: 
      | vehicleType | avoidances | routeType | debug | responseFormat | errorMessage                                                                                                     | statusCode |
      | foot        |            | fastest   | true  | json           | Parameter blah is not a valid parameter for resource nearest. Valid parameters for requested resource are point. | 400        |

  # Parameter :  calc_points
  @ErrorMessages @Smoke
  Scenario Outline: Invalid Parameter Value for "calc_points"
    Given I have route point as
      | pointA              | pointB              |
      | 53.298525,-1.679533 | 53.203145,-1.799292 |
    And I have vehicle as "<vehicleType>"
    And I have avoidances as "<avoidances>"
    And I have calc_points as "<debug>"
    And I have weighting as "<routeType>"
    And I have type as "<responseFormat>"
    When I request for a route
    Then I should be able to verify the response message as "<errorMessage>"
    Then I should be able to verify the statuscode as "<statusCode>"

    Examples: 
      | vehicleType | avoidances | routeType | calc_points | responseFormat | errorMessage                                                                       | statusCode |
      | foot        |            | fastest   | xyz         | json           | xyz is not a valid value for parameter calc_points. Valid values are true or false | 400        |

  # Parameter :  calc_points
  @ErrorMessages @Smoke
  Scenario Outline: Invalid Parameter Name for "calc_points"
    Given I have route point as
      | pointA              | pointB              |
      | 53.298525,-1.679533 | 53.203145,-1.799292 |
    And I have vehicle as "<vehicleType>"
    And I have avoidance as "<avoidances>"
    And I have calc_point as "<calc_points>"
    And I have weightings as "<routeType>"
    And I have type as "<responseFormat>"
    When I request for a route
    Then I should be able to verify the response message as "<errorMessage>"
    Then I should be able to verify the statuscode as "<statusCode>"

    Examples: 
      | vehicleType | avoidances | routeType | calc_points | responseFormat | errorMessage                                                                                                     | statusCode |
      | foot        |            | fastest   | true        | json           | Parameter blah is not a valid parameter for resource nearest. Valid parameters for requested resource are point. | 400        |

  # Parameter :  Type
  @ErrorMessages @Smoke
  Scenario Outline: Invalid Parameter Value for "type"
    Given I have route point as
      | pointA              | pointB              |
      | 53.298525,-1.679533 | 53.203145,-1.799292 |
    And I have vehicle as "<vehicleType>"
    And I have avoidances as "<avoidances>"
    And I have calc_points as "<debug>"
    And I have weighting as "<routeType>"
    And I have type as "<responseFormat>"
    When I request for a route
    Then I should be able to verify the response message as "<errorMessage>"
    Then I should be able to verify the statuscode as "<statusCode>"

    Examples: 
      | vehicleType | avoidances | routeType | calc_points | responseFormat | errorMessage                                                               | statusCode |
      | foot        |            | fastest   | true        | txt            | txt is not a valid value for parameter type. Valid values are GPX or JSON. | 400        |

  # Parameter :  Type
  @ErrorMessages @Smoke
  Scenario Outline: Invalid Parameter Name for "calc_points"
    Given I have route point as
      | pointA              | pointB              |
      | 53.298525,-1.679533 | 53.203145,-1.799292 |
    And I have vehicle as "<vehicleType>"
    And I have avoidance as "<avoidances>"
    And I have calc_pointSSS as "<calc_points>"
    And I have weightings as "<routeType>"
    And I have responseType as "<responseFormat>"
    When I request for a route
    Then I should be able to verify the response message as "<errorMessage>"
    Then I should be able to verify the statuscode as "<statusCode>"

    Examples: 
      | vehicleType | avoidances | routeType | calc_points | responseFormat | errorMessage                                                                                                              | statusCode |
      | foot        |            | fastest   | true        | json           | Parameter calc_pointSSS is not a valid parameter for resource nearest. Valid parameters for requested resource are point. | 400        |

  # http method
  @ErrorMessages @Smoke
  Scenario Outline: http methods "PUT/POST/DEL/GET"
    Given I have route point as
      | pointA              | pointB              |
      | 53.298525,-1.679533 | 53.203145,-1.799292 |
    Given I have vehicle as "<vehicleType>"
    And I have avoidances as "<avoidances>"
    And I have weighting as "<routeType>"
    And I have type as "<responseFormat>"
    And I request for HTTP "<httpMethod>" method
    When I request for a route
    Then I should be able to verify the http response message as "<httpErrorMessage>"
    Then I should be able to verify the http statuscode as "<statusCode>"

    Examples: 
      | vehicleType | avoidances | routeType | httpMethod | responseFormat | httpErrorMessage   | statusCode |
      | foot        |            | fastest   | PUT        | json           | Method Not Allowed | 405        |
      | foot        |            | fastest   | DEL        | json           | Method Not Allowed | 405        |
      | foot        |            | fastest   | POST       | json           | Method Not Allowed | 405        |
      | foot        |            | fastest   | GET        | json           | OK                 | 200        |
      | foot        |            | fastest   | OPTIONS    | json           | OK                 | 200        |

  # Nearest Point : Invalid Parameter Value "point"
  @ErrorMessages @Smoke
  Scenario Outline: Verify  nearest point of point using NearestPoint API
    Given I have type as "<responseFormat>"
    And My routing points for nearestPoint API as "<pointA>"
    Then I should be able to verify the response message as "<errorMessage>"
    Then I should be able to verify the statuscode as "<statusCode>"

    #  Then I should be able to verify the http response message as "<httpErrorMessage>"
    #Then I should be able to verify the http statuscode as "<statusCode>"
    Examples: 
      | pointA              | errorMessage | responseFormat | statusCode |
      | 51.878966,-0.903849 | e            | json           | 400        |

      
       @Smoke
  Scenario Outline: Verify DPN Route with avoidance -(A Road)
    Given I have route point as
      | pointA              | pointB              |
      | 53.298525,-1.679533 | 53.203145,-1.799292 |
    And I have vehicle as "<vehicleType>"
    And I have avoidances as "<avoidances>"
    And I have weighting as "<routeType>"
    When I request for a route
    Then I should be able to verify the waypoints not on the route map:
      | wayPointIndex | waypointco         | waypointdesc           | azimuth | direction | time  | distance | avoidance |
      | 6             | 53.28017,-1.711765 | Turn right onto A Road | 236.0   | SW        | 43838 | 219.2    | ARoad     |

    Examples: 
      | vehicleType  | avoidances | routeType |
      | mountainbike | A Road      |      fastavoid     |
      