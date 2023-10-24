var main={ //이렇게 메인 변수로 함수를 묶어둠으로써 중복 js의 함수명겹칩 문제를 해결한다.
    init : function (){ // 초기화하겠다는 의미 , :는 JavaScript 객체의 속성(property)을 정의하는 구문입니다. init이라는 이름으로 함수를 정의하겠다는 의미
        var _this = this;
        $('#btn-save').on('click',function (){ // 제이쿼리로 잡은 객체에 클릭이 발생하면 펑션을 실행하겠다는 의미
            _this.save();

        });
    },

    save : function (){
        var data = {
            title: $('#title').val(), //$는 제이쿼리의 해당 객체를 잡겠다는 의미, #은 html태그의 id를 조회하겠다는 것, val()은 제이쿼리에서 잡은 객체의 값을 가져오겠다는 의미. title : 은 키 값으로 쓰겠다는 의미
            author: $('#author').val(),
            content: $('#content').val()
        };

        $.ajax({ // json으로 통신하기 위한 아잭스
            type: 'POST',
            url: '/api/v1/posts', //  /는 루트 ./현재위치 ../는 상위위치
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data)  // JavaScript에서 사용되는 메서드로, JavaScript 객체나 배열을 JSON 문자열로 변환하는 역할을 합니다
        }).done(function (){
            alert('글이 등록되었습니다');
            window.location.href = '/';
        }).fail(function (error){
            alert(JSON.stringify(error));
        });
    }
};
main.init();