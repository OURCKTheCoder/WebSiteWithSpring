package top.ourck.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import top.ourck.beans.News;
import top.ourck.dao.NewsDAO;
import top.ourck.util.ImageUtil;

@Service
public class NewsService {

	@Autowired
	private NewsDAO newsDAO;
	
	public News getNewsById(int id) {
		return newsDAO.selectById(id);
	}
	
	public List<News> getLatestNews(int userId, int offset, int limit) {
		return newsDAO.selectByUserIdAndOffset(userId, offset, limit);
	}
	
	public String saveImageToLocal(MultipartFile file) throws IOException {
		// 1. 扩展名验证
		int dotPos = file.getOriginalFilename().lastIndexOf('.');
		if(dotPos < 0)
			return null;
		String extName = file.getOriginalFilename().substring(dotPos + 1).toLowerCase();
		if(!ImageUtil.isValid(extName))
			return null;
		
		// 2. 生成文件名
		try {
			String fileName = UUID.randomUUID().toString().replaceAll("-", "") + "." + extName;
			Files.copy(file.getInputStream(),
						new File(ImageUtil.IMAGE_SAVE_PATH + fileName).toPath(),
						StandardCopyOption.REPLACE_EXISTING);
			// 3. 下发访问地址
			return ImageUtil.IMAGE_DOMAIN + "image?name=" + fileName;
		} catch (IOException e) {
			IOException ex = new IOException("NewsService: Upload image failed!" + e.getMessage());
			ex.initCause(e);
			throw ex;
		}
	}
	
	public void updateCommentCount(int newsId, int newCommentCount) {
		newsDAO.updateCommentCount(newsId, newCommentCount);
	}
	
	public void updateLikeCount(int newsId, int newLikeCount) {
		newsDAO.updateLikeCount(newsId, newLikeCount);
	}
	
	public void addNews(News news) {
		newsDAO.addNews(news);
	}
	
	public void addNews(String title, String link, String image, int userId) {
		News news = new News();
		news.setTitle(title);
		news.setLink(link);
		news.setImage(image);
		news.setUserId(userId);
		news.setCommentCount(0);
		news.setLikeCount(0);
		news.setCreatedDate(new Date());
		addNews(news);
	}
}
