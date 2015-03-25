package com.hua.goddess.utils;

import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.hua.goddess.vo.CommentVO;
import com.hua.goddess.vo.NewsContentVo;

public class HtmlResolving {
	/*
	 * 解析新闻类容
	 */
	public ArrayList<NewsContentVo> getNewsContent(String news_detaiol) {
		ArrayList<NewsContentVo> contents = new ArrayList<NewsContentVo>();
		NewsContentVo ncv = null;

		Document document = Jsoup.parse(news_detaiol);

		Elements info = document.getElementsByTag("span");
		for (Element element : info) {
			ncv = new NewsContentVo();
			ncv.setIsImg(0);
			ncv.setContentList(element.text());
			contents.add(ncv);
		}
		Elements elements = document.getElementsByTag("p");
		Elements media = document.select("[src]");

		int i = 1;
		for (Element element : elements) {

			if (element.hasText()) {
				ncv = new NewsContentVo();
				ncv.setIsImg(0);
				ncv.setContentList(element.text());
				contents.add(ncv);
			} else {
				if (element.hasAttr("align") && media != null
						&& media.size() > 0 && media.size() > i) {
					Element src = media.get(i);
					if (src.tagName().equals("img")) {
						ncv = new NewsContentVo();
						ncv.setIsImg(1);
						ncv.setContentList(src.attr("src"));
						contents.add(ncv);
					}
					i++;
				}
			}
		}

		return contents;
	}

	/*
	 * 新闻评论解析
	 */
	public ArrayList<CommentVO> getCommentContent(String comments) {
		ArrayList<CommentVO> comm = new ArrayList<CommentVO>();
		CommentVO cvo = null;
		Document document = Jsoup.parse(comments);

		Elements elements = document.getElementsByClass("content");
		for (Element element : elements) {
			cvo = new CommentVO();
			cvo.setTime(element.child(0).text());
			cvo.setName(element.child(1).text());
			cvo.setContent(element.ownText());
			comm.add(cvo);
		}
		return comm;
	}

}
