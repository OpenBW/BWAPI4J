package bwem;

//struct Bits
//{
//                    Bits() : buildable(0), groundHeight(0), doodad(0) {}
//    uint8_t			buildable:1;
//    uint8_t			groundHeight:2;
//    uint8_t			doodad:1;
//};
public class Bits {

    public byte buildable;
    public byte groundHeight;
    public byte doodad;

    public Bits() {
        buildable = 0x0;
        groundHeight = 0x0;
        doodad = 0x0;
    }

}
