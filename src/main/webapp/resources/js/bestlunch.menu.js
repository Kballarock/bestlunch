var menuAjaxUrl = "ajax/menu/{{ $id }}/";

function updateFilteredTable(id) {
    $.ajax({
        type: "GET",
        url: menuAjaxUrl + id +  "/filter",
        data: $("#menuFilter").serialize()
    }).done(updateTableByData);
}

function clearFilter() {
    $("#menuFilter")[0].reset();
    $.get(menuAjaxUrl, updateTableByData);
}

$.ajaxSetup({
    converters: {
        "text json": function (stringData) {
            var json = JSON.parse(stringData);
            $(json).each(function () {
                this.added = this.added.replace('T', ' ').substr(0, 16);
            });
            return json;
        }
    }
});

$(function () {
    makeEditable({
        ajaxUrl: menuAjaxUrl,
        datatableOpts: {
            "columns": [
                {
                    "data": "name"
                },
                {
                    "data": "price"
                },
                {
                    "data": "date",
                    "render": function (data) {
                        var date = new Date(data);
                        var month = date.getMonth() + 1;
                        if (localeCode === 'ru') {
                            return date.getDate() + "-" + (month.toString().length > 1 ? month : "0" + month) + "-" + date.getFullYear();
                        }
                        return data;
                    }
                },
                {
                    "orderable": false,
                    "defaultContent": "",
                    "render": renderEditBtn
                },
                {
                    "orderable": false,
                    "defaultContent": "",
                    "render": renderDeleteBtn
                }
            ],
            "order": [
                [
                    0,
                    "asc"
                ]
            ]
        },
        updateTable: updateFilteredTable
    });

    $.datetimepicker.setLocale(localeCode);

    var date = $('#date');
    date.datetimepicker({
        timepicker: false,
        format: 'Y-m-d',
        formatDate: 'Y-m-d',
        defaultDate: new Date(),
        onShow: function (ct) {
            this.setOptions({
                maxDate: "+0D"
            })
        }
    });
});


