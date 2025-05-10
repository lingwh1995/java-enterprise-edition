package org.bluebridge.mvcsuperior.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author ronin
 * @version V1.0
 * @desc
 * @since 2019/7/23 11:06
 */
public interface IRequestContextHolderService {
    HttpServletRequest getHttpServletRequest();
    HttpServletResponse getHttpServletResponse();
}
