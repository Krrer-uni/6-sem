const PUZZLE_DIFFICULTY = 4;
const PUZZLE_HOVER_TINT = '#037ffc';

var _stage;
var _canvas;

var _img;
var _pieces;
var _puzzleWidth;
var _puzzleHeight;
var _pieceWidth;
var _pieceHeight;
var _currentPiece;
var _currentDropPiece;
var _emptyPiecePos;
var _emptyPiece;
var _availableMoves;

var _mouse;

function init() {
    _img = new Image();
    _img.addEventListener('load', onImage);
    _img.src = "car.png";
}

function onImage() {
    _pieceWidth = Math.floor(_img.width / PUZZLE_DIFFICULTY)
    _pieceHeight = Math.floor(_img.height / PUZZLE_DIFFICULTY)
    _puzzleWidth = _pieceWidth * PUZZLE_DIFFICULTY;
    _puzzleHeight = _pieceHeight * PUZZLE_DIFFICULTY;
    setCanvas();
    initPuzzle();
}

function setCanvas() {
    _canvas = document.getElementById('canvas');
    _stage = _canvas.getContext('2d');
    _canvas.width = _puzzleWidth;
    _canvas.height = _puzzleHeight;
    _canvas.style.border = "1px solid black";
}

function initPuzzle() {
    _pieces = [];
    _mouse = { x: 0, y: 0 };
    _currentPiece = null;
    _currentDropPiece = null;
    _stage.drawImage(_img, 0, 0, _puzzleWidth, _puzzleHeight, 0, 0, _puzzleWidth, _puzzleHeight);
    createTitle("Click to Start Puzzle");
    buildPieces();
}

function createTitle(msg) {
    _stage.fillStyle = "#808080";
    _stage.globalAlpha = .4;
    _stage.fillRect(100, _puzzleHeight - 40, _puzzleWidth - 200, 40);
    _stage.fillStyle = "#FFFFFF";
    _stage.globalAlpha = 1;
    _stage.textAlign = "center";
    _stage.textBaseline = "middle";
    _stage.font = "20px Arial";
    _stage.fillText(msg, _puzzleWidth / 2, _puzzleHeight - 20);
}

function buildPieces() {
    var i;
    var piece;
    var xPos = 0;
    var yPos = 0;
    for (i = 0; i < PUZZLE_DIFFICULTY * PUZZLE_DIFFICULTY; i++) {
        piece = {};
        piece.sx = xPos;
        piece.sy = yPos;
        _pieces.push(piece);
        xPos += _pieceWidth;
        if (xPos >= _puzzleWidth) {
            xPos = 0;
            yPos += _pieceHeight;
        }
    }
    document.onmousedown = shufflePuzzle;
}

function shufflePuzzle() {
    // _pieces = shuffleArray(_pieces);
    _stage.clearRect(0, 0, _puzzleWidth, _puzzleHeight);
    var i;
    var piece;
    var xPos = 0;
    var yPos = 0;
    for (i = 0; i < _pieces.length; i++) {
        piece = _pieces[i];
        piece.idx = i;
        piece.xPos = xPos;
        piece.yPos = yPos;
        if (piece.sx == 0 && piece.sy == 0) {
            _emptyPiecePos = i;
            _emptyPiece = piece;
            _stage.fillStyle = "blue";
            _stage.fillRect(xPos, yPos, _pieceWidth, _pieceHeight);
        } else {
            _stage.drawImage(_img, piece.sx, piece.sy, _pieceWidth, _pieceHeight, xPos, yPos, _pieceWidth, _pieceHeight);
            _stage.strokeRect(xPos, yPos, _pieceWidth, _pieceHeight);
        }
        xPos += _pieceWidth;
        if (xPos >= _puzzleWidth) {
            xPos = 0;
            yPos += _pieceHeight;
        }
    }
    for(i = 0; i < 200; i++){
        let availableMoves = checkAvailableMoves();
        let randidx = Math.floor(Math.random() * availableMoves.length);
        piece = availableMoves[randidx];
        var tmp = { sx: piece.sx, sy: piece.sy };
        piece.sx = _emptyPiece.sx;
        piece.sy = _emptyPiece.sy;
        _emptyPiece.sx = tmp.sx;
        _emptyPiece.sy = tmp.sy;
        const tmpPiece = _emptyPiece;
        _emptyPiece = piece;
        piece = tmpPiece;
    
        _emptyPiecePos = _emptyPiece.xPos / _pieceWidth + _emptyPiece.yPos /_pieceHeight * PUZZLE_DIFFICULTY;
    }
    _availableMoves = checkAvailableMoves();
    resetPuzzleAndCheckWin()

    document.onmousedown = onPuzzleClick;
    document.onmousemove = showAvailableMoves;
}

