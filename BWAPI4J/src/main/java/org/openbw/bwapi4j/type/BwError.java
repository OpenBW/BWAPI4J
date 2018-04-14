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

package org.openbw.bwapi4j.type;

public enum BwError {

    Unit_Does_Not_Exist,
    Unit_Not_Visible,
    Unit_Not_Owned,
    Unit_Busy,
    Incompatible_UnitType,
    Incompatible_TechType,
    Incompatible_State,
    Already_Researched,
    Fully_Upgraded,
    Currently_Researching,
    Currently_Upgrading,
    Insufficient_Minerals,
    Insufficient_Gas,
    Insufficient_Supply,
    Insufficient_Energy,
    Insufficient_Tech,
    Insufficient_Ammo,
    Insufficient_Space,
    Invalid_Tile_Position,
    Unbuildable_Location,
    Unreachable_Location,
    Out_Of_Range,
    Unable_To_Hit,
    Access_Denied,
    File_Not_Found,
    Invalid_Parameter,
    None,
    Unknown;
}
