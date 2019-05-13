package GameModel;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Coord {
    private final int ring;
    private final int x;
    private final int y;

    @Override
    public String toString() {
        String ringText = "";
        switch (ring){
            case 0:
                ringText = "Outer";
                break;
            case 1:
                ringText = "Middle";
                break;
            case 2:
                ringText = "Inner";
                break;
            default:
                ringText = "Invalid Coord";
                return ringText;
        }
        return String.format("%s(%d,%d)",ringText,x,y);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Coord){
            Coord o = (Coord) obj;
            return ring==o.ring && x==o.x && y==o.y;
        }
        return false;
    }
}
