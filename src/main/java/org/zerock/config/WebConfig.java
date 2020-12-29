package org.zerock.config;

import java.io.IOException;

import javax.servlet.ServletRegistration.Dynamic;

import org.springframework.context.annotation.Bean;
import org.springframework.core.io.FileSystemResource;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

// web.xml 대신
public class WebConfig extends AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class[] { RootConfig.class };
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class[] { ServletConfig.class };
	}

	@Override
	protected String[] getServletMappings() {
		return new String[] { "/" };
	}

	@Override
	protected void customizeRegistration(Dynamic registration) {
		registration.setInitParameter("throwExceptionIfNoHandlerFound", "true");
	}
	
	@Bean(name = "multipartResolver")
	public CommonsMultipartResolver getResolver() throws IOException {
		CommonsMultipartResolver resolver = new CommonsMultipartResolver();
		
		resolver.setMaxUploadSize(1024 * 1024 * 10);
		resolver.setMaxUploadSizePerFile(1024 * 1024 * 2);
		resolver.setMaxInMemorySize(1024 * 1024);
		
		resolver.setUploadTempDir(new FileSystemResource(""));
		resolver.setDefaultEncoding("UTF-8");
		
		return resolver;
	}
	
	
	
	
	
}
