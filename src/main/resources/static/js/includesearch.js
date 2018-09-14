"use strict";
/**
 * 收录查询逻辑
 */
$(function () {
    console.log('你在破解吗？');

    var tabs_li = $('.inc-tabs li'),
        url_text_input = $('#url-text'),
        result_div = $('.inc-result'),
        total_result_div = $('.total-result'),
        inc_result_div = $('.inc-result'),
        total_input_count = total_result_div.find('strong').eq(0),
        searched_count = total_result_div.find('strong').eq(1),
        included_count = total_result_div.find('strong').eq(2),
        un_included_count = total_result_div.find('strong').eq(3),
        included_rate = total_result_div.find('strong').eq(4),
        result_item = $('#result-item'),
        inc_btn = $('.inc-but'),
        _id = 1;

    inc_btn.click(function () {
        var text = url_text_input.val();
        if ('' === text)
            return false;

        var parse = parse_text(text);
        if (parse[0] !== '') {
            alert(parse[0]);
            return false;
        }

        search(parse[1]);
    });



    function parse_text(text) {
        if (text === '')
            return ['请填写网站', []];

        var u = [], rh=/^https:\/{2}/, rh2=/^http:\/{2}/, m='';
        $.each(text.split('\n'), function(i,r){
            r = $.trim(r);
            if (r === '') return
            if (!rh.test(r) && !rh2.test(r)) {
                m = '第' + (i+1) + '个url地址不合法';
            }else{
                u.push(r);
            }
        });

        if (m !== '')
            return [m, []];

        if (u.length > 10) {
            return ['最多查询10个地址', []];
        }

        return ['', u];
    }

    function get_img(s, r, u) {
        if (s === '1') {
            return '<a href="https://www.baidu.com/s?wd='+u+'" target="_blank"><img src="images/baidu'+(r === '1' ? '' : '-no')+'.jpg"/></a>'
        }
        if (s === '3') {
            return '<a href="https://www.so.com/s?q='+u+'" target="_blank"><img src="images/360'+(r === '1' ? '' : '-no')+'.jpg"/></a>'
        }
        return '<a href="https://www.sogou.com/web?query='+u+'" target="_blank"><img src="images/sougou'+(r === '1' ? '' : '-no')+'.jpg"/></a>'
    }

    function get_id(){
        _id += 1;
        return 'tr-' + _id;
    }

    function search(urls) {

        var param = {
            sourceList: ['1', '2', '3'],
            urlList: urls
        }

        $('.fakeloader').show();
        $('.fakeloader').fakeLoader({
            timeToHide: 10 * 1000
        })
        $.ajax({
            url: '/api/seo/url_search_info/batch_query_url',
            datatype: 'json',
            type: 'post',
            headers:{
                Accept:"application/json",
                "Content-Type":"application/json"
            },
            data: JSON.stringify(param),
            success: function (json) {

                if (json.resultCode === '000000') {

                    result_item.html('');
                    var _d = {}

                    var n = 0;
                    for (var i=0; i < json.data.length; i++) {
                        var d = json.data[i];
                        if (d.record === '1') {
                            n += 1;
                        }

                        if (_d[d.url]) {
                            _d[d.url].find('td').last().append(get_img(d.source, d.record, d.url));
                        }else{
                            var tr = $('<tr><td id="'+get_id()+'">'+d.url+'</td><td>'+get_img(d.source, d.record, d.url)+'</td></tr>');
                            _d[d.url] = tr;
                            result_item.append(tr);
                        }
                    }

                    inc_result_div.removeClass('hide');
                    total_input_count.html(urls.length);
                    searched_count.html(urls.length * 3);
                    included_count.html(n);
                    un_included_count.html(urls.length * 3 - n);
                    included_rate.html(((n / (urls.length * 3)) * 100).toFixed(0) + '%');

                }else{
                    alert(json.resultMesg);
                }

            }
        }).always(function () {
            $('.fakeloader').fadeOut();
        });
    }

})
