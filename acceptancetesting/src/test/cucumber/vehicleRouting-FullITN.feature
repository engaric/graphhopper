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
      | wayPointIndex | waypointco          | waypointdesc                                             | azimuth | direction | time    | distance | avoidance |
      | 1             | 50.896796,-1.400544 | Continue onto PLATFORM ROAD (A33)                        | 254.0   | W         | 3192    | 84.3     |           |
      | 16            | 50.951921,-1.404239 | At roundabout, take exit 1 onto A33                      | 318.0   | NW        | 7083    | 187.0    |           |
      | 17            | 50.953446,-1.403571 | Turn slight right onto M3                                | 28.0    | NE        | 566900  | 15747.6  |           |
      | 18            | 51.07086,-1.292917  | At roundabout, take exit 2 onto A34 (WINCHESTER BY-PASS) | 284.0   | W         | 55129   | 1454.8   |           |
      | 20            | 51.868385,-1.199845 | At roundabout, take exit 1 onto M40                      | 357.0   | N         | 2636747 | 73242.2  |           |
      | 24            | 52.381175,-1.790061 | At roundabout, take exit 1 onto A34 (STRATFORD ROAD)     | 301.0   | NW        | 46514   | 1227.5   |           |

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
      | 1             | 51.507234,-0.127584 | At roundabout, take exit 2 onto CHARING CROSS (A4) | 253.0   | W         | 4202  | 111.0    |           |
      | 7             | 51.517207,-0.142804 | Turn slight left onto A4201                        | 307.0   | NW        | 1628  | 43.0     |           |
      | 21            | 51.577774,-0.220823 | Continue onto A41 (HENDON WAY)                     | 301.0   | NW        | 28342 | 748.0    |           |
      | 22            | 51.582726,-0.227154 | Continue onto A41 (WATFORD WAY)                    | 340.0   | N         | 88373 | 2332.3   |           |
      | 23            | 51.601209,-0.234509 | Continue onto A1 (WATFORD WAY (BARNET BY-PASS))    | 325.0   | NW        | 72410 | 1911.0   |           |

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
      | car         |            | fastest   | 2h 37min      |

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
  Scenario Outline: Verify  waypoints on a Route from Southampton to Glasgow
    Given I have route point as
      | pointA              | pointB             |
      | 50.896617,-1.400465 | 55.861284,-4.24996 |
    And I have vehicle as "<vehicleType>"
    And I have weighting as "<routeType>"
    When I request for a route
    Then I should be able to verify the trackPoints on the route map:
      | trackPointco        |
      | 52.52355,-1.902136  |
      | 53.779418,-2.647821 |
      | 54.304996,-2.646641 |
      | 55.802602,-4.053713 |

    Examples: 
      | vehicleType | avoidances | routeType |
      | car         |            | fastest   |

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
      | 3             | 50.971952,-1.350891 | Turn left onto THE CRESCENT | 294.0   | NW        | 2981 | 37.3     |           |

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
      | 2             | 51.468925,-0.359049| Turn left onto HANWORTH ROAD (A315) | 239.0   | SW        | 16521 | 224.0    |           |

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
    Then I should be able to verify the trackPoints not on the route map:
      | trackPointco        |
      | 51.470009,-0.357019 |

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

  @WebOnly
  Scenario Outline: Verify  Route using Full UK Address (Coventry)
    Given I have route point as
      | pointA                                                         | pointB                              |
      | 3 BROMLEIGH VILLAS, COVENTRY ROAD, BAGINTON, COVENTRY, CV8 3AS | 2, PAXMEAD CLOSE, COVENTRY, CV6 2NJ |
    And I have vehicle as "<vehicleType>"
    And I have weighting as "<routeType>"
    When I request for a route
    Then I should be able to verify the waypoints on the route map:
      | wayPointIndex | waypointdesc                         |
      | 3             | Turn right onto HOWES LANE (B4115)   |
      | 6             | At roundabout, take exit 2 onto A444 |
      | 16            | Turn right onto PENNY PARK LANE      |

    Examples: 
      | vehicleType | avoidances | routeType |
      | car         |            | fastest   |

  @WebOnly
  Scenario Outline: Verify  Route using Full UK Address (Kington to London )
    Given I have route point as
      | pointA                           | pointB                                |
      | 5, OXFORD LANE, KINGTON, HR5 3ED | 64, TOWER MILL ROAD, LONDON, SE15 6BZ |
    And I have vehicle as "<vehicleType>"
    And I have weighting as "<routeType>"
    When I request for a route
    Then I should be able to verify the waypoints on the route map:
      | wayPointIndex | waypointdesc                                          |
      | 6             | Turn slight left onto HEADBROOK                       |
      | 16            | At roundabout, take exit 3 onto A49 (VICTORIA STREET) |

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
      | 3             | 50.920147,-1.310351 |At roundabout, take exit 2 onto CHARLES WATTS WAY (A334) | 0.0     | N         | 16555 | 465.7    |           |

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
      | 1             | 51.472387,-0.361788 | Continue onto ELLINGTON ROAD         | 286.0   | W         | 8390   | 104.9    |           |
      | 8             | 51.440696,-0.53089  | Continue onto M25                    | 204.0   | SW        | 32673  | 907.6    |           |
      | 12            | 51.355407,-0.679946 | At roundabout, take exit 3 onto A322 | 184.0   | S         | 224937 | 5936.2   |           |

    Examples: 
      | vehicleType | avoidances | routeType |
      | car         |            | fastest   |

  @Routing
  Scenario Outline: Verify  Route using one intermediate waypoint (Wentworth to Ascot via Windsor Park )
    Given I have route point as
      | pointA              | pointB              | pointC             |
      | 51.409426,-0.591727 | 51.407904,-0.617237 | 51.41855,-0.672385 |
    And I have vehicle as "<vehicleType>"
    And I have weighting as "<routeType>"
    When I request for a route
    Then I should be able to verify the waypoints on the route map:
      | wayPointIndex | waypointco          | waypointdesc                          | azimuth | direction | time   | distance | avoidance |
      | 2             | 51.40643,-0.596399  | Turn right onto BLACKNEST ROAD (A329) | 289.0   | W         | 88113  | 1115.9   |           |
      | 5             | 51.407984,-0.617235 | Continue onto LONDON ROAD (A329)      | 274.0   | W         | 162619 | 2540.3   |           |
      | 7             | 51.410306,-0.668737 | Turn right onto WINKFIELD ROAD (A330) | 7.0     | N         | 46532  | 955.5    |           |

    Examples: 
      | vehicleType | avoidances | routeType |
      | car         |            | fastest   |

  @Routing
  Scenario Outline: Verify  Route using one intermediate waypoint ( Chelsea to Winchester via Windlesham)
    Given I have route point as
      | pointA             | pointB             | pointC              |
      | 51.48676,-0.170426 | 51.36166,-0.645979 | 51.070889,-1.315293 |
    And I have vehicle as "<vehicleType>"
    And I have weighting as "<routeType>"
    When I request for a route
    Then I should be able to verify the waypoints on the route map:
      | wayPointIndex | waypointco          | waypointdesc                          | azimuth | direction | time  | distance | avoidance |
      | 4             | 51.489964,-0.172906 | Turn left onto DOVEHOUSE STREET       | 321.0   | NW        | 7600  | 95.0     |           |
      | 9             | 51.493673,-0.174548 | Turn right onto PELHAM STREET (A3218) | 0.0     | N         | 2446  | 55.7     |           |
      | 13            | 51.486844,-0.252027 | At roundabout, take exit 3 onto A4    | 189.0   | S         | 69313 | 1829.3   |           |

    Examples: 
      | vehicleType | avoidances | routeType |
      | car         |            | fastest   |

  @Routing
  Scenario Outline: Verify  Route using 2 intermediate waypoints (Hounslow to Reading via Staines and Bracknell )
    Given I have route point as
      | pointA                                 | pointB              | pointC                                 | pointD              |
      | 51.471546541834144,-0.3618621826171875 | 51.414152,-0.747504 | 51.45914115860512,-0.96679687499999995 | 51.433882,-0.537904 |
    And I have vehicle as "<vehicleType>"
    And I have weighting as "<routeType>"
    When I request for a route
    Then I should be able to verify the waypoints on the route map:
      | wayPointIndex | waypointco          | waypointdesc                      | azimuth | direction | time   | distance | avoidance |
      | 1             | 51.472387,-0.361788 | Continue onto ELLINGTON ROAD      | 286.0   | W         | 8390   | 104.9    |           |
      | 9             | 51.406127,-0.539808 | Continue onto M3                  | 162.0   | S         | 445073 | 12363.5  |           |
      | 16            | 51.414151,-0.747502 | Continue onto CHURCH ROAD (A3095) | 28.0    | NE        | 12891  | 340.2    |           |
      | 27            | 51.451397,-0.960099 | Turn right onto WATLINGTON STREET | 333.0   | NW        | 11978  | 149.7    |           |
      | 55            | 51.440767,-0.531845 | Continue onto A30                 | 17.0    | N         | 14025  | 370.1    |           |

    Examples: 
      | vehicleType | avoidances | routeType |
      | car         |            | fastest   |

  @Routing
  Scenario Outline: Verify  Route using 2 intermediate waypoints (Oxford to Eaton via Warwick and Cambridge )
    Given I have route point as
      | pointA              | pointB              | pointC             | pointD              |
      | 51.746075,-1.263972 | 52.289962,-1.604752 | 52.202814,0.051429 | 51.491412,-0.610276 |
    And I have vehicle as "<vehicleType>"
    And I have weighting as "<routeType>"
    When I request for a route
    Then I should be able to verify the waypoints on the route map:
      | wayPointIndex | waypointco          | waypointdesc                                         | azimuth | direction | time   | distance | avoidance |
      | 5             | 51.748432,-1.261457 | Turn left onto THAMES STREET (A420)                  | 275.0   | W         | 5517   | 145.6    |           |
      | 21            | 52.289769,-1.60905  | At roundabout, take exit 3 onto A46                  | 249.0   | W         | 481412 | 12704.3  |           |
      | 32            | 52.256925,-0.123683 | At roundabout, take exit 3 onto ST IVES ROAD (A1198) | 95.0    | E         | 57226  | 1510.2   |           |
      | 68            | 51.560087,-0.496049 | At roundabout, take exit 2 onto A412 (DENHAM ROAD)   | 98.0    | E         | 31561  | 832.9    |           |

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
      | 5             | 56.170837,-3.970499 | At roundabout, take exit 3 onto M9                     | 91.0    | E         | 142970 | 3961.4   |           |
      | 11            | 55.917381,-4.067178 | At roundabout, take exit 2 onto CUMBERNAULD ROAD (A80) | 156.0   | SE        | 175684 | 4636.2   |           |
      | 20            | 55.938772,-3.402452 | At roundabout, take exit 4 onto A8 (GLASGOW ROAD)      | 302.0   | NW        | 266985 | 7046.2   |           |
      | 30            | 55.949621,-3.214174 | Turn right onto MELVILLE STREET (MELVILLE CRESCENT)    | 51.0    | NE        | 2218   | 30.8     |           |

    Examples: 
      | vehicleType | avoidances | routeType |
      | car         |            | fastest   |

  @Routing
  Scenario Outline: Verify  nearest point of point using NearestPoint API
    Given I have type as "<responseFormat>"
    And My routing point for nearestPoint API as "<pointA>"
    When I request a nearest point from from Nearest Point API
    Then I should be able to verify the nearest point to be "<pointB>" at a distance of "<distance>"

    Examples: 
      | pointA                                 | pointB                                 | distance           |
      | 51.878966,-0.903849                    | 51.875144098888576,-0.9107481891829116 | 636.3215777261629  |
      | 53.101756,-1.954888                    | 53.10043020792586,-1.961414745138117   | 460.0011625834564  |
      | 53.065293927002806,-1.9071498141906338 | 53.065293927002806,-1.9071498141906338 | 0                  |
      | 52.784893,-1.84522                     | 52.79515894789604,-1.8521510478589918  | 1233.001210212637  |
      | 52.79515894789604,-1.8521510478589918  | 52.79515894789604,-1.8521510478589918  | 0                  |
      | 54.094977,-2.006081                    | 54.09420551570219,-2.0283477834242833  | 1454.551799711362  |
      | 54.115309,-2.111881                    | 54.133065323525635,-2.131028334744908  | 2335.612435123903  |
      | 54.095897,-2.144795                    | 54.08689388826998,-2.1488754559056935  | 1035.8644445463867 |
      | 50.658849,-1.386463                    | 50.65520130477257,-1.4000444889283343  | 1039.7773305822475 |
      | 56.025277,-4.917874                    | 56.02196904113215,-4.906092518708935   | 819.3253424080308  |
      | 50.664175,-1.358463                    | 50.66192580003871,-1.3486298102579224  | 736.8284619868352  |

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
      | 1             | 51.472387,-0.361788 | Continue onto ELLINGTON ROAD        | 286.0   | W         | 8390   | 104.9    |           |
      | 9             | 51.468674,-0.370905 | Turn right onto BATH ROAD (A3006)   | 280.0   | W         | 134212 | 1758.0   |           |
      | 17            | 51.483218,-0.583061 | Turn right onto WINDSOR ROAD (B470) | 321.0   | NW        | 60059  | 750.7    |           |

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
      | wayPointIndex | waypointco          | waypointdesc                           | azimuth | direction | time    | distance | avoidance |
      | 1             | 51.472387,-0.361788 | Continue onto ELLINGTON ROAD           | 286.0   | W         | 8390    | 104.9    |           |
      | 8             | 51.491316,-0.41082  | At roundabout, take exit 2 onto M4     | 339.0   | N         | 1300443 | 36121.5  |           |
      | 16            | 51.453903,-0.961826 | Continue onto WATLINGTON STREET (A329) | 341.0   | N         | 8584    | 132.7    |           |

    Examples: 
      | vehicleType | avoidances | routeType |
      | car         |            | fastest   |

  @Routing
  Scenario Outline: Verify  waypoints on a Route from London to Liverpool
    Given I have route point as
      | pointA              | pointB              |
      | 53.432923,-2.971511 | 51.505165,-0.147902 |
    And I have vehicle as "<vehicleType>"
    And I have weighting as "<routeType>"
    When I request for a route
    Then I should be able to verify the waypoints on the route map:
      | wayPointIndex | waypointco          | waypointdesc                                      | azimuth | direction | time    | distance | avoidance |
      | 9             | 53.47091,-2.632536  | Turn slight left onto M6                          | 186.0   | S         | 6600018 | 183338.0 |           |
      | 11            | 51.632754,-0.299837 | Continue onto A41 (EDGWARE WAY (WATFORD BY-PASS)) | 156.0   | SE        | 48011   | 1267.0   |           |

    Examples: 
      | vehicleType | avoidances | routeType |
      | car         |            | fastest   |

  @Routing
  Scenario Outline: Verify  waypoints on a Route from London to Liverpool
    Given I have route point as
      | pointA              | pointB              |
      | 53.432923,-2.971511 | 51.505165,-0.147902 |
    And I have vehicle as "<vehicleType>"
    And I have weighting as "<routeType>"
    When I request for a route
    Then I should be able to verify the waypoints on the route map:
      | wayPointIndex | waypointco          | waypointdesc                                        | azimuth | direction | time  | distance | avoidance |
      | 30            | 53.340148,-2.735071 | Turn slight left onto A533 (BRIDGEWATER EXPRESSWAY) | 96.0    | E         | 34015 | 897.7    |           |
      | 41            | 53.291868,-2.591888 | Turn slight right onto A49 (WARRINGTON ROAD)        | 162.0   | S         | 99801 | 2633.8   |           |
      | 80            | 53.101356,-2.286824 | Turn slight left onto LINLEY LANE (A50)             | 174.0   | S         | 6872  | 85.9     |           |

    Examples: 
      | vehicleType | avoidances | routeType |
      | car         |            | shortest  |

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
      | 5             | 50.705368,-1.324668 | Turn right onto GUNVILLE ROAD         | 161.0   | S         | 119526 | 1494.1   |           |
      | 9             | 50.687826,-1.320457 | Turn right onto BOWCOMBE ROAD (B3323) | 200.0   | S         | 274969 | 3715.5   |           |

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
      | wayPointIndex | waypointco          | waypointdesc                               | azimuth | direction | time  | distance | avoidance |
      | 6             | 50.643066,-1.391581 | Turn left onto LIMERSTONE ROAD (B3399)     | 77.0    | E         | 74475 | 1034.4   |           |
      | 10            | 50.64454,-1.354991  | Turn slight left onto FARRIERS WAY (B3323) | 11.0    | N         | 22784 | 284.8    |           |

    Examples: 
      | vehicleType | avoidances | routeType |
      | car         |            | shortest  |

  @Routing
  Scenario Outline: Verify  waypoints on a Route  from Stronchullin to Admore
    Given I have route point as
      | pointA              | pointB              |
      | 56.025277,-4.917874 | 55.992355,-4.636534 |
    And I have vehicle as "<vehicleType>"
    And I have weighting as "<routeType>"
    When I request for a route
    Then I should be able to verify the waypoints on the route map:
      | wayPointIndex | waypointco          | waypointdesc                           | azimuth | direction | time  | distance | avoidance |
      | 10            | 56.005209,-4.750748 | Continue onto WEST CLYDE STREET (A814) | 97.0    | E         | 40914 | 1079.8   |           |
      | 16            | 55.986711,-4.666085 | Continue onto STONEYMOLLAN ROAD        | 2.0     | N         | 44867 | 623.2    |           |

    Examples: 
      | vehicleType | avoidances | routeType |
      | car         |            | fastest   |

  @Routing
  Scenario Outline: Verify  waypoints on a Route from Stronchullin to Admore
    Given I have route point as
      | pointA              | pointB              |
      | 56.025277,-4.917874 | 55.992355,-4.636534 |
    And I have vehicle as "<vehicleType>"
    And I have weighting as "<routeType>"
    When I request for a route
    Then I should be able to verify the waypoints on the route map:
      | wayPointIndex | waypointco          | waypointdesc                         | azimuth | direction | time   | distance | avoidance |
      | 5             | 56.09353,-4.836554  | At roundabout, take exit 1 onto A814 | 86.0    | E         | 76206  | 2011.0   |           |
      | 15            | 55.975811,-4.678383 | Turn left onto RED ROAD              | 42.0    | NE        | 120144 | 1668.7   |           |

    Examples: 
      | vehicleType | avoidances | routeType |
      | car         |            | shortest  |

  @Routing
  Scenario Outline: Verify  waypoints on a Route from Swansea to Bath
    Given I have route point as
      | pointA              | pointB              |
      | 51.630586,-3.943108 | 51.386345,-2.344899 |
    And I have vehicle as "<vehicleType>"
    And I have weighting as "<routeType>"
    When I request for a route
    Then I should be able to verify the waypoints on the route map:
      | wayPointIndex | waypointco          | waypointdesc                         | azimuth | direction | time  | distance | avoidance |
      | 7             | 51.624622,-3.857058 | At roundabout, take exit 3 onto A483 | 5.0     | N         | 52856 | 1394.9   |           |
      | 15            | 51.387526,-2.346317 | Turn right onto WARMINSTER ROAD      | 161.0   | S         | 759   | 9.5      |           |

    Examples: 
      | vehicleType | avoidances | routeType |
      | car         |            | fastest   |

  @Routing
  Scenario Outline: Verify  waypoints on a Route from Swansea to Bath
    Given I have route point as
      | pointA              | pointB              |
      | 51.630586,-3.943108 | 51.386345,-2.344899 |
    And I have vehicle as "<vehicleType>"
    And I have weighting as "<routeType>"
    When I request for a route
    Then I should be able to verify the waypoints on the route map:
      | wayPointIndex | waypointco          | waypointdesc                                         | azimuth | direction | time   | distance | avoidance |
      | 15            | 51.531866,-3.681224 | Turn left onto BRIDGE STREET (B4281)                 | 50.0    | NE        | 780    | 9.8      |           |
      | 32            | 51.567867,-3.022101 | At roundabout, take exit 1 onto CARDIFF ROAD (B4237) | 36.0    | NE        | 203767 | 2710.8   |           |

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
      | 10            | 50.413722,-4.328221 | Continue onto A38 (MILL ROAD) | 74.0    | E         | 5105 | 134.8    |           |
      | 19            | 50.398962,-4.133073 | Turn left onto MAITLAND DRIVE | 36.0    | NE        | 4813 | 60.2     |           |

    Examples: 
      | vehicleType | avoidances | routeType |
      | car         |            | fastest   |
