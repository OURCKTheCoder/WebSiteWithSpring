package top.ourck.test;

import java.util.Date;
import java.util.List;
import java.util.Random;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import top.ourck.beans.News;
import top.ourck.dao.NewsDAO;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class NewsDAOTest {

	@Autowired
	private NewsDAO newsDAO;
	
//	@Test
	public void insertTest() {
		Random r = new Random(System.currentTimeMillis());
		for(int i = 0; i < 11; i++ ) {
			News news = new News();
			news.setCommentCount(i);
	        Date date = new Date();
	        date.setTime(date.getTime() + 1000 * 3600 * 5 * i);
	        news.setCreatedDate(date);
	        news.setImage(String.format("http://images.nowcoder.com/head/%dm.png", r.nextInt(1000)));
	        news.setLikeCount(i + 1);
	        news.setLink(String.format("http://www.nowcoder.com/link/{%d}.html", i));
	        news.setTitle(String.format("Title {%d} ", i));
	        news.setUserId(i+1);
	        
	        newsDAO.addNews(news);
	        System.out.println(news.getId());
		}
	}
	
	@Test
	public void getNewsTest() {
		List<News> nList = newsDAO.selectByUserIdAndOffset(1, 0, 10);
		for(News news : nList) System.out.println(news);
	}
	
}
