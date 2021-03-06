@Vehicle
Feature: Vehicle Routing Smoke Tests
   As a user
   I want to get a route from location A to location B using the routing service
   And route should be the fastest route and contain the waypoints,restrictions,time and other instructions

  #Error Messages
  #Successful request
  @Smoke
  Scenario Outline: Successful request with all parameters
    Given I have route <point> as
      | pointA              | pointB              |
      | 50.729961,-3.524853 | 50.723364,-3.523895 |
    And I have <vehicle> as "car"
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

  # Parameter :  vehicle
  @Smoke
  Scenario Outline: Incorrect Parameter Value for "Vehicle"
    Given I have route point as
      | pointA              | pointB              |
      | 50.729961,-3.524853 | 50.723364,-3.523895 |
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
      | 123         |            | fastest   | Vehicle 123 is not a valid vehicle. Valid vehicles are car,emv   | 400        | Bad Request      |
      | foot        |            | fastest   | Vehicle foot is not a valid vehicle. Valid vehicles are car,emv  | 400        | Bad Request      |
      | cycle       |            | fastest   | Vehicle cycle is not a valid vehicle. Valid vehicles are car,emv | 400        | Bad Request      |
      | Bike        |            | fastest   | Vehicle Bike is not a valid vehicle. Valid vehicles are car,emv  | 400        | Bad Request      |

  # Parameter :  vehicle
  @Smoke
  Scenario Outline: Incorrect Parameter Name "vehicles"
    Given I have route point as
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
      | vehicleType | avoidances | routeType | responseFormat | errorMessage                   | statusCode |
      | car         |            | fastest   | json           | No vehicle parameter provided. | 400        |

  # Parameter :  avoidances
@Smoke
  Scenario Outline: Invalid Parameter Value for "avoidances"
    Given I have route point as
      | pointA              | pointB              |
      | 50.729961,-3.524853 | 50.723364,-3.523895 |
    And I have vehicle as "<vehicleType>"
    And I have weighting as "<routeType>"
    And I have avoidances as "<avoidances>"
    And I have type as "<responseFormat>"
    When I request for a route
    Then I should be able to verify the response message as "<errorMessage>"
    Then I should be able to verify the statuscode as "<statusCode>"

    Examples: 
      | vehicleType | avoidances | routeType | responseFormat | errorMessage                                                          | statusCode |
      | car         | trees      | fastest   | json           | trees is not a valid value for parameter avoidances. Valid values are | 400        |