#fixed
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
      | 15            | 50.292674,-4.941538 | At roundabout, take exit 1 onto A390 | 87.0    | E         | 675020 | 12232.4  |           |
      | 20            | 50.338479,-4.772896 | Continue onto MOUNT CHARLES ROAD      | 85.0    | E         | 12180  | 163.4    |           |

    Examples: 
      | vehicleType | avoidances | routeType |
      | car         |            | shortest  |

  @Routing
  Scenario Outline: Verify  Route using 2 intermediate waypoints (Oxford to LONDON )
    Given I have route point as
      | pointA              | pointB              | pointC             | pointD              |
      | 51.746075,-1.263972 | 52.289962,-1.604752 | 52.202814,0.051429 | 51.491412,-0.610276 |
    And I have vehicle as "<vehicleType>"
    And I have weighting as "<routeType>"
    When I request for a route
    Then I should be able to verify the waypoints on the route map:
      | wayPointIndex | waypointco          | waypointdesc                                         | azimuth | direction | time   | distance | avoidance |
      | 5             | 51.748432,-1.261457 | Turn left onto THAMES STREET (A420)                  | 275.0   | W         | 5517   | 145.6    |           |
      | 21            | 52.289769,-1.60905  | At roundabout, take exit 3 onto A46                  | 249.0   | W         | 481412 | 12704.3  |           |
      | 32            | 52.256925,-0.123683 | At roundabout, take exit 3 onto ST IVES ROAD (A1198) | 95.0    | E         | 57226  | 1510.2   |           |
      | 68            | 51.560087,-0.496049 | At roundabout, take exit 2 onto A412 (DENHAM ROAD)   | 98.0    | E         | 31561  | 832.9    |           |

    Examples: 
      | vehicleType | avoidances | routeType |
      | car         |            | fastest   |

  @Routing
  Scenario Outline: Verify  Route using 2 intermediate waypoints (Oxford to LONDON )
    Given I have route point as
      | pointA              | pointB              | pointC             | pointD              |
      | 51.746075,-1.263972 | 52.289962,-1.604752 | 52.202814,0.051429 | 51.491412,-0.610276 |
    And I have vehicle as "<vehicleType>"
    And I have weighting as "<routeType>"
    When I request for a route
    Then I should be able to verify the waypoints on the route map:
      | wayPointIndex | waypointco          | waypointdesc                              | azimuth | direction | time   | distance | avoidance |
      | 5             | 51.748432,-1.261457 | Turn left onto THAMES STREET (A420)       | 275.0   | W         | 5517   | 145.6    |           |
      | 21            | 51.922501,-1.324913 | Turn slight left onto OXFORD ROAD (A4260) | 342.0   | N         | 243586 | 6342.0   |           |
      | 32            | 52.056919,-1.341208 | Turn right onto BEARGARDEN ROAD           | 324.0   | NW        | 24343  | 304.3    |           |
      | 67            | 52.288814,-1.607721 | Turn left onto BIRMINGHAM ROAD (A425)     | 130.0   | SE        | 26878  | 396.7    |           |

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
      | 5             | 56.204647,-3.952177 | Turn slight left onto B8033 | 225.0   | SW        | 42144  | 585.4    |           |
      | 22            | 55.871622,-4.198356 | Turn slight right onto M8   | 43.0    | NE        | 278576 | 7738.5   |           |

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
      | wayPointIndex | waypointco          | waypointdesc                       | azimuth | direction | time   | distance | avoidance |
      | 5             | 56.204647,-3.952177 | Turn slight left onto B8033 | 225.0    |SW        | 142970 | 30648   |           |
      | 15            | 55.871772,-4.195164 | At roundabout, take exit 2 onto A80                | 85.0   | SW        | 46281  | 944.1    |           |
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
  @Routing
  Scenario Outline: Verify  waypoints on a Route
    Given I have route point as
      | pointA              | pointB              |
      | 50.949322,-1.483392 | 50.961009,-1.424954 |
    And I have vehicle as "<vehicleType>"
    And I have weighting as "<routeType>"
    When I request for a route
    Then I should be able to verify the waypoints on the route map:
      | wayPointIndex | waypointco          | waypointdesc                     | azimuth | direction | time  | distance | avoidance |
      | 2             | 50.956976,-1.485761 | Turn right onto COLDHARBOUR LANE | 89.0    | E         | 69870 | 970.4    |           |

    Examples: 
      | vehicleType | avoidances | routeType |
      | car         |            | fastest   |
