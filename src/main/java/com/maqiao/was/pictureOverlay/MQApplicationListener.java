/**
 * 
 */
package com.maqiao.was.pictureOverlay;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Scope;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

import com.maqiao.was.pictureOverlay.pictureShape.InterfaceShape;
import com.maqiao.was.pictureOverlay.specialPictures.InterfaceSpecialPicture;
import com.maqiao.was.pictureOverlay.specialPictures.qrCode.InterfaceQRCode;
import com.maqiao.was.pictureOverlay.specialPictures.qrCode.QR_CODE;

/**
 * @author Sunjian
 * @version 1.0
 * @since jdk1.7
 */
@Service("mqPOApplicationListener")
@Scope("singleton")
public class MQApplicationListener implements ApplicationListener<ContextRefreshedEvent>, ApplicationContextAware, ServletContextListener {
	private static ServletContext sc;
	private static ApplicationContext ac;

	/*
	 * (non-Javadoc)
	 * @see org.springframework.context.ApplicationListener#onApplicationEvent(org.springframework.context.ApplicationEvent)
	 */
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		ApplicationContext ac2 = event.getApplicationContext();
		SearchIO(ac2);
	}

	void SearchIO(ApplicationContext ac2) {
		if (ac2 == null) return;
		Object obj = null;
		try {
			for (String name : ac2.getBeanDefinitionNames()) {
				obj = ac2.getBean(name);
				insertObject(obj);
			}
		} catch (Exception e) {
			MQLogger.loggerInfo(e);
		}
	}

	void insertObject(Object obj) {
		/**
		 * 设置图型
		 */
		if (obj instanceof InterfaceShape) {
			InterfaceShape e = (InterfaceShape) obj;
			if (MQConst.ACC_PictureShapeMap.containsKey(e.getShapeID())) return;
			MQConst.ACC_PictureShapeMap.put(e.getShapeID(), e);
		}
		/**
		 * 设置特殊图片
		 */
		if (obj instanceof InterfaceSpecialPicture) {
			InterfaceSpecialPicture e = (InterfaceSpecialPicture) obj;
			if (MQLayerSpecialPicture.ACC_SPExtendMap.containsKey(e.getSpecialPictureID())) return;
			MQLayerSpecialPicture.ACC_SPExtendMap.put(e.getSpecialPictureID(), e);
		}
		/**
		 * 设置特殊图片(二维码样式)
		 */
		if (obj instanceof InterfaceQRCode) {
			InterfaceQRCode e = (InterfaceQRCode) obj;
			String key = e.getKey();
			if (key == null) return;
			for (int i = 0, len = QR_CODE.QRCodeList.size(); i < len; i++)
				if (key.equals(QR_CODE.QRCodeList.get(i))) return;
			QR_CODE.QRCodeList.add(e);
		}

	}

	/*
	 * (non-Javadoc)
	 * @see javax.servlet.ServletContextListener#contextInitialized(javax.servlet.ServletContextEvent)
	 */
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		sc = sce.getServletContext();

	}

	/*
	 * (non-Javadoc)
	 * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.ServletContextEvent)
	 */
	@Override
	public void contextDestroyed(ServletContextEvent sce) {

	}

	/*
	 * (non-Javadoc)
	 * @see org.springframework.context.ApplicationContextAware#setApplicationContext(org.springframework.context.ApplicationContext)
	 */
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		ac = applicationContext;
	}

	/**
	 * 从当前IOC获取bean
	 * @param id
	 *            bean的id
	 * @return Object
	 */
	public static Object getObject(String id) {
		Object object = null;
		object = ac.getBean(id);
		return object;
	}

	public static final ServletContext getSc() {
		return sc;
	}

	public static final void setSc(ServletContext sc) {
		MQApplicationListener.sc = sc;
	}

	public static final ApplicationContext getAc() {
		return ac;
	}

	public static final void setAc(ApplicationContext ac) {
		MQApplicationListener.ac = ac;
	}

}
