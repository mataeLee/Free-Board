function openArticleNewFormModal() {
    $.ajax({
        type: 'GET',
        url: '/articles/new',
        dataType: 'html',
        success: function (form) {
            $('#app-body').empty()
            $('#app-body').append(form)
            $('#article-modal').modal('show')
        }
    })
}

function openArticleEditFormModal(id) {
    $.ajax({
        type: 'GET',
        url: '/articles/' + id + '/edit',
        dataType: 'html',
        success: function (form) {
            $('#article-modal-show').modal('hide')
            $('#app-body').empty()
            $('#app-body').append(form)
            $('#article-modal').modal('show')
        }
    })
}

function openArticleShowModal(id) {
    $.ajax({
        type: 'GET',
        url: '/articles/' + id,
        dataType: 'html',
        success: function (form) {
            $('#app-body').empty()
            $('#app-body').append(form)
            $('#article-modal-show').modal('show')
        }
    })
}