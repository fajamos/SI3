package GameModel;

import GameModel.Players.Player;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class Pawn {
    private Player player;
    private Coord position = null;
    private Coord lastPosition = null;

    public Pawn(Player player){
        this.player = player;
    }

    public Pawn(Pawn p) {
        position = p.position;
        lastPosition = p.lastPosition;
    }
}
