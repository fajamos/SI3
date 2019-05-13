package GameModel;

import GameModel.Players.Player;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Turn {
    private final TurnType type;
    private final Player player;
    private final Coord coord;
    private final Coord to;

    public enum TurnType{
        PLACE,
        MOVE,
        REMOVE;
    }
}
