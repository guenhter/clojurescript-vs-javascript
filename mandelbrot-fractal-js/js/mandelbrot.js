(function() {
    var WIDTH = 900;
    var HEIGHT = 600;

    var started = false;
    var history = [];
    var iteration = 0;

    function mandelIter(cx, cy, maxIter) {
        var x = 0.0;
        var y = 0.0;

        var i = 0;
        while (i < maxIter && x*x + y*y < 2*2) {
            var xtemp = x*x - y*y + cx;
            y = 2*x*y + cy;
            x = xtemp
            i++;
        }
        return i;
    }

    function mandelbrot(xmin, xmax, ymin, ymax, iterations) {
        var model = [];

        for (var ix = 0; ix < WIDTH; ++ix) {
            for (var iy = 0; iy < HEIGHT; ++iy) {
                var x = xmin + (xmax - xmin) * ix / (WIDTH - 1);
                var y = ymin + (ymax - ymin) * iy / (HEIGHT - 1);
                model[WIDTH * iy + ix] = mandelIter(x, y, iterations);
            }
        }
        return model;
    }

    function rgb(pix, ppos, r, g, b) {
        pix[ppos] = r;
        pix[ppos + 1] = g;
        pix[ppos + 2] = b;
        pix[ppos + 3] = 255;
    }

    function mandelbrotToCanvas(model, canvas, iterations) {
        var ctx = canvas.getContext('2d');
        var img = ctx.getImageData(0, 0, WIDTH, HEIGHT);
        var pix = img.data;

        for (var ix = 0; ix < WIDTH; ++ix) {
            for (var iy = 0; iy < HEIGHT; ++iy) {
                var ppos = 4 * (WIDTH * iy + ix);
                var i = model[WIDTH * iy + ix];

                if (i >= iterations) {
                    rgb(pix, ppos, 0, 0, 0);
                } else {
                    var c = 3 * Math.log(i) / Math.log(iterations - 1.0);

                    if (c < 1) {
                        rgb(pix, ppos, 255 * c, 0, 0);
                    } else if ( c < 2 ) {
                        rgb(pix, ppos, 255, 255 * (c - 1), 0);
                    } else {
                        rgb(pix, ppos, 255, 255, 255 * (c - 2));
                    }
                }
            }
        }
        ctx.putImageData(img, 0, 0);
        return model;
    }

    function gameLoop() {
        if (!started) return;
        var limit = document.getElementById('iterationsInput').value;
        if (iteration > Number(limit)) {
            startStop();
            return;
        }

        var canvas = document.getElementById('canvas');
        canvas.width = WIDTH;
        canvas.height = HEIGHT;

        var model = history[iteration];
        if (!model) {
            model = mandelbrot(-2, 1, -1, 1, iteration);
            history[iteration] = model;
        }
        mandelbrotToCanvas(model, canvas, iteration);
        document.getElementById('iterationsOutput').innerHTML = iteration;
        iteration++;
    }

    function startStop() {
        iteration = 0;
        if (started) {
            document.getElementById('startStopButton').innerHTML = 'Start';
            document.getElementById('iterationsInput').disabled = false;
        } else {
            document.getElementById('startStopButton').innerHTML = 'Stop'
            document.getElementById('iterationsInput').disabled = true;
        }

        started = !started;
    }

    function init() {
        document.getElementById('startStopButton').onclick = startStop;
        window.setInterval(gameLoop, 30);
    }

    init();
}());