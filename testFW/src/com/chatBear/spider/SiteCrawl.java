package com.chatBear.spider;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

import org.apache.log4j.Logger;

import com.chatBear.enums.PageState;
import com.chatBear.enums.SiteState;
import com.chatBear.enums.SiteType;
import com.chatBear.model.CrawlPage;
import com.chatBear.model.CrawlSite;
import com.testFW.servlet.ChatServlet;
import com.testFW.util.DateUtil;
import com.testFW.util.XMLUtil;

public class SiteCrawl {
	private static Logger logger = Logger.getLogger(ChatServlet.class);

	/**
	 * 爬取指定的页面
	 * 
	 * @param page
	 */
	private CrawlPage doCrawlPage(CrawlPage page) {
		logger.info("---爬取页面URL：" + page.getUrl() + "--开始时间:"
				+ DateUtil.getTimeNow());

		// 初始化待爬取url
		URL url;
		try {
			url = new URL(page.getUrl());

			// 获取链接
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestProperty("User-Agent",
					"Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");// IE代理进行下载
			conn.setConnectTimeout(60000);
			conn.setReadTimeout(60000);
			conn.connect();

			// 获得网页返回信息码
			int responseCode = -1;
			responseCode = conn.getResponseCode();
			if (responseCode == -1) {
				logger.error("爬取页面：" + url.toString()
						+ "---出错----：错误信息： connection is failure...");
				conn.disconnect();
				return page;
			}
			logger.info("爬取页面：" + url.toString() + "的response code: "
					+ responseCode);
			if (responseCode >= 400) // 请求失败
			{
				logger.error("爬取页面" + url.toString()
						+ "---请求失败:response code: " + responseCode);
				conn.disconnect();
				return page;
			}

			page.setState(PageState.CRAWLING);
			// 获取网站的头部文件
			Map<String, List<String>> header = conn.getHeaderFields();
			StringBuffer headerStr = new StringBuffer();
			for (String key : header.keySet()) {
				headerStr.append(key + ":" + header.get(key) + "|");
			}
			page.setHead(headerStr.toString());

			// 获取网站内容
			BufferedReader br = new BufferedReader(new InputStreamReader(
					conn.getInputStream(), page.getUnicode()));
			StringBuffer content = new StringBuffer();
			String str = null;
			while ((str = br.readLine()) != null) {
				content.append(str + "\n");
			}
			page.setContent(content.toString());

			// 一次爬取结束
			conn.disconnect();
			page.setState(PageState.CRAWLED);
			logger.info("---爬取页面URL：" + page.getUrl() + "--结束时间:"
					+ DateUtil.getTimeNow());
		} catch (Exception e) {
			page.setState(PageState.ERROR);
			logger.error("---爬取页面URL：" + page.getUrl() + "--出错！");
			e.printStackTrace();
		}
		return page;
	}

	/**
	 * 爬取指定的网站
	 * 
	 * @return
	 */
	private CrawlSite doCrawlSite(CrawlSite site) {
		logger.info("---爬取站点：" + site.getName() + "--开始时间:"
				+ DateUtil.getTimeNow());

		try {
			List<CrawlPage> result = new ArrayList();

			// 多页网站
			if (SiteType.MUTI_PAGE.equals(site.getType())) {
				// 依据首页信息获取分页信息，并初始化urls
				site = initUrlsByPage(site);
				// 依据urls，返回需要遍历的页面
				List<CrawlPage> toCrawlPages = initCrawlPages(site);
				// 遍历页面集合，获取信息后返回
				for (CrawlPage page : toCrawlPages) {
					page = doCrawlPage(page);
					site.setCurrentUrl(page.getUrl());
					result.add(page);
				}
			} else if (SiteType.SINGLE_PAGE.equals(site.getType())) {// 单页网站
				// 获取网站首页内容
				CrawlPage indexPage = doCrawlPage(site.getIndexPage());

				result.add(indexPage);
			}

			site.setResultPages(result);
			logger.info("---爬取站点：" + site.getName() + "--结束时间:"
					+ DateUtil.getTimeNow());
		} catch (Exception e) {
			site.setState(SiteState.ERROR);
			logger.error("---爬取站点：" + site.getName() + "出错!");
			e.printStackTrace();
		}

		return site;
	}

	/**
	 * 依据页面初始化分页Urls--针对页码符合1，2，3...规则的网站
	 * 
	 * @param site
	 * @param page
	 * @return
	 */
	private CrawlSite initUrlsByPage(CrawlSite site) {
		List<String> urls = new ArrayList();
		for (int i = 1; i < site.getTotalPage() + 1; i++) {
			String url = site.getPageUrl().replace("@", i + "");
			urls.add(url);
		}
		site.setUrls(urls);
		return site;
	}

	/**
	 * 依据urls，返回需要遍历的页面
	 * 
	 * @param site
	 * @return
	 */
	private List initCrawlPages(CrawlSite site) {
		List<CrawlPage> result = new ArrayList<CrawlPage>();
		for (String url : site.getUrls()) {
			CrawlPage page = new CrawlPage();
			page.setSiteName(site.getName());
			page.setUnicode(site.getUnicode());
			page.setUrl(url);
			result.add(page);
		}
		return result;
	}

	public static void main(String[] args) {
		List<CrawlSite> sites = XMLUtil.getInstance().sites;
		for(CrawlSite site:sites) {
			SiteCrawl crawl = new SiteCrawl();
			crawl.doCrawlSite(site);
		}
		logger.info("end");
	}
	
	
}