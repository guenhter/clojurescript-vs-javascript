var data = { a: 1, b: 2 };
renderUI(data); // update the DOM with the given data
loadDataIntoStore(url, () => {
    renderUI(data); // may have changed -> re-render
});


var data = { a: 1, b: 2 };
renderUI(data); // update the DOM with the given data
Object.observe(data, changes => {
    renderUI(data, changes);
});
loadDataIntoStore(url);



var data { a: 1, c: 2};
renderUI(data);
Object.observe(data, changes => {
    renderUI(data, changes);
});
// update the fields
data.a = 10;
data.b = 20;


var data { a: 1, b: {b1: 1}}
Object.observe(data, changes => {
    renderUI(data, changes);
});
data.b.b1 = 100;


var data { a: 1, b: {b1: 1}}
Object.observeDeeply(data, changes => {
    renderUI(data, changes);
});
var old = data.c;
data.b.b1 = 100; // observed
data.b = {b1: 2}; // change of 'c' also observed
data.b.a1 = 100; // not observed because c is new
old.b1 = 50; // still under observation



var data {
    dirty: false,
    _raw: {key: "value"},
    get: function(key) {
        return this._raw[key];
    }
    set: function (key, newValue) {
        this._raw[key] = newValue;
        this.dirty true;
    }
}
function renderUI(data) {
    if (!data.dirty) return;
    data.dirty = false;
    // actually updates the UI
}
data.a = 3;
renderUI(data); // actually renders
renderUI(data); // does nothing
renderUI(data); // does nothing
data.a = 4; // marks
renderUI(data); // actually renders



function renderHeader(data) {
    if (!data.dirty) return;
    data.dirty = false;
    // actually updates the UI
}
function renderSidebar(data) {
    if (!data.dirty) return;
    data.dirty = false;
    // actually updates the UI
}
data.a = 3;
renderHeader(data); // actually renders
renderSidebar(data); // does nothing!




