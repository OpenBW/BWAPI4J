////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (C) 2017-2018 OpenBW Team
//
//    This file is part of BWAPI4J.
//
//    BWAPI4J is free software: you can redistribute it and/or modify
//    it under the terms of the Lesser GNU General Public License as published 
//    by the Free Software Foundation, version 3 only.
//
//    BWAPI4J is distributed in the hope that it will be useful,
//    but WITHOUT ANY WARRANTY; without even the implied warranty of
//    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//    GNU General Public License for more details.
//
//    You should have received a copy of the GNU General Public License
//    along with BWAPI4J.  If not, see <http://www.gnu.org/licenses/>.
//
////////////////////////////////////////////////////////////////////////////////

package org.openbw.bwapi4j.unit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openbw.bwapi4j.BW;
import org.openbw.bwapi4j.type.UnitType;

public class UnitFactory {

    private static final Logger logger = LogManager.getLogger();
    
    private BW bw;
    
    public UnitFactory() {
    	
    }
    
    public final void setBW(BW bw) {
    	this.bw = bw;
    }
    
    protected MineralPatch getMineralPatch(int unitId, int timeSpotted) {
    	
		return new MineralPatch(unitId);
	}

	protected VespeneGeyser getVespeneGeyser(int unitId, int timeSpotted) {

		return new VespeneGeyser(unitId);
	}

	protected Refinery getRefinery(int unitId, int timeSpotted) {

		return new Refinery(unitId, timeSpotted);
	}

	protected SCV getSCV(int unitId, int timeSpotted) {

    	return new SCV(unitId);
    }
	
    protected Marine getMarine(int unitId, int timeSpotted) {
    	
    	return new Marine(unitId);
    }
    
    protected Medic getMedic(int unitId, int timeSpotted) {
    	
    	return new Medic(unitId);
    }
    
    protected Firebat getFirebat(int unitId, int timeSpotted) {
    	
    	return new Firebat(unitId);
    }
    
    protected Ghost getGhost(int unitId, int timeSpotted) {
    	
    	return new Ghost(unitId);
    }
    
	protected Vulture getVulture(int unitId, int timeSpotted) {
	    	
    	return new Vulture(unitId);
    }
	
	protected Goliath getGoliath(int unitId, int timeSpotted) {
    	
    	return new Goliath(unitId);
    }
	
	protected SiegeTank getSiegeTank(int unitId, int timeSpotted, boolean sieged) {
		
		return new SiegeTank(unitId, sieged);
	}
	
	protected Wraith getWraith(int unitId, int timeSpotted) {
		
		return new Wraith(unitId);
	}
	
	protected Dropship getDropship(int unitId, int timeSpotted) {
		
		return new Dropship(unitId);
	}
	
	protected Valkyrie getValkyrie(int unitId, int timeSpotted) {
		
		return new Valkyrie(unitId);
	}
	
	protected BattleCruiser getBattleCruiser(int unitId, int timeSpotted) {
		
		return new BattleCruiser(unitId);
	}
	
	protected ScienceVessel getScienceVessel(int unitId, int timeSpotted) {
		
		return new ScienceVessel(unitId);
	}
    
	// TODO all the other getters
	
