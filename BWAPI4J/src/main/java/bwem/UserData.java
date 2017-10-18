/*
Status: Ready for use
*/

package bwem;

import org.apache.commons.lang3.mutable.MutableInt;

// utils.h
//////////////////////////////////////////////////////////////////////////////////////////////
//                                                                                          //
//                                  class UserData
//                                                                                          //
//////////////////////////////////////////////////////////////////////////////////////////////
//
//  Provides free-to-use, intrusive data for several types of the BWEM library
//	Despite their names and their types, they can be used for any purpose.
//
//////////////////////////////////////////////////////////////////////////////////////////////

public final class UserData {

    private MutableInt m_data;

    public UserData() {
        m_data = new MutableInt(0);
    }

    public UserData(MutableInt data) {
        m_data = data;
    }

    public MutableInt Data() {
        return m_data;
    }

    public void SetData(MutableInt data) {
        m_data = data;
    }

}
