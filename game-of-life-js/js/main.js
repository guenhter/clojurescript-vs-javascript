(function() {
    var GRID_H_W = 4; // 1 px border makes 10 px per cell
    var COLOR_DEAD_CELL = '#D9D9D9';
    var COLOR_LIFE_CELL = '#0000FF';
    var COLOR_BORDER = '#9E9E9E';
    var MAX_COLUMNS = 200;
    var MAX_ROWS = 100;
    var SPEED_MS = 10;
    var TRACK_GENERATIONS = true;

    var canvas;
    var context;
    var generationCount = 0;
    var generationHistory = [];
    var currentGeneration = [];
    var started = false;
    var pattern1 = [[1,0], [1,2], [0,2], [3,1], [4,2], [5,2], [6,2]];
    var pattern2 = [[2,0], [0,1], [2,1], [1,2], [2,2]];
    var pattern = pattern1;

    init();

    function init() {
        initFirstGeneration();

        canvas = document.getElementById("gamescreen");
        canvas.width = MAX_COLUMNS * (GRID_H_W + 1); // 1 px for the grid
        canvas.height = MAX_ROWS * (GRID_H_W + 1); // 1 px for the grid
        context = canvas.getContext("2d");
        drawCanvasGrid(currentGeneration);

        document.getElementById('stepButton').onclick = updateToNextGeneration;
        document.getElementById('startStopButton').onclick = function() {
            if (started) {
                document.getElementById('startStopButton').innerHTML = 'Start'
            } else {
                document.getElementById('startStopButton').innerHTML = 'Stop'
            }
            started = !started;
        };
        document.getElementById('goToGenerationButton').onclick = function() {
            var level = document.getElementById('goToGenerationInput').value;
            if (level > 0 && level < generationHistory.length) {
                drawCanvasGrid(generationHistory[level])
            }
        };

        window.setInterval(gameLoop, SPEED_MS);
    }

    function gameLoop() {
        if (started) {
            updateToNextGeneration()
        }
    }

    function updateToNextGeneration() {
        toNextGeneration();
        drawCanvasGrid(currentGeneration)
    }

    function toNextGeneration() {
        currentGeneration = createNextGeneration(currentGeneration);

        if (TRACK_GENERATIONS) {
            generationHistory[generationCount] = currentGeneration
        }
        generationCount++;

        var generationElem = document.getElementById('generation');
        generationElem.innerHTML = generationCount
    }

    function initFirstGeneration() {
        var newGeneration = [];
        var plusX = MAX_COLUMNS / 2;
        var plusY = MAX_ROWS / 2;

        for (var i = 0; i < pattern.length; i++) {
            var coordinate = pattern[i];
            newGeneration[index(coordinate[0] + plusX, coordinate[1] + plusY)] = true
        }

        currentGeneration = newGeneration;
        if (TRACK_GENERATIONS) {
            generationHistory[generationCount] = newGeneration
        }
        generationCount++
    }

    function drawCanvasGrid(generation) {
        fillBackground();

        for (var x = 0; x < MAX_COLUMNS; x++) {
            for (var y = 0; y < MAX_ROWS; y++) {
                drawRect(
                    x * (GRID_H_W + 1),
                    y * (GRID_H_W + 1),
                    generation[index(x, y)] ? COLOR_LIFE_CELL : COLOR_DEAD_CELL);
            }
        }
    }

    function fillBackground() {
        context.fillStyle = COLOR_BORDER;
        context.fillRect(0, 0, canvas.width, canvas.height)
    }

    function drawRect(x, y, fillColor) {
        context.fillStyle = fillColor;
        context.fillRect(x, y, GRID_H_W, GRID_H_W)
    }

    function createNextGeneration(oldGeneration) {
        var newGeneration = [MAX_COLUMNS, MAX_ROWS];


        for (var x = 0; x < MAX_COLUMNS; x++) {
            for (var y = 0; y < MAX_ROWS; y++) {
                var livingNeighbors = getLivingNeighbors(x, y, oldGeneration);

                newGeneration[index(x, y)] = oldGeneration[index(x, y)]
                    ? (livingNeighbors == 2 || livingNeighbors == 3)
                    : (livingNeighbors == 3)
            }
        }
        return newGeneration
    }

    function getLivingNeighbors(x, y, oldGeneration) {
        var living = 0;
        for (var xOffset = -1; xOffset <= 1; xOffset++) {
            for (var yOffset = -1; yOffset <= 1; yOffset++) {
                if ((xOffset != 0 || yOffset != 0) && oldGeneration[index(getX(x + xOffset), getY(y + yOffset))]) {
                    living++
                }
            }
        }
        return living
    }

    function index(x, y) {
        return x * MAX_COLUMNS + y
    }

    function getX(x) {
        return getXY(x, MAX_COLUMNS)
    }
    function getY(y) {
        return getXY(y, MAX_ROWS)
    }
    function getXY(xy, maxXY) {
        if (xy < 0) {
            return getXY(xy + maxXY)
        }
        if (xy >= maxXY) {
            return getXY(xy - maxXY)
        }
        return xy;
    }
}());


