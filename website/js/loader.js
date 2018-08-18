// TODO make this work dynamically
function Loader() {
    this.load = function(pages) {
        $('#content').empty();
        var divs = [];
        if (pages != null) {
            pages.forEach(function(page) {
                var div = document.createElement('div');
                div.setAttribute("id", page);
                var loaded = $(div).load(page + '.html');
                $('#content').append(div);
                divs.push(div);
            });
        }
        return divs;
    };
};
