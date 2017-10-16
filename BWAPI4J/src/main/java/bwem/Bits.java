package bwem;

//struct Bits
//{
//                    Bits() : buildable(0), groundHeight(0), doodad(0) {}
//    uint8_t			buildable:1;
//    uint8_t			groundHeight:2;
//    uint8_t			doodad:1;
//};
public final class Bits {

    public byte buildable;
    public byte groundHeight;
    public byte doodad;

    public Bits() {
        this.buildable = 0x0;
        this.groundHeight = 0x0;
        this.doodad = 0x0;
    }

    public Bits(Bits bits) {
        this.buildable = bits.buildable;
        this.groundHeight = bits.groundHeight;
        this.doodad = bits.doodad;
    }

}