function showAvailableMoves(e) {
    if (e.layerX || e.layerX == 0) {
        _mouse.x = e.layerX - _canvas.offsetLeft;
        _mouse.y = e.layerY - _canvas.offsetTop;
    } else if (e.offsetX || e.offsetX == 0) {
        _mouse.x = e.offsetX - _canvas.offsetLeft;
        _mouse.y = e.offsetY - _canvas.offsetTop;
    }
    for (i = 0; i < _pieces.length; i++) {
        piece = _pieces[i];
        if (piece.sx == 0 && piece.sy == 0) {
            _stage.fillStyle = "blue";
            _stage.fillRect(piece.xPos, piece.yPos, _pieceWidth, _pieceHeight);
        } else {
            _stage.drawImage(_img, piece.sx, piece.sy, _pieceWidth, _pieceHeight, piece.xPos, piece.yPos, _pieceWidth, _pieceHeight);
            _stage.strokeRect(piece.xPos, piece.yPos, _pieceWidth, _pieceHeight);
        }
    }
    let hoverPiece = checkPieceClicked();
    if (hoverPiece != null) {
        _availableMoves.forEach(piece => {
             if(piece.sy == hoverPiece.sy && piece.sx == hoverPiece.sx){
                _stage.save();
                _stage.globalAlpha = .4;
                _stage.fillStyle = PUZZLE_HOVER_TINT;
                _stage.fillRect(piece.xPos, piece.yPos, _pieceWidth, _pieceHeight);
                _stage.restore();
             }
            
        });
    }

}

function onPuzzleClick(e) {
    if (e.layerX || e.layerX == 0) {
        _mouse.x = e.layerX - _canvas.offsetLeft;
        _mouse.y = e.layerY - _canvas.offsetTop;
    } else if (e.offsetX || e.offsetX == 0) {
        _mouse.x = e.offsetX - _canvas.offsetLeft;
        _mouse.y = e.offsetY - _canvas.offsetTop;
    }

    _currentPiece = checkPieceClicked();
    if (_availableMoves.includes(_currentPiece)) {
        changePiece()
    }
}

function checkAvailableMoves() {
    let availableMoves = [];
    if (_emptyPiecePos >= PUZZLE_DIFFICULTY) {
        availableMoves.push(_pieces[_emptyPiecePos - PUZZLE_DIFFICULTY]);
    }
    if (_emptyPiecePos < PUZZLE_DIFFICULTY * (PUZZLE_DIFFICULTY - 1)) {
        availableMoves.push(_pieces[_emptyPiecePos + PUZZLE_DIFFICULTY]);
    }
    if (_emptyPiecePos % PUZZLE_DIFFICULTY != 0) {
        availableMoves.push(_pieces[_emptyPiecePos - 1]);
    }
    if ((_emptyPiecePos + 1) % PUZZLE_DIFFICULTY != 0) {
        availableMoves.push(_pieces[_emptyPiecePos + 1]);
    }
    return availableMoves;
}

function checkPieceClicked() {
    var i;
    var piece;
    for (i = 0; i < _pieces.length; i++) {
        piece = _pieces[i];
        if (_mouse.x < piece.xPos || _mouse.x > (piece.xPos + _pieceWidth) || _mouse.y < piece.yPos || _mouse.y > (piece.yPos + _pieceHeight)) {
            //PIECE NOT HIT
        }
        else {
            return piece;
        }
    }
    return null;
}

function changePiece() {
    var tmp = { sx: _currentPiece.sx, sy: _currentPiece.sy };
    _currentPiece.sx = _emptyPiece.sx;
    _currentPiece.sy = _emptyPiece.sy;
    _emptyPiece.sx = tmp.sx;
    _emptyPiece.sy = tmp.sy;
    const tmpPiece = _emptyPiece;
    _emptyPiece = _currentPiece;
    _currentPiece = tmpPiece;

    _emptyPiecePos = _emptyPiece.xPos / _pieceWidth + _emptyPiece.yPos /_pieceHeight * PUZZLE_DIFFICULTY;
    _availableMoves = checkAvailableMoves();
    console.log(_emptyPiecePos)
    console.log(_availableMoves);
    resetPuzzleAndCheckWin();   

        console.log(_pieces)
}

function resetPuzzleAndCheckWin() {
    _stage.clearRect(0, 0, _puzzleWidth, _puzzleHeight);
    var gameWin = true;
    var i;
    var piece;
    for (i = 0; i < _pieces.length; i++) {
        piece = _pieces[i];
        if (piece.sx == 0 && piece.sy == 0) {
            _stage.fillStyle = "blue";
            _stage.fillRect(piece.xPos, piece.yPos, _pieceWidth, _pieceHeight);
        } else {
            _stage.drawImage(_img, piece.sx, piece.sy, _pieceWidth, _pieceHeight, piece.xPos, piece.yPos, _pieceWidth, _pieceHeight);
            _stage.strokeRect(piece.xPos, piece.yPos, _pieceWidth, _pieceHeight);
        }

        if (piece.xPos != piece.sx || piece.yPos != piece.sy) {
            gameWin = false;
        }
    }
    if (gameWin) {
        setTimeout(gameOver, 500);
    }
}
function gameOver() {
    document.onmousedown = null;
    document.onmousemove = null;
    document.onmouseup = null;
    initPuzzle();
}
function shuffleArray(o) {
    for (var j, x, i = o.length; i; j = parseInt(Math.random() * i), x = o[--i], o[i] = o[j], o[j] = x);
    return o;
}

