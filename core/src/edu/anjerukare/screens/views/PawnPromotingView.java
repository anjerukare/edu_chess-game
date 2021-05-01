package edu.anjerukare.screens.views;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Group;
import edu.anjerukare.Assets;
import edu.anjerukare.screens.enums.Team;
import edu.anjerukare.screens.views.pieces.*;

import static com.badlogic.gdx.scenes.scene2d.Touchable.childrenOnly;
import static edu.anjerukare.Assets.piecesAtlas;
import static edu.anjerukare.screens.enums.Team.WHITE;

public class PawnPromotingView extends Group {

    private PawnView pawn;

    public PieceView[] pieces = new PieceView[4];
    public Team team = WHITE;

    private static final TextureAtlas atlas = Assets.get(piecesAtlas);

    public PawnPromotingView() {
        setSize(44, 176);
        setTouchable(childrenOnly);
        setVisible(false);

        addPiece(new QueenView(WHITE), 0);
        addPiece(new KnightView(WHITE), 1);
        addPiece(new BishopView(WHITE), 2);
        addPiece(new RookView(WHITE), 3);
    }

    public void addPiece(PieceView piece, int i) {
        pieces[i] = piece;
        piece.setPosition(0, 44 * i);
        addActor(piece);
    }

    public void setPiecesTeam(Team team) {
        if (this.team != team) {
            pieces[0].team = team;
            pieces[0].textureRegion = atlas.findRegion(
                    (team == WHITE ? "white" : "black") + "_queen");
            pieces[1].team = team;
            pieces[1].textureRegion = atlas.findRegion(
                    (team == WHITE ? "white" : "black") + "_knight");
            pieces[2].team = team;
            pieces[2].textureRegion = atlas.findRegion(
                    (team == WHITE ? "white" : "black") + "_bishop");
            pieces[3].team = team;
            pieces[3].textureRegion = atlas.findRegion(
                    (team == WHITE ? "white" : "black") + "_rook");
            this.team = team;
        }
    }

    public void setPawn(PawnView pawn) {
        setPiecesTeam(pawn.team);
        this.pawn = pawn;
    }

    public PawnView getPawn() {
        return pawn;
    }
}
