var menuAjaxUrl = "/ajax/admin/{restaurantId}/menu";

function updateFilteredTable() {
    $.ajax({
        type: "GET",
        url: menuAjaxUrl + "filter",
        data: $("#filter").serialize()
    }).done(updateTableByData);
}

function clearFilter() {
    $("#filter")[0].reset();
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
        updateTable: function () {
            if (dateVotingFrom.val() === "" && dateVotingTo.val() === "") {
                $.get(menuAjaxUrl, updateTableByData);
            } else {
                updateFilteredTable();
            }
        }
    });

    $.datetimepicker.setLocale(localeCode);

    var dateVotingFrom = $('#dateVotingFrom');
    var dateVotingTo = $('#dateVotingTo');

    dateVotingFrom.datetimepicker({
        timepicker: false,
        format: 'Y-m-d',
        formatDate: 'Y-m-d',
        onShow: function (ct) {
            this.setOptions({
                maxDate: dateVotingTo.val() ? dateVotingTo.val() : false
            })
        }
    });
});


