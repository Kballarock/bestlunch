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

function voteCreateOrUpdate(chkbox, id) {
    var vote = chkbox.is(":checked");
    console.log(vote);
    $.ajax({
        url: restaurantAjaxUrl + id + "/vote",
        type: "POST",
        data: "vote=" + vote
    }).done(function () {
        if (vote) {
            $.get(restaurantAjaxUrl, updateTableByData);
            successNoty(vote ? "common.vote.enabled" : "common.vote.disabled");
        } else {
            voteDelete(chkbox, id);
        }
    }).fail(function () {
        $(chkbox).prop("checked", !vote);
    });

}

function voteDelete(chkbox, id) {
    var vote = chkbox.is(":checked");
    $.ajax({
        url: restaurantAjaxUrl + id + "/vote/",
        type: "DELETE",
        data: "vote=" + vote
    }).done(function () {
        $.get(restaurantAjaxUrl, updateTableByData);
        successNoty(vote ? "common.vote.enabled" : "common.vote.disabled");
    }).fail(function () {
        $(chkbox).prop("checked", !vote);
    });

}

$(function () {
    makeEditable({
        ajaxUrl: restaurantAjaxUrl,
        datatableOpts: {
            "columnDefs": [
                {"className": "dt-center", "targets": [5]}
            ],
            "columns": [
                {
                    "orderable": false,
                    "orderData": false,
                    "defaultContent": "",
                    "render": menuBtn
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
                    "render": dateLocale
                },
                {
                    "data": "amount"
                },
                {
                    "data": "vote",
                    "render": voteForRestaurant
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

function menuBtn(data, type, row) {
    if (type === "display") {
        return "<div style='text-align: center'>" +
            "<a title='" + menuBtnTitle() + "' href='" + row.name + "/" + row.id + "/menu'><span class='fa fa-book' style='color: #f37a24;'></span></a>" +
            "</div>";
    }
}

function dateLocale(data, row) {
    var date = new Date(data);
    var month = date.getMonth() + 1;
    if (localeCode === 'ru') {
        return date.getDate() + "-" + (month.toString().length > 1 ? month : "0" + month) + "-" + date.getFullYear() + "&nbsp;" + (date.getHours() < 10 ? ("0" + date.getHours()) : date.getHours()) + ":" + (date.getMinutes() < 10 ? ("0" + date.getMinutes()) : date.getMinutes());
    }
    return data;
}

function voteForRestaurant(data, type, row) {
    if (type === "display") {
        return "<div title='"+ voteBtnTitle() +"' class='vote-checkbox' style='text-align: center'>" +
            "<input id='" + row.id + "' type='checkbox' onclick='voteCreateOrUpdate($(this), " + row.id + ");' " + (data ? "checked" : "") + "/>" +
            "<label for='" + row.id + "'></label>" +
            "</div>";
    }
    return data;
}

function menuBtnTitle() {
    return menuMsg;
}

function voteBtnTitle() {
    return voteMsg;
}