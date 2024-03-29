// 提交回复
function post() {
	var questionId = $("#question_id").val();
	var content = $("#comment_content").val();
	comment2target(questionId, 1, content);
}

function comment2target(targetId, type, content) {
	if (!content) {
		alert("不能回复空内容");
		return;
	}

	$.ajax({
		type: "POST",
		url: "/comment",
		data: JSON.stringify({
			"parentId": targetId,
			"content": content,
			"type": type
		}),
		success: function(response) {
			if (response.code == 200) {
				window.location.reload();
			} else {
				if (response.code == 2003) {
					var isAccepted = confirm(response.message);
					if (isAccepted) {
						window.open("https://github.com/login/oauth/authorize?client_id=e5cccabe4f51b89060bc&redirect_url=http://localhost:8080/callback&scope=user&state=1");
						window.localStorage.setItem("closable", true);
					}
				} else {
					alert(response.message);
				}
			}
		},
		dataType: "json",
		contentType: "application/json"
	});
}


function comment(e) {
	var commentId = e.getAttribute("data-id");
	var content = $("#input-" + commentId).val();
	comment2target(commentId, 2, content);
}

/**
展开二级评论
在这个示例代码中，我们首先使用jQuery的hasClass()方法检查评论区域是否具有 "in" 类名。
如果有，我们使用removeClass()方法将其删除，从而折叠该评论区域。
如果没有，我们使用addClass()方法将其添加，从而展开该评论区域。
 */

function collapseComments(e) {
	var id = e.getAttribute("data-id");
	var comments = $("#comment-" + id);

	//获取二级评论的展开状态
	var collapse = e.getAttribute("data-collapse");

	if (collapse) {
		comments.removeClass("in");
		e.removeAttribute("data-collapse");
		e.classList.remove("active");
	} else {
		var subCommentContainer = $("#comment-" + id);
		if (subCommentContainer.children().length != 1) {
			//展开二级评论
			comments.addClass("in");
			//标记二级评论展开状态
			e.setAttribute("data-collapse", "in");
			e.classList.add("active");
		} else {
			$.getJSON("/comment/" + id, function(data) {
				$.each(data.data.reverse(), function(index, comment) {
					var mediaLeftElement = $("<div/>", {
						"class": "midia-left"
					}).append($("<img/>", {
						"class": "media-object img-rounded",
						"src": comment.user.avatarUrl
					}));

					var mediaBodyElement = $("<div/>", {
						"class": "media-body"
					}).append($("<h5/>", {
						"class": "media-heading",
						"html": comment.user.name
					})).append($("<div/>", {
						"html": comment.content
					})).append($("<div/>", {
						"class": "menu"
					}).append($("<span/>", {
						"class": "pull-right",
						"html": moment(comment.gmtCreate).format('YYYY-MM-DD')
					})));

					var mediaElement = $("<div/>", {
						"class": "media"
					}).append(mediaLeftElement).append(mediaBodyElement);

					var commentElement = $("<div/>", {
						"class": "col-lg-12 col-md-12 col-sm-12 col-xs-12 comments"
					}).append(mediaElement);

					subCommentContainer.prepend(commentElement);
				});

				//展开二级评论
				comments.addClass("in");
				//标记二级评论展开状态
				e.setAttribute("data-collapse", "in");
				e.classList.add("active");
			});
		}
	}
}


function selectTag(e) {
	var value = e.getAttribute("data-tag")
	var previous = $("#tag").val();
	if (previous.indexOf(value) == -1) {
		if (previous) {
			$("#tag").val(previous + ',' + value);
		} else {
			$("#tag").val(value);
		}
	}
}


//点击空白关闭弹窗
$(document).mouseup(function(e) {
    var container = $("#select-tag");// 设置目标区域
    // 如果点击事件的目标不是弹出框或者弹出框内部的元素，则隐藏弹出框
    if (!container.is(e.target) && container.has(e.target).length === 0) {
        container.hide();
    }
});


function showSelectTag() {
  $("#select-tag").show();

}

