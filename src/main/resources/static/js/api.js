var main = {
  init: function () {
    var _this = this;

    $('#btn-register').on('click', function () {
      _this.register();
    });

    $('#btn-address').on('click', function () {
      _this.address();
    });

    $('#btn-oauth-address').on('click', function () {
      _this.oauthAddress();
    });

    $('#btn-oauth-register').on('click', function () {
      _this.oauthRegister();
    });

    $('#btn-save').on('click', function () {
      _this.save();
    });

    $('#btn-update').on('click', function () {
      _this.update();
    });

    $('#btn-delete').on('click', function () {
      _this.delete();
    });

    $('#btn-like').on('click', function () {
      _this.postLike();
    });

    $('#btn-comment').on('click', function () {
      _this.comment();
    });

    $('#btn-comment-del').on('click', function () {
      _this.commentDel();
    });

    $('#btn-comment-like').on('click', function () {
      _this.commentLike();
    });

    $('#btn-ban').on('click', function () {
      _this.ban();
    });

    $('#btn-stop-ban').on('click', function () {
      _this.stopBan();
    });

    $('#btn-post-del-admin').on('click', function () {
      _this.adminDeletePost();
    });

    $('#btn-comment-del-admin').on('click', function () {
      _this.adminDeleteComment();
    });

    $('#btn-password-update').on('click', function () {
      _this.passwordUpdate();
    });

    $('#btn-info-update').on('click', function () {
      _this.infoUpdate();
    })
  },

  register: function () {
    var data = {
      email: $('#email').val(),
      password: $('#password').val(),
      name: $('#name').val(),
      birth: $('#birth').val(),
      address: $('#address').val(),
      detailAddress: $('#detailAddress').val()
    };

    $.ajax({
      type: 'POST',
      url: '/user/register',
      dataType: 'json',
      contentType: 'application/json; charset=utf-8',
      data: JSON.stringify(data)
    }).done(function (res) {

      if (res.status) {
        alert(res.message);
        location.href = '/';
      } else {
        alert(res.message);
      }

    }).fail(function (error) {
      alert(error.responseText);
    })
  },

  address: function () {

    new daum.Postcode({
      oncomplete: function (data) {

        var addr = '';

        if (data.userSelectedType === 'R') {
          addr = data.roadAddress;
        } else {
          addr = data.jibunAddress;
        }

        document.getElementById('address').value = addr;
        document.getElementById('detailAddress').focus();
      }
    }).open();
  },

  oauthAddress: function () {

    new daum.Postcode({
      oncomplete: function (data) {

        var addr = '';

        if (data.userSelectedType === 'R') {
          addr = data.roadAddress;
        } else {
          addr = data.jibunAddress;
        }

        document.getElementById('address').value = addr;
        document.getElementById('detailAddress').focus();
      }
    }).open();
  },

  oauthRegister: function () {
    var data = {
      email: $('#email').val(),
      birth: $('#birth').val(),
      address: $('#address').val(),
      detailAddress: $('#detailAddress').val()
    };

    $.ajax({
      type: 'POST',
      url: '/user/oauth-register',
      dataType: 'json',
      contentType: 'application/json; charset=utf-8',
      data: JSON.stringify(data)
    }).done(function (res) {

      if (res.status) {
        alert(res.message);
        location.href = '/logout';
      } else {
        alert(res.message);
      }

    })
  },

  save: function () {
    var data = {
      title: $('#title').val(),
      category: $('input[type=radio][name=category]:checked').val(),
      content: $('#content').val()
    };

    $.ajax({
      type: 'POST',
      url: '/post/save',
      dataType: 'json',
      contentType: 'application/json; charset=utf-8',
      data: JSON.stringify(data)
    }).done(function (res) {
      alert(res.message);
      location.href = '/post/list';
    }).fail(function (error) {
      alert(JSON.stringify(error));
    })
  },

  update: function () {
    var data = {
      title: $('#title').val(),
      writer : $('#writer').val(),
      category: $('input[type=radio][name=category]:checked').val(),
      content: $('#content').val()
    };

    var id = $('#id').val();

    $.ajax({
      type: 'PUT',
      url: '/post/update/' + id,
      dataType: 'json',
      contentType: 'application/json; charset=utf-8',
      data: JSON.stringify(data)
    }).done(function (res) {

      if (res.status) {
        alert(res.message);
        location.href = '/post/list';
      } else {
        alert(res.message);
      }
    }).fail(function (error) {
      JSON.stringify(error);
    })
  },

  delete: function () {
    var id = $('#id').val();

    $.ajax({
      type: 'DELETE',
      url: '/post/delete/' + id,
      dataType: 'json',
      contentType: 'application/json; charset=utf-8',
    }).done(function (res) {
      if(res.status){
        alert(res.message);
        location.href = '/post/list';
      }else{
        alert(res.message);
      }
    }).fail(function (error) {
      alert(JSON.stringify(error));
    })
  },

  postLike: function () {

    var postId = $('#btn-like').val();

    $.ajax({
      type: 'POST',
      url: '/post/like/' + postId,
      dataType: 'json',
      contentType: 'application/json; charset=utf-8',
    }).done(function (res) {
      if (res.status) {
        alert(res.message);
        $('#likeNum').text(res.likeNum);
      } else {
        alert(res.message);
      }
    })
  },

  comment: function () {
    var data = {
      postId: $('#post-id').val(),
      comment: $('#comment').val()
    };

    $.ajax({
      type: 'POST',
      url: '/comment/save',
      dataType: 'json',
      contentType: 'application/json; charset=utf-8',
      data: JSON.stringify(data)
    }).done(function (res) {
      if (res.status) {
        alert(res.message);
        location.reload();
      }
    })
  },

  commentDel: function () {
    var commentId = $('#btn-comment-del').val();

    $.ajax({
      type: 'DELETE',
      url: '/comment/delete/' + commentId,
      dataType: 'json',
      contentType: 'application/json; charset=utf-8'
    }).done(function (res) {

      if (res.status) {
        alert(res.message);
        location.reload();
      }
    }).fail(function (err) {
      alert(JSON.stringify(err));
    })
  },

  commentLike: function () {
    var commentId = $('#btn-comment-like').val();

    $.ajax({
      type: 'POST',
      url: '/comment/like/' + commentId,
      dataType: 'json',
      contentType: 'application/json; charset=utf-8'
    }).done(function (res) {
      if (res.status) {
        alert(res.message);
        $('#comment-like').text(res.likeNum);
      }
    }).fail(function (err) {
      alert(JSON.stringify(err));
    })
  },

  ban: function () {
    var email = $('#btn-ban').val();

    $.ajax({
      type: 'PUT',
      url: '/admin/users/status/' + email
    }).done(function (res) {

      if (res.status) {
        alert(res.message);
        location.reload();
      }
    })
  },

  stopBan: function () {
    var email = $('#btn-stop-ban').val();

    $.ajax({
      type: 'PUT',
      url: '/admin/users/status/' + email
    }).done(function (res) {
      if (res.status) {
        alert(res.message);
        location.reload();
      }
    })
  },

  adminDeletePost: function () {
    var id = $('#btn-post-del-admin').val();

    $.ajax({
      type: 'DELETE',
      url: '/admin/posts/' + id,
    }).done(function (res) {
      alert(res.message);
      location.reload();
    }).fail(function (error) {
      alert(JSON.stringify(error));
    })
  },

  adminDeleteComment: function () {
    var commentId = $('#btn-comment-del-admin').val();

    $.ajax({
      type: 'DELETE',
      url: '/admin/comments/' + commentId
    }).done(function (res) {
      alert(res.message);
      location.reload();
    }).fail(function (error) {
      alert(JSON.stringify(error));
    })
  },

  passwordUpdate: function () {
    var data = {
      email: $('#email').val(),
      password: $('#password').val(),
      repassword: $('#repassword').val()
    };

    $.ajax({
      type: 'PUT',
      url: '/user/update-password',
      dataType: 'json',
      contentType: 'application/json; charset=utf-8',
      data: JSON.stringify(data)
    }).done(function (res) {
      alert(res.message);
    }).fail(function (err) {
      alert(JSON.stringify(err));
    })
  },

  infoUpdate: function () {
    var email = $('#email').val();

    var data = {
      email: $('#email').val(),
      address: $('#address').val(),
      detailAddress: $('#detailAddress').val()
    };

    $.ajax({
      type: 'PUT',
      url: '/user/update-info',
      dataType: 'json',
      contentType: 'application/json; charset=utf-8',
      data: JSON.stringify(data)
    }).done(function (res) {
      if (res.result) {
        alert(res.message);
        location.href = '/user/my-page';
      }
    }).fail(function(res){
        alert(res.responseText);
    })
  }
};

main.init();