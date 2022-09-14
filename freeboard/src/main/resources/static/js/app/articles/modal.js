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
            $('#article-show-modal').modal('hide')
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
            $('#article-show-modal').modal('show').on(commentOnload(id))
        }
    })
}

function closeArticleShowModal(){
    $('#article-show-modal').modal('hide')
}