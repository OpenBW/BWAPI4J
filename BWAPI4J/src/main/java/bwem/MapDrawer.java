// Original work Copyright (c) 2015, 2017, Igor Dimitrijevic 
// Modified work Copyright (c) 2017-2018 OpenBW Team

//////////////////////////////////////////////////////////////////////////
//
// This file is part of the BWEM Library.
// BWEM is free software, licensed under the MIT/X11 License. 
// A copy of the license is provided with the library in the LICENSE file.
// Copyright (c) 2015, 2017, Igor Dimitrijevic
//
//////////////////////////////////////////////////////////////////////////

package bwem;

import org.apache.commons.lang3.mutable.MutableBoolean;
import org.openbw.bwapi4j.BW;
import org.openbw.bwapi4j.type.Color;

//////////////////////////////////////////////////////////////////////////////////////////////
//                                                                                          //
//                                  class MapDrawer
//                                                                                          //
//////////////////////////////////////////////////////////////////////////////////////////////

public class MapDrawer {

    private enum Command {

        SHOW_SEAS("seas"),
        SHOW_LAKES("lakes"),
        SHOW_UNBUILDABLE("unbuildable"),
        SHOW_GROUND_HEIGHT("gh"),
        SHOW_MINERALS("minerals"),
        SHOW_GEYSERS("geysers"),
        SHOW_STATIC_BUILDINGS("static buildings"),
        SHOW_BASES("bases"),
        SHOW_ASSIGNED_RESOURCES("assigned resources"),
        SHOW_FRONTIER("frontier"),
        SHOW_CP("cp");

        private final String command;

        Command(String command) {
            this.command = command;
        }

        @Override
        public String toString() {
            return this.command;
        }

    }

    public enum SpecialColor {

        SEA(Color.BLUE),
        LAKES(Color.BLUE),
        UNBUILDABLE(Color.BROWN),
        HIGH_GROUND(Color.BROWN),
        VERY_HIGH_GROUND(Color.RED),
        MINERALS(Color.CYAN),
        GEYSERS(Color.GREEN),
        STATIC_BUILDINGS(Color.PURPLE),
        BASES(Color.BLUE),
        ASSIGNED_RESOURCES(Color.GREY),
        FRONTIER(Color.GREY),
        CP(Color.WHITE);

        private final Color color;

        SpecialColor(Color color) {
            this.color = color;
        }

        public Color color() {
            return this.color;
        }

    }

    private final BW bw;
    private final MutableBoolean showSeas = new MutableBoolean(true);
    private final MutableBoolean showLakes = new MutableBoolean(true);
    private final MutableBoolean showUnbuildable = new MutableBoolean(true);
    private final MutableBoolean showGroundHeight = new MutableBoolean(true);
    private final MutableBoolean showMinerals = new MutableBoolean(true);
    private final MutableBoolean showGeysers = new MutableBoolean(true);
    private final MutableBoolean showStaticBuildings = new MutableBoolean(true);
    private final MutableBoolean showBases = new MutableBoolean(true);
    private final MutableBoolean showAssignedResources = new MutableBoolean(true);
    private final MutableBoolean showFrontier = new MutableBoolean(true);
    private final MutableBoolean showCP = new MutableBoolean(true);

    public MapDrawer(BW bw) {
        this.bw = bw;
    }

    public boolean processCommand(String command) {
        if (processCommandVariants(command, Command.SHOW_SEAS.toString(), showSeas)) return true;
        if (processCommandVariants(command, Command.SHOW_LAKES.toString(), showLakes)) return true;
        if (processCommandVariants(command, Command.SHOW_UNBUILDABLE.toString(), showUnbuildable)) return true;
        if (processCommandVariants(command, Command.SHOW_GROUND_HEIGHT.toString(), showGroundHeight)) return true;
        if (processCommandVariants(command, Command.SHOW_MINERALS.toString(), showMinerals)) return true;
        if (processCommandVariants(command, Command.SHOW_GEYSERS.toString(), showGeysers)) return true;
        if (processCommandVariants(command, Command.SHOW_STATIC_BUILDINGS.toString(), showStaticBuildings)) return true;
        if (processCommandVariants(command, Command.SHOW_BASES.toString(), showBases)) return true;
        if (processCommandVariants(command, Command.SHOW_ASSIGNED_RESOURCES.toString(), showAssignedResources)) return true;
        if (processCommandVariants(command, Command.SHOW_FRONTIER.toString(), showFrontier)) return true;
        if (processCommandVariants(command, Command.SHOW_CP.toString(), showCP)) return true;

        if (processCommandVariants(command, "all", showSeas))
        if (processCommandVariants(command, "all", showLakes))
        if (processCommandVariants(command, "all", showUnbuildable))
        if (processCommandVariants(command, "all", showGroundHeight))
        if (processCommandVariants(command, "all", showMinerals))
        if (processCommandVariants(command, "all", showGeysers))
        if (processCommandVariants(command, "all", showStaticBuildings))
        if (processCommandVariants(command, "all", showBases))
        if (processCommandVariants(command, "all", showAssignedResources))
        if (processCommandVariants(command, "all", showFrontier))
        if (processCommandVariants(command, "all", showCP))
            return true;

        return false;
    }


	private boolean processCommandVariants(String command, String attributName, MutableBoolean attribut) {
        if (command.equals("show " + attributName)) { attribut.setTrue(); return true; }
        if (command.equals("hide " + attributName)) { attribut.setFalse(); return true; }
        if (command.equals(attributName)) { attribut.setValue(!attribut.booleanValue()); return true; }
        return false;
    }

}
