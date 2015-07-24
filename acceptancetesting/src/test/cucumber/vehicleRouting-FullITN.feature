Feature: Verify a route from A to B for Vehicle (Full ITN)
   As a user
   I want to get a route from location A to location B using the routing service
   And route should be the fastest route and contain the waypoints,restrictions,time and other instructions

  @Routing
  Scenario Outline: Verify  waypoints on a Route
    Given I have route point as
      | pointA                                 | pointB                                 |
      | 51.471546541834144,-0.3618621826171875 | 51.45914115860512,-0.96679687499999995 |
    And I have vehicle as "<vehicleType>"
    And I have weighting as "<routeType>"
    When I request for a route
    Then I should be able to verify the waypoints on the route map:
      | wayPointIndex | waypointco          | waypointdesc                 | azimuth | direction | time | distance | avoidance |
      | 1             | 51.472114,-0.361993 | Continue onto ELLINGTON ROAD | 277.0   | W         | 9870 | 131.6    |           |

    Examples: 
      | vehicleType | avoidances | routeType |
      | car         |            | fastest   |

  @Routing
  Scenario Outline: Verify  waypoints on a Route from Southampton to Glasgow
    Given I have route point as
      | pointA              | pointB             |
      | 50.896617,-1.400465 | 55.861284,-4.24996 |
    And I have vehicle as "<vehicleType>"
    And I have weighting as "<routeType>"
    When I request for a route
    Then I should be able to verify the waypoints on the route map:
      | wayPointIndex | waypointco          | waypointdesc                                         | azimuth | direction | time    | distance | avoidance |
      | 1             | 50.896712,-1.400486 | Continue onto PLATFORM ROAD (A33)                    | 262.0   | W         | 2458    | 73.1     |           |
      | 10            | 50.90789,-1.398144  | Turn slight right onto ST ANDREWS ROAD (A33)         | 314.0   | NW        | 10644   | 316.5    |           |
      | 20            | 51.868385,-1.199845 | At roundabout, take exit 1 onto M40                  | 357.0   | N         | 2464575 | 73242.2  |           |
      | 30            | 52.405293,-1.823124 | At roundabout, take exit 2 onto A34 (STRATFORD ROAD) | 290.0   | W         | 52096   | 1199.4   |           |

    Examples: 
      | vehicleType | avoidances | routeType |
      | car         |            | fastest   |

  @Routing
  Scenario Outline: Verify  waypoints on a Route from London to Birmingham
    Given I have route point as
      | pointA              | pointB              |
      | 51.507229,-0.127581 | 52.481875,-1.898743 |
    And I have vehicle as "<vehicleType>"
    And I have weighting as "<routeType>"
    When I request for a route
    Then I should be able to verify the waypoints on the route map:
      | wayPointIndex | waypointco          | waypointdesc                                       | azimuth | direction | time  | distance | avoidance |
      | 1             | 51.507234,-0.127584 | At roundabout, take exit 2 onto CHARING CROSS (A4) | 253.0   | W         | 4623  | 111.0    |           |
      | 7             | 51.517207,-0.142804 | Turn slight left onto A4201                        | 307.0   | NW        | 2379  | 43.0     |           |
      | 21            | 51.577774,-0.220823 | Continue onto A41 (HENDON WAY)                     | 301.0   | NW        | 25165 | 748.0    |           |
      | 22            | 51.582726,-0.227154 | Continue onto A41 (WATFORD WAY)                    | 340.0   | N         | 78460 | 2332.3   |           |
      | 23            | 51.601209,-0.234509 | Continue onto A1 (WATFORD WAY (BARNET BY-PASS))    | 325.0   | NW        | 64289 | 1911.0   |           |

    Examples: 
      | vehicleType | avoidances | routeType |
      | car         |            | fastest   |

  @Routing
  Scenario Outline: Verify  waypoints on a Route from London to Birmingham and the total route time estimate
    Given I have route point as
      | pointA              | pointB              |
      | 51.507229,-0.127581 | 52.481875,-1.898743 |
    And I have vehicle as "<vehicleType>"
    And I have weighting as "<routeType>"
    When I request for a route
    Then The total route time should be not more than "<totalRouteTime>"

    Examples: 
      | vehicleType | avoidances | routeType | totalRouteTime |
      | car         |            | fastest   | 2h 37min       |

  @Routing
  Scenario Outline: Verify  waypoints on a Route from Hounslow to Burnham and the total route time estimate
    Given I have route point as
      | pointA             | pointB              |
      | 51.475161,-0.39591 | 51.536292,-0.656802 |
    And I have vehicle as "<vehicleType>"
    And I have weighting as "<routeType>"
    When I request for a route
    Then The total route time should be not more than "<totalRouteTime>"

    Examples: 
      | vehicleType | avoidances | routeType | totalRouteTime |
      | car         |            | fastest   | 0h30min        |

  @ServiceOnly
  Scenario Outline: Verify  oneway Restrictions on a Route (Burmingham Route with one way restriction-WSPIP-74)
    Given I have route point as
      | pointA              | pointB              |
      | 52.446823,-1.929077 | 52.446604,-1.930043 |
    And I have vehicle as "<vehicleType>"
    And I have weighting as "<routeType>"
    When I request for a route
    Then I should be able to verify the trackPoints not on the route map:
      | trackPointco        | time                      |
      | 52.446899,-1.929721 | 2014-10-31T19:17:22+00:00 |

    Examples: 
      | vehicleType | avoidances | routeType |
      | car         |            | fastest   |

  @KnownIssues @ServiceOnly
  Scenario Outline: Verify  No Turn Restrictions  on a Route (Birmingham WSPIP-77)
    Given I have route point as
      | pointA              | pointB              |
      | 52.446564,-1.930268 | 52.446744,-1.929469 |
    And I have vehicle as "<vehicleType>"
    And I have weighting as "<routeType>"
    When I request for a route
    Then I should be able to verify the trackPoints not on the route map:
      | trackPointco        |
      | 52.446779,-1.929385 |

    Examples: 
      | vehicleType | avoidances | routeType |
      | car         |            | fastest   |

  @KnownIssues @ServiceOnly
  Scenario Outline: Verify  No Turn Restrictions  on a Route (Birmingham Bristol Road WSPIP-83)
    Given I have route point as
      | pointA              | pointB              |
      | 52.446823,-1.929077 | 52.446672,-1.929691 |
    And I have vehicle as "<vehicleType>"
    And I have weighting as "<routeType>"
    When I request for a route
    Then I should be able to verify the trackPoints not on the route map:
      | trackPointco        |
      | 52.446764,-1.929391 |

    Examples: 
      | vehicleType | avoidances | routeType |
      | car         |            | fastest   |

  @ServiceOnly
  Scenario Outline: Verify  under pass still finds route  from top road (Southampton- Charle WattsWay)
    Given I have route point as
      | pointA              | pointB              |
      | 50.917598,-1.317992 | 50.919748,-1.310342 |
    And I have vehicle as "<vehicleType>"
    And I have weighting as "<routeType>"
    When I request for a route
    Then I should be able to verify the trackPoints on the route map:
      | trackPointco        |
      | 50.917268,-1.316368 |

    Examples: 
      | vehicleType | avoidances | routeType |
      | car         |            | fastest   |

  @ServiceOnly
  Scenario Outline: Verify  under pass still finds route from bottom road  (Southampton- Charle WattsWay)
    Given I have route point as
      | pointA             | pointB             |
      | 50.91525,-1.318761 | 50.92045,-1.316021 |
    And I have vehicle as "<vehicleType>"
    And I have weighting as "<routeType>"
    When I request for a route
    Then I should be able to verify the trackPoints on the route map:
      | trackPointco        |
      | 50.919194,-1.316553 |

    Examples: 
      | vehicleType | avoidances | routeType |
      | car         |            | fastest   |

  @Routing
  Scenario Outline: Verify  No Turn   (WSPIP-76:Eastley- TWYFORD ROAD )
    Given I have route point as
      | pointA              | pointB              |
      | 50.972281,-1.350942 | 50.972212,-1.351183 |
    And I have vehicle as "<vehicleType>"
    And I have weighting as "<routeType>"
    When I request for a route
    Then I should be able to verify the waypoints on the route map:
      | wayPointIndex | waypointco          | waypointdesc                | azimuth | direction | time | distance | avoidance |
      | 3             | 50.971952,-1.350891 | Turn left onto THE CRESCENT | 294.0   | NW        | 2795 | 37.3     |           |

    Examples: 
      | vehicleType | avoidances | routeType |
      | car         |            | fastest   |

  @Routing
  Scenario Outline: Verify  No Turn   (WSPIP-76:Eastley- Station Hill Road)
    Given I have route point as
      | pointA              | pointB             |
      | 50.970024,-1.350267 | 50.97008,-1.350521 |
    And I have vehicle as "<vehicleType>"
    And I have weighting as "<routeType>"
    When I request for a route
    Then I should be able to verify the waypoints on the route map:
      | wayPointIndex | waypointco          | waypointdesc                      | azimuth | direction | time | distance | avoidance |
      | 2             | 50.969817,-1.350504 | Continue onto STATION HILL (A335) | 180.0   | S         | 4297 | 57.3     |           |

    Examples: 
      | vehicleType | avoidances | routeType |
      | car         |            | fastest   |

  @Routing
  Scenario Outline: Verify  No Turn   (Treaty Center-Hounslow- Fairfields Road)
    Given I have route point as
      | pointA             | pointB              |
      | 51.46882,-0.358687 | 51.469454,-0.357831 |
    And I have vehicle as "<vehicleType>"
    And I have weighting as "<routeType>"
    When I request for a route
    Then I should be able to verify the waypoints on the route map:
      | wayPointIndex | waypointco          | waypointdesc                        | azimuth | direction | time  | distance | avoidance |
      | 2             | 51.468925,-0.359049 | Turn left onto HANWORTH ROAD (A315) | 239.0   | SW        | 16521 | 224.0    |           |

    Examples: 
      | vehicleType | avoidances | routeType |
      | car         |            | fastest   |

  @KnownIssues @ServiceOnly
  Scenario Outline: Verify  No Turns with Exceptions(Vehicle Type:Bus)   (High Street-Hounslow)
    Given I have route point as
      | pointA              | pointB              |
      | 51.470198,-0.356036 | 51.470352,-0.357388 |
    And I have vehicle as "<vehicleType>"
    And I have weighting as "<routeType>"
    When I request for a route
    Then I should be able to verify the waypoints on the route map:
      | trackPointco        |
      | 51.469998,-0.357031 |

    Examples: 
      | vehicleType | avoidances | routeType |
      | car         |            | fastest   |

  @Routing
  Scenario Outline: Verify  Mandatory Turn   (Alexandra Road-Hounslow- Fairfields Road)
    Given I have route point as
      | pointA             | pointB              |
      | 51.47118,-0.363609 | 51.470254,-0.363412 |
    And I have vehicle as "<vehicleType>"
    And I have weighting as "<routeType>"
    When I request for a route
    Then I should be able to verify the waypoints on the route map:
      | wayPointIndex | waypointco          | waypointdesc                   | azimuth | direction | time | distance | avoidance |
      | 2             | 51.470846,-0.363527 | Turn right onto LANSDOWNE ROAD | 259.0   | W         | 9313 | 124.2    |           |

    Examples: 
      | vehicleType | avoidances | routeType |
      | car         |            | fastest   |

  @ServiceOnly
  Scenario Outline: Verify  Private Road Restricted Access (Warwick Road-Carlisle)
    Given I have route point as
      | pointA              | pointB            |
      | 54.894427,-2.921111 | 54.8922,-2.928296 |
    And I have vehicle as "<vehicleType>"
    And I have weighting as "<routeType>"
    When I request for a route
    Then I should be able to verify the trackPoints not on the route map:
      | trackPointco        |
      | 54.894721,-2.921665 |

    Examples: 
      | vehicleType | avoidances | routeType |
      | car         |            | fastest   |

  @Routing
  Scenario Outline: Verify  Ford Gate at CRAMPOOR ROAD(ROMSEY-Southampton)
    Given I have route point as
      | pointA              | pointB              |
      | 50.995817,-1.454224 | 50.998501,-1.454504 |
    And I have vehicle as "<vehicleType>"
    And I have weighting as "<routeType>"
    When I request for a route
    Then I should be able to verify the waypoints on the route map:
      | wayPointIndex | waypointco          | waypointdesc                         | azimuth | direction | time  | distance | avoidance |
      | 2             | 50.993815,-1.461397 | Turn slight right onto HIGHWOOD LANE | 349.0   | N         | 38023 | 520.5    |           |

    Examples: 
      | vehicleType | avoidances | routeType |
      | car         |            | fastest   |

  @Routing
  Scenario Outline: Verify  Ford Gate at CRAMPOOR ROAD(ROMSEY-Southampton)
    Given I have route point as
      | pointA             | pointB              |
      | 50.78222,-1.059975 | 50.779123,-1.080019 |
    And I have vehicle as "<vehicleType>"
    And I have weighting as "<routeType>"
    When I request for a route
    Then I should be able to verify the waypoints on the route map:
      | wayPointIndex | waypointco          | waypointdesc                               | azimuth | direction | time  | distance | avoidance |
      | 3             | 50.782654,-1.060556 | Turn sharp left onto A288 (EASTERN PARADE) | 248.0   | W         | 53421 | 712.3    |           |

    Examples: 
      | vehicleType | avoidances | routeType |
      | car         |            | fastest   |

  @WebOnly
  Scenario Outline: Verify  Route using Full UK Address (Southampton to London)
    Given I have route point as
      | pointA                                                            | pointB                                 |
      | ORDNANCE SURVEY, 4, ADANAC DRIVE, NURSLING, SOUTHAMPTON, SO16 0AS | 1, PICCADILLY ARCADE, LONDON, SW1Y 6NH |
    And I have vehicle as "<vehicleType>"
    And I have weighting as "<routeType>"
    When I request for a route
    Then I should be able to verify the waypoints on the route map:
      | wayPointIndex | waypointdesc                                  |
      | 3             | At roundabout, take exit 2 onto BROWNHILL WAY |
      | 18            | Continue onto PICCADILLY (A4)                 |

    Examples: 
      | vehicleType | avoidances | routeType |
      | car         |            | fastest   |

  @WebOnly
  Scenario Outline: Verify  Route using Full UK Address (Hounslow to Slough)
    Given I have route point as
      | pointA                              | pointB                                      |
      | 131, TIVOLI ROAD, HOUNSLOW, TW4 6AS | 40, CHILTERN ROAD, BURNHAM, SLOUGH, SL1 7NH |
    And I have vehicle as "<vehicleType>"
    And I have weighting as "<routeType>"
    When I request for a route
    Then I should be able to verify the waypoints on the route map:
      | wayPointIndex | waypointdesc                                   |
      | 9             | At roundabout, take exit 1 onto BATH ROAD (A4) |
      | 10            | Turn right onto HUNTERCOMBE LANE NORTH         |

    Examples: 
      | vehicleType | avoidances | routeType |
      | car         |            | fastest   |

  @WebOnly
  Scenario Outline: Verify  Route using Full UK Address (Southampton to London)
    Given I have route point as
      | pointA                                                     | pointB                                              |
      | SOUTHAMPTON MEGABOWL, AUCKLAND ROAD, SOUTHAMPTON, SO15 0SD | CANARY WHARF LTD, 1, CANADA SQUARE, LONDON, E14 5AB |
    And I have vehicle as "<vehicleType>"
    And I have weighting as "<routeType>"
    When I request for a route
    Then I should be able to verify the waypoints on the route map:
      | wayPointIndex | waypointdesc                            |
      | 2             | Turn sharp left onto A35 (TEBOURBA WAY) |
      | 20            | Turn slight left onto PALL MALL (A4)    |

    Examples: 
      | vehicleType | avoidances | routeType |
      | car         |            | fastest   |

  @WebOnly
  Scenario Outline: Verify  Route using Full UK Address (Birmingham to reading)
    Given I have route point as
      | pointA                                                      | pointB                                                                                |
      | BIRMINGHAM VOLKSWAGEN, LAWLEY MIDDLEWAY, BIRMINGHAM, B4 7XH | READING ENTERPRISE CENTRE, UNIVERSITY OF READING, WHITEKNIGHTS ROAD, READING, RG6 6BU |
    And I have vehicle as "<vehicleType>"
    And I have weighting as "<routeType>"
    When I request for a route
    Then I should be able to verify the waypoints on the route map:
      | wayPointIndex | waypointdesc                                         |
      | 2             | Turn left onto MONTAGUE STREET                       |
      | 19            | At roundabout, take exit 2 onto A34 (STRATFORD ROAD) |

    Examples: 
      | vehicleType | avoidances | routeType |
      | car         |            | fastest   |

  @WebOnly
  Scenario Outline: Verify  Route using Full UK Address (Southhampton to London)
    Given I have route point as
      | pointA                                               | pointB                           |
      | 6, CHANNEL WAY, OCEAN VILLAGE, SOUTHAMPTON, SO14 3TG | 311, CITY ROAD, LONDON, EC1V 1LA |
    And I have vehicle as "<vehicleType>"
    And I have weighting as "<routeType>"
    When I request for a route
    Then I should be able to verify the waypoints on the route map:
      | wayPointIndex | waypointdesc                        |
      | 3             | Continue onto ENDLE STREET          |
      | 21            | At roundabout, take exit 3 onto A30 |

    Examples: 
      | vehicleType | avoidances | routeType |
      | car         |            | fastest   |

  @Routing
  Scenario Outline: Verify a Roundabout(Charles Watts Way)
    Given I have route point as
      | pointA             | pointB              |
      | 50.915416,-1.31902 | 50.915551,-1.294049 |
    And I have vehicle as "<vehicleType>"
    And I have weighting as "<routeType>"
    When I request for a route
    Then I should be able to verify the waypoints on the route map:
      | wayPointIndex | waypointco          | waypointdesc                                             | azimuth | direction | time  | distance | avoidance |
      | 3             | 50.920147,-1.310351 | At roundabout, take exit 2 onto CHARLES WATTS WAY (A334) | 0.0     | N         | 16555 | 465.7    |           |

    Examples: 
      | vehicleType | avoidances | routeType |
      | car         |            | fastest   |

  @Routing
  Scenario Outline: Verify a Roundabout(A30)
    Given I have route point as
      | pointA              | pointB             |
      | 50.729071,-3.732732 | 50.72813,-3.730887 |
    And I have vehicle as "<vehicleType>"
    And I have weighting as "<routeType>"
    When I request for a route
    Then I should be able to verify the waypoints on the route map:
      | wayPointIndex | waypointco          | waypointdesc              | azimuth | direction | time  | distance | avoidance |
      | 3             | 50.726474,-3.727558 | Turn slight left onto A30 | 4.0     | N         | 16203 | 308.6    |           |

    Examples: 
      | vehicleType | avoidances | routeType |
      | car         |            | fastest   |

  @Routing
  Scenario Outline: Verify a Roundabout(The City Of Edinburgh By-pass)
    Given I have route point as
      | pointA              | pointB              |
      | 55.913061,-3.060099 | 55.924345,-3.053462 |
    And I have vehicle as "<vehicleType>"
    And I have weighting as "<routeType>"
    When I request for a route
    Then I should be able to verify the waypoints on the route map:
      | wayPointIndex | waypointco          | waypointdesc                         | azimuth | direction | time  | distance | avoidance |
      | 3             | 55.913915,-3.065976 | At roundabout, take exit 1 onto A720 | 199.0   | S         | 53178 | 1299.3   |           |

    Examples: 
      | vehicleType | avoidances | routeType |
      | car         |            | fastest   |

  @Routing
  Scenario Outline: Verify  Route using one intermediate waypoint (Hounslow to Reading via Staines )
    Given I have route point as
      | pointA                                 | pointB              | pointC                                 |
      | 51.471546541834144,-0.3618621826171875 | 51.433882,-0.537904 | 51.45914115860512,-0.96679687499999995 |
    And I have vehicle as "<vehicleType>"
    And I have weighting as "<routeType>"
    When I request for a route
    Then I should be able to verify the waypoints on the route map:
      | wayPointIndex | waypointco          | waypointdesc                         | azimuth | direction | time   | distance | avoidance |
      | 1             | 51.472114,-0.361993 | Continue onto ELLINGTON ROAD         | 286.0   | W         | 9870   | 131.6    |           |
      | 8             | 51.440696,-0.53089  | Continue onto M25                    | 204.0   | SW        | 30319  | 907.6    |           |
      | 13            | 51.355407,-0.679946 | At roundabout, take exit 3 onto A322 | 184.0   | S         | 205943 | 5936.2   |           |

    Examples: 
      | vehicleType | avoidances | routeType |
      | car         |            | fastest   |

  @Routing
  Scenario Outline: Verify  Route using 2 intermediate waypoints (Perth to Edinburgh via Stirling and Glasgow )
    Given I have route point as
      | pointA             | pointB              | pointC              | pointD              |
      | 56.38721,-3.466273 | 56.136656,-3.970408 | 55.871665,-4.195067 | 55.950467,-3.208924 |
    And I have vehicle as "<vehicleType>"
    And I have weighting as "<routeType>"
    When I request for a route
    Then I should be able to verify the waypoints on the route map:
      | wayPointIndex | waypointco          | waypointdesc                                           | azimuth | direction | time   | distance | avoidance |
      | 5             | 56.170837,-3.970499 | At roundabout, take exit 3 onto M9                     | 91.0    | E         | 137478 | 3961.4   |           |
      | 11            | 55.895529,-4.128277 | At roundabout, take exit 2 onto CUMBERNAULD ROAD (A80) | 156.0   | SE        | 203425 | 4479.8   |           |
      | 20            | 55.938772,-3.402452 | At roundabout, take exit 4 onto A8 (GLASGOW ROAD)      | 302.0   | NW        | 260159 | 7046.2   |           |
      | 29            | 55.948911,-3.215644 | Turn right onto MELVILLE STREET                        | 51.0    | NE        | 19570  | 410.6    |           |

    Examples: 
      | vehicleType | avoidances | routeType |
      | car         |            | fastest   |

  @KnownIssues
  Scenario Outline: Verify  nearest point of point using NearestPoint API
    Given I have type as "<responseFormat>"
    And My routing point for nearestPoint API as "<pointA>"
    When I request a nearest point from from Nearest Point API
    Then I should be able to verify the nearest point to be "<pointB>" at a distance of "<distance>"

    Examples: 
      | pointA              | pointB                                 | distance          |
      | 56.170837,-3.970499 | 51.875144098888576,-0.9107481891829116 | 636.3215777261629 |
      | 56.38721,-3.466273  | 53.10043020792586,-1.961414745138117   | 460.0011625834564 |
      | 55.948911,-3.215644 | 53.065293927002806,-1.9071498141906338 | 0                 |

  # Fastest and Shortest Route Scenarios
  @Routing
  Scenario Outline: Verify  waypoints on a Route from Hounslow to Reading
    Given I have route point as
      | pointA                                 | pointB                                 |
      | 51.471546541834144,-0.3618621826171875 | 51.45914115860512,-0.96679687499999995 |
    And I have vehicle as "<vehicleType>"
    And I have weighting as "<routeType>"
    When I request for a route
    Then I should be able to verify the waypoints on the route map:
      | wayPointIndex | waypointco          | waypointdesc                        | azimuth | direction | time   | distance | avoidance |
      | 1             | 51.472114,-0.361993 | Continue onto ELLINGTON ROAD        | 277.0   | W         | 9870   | 131.6    |           |
      | 9             | 51.468674,-0.370905 | Turn right onto BATH ROAD (A3006)   | 278.0   | W         | 125237 | 1767.2   |           |
      | 17            | 51.483218,-0.583061 | Turn right onto WINDSOR ROAD (B470) | 321.0   | NW        | 56306  | 750.7    |           |

    Examples: 
      | vehicleType | avoidances | routeType |
      | car         |            | shortest  |

  @Routing
  Scenario Outline: Verify  waypoints on a Route from Hounslow to Reading
    Given I have route point as
      | pointA                                 | pointB                                 |
      | 51.471546541834144,-0.3618621826171875 | 51.45914115860512,-0.96679687499999995 |
    And I have vehicle as "<vehicleType>"
    And I have weighting as "<routeType>"
    When I request for a route
    Then I should be able to verify the waypoints on the route map:
      | wayPointIndex | waypointco          | waypointdesc                           | azimuth | direction | time   | distance | avoidance |
      | 1             | 51.472114,-0.361993 | Continue onto ELLINGTON ROAD           | 277.0   | W         | 9870   | 131.6    |           |
      | 8             | 51.491316,-0.41082  | At roundabout, take exit 2 onto M4     | 339.0   | N         | 101456 | 2998.5   |           |
      | 16            | 51.453903,-0.961826 | Continue onto WATLINGTON STREET (A329) | 341.0   | N         | 7956   | 132.7    |           |

    Examples: 
      | vehicleType | avoidances | routeType |
      | car         |            | fastest   |

  @Routing
  Scenario Outline: Verify  waypoints on a Route in Isle of Wight
    Given I have route point as
      | pointA             | pointB              |
      | 50.690318,-1.38526 | 50.664175,-1.358463 |
    And I have vehicle as "<vehicleType>"
    And I have weighting as "<routeType>"
    When I request for a route
    Then I should be able to verify the waypoints on the route map:
      | wayPointIndex | waypointco          | waypointdesc                          | azimuth | direction | time   | distance | avoidance |
      | 5             | 50.687826,-1.320457 | Turn right onto BOWCOMBE ROAD (B3323) | 200.0   | S         | 196191 | 3563.3   |           |

    Examples: 
      | vehicleType | avoidances | routeType |
      | car         |            | fastest   |

  @Routing
  Scenario Outline: Verify  waypoints on a Route in Isle of Wight
    Given I have route point as
      | pointA             | pointB              |
      | 50.690318,-1.38526 | 50.664175,-1.358463 |
    And I have vehicle as "<vehicleType>"
    And I have weighting as "<routeType>"
    When I request for a route
    Then I should be able to verify the waypoints on the route map:
      | wayPointIndex | waypointco          | waypointdesc                          | azimuth | direction | time   | distance | avoidance |
      | 5             | 50.687826,-1.320457 | Turn right onto BOWCOMBE ROAD (B3323) | 200.0   | S         | 196191 | 3563.3   |           |

    Examples: 
      | vehicleType | avoidances | routeType |
      | car         |            | shortest  |

  @Routing
  Scenario Outline: Verify  waypoints on a Route from Turbo to Plymouth
    Given I have route point as
      | pointA              | pointB              |
      | 50.270096,-5.052681 | 50.399429,-4.132644 |
    And I have vehicle as "<vehicleType>"
    And I have weighting as "<routeType>"
    When I request for a route
    Then I should be able to verify the waypoints on the route map:
      | wayPointIndex | waypointco          | waypointdesc                  | azimuth | direction | time | distance | avoidance |
      | 10            | 50.413722,-4.328221 | Continue onto A38 (MILL ROAD) | 74.0    | E         | 6638 | 134.8    |           |
      | 19            | 50.398962,-4.133073 | Turn left onto MAITLAND DRIVE | 36.0    | NE        | 4512 | 60.2     |           |

    Examples: 
      | vehicleType | avoidances | routeType |
      | car         |            | fastest   |

  @Routing
  Scenario Outline: Verify  waypoints on a Route from Turbo to Plymouth
    Given I have route point as
      | pointA              | pointB              |
      | 50.270096,-5.052681 | 50.399429,-4.132644 |
    And I have vehicle as "<vehicleType>"
    And I have weighting as "<routeType>"
    When I request for a route
    Then I should be able to verify the waypoints on the route map:
      | wayPointIndex | waypointco          | waypointdesc                         | azimuth | direction | time   | distance | avoidance |
      | 15            | 50.292674,-4.941538 | At roundabout, take exit 1 onto A390 | 87.0    | E         | 675020 | 12232.1  |           |
      | 20            | 50.338479,-4.772896 | Continue onto MOUNT CHARLES ROAD     | 85.0    | E         | 12180  | 163.4    |           |

    Examples: 
      | vehicleType | avoidances | routeType |
      | car         |            | shortest  |

  @Routing
  Scenario Outline: Verify  Route using 2 intermediate waypoints (Route-120 :Perth to Edinburgh via Stirling and Glasgow )
    Given I have route point as
      | pointA             | pointB              | pointC              | pointD              |
      | 56.38721,-3.466273 | 56.136656,-3.970408 | 55.871665,-4.195067 | 55.950467,-3.208924 |
    And I have vehicle as "<vehicleType>"
    And I have weighting as "<routeType>"
    When I request for a route
    Then I should be able to verify the waypoints on the route map:
      | wayPointIndex | waypointco          | waypointdesc                | azimuth | direction | time   | distance | avoidance |
      | 5             | 56.204647,-3.952177 | Turn slight left onto B8033 | 225.0   | SW        | 30648  | 585.4    |           |
      | 22            | 55.871622,-4.198356 | Turn slight right onto M8   | 43.0    | NE        | 259531 | 7738.5   |           |

    Examples: 
      | vehicleType | avoidances | routeType |
      | car         |            | shortest  |

  @Routing
  Scenario Outline: Verify  Route using 2 intermediate waypoints (Route-120 :Perth to Edinburgh via Stirling and Glasgow )
    Given I have route point as
      | pointA             | pointB              | pointC              | pointD              |
      | 56.38721,-3.466273 | 56.136656,-3.970408 | 55.871665,-4.195067 | 55.950467,-3.208924 |
    And I have vehicle as "<vehicleType>"
    And I have weighting as "<routeType>"
    When I request for a route
    Then I should be able to verify the waypoints on the route map:
      | wayPointIndex | waypointco          | waypointdesc                                      | azimuth | direction | time   | distance | avoidance |
      | 5             | 56.170837,-3.970499 | At roundabout, take exit 3 onto M9                | 91.0    | E         | 137478 | 3961.4   |           |
      | 20            | 55.938772,-3.402452 | At roundabout, take exit 4 onto A8 (GLASGOW ROAD) | 302.0   | NW        | 260159 | 7046.2   |           |

    Examples: 
      | vehicleType | avoidances | routeType |
      | car         |            | fastest   |

  @SampleScenario
  Scenario Outline: Verify  Route using 2 intermediate waypoints (Perth to Edinburgh via Stirling and Glasgow )
    Given I have route point as
      | pointA              | pointB              | pointC             | pointD              |
      | 51.746075,-1.263972 | 52.289962,-1.604752 | 52.202814,0.051429 | 51.491412,-0.610276 |
    And I have vehicle as "<vehicleType>"
    And I have weighting as "<routeType>"
    When I request for a route

    Examples: 
      | vehicleType | avoidances | routeType |
      | car         |            | fastest   |

  #[ROUTE-133 ]Routing to a waypoint close to motorway stops on motorway instead of routing through local roads
  @KnownIssues
  Scenario Outline: Verify  waypoints on a Route (ROUTE-133)
    Given I have route point as
      | pointA              | pointB            |
      | 50.949322,-1.483392 | 50.962076,-1.4374 |
    And I have vehicle as "<vehicleType>"
    And I have weighting as "<routeType>"
    When I request for a route
    Then I should be able to verify the waypoints on the route map:
      | wayPointIndex | waypointco          | waypointdesc                     | azimuth | direction | time  | distance | avoidance |
      | 2             | 50.956976,-1.485761 | Turn right onto COLDHARBOUR LANE | 89.0    | E         | 69870 | 970.4    |           |

    Examples: 
      | vehicleType | avoidances | routeType |
      | car         |            | fastest   |
