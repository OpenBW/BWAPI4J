package org.openbw.bwapi4j.unit;

import org.openbw.bwapi4j.type.UnitType;

public class UnitFactory {

    public Unit createUnit(int unitId, UnitType unitType, int timeSpotted) {

        Unit unit;
        switch (unitType) {
        case Terran_Academy:
            unit = new Academy(unitId, timeSpotted);
            break;
        case Terran_Armory:
            unit = new Armory(unitId, timeSpotted);
            break;
        case Terran_Barracks:
            unit = new Barracks(unitId, timeSpotted);
            break;
        case Terran_Battlecruiser:
            unit = new BattleCruiser(unitId);
            break;
        case Terran_Bunker:
            unit = new Bunker(unitId, timeSpotted);
            break;
        case Terran_Command_Center:
            unit = new CommandCenter(unitId, timeSpotted);
            break;
        case Terran_Comsat_Station:
            unit = new ComsatStation(unitId, timeSpotted);
            break;
        case Terran_Control_Tower:
            unit = new ControlTower(unitId, timeSpotted);
            break;
        case Terran_Covert_Ops:
            unit = new CovertOps(unitId, timeSpotted);
            break;
        case Terran_Dropship:
            unit = new Dropship(unitId);
            break;
        case Terran_Engineering_Bay:
            unit = new EngineeringBay(unitId, timeSpotted);
            break;
        case Terran_Factory:
            unit = new Factory(unitId, timeSpotted);
            break;
        case Terran_Firebat:
            unit = new Firebat(unitId);
            break;
        case Terran_Ghost:
            unit = new Ghost(unitId);
            break;
        case Terran_Goliath:
            unit = new Goliath(unitId);
            break;
        case Terran_Machine_Shop:
            unit = new MachineShop(unitId, timeSpotted);
            break;
        case Terran_Marine:
            unit = new Marine(unitId);
            break;
        case Terran_Medic:
            unit = new Medic(unitId);
            break;
        case Terran_Missile_Turret:
            unit = new MissileTurret(unitId, timeSpotted);
            break;
        case Terran_Nuclear_Silo:
            unit = new NuclearSilo(unitId, timeSpotted);
            break;
        case Terran_Physics_Lab:
            unit = new PhysicsLab(unitId, timeSpotted);
            break;
        case Terran_Refinery:
            unit = new Refinery(unitId, timeSpotted);
            break;
        case Terran_Science_Facility:
            unit = new ScienceFacility(unitId, timeSpotted);
            break;
        case Terran_Science_Vessel:
            unit = new ScienceVessel(unitId);
            break;
        case Terran_SCV:
            unit = new SCV(unitId);
            break;
        case Terran_Siege_Tank_Tank_Mode:
            unit = new SiegeTank(unitId);
            break;
        case Terran_Vulture_Spider_Mine:
            unit = new SpiderMine(unitId);
            break;
        case Terran_Starport:
            unit = new Starport(unitId, timeSpotted);
            break;
        case Terran_Supply_Depot:
            unit = new SupplyDepot(unitId, timeSpotted);
            break;
        case Terran_Valkyrie:
            unit = new Valkyrie(unitId);
            break;
        case Terran_Vulture:
            unit = new Vulture(unitId);
            break;
        case Terran_Wraith:
            unit = new Wraith(unitId);
            break;
        case Resource_Mineral_Field:
        case Resource_Mineral_Field_Type_2:
        case Resource_Mineral_Field_Type_3:
            unit = new MineralPatch(unitId);
            break;
        case Resource_Vespene_Geyser:
            unit = new VespeneGeyser(unitId);
            break;
        default:
            throw new UnsupportedUnitException("UnitType " + unitType + " is not supported.");
        }
        return unit;
    }
}
