Feature: Verify a route from A to B
    As a user
    I want to get a route from location A to location B by Foot using the routing service
    And route should be the fastest route and contain the waypoints,restrictions,time and other instructions

  @Routing
  Scenario Outline: Verify  Road Names on a Walking Route  (Mill lane-BUXTON)
    Given I have route point as
      | pointA              | pointB             |
      | 53.176062,-1.871472 | 53.154773,-1.77272 |
    And I have vehicle as "<vehicleType>"
    And I have avoidances as "<avoidances>"
    And I have weighting as "<routeType>"
    When I request for a route
    Then I should be able to verify the waypoints on the route map:
      | wayPointIndex | waypointco        | waypointdesc            | azimuth | direction | time  | distance | avoidance |
      | 4             | 53.1356,-1.820891 | Continue onto Mill Lane | 70.0    | E         | 23171 | 32.2     |           |

    Examples: 
      | vehicleType | avoidances | routeType |
      | foot        |            |           |

  @Routing
  Scenario Outline: Verify  Road Names on a Walking Route  (Chatswoth Park)
    Given I have route point as
      | pointA              | pointB              |
      | 53.211013,-1.619393 | 53.185757,-1.611969 |
    And I have vehicle as "<vehicleType>"
    And I have avoidances as "<avoidances>"
    And I have weighting as "<routeType>"
    When I request for a route
    Then I should be able to verify the waypoints on the route map:
      | wayPointIndex | waypointco          | waypointdesc                  | azimuth | direction | time   | distance | avoidance |
      | 2             | 53.221055,-1.623152 | Turn right onto B Road        | 157.0   | SE        | 984273 | 1367.1   |           |
      | 5             | 53.197269,-1.608797 | Continue onto Chatsworth Road | 181.0   | S         | 678871 | 942.9    |           |

    Examples: 
      | vehicleType | avoidances | routeType |
      | foot        |            |           |

  @Routing
  Scenario Outline: Verify  Road Names on a Walking Route (Musden Low)
    Given I have route point as
      | pointA              | pointB              |
      | 53.049589,-1.823866 | 53.076372,-1.853379 |
    And I have vehicle as "<vehicleType>"
    And I have avoidances as "<avoidances>"
    And I have weighting as "<routeType>"
    When I request for a route
    Then I should be able to verify the waypoints on the route map:
      | wayPointIndex | waypointco          | waypointdesc          | azimuth | direction | time  | distance | avoidance |
      | 3             | 53.042479,-1.820522 | Turn right onto Route | 297.0   | NW        | 35181 | 48.9     |           |

    Examples: 
      | vehicleType | avoidances | routeType |
      | foot        |            |           |

  @Routing
  Scenario Outline: Verify  Road Names on a Walking Route (A54)
    Given I have route point as
      | pointA              | pointB              |
      | 53.173064,-2.060321 | 53.214387,-2.017271 |
    And I have vehicle as "<vehicleType>"
    And I have avoidances as "<avoidances>"
    And I have weighting as "<routeType>"
    When I request for a route
    Then I should be able to verify the waypoints on the route map:
      | wayPointIndex | waypointco          | waypointdesc         | azimuth | direction | time   | distance | avoidance |
      | 4             | 53.176842,-2.069334 | Turn left onto Track | 255.0   | W         | 187602 | 260.6    |           |

    Examples: 
      | vehicleType | avoidances | routeType |
      | foot        |            |           |

  @Routing
  Scenario Outline: Verify  Road Names on a Walking Route (Townhead )
    Given I have route point as
      | pointA              | pointB             |
      | 53.122676,-1.909914 | 53.088159,-1.87142 |
    And I have vehicle as "<vehicleType>"
    And I have avoidances as "<avoidances>"
    And I have weighting as "<routeType>"
    When I request for a route
    Then I should be able to verify the waypoints on the route map:
      | wayPointIndex | waypointco         | waypointdesc                | azimuth | direction | time  | distance | avoidance |
      | 5             | 53.11862,-1.909506 | Turn slight left onto Route | 169.0   | S         | 51007 | 70.8     |           |

    Examples: 
      | vehicleType | avoidances | routeType |
      | foot        |            |           |

  @Routing
  Scenario Outline: Verify  Road Names on a Walking Route (Martin's Low)
    Given I have route point as
      | pointA             | pointB              |
      | 53.06535,-1.906169 | 53.100994,-1.956274 |
    And I have vehicle as "<vehicleType>"
    And I have avoidances as "<avoidances>"
    And I have weighting as "<routeType>"
    When I request for a route
    Then I should be able to verify the waypoints on the route map:
      | wayPointIndex | waypointco          | waypointdesc         | azimuth | direction | time   | distance | avoidance |
      | 3             | 53.071624,-1.914417 | Turn right onto Path | 356.0   | N         | 425697 | 591.2    |           |

    Examples: 
      | vehicleType | avoidances | routeType |
      | foot        |            |           |

  @Routing
  Scenario Outline: Verify  Road Names on a Walking Route (Castleton Road)
    Given I have route point as
      | pointA              | pointB              |
      | 53.348832,-1.761122 | 53.197338,-1.594157 |
    And I have vehicle as "<vehicleType>"
    And I have avoidances as "<avoidances>"
    And I have weighting as "<routeType>"
    When I request for a route
    Then I should be able to verify the waypoints on the route map:
      | wayPointIndex | waypointco          | waypointdesc                  | azimuth | direction | time   | distance | avoidance |
      | 2             | 53.347406,-1.760973 | Turn left onto Castleton Road | 109.0   | E         | 878424 | 1220.0   | ARoad     |

    Examples: 
      | vehicleType | avoidances | routeType |
      | foot        |            |           |

  @Routing
  Scenario Outline: Verify  Road Names on a Walking Route (Hernstone Lane )
    Given I have route point as
      | pointA              | pointB              |
      | 53.300714,-1.786126 | 53.287803,-1.816746 |
    And I have vehicle as "<vehicleType>"
    And I have avoidances as "<avoidances>"
    And I have weighting as "<routeType>"
    When I request for a route
    Then I should be able to verify the waypoints on the route map:
      | wayPointIndex | waypointco          | waypointdesc                 | azimuth | direction | time   | distance | avoidance |
      | 3             | 53.305821,-1.814508 | Continue onto Hernstone Lane | 299.0   | NW        | 304923 | 423.5    | ARoad     |

    Examples: 
      | vehicleType | avoidances | routeType |
      | foot        |            |           |

  @Routing
  Scenario Outline: Verify  Road Names on a Walking Route (Monyash Road)
    Given I have route point as
      | pointA              | pointB              |
      | 53.194909,-1.710481 | 53.156696,-1.634947 |
    And I have vehicle as "<vehicleType>"
    And I have avoidances as "<avoidances>"
    And I have weighting as "<routeType>"
    When I request for a route
    Then I should be able to verify the waypoints on the route map:
      | wayPointIndex | waypointco         | waypointdesc               | azimuth | direction | time   | distance | avoidance |
      | 5             | 53.20882,-1.688212 | Continue onto Monyash Road | 55.0    | NE        | 482979 | 670.8    |           |

    Examples: 
      | vehicleType | avoidances | routeType |
      | foot        |            |           |

  @Routing
  Scenario Outline: Verify  Road Names on a Walking Route (Whitfield lane)
    Given I have route point as
      | pointA              | pointB              |
      | 53.142876,-1.642599 | 53.163897,-1.714249 |
    And I have vehicle as "<vehicleType>"
    And I have avoidances as "<avoidances>"
    And I have weighting as "<routeType>"
    When I request for a route
    Then I should be able to verify the waypoints on the route map:
      | wayPointIndex | waypointco          | waypointdesc               | azimuth | direction | time   | distance | avoidance |
      | 4             | 53.143286,-1.647841 | Turn right onto Elton Road | 282.0   | W         | 195384 | 271.4    |           |

    Examples: 
      | vehicleType | avoidances | routeType |
      | foot        |            |           |

  @Routing
  Scenario Outline: Verify  Road Names on a Walking Route  (Cardlemere Lane)
    Given I have route point as
      | pointA              | pointB             |
      | 53.114295,-1.762789 | 53.086961,-1.69626 |
    And I have vehicle as "<vehicleType>"
    And I have avoidances as "<avoidances>"
    And I have weighting as "<routeType>"
    When I request for a route
    Then I should be able to verify the waypoints on the route map:
      | wayPointIndex | waypointco          | waypointdesc                   | azimuth | direction | time   | distance | avoidance |
      | 4             | 53.129383,-1.754591 | Turn left onto Cardlemere Lane | 121.0   | SE        | 594909 | 826.3    |           |

    Examples: 
      | vehicleType | avoidances | routeType |
      | foot        |            |           |

  @Routing
  Scenario Outline: Verify  Road Names on a Walking Route using one intermediate point ( Old Coalpit Lane)
    Given I have route point as via "<pointC>"
      | pointA              | pointB            | pointC              |
      | 53.238625,-1.794511 | 53.1651,-1.776435 | 53.125221,-1.871205 |
    And I have vehicle as "<vehicleType>"
    And I have avoidances as "<avoidances>"
    And I have weighting as "<routeType>"
    When I request for a route
    Then I should be able to verify the waypoints on the route map:
      | wayPointIndex | waypointco          | waypointdesc                    | azimuth | direction | time   | distance | avoidance |
      | 2             | 53.23952,-1.803512  | Turn left onto Sough Lane       | 189.0   | S         | 452948 | 629.1    |           |
      | 29            | 53.140548,-1.810174 | Turn slight left onto Mill Lane | 216.0   | SW        | 49046  | 68.1     |           |
      | 34            | 53.129146,-1.866738 | Turn left onto Cheadle Road     | 179.0   | S         | 171195 | 237.8    |           |

    Examples: 
      | vehicleType | avoidances | routeType |
      | foot        |            |           |

  @Routing
  Scenario Outline: Verify  Road Names on a Walking Route using one intermediate point ( Newhouses Farm)
    Given I have route point as via "<pointC>"
      | pointA              | pointB             | pointC              |
      | 53.303058,-1.836061 | 53.28261,-1.761964 | 53.233207,-1.633878 |
    And I have vehicle as "<vehicleType>"
    And I have avoidances as "<avoidances>"
    And I have weighting as "<routeType>"
    When I request for a route
    Then I should be able to verify the waypoints on the route map:
      | wayPointIndex | waypointco          | waypointdesc                          | azimuth | direction | time    | distance | avoidance |
      | 7             | 53.305394,-1.819253 | Turn slight right onto Hernstone Lane | 66.0    | NE        | 304923  | 423.5    | ARoad     |
      | 14            | 53.25475,-1.727239  | Continue onto Castlegate Lane         | 183.0   | S         | 1156073 | 1605.7   |           |

    Examples: 
      | vehicleType | avoidances | routeType |
      | foot        |            |           |

  @Routing
  Scenario Outline: Verify  Road Names on a Walking Route using one intermediate point ( Bakewell)
    Given I have route point as via "<pointC>"
      | pointA              | pointB              | pointC              |
      | 53.138247,-1.752507 | 53.195653,-1.762655 | 53.211574,-1.682278 |
    And I have vehicle as "<vehicleType>"
    And I have avoidances as "<avoidances>"
    And I have weighting as "<routeType>"
    When I request for a route
    Then I should be able to verify the waypoints on the route map:
      | wayPointIndex | waypointco          | waypointdesc                   | azimuth | direction | time    | distance | avoidance |
      | 6             | 53.145466,-1.778242 | Continue onto Tissington Trail | 17.0    | N         | 2006616 | 2787.0   |           |
      | 15            | 53.195118,-1.761669 | Continue onto Church Street    | 38.0    | NE        | 897815  | 1247.0   |           |
      | 19            | 53.20882,-1.688212  | Continue onto Monyash Road     | 55.0    | NE        | 369935  | 513.8    |           |

    Examples: 
      | vehicleType | avoidances | routeType |
      | foot        |            |           |

  @Routing
  Scenario Outline: Verify  Road Names on a Walking Route  using 2 intermediate waypoints (Mill Lane)
    Given I have route point as via "<pointC>" and "<pointD>"
      | pointA              | pointB              | pointC             | pointD              |
      | 53.139805,-1.803217 | 53.133646,-1.826223 | 53.14993,-1.868096 | 53.181298,-1.869034 |
    And I have vehicle as "<vehicleType>"
    And I have avoidances as "<avoidances>"
    And I have weighting as "<routeType>"
    When I request for a route
    Then I should be able to verify the waypoints on the route map:
      | wayPointIndex | waypointco          | waypointdesc                    | azimuth | direction | time   | distance | avoidance |
      | 5             | 53.140548,-1.810174 | Turn slight left onto Mill Lane | 216.0   | SW        | 49046  | 68.1     |           |
      | 12            | 53.131356,-1.852045 | Turn slight right onto Path     | 317.0   | NW        | 721024 | 1001.4   |           |
      | 16            | 53.181282,-1.869038 | Turn left onto Market Place     | 315.0   | NW        | 791    | 1.1      |           |

    Examples: 
      | vehicleType | avoidances | routeType |
      | foot        |            |           |

  @Routing
  Scenario Outline: Verify  Road Names on a Walking Route  using 2 intermediate waypoints (Tag Lane)
    Given I have route point as via "
      | pointA              | pointB              | pointC              | pointD              |
      | 53.190346,-1.802704 | 53.239419,-1.818421 | 53.280601,-1.764495 | 53.233207,-1.633878 |
    And I have vehicle as "<vehicleType>"
    And I have avoidances as "<avoidances>"
    And I have weighting as "<routeType>"
    When I request for a route
    Then I should be able to verify the waypoints on the route map:
      | wayPointIndex | waypointco          | waypointdesc                    | azimuth | direction | time    | distance | avoidance |
      | 7             | 53.227765,-1.848174 | Turn left onto Old Coalpit Lane | 58.0    | NE        | 908755  | 1262.2   | ARoad     |
      | 18            | 53.244806,-1.809527 | Continue onto Blackwell Dale    | 48.0    | NE        | 1027560 | 1427.2   |           |
      | 26            | 53.281579,-1.765467 | Continue onto Whitecross Road   | 59.0    | NE        | 210216  | 292.0    |           |
      | 32            | 53.224822,-1.70717  | Turn left onto Hall End Lane    | 59.0    | NE        | 80272   | 111.5    |           |

    Examples: 
      | vehicleType | avoidances | routeType |
      | foot        |            |           |

  @Routing
  Scenario Outline: Verify  Road Names on a Walking Route  using 2 intermediate waypoints (Dowlow Farm)
    Given I have route point as via
      | pointA              | pointB              | pointC              | pointD             |
      | 53.206965,-1.839021 | 53.203607,-1.857557 | 53.149631,-1.867364 | 53.11417,-1.895082 |
    And I have vehicle as "<vehicleType>"
    And I have avoidances as "<avoidances>"
    And I have weighting as "<routeType>"
    When I request for a route
    Then I should be able to verify the waypoints on the route map:
      | wayPointIndex | waypointco          | waypointdesc                  | azimuth | direction | time   | distance | avoidance |
      | 2             | 53.206014,-1.83483  | Turn right onto Midshires Way | 191.0   | S         | 265210 | 368.3    |           |
      | 19            | 53.202937,-1.870926 | Continue onto Glutton Dale    | 254.0   | W         | 279743 | 388.5    |           |
      | 28            | 53.124725,-1.870683 | Turn right onto Cheadle Road  | 243.0   | SW        | 121072 | 168.2    |           |

    Examples: 
      | vehicleType | avoidances | routeType |
      | foot        |            |           |

  # Avoidances : A Road,Boulders,Cliff,Inland Water,Marsh,Quarry Or Pit,Scree,Rock,Mud,Sand,Shingle
  #scree
  @Routing
  Scenario Outline: Verify DPN Route without Scree avoidance -(scree)
    Given I have route point as
      | pointA              | pointB              |
      | 53.267104,-1.818304 | 53.131858,-1.661941 |
    And I have vehicle as "<vehicleType>"
    And I have avoidances as "<avoidances>"
    And I have weighting as "<routeType>"
    When I request for a route
    Then I should be able to verify the waypoints on the route map:
      | wayPointIndex | waypointco          | waypointdesc       | azimuth | direction | time  | distance | avoidance |
      | 15            | 53.252061,-1.826618 | Continue onto Path | 97.0    | E         | 24386 | 33.9     | Scree     |

    Examples: 
      | vehicleType | avoidances | routeType |
      | foot        |            |           |

  @Routing
  Scenario Outline: Verify DPN Route with Scree avoidance -(scree)
    Given I have route point as
      | pointA              | pointB              |
      | 53.267104,-1.818304 | 53.131858,-1.661941 |
    And I have vehicle as "<vehicleType>"
    And I have avoidances as "<avoidances>"
    And I have weighting as "<routeType>"
    When I request for a route
    Then I should be able to verify the waypoints not on the route map:
      | wayPointIndex | waypointco          | waypointdesc       | azimuth | direction | time  | distance | avoidance |
      | 15            | 53.252061,-1.826618 | Continue onto Path | 97.0    | E         | 24386 | 33.9     | Scree     |

    Examples: 
      | vehicleType | avoidances | routeType |
      | foot        | Scree      | fastavoid |

  #cliff
  @Routing
  Scenario Outline: Verify DPN Route without cliff avoidance -(cliff)
    Given I have route point as
      | pointA             | pointB              |
      | 53.31676,-1.631903 | 53.156465,-1.908797 |
    And I have vehicle as "<vehicleType>"
    And I have avoidances as "<avoidances>"
    And I have weighting as "<routeType>"
    When I request for a route
    Then I should be able to verify the waypoints on the route map:
      | wayPointIndex | waypointco          | waypointdesc        | azimuth | direction | time | distance | avoidance |
      | 5             | 53.311409,-1.627165 | Continue onto Route | 178.0   | S         | 2655 | 3.7      | Cliff     |

    Examples: 
      | vehicleType | avoidances | routeType |
      | foot        |            |           |

  @Routing
  Scenario Outline: Verify DPN Route with cliff avoidance -(cliff)
    Given I have route point as
      | pointA             | pointB              |
      | 53.31676,-1.631903 | 53.156465,-1.908797 |
    And I have vehicle as "<vehicleType>"
    And I have avoidances as "<avoidances>"
    And I have weighting as "<routeType>"
    When I request for a route
    Then I should be able to verify the waypoints not on the route map:
      | wayPointIndex | waypointco          | waypointdesc        | azimuth | direction | time | distance | avoidance |
      | 5             | 53.311409,-1.627165 | Continue onto Route | 178.0   | S         | 2655 | 3.7      | Cliff     |

    Examples: 
      | vehicleType | avoidances | routeType |
      | foot        | Cliff      | fastavoid |

  @Routing
  Scenario Outline: Verify DPN Route with cliff avoidance -(cliff)
    Given I have route point as
      | pointA            | pointB              |
      | 53.5534,-1.983177 | 53.540061,-1.978324 |
    And I have vehicle as "<vehicleType>"
    And I have avoidances as "<avoidances>"
    And I have weighting as "<routeType>"
    When I request for a route
    Then I should be able to verify the waypoints on the route map:
      | wayPointIndex | waypointco          | waypointdesc        | azimuth | direction | time | distance | avoidance |
      | 3             | 53.547029,-1.979038 | Continue onto Route | 191.0   | S         | 3920 | 5.4      | Cliff     |
      | 5             | 53.546893,-1.979082 | Continue onto Route | 180.0   | S         | 5085 | 7.1      | Cliff     |
      | 7             | 53.542735,-1.981237 | Continue onto Route | 185.0   | S         | 9321 | 12.9     | Cliff     |

    Examples: 
      | vehicleType | avoidances | routeType |
      | foot        |            |           |

  @Routing
  Scenario Outline: Verify DPN Route with cliff avoidance -(cliff)
    Given I have route point as
      | pointA            | pointB              |
      | 53.5534,-1.983177 | 53.540061,-1.978324 |
    And I have vehicle as "<vehicleType>"
    And I have avoidances as "<avoidances>"
    And I have weighting as "<routeType>"
    When I request for a route
    Then I should be able to verify the waypoints not on the route map:
      | wayPointIndex | waypointco          | waypointdesc        | azimuth | direction | time | distance | avoidance |
      | 3             | 53.547029,-1.979038 | Continue onto Route | 191.0   | S         | 3920 | 5.4      | Cliff     |
      | 5             | 53.546893,-1.979082 | Continue onto Route | 180.0   | S         | 5085 | 7.1      | Cliff     |
      | 7             | 53.542735,-1.981237 | Continue onto Route | 185.0   | S         | 9321 | 12.9     | Cliff     |

    Examples: 
      | vehicleType | avoidances | routeType |
      | foot        | Cliff      | fastavoid |

  #boulders
  @Routing
  Scenario Outline: Verify DPN Route without boulders avoidance -(boulders)
    Given I have route point as
      | pointA              | pointB              |
      | 53.311217,-1.629849 | 53.156465,-1.908797 |
    And I have vehicle as "<vehicleType>"
    And I have avoidances as "<avoidances>"
    And I have weighting as "<routeType>"
    When I request for a route
    Then I should be able to verify the waypoints on the route map:
      | wayPointIndex | waypointco          | waypointdesc        | azimuth | direction | time  | distance | avoidance |
      | 3             | 53.309004,-1.627564 | Turn left onto Path | 98.0    | E         | 99563 | 138.3    | Boulders  |

    Examples: 
      | vehicleType | avoidances | routeType |
      | foot        |            |           |

  @Routing
  Scenario Outline: Verify DPN Route with boulders avoidance -(boulders)
    Given I have route point as
      | pointA              | pointB              |
      | 53.311217,-1.629849 | 53.156465,-1.908797 |
    And I have vehicle as "<vehicleType>"
    And I have avoidances as "<avoidances>"
    And I have weighting as "<routeType>"
    When I request for a route
    Then I should be able to verify the waypoints not on the route map:
      | wayPointIndex | waypointco          | waypointdesc        | azimuth | direction | time  | distance | avoidance |
      | 3             | 53.309004,-1.627564 | Turn left onto Path | 98.0    | E         | 99563 | 138.3    | Boulders  |

    Examples: 
      | vehicleType | avoidances | routeType |
      | foot        | Boulders   | fastavoid |

  #Marsh
  @Routing
  Scenario Outline: Verify DPN Route with avoidance -(Marsh)
    Given I have route point as
      | pointA              | pointB              |
      | 53.558921,-1.935834 | 53.512816,-1.873835 |
    And I have vehicle as "<vehicleType>"
    And I have avoidances as "<avoidances>"
    And I have weighting as "<routeType>"
    When I request for a route
    Then I should be able to verify the waypoints not on the route map:
      | wayPointIndex | waypointco          | waypointdesc                         | azimuth | direction | time  | distance | avoidance |
      | 4             | 53.578173,-1.920445 | Turn sharp right onto Wessenden Road | 185.0   | S         | 45731 | 63.5     | Marsh     |

    Examples: 
      | vehicleType | avoidances | routeType |
      | foot        | Marsh      | fastavoid |

  #Quarry Or Pit
  @Routing
  Scenario Outline: Verify DPN Route with avoidance -(Quarry Or Pit)
    Given I have route point as
      | pointA              | pointB              |
      | 53.348269,-2.061068 | 53.318817,-2.069958 |
    And I have vehicle as "<vehicleType>"
    And I have avoidances as "<avoidances>"
    And I have weighting as "<routeType>"
    When I request for a route
    Then I should be able to verify the waypoints not on the route map:
      | wayPointIndex | waypointco          | waypointdesc                        | azimuth | direction | time   | distance | avoidance     |
      | 8             | 53.329862,-2.078685 | Turn slight left onto Shrigley Road | 184.0   | S         | 728997 | 1012.5   | Quarry Or Pit |

    Examples: 
      | vehicleType | avoidances    | routeType |
      | foot        | Quarry Or Pit | fastavoid |

  #Multiple Avoidance
  @Routing
  Scenario Outline: Verify DPN Route with boulders avoidance -(boulders and A Road)
    Given I have route point as
      | pointA             | pointB              |
      | 53.31423,-1.554174 | 53.179724,-1.677904 |
    And I have vehicle as "<vehicleType>"
    And I have avoidances as "<avoidances>"
    And I have weighting as "<routeType>"
    When I request for a route
    Then I should be able to verify the waypoints not on the route map:
      | wayPointIndex | waypointco          | waypointdesc                          | azimuth | direction | time   | distance | avoidance |
      | 1             | 53.31462,-1.554123  | Continue onto Moss Road               | 274.0   | W         | 426191 | 591.9    | A Road    |
      | 15            | 53.341682,-1.612202 | Turn slight left onto Ringinglow Road | 195.0   | S         | 52520  | 72.9     | Boulders  |

    Examples: 
      | vehicleType | avoidances      | routeType |
      | foot        | Boulders,A Road | fastavoid |

  @Routing
  Scenario Outline: Verify DPN Route without boulders avoidance -(boulders)
    Given I have route point as
      | pointA            | pointB              |
      | 53.5534,-1.983177 | 53.490733,-1.977715 |
    And I have vehicle as "<vehicleType>"
    And I have avoidances as "<avoidances>"
    And I have weighting as "<routeType>"
    When I request for a route
    Then I should be able to verify the waypoints on the route map:
      | wayPointIndex | waypointco          | waypointdesc        | azimuth | direction | time   | distance | avoidance |
      | 6             | 53.545217,-1.986871 | Continue onto Route | 106.0   | E         | 1660   | 2.3      | Cliff     |
      | 9             | 53.545038,-1.986338 | Continue onto Route | 130.0   | SE        | 178328 | 247.7    | Boulders  |

    Examples: 
      | vehicleType | avoidances | routeType |
      | foot        |            |           |

  @Routing
  Scenario Outline: Verify DPN Route with boulders avoidance -(boulders)
    Given I have route point as
      | pointA            | pointB              |
      | 53.5534,-1.983177 | 53.490733,-1.977715 |
    And I have vehicle as "<vehicleType>"
    And I have avoidances as "<avoidances>"
    And I have weighting as "<routeType>"
    When I request for a route
    Then I should be able to verify the waypoints not on the route map:
      | wayPointIndex | waypointco          | waypointdesc        | azimuth | direction | time   | distance | avoidance |
      | 6             | 53.545217,-1.986871 | Continue onto Route | 106.0   | E         | 1660   | 2.3      | Cliff     |
      | 9             | 53.545038,-1.986338 | Continue onto Route | 130.0   | SE        | 178328 | 247.7    | Boulders  |

    Examples: 
      | vehicleType | avoidances     | routeType |
      | foot        | Boulders,Cliff | fastavoid |

  # MOUNTAIN BIKE
  @Routing
  Scenario Outline: Verify DPN Route with -(mountainbike)
    Given I have route point as
      | pointA              | pointB              |
      | 53.298525,-1.679533 | 53.203145,-1.799292 |
    And I have vehicle as "<vehicleType>"
    And I have avoidances as "<avoidances>"
    And I have weighting as "<routeType>"
    When I request for a route
    Then I should be able to verify the waypoints on the route map:
      | wayPointIndex | waypointco          | waypointdesc                        | azimuth | direction | time   | distance | avoidance         |
      | 1             | 53.297007,-1.679015 | Continue onto Sir William Hill Road | 257.0   | W         | 100235 | 501.2    | cycleway, unpaved |

    Examples: 
      | vehicleType  | avoidances | routeType |
      | mountainbike |            |           |

  @Routing
  Scenario Outline: Verify DPN Route with out avoidance -(InlandWater)
    Given I have route point as
      | pointA              | pointB              |
      | 53.334174,-1.936591 | 53.182547,-1.824527 |
    And I have vehicle as "<vehicleType>"
    And I have avoidances as "<avoidances>"
    And I have weighting as "<routeType>"
    When I request for a route
    Then I should be able to verify the waypoints on the route map:
      | wayPointIndex | waypointco          | waypointdesc        | azimuth | direction | time | distance | avoidance   |
      | 4             | 53.348198,-1.933166 | Continue onto Route | 33.0    | NE        | 3799 | 4.2      | InlandWater |

    Examples: 
      | vehicleType  | avoidances | routeType |
      | mountainbike |            |           |

  # ARoad
  @Routing
  Scenario Outline: Verify DPN Route without  avoidance -(A Road )
    Given I have route point as
      | pointA              | pointB              |
      | 53.288886,-1.980339 | 53.311846,-1.783654 |
    And I have vehicle as "<vehicleType>"
    And I have weighting as "<routeType>"
    When I request for a route
    Then I should be able to verify the waypoints on the route map:
      | wayPointIndex | waypointco          | waypointdesc              | azimuth | direction | time    | distance | avoidance |
      | 5             | 53.277954,-1.981109 | Turn left onto Minor Road | 155.0   | SE        | 1125018 | 5625.1   | cycleway  |

    Examples: 
      | vehicleType  | avoidances | routeType |
      | mountainbike |            |           |

  #InlandWater , Scree
  @Routing
  Scenario Outline: Verify DPN Route with out avoidance -(InlandWater , Scree)
    Given I have route point as
      | pointA              | pointB              |
      | 54.156158,-2.337516 | 54.175403,-2.233866 |
    And I have vehicle as "<vehicleType>"
    And I have weighting as "<routeType>"
    When I request for a route
    Then I should be able to verify the waypoints on the route map:
      | wayPointIndex | waypointco          | waypointdesc               | azimuth | direction | time   | distance | avoidance   |
      | 2             | 54.15466,-2.329533  | Continue onto Path         | 103.0   | E         | 1818   | 2.0      | InlandWater |
      | 4             | 54.154148,-2.326061 | Turn slight left onto Path | 92.0    | E         | 240237 | 266.9    | Scree       |

    Examples: 
      | vehicleType  | avoidances | routeType |
      | mountainbike |            |           |

  # QuarryOrPit
  @Routing
  Scenario Outline: Verify DPN Route without  avoidance -(QuarryOrPit )
    Given I have route point as
      | pointA            | pointB              |
      | 53.328689,-2.0808 | 53.318817,-2.069958 |
    And I have vehicle as "<vehicleType>"
    And I have weighting as "<routeType>"
    When I request for a route
    Then I should be able to verify the waypoints on the route map:
      | wayPointIndex | waypointco          | waypointdesc       | azimuth | direction | time  | distance | avoidance   |
      | 5             | 53.329358,-2.074421 | Continue onto Path | 91.0    | E         | 73446 | 81.6     | QuarryOrPit |

    Examples: 
      | vehicleType  | avoidances | routeType |
      | mountainbike |            |           |

  # Yorkshire Dales National Park
  #Marsh
  @Routing
  Scenario Outline: Verify DPN Route with out avoidance -(Yorkshire Dales National Park)
    Given I have route point as
      | pointA              | pointB              |
      | 54.356512,-2.321062 | 54.366348,-2.192719 |
    And I have vehicle as "<vehicleType>"
    And I have weighting as "<routeType>"
    When I request for a route
    Then I should be able to verify the waypoints on the route map:
      | wayPointIndex | waypointco          | waypointdesc       | azimuth | direction | time | distance | avoidance |
      | 4             | 54.327239,-2.255641 | Continue onto Path | 120.0   | SE        | 5864 | 26.1     | Marsh     |

    Examples: 
      | vehicleType  | avoidances | routeType |
      | mountainbike |            |           |

  # Loch Lomond and The Trossachs National Park
  @Routing
  Scenario Outline: Verify DPN Route without  avoidance -(Loch Lomond and The Trossachs National Park )
    Given I have route point as
      | pointA              | pointB              |
      | 56.202278,-4.795406 | 56.254588,-4.254916 |
    And I have vehicle as "<vehicleType>"
    And I have weighting as "<routeType>"
    When I request for a route
    Then I should be able to verify the waypoints on the route map:
      | wayPointIndex | waypointco         | waypointdesc                     | azimuth | direction | time  | distance | avoidance |
      | 5             | 56.20172,-4.725335 | Turn slight left onto Local Road | 42.0    | NE        | 65891 | 329.5    | cycleway  |

    Examples: 
      | vehicleType  | avoidances | routeType |
      | mountainbike |            |           |

  # Northumberland National Park
  @Routing
  Scenario Outline: Verify DPN Route without  avoidance -(Northumberland National Park )
    Given I have route point as
      | pointA              | pointB              |
      | 55.301982,-1.899986 | 55.251792,-2.317673 |
    And I have vehicle as "<vehicleType>"
    And I have weighting as "<routeType>"
    When I request for a route
    Then I should be able to verify the waypoints on the route map:
      | wayPointIndex | waypointco          | waypointdesc                          | azimuth | direction | time  | distance | avoidance |
      | 3             | 55.304153,-1.909006 | Turn slight left onto Carterside Road | 273.0   | W         | 90932 | 454.7    | cycleway  |

    Examples: 
      | vehicleType  | avoidances | routeType |
      | mountainbike |            |           |

  # A Road
  @Routing
  Scenario Outline: Verify DPN Route with avoidance -(ARoad)
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
      | mountainbike | A Road     | fastavoid |

  #InlandWater
  @Routing
  Scenario Outline: Verify DPN Route with avoidance -(InlandWater)
    Given I have route point as
      | pointA              | pointB             |
      | 53.996509,-2.132063 | 54.24286,-2.284947 |
    And I have vehicle as "<vehicleType>"
    And I have avoidances as "<avoidances>"
    And I have weighting as "<routeType>"
    When I request for a route
    Then I should be able to verify the waypoints not on the route map:
      | wayPointIndex | waypointco          | waypointdesc                 | azimuth | direction | time | distance | avoidance   |
      | 19            | 54.122794,-2.279481 | Continue onto Moor Head Lane | 298.0   | NW        | 407  | 1.8      | InlandWater |

    Examples: 
      | vehicleType  | avoidances  | routeType |
      | mountainbike | InlandWater | fastavoid |

  #Boulders
  @Routing
  Scenario Outline: Verify DPN Route with avoidance -(Boulders)
    Given I have route point as
      | pointA              | pointB              |
      | 54.210408,-2.135322 | 54.169941,-2.192232 |
    And I have vehicle as "<vehicleType>"
    And I have avoidances as "<avoidances>"
    And I have weighting as "<routeType>"
    When I request for a route
    Then I should be able to verify the waypoints not on the route map:
      | wayPointIndex | waypointco          | waypointdesc        | azimuth | direction | time   | distance | avoidance |
      | 3             | 54.208065,-2.143237 | Continue onto Route | 295.0   | NW        | 185328 | 205.9    | Boulders  |

    Examples: 
      | vehicleType  | avoidances | routeType |
      | mountainbike | Boulders   | fastavoid |

  #Cliff
  @Routing
  Scenario Outline: Verify DPN Route with avoidance -(Cliff)
    Given I have route point as
      | pointA              | pointB              |
      | 53.558168,-1.939639 | 53.505273,-1.881245 |
    And I have vehicle as "<vehicleType>"
    And I have avoidances as "<avoidances>"
    And I have weighting as "<routeType>"
    When I request for a route
    Then I should be able to verify the waypoints not on the route map:
      | wayPointIndex | waypointco          | waypointdesc        | azimuth | direction | time | distance | avoidance |
      | 22            | 53.496366,-1.888756 | Continue onto Route | 92.0    | E         | 6003 | 6.7      | Cliff     |

    Examples: 
      | vehicleType  | avoidances | routeType |
      | mountainbike | Cliff      | fastavoid |
