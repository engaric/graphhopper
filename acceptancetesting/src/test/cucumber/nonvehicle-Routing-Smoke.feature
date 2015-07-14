Feature: Non-Vehicle Routing Smoke Tests
   As a user
   I want to get a route from location A to location B using the routing service
   And route should be the fastest route and contain the waypoints,restrictions,time and other instructions

  #Error Messages
  #Successful request
  @ErrorMessages @Smoke
  Scenario Outline: Successful request with all parameters
    Given I have route <point> as
      | pointA              | pointB             |
      | 53.176062,-1.871472 | 53.154773,-1.77272 |
    And I have <vehicle> as "foot"
    And I have <weighting> as "fastest"
    And I have <locale> as "en_GB"
    And I have <debug> as "true"
    And I have <points_encoded> as "true"
    And I have <calc_points> as "true"
    And I have <instructions> as "true"
    And I have <algorithm> as "astar"
    And I have <avoidances> as "cliff"
    And I have <type> as "json"
    When I request for a route
    Then I should be able to verify the http response message as "OK"
    And I should be able to verify the http statuscode as "200"
    And I receive a valid routing response

    Examples: 
      | point | vehicle | weighting | locale | debug | points_encoded | calc_points | instructions | algorithm | type | avoidances |
      | point | vehicle | weighting | locale | debug | points_encoded | calc_points | instructions | algorithm | type | avoidances |
      | POINT | VEHICLE | WEIGHTING | LOCALE | DEBUG | POINTS_ENCODED | CALC_POINTS | INSTRUCTIONS | ALGORITHM | TYPE | AVOIDANCES |
      | POinT | VEHiCLE | WEIGHtING | LOCaLE | DEbUG | POINTs_ENCODED | CALc_POINTS | INSTRuCTIONS | ALGOrITHM | TYpE | AVoidances |

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
      | vehicleType | avoidances | routeType | errorMessage                                                      | statusCode | httpErrorMessage |
      | 123         | cliff      | fastest   | Vehicle 123 is not a valid vehicle. Valid vehicles are foot,mtb   | 400        | Bad Request      |
      | car         | boulders   | fastest   | Vehicle car is not a valid vehicle. Valid vehicles are foot,mtb   | 400        | Bad Request      |
      | cycle       | mud        | fastest   | Vehicle cycle is not a valid vehicle. Valid vehicles are foot,mtb | 400        | Bad Request      |
      | Bike        | aroad      | fastest   | Vehicle Bike is not a valid vehicle. Valid vehicles are foot,mtb  | 400        | Bad Request      |

  # Parameter :  avoidances
  @ErrorMessages @Smoke
  Scenario Outline: Invalid Parameter Value for "avoidances"
    Given I have route point as
      | pointA              | pointB              |
      | 53.211013,-1.619393 | 53.185757,-1.611969 |
    And I have vehicle as "<vehicleType>"
    And I have avoidances as "<avoidances>"
    And I have weighting as "<routeType>"
    And I have type as "<responseFormat>"
    When I request for a route
    Then I should be able to verify the response message as "<errorMessage>"
    Then I should be able to verify the statuscode as "<statusCode>"

    Examples: 
      | vehicleType | avoidances | routeType | responseFormat | errorMessage                                                                                                                                                                        | statusCode |
      | foot        | trees      | fastest   | json           | trees is not a valid value for parameter avoidances. Valid values are aroad, boulders, cliff, inlandwater, marsh, quarryorpit, scree, rock, mud, sand, shingle, spoil or tidalwater | 400        |

  # Parameter :  avoidances
  @ErrorMessages @Smoke
  Scenario Outline: Valid Parameter Values for "avoidances"
    Given I have route point as
      | pointA              | pointB              |
      | 53.211013,-1.619393 | 53.185757,-1.611969 |
    And I have vehicle as "foot"
    And I have avoidances as "<avoidances>"
    And I have weighting as "fastest"
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
      | avoidances                                                                                                 |
      | aroad, boulders, cliff, inlandwater, marsh, quarryorpit, scree, rock, mud, sand, shingle, spoil,tidalwater |
      | boulders,aroad,  cliff, inlandwater, marsh, quarryorpit, scree, rock, mud, sand, shingle, spoil,tidalwater |
      | marsh, quarryorpit, scree, rock, mud, sand, shingle, spoil,tidalwater                                      |
