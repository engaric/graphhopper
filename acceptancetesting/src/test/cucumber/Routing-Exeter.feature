Feature: Verify a route from A to B
    As a user
    I want to get a route from location A to location B using the routing service
    And route should be the fastest route and contain the waypoints,restrictions,time and other instructions

  @Routing
  Scenario Outline: Verify  one Way  Restrictions  on a Route (EX-Bridge South - Exteter)
    Given I have route point as
      | pointA              | pointB              |
      | 50.717615,-3.536538 | 50.719106,-3.535359 |
    And I have vehicle as "<vehicleType>"
    And I have avoidances as "<avoidances>"
    And I have weighting as "<routeType>"
    When I request for a route
    Then I should be able to verify the waypoints on the route map:
      | wayPointIndex | waypointco          | waypointdesc                      | azimuth | direction | time  | distance | avoidance |
      | 2             | 50.719156,-3.537811 | Continue onto A3015 (FROG STREET) | 41.0    | NE        | 13259 | 221.8    |           |

    Examples: 
      | vehicleType | avoidances | routeType |
      | car         |            | fastest   |

  @Routing
  Scenario Outline: Verify  one Way  Restrictions on a Route (Cleveladn Street-Exeter)
    Given I have route point as
      | pointA              | pointB              |
      | 50.717951,-3.542331 | 50.718613,-3.539589 |
    And I have vehicle as "<vehicleType>"
    And I have avoidances as "<avoidances>"
    And I have weighting as "<routeType>"
    When I request for a route
    Then I should be able to verify the waypoints on the route map:
      | wayPointIndex | waypointco         | waypointdesc               | azimuth | direction | time | distance | avoidance |
      | 2             | 50.717806,-3.54264 | Turn left onto BULLER ROAD | 137.0   | SE        | 4467 | 55.8     |           |

    Examples: 
      | vehicleType | avoidances | routeType |
      | car         |            | fastest   |

  @Routing
  Scenario Outline: Verify  one Way  Restrictions on a Route (Cleveladn Street-Exeter)
    Given I have route point as
      | pointA              | pointB              |
      | 50.718282,-3.538437 | 50.717687,-3.541511 |
    And I have vehicle as "<vehicleType>"
    And I have avoidances as "<avoidances>"
    And I have weighting as "<routeType>"
    When I request for a route
    Then I should be able to verify the waypoints on the route map:
      | wayPointIndex | waypointco          | waypointdesc                    | azimuth | direction | time | distance | avoidance |
      | 4             | 50.718462,-3.541302 | Turn left onto CLEVELAND STREET | 232.0   | SW        | 9534 | 119.2    |           |

    Examples: 
      | vehicleType | avoidances | routeType |
      | car         |            | fastest   |

  @Routing
  Scenario Outline: Verify  one Way  Restrictions (Except Buses) on a Route (SIDWELL STREET-Exeter)
    Given I have route point as
      | pointA              | pointB               |
      | 50.727949,-3.523498 | 50.726428,-3.5251291 |
    And I have vehicle as "<vehicleType>"
    And I have avoidances as "<avoidances>"
    And I have weighting as "<routeType>"
    When I request for a route
    Then I should be able to verify the waypoints on the route map:
      | wayPointIndex | waypointco         | waypointdesc                    | azimuth | direction | time | distance | avoidance |
      | 4             | 50.726689,-3.52712 | Turn left onto LONGBROOK STREET | 190.0   | S         | 6267 | 78.3     |           |

    Examples: 
      | vehicleType | avoidances | routeType |
      | car         |            | fastest   |

  @Routing
  Scenario Outline: Verify  oneway Restrictions on a Route (Exeter)
    Given I have route point as
      | pointA              | pointB             |
      | 50.720492,-3.535221 | 50.718641,-3.53476 |
    And I have vehicle as "<vehicleType>"
    And I have avoidances as "<avoidances>"
    And I have weighting as "<routeType>"
    When I request for a route
    Then I should be able to verify the trackPoints not on the route map:
      | trackPointco       |
      | 50.71958,-3.534089 |

    Examples: 
      | vehicleType | avoidances | routeType |
      | car         |            | fastest   |

  @KnownIssues
  Scenario Outline: Verify  one Way  Restrictions  on a Route (Exeter WSPIP-98)
    Given I have route point as
      | pointA              | pointB              |
      | 50.720454,-3.530089 | 50.722657,-3.526321 |
    And I have vehicle as "<vehicleType>"
    And I have avoidances as "<avoidances>"
    And I have weighting as "<routeType>"
    When I request for a route
    Then I should be able to verify the waypoints not on the route map:
      | wayPointIndex | waypointco          | waypointdesc                    | azimuth | direction | time | distance | avoidance |
      | 7             | 50.722198,-3.526704 | Turn left onto SOUTHERNHAY EAST | 32      | NE        | 5838 | 56.761   |           |

    Examples: 
      | vehicleType | avoidances | routeType |
      | car         |            | fastest   |

  # No Entry Restrictions
  @Routing
  Scenario Outline: Verify  No Entry  Restrictions on a Route (High Street(London Inn Square)-Exeter)
    Given I have route point as
      | pointA              | pointB             |
      | 50.725425,-3.526925 | 50.72442,-3.532756 |
    And I have vehicle as "<vehicleType>"
    And I have avoidances as "<avoidances>"
    And I have weighting as "<routeType>"
    When I request for a route
    Then I should be able to verify the waypoints on the route map:
      | wayPointIndex | waypointco         | waypointdesc                                 | azimuth | direction | time  | distance | avoidance |
      | 2             | 50.725549,-3.52693 | Turn slight left onto NEW NORTH ROAD (B3183) | 285.0   | W         | 57545 | 729.1    |           |

    Examples: 
      | vehicleType | avoidances | routeType |
      | car         |            | fastest   |

  @Routing
  Scenario Outline: Verify  No Entry  Restrictions on a Route (CHEEK STREET-Exeter)
    Given I have route point as
      | pointA              | pointB             |
      | 50.726234,-3.524072 | 50.727186,-3.52392 |
    And I have vehicle as "<vehicleType>"
    And I have avoidances as "<avoidances>"
    And I have weighting as "<routeType>"
    When I request for a route
    Then I should be able to verify the waypoints on the route map:
      | wayPointIndex | waypointco          | waypointdesc                     | azimuth | direction | time | distance | avoidance |
      | 2             | 50.727244,-3.522476 | Turn left onto SUMMERLAND STREET | 313.0   | NW        | 5223 | 65.3     |           |

    Examples: 
      | vehicleType | avoidances | routeType |
      | car         |            | fastest   |

  @Routing
  Scenario Outline: Verify  No Entry(Except for Buses and Taxis)  Restrictions on a Route (Sidwell Street-Exeter)
    Given I have route point as
      | pointA              | pointB             |
      | 50.726529,-3.524928 | 50.727002,-3.52419 |
    And I have vehicle as "<vehicleType>"
    And I have avoidances as "<avoidances>"
    And I have weighting as "<routeType>"
    When I request for a route
    Then I should be able to verify the waypoints on the route map:
      | wayPointIndex | waypointco         | waypointdesc                    | azimuth | direction | time  | distance | avoidance |
      | 4             | 50.726418,-3.52381 | Turn left onto BAMPFYLDE STREET | 45.0    | NE        | 10510 | 131.4    |           |

    Examples: 
      | vehicleType | avoidances | routeType |
      | car         |            | fastest   |

  # No Turns Restrictions and Roundabout
  @Routing
  Scenario Outline: Verify  No Turn  Restrictions on a Route (Western Way-Exeter)
    Given I have route point as
      | pointA              | pointB              |
      | 50.726735,-3.520955 | 50.726914,-3.522033 |
    And I have vehicle as "<vehicleType>"
    And I have avoidances as "<avoidances>"
    And I have weighting as "<routeType>"
    When I request for a route
    Then I should be able to verify the waypoints on the route map:
      | wayPointIndex | waypointco          | waypointdesc                                   | azimuth | direction | time  | distance | avoidance |
      | 2             | 50.728509,-3.520647 | At roundabout, take exit 1 onto SIDWELL STREET | 282.0   | W         | 16437 | 212.5    |           |

    Examples: 
      | vehicleType | avoidances | routeType |
      | car         |            | fastest   |

  @Routing
  Scenario Outline: Verify  No Turn Restriction (Denmark Road-Exeter)
    Given I have route point as
      | pointA              | pointB              |
      | 50.724901,-3.521588 | 50.724524,-3.520923 |
    And I have vehicle as "<vehicleType>"
    And I have avoidances as "<avoidances>"
    And I have weighting as "<routeType>"
    When I request for a route
    Then I should be able to verify the waypoints on the route map:
      | wayPointIndex | waypointco          | waypointdesc                  | azimuth | direction | time  | distance | avoidance |
      | 3             | 50.725002,-3.520632 | Turn left onto RUSSELL STREET | 303.0   | NW        | 19909 | 248.9    |           |

    Examples: 
      | vehicleType | avoidances | routeType |
      | car         |            | fastest   |

  @Routing
  Scenario Outline: Verify  Turn Restrictions  on a Route (Exeter)
    Given I have route point as
      | pointA             | pointB             |
      | 50.72148,-3.532485 | 50.721888,-3.53182 |
    And I have vehicle as "<vehicleType>"
    And I have avoidances as "<avoidances>"
    And I have weighting as "<routeType>"
    When I request for a route
    Then I should be able to verify the trackPoints not on the route map:
      | trackPointco        |
      | 50.721201,-3.532498 |

    Examples: 
      | vehicleType | avoidances | routeType |
      | car         |            | fastest   |

  # The below issue is a data issue and been reported into jira as route-67
  @KnownIssues
  Scenario Outline: Verify No  Turn Restrictions(Except Bus)  on a Route (BELGROVE ROAD -Exeter ROUTE-67)
    Given I have route point as
      | pointA              | pointB             |
      | 50.726085,-3.522837 | 50.725076,-3.52442 |
    And I have vehicle as "<vehicleType>"
    And I have avoidances as "<avoidances>"
    And I have weighting as "<routeType>"
    When I request for a route
    Then I should be able to verify the waypoints not on the route map:
      | wayPointIndex | waypointco         | waypointdesc                 | azimuth | direction | time | distance | avoidance |
      | 2             | 50.725997,-3.52296 | Turn left onto CHEEKE STREET | 135     | SE        | 5639 | 56.915   |           |

    Examples: 
      | vehicleType | avoidances | routeType |
      | car         |            | fastest   |

  # Mandatory Turn Restrictions
  @Routing
  Scenario Outline: Verify  Mandatory Turn(with exceptions) at Exeter area
    Given I have route point as
      | pointA              | pointB              |
      | 50.726823,-3.524432 | 50.725423,-3.526813 |
    And I have vehicle as "<vehicleType>"
    And I have avoidances as "<avoidances>"
    And I have weighting as "<routeType>"
    When I request for a route
    Then I should be able to verify the waypoints on the route map:
      | wayPointIndex | waypointco          | waypointdesc                | azimuth | direction | time | distance | avoidance |
      | 2             | 50.726462,-3.523882 | Continue onto CHEEKE STREET | 133.0   | SE        | 564  | 7.1      |           |

    Examples: 
      | vehicleType | avoidances | routeType |
      | car         |            | fastest   |

  @Routing
  Scenario Outline: Verify  Mandatory Turn at Exeter area(DENMARK ROAD)
    Given I have route point as
      | pointA              | pointB              |
      | 50.724777,-3.520811 | 50.724394,-3.520953 |
    And I have vehicle as "<vehicleType>"
    And I have avoidances as "<avoidances>"
    And I have weighting as "<routeType>"
    When I request for a route
    Then I should be able to verify the waypoints on the route map:
      | wayPointIndex | waypointco          | waypointdesc                  | azimuth | direction | time  | distance | avoidance |
      | 2             | 50.725002,-3.520632 | Turn left onto RUSSELL STREET | 303.0   | NW        | 19909 | 248.9    |           |

    Examples: 
      | vehicleType | avoidances | routeType |
      | car         |            | fastest   |

  @Routing @KnownIssues
  Scenario Outline: Verify  Mandatory Turn at Exeter area(COLLEGE ROAD)
    Given I have route point as
      | pointA             | pointB              |
      | 50.723597,-3.51776 | 50.723773,-3.517251 |
    And I have vehicle as "<vehicleType>"
    And I have avoidances as "<avoidances>"
    And I have weighting as "<routeType>"
    When I request for a route
    Then I should be able to verify the waypoints on the route map:
      | wayPointIndex | waypointco         | waypointdesc                | azimuth | direction | time  | distance | avoidance |
      | 2             | 50.72133,-3.519451 | Turn right onto SPICER ROAD | 278     | W         | 41233 | 400.903  |           |

    Examples: 
      | vehicleType | avoidances | routeType |
      | car         |            | fastest   |

  @Routing
  Scenario Outline: Verify  Mandatory Turn Restriction (Denmark Road-Exeter)
    Given I have route point as
      | pointA              | pointB             |
      | 50.724378,-3.520993 | 50.72413,-3.518874 |
    And I have vehicle as "<vehicleType>"
    And I have avoidances as "<avoidances>"
    And I have weighting as "<routeType>"
    When I request for a route
    Then I should be able to verify the waypoints not on the route map:
      | wayPointIndex | waypointco          | waypointdesc                           | azimuth | direction | time | distance | avoidance |
      | 2             | 50.724703,-3.520835 | Turn right onto HEAVITREE ROAD (B3183) | 293.0   | NW        | 1118 | 10.9     |           |

    Examples: 
      | vehicleType | avoidances | routeType |
      | car         |            | fastest   |

  # Access Limited To
  @Routing
  Scenario Outline: Verify  Access Limited To  Restrictions on a Route (North Street-Exeter)
    Given I have route point as
      | pointA              | pointB              |
      | 50.722996,-3.533354 | 50.726428,-3.525129 |
    And I have vehicle as "<vehicleType>"
    And I have avoidances as "<avoidances>"
    And I have weighting as "<routeType>"
    When I request for a route
    Then I should be able to verify the waypoints on the route map:
      | wayPointIndex | waypointco       | waypointdesc               | azimuth | direction | time  | distance | avoidance |
      | 2             | 50.72258,-3.5326 | Continue onto SOUTH STREET | 135.0   | SE        | 30038 | 379.1    |           |

    Examples: 
      | vehicleType | avoidances | routeType |
      | car         |            | fastest   |

  @Routing
  Scenario Outline: Verify  Access Limited To  Restrictions on a Route (Paris Street-Exeter)
    Given I have route point as
      | pointA              | pointB              |
      | 50.724989,-3.526006 | 50.729735,-3.519862 |
    And I have vehicle as "<vehicleType>"
    And I have avoidances as "<avoidances>"
    And I have weighting as "<routeType>"
    When I request for a route
    Then I should be able to verify the waypoints on the route map:
      | wayPointIndex | waypointco         | waypointdesc                    | azimuth | direction | time  | distance | avoidance |
      | 5             | 50.726418,-3.52381 | Turn left onto BAMPFYLDE STREET | 45.0    | NE        | 10510 | 131.4    |           |

    Examples: 
      | vehicleType | avoidances | routeType |
      | car         |            | fastest   |

  # Access Prohibited To
  @Routing
  Scenario Outline: Verify  Access Prohibited To  Restrictions on a Route (Iron Bridge Street-Exeter)
    Given I have route point as
      | pointA             | pointB              |
      | 50.72458,-3.536493 | 50.723442,-3.534131 |
    And I have vehicle as "<vehicleType>"
    And I have avoidances as "<avoidances>"
    And I have weighting as "<routeType>"
    When I request for a route
    Then I should be able to verify the waypoints on the route map:
      | wayPointIndex | waypointco         | waypointdesc                   | azimuth | direction | time  | distance | avoidance |
      | 2             | 50.724661,-3.53639 | Turn left onto ST DAVID'S HILL | 310.0   | NW        | 35154 | 439.4    |           |

    Examples: 
      | vehicleType | avoidances | routeType |
      | car         |            | fastest   |

  @Routing @KnownIssues
  Scenario Outline: Verify  Access Prohibited To  Restrictions on a Route (Upper Paul Street-Exeter)
    Given I have route point as
      | pointA              | pointB              |
      | 50.724614,-3.532555 | 50.724639,-3.530457 |
    And I have vehicle as "<vehicleType>"
    And I have avoidances as "<avoidances>"
    And I have weighting as "<routeType>"
    When I request for a route
    Then I should be able to verify the waypoints on the route map:
      | wayPointIndex | waypointco          | waypointdesc                | azimuth | direction | time  | distance | avoidance |
      | 2             | 50.724819,-3.532223 | Turn left onto QUEEN STREET | 324.0   | NW        | 37994 | 369.4    |           |

    Examples: 
      | vehicleType | avoidances | routeType |
      | car         |            | fastest   |

  # Ford
  @Routing
  Scenario Outline: Verify  Ford  Restrictions on a Route (BONHAY Road-Exeter)
    Given I have route point as
      | pointA             | pointB              |
      | 50.731111,-3.54277 | 50.719327,-3.538255 |
    And I have vehicle as "<vehicleType>"
    And I have avoidances as "<avoidances>"
    And I have weighting as "<routeType>"
    When I request for a route
    Then I should be able to verify the waypoints on the route map:
      | wayPointIndex | waypointco          | waypointdesc                    | azimuth | direction | time  | distance | avoidance |
      | 4             | 50.727823,-3.540036 | Turn slight left onto HELE ROAD | 85.0    | E         | 12281 | 153.5    |           |

    Examples: 
      | vehicleType | avoidances | routeType |
      | car         |            | fastest   |

  @Routing
  Scenario Outline: Verify  Ford  Restrictions on a Route (Quadrangle Road-Exeter)
    Given I have route point as
      | pointA             | pointB              |
      | 50.730861,-3.52934 | 50.731808,-3.529829 |
    And I have vehicle as "<vehicleType>"
    And I have avoidances as "<avoidances>"
    And I have weighting as "<routeType>"
    When I request for a route
    Then I should be able to verify the waypoints on the route map:
      | wayPointIndex | waypointco          | waypointdesc               | azimuth | direction | time  | distance | avoidance |
      | 2             | 50.730716,-3.530028 | Turn left onto HORSEGUARDS | 189.0   | S         | 21893 | 273.7    |           |

    Examples: 
      | vehicleType | avoidances | routeType |
      | car         |            | fastest   |

  # Gate
  @Routing
  Scenario Outline: Verify  Gate  Restrictions on a Route (Cathedral Close Road-Exeter)
    Given I have route point as
      | pointA              | pointB             |
      | 50.722333,-3.527488 | 50.72243,-3.532372 |
    And I have vehicle as "<vehicleType>"
    And I have avoidances as "<avoidances>"
    And I have weighting as "<routeType>"
    When I request for a route
    Then I should be able to verify the waypoints on the route map:
      | wayPointIndex | waypointco          | waypointdesc                     | azimuth | direction | time  | distance | avoidance |
      | 3             | 50.722198,-3.526704 | Turn right onto SOUTHERNHAY EAST | 202.0   | SW        | 20129 | 255.9    |           |

    Examples: 
      | vehicleType | avoidances | routeType |
      | car         |            | fastest   |

  @Routing
  Scenario Outline: Verify  Gate  Restrictions on a Route (Lower Northen Road-Exeter)
    Given I have route point as
      | pointA            | pointB              |
      | 50.7244,-3.535817 | 50.723705,-3.534493 |
    And I have vehicle as "<vehicleType>"
    And I have avoidances as "<avoidances>"
    And I have weighting as "<routeType>"
    When I request for a route
    Then I should be able to verify the waypoints on the route map:
      | wayPointIndex | waypointco          | waypointdesc        | azimuth | direction | time  | distance | avoidance |
      | 2             | 50.722081,-3.539012 | Turn left onto A377 | 166.0   | S         | 29003 | 395.5    |           |

    Examples: 
      | vehicleType | avoidances | routeType |
      | car         |            | fastest   |

  #Roundabout
  @Routing
  Scenario Outline: Verify a  Private Road (Publicly Accessible) on a Route (QUEEN STREET)
    Given I have route point as
      | pointA              | pointB              |
      | 50.727003,-3.535041 | 50.727023,-3.533083 |
    And I have vehicle as "<vehicleType>"
    And I have avoidances as "<avoidances>"
    And I have weighting as "<routeType>"
    When I request for a route
    Then I should be able to verify the waypoints on the route map:
      | wayPointIndex | waypointco          | waypointdesc                                           | azimuth | direction | time  | distance | avoidance |
      | 2             | 50.727397,-3.535531 | At roundabout, take exit 3 onto NEW NORTH ROAD (B3183) | 295.0   | NW        | 14023 | 181.3    |           |

    Examples: 
      | vehicleType | avoidances | routeType |
      | car         |            | fastest   |

  @Routing
  Scenario Outline: Verify a PrivateRoad -Restricted Access(WESTERN WAY)
    Given I have route point as
      | pointA              | pointB             |
      | 50.725876,-3.521801 | 50.72619,-3.521541 |
    And I have vehicle as "<vehicleType>"
    And I have avoidances as "<avoidances>"
    And I have weighting as "<routeType>"
    When I request for a route
    Then I should be able to verify the waypoints on the route map:
      | wayPointIndex | waypointco         | waypointdesc                      | azimuth | direction | time | distance | avoidance |
      | 1             | 50.72593,-3.521909 | Continue onto B3212 (WESTERN WAY) | 38.0    | NE        | 3099 | 38.7     |           |

    Examples: 
      | vehicleType | avoidances | routeType |
      | car         |            | fastest   |

  @Routing
  Scenario Outline: Verify a Private Road - Restricted Access (Denmark Road-Exeter)
    Given I have route point as
      | pointA              | pointB             |
      | 50.724316,-3.521008 | 50.72413,-3.518874 |
    And I have vehicle as "<vehicleType>"
    And I have avoidances as "<avoidances>"
    And I have weighting as "<routeType>"
    When I request for a route
    Then I should be able to verify the trackPoints not on the route map:
      | trackPointco      |
      | 50.723966,-3.5198 |

    Examples: 
      | vehicleType | avoidances | routeType |
      | car         |            | fastest   |

  #Roundabouts
  @Routing
  Scenario Outline: Verify a roundabout(WESTERN WAY)
    Given I have route point as
      | pointA              | pointB              |
      | 50.729277,-3.519078 | 50.728889,-3.522884 |
    And I have vehicle as "<vehicleType>"
    And I have avoidances as "<avoidances>"
    And I have weighting as "<routeType>"
    When I request for a route
    Then I should be able to verify the waypoints on the route map:
      | wayPointIndex | waypointco          | waypointdesc                                   | azimuth | direction | time  | distance | avoidance |
      | 2             | 50.728793,-3.520273 | At roundabout, take exit 2 onto SIDWELL STREET | 178.0   | S         | 19800 | 259.2    |           |

    Examples: 
      | vehicleType | avoidances | routeType |
      | car         |            | fastest   |

  @Routing
  Scenario Outline: Verify a roundabout(WESTERN WAY)
    Given I have route point as
      | pointA              | pointB              |
      | 50.724137,-3.518937 | 50.728366,-3.524132 |
    And I have vehicle as "<vehicleType>"
    And I have avoidances as "<avoidances>"
    And I have weighting as "<routeType>"
    When I request for a route
    Then I should be able to verify the waypoints on the route map:
      | wayPointIndex | waypointco          | waypointdesc                                        | azimuth | direction | time  | distance | avoidance |
      | 2             | 50.725096,-3.522378 | At roundabout, take exit 4 onto B3212 (WESTERN WAY) | 239.0   | SW        | 19384 | 255.8    |           |

    Examples: 
      | vehicleType | avoidances | routeType |
      | car         |            | fastest   |

  # Quickest route
  # Motorways (Victoria Street, Union Road ,Blackhall Road ,Well Street ,Devon Shire Place, Culverland Road).These roads are converted into motorways in Exeter
  @Routing
  Scenario Outline: Verify  a quickest route  on a Route (Springfield Road-Exeter)
    Given I have route point as
      | pointA             | pointB            |
      | 50.733719,-3.52332 | 50.732556,-3.5211 |
    And I have vehicle as "<vehicleType>"
    And I have avoidances as "<avoidances>"
    And I have weighting as "<routeType>"
    When I request for a route
    Then I should be able to verify the waypoints on the route map:
      | wayPointIndex | waypointco          | waypointdesc                    | azimuth | direction | time | distance | avoidance |
      | 2             | 50.733764,-3.523212 | Turn right onto VICTORIA STREET | 150.0   | SE        | 7689 | 213.6    |           |

    Examples: 
      | vehicleType | avoidances | routeType |
      | car         |            | fastest   |

  @Routing
  Scenario Outline: Verify  a quickest route  on a Route (DEVON SHIRE PLACE-Exeter)
    Given I have route point as
      | pointA              | pointB             |
      | 50.734095,-3.524696 | 50.72809,-3.524451 |
    And I have vehicle as "<vehicleType>"
    And I have avoidances as "<avoidances>"
    And I have weighting as "<routeType>"
    When I request for a route
    Then I should be able to verify the waypoints on the route map:
      | wayPointIndex | waypointco          | waypointdesc                      | azimuth | direction | time | distance | avoidance |
      | 3             | 50.733574,-3.524027 | Turn right onto DEVON SHIRE PLACE | 162.0   | S         | 8490 | 235.9    |           |

    Examples: 
      | vehicleType | avoidances | routeType |
      | car         |            | fastest   |

  @Routing
  Scenario Outline: Verify  a quickest route  on a Route (BLACKALL ROAD-Exeter)
    Given I have route point as
      | pointA              | pointB             |
      | 50.729887,-3.526896 | 50.726279,-3.52780 |
    And I have vehicle as "<vehicleType>"
    And I have avoidances as "<avoidances>"
    And I have weighting as "<routeType>"
    When I request for a route
    Then I should be able to verify the waypoints on the route map:
      | wayPointIndex | waypointco          | waypointdesc                                | azimuth | direction | time  | distance | avoidance |
      | 3             | 50.727984,-3.530548 | Turn sharp left onto NEW NORTH ROAD (B3183) | 117.0   | SE        | 22108 | 276.4    |           |

    Examples: 
      | vehicleType | avoidances | routeType |
      | car         |            | fastest   |

  @Routing
  Scenario Outline: Verify  a quickest route  on a Route (VICTORIA STREET-Exeter)
    Given I have route point as
      | pointA              | pointB              |
      | 50.733648,-3.523662 | 50.732844,-3.521332 |
    And I have vehicle as "<vehicleType>"
    And I have avoidances as "<avoidances>"
    And I have weighting as "<routeType>"
    When I request for a route
    Then I should be able to verify the waypoints on the route map:
      | wayPointIndex | waypointco          | waypointdesc                    | azimuth | direction | time | distance | avoidance |
      | 2             | 50.733764,-3.523212 | Turn right onto VICTORIA STREET | 150.0   | SE        | 7689 | 213.6    |           |

    Examples: 
      | vehicleType | avoidances | routeType |
      | car         |            | fastest   |

  # Shortest and Fastest Routes
  @Routing
  Scenario Outline: Verify  a shortest route  on a Route (St DAVID's-Exeter)
    Given I have route point as
      | pointA              | pointB             |
      | 50.729373,-3.540902 | 50.725564,-3.51809 |
    And I have vehicle as "<vehicleType>"
    And I have avoidances as "<avoidances>"
    And I have weighting as "<routeType>"
    When I request for a route
    Then I should be able to verify the waypoints on the route map:
      | wayPointIndex | waypointco         | waypointdesc                        | azimuth | direction | time  | distance | avoidance |
      | 6             | 50.725549,-3.52693 | Turn sharp left onto SIDWELL STREET | 47.0    | NE        | 17774 | 226.1    |           |

    Examples: 
      | vehicleType | avoidances | routeType |
      | car         |            | shortest  |

  @Routing
  Scenario Outline: Verify  a fastest route  on a Route (St DAVID's-Exeter)
    Given I have route point as
      | pointA              | pointB             |
      | 50.729373,-3.540902 | 50.725564,-3.51809 |
    And I have vehicle as "<vehicleType>"
    And I have avoidances as "<avoidances>"
    And I have weighting as "<routeType>"
    When I request for a route
    Then I should be able to verify the waypoints on the route map:
      | wayPointIndex | waypointco          | waypointdesc                 | azimuth | direction | time  | distance | avoidance |
      | 6             | 50.727984,-3.530548 | Turn left onto BLACKALL ROAD | 34.0    | NE        | 13596 | 377.7    |           |

    Examples: 
      | vehicleType | avoidances | routeType |
      | car         |            | fastest   |

  @Routing
  Scenario Outline: Verify  a shortest route  on a Route (Prince Of Wales Road-Exeter)
    Given I have route point as
      | pointA              | pointB             |
      | 50.729373,-3.540902 | 50.725564,-3.51809 |
    And I have vehicle as "<vehicleType>"
    And I have avoidances as "<avoidances>"
    And I have weighting as "<routeType>"
    When I request for a route
    Then I should be able to verify the waypoints on the route map:
      | wayPointIndex | waypointco          | waypointdesc                    | azimuth | direction | time  | distance | avoidance |
      | 2             | 50.727823,-3.540036 | Turn slight left onto HELE ROAD | 85.0    | E         | 12281 | 153.5    |           |

    Examples: 
      | vehicleType | avoidances | routeType |
      | car         |            | shortest  |

  @Routing
  Scenario Outline: Verify  a fastest route  on a Route (Prince Of Wales Road-Exeter)
    Given I have route point as
      | pointA              | pointB             |
      | 50.732019,-3.537145 | 50.725564,-3.51809 |
    And I have vehicle as "<vehicleType>"
    And I have avoidances as "<avoidances>"
    And I have weighting as "<routeType>"
    When I request for a route
    Then I should be able to verify the waypoints on the route map:
      | wayPointIndex | waypointco          | waypointdesc             | azimuth | direction | time | distance | avoidance |
      | 2             | 50.733417,-3.524972 | Continue onto UNION ROAD | 77.0    | E         | 2478 | 68.8     |           |

    Examples: 
      | vehicleType | avoidances | routeType |
      | car         |            | fastest   |

  @Routing
  Scenario Outline: Verify  a shortest route  on a Route (Blackall Road - Exeter)
    Given I have route point as
      | pointA              | pointB              |
      | 50.728119,-3.530372 | 50.723788,-3.517289 |
    And I have vehicle as "<vehicleType>"
    And I have avoidances as "<avoidances>"
    And I have weighting as "<routeType>"
    When I request for a route
    Then I should be able to verify the waypoints on the route map:
      | wayPointIndex | waypointco          | waypointdesc                                | azimuth | direction | time  | distance | avoidance |
      | 2             | 50.727984,-3.530548 | Turn sharp left onto NEW NORTH ROAD (B3183) | 117.0   | SE        | 29739 | 378.4    |           |

    Examples: 
      | vehicleType | avoidances | routeType |
      | car         |            | shortest  |

  @Routing
  Scenario Outline: Verify  a fastest route  on a Route (Blackall Road - Exeter)
    Given I have route point as
      | pointA              | pointB              |
      | 50.728119,-3.530372 | 50.723788,-3.517289 |
    And I have vehicle as "<vehicleType>"
    And I have avoidances as "<avoidances>"
    And I have weighting as "<routeType>"
    When I request for a route
    Then I should be able to verify the waypoints on the route map:
      | wayPointIndex | waypointco         | waypointdesc                      | azimuth | direction | time | distance | avoidance |
      | 2             | 50.729609,-3.52599 | Turn right onto PENNSYLVANIA ROAD | 193.0   | S         | 4106 | 51.3     |           |

    Examples: 
      | vehicleType | avoidances | routeType |
      | car         |            | fastest   |

  # Intermediate waypoints
  @Routing
  Scenario Outline: Verify  Route using one intermediate waypoint (Sidwell-Street and Western Way )
    Given I have route point as
      | pointA             | pointB              | pointC              |
      | 50.726537,-3.52799 | 50.729285,-3.518781 | 50.724236,-3.523403 |
    And I have vehicle as "<vehicleType>"
    And I have avoidances as "<avoidances>"
    And I have weighting as "<routeType>"
    When I request for a route
    Then I should be able to verify the waypoints on the route map:
      | wayPointIndex | waypointco          | waypointdesc                         | azimuth | direction | time  | distance | avoidance |
      | 2             | 50.725549,-3.52693  | Turn sharp left onto SIDWELL STREET  | 47.0    | NE        | 17774 | 226.1    |           |
      | 12            | 50.729424,-3.518763 | Turn left onto B3212 (BLACKBOY ROAD) | 252.0   | W         | 9435  | 128.5    |           |

    Examples: 
      | vehicleType | avoidances | routeType |
      | car         |            | fastest   |

  @Routing
  Scenario Outline: Verify  Route using one intermediate waypoint (Southern Way and Paris-Street)
    Given I have route point as
      | pointA              | pointB              | pointC              |
      | 50.722314,-3.539121 | 50.721886,-3.527393 | 50.727346,-3.529876 |
    And I have vehicle as "<vehicleType>"
    And I have avoidances as "<avoidances>"
    And I have weighting as "<routeType>"
    When I request for a route
    Then I should be able to verify the waypoints on the route map:
      | wayPointIndex | waypointco          | waypointdesc                                         | azimuth | direction | time  | distance | avoidance |
      | 6             | 50.720037,-3.528958 | Continue onto B3212 (MAGDALEN STREET)                | 64.0    | NE        | 3438  | 45.4     |           |
      | 15            | 50.725108,-3.52286  | At roundabout, take exit 1 onto PARIS STREET (B3183) | 321.0   | NW        | 24607 | 319.7    |           |

    Examples: 
      | vehicleType | avoidances | routeType |
      | car         |            | fastest   |

  @Routing
  Scenario Outline: Verify  Route using (2 intermediate waypoints)
    Given I have route point as
      | pointA              | pointB              | pointC              | pointD            |
      | 50.722314,-3.539121 | 50.721886,-3.527393 | 50.727346,-3.529876 | 50.72373,-3.51756 |
    And I have vehicle as "<vehicleType>"
    And I have avoidances as "<avoidances>"
    And I have weighting as "<routeType>"
    When I request for a route
    Then I should be able to verify the waypoints on the route map:
      | wayPointIndex | waypointco          | waypointdesc                   | azimuth | direction | time  | distance | avoidance |
      | 5             | 50.719971,-3.529196 | Continue onto A3015            | 65.0    | NE        | 1466  | 18.3     |           |
      | 10            | 50.721881,-3.52737  | Continue onto SOUTHERNHAY WEST | 19.0    | N         | 3779  | 47.2     |           |
      | 21            | 50.726462,-3.523882 | Continue onto CHEEKE STREET    | 128.0   | SE        | 10960 | 139.9    |           |

    Examples: 
      | vehicleType | avoidances | routeType |
      | car         |            | fastest   |

  @Routing
  Scenario Outline: Verify  Route using 2 intermediate waypoints via (Western way and Paris Street)
    Given I have route point as
      | pointA              | pointB              | pointC              | pointD            |
      | 50.721075,-3.519606 | 50.724423,-3.519888 | 50.727505,-3.529782 | 50.72373,-3.51756 |
    And I have vehicle as "<vehicleType>"
    And I have avoidances as "<avoidances>"
    And I have weighting as "<routeType>"
    When I request for a route
    Then I should be able to verify the waypoints on the route map:
      | wayPointIndex | waypointco          | waypointdesc                                         | azimuth | direction | time  | distance | avoidance |
      | 1             | 50.721075,-3.519573 | Continue onto ST LEONARD'S ROAD                      | 1.0     | N         | 421   | 5.3      |           |
      | 6             | 50.725096,-3.522378 | At roundabout, take exit 2 onto PARIS STREET (B3183) | 239.0   | SW        | 27224 | 356.1    |           |
      | 10            | 50.725549,-3.52693  | Turn sharp left onto SIDWELL STREET                  | 47.0    | NE        | 17774 | 226.1    |           |

    Examples: 
      | vehicleType | avoidances | routeType |
      | car         |            | fastest   |

  @Routing
  Scenario Outline: Verify  Route using 3 intermediate waypoints via (Streathem drive ,Prince of wales Rd and Clifton Rd)
    Given I have route point as
      | pointA              | pointB             | pointC              | pointD              | pointE              |
      | 50.718552,-3.518781 | 50.72703,-3.517964 | 50.733328,-3.524962 | 50.732093,-3.535139 | 50.732981,-3.541765 |
    And I have vehicle as "<vehicleType>"
    And I have avoidances as "<avoidances>"
    And I have weighting as "<routeType>"
    When I request for a route
    Then I should be able to verify the waypoints on the route map:
      | wayPointIndex | waypointco          | waypointdesc                                      | azimuth | direction | time  | distance | avoidance |
      | 5             | 50.724453,-3.519949 | Turn right onto CLIFTON ROAD                      | 19.0    | N         | 28231 | 352.9    |           |
      | 10            | 50.728509,-3.520647 | At roundabout, take exit 2 onto OLD TIVERTON ROAD | 282.0   | W         | 11187 | 150.3    |           |
      | 17            | 50.733333,-3.524999 | Continue onto PENNSYLVANIA ROAD                   | 12.0    | N         | 764   | 9.6      |           |
      | 20            | 50.732212,-3.535235 | Continue onto PRINCE OF WALES ROAD                | 252.0   | W         | 15641 | 195.5    |           |

    Examples: 
      | vehicleType | avoidances | routeType |
      | car         |            | fastest   |

  @Routing
  Scenario Outline: Verify  Route using (10 intermediate waypoints)
    Given I have route point as
      | pointA              | pointB              | pointC              | pointD              | pointE              | pointF              | pointG              | pointH              | pointI            | pointJ              |
      | 50.729961,-3.524853 | 50.723364,-3.523895 | 50.719078,-3.541011 | 50.720275,-3.526888 | 50.719826,-3.529631 | 50.729774,-3.519937 | 50.734471,-3.516965 | 50.732477,-3.517843 | 50.727248,-3.5205 | 50.719852,-3.544358 |
    And I have vehicle as "<vehicleType>"
    And I have avoidances as "<avoidances>"
    And I have weighting as "<routeType>"
    When I request for a route
    Then I should be able to verify the waypoints on the route map:
      | wayPointIndex | waypointco          | waypointdesc                                        | azimuth | direction | time  | distance | avoidance |
      | 1             | 50.729734,-3.52464  | Continue onto ST SIDWELL'S AVENUE                   | 119.0   | SE        | 9351  | 116.9    |           |
      | 8             | 50.723365,-3.523902 | Continue onto B3212 (WESTERN WAY)                   | 195.0   | S         | 21140 | 272.8    |           |
      | 16            | 50.719224,-3.540839 | Continue onto OKEHAMPTON STREET                     | 126.0   | SE        | 17995 | 228.1    |           |
      | 23            | 50.7203,-3.526902   | Continue onto B3212 (MAGDALEN STREET)               | 250.0   | W         | 58    | 0.7      |           |
      | 30            | 50.719848,-3.529643 | Continue onto WESTERN WAY (A3015)                   | 68.0    | E         | 1997  | 34.4     |           |
      | 37            | 50.729774,-3.519937 | Continue onto OLD TIVERTON ROAD                     | 35.0    | NE        | 38268 | 478.4    |           |
      | 42            | 50.729023,-3.520593 | At roundabout, take exit 2 onto B3212 (WESTERN WAY) | 113.0   | SE        | 15894 | 209.9    |           |
      | 50            | 50.718282,-3.535898 | Continue onto A377                                  | 207.0   | SW        | 12712 | 216.9    |           |

    Examples: 
      | vehicleType | avoidances | routeType |
      | car         |            | fastest   |

  # We have modified HELE ROAD ,ELM GROVE ROAD into private roads(publically accecible)
  # Preston Road as private restricted
  # Perry Road (Private Road- Publicly accessible)
  @Current
  Scenario Outline: Verify  a route for emergencey services on a (Transit through PERRY ROAD)
    Given I have route point as
      | pointA              | pointB              |
      | 50.732343,-3.534682 | 50.733856,-3.538043 |
    And I have vehicle as "<vehicleType>"
    And I have avoidances as "<avoidances>"
    And I have weighting as "<routeType>"
    And I have private as "true"
    When I request for a route
    Then I should be able to verify the waypoints on the route map:
      | wayPointIndex | waypointco          | waypointdesc               | azimuth | direction | time  | distance | avoidance |
      | 2             | 50.732222,-3.535207 | Turn right onto PERRY ROAD | 309.0   | NW        | 18936 | 226.2    |           |

    Examples: 
      | vehicleType | avoidances | routeType |
      | emv         |            | fastest   |

  @Current
  Scenario Outline: Verify  a route for emergencey services on a (Start in PERRY ROAD)
    Given I have route point as
      | pointA              | pointB              |
      | 50.732275,-3.535375 | 50.733704,-3.537969 |
    And I have vehicle as "<vehicleType>"
    And I have avoidances as "<avoidances>"
    And I have weighting as "<routeType>"
    And I have private as "true"
    When I request for a route
    Then I should be able to verify the waypoints on the route map:
      | wayPointIndex | waypointco          | waypointdesc             | azimuth | direction | time  | distance | avoidance |
      | 1             | 50.732296,-3.535347 | Continue onto PERRY ROAD | 309.0   | NW        | 17852 | 213.2    |           |

    Examples: 
      | vehicleType | avoidances | routeType |
      | emv         |            | fastest   |

  @Current
  Scenario Outline: Verify  a route for emergencey services on a (End in PERRY ROAD)
    Given I have route point as
      | pointA              | pointB              |
      | 50.732343,-3.534682 | 50.733464,-3.537391 |
    And I have vehicle as "<vehicleType>"
    And I have avoidances as "<avoidances>"
    And I have weighting as "<routeType>"
    And I have private as "true"
    When I request for a route
    Then I should be able to verify the waypoints on the route map:
      | wayPointIndex | waypointco          | waypointdesc               | azimuth | direction | time  | distance | avoidance |
      | 2             | 50.732222,-3.535207 | Turn right onto PERRY ROAD | 309.0   | NW        | 17354 | 207.3    |           |

    Examples: 
      | vehicleType | avoidances | routeType |
      | emv         |            | fastest   |

  # Perry Road (Private Road- Publicly accessible)
  @Current
  Scenario Outline: Verify  a route for non-emergencey vehicle on a (Not to transit through PERRY ROAD-Private(True))
    Given I have route point as
      | pointA              | pointB              |
      | 50.732343,-3.534682 | 50.733856,-3.538043 |
    And I have vehicle as "<vehicleType>"
    And I have avoidances as "<avoidances>"
    And I have weighting as "<routeType>"
    And I have private as "true"
    When I request for a route
    Then I should be able to verify the waypoints on the route map:
      | wayPointIndex | waypointco          | waypointdesc                       | azimuth | direction | time  | distance | avoidance |
      | 1             | 50.732369,-3.534702 | Continue onto PRINCE OF WALES ROAD | 245.0   | SW        | 19838 | 237.0    |           |

    Examples: 
      | vehicleType | avoidances | routeType |
      | car         |            | fastest   |

  @Current
  Scenario Outline: Verify  a route for non-emergencey vehicle on a (Not to transit through PERRY ROAD-Private(False))
    Given I have route point as
      | pointA              | pointB              |
      | 50.732343,-3.534682 | 50.733856,-3.538043 |
    And I have vehicle as "<vehicleType>"
    And I have avoidances as "<avoidances>"
    And I have weighting as "<routeType>"
    And I have private as "false"
    When I request for a route
    Then I should be able to verify the waypoints on the route map:
      | wayPointIndex | waypointco         | waypointdesc                    | azimuth | direction | time  | distance | avoidance |
      | 2             | 50.732011,-3.53798 | Turn right onto STREATHAM DRIVE | 2.0     | N         | 17987 | 214.9    |           |

    Examples: 
      | vehicleType | avoidances | routeType |
      | car         |            | fastest   |

  @Current
  Scenario Outline: Verify  a route for non-emergencey vehicle on a (Start on PERRY ROAD-Private(True))
    Given I have route point as
      | pointA             | pointB              |
      | 50.73244,-3.535622 | 50.733856,-3.538043 |
    And I have vehicle as "<vehicleType>"
    And I have avoidances as "<avoidances>"
    And I have weighting as "<routeType>"
    And I have private as "true"
    When I request for a route
    Then I should be able to verify the waypoints on the route map:
      | wayPointIndex | waypointco         | waypointdesc             | azimuth | direction | time  | distance | avoidance |
      | 1             | 50.732441,-3.53562 | Continue onto PERRY ROAD | 309.0   | NW        | 15749 | 188.1    |           |

    Examples: 
      | vehicleType | avoidances | routeType |
      | car         |            | fastest   |

  @Current
  Scenario Outline: Verify  a route for non-emergencey vehicle on a (Start on PERRY ROAD-Private(False))
    Given I have route point as
      | pointA             | pointB              |
      | 50.73244,-3.535622 | 50.733856,-3.538043 |
    And I have vehicle as "<vehicleType>"
    And I have avoidances as "<avoidances>"
    And I have weighting as "<routeType>"
    And I have private as "false"
    When I request for a route
    Then I should be able to verify the http response message as "Not Found"
    Then I should be able to verify the http statuscode as "404"

    Examples: 
      | vehicleType | avoidances | routeType |
      | car         |            | fastest   |

  @Current
  Scenario Outline: Verify  a route for non-emergencey vehicle on a (End on PERRY ROAD-Private(True))
    Given I have route point as
      | pointA              | pointB              |
      | 50.732343,-3.534682 | 50.733335,-3.537294 |
    And I have vehicle as "<vehicleType>"
    And I have avoidances as "<avoidances>"
    And I have weighting as "<routeType>"
    And I have private as "true"
    When I request for a route
    Then I should be able to verify the waypoints on the route map:
      | wayPointIndex | waypointco          | waypointdesc               | azimuth | direction | time  | distance | avoidance |
      | 2             | 50.732222,-3.535207 | Turn right onto PERRY ROAD | 309.0   | NW        | 16062 | 191.9    |           |

    Examples: 
      | vehicleType | avoidances | routeType |
      | car         |            | fastest   |

  @Current
  Scenario Outline: Verify  a route for non-emergencey vehicle on a (End on PERRY ROAD-Private(False))
    Given I have route point as
      | pointA              | pointB              |
      | 50.732343,-3.534682 | 50.733335,-3.537294 |
    And I have vehicle as "<vehicleType>"
    And I have avoidances as "<avoidances>"
    And I have weighting as "<routeType>"
    And I have private as "false"
    When I request for a route
    Then I should be able to verify the http response message as "Not Found"
    Then I should be able to verify the http statuscode as "404"

    Examples: 
      | vehicleType | avoidances | routeType |
      | car         |            | fastest   |

  # Oxford Road (Private Road(Restricted)
  @Current
  Scenario Outline: Verify  a route for emergencey vehicle on Oxford Road ( Not to transit through Oxford Road)
    Given I have route point as
      | pointA              | pointB              |
      | 50.730031,-3.521297 | 50.727926,-3.523429 |
    And I have vehicle as "<vehicleType>"
    And I have avoidances as "<avoidances>"
    And I have weighting as "<routeType>"
    And I have private as "false"
    When I request for a route
    Then I should be able to verify the waypoints on the route map:
      | wayPointIndex | waypointco         | waypointdesc                     | azimuth | direction | time  | distance | avoidance |
      | 2             | 50.73051,-3.522412 | Turn sharp left onto WELL STREET | 197.0   | S         | 22511 | 268.9    |           |

    Examples: 
      | vehicleType | avoidances | routeType |
      | emv         |            | fastest   |

  @Current
  Scenario Outline: Verify  a route for emergencey vehicle on Oxford Road ( Start on  Oxford Road)
    Given I have route point as
      | pointA              | pointB              |
      | 50.730188,-3.521674 | 50.727926,-3.523429 |
    And I have vehicle as "<vehicleType>"
    And I have avoidances as "<avoidances>"
    And I have weighting as "<routeType>"
    And I have private as "true"
    When I request for a route
    Then I should be able to verify the waypoints on the route map:
      | wayPointIndex | waypointco          | waypointdesc              | azimuth | direction | time  | distance | avoidance |
      | 1             | 50.730007,-3.521796 | Continue onto OXFORD ROAD | 210.0   | SW        | 20567 | 245.7    |           |

    Examples: 
      | vehicleType | avoidances | routeType |
      | emv         |            | fastest   |

  Scenario Outline: Verify  a route for emergencey vehicle on Oxford Road ( End on Oxford Road )
    Given I have route point as
      | pointA              | pointB              |
      | 50.730031,-3.521297 | 50.728449,-3.523446 |
    And I have vehicle as "<vehicleType>"
    And I have avoidances as "<avoidances>"
    And I have weighting as "<routeType>"
    And I have private as "false"
    When I request for a route
    Then I should be able to verify the waypoints on the route map:
      | wayPointIndex | waypointco          | waypointdesc               | azimuth | direction | time  | distance | avoidance |
      | 2             | 50.730179,-3.521622 | Turn left onto OXFORD ROAD | 211.0   | SW        | 19386 | 231.6    |           |

    Examples: 
      | vehicleType | avoidances | routeType |
      | emv         |            | fastest   |

  @Current
  Scenario Outline: Verify  a route for non-emergencey vehicle on Oxford Road ( Not to transit through Oxford Road -Private(true)
    Given I have route point as
      | pointA              | pointB              |
      | 50.730031,-3.521297 | 50.727926,-3.523429 |
    And I have vehicle as "<vehicleType>"
    And I have avoidances as "<avoidances>"
    And I have weighting as "<routeType>"
    And I have private as "true"
    When I request for a route
    Then I should be able to verify the waypoints on the route map:
      | wayPointIndex | waypointco         | waypointdesc                     | azimuth | direction | time  | distance | avoidance |
      | 2             | 50.73051,-3.522412 | Turn sharp left onto WELL STREET | 197.0   | S         | 22511 | 268.9    |           |

    Examples: 
      | vehicleType | avoidances | routeType |
      | car         |            | fastest   |

  @Current
  Scenario Outline: Verify  a route for non-emergencey vehicle on Oxford Road ( Not to transit through Oxford Road - Private(false)
    Given I have route point as
      | pointA              | pointB              |
      | 50.730031,-3.521297 | 50.727926,-3.523429 |
    And I have vehicle as "<vehicleType>"
    And I have avoidances as "<avoidances>"
    And I have weighting as "<routeType>"
    And I have private as "false"
    When I request for a route
    Then I should be able to verify the waypoints on the route map:
      | wayPointIndex | waypointco         | waypointdesc                     | azimuth | direction | time  | distance | avoidance |
      | 2             | 50.73051,-3.522412 | Turn sharp left onto WELL STREET | 197.0   | S         | 22511 | 268.9    |           |

    Examples: 
      | vehicleType | avoidances | routeType |
      | car         |            | fastest   |

  @Current
  Scenario Outline: Verify  a route for emergencey vehicle on Oxford Road ( Start on  Oxford Road- Private(true))
    Given I have route point as
      | pointA              | pointB              |
      | 50.730188,-3.521674 | 50.727926,-3.523429 |
    And I have vehicle as "<vehicleType>"
    And I have avoidances as "<avoidances>"
    And I have weighting as "<routeType>"
    And I have private as "true"
    When I request for a route
    Then I should be able to verify the http response message as "Not Found"
    Then I should be able to verify the http statuscode as "404"

    Examples: 
      | vehicleType | avoidances | routeType |
      | car         |            | fastest   |

  @Current
  Scenario Outline: Verify  a route for non-emergencey vehicle on Oxford Road ( Start on  Oxford Road-Private (false))
    Given I have route point as
      | pointA              | pointB              |
      | 50.730188,-3.521674 | 50.727926,-3.523429 |
    And I have vehicle as "<vehicleType>"
    And I have avoidances as "<avoidances>"
    And I have weighting as "<routeType>"
    And I have private as "false"
    When I request for a route
    Then I should be able to verify the http response message as "Not Found"
    Then I should be able to verify the http statuscode as "404"

    Examples: 
      | vehicleType | avoidances | routeType |
      | car         |            | fastest   |

  @Current
  Scenario Outline: Verify  a route for non-emergencey vehicle on Oxford Road ( End on Oxford Road -Private(true))
    Given I have route point as
      | pointA              | pointB              |
      | 50.730031,-3.521297 | 50.728449,-3.523446 |
    And I have vehicle as "<vehicleType>"
    And I have avoidances as "<avoidances>"
    And I have weighting as "<routeType>"
    And I have private as "true"
    When I request for a route
    Then I should be able to verify the http response message as "Not Found"
    Then I should be able to verify the http statuscode as "404"

    Examples: 
      | vehicleType | avoidances | routeType |
      | car         |            | fastest   |

  @Current
  Scenario Outline: Verify  a route for non-emergencey vehicle on Oxford Road ( End on Oxford Road -Private(false))
    Given I have route point as
      | pointA              | pointB              |
      | 50.730031,-3.521297 | 50.728449,-3.523446 |
    And I have vehicle as "<vehicleType>"
    And I have avoidances as "<avoidances>"
    And I have weighting as "<routeType>"
    And I have private as "false"
    When I request for a route
    Then I should be able to verify the http response message as "Not Found"
    Then I should be able to verify the http statuscode as "404"

    Examples: 
      | vehicleType | avoidances | routeType |
      | car         |            | fastest   |

  # Hele road (Private Road- Publicly accessible)
  @Current
  Scenario Outline: Verify  a route for emergencey services on a (Transit through Hele road)
    Given I have route point as
      | pointA              | pointB              |
      | 50.727596,-3.535984 | 50.727339,-3.539639 |
    And I have vehicle as "<vehicleType>"
    And I have avoidances as "<avoidances>"
    And I have weighting as "<routeType>"
    And I have private as "true"
    When I request for a route
    Then I should be able to verify the waypoints on the route map:
      | wayPointIndex | waypointco         | waypointdesc            | azimuth | direction | time  | distance | avoidance |
      | 3             | 50.72778,-3.537866 | Continue onto HELE ROAD | 277.0   | W         | 12852 | 153.5    |           |

    Examples: 
      | vehicleType | avoidances | routeType |
      | emv         |            | fastest   |

  @Current
  Scenario Outline: Verify  a route for emergencey services on a (Start on  Hele road)
    Given I have route point as
      | pointA              | pointB              |
      | 50.727811,-3.537274 | 50.727339,-3.539639 |
    And I have vehicle as "<vehicleType>"
    And I have avoidances as "<avoidances>"
    And I have weighting as "<routeType>"
    And I have private as "true"
    When I request for a route
    Then I should be able to verify the waypoints on the route map:
      | wayPointIndex | waypointco         | waypointdesc            | azimuth | direction | time  | distance | avoidance |
      | 2             | 50.72778,-3.537866 | Continue onto HELE ROAD | 277.0   | W         | 12852 | 153.5    |           |

    Examples: 
      | vehicleType | avoidances | routeType |
      | emv         |            | fastest   |

  @Current
  Scenario Outline: Verify  a route for emergencey services on a (End on  Hele road)
    Given I have route point as
      | pointA              | pointB              |
      | 50.727596,-3.535984 | 50.727801,-3.539509 |
    And I have vehicle as "<vehicleType>"
    And I have avoidances as "<avoidances>"
    And I have weighting as "<routeType>"
    And I have private as "true"
    When I request for a route
    Then I should be able to verify the waypoints on the route map:
      | wayPointIndex | waypointco         | waypointdesc            | azimuth | direction | time | distance | avoidance |
      | 3             | 50.72778,-3.537866 | Continue onto HELE ROAD | 277.0   | W         | 9769 | 116.7    |           |

    Examples: 
      | vehicleType | avoidances | routeType |
      | emv         |            | fastest   |

  @Current
  Scenario Outline: Verify  a route for non-emergencey services on a (not to transit through Hele road- Private(true))
    Given I have route point as
      | pointA              | pointB              |
      | 50.727596,-3.535984 | 50.727339,-3.539639 |
    And I have vehicle as "<vehicleType>"
    And I have avoidances as "<avoidances>"
    And I have weighting as "<routeType>"
    And I have private as "true"
    When I request for a route
    Then I should be able to verify the waypoints on the route map:
      | wayPointIndex | waypointco        | waypointdesc               | azimuth | direction | time  | distance | avoidance |
      | 2             | 50.72956,-3.53876 | Turn left onto HOWELL ROAD | 261.0   | W         | 12585 | 150.3    |           |

    Examples: 
      | vehicleType | avoidances | routeType |
      | car         |            | fastest   |

  @Current
  Scenario Outline: Verify  a route for non-emergencey services on a (not to transit through Hele road- Private(false))
    Given I have route point as
      | pointA              | pointB              |
      | 50.727596,-3.535984 | 50.727339,-3.539639 |
    And I have vehicle as "<vehicleType>"
    And I have avoidances as "<avoidances>"
    And I have weighting as "<routeType>"
    And I have private as "false"
    When I request for a route
    Then I should be able to verify the waypoints on the route map:
      | wayPointIndex | waypointco        | waypointdesc               | azimuth | direction | time  | distance | avoidance |
      | 2             | 50.72956,-3.53876 | Turn left onto HOWELL ROAD | 261.0   | W         | 12585 | 150.3    |           |

    Examples: 
      | vehicleType | avoidances | routeType |
      | car         |            | fastest   |

  @Current
  Scenario Outline: Verify  a route for non-emergencey services on a (Start on  Hele road-private(true))
    Given I have route point as
      | pointA              | pointB              |
      | 50.727784,-3.538141 | 50.727339,-3.539639 |
    And I have vehicle as "<vehicleType>"
    And I have avoidances as "<avoidances>"
    And I have weighting as "<routeType>"
    And I have private as "true"
    When I request for a route
    Then I should be able to verify the waypoints on the route map:
      | wayPointIndex | waypointco          | waypointdesc            | azimuth | direction | time  | distance | avoidance |
      | 1             | 50.727799,-3.538138 | Continue onto HELE ROAD | 277.0   | W         | 11237 | 134.2    |           |

    Examples: 
      | vehicleType | avoidances | routeType |
      | car         |            | fastest   |

  @Current
  Scenario Outline: Verify  a route for non-emergencey services on a (Start on  Hele road-private(false))
    Given I have route point as
      | pointA              | pointB              |
      | 50.727784,-3.538141 | 50.727339,-3.539639 |
    And I have vehicle as "<vehicleType>"
    And I have avoidances as "<avoidances>"
    And I have weighting as "<routeType>"
    And I have private as "false"
    When I request for a route
    Then I should be able to verify the http response message as "Not Found"
    Then I should be able to verify the http statuscode as "404"

    Examples: 
      | vehicleType | avoidances | routeType |
      | car         |            | fastest   |

  @Current
  Scenario Outline: Verify  a route for non-emergencey services on a (End on  Hele road-private(true))
    Given I have route point as
      | pointA              | pointB              |
      | 50.727596,-3.535984 | 50.727801,-3.539509 |
    And I have vehicle as "<vehicleType>"
    And I have avoidances as "<avoidances>"
    And I have weighting as "<routeType>"
    And I have private as "true"
    When I request for a route
    Then I should be able to verify the waypoints on the route map:
      | wayPointIndex | waypointco         | waypointdesc            | azimuth | direction | time | distance | avoidance |
      | 3             | 50.72778,-3.537866 | Continue onto HELE ROAD | 277.0   | W         | 9769 | 116.7    |           |

    Examples: 
      | vehicleType | avoidances | routeType |
      | car         |            | fastest   |

  @Current
  Scenario Outline: Verify  a route for non-emergencey services on a (End on  Hele road-private(false))
    Given I have route point as
      | pointA              | pointB              |
      | 50.727596,-3.535984 | 50.727801,-3.539509 |
    And I have vehicle as "<vehicleType>"
    And I have avoidances as "<avoidances>"
    And I have weighting as "<routeType>"
    And I have private as "false"
    When I request for a route
    Then I should be able to verify the http response message as "Not Found"
    Then I should be able to verify the http statuscode as "404"

    Examples: 
      | vehicleType | avoidances | routeType |
      | car         |            | fastest   |

  # West avenue (private road - private ristricted)
  @Current
  Scenario Outline: Verify  a route for emergencey services on a( not to transit through West Avenue)
    Given I have route point as
      | pointA             | pointB              |
      | 50.73197,-3.528874 | 50.731175,-3.524744 |
    And I have vehicle as "<vehicleType>"
    And I have avoidances as "<avoidances>"
    And I have weighting as "<routeType>"
    And I have private as "true"
    When I request for a route
    Then I should be able to verify the waypoints on the route map:
      | wayPointIndex | waypointco         | waypointdesc                     | azimuth | direction | time  | distance | avoidance |
      | 3             | 50.729609,-3.52599 | Turn left onto PENNSYLVANIA ROAD | 13.0    | N         | 15692 | 187.5    |           |

    Examples: 
      | vehicleType | avoidances | routeType |
      | emv         |            | fastest   |

  @Current
  Scenario Outline: Verify  a route for non-emergencey services on a(Not to transit through West Avenue-private(true))
    Given I have route point as
      | pointA             | pointB              |
      | 50.73197,-3.528874 | 50.731175,-3.524744 |
    And I have vehicle as "<vehicleType>"
    And I have avoidances as "<avoidances>"
    And I have weighting as "<routeType>"
    And I have private as "true"
    When I request for a route
    Then I should be able to verify the waypoints on the route map:
      | wayPointIndex | waypointco         | waypointdesc                     | azimuth | direction | time  | distance | avoidance |
      | 3             | 50.729609,-3.52599 | Turn left onto PENNSYLVANIA ROAD | 13.0    | N         | 15692 | 187.5    |           |

    Examples: 
      | vehicleType | avoidances | routeType |
      | car         |            | fastest   |

  @Current
  Scenario Outline: Verify  a route for non-emergencey services on a ( Start on  West Avenue-private(false))
    Given I have route point as
      | pointA              | pointB             |
      | 50.731026,-3.525898 | 50.73192,-3.528785 |
    And I have vehicle as "<vehicleType>"
    And I have avoidances as "<avoidances>"
    And I have weighting as "<routeType>"
    And I have private as "false"
    When I request for a route
    Then I should be able to verify the http response message as "Not Found"
    Then I should be able to verify the http statuscode as "404"

    Examples: 
      | vehicleType | avoidances | routeType |
      | car         |            | fastest   |

  # Clinton Street (Private Road- Publicly accessible)
  @Current
  Scenario Outline: Verify  a route for emergencey services on a( Transit through -Clinton Street)
    Given I have route point as
      | pointA              | pointB              |
      | 50.717791,-3.543885 | 50.718544,-3.539735 |
    And I have vehicle as "<vehicleType>"
    And I have avoidances as "<avoidances>"
    And I have weighting as "<routeType>"
    And I have private as "true"
    When I request for a route
    Then I should be able to verify the waypoints on the route map:%2C
      | wayPointIndex | waypointco         | waypointdesc                  | azimuth | direction | time | distance | avoidance |
      | 3             | 50.717445,-3.54209 | Turn left onto CLINTON STREET | 53.0    | NE        | 9560 | 114.2    |           |

    Examples: 
      | vehicleType | avoidances | routeType |
      | emv         |            | fastest   |

  @Current
  Scenario Outline: Verify  a route for non-emergencey services on a(Not to transit through Clinton road-private(true))
    Given I have route point as
      | pointA              | pointB              |
      | 50.717791,-3.543885 | 50.718544,-3.539735 |
    And I have vehicle as "<vehicleType>"
    And I have avoidances as "<avoidances>"
    And I have weighting as "<routeType>"
    And I have private as "true"
    When I request for a route
    Then I should be able to verify the waypoints on the route map:
      | wayPointIndex | waypointco          | waypointdesc                      | azimuth | direction | time | distance | avoidance |
      | 2             | 50.718195,-3.543191 | Turn slight left onto BULLER ROAD | 8.0     | N         | 3253 | 38.9     |           |

    Examples: 
      | vehicleType | avoidances | routeType |
      | car         |            | fastest   |

  Scenario Outline: Verify  a route for non-emergencey services on a(End on Clinton road-private(false))
    Given I have route point as
      | pointA              | pointB              |
      | 50.717791,-3.543885 | 50.717927,-3.541066 |
    And I have vehicle as "<vehicleType>"
    And I have avoidances as "<avoidances>"
    And I have weighting as "<routeType>"
    And I have private as "false"
    When I request for a route
    Then I should be able to verify the http response message as "Not Found"
    Then I should be able to verify the http statuscode as "404"

    Examples: 
      | vehicleType | avoidances | routeType |
      | car         |            | fastest   |

  # Springfield road Private restricted road
  @Current
  Scenario Outline: Verify  a route for emergencey services on a( Not to transit through -Springfield road)
    Given I have route point as
      | pointA              | pointB              |
      | 50.733653,-3.523541 | 50.732695,-3.520218 |
    And I have vehicle as "<vehicleType>"
    And I have avoidances as "<avoidances>"
    And I have weighting as "<routeType>"
    And I have private as "true"
    When I request for a route
    Then I should be able to verify the waypoints on the route map:
      | wayPointIndex | waypointco          | waypointdesc                    | azimuth | direction | time  | distance | avoidance |
      | 2             | 50.733764,-3.523212 | Turn right onto VICTORIA STREET | 213.6   | SE        | 17884 | 213.6    |           |

    Examples: 
      | vehicleType | avoidances | routeType |
      | emv         |            | fastest   |

  @Current
  Scenario Outline: Verify  a route for non-emergencey services on a(Transit through Springfield-private(true))
    Given I have route point as
      | pointA              | pointB              |
      | 50.733653,-3.523541 | 50.732695,-3.520218 |
    And I have vehicle as "<vehicleType>"
    And I have avoidances as "<avoidances>"
    And I have weighting as "<routeType>"
    And I have private as "true"
    When I request for a route
    Then I should be able to verify the waypoints on the route map:
      | wayPointIndex | waypointco          | waypointdesc                     | azimuth | direction | time  | distance | avoidance |
      | 2             | 50.733973,-3.522397 | Turn right onto SPRINGFIELD ROAD | 158.0   | S         | 13219 | 157.9    |           |

    Examples: 
      | vehicleType | avoidances | routeType |
      | car         |            | fastest   |

  @Current
  Scenario Outline: Verify  a route for non-emergencey services on a(Not to transit through Springfield-private(false))
    Given I have route point as
      | pointA              | pointB              |
      | 50.733653,-3.523541 | 50.732695,-3.520218 |
    And I have vehicle as "<vehicleType>"
    And I have avoidances as "<avoidances>"
    And I have weighting as "<routeType>"
    And I have private as "false"
    Then I should be able to verify the http response message as "Not Found"
    Then I should be able to verify the http statuscode as "404"

    Examples: 
      | vehicleType | avoidances | routeType |
      | car         |            | fastest   |
