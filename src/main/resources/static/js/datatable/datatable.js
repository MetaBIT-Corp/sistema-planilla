$(document).ready(function(){

    //DataTable1
    $("#example1").DataTable({
        "responsive": true,
        "autoWidth": false,
    });

    //DataTable2
    $('#example2').DataTable({
        "paging": true,
        "lengthChange": false,
        "searching": false,
        "ordering": true,
        "info": true,
        "autoWidth": false,
        "responsive": true,
    });

    // Restricts input for the given textbox to the given inputFilter.
    function setInputFilter(textbox, inputFilter) {
        ["input", "keydown", "keyup", "mousedown", "mouseup", "select", "contextmenu", "drop"].forEach(function(event) {
            textbox.addEventListener(event, function() {
                if (inputFilter(this.value)) {
                    this.oldValue = this.value;
                    this.oldSelectionStart = this.selectionStart;
                    this.oldSelectionEnd = this.selectionEnd;
                } else if (this.hasOwnProperty("oldValue")) {
                    this.value = this.oldValue;
                    this.setSelectionRange(this.oldSelectionStart, this.oldSelectionEnd);
                }
            });
        });
    }

    setInputFilter(document.getElementById("cantidad"), function(value) {
        return /^\d*$/.test(value);
    });

    setInputFilter(document.getElementById("peso"), function(value) {
        return /^\d*$/.test(value);
    });


    // Integer values (positive only):
    // /^\d*$/.test(value)
    // Integer values (positive and up to a particular limit):
    // /^\d*$/.test(value) && (value === "" || parseInt(value) <= 500)
    // Integer values (both positive and negative):
    // /^-?\d*$/.test(value)
    // Floating point values (allowing both . and , as decimal separator):
    // /^-?\d*[.,]?\d*$/.test(value)
    // Currency values (i.e. at most two decimal places):
    // /^-?\d*[.,]?\d{0,2}$/.test(value)
    // A-Z only (i.e. basic Latin letters):
    // /^[a-z]*$/i.test(value)
    // Latin letters only (i.e. English and most European languages, see https://unicode-table.com for details about Unicode character ranges):
    // /^[a-z\u00c0-\u024f]*$/i.test(value)
    // Hexadecimal values:
    // /^[0-9a-f]*$/i.test(value)

});