'use strict';
var AV = require('avoscloud-sdk').AV;
var aimer = require('aimer')

var appId = 'i7j2k7bm26g7csk7uuegxlvfyw79gkk4p200geei8jmaevmx';
var appKey = 'n6elpebcs84yjeaj5ht7x0eii9z83iea8bec9szerejj7zy3';
AV.initialize(appId,appKey);

var reg = new RegExp("<(?!br).*?>", "gi");

for(var page=0;page<=10;page++)
{
	aimer('http://ear.duomi.com/?cat=13&paged='+page)
	.then($ =>
	{
		$('.post').each(function ()
		{
			const title = $(this).find('.title a').attr('title')
			const url = $(this).find('.title a').attr('href')
			const imgurl = $(this).find('.thumbnail').attr('src')
			const time = $(this).find('.thumbnail').attr('src')

			aimer(url)
			.then($ =>
			{
				var article="";
				var author=$('.post-meta').find(".comments").eq(1).text();
				var i=0;
				$('.entry p').each(function(){
					if(i>1&&$(this).html().indexOf("本文章版权属于")<0)//&&$(this).html().indexOf("曲目")<0)
						article+=$(this).html();
					i++;
				})
				console.log(title)

              // 基本存储
              var TestClass = AV.Object.extend('Image');
              var testObj = new TestClass();
              testObj.set({
              	type:"民谣",
              	author: author,
              	title: title,
              	image: imgurl,
              	article: article.replace(reg, "")
              });

              testObj.save().then(function () {
              	console.log('success');
              }).catch(function (err) {
              	console.log('failed');
              	console.log(err);
              });

          }).catch(e => console.log(e.message))
		})
}).catch(e => console.log(e.message))
}



