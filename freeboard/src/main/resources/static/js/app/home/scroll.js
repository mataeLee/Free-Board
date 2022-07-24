let articleTemplate = ""
let page = 0
let pageSize = 9
let hasNextArticles = true

window.onload = function (e) {
    appendArticlesAll(page)
    articleTemplate = document.getElementById("article-card")
    page++
}

window.onscroll = function (e) {
    if ((window.innerHeight + window.scrollY) >= document.getElementById("article-grid").offsetHeight) {
        if (hasNextArticles) {
            appendArticlesAll(page)
            page++
        } else
            console.log("no more contents")
    }
}

// create article
function createArticle(article) {
    const articleCard = articleTemplate.cloneNode(true)
    articleCard.hidden = false
    // article card onclick event listener
    articleCard.onclick = () => {
        openArticleShowModal(article.id)
    }
    // articleCard.onclick = () => {
    //     location.href = window.location.pathname + '/' + article.id
    // }

    // article card childs
    const articleThumbnail = articleCard.querySelector("#article-thumbnail")
    articleThumbnail.src = '/profiles/thumbnail.jpeg'
    // articleThumbnail.src = article.thumbnail

    const articleTitle = articleCard.querySelector("#article-title")
    articleTitle.innerHTML = article.title

    const articleSummary = articleCard.querySelector("#article-summary")
    articleSummary.innerHTML = article.summary

    return articleCard
}

function appendArticlesAll(page) {
    $.ajax({
        type: "GET",
        url: window.location.pathname + '/scroll',
        dataType: "json",
        data: {'page': page},
        success: function (resultMap) {
            console.log('scroll req path: ' + window.location.pathname + ' success! page is: ' + page)
            const articleGrid = document.getElementById("article-grid")

            const articles = resultMap.articles
            for (let article of articles) {
                // article card init attributes
                const articleCard = createArticle(article)
                // article grid add article card
                articleGrid.appendChild(articleCard)
            }
            if (articles.length < pageSize) hasNextArticles = false
        }
    })
}