const INDEX_PAGE = (function () {
    const Api = function () {
        const request = {
            get(path) {
                return axios.get(`${path}`);
            },
            post(path, data) {
                return axios.post(`${path}`, data);
            },
            put(path, data) {
                return axios.put(`${path}`, data);
            },
            delete(path) {
                return axios.delete(`${path}`);
            }
        };

        return {
            request: request
        };
    };

    const IndexPageController = function () {
        const searchService = new SearchService();
        const articleService = new ArticleService();
        const commentService = new CommentService();
        const ddabongService = new DdabongService();

        const toggleSearchInput = function () {
            document.querySelector('.search-toggle').addEventListener('click', searchService.toggleSearchInput);
        };

        const showSearchedList = function () {
            document.querySelector(".search-input input").addEventListener('keyup', searchService.showSearchedList);
        };

        const fetchArticles = function () {
            articleService.fetchArticlePages();
        };

        const addComment = function () {
            document.querySelectorAll('.btn-add-comment')
                .forEach(el => el.addEventListener('click', commentService.addComment));
        };

        const toggleHeart = function () {
            document.querySelectorAll('.fa-heart-o').forEach((el) => {
                el.addEventListener('click', ddabongService.toggleHeart);
            })
        };

        const getScrollTop = function () {
            return (window.pageYOffset !== undefined) ? window.pageYOffset : (document.documentElement || document.body.parentNode || document.body).scrollTop;
        };

        const getDocumentHeight = function () {
            const body = document.body;
            const html = document.documentElement;

            return Math.max(
                body.scrollHeight, body.offsetHeight,
                html.clientHeight, html.scrollHeight, html.offsetHeight
            );
        };

        const init = function () {
            toggleSearchInput();
            showSearchedList();
            fetchArticles();
            addComment();
            toggleHeart();
        };

        const onscroll = function () {
            // console.log('getScrollTop : ' + getScrollTop());
            // console.log('getDocumentHeight : ' + getDocumentHeight());
            // console.log('window.innerHeight : ' + window.innerHeight);

            if (getScrollTop() === getDocumentHeight() - window.innerHeight) {
                articleService.fetchArticlePages();
            }
        };

        return {
            init: init,
            onscroll: onscroll,
        };
    };

    const ArticleService = function () {
        const fetchArticlePages = function () {
            console.log('fetch Articles Pages');
        };

        return {
            fetchArticlePages: fetchArticlePages,
        }
    };

    const SearchService = function () {
        const toggleSearchInput = function (event) {
            event.preventDefault();
            document.querySelector('.search-box').classList.toggle('active')
            document.querySelector(".search-input").classList.toggle("active")
            document.querySelector(".search-input input").focus()
        };

        const showSearchedList = function (event) {
            if (event.target.value.length > 0) {
                document.querySelector(".advanced-search").classList.add("active")
            } else {
                document.querySelector(".advanced-search").classList.remove("active")
            }
        };

        return {
            toggleSearchInput: toggleSearchInput,
            showSearchedList: showSearchedList,
        }
    };

    const CommentService = function () {
        const request = new Api().request;

        const getCommentTemplate = function (nickName, commentContents) {
            return `<li>
                          <p class="inline-block text-bold  no-mrg-btm mrg-left-15">
                              ${nickName}
                          </p>
                          <p class="inline-block no-mrg-btm mrg-left-5">${commentContents}</p>
                    </li>`;
        };

        const addComment = function (event) {
            const message = event.target.closest("div");
            const articleId = message.id.split("-")[2];

            const inputValue = message.querySelector("input").value;
            const commentList = message.parentElement.querySelector("#comment-list");

            if (inputValue.length < 1 || inputValue.length > 100) {
                alert('댓글은 1글자 이상 100글자 이하로 입력해 주세요');
                return;
            }

            request
                .post('/' + articleId + '/comments/new', {contents: inputValue})
                .then(res => {
                    console.log(res);
                    const nickName = res.data.commenterNickName;
                    const commentContents = res.data.commentContents;

                    const comment = getCommentTemplate(nickName, commentContents);
                    commentList.insertAdjacentHTML('beforeend', comment);
                    document.getElementById('comment-input').value = '';
                }).catch(err => {
                alert(err.response.data);
            });
        };

        return {
            addComment: addComment,
        }
    };

    const DdabongService = function () {
        const request = new Api().request;

        const toggleHeart = function (event) {
            event.preventDefault();
            const message = event.target.closest("div");
            const articleId = message.id;
            const childNodes = message.childNodes;
            const ddabongCountTag = childNodes[7].childNodes[3].childNodes[3];

            request
                .get('/articles/' + articleId + '/ddabongs')
                .then(response => {
                    console.log(response);
                    ddabongCountTag.innerText = response.data.count;

                    if (response.data.clicked === true) {
                        event.target.classList.remove('fa-heart-o');
                        event.target.classList.add('fa-heart', 'activated-heart');
                    } else {
                        event.target.classList.remove('fa-heart', 'activated-heart');
                        event.target.classList.add('fa-heart-o');
                    }
                });
        };
        return {
            toggleHeart: toggleHeart,
        }
    };

    const indexPageController = new IndexPageController();

    const init = function () {
        indexPageController.init();
    };

    const onscroll = function () {
        indexPageController.onscroll();
    };

    return {
        init: init,
        onscroll: onscroll,
    }
})();

INDEX_PAGE.init();

window.onscroll = function () {
    INDEX_PAGE.onscroll();
};