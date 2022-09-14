let commentPage = 0
let commentPageSize = 10
let commentTemplate
let hasNextComments = true

function postComment(articleId) {
    console.log('post comment func called articleId : ' + articleId)
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");

    let commentDTO = {
        content: $('#input-comment-content').val()
    }

    $.ajax({
        type: 'POST',
        url: '/comments/' + articleId,
        data : commentDTO,
        dataType: 'json',
        beforeSend: function (xhr) {
            xhr.setRequestHeader(header, token);
        },
        success: function (resultMap) {
            const commentContainer = document.getElementById("comment-container")
            const comment = resultMap.comment
            const commentBox = createComment(comment)
            commentContainer.prepend(commentBox)
        }
    })
}

function commentOnload(articleId) {
    appendCommentsByPage(articleId, commentPage)
    commentTemplate = document.getElementById("comment-box")
    commentPage++
}

function getComments(articleId) {
    if (hasNextComments) {
        appendCommentsByPage(articleId, commentPage)
        commentPage++
    } else
        console.log("no more contents")
}

// create comment
function createComment(comment) {
    const commentBox = commentTemplate.cloneNode(true)
    commentBox.hidden = false

    // commentBox childs
    const commentProfileImg = commentBox.querySelector('#comment-profile-img')
    commentProfileImg.src ="/profiles/thumbnail.jpeg"
    // userprofile

    const commentProfileName = commentBox.querySelector('#comment-profile-name')
    commentProfileName.innerHTML = comment.username

    const commentContent = commentBox.querySelector('#comment-content')
    commentContent.innerHTML = comment.content

    return commentBox
}

function appendCommentsByPage(articleId, page) {
    $.ajax({
        type: "GET",
        url: '/comments/' + articleId,
        dataType: "json",
        data: {'page': page},
        success: function (resultMap) {
            const commentContainer = document.getElementById("comment-container")

            const comments = resultMap.comments
            for (let comment of comments) {
                // init commentBox attributes
                const commentBox = createComment(comment)
                // add commentBox at commentContainer
                commentContainer.appendChild(commentBox)
            }
            if (comments.length < commentPageSize) {
                console.log('has not next comments')
                hasNextComments = false
                $('#comment-more').hide()
            }
        }
    })
}