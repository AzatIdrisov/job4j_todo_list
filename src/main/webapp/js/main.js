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
    if ($('#cIds').val() == '') {
        fields.push('Категории');
        result = false
    }
    if (fields.length >0 ) {
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
        loadTasksAndCategories();
    }
)


function loadTasksAndCategories() {
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
                let categoryContent = '<ul>';
                let categories = tasks[i].categories;
                for (let j = 0; j < categories.length; j++){
                    categoryContent += '<li>' + categories[j].name + '</li>';
                }
                categoryContent += '</ul>';
                content += categoryContent + '</td>';
                content += '<td>' + tasks[i].author + '</td><td>';
                if (tasks[i].done === true) {
                    content += "<label><input type=\"checkbox\" checked=\"checked\" disabled></label>"
                } else {
                    let id = tasks[i].id;
                    content += "<label><input type=\"checkbox\" id=\"click\" onclick='closeTask(" + id + ")' ></label>";
                }
                content += "</td></tr>";
            }
            $('#table_body').html(content);
            loadCategories(data);
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

function loadCategories(data) {
            let categories = data.allCategories;
            let content = "";
            for (let i = 0; i < categories.length; i++) {
                content += ' <option value=\'' + categories[i].id + '\'>';
                content += categories[i].name + '</option>';
            }
            $('#cIds').html(content);
}
