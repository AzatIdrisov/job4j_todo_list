function validate() {
    let result = true;
    let fields = [];
    if ($('#name').val() == '') {
        fields.push('Имя');
        result = false
    }
    if ($('#email').val() == '') {
        fields.push('email');
        result = false
    }
    if ($('#password').val() == '') {
        fields.push('пароль');
        result = false
    }
    if (fields.length > 0) {
        let msg = fields[0];
        for (let i = 1; i < fields.length; ++i) {
            msg += ', ' + fields[i];
        }
        msg += ' не заполнено';
        alert(msg);
    }
    return result;
}
