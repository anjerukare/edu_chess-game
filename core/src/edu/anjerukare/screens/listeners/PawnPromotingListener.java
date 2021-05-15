package edu.anjerukare.screens.listeners;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import edu.anjerukare.screens.models.Board;
import edu.anjerukare.screens.models.Piece;
import edu.anjerukare.screens.models.pieces.Pawn;
import edu.anjerukare.screens.utils.Point;
import edu.anjerukare.screens.views.*;
import edu.anjerukare.screens.views.pieces.BishopView;
import edu.anjerukare.screens.views.pieces.KnightView;
import edu.anjerukare.screens.views.pieces.QueenView;
import edu.anjerukare.screens.views.pieces.RookView;

import static edu.anjerukare.screens.views.VictoryView.GameResult.CHECKMATE;
import static edu.anjerukare.screens.views.VictoryView.GameResult.STALEMATE;

public class PawnPromotingListener extends ClickListener {

    private final Board board;
    private final BoardView boardView;
    private final PawnPromotingView pawnPromotingView;

    private final BoardListener boardListener;
    private final VictoryListener victoryListener;

    public PawnPromotingListener(Board board, BoardView boardView, PawnPromotingView pawnPromotingView,
                                 BoardListener boardListener, VictoryListener victoryListener) {
        this.board = board;
        this.boardView = boardView;
        this.pawnPromotingView = pawnPromotingView;
        this.boardListener = boardListener;
        this.victoryListener = victoryListener;
    }

    @Override
    public void clicked(InputEvent event, float x, float y) {
        PieceView pieceView = (PieceView) event.getTarget();
        Point point = boardView.getPointForPiece(pawnPromotingView.getPawn());

        TileView tile = boardView.getTileAt(point);
        switch (pieceView.type) {
            case QUEEN:
                boardView.setPiece(point, new QueenView(tile.piece.team));
                break;
            case KNIGHT:
                boardView.setPiece(point, new KnightView(tile.piece.team));
                break;
            case BISHOP:
                boardView.setPiece(point, new BishopView(tile.piece.team));
                break;
            case ROOK:
                boardView.setPiece(point, new RookView(tile.piece.team));
                break;
        }
        Piece piece = board.getPieceAt(point);
        board.promotePawn((Pawn) piece, pieceView.type);

        pawnPromotingView.setVisible(false);
        boardView.setOverlapped(false);
        board.passTurnToNextPlayer();
        boardListener.updateCheckState();
        if (board.hasCurrentPlayerNoMoves()) {
            if (board.isCheck())
                victoryListener.showViewWith(CHECKMATE);
            else
                victoryListener.showViewWith(STALEMATE);
        }
    }
}
