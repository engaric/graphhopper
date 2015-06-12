ssFeature: Verify Error Messages from a routing service
   As a user
   I want to get a valid Error message and status code for a invalid route request

  #Error Messages
  #Successful request
  @ErrorMessages
  Scenario: Successful request with all parameters
    Given I have route point as
      | pointA              | pointB              |
      | 50.729961,-3.524853 | 50.723364,-3.523895 |
    And I have vehicle as "car"
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

  # Parameter :  point
  @ErrorMessages
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
      | car         |            | fastest   | json           | At least 2 points has to be specified, but was:1 | 400        |

  @ErrorMessages
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
      | car         |            | fastest   | json           | At least 2 points has to be specified, but was:1 | 400        |

  @ErrorMessages
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
      | car         |            | fastest   | json           | Cannot find point 0: 292530.0,92635.0 | 400        |

  # Parameter :  point
  @ErrorMessages
  Scenario Outline: Incorrect Parameter Name "points"
    Given I have route points as
      | pointA              | pointB              |
      | 50.729961,-3.524853 | 50.723364,-3.523895 |
    And I have vehicles as "<vehicleType>"
    And I have avoidances as "<avoidances>"
    And I have weighting as "<routeType>"
    And I have type as "<responseFormat>"
    When I request for a route
    Then I should be able to verify the response message as "<errorMessage>"
    Then I should be able to verify the statuscode as "<statusCode>"

    Examples: 
      | vehicleType | avoidances | routeType | responseFormat | errorMessage                | statusCode |
      | car         |            | fastest   | json           | No point parameter provided | 400        |

  # Parameter :  point
  @ErrorMessages
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
      | car         |            | fastest   | json           | No point parameter provided | 400        |

  # Parameter :  avoidances
  @ErrorMessages
  Scenario Outline: Invalid Parameter Value for "avoidances"
    Given I have route point as
      | pointA              | pointB              |
      | 50.729961,-3.524853 | 50.723364,-3.523895 |
    And I have vehicle as "<vehicleType>"
    And I have avoidances as "<avoidances>"
    And I have weighting as "<routeType>"
    And I have type as "<responseFormat>"
    When I request for a route
    Then I should be able to verify the response message as "<errorMessage>"
    Then I should be able to verify the statuscode as "<statusCode>"

    Examples: 
      | vehicleType | avoidances | routeType | responseFormat | errorMessage                                                                                                     | statusCode |
      | car         | trees      | fastest   | json           | Parameter blah is not a valid parameter for resource nearest. Valid parameters for requested resource are point. | 400        |

  # Parameter :  avoidances
  @ErrorMessages
  Scenario Outline: Invalid Parameter Name for "avoidances"
    Given I have route point as
      | pointA              | pointB              |
      | 50.729961,-3.524853 | 50.723364,-3.523895 |
    And I have vehicle as "<vehicleType>"
    And I have avoidance as "<avoidances>"
    And I have weighting as "<routeType>"
    And I have type as "<responseFormat>"
    When I request for a route
    Then I should be able to verify the response message as "<errorMessage>"
    Then I should be able to verify the statuscode as "<statusCode>"

    Examples: 
      | vehicleType | avoidances | routeType | responseFormat | errorMessage                                                                                                     | statusCode |
      | car         | cliff      | fastest   | json           | Parameter blah is not a valid parameter for resource nearest. Valid parameters for requested resource are point. | 400        |

  # Parameter :  weighting
  @ErrorMessages
  Scenario Outline: Invalid Parameter Value for "weighting"
    Given I have route point as
      | pointA              | pointB              |
      | 50.729961,-3.524853 | 50.723364,-3.523895 |
    And I have vehicle as "<vehicleType>"
    And I have avoidances as "<avoidances>"
    And I have weighting as "<routeType>"
    And I have type as "<responseFormat>"
    When I request for a route
    Then I should be able to verify the response message as "<errorMessage>"
    Then I should be able to verify the statuscode as "<statusCode>"

    Examples: 
      | vehicleType | avoidances | routeType | responseFormat | errorMessage                                                                                 | statusCode |
      | car         |            | quick     | json           | Weighting faster not supported. Valid weightings are shorted, fastest, fastavoid, shortavoid | 400        |

  # Parameter :  weighting
  @ErrorMessages
  Scenario Outline: Invalid Parameter Name for "weighting"
    Given I have route point as
      | pointA              | pointB              |
      | 50.729961,-3.524853 | 50.723364,-3.523895 |
    And I have vehicle as "<vehicleType>"
    And I have avoidance as "<avoidances>"
    And I have weightings as "<routeType>"
    And I have type as "<responseFormat>"
    When I request for a route
    Then I should be able to verify the response message as "<errorMessage>"
    Then I should be able to verify the statuscode as "<statusCode>"

    Examples: 
      | vehicleType | avoidances | routeType | responseFormat | errorMessage                                                                                                     | statusCode |
      | car         |            | fastest   | json           | Parameter blah is not a valid parameter for resource nearest. Valid parameters for requested resource are point. | 400        |

  # Parameter :  locale
  @ErrorMessages
  Scenario Outline: Invalid Parameter Value for "locale"      | t  |
    Given I have route point as
      | pointA              | pointB              |
      | 50.729961,-3.524853 | 50.723364,-3.523895 |
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
      | car         |            | fastest   | en     | json           | en is not a valid value for parameter locale. Valid values are bg, ca, cz, de_DE, el, en_US, es, fa, fil, fi, fr, gl, he, hu_HU, it, ja, ne, nl, pl_PL, pt_BR, pt_PT, ro, ru, si, sk, sv_SE, tr, uk, vi_VI or zh_CN | 400        |

  # Parameter :  locale
  @ErrorMessages
  Scenario Outline: Invalid Parameter Name for "locale"
    Given I have route point as
      | pointA              | pointB              |
      | 50.729961,-3.524853 | 50.723364,-3.523895 |
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
      | car         |            | fastest   | en-GB  | json           | Parameter blah is not a valid parameter for resource nearest. Valid parameters for requested resource are point. | 400        |

  # Parameter :  instructions
  @ErrorMessages
  Scenario Outline: Invalid Parameter Value for "instructions"
    Given I have route point as
      | pointA              | pointB              |
      | 50.729961,-3.524853 | 50.723364,-3.523895 |
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
      | car         |            | fastest   | msg("box")   | json           | msg("box") is not a valid value for parameter instructions. Valid values are true or false | 400        |

  # Parameter :  instructions
  @ErrorMessages
  Scenario Outline: Invalid Parameter Name for "instructions"
    Given I have route point as
      | pointA              | pointB              |
      | 50.729961,-3.524853 | 50.723364,-3.523895 |
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
      | car         |            | fastest   | true         | json           | Parameter blah is not a valid parameter for resource nearest. Valid parameters for requested resource are point. | 400        |

  # Parameter :  algorithm
  @ErrorMessages
  Scenario Outline: Invalid Parameter Value for "algorithm"
    Given I have route point as
      | pointA              | pointB              |
      | 50.729961,-3.524853 | 50.723364,-3.523895 |
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
      | car         |            | fastest   | xyz       | json           | xyz is not a valid value for parameter algorithm. Valid values are astar, astarbi, dijkstra, dijkstrabi or dijkstraOneToMany | 400        |
      | car         |            | fastest   | select*   | json           | select* blah is not a valid parameter for resource nearest. Valid parameters for requested resource are point.               | 400        |

  #
  # Parameter :  algorithm
  @ErrorMessages
  Scenario Outline: Valid Parameter Values for "algorithm"
    Given I have route point as
      | pointA              | pointB              |
      | 50.729961,-3.524853 | 50.723364,-3.523895 |
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
      | car         |            | fastest   | dijkstra   | json           |
      | car         |            | fastest   | astar      | json           |
      | car         |            | fastest   | astarbi    | json           |
      | car         |            | fastest   | dijkstrabi | json           |

  #
  # Parameter :  algorithm
  @ErrorMessages
  Scenario Outline: Invalid Parameter Name for "algorithm"
    Given I have route point as
      | pointA              | pointB              |
      | 50.729961,-3.524853 | 50.723364,-3.523895 |
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
      | car         |            | fastest   | dijkstra  | json           | Parameter blah is not a valid parameter for resource nearest. Valid parameters for requested resource are point. | 400        |

  # Parameter :  points_encoded
  @ErrorMessages
  Scenario Outline: Invalid Parameter Value for "points_encoded"
    Given I have route point as
      | pointA              | pointB              |
      | 50.729961,-3.524853 | 50.723364,-3.523895 |
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
      | car         |            | fastest   | xyz            | json           | xyz is not a valid value for parameter pointsEncodedString. Valid values are true or false | 400        |

  # Parameter :  points_encoded
  @ErrorMessages
  Scenario Outline: Invalid Parameter Name for "points_encoded"
    Given I have route point as
      | pointA              | pointB              |
      | 50.729961,-3.524853 | 50.723364,-3.523895 |
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
      | car         |            | fastest   | true           | json           | Parameter blah is not a valid parameter for resource nearest. Valid parameters for requested resource are point. | 400        |

  # Parameter :  debug
  @ErrorMessages
  Scenario Outline: Invalid Parameter Value for "debug"
    Given I have route point as
      | pointA              | pointB              |
      | 50.729961,-3.524853 | 50.723364,-3.523895 |
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
      | car         |            | fastest   | xyz   | json           | xyz is not a valid value for parameter debug. Valid values are true or false | 400        |

  # Parameter :  debug
  @ErrorMessages
  Scenario Outline: Invalid Parameter Name for "debug"
    Given I have route point as
      | pointA              | pointB              |
      | 50.729961,-3.524853 | 50.723364,-3.523895 |
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
      | car         |            | fastest   | true  | json           | Parameter blah is not a valid parameter for resource nearest. Valid parameters for requested resource are point. | 400        |

  # Parameter :  calc_points
  @ErrorMessages
  Scenario Outline: Invalid Parameter Value for "calc_points"
    Given I have route point as
      | pointA              | pointB              |
      | 50.729961,-3.524853 | 50.723364,-3.523895 |
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
      | car         |            | fastest   | xyz         | json           | xyz is not a valid value for parameter calc_points. Valid values are true or false | 400        |

  # Parameter :  calc_points
  @ErrorMessages
  Scenario Outline: Invalid Parameter Name for "calc_points"
    Given I have route point as
      | pointA              | pointB              |
      | 50.729961,-3.524853 | 50.723364,-3.523895 |
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
      | car         |            | fastest   | true        | json           | Parameter blah is not a valid parameter for resource nearest. Valid parameters for requested resource are point. | 400        |

  # Parameter :  Type
  @ErrorMessages
  Scenario Outline: Invalid Parameter Value for "type"
    Given I have route point as
      | pointA              | pointB              |
      | 50.729961,-3.524853 | 50.723364,-3.523895 |
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
      | car         |            | fastest   | true        | txt            | txt is not a valid value for parameter type. Valid values are GPX or JSON. | 400        |

  # Parameter :  Type
  @ErrorMessages
  Scenario Outline: Invalid Parameter Name for "calc_points"
    Given I have route point as
      | pointA              | pointB              |
      | 50.729961,-3.524853 | 50.723364,-3.523895 |
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
      | car         |            | fastest   | true        | json           | Parameter calc_pointSSS is not a valid parameter for resource nearest. Valid parameters for requested resource are point. | 400        |

  # http method
  @ErrorMessages
  Scenario Outline: http methods "PUT/POST/DEL/GET"
    Given I have route point as
      | pointA              | pointB              |
      | 50.729961,-3.524853 | 50.723364,-3.523895 |
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
      | car         |            | fastest   | PUT        | json           | Method Not Allowed | 405        |
      | car         |            | fastest   | DEL        | json           | Method Not Allowed | 405        |
      | car         |            | fastest   | POST       | json           | Method Not Allowed | 405        |
      | car         |            | fastest   | GET        | json           | OK                 | 200        |
      | car         |            | fastest   | OPTIONS    | json           | OK                 | 200        |

  # Nearest Point : Invalid Parameter Value "point"
  @ErrorMessages
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

  #Successful request
  @ErrorMessages
  Scenario Outline: verify valid parameter values for "locale"
    Given I have route point as
      | pointA              | pointB              |
      | 50.729961,-3.524853 | 50.723364,-3.523895 |
    And I have vehicle as "car"
    And I have avoidances as ""
    And I have weighting as "fastest"
    And I have locale as "<locale>"
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
      | locale |
      | en     |
      | bg     |
      | ca     |
      | cz     |
      | de_DE  |
      | en_US  |
      | es     |
      | fa     |
      | fil    |
      | fi     |
      | fr     |
      | gl     |
      | he     |
      | hu_HU  |
      | it     |
      | ja     |
      | ne     |
      | nl     |
      | pl_PL  |
      | pt_BR  |
      | pt_PT  |
      | ro     |
      | ru     |
      | si     |
      | sk     |
      | sv_SE  |
      | tr     |
      | uk     |
      | vi_VI  |
      | zh_CN  |

  @ErrorMessages
  Scenario Outline: verify valid parameter values for "algorithm "
    Given I have route point as
      | pointA              | pointB              |
      | 50.729961,-3.524853 | 50.723364,-3.523895 |
    And I have vehicle as "car"
    And I have avoidances as ""
    And I have weighting as "fastest"
    And I have locale as "en_US"
    And I have debug as "true"
    And I have points_encoded as "true"
    And I have points_calc as "true"
    And I have instructions as "true"
    And I have algorithm as "<algorithm>"
    And I have type as "json"
    When I request for a route
    Then I should be able to verify the http response message as "OK"
    Then I should be able to verify the http statuscode as "200"

    Examples: 
      | algorithm  |
      | dijkstra   |
      | astar      |
      | astarbi    |
      | dijkstrabi |

  @ErrorMessages
  Scenario Outline: verify valid parameter values for "debug "
    Given I have route point as
      | pointA              | pointB              |
      | 50.729961,-3.524853 | 50.723364,-3.523895 |
    And I have vehicle as "car"
    And I have avoidances as ""
    And I have weighting as "fastest"
    And I have locale as "en_US"
    And I have debug as "<debug>"
    And I have points_encoded as "true"
    And I have points_calc as "true"
    And I have instructions as "true"
    And I have algorithm as "astar"
    And I have type as "json"
    When I request for a route
    Then I should be able to verify the http response message as "OK"
    Then I should be able to verify the http statuscode as "200"

    Examples: 
      | debug |
      | true  |
      | false |

  @ErrorMessages
  Scenario Outline: verify valid parameter values for "points_calc "
    Given I have route point as
      | pointA              | pointB              |
      | 50.729961,-3.524853 | 50.723364,-3.523895 |
    And I have vehicle as "car"
    And I have avoidances as ""
    And I have weighting as "fastest"
    And I have locale as "en_US"
    And I have debug as "true"
    And I have points_encoded as "true"
    And I have points_calc as "<points_calc>"
    And I have instructions as "true"
    And I have algorithm as "astar"
    And I have type as "json"
    When I request for a route
    Then I should be able to verify the http response message as "OK"
    Then I should be able to verify the http statuscode as "200"

    Examples: 
      | points_calc |
      | true        |
      | false       |

  @ErrorMessages
  Scenario Outline: verify valid parameter values for "points_encoded "
    Given I have route point as
      | pointA              | pointB              |
      | 50.729961,-3.524853 | 50.723364,-3.523895 |
    And I have vehicle as "car"
    And I have avoidances as ""
    And I have weighting as "fastest"
    And I have locale as "en_US"
    And I have debug as "true"
    And I have points_encoded as "<points_encoded>"
    And I have points_calc as "true"
    And I have instructions as "true"
    And I have algorithm as "astar"
    And I have type as "json"
    When I request for a route
    Then I should be able to verify the http response message as "OK"
    Then I should be able to verify the http statuscode as "200"

    Examples: 
      | points_encoded |
      | true           |
      | false          |

  @ErrorMessages
  Scenario Outline: verify valid parameter values for "instructions "
    Given I have route point as
      | pointA              | pointB              |
      | 50.729961,-3.524853 | 50.723364,-3.523895 |
    And I have vehicle as "car"
    And I have avoidances as ""
    And I have weighting as "fastest"
    And I have locale as "en_US"
    And I have debug as "true"
    And I have points_encoded as "true"
    And I have points_calc as "true"
    And I have instructions as "<instructions>"
    And I have algorithm as "astar"
    And I have type as "json"
    When I request for a route
    Then I should be able to verify the http response message as "OK"
    Then I should be able to verify the http statuscode as "200"

    Examples: 
      | instructions |
      | true         |
      | false        |

  @ErrorMessages
  Scenario Outline: verify valid parameter values for "type "
    Given I have route point as
      | pointA              | pointB              |
      | 50.729961,-3.524853 | 50.723364,-3.523895 |
    And I have vehicle as "car"
    And I have avoidances as ""
    And I have weighting as "fastest"
    And I have locale as "en_US"
    And I have debug as "true"
    And I have points_encoded as "true"
    And I have points_calc as "true"
    And I have instructions as "true"
    And I have algorithm as "astar"
    And I have type as "<type>"
    When I request for a route
    Then I should be able to verify the http response message as "OK"
    Then I should be able to verify the http statuscode as "200"

    Examples: 
      | type |
      | gpx  |
      | json |
