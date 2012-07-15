package net.slipp.support.wiki;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

public class SlippWikiUtils {
	private static Logger logger = LoggerFactory.getLogger(SlippWikiUtils.class);

	private static final Pattern IMAGE_WIKI_PATTERN = Pattern.compile("!([a-zA-Z0-9]{32})!");

	public static List<String> createImageListFrom(String contents) {
		logger.debug("content : {}", contents);
		Matcher matcher = IMAGE_WIKI_PATTERN.matcher(contents);
		List<String> images = Lists.newArrayList();
		for (int i = 0; matcher.find(); i++) {
			logger.debug("index : {}, result : {}", i, matcher.group(1));
			if (!images.contains(matcher.group(1))) {
				images.add(matcher.group(1));
			}
		}
		return ImmutableList.copyOf(images);
	}

	public static String convertTabToSpace(String contents) {
		if (contents == null) {
			return null;
		}
		
		return contents.replace("\t", "  ");
	}

	public static String replaceImages(String contents, String slippUrl) {
		Matcher matcher = IMAGE_WIKI_PATTERN.matcher(contents);
		if (matcher.find()) {
			contents = matcher.replaceAll(createImageHtml(slippUrl, matcher.group(1)));
		}
		return contents;
	}
	
	private static String createImageHtml(String slippUrl, String attachmentId) {
		String imageUrl = slippUrl + "/attachments/" + attachmentId;
		return "<img src=\"" + imageUrl + "\"/>";
	}
}
