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
    And I have weighting as "<routeType>"
    When I request for a route
    Then I should be able to verify the waypoints on the route map:
      | wayPointIndex | waypointco          | waypointdesc             | azimuth | direction | time  | distance | avoidance |
      | 1             | 53.176154,-1.870867 | Continue onto Minor Road | 165.0   | S         | 79514 | 110.4    |           |

    Examples: 
      | vehicleType | avoidances | routeType |
      | foot        |            |           |

  @Routing
  Scenario Outline: Verify  Road Names on a Walking Route  (Chatswoth Park)
    Given I have route point as
      | pointA              | pointB              |
      | 53.211013,-1.619393 | 53.185757,-1.611969 |
    And I have vehicle as "<vehicleType>"
    And I have weighting as "<routeType>"
    When I request for a route
    Then I should be able to verify the waypoints on the route map:
      | wayPointIndex | waypointco          | waypointdesc                        | azimuth | direction | time  | distance | avoidance |
      | 2             | 53.21037,-1.616188  | Turn slight right onto Long Gallery | 158.0   | S         | 16825 | 23.4     |           |
      | 8             | 53.189832,-1.619443 | Turn left onto Church Lane          | 141.0   | SE        | 90545 | 125.8    |           |

    Examples: 
      | vehicleType | avoidances | routeType |
      | foot        |            |           |

  @Routing
  Scenario Outline: Verify  Road Names on a Walking Route (Musden Low)
    Given I have route point as
      | pointA              | pointB              |
      | 53.049589,-1.823866 | 53.076372,-1.853379 |
    And I have vehicle as "<vehicleType>"
    And I have weighting as "<routeType>"
    When I request for a route
    Then I should be able to verify the waypoints on the route map:
      | wayPointIndex | waypointco          | waypointdesc              | azimuth | direction | time   | distance | avoidance |
      | 3             | 53.047941,-1.841795 | Turn right onto Back Lane | 340.0   | N         | 347789 | 483.0    |           |

    Examples: 
      | vehicleType | avoidances | routeType |
      | foot        |            |           |

  @Routing
  Scenario Outline: Verify  Road Names on a Walking Route (A54)
    Given I have route point as
      | pointA              | pointB              |
      | 53.173064,-2.060321 | 53.214387,-2.017271 |
    And I have vehicle as "<vehicleType>"
    And I have weighting as "<routeType>"
    When I request for a route
    Then I should be able to verify the waypoints on the route map:
      | wayPointIndex | waypointco          | waypointdesc                 | azimuth | direction | time   | distance | avoidance |
      | 4             | 53.176842,-2.069334 | Turn right onto Private Road | 1.0     | N         | 919954 | 1277.7   |           |

    Examples: 
      | vehicleType | avoidances | routeType |
      | foot        |            |           |

  @Routing
  Scenario Outline: Verify  Road Names on a Walking Route (Townhead )
    Given I have route point as
      | pointA              | pointB             |
      | 53.122676,-1.909914 | 53.088159,-1.87142 |
    And I have vehicle as "<vehicleType>"
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
    And I have weighting as "<routeType>"
    When I request for a route
    Then I should be able to verify the waypoints on the route map:
      | wayPointIndex | waypointco          | waypointdesc                  | azimuth | direction | time   | distance | avoidance |
      | 2             | 53.347406,-1.760973 | Turn left onto Castleton Road | 109.0   | E         | 550345 | 764.4    | ARoad     |

    Examples: 
      | vehicleType | avoidances | routeType |
      | foot        |            |           |

  @Routing
  Scenario Outline: Verify  Road Names on a Walking Route (Hernstone Lane )
    Given I have route point as
      | pointA              | pointB              |
      | 53.300714,-1.786126 | 53.287803,-1.816746 |
    And I have vehicle as "<vehicleType>"
    And I have weighting as "<routeType>"
    When I request for a route
    Then I should be able to verify the waypoints on the route map:
      | wayPointIndex | waypointco          | waypointdesc                          | azimuth | direction | time    | distance | avoidance |
      | 3             | 53.300719,-1.784239 | Turn sharp right onto Manchester Road | 208.0   | SW        | 1000566 | 1389.7   |           |

    Examples: 
      | vehicleType | avoidances | routeType |
      | foot        |            |           |

  @Routing
  Scenario Outline: Verify  Road Names on a Walking Route (Monyash Road)
    Given I have route point as
      | pointA              | pointB              |
      | 53.194909,-1.710481 | 53.156696,-1.634947 |
    And I have vehicle as "<vehicleType>"
    And I have weighting as "<routeType>"
    When I request for a route
    Then I should be able to verify the waypoints on the route map:
      | wayPointIndex | waypointco          | waypointdesc               | azimuth | direction | time   | distance | avoidance |
      | 2             | 53.195479,-1.701857 | Continue onto Monyash Road | 93.0    | E         | 199214 | 276.7    |           |

    Examples: 
      | vehicleType | avoidances | routeType |
      | foot        |            |           |

  @Routing
  Scenario Outline: Verify  Road Names on a Walking Route (Whitfield lane)
    Given I have route point as
      | pointA              | pointB              |
      | 53.142876,-1.642599 | 53.163897,-1.714249 |
    And I have vehicle as "<vehicleType>"
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
    And I have weighting as "<routeType>"
    When I request for a route
    Then I should be able to verify the waypoints on the route map:
      | wayPointIndex | waypointco          | waypointdesc                   | azimuth | direction | time   | distance | avoidance |
      | 5             | 53.088126,-1.734515 | Turn slight left onto Dam Lane | 83.0    | E         | 242473 | 336.8    |           |

    Examples: 
      | vehicleType | avoidances | routeType |
      | foot        |            |           |

  @Routing
  Scenario Outline: Verify  Road Names on a Walking Route using one intermediate point ( Old Coalpit Lane)
    Given I have route point as
      | pointA              | pointB            | pointC              |
      | 53.184738,-1.780104 | 53.1651,-1.776435 | 53.130965,-1.835199 |
    And I have vehicle as "<vehicleType>"
    And I have weighting as "<routeType>"
    When I request for a route
    Then I should be able to verify the waypoints on the route map:
      | wayPointIndex | waypointco          | waypointdesc                     | azimuth | direction | time   | distance | avoidance |
      | 2             | 53.169799,-1.780276 | Turn left onto A Road            | 153.0   | SE        | 42081  | 58.4     | ARoad     |
      | 5             | 53.165123,-1.776337 | Continue onto A Road             | 158.0   | S         | 162090 | 225.1    |           |
      | 15            | 53.130361,-1.834063 | Turn sharp right onto Minor Road | 325.0   | NW        | 71716  | 99.6     |           |

    Examples: 
      | vehicleType | avoidances | routeType |
      | foot        |            |           |

  @Routing
  Scenario Outline: Verify  Road Names on a Walking Route using one intermediate point ( Newhouses Farm)
    Given I have route point as
      | pointA              | pointB             | pointC              |
      | 53.286725,-1.811501 | 53.28261,-1.761964 | 53.233207,-1.633878 |
    And I have vehicle as "<vehicleType>"
    And I have weighting as "<routeType>"
    When I request for a route
    Then I should be able to verify the waypoints on the route map:
      | wayPointIndex | waypointco          | waypointdesc                    | azimuth | direction | time   | distance | avoidance |
      | 5             | 53.279914,-1.775093 | Turn right onto Manchester Road | 96.0    | E         | 9059   | 12.6     |           |
      | 11            | 53.282733,-1.761774 | Continue onto Conjoint Lane     | 132.0   | SE        | 608458 | 845.1    |           |

    Examples: 
      | vehicleType | avoidances | routeType |
      | foot        |            |           |

  @Routing
  Scenario Outline: Verify  Road Names on a Walking Route using one intermediate point ( Bakewell)
    Given I have route point as
      | pointA              | pointB              | pointC              |
      | 53.138247,-1.752507 | 53.195653,-1.762655 | 53.211574,-1.682278 |
    And I have vehicle as "<vehicleType>"
    And I have weighting as "<routeType>"
    When I request for a route
    Then I should be able to verify the waypoints on the route map:
      | wayPointIndex | waypointco          | waypointdesc                | azimuth | direction | time   | distance | avoidance |
      | 4             | 53.17256,-1.736315  | Turn left onto Long Rake    | 262.0   | W         | 439610 | 610.6    |           |
      | 12            | 53.195118,-1.761669 | Continue onto Church Street | 38.0    | NE        | 897815 | 1247.0   |           |

    Examples: 
      | vehicleType | avoidances | routeType |
      | foot        |            |           |

  @Routing
  Scenario Outline: Verify  Road Names on a Walking Route  using 2 intermediate waypoints (Mill Lane)
    Given I have route point as
      | pointA              | pointB              | pointC             | pointD              |
      | 53.139805,-1.803217 | 53.133646,-1.826223 | 53.14993,-1.868096 | 53.181298,-1.869034 |
    And I have vehicle as "<vehicleType>"
    And I have weighting as "<routeType>"
    When I request for a route
    Then I should be able to verify the waypoints on the route map:
      | wayPointIndex | waypointco          | waypointdesc                    | azimuth | direction | time   | distance | avoidance |
      | 4             | 53.140548,-1.810174 | Turn slight left onto Mill Lane | 216.0   | SW        | 49046  | 68.1     |           |
      | 12            | 53.134882,-1.827561 | Turn left onto Private Road     | 303.0   | NW        | 243573 | 338.3    |           |
      | 33            | 53.181282,-1.869038 | Turn left onto Market Place     | 315.0   | NW        | 791    | 1.1      |           |

    Examples: 
      | vehicleType | avoidances | routeType |
      | foot        |            |           |

  @Routing
  Scenario Outline: Verify  Road Names on a Walking Route  using 2 intermediate waypoints (Tag Lane)
    Given I have route point as
      | pointA              | pointB              | pointC              | pointD              |
      | 53.190346,-1.802704 | 53.239419,-1.818421 | 53.280601,-1.764495 | 53.233207,-1.633878 |
    And I have vehicle as "<vehicleType>"
    And I have weighting as "<routeType>"
    When I request for a route
    Then I should be able to verify the waypoints on the route map:
      | wayPointIndex | waypointco          | waypointdesc                     | azimuth | direction | time    | distance | avoidance |
      | 7             | 53.22401,-1.821103  | Turn slight left onto Minor Road | 276.0   | W         | 92316   | 128.2    |           |
      | 16            | 53.244806,-1.809527 | Continue onto Blackwell Dale     | 48.0    | NE        | 1027560 | 1427.2   |           |
      | 26            | 53.2788,-1.771289   | Continue onto Whitecross Road    | 58.0    | NE        | 357520  | 496.6    |           |

    Examples: 
      | vehicleType | avoidances | routeType |
      | foot        |            |           |

  @Routing
  Scenario Outline: Verify  Road Names on a Walking Route  using 2 intermediate waypoints (Dowlow Farm)
    Given I have route point as
      | pointA              | pointB              | pointC              | pointD             |
      | 53.206965,-1.839021 | 53.203607,-1.857557 | 53.149631,-1.867364 | 53.11417,-1.895082 |
    And I have vehicle as "<vehicleType>"
    And I have weighting as "<routeType>"
    When I request for a route
    Then I should be able to verify the waypoints on the route map:
      | wayPointIndex | waypointco          | waypointdesc                     | azimuth | direction | time    | distance | avoidance |
      | 2             | 53.206014,-1.83483  | Turn right onto Midshires Way    | 191.0   | S         | 265210  | 368.3    |           |
      | 20            | 53.196243,-1.857282 | Turn sharp left onto Minor Road  | 111.0   | E         | 1156273 | 1605.9   |           |
      | 43            | 53.148266,-1.867183 | Turn sharp right onto Minor Road | 261.0   | W         | 246565  | 342.5    |           |

    Examples: 
      | vehicleType | avoidances | routeType |
      | foot        |            |           |

  # Avoidances : A Road,Boulders,Cliff,Inland Water,Marsh,Quarry Or Pit,Scree,Rock,Mud,Sand,Shingle
  #scree
  @Routing
  Scenario Outline: Verify DPN Route without Scree avoidance -(scree)
    Given I have route point as
      | pointA              | pointB              |
      | 53.202622,-1.787557 | 53.188642,-1.720999 |
    And I have vehicle as "<vehicleType>"
    And I have weighting as "<routeType>"
    When I request for a route
    Then I should be able to verify the waypoints on the route map:
      | wayPointIndex | waypointco          | waypointdesc       | azimuth | direction | time   | distance | avoidance |
      | 11            | 53.189871,-1.744722 | Continue onto Path | 112.0   | SE        | 253051 | 351.5    | Scree     |

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
    And I have weighting as "<routeType>"
    When I request for a route
    Then I should be able to verify the waypoints on the route map:
      | wayPointIndex | waypointco          | waypointdesc        | azimuth | direction | time | distance | avoidance |
      | 4             | 53.311409,-1.627165 | Continue onto Route | 178.0   | S         | 2655 | 3.7      | Cliff     |

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
    And I have weighting as "<routeType>"
    When I request for a route
    Then I should be able to verify the waypoints on the route map:
      | wayPointIndex | waypointco          | waypointdesc        | azimuth | direction | time  | distance | avoidance |
      | 6             | 53.309601,-1.627555 | Continue onto Route | 180.0   | S         | 47751 | 66.3     | Boulders  |

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
  #(ARoad, InlandWater, Boulders, Cliff, Scree, Marsh, Quarry or Pit)
  @Routing
  Scenario Outline: Verify DPN Route with -(mountainbike)
    Given I have route point as
      | pointA              | pointB              |
      | 53.298525,-1.679533 | 53.203145,-1.799292 |
    And I have vehicle as "<vehicleType>"
    And I have weighting as "<routeType>"
    When I request for a route
    Then I should be able to verify the waypoints on the route map:
      | wayPointIndex | waypointco          | waypointdesc                        | azimuth | direction | time   | distance | avoidance         |
      | 1             | 53.297007,-1.679015 | Continue onto Sir William Hill Road | 257.0   | W         | 100235 | 501.2    | cycleway, unpaved |

    Examples: 
      | vehicleType  | avoidances | routeType |
      | mountainbike |            |           |

  #InlandWater
  @Routing
  Scenario Outline: Verify DPN Route with out avoidance -(InlandWater)
    Given I have route point as
      | pointA              | pointB              |
      | 53.334174,-1.936591 | 53.182547,-1.824527 |
    And I have vehicle as "<vehicleType>"
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

  #2 intermediate waypoints for mountainbike
  @Routing
  Scenario Outline: Verify  Route using( 2 intermediate waypoints for mountainbike )
    Given I have route point as
      | pointA            | pointB              | pointC             | pointD              |
      | 54.2948,-2.204429 | 54.276757,-2.196492 | 54.26687,-2.203109 | 54.256984,-2.208218 |
    And I have vehicle as "<vehicleType>"
    And I have weighting as "<routeType>"
    When I request for a route
    Then I should be able to verify the waypoints on the route map:
      | wayPointIndex | waypointco          | waypointdesc                        | azimuth | direction | time   | distance | avoidance |
      | 2             | 54.295461,-2.20503  | Turn slight right onto Route        | 39.0    | NE        | 94634  | 131.4    | Boulders  |
      | 13            | 54.273671,-2.196382 | Turn sharp right onto Cam High Road | 227.0   | SW        | 651877 | 905.4    |           |
      | 16            | 54.257446,-2.215557 | Continue onto Oughtershaw Road      | 138.0   | SE        | 116685 | 162.1    |           |

    Examples: 
      | vehicleType  | avoidances | routeType |
      | mountainbike |            |           |

  # Pembrokeshire Coast National Park(fastest and shortest)
  @Routing
  Scenario Outline: Verify DPN Route without avoidance -(Pembrokeshire Coast National Park )
    Given I have route point as
      | pointA              | pointB              |
      | 51.993625,-4.899369 | 51.630076,-4.976465 |
    And I have vehicle as "<vehicleType>"
    And I have weighting as "<routeType>"
    When I request for a route
    Then I should be able to verify the waypoints on the route map:
      | wayPointIndex | waypointco          | waypointdesc          | azimuth | direction | time   | distance | avoidance |
      | 6             | 51.857225,-4.967991 | Turn left onto A Road | 177.0   | S         | 732342 | 3661.7   | ARoad     |

    Examples: 
      | vehicleType  | avoidances | routeType |
      | mountainbike |            | fastest   |

  @Routing
  Scenario Outline: Verify DPN Route without avoidance -(Pembrokeshire Coast National Park )
    Given I have route point as
      | pointA              | pointB              |
      | 51.993625,-4.899369 | 51.630076,-4.976465 |
    And I have vehicle as "<vehicleType>"
    And I have weighting as "<routeType>"
    When I request for a route
    Then I should be able to verify the waypoints on the route map:
      | wayPointIndex | waypointco          | waypointdesc        | azimuth | direction | time | distance | avoidance   |
      | 6             | 51.970886,-4.881752 | Continue onto Route | 239.0   | SW        | 2610 | 2.9      | InlandWater |

    Examples: 
      | vehicleType  | avoidances | routeType |
      | mountainbike |            | shortest  |

  # Northumberland National Park(fastest and shortest)
  @Routing
  Scenario Outline: Verify DPN Route without avoidance -(Northumberland National Park )
    Given I have route point as
      | pointA             | pointB              |
      | 55.07745,-2.528548 | 55.371494,-2.488282 |
    And I have vehicle as "<vehicleType>"
    And I have weighting as "<routeType>"
    When I request for a route
    Then I should be able to verify the waypoints on the route map:
      | wayPointIndex | waypointco          | waypointdesc             | azimuth | direction | time | distance | avoidance   |
      | 2             | 55.065219,-2.518842 | Continue onto Minor Road | 92.0    | E         | 3200 | 16.0     | InlandWater |

    Examples: 
      | vehicleType  | avoidances | routeType |
      | mountainbike |            | fastest   |

  @Routing
  Scenario Outline: Verify DPN Route without avoidance -(Northumberland National Park )
    Given I have route point as
      | pointA             | pointB              |
      | 55.07745,-2.528548 | 55.371494,-2.488282 |
    And I have vehicle as "<vehicleType>"
    And I have weighting as "<routeType>"
    When I request for a route
    Then I should be able to verify the waypoints on the route map:
      | wayPointIndex | waypointco        | waypointdesc        | azimuth | direction | time   | distance | avoidance |
      | 15            | 54.992982,-2.4999 | Continue onto Route | 64.0    | NE        | 979584 | 1088.4   | Boulders  |

    Examples: 
      | vehicleType  | avoidances | routeType |
      | mountainbike |            | shortest  |

  #New Forest National Park (mtb)
  @Routing
  Scenario Outline: Verify DPN Route without avoidance -( New Forest National Park)
    Given I have route point as
      | pointA              | pointB              |
      | 50.789487,-1.694991 | 50.927459,-1.607637 |
    And I have vehicle as "<vehicleType>"
    And I have weighting as "<routeType>"
    When I request for a route
    Then I should be able to verify the waypoints on the route map:
      | wayPointIndex | waypointco          | waypointdesc                 | azimuth | direction | time   | distance | avoidance |
      | 7             | 50.834329,-1.691349 | Continue onto Lyndhurst Road | 31.0    | NE        | 509238 | 2546.2   | cycleway  |

    Examples: 
      | vehicleType  | avoidances | routeType |
      | mountainbike |            | fastest   |

  #New Forest National Park(foot)
  #Marsh
  @Routing
  Scenario Outline: Verify DPN Route without avoidance -( New Forest National Park)
    Given I have route point as
      | pointA              | pointB              |
      | 50.796532,-1.608734 | 50.875982,-1.651845 |
    And I have vehicle as "<vehicleType>"
    And I have weighting as "<routeType>"
    When I request for a route
    Then I should be able to verify the waypoints on the route map:
      | wayPointIndex | waypointco          | waypointdesc       | azimuth | direction | time  | distance | avoidance |
      | 4             | 50.807982,-1.596756 | Continue onto Path | 330.0   | NW        | 92960 | 129.1    | Marsh     |

    Examples: 
      | vehicleType | avoidances | routeType |
      | foot        |            | fastest   |

  #Snowdonia National Park (mtb)
  #Shingle
  @Routing
  Scenario Outline: Verify DPN Route without avoidance -( Snowdonia National Park)
    Given I have route point as
      | pointA              | pointB              |
      | 52.934101,-3.823519 | 52.594998,-4.061792 |
    And I have vehicle as "<vehicleType>"
    And I have weighting as "<routeType>"
    When I request for a route
    Then I should be able to verify the waypoints on the route map:
      | wayPointIndex | waypointco          | waypointdesc        | azimuth | direction | time   | distance | avoidance |
      | 5             | 52.929364,-3.820934 | Continue onto Route | 178.0   | S         | 155309 | 172.6    | Shingle   |

    Examples: 
      | vehicleType  | avoidances | routeType |
      | mountainbike |            | fastest   |

  #Snowdonia National Park (foot)
  #Inland water
  @Routing
  Scenario Outline: Verify DPN Route without avoidance -( Snowdonia National Park)
    Given I have route point as
      | pointA             | pointB              |
      | 52.927159,-3.91279 | 52.919672,-3.762104 |
    And I have vehicle as "<vehicleType>"
    And I have weighting as "<routeType>"
    When I request for a route
    Then I should be able to verify the waypoints on the route map:
      | wayPointIndex | waypointco          | waypointdesc        | azimuth | direction | time | distance | avoidance   |
      | 7             | 52.921373,-3.883349 | Continue onto Route | 137.0   | SE        | 3562 | 4.9      | InlandWater |

    Examples: 
      | vehicleType | avoidances | routeType |
      | foot        |            | fastest   |

  # North York Moors National Park (mtb)
  @Routing
  Scenario Outline: Verify DPN Route without avoidance -(North York Moors National Park )
    Given I have route point as
      | pointA              | pointB              |
      | 54.398737,-0.927914 | 54.285296,-1.023336 |
    And I have vehicle as "<vehicleType>"
    And I have weighting as "<routeType>"
    When I request for a route
    Then I should be able to verify the waypoints on the route map:
      | wayPointIndex | waypointco          | waypointdesc                  | azimuth | direction | time  | distance | avoidance |
      | 12            | 54.378992,-1.024327 | Turn right onto Westside Road | 334.0   | NW        | 41791 | 209.0    |           |

    Examples: 
      | vehicleType  | avoidances | routeType |
      | mountainbike |            | fastest   |

  # North York Moors National Park (foot)
  @Routing
  Scenario Outline: Verify DPN Route without avoidance -(North York Moors National Park )
    Given I have route point as
      | pointA           | pointB             |
      | 54.4302,-0.98709 | 54.39359,-1.033704 |
    And I have vehicle as "<vehicleType>"
    And I have weighting as "<routeType>"
    When I request for a route
    Then I should be able to verify the waypoints on the route map:
      | wayPointIndex | waypointco          | waypointdesc                | azimuth | direction | time  | distance | avoidance      |
      | 6             | 54.411017,-0.989613 | Turn slight right onto Path | 192.0   | S         | 82532 | 114.6    | Boulders Marsh |

    Examples: 
      | vehicleType | avoidances | routeType |
      | foot        |            | fastest   |

  # Brecon Beacons National Park (mtb)
  @Routing
  Scenario Outline: Verify DPN Route without avoidance -(Brecon Beacons National Park )
    Given I have route point as
      | pointA              | pointB             |
      | 51.921454,-3.510189 | 51.856138,-3.08679 |
    And I have vehicle as "<vehicleType>"
    And I have weighting as "<routeType>"
    When I request for a route
    Then I should be able to verify the waypoints on the route map:
      | wayPointIndex | waypointco         | waypointdesc                 | azimuth | direction | time  | distance | avoidance |
      | 5             | 51.945454,-3.40724 | Continue onto Newgate Street | 130.0   | SE        | 96342 | 481.7    | cycleway  |

    Examples: 
      | vehicleType  | avoidances | routeType |
      | mountainbike |            | fastest   |

  # Brecon Beacons National Park (foot)
  @Routing
  Scenario Outline: Verify DPN Route without avoidance -((Brecon Beacons National Park)
    Given I have route point as
      | pointA              | pointB             |
      | 51.921454,-3.510189 | 51.856138,-3.08679 |
    And I have vehicle as "<vehicleType>"
    And I have weighting as "<routeType>"
    When I request for a route
    Then I should be able to verify the waypoints on the route map:
      | wayPointIndex | waypointco          | waypointdesc                | azimuth | direction | time  | distance | avoidance |
      | 7             | 51.945249,-3.410684 | Turn sharp left onto A Road | 352.0   | N         | 58703 | 81.5     | ARoad     |

    Examples: 
      | vehicleType | avoidances | routeType |
      | foot        |            | fastest   |

  #Lake District National park (mtb)
  @Routing
  Scenario Outline: Verify DPN Route without avoidance -(Lake District National park )
    Given I have route point as
      | pointA              | pointB              |
      | 54.626942,-3.282542 | 54.387933,-3.125019 |
    And I have vehicle as "<vehicleType>"
    And I have weighting as "<routeType>"
    When I request for a route
    Then I should be able to verify the waypoints on the route map:
      | wayPointIndex | waypointco          | waypointdesc                | azimuth | direction | time  | distance | avoidance |
      | 5             | 54.601608,-3.169558 | Turn sharp left onto A Road | 46.0    | NE        | 38561 | 192.8    | ARoad     |

    Examples: 
      | vehicleType  | avoidances | routeType |
      | mountainbike |            | fastest   |

  #Lake District National park (foot)
  @Routing
  Scenario Outline: Verify DPN Route without avoidance -(Lake District National park )
    Given I have route point as
      | pointA              | pointB              |
      | 54.626942,-3.282542 | 54.387933,-3.125019 |
    And I have vehicle as "<vehicleType>"
    And I have weighting as "<routeType>"
    When I request for a route
    Then I should be able to verify the waypoints on the route map:
      | wayPointIndex | waypointco          | waypointdesc       | azimuth | direction | time   | distance | avoidance |
      | 5             | 54.621746,-3.274322 | Continue onto Path | 254.0   | W         | 318460 | 442.3    | Scree     |

    Examples: 
      | vehicleType | avoidances | routeType |
      | foot        |            | fastest   |

  # Yorkshire Dales National Park (mtb)
  #Marsh
  @Routing
  Scenario Outline: Verify DPN Route without avoidance -(Yorkshire Dales National Park)
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
      | mountainbike |            | fastest   |

  # Yorkshire Dales National Park (foot)
  # Boulders Scree
  @Routing
  Scenario Outline: Verify DPN Route without avoidance -(Yorkshire Dales National Park)
    Given I have route point as
      | pointA              | pointB              |
      | 54.188387,-2.480236 | 54.114215,-2.182979 |
    And I have vehicle as "<vehicleType>"
    And I have weighting as "<routeType>"
    When I request for a route
    Then I should be able to verify the waypoints on the route map:
      | wayPointIndex | waypointco          | waypointdesc         | azimuth | direction | time   | distance | avoidance      |
      | 2             | 54.186539,-2.481875 | Turn left onto Route | 156.0   | SE        | 750925 | 1043.0   | Boulders Scree |

    Examples: 
      | vehicleType | avoidances | routeType |
      | foot        |            | fastest   |

  # Norfolk and Suflock Broads (mtb)
  # ARoad
  @Routing
  Scenario Outline: Verify DPN Route without avoidance -(Norfolk and Suflock Broads )
    Given I have route point as
      | pointA             | pointB             |
      | 52.480259,1.661591 | 52.467833,1.668393 |
    And I have vehicle as "<vehicleType>"
    And I have weighting as "<routeType>"
    When I request for a route
    Then I should be able to verify the waypoints on the route map:
      | wayPointIndex | waypointco         | waypointdesc               | azimuth | direction | time   | distance | avoidance |
      | 3             | 52.484179,1.631402 | Continue onto Beccles Road | 268.0   | W         | 122102 | 610.5    | cycleway  |

    Examples: 
      | vehicleType  | avoidances | routeType |
      | mountainbike |            | fastest   |

  # Norfolk and Suflock Broads (foot)
  @Routing
  Scenario Outline: Verify DPN Route without avoidance -(Norfolk and Suflock Broads )
    Given I have route point as
      | pointA             | pointB             |
      | 52.480259,1.661591 | 52.467833,1.668393 |
    And I have vehicle as "<vehicleType>"
    And I have weighting as "<routeType>"
    When I request for a route
    Then I should be able to verify the waypoints on the route map:
      | wayPointIndex | waypointco         | waypointdesc               | azimuth | direction | time   | distance | avoidance |
      | 2             | 52.484179,1.631402 | Continue onto Beccles Road | 268.0   | W         | 439573 | 610.5    |           |

    Examples: 
      | vehicleType | avoidances | routeType |
      | foot        |            | fastest   |

  #Northumberland National Park (mtb)
  #Boulders
  @Routing
  Scenario Outline: Verify DPN Route without avoidance -(Northumberland National Park )
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
      | mountainbike |            | fastest   |

  #Northumberland National Park (foot)
  #cliff
  @Routing
  Scenario Outline: Verify DPN Route without avoidance -(Northumberland National Park )
    Given I have route point as
      | pointA             | pointB              |
      | 55.41105,-2.201315 | 55.555018,-2.162993 |
    And I have vehicle as "<vehicleType>"
    And I have weighting as "<routeType>"
    When I request for a route
    Then I should be able to verify the waypoints on the route map:
      | wayPointIndex | waypointco          | waypointdesc       | azimuth | direction | time | distance | avoidance |
      | 7             | 55.472241,-2.174253 | Continue onto Path | 283.0   | W         | 9669 | 13.4     | Cliff     |

    Examples: 
      | vehicleType | avoidances | routeType |
      | foot        |            | fastest   |

  # Loch Lomond and The Trossachs National Park
  @Routing
  Scenario Outline: Verify DPN Route without avoidance -(Loch Lomond and The Trossachs National Park )
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
      | mountainbike |            | fastest   |

  # Loch Lomond and The Trossachs National Park
  #Marsh
  @Routing
  Scenario Outline: Verify DPN Route without  avoidance -(Loch Lomond and The Trossachs National Park )
    Given I have route point as
      | pointA             | pointB              |
      | 56.35644,-4.675223 | 56.100288,-4.505227 |
    And I have vehicle as "<vehicleType>"
    And I have weighting as "<routeType>"
    When I request for a route
    Then I should be able to verify the waypoints on the route map:
      | wayPointIndex | waypointco         | waypointdesc                | azimuth | direction | time   | distance | avoidance |
      | 4             | 56.35408,-4.684804 | Turn slight right onto Path | 239.0   | SW        | 278884 | 387.3    | Marsh     |

    Examples: 
      | vehicleType | avoidances | routeType |
      | foot        |            | fastest   |

  # Pembrokeshire Coast National Park(mtb)
  @Routing
  Scenario Outline: Verify DPN Route without avoidance -(Pembrokeshire Coast National Park )
    Given I have route point as
      | pointA              | pointB              |
      | 51.993625,-4.899369 | 51.630076,-4.976465 |
    And I have vehicle as "<vehicleType>"
    And I have weighting as "<routeType>"
    When I request for a route
    Then I should be able to verify the waypoints on the route map:
      | wayPointIndex | waypointco          | waypointdesc          | azimuth | direction | time   | distance | avoidance |
      | 6             | 51.857225,-4.967991 | Turn left onto A Road | 177.0   | S         | 732342 | 3661.7   | ARoad     |

    Examples: 
      | vehicleType  | avoidances | routeType |
      | mountainbike |            | fastest   |

  # Pembrokeshire Coast National Park(foot)
  # Mud (with and without avoidance mud)
  @Routing
  Scenario Outline: Verify DPN Route without avoidance -(Pembrokeshire Coast National Park )
    Given I have route point as
      | pointA              | pointB              |
      | 51.769671,-4.948304 | 51.801625,-4.954451 |
    And I have vehicle as "<vehicleType>"
    And I have weighting as "<routeType>"
    When I request for a route
    Then I should be able to verify the waypoints on the route map:
      | wayPointIndex | waypointco          | waypointdesc        | azimuth | direction | time  | distance | avoidance |
      | 8             | 51.776593,-4.947697 | Continue onto Route | 65.0    | NE        | 51510 | 71.5     | Marsh Mud |

    Examples: 
      | vehicleType | avoidances | routeType |
      | foot        |            | fastest   |

  @Routing
  Scenario Outline: Verify DPN Route with avoidance -(Pembrokeshire Coast National Park )
    Given I have route point as
      | pointA              | pointB              |
      | 51.769671,-4.948304 | 51.801625,-4.954451 |
    And I have vehicle as "<vehicleType>"
    And I have avoidances as "<avoidances>"
    And I have weighting as "<routeType>"
    When I request for a route
    Then I should be able to verify the waypoints not on the route map:
      | wayPointIndex | waypointco          | waypointdesc        | azimuth | direction | time  | distance | avoidance |
      | 8             | 51.776593,-4.947697 | Continue onto Route | 65.0    | NE        | 51510 | 71.5     | Marsh Mud |

    Examples: 
      | vehicleType | avoidances | routeType |
      | foot        | Mud        | fastest   |

  # Marsh Mud TidalWater
  @Routing
  Scenario Outline: Verify DPN Route without avoidance -(Pembrokeshire Coast National Park )
    Given I have route point as
      | pointA              | pointB              |
      | 51.769671,-4.948304 | 51.801625,-4.954451 |
    And I have vehicle as "<vehicleType>"
    And I have weighting as "<routeType>"
    When I request for a route
    Then I should be able to verify the waypoints on the route map:
      | wayPointIndex | waypointco          | waypointdesc        | azimuth | direction | time | distance | avoidance            |
      | 7             | 51.776573,-4.947771 | Continue onto Route | 65.0    | NE        | 3973 | 5.5      | Marsh Mud TidalWater |

    Examples: 
      | vehicleType | avoidances | routeType |
      | foot        |            | fastest   |

  @Routing
  Scenario Outline: Verify DPN Route with avoidance -(Pembrokeshire Coast National Park )
    Given I have route point as
      | pointA              | pointB              |
      | 51.769671,-4.948304 | 51.801625,-4.954451 |
    And I have vehicle as "<vehicleType>"
    And I have avoidances as "<avoidances>"
    And I have weighting as "<routeType>"
    When I request for a route
    Then I should be able to verify the waypoints not on the route map:
      | wayPointIndex | waypointco          | waypointdesc        | azimuth | direction | time | distance | avoidance            |
      | 7             | 51.776573,-4.947771 | Continue onto Route | 65.0    | NE        | 3973 | 5.5      | Marsh Mud TidalWater |

    Examples: 
      | vehicleType | avoidances      | routeType |
      | foot        | Mud ,TidalWater | fastest   |

  #  Dartmoor national Park (foot)
  # InlandWater
  @Routing
  Scenario Outline: Verify DPN Route without avoidance -(Dartmoor national Park)
    Given I have route point as
      | pointA             | pointB              |
      | 50.620148,-3.92391 | 50.546151,-3.859439 |
    And I have vehicle as "<vehicleType>"
    And I have weighting as "<routeType>"
    When I request for a route
    Then I should be able to verify the waypoints on the route map:
      | wayPointIndex | waypointco          | waypointdesc        | azimuth | direction | time | distance | avoidance   |
      | 2             | 50.594729,-3.911446 | Continue onto Route | 136.0   | SE        | 2360 | 3.3      | InlandWater |

    Examples: 
      | vehicleType | avoidances | routeType |
      | foot        |            | fastest   |

  #  Dartmoor national Park (mtb)
  @Routing
  Scenario Outline: Verify DPN Route without avoidance -(Dartmoor national Park)
    Given I have route point as
      | pointA              | pointB              |
      | 50.592854,-4.131881 | 50.680593,-4.098598 |
    And I have vehicle as "<vehicleType>"
    And I have weighting as "<routeType>"
    When I request for a route
    Then I should be able to verify the waypoints on the route map:
      | wayPointIndex | waypointco          | waypointdesc             | azimuth | direction | time   | distance | avoidance |
      | 5             | 50.600622,-4.126057 | Continue onto Minor Road | 268.0   | W         | 375268 | 1876.3   | cycleway  |

    Examples: 
      | vehicleType  | avoidances | routeType |
      | mountainbike |            | fastest   |

  # Exmoor National Park (mtb)
  # InlandWater
  @Routing
  Scenario Outline: Verify DPN Route without avoidance -(Exmoor National Park )
    Given I have route point as
      | pointA              | pointB              |
      | 51.206305,-3.683483 | 51.195761,-3.848208 |
    And I have vehicle as "<vehicleType>"
    And I have weighting as "<routeType>"
    When I request for a route
    Then I should be able to verify the waypoints on the route map:
      | wayPointIndex | waypointco         | waypointdesc             | azimuth | direction | time | distance | avoidance   |
      | 3             | 51.215963,-3.73078 | Continue onto Minor Road | 285.0   | W         | 4538 | 22.7     | InlandWater |

    Examples: 
      | vehicleType  | avoidances | routeType |
      | mountainbike |            | fastest   |

  # Exmoor National Park (foot)
  @Routing
  Scenario Outline: Verify DPN Route without avoidance -(Exmoor National Park )
    Given I have route point as
      | pointA              | pointB              |
      | 51.206305,-3.683483 | 51.195761,-3.848208 |
    And I have vehicle as "<vehicleType>"
    And I have weighting as "<routeType>"
    When I request for a route
    Then I should be able to verify the waypoints on the route map:
      | wayPointIndex | waypointco         | waypointdesc             | azimuth | direction | time  | distance | avoidance   |
      | 4             | 51.215963,-3.73078 | Continue onto Minor Road | 285.0   | W         | 16336 | 22.7     | InlandWater |

    Examples: 
      | vehicleType | avoidances | routeType |
      | foot        |            | fastest   |

  # BNG and WGS 84
  @Routing
  Scenario Outline: Verify DPN Route without avoidance -(Exmoor National Park )
    Given I have route point as
      | pointA              | pointB              |
      | 51.206305,-3.683483 | 51.195761,-3.848208 |
    And I have vehicle as "<vehicleType>"
    And I have weighting as "<routeType>"
    And I have srs as "WGS84"
    And I have output_srs as "WGS84"
    When I request for a route
    Then I should be able to verify the waypoints on the route map:
      | wayPointIndex | waypointco         | waypointdesc             | azimuth | direction | time | distance | avoidance   |
      | 3             | 51.215963,-3.73078 | Continue onto Minor Road | 285.0   | W         | 4538 | 22.7     | InlandWater |

    Examples: 
      | vehicleType  | avoidances | routeType |
      | mountainbike |            | fastest   |

  @Routing
  Scenario Outline: Verify DPN Route without avoidance -(Exmoor National Park )
    Given I have route point as
      | pointA        | pointB        |
      | 282492,146580 | 270956,145684 |
    And I have vehicle as "<vehicleType>"
    And I have weighting as "<routeType>"
    And I have srs as "BNG"
    And I have output_srs as "BNG"
    When I request for a route
    Then I should be able to verify the waypoints on the route map:
      | wayPointIndex | waypointco                  | waypointdesc        | azimuth | direction | time | distance | avoidance   |
      | 4             | 281439.280111,146371.327562 | Continue onto Track | 266.0   | W         | 2620 | 3.6      | InlandWater |

    Examples: 
      | vehicleType | avoidances | routeType |
      | foot        |            | fastest   |

  @Routing
  Scenario Outline: Verify DPN Route without avoidance -(Exmoor National Park )
    Given I have route point as
      | pointA              | pointB              |
      | 51.206305,-3.683483 | 51.195761,-3.848208 |
    And I have vehicle as "<vehicleType>"
    And I have weighting as "<routeType>"
    And I have srs as "WGS84"
    And I have output_srs as "BNG"
    When I request for a route
    Then I should be able to vlerify the waypoints on the route map:
      | wayPointIndex | waypointco                   | waypointdesc             | azimuth | direction | time   | distance | avoidance |
      | 3             | 282414.462523, 146543.039233 | Continue onto Minor Road | 259.0   | W         | 588882 | 2944.4   | cycleway  |

    Examples: 
      | vehicleType  | avoidances | routeType |
      | mountainbike |            | fastest   |

  @Routing
  Scenario Outline: Verify DPN Route without avoidance -(Exmoor National Park )
    Given I have route point as
      | pointA        | pointB        |
      | 282492,146580 | 270956,145684 |
    And I have vehicle as "<vehicleType>"
    And I have weighting as "<routeType>"
    And I have srs as "BNG"
    And I have output_srs as "WGS84"
    When I request for a route
    Then I should be able to verify the waypoints on the route map:
      | wayPointIndex | waypointco          | waypointdesc        | azimuth | direction | time | distance | avoidance   |
      | 4             | 51.204211,-3.698476 | Continue onto Track | 266.0   | W         | 2620 | 3.6      | InlandWater |

    Examples: 
      | vehicleType | avoidances | routeType |
      | foot        |            | fastest   |
