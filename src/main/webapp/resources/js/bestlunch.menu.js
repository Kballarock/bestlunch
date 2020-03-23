var url = window.location.pathname.split("/");
var restaurantId = url[3];
var restaurantName = url[2].replace("%20", " ");
var menuAjaxUrl = "ajax/" + restaurantId + "/menu/";

function updateFilteredTable() {
    $.ajax({
        type: "GET",
        url: menuAjaxUrl,
        data: $("#menuFilter").serialize()
    }).done(updateTableByData);
}

function clearFilter() {
    $("#menuFilter")[0].reset();
    $.get(menuAjaxUrl, updateTableByData);
}

$(function () {
    makeEditable({
        ajaxUrl: menuAjaxUrl,
        datatableOpts: {
            columnDefs: [
                {
                    targets: [3, 4],
                    visible: role === 'ADMIN'
                }
            ],
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

    var menuDate = $('#menuDate');
    menuDate.datetimepicker({
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