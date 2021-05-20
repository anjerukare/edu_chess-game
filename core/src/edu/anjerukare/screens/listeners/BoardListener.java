package edu.anjerukare.screens.listeners;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import edu.anjerukare.Assets;
import edu.anjerukare.Chess;
import edu.anjerukare.screens.models.Board;
import edu.anjerukare.screens.models.Piece;
import edu.anjerukare.screens.models.pieces.King;
import edu.anjerukare.screens.models.pieces.Pawn;
import edu.anjerukare.screens.models.pieces.Rook;
import edu.anjerukare.screens.utils.Point;
import edu.anjerukare.screens.views.*;
import edu.anjerukare.screens.views.pieces.KingView;
import edu.anjerukare.screens.views.pieces.PawnView;
import edu.anjerukare.screens.views.pieces.RookView;

import java.util.List;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.delay;
import static edu.anjerukare.Assets.*;
import static edu.anjerukare.screens.enums.PieceType.KING;
import static edu.anjerukare.screens.enums.Team.WHITE;
import static edu.anjerukare.screens.enums.PieceType.PAWN;
import static edu.anjerukare.screens.views.GameInfoView.BLACK_MOVE;
import static edu.anjerukare.screens.views.GameInfoView.WHITE_MOVE;
import static edu.anjerukare.screens.views.TileView.State.SELECTED;
import static edu.anjerukare.screens.views.GameOverView.GameResult.CHECKMATE;
import static edu.anjerukare.screens.views.GameOverView.GameResult.STALEMATE;

public class BoardListener extends ClickListener {

    private final Chess game;

    protected final Board board;
    protected final BoardView boardView;
    protected final PawnPromotingView pawnPromotingView;
    protected final GameOverView gameOverView;
    protected final GameInfoView gameInfoView;

    private final GameOverListener gameOverListener;

    public BoardListener(Board board, BoardView boardView, PawnPromotingView pawnPromotingView,
                         GameOverView gameOverView, GameInfoView gameInfoView, GameOverListener gameOverListener) {
        this.gameOverListener = gameOverListener;
        game = (Chess) Gdx.app.getApplicationListener();

        this.board = board;
        this.boardView = boardView;
        this.pawnPromotingView = pawnPromotingView;
        this.gameOverView = gameOverView;
        this.gameInfoView = gameInfoView;
    }

