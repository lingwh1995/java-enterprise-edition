package com.dragonsoft.service;

/**
 * 发送邮件接口
 */
public interface IMailService {

    /**
     * 发送简单邮件
     * @param to 邮件接收者
     * @param subject 邮件主题
     * @param content 邮件内容
     */
    void sendSimpleMail(String to, String subject, String content);

    /**
     * 发送html邮件
     * @param to 邮件接收者
     * @param subject 邮件主题
     * @param content 邮件内容
     */
    void sendHtmlMail(String to, String subject, String content);

    /**
     * 发送带有附件的邮件
     * @param to 邮件接收者
     * @param subject 邮件主题
     * @param content 邮件内容
     * @param filePath 文件路径
     */
    void sendAttachmentsMail(String to, String subject, String content, String filePath);

    /**
     * 发送嵌入静态资源的邮件
     * @param to
     * @param subject
     * @param content
     * @param rscPath
     * @param rscId
     */
    void sendInlineResourceMail(String to, String subject, String content, String rscPath, String rscId);

}
