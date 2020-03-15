var restaurantAjaxUrl = "ajax/admin/";

function updateFilteredTable() {
    $.ajax({
        type: "GET",
        url: restaurantAjaxUrl + "filter",
        data: $("#filter").serialize()
    }).done(updateTableByData);
}

function clearFilter() {
    $("#filter")[0].reset();
    $.get(restaurantAjaxUrl, updateTableByData);
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
        ajaxUrl: restaurantAjaxUrl,
        datatableOpts: {
            "columns": [
                {
                    "orderable": false,
                    "orderData": false,
                    "ordering": false,
                    "defaultContent": "",
                    "render": function (data, type, row) {
                        if (type === "display") {
                            return "<a href='"+ row.name+"/" + row.id + "/menu'><span class='fa fa-book' style='color: #f37a24'></span></a>";
                        }
                    }
                },
                {
                    "data": "name"
                },
                {
                    "data": "description"
                },
                {
                    "data": "address"
                },
                {
                    "data": "added",
                    "render": function (data, row) {
                        var date = new Date(data);
                        var month = date.getMonth() + 1;
                        if (localeCode === 'ru') {
                            return date.getDate() + "-" + (month.toString().length > 1 ? month : "0" + month) + "-" + date.getFullYear() + "&nbsp;" + (date.getHours() < 10 ? ("0" + date.getHours()) : date.getHours()) + ":" + (date.getMinutes() < 10 ? ("0" + date.getMinutes()) : date.getMinutes());
                        }
                        return data;
                    }
                },
                {
                    "data": "amount"
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
                $.get(restaurantAjaxUrl, updateTableByData);
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

    dateVotingTo.datetimepicker({
        timepicker: false,
        format: 'Y-m-d',
        formatDate: 'Y-m-d',
        onShow: function (ct) {
            this.setOptions({
                minDate: dateVotingFrom.val() ? dateVotingFrom.val() : false
            })
        }
    });
});