    @Override
    public void clicked(InputEvent event, float x, float y) {
        TileView tile = (TileView) event.getTarget();
        Point tilePos = boardView.getPointForTile(tile);
        Point point = boardView.getPointForPiece(boardView.selectedPiece);

        switch (tile.state) {
            case DEFAULT: {
                boardView.resetTileStates();

                if (tile.piece != null && board.doesPieceBelongToCurrentPlayer(board.getPieceAt(tilePos))) {
                    tile.state = SELECTED;
                    boardView.selectedPiece = tile.piece;
                    Piece piece = board.getPieceAt(tilePos);
                    boardView.markTiles(board.getMovesForPiece(piece));

                    if (piece.type == KING) {
                        King king = (King) piece;
                        List<Point> castlingLocations = board.getCastlingMoves(king);
                        if (castlingLocations != null) {
                            boardView.markTilesAsCastlingAvailable(castlingLocations);
                        }
                    }

                    if (piece.type == PAWN) {
                        Pawn pawn = (Pawn) piece;
                        Point enPassantLocation = board.getEnPassantMove(pawn);
                        if (enPassantLocation != null) {
                            boardView.markTileAsEnPassantAvailable(enPassantLocation);
                        }
                    }
                }
                break;
            }
            case MOVEAVAILABLE: {
                boardView.resetTileStates();

                boardView.movePiece(boardView.selectedPiece, tilePos);
                Piece piece = board.getPieceAt(point);
                board.move(piece, tilePos);
                Assets.get(pieceMoveSound).play(0.4f);

                updateCheckState();

                if (piece.type == PAWN && (tilePos.y == 0 || tilePos.y == 7)) {
                    boardView.setOverlapped(true);

                    pawnPromotingView.setPawn((PawnView) boardView.selectedPiece);
                    showPromotingPopup(tile.getX(), tile.getY());
                } else {
                    turnIsDone();
                }

                updateCheckState();
                if (board.hasCurrentPlayerNoMoves()) {
                    gameIsOver();
                }
                break;
            }
            case CAPTUREAVAILABLE: {
                boardView.resetTileStates();

                PieceView targetPiece = boardView.getPieceAt(tilePos);
                boardView.removePiece(targetPiece);
                boardView.movePiece(boardView.selectedPiece, tilePos);
                board.capturePieceAt(tilePos);
                Piece piece = board.getPieceAt(point);
                board.move(piece, tilePos);
                Assets.get(pieceCaptureSound).play(0.3f);

                updateCheckState();

                if (piece.type == PAWN && (tilePos.y == 0 || tilePos.y == 7)) {
                    boardView.setOverlapped(true);

                    pawnPromotingView.setPawn((PawnView) boardView.selectedPiece);
                    showPromotingPopup(tile.getX(), tile.getY());
                } else {
                    turnIsDone();
                }

                updateCheckState();
                if (board.hasCurrentPlayerNoMoves()) {
                    gameIsOver();
                }
                break;
            }
            case CASTLINGAVAILABLE: {
                boardView.resetTileStates();

                Point kingPosition = boardView.getPointForPiece(boardView.selectedPiece);
                boardView.castlePieces((KingView) boardView.selectedPiece,
                        (RookView) tile.piece);
                board.castle((King) board.getPieceAt(kingPosition), (Rook) board.getPieceAt(tilePos));
                Assets.get(pieceMoveSound).play(0.4f);

                turnIsDone();
                updateCheckState();
                if (board.hasCurrentPlayerNoMoves()) {
                    gameIsOver();
                }
                break;
            }
            case ENPASSANTAVAILABLE: {
                boardView.resetTileStates();

                int forwardDirection = board.currentPlayer.forward;
                Point enPassantTarget = new Point(tilePos.x, tilePos.y - forwardDirection);
                boardView.removePiece(boardView.getPieceAt(enPassantTarget));
                boardView.movePiece(boardView.selectedPiece, tilePos);
                board.capturePieceAt(enPassantTarget);
                board.move(board.getPieceAt(point), tilePos);
                Assets.get(pieceCaptureSound).play(0.3f);

                updateCheckState();

                turnIsDone();
                updateCheckState();
                if (board.hasCurrentPlayerNoMoves()) {
                    gameIsOver();
                }
                break;
            }
        }
    }

    private void showPromotingPopup(float x, float y) {
        PawnView pawn = pawnPromotingView.getPawn();
        pawnPromotingView.setPosition(x + boardView.getX(),
                y + boardView.getY() + (pawn.team == WHITE ? 44 : 0),
                (pawn.team == WHITE ? Align.topLeft : Align.bottomLeft));
        pawnPromotingView.setVisible(true);
    }

    public void updateCheckState() {
        Point kingPosition = board.getPointForPiece(board.getCurrentPlayerKing());
        KingView kingView = (KingView) boardView.getPieceAt(kingPosition);
        if (board.isCheck()) {
            Assets.get(checkSound).play(0.15f);
            kingView.checked = true;
        } else {
            kingView.checked = false;
        }
    }

    private void turnIsDone() {
        board.passTurnToNextPlayer();
        if (board.getCurrentPlayerTeam() == WHITE)
            gameInfoView.setLabelText(WHITE_MOVE);
        else
            gameInfoView.setLabelText(BLACK_MOVE);
    }

    private void gameIsOver() {
        if (board.isCheck())
            gameOverListener.showViewWith(CHECKMATE);
        else
            gameOverListener.showViewWith(STALEMATE);
    }
}