    /**
     * Creates a unit for given ID and type.
     * @param unitId ID of the unit to create
     * @param unitType type of the unit to create
     * @param timeSpotted time stamp when unit first appeared
     * @return the created unit
     */
    public final Unit createUnit(int unitId, UnitType unitType, int timeSpotted) {

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
            unit = getBattleCruiser(unitId, timeSpotted);
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
            unit = getDropship(unitId, timeSpotted);
            break;
        case Terran_Engineering_Bay:
            unit = new EngineeringBay(unitId, timeSpotted);
            break;
        case Terran_Factory:
            unit = new Factory(unitId, timeSpotted);
            break;
        case Terran_Firebat:
            unit = getFirebat(unitId, timeSpotted);
            break;
        case Terran_Ghost:
            unit = getGhost(unitId, timeSpotted);
            break;
        case Terran_Goliath:
            unit = getGoliath(unitId, timeSpotted);
            break;
        case Terran_Machine_Shop:
            unit = new MachineShop(unitId, timeSpotted);
            break;
        case Terran_Marine:
            unit = getMarine(unitId, timeSpotted);
            break;
        case Terran_Medic:
            unit = getMedic(unitId, timeSpotted);
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
            unit = getRefinery(unitId, timeSpotted);
            break;
        case Terran_Science_Facility:
            unit = new ScienceFacility(unitId, timeSpotted);
            break;
        case Terran_Science_Vessel:
            unit = getScienceVessel(unitId, timeSpotted);
            break;
        case Terran_SCV:
            unit = getSCV(unitId, timeSpotted);
            break;
        case Terran_Siege_Tank_Tank_Mode:
        	unit = getSiegeTank(unitId, timeSpotted, false);
            break;
        case Terran_Siege_Tank_Siege_Mode:
            unit = getSiegeTank(unitId, timeSpotted, true);
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
            unit = getValkyrie(unitId, timeSpotted);
            break;
        case Terran_Vulture:
            unit = getVulture(unitId, timeSpotted);
            break;
        case Terran_Wraith:
            unit = getWraith(unitId, timeSpotted);
            break;
        case Zerg_Zergling:
            unit = new Zergling(unitId);
            break;
        case Zerg_Hydralisk:
            unit = new Hydralisk(unitId);
            break;
        case Zerg_Ultralisk:
            unit = new Ultralisk(unitId);
            break;
        case Zerg_Broodling:
            unit = new Broodling(unitId);
            break;
        case Zerg_Drone:
            unit = new Drone(unitId);
            break;
        case Zerg_Overlord:
            unit = new Overlord(unitId);
            break;
        case Zerg_Mutalisk:
            unit = new Mutalisk(unitId);
            break;
        case Zerg_Guardian:
            unit = new Guardian(unitId);
            break;
        case Zerg_Queen:
            unit = new Queen(unitId);
            break;
        case Zerg_Defiler:
            unit = new Defiler(unitId);
            break;
        case Zerg_Scourge:
            unit = new Scourge(unitId);
            break;
        case Zerg_Lurker:
            unit = new Lurker(unitId);
            break;
        case Zerg_Infested_Terran:
            unit = new InfestedTerran(unitId);
            break;
        case Zerg_Devourer:
            unit = new Devourer(unitId);
            break;
        case Zerg_Larva:
            unit = new Larva(unitId);
            break;
        case Zerg_Egg:
            unit = new Egg(unitId);
            break;
        case Zerg_Cocoon:
            unit = new Cocoon(unitId);
            break;
        case Zerg_Lurker_Egg:
            unit = new LurkerEgg(unitId);
            break;
        case Resource_Mineral_Field:
        case Resource_Mineral_Field_Type_2:
        case Resource_Mineral_Field_Type_3:
            unit = getMineralPatch(unitId, timeSpotted);
            break;
        case Resource_Vespene_Geyser:
            unit = getVespeneGeyser(unitId, timeSpotted);
            break;
        case Terran_Nuclear_Missile:
            unit = new NuclearMissile(unitId);
            break;
        case Zerg_Infested_Command_Center:
            unit = new InfestedCommandCenter(unitId, timeSpotted);
            break;
        case Zerg_Hatchery:
            unit = new Hatchery(unitId, timeSpotted);
            break;
        case Zerg_Lair:
            unit = new Lair(unitId, timeSpotted);
            break;
        case Zerg_Hive:
            unit = new Hive(unitId, timeSpotted);
            break;
        case Zerg_Nydus_Canal:
            unit = new NydusCanal(unitId, timeSpotted);
            break;
        case Zerg_Hydralisk_Den:
            unit = new HydraliskDen(unitId, timeSpotted);
            break;
        case Zerg_Defiler_Mound:
            unit = new DefilerMound(unitId, timeSpotted);
            break;
        case Zerg_Greater_Spire:
            unit = new GreaterSpire(unitId, timeSpotted);
            break;
        case Zerg_Queens_Nest:
            unit = new QueensNest(unitId, timeSpotted);
            break;
        case Zerg_Evolution_Chamber:
            unit = new EvolutionChamber(unitId, timeSpotted);
            break;
        case Zerg_Ultralisk_Cavern:
            unit = new UltraliskCavern(unitId, timeSpotted);
            break;
        case Zerg_Spire:
            unit = new Spire(unitId, timeSpotted);
            break;
        case Zerg_Spawning_Pool:
            unit = new SpawningPool(unitId, timeSpotted);
            break;
        case Zerg_Creep_Colony:
            unit = new CreepColony(unitId, timeSpotted);
            break;
        case Zerg_Spore_Colony:
            unit = new SporeColony(unitId, timeSpotted);
            break;
        case Zerg_Sunken_Colony:
            unit = new SunkenColony(unitId, timeSpotted);
            break;
        case Zerg_Extractor:
            unit = new Extractor(unitId, timeSpotted);
            break;
        case Protoss_Corsair:
            unit = new Corsair(unitId);
            break;
        case Protoss_Dark_Templar:
            unit = new DarkTemplar(unitId);
            break;
        case Protoss_Dark_Archon:
            unit = new DarkArchon(unitId);
            break;
        case Protoss_Probe:
            unit = new Probe(unitId);
            break;
        case Protoss_Zealot:
            unit = new Zealot(unitId);
            break;
        case Protoss_Dragoon:
            unit = new Dragoon(unitId);
            break;
        case Protoss_High_Templar:
            unit = new HighTemplar(unitId);
            break;
        case Protoss_Archon:
            unit = new Archon(unitId);
            break;
        case Protoss_Shuttle:
            unit = new Shuttle(unitId);
            break;
        case Protoss_Scout:
            unit = new Scout(unitId);
            break;
        case Protoss_Arbiter:
            unit = new Arbiter(unitId);
            break;
        case Protoss_Carrier:
            unit = new Carrier(unitId);
            break;
        case Protoss_Interceptor:
            unit = new Interceptor(unitId);
            break;
        case Protoss_Reaver:
            unit = new Reaver(unitId);
            break;
        case Protoss_Observer:
            unit = new Observer(unitId);
            break;
        case Protoss_Scarab:
            unit = new Scarab(unitId);
            break;
        case Protoss_Nexus:
        	unit = new Nexus(unitId, timeSpotted);
        	break;
        case Protoss_Robotics_Facility:
        	unit = new RoboticsFacility(unitId, timeSpotted);
        	break;
        case Protoss_Pylon:
        	unit = new Pylon(unitId, timeSpotted);
        	break;
        case Protoss_Assimilator:
        	unit = new Assimilator(unitId, timeSpotted);
        	break;
        case Protoss_Observatory:
        	unit = new Observatory(unitId, timeSpotted);
        	break;
        case Protoss_Gateway:
        	unit = new Gateway(unitId, timeSpotted);
        	break;
        case Protoss_Photon_Cannon:
        	unit = new PhotonCannon(unitId, timeSpotted);
        	break;
        case Protoss_Citadel_of_Adun:
        	unit = new CitadelOfAdun(unitId, timeSpotted);
        	break;
        case Protoss_Cybernetics_Core:
        	unit = new CyberneticsCore(unitId, timeSpotted);
        	break;
        case Protoss_Templar_Archives:
        	unit = new TemplarArchives(unitId, timeSpotted);
        	break;
        case Protoss_Forge:
        	unit = new Forge(unitId, timeSpotted);
        	break;
        case Protoss_Stargate:
        	unit = new Stargate(unitId, timeSpotted);
        	break;
        case Protoss_Fleet_Beacon:
        	unit = new FleetBeacon(unitId, timeSpotted);
        	break;
        case Protoss_Arbiter_Tribunal:
        	unit = new ArbiterTribunal(unitId, timeSpotted);
        	break;
        case Protoss_Robotics_Support_Bay:
        	unit = new RoboticsSupportBay(unitId, timeSpotted);
        	break;
        case Protoss_Shield_Battery:
        	unit = new ShieldBattery(unitId, timeSpotted);
        	break;
        case Spell_Scanner_Sweep:
            unit = new ScannerSweep(unitId, timeSpotted);
            break;
        case Spell_Disruption_Web:
            unit = new DisruptionWeb(unitId, timeSpotted);
            break;
        case Spell_Dark_Swarm:
            unit = new DarkSwarm(unitId, timeSpotted);
            break;
        // treat all critter units as the same class
        case Critter_Rhynadon:
        case Critter_Bengalaas:
        case Critter_Scantid:
        case Critter_Kakaru:
        case Critter_Ragnasaur:
        case Critter_Ursadon:
            unit = new Critter(unitId, unitType);
            break;
        
        // treat the following special units as the same class
        case Special_Cargo_Ship:
        case Special_Mercenary_Gunship:
        case Special_Floor_Missile_Trap:
        case Special_Floor_Hatch:
        case Special_Upper_Level_Door:
        case Special_Right_Upper_Level_Door:
        case Special_Pit_Door:
        case Special_Right_Pit_Door:
        case Special_Floor_Gun_Trap:
        case Special_Wall_Missile_Trap:
        case Special_Wall_Flame_Trap:
        case Special_Right_Wall_Missile_Trap:
        case Special_Right_Wall_Flame_Trap:
        	unit = new SpecialUnit(unitId, unitType);
            break;

        case Special_Independant_Starport:
        case Special_Ion_Cannon:
        case Special_Crashed_Norad_II:
        case Special_Psi_Disrupter:
        case Special_Khaydarin_Crystal_Form:
        case Special_Protoss_Temple:
        case Special_XelNaga_Temple:
        case Special_Power_Generator:
        case Special_Warp_Gate:
        case Special_Cerebrate:
        case Special_Cerebrate_Daggoth:
        case Special_Mature_Chrysalis:
        case Special_Overmind:
        case Special_Overmind_Cocoon:
        case Special_Overmind_With_Shell:
        case Special_Stasis_Cell_Prison:
            unit = new SpecialBuilding(unitId, unitType, timeSpotted);
            break;

        // ignore special pseudo units
        case Special_Start_Location:
        case Special_Zerg_Flag_Beacon:
        case Special_Terran_Flag_Beacon:
        case Special_Protoss_Flag_Beacon:
        case Special_Zerg_Beacon:
        case Special_Terran_Beacon:
        case Special_Protoss_Beacon:
        case Special_Map_Revealer:
            
        // ignore turrets (for now)
        case Terran_Goliath_Turret:
        case Terran_Siege_Tank_Tank_Mode_Turret:
        case Terran_Siege_Tank_Siege_Mode_Turret:
            
        // ignore all hero units
        case Terran_Civilian:
            
        case Hero_Gui_Montag:
        case Hero_Sarah_Kerrigan:
        case Hero_Alan_Schezar:
        case Hero_Alan_Schezar_Turret:
        case Hero_Jim_Raynor_Vulture:
        case Hero_Jim_Raynor_Marine:
        case Hero_Tom_Kazansky:
        case Hero_Magellan:
        case Hero_Edmund_Duke_Tank_Mode:
        case Hero_Edmund_Duke_Tank_Mode_Turret:
        case Hero_Edmund_Duke_Siege_Mode:
        case Hero_Edmund_Duke_Siege_Mode_Turret:
        case Hero_Arcturus_Mengsk:
        case Hero_Hyperion:
        case Hero_Norad_II:
        case Hero_Torrasque:
        case Hero_Matriarch:
        case Hero_Infested_Kerrigan:
        case Hero_Unclean_One:
        case Hero_Hunter_Killer:
        case Hero_Devouring_One:
        case Hero_Kukulza_Mutalisk:
        case Hero_Kukulza_Guardian:
        case Hero_Yggdrasill:
        case Hero_Dark_Templar:
        case Hero_Zeratul:
        case Hero_Tassadar_Zeratul_Archon:
        case Hero_Fenix_Zealot:
        case Hero_Fenix_Dragoon:
        case Hero_Tassadar:
        case Hero_Mojo:
        case Hero_Warbringer:
        case Hero_Gantrithor:
        case Hero_Danimoth:
        case Hero_Aldaris:
        case Hero_Artanis:
        case Hero_Raszagal:
        case Hero_Samir_Duran:
        case Hero_Alexei_Stukov:
        case Hero_Gerard_DuGalle:
        case Hero_Infested_Duran:
            
        // ignore all unused units
        case Unused_Terran1:
        case Unused_Terran2:
        case Unused_Protoss1:
        case Unused_Protoss2:
        case Unused_Zerg1:
        case Unused_Zerg2:
        case Unused_Cave:
        case Unused_Cave_In:
        case Unused_Cantina:
        case Unused_Mining_Platform:
        case Unused_Independant_Command_Center:
        case Unused_Independant_Jump_Gate:
        case Unused_Ruins:
        case Unused_Khaydarin_Crystal_Formation:
        case Unused_Zerg_Marker:
        case Unused_Terran_Marker:
        case Unused_Protoss_Marker:
        
        // ignore all powerup units
        case Powerup_Uraj_Crystal:
        case Powerup_Khalis_Crystal:
        case Powerup_Flag:
        case Powerup_Young_Chrysalis:
        case Powerup_Psi_Emitter:
        case Powerup_Data_Disk:
        case Powerup_Khaydarin_Crystal:
        case Powerup_Mineral_Cluster_Type_1:
        case Powerup_Mineral_Cluster_Type_2:
        case Powerup_Protoss_Gas_Orb_Type_1:
        case Powerup_Protoss_Gas_Orb_Type_2:
        case Powerup_Zerg_Gas_Sac_Type_1:
        case Powerup_Zerg_Gas_Sac_Type_2:
        case Powerup_Terran_Gas_Tank_Type_1:
        case Powerup_Terran_Gas_Tank_Type_2:
            
            unit = null;
            break;
        default:
            logger.error("UnitType {} is not supported.", unitType);
            throw new UnsupportedUnitException("UnitType " + unitType + " is not supported.");
        }
        
        if (unit != null) {
        	
            unit.setBW(bw);
        }
        
        return unit;
    }
}
