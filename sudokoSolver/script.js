const sudokuGrid = document.getElementById('sudoku-grid');
const newGameButton = document.getElementById('new-game');
const submitButton = document.getElementById('submit');
const result = document.getElementById("result");

function createSudokuGrid(matrix) {
    sudokuGrid.innerHTML = '';
    matrix.forEach(row => {
        row.forEach(cell => {
            const cellElement = document.createElement('div');
            cellElement.className = 'cell' + (cell === null ? ' blank' : '');
            cellElement.contentEditable = cell === null; // Only make blank cells editable
            cellElement.textContent = cell === null ? '' : cell;
            cellElement.addEventListener('input', handleCellInput);
            sudokuGrid.appendChild(cellElement);
        });
    });
}

function handleCellInput(event) {
    const cell = event.target;
    const value = cell.textContent;
    if (!/^[1-9]$/.test(value)) {
        cell.textContent = ''; // Clear invalid input
    }
}

function startNewGame() {
    const newMatrix = generateSudoku();
    createSudokuGrid(newMatrix);
    result.innerHTML="";
}

function getSudokuMatrix() {
    const matrix = [];
    let currentRow = [];
    let cellCount = 0;
    sudokuGrid.childNodes.forEach(cellElement => {
        if (cellCount % 9 === 0) {
            if (currentRow.length > 0) {
                matrix.push(currentRow);
            }
            currentRow = [];
        }
        currentRow.push(cellElement.textContent ? parseInt(cellElement.textContent) : null);
        cellCount++;
    });
    if (currentRow.length > 0) {
        matrix.push(currentRow);
    }
    return matrix;
}

var isValidSudoku = function(board) {
    let row = {};
    let col = {};
    let matrix={};
    
    for (let i =0 ; i<9 ; i++){
        for ( let j =0 ; j<9 ; j++){
           
            let num = board[i][j];
            
            if ( num === '.'){
                continue;
            }
            if ( num<0 || num>9){
                return false;
            }
            let idx =`${Math.floor(i / 3)}${Math.floor(j / 3)}`;
            
            if(!row.hasOwnProperty(i)){
                row[i]=new Set();
            }
            if(!col.hasOwnProperty(j)){
                col[j]=new Set();
            }
            if(!matrix.hasOwnProperty(idx)){
                matrix[idx]=new Set();
            }
            
            if(
                row[i].has(num)
                || col[j].has(num)
                || matrix[idx].has(num)
                
            ){
                return false;
            }
            col[j].add(num);
            row[i].add(num);
            matrix[idx].add(num);
    }
    }
    return true;
    
};

newGameButton.addEventListener('click', startNewGame);
submitButton.addEventListener('click',()=>{
  const matrix =  getSudokuMatrix();
  const isValid = isValidSudoku(matrix);
  if(isValid){
    result.innerHTML="Winner Winner Chicken Dinner";
  }else{
    result.innerHTML="Try Again";
  }
})
function generateSudoku() {
    // Helper function to create a fully completed valid Sudoku grid
    function fillGrid(grid) {
        const emptyCells = findEmptyCells(grid);
        if (emptyCells.length === 0) {
            return true; // All cells are filled
        }
        const [row, col] = emptyCells[0];
        const numbers = shuffle([...Array(9).keys()].map(i => i + 1));
        for (let num of numbers) {
            if (isValidPlacement(grid, row, col, num)) {
                grid[row][col] = num;
                if (fillGrid(grid)) {
                    return true;
                }
                grid[row][col] = null; // Undo the move
            }
        }
        return false;
    }

    // Helper function to find empty cells
    function findEmptyCells(grid) {
        const emptyCells = [];
        for (let row = 0; row < 9; row++) {
            for (let col = 0; col < 9; col++) {
                if (grid[row][col] === null) {
                    emptyCells.push([row, col]);
                }
            }
        }
        return emptyCells;
    }

    // Helper function to check if a number can be placed in a cell
    function isValidPlacement(grid, row, col, num) {
        // Check row and column
        for (let i = 0; i < 9; i++) {
            if (grid[row][i] === num || grid[i][col] === num) {
                return false;
            }
        }
        // Check 3x3 box
        const startRow = Math.floor(row / 3) * 3;
        const startCol = Math.floor(col / 3) * 3;
        for (let i = startRow; i < startRow + 3; i++) {
            for (let j = startCol; j < startCol + 3; j++) {
                if (grid[i][j] === num) {
                    return false;
                }
            }
        }
        return true;
    }

    // Helper function to shuffle an array
    function shuffle(array) {
        for (let i = array.length - 1; i > 0; i--) {
            const j = Math.floor(Math.random() * (i + 1));
            [array[i], array[j]] = [array[j], array[i]];
        }
        return array;
    }

    // Helper function to create a puzzle from the completed grid
    function createPuzzle(grid, numHoles) {
        let puzzle = grid.map(row => row.slice());
        while (numHoles > 0) {
            const row = Math.floor(Math.random() * 9);
            const col = Math.floor(Math.random() * 9);
            if (puzzle[row][col] !== null) {
                puzzle[row][col] = null;
                numHoles--;
            }
        }
        return puzzle;
    }

    // Create a completed Sudoku grid
    const grid = Array.from({ length: 9 }, () => Array(9).fill(null));
    fillGrid(grid);

    // Remove numbers to create the puzzle
    const numHoles = 40; // Adjust the number of holes to control difficulty
    const puzzle = createPuzzle(grid, numHoles);

    return puzzle;
}


// Initialize the game on page load
startNewGame();
