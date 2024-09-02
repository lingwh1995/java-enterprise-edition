package com.dragonsoft;

import com.dragonsoft.service.IMailService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 测试使用Springboot发送各种邮件
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MailServiceTest {

    @Autowired
    private IMailService mailService;

    /**
     * 测试发送普通邮件
     * @throws Exception
     */
    @Test
    public void testSimpleMail() throws Exception {
        mailService.sendSimpleMail("1458687169@qq.com","test simple mail"," hello this is simple mail");
    }

    /**
     * 测试发送html邮件
     * @throws Exception
     */
    @Test
    public void testHtmlMail() throws Exception {
        String content="<html>\n" +
                "<body>\n" +
                "    <h3>hello world ! 这是一封html邮件!</h3>\n" +
                "</body>\n" +
                "</html>";
        mailService.sendHtmlMail("1458687169@qq.com","test simple mail",content);
    }

    /**
     * 测试发送带有附件的邮件
     */
    @Test
    public void sendAttachmentsMail() {
        String filePath="e:\\a.txt";
        mailService.sendAttachmentsMail("1458687169@qq.com", "主题：带附件的邮件", "有附件，请查收！", filePath);
    }

    /**
     * 测试发送带有静态资源的邮件
     */
    @Test
    public void sendInlineResourceMail() {
        String rscId = "001";
        String content="<html><body>这是有图片的邮件：<img src=\'cid:" + rscId + "\' ></body></html>";
        String imgPath = "e:\\a.png";

        mailService.sendInlineResourceMail("1458687169@qq.com", "主题：这是有图片的邮件", content, imgPath, rscId);
    }
}
