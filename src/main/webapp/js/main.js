function validate() {
    let result = true;
    let fields = [];
    if ($('#head').val() == '') {
        fields.push('Заголовок');
        result = false
    }
    if ($('#desc').val() == '') {
        fields.push('Описание');
        result = false
    }
    if (fields.length > 1) {
        let msg = fields[0];
        for (let i = 1; i < fields.length; ++i) {
            msg += ', ' + fields[i];
        }
        msg += ' не заполнены';
        alert(msg);
    }
    return result;
}


$(document).ready(
    function () {
        loadTasks();
    }
)


function loadTasks() {
    $.ajax({
        type: 'GET',
        url: 'http://localhost:8080/todo/task.do?show_all=' + $('#show_all').is(":checked"),
        contentType: 'application/json',
        dataType: 'json',
        success: function (data) {
            let tasks = data.tasks;
            let content = "";
            for (let i = 0; i < tasks.length; i++) {
                content += '<tr><td>' + tasks[i].head + '</td>';
                content += '<td>' + tasks[i].desc + '</td><td>';
                if (tasks[i].done === true) {
                    content += "<label><input type=\"checkbox\" checked=\"checked\" disabled></label>"
                } else {
                    let id = tasks[i].id;
                    content += "<label><input type=\"checkbox\" id=\"click\" onclick='closeTask(" + id + ")' ></label>";
                }
                content += "</td></tr>";
            }
            $('#table_body').html(content);
        }
    })
}

function closeTask(id) {
    let result = false;
    $.ajax({
        type: 'POST',
        url: 'http://localhost:8080/todo/task.do',
        data: {"task_id": id},
        success: function () {
            loadTasks();
            result = true;
        }
    });
    return result;
}


