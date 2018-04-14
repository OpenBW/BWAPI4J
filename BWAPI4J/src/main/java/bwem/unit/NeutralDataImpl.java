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

package bwem.unit;

import bwem.map.Map;
import org.openbw.bwapi4j.unit.Building;
import org.openbw.bwapi4j.unit.MineralPatch;
import org.openbw.bwapi4j.unit.PlayerUnit;
import org.openbw.bwapi4j.unit.Unit;
import org.openbw.bwapi4j.unit.VespeneGeyser;

import java.util.ArrayList;
import java.util.List;

public class NeutralDataImpl implements NeutralData {

    private final List<Mineral> minerals;
    private final List<Geyser> geysers;
    private final List<StaticBuilding> staticBuildings;

    public NeutralDataImpl(
            final Map map,
            final List<MineralPatch> mineralPatches,
            final List<VespeneGeyser> vespeneGeysers,
            final List<PlayerUnit> neutralUnits
    ) {

        ////////////////////////////////////////////////////////////////////////
        // MapImpl::InitializeNeutrals
        ////////////////////////////////////////////////////////////////////////

        this.minerals = new ArrayList<>();
        for (final MineralPatch mineralPatch : mineralPatches) {
            this.minerals.add(new Mineral(mineralPatch, map));
        }

        this.geysers = new ArrayList<>();
        for (final VespeneGeyser vespeneGeyser : vespeneGeysers) {
            this.geysers.add(new Geyser(vespeneGeyser, map));
        }

        this.staticBuildings = new ArrayList<>();
        for (final Unit neutralUnit : neutralUnits) {
            if (neutralUnit instanceof Building) {
                this.staticBuildings.add(new StaticBuilding(neutralUnit, map));
            }
        }

        //TODO: Add "Special_Pit_Door" and "Special_Right_Pit_Door" to static buildings list? See mapImpl.cpp:238.
//				if (n->getType() == Special_Pit_Door)
//					m_StaticBuildings.push_back(make_unique<StaticBuilding>(n, this));
//				if (n->getType() == Special_Right_Pit_Door)
//					m_StaticBuildings.push_back(make_unique<StaticBuilding>(n, this));

        ////////////////////////////////////////////////////////////////////////

    }

    @Override
    public List<Mineral> getMinerals() {
        return this.minerals;
    }

    @Override
    public Mineral getMineral(final Unit unit) {
        for (final Mineral mineral : getMinerals()) {
            if (mineral.getUnit().equals(unit)) {
                return mineral;
            }
        }
        return null;
    }

    @Override
    public List<Geyser> getGeysers() {
        return this.geysers;
    }

    @Override
    public Geyser getGeyser(final Unit unit) {
        for (final Geyser geyser : getGeysers()) {
            if (geyser.getUnit().equals(unit)) {
                return geyser;
            }
        }
        return null;
    }

    @Override
    public List<StaticBuilding> getStaticBuildings() {
        return this.staticBuildings;
    }

}
