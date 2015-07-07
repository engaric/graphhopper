Feature: Verify Error Messages for non-vehicle Routing service (Peak District)
   As a user
   I want to get a valid Error message and status code for a invalid route request

  #Error Messages
  #Successful request
  @ErrorMessages
  Scenario Outline: Successful request with all parameters
    Given I have route <point> as
      | pointA              | pointB              |
      | 53.384508,-1.933148 | 53.207259,-1.859641 |
    And I have <vehicle> as "foot"
    And I have <weighting> as "fastest"
    And I have <locale> as "en_GB"
    And I have <debug> as "true"
    And I have <points_encoded> as "true"
    And I have <calc_points> as "true"
    And I have <instructions> as "true"
    And I have <algorithm> as "astar"
    And I have <type> as "json"
    When I request for a route
    Then I should be able to verify the http response message as "OK"
    And I should be able to verify the http statuscode as "200"
    And I receive a valid routing response

    Examples: 
      | point | vehicle | weighting | locale | debug | points_encoded | calc_points | instructions | algorithm | type |
      | point | vehicle | weighting | locale | debug | points_encoded | calc_points | instructions | algorithm | type |
      | POINT | VEHICLE | WEIGHTING | LOCALE | DEBUG | POINTS_ENCODED | CALC_POINTS | INSTRUCTIONS | ALGORITHM | TYPE |
      | Point | Vehicle | Weighting | Locale | Debug | Points_Encoded | Calc_Points | Instructions | Algorithm | Type |
      | POinT | VEHiCLE | WEIGHtING | LOCaLE | DEbUG | POINTs_ENCODED | CALc_POINTS | INSTRuCTIONS | ALGOrITHM | TYpE |

  # Parameter :  point
  @ErrorMessages
  Scenario Outline: Incorrect Parameter Value "point"
    Given I have route point as
      | pointA           | pointB              |
      | 50.729961,string | 50.723364,-3.523895 |
    And I have vehicle as "<vehicleType>"
    And I have weighting as "<routeType>"
    And I have type as "<responseFormat>"
    When I request for a route
    Then I should be able to verify the response message as "<errorMessage>"
    Then I should be able to verify the statuscode as "<statusCode>"

    Examples: 
      | vehicleType | avoidances | routeType | responseFormat | errorMessage                                                                                                 | statusCode |
      | foot        |            | fastest   | json           | Point 50.729961,string is not a valid point. Point must be a comma separated coordinate in WGS84 projection. | 400        |

  @ErrorMessages
  Scenario Outline: Incorrect Parameter Value "point"
    Given I have route point as
      | pointA              |
      | 50.723364,-3.523895 |
    And I have vehicle as "<vehicleType>"
    And I have weighting as "<routeType>"
    And I have type as "<responseFormat>"
    When I request for a route
    Then I should be able to verify the response message as "<errorMessage>"
    Then I should be able to verify the statuscode as "<statusCode>"

    Examples: 
      | vehicleType | avoidances | routeType | responseFormat | errorMessage                                     | statusCode |
      | foot        |            | fastest   | json           | At least 2 points has to be specified, but was:1 | 400        |

  # Parameter :  Invalid Parameter Name
  @ErrorMessages
  Scenario Outline: Incorrect Parameter Name "points"
    Given I have route point as
      | pointA              | pointB              |
      | 50.729961,-3.524853 | 50.723364,-3.523895 |
    And I have route points as
      | pointA              | pointB              |
      | 50.729961,-3.524853 | 50.723364,-3.523895 |
    And I have vehicle as "<vehicleType>"
    And I have weighting as "<routeType>"
    And I have type as "<responseFormat>"
    When I request for a route
    Then I should be able to verify the response message as "<errorMessage>"
    Then I should be able to verify the statuscode as "<statusCode>"

    Examples: 
      | vehicleType | avoidances | routeType | responseFormat | errorMessage                                                                                                                                                                                                                                                  | statusCode |
      | foot        |            | fastest   | json           | Parameter points is not a valid parameter for resource route. Valid parameters for requested resource are point, vehicle, locale, instructions, weighting, algorithm, points_encoded, debug, pretty, calc_points, type, avoidances, private, srs, output_srs. | 400        |

  # Parameter :  point
  @ErrorMessages
  Scenario Outline: Missing Parameter "point"
    Given I have vehicle as "<vehicleType>"
    And I have weighting as "<routeType>"
    And I have type as "<responseFormat>"
    When I request for a route
    Then I should be able to verify the response message as "<errorMessage>"
    Then I should be able to verify the statuscode as "<statusCode>"

    Examples: 
      | vehicleType | avoidances | routeType | responseFormat | errorMessage                 | statusCode |
      | foot        |            | fastest   | json           | No point parameter provided. | 400        |

  # Parameter :  vehicle
  @ErrorMessages @Smoke
  Scenario Outline: Missing Parameter "vehicle"
    Given I have route point as
      | pointA              | pointB              |
      | 53.298525,-1.679533 | 53.203145,-1.799292 |
    And I have avoidances as "<avoidances>"
    And I have weighting as "<routeType>"
    And I have type as "<responseFormat>"
    When I request for a route
    Then I should be able to verify the response message as "<errorMessage>"
    Then I should be able to verify the statuscode as "<statusCode>"

    Examples: 
      | vehicleType | avoidances | routeType | responseFormat | errorMessage                   | statusCode |
      | foot        |            | fastest   | json           | No vehicle parameter provided. | 400        |

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
      | vehicleType | avoidances | routeType | responseFormat | errorMessage                                                                             | statusCode |
      | foot        |            | quick     | json           | quick is not a valid value for parameter weighting. Valid values are fastest or shortest | 400        |

  # Parameter :  locale
  @ErrorMessages
  Scenario Outline: Invalid Parameter Value for "locale"
    Given I have route point as
      | pointA              | pointB              |
      | 50.729961,-3.524853 | 50.723364,-3.523895 |
    And I have vehicle as "<vehicleType>"
    And I have locale as "<locale>"
    And I have weighting as "<routeType>"
    And I have type as "<responseFormat>"
    When I request for a route
    Then I should be able to verify the response message as "<errorMessage>"
    Then I should be able to verify the statuscode as "<statusCode>"

    Examples: 
      | vehicleType | avoidances | routeType | locale | responseFormat | errorMessage                                                                                                                                                                                                               | statusCode |
      | mtb         |            | fastest   | en     | json           | en is not a valid value for parameter locale. Valid values are bg, ca, cz, de_DE, el, en_US, es, fa, fil, fi, fr, gl, he, hu_HU, it, ja, ne, nl, pl_PL, pt_BR, pt_PT, ro, ru, si, sk, sv_SE, tr, uk, vi_VI, zh_CN or en_GB | 400        |

  # Parameter :  instructions
  @ErrorMessages
  Scenario Outline: Invalid Parameter Value for "instructions"
    Given I have route point as
      | pointA              | pointB              |
      | 50.729961,-3.524853 | 50.723364,-3.523895 |
    And I have vehicle as "<vehicleType>"
    And I have instructions as "<instructions>"
    And I have weighting as "<routeType>"
    And I have type as "<responseFormat>"
    When I request for a route
    Then I should be able to verify the response message as "<errorMessage>"
    Then I should be able to verify the statuscode as "<statusCode>"

    Examples: 
      | vehicleType | avoidances | routeType | instructions | responseFormat | errorMessage                                                                             | statusCode |
      | mtb         |            | fastest   | msg(box)     | json           | msg(box) is not a valid value for parameter instructions. Valid values are true or false | 400        |

  # Parameter :  algorithm
  @ErrorMessages
  Scenario Outline: Invalid Parameter Value for "algorithm"
    Given I have route point as
      | pointA              | pointB              |
      | 50.729961,-3.524853 | 50.723364,-3.523895 |
    And I have vehicle as "<vehicleType>"
    And I have algorithm as "<algorithm>"
    And I have weighting as "<routeType>"
    And I have type as "<responseFormat>"
    When I request for a route
    Then I should be able to verify the response message as "<errorMessage>"
    Then I should be able to verify the statuscode as "<statusCode>"

    Examples: 
      | vehicleType | avoidances | routeType | algorithm | responseFormat | errorMessage                                                                                                                     | statusCode |
      | foot        |            | fastest   | xyz       | json           | xyz is not a valid value for parameter algorithm. Valid values are astar, astarbi, dijkstra, dijkstrabi or dijkstraOneToMany     | 400        |
      | mtb         |            | fastest   | select*   | json           | select* is not a valid value for parameter algorithm. Valid values are astar, astarbi, dijkstra, dijkstrabi or dijkstraOneToMany | 400        |

  # Parameter :  points_encoded
  @ErrorMessages
  Scenario Outline: Invalid Parameter Value for "points_encoded"
    Given I have route point as
      | pointA              | pointB              |
      | 50.729961,-3.524853 | 50.723364,-3.523895 |
    And I have vehicle as "<vehicleType>"
    And I have points_encoded as "<points_encoded>"
    And I have weighting as "<routeType>"
    And I have type as "<responseFormat>"
    When I request for a route
    Then I should be able to verify the response message as "<errorMessage>"
    Then I should be able to verify the statuscode as "<statusCode>"

    Examples: 
      | vehicleType | avoidances | routeType | points_encoded | responseFormat | errorMessage                                                                          | statusCode |
      | foot        |            | fastest   | xyz            | json           | xyz is not a valid value for parameter points_encoded. Valid values are true or false | 400        |

  # Parameter :  debug
  @ErrorMessages
  Scenario Outline: Invalid Parameter Value for "debug"
    Given I have route point as
      | pointA              | pointB              |
      | 50.729961,-3.524853 | 50.723364,-3.523895 |
    And I have vehicle as "<vehicleType>"
    And I have debug as "<debug>"
    And I have weighting as "<routeType>"
    And I have type as "<responseFormat>"
    When I request for a route
    Then I should be able to verify the response message as "<errorMessage>"
    Then I should be able to verify the statuscode as "<statusCode>"

    Examples: 
      | vehicleType | avoidances | routeType | debug | responseFormat | errorMessage                                                                 | statusCode |
      | foot        |            | fastest   | xyz   | json           | xyz is not a valid value for parameter debug. Valid values are true or false | 400        |

  # Parameter :  calc_points
  @ErrorMessages
  Scenario Outline: Invalid Parameter Value for "calc_points"
    Given I have route point as
      | pointA              | pointB              |
      | 50.729961,-3.524853 | 50.723364,-3.523895 |
    And I have vehicle as "<vehicleType>"
    And I have calc_points as "<calc_points>"
    And I have weighting as "<routeType>"
    And I have type as "<responseFormat>"
    When I request for a route
    Then I should be able to verify the response message as "<errorMessage>"
    Then I should be able to verify the statuscode as "<statusCode>"

    Examples: 
      | vehicleType | avoidances | routeType | calc_points | responseFormat | errorMessage                                                                       | statusCode |
      | mtb         |            | fastest   | xyz         | json           | xyz is not a valid value for parameter calc_points. Valid values are true or false | 400        |

  @ErrorMessages
  Scenario Outline: Invalid Parameter Value for "SRS "
    Given I have route point as
      | pointA              | pointB              |
      | 50.729961,-3.524853 | 50.723364,-3.523895 |
    And I have vehicle as "foot"
    And I have weighting as "fastest"
    And I have locale as "en_US"
    And I have debug as "true"
    And I have points_encoded as "true"
    And I have calc_points as "true"
    And I have instructions as "true"
    And I have algorithm as "astar"
    And I have type as "json"
    And I have srs as "<SRS>"
    When I request for a route
    Then I should be able to verify the response message as "<errorMessage>"
    Then I should be able to verify the statuscode as "<statusCode>"

    Examples: 
      | SRS | errorMessage                          | statusCode |
      | ABC | Srs abc is not a valid srs for input. | 400        |
      | 123 | Srs 123 is not a valid srs for input. | 400        |

  # Parameter :  Type
  @ErrorMessages
  Scenario Outline: Invalid Parameter Value for "type"
    Given I have route point as
      | pointA              | pointB              |
      | 53.410574,-1.825276 | 53.277655,-1.805662 |
    And I have vehicle as "<vehicleType>"
    And I have calc_points as "<calc_points>"
    And I have weighting as "<routeType>"
    And I have type as "<responseFormat>"
    When I request for a route
    Then I should be able to verify the response message as "<errorMessage>"
    Then I should be able to verify the statuscode as "<statusCode>"

    Examples: 
      | vehicleType | avoidances | routeType | calc_points | responseFormat | errorMessage                                                               | statusCode |
      | foot        |            | fastest   | true        | txt            | txt is not a valid value for parameter type. Valid values are GPX or JSON. | 400        |

  @ErrorMessages
  Scenario Outline: Invalid Parameter Value for "output_srs "
    Given I have route point as
      | pointA              | pointB              |
      | 53.410574,-1.825276 | 53.277655,-1.805662 |
    And I have vehicle as "foot"
    And I have weighting as "fastest"
    And I have locale as "en_US"
    And I have debug as "true"
    And I have points_encoded as "true"
    And I have calc_points as "true"
    And I have instructions as "true"
    And I have algorithm as "astar"
    And I have type as "json"
    And I have srs as "WGS84"
    And I have output_srs as "ABC"
    When I request for a route
    Then I should be able to verify the response message as "<errorMessage>"
    Then I should be able to verify the statuscode as "<statusCode>"

    Examples: 
      | errorMessage                                  | statusCode |
      | Srs ABC is not a valid output_srs for output. | 400        |

  @ErrorMessages
  Scenario Outline: Invalid Parameter Value for "private "
    Given I have route point as
      | pointA              | pointB              |
      | 53.410574,-1.825276 | 53.277655,-1.805662 |
    And I have vehicle as "foot"
    And I have weighting as "fastest"
    And I have locale as "en_US"
    And I have debug as "true"
    And I have points_encoded as "true"
    And I have calc_points as "true"
    And I have instructions as "true"
    And I have algorithm as "astar"
    And I have type as "json"
    And I have srs as "WGS84"
    And I have private as "ABC"
    When I request for a route
    Then I should be able to verify the response message as "<errorMessage>"
    Then I should be able to verify the statuscode as "<statusCode>"

    Examples: 
      | errorMessage                                                                   | statusCode |
      | ABC is not a valid value for parameter private. Valid values are true or false | 400        |

  @ErrorMessages
  Scenario Outline: Invalid Parameter Value for "pretty "
    Given I have route point as
      | pointA              | pointB              |
      | 53.410574,-1.825276 | 53.277655,-1.805662 |
    And I have vehicle as "foot"
    And I have weighting as "fastest"
    And I have locale as "en_US"
    And I have debug as "true"
    And I have points_encoded as "true"
    And I have calc_points as "true"
    And I have instructions as "true"
    And I have algorithm as "astar"
    And I have type as "json"
    And I have srs as "WGS84"
    And I have pretty as "ABC"
    When I request for a route
    Then I should be able to verify the response message as "<errorMessage>"
    Then I should be able to verify the statuscode as "<statusCode>"

    Examples: 
      | errorMessage                                                                  | statusCode |
      | ABC is not a valid value for parameter pretty. Valid values are true or false | 400        |

  # http method
  @ErrorMessages
  Scenario Outline: http methods "PUT/POST/DEL/GET"
    Given I have route point as
      | pointA              | pointB              |
      | 53.410574,-1.825276 | 53.277655,-1.805662 |
    Given I have vehicle as "<vehicleType>"
    And I have weighting as "<routeType>"
    And I have type as "<responseFormat>"
    And I request for HTTP "<httpMethod>" method
    When I request for a route
    Then I should be able to verify the http response message as "<httpErrorMessage>"
    Then I should be able to verify the http statuscode as "<statusCode>"

    Examples: 
      | vehicleType | avoidances | routeType | httpMethod | responseFormat | httpErrorMessage   | statusCode |
      | foot        |            | fastest   | PUT        | json           | Method Not Allowed | 405        |
      | mtb         |            | fastest   | DEL        | json           | Method Not Allowed | 405        |
      | foot        |            | fastest   | POST       | json           | Method Not Allowed | 405        |
      | mtb         |            | fastest   | GET        | json           | OK                 | 200        |
      | foot        |            | fastest   | OPTIONS    | json           | OK                 | 200        |

  @Routing
  Scenario Outline: Invalid Parameter Value for "point in BNG"
    Given I have route point as
      | pointA              | pointB              |
      | 51.206305,-3.683483 | 51.195761,-3.848208 |
    And I have vehicle as "foot"
    And I have srs as "BNG"
    And I have output_srs as "BNG"
    When I request for a route
    Then I should be able to verify the response message as "<errorMessage>"
    Then I should be able to verify the statuscode as "<statusCode>"

    Examples: 
      | errorMessage                             | statusCode |
      | Cannot find point 0: 51.206305,-3.683483 | 400        |

  @Routing
  Scenario Outline: Invalid Parameter Value for "point in WGS84"
    Given I have route point as
      | pointA        | pointB        |
      | 146580,282492 | 145684,270956 |
    And I have vehicle as "mtb"
    And I have srs as "WGS84"
    And I have output_srs as "WGS84"
    When I request for a route
    Then I should be able to verify the response message as "<errorMessage>"
    Then I should be able to verify the statuscode as "<statusCode>"

    Examples: 
      | errorMessage                       | statusCode |
      | Cannot find point 0: 146580,282492 | 400        |

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
      | pointA              | errorMessage                 | responseFormat | statusCode |
      | 51.878966,-0.903849 | No point parameter provided. | json           | 400        |

  #Successful request
  @ErrorMessages
  Scenario Outline: verify valid parameter values for "locale"
    Given I have route point as
      | pointA              | pointB              |
      | 53.410574,-1.825276 | 53.277655,-1.805662 |
    And I have vehicle as "foot"
    And I have weighting as "fastest"
    And I have locale as "<locale>"
    And I have debug as "true"
    And I have points_encoded as "true"
    And I have calc_points as "true"
    And I have instructions as "true"
    And I have algorithm as "astar"
    And I have type as "json"
    When I request for a route
    Then I should be able to verify the http response message as "OK"
    And I should be able to verify the http statuscode as "200"

    Examples: 
      | locale |
      | en_GB  |
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

  # Parameter :  weighting
  @ErrorMessages @Smoke
  Scenario Outline: Valid Parameter Values for "weighting"
    Given I have route point as
      | pointA              | pointB              |
      | 53.410574,-1.825276 | 53.277655,-1.805662 |
    And I have vehicle as "foot"
    And I have weighting as "<weighting>"
    And I have locale as "en_US"
    And I have debug as "true"
    And I have points_encoded as "true"
    And I have calc_points as "true"
    And I have instructions as "true"
    And I have algorithm as "astar"
    And I have type as "json"
    When I request for a route
    Then I should be able to verify the http response message as "OK"
    Then I should be able to verify the http statuscode as "200"

    Examples: 
      | weighting |
      | fastest   |
      | shortest  |

  @ErrorMessages
  Scenario Outline: verify valid parameter values for "algorithm "
    Given I have route point as
      | pointA              | pointB              |
      | 53.410574,-1.825276 | 53.277655,-1.805662 |
    And I have vehicle as "foot"
    And I have weighting as "fastest"
    And I have locale as "en_US"
    And I have debug as "true"
    And I have points_encoded as "true"
    And I have calc_points as "true"
    And I have instructions as "true"
    And I have algorithm as "<algorithm>"
    And I have type as "json"
    When I request for a route
    Then I should be able to verify the http response message as "OK"
    Then I should be able to verify the http statuscode as "200"

    Examples: 
      | algorithm         |
      | dijkstra          |
      | astar             |
      | astarbi           |
      | dijkstrabi        |
      | dijkstraOneToMany |

  @ErrorMessages
  Scenario Outline: verify valid parameter values for "debug "
    Given I have route point as
      | pointA              | pointB              |
      | 53.410574,-1.825276 | 53.277655,-1.805662 |
    And I have vehicle as "foot"
    And I have weighting as "fastest"
    And I have locale as "en_US"
    And I have debug as "<debug>"
    And I have points_encoded as "true"
    And I have calc_points as "true"
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
      | 53.410574,-1.825276 | 53.277655,-1.805662 |
    And I have vehicle as "foot"
    And I have weighting as "fastest"
    And I have locale as "en_US"
    And I have debug as "true"
    And I have points_encoded as "true"
    And I have calc_points as "<points_calc>"
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
      | 53.410574,-1.825276 | 53.277655,-1.805662 |
    And I have vehicle as "foot"
    And I have weighting as "fastest"
    And I have locale as "en_US"
    And I have debug as "true"
    And I have points_encoded as "<points_encoded>"
    And I have calc_points as "true"
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
      | 53.410574,-1.825276 | 53.277655,-1.805662 |
    And I have vehicle as "foot"
    And I have weighting as "fastest"
    And I have locale as "en_US"
    And I have debug as "true"
    And I have points_encoded as "true"
    And I have calc_points as "true"
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
      | 53.410574,-1.825276 | 53.277655,-1.805662 |
    And I have vehicle as "foot"
    And I have weighting as "fastest"
    And I have locale as "en_US"
    And I have debug as "true"
    And I have points_encoded as "true"
    And I have calc_points as "true"
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

  @ErrorMessages
  Scenario Outline: verify valid parameter values for "SRS "
    Given I have route point as
      | pointA              | pointB              |
      | 53.410574,-1.825276 | 53.277655,-1.805662 |
    And I have vehicle as "foot"
    And I have weighting as "fastest"
    And I have locale as "en_US"
    And I have debug as "true"
    And I have points_encoded as "true"
    And I have calc_points as "true"
    And I have instructions as "true"
    And I have algorithm as "astar"
    And I have type as "json"
    And I have srs as "<SRS>"
    When I request for a route
    Then I should be able to verify the http response message as "OK"
    Then I should be able to verify the http statuscode as "200"

    Examples: 
      | SRS   |
      | WGS84 |
      | BNG   |

  @ErrorMessages
  Scenario Outline: verify valid parameter values for "output_srs "
    Given I have route point as
      | pointA              | pointB              |
      | 53.410574,-1.825276 | 53.277655,-1.805662 |
    And I have vehicle as "foot"
    And I have weighting as "fastest"
    And I have locale as "en_US"
    And I have debug as "true"
    And I have points_encoded as "true"
    And I have calc_points as "true"
    And I have instructions as "true"
    And I have algorithm as "astar"
    And I have type as "json"
    And I have srs as "WGS84"
    And I have output_srs as "<output_srs>"
    When I request for a route
    Then I should be able to verify the http response message as "OK"
    Then I should be able to verify the http statuscode as "200"

    Examples: 
      | output_srs |
      | WGS84      |
      | BNG        |

  @ErrorMessages
  Scenario Outline: verify valid parameter values for "private "
    Given I have route point as
      | pointA              | pointB              |
      | 53.410574,-1.825276 | 53.277655,-1.805662 |
    And I have vehicle as "foot"
    And I have weighting as "fastest"
    And I have locale as "en_US"
    And I have debug as "true"
    And I have points_encoded as "true"
    And I have calc_points as "true"
    And I have instructions as "true"
    And I have algorithm as "astar"
    And I have type as "json"
    And I have srs as "WGS84"
    And I have private as "<private>"
    When I request for a route
    Then I should be able to verify the http response message as "OK"
    Then I should be able to verify the http statuscode as "200"

    Examples: 
      | private |
      | true    |
      | false   |

  @ErrorMessages
  Scenario Outline: verify valid parameter values for "pretty "
    Given I have route point as
      | pointA              | pointB              |
      | 53.410574,-1.825276 | 53.277655,-1.805662 |
    And I have vehicle as "foot"
    And I have weighting as "fastest"
    And I have locale as "en_US"
    And I have debug as "true"
    And I have points_encoded as "true"
    And I have calc_points as "true"
    And I have instructions as "true"
    And I have algorithm as "astar"
    And I have type as "json"
    And I have srs as "WGS84"
    And I have pretty as "<pretty>"
    When I request for a route
    Then I should be able to verify the http response message as "OK"
    Then I should be able to verify the http statuscode as "200"

    Examples: 
      | pretty |
      | true   |
      | false  |

  @ErrorMessages
  Scenario Outline: with invalid uri and verify resource not available
    Given I have route point as
      | pointA              | pointB              |
      | 53.410574,-1.825276 | 53.277655,-1.805662 |
    And I have vehicle as "foot"
    And I have weighting as "fastest"
    And I have locale as "en_US"
    And I have debug as "true"
    And I have points_encoded as "true"
    And I have calc_points as "true"
    And I have instructions as "true"
    And I have algorithm as "astar"
    And I have type as "json"
    And I prefix the string "<prefixString>" and append the string "<appendString>" to service URL
    When I request for a route
    Then I should be able to verify the http response message as "Not Found"
    Then I should be able to verify the http statuscode as "404"

    Examples: 
      | prefixString | appendString |
      | test/        |              |
      |              | 1234/        |
      |              | /1234/       |